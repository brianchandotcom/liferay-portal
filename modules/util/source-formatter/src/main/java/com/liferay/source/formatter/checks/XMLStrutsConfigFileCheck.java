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

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLStrutsConfigFileCheck extends BaseFileCheck {

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith("/struts-config.xml")) {
			_checkStrutsConfigXML(fileName, content);
		}

		return content;
	}

	private void _checkStrutsConfigXML(String fileName, String content)
		throws Exception {

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		checkElementOrder(
			fileName, rootElement.element("action-mappings"), "action", null,
			new StrutsActionElementComparator("path"));
	}

	private class StrutsActionElementComparator extends ElementComparator {

		public StrutsActionElementComparator(String nameAttribute) {
			super(nameAttribute);
		}

		@Override
		public int compare(Element actionElement1, Element actionElement2) {
			String path1 = actionElement1.attributeValue("path");
			String path2 = actionElement2.attributeValue("path");

			if (!path1.startsWith("/portal/") && path2.startsWith("/portal/")) {
				return 1;
			}

			if (path1.startsWith("/portal/") && !path2.startsWith("/portal/")) {
				return -1;
			}

			return path1.compareTo(path2);
		}

	}

}