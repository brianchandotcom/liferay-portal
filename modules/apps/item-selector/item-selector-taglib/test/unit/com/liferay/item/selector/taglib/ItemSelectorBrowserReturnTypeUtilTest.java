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

		populateItemSelectorReturnTypes();

		ItemSelectorReturnType itemSelectorReturnType =
			ItemSelectorBrowserReturnTypeUtil.
				getFirstAvailableDraggableFileReturnType(
					_itemSelectorReturnTypes);

		Assert.assertEquals(
			Base64ItemSelectorReturnType.class.getName(),
			itemSelectorReturnType.getClass().getName());
	}

	@Test
	public void testGetFirstAvailableExistingFileEntryReturnType()
		throws Exception {

		populateItemSelectorReturnTypes();

		ItemSelectorReturnType itemSelectorReturnType =
			ItemSelectorBrowserReturnTypeUtil.
				getFirstAvailableExistingFileEntryReturnType(
					_itemSelectorReturnTypes);

		Assert.assertEquals(
			URLItemSelectorReturnType.class.getName(),
			itemSelectorReturnType.getClass().getName());
	}

	@Test
	public void testGetFirstAvailableMethodsDoNotModifyOriginalList()
		throws Exception {

		populateItemSelectorReturnTypes();

		ItemSelectorBrowserReturnTypeUtil.
			getFirstAvailableDraggableFileReturnType(_itemSelectorReturnTypes);

		ItemSelectorBrowserReturnTypeUtil.
			getFirstAvailableExistingFileEntryReturnType(
				_itemSelectorReturnTypes);

		Assert.assertEquals(3, _itemSelectorReturnTypes.size());
	}

	protected void populateItemSelectorReturnTypes() {
		_itemSelectorReturnTypes.add(new Base64ItemSelectorReturnType());
		_itemSelectorReturnTypes.add(new URLItemSelectorReturnType());
		_itemSelectorReturnTypes.add(new UploadableFileReturnType());
	}

	private final List<ItemSelectorReturnType> _itemSelectorReturnTypes =
		new ArrayList<>();

}