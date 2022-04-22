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

package com.liferay.item.selector.taglib.internal.util;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Roberto Díaz
 */
public class ItemSelectorRepositoryEntryBrowserUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetItemSelectorReturnTypeClassNameWithoutResolver()
		throws Exception {

		String itemSelectorReturnTypeClassName =
			ItemSelectorRepositoryEntryBrowserUtil.
				getItemSelectorReturnTypeClassName(
					null, new TestItemSelectorReturnType());

		Class<TestItemSelectorReturnType> testItemSelectorReturnTypeClass =
			TestItemSelectorReturnType.class;

		Assert.assertEquals(
			testItemSelectorReturnTypeClass.getName(),
			itemSelectorReturnTypeClassName);
	}

	@Test
	public void testGetItemSelectorReturnTypeClassNameWithResolver()
		throws Exception {

		String itemSelectorReturnTypeClassName =
			ItemSelectorRepositoryEntryBrowserUtil.
				getItemSelectorReturnTypeClassName(
					new TestFileEntryItemSelectorReturnTypeResolver(),
					new TestItemSelectorReturnType());

		Class<FileEntryItemSelectorReturnType>
			fileEntryItemSelectorReturnTypeClass =
				FileEntryItemSelectorReturnType.class;

		Assert.assertEquals(
			fileEntryItemSelectorReturnTypeClass.getName(),
			itemSelectorReturnTypeClassName);
	}

	@Test
	public void testGetValueWithResolver() throws Exception {
		FileEntry fileEntry = Mockito.mock(FileEntry.class);
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Assert.assertEquals(
			"TestFileEntryItemSelectorReturnTypeResolverValue",
			ItemSelectorRepositoryEntryBrowserUtil.getValue(
				new TestFileEntryItemSelectorReturnTypeResolver(),
				new TestItemSelectorReturnType(), fileEntry, themeDisplay));
	}

	private class TestFileEntryItemSelectorReturnTypeResolver
		implements ItemSelectorReturnTypeResolver
			<FileEntryItemSelectorReturnType, FileEntry> {

		public Class<FileEntryItemSelectorReturnType>
			getItemSelectorReturnTypeClass() {

			return FileEntryItemSelectorReturnType.class;
		}

		public Class<FileEntry> getModelClass() {
			return FileEntry.class;
		}

		public String getValue(FileEntry fileEntry, ThemeDisplay themeDisplay)
			throws Exception {

			return "TestFileEntryItemSelectorReturnTypeResolverValue";
		}

	}

	private class TestItemSelectorReturnType implements ItemSelectorReturnType {
	}

}