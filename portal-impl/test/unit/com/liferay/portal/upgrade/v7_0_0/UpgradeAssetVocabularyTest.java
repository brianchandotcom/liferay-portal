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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author José Manuel Navarro
 */
public class UpgradeAssetVocabularyTest {

	@Test
	public void testUpgradeWithEmptySettings() {
		String oldSettings = "multiValued=false\nselectedClassNameIds=0\n";
		String expectedSettings =
			"multiValued=false\nselectedClassNameIds=0:-1\n";

		_testUpgrade(oldSettings, expectedSettings);
	}

	@Test
	public void testUpgradeWithMultipleRequiredSettings() {
		String oldSettings =
			"multiValued=true\nrequiredClassNameIds=10005\n" +
				"selectedClassNameIds=10007,10005,10109\n";
		String expectedSettings =
			"multiValued=true\nrequiredClassNameIds=10005:-1\n" +
				"selectedClassNameIds=10007:-1,10005:-1,10109:-1\n";

		_testUpgrade(oldSettings, expectedSettings);
	}

	@Test
	public void testUpgradeWithMultiValuedSettings() {
		String oldSettings =
			"multiValued=true\nselectedClassNameIds=10007,10005\n";
		String expectedSettings =
			"multiValued=true\nselectedClassNameIds=10007:-1,10005:-1\n";

		_testUpgrade(oldSettings, expectedSettings);
	}

	@Test
	public void testUpgradeWithoutRequiredSettings() {
		String oldSettings = "multiValued=false\nselectedClassNameIds=10007\n";
		String expectedSettings =
			"multiValued=false\nselectedClassNameIds=10007:-1\n";

		_testUpgrade(oldSettings, expectedSettings);
	}

	@Test
	public void testUpgradeWithRequiredSettings() {
		String oldSettings =
			"multiValued=false\nrequiredClassNameIds=10007\n" +
				"selectedClassNameIds=10007\n";
		String expectedSettings =
			"multiValued=false\nrequiredClassNameIds=10007:-1\n" +
				"selectedClassNameIds=10007:-1\n";

		_testUpgrade(oldSettings, expectedSettings);
	}

	private void _testUpgrade(String oldSettings, String expectedSettings) {
		UpgradeAsset upgradeAsset = new UpgradeAsset();

		String upgradedSettings = upgradeAsset.upgradeVocabularySettings(
			oldSettings);

		Assert.assertEquals(expectedSettings, upgradedSettings);
	}

}