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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Luis Ortiz
 */
public class VersionTreeMapTest {

	@Test
	public void testInsertMultipleUpgradeProcesses() {
		VersionTreeMap treeMap = new VersionTreeMap();

		UpgradeProcess[] upgradeProcesses = {
			new DummyUpgradeProcess(), new DummyUpgradeProcess(),
			new DummyUpgradeProcess()
		};

		treeMap.put(new Version(1, 0, 0), upgradeProcesses);

		_checkTreeMapValues(treeMap, upgradeProcesses);
	}

	@Test
	public void testInsertSingleUpgradeProcess() {
		VersionTreeMap treeMap = new VersionTreeMap();

		UpgradeProcess upgradeProcess = new DummyUpgradeProcess();

		treeMap.put(new Version(1, 0, 0), upgradeProcess);

		UpgradeProcess[] upgradeProcesses = {upgradeProcess};

		_checkTreeMapValues(treeMap, upgradeProcesses);
	}

	private void _checkTreeMapValues(
		VersionTreeMap treeMap, UpgradeProcess[] upgradeProcesses) {

		Assert.assertEquals(upgradeProcesses.length, treeMap.size());

		Collection<Version> keys = treeMap.keySet();

		Iterator<Version> iterator = keys.iterator();

		int i = 0;

		while (iterator.hasNext()) {
			Version version = iterator.next();

			UpgradeProcess upgradeProcess = treeMap.get(version);

			Assert.assertEquals(upgradeProcesses[i], upgradeProcess);

			String step = version.getQualifier();

			if (iterator.hasNext()) {
				Assert.assertTrue(step.equals("step-" + (i + 1)));
			}
			else {
				Assert.assertTrue(step.equals(StringPool.BLANK));
			}

			i++;
		}
	}

}