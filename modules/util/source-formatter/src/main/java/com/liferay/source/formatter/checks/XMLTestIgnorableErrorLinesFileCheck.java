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

import com.liferay.source.formatter.checks.comparator.ElementComparator;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLTestIgnorableErrorLinesFileCheck extends BaseFileCheck {

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith("/test-ignorable-error-lines.xml")) {
			_checkTestIgnorableErrorLinesXml(fileName, content);
		}

		return content;
	}

	private void _checkTestIgnorableErrorLinesXml(
			String fileName, String content)
		throws Exception {

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		List<Element> javascriptElements = rootElement.elements("javascript");

		for (Element javascriptElement : javascriptElements) {
			checkElementOrder(
				fileName, javascriptElement, "ignore-error", null,
				new ElementComparator("description"));
		}

		List<Element> logElements = rootElement.elements("log");

		for (Element logElement : logElements) {
			checkElementOrder(
				fileName, logElement, "ignore-error", null,
				new ElementComparator("description"));
		}
	}

}