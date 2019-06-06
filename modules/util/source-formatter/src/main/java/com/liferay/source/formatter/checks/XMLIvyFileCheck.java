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
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLIvyFileCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws DocumentException {

		if (fileName.endsWith("/ivy.xml")) {
			_checkIvyXML(fileName, content);
		}

		return content;
	}

	private void _checkIvyXML(String fileName, String content)
		throws DocumentException {

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		for (Element dependenciesElement :
				(List<Element>)rootElement.elements("dependencies")) {

			String defaultconf = dependenciesElement.attributeValue(
				"defaultconf");

			checkElementOrder(
				fileName, dependenciesElement, "dependency", defaultconf,
				new ElementComparator());
		}
	}

}