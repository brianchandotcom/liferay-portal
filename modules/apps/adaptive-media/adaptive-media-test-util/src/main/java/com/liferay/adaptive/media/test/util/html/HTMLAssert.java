/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		Elements expectedChildrenElements = expectedElement.children();
		Elements actualChildrenElements = actualElement.children();

		Assert.assertEquals(
			expectedChildrenElements.size(), actualChildrenElements.size());

		for (int i = 0; i < expectedChildrenElements.size(); i++) {
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