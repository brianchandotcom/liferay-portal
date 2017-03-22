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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.checks.util.XMLSourceUtil;
import com.liferay.util.xml.Dom4jUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLPortletFileCheck extends BaseFileCheck {

	public XMLPortletFileCheck(
		List<String> excludes, boolean portalSource, boolean subrepository) {

		_excludes = excludes;
		_portalSource = portalSource;
		_subrepository = subrepository;
	}

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		if (fileName.endsWith("/liferay-portlet.xml") ||
			((_portalSource || _subrepository) &&
			 fileName.endsWith("/portlet-custom.xml")) ||
			(!_portalSource && !_subrepository &&
			 fileName.endsWith("/portlet.xml"))) {

			content = _formatPortletXML(
				sourceFormatterMessages, fileName, absolutePath, content);
		}

		return new Tuple(content, sourceFormatterMessages);
	}

	private String _formatPortletXML(
			Set<SourceFormatterMessage> sourceFormatterMessages,
			String fileName, String absolutePath, String content)
		throws Exception {

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		boolean checkNumericalPortletNameElement = !isExcludedPath(
			_excludes, absolutePath);

		List<Element> portletElements = rootElement.elements("portlet");

		for (Element portletElement : portletElements) {
			if (checkNumericalPortletNameElement) {
				Element portletNameElement = portletElement.element(
					"portlet-name");

				String portletNameText = portletNameElement.getText();

				if (!Validator.isNumber(portletNameText)) {
					addMessage(
						sourceFormatterMessages, fileName,
						"Nonstandard portlet-name element '" + portletNameText +
							"'");
				}
			}

			if (fileName.endsWith("/liferay-portlet.xml")) {
				continue;
			}

			XMLSourceUtil.sortElementsByChildElement(
				portletElement, "init-param", "name");

			Element portletPreferencesElement = portletElement.element(
				"portlet-preferences");

			if (portletPreferencesElement != null) {
				XMLSourceUtil.sortElementsByChildElement(
					portletPreferencesElement, "preference", "name");
			}
		}

		return Dom4jUtil.toString(document);
	}

	private final List<String> _excludes;
	private final boolean _portalSource;
	private final boolean _subrepository;

}