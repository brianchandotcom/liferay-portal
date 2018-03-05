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

package com.liferay.portal.spring.extender.internal.context;

import com.liferay.osgi.felix.util.AbstractExtender;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBContext;
import com.liferay.portal.kernel.dao.db.DBManager;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.configuration.configurator.ServiceConfigurator;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.spring.extender.internal.classloader.BundleResolverClassLoader;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.sql.DataSource;

import org.apache.felix.dm.DependencyManager;
import org.apache.felix.dm.ServiceDependency;
import org.apache.felix.utils.extender.Extension;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Pastor
 */
@Component(immediate = true)
public class ModuleApplicationContextExtender extends AbstractExtender {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		start(bundleContext);
	}

	@Deactivate
	protected void deactivate(BundleContext bundleContext) throws Exception {
		stop(bundleContext);
	}

	@Override
	protected void debug(Bundle bundle, String s) {
		if (_log.isDebugEnabled()) {
			_log.debug(s);
		}
	}

	@Override
	protected Extension doCreateExtension(Bundle bundle) throws Exception {
		Dictionary<String, String> headers = bundle.getHeaders();

		if (headers.get("Liferay-Spring-Context") == null) {
			return null;
		}

		return new ModuleApplicationContextExtension(bundle);
	}

	@Override
	protected void error(String s, Throwable throwable) {
		_log.error(s, throwable);
	}

	@Reference(
		target = "(&(bean.id=liferayDataSource)(original.bean=true))",
		unbind = "-"
	)
	protected void setDataSource(DataSource dataSource) {
	}

	@Reference(target = "(original.bean=true)", unbind = "-")
	protected void setInfrastructureUtil(
		InfrastructureUtil infrastructureUtil) {
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(target = "(original.bean=true)", unbind = "-")
	protected void setSaxReaderUtil(SAXReaderUtil saxReaderUtil) {
	}

	@Reference(target = "(original.bean=true)", unbind = "-")
	protected void setServiceConfigurator(
		ServiceConfigurator serviceConfigurator) {

		_serviceConfigurator = serviceConfigurator;
	}

	@Override
	protected void warn(Bundle bundle, String s, Throwable throwable) {
		if (_log.isWarnEnabled()) {
			_log.warn(s, throwable);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ModuleApplicationContextExtender.class);

	private ServiceConfigurator _serviceConfigurator;

	private class ModuleApplicationContextExtension implements Extension {

		public ModuleApplicationContextExtension(Bundle bundle) {
			_bundle = bundle;

			_dependencyManager = new DependencyManager(
				bundle.getBundleContext());
		}

		@Override
		public void destroy() throws Exception {
			if (_upgradeStepServiceRegistration != null) {
				_upgradeStepServiceRegistration.unregister();
			}

			if (_component != null) {
				_dependencyManager.remove(_component);
			}
		}

		public String getSQLTemplateString(String templateName)
			throws UpgradeException {

			URL resource = _bundle.getResource("/META-INF/sql/" + templateName);

			if (resource == null) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to locate SQL template " + templateName);
				}

				return null;
			}

			try (InputStream inputStream = resource.openStream()) {
				return StringUtil.read(inputStream);
			}
			catch (IOException ioe) {
				throw new UpgradeException(
					"Unable to read SQL template " + templateName, ioe);
			}
		}

		@Override
		public void start() throws Exception {
			_component = _dependencyManager.createComponent();

			BundleContext bundleContext =
				ModuleApplicationContextExtender.this.getBundleContext();

			Bundle bundle = bundleContext.getBundle();

			_component.setImplementation(
				new ModuleApplicationContextRegistrator(
					_bundle, bundle, _serviceConfigurator));

			ClassLoader classLoader = new BundleResolverClassLoader(
				_bundle, bundle);

			List<ContextDependency> contextDependencies =
				_processServiceReferences(_bundle);

			for (ContextDependency contextDependency : contextDependencies) {
				ServiceDependency serviceDependency =
					_dependencyManager.createServiceDependency();

				serviceDependency.setRequired(true);

				Class<?> serviceClass = Class.forName(
					contextDependency.getServiceClassName(), false,
					classLoader);

				serviceDependency.setService(
					serviceClass, contextDependency.getFilterString());

				_component.add(serviceDependency);
			}

			Dictionary<String, String> headers = _bundle.getHeaders();

			String requireSchemaVersion = headers.get(
				"Liferay-Require-SchemaVersion");

			if (Validator.isNull(requireSchemaVersion)) {
				_generateReleaseInfo();
			}

			_dependencyManager.add(_component);

			_upgradeStepServiceRegistration = _processInitialUpgrade(
				classLoader);
		}

		private void _generateReleaseInfo() {
			ServiceDependency serviceDependency =
				_dependencyManager.createServiceDependency();

			serviceDependency.setRequired(true);

			serviceDependency.setService(
				Release.class,
				StringBundler.concat(
					"(&(release.bundle.symbolic.name=",
					_bundle.getSymbolicName(), ")(release.schema.version=",
					String.valueOf(_bundle.getVersion()), "))"));

			_component.add(serviceDependency);
		}

		private ServiceRegistration<UpgradeStep> _processInitialUpgrade(
			ClassLoader classLoader) {

			Dictionary<String, String> headers = _bundle.getHeaders();

			String upgradeToSchemaVersion = GetterUtil.getString(
				headers.get("Liferay-Require-SchemaVersion"),
				headers.get("Bundle-Version"));

			Dictionary<String, Object> properties = new Hashtable<>();

			try {
				Configuration configuration =
					ConfigurationFactoryUtil.getConfiguration(
						classLoader, "service");

				String buildNumber = configuration.get("build.number");

				if (buildNumber != null) {
					properties.put("build.number", buildNumber);
				}
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to read service.properties for bundle " +
							_bundle.getSymbolicName());
				}
			}

			properties.put("upgrade.initial.database.creation", "true");

			properties.put(
				"upgrade.bundle.symbolic.name", _bundle.getSymbolicName());
			properties.put("upgrade.db.type", "any");
			properties.put("upgrade.from.schema.version", "0.0.0");
			properties.put("upgrade.to.schema.version", upgradeToSchemaVersion);

			BundleContext bundleContext =
				ModuleApplicationContextExtender.this.getBundleContext();

			return bundleContext.registerService(
				UpgradeStep.class,
				new UpgradeStep() {

					@Override
					public String toString() {
						return "Initial Database Creation";
					}

					@Override
					public void upgrade(DBProcessContext dbProcessContext)
						throws UpgradeException {

						DBContext dbContext = dbProcessContext.getDBContext();

						DBManager dbManager = dbContext.getDBManager();

						DB db = dbManager.getDB();

						String tablesSQL = getSQLTemplateString("tables.sql");
						String sequencesSQL = getSQLTemplateString(
							"sequences.sql");
						String indexesSQL = getSQLTemplateString("indexes.sql");

						if (tablesSQL != null) {
							try {
								db.runSQLTemplateString(tablesSQL, true, true);
							}
							catch (Exception e) {
								throw new UpgradeException(
									StringBundler.concat(
										"Bundle ", String.valueOf(_bundle),
										" has invalid content in ",
										"tables.sql:\n", tablesSQL),
									e);
							}
						}

						if (sequencesSQL != null) {
							try {
								db.runSQLTemplateString(
									sequencesSQL, true, true);
							}
							catch (Exception e) {
								throw new UpgradeException(
									StringBundler.concat(
										"Bundle ", String.valueOf(_bundle),
										" has invalid content in ",
										"sequences.sql:\n", sequencesSQL),
									e);
							}
						}

						if (indexesSQL != null) {
							try {
								db.runSQLTemplateString(indexesSQL, true, true);
							}
							catch (Exception e) {
								throw new UpgradeException(
									StringBundler.concat(
										"Bundle ", String.valueOf(_bundle),
										" has invalid content in ",
										"indexes.sql:\n", indexesSQL),
									e);
							}
						}
					}

				},
				properties);
		}

		private List<ContextDependency> _processServiceReferences(Bundle bundle)
			throws IOException {

			List<ContextDependency> contextDependencies = new ArrayList<>();

			URL url = bundle.getEntry("OSGI-INF/context/context.dependencies");

			if (url == null) {
				return contextDependencies;
			}

			List<String> lines = new ArrayList<>();

			StringUtil.readLines(url.openStream(), lines);

			for (String line : lines) {
				if (Validator.isNull(line)) {
					continue;
				}

				line = line.trim();

				String[] array = line.split(" ");

				String filterString = "";

				if (array.length > 1) {
					filterString = array[1];
				}

				contextDependencies.add(
					new ContextDependency(array[0], filterString));
			}

			return contextDependencies;
		}

		private final Bundle _bundle;
		private org.apache.felix.dm.Component _component;
		private final DependencyManager _dependencyManager;
		private ServiceRegistration<UpgradeStep>
			_upgradeStepServiceRegistration;

		private class ContextDependency {

			public ContextDependency(
				String serviceClassName, String filterString) {

				this.serviceClassName = serviceClassName;

				if (!filterString.equals("")) {
					this.filterString = filterString;
				}
			}

			public String getFilterString() {
				return filterString;
			}

			public String getServiceClassName() {
				return serviceClassName;
			}

			protected String filterString;
			protected final String serviceClassName;

		}

	}

}