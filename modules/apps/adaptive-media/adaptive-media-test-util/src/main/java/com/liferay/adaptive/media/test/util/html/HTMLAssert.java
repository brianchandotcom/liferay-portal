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

package com.liferay.adaptive.media.test.util.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.junit.Assert;

/**
 * @author Alejandro Tardín
 */
public class HTMLAssert {

	public static void assertHTMLEquals(
		String expectedHtml, String actualHtml) {

		_assertEquals(_parseBody(expectedHtml), _parseBody(actualHtml));
	}

	private static void _assertEquals(
		Element expectedElement, Element actualElement) {

		Assert.assertEquals(expectedElement.tagName(), actualElement.tagName());
		Assert.assertEquals(
			expectedElement.attributes(), actualElement.attributes());

		Elements expectedChildren = expectedElement.children();
		Elements actualChildren = actualElement.children();

		Assert.assertEquals(expectedChildren.size(), actualChildren.size());

		for (int i = 0; i < expectedChildren.size(); i++) {
			_assertEquals(expectedElement.child(i), actualElement.child(i));
		}
	}

	private static Element _parseBody(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(true);
		outputSettings.syntax(Document.OutputSettings.Syntax.xml);

		document.outputSettings(outputSettings);

		return document.body();
	}

}