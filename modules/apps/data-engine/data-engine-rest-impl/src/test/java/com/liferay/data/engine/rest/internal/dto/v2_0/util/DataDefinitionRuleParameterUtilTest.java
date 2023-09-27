/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

/**
 * @author Mateus Santana
 */
public class DataDefinitionRuleParameterUtilTest {

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