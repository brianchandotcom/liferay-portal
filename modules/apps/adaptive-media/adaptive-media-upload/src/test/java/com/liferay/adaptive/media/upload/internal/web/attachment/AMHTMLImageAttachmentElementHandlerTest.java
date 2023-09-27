/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			arguments -> arguments.getArgument(0, String.class)
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