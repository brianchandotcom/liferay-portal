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

import com.liferay.item.selector.web.FlickrItemSelectorCriterion;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.util.HashMap;
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

		_itemSelectorCriterionSerializer =
			new ItemSelectorCriterionSerializer<>(
				_flickrItemSelectorCriterion, _PREFIX);

		new JSONFactoryUtil().setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testGetProperties() {
		Map<String, String[]> properties =
			_itemSelectorCriterionSerializer.getProperties();

		String json = properties.get(
			_PREFIX + ItemSelectorCriterionSerializer.JSON)[0];

		json = _assertContainsAndRemove("\"user\":\"anonymous\"", json);

		json = _assertContainsAndRemove(
			"\"tags\":[\"me\",\"photo\",\"picture\"]", json);

		Assert.assertEquals("{,}", json);
	}

	@Test
	public void testSetProperties() {
		Map<String, String[]> properties = new HashMap<>();

		properties.put(
			_PREFIX + ItemSelectorCriterionSerializer.JSON,
			new String[] {
				"{\"user\":\"Joe Bloggs\"," +
				"\"tags\":[\"tag1\",\"tag2\",\"tag3\"]}"
			});

		_itemSelectorCriterionSerializer.setProperties(properties);

		Assert.assertEquals(
			"Joe Bloggs", _flickrItemSelectorCriterion.getUser());
		Assert.assertArrayEquals(
			new String[] {"tag1", "tag2", "tag3"},
			_flickrItemSelectorCriterion.getTags());
	}

	private String _assertContainsAndRemove(String expected, String json) {
		Assert.assertTrue(json.contains(expected));

		return json.replaceAll(Pattern.quote(expected), "");
	}

	private static final String _PREFIX = "prefix_";

	private FlickrItemSelectorCriterion _flickrItemSelectorCriterion;
	private ItemSelectorCriterionSerializer<FlickrItemSelectorCriterion>
		_itemSelectorCriterionSerializer;

}