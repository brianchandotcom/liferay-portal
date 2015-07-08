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

package com.liferay.item.selector.taglib;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.Base64ItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.UploadableFileReturnType;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class ItemSelectorBrowserReturnTypeUtilTest {

	@Test
	public void testGetFirstAvailableDraggableFileReturnType()
		throws Exception {

		populateItemSelectorReturnTypesSet();

		ItemSelectorReturnType itemSelectorReturnType =
			ItemSelectorBrowserReturnTypeUtil.
				getFirstAvailableDraggableFileReturnType(
					_itemSelectorReturnTypeSet);

		Assert.assertEquals(
			Base64ItemSelectorReturnType.class.getName(),
			itemSelectorReturnType.getClass().getName());
	}

	@Test
	public void testGetFirstAvailableExistingFileEntryReturnType()
		throws Exception {

		populateItemSelectorReturnTypesSet();

		ItemSelectorReturnType itemSelectorReturnType =
			ItemSelectorBrowserReturnTypeUtil.
				getFirstAvailableExistingFileEntryReturnType(
					_itemSelectorReturnTypeSet);

		Assert.assertEquals(
			URLItemSelectorReturnType.class.getName(),
			itemSelectorReturnType.getClass().getName());
	}

	@Test
	public void testGetFirstAvailableMethodsDoNotModifyOriginalSet()
		throws Exception {

		populateItemSelectorReturnTypesSet();

		ItemSelectorBrowserReturnTypeUtil.
			getFirstAvailableDraggableFileReturnType(
				_itemSelectorReturnTypeSet);

		ItemSelectorBrowserReturnTypeUtil.
			getFirstAvailableExistingFileEntryReturnType(
				_itemSelectorReturnTypeSet);

		Assert.assertEquals(3, _itemSelectorReturnTypeSet.size());
	}

	protected void populateItemSelectorReturnTypesSet() {
		_itemSelectorReturnTypeSet.add(new Base64ItemSelectorReturnType());
		_itemSelectorReturnTypeSet.add(new URLItemSelectorReturnType());
		_itemSelectorReturnTypeSet.add(new UploadableFileReturnType());
	}

	private final List<ItemSelectorReturnType> _itemSelectorReturnTypeSet =
		new ArrayList<>();

}