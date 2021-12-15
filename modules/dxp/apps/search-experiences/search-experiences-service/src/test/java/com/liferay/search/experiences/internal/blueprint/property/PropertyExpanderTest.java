/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.blueprint.property;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class PropertyExpanderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testExpand() {
		String string =
			"${one} | ${two} = ${one} | ${three|up} = ${three|len=4,up} | " +
				"${four} = \"${five}\" | ${si";

		_testExpand(string, string);

		Map<String, String> values = HashMapBuilder.put(
			"one", "alpha"
		).put(
			"two", "beta"
		).build();

		PropertyResolver propertyResolver1 = (name, options) -> values.get(
			name);

		_testExpand(
			"alpha | beta = alpha | ${three|up} = ${three|len=4,up} | " +
				"${four} = \"${five}\" | ${si",
			string, propertyResolver1);

		PropertyResolver propertyResolver2 = (name, options) -> {
			if (MapUtil.isEmpty(options)) {
				return null;
			}

			String value = "gamma";

			if (options.containsKey("len")) {
				value = StringUtil.shorten(
					value, GetterUtil.getInteger(options.get("len")));
			}

			if (options.containsKey("up")) {
				value = StringUtil.toUpperCase(value);
			}

			return value;
		};

		PropertyResolver propertyResolver3 = (name, options) -> {
			if (name.equals("five")) {
				return JSONFactoryUtil.createJSONObject();
			}

			return null;
		};

		_testExpand(
			"alpha | beta = alpha | GAMMA = G... | ${four} = {} | ${si", string,
			propertyResolver1, propertyResolver2, propertyResolver3);
	}

	private void _testExpand(
		String expected, String string, PropertyResolver... propertyResolvers) {

		PropertyExpander propertyExpander = new PropertyExpander(
			propertyResolvers);

		Assert.assertEquals(expected, propertyExpander.expand(string));
	}

}