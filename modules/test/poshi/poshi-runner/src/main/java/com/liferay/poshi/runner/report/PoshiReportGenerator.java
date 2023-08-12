/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.report;

import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.PoshiProperties;
import com.liferay.poshi.core.elements.ExecutePoshiElement;
import com.liferay.poshi.core.elements.PoshiElement;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.poshi.core.util.Validator;
import com.liferay.poshi.runner.util.DateUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;

import org.json.JSONArray;

/**
 * @author Calum Ragan
 */
public class PoshiReportGenerator {

	public static void main(String[] args) throws Exception {
		if (_poshiProperties.generateUsageReport) {
			_generateMacroUsageReport();
		}
	}

	private static void _findMacroExecuteElementUsages() {
		Map<String, Element> rootElementMap = PoshiContext.getRootElements();

		List<String> rootElementKeys = new ArrayList<>(rootElementMap.keySet());

		for (String rootElementKey : rootElementKeys) {
			Element rootElement = rootElementMap.get(rootElementKey);

			_storeMacroExecuteElements(rootElement);
		}
	}

	private static void _generateMacroUsageReport() throws Exception {
		PoshiContext.readFiles();

		_findMacroExecuteElementUsages();

		_writeBaseUsageReportFiles();

		_writeDataJavaScriptFile();
	}

	private static void _storeMacroExecuteElements(Element element) {
		String macroName = element.attributeValue("macro");

		if ((element instanceof ExecutePoshiElement) &&
			Validator.isNotNull(macroName)) {

			Set<Element> elements = new HashSet<>();

			if (_executeMacroElementMap.containsKey(macroName)) {
				elements = _executeMacroElementMap.get(macroName);

				elements.add(element);
			}
			else {
				elements.add(element);

				_executeMacroElementMap.put(macroName, elements);
			}
		}

		List<Element> childElementList = element.elements();

		for (Element childElement : childElementList) {
			_storeMacroExecuteElements(childElement);
		}
	}

	private static void _writeBaseUsageReportFiles() throws Exception {
		String currentDirName = FileUtil.getCanonicalPath(".");

		ClassLoader classLoader = PoshiReportGenerator.class.getClassLoader();

		URL url = classLoader.getResource("reports/usage/index.html");

		String indexHTMLContent = FileUtil.read(url);

		String testBaseDirName = _poshiProperties.testBaseDirName;

		if (_poshiProperties.testRunLocally) {
			FileUtil.copyFileFromResource(
				"reports/usage/css/main.css",
				currentDirName + "/usage-report/css/main.css");
			FileUtil.copyFileFromResource(
				"reports/usage/js/main.js",
				currentDirName + "/usage-report/js/main.js");
			FileUtil.copyFileFromResource(
				"reports/usage/js/data.js",
				currentDirName + "/usage-report/js/data.js");
		}
		else {
			FileUtil.copyFileFromResource(
				"reports/usage/css/main.css",
				testBaseDirName + "/usage-report/css/main.css");
			FileUtil.copyFileFromResource(
				"reports/usage/js/main.js",
				testBaseDirName + "/usage-report/js/main.js");
			FileUtil.copyFileFromResource(
				"reports/usage/js/data.js",
				testBaseDirName + "/usage-report/js/data.js");
		}

		StringBuilder sb = new StringBuilder();

		if (_poshiProperties.testRunLocally) {
			sb.append(currentDirName);
		}
		else {
			sb.append(testBaseDirName);
		}

		sb.append("/usage-report/index.html");

		FileUtil.write(sb.toString(), indexHTMLContent);
	}

	private static void _writeDataJavaScriptFile() throws Exception {
		JSONArray executeUsageJSONArray = new JSONArray();

		executeUsageJSONArray.put(
			new String[] {"Name", "File Path", "Usage Count"});

		List<String> executeMacroElementKeys = new ArrayList<>(
			_executeMacroElementMap.keySet());

		Map<String, Element> commandElements =
			PoshiContext.getCommandElements();

		List<String> commandKeys = new ArrayList<>(commandElements.keySet());

		for (String executeMacroElementKey : executeMacroElementKeys) {
			JSONArray jsonArray = new JSONArray();

			jsonArray.put(executeMacroElementKey);

			for (String commandKey : commandKeys) {
				if (!commandKey.startsWith("macro")) {
					continue;
				}

				String macroName = commandKey.substring(
					commandKey.indexOf(".") + 1);

				if (macroName.equals(executeMacroElementKey)) {
					PoshiElement poshiElement =
						(PoshiElement)commandElements.get(commandKey);

					StringBuilder sb = new StringBuilder();

					URL fileURL = poshiElement.getFilePathURL();

					sb.append(fileURL.toString());

					sb.append(":");
					sb.append(poshiElement.getPoshiScriptLineNumber());

					jsonArray.put(sb.toString());
				}
			}

			Set<Element> executeMacroElements = _executeMacroElementMap.get(
				executeMacroElementKey);

			Integer filePathsSize = executeMacroElements.size();

			jsonArray.put(filePathsSize.toString());

			executeUsageJSONArray.put(jsonArray);
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var executeUsageData = ");
		sb.append(executeUsageJSONArray);
		sb.append(";\nvar executeUsageDataGeneratedDate = new Date(");
		sb.append(DateUtil.getTimeInMilliseconds());
		sb.append(")");

		FileUtil.write(_USAGE_DATA_JAVA_SCRIPT_FILE_PATH, sb.toString());
	}

	private static final String _USAGE_DATA_JAVA_SCRIPT_FILE_PATH;

	private static final Map<String, Set<Element>> _executeMacroElementMap =
		Collections.synchronizedMap(new HashMap<>());
	private static final PoshiProperties _poshiProperties =
		PoshiProperties.getPoshiProperties();

	static {
		StringBuilder sb = new StringBuilder();

		if (_poshiProperties.testRunLocally) {
			sb.append(FileUtil.getCanonicalPath("./usage-report/js/data.js"));
		}
		else {
			sb.append(_poshiProperties.testBaseDirName);
			sb.append("/usage-report/js/data.js");
		}

		_USAGE_DATA_JAVA_SCRIPT_FILE_PATH = sb.toString();
	}

}