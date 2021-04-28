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

package com.liferay.data.engine.rest.internal.dto.v2_0.util;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Mateus Santana
 */
@RunWith(MockitoJUnitRunner.class)
public class DataDefinitionRuleParameterUtilTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToDataDefinitionRuleParameters() throws JSONException {
		Assert.assertEquals(
			HashMapBuilder.<String, Object>put(
				"en_US", "value"
			).build(),
			DataDefinitionRuleParameterUtil.toDataDefinitionRuleParameters(
				JSONUtil.put("en_US", "value")));
	}

	@Test
	public void testToJSONObject() throws JSONException {
		JSONObject jsonObject = DataDefinitionRuleParameterUtil.toJSONObject(
			HashMapBuilder.<String, Object>put(
				"en_US", "value"
			).build());

		Assert.assertEquals("{\"en_US\":\"value\"}", jsonObject.toString());
	}

	@Test
	public void testToJSONObjectEmptyMap() throws JSONException {
		JSONObject jsonObject = DataDefinitionRuleParameterUtil.toJSONObject(
			new HashMap<>());

		Assert.assertEquals("{}", jsonObject.toString());
	}

}