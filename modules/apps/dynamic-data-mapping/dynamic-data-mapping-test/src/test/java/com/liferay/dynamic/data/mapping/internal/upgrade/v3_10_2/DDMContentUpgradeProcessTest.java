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

import com.liferay.dynamic.data.mapping.internal.io.DDMFormValuesJSONDeserializer;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.powermock.api.support.membermodification.MemberMatcher;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Carolina Barbosa
 */
public class DDMContentUpgradeProcessTest
	extends BaseDDMUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_setUpDDMFormValuesJSONDeserializer();
	}

	@Test
	public void testUpgradeDDMContentData() throws Exception, IOException {
		DDMContentUpgradeProcess ddmContentUpgradeProcess =
			new DDMContentUpgradeProcess(
				ddmFormJSONDeserializer, ddmFormValuesJSONDeserializer,
				ddmFormValuesJSONSerializer, jsonFactory);

		String data = read("ddm-content-data.json");

		String definition = read("ddm-structure-version-definition.json");

		JSONAssert.assertEquals(
			read("updated-ddm-content-data.json"),
			ddmContentUpgradeProcess.upgradeDDMContentData(data, definition),
			false);
	}

	private void _setUpDDMFormValuesJSONDeserializer() throws Exception {
		MemberMatcher.field(
			DDMFormValuesJSONDeserializer.class, "_serviceTrackerMap"
		).set(
			ddmFormValuesJSONDeserializer,
			ProxyFactory.newDummyInstance(ServiceTrackerMap.class)
		);
	}

}