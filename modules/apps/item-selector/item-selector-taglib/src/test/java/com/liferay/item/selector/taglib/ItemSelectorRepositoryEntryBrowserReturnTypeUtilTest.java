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
 * @author Sergio González
 */
public class ItemSelectorRepositoryEntryBrowserReturnTypeUtilTest {

	@Test
	public void testGetFirstAvailableDraggableFileReturnTypeFirst()
		throws Exception {

		List<ItemSelectorReturnType> itemSelectorReturnTypes =
			new ArrayList<>();

		UploadableFileReturnType draggableItemSelectorReturnType =
			new UploadableFileReturnType();

		itemSelectorReturnTypes.add(draggableItemSelectorReturnType);
		itemSelectorReturnTypes.add(new URLItemSelectorReturnType());

		ItemSelectorReturnType itemSelectorReturnType =
			ItemSelectorRepositoryEntryBrowserReturnTypeUtil.
				getFirstAvailableDraggableFileReturnType(
					itemSelectorReturnTypes);

		Assert.assertEquals(
			draggableItemSelectorReturnType, itemSelectorReturnType);
	}

	@Test
	public void testGetFirstAvailableDraggableFileReturnTypeSecond()
		throws Exception {

		List<ItemSelectorReturnType> itemSelectorReturnTypes =
			new ArrayList<>();

		UploadableFileReturnType draggableFileReturnType =
			new UploadableFileReturnType();

		itemSelectorReturnTypes.add(new URLItemSelectorReturnType());
		itemSelectorReturnTypes.add(draggableFileReturnType);

		ItemSelectorReturnType itemSelectorReturnType =
			ItemSelectorRepositoryEntryBrowserReturnTypeUtil.
				getFirstAvailableDraggableFileReturnType(
					itemSelectorReturnTypes);

		Assert.assertEquals(draggableFileReturnType, itemSelectorReturnType);
	}

	@Test
	public void testGetFirstAvailableExistingFileEntryReturnTypeFirst()
		throws Exception {

		List<ItemSelectorReturnType> itemSelectorReturnTypes =
			new ArrayList<>();

		URLItemSelectorReturnType existingFileEntryReturnType =
			new URLItemSelectorReturnType();

		itemSelectorReturnTypes.add(existingFileEntryReturnType);
		itemSelectorReturnTypes.add(new Base64ItemSelectorReturnType());

		ItemSelectorReturnType itemSelectorReturnType =
			ItemSelectorRepositoryEntryBrowserReturnTypeUtil.
				getFirstAvailableExistingFileEntryReturnType(
					itemSelectorReturnTypes);

		Assert.assertEquals(
			existingFileEntryReturnType, itemSelectorReturnType);
	}

	@Test
	public void testGetFirstAvailableExistingFileEntryReturnTypeSecond()
		throws Exception {

		List<ItemSelectorReturnType> itemSelectorReturnTypes =
			new ArrayList<>();

		URLItemSelectorReturnType existingFileEntryReturnType =
			new URLItemSelectorReturnType();

		itemSelectorReturnTypes.add(new Base64ItemSelectorReturnType());
		itemSelectorReturnTypes.add(existingFileEntryReturnType);

		ItemSelectorReturnType itemSelectorReturnType =
			ItemSelectorRepositoryEntryBrowserReturnTypeUtil.
				getFirstAvailableExistingFileEntryReturnType(
					itemSelectorReturnTypes);

		Assert.assertEquals(
			existingFileEntryReturnType, itemSelectorReturnType);
	}

	@Test
	public void testGetFirstAvailableMethodsDoNotModifyOriginalList()
		throws Exception {

		List<ItemSelectorReturnType> itemSelectorReturnTypes =
			new ArrayList<>();

		itemSelectorReturnTypes.add(new Base64ItemSelectorReturnType());
		itemSelectorReturnTypes.add(new URLItemSelectorReturnType());
		itemSelectorReturnTypes.add(new UploadableFileReturnType());

		ItemSelectorRepositoryEntryBrowserReturnTypeUtil.
			getFirstAvailableDraggableFileReturnType(itemSelectorReturnTypes);

		ItemSelectorRepositoryEntryBrowserReturnTypeUtil.
			getFirstAvailableExistingFileEntryReturnType(
				itemSelectorReturnTypes);

		Assert.assertEquals(3, itemSelectorReturnTypes.size());
	}

}