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

package com.liferay.adaptive.media.blogs.item.selector.web.internal.provider;

import com.liferay.adaptive.media.image.item.selector.AMImageFileEntryItemSelectorReturnType;
import com.liferay.adaptive.media.image.item.selector.AMImageURLItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewReturnTypeProvider;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sergio González
 */
public class AMBlogsItemSelectorViewReturnTypeProviderTest {

	@Test
	public void testAddAMImageURLItemSelectorReturnTypeWithEmptyList()
		throws Exception {

		ItemSelectorViewReturnTypeProvider
			amBlogsItemSelectorViewReturnTypeProvider =
				new AMBlogsItemSelectorViewReturnTypeProvider();

		List<ItemSelectorReturnType> supportedItemSelectorReturnTypes =
			new ArrayList<>();

		List<ItemSelectorReturnType> itemSelectorReturnTypes =
			amBlogsItemSelectorViewReturnTypeProvider.
				populateSupportedItemSelectorReturnTypes(
					supportedItemSelectorReturnTypes);

		Assert.assertEquals(
			itemSelectorReturnTypes.toString(), 2,
			itemSelectorReturnTypes.size());
		Assert.assertTrue(
			itemSelectorReturnTypes.get(0) instanceof
				AMImageFileEntryItemSelectorReturnType);
		Assert.assertTrue(
			itemSelectorReturnTypes.get(1) instanceof
				AMImageURLItemSelectorReturnType);
	}

	@Test
	public void testAddAMImageURLItemSelectorReturnTypeWithNonemptyList()
		throws Exception {

		ItemSelectorViewReturnTypeProvider
			amBlogsItemSelectorViewReturnTypeProvider =
				new AMBlogsItemSelectorViewReturnTypeProvider();

		List<ItemSelectorReturnType> supportedItemSelectorReturnTypes =
			new ArrayList<>();

		supportedItemSelectorReturnTypes.add(
			new FileEntryItemSelectorReturnType());
		supportedItemSelectorReturnTypes.add(new URLItemSelectorReturnType());

		List<ItemSelectorReturnType> itemSelectorReturnTypes =
			amBlogsItemSelectorViewReturnTypeProvider.
				populateSupportedItemSelectorReturnTypes(
					supportedItemSelectorReturnTypes);

		Assert.assertEquals(
			itemSelectorReturnTypes.toString(), 4,
			itemSelectorReturnTypes.size());
		Assert.assertTrue(
			itemSelectorReturnTypes.get(0) instanceof
				FileEntryItemSelectorReturnType);
		Assert.assertTrue(
			itemSelectorReturnTypes.get(1) instanceof
				URLItemSelectorReturnType);
		Assert.assertTrue(
			itemSelectorReturnTypes.get(2) instanceof
				AMImageFileEntryItemSelectorReturnType);
		Assert.assertTrue(
			itemSelectorReturnTypes.get(3) instanceof
				AMImageURLItemSelectorReturnType);
	}

}