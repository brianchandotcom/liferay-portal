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

package com.liferay.portal.upgrade.internal.release;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapListener;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.upgrade.ReleaseManager;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.output.stream.container.constants.OutputStreamContainerConstants;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.upgrade.internal.executor.UpgradeExecutor;
import com.liferay.portal.upgrade.internal.graph.ReleaseGraphManager;
import com.liferay.portal.upgrade.internal.registry.UpgradeInfo;
import com.liferay.portal.upgrade.internal.registry.UpgradeStepRegistratorThreadLocal;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Alberto Chaparro
 * @author Samuel Ziemer
 */
@Component(
	immediate = true, service = {ReleaseManager.class, ReleaseManagerImpl.class}
)
public class ReleaseManagerImpl implements ReleaseManager {

	public Set<String> getBundleSymbolicNames() {
		return _serviceTrackerMap.keySet();
	}

	public String getSchemaVersionString(String bundleSymbolicName) {
		String currentSchemaVersion = "0.0.0";

		Release release = _releaseLocalService.fetchRelease(bundleSymbolicName);

		if ((release != null) &&
			Validator.isNotNull(release.getSchemaVersion())) {

			currentSchemaVersion = release.getSchemaVersion();
		}

		return currentSchemaVersion;
	}

	@Override
	public String getStatusMessage(boolean onlyRequiredUpgrades) {
		String message =
			"%s upgrades in %s are pending. Run the upgrade process or type " +
				"upgrade:checkAll in the Gogo shell to get more information.";

		if (onlyRequiredUpgrades) {
			if (_isPendingRequiredModuleUpgrades()) {
				return String.format(message, "Required", "modules");
			}

			return StringPool.BLANK;
		}

		String where = StringPool.BLANK;

		try (Connection connection = DataAccess.getConnection()) {
			if (!PortalUpgradeProcess.isInLatestSchemaVersion(connection)) {
				where = "portal";
			}
		}
		catch (SQLException sqlException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get pending upgrade information for the portal");
			}
		}

		if (_isPendingModuleUpgrades()) {
			if (Validator.isNotNull(where)) {
				where = "and " + where;
			}
			else {
				where = "modules";
			}
		}

		if (Validator.isNotNull(where)) {
			return String.format(message, "Optional", where);
		}

