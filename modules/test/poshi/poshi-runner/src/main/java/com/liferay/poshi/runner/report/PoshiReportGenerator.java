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

	public static Map<String, Set<String>> executeFilePaths = new HashMap<>();
	public static Map<String, Set<String>> macroFilePaths = new HashMap<>();

	public static void getFilePaths(
		Map<String, Set<Element>> elementsMap,
		Map<String, Set<String>> filePathsMap) {

		List<String> keyList = new ArrayList<>(elementsMap.keySet());

		for (String key : keyList) {
			Set<Element> elementSet = elementsMap.get(key);

			List<Element> elementList = new ArrayList<>(elementSet);

			Set<String> filePathList = new HashSet<>();

			for (Element element : elementList) {
				PoshiElement poshiElement = (PoshiElement)element;

				StringBuilder sb = new StringBuilder();
				URL fileURL = poshiElement.getFilePathURL();

				sb.append(fileURL.toString());

				sb.append(":");
				sb.append(poshiElement.getPoshiScriptLineNumber());

				filePathList.add(sb.toString());
			}

			filePathsMap.put(key, filePathList);
		}
	}

	public static void main(String[] args) throws Exception {
		_poshiProperties = PoshiProperties.getPoshiProperties();

		if (_poshiProperties.generateUsageReport) {
			_generateMacroUsageReport();
		}
	}

	private static void _findExecuteElements() {
		Map<String, Element> rootElementMap = PoshiContext.getRootElements();

		List<String> rootElementList = new ArrayList<>(rootElementMap.keySet());

		for (String rootElementName : rootElementList) {
			Element rootElement = rootElementMap.get(rootElementName);

			_storeExecuteElements(rootElement);
		}
	}

	private static void _generateMacroUsageReport() throws Exception {
		PoshiContext.readFiles();

		_findExecuteElements();

		_setJavaScriptDataFilePath();

		_writeBaseUsageReportFiles();

		_writeDataJavaScriptFile(_macroElements, _javaScriptDataFilePath);
	}

	private static void _setJavaScriptDataFilePath() {
		StringBuilder sb = new StringBuilder();

		if (_poshiProperties.testRunLocally) {
			sb.append(FileUtil.getCanonicalPath("./usage-report/js/data.js"));
		}
		else {
			sb.append(_poshiProperties.testBaseDirName);
			sb.append("/usage-report/js/data.js");
		}

		_javaScriptDataFilePath = sb.toString();
	}

	private static void _storeExecuteElements(Element element) {
		String macroName = element.attributeValue("macro");

		if ((element instanceof ExecutePoshiElement) &&
			Validator.isNotNull(macroName)) {

			Set<Element> elements = new HashSet<>();

			if (_macroElements.containsKey(macroName)) {
				elements = _macroElements.get(macroName);

				elements.add(element);
			}
			else {
				elements.add(element);

				_macroElements.put(macroName, elements);
			}
		}

		List<Element> childElementList = element.elements();

		for (Element childElement : childElementList) {
			_storeExecuteElements(childElement);
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
		JSONArray executeDataJSONArray = new JSONArray();

		executeDataJSONArray.put(
			new String[] {"Name", "File Path", "Usage Count"});

		List<String> keyList = new ArrayList<>(elementsMap.keySet());

		Map<String, Element> commandElementMap =
			PoshiContext.getCommandElements();

		List<String> commandKeyList = new ArrayList<>(
			commandElementMap.keySet());

		for (String key : keyList) {
			JSONArray jsonArray = new JSONArray();

			jsonArray.put(key);

			for (String commandKey : commandKeyList) {
				if (!commandKey.startsWith("macro")) {
					continue;
				}

				String macroName = commandKey.substring(
					commandKey.indexOf(".") + 1);

				if (macroName.equals(key)) {
					PoshiElement poshiElement =
						(PoshiElement)commandElementMap.get(commandKey);

					StringBuilder sb = new StringBuilder();
					URL fileURL = poshiElement.getFilePathURL();

					sb.append(fileURL.toString());

					sb.append(":");
					sb.append(poshiElement.getPoshiScriptLineNumber());

					jsonArray.put(sb.toString());
				}
			}

			Set<Element> elementSet = elementsMap.get(key);

			Integer filePathsSize = elementSet.size();

			jsonArray.put(filePathsSize.toString());

			executeDataJSONArray.put(jsonArray);
		}

		StringBuilder sb = new StringBuilder();

		sb.append("var executeUsageData = ");
		sb.append(executeDataJSONArray);
		sb.append(";\nvar executeUsageDataGeneratedDate = new Date(");
		sb.append(DateUtil.getTimeInMilliseconds());
		sb.append(")");

		FileUtil.write(filePath, sb.toString());
	}

	private static String _javaScriptDataFilePath;
	private static final Map<String, Set<Element>> _macroElements =
		Collections.synchronizedMap(new HashMap<>());
	private static PoshiProperties _poshiProperties;

}