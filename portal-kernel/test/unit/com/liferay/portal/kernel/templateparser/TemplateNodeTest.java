/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.templateparser;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Attila Bakay
 */
public class TemplateNodeTest {

	@Test
	public void testGetDataNumericFormatsAcrossLocales() {
		_testGetDataNumeric("123.456", LocaleUtil.US, "123.456");
		_testGetDataNumeric("123.456", LocaleUtil.SPAIN, "123,456");
		_testGetDataNumeric("123.456", LocaleUtil.GERMANY, "123,456");
		_testGetDataNumeric("123.456", LocaleUtil.FRANCE, "123,456");
	}

	@Test
	public void testGetDataNumericParsesJavaCanonicalDataWithSiteLocale() {
		_testGetDataNumeric("20.3", LocaleUtil.SPAIN, LocaleUtil.SPAIN, "20,3");
		_testGetDataNumeric(
			"20.3", LocaleUtil.SPAIN, LocaleUtil.GERMANY, "20,3");
		_testGetDataNumeric("20.3", LocaleUtil.SPAIN, LocaleUtil.US, "20.3");
	}

	@Test
	public void testGetDataNumericPreservesNegativeNumbers() {
		_testGetDataNumeric("-123.45", LocaleUtil.US, "-123.45");
		_testGetDataNumeric("-123.45", LocaleUtil.SPAIN, "-123,45");
		_testGetDataNumeric("-123.45", LocaleUtil.GERMANY, "-123,45");
		_testGetDataNumeric("-123.45", LocaleUtil.FRANCE, "-123,45");
	}

	@Test
	public void testGetDataNumericPreservesSmallFractions() {
		_testGetDataNumeric("0.0000012345", LocaleUtil.US, "0.0000012345");
		_testGetDataNumeric("0.0000012345", LocaleUtil.SPAIN, "0,0000012345");
	}

	@Test
	public void testGetDataNumericSupportsAllNumericTypes() {
		for (String type :
				new String[] {"numeric", "ddm-decimal", "ddm-number"}) {

			ThemeDisplay themeDisplay = new ThemeDisplay();

			themeDisplay.setSiteDefaultLocale(LocaleUtil.US);

			TemplateNode templateNode = new TemplateNode(
				LocaleUtil.SPAIN, themeDisplay, "field", "123.456", type,
				Collections.emptyMap());

			Assert.assertEquals("123,456", templateNode.getData());
		}
	}

	private void _testGetDataNumeric(
		String data, Locale siteDefaultLocale, Locale locale, String expected) {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setSiteDefaultLocale(siteDefaultLocale);

		TemplateNode templateNode = new TemplateNode(
			locale, themeDisplay, "field", data, "numeric",
			Collections.emptyMap());

		Assert.assertEquals(expected, templateNode.getData());
	}

	private void _testGetDataNumeric(
		String data, Locale locale, String expected) {

		_testGetDataNumeric(data, LocaleUtil.US, locale, expected);
	}

}