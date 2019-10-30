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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class JavaServiceObjectCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		List<String> importNames = getImportNames(javaTerm);

		if (importNames.isEmpty()) {
			return javaTerm.getContent();
		}

		String javaTermContent = _formatGetterMethodCalls(
			javaTerm.getContent(), fileContent, importNames);

		return _formatSetterMethodCalls(
			javaTermContent, fileContent, importNames);
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private String _formatGetterMethodCalls(
		String content, String fileContent, List<String> importNames) {

		Matcher matcher = _getterCallPattern.matcher(content);

		while (matcher.find()) {
			String variableName = matcher.group(1);

			String variableTypeName = getVariableTypeName(
				content, fileContent, variableName);

			if (variableTypeName == null) {
				continue;
			}

			String getterObjectName = TextFormatter.format(
				matcher.group(3), TextFormatter.I);

			if (_isBooleanColumn(
					variableTypeName, getterObjectName, importNames)) {

				return StringUtil.replaceFirst(
					content, "get", "is", matcher.start(2));
			}
		}

		return content;
	}

	private String _formatSetterMethodCalls(
		String content, String fileContent, List<String> importNames) {

		Matcher matcher1 = _setterCallsPattern.matcher(content);

		while (matcher1.find()) {
			String setterCallsCodeBlock = matcher1.group();

			String packageName = null;
			String previousMatch = null;
			String previousSetterObjectName = null;
			String previousVariableName = null;
			String variableTypeName = null;

			Matcher matcher2 = _setterCallPattern.matcher(setterCallsCodeBlock);

			while (matcher2.find()) {
				String match = matcher2.group();
				String setterObjectName = TextFormatter.format(
					matcher2.group(2), TextFormatter.I);
				String variableName = matcher2.group(1);

				if (!variableName.equals(previousVariableName)) {
					previousMatch = match;
					previousSetterObjectName = setterObjectName;
					previousVariableName = variableName;

					variableTypeName = getVariableTypeName(
						content, fileContent, variableName);

					packageName = _getPackageName(
						variableTypeName, importNames);

					continue;
				}

				Element serviceXMLElement = _getServiceXMLElement(packageName);

				if (serviceXMLElement != null) {
					int index1 = _getColumnIndex(
						serviceXMLElement, variableTypeName,
						previousSetterObjectName);
					int index2 = _getColumnIndex(
						serviceXMLElement, variableTypeName, setterObjectName);

					if ((index2 != -1) && (index1 > index2)) {
						int x = matcher2.start();

						int y = content.lastIndexOf(previousMatch, x);

						content = StringUtil.replaceFirst(
							content, match, previousMatch, x);

						return StringUtil.replaceFirst(
							content, previousMatch, match, y);
					}
				}

				previousMatch = match;
				previousSetterObjectName = setterObjectName;
				previousVariableName = variableName;
			}
		}

		return content;
	}

	private int _getColumnIndex(
		Element serviceXMLElement, String entityName, String columnName) {

		for (Element entityElement :
				(List<Element>)serviceXMLElement.elements("entity")) {

			if (!entityName.equals(entityElement.attributeValue("name"))) {
				continue;
			}

			int i = 0;

			for (Element columnElement :
					(List<Element>)entityElement.elements("column")) {

				if (columnName.equals(columnElement.attributeValue("name"))) {
					return i;
				}

				i++;
			}
		}

		return -1;
	}

	private String _getPackageName(
		String variableTypeName, List<String> importNames) {

		for (String importName : importNames) {
			if (importName.startsWith("com.liferay.") &&
				importName.endsWith(".model." + variableTypeName)) {

				return StringUtil.replaceLast(
					importName, "." + variableTypeName, StringPool.BLANK);
			}
		}

		return StringPool.BLANK;
	}

	private Element _getServiceXMLElement(String packageName) {
		if (_serviceXMLElementsMap != null) {
			return _serviceXMLElementsMap.get(packageName);
		}

		_serviceXMLElementsMap = new HashMap<>();

		try {
			_populateServiceXMLElements("modules/apps", 6);
			_populateServiceXMLElements("portal-impl/src/com/liferay", 4);
		}
		catch (DocumentException | IOException e) {
			return null;
		}

		return _serviceXMLElementsMap.get(packageName);
	}

	private boolean _isBooleanColumn(
		String variableTypeName, String getterObjectName,
		List<String> importNames) {

		String packageName = _getPackageName(variableTypeName, importNames);

		Element serviceXMLElement = _getServiceXMLElement(packageName);

		if (serviceXMLElement == null) {
			return false;
		}

		for (Element entityElement :
				(List<Element>)serviceXMLElement.elements("entity")) {

			if (!variableTypeName.equals(
					entityElement.attributeValue("name"))) {

				continue;
			}

			for (Element columnElement :
					(List<Element>)entityElement.elements("column")) {

				if (getterObjectName.equals(
						columnElement.attributeValue("name")) &&
					Objects.equals(
						columnElement.attributeValue("type"), "boolean")) {

					return true;
				}
			}
		}

		return false;
	}

	private void _populateServiceXMLElements(String dirName, int maxDepth)
		throws DocumentException, IOException {

		File directory = getFile(dirName, ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (directory == null) {
			return;
		}

		final List<File> serviceXMLFiles = new ArrayList<>();

		Files.walkFileTree(
			directory.toPath(), EnumSet.noneOf(FileVisitOption.class), maxDepth,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					String dirName = String.valueOf(dirPath.getFileName());

					if (ArrayUtil.contains(_SKIP_DIR_NAMES, dirName)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					Path path = dirPath.resolve("service.xml");

					if (Files.exists(path)) {
						serviceXMLFiles.add(path.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		for (File serviceXMLFile : serviceXMLFiles) {
			Document serviceXMLDocument = SourceUtil.readXML(
				FileUtil.read(serviceXMLFile));

			Element serviceXMLElement = serviceXMLDocument.getRootElement();

			String packagePath = serviceXMLElement.attributeValue(
				"api-package-path");

			if (packagePath == null) {
				packagePath = serviceXMLElement.attributeValue("package-path");
			}

			if (packagePath != null) {
				_serviceXMLElementsMap.put(
					packagePath + ".model", serviceXMLElement);
			}
		}
	}

	private static final String[] _SKIP_DIR_NAMES = {
		".git", ".gradle", ".idea", ".m2", ".settings", "bin", "build",
		"classes", "dependencies", "node_modules", "node_modules_cache", "sql",
		"src", "test", "test-classes", "test-coverage", "test-results", "tmp"
	};

	private static final Pattern _getterCallPattern = Pattern.compile(
		"\\W(\\w+)\\.\\s*(get)([A-Z]\\w*)\\(\\)");
	private static final Pattern _setterCallPattern = Pattern.compile(
		"(\\w+)\\.\\s*set([A-Z]\\w*)\\([^;]+;");
	private static final Pattern _setterCallsPattern = Pattern.compile(
		"(^[ \t]*\\w+\\.\\s*set[A-Z]\\w*\\([^;]+;\n)+",
		Pattern.DOTALL | Pattern.MULTILINE);

	private Map<String, Element> _serviceXMLElementsMap;

}