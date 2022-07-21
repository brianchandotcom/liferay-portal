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

package com.liferay.portal.kernel.version;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.TreeMap;

/**
 * @author Luis Ortiz
 */
public class VersionTreeMap extends TreeMap<Version, UpgradeProcess> {

	public void put(Version key, UpgradeProcess... values) {
		for (int i = 0; i < (values.length - 1); i++) {
			UpgradeProcess upgradeProcess = values[i];

			Version stepVersion = new Version(
				key.getMajor(), key.getMinor(), key.getMicro(),
				"step-" + (i + 1));

			put(stepVersion, upgradeProcess);
		}

		UpgradeProcess upgradeProcess = values[values.length - 1];

		Version stepVersion = new Version(
			key.getMajor(), key.getMinor(), key.getMicro());

		put(stepVersion, upgradeProcess);
	}

}