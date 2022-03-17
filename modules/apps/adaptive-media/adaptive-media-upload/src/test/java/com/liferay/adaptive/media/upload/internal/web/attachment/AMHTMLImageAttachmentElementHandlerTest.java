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

package com.liferay.adaptive.media.upload.internal.web.attachment;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.upload.AttachmentElementReplacer;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Tardín
 */
public class AMHTMLImageAttachmentElementHandlerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_fileEntry = Mockito.mock(FileEntry.class);

		Mockito.when(
			_fileEntry.getFileEntryId()
		).thenReturn(
			_IMAGE_FILE_ENTRY_ID
		);

		_defaultAttachmentElementReplacer = Mockito.mock(
			AttachmentElementReplacer.class);

		Mockito.when(
			_defaultAttachmentElementReplacer.replace(
				Mockito.anyString(), Mockito.eq(_fileEntry))
		).thenAnswer(
			arguments -> arguments.getArgumentAt(0, String.class)
		);

		_amHTMLImageAttachmentElementReplacer =
			new AMHTMLImageAttachmentElementReplacer(
				_defaultAttachmentElementReplacer);
	}

	@Test
	public void testGetBlogsEntryAttachmentFileEntryImgTag() {
		String originalImgTag = String.format(
			"<img src=\"%s\" />", _FILE_ENTRY_IMAGE_URL);
		String expectedImgTag = String.format(
			"<img src=\"%s\" data-fileentryid=\"%s\" />", _FILE_ENTRY_IMAGE_URL,
			_IMAGE_FILE_ENTRY_ID);

		String actualTag = _amHTMLImageAttachmentElementReplacer.replace(
			originalImgTag, _fileEntry);

		Assert.assertEquals(expectedImgTag, actualTag);
	}

	@Test
	public void testGetBlogsEntryAttachmentFileEntryImgTagWithCustomAttribute() {
		String originalImgTag = String.format(
			"<img class=\"custom\" src=\"%s\" />", _FILE_ENTRY_IMAGE_URL);
		String expectedImgTag = String.format(
			"<img class=\"custom\" src=\"%s\" data-fileentryid=\"%s\" />",
			_FILE_ENTRY_IMAGE_URL, _IMAGE_FILE_ENTRY_ID);

		String actualTag = _amHTMLImageAttachmentElementReplacer.replace(
			originalImgTag, _fileEntry);

		Assert.assertEquals(expectedImgTag, actualTag);
	}

	private static final String _FILE_ENTRY_IMAGE_URL =
		RandomTestUtil.randomString();

	private static final long _IMAGE_FILE_ENTRY_ID =
		RandomTestUtil.randomLong();

	private static AMHTMLImageAttachmentElementReplacer
		_amHTMLImageAttachmentElementReplacer;
	private static AttachmentElementReplacer _defaultAttachmentElementReplacer;
	private static FileEntry _fileEntry;

}