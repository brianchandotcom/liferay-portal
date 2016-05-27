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

package com.liferay.wiki.engine.creole;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.wiki.engine.creole.internal.antlrwiki.translator.XhtmlTranslator;
import com.liferay.wiki.engine.creole.util.test.CreoleTestUtil;
import com.liferay.wiki.model.WikiPage;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Roberto Díaz
 */
@RunWith(PowerMockRunner.class)
public class XhtmlTranslatorTest {

	@BeforeClass
	public static void setUpClass() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	@Test
	public void testParseCorrectlyMultipleHeadingBlocks() {
		WikiPage page = Mockito.mock(WikiPage.class);

		Mockito.when(
			page.getTitle()
		).thenReturn(
			"test"
		);

		XhtmlTranslator xhtmlTranslator = new XhtmlTranslator();

		String translation = xhtmlTranslator.translate(
			page, null, null, null,
			CreoleTestUtil.getWikiPageNode("heading-10.creole", getClass()));

		page = Mockito.verify(page, Mockito.atLeast(1));

		page.getTitle();

		Assert.assertEquals(
			"<h1 id=\"section-test-Level+1\">Level 1</h1><h2 " +
				"id=\"section-test-Level+2\">Level 2</h2><h3 " +
					"id=\"section-test-Level+3\">Level 3</h3>",
			translation);
	}

}