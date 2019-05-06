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

package com.liferay.portal.spring.extender.internal.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBContext;
import com.liferay.portal.kernel.dao.db.DBManager;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.internal.configuration.ConfigurationUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class InitialUpgradeExtender
	implements BundleTrackerCustomizer
		<InitialUpgradeExtender.InitialUpgradeExtension> {

	@Override
	public InitialUpgradeExtension addingBundle(
		Bundle bundle, BundleEvent bundleEvent) {

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		if (headers.get("Liferay-Service") == null) {
			return null;
		}

		InitialUpgradeExtension initialUpgradeExtension =
			new InitialUpgradeExtension(_bundleContext, bundle);

		initialUpgradeExtension.start();

		return initialUpgradeExtension;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		InitialUpgradeExtension initialUpgradeExtension) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent,
		InitialUpgradeExtension initialUpgradeExtension) {

		initialUpgradeExtension.destroy();
	}

	public static class InitialUpgradeExtension {

		public void destroy() {
			_serviceRegistration.unregister();
		}

		public void start() {
			_serviceRegistration = _processInitialUpgrade(
				_bundleContext, _bundle);
		}

		private static ServiceRegistration<UpgradeStep> _processInitialUpgrade(
			BundleContext bundleContext, Bundle bundle) {

			Dictionary<String, String> headers = bundle.getHeaders(
				StringPool.BLANK);

			String upgradeToSchemaVersion = GetterUtil.getString(
				headers.get("Liferay-Require-SchemaVersion"),
				headers.get("Bundle-Version"));

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			ClassLoader classLoader = bundleWiring.getClassLoader();

			Configuration configuration = ConfigurationUtil.getConfiguration(
				classLoader, "service");

			if (configuration != null) {
				String buildNumber = configuration.get("build.number");

				if (buildNumber != null) {
					properties.put("build.number", buildNumber);
				}
			}

			properties.put("upgrade.initial.database.creation", "true");

			properties.put(
				"upgrade.bundle.symbolic.name", bundle.getSymbolicName());
			properties.put("upgrade.db.type", "any");
			properties.put("upgrade.from.schema.version", "0.0.0");
			properties.put("upgrade.to.schema.version", upgradeToSchemaVersion);

			return bundleContext.registerService(
				UpgradeStep.class, new InitialUpgradeStep(bundle), properties);
		}

		private InitialUpgradeExtension(
			BundleContext bundleContext, Bundle bundle) {

			_bundleContext = bundleContext;
			_bundle = bundle;
		}

		private final Bundle _bundle;
		private final BundleContext _bundleContext;
		private ServiceRegistration<UpgradeStep> _serviceRegistration;

	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE | Bundle.STARTING, this);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InitialUpgradeExtender.class);

	private BundleContext _bundleContext;
	private BundleTracker<?> _bundleTracker;

	@Reference(target = "(original.bean=true)")
	private InfrastructureUtil _infrastructureUtil;

	private static class InitialUpgradeStep implements UpgradeStep {

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

			String tablesSQL = _getSQLTemplateString("tables.sql");
			String sequencesSQL = _getSQLTemplateString("sequences.sql");
			String indexesSQL = _getSQLTemplateString("indexes.sql");

			if (tablesSQL != null) {
				try {
					db.runSQLTemplateString(tablesSQL, true, true);
				}
				catch (Exception e) {
					throw new UpgradeException(
						StringBundler.concat(
							"Bundle ", _bundle,
							" has invalid content in tables.sql:\n", tablesSQL),
						e);
				}
			}

			if (sequencesSQL != null) {
				try {
					db.runSQLTemplateString(sequencesSQL, true, true);
				}
				catch (Exception e) {
					throw new UpgradeException(
						StringBundler.concat(
							"Bundle ", _bundle,
							" has invalid content in sequences.sql:\n",
							sequencesSQL),
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
							"Bundle ", _bundle,
							" has invalid content in indexes.sql:\n",
							indexesSQL),
						e);
				}
			}
		}

		private InitialUpgradeStep(Bundle bundle) {
			_bundle = bundle;
		}

		private String _getSQLTemplateString(String templateName)
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

		private final Bundle _bundle;

	}

}