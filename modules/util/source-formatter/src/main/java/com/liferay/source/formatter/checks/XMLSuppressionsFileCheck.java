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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.source.formatter.checks.comparator.ElementComparator;
import com.liferay.source.formatter.checks.util.SourceUtil;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLSuppressionsFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith("-suppressions.xml")) {
			return content;
		}

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		SuppressElementComparator suppressElementComparator =
			new SuppressElementComparator();

		checkElementOrder(
			fileName, rootElement, "suppress", null, suppressElementComparator);

		checkElementOrder(
			fileName, rootElement.element("checkstyle"), "suppress",
			"checkstyle", suppressElementComparator);
		checkElementOrder(
			fileName, rootElement.element("source-check"), "suppress",
			"source-check", suppressElementComparator);

		return content;
	}

	private class SuppressElementComparator extends ElementComparator {

		@Override
		public String getElementName(Element suppressElement) {
			StringBundler sb = new StringBundler(3);

			sb.append(suppressElement.attributeValue("checks"));
			sb.append(StringPool.POUND);
			sb.append(suppressElement.attributeValue("files"));

			return sb.toString();
		}

	}

}