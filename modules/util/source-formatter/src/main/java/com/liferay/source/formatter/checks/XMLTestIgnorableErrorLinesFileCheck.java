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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.ElementComparator;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLTestIgnorableErrorLinesFileCheck extends BaseFileCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		if (fileName.endsWith("/test-ignorable-error-lines.xml")) {
			_checkTestIgnorableErrorLinesXml(
				sourceFormatterMessages, fileName, content);
		}

		return new Tuple(content, sourceFormatterMessages);
	}

	private void _checkTestIgnorableErrorLinesXml(
			Set<SourceFormatterMessage> sourceFormatterMessages,
			String fileName, String content)
		throws Exception {

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		List<Element> javascriptElements = rootElement.elements("javascript");

		for (Element javascriptElement : javascriptElements) {
			checkElementOrder(
				sourceFormatterMessages, fileName, javascriptElement,
				"ignore-error", null, new ElementComparator("description"));
		}

		List<Element> logElements = rootElement.elements("log");

		for (Element logElement : logElements) {
			checkElementOrder(
				sourceFormatterMessages, fileName, logElement, "ignore-error",
				null, new ElementComparator("description"));
		}
	}

}