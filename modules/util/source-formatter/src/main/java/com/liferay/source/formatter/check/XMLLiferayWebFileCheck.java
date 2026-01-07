/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.io.IOException;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Alan Huang
 */
public class XMLLiferayWebFileCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith("portal-web/docroot/WEB-INF/liferay-web.xml")) {
			return content;
		}

		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return null;
		}

		String filterName = StringPool.BLANK;

		Element rootElement = document.getRootElement();

		List<Element> filterElements = rootElement.elements("filter");

		for (Element filterElement : filterElements) {
			Element filterClassElement = filterElement.element("filter-class");

			if (filterClassElement == null) {
				continue;
			}

			String filterClass = filterClassElement.getText();

			if (!filterClass.equals(
					"com.liferay.portal.servlet.filters.portal.instances." +
						"PortalInstancesFilter")) {

				continue;
			}

			Element filterNameElement = filterElement.element("filter-name");

			if (filterNameElement != null) {
				filterName = filterNameElement.getText();

				break;
			}

			return content;
		}

		if (filterName.equals(StringPool.BLANK)) {
			return content;
		}

		List<Element> dispatcherElements = null;

		List<Element> filterMappingElements = rootElement.elements(
			"filter-mapping");

		for (int i = 0; i < filterMappingElements.size(); i++) {
			Element filterMappingElement = filterMappingElements.get(i);

			Element filterNameElement = filterMappingElement.element(
				"filter-name");

			if ((filterNameElement == null) ||
				!StringUtil.equals(filterName, filterNameElement.getText())) {

				continue;
			}

			if (i == 0) {
				dispatcherElements = filterMappingElement.elements(
					"dispatcher");

				break;
			}

			addMessage(
				fileName,
				StringBundler.concat(
					"Filter mapping for \"", filterName,
					"\" must be in the first place before the execution of ",
					"any other filter"));

			return content;
		}

		if (dispatcherElements == null) {
			addMessage(
				fileName,
				"Missing \"<filter-mapping>\" for \"" + filterName + "\"");

			return content;
		}

		String[] dispatcherNames = TransformUtil.transformToArray(
			dispatcherElements,
			dispatcherElement -> dispatcherElement.getText(), String.class);

		if (!ArrayUtil.contains(dispatcherNames, "ERROR")) {
			addMessage(
				fileName,
				"Missing \"<dispatcher>ERROR</dispatcher>\" for \"" +
					filterName + "\"");
		}

		if (!ArrayUtil.contains(dispatcherNames, "REQUEST")) {
			addMessage(
				fileName,
				"Missing \"<dispatcher>REQUEST</dispatcher>\" for \"" +
					filterName + "\"");
		}

		return content;
	}

}