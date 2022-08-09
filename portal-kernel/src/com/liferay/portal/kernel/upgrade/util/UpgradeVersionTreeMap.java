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

package com.liferay.portal.kernel.upgrade.util;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.version.Version;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * @author Luis Ortiz
 */
public class UpgradeVersionTreeMap extends TreeMap<Version, UpgradeStep> {

	public void put(Version key, UpgradeProcess value) {
		_put(key, value.getUpgradeSteps());
	}

	public void put(Version key, UpgradeProcess... values) {
		List<UpgradeStep> upgradeStepList = new ArrayList<>();

		for (UpgradeProcess upgradeProcess : values) {
			Collections.addAll(
				upgradeStepList, upgradeProcess.getUpgradeSteps());
		}

		_put(key, upgradeStepList.toArray(new UpgradeStep[0]));
	}

	private void _put(Version key, UpgradeStep... values) {
		for (int i = 0; i < (values.length - 1); i++) {
			UpgradeStep upgradeStep = values[i];

			Version stepVersion = new Version(
				key.getMajor(), key.getMinor(), key.getMicro(),
				"step-" + (i + 1));

			put(stepVersion, upgradeStep);
		}

		Version finalVersion = new Version(
			key.getMajor(), key.getMinor(), key.getMicro());

		put(finalVersion, values[values.length - 1]);
	}

}