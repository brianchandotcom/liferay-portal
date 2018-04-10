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

package com.liferay.source.formatter.checks.configuration;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.xml.Dom4jUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.CheckType;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;

import java.util.List;
import java.util.Objects;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class SuppressionsLoader {

	public static SourceFormatterSuppressions loadSuppressions(
			String baseDirName, List<File> files)
		throws Exception {

		SourceFormatterSuppressions sourceFormatterSuppressions =
			new SourceFormatterSuppressions();

		for (File file : files) {
			String content = FileUtil.read(file);

			Document document = SourceUtil.readXML(content);

			Element rootElement = document.getRootElement();

			String absolutePath = SourceUtil.getAbsolutePath(file);

			if (absolutePath.endsWith("checkstyle-suppressions.xml")) {
				sourceFormatterSuppressions = _loadCheckstyleSuppressions(
					sourceFormatterSuppressions, rootElement, absolutePath,
					true);
			}
			else if (absolutePath.endsWith(
						"source-formatter-suppressions.xml")) {

				sourceFormatterSuppressions = _loadCheckstyleSuppressions(
					sourceFormatterSuppressions,
					rootElement.element(_CHECKSTYLE_ATTRIBUTE_NAME),
					absolutePath, false);
				sourceFormatterSuppressions = _loadSourceChecksSuppressions(
					sourceFormatterSuppressions,
					rootElement.element(_SOURCE_CHECK_ATTRIBUTE_NAME),
					absolutePath, _getPortalFileLocation(baseDirName), false);
			}
			else if (absolutePath.endsWith("sourcechecks-suppressions.xml")) {
				sourceFormatterSuppressions = _loadSourceChecksSuppressions(
					sourceFormatterSuppressions, rootElement, absolutePath,
					_getPortalFileLocation(baseDirName), true);
			}
		}

		return sourceFormatterSuppressions;
	}

	private static String _getFileLocation(String absolutePath) {
		int pos = absolutePath.lastIndexOf(CharPool.SLASH);

		return absolutePath.substring(0, pos + 1);
	}

	private static String _getPortalFileLocation(String baseDirName) {
		if (_portalFileLocation != null) {
			return _portalFileLocation;
		}

		File portalImplDir = SourceFormatterUtil.getFile(
			baseDirName, "portal-impl", ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (portalImplDir == null) {
			return null;
		}

		_portalFileLocation = _getFileLocation(
			SourceUtil.getAbsolutePath(portalImplDir));

		return _portalFileLocation;
	}

	private static SourceFormatterSuppressions _loadCheckstyleSuppressions(
			SourceFormatterSuppressions sourceFormatterSuppressions,
			Element suppressionsElement, String absolutePath,
			boolean moveToSourceFormatterSuppressionsFile)
		throws Exception {

		if (suppressionsElement == null) {
			return sourceFormatterSuppressions;
		}

		List<Element> suppressElements =
			(List<Element>)suppressionsElement.elements("suppress");

		if (moveToSourceFormatterSuppressionsFile) {
			_moveSuppressionsToSourceFormatterSuppressionsFile(
				absolutePath, suppressElements, _CHECKSTYLE_ATTRIBUTE_NAME);
		}

		for (Element suppressElement : suppressElements) {
			sourceFormatterSuppressions.addSuppression(
				CheckType.CHECKSTYLE, null,
				suppressElement.attributeValue(_CHECK_ATTRIBUTE_NAME),
				suppressElement.attributeValue(_FILE_REGEX_ATTRIBUTE_NAME));
		}

		return sourceFormatterSuppressions;
	}

	private static SourceFormatterSuppressions _loadSourceChecksSuppressions(
			SourceFormatterSuppressions sourceFormatterSuppressions,
			Element suppressionsElement, String absolutePath,
			String portalFileLocation,
			boolean moveToSourceFormatterSuppressionsFile)
		throws Exception {

		if (suppressionsElement == null) {
			return sourceFormatterSuppressions;
		}

		List<Element> suppressElements =
			(List<Element>)suppressionsElement.elements("suppress");

		if (moveToSourceFormatterSuppressionsFile) {
			_moveSuppressionsToSourceFormatterSuppressionsFile(
				absolutePath, suppressElements, _SOURCE_CHECK_ATTRIBUTE_NAME);
		}

		String suppressionsFileLocation = _getFileLocation(absolutePath);

		for (Element suppressElement : suppressElements) {
			String fileNameRegex = suppressElement.attributeValue(
				_FILE_REGEX_ATTRIBUTE_NAME);

			if (Objects.equals(portalFileLocation, suppressionsFileLocation)) {
				fileNameRegex = portalFileLocation + fileNameRegex;
			}

			sourceFormatterSuppressions.addSuppression(
				CheckType.SOURCE_CHECK, suppressionsFileLocation,
				suppressElement.attributeValue(_CHECK_ATTRIBUTE_NAME),
				fileNameRegex);
		}

		return sourceFormatterSuppressions;
	}

	private static void _moveSuppressionsToSourceFormatterSuppressionsFile(
			String absolutePath, List<Element> suppressElements, String name)
		throws Exception {

		int pos = absolutePath.lastIndexOf(CharPool.SLASH);

		String sourceFormatterSuppressionsFileName =
			absolutePath.substring(0, pos + 1) +
				"source-formatter-suppressions.xml";

		File sourceFormatterSuppressionsFile = new File(
			sourceFormatterSuppressionsFileName);

		boolean modified = false;

		Document document = null;
		Element rootElement = null;

		if (sourceFormatterSuppressionsFile.exists()) {
			document = SourceUtil.readXML(
				FileUtil.read(sourceFormatterSuppressionsFile));

			rootElement = document.getRootElement();
		}
		else {
			document = DocumentHelper.createDocument();

			rootElement = document.addElement("suppressions");

			modified = true;
		}

		Element checkTypeElement = rootElement.element(name);

		if (checkTypeElement == null) {
			checkTypeElement = rootElement.addElement(name);

			modified = true;
		}

		outerLoop:
		for (Element suppressElement : suppressElements) {
			List<Element> childElements = checkTypeElement.elements();

			for (Element childElement : childElements) {
				if (Objects.equals(
						suppressElement.asXML(), childElement.asXML())) {

					continue outerLoop;
				}
			}

			suppressElement.detach();

			checkTypeElement.add(suppressElement);

			modified = true;
		}

		if (modified) {
			FileUtil.write(
				sourceFormatterSuppressionsFile, Dom4jUtil.toString(document));

			System.out.println(sourceFormatterSuppressionsFileName);
		}
	}

	private static final String _CHECK_ATTRIBUTE_NAME = "checks";

	private static final String _CHECKSTYLE_ATTRIBUTE_NAME = "checkstyle";

	private static final String _FILE_REGEX_ATTRIBUTE_NAME = "files";

	private static final String _SOURCE_CHECK_ATTRIBUTE_NAME = "source-check";

	private static String _portalFileLocation;

}