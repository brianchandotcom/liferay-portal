/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
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

				return content;
			}

			List<String> testrayAllTeamsComponentNames =
				_getTestrayAllTeamsComponentNames();

			if (!testrayAllTeamsComponentNames.contains(
					testrayMainComponentName)) {

				addMessage(
					fileName,
					StringBundler.concat(
						"Property value '", testrayMainComponentName,
						"' does not exist in 'testray.team.*.component.names' ",
						"in ", SourceUtil.getRootDirName(absolutePath),
						"/test.properties"));
			}

			File portalDir = getPortalDir();
			String moduleName = _getModuleName(absolutePath);

			List<String> buildGradleFileNames = SourceFormatterUtil.scanForFileNames(
				portalDir.getCanonicalPath(),
				new String[] {
					"modules/apps/**/" + moduleName + "/build.gradle",
					"modules/dxp/apps/**/" + moduleName + "/build.gradle"
				});

			if (ListUtil.isEmpty(buildGradleFileNames) || buildGradleFileNames.size() != 1) {
				return content;
			}

			List<String> fileNames = new ArrayList<>();
			String moduleRootDirLocation = "modules/";

			for (int i = 0; i < getMaxDirLevel(); i++) {
				File file = new File(getBaseDirName() + moduleRootDirLocation);

				if (file.exists()) {
					fileNames = SourceFormatterUtil.scanForFileNames(
						file.getCanonicalPath(),
						new String[] {
							"apps/**/" + moduleName + "/test.properties",
							"dxp/apps/**/" + moduleName + "/test.properties"
						});

					break;
				}

				moduleRootDirLocation = "../" + moduleRootDirLocation;
			}

			if (ListUtil.isEmpty(fileNames)) {
				addMessage(
					fileName,
					"Missing test.properties for module '" + moduleName +
						"' in modules");
			}
		}

		if (absolutePath.contains("/modules/apps/") ||
			absolutePath.contains("/modules/dxp/apps/")) {

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

	private synchronized List<String> _getTestrayAllTeamsComponentNames()
		throws IOException {

		if (_testrayAllTeamsComponentNames != null) {
			return _testrayAllTeamsComponentNames;
		}

		_testrayAllTeamsComponentNames = new ArrayList<>();

		File file = new File(getPortalDir(), "test.properties");

		if (!file.exists()) {
			return _testrayAllTeamsComponentNames;
		}

		Properties properties = new Properties();

		properties.load(new FileInputStream(file));

		List<String> testrayAvailableComponentNames = ListUtil.fromString(
			properties.getProperty("testray.available.component.names"),
			StringPool.COMMA);

		for (String testrayAvailableComponentName :
				testrayAvailableComponentNames) {

			if (!testrayAvailableComponentName.startsWith("${") &&
				!testrayAvailableComponentName.endsWith("}")) {

				continue;
			}

			String testrayTeamComponentName =
				testrayAvailableComponentName.substring(
					2, testrayAvailableComponentName.length() - 1);

			List<String> testrayTeamComponentNames = ListUtil.fromString(
				properties.getProperty(testrayTeamComponentName),
				StringPool.COMMA);

			if (ListUtil.isEmpty(testrayTeamComponentNames)) {
				continue;
			}

			_testrayAllTeamsComponentNames.addAll(testrayTeamComponentNames);
		}

		return _testrayAllTeamsComponentNames;
	}

	private static final String _PLAYWRIGHT_TEST_PROJECT_NAME =
		"playwright.test.project[playwright-js-tomcat90-mysql57-jdk8]" +
			"[relevant]";

	private static final String _TESTRAY_MAIN_COMPONENT_NAME =
		"testray.main.component.name";

	private List<String> _testrayAllTeamsComponentNames;

}