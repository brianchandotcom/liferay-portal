/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.OldServiceComponentException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableListener;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.model.ServiceComponent;
import com.liferay.portal.service.base.ServiceComponentLocalServiceBaseImpl;
import com.liferay.portal.tools.servicebuilder.Entity;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Field;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class ServiceComponentLocalServiceImpl
	extends ServiceComponentLocalServiceBaseImpl {

	@Override
	public void destroyServiceComponent(
			ServletContext servletContext, ClassLoader classLoader)
		throws SystemException {

		try {
			clearCacheRegistry(servletContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public ServiceComponent initServiceComponent(
			ServletContext servletContext, ClassLoader classLoader,
			String buildNamespace, long buildNumber, long buildDate,
			boolean buildAutoUpgrade)
		throws PortalException, SystemException {

		try {
			ModelHintsUtil.read(
				classLoader, "META-INF/portlet-model-hints.xml");
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		try {
			ModelHintsUtil.read(
				classLoader, "META-INF/portlet-model-hints-ext.xml");
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		ServiceComponent serviceComponent = null;
		ServiceComponent previousServiceComponent = null;

		List<ServiceComponent> serviceComponents =
			serviceComponentPersistence.findByBuildNamespace(
				buildNamespace, 0, 1);

		if (serviceComponents.isEmpty()) {
			long serviceComponentId = counterLocalService.increment();

			serviceComponent = serviceComponentPersistence.create(
				serviceComponentId);

			serviceComponent.setBuildNamespace(buildNamespace);
			serviceComponent.setBuildNumber(buildNumber);
			serviceComponent.setBuildDate(buildDate);
		}
		else {
			serviceComponent = serviceComponents.get(0);

			if (serviceComponent.getBuildNumber() < buildNumber) {
				previousServiceComponent = serviceComponent;

				long serviceComponentId = counterLocalService.increment();

				serviceComponent = serviceComponentPersistence.create(
					serviceComponentId);

				serviceComponent.setBuildNamespace(buildNamespace);
				serviceComponent.setBuildNumber(buildNumber);
				serviceComponent.setBuildDate(buildDate);
			}
			else if (serviceComponent.getBuildNumber() > buildNumber) {
				throw new OldServiceComponentException(
					"Build namespace " + buildNamespace + " has build number " +
						serviceComponent.getBuildNumber() +
							" which is newer than " + buildNumber);
			}
			else {
				return serviceComponent;
			}
		}

		try {
			Document document = SAXReaderUtil.createDocument(StringPool.UTF8);

			Element dataElement = document.addElement("data");

			Element tablesSQLElement = dataElement.addElement("tables-sql");

			String tablesSQL = HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/sql/tables.sql"));

			tablesSQLElement.addCDATA(tablesSQL);

			Element sequencesSQLElement = dataElement.addElement(
				"sequences-sql");

			String sequencesSQL = HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/sql/sequences.sql"));

			sequencesSQLElement.addCDATA(sequencesSQL);

			Element indexesSQLElement = dataElement.addElement("indexes-sql");

			String indexesSQL = HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/sql/indexes.sql"));

			indexesSQLElement.addCDATA(indexesSQL);

			String dataXML = document.formattedString();

			serviceComponent.setData(dataXML);

			serviceComponentPersistence.update(serviceComponent);

			serviceComponentLocalService.upgradeDB(
				classLoader, buildNamespace, buildNumber, buildAutoUpgrade,
				previousServiceComponent, tablesSQL, sequencesSQL, indexesSQL);

			removeOldServiceComponents(buildNamespace);

			return serviceComponent;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public void upgradeDB(
			final ClassLoader classLoader, final String buildNamespace,
			final long buildNumber, final boolean buildAutoUpgrade,
			final ServiceComponent previousServiceComponent,
			final String tablesSQL, final String sequencesSQL,
			final String indexesSQL)
		throws Exception {

		ProtectionDomain protectionDomain = new ProtectionDomain(
			null, null, classLoader, null);

		AccessControlContext accessControlContext = new AccessControlContext(
			new ProtectionDomain[] {protectionDomain});

		AccessController.doPrivileged(
			new PrivilegedExceptionAction<Void>() {

				public Void run() throws Exception {
					doUpgradeDB(
						classLoader, buildNamespace, buildNumber,
						buildAutoUpgrade, previousServiceComponent, tablesSQL,
						sequencesSQL, indexesSQL);

					return null;
				}

			},
			accessControlContext
		);
	}

	@Override
	public void verifyDB() throws SystemException {
		List<ServiceComponent> serviceComponents =
			serviceComponentPersistence.findAll();

		for (ServiceComponent serviceComponent : serviceComponents) {
			String buildNamespace = serviceComponent.getBuildNamespace();
			String tablesSQL = serviceComponent.getTablesSQL();
			String sequencesSQL = serviceComponent.getSequencesSQL();
			String indexesSQL = serviceComponent.getIndexesSQL();

			try {
				serviceComponentLocalService.upgradeDB(
					null, buildNamespace, 0, false, null, tablesSQL,
					sequencesSQL, indexesSQL);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected void clearCacheRegistry(ServletContext servletContext)
		throws DocumentException {

		InputStream inputStream = servletContext.getResourceAsStream(
			"/WEB-INF/classes/META-INF/portlet-hbm.xml");

		if (inputStream == null) {
			return;
		}

		Document document = SAXReaderUtil.read(inputStream);

		Element rootElement = document.getRootElement();

		List<Element> classElements = rootElement.elements("class");

		for (Element classElement : classElements) {
			String name = classElement.attributeValue("name");

			CacheRegistryUtil.unregister(name);
		}

		CacheRegistryUtil.clear();

		EntityCacheUtil.clearCache();
		FinderCacheUtil.clearCache();
	}

	protected void doUpgradeDB(
			ClassLoader classLoader, String buildNamespace, long buildNumber,
			boolean buildAutoUpgrade, ServiceComponent previousServiceComponent,
			String tablesSQL, String sequencesSQL, String indexesSQL)
		throws Exception {

		DB db = DBFactoryUtil.getDB();

		if (previousServiceComponent == null) {
			if (_log.isInfoEnabled()) {
				_log.info("Running " + buildNamespace + " SQL scripts");
			}

			db.runSQLTemplateString(tablesSQL, true, false);
			db.runSQLTemplateString(sequencesSQL, true, false);
			db.runSQLTemplateString(indexesSQL, true, false);
		}
		else if (buildAutoUpgrade) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Upgrading " + buildNamespace +
						" database to build number " + buildNumber);
			}

			if (!tablesSQL.equals(previousServiceComponent.getTablesSQL())) {
				if (_log.isInfoEnabled()) {
					_log.info("Upgrading database with tables.sql");
				}

				db.runSQLTemplateString(tablesSQL, true, false);

				upgradeModels(classLoader, previousServiceComponent);
			}

			if (!sequencesSQL.equals(
					previousServiceComponent.getSequencesSQL())) {

				if (_log.isInfoEnabled()) {
					_log.info("Upgrading database with sequences.sql");
				}

				db.runSQLTemplateString(sequencesSQL, true, false);
			}

			if (!indexesSQL.equals(previousServiceComponent.getIndexesSQL())) {
				if (_log.isInfoEnabled()) {
					_log.info("Upgrading database with indexes.sql");
				}

				db.runSQLTemplateString(indexesSQL, true, false);
			}
		}
	}

	protected List<String> getModels(ClassLoader classLoader)
		throws DocumentException, IOException {

		List<String> models = new ArrayList<String>();

		String xml = StringUtil.read(
			classLoader, "META-INF/portlet-model-hints.xml");

		models.addAll(getModels(xml));

		try {
			xml = StringUtil.read(
				classLoader, "META-INF/portlet-model-hints-ext.xml");

			models.addAll(getModels(xml));
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"No optional file META-INF/portlet-model-hints-ext.xml " +
						"found");
			}
		}

		return models;
	}

	protected List<String> getModels(String xml) throws DocumentException {
		List<String> models = new ArrayList<String>();

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> modelElements = rootElement.elements("model");

		for (Element modelElement : modelElements) {
			String name = modelElement.attributeValue("name");

			models.add(name);
		}

		return models;
	}

	protected UpgradeTableListener getUpgradeTableListener(
		ClassLoader classLoader, Class<?> modelClass) {

		String modelClassName = modelClass.getName();

		String upgradeTableListenerClassName = modelClassName;

		upgradeTableListenerClassName = StringUtil.replaceLast(
			upgradeTableListenerClassName, ".model.impl.", ".model.upgrade.");
		upgradeTableListenerClassName = StringUtil.replaceLast(
			upgradeTableListenerClassName, "ModelImpl", "UpgradeTableListener");

		try {
			UpgradeTableListener upgradeTableListener =
				(UpgradeTableListener)InstanceFactory.newInstance(
					classLoader, upgradeTableListenerClassName);

			if (_log.isInfoEnabled()) {
				_log.info("Instantiated " + upgradeTableListenerClassName);
			}

			return upgradeTableListener;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to instantiate " + upgradeTableListenerClassName);
			}

			return null;
		}
	}

	protected void removeOldServiceComponents(String buildNamespace)
		throws SystemException {

		int serviceComponentsCount =
			serviceComponentPersistence.countByBuildNamespace(buildNamespace);

		if (serviceComponentsCount < _MAX_SERVICE_COMPONENTS) {
			return;
		}

		List<ServiceComponent> serviceComponents =
			serviceComponentPersistence.findByBuildNamespace(
				buildNamespace, _MAX_SERVICE_COMPONENTS,
				serviceComponentsCount);

		for (int i = 0; i < serviceComponents.size(); i++) {
			ServiceComponent serviceComponent = serviceComponents.get(i);

			serviceComponentPersistence.remove(serviceComponent);
		}
	}

	protected void upgradeModels(
			ClassLoader classLoader, ServiceComponent previousServiceComponent)
		throws Exception {

		List<String> models = getModels(classLoader);

		for (String name : models) {
			int pos = name.lastIndexOf(".model.");

			name =
				name.substring(0, pos) + ".model.impl." +
					name.substring(pos + 7) + "ModelImpl";

			Class<?> modelClass = Class.forName(name, true, classLoader);

			Field tableNameField = modelClass.getField("TABLE_NAME");
			Field tableColumnsField = modelClass.getField("TABLE_COLUMNS");
			Field tableSQLCreateField = modelClass.getField("TABLE_SQL_CREATE");
			Field dataSourceField = modelClass.getField("DATA_SOURCE");

			String tableName = (String)tableNameField.get(null);
			Object[][] tableColumns = (Object[][])tableColumnsField.get(null);
			String tableSQLCreate = (String)tableSQLCreateField.get(null);
			String dataSource = (String)dataSourceField.get(null);

			if (!dataSource.equals(Entity.DEFAULT_DATA_SOURCE)) {
				continue;
			}

			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				tableName, tableColumns);

			UpgradeTableListener upgradeTableListener = getUpgradeTableListener(
				classLoader, modelClass);

			upgradeTable.setCreateSQL(tableSQLCreate);

			if (upgradeTableListener != null) {
				upgradeTableListener.onBeforeUpdateTable(
					previousServiceComponent, upgradeTable);
			}

			upgradeTable.updateTable();

			if (upgradeTableListener != null) {
				upgradeTableListener.onAfterUpdateTable(
					previousServiceComponent, upgradeTable);
			}
		}
	}

	private static final int _MAX_SERVICE_COMPONENTS = 10;

	private static Log _log = LogFactoryUtil.getLog(
		ServiceComponentLocalServiceImpl.class);

}