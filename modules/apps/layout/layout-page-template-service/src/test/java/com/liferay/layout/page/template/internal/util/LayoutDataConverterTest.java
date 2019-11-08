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
import com.liferay.portal.kernel.json.JSONArray;
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
	public void testConvertComplete() throws Exception {
		String actualLayoutData = LayoutDataConverter.convert(
			_read("layout_data_v0_complete.json"));

		JSONObject actualLayoutDataJSONObject =
			JSONFactoryUtil.createJSONObject(actualLayoutData);

		JSONObject rootItemsJSONObject =
			actualLayoutDataJSONObject.getJSONObject("rootItems");

		String mainUUID = rootItemsJSONObject.getString("main");

		JSONObject itemsJSONObject = actualLayoutDataJSONObject.getJSONObject(
			"items");

		JSONObject mainJSONObject = itemsJSONObject.getJSONObject(mainUUID);

		JSONArray mainChildrenJSONArray = mainJSONObject.getJSONArray(
			"children");

		String container0UUID = mainChildrenJSONArray.getString(0);
		String container1UUID = mainChildrenJSONArray.getString(1);
		String container2UUID = mainChildrenJSONArray.getString(2);
		String container3UUID = mainChildrenJSONArray.getString(3);

		JSONObject container0JSONObject = itemsJSONObject.getJSONObject(
			container0UUID);
		JSONObject container1JSONObject = itemsJSONObject.getJSONObject(
			container1UUID);
		JSONObject container2JSONObject = itemsJSONObject.getJSONObject(
			container2UUID);
		JSONObject container3JSONObject = itemsJSONObject.getJSONObject(
			container3UUID);

		JSONArray container0ChildrenJSONArray =
			container0JSONObject.getJSONArray("children");
		JSONArray container1ChildrenJSONArray =
			container1JSONObject.getJSONArray("children");
		JSONArray container2ChildrenJSONArray =
			container2JSONObject.getJSONArray("children");
		JSONArray container3ChildrenJSONArray =
			container3JSONObject.getJSONArray("children");

		String row0UUID = container0ChildrenJSONArray.getString(0);
		String row1UUID = container1ChildrenJSONArray.getString(0);
		String row2UUID = container2ChildrenJSONArray.getString(0);
		String row3UUID = container3ChildrenJSONArray.getString(0);

		JSONObject row0JSONObject = itemsJSONObject.getJSONObject(row0UUID);
		JSONObject row1JSONObject = itemsJSONObject.getJSONObject(row1UUID);
		JSONObject row2JSONObject = itemsJSONObject.getJSONObject(row2UUID);
		JSONObject row3JSONObject = itemsJSONObject.getJSONObject(row3UUID);

		JSONArray row0childrenJSONArray = row0JSONObject.getJSONArray(
			"children");
		JSONArray row1childrenJSONArray = row1JSONObject.getJSONArray(
			"children");
		JSONArray row2childrenJSONArray = row2JSONObject.getJSONArray(
			"children");
		JSONArray row3childrenJSONArray = row3JSONObject.getJSONArray(
			"children");

		String row0Column0UUID = row0childrenJSONArray.getString(0);
		String row0Column1UUID = row0childrenJSONArray.getString(1);
		String row1Column0UUID = row1childrenJSONArray.getString(0);
		String row1Column1UUID = row1childrenJSONArray.getString(1);
		String row2Column0UUID = row2childrenJSONArray.getString(0);
		String row3Column0UUID = row3childrenJSONArray.getString(0);

		JSONObject row0Column0JSONObject = itemsJSONObject.getJSONObject(
			row0Column0UUID);
		JSONObject row0Column1JSONObject = itemsJSONObject.getJSONObject(
			row0Column1UUID);
		JSONObject row1Column0JSONObject = itemsJSONObject.getJSONObject(
			row1Column0UUID);
		JSONObject row1Column1JSONObject = itemsJSONObject.getJSONObject(
			row1Column1UUID);

		JSONArray row0Column0ChildrenJSONArray =
			row0Column0JSONObject.getJSONArray("children");
		JSONArray row0Column1ChildrenJSONArray =
			row0Column1JSONObject.getJSONArray("children");
		JSONArray row1Column0ChildrenJSONArray =
			row1Column0JSONObject.getJSONArray("children");
		JSONArray row1Column1ChildrenJSONArray =
			row1Column1JSONObject.getJSONArray("children");

		String fragment0UUID = row0Column0ChildrenJSONArray.getString(0);
		String fragment1UUID = row0Column0ChildrenJSONArray.getString(1);
		String fragment2UUID = row0Column1ChildrenJSONArray.getString(0);
		String fragment3UUID = row0Column1ChildrenJSONArray.getString(1);
		String fragment4UUID = row1Column0ChildrenJSONArray.getString(0);
		String fragment5UUID = row1Column0ChildrenJSONArray.getString(1);
		String fragment6UUID = row1Column1ChildrenJSONArray.getString(0);
		String fragment7UUID = row1Column1ChildrenJSONArray.getString(1);
		String fragment8UUID = mainChildrenJSONArray.getString(4);

		String expectedLayoutData = _read(
			"expected_layout_data_v1_complete.json");

		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "MAIN-UUID", mainUUID);

		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "CONTAINER0-UUID", container0UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "CONTAINER1-UUID", container1UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "CONTAINER2-UUID", container2UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "CONTAINER3-UUID", container3UUID);

		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "ROW0-UUID", row0UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "ROW1-UUID", row1UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "ROW2-UUID", row2UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "ROW3-UUID", row3UUID);

		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "ROW0-COLUMN0-UUID", row0Column0UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "ROW0-COLUMN1-UUID", row0Column1UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "ROW1-COLUMN1-UUID", row1Column1UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "ROW1-COLUMN0-UUID", row1Column0UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "ROW2-COLUMN0-UUID", row2Column0UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "ROW3-COLUMN0-UUID", row3Column0UUID);

		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "FRAGMENT0-UUID", fragment0UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "FRAGMENT1-UUID", fragment1UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "FRAGMENT2-UUID", fragment2UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "FRAGMENT3-UUID", fragment3UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "FRAGMENT4-UUID", fragment4UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "FRAGMENT5-UUID", fragment5UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "FRAGMENT6-UUID", fragment6UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "FRAGMENT7-UUID", fragment7UUID);
		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "FRAGMENT8-UUID", fragment8UUID);

		JSONObject expectedLayoutDataJSONObject =
			JSONFactoryUtil.createJSONObject(expectedLayoutData);

		JSONObject expectedItemsJSONObject =
			expectedLayoutDataJSONObject.getJSONObject("items");

		JSONObject actualItemsJSONObject =
			actualLayoutDataJSONObject.getJSONObject("items");

		Assert.assertEquals(
			expectedItemsJSONObject.keySet(), actualItemsJSONObject.keySet());

		for (String key : expectedItemsJSONObject.keySet()) {
			JSONObject expectedItemJSONObject =
				expectedItemsJSONObject.getJSONObject(key);
			JSONObject actualItemJSONObject =
				actualItemsJSONObject.getJSONObject(key);

			Assert.assertEquals(
				expectedItemJSONObject.toJSONString(),
				actualItemJSONObject.toJSONString());
		}

		Assert.assertEquals(
			expectedLayoutDataJSONObject.keySet(),
			actualLayoutDataJSONObject.keySet());

		JSONObject expectedRootItemsJSONObject =
			expectedLayoutDataJSONObject.getJSONObject("rootItems");
		JSONObject actualRootItemsJSONObject =
			actualLayoutDataJSONObject.getJSONObject("rootItems");

		Assert.assertEquals(
			expectedRootItemsJSONObject.toJSONString(),
			actualRootItemsJSONObject.toJSONString());

		Assert.assertEquals(
			expectedLayoutDataJSONObject.getInt("version"),
			actualLayoutDataJSONObject.getInt("version"));
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