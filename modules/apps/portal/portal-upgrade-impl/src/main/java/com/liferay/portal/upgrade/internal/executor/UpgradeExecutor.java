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
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.db.DBContext;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.upgrade.internal.graph.ReleaseGraphManager;
import com.liferay.portal.upgrade.internal.index.updater.IndexUpdaterUtil;
import com.liferay.portal.upgrade.internal.registry.UpgradeInfo;
import com.liferay.portal.upgrade.internal.release.ReleasePublisher;

import java.io.OutputStream;

import java.util.Dictionary;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = UpgradeExecutor.class)
public class UpgradeExecutor {

	public void execute(
		String bundleSymbolicName, List<UpgradeInfo> upgradeInfos,
		String outputStreamContainerFactoryName) {

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

		Release release = _releaseLocalService.fetchRelease(bundleSymbolicName);

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
				bundleSymbolicName, upgradeInfosList.get(0),
				outputStreamContainerFactoryName);
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
		String bundleSymbolicName, List<UpgradeInfo> upgradeInfos,
		String outputStreamContainerFactoryName) {

		Release release = _releaseLocalService.fetchRelease(bundleSymbolicName);

		if (release != null) {
			_releasePublisher.publishInProgress(release);
		}

		UpgradeInfosRunnable upgradeInfosRunnable = new UpgradeInfosRunnable(
			bundleSymbolicName, upgradeInfos,
			_swappedLogExecutor::getOutputStream);

		_swappedLogExecutor.execute(
			bundleSymbolicName, upgradeInfosRunnable,
			outputStreamContainerFactoryName);

		release = _releaseLocalService.fetchRelease(bundleSymbolicName);

		if (release != null) {
			_releasePublisher.publish(release);
		}

		return release;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeExecutor.class);

	private BundleContext _bundleContext;

	@Reference
	private ReleaseLocalService _releaseLocalService;

	@Reference
	private ReleasePublisher _releasePublisher;

	@Reference
	private SwappedLogExecutor _swappedLogExecutor;

	private class UpgradeInfosRunnable implements Runnable {

		@Override
		public void run() {
			int buildNumber = 0;
			int state = ReleaseConstants.STATE_GOOD;

			try {
				_updateReleaseState(_STATE_IN_PROGRESS);

				for (UpgradeInfo upgradeInfo : _upgradeInfos) {
					UpgradeStep upgradeStep = upgradeInfo.getUpgradeStep();

					upgradeStep.upgrade(
						new DBProcessContext() {

							@Override
							public DBContext getDBContext() {
								return new DBContext();
							}

							@Override
							public OutputStream getOutputStream() {
								return _outputStreamSupplier.get();
							}

						});

					_releaseLocalService.updateRelease(
						_bundleSymbolicName,
						upgradeInfo.getToSchemaVersionString(),
						upgradeInfo.getFromSchemaVersionString());

					buildNumber = upgradeInfo.getBuildNumber();
				}
			}
			catch (Exception e) {
				state = ReleaseConstants.STATE_UPGRADE_FAILURE;

				ReflectionUtil.throwException(e);
			}
			finally {
				Release release = _releaseLocalService.fetchRelease(
					_bundleSymbolicName);

				if (release != null) {
					if (buildNumber > 0) {
						release.setBuildNumber(buildNumber);
					}

					release.setState(state);

					_releaseLocalService.updateRelease(release);
				}
			}

			Bundle bundle = IndexUpdaterUtil.getBundle(
				_bundleContext, _bundleSymbolicName);

			if (_requiresUpdateIndexes(bundle)) {
				try {
					IndexUpdaterUtil.updateIndexes(bundle);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			CacheRegistryUtil.clear();
		}

		private UpgradeInfosRunnable(
			String bundleSymbolicName, List<UpgradeInfo> upgradeInfos,
			Supplier<OutputStream> outputStreamSupplier) {

			_bundleSymbolicName = bundleSymbolicName;
			_upgradeInfos = upgradeInfos;
			_outputStreamSupplier = outputStreamSupplier;
		}

		private boolean _requiresUpdateIndexes(Bundle bundle) {
			if (!IndexUpdaterUtil.isLiferayServiceBundle(bundle)) {
				return false;
			}

			if (_upgradeInfos.size() != 1) {
				return true;
			}

			UpgradeInfo upgradeInfo = _upgradeInfos.get(0);

			UpgradeStep upgradeStep = upgradeInfo.getUpgradeStep();

			if (upgradeStep instanceof DummyUpgradeStep) {
				return false;
			}

			String fromSchemaVersion = upgradeInfo.getFromSchemaVersionString();

			String upgradeStepName = upgradeStep.toString();

			if (fromSchemaVersion.equals("0.0.0") &&
				upgradeStepName.equals("Initial Database Creation")) {

				return false;
			}

			return true;
		}

		private void _updateReleaseState(int state) {
			Release release = _releaseLocalService.fetchRelease(
				_bundleSymbolicName);

			if (release != null) {
				release.setState(state);

				_releaseLocalService.updateRelease(release);
			}
		}

		private static final int _STATE_IN_PROGRESS = -1;

		private final String _bundleSymbolicName;
		private final Supplier<OutputStream> _outputStreamSupplier;
		private final List<UpgradeInfo> _upgradeInfos;

	}

}