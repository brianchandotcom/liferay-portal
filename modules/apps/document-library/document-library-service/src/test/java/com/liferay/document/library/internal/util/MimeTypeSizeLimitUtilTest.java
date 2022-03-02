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
	public void testParseValidMimeTypeSizeLimitTest() {
		MimeTypeSizeLimitUtil.parseMimeTypeSizeLimit(
			"  image/png  :  12345  ",
			(mimeType, sizeLimit) -> {
				Assert.assertEquals("image/png", mimeType);
				Assert.assertEquals(12345, sizeLimit.intValue());
			});
	}

}