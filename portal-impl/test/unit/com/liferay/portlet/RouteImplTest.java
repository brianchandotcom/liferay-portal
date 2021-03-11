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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.Route;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portlet.internal.RouteImpl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Connor McKay
 */
public class RouteImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testNonmatchingRoute() {
		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(new HttpImpl());

		Map<String, String> parameters = HashMapBuilder.put(
			"action", "view"
		).put(
			"id", "bob"
		).build();

		Map<String, String> originalParameters = new HashMap<>(parameters);

		Route route = new RouteImpl("{action}/{id:\\d+}");

		String url = route.parametersToUrl(parameters);

		Assert.assertNull(url);

		Assert.assertEquals(originalParameters, parameters);
	}

}