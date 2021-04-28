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

package com.liferay.item.selector.web.internal.util;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.web.internal.FlickrItemSelectorCriterion;
import com.liferay.item.selector.web.internal.TestFileEntryItemSelectorReturnType;
import com.liferay.item.selector.web.internal.TestStringItemSelectorReturnType;
import com.liferay.item.selector.web.internal.TestURLItemSelectorReturnType;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Iván Zaera
 * @author Roberto Díaz
 */
public class ItemSelectorCriterionSerializerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_flickrItemSelectorCriterion = new FlickrItemSelectorCriterion();

		_flickrItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			_testStringItemSelectorReturnType, _testURLItemSelectorReturnType);

		_stubItemSelectorCriterionSerializerImpl.addItemSelectorReturnType(
			_testFileEntryItemSelectorReturnType);
		_stubItemSelectorCriterionSerializerImpl.addItemSelectorReturnType(
			_testStringItemSelectorReturnType);
		_stubItemSelectorCriterionSerializerImpl.addItemSelectorReturnType(
			_testURLItemSelectorReturnType);
	}

	@Test
	public void testGetProperties() {
		_flickrItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			_testURLItemSelectorReturnType);

		String json = _stubItemSelectorCriterionSerializerImpl.serialize(
			_flickrItemSelectorCriterion);

		String itemSelectorReturnTypeKey =
			ItemSelectorKeyUtil.getItemSelectorReturnTypeKey(
				TestURLItemSelectorReturnType.class);

		json = _assert(
			"\"desiredItemSelectorReturnTypes\":\"" +
				itemSelectorReturnTypeKey + "\"",
			json);

		json = _assert("\"tags\":[\"me\",\"photo\",\"picture\"]", json);
		json = _assert("\"user\":\"anonymous\"", json);

		Assert.assertEquals("{,,}", json);
	}

	@Test
	public void testSetProperties() {
		Class<? extends ItemSelectorReturnType>
			testURLItemSelectorReturnTypeClass =
				_testURLItemSelectorReturnType.getClass();

		String json = StringBundler.concat(
			"{\"desiredItemSelectorReturnTypes\":\"",
			testURLItemSelectorReturnTypeClass.getName(), "\",\"tags\":",
			"[\"tag1\",\"tag2\",\"tag3\"],\"user\":\"Joe Bloggs\"}");

		_flickrItemSelectorCriterion =
			_stubItemSelectorCriterionSerializerImpl.deserialize(
				_flickrItemSelectorCriterion.getClass(), json);

		Assert.assertEquals(
			"Joe Bloggs", _flickrItemSelectorCriterion.getUser());
		Assert.assertArrayEquals(
			new String[] {"tag1", "tag2", "tag3"},
			_flickrItemSelectorCriterion.getTags());

		Assert.assertEquals(
			ListUtil.fromArray(_testURLItemSelectorReturnType),
			_flickrItemSelectorCriterion.getDesiredItemSelectorReturnTypes());
	}

	private String _assert(String expected, String json) {
		Assert.assertTrue(json, json.contains(expected));

		return json.replaceAll(Pattern.quote(expected), "");
	}

	private FlickrItemSelectorCriterion _flickrItemSelectorCriterion;
	private final StubItemSelectorCriterionSerializerImpl
		_stubItemSelectorCriterionSerializerImpl =
			new StubItemSelectorCriterionSerializerImpl();
	private final ItemSelectorReturnType _testFileEntryItemSelectorReturnType =
		new TestFileEntryItemSelectorReturnType();
	private final ItemSelectorReturnType _testStringItemSelectorReturnType =
		new TestStringItemSelectorReturnType();
	private final ItemSelectorReturnType _testURLItemSelectorReturnType =
		new TestURLItemSelectorReturnType();

	private class StubItemSelectorCriterionSerializerImpl
		extends ItemSelectorCriterionSerializerImpl {

		@Override
		public void addItemSelectorReturnType(
			ItemSelectorReturnType itemSelectorReturnType) {

			super.addItemSelectorReturnType(itemSelectorReturnType);
		}

	}

}