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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Alan Huang
 */
public class TLDStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws DocumentException {

		_checkMissingCDATA(fileName, content);

		return content;
	}

	private void _checkMissingCDATA(String fileName, String content)
		throws DocumentException {

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		List<Element> tagElements = rootElement.elements("tag");

		for (Element tagElement : tagElements) {
			Element nameElement = tagElement.element("name");

			Element descriptionElement = tagElement.element("description");

			if (descriptionElement == null) {
				continue;
			}

			String description = descriptionElement.getText();

			int x = description.indexOf("replaced by");

			if (x == -1) {
				continue;
			}

			x = description.indexOf("<![CDATA[", x);

			if (x == -1) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Missing '<![CDATA[' after 'replaced by' in the ",
						"description of tag '", nameElement.getText(), "'"));
			}
		}
	}

}