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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class TLDSourceProcessor extends BaseSourceProcessor {

	@Override
	public String[] getIncludes() {
		return _INCLUDES;
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		content = trimContent(content, false);

		Matcher matcher = _typePattern.matcher(content);

		while (matcher.find()) {
			int lineCount = getLineCount(content, matcher.start());

			processErrorMessage(
				fileName,
				"Use fully qualified classType: " + fileName + " " + lineCount);
		}

		Document document = readXML(content);

		Element rootElement = document.getRootElement();

		List<Element> tagElements = rootElement.elements("tag");

		for (Element tagElement : tagElements) {
			Element nameElement = tagElement.element("name");

			checkOrder(
				fileName, tagElement, "attribute", nameElement.getText(),
				new TagElementComparator());
		}

		checkOrder(
			fileName, rootElement, "tag", null, new TagElementComparator());

		return StringUtil.replace(content, "\n\n\n", "\n\n");
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		String[] excludes = new String[] {"**/WEB-INF/tld/**", "**/test_*.tld"};

		return getFileNames(excludes, getIncludes());
	}

	private static final String[] _INCLUDES = new String[] {"**/*.tld"};

	private static final Pattern _typePattern = Pattern.compile(
		"<type>[A-Z][a-z]*</type>");

	private static class TagElementComparator extends ElementComparator {

		@Override
		protected String getElementName(Element element) {
			Element nameElement = element.element(getNameAttribute());

			return nameElement.getText();
		}

	}

}