/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.util;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class MimeTypeSizeLimitUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testParseInvalidInput() {
		MimeTypeSizeLimitUtil.parseMimeTypeSizeLimit(
			RandomTestUtil.randomString(),
			(mimeType, sizeLimit) -> {
				Assert.assertNull(mimeType);
				Assert.assertNull(sizeLimit);
			});
	}

	@Test
	public void testParseInvalidMimeType() {
		MimeTypeSizeLimitUtil.parseMimeTypeSizeLimit(
			"type:12345", (mimeType, sizeLimit) -> Assert.assertNull(mimeType));
	}

	@Test
	public void testParseInvalidSizeLimit() {
		MimeTypeSizeLimitUtil.parseMimeTypeSizeLimit(
			"image/png:" + RandomTestUtil.randomString(),
			(mimeType, sizeLimit) -> Assert.assertNull(sizeLimit));
	}

	@Test
	public void testParseNullInput() {
		MimeTypeSizeLimitUtil.parseMimeTypeSizeLimit(
			null,
			(mimeType, sizeLimit) -> {
				Assert.assertNull(mimeType);
				Assert.assertNull(sizeLimit);
			});
	}

	@Test
	public void testParseValidMimeTypeNamePattern() {
		for (char specialCharacter : _SPECIAL_CHARACTERS.toCharArray()) {
			String specialCharacterMimeType =
				"image/png" + specialCharacter + "xml";

			MimeTypeSizeLimitUtil.parseMimeTypeSizeLimit(
				specialCharacterMimeType + ":12345",
				(mimeType, sizeLimit) -> Assert.assertEquals(
					specialCharacterMimeType, mimeType));
		}
	}

	@Test
	public void testParseValidMimeTypeSizeLimitTest() {
		MimeTypeSizeLimitUtil.parseMimeTypeSizeLimit(
			"  image/png  :  12345  ",
			(mimeType, sizeLimit) -> {
				Assert.assertEquals("image/png", mimeType);
				Assert.assertEquals(12345, sizeLimit.intValue());
			});
	}

	@Test
	public void testParseWildcardMimeType() {
		MimeTypeSizeLimitUtil.parseMimeTypeSizeLimit(
			"type/*:12345",
			(mimeType, sizeLimit) -> Assert.assertEquals("type/*", mimeType));
	}

	private static final String _SPECIAL_CHARACTERS = "$!#&.,;=^_+-";

}