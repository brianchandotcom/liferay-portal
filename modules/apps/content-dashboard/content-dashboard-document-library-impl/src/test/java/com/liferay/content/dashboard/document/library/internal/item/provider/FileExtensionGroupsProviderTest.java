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

package com.liferay.content.dashboard.document.library.internal.item.provider;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MimeTypes;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Cristina González
 */
public class FileExtensionGroupsProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetFileExtensionGroups() throws Exception {
		FileExtensionGroupsProvider fileExtensionGroupsProvider =
			new FileExtensionGroupsProvider();

		MimeTypes mimeTypes = Mockito.mock(MimeTypes.class);

		Mockito.when(
			mimeTypes.getExtensions("image/png")
		).thenReturn(
			Collections.singleton("png")
		);

		ReflectionTestUtil.setFieldValue(
			fileExtensionGroupsProvider, "_mimeTypes", mimeTypes);

		fileExtensionGroupsProvider.activate(new HashMap<>());

		Assert.assertTrue(
			ListUtil.exists(
				fileExtensionGroupsProvider.getFileExtensionGroups(),
				fileExtensionGroup -> Objects.equals(
					fileExtensionGroup.getKey(), "image")));
	}

	@Test
	public void testGetFileGroupKey() throws Exception {
		FileExtensionGroupsProvider fileExtensionGroupsProvider =
			new FileExtensionGroupsProvider();

		MimeTypes mimeTypes = Mockito.mock(MimeTypes.class);

		Mockito.when(
			mimeTypes.getExtensions("image/png")
		).thenReturn(
			Collections.singleton("png")
		);

		ReflectionTestUtil.setFieldValue(
			fileExtensionGroupsProvider, "_mimeTypes", mimeTypes);

		fileExtensionGroupsProvider.activate(new HashMap<>());

		Assert.assertEquals(
			"image", fileExtensionGroupsProvider.getFileGroupKey("png"));
	}

	@Test
	public void testIsOther() throws Exception {
		FileExtensionGroupsProvider fileExtensionGroupsProvider =
			new FileExtensionGroupsProvider();

		MimeTypes mimeTypes = Mockito.mock(MimeTypes.class);

		Mockito.when(
			mimeTypes.getExtensions("image/png")
		).thenReturn(
			Collections.singleton("png")
		);

		ReflectionTestUtil.setFieldValue(
			fileExtensionGroupsProvider, "_mimeTypes", mimeTypes);

		fileExtensionGroupsProvider.activate(new HashMap<>());

		Assert.assertTrue(
			fileExtensionGroupsProvider.isOther("other-extension"));
	}

	@Test
	public void testIsOtherWithPNGExtension() throws Exception {
		FileExtensionGroupsProvider fileExtensionGroupsProvider =
			new FileExtensionGroupsProvider();

		MimeTypes mimeTypes = Mockito.mock(MimeTypes.class);

		Mockito.when(
			mimeTypes.getExtensions("image/png")
		).thenReturn(
			Collections.singleton("png")
		);

		ReflectionTestUtil.setFieldValue(
			fileExtensionGroupsProvider, "_mimeTypes", mimeTypes);

		fileExtensionGroupsProvider.activate(new HashMap<>());

		Assert.assertFalse(fileExtensionGroupsProvider.isOther("png"));
	}

}