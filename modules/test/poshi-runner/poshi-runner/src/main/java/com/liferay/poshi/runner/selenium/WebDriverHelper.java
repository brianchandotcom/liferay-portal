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

package com.liferay.poshi.runner.selenium;

import com.liferay.poshi.runner.util.FileUtil;
import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.StringUtil;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.openqa.selenium.By;

/**
 * @author Kenji Heigel
 */
public class WebDriverHelper {

	public static By getBy(String locator) {
		if (locator.startsWith("//")) {
			return By.xpath(locator);
		}
		else if (locator.startsWith("class=")) {
			locator = locator.substring(6);

			return By.className(locator);
		}
		else if (locator.startsWith("css=")) {
			locator = locator.substring(4);

			return By.cssSelector(locator);
		}
		else if (locator.startsWith("link=")) {
			locator = locator.substring(5);

			return By.linkText(locator);
		}
		else if (locator.startsWith("name=")) {
			locator = locator.substring(5);

			return By.name(locator);
		}
		else if (locator.startsWith("tag=")) {
			locator = locator.substring(4);

			return By.tagName(locator);
		}
		else if (locator.startsWith("xpath=") || locator.startsWith("xPath=")) {
			locator = locator.substring(6);

			return By.xpath(locator);
		}
		else {
			return By.id(locator);
		}
	}

	public static String getCSSSource(String htmlSource) throws Exception {
		Document htmlDocument = Jsoup.parse(htmlSource);

		Elements elements = htmlDocument.select("link[type=text/css]");

		StringBuilder sb = new StringBuilder();

		for (Element element : elements) {
			String href = element.attr("href");

			if (!href.contains(PropsValues.PORTAL_URL)) {
				href = PropsValues.PORTAL_URL + href;
			}

			Connection connection = Jsoup.connect(href);

			Document document = connection.get();

			sb.append(document.text());

			sb.append("\n");
		}

		return sb.toString();
	}

	public static void saveWebPage(String fileName, String htmlSource)
		throws Exception {

		if (!PropsValues.SAVE_WEB_PAGE) {
			return;
		}

		StringBuilder sb = new StringBuilder(3);

		sb.append("<style>");
		sb.append(getCSSSource(htmlSource));
		sb.append("</style></html>");

		FileUtil.write(
			fileName,
			StringUtil.replace(htmlSource, "<\\html>", sb.toString()));
	}

}