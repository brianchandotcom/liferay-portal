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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_10_2;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Carolina Barbosa
 * @author Renato Rego
 */
public class DDMStructureUpgradeProcessTest
	extends BaseDDMUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testUpgradeDDMStructureLayoutDefinition()
		throws Exception, IOException {

		DDMStructureUpgradeProcess ddmStructureUpgradeProcess =
			new DDMStructureUpgradeProcess(
				ddmFormJSONDeserializer, ddmFormLayoutJSONDeserializer,
				ddmFormLayoutJSONSerializer, ddmFormJSONSerializer,
				jsonFactory);

		String definition = read("ddm-structure-layout-definition.json");

		JSONAssert.assertEquals(
			read("updated-ddm-structure-layout-definition.json"),
			ddmStructureUpgradeProcess.upgradeDDMStructureLayoutDefinition(
				definition),
			false);
	}

	@Test
	public void testUpgradeDDMStructureVersionDefinition()
		throws Exception, IOException {

		DDMStructureUpgradeProcess ddmStructureUpgradeProcess =
			new DDMStructureUpgradeProcess(
				ddmFormJSONDeserializer, ddmFormLayoutJSONDeserializer,
				ddmFormLayoutJSONSerializer, ddmFormJSONSerializer,
				jsonFactory);

		String definition = read("ddm-structure-version-definition.json");

		JSONAssert.assertEquals(
			read("updated-ddm-structure-version-definition.json"),
			ddmStructureUpgradeProcess.upgradeDDMStructureVersionDefinition(
				definition),
			false);
	}

}