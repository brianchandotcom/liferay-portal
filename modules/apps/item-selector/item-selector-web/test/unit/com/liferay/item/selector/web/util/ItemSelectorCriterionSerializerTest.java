/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.item.selector.web.util;

import com.liferay.item.selector.web.FlickrItemSelectorCriterion;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

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
	}

	@Test
	public void testGetProperties() {
		Map<String, String[]> properties =
			_itemSelectorCriterionSerializer.getProperties();

		Assert.assertEquals(2, properties.size());
		Assert.assertEquals(
			_flickrItemSelectorCriterion.getUser(),
			properties.get("prefix_user")[0]);
		Assert.assertEquals(
			StringUtil.merge(_flickrItemSelectorCriterion.getTags()),
			properties.get("prefix_tags")[0]);
	}

	@Test
	public void testSetProperties() {
		Map<String, String[]> properties = new HashMap<>();

		properties.put(_PREFIX + "user", new String[] {"Joe Bloggs"});
		properties.put(_PREFIX + "tags", new String[] {"tag1,tag2,tag3"});
		properties.put("user", new String[] {"Invalid user"});

		_itemSelectorCriterionSerializer.setProperties(properties);

		Assert.assertEquals(
			"Joe Bloggs", _flickrItemSelectorCriterion.getUser());
		Assert.assertArrayEquals(
			new String[] {"tag1", "tag2", "tag3"},
			_flickrItemSelectorCriterion.getTags());
	}

	private static final String _PREFIX = "prefix_";

	private FlickrItemSelectorCriterion _flickrItemSelectorCriterion;
	private ItemSelectorCriterionSerializer<FlickrItemSelectorCriterion>
		_itemSelectorCriterionSerializer;

}