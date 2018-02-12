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

package com.liferay.portal.template;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.FileImpl;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class URLResourceParserTest {

	@Test
	public void testIsTemplateResourceValid() {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(FileImpl.getInstance());

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
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Unable to parse path //", iae.getMessage());
		}

		Assert.assertEquals(
			"abc", ClassLoaderResourceParser.normalizePath("abc/./"));
		Assert.assertEquals(
			"def", ClassLoaderResourceParser.normalizePath("abc/../def"));

		try {
			ClassLoaderResourceParser.normalizePath("../");

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Unable to parse path ../", iae.getMessage());
		}

		Assert.assertEquals(
			"/efg/hij",
			ClassLoaderResourceParser.normalizePath("/abc/../efg/./hij/"));
	}

}