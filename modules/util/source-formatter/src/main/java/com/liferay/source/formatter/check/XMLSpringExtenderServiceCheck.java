/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Alan Huang
 */
public class XMLSpringExtenderServiceCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.contains("/modules/apps/") &&
			!absolutePath.contains("/modules/dxp/apps/")) {

			return content;
		}

		List<String> allowedSpringExtenderServiceDirNames = getAttributeValues(
			_ALLOWED_SPRING_EXTENDER_SERVICE_DIR_NAMES_KEY, absolutePath);

		for (String allowedSpringExtenderServiceDirName :
				allowedSpringExtenderServiceDirNames) {

			if (absolutePath.contains(allowedSpringExtenderServiceDirName)) {
				return content;
			}
		}

		int x = absolutePath.indexOf("/modules/apps/archived/");

		if (x != -1) {
			return content;
		}

		if (fileName.endsWith(
				"-service/src/main/resources/META-INF/spring/module-spring." +
					"xml")) {

			addMessage(
				fileName,
				"Do not use Spring extender service as a dependency " +
					"injection, use DS instead");
		}
		else if (fileName.endsWith("/service.xml")) {
			Document document = SourceUtil.readXML(content);

			if (document == null) {
				return content;
			}

			Element rootElement = document.getRootElement();

			String dependencyInjector = rootElement.attributeValue(
				"dependency-injector");

			if (Validator.isNotNull(dependencyInjector) &&
				dependencyInjector.equals("spring")) {

				addMessage(
					fileName,
					"Do not use Spring extender service as a dependency " +
						"injection, use DS instead");
			}
		}

		return content;
	}

	private static final String _ALLOWED_SPRING_EXTENDER_SERVICE_DIR_NAMES_KEY =
		"allowedSpringExtenderServiceDirNames";

}