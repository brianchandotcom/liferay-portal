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

package com.liferay.layout.page.template.internal.util;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.FileImpl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Rubén Pulido
 */
public class LayoutDataConverterTest {

	@BeforeClass
	public static void setUpClass() {
		new FileUtil().setFile(new FileImpl());

		new JSONFactoryUtil().setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testConvertEmpty() throws Exception {
		String actualLayoutData = LayoutDataConverter.convert(
			_read("layout_data_v0_empty.json"));

		JSONObject actualLayoutDataJSONObject =
			JSONFactoryUtil.createJSONObject(actualLayoutData);

		JSONObject rootItemsJSONObject =
			actualLayoutDataJSONObject.getJSONObject("rootItems");

		String mainUUID = rootItemsJSONObject.getString("main");

		String expectedLayoutData = StringUtil.replace(
			_read("expected_layout_data_v1_empty.json"), "MAIN-UUID", mainUUID);

		JSONObject expectedLayoutDataJSONObject =
			JSONFactoryUtil.createJSONObject(expectedLayoutData);

		Assert.assertEquals(
			expectedLayoutDataJSONObject.toJSONString(),
			actualLayoutDataJSONObject.toJSONString());
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

}