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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.ElementComparator;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLBuildFileCheck extends BaseFileCheck {

	public XMLBuildFileCheck(String baseDirName) {
		_baseDirName = baseDirName;
	}

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		if (fileName.startsWith(_baseDirName + "build") ||
			(fileName.contains("/build") && !fileName.contains("/tools/"))) {

			_checkBuildXML(
				sourceFormatterMessages, fileName, absolutePath, content);
		}

		return new Tuple(content, sourceFormatterMessages);
	}

	private void _checkBuildProjectName(
		Set<SourceFormatterMessage> sourceFormatterMessages, String fileName,
		Document document) {

		Matcher matcher = _projectNamePattern.matcher(fileName);

		if (!matcher.find()) {
			return;
		}

		String expectedProjectName = matcher.group(1);

		Element rootElement = document.getRootElement();

		String projectName = rootElement.attributeValue("name");

		if (!projectName.equals(expectedProjectName)) {
			addMessage(
				sourceFormatterMessages, fileName,
				"Incorrect project name '" + projectName + "'");
		}
	}

	private void _checkBuildXML(
			Set<SourceFormatterMessage> sourceFormatterMessages,
			String fileName, String absolutePath, String content)
		throws Exception {

		Document document = SourceUtil.readXML(content);

		_checkBuildProjectName(sourceFormatterMessages, fileName, document);

		checkElementOrder(
			sourceFormatterMessages, fileName, document.getRootElement(),
			"macrodef", null, new ElementComparator());
		checkElementOrder(
			sourceFormatterMessages, fileName, document.getRootElement(),
			"target", null, new ElementComparator());

		int x = content.lastIndexOf("\n\t</macrodef>");
		int y = content.indexOf("\n\t<process-ivy");

		if ((y != -1) && (x > y)) {
			addMessage(
				sourceFormatterMessages, fileName,
				"Macrodefs go before process-ivy");
		}

		int z = content.indexOf("\n\t</target>");

		if ((z != -1) && (x > z)) {
			addMessage(
				sourceFormatterMessages, fileName,
				"Macrodefs go before targets");
		}

		_checkImportFiles(sourceFormatterMessages, fileName, content);

		_checkTargetNames(
			sourceFormatterMessages, fileName, absolutePath, content);
	}

	private void _checkImportFiles(
		Set<SourceFormatterMessage> sourceFormatterMessages, String fileName,
		String content) {

		Matcher matcher = _importFilePattern.matcher(content);

		while (matcher.find()) {
			String importFileName = fileName;

			int pos = importFileName.lastIndexOf(StringPool.SLASH);

			if (pos == -1) {
				return;
			}

			importFileName = importFileName.substring(0, pos + 1);

			importFileName = importFileName + matcher.group(1);

			File file = new File(importFileName);

			if (!file.exists()) {
				addMessage(
					sourceFormatterMessages, fileName,
					"Incorrect import file '" + matcher.group(1) + "'");
			}
		}
	}

	private void _checkTargetName(
			Set<SourceFormatterMessage> sourceFormatterMessages,
			String targetName, String buildFileName, String fileName)
		throws Exception {

		List<String> targetNames = _getTargetNames(
			sourceFormatterMessages, buildFileName, fileName, null, false);

		if ((targetNames == null) || targetNames.contains(targetName)) {
			return;
		}

		int x = targetName.lastIndexOf(CharPool.PERIOD);

		if (x != -1) {
			targetName = targetName.substring(x + 1);
		}

		if (!targetNames.contains(targetName)) {
			addMessage(
				sourceFormatterMessages, fileName,
				"Target '" + targetName + "' does not exist");
		}
	}

	private void _checkTargetNames(
			Set<SourceFormatterMessage> sourceFormatterMessages,
			String fileName, String absolutePath, String content)
		throws Exception {

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		List<Element> antCallElements = _getElementsByName(
			"antcall", rootElement, null);

		for (Element antCallElement : antCallElements) {
			String targetName = antCallElement.attributeValue("target");

			if ((targetName == null) ||
				targetName.contains(StringPool.OPEN_CURLY_BRACE)) {

				continue;
			}

			_checkTargetName(
				sourceFormatterMessages, targetName, absolutePath, fileName);
		}

		String fileDirName = fileName.substring(
			0, fileName.lastIndexOf(CharPool.SLASH) + 1);

		List<Element> antElements = _getElementsByName(
			"ant", rootElement, null);

		for (Element antElement : antElements) {
			String targetName = antElement.attributeValue("target");

			if ((targetName == null) ||
				targetName.contains(StringPool.OPEN_CURLY_BRACE)) {

				continue;
			}

			String fullDirName = fileDirName;

			String dirName = antElement.attributeValue("dir");

			if (dirName != null) {
				if (dirName.contains(StringPool.OPEN_CURLY_BRACE)) {
					continue;
				}

				fullDirName = fullDirName + dirName + StringPool.SLASH;
			}

			String antFileName = antElement.attributeValue("antfile");

			if (antFileName == null) {
				antFileName = "build.xml";
			}

			_checkTargetName(
				sourceFormatterMessages, targetName, fullDirName + antFileName,
				fileName);
		}
	}

	private List<Element> _getElementsByName(
		String name, Element element, List<Element> elements) {

		if (elements == null) {
			elements = new ArrayList<>();
		}

		List<Element> childElements = element.elements();

		for (Element childElement : childElements) {
			String elementName = childElement.getName();

			if (elementName.equals(name)) {
				elements.add(childElement);
			}

			elements = _getElementsByName(name, childElement, elements);
		}

		return elements;
	}

	private List<String> _getTargetNames(
			Set<SourceFormatterMessage> sourceFormatterMessages,
			String buildFileName, String fileName, List<String> targetNames,
			boolean importFile)
		throws Exception {

		if (buildFileName.contains(StringPool.OPEN_CURLY_BRACE)) {
			return null;
		}

		File file = new File(buildFileName);

		if (!file.exists()) {
			if (!importFile) {
				addMessage(
					sourceFormatterMessages, fileName,
					"Ant element points to non-existing build file '" +
						buildFileName + "'");
			}

			return null;
		}

		Document document = SourceUtil.readXML(FileUtil.read(file));

		Element rootElement = document.getRootElement();

		if (targetNames == null) {
			targetNames = new ArrayList<>();
		}

		List<Element> targetElements = rootElement.elements("target");

		for (Element targetElement : targetElements) {
			targetNames.add(targetElement.attributeValue("name"));
		}

		List<Element> importElements = rootElement.elements("import");

		for (Element importElement : importElements) {
			String buildDirName = buildFileName.substring(
				0, buildFileName.lastIndexOf(CharPool.SLASH) + 1);

			String importFileName =
				buildDirName + importElement.attributeValue("file");

			targetNames = _getTargetNames(
				sourceFormatterMessages, importFileName, fileName, targetNames,
				true);
		}

		return targetNames;
	}

	private final String _baseDirName;
	private final Pattern _importFilePattern = Pattern.compile(
		"<import file=\"(.*)\"");
	private final Pattern _projectNamePattern = Pattern.compile(
		"/(\\w*-(ext|hooks|layouttpl|portlet|theme|web))/build\\.xml$");

}