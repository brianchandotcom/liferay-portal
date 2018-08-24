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
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.db.DBContext;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.output.stream.container.OutputStreamContainer;
import com.liferay.portal.output.stream.container.OutputStreamContainerFactory;
import com.liferay.portal.output.stream.container.OutputStreamContainerFactoryTracker;
import com.liferay.portal.upgrade.internal.graph.ReleaseGraphManager;
import com.liferay.portal.upgrade.internal.registry.UpgradeInfo;
import com.liferay.portal.upgrade.internal.release.ReleasePublisher;

import java.io.IOException;
import java.io.OutputStream;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = UpgradeExecutor.class)
public class UpgradeExecutor {

	public void execute(
		String bundleSymbolicName, List<UpgradeInfo> upgradeInfos) {

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

		if (size == 0) {
			return;
		}

		executeUpgradeInfos(bundleSymbolicName, upgradeInfosList.get(0));
	}

	public void executeUpgradeInfos(
		String bundleSymbolicName, List<UpgradeInfo> upgradeInfos) {

		OutputStreamContainerFactory outputStreamContainerFactory =
			_outputStreamContainerFactoryTracker.
				getOutputStreamContainerFactory();

		OutputStreamContainer outputStreamContainer =
			outputStreamContainerFactory.create(
				"upgrade-" + bundleSymbolicName);

		Release release = _releaseLocalService.fetchRelease(bundleSymbolicName);

		if (release != null) {
			_releasePublisher.publishInProgress(release);
		}

		try (OutputStream outputStream =
				outputStreamContainer.getOutputStream()) {

			_outputStreamContainerFactoryTracker.runWithSwappedLog(
				new UpgradeInfosRunnable(
					bundleSymbolicName, upgradeInfos, outputStream),
				outputStreamContainer.getDescription(), outputStream);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		release = _releaseLocalService.fetchRelease(bundleSymbolicName);

		if (release != null) {
			_releasePublisher.publish(release);
		}
	}

	@Reference
	private OutputStreamContainerFactoryTracker
		_outputStreamContainerFactoryTracker;

	@Reference
	private ReleaseLocalService _releaseLocalService;

	@Reference
	private ReleasePublisher _releasePublisher;

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
								return _outputStream;
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

			CacheRegistryUtil.clear();
		}

		private UpgradeInfosRunnable(
			String bundleSymbolicName, List<UpgradeInfo> upgradeInfos,
			OutputStream outputStream) {

			_bundleSymbolicName = bundleSymbolicName;
			_upgradeInfos = upgradeInfos;
			_outputStream = outputStream;
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
		private final OutputStream _outputStream;
		private final List<UpgradeInfo> _upgradeInfos;

	}

}