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
 */
public class DDMContentUpgradeProcessTest extends BaseDDMTestCase {

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
		setUpDDMFormValuesJSONDeserializer();
		setUpDDMFormValuesJSONSerializer();
		setUpJSONFactoryUtil();
		setUpLanguageUtil();
	}

	@Test
	public void testUpgradeDDMContentData() throws Exception, IOException {
		DDMContentUpgradeProcess ddmContentUpgradeProcess =
			new DDMContentUpgradeProcess(
				ddmFormJSONDeserializer, ddmFormValuesJSONDeserializer,
				ddmFormValuesJSONSerializer, jsonFactory);

		JSONAssert.assertEquals(
			read("updated-ddm-content-data.json"),
			ddmContentUpgradeProcess.upgradeDDMContentData(
				read("ddm-content-data.json"),
				read("ddm-structure-version-definition.json")),
			false);
	}

}