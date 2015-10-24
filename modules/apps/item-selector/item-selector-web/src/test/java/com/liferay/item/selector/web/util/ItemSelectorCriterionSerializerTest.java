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

package com.liferay.item.selector.web.util;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.web.FlickrItemSelectorCriterion;
import com.liferay.item.selector.web.TestFileEntryItemSelectorReturnType;
import com.liferay.item.selector.web.TestStringItemSelectorReturnType;
import com.liferay.item.selector.web.TestURLItemSelectorReturnType;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Iván Zaera
 */
public class ItemSelectorCriterionSerializerTest {

	@Before
	public void setUp() {
		_flickrItemSelectorCriterion = new FlickrItemSelectorCriterion();

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(_testStringItemSelectorReturnType);
		desiredItemSelectorReturnTypes.add(_testURLItemSelectorReturnType);

		_flickrItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		_itemSelectorCriterionSerializer.addItemSelectorReturnType(
			_testFileEntryItemSelectorReturnType);
		_itemSelectorCriterionSerializer.addItemSelectorReturnType(
			_testStringItemSelectorReturnType);
		_itemSelectorCriterionSerializer.addItemSelectorReturnType(
			_testURLItemSelectorReturnType);

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testGetProperties() {
		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(_testURLItemSelectorReturnType);

		_flickrItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		Map<String, String[]> properties =
			_itemSelectorCriterionSerializer.getProperties(
				_flickrItemSelectorCriterion, _PREFIX);

		String json = properties.get(
			_PREFIX + ItemSelectorCriterionSerializer.JSON)[0];

		Class<? extends ItemSelectorReturnType>
			testURLItemSelectorReturnTypeClass =
				_testURLItemSelectorReturnType.getClass();

		json = _assert(
			"\"desiredItemSelectorReturnTypes\":\"" +
				testURLItemSelectorReturnTypeClass.getName() + "\"",
			json);
		json = _assert("\"tags\":[\"me\",\"photo\",\"picture\"]", json);
		json = _assert("\"user\":\"anonymous\"", json);

		Assert.assertEquals("{,,}", json);
	}

	@Test
	public void testSetProperties() {
		Map<String, String[]> properties = new HashMap<>();

		Class<? extends ItemSelectorReturnType>
			testURLItemSelectorReturnTypeClass =
				_testURLItemSelectorReturnType.getClass();

		properties.put(
			_PREFIX + ItemSelectorCriterionSerializer.JSON,
			new String[] {
				"{\"desiredItemSelectorReturnTypes\":\"" +
					testURLItemSelectorReturnTypeClass.getName() + "\",\"" +
						"tags\":[\"tag1\",\"tag2\",\"tag3\"],\"user\":\"" +
							"Joe Bloggs\"}"
			});

		_itemSelectorCriterionSerializer.setProperties(
			_flickrItemSelectorCriterion, _PREFIX, properties);

		Assert.assertEquals(
			"Joe Bloggs", _flickrItemSelectorCriterion.getUser());
		Assert.assertArrayEquals(
			new String[] {"tag1", "tag2", "tag3"},
			_flickrItemSelectorCriterion.getTags());

		List<ItemSelectorReturnType> expectedDesiredItemSelectorReturnTypes =
			new ArrayList<>();

		expectedDesiredItemSelectorReturnTypes.add(
			_testURLItemSelectorReturnType);

		Assert.assertEquals(
			expectedDesiredItemSelectorReturnTypes,
			_flickrItemSelectorCriterion.getDesiredItemSelectorReturnTypes());
	}

	private String _assert(String expected, String json) {
		Assert.assertTrue(json.contains(expected));

		return json.replaceAll(Pattern.quote(expected), "");
	}

	private static final String _PREFIX = "prefix_";

	private FlickrItemSelectorCriterion _flickrItemSelectorCriterion;
	private final ItemSelectorCriterionSerializer<FlickrItemSelectorCriterion>
		_itemSelectorCriterionSerializer =
			new ItemSelectorCriterionSerializer();
	private final ItemSelectorReturnType _testFileEntryItemSelectorReturnType =
		new TestFileEntryItemSelectorReturnType();
	private final ItemSelectorReturnType _testStringItemSelectorReturnType =
		new TestStringItemSelectorReturnType();
	private final ItemSelectorReturnType _testURLItemSelectorReturnType =
		new TestURLItemSelectorReturnType();

}