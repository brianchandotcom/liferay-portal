/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.Objects;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Kyle Miho
 */
public class XMLUpgradeDeclarativeServicesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith("/service.xml")) {
			return content;
		}

		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return content;
		}

		Element rootElement = document.getRootElement();

		Attribute attribute = rootElement.attribute("dependency-injector");

		if (attribute == null) {
			return content.replaceFirst(
				"(<service-builder)", "$1 dependency-injector=\"ds\"");
		}

		String dependencyInjector = attribute.getValue();

		if (!Objects.equals(dependencyInjector, "ds")) {
			return content.replaceFirst(
				"dependency-injector\\s*=\\s*\".*?\"",
				"dependency-injector=\"ds\"");
		}

		return content;
	}

}