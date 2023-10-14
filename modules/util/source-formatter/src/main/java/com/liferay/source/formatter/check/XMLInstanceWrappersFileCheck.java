/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.source.formatter.check.comparator.ElementComparator;
import com.liferay.source.formatter.check.comparator.PropertyValueComparator;
import com.liferay.source.formatter.check.util.SourceUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Alan Huang
 */
public class XMLInstanceWrappersFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws DocumentException {

		if (fileName.endsWith("/instance_wrappers.xml")) {
			_checkOrder(fileName, content);
		}

		return content;
	}

	private void _checkOrder(String fileName, String content) {
		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return;
		}

		checkElementOrder(
			fileName, document.getRootElement(), "instance-wrapper", null,
			new InstanceWrapperElementComparator());
	}

	private class InstanceWrapperElementComparator extends ElementComparator {

		@Override
		public int compare(Element element1, Element element2) {
			String parentDir1 = element1.attributeValue("parent-dir");
			String parentDir2 = element2.attributeValue("parent-dir");

			PropertyValueComparator propertyValueComparator =
				new PropertyValueComparator();

			if (!parentDir1.equals(parentDir2)) {
				return propertyValueComparator.compare(parentDir1, parentDir2);
			}

			String srcFile1 = element1.attributeValue("src-file");
			String srcFile2 = element2.attributeValue("src-file");

			return propertyValueComparator.compare(srcFile1, srcFile2);
		}

		@Override
		public String getElementName(Element element) {
			return StringBundler.concat(
				"{parent-dir=", element.attributeValue("parent-dir"),
				", src-file=", element.attributeValue("src-file"), "}");
		}

	}

}