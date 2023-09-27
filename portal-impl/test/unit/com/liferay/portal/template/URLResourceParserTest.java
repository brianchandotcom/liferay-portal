/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.URL;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class URLResourceParserTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testIsTemplateResourceValid() {
		URLResourceParser urlResourceParser = new URLResourceParser() {

			@Override
			public URL getURL(String templateId) {
				return null;
			}

		};

		for (String langType : TemplateConstants.ALLOWED_LANG_TYPES) {
			Assert.assertTrue(
				urlResourceParser.isTemplateResourceValid(
					"_SEPARATOR_/template." + langType, langType));
			Assert.assertFalse(
				urlResourceParser.isTemplateResourceValid(
					"portal-ext.properties", langType));
		}

		Assert.assertTrue(
			urlResourceParser.isTemplateResourceValid(
				"_SEPARATOR_/template.custom", "custom"));
		Assert.assertFalse(
			urlResourceParser.isTemplateResourceValid(
				"..\\file", StringPool.BLANK));
		Assert.assertFalse(
			urlResourceParser.isTemplateResourceValid(
				"../\\file", StringPool.BLANK));
		Assert.assertFalse(
			urlResourceParser.isTemplateResourceValid(
				"..\\/file", StringPool.BLANK));
		Assert.assertFalse(
			urlResourceParser.isTemplateResourceValid(
				"\\..\\file", StringPool.BLANK));
		Assert.assertFalse(
			urlResourceParser.isTemplateResourceValid(
				"/..\\file", StringPool.BLANK));
		Assert.assertFalse(
			urlResourceParser.isTemplateResourceValid(
				"\\../\\file", StringPool.BLANK));
		Assert.assertFalse(
			urlResourceParser.isTemplateResourceValid(
				"\\..\\/file", StringPool.BLANK));
		Assert.assertFalse(
			urlResourceParser.isTemplateResourceValid(
				"%2f..%2ffile", StringPool.BLANK));
		Assert.assertFalse(
			urlResourceParser.isTemplateResourceValid(
				"/file?a=.ftl", StringPool.BLANK));
		Assert.assertFalse(
			urlResourceParser.isTemplateResourceValid(
				"/file#a=.ftl", StringPool.BLANK));
		Assert.assertFalse(
			urlResourceParser.isTemplateResourceValid(
				"/file;a=.ftl", StringPool.BLANK));
	}

	@Test
	public void testNormalizePath() {
		Assert.assertEquals(
			"abc", ClassLoaderResourceParser.normalizePath("abc"));
		Assert.assertEquals(
			"/abc", ClassLoaderResourceParser.normalizePath("/abc"));

		try {
			ClassLoaderResourceParser.normalizePath("//");

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"Unable to parse path //",
				illegalArgumentException.getMessage());
		}

		Assert.assertEquals(
			"abc", ClassLoaderResourceParser.normalizePath("abc/./"));
		Assert.assertEquals(
			"def", ClassLoaderResourceParser.normalizePath("abc/../def"));

		try {
			ClassLoaderResourceParser.normalizePath("../");

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"Unable to parse path ../",
				illegalArgumentException.getMessage());
		}

		Assert.assertEquals(
			"/efg/hij",
			ClassLoaderResourceParser.normalizePath("/abc/../efg/./hij/"));
	}

}