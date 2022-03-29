/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.BaseDBProcess;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.db.IndexMetadataFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public abstract class UpgradeProcess
	extends BaseDBProcess implements UpgradeStep {

	public void clearIndexesCache() {
		_portalIndexesSQL.clear();
	}

	public int getThreshold() {

		// This upgrade process will only run if the build number is larger than
		// the returned threshold value. Return 0 to always run this upgrade
		// process.

		return 0;
	}

	public void upgrade() throws UpgradeException {
		long start = System.currentTimeMillis();

		String message = "Completed upgrade process ";

		try (Connection connection = getConnection()) {
			this.connection = connection;

			if (isSkipUpgradeProcess()) {
				return;
			}

			process(
				companyId -> {
					if (_log.isInfoEnabled()) {
						String info =
							"Upgrading " + ClassUtil.getClassName(this);

						if (Validator.isNotNull(companyId)) {
							info += "#" + companyId;
						}

						_log.info(info);
					}

					doUpgrade();
				});
		}
		catch (Throwable throwable) {
			message = "Failed upgrade process ";

			throw new UpgradeException(throwable);
		}
		finally {
			this.connection = null;

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						message, ClassUtil.getClassName(this), " in ",
						System.currentTimeMillis() - start, " ms"));
			}
		}
	}

	@Override
	public void upgrade(DBProcessContext dbProcessContext)
		throws UpgradeException {

		upgrade();
	}

	public void upgrade(UpgradeProcess upgradeProcess) throws UpgradeException {
		upgradeProcess.upgrade();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public interface Alterable {

		public static boolean containsIgnoreCase(
			Collection<String> columnNames, String columnName) {

			for (String curColumnName : columnNames) {
				if (StringUtil.equalsIgnoreCase(curColumnName, columnName)) {
					return true;
				}
			}

			return false;
		}

		public String getSQL(String tableName);

		public boolean shouldAddIndex(Collection<String> columnNames);

		public boolean shouldDropIndex(Collection<String> columnNames);

	}

	protected SafeCloseable addTempIndex(
			String tableName, boolean unique, String... columnNames)
		throws Exception {

		IndexMetadata indexMetadata = new IndexMetadata(
			"IX_TEMP", tableName, unique, columnNames);

		addIndexes(connection, new ArrayList<>(Arrays.asList(indexMetadata)));

		return () -> {
			try {
				runSQL("drop index IX_TEMP on " + tableName);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to drop temporary index IX_TEMP on " +
							tableName);
				}
			}
		};
	}

	protected abstract void doUpgrade() throws Exception;

	protected void ensureTableExists(
			DatabaseMetaData databaseMetaData, DBInspector dbInspector,
			String tableName)
		throws SQLException {

		try (ResultSet resultSet = databaseMetaData.getTables(
				dbInspector.getCatalog(), dbInspector.getSchema(), tableName,
				null)) {

			if (!resultSet.next()) {
				throw new SQLException(
					StringBundler.concat(
						"Table with name '", tableName, "' does not exist in ",
						dbInspector.getSchema()));
			}
		}
	}

	protected Connection getConnection() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		if (bundle != null) {
			BundleContext bundleContext = bundle.getBundleContext();

			Collection<ServiceReference<DataSource>> serviceReferences =
				bundleContext.getServiceReferences(
					DataSource.class,
					StringBundler.concat(
						"(origin.bundle.symbolic.name=",
						bundle.getSymbolicName(), ")"));

			Iterator<ServiceReference<DataSource>> iterator =
				serviceReferences.iterator();

			if (iterator.hasNext()) {
				ServiceReference<DataSource> serviceReference = iterator.next();

				DataSource dataSource = bundleContext.getService(
					serviceReference);

				try {
					if (dataSource != null) {
						return dataSource.getConnection();
					}
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			}
		}

		return DataAccess.getConnection();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getIndexSQLs(Class, String)}
	 */
	@Deprecated
	protected List<ObjectValuePair<String, IndexMetadata>> getIndexesSQL(
			ClassLoader classLoader, String tableName)
		throws IOException {

		if (!PortalClassLoaderUtil.isPortalClassLoader(classLoader)) {
			try (InputStream inputStream = classLoader.getResourceAsStream(
					"META-INF/sql/indexes.sql")) {

				if (inputStream == null) {
					return null;
				}

				List<ObjectValuePair<String, IndexMetadata>> objectValuePairs =
					new ArrayList<>();

				try (Reader reader = new InputStreamReader(inputStream);
					UnsyncBufferedReader unsyncBufferedReader =
						new UnsyncBufferedReader(reader)) {

					String line = null;

					while ((line = unsyncBufferedReader.readLine()) != null) {
						line = line.trim();

						if (line.isEmpty()) {
							continue;
						}

						IndexMetadata indexMetadata =
							IndexMetadataFactoryUtil.createIndexMetadata(line);

						if (tableName.equals(indexMetadata.getTableName())) {
							objectValuePairs.add(
								new ObjectValuePair<>(line, indexMetadata));
						}
					}
				}

				return objectValuePairs;
			}
		}

		if (!_portalIndexesSQL.isEmpty()) {
			return _portalIndexesSQL.get(tableName);
		}

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/portal/tools/sql/dependencies/indexes.sql");
			Reader reader = new InputStreamReader(inputStream);
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(reader)) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = line.trim();

				if (line.isEmpty()) {
					continue;
				}

				IndexMetadata indexMetadata =
					IndexMetadataFactoryUtil.createIndexMetadata(line);

				List<ObjectValuePair<String, IndexMetadata>> objectValuePairs =
					_portalIndexesSQL.get(indexMetadata.getTableName());

				if (objectValuePairs == null) {
					objectValuePairs = new ArrayList<>();

					_portalIndexesSQL.put(
						indexMetadata.getTableName(), objectValuePairs);
				}

				objectValuePairs.add(
					new ObjectValuePair<>(line, indexMetadata));
			}
		}

		return _portalIndexesSQL.get(tableName);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	protected Map<String, Integer> getTableColumnsMap(Class<?> tableClass)
		throws Exception {

		Field tableNameField = tableClass.getField("TABLE_COLUMNS_MAP");

		return (Map<String, Integer>)tableNameField.get(null);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	protected String getTableName(Class<?> tableClass) throws Exception {
		Field tableNameField = tableClass.getField("TABLE_NAME");

		return (String)tableNameField.get(null);
	}

	protected long increment() {
		return CounterLocalServiceUtil.increment();
	}

	protected long increment(String name) {
		return CounterLocalServiceUtil.increment(name);
	}

	protected long increment(String name, int size) {
		return CounterLocalServiceUtil.increment(name, size);
	}

	protected boolean isPortal62TableName(String tableName) {
		return _portal62TableNames.contains(StringUtil.toLowerCase(tableName));
	}

	protected boolean isSkipUpgradeProcess() throws Exception {
		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(UpgradeProcess.class);

	private static final Set<String> _portal62TableNames = new HashSet<>(
		Arrays.asList(
			"account_", "address", "announcementsdelivery",
			"announcementsentry", "announcementsflag", "assetcategory",
			"assetcategoryproperty", "assetentries_assetcategories",
			"assetentries_assettags", "assetentry", "assetlink", "assettag",
			"assettagstats", "assetvocabulary", "backgroundtask", "blogsentry",
			"blogsstatsuser", "bookmarksentry", "bookmarksfolder",
			"browsertracker", "calevent", "classname_", "clustergroup",
			"company", "contact_", "counter", "country", "ddlrecord",
			"ddlrecordset", "ddlrecordversion", "ddmcontent", "ddmstoragelink",
			"ddmstructure", "ddmstructurelink", "ddmtemplate", "dlcontent",
			"dlfileentry", "dlfileentrymetadata", "dlfileentrytype",
			"dlfileentrytypes_dlfolders", "dlfilerank", "dlfileshortcut",
			"dlfileversion", "dlfolder", "dlsyncevent", "emailaddress",
			"expandocolumn", "expandorow", "expandotable", "expandovalue",
			"exportimportconfiguration", "group_", "groups_orgs",
			"groups_roles", "groups_usergroups", "image", "journalarticle",
			"journalarticleimage", "journalarticleresource",
			"journalcontentsearch", "journalfeed", "journalfolder",
			"journalstructure", "journaltemplate", "layout", "layoutbranch",
			"layoutfriendlyurl", "layoutprototype", "layoutrevision",
			"layoutset", "layoutsetbranch", "layoutsetprototype", "listtype",
			"lock_", "marketplace_app", "mbban", "mbcategory", "mbdiscussion",
			"mbmailinglist", "mbmessage", "mbstatsuser", "mbthread",
			"mbthreadflag", "mdraction", "mdrrule", "mdrrulegroup",
			"mdrrulegroupinstance", "membershiprequest", "organization_",
			"orggrouprole", "orglabor", "passwordpolicy", "passwordpolicyrel",
			"passwordtracker", "phone", "pluginsetting", "pollschoice",
			"pollsquestion", "pollsvote", "portalpreferences", "portlet",
			"portletitem", "portletpreferences", "ratingsentry", "ratingsstats",
			"recentlayoutbranch", "recentlayoutrevision",
			"recentlayoutsetbranch", "region", "release_", "repository",
			"repositoryentry", "resourceaction", "resourceblock",
			"resourceblockpermission", "resourcepermission",
			"resourcetypepermission", "role_", "servicecomponent",
			"socialactivity", "socialactivityachievement",
			"socialactivitycounter", "socialactivitylimit", "socialactivityset",
			"socialactivitysetting", "socialrelation", "socialrequest",
			"subscription", "systemevent", "team", "ticket", "trashentry",
			"trashversion", "usernotificationdelivery", "user_", "usergroup",
			"usergroupgrouprole", "usergrouprole", "usergroups_teams",
			"useridmapper", "usernotificationevent", "users_groups",
			"users_orgs", "users_roles", "users_teams", "users_usergroups",
			"usertracker", "usertrackerpath", "virtualhost", "webdavprops",
			"website", "wikinode", "wikipage", "wikipageresource",
			"workflowdefinitionlink", "workflowinstancelink"));
	private static final Map
		<String, List<ObjectValuePair<String, IndexMetadata>>>
			_portalIndexesSQL = new HashMap<>();

}