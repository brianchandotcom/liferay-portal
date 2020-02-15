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

package com.liferay.layout.page.template.util.test;

import com.liferay.layout.page.template.util.LayoutDataConverter;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.FileImpl;

import java.util.List;

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

		LayoutStructure layoutStructure = LayoutStructure.of(actualLayoutData);

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getMainLayoutStructureItem();

		List<String> mainItemIds = layoutStructureItem.getChildrenItemIds();

		LayoutStructureItem container0LayoutStructureItem =
			layoutStructure.getLayoutStructureItem(mainItemIds.get(0));
		LayoutStructureItem container1LayoutStructureItem =
			layoutStructure.getLayoutStructureItem(mainItemIds.get(1));
		LayoutStructureItem container2LayoutStructureItem =
			layoutStructure.getLayoutStructureItem(mainItemIds.get(2));
		LayoutStructureItem container3LayoutStructureItem =
			layoutStructure.getLayoutStructureItem(mainItemIds.get(3));

		List<String> container0ChildrenItemIds =
			container0LayoutStructureItem.getChildrenItemIds();
		List<String> container1ChildrenItemIds =
			container1LayoutStructureItem.getChildrenItemIds();
		List<String> container2ChildrenItemIds =
			container2LayoutStructureItem.getChildrenItemIds();
		List<String> container3ChildrenItemIds =
			container3LayoutStructureItem.getChildrenItemIds();

		LayoutStructureItem row0LayoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				container0ChildrenItemIds.get(0));
		LayoutStructureItem row1LayoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				container1ChildrenItemIds.get(0));
		LayoutStructureItem row2LayoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				container2ChildrenItemIds.get(0));
		LayoutStructureItem row3LayoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				container3ChildrenItemIds.get(0));

		List<String> row0ChildrenItemIds =
			row0LayoutStructureItem.getChildrenItemIds();
		List<String> row1ChildrenItemIds =
			row1LayoutStructureItem.getChildrenItemIds();
		List<String> row2ChildrenItemIds =
			row2LayoutStructureItem.getChildrenItemIds();
		List<String> row3ChildrenItemIds =
			row3LayoutStructureItem.getChildrenItemIds();

		LayoutStructureItem row0Column0LayoutStructureItem =
			layoutStructure.getLayoutStructureItem(row0ChildrenItemIds.get(0));
		LayoutStructureItem row0Column1LayoutStructureItem =
			layoutStructure.getLayoutStructureItem(row0ChildrenItemIds.get(1));
		LayoutStructureItem row1Column0LayoutStructureItem =
			layoutStructure.getLayoutStructureItem(row1ChildrenItemIds.get(0));
		LayoutStructureItem row1Column1LayoutStructureItem =
			layoutStructure.getLayoutStructureItem(row1ChildrenItemIds.get(1));

		List<String> row0Column0ChildrenItemIds =
			row0Column0LayoutStructureItem.getChildrenItemIds();
		List<String> row0Column1ChildrenItemIds =
			row0Column1LayoutStructureItem.getChildrenItemIds();
		List<String> row1Column0ChildrenItemIds =
			row1Column0LayoutStructureItem.getChildrenItemIds();
		List<String> row1Column1ChildrenItemIds =
			row1Column1LayoutStructureItem.getChildrenItemIds();

		String expectedLayoutData = StringUtil.replace(
			_read("expected_layout_data_v1_complete.json"),
			new String[] {
				"MAIN-UUID", "CONTAINER0-UUID", "CONTAINER1-UUID",
				"CONTAINER2-UUID", "CONTAINER3-UUID", "ROW0-UUID", "ROW1-UUID",
				"ROW2-UUID", "ROW3-UUID", "ROW0-COLUMN0-UUID",
				"ROW0-COLUMN1-UUID", "ROW1-COLUMN1-UUID", "ROW1-COLUMN0-UUID",
				"ROW2-COLUMN0-UUID", "ROW3-COLUMN0-UUID", "FRAGMENT0-UUID",
				"FRAGMENT1-UUID", "FRAGMENT2-UUID", "FRAGMENT3-UUID",
				"FRAGMENT4-UUID", "FRAGMENT5-UUID", "FRAGMENT6-UUID",
				"FRAGMENT7-UUID", "FRAGMENT8-UUID"
			},
			new String[] {
				layoutStructure.getMainItemId(), mainItemIds.get(0),
				mainItemIds.get(1), mainItemIds.get(2), mainItemIds.get(3),
				container0ChildrenItemIds.get(0),
				container1ChildrenItemIds.get(0),
				container2ChildrenItemIds.get(0),
				container3ChildrenItemIds.get(0), row0ChildrenItemIds.get(0),
				row0ChildrenItemIds.get(1), row1ChildrenItemIds.get(1),
				row1ChildrenItemIds.get(0), row2ChildrenItemIds.get(0),
				row3ChildrenItemIds.get(0), row0Column0ChildrenItemIds.get(0),
				row0Column0ChildrenItemIds.get(1),
				row0Column1ChildrenItemIds.get(0),
				row0Column1ChildrenItemIds.get(1),
				row1Column0ChildrenItemIds.get(0),
				row1Column0ChildrenItemIds.get(1),
				row1Column1ChildrenItemIds.get(0),
				row1Column1ChildrenItemIds.get(1), mainItemIds.get(4)
			});

		JSONObject expectedLayoutDataJSONObject =
			JSONFactoryUtil.createJSONObject(expectedLayoutData);

		JSONObject expectedItemsJSONObject =
			expectedLayoutDataJSONObject.getJSONObject("items");

		JSONObject actualLayoutDataJSONObject = layoutStructure.toJSONObject();

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

		LayoutStructure layoutStructure = LayoutStructure.of(actualLayoutData);

		String expectedLayoutData = StringUtil.replace(
			_read("expected_layout_data_v1_empty.json"), "MAIN-UUID",
			layoutStructure.getMainItemId());

		JSONObject expectedLayoutDataJSONObject =
			JSONFactoryUtil.createJSONObject(expectedLayoutData);

		JSONObject layoutStructureJSONObject = layoutStructure.toJSONObject();

		Assert.assertEquals(
			expectedLayoutDataJSONObject.toJSONString(),
			layoutStructureJSONObject.toJSONString());
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

}