/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import java.util.List;
import java.util.Properties;

/**
 * @author Alan Huang
 */
public class PropertiesPlaywrightTestCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (!fileName.endsWith("/test.properties")) {
			return content;
		}

		if (absolutePath.contains("/modules/test/playwright/tests/")) {
			Properties properties = new Properties();

			properties.load(new StringReader(content));

			String testrayMainComponentName = properties.getProperty(
				_TESTRAY_MAIN_COMPONENT_NAME);

			if (testrayMainComponentName == null) {
				addMessage(
					fileName,
					"Missing property '" + _TESTRAY_MAIN_COMPONENT_NAME +
						"' in test.properties");
			}

			return content;
		}

		if (absolutePath.contains("/modules/apps/")) {
			String moduleName = _getModuleName(absolutePath);

			File file = new File(
				getPortalDir() + "/modules/test/playwright/tests/" +
					moduleName);

			if (!file.exists()) {
				return content;
			}

			file = new File(file, "test.properties");

			if (!file.exists()) {
				addMessage(
					fileName,
					"Missing test.properties in playwright/tests/" +
						moduleName);

				return content;
			}

			Properties properties = new Properties();

			properties.load(new StringReader(content));

			List<String> playwrightTestProjectList = ListUtil.fromString(
				properties.getProperty(_PLAYWRIGHT_TEST_PROJECT_NAME),
				StringPool.COMMA);

			if (ListUtil.isEmpty(playwrightTestProjectList)) {
				addMessage(
					fileName,
					"Missing property '" + _PLAYWRIGHT_TEST_PROJECT_NAME +
						"' in test.properties");
			}
			else if (!playwrightTestProjectList.contains(moduleName)) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Missing property value '", moduleName, "' in '",
						_PLAYWRIGHT_TEST_PROJECT_NAME, "'"));
			}
		}

		return content;
	}

	private String _getModuleName(String absolutePath) {
		int x = absolutePath.lastIndexOf(StringPool.SLASH);

		int y = absolutePath.lastIndexOf(StringPool.SLASH, x - 1);

		return absolutePath.substring(y + 1, x);
	}

	private static final String _PLAYWRIGHT_TEST_PROJECT_NAME =
		"playwright.test.project[playwright-js-tomcat90-mysql57-jdk8]" +
			"[relevant]";

	private static final String _TESTRAY_MAIN_COMPONENT_NAME =
		"testray.main.component.name";

}