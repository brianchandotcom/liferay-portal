/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		upgradeVersionTreeMap.put(new Version(1, 0, 0), new MultiStepUpgrade());

		Collection<UpgradeProcess> upgradeProcesses =
			upgradeVersionTreeMap.values();

		_checkTreeMapValues(
			upgradeVersionTreeMap,
			upgradeProcesses.toArray(new UpgradeProcess[0]));
	}

	private void _checkTreeMapValues(
		UpgradeVersionTreeMap upgradeVersionTreeMap,
		UpgradeProcess[] upgradeProcesses) {

		Assert.assertEquals(
			upgradeVersionTreeMap.toString(), upgradeProcesses.length,
			upgradeVersionTreeMap.size());

		Collection<Version> keys = upgradeVersionTreeMap.keySet();

		Iterator<Version> iterator = keys.iterator();

		int i = 0;

		while (iterator.hasNext()) {
			Version version = iterator.next();

			UpgradeProcess upgradeProcess = upgradeVersionTreeMap.get(version);

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