/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.web.internal.upload;

import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.message.boards.service.MBMessageService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Roberto Díaz
 */
public class BaseMBUploadFileEntryHandlerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test(expected = FileExtensionException.InvalidExtension.class)
	public void testUploadValidatesFileExtension() throws Exception {
		Mockito.doThrow(
			FileExtensionException.InvalidExtension.class
		).when(
			_dlValidator
		).validateFileExtension(
			Mockito.anyString()
		);

		TestMBUploadFileEntryHandler testMBUploadFileEntryHandler =
			new TestMBUploadFileEntryHandler(_dlValidator, _mbMessageService);

		Mockito.when(
			_uploadPortletRequest.getFileName(Mockito.anyString())
		).thenReturn(
			RandomTestUtil.randomString()
		);

		testMBUploadFileEntryHandler.upload(_uploadPortletRequest);
	}

	private final DLValidator _dlValidator = Mockito.mock(DLValidator.class);
	private final MBMessageService _mbMessageService = Mockito.mock(
		MBMessageService.class);
	private final UploadPortletRequest _uploadPortletRequest = Mockito.mock(
		UploadPortletRequest.class);

	private static class TestMBUploadFileEntryHandler
		extends BaseMBUploadFileEntryHandler {

		public TestMBUploadFileEntryHandler(
			DLValidator dlValidator, MBMessageService mbMessageService) {

			super(dlValidator, mbMessageService);
		}

		@Override
		protected String getParameterName() {
			return RandomTestUtil.randomString();
		}

	}

}