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
		testUpgrade(
			"multiValued=false\nselectedClassNameIds=0\n",
			"multiValued=false\nselectedClassNameIds=0:-1\n");
	}

	@Test
	public void testUpgradeWithMultipleRequiredSettings() {
		testUpgrade(
			"multiValued=true\nrequiredClassNameIds=10005\n" +
				"selectedClassNameIds=10007,10005,10109\n",
			"multiValued=true\nrequiredClassNameIds=10005:-1\n" +
				"selectedClassNameIds=10007:-1,10005:-1,10109:-1\n");
	}

	@Test
	public void testUpgradeWithMultiValuedSettings() {
		testUpgrade(
			"multiValued=true\nselectedClassNameIds=10007,10005\n",
			"multiValued=true\nselectedClassNameIds=10007:-1,10005:-1\n");
	}

	@Test
	public void testUpgradeWithoutRequiredSettings() {
		testUpgrade(
			"multiValued=false\nselectedClassNameIds=10007\n",
			"multiValued=false\nselectedClassNameIds=10007:-1\n");
	}

	@Test
	public void testUpgradeWithRequiredSettings() {
		testUpgrade(
			"multiValued=false\nrequiredClassNameIds=10007\n" +
				"selectedClassNameIds=10007\n",
			"multiValued=false\nrequiredClassNameIds=10007:-1\n" +
				"selectedClassNameIds=10007:-1\n");
	}

	protected void testUpgrade(
		String oldSettings, String expectedUpgradedSettings) {

		UpgradeAsset upgradeAsset = new UpgradeAsset();

		String actualUpgradedSettings = upgradeAsset.upgradeVocabularySettings(
			oldSettings);

		Assert.assertEquals(expectedUpgradedSettings, actualUpgradedSettings);
	}

}