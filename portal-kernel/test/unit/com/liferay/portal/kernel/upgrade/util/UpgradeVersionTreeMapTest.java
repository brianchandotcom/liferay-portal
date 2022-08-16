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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.version.Version;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Luis Ortiz
 */
public class UpgradeVersionTreeMapTest {

	@Test
	public void testPutMultipleUpgradeProcesses() {
		UpgradeVersionTreeMap treeMap = new UpgradeVersionTreeMap();

		UpgradeProcess[] upgradeProcesses = {
			new DummyUpgradeProcess(), new DummyUpgradeProcess(),
			new DummyUpgradeProcess()
		};

		treeMap.put(new Version(1, 0, 0), upgradeProcesses);

		_checkTreeMapValues(treeMap, upgradeProcesses);
	}

	@Test
	public void testPutSingleUpgradeProcess() {
		UpgradeVersionTreeMap treeMap = new UpgradeVersionTreeMap();

		UpgradeProcess upgradeProcess = new DummyUpgradeProcess();

		treeMap.put(new Version(1, 0, 0), upgradeProcess);

		UpgradeProcess[] upgradeProcesses = {upgradeProcess};

		_checkTreeMapValues(treeMap, upgradeProcesses);
	}

	@Test
	public void testSingleMultiStepUpgrade() {
		UpgradeVersionTreeMap treeMap = new UpgradeVersionTreeMap();

		treeMap.put(new Version(1, 0, 0), new MultiStepUpgrade());

		Collection<UpgradeStep> upgradeSteps = treeMap.values();

		_checkTreeMapValues(treeMap, upgradeSteps.toArray(new UpgradeStep[0]));
	}

	private void _checkTreeMapValues(
		UpgradeVersionTreeMap treeMap, UpgradeStep[] upgradeProcesses) {

		Assert.assertEquals(
			treeMap.toString(), upgradeProcesses.length, treeMap.size());

		Collection<Version> keys = treeMap.keySet();

		Iterator<Version> iterator = keys.iterator();

		int i = 0;

		while (iterator.hasNext()) {
			Version version = iterator.next();

			UpgradeStep upgradeStep = treeMap.get(version);

			Assert.assertEquals(upgradeProcesses[i], upgradeStep);

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

	private class MultiStepUpgrade extends DummyUpgradeProcess {

		@Override
		protected UpgradeStep[] getPostUpgradeSteps() {
			return new UpgradeStep[] {new DummyUpgradeProcess()};
		}

		@Override
		protected UpgradeStep[] getPreUpgradeSteps() {
			return new UpgradeStep[] {new DummyUpgradeProcess()};
		}

	}

}