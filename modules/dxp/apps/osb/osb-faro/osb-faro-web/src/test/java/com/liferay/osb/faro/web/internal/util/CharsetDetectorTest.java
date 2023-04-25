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

package com.liferay.osb.faro.web.internal.util;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alejo Ceballos
 */
public class CharsetDetectorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testReadUS_ASCII() throws IOException {
		Assert.assertEquals(
			StandardCharsets.US_ASCII,
			_charsetDetector.detect(_getFile("charset_us-ascii.csv")));
	}

	@Test
	public void testReadWINDOWS_1252() throws IOException {
		Assert.assertEquals(
			Charset.forName("windows-1252"),
			_charsetDetector.detect(_getFile("charset_windows-1252.csv")));
	}

	private File _getFile(String fileName) {
		URL resourceURL = Objects.requireNonNull(
			_classLoader.getResource(fileName));

		return new File(resourceURL.getFile());
	}

	private final CharsetDetector _charsetDetector = new CharsetDetector();
	private final ClassLoader _classLoader = getClass().getClassLoader();

}