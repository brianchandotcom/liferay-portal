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

package com.liferay.reading.time.internal.calculator;

import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.reading.time.calculator.ReadingTimeCalculator;
import com.liferay.reading.time.calculator.ReadingTimeCalculatorImpl;

import java.time.Duration;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class ReadingTimeCalculatorImplTest {

	@Test
	public void testAcceptsLatinHTML() {
		ReadingTimeCalculator readingTimeCalculator =
			new ReadingTimeCalculatorImpl();

		for (Locale locale : _supportedLocales) {
			for (String contentType : _supportedContentTypes) {
				Optional<Duration> readingTimeOptional =
					readingTimeCalculator.calculate(
						StringUtil.randomString(), contentType, locale);

				Assert.assertTrue(
					locale.getLanguage(), readingTimeOptional.isPresent());
			}
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

		Duration readingTime = _calculateReadingTime(
			sb.toString(), "text/html", Locale.getDefault());

		Assert.assertEquals(60 + 3 * 3, readingTime.getSeconds());
	}

	@Test
	public void testCounts0ForEmptyContent() {
		Duration readingTime = _calculateReadingTime(
			StringPool.BLANK, "text/html", Locale.getDefault());

		Assert.assertEquals(0, readingTime.getSeconds());
	}

	@Test
	public void testCounts0ForNullContent() {
		Duration readingTime = _calculateReadingTime(
			null, "text/html", Locale.getDefault());

		Assert.assertEquals(0, readingTime.getSeconds());
	}

	@Test
	public void testCounts265WordsPerMinuteInHTML() {
		StringBundler sb = new StringBundler(265);

		for (int i = 0; i < 265; i++) {
			sb.append("<span class=\"random\">word</span>&nbsp;");
		}

		Duration readingTime = _calculateReadingTime(
			sb.toString(), "text/html", Locale.getDefault());

		Assert.assertEquals(60, readingTime.getSeconds());
	}

	@Test
	public void testCounts265WordsPerMinuteInText() {
		StringBundler sb = new StringBundler(265);

		for (int i = 0; i < 265; i++) {
			sb.append("word ");
		}

		Duration readingTime = _calculateReadingTime(
			sb.toString(), "text/plain", Locale.getDefault());

		Assert.assertEquals(60, readingTime.getSeconds());
	}

	@Test
	public void testDoesNotAcceptLatinPDFs() {
		ReadingTimeCalculator readingTimeCalculator =
			new ReadingTimeCalculatorImpl();

		for (Locale locale : _supportedLocales) {
			Optional<Duration> readingTimeOptional =
				readingTimeCalculator.calculate(
					StringUtil.randomString(), ContentTypes.APPLICATION_PDF,
					locale);

			Assert.assertFalse(
				locale.getLanguage(), readingTimeOptional.isPresent());
		}
	}

	@Test
	public void testDoesNotAcceptNonLatinLanguages() {
		ReadingTimeCalculator readingTimeCalculator =
			new ReadingTimeCalculatorImpl();

		for (Locale locale : _unsupportedLocales) {
			for (String contentType : _supportedContentTypes) {
				Optional<Duration> readingTimeOptional =
					readingTimeCalculator.calculate(
						StringUtil.randomString(), contentType, locale);

				Assert.assertFalse(
					locale.getLanguage(), readingTimeOptional.isPresent());
			}
		}
	}

	private Duration _calculateReadingTime(
		String content, String contentType, Locale locale) {

		ReadingTimeCalculator readingTimeCalculator =
			new ReadingTimeCalculatorImpl();

		Optional<Duration> durationOptional = readingTimeCalculator.calculate(
			content, contentType, locale);

		return durationOptional.get();
	}

	private static final List<String> _supportedContentTypes = Arrays.asList(
		ContentTypes.TEXT_HTML, ContentTypes.TEXT_HTML_UTF8, ContentTypes.TEXT,
		ContentTypes.TEXT_PLAIN, ContentTypes.TEXT_PLAIN_UTF8);
	private static final List<Locale> _supportedLocales = Arrays.asList(
		Locale.ENGLISH, Locale.US, Locale.FRENCH, Locale.GERMAN, Locale.CANADA,
		Locale.CANADA_FRENCH, Locale.ITALIAN, Locale.ITALY, Locale.UK);
	private static final List<Locale> _unsupportedLocales = Arrays.asList(
		Locale.CHINA, Locale.CHINESE, Locale.SIMPLIFIED_CHINESE,
		Locale.TRADITIONAL_CHINESE, Locale.JAPAN, Locale.JAPANESE, Locale.KOREA,
		Locale.KOREAN, Locale.TAIWAN);

}