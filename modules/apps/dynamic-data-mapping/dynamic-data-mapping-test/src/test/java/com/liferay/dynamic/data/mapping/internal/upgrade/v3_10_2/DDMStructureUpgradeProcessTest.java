/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_10_2;

import com.liferay.dynamic.data.mapping.BaseDDMTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Carolina Barbosa
 * @author Renato Rego
 */
public class DDMStructureUpgradeProcessTest extends BaseDDMTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpDDMFormJSONDeserializer();
		setUpDDMFormJSONSerializer();
		setUpDDMFormLayoutJSONDeserializer();
		setUpDDMFormLayoutJSONSerializer();
		setUpJSONFactoryUtil();
		setUpLanguageUtil();
	}

	@Test
	public void testUpgradeDDMStructureLayoutDefinition()
		throws Exception, IOException {

		DDMStructureUpgradeProcess ddmStructureUpgradeProcess =
			new DDMStructureUpgradeProcess(
				ddmFormJSONDeserializer, ddmFormLayoutJSONDeserializer,
				ddmFormLayoutJSONSerializer, ddmFormJSONSerializer,
				jsonFactory);

		JSONAssert.assertEquals(
			read("updated-ddm-structure-layout-definition.json"),
			ddmStructureUpgradeProcess.upgradeDDMStructureLayoutDefinition(
				read("ddm-structure-layout-definition.json")),
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

		JSONAssert.assertEquals(
			read("updated-ddm-structure-version-definition.json"),
			ddmStructureUpgradeProcess.upgradeDDMStructureVersionDefinition(
				read("ddm-structure-version-definition.json")),
			false);
	}

}