/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.reading.time.internal.calculator;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.reading.time.calculator.ReadingTimeCalculator;

import java.time.Duration;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class ReadingTimeCalculatorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAcceptsHTMLAndText() {
		ReadingTimeCalculator readingTimeCalculator =
			new ReadingTimeCalculatorImpl();

		for (String contentType : _supportedContentTypes) {
			Duration readingTimeDuration = readingTimeCalculator.calculate(
				StringUtil.randomString(), contentType,
				LocaleUtil.getDefault());

			Assert.assertNotNull(readingTimeDuration);
		}
	}

	@Test
	public void testAdds3SecondsPerImageInHTML() {
		StringBundler sb = new StringBundler(265 + 3);

		for (int i = 0; i < 265; i++) {
			sb.append("<b>word</b>&nbsp;");
		}

		sb.append("<img src=\"img1\"/>");
		sb.append("<img src=\"img2\"/>");
		sb.append("<img src=\"img3\"/>");

		Duration readingTimeDuration = _calculateReadingTime(
			sb.toString(), "text/html", LocaleUtil.getDefault());

		Assert.assertEquals(60 + (3 * 3), readingTimeDuration.getSeconds());
	}

	@Test
	public void testCounts0ForEmptyContent() {
		Duration readingTimeDuration = _calculateReadingTime(
			StringPool.BLANK, "text/html", LocaleUtil.getDefault());

		Assert.assertEquals(0, readingTimeDuration.getSeconds());
	}

	@Test
	public void testCounts0ForNullContent() {
		Duration readingTimeDuration = _calculateReadingTime(
			null, "text/html", LocaleUtil.getDefault());

		Assert.assertEquals(0, readingTimeDuration.getSeconds());
	}

	@Test
	public void testCounts265WordsPerMinuteInHTML() {
		StringBundler sb = new StringBundler(265);

		for (int i = 0; i < 265; i++) {
			sb.append("<span class=\"random\">word</span>&nbsp;");
		}

		Duration readingTimeDuration = _calculateReadingTime(
			sb.toString(), "text/html", LocaleUtil.getDefault());

		Assert.assertEquals(60, readingTimeDuration.getSeconds());
	}

	@Test
	public void testCounts265WordsPerMinuteInText() {
		StringBundler sb = new StringBundler(265);

		for (int i = 0; i < 265; i++) {
			sb.append("word ");
		}

		Duration readingTimeDuration = _calculateReadingTime(
			sb.toString(), "text/plain", LocaleUtil.getDefault());

		Assert.assertEquals(60, readingTimeDuration.getSeconds());
	}

	@Test
	public void testDoesNotAcceptPDFs() {
		ReadingTimeCalculator readingTimeCalculator =
			new ReadingTimeCalculatorImpl();

		Duration readingTimeDuration = readingTimeCalculator.calculate(
			StringUtil.randomString(), ContentTypes.APPLICATION_PDF,
			LocaleUtil.getDefault());

		Assert.assertNull(readingTimeDuration);
	}

	private Duration _calculateReadingTime(
		String content, String contentType, Locale locale) {

		ReadingTimeCalculator readingTimeCalculator =
			new ReadingTimeCalculatorImpl();

		return readingTimeCalculator.calculate(content, contentType, locale);
	}

	private static final List<String> _supportedContentTypes = Arrays.asList(
		ContentTypes.TEXT_HTML, ContentTypes.TEXT_HTML_UTF8, ContentTypes.TEXT,
		ContentTypes.TEXT_PLAIN, ContentTypes.TEXT_PLAIN_UTF8);

}