/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.onedrive.web.internal.constants;

import com.liferay.document.library.opener.constants.DLOpenerMimeTypes;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class DLOpenerOneDriveMimeTypesTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetMimeTypeExtension() {
		Assert.assertEquals(
			".docx",
			DLOpenerOneDriveMimeTypes.getMimeTypeExtension(
				DLOpenerMimeTypes.APPLICATION_VND_DOCX));
		Assert.assertEquals(
			".pptx",
			DLOpenerOneDriveMimeTypes.getMimeTypeExtension(
				DLOpenerMimeTypes.APPLICATION_VND_PPTX));
		Assert.assertEquals(
			".xlsx",
			DLOpenerOneDriveMimeTypes.getMimeTypeExtension(
				DLOpenerMimeTypes.APPLICATION_VND_XLSX));
	}

	@Test
	public void testGetOffice365MimeType() {
		Assert.assertEquals(
			DLOpenerMimeTypes.APPLICATION_VND_DOCX,
			DLOpenerOneDriveMimeTypes.getOffice365MimeType(
				DLOpenerMimeTypes.APPLICATION_VND_DOCX));
		Assert.assertEquals(
			DLOpenerMimeTypes.APPLICATION_VND_PPTX,
			DLOpenerOneDriveMimeTypes.getOffice365MimeType(
				DLOpenerMimeTypes.APPLICATION_VND_PPTX));
		Assert.assertEquals(
			DLOpenerMimeTypes.APPLICATION_VND_XLSX,
			DLOpenerOneDriveMimeTypes.getOffice365MimeType(
				DLOpenerMimeTypes.APPLICATION_VND_XLSX));
	}

}