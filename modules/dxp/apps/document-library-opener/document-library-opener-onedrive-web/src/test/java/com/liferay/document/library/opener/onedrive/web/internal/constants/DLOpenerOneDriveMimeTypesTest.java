/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal.constants;

import com.liferay.document.library.opener.constants.DLOpenerMimeTypes;
import com.liferay.portal.kernel.util.ContentTypes;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class DLOpenerOneDriveMimeTypesTest {

	@Test
	public void testGetMimeTypeExtension() {
		Assert.assertEquals(
			".doc",
			DLOpenerOneDriveMimeTypes.getMimeTypeExtension(
				ContentTypes.APPLICATION_MSWORD));

		Assert.assertEquals(
			".xls",
			DLOpenerOneDriveMimeTypes.getMimeTypeExtension(
				ContentTypes.APPLICATION_VND_MS_EXCEL));

		Assert.assertEquals(
			".ppt",
			DLOpenerOneDriveMimeTypes.getMimeTypeExtension(
				ContentTypes.APPLICATION_VND_MS_POWERPOINT));
	}

	@Test
	public void testGetOffice365MimeType() {
		Assert.assertEquals(
			DLOpenerMimeTypes.APPLICATION_VND_DOCX,
			DLOpenerOneDriveMimeTypes.getOffice365MimeType(
				ContentTypes.APPLICATION_MSWORD));

		Assert.assertEquals(
			DLOpenerMimeTypes.APPLICATION_VND_XSLX,
			DLOpenerOneDriveMimeTypes.getOffice365MimeType(
				ContentTypes.APPLICATION_VND_MS_EXCEL));

		Assert.assertEquals(
			DLOpenerMimeTypes.APPLICATION_VND_PPTX,
			DLOpenerOneDriveMimeTypes.getOffice365MimeType(
				ContentTypes.APPLICATION_VND_MS_POWERPOINT));
	}

	@Test
	public void testIsOffice365MimeTypeSupported() {
		Assert.assertTrue(
			DLOpenerOneDriveMimeTypes.isOffice365MimeTypeSupported(
				"application/msword"));
		Assert.assertTrue(
			DLOpenerOneDriveMimeTypes.isOffice365MimeTypeSupported(
				"application/vnd.openxmlformats-officedocument." +
					"wordprocessingml.document"));
		Assert.assertTrue(
			DLOpenerOneDriveMimeTypes.isOffice365MimeTypeSupported(
				"application/vnd.ms-word.document.macroEnabled.12"));
		Assert.assertTrue(
			DLOpenerOneDriveMimeTypes.isOffice365MimeTypeSupported(
				"application/vnd.openxmlformats-officedocument." +
					"wordprocessingml.template"));
		Assert.assertTrue(
			DLOpenerOneDriveMimeTypes.isOffice365MimeTypeSupported(
				"application/vnd.ms-word.template.macroEnabled.12"));
		Assert.assertTrue(
			DLOpenerOneDriveMimeTypes.isOffice365MimeTypeSupported(
				"text/html"));
		Assert.assertTrue(
			DLOpenerOneDriveMimeTypes.isOffice365MimeTypeSupported(
				"text/plain"));
		Assert.assertTrue(
			DLOpenerOneDriveMimeTypes.isOffice365MimeTypeSupported("text"));
		Assert.assertTrue(
			DLOpenerOneDriveMimeTypes.isOffice365MimeTypeSupported(
				"application/rtf"));
		Assert.assertTrue(
			DLOpenerOneDriveMimeTypes.isOffice365MimeTypeSupported(
				"application/vnd.oasis.opendocument.text"));
	}

}