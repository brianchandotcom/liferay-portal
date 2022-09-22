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

package com.liferay.portal.upgrade.internal.registry;

import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.Version;

/**
 * @author Preston Crary
 */
public class UpgradeStepRegistry implements UpgradeStepRegistrator.Registry {

	public UpgradeStepRegistry(int buildNumber) {
		_buildNumber = buildNumber;
	}

	public List<UpgradeStep> getInitialDeploymentUpgradeSteps() {
		return _initialDeploymentUpgradeSteps;
	}

	public List<UpgradeStep> getReleaseCreationUpgradeSteps() {
		return _releaseCreationUpgradeSteps;
	}

	public List<UpgradeInfo> getUpgradeInfos() {
		if (_initialization) {
			if (_upgradeInfos.isEmpty()) {
				return Arrays.asList(
					new UpgradeInfo(
						"0.0.0", "1.0.0", _buildNumber,
						new DummyUpgradeStep()));
			}

			return ListUtil.concat(
				Arrays.asList(
					new UpgradeInfo(
						"0.0.0", _getFinalSchemaVersion(_upgradeInfos),
						_buildNumber, new DummyUpgradeStep())),
				_upgradeInfos);
		}

		return _upgradeInfos;
	}

	@Override
	public void register(
		String fromSchemaVersionString, String toSchemaVersionString,
		UpgradeStep... upgradeSteps) {

		_createUpgradeInfos(
			fromSchemaVersionString, toSchemaVersionString, _buildNumber,
			upgradeSteps);
	}

	@Override
	public void registerInitialDeploymentUpgradeSteps(
		UpgradeStep... upgradeSteps) {

		Collections.addAll(_initialDeploymentUpgradeSteps, upgradeSteps);
	}

	@Override
	public void registerInitialization() {
		_initialization = true;
	}

	@Override
	public void registerReleaseCreationUpgradeSteps(
		UpgradeStep... upgradeSteps) {

		Collections.addAll(_releaseCreationUpgradeSteps, upgradeSteps);
	}

	private void _createUpgradeInfos(
		String fromSchemaVersionString, String toSchemaVersionString,
		int buildNumber, UpgradeStep... upgradeSteps) {

		if (ArrayUtil.isEmpty(upgradeSteps)) {
			return;
		}

		String upgradeInfoFromSchemaVersionString = fromSchemaVersionString;

		List<UpgradeStep> upgradeStepsList = new ArrayList<>();

		for (UpgradeStep upgradeStep : upgradeSteps) {
			if (upgradeStep instanceof UpgradeProcess) {
				UpgradeProcess upgradeProcess = (UpgradeProcess)upgradeStep;

				for (UpgradeStep innerUpgradeStep :
						upgradeProcess.getUpgradeSteps()) {

					upgradeStepsList.add(innerUpgradeStep);
				}
			}
			else {
				upgradeStepsList.add(upgradeStep);
			}
		}

		for (int i = 0; i < (upgradeStepsList.size() - 1); i++) {
			UpgradeStep upgradeStep = upgradeStepsList.get(i);

			String upgradeInfoToSchemaVersionString =
				toSchemaVersionString + ".step" +
					(i - upgradeStepsList.size() + 1);

			UpgradeInfo upgradeInfo = new UpgradeInfo(
				upgradeInfoFromSchemaVersionString,
				upgradeInfoToSchemaVersionString, buildNumber, upgradeStep);

			_upgradeInfos.add(upgradeInfo);

			upgradeInfoFromSchemaVersionString =
				upgradeInfoToSchemaVersionString;
		}

		UpgradeInfo upgradeInfo = new UpgradeInfo(
			upgradeInfoFromSchemaVersionString, toSchemaVersionString,
			buildNumber, upgradeStepsList.get(upgradeStepsList.size() - 1));

		_upgradeInfos.add(upgradeInfo);
	}

	private String _getFinalSchemaVersion(List<UpgradeInfo> upgradeInfos) {
		Version finalSchemaVersion = null;

		for (UpgradeInfo upgradeInfo : upgradeInfos) {
			String toSchemaVersion = upgradeInfo.getToSchemaVersionString();

			Version schemaVersion = Version.parseVersion(
				toSchemaVersion.substring(0, 5));

			if (finalSchemaVersion == null) {
				finalSchemaVersion = schemaVersion;
			}
			else {
				finalSchemaVersion =
					(finalSchemaVersion.compareTo(schemaVersion) >= 0) ?
						finalSchemaVersion : schemaVersion;
			}
		}

		return finalSchemaVersion.toString();
	}

	private final int _buildNumber;
	private final List<UpgradeStep> _initialDeploymentUpgradeSteps =
		new ArrayList<>();
	private boolean _initialization;
	private final List<UpgradeStep> _releaseCreationUpgradeSteps =
		new ArrayList<>();
	private final List<UpgradeInfo> _upgradeInfos = new ArrayList<>();

}