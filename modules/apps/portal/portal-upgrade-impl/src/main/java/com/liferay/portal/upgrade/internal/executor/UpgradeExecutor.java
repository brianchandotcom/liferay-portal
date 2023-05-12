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

package com.liferay.portal.upgrade.internal.executor;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.db.index.IndexUpdaterUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.module.util.BundleUtil;
import com.liferay.portal.upgrade.internal.graph.ReleaseGraphManager;
import com.liferay.portal.upgrade.internal.registry.UpgradeInfo;
import com.liferay.portal.upgrade.internal.registry.UpgradeStepRegistratorTracker;
import com.liferay.portal.upgrade.internal.release.ReleasePublisher;
import com.liferay.portal.upgrade.log.UpgradeLogContext;

import java.util.Dictionary;
import java.util.List;
import java.util.Objects;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = UpgradeExecutor.class)
public class UpgradeExecutor {

	public void execute(
		String bundleSymbolicName, List<UpgradeInfo> upgradeInfos) {

		Bundle bundle = null;

		for (UpgradeInfo upgradeInfo : upgradeInfos) {
			UpgradeStep upgradeStep = upgradeInfo.getUpgradeStep();

			Bundle currentBundle = FrameworkUtil.getBundle(
				upgradeStep.getClass());

			if (currentBundle == null) {
				continue;
			}

			if (Objects.equals(
					currentBundle.getSymbolicName(), bundleSymbolicName)) {

				bundle = currentBundle;

				break;
			}
		}

		Version requiredVersion = null;

		if (bundle != null) {
			Dictionary<String, String> headers = bundle.getHeaders(
				StringPool.BLANK);

			requiredVersion = Version.parseVersion(
				headers.get("Liferay-Require-SchemaVersion"));
		}

		ReleaseGraphManager releaseGraphManager = new ReleaseGraphManager(
			upgradeInfos);

		String schemaVersionString = "0.0.0";

		Release release = _upgradeStepRegistratorTracker.fetchUpgradedRelease(
			bundleSymbolicName);

		if ((release != null) &&
			Validator.isNotNull(release.getSchemaVersion())) {

			schemaVersionString = release.getSchemaVersion();
		}

		List<List<UpgradeInfo>> upgradeInfosList =
			releaseGraphManager.getUpgradeInfosList(schemaVersionString);

		int size = upgradeInfosList.size();

		if (size > 1) {
			throw new IllegalStateException(
				StringBundler.concat(
					"There are ", size, " possible end nodes for ",
					schemaVersionString));
		}

		if (size != 0) {
			release = executeUpgradeInfos(
				bundleSymbolicName, upgradeInfosList.get(0));
		}

		if (release != null) {
			String schemaVersion = release.getSchemaVersion();

			if (Validator.isNull(schemaVersion) || (requiredVersion == null)) {
				return;
			}

			if (requiredVersion.compareTo(Version.parseVersion(schemaVersion)) >
					0) {

				throw new IllegalStateException(
					StringBundler.concat(
						"Unable to upgrade ", bundleSymbolicName, " to ",
						requiredVersion, " from ", schemaVersion));
			}
		}
	}

	public Release executeUpgradeInfos(
		String bundleSymbolicName, List<UpgradeInfo> upgradeInfos) {

		try {
			UpgradeLogContext.setContext(bundleSymbolicName);

			_executeUpgradeInfos(bundleSymbolicName, upgradeInfos);

			Release release = _releaseLocalService.fetchRelease(
				bundleSymbolicName);

			if (release != null) {
				_releasePublisher.publish(
					release, _isInitialRelease(upgradeInfos));
			}

			return release;
		}
		catch (Exception exception) {
			Release release = _releaseLocalService.fetchRelease(
				bundleSymbolicName);

			if (release != null) {
				_releasePublisher.unpublish(release);
			}

			return ReflectionUtil.throwException(exception);
		}
		finally {
			UpgradeLogContext.clearContext();
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_upgradeStepRegistratorTracker = new UpgradeStepRegistratorTracker(
			bundleContext, _releaseLocalService, this);

		_upgradeStepRegistratorTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_upgradeStepRegistratorTracker.close();
	}

	private void _executeUpgradeInfos(
		String bundleSymbolicName, List<UpgradeInfo> upgradeInfos) {

		int buildNumber = 0;
		int state = ReleaseConstants.STATE_GOOD;

		try {
			_updateReleaseState(bundleSymbolicName, _STATE_IN_PROGRESS);

			for (UpgradeInfo upgradeInfo : upgradeInfos) {
				UpgradeStep upgradeStep = upgradeInfo.getUpgradeStep();

				upgradeStep.upgrade();

				_releaseLocalService.updateRelease(
					bundleSymbolicName, upgradeInfo.getToSchemaVersionString(),
					upgradeInfo.getFromSchemaVersionString());

				buildNumber = upgradeInfo.getBuildNumber();
			}
		}
		catch (Exception exception) {
			state = ReleaseConstants.STATE_UPGRADE_FAILURE;

			ReflectionUtil.throwException(exception);
		}
		finally {
			Release release = _releaseLocalService.fetchRelease(
				bundleSymbolicName);

			if (release != null) {
				if (buildNumber > 0) {
					release.setBuildNumber(buildNumber);
				}

				release.setVerified(_isInitialRelease(upgradeInfos));

				release.setState(state);

				_releaseLocalService.updateRelease(release);
			}
		}

		Bundle bundle = BundleUtil.getBundle(
			_bundleContext, bundleSymbolicName);

		if (_requiresUpdateIndexes(bundle, upgradeInfos)) {
			try {
				IndexUpdaterUtil.updateIndexes(bundle);
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}

		CacheRegistryUtil.clear();
	}

	private boolean _isInitialRelease(List<UpgradeInfo> upgradeInfos) {
		UpgradeInfo upgradeInfo = upgradeInfos.get(0);

		String fromSchemaVersion = upgradeInfo.getFromSchemaVersionString();

		if (fromSchemaVersion.equals("0.0.0")) {
			return true;
		}

		return false;
	}

	private boolean _requiresUpdateIndexes(
		Bundle bundle, List<UpgradeInfo> upgradeInfos) {

		if (!BundleUtil.isLiferayServiceBundle(bundle)) {
			return false;
		}

		if (upgradeInfos.size() != 1) {
			return true;
		}

		return !_isInitialRelease(upgradeInfos);
	}

	private void _updateReleaseState(String bundleSymbolicName, int state) {
		Release release = _releaseLocalService.fetchRelease(bundleSymbolicName);

		if (release != null) {
			release.setState(state);

			_releaseLocalService.updateRelease(release);
		}
	}

	private static final int _STATE_IN_PROGRESS = -1;

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeExecutor.class);

	private BundleContext _bundleContext;

	@Reference
	private ReleaseLocalService _releaseLocalService;

	@Reference
	private ReleasePublisher _releasePublisher;

	private UpgradeStepRegistratorTracker _upgradeStepRegistratorTracker;

}