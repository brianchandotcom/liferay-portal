/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.web.internal.upload.format.handlers;

import com.liferay.message.boards.web.internal.util.MBAttachmentFileEntryReference;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Tardín
 */
public class MBMessageHTMLFormatUploadHandlerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_mbMessageHTMLFormatUploadHandler, "_portletFileRepository",
			_portletFileRepository);
	}

	@Test
	public void testDoesNotReplaceImageReferencesWithoutDataImageIdAttribute() {
		List<MBAttachmentFileEntryReference> fileEntryReferences =
			new ArrayList<>();

		FileEntry fileEntry = Mockito.mock(FileEntry.class);
		long tempFileId = 1;

		String originalContent = "<img src=\"http://random\"/>";

		String finalURL = "http://final";

		Mockito.doReturn(
			finalURL
		).when(
			_portletFileRepository
		).getPortletFileEntryURL(
			Mockito.nullable(ThemeDisplay.class), Mockito.eq(fileEntry),
			Mockito.eq(StringPool.BLANK)
		);

		fileEntryReferences.add(
			new MBAttachmentFileEntryReference(tempFileId, fileEntry));

		String finalContent =
			_mbMessageHTMLFormatUploadHandler.replaceImageReferences(
				originalContent, fileEntryReferences);

		Assert.assertEquals(originalContent, finalContent);
	}

	@Test
	public void testReplaceASingleImageReference() {
		List<MBAttachmentFileEntryReference> fileEntryReferences =
			new ArrayList<>();

		FileEntry fileEntry = Mockito.mock(FileEntry.class);

		long tempFileId = 1;

		String originalContent = String.format(
			"<img data-image-id=\"%d\" src=\"%s\"/>", tempFileId,
			"http://temporal");

		String finalURL = "http://final";

		Mockito.doReturn(
			finalURL
		).when(
			_portletFileRepository
		).getPortletFileEntryURL(
			Mockito.nullable(ThemeDisplay.class), Mockito.eq(fileEntry),
			Mockito.eq(StringPool.BLANK)
		);

		fileEntryReferences.add(
			new MBAttachmentFileEntryReference(tempFileId, fileEntry));

		String finalContent =
			_mbMessageHTMLFormatUploadHandler.replaceImageReferences(
				originalContent, fileEntryReferences);

		Assert.assertEquals("<img src=\"" + finalURL + "\" />", finalContent);
	}

	@Test
	public void testReplaceSeveralImageReferences() {
		List<MBAttachmentFileEntryReference> fileEntryReferences =
			new ArrayList<>();

		StringBundler originalContentSB = new StringBundler();
		StringBundler expectedContentSB = new StringBundler();

		for (int tempFileId = 0; tempFileId < 3; tempFileId++) {
			FileEntry fileEntry = Mockito.mock(FileEntry.class);

			String finalURL = "http://final-" + tempFileId;

			String curOriginalContent = String.format(
				"<img data-image-id=\"%d\" src=\"%s\"/>", tempFileId,
				"http://temporal-" + tempFileId);

			Mockito.doReturn(
				finalURL
			).when(
				_portletFileRepository
			).getPortletFileEntryURL(
				Mockito.nullable(ThemeDisplay.class), Mockito.eq(fileEntry),
				Mockito.eq(StringPool.BLANK)
			);

			fileEntryReferences.add(
				new MBAttachmentFileEntryReference(tempFileId, fileEntry));

			originalContentSB.append(curOriginalContent);

			expectedContentSB.append("<img src=\"");
			expectedContentSB.append(finalURL);
			expectedContentSB.append("\" />");
		}

		String finalContent =
			_mbMessageHTMLFormatUploadHandler.replaceImageReferences(
				originalContentSB.toString(), fileEntryReferences);

		Assert.assertEquals(expectedContentSB.toString(), finalContent);
	}

	private final MBMessageHTMLFormatUploadHandler
		_mbMessageHTMLFormatUploadHandler =
			new MBMessageHTMLFormatUploadHandler();
	private final PortletFileRepository _portletFileRepository = Mockito.mock(
		PortletFileRepository.class);

}