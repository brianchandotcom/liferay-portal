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
public class VersionTreeMapTest {

	@Test
	public void testPutMultipleUpgradeProcesses() {
		VersionTreeMap versionTreeMap = new VersionTreeMap();

		UpgradeProcess[] upgradeProcesses = {
			new DummyUpgradeProcess(), new DummyUpgradeProcess(),
			new DummyUpgradeProcess()
		};

		versionTreeMap.put(new Version(1, 0, 0), upgradeProcesses);

		_checkTreeMapValues(versionTreeMap, upgradeProcesses);
	}

	@Test
	public void testPutSingleUpgradeProcess() {
		VersionTreeMap versionTreeMap = new VersionTreeMap();

		UpgradeProcess upgradeProcess = new DummyUpgradeProcess();

		versionTreeMap.put(new Version(1, 0, 0), upgradeProcess);

		UpgradeProcess[] upgradeProcesses = {upgradeProcess};

		_checkTreeMapValues(versionTreeMap, upgradeProcesses);
	}

	@Test
	public void testSingleMultiStepUpgrade() {
		MultiStepUpgradeProcess multiStepUpgradeProcess =
			new MultiStepUpgradeProcess();

		VersionTreeMap versionTreeMap = new VersionTreeMap();

		versionTreeMap.put(new Version(1, 0, 0), multiStepUpgradeProcess);

		_checkTreeMapValues(
			versionTreeMap, multiStepUpgradeProcess.getUpgradeSteps());
	}

	private void _checkTreeMapValues(
		VersionTreeMap versionTreeMap, UpgradeStep[] upgradeProcesses) {

		Assert.assertEquals(
			versionTreeMap.toString(), upgradeProcesses.length,
			versionTreeMap.size());

		Collection<Version> keys = versionTreeMap.keySet();

		Iterator<Version> iterator = keys.iterator();

		int i = 0;

		while (iterator.hasNext()) {
			Version version = iterator.next();

			UpgradeStep upgradeStep = versionTreeMap.get(version);

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

	private class MultiStepUpgradeProcess extends DummyUpgradeProcess {

		@Override
		protected UpgradeStep[] getPostUpgradeSteps() {
			return new UpgradeStep[] {_postUpgradeStep};
		}

		@Override
		protected UpgradeStep[] getPreUpgradeSteps() {
			return new UpgradeStep[] {_preUpgradeStep};
		}

		private UpgradeProcess _postUpgradeStep = new DummyUpgradeProcess();
		private UpgradeProcess _preUpgradeStep = new DummyUpgradeProcess();

	}

}