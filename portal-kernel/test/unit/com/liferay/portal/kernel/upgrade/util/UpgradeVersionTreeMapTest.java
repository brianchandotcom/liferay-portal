/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.util;

import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.version.Version;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Luis Ortiz
 */
public class UpgradeVersionTreeMapTest {

	@Test
	public void testPutMultipleUpgradeProcesses() {
		UpgradeVersionTreeMap upgradeVersionTreeMap =
			new UpgradeVersionTreeMap();

		UpgradeProcess[] upgradeProcesses = {
			new DummyUpgradeProcess(), new DummyUpgradeProcess(),
			new DummyUpgradeProcess()
		};

		upgradeVersionTreeMap.put(new Version(1, 0, 0), upgradeProcesses);

		_checkTreeMapValues(upgradeVersionTreeMap, upgradeProcesses);
	}

	@Test
	public void testPutSingleUpgradeProcess() {
		UpgradeVersionTreeMap upgradeVersionTreeMap =
			new UpgradeVersionTreeMap();

		UpgradeProcess upgradeProcess = new DummyUpgradeProcess();

		upgradeVersionTreeMap.put(new Version(1, 0, 0), upgradeProcess);

		UpgradeProcess[] upgradeProcesses = {upgradeProcess};

		_checkTreeMapValues(upgradeVersionTreeMap, upgradeProcesses);
	}

	@Test
	public void testSingleMultiStepUpgrade() {
		UpgradeVersionTreeMap upgradeVersionTreeMap =
			new UpgradeVersionTreeMap();

		UpgradeProcess upgradeProcess = new MultiStepUpgrade();

		upgradeVersionTreeMap.put(new Version(1, 0, 0), upgradeProcess);

		UpgradeProcess[] upgradeProcesses =
			new UpgradeProcess[upgradeProcess.getUpgradeSteps().length];

		int i = 0;

		for (UpgradeStep upgradeStep : upgradeProcess.getUpgradeSteps()) {
			upgradeProcesses[i] = (UpgradeProcess)upgradeStep;
			i++;
		}

		_checkTreeMapValues(upgradeVersionTreeMap, upgradeProcesses);
	}

	private void _checkTreeMapValues(
		UpgradeVersionTreeMap upgradeVersionTreeMap,
		UpgradeProcess[] upgradeProcesses) {

		Assert.assertEquals(
			upgradeVersionTreeMap.toString(), 1, upgradeVersionTreeMap.size());

		List<UpgradeProcess> upgradeProcesList =
			upgradeVersionTreeMap.firstEntry(
			).getValue();

		Assert.assertEquals(
			upgradeProcesList.toString(), upgradeProcesses.length,
			upgradeProcesList.size());
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