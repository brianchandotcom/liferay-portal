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
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLStrutsConfigFileCheck extends BaseFileCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		if (fileName.endsWith("/struts-config.xml")) {
			_checkStrutsConfigXML(sourceFormatterMessages, fileName, content);
		}

		return new Tuple(content, sourceFormatterMessages);
	}

	private void _checkStrutsConfigXML(
			Set<SourceFormatterMessage> sourceFormatterMessages,
			String fileName, String content)
		throws Exception {

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		checkElementOrder(
			sourceFormatterMessages, fileName,
			rootElement.element("action-mappings"), "action", null,
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