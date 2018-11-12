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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class JSONUtilTest {

	@Before
	public void setUp() throws Exception {
		JSONInit.init();

		JSONFactoryImpl jsonFactoryImpl = new JSONFactoryImpl();

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(jsonFactoryImpl);
	}

	@Test
	public void testSerializeArraysWithObjects() {
		JSONArray jsonArray = JSONUtil.put(JSONFactoryUtil.createJSONObject());

		Assert.assertEquals("[{}]", jsonArray.toString());
	}

}