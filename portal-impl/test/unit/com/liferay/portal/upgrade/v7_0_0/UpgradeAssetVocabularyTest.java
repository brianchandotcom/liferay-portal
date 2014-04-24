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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.MockPortletPreferences;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.upgrade.v7_0_0.UpgradeMessageBoards;
import org.junit.Assert;
import org.junit.Test;

import javax.portlet.PortletPreferences;

/**
 * @author José Manuel Navarro
 */
public class UpgradeAssetVocabularyTest {

	@Test
	public void testUpgradeWithEmptySettings() {
		String oldSettings = "multiValued=false\n" +
			"selectedClassNameIds=0\n";
		String expectedSettings = "multiValued=false\n" +
			"selectedClassNameIds=0:0\n";

		_testUpgrade(oldSettings, expectedSettings);
	}

	@Test
	public void testUpgradeWithoutRequiredSettings() {
		String oldSettings = "multiValued=false\n" +
			"selectedClassNameIds=10007\n";
		String expectedSettings = "multiValued=false\n" +
			"selectedClassNameIds=10007:0\n";

		_testUpgrade(oldSettings, expectedSettings);
	}

	@Test
	public void testUpgradeWithRequiredSettings() {
		String oldSettings = "multiValued=false\n" +
			"requiredClassNameIds=10007\n" +
			"selectedClassNameIds=10007\n";
		String expectedSettings = "multiValued=false\n" +
			"requiredClassNameIds=10007:0\n" +
			"selectedClassNameIds=10007:0\n";

		_testUpgrade(oldSettings, expectedSettings);
	}

	@Test
	public void testUpgradeWithMultipleRequiredSettings() {
		String oldSettings = "multiValued=true\n" +
			"requiredClassNameIds=10005\n" +
			"selectedClassNameIds=10007,10005,10109\n";
		String expectedSettings = "multiValued=true\n" +
			"requiredClassNameIds=10005:0\n" +
			"selectedClassNameIds=10007:0,10005:0,10109:0\n";

		_testUpgrade(oldSettings, expectedSettings);
	}

	@Test
	public void testUpgradeWithMultiValuedSettings() {
		String oldSettings = "multiValued=true\n" +
			"selectedClassNameIds=10007,10005\n";
		String expectedSettings = "multiValued=true\n" +
			"selectedClassNameIds=10007:0,10005:0\n";

		_testUpgrade(oldSettings, expectedSettings);
	}

	private void _testUpgrade(String oldSettings, String expectedSettings) {
		UpgradeAsset upgradeAsset = new UpgradeAsset();

		String upgradedSettings =
			upgradeAsset.doUpgradeVocabularySettings(oldSettings);

		Assert.assertEquals(expectedSettings, upgradedSettings);
	}

}