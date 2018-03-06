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
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;
import com.liferay.source.formatter.util.CheckType;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;

import java.util.List;
import java.util.Objects;

import org.dom4j.Document;
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

		String portalFileLocation = null;

		for (File file : files) {
			String content = FileUtil.read(file);

			Document document = SourceUtil.readXML(content);

			Element rootElement = document.getRootElement();

			String fileName = file.getName();

			if (fileName.endsWith("checkstyle-suppressions.xml")) {
				_loadCheckstyleSuppressions(
					sourceFormatterSuppressions,
					(List<Element>)rootElement.elements("suppress"));
			}

			if (fileName.endsWith("sourcechecks-suppressions.xml")) {
				if (portalFileLocation == null) {
					portalFileLocation = _getPortalFileLocation(baseDirName);
				}

				_loadSourceChecksSuppressions(
					sourceFormatterSuppressions,
					(List<Element>)rootElement.elements("suppress"), file,
					portalFileLocation);
			}
		}

		return sourceFormatterSuppressions;
	}

	private static String _getFileLocation(File file) {
		String absolutePath = SourceUtil.getAbsolutePath(file);

		int pos = absolutePath.lastIndexOf(CharPool.SLASH);

		return absolutePath.substring(0, pos + 1);
	}

	private static String _getPortalFileLocation(String baseDirName) {
		File portalImplDir = SourceFormatterUtil.getFile(
			baseDirName, "portal-impl", ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (portalImplDir == null) {
			return null;
		}

		return _getFileLocation(portalImplDir);
	}

	private static SourceFormatterSuppressions _loadCheckstyleSuppressions(
		SourceFormatterSuppressions sourceFormatterSuppressions,
		List<Element> suppressElements) {

		for (Element suppressElement : suppressElements) {
			sourceFormatterSuppressions.addSuppression(
				CheckType.CHECKSTYLE, null,
				suppressElement.attributeValue(_CHECK_ATTRIBUTE_NAME),
				suppressElement.attributeValue(_FILE_ATTRIBUTE_NAME));
		}

		return sourceFormatterSuppressions;
	}

	private static SourceFormatterSuppressions _loadSourceChecksSuppressions(
		SourceFormatterSuppressions sourceFormatterSuppressions,
		List<Element> suppressElements, File file, String portalFileLocation) {

		String suppressionsFileLocation = _getFileLocation(file);

		for (Element suppressElement : suppressElements) {
			String fileName = suppressElement.attributeValue(
				_FILE_ATTRIBUTE_NAME);

			if (Objects.equals(portalFileLocation, suppressionsFileLocation)) {
				fileName = portalFileLocation + fileName;
			}

			sourceFormatterSuppressions.addSuppression(
				CheckType.SOURCE_CHECK, suppressionsFileLocation,
				suppressElement.attributeValue(_CHECK_ATTRIBUTE_NAME),
				fileName);
		}

		return sourceFormatterSuppressions;
	}

	private static final String _CHECK_ATTRIBUTE_NAME = "checks";

	private static final String _FILE_ATTRIBUTE_NAME = "files";

}