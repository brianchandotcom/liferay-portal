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
public class XMLResourceActionsFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.contains("/resource-actions/")) {
			_checkResourceActionXML(fileName, content, "model");
			_checkResourceActionXML(fileName, content, "portlet");
		}

		return content;
	}

	private void _checkResourceActionXML(
			String fileName, String content, String type)
		throws Exception {

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		List<Element> resourceElements = rootElement.elements(
			type + "-resource");

		for (Element resourceElement : resourceElements) {
			Element nameElement = resourceElement.element(type + "-name");

			if (nameElement == null) {
				continue;
			}

			String name = nameElement.getText();

			Element permissionsElement = resourceElement.element("permissions");

			if (permissionsElement == null) {
				continue;
			}

			List<Element> permissionsChildElements =
				permissionsElement.elements();

			for (Element permissionsChildElement : permissionsChildElements) {
				checkElementOrder(
					fileName, permissionsChildElement, "action-key", name,
					new ResourceActionActionKeyElementComparator());
			}
		}

		checkElementOrder(
			fileName, rootElement, type + "-resource", null,
			new ResourceActionResourceElementComparator(type + "-name"));
	}

	private class ResourceActionActionKeyElementComparator
		extends ElementComparator {

		@Override
		public String getElementName(Element actionKeyElement) {
			return actionKeyElement.getStringValue();
		}

	}

	private class ResourceActionResourceElementComparator
		extends ElementComparator {

		public ResourceActionResourceElementComparator(String nameAttribute) {
			super(nameAttribute);
		}

		@Override
		public String getElementName(Element portletResourceElement) {
			Element portletNameElement = portletResourceElement.element(
				getNameAttribute());

			if (portletNameElement == null) {
				return null;
			}

			return portletNameElement.getText();
		}

	}

}