/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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