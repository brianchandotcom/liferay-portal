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

package com.liferay.portal.service.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBContext;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.exception.OldServiceComponentException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ServiceComponent;
import com.liferay.portal.kernel.service.configuration.ServiceComponentConfiguration;
import com.liferay.portal.kernel.service.configuration.servlet.ServletServiceContextComponentConfiguration;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableListener;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.service.base.ServiceComponentLocalServiceBaseImpl;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.io.IOException;
import java.io.OutputStream;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class ServiceComponentLocalServiceImpl
	extends ServiceComponentLocalServiceBaseImpl {

	public ServiceComponentLocalServiceImpl() {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			StringBundler.concat(
				"(&(objectClass=", UpgradeStep.class.getName(),
				")(upgrade.from.schema.version=0.0.0)(upgrade.initial.",
				"database.creation=true))"));

		_upgradeStepServiceTracker = registry.trackServices(
			filter, new UpgradeStepServiceTrackerCustomizer());

		_upgradeStepServiceTracker.open();
	}

	@Override
	public void destroy() {
		super.destroy();

		_upgradeStepServiceTracker.close();
	}

	@Override
	public void destroyServiceComponent(
		ServiceComponentConfiguration serviceComponentConfiguration,
		ClassLoader classLoader) {

		if (PropsValues.CACHE_CLEAR_ON_PLUGIN_UNDEPLOY) {
			CacheRegistryUtil.clear();
		}
	}

	@Override
	public List<ServiceComponent> getLatestServiceComponents() {
		return serviceComponentFinder.findByMaxBuildNumber();
	}

	@Override
	public ServiceComponent initServiceComponent(
			ServiceComponentConfiguration serviceComponentConfiguration,
			ClassLoader classLoader, String buildNamespace, long buildNumber,
			long buildDate)
		throws PortalException {

		try {
			ModelHintsUtil.read(
				classLoader,
				serviceComponentConfiguration.getModelHintsInputStream());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}

		try {
			ModelHintsUtil.read(
				classLoader,
				serviceComponentConfiguration.getModelHintsExtInputStream());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}

		long previousBuildNumber = 0;
		ServiceComponent previousServiceComponent = null;
		ServiceComponent serviceComponent = null;

		Map<String, ServiceComponent> serviceComponents =
			_getServiceComponents();

		serviceComponent = serviceComponents.get(buildNamespace);

		if (serviceComponent == null) {
			long serviceComponentId = counterLocalService.increment();

			serviceComponent = serviceComponentPersistence.create(
				serviceComponentId);

			serviceComponent.setBuildNamespace(buildNamespace);
			serviceComponent.setBuildNumber(buildNumber);
			serviceComponent.setBuildDate(buildDate);
		}
		else {
			previousBuildNumber = serviceComponent.getBuildNumber();

			if (previousBuildNumber < buildNumber) {
				previousServiceComponent = serviceComponent;

				long serviceComponentId = counterLocalService.increment();

				serviceComponent = serviceComponentPersistence.create(
					serviceComponentId);

				serviceComponent.setBuildNamespace(buildNamespace);
				serviceComponent.setBuildNumber(buildNumber);
				serviceComponent.setBuildDate(buildDate);
			}
			else if (previousBuildNumber > buildNumber) {
				throw new OldServiceComponentException(
					StringBundler.concat(
						"Build namespace ", buildNamespace,
						" has build number ", previousBuildNumber,
						" which is newer than ", buildNumber));
			}
			else {
				return serviceComponent;
			}
		}

		try {
			Document document = SAXReaderUtil.createDocument(StringPool.UTF8);

			Element dataElement = document.addElement("data");

			Element tablesSQLElement = dataElement.addElement("tables-sql");

			String tablesSQL = StringUtil.read(
				serviceComponentConfiguration.getSQLTablesInputStream());

			tablesSQLElement.addCDATA(tablesSQL);

			Element sequencesSQLElement = dataElement.addElement(
				"sequences-sql");

			String sequencesSQL = StringUtil.read(
				serviceComponentConfiguration.getSQLSequencesInputStream());

			sequencesSQLElement.addCDATA(sequencesSQL);

			Element indexesSQLElement = dataElement.addElement("indexes-sql");

			String indexesSQL = StringUtil.read(
				serviceComponentConfiguration.getSQLIndexesInputStream());

			indexesSQLElement.addCDATA(indexesSQL);

			String dataXML = document.formattedString();

			serviceComponent.setData(dataXML);

			serviceComponent = serviceComponentPersistence.update(
				serviceComponent);

			if (((serviceComponentConfiguration instanceof
					ServletServiceContextComponentConfiguration) &&
				 (previousServiceComponent == null)) ||
				((previousBuildNumber < buildNumber) &&
				 (previousServiceComponent != null))) {

				serviceComponentLocalService.upgradeDB(
					classLoader, buildNamespace, buildNumber,
					previousServiceComponent, tablesSQL, sequencesSQL,
					indexesSQL);
			}

			serviceComponents.put(buildNamespace, serviceComponent);

			removeOldServiceComponents(buildNamespace);

			return serviceComponent;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Override
	public void upgradeDB(
			final ClassLoader classLoader, final String buildNamespace,
			final long buildNumber,
			final ServiceComponent previousServiceComponent,
			final String tablesSQL, final String sequencesSQL,
			final String indexesSQL)
		throws Exception {

		_upgradeDB(
			classLoader, buildNamespace, buildNumber, previousServiceComponent,
			tablesSQL, sequencesSQL, indexesSQL);
	}

	@Override
	public void verifyDB() {
		for (Object service : _upgradeStepServiceTracker.getServices()) {
			UpgradeStepHolder upgradeStepHolder = (UpgradeStepHolder)service;

			String servletContextName = upgradeStepHolder._servletContextName;

			Release release = releaseLocalService.fetchRelease(
				upgradeStepHolder._servletContextName);

			if ((release != null) &&
				!Objects.equals(release.getSchemaVersion(), "0.0.0")) {

				continue;
			}

			try {
				UpgradeStep upgradeStep = upgradeStepHolder._upgradeStep;

				upgradeStep.upgrade(
					new DBProcessContext() {

						@Override
						public DBContext getDBContext() {
							return new DBContext();
						}

						@Override
						public OutputStream getOutputStream() {
							return null;
						}

					});

				releaseLocalService.updateRelease(
					servletContextName, "0.0.1", "0.0.0");

				release = releaseLocalService.fetchRelease(servletContextName);

				int buildNumber = upgradeStepHolder._buildNumber;

				release.setBuildNumber(buildNumber);

				releaseLocalService.updateRelease(release);
			}
			catch (Exception exception) {
				_log.error(exception, exception);
			}
		}
	}

	protected List<String> getModelNames(ClassLoader classLoader)
		throws DocumentException, IOException {

		List<String> modelNames = new ArrayList<>();

		String xml = StringUtil.read(
			classLoader, "META-INF/portlet-model-hints.xml");

		modelNames.addAll(getModelNames(xml));

		try {
			xml = StringUtil.read(
				classLoader, "META-INF/portlet-model-hints-ext.xml");

			modelNames.addAll(getModelNames(xml));
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"No optional file META-INF/portlet-model-hints-ext.xml " +
						"found");
			}
		}

		return modelNames;
	}

	protected List<String> getModelNames(String xml) throws DocumentException {
		List<String> modelNames = new ArrayList<>();

		Document document = UnsecureSAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> modelElements = rootElement.elements("model");

		for (Element modelElement : modelElements) {
			String name = modelElement.attributeValue("name");

			modelNames.add(name);
		}

		return modelNames;
	}

	protected List<String> getModifiedTableNames(
		String previousTablesSQL, String tablesSQL) {

		List<String> modifiedTableNames = new ArrayList<>();

		List<String> previousTablesSQLParts = ListUtil.fromArray(
			StringUtil.split(previousTablesSQL, StringPool.SEMICOLON));
		List<String> tablesSQLParts = ListUtil.fromArray(
			StringUtil.split(tablesSQL, StringPool.SEMICOLON));

		tablesSQLParts.removeAll(previousTablesSQLParts);

		for (String tablesSQLPart : tablesSQLParts) {
			int x = tablesSQLPart.indexOf("create table ");
			int y = tablesSQLPart.indexOf(" (");

			modifiedTableNames.add(tablesSQLPart.substring(x + 13, y));
		}

		return modifiedTableNames;
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
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to instantiate " + upgradeTableListenerClassName);
			}

			return null;
		}
	}

	protected void removeOldServiceComponents(String buildNamespace) {
		int serviceComponentsCount =
			serviceComponentPersistence.countByBuildNamespace(buildNamespace);

		if (serviceComponentsCount < _SERVICE_COMPONENTS_MAX) {
			return;
		}

		List<ServiceComponent> serviceComponents =
			serviceComponentPersistence.findByBuildNamespace(
				buildNamespace, _SERVICE_COMPONENTS_MAX,
				serviceComponentsCount);

		for (ServiceComponent serviceComponent : serviceComponents) {
			serviceComponentPersistence.remove(serviceComponent);
		}
	}

	protected void upgradeModels(
			ClassLoader classLoader, ServiceComponent previousServiceComponent,
			String tablesSQL)
		throws Exception {

		List<String> modifiedTableNames = getModifiedTableNames(
			previousServiceComponent.getTablesSQL(), tablesSQL);

		List<String> modelNames = getModelNames(classLoader);

		for (String modelName : modelNames) {
			int pos = modelName.lastIndexOf(".model.");

			Class<?> modelClass = Class.forName(
				StringBundler.concat(
					modelName.substring(0, pos), ".model.impl.",
					modelName.substring(pos + 7), "ModelImpl"),
				true, classLoader);

			Field dataSourceField = modelClass.getField("DATA_SOURCE");

			String dataSource = (String)dataSourceField.get(null);

			if (!dataSource.equals(_DATA_SOURCE_DEFAULT)) {
				continue;
			}

			Field tableNameField = modelClass.getField("TABLE_NAME");

			String tableName = (String)tableNameField.get(null);

			if (!modifiedTableNames.contains(tableName)) {
				continue;
			}

			Field tableColumnsField = modelClass.getField("TABLE_COLUMNS");

			Object[][] tableColumns = (Object[][])tableColumnsField.get(null);

			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				tableName, tableColumns);

			UpgradeTableListener upgradeTableListener = getUpgradeTableListener(
				classLoader, modelClass);

			Field tableSQLCreateField = modelClass.getField("TABLE_SQL_CREATE");

			String tableSQLCreate = (String)tableSQLCreateField.get(null);

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

	private Map<String, ServiceComponent> _getServiceComponents() {
		if (_serviceComponents != null) {
			return _serviceComponents;
		}

		synchronized (this) {
			if (_serviceComponents != null) {
				return _serviceComponents;
			}

			Map<String, ServiceComponent> serviceComponents =
				new ConcurrentHashMap<>();

			for (ServiceComponent serviceComponent :
					serviceComponentPersistence.findAll()) {

				String buildNamespace = serviceComponent.getBuildNamespace();

				ServiceComponent previousServiceComponent =
					serviceComponents.get(buildNamespace);

				if ((previousServiceComponent == null) ||
					(serviceComponent.getBuildNumber() >
						previousServiceComponent.getBuildNumber())) {

					serviceComponents.put(buildNamespace, serviceComponent);
				}
			}

			_serviceComponents = serviceComponents;
		}

		return _serviceComponents;
	}

	private void _upgradeDB(
			ClassLoader classLoader, String buildNamespace, long buildNumber,
			ServiceComponent previousServiceComponent, String tablesSQL,
			String sequencesSQL, String indexesSQL)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		if (previousServiceComponent == null) {
			if (_log.isInfoEnabled()) {
				_log.info("Running " + buildNamespace + " SQL scripts");
			}

			db.runSQLTemplateString(tablesSQL, false);
			db.runSQLTemplateString(sequencesSQL, false);
			db.runSQLTemplateString(indexesSQL, false);
		}
		else if (PropsValues.SCHEMA_MODULE_BUILD_AUTO_UPGRADE) {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(7);

				sb.append("Auto upgrading ");
				sb.append(buildNamespace);
				sb.append(" database to build number ");
				sb.append(buildNumber);
				sb.append(" is not supported for a production environment. ");
				sb.append("Write an UpgradeStep to ensure data is upgraded ");
				sb.append("correctly.");

				_log.warn(sb.toString());
			}

			if (!tablesSQL.equals(previousServiceComponent.getTablesSQL())) {
				if (_log.isInfoEnabled()) {
					_log.info("Upgrading database with tables.sql");
				}

				db.runSQLTemplateString(tablesSQL, false);

				upgradeModels(classLoader, previousServiceComponent, tablesSQL);
			}

			if (!sequencesSQL.equals(
					previousServiceComponent.getSequencesSQL())) {

				if (_log.isInfoEnabled()) {
					_log.info("Upgrading database with sequences.sql");
				}

				db.runSQLTemplateString(sequencesSQL, false);
			}

			if (!indexesSQL.equals(previousServiceComponent.getIndexesSQL()) ||
				!tablesSQL.equals(previousServiceComponent.getTablesSQL())) {

				if (_log.isInfoEnabled()) {
					_log.info("Upgrading database with indexes.sql");
				}

				db.runSQLTemplateString(indexesSQL, false);
			}
		}
	}

	private static final String _DATA_SOURCE_DEFAULT = "liferayDataSource";

	private static final int _SERVICE_COMPONENTS_MAX = 10;

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceComponentLocalServiceImpl.class);

	private volatile Map<String, ServiceComponent> _serviceComponents;
	private final ServiceTracker<UpgradeStep, UpgradeStepHolder>
		_upgradeStepServiceTracker;

	private static class UpgradeStepHolder {

		private UpgradeStepHolder(
			String servletContextName, int buildNumber,
			UpgradeStep upgradeStep) {

			_servletContextName = servletContextName;
			_buildNumber = buildNumber;
			_upgradeStep = upgradeStep;
		}

		private final int _buildNumber;
		private final String _servletContextName;
		private final UpgradeStep _upgradeStep;

	}

	private static class UpgradeStepServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<UpgradeStep, UpgradeStepHolder> {

		@Override
		public UpgradeStepHolder addingService(
			ServiceReference<UpgradeStep> serviceReference) {

			String servletContextName = (String)serviceReference.getProperty(
				"upgrade.bundle.symbolic.name");
			int buildNumber = GetterUtil.getInteger(
				serviceReference.getProperty("build.number"));

			Registry registry = RegistryUtil.getRegistry();

			UpgradeStep upgradeStep = registry.getService(serviceReference);

			return new UpgradeStepHolder(
				servletContextName, buildNumber, upgradeStep);
		}

		@Override
		public void modifiedService(
			ServiceReference<UpgradeStep> serviceReference,
			UpgradeStepHolder upgradeStepHolder) {

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<UpgradeStep> serviceReference,
			UpgradeStepHolder upgradeStepHolder) {
		}

	}

}