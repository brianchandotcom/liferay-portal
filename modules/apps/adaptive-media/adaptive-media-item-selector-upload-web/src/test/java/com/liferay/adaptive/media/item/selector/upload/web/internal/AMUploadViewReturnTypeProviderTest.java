/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.item.selector.upload.web.internal;

import com.liferay.adaptive.media.image.item.selector.AMImageFileEntryItemSelectorReturnType;
import com.liferay.adaptive.media.image.item.selector.AMImageURLItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewReturnTypeProvider;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class AMUploadViewReturnTypeProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAddAMImageURLItemSelectorReturnTypeWithEmptyList()
		throws Exception {

		ItemSelectorViewReturnTypeProvider itemSelectorViewReturnTypeProvider =
			new AMUploadViewReturnTypeProvider();

		List<ItemSelectorReturnType> supportedItemSelectorReturnTypes =
			new ArrayList<>();

		List<ItemSelectorReturnType> itemSelectorReturnTypes =
			itemSelectorViewReturnTypeProvider.
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

		ItemSelectorViewReturnTypeProvider itemSelectorViewReturnTypeProvider =
			new AMUploadViewReturnTypeProvider();

		List<ItemSelectorReturnType> supportedItemSelectorReturnTypes =
			new ArrayList<>();

		supportedItemSelectorReturnTypes.add(
			new FileEntryItemSelectorReturnType());
		supportedItemSelectorReturnTypes.add(new URLItemSelectorReturnType());

		List<ItemSelectorReturnType> itemSelectorReturnTypes =
			itemSelectorViewReturnTypeProvider.
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