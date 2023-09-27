/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public List<UpgradeStep> getReleaseCreationUpgradeSteps() {
		return _releaseCreationUpgradeSteps;
	}

	public List<UpgradeInfo> getUpgradeInfos(boolean portalUpgraded) {
		if (_initialization && portalUpgraded) {
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

		if (upgradeStepsList.size() == 1) {
			_upgradeInfos.add(
				new UpgradeInfo(
					fromSchemaVersionString, toSchemaVersionString, buildNumber,
					upgradeStepsList.get(0)));
		}
		else {
			_upgradeInfos.add(
				new UpgradeInfo(
					fromSchemaVersionString, toSchemaVersionString, buildNumber,
					() -> {
						for (UpgradeStep upgradeStep : upgradeStepsList) {
							upgradeStep.upgrade();
						}
					}));
		}
	}

	private String _getFinalSchemaVersion(List<UpgradeInfo> upgradeInfos) {
		Version finalSchemaVersion = null;

		for (UpgradeInfo upgradeInfo : upgradeInfos) {
			Version schemaVersion = Version.parseVersion(
				upgradeInfo.getToSchemaVersionString());

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
	private boolean _initialization;
	private final List<UpgradeStep> _releaseCreationUpgradeSteps =
		new ArrayList<>();
	private final List<UpgradeInfo> _upgradeInfos = new ArrayList<>();

}