		return StringPool.BLANK;
	}

	public Set<String> getUpgradableBundleSymbolicNames() {
		Set<String> upgradableBundleSymbolicNames = new HashSet<>();

		for (String bundleSymbolicName : getBundleSymbolicNames()) {
			if (_isUpgradable(bundleSymbolicName)) {
				upgradableBundleSymbolicNames.add(bundleSymbolicName);
			}
		}

		return upgradableBundleSymbolicNames;
	}

	public List<UpgradeInfo> getUpgradeInfos(String bundleSymbolicName) {
		return _serviceTrackerMap.getService(bundleSymbolicName);
	}

	@Activate
	protected void activate(final BundleContext bundleContext) {
		DB db = DBManagerUtil.getDB();

		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, UpgradeStep.class,
			StringBundler.concat(
				"(&(upgrade.bundle.symbolic.name=*)(|(upgrade.db.type=any)",
				"(upgrade.db.type=", db.getDBType(), ")))"),
			new PropertyServiceReferenceMapper<String, UpgradeStep>(
				"upgrade.bundle.symbolic.name"),
			new ReleaseManagerImpl.UpgradeServiceTrackerCustomizer(
				bundleContext),
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<UpgradeStep>(
					"upgrade.from.schema.version")),
			new ReleaseManagerImpl.UpgradeInfoServiceTrackerMapListener());

		Set<String> upgradedBundleSymbolicNames = new HashSet<>();

		Set<String> bundleSymbolicNames = _serviceTrackerMap.keySet();

		while (upgradedBundleSymbolicNames.addAll(bundleSymbolicNames)) {
			for (String bundleSymbolicName : bundleSymbolicNames) {
				if (!PropsValues.UPGRADE_DATABASE_AUTO_RUN &&
					(_releaseLocalService.fetchRelease(bundleSymbolicName) !=
						null)) {

					continue;
				}

				List<UpgradeInfo> upgradeSteps = _serviceTrackerMap.getService(
					bundleSymbolicName);

				_upgradeExecutor.execute(
					bundleSymbolicName, upgradeSteps,
					OutputStreamContainerConstants.FACTORY_NAME_DUMMY);
			}

			bundleSymbolicNames = _serviceTrackerMap.keySet();
		}

		_activated = true;
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private boolean _isPendingModuleUpgrades() {
		for (String bundleSymbolicName : getBundleSymbolicNames()) {
			if (_isUpgradable(bundleSymbolicName)) {
				return true;
			}
		}

		return false;
	}

	private boolean _isPendingRequiredModuleUpgrades() {
		Set<String> upgradableBundleSymbolicNames =
			getUpgradableBundleSymbolicNames();

		for (String bundleSymbolicName : upgradableBundleSymbolicNames) {
			ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
				getUpgradeInfos(bundleSymbolicName));

			List<List<UpgradeInfo>> upgradeInfosList =
				releaseGraphManager.getUpgradeInfosList(
					getSchemaVersionString(bundleSymbolicName));

			List<UpgradeInfo> upgradeInfos = upgradeInfosList.get(0);

			for (UpgradeInfo upgradeInfo : upgradeInfos) {
				if (UpgradeProcessUtil.isRequiredSchemaVersion(
						Version.parseVersion(
							upgradeInfo.getFromSchemaVersionString()),
						Version.parseVersion(
							upgradeInfo.getToSchemaVersionString()))) {

					return true;
				}
			}
		}

		return false;
	}

	private boolean _isUpgradable(String bundleSymbolicName) {
		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			getUpgradeInfos(bundleSymbolicName));

		List<List<UpgradeInfo>> upgradeInfosList =
			releaseGraphManager.getUpgradeInfosList(
				getSchemaVersionString(bundleSymbolicName));

		if (upgradeInfosList.size() == 1) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReleaseManagerImpl.class);

	private boolean _activated;

	@Reference
	private ReleaseLocalService _releaseLocalService;

	private ServiceTrackerMap<String, List<UpgradeInfo>> _serviceTrackerMap;

	@Reference
	private UpgradeExecutor _upgradeExecutor;

	private static class UpgradeServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<UpgradeStep, UpgradeInfo> {

		public UpgradeServiceTrackerCustomizer(BundleContext bundleContext) {
			_bundleContext = bundleContext;
		}

		@Override
		public UpgradeInfo addingService(
			ServiceReference<UpgradeStep> serviceReference) {

			String fromSchemaVersionString =
				(String)serviceReference.getProperty(
					"upgrade.from.schema.version");
			String toSchemaVersionString = (String)serviceReference.getProperty(
				"upgrade.to.schema.version");
			Object buildNumberObject = serviceReference.getProperty(
				"build.number");

			UpgradeStep upgradeStep = _bundleContext.getService(
				serviceReference);

			if (upgradeStep == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Skipping service " + serviceReference +
							" because it does not implement UpgradeStep");
				}

				return null;
			}

			int buildNumber = 0;

			if (buildNumberObject == null) {
				try {
					Class<? extends UpgradeStep> clazz = upgradeStep.getClass();

					Configuration configuration =
						ConfigurationFactoryUtil.getConfiguration(
							clazz.getClassLoader(), "service");

					Properties properties = configuration.getProperties();

					buildNumber = GetterUtil.getInteger(
						properties.getProperty("build.number"));
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to read service.properties for " +
								serviceReference);
					}
				}
			}
			else {
				buildNumber = GetterUtil.getInteger(buildNumberObject);
			}

			return new UpgradeInfo(
				fromSchemaVersionString, toSchemaVersionString, buildNumber,
				upgradeStep);
		}

		@Override
		public void modifiedService(
			ServiceReference<UpgradeStep> serviceReference,
			UpgradeInfo upgradeInfo) {
		}

		@Override
		public void removedService(
			ServiceReference<UpgradeStep> serviceReference,
			UpgradeInfo upgradeInfo) {

			_bundleContext.ungetService(serviceReference);
		}

		private final BundleContext _bundleContext;

	}

	private class UpgradeInfoServiceTrackerMapListener
		implements ServiceTrackerMapListener
			<String, UpgradeInfo, List<UpgradeInfo>> {

		@Override
		public void keyEmitted(
			ServiceTrackerMap<String, List<UpgradeInfo>> serviceTrackerMap,
			final String key, UpgradeInfo upgradeInfo,
			List<UpgradeInfo> upgradeInfos) {

			if (_activated && UpgradeStepRegistratorThreadLocal.isEnabled() &&
				(PropsValues.UPGRADE_DATABASE_AUTO_RUN ||
				 (_releaseLocalService.fetchRelease(key) == null))) {

				_upgradeExecutor.execute(key, upgradeInfos, null);
			}
		}

		@Override
		public void keyRemoved(
			ServiceTrackerMap<String, List<UpgradeInfo>> serviceTrackerMap,
			String key, UpgradeInfo upgradeInfo,
			List<UpgradeInfo> upgradeInfos) {
		}

	}

}