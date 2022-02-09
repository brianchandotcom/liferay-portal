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

package com.liferay.source.formatter.check;

import aQute.bnd.version.Version;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.SourceFormatterExcludes;
import com.liferay.source.formatter.check.util.BNDSourceUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.ParseException;
import com.liferay.source.formatter.util.FileUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class BNDSchemaVersionCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException, ParseException {

		String schemaVersion = BNDSourceUtil.getDefinitionValue(
			content, "Liferay-Require-SchemaVersion");

		if (GetterUtil.getBoolean(
				BNDSourceUtil.getDefinitionValue(content, "Liferay-Service"))) {

			if (schemaVersion != null) {
				return _fixSchemaVersion(absolutePath, content, schemaVersion);
			}

			int pos = absolutePath.lastIndexOf(CharPool.SLASH);

			File serviceXMLfile = new File(
				absolutePath.substring(0, pos + 1) + "service.xml");

			if (serviceXMLfile.exists()) {
				addMessage(fileName, "Missing 'Liferay-Require-SchemaVersion'");
			}
		}
		else if (schemaVersion != null) {
			addMessage(
				fileName,
				"The header 'Liferay-Require-SchemaVersion' can only be used " +
					"when the header 'Liferay-Service' has value 'true'");
		}

		if (fileName.endsWith("-web/bnd.bnd") &&
			Objects.equals(schemaVersion, "1.0.0")) {

			addMessage(
				fileName,
				"Do not include the header Liferay-Require-SchemaVersion in " +
					"web modules");
		}

		return content;
	}

	private String _fixSchemaVersion(
			String absolutePath, String content, String schemaVersion)
		throws IOException, ParseException {

		int x = absolutePath.lastIndexOf(CharPool.SLASH);

		absolutePath.substring(0, x + 1);

		List<String> upgradeFileNames = SourceFormatterUtil.scanForFiles(
			absolutePath.substring(0, x + 1), new String[0],
			new String[] {"**/upgrade/*.java", "**/upgrade/**/*.java"},
			new SourceFormatterExcludes(), false);

		String expectedSchemaVersion = _getExpectedSchemaVersion(
			upgradeFileNames);

		if ((expectedSchemaVersion == null) ||
			schemaVersion.equals(expectedSchemaVersion)) {

			return content;
		}

		return StringUtil.replace(
			content, "Liferay-Require-SchemaVersion: " + schemaVersion,
			"Liferay-Require-SchemaVersion: " + expectedSchemaVersion);
	}

	private String _getExpectedSchemaVersion(List<String> fileNames)
		throws IOException, ParseException {

		Version expectedSchemaVersion = null;

		for (String fileName : fileNames) {
			fileName = StringUtil.replace(
				fileName, CharPool.BACK_SLASH, CharPool.SLASH);

			File file = new File(fileName);

			String content = FileUtil.read(file);

			JavaClass javaClass = JavaClassParser.parseJavaClass(
				fileName, content);

			List<String> implementedClassNames =
				javaClass.getImplementedClassNames();

			if (!implementedClassNames.contains("UpgradeStepRegistrator")) {
				continue;
			}

			int x = -1;

			while (true) {
				x = content.indexOf(".register(", x + 1);

				if (x == -1) {
					break;
				}

				List<String> parameterList = JavaSourceUtil.getParameterList(
					content.substring(x - 1));

				if (parameterList.size() < 3) {
					break;
				}

				for (int i = parameterList.size() - 2; i > 0; i--) {
					Version schemaVersion = null;

					try {
						schemaVersion = new Version(
							StringUtil.removeChar(
								parameterList.get(i), CharPool.QUOTE));
					}
					catch (IllegalArgumentException illegalArgumentException) {
						if (_log.isDebugEnabled()) {
							_log.debug(illegalArgumentException);
						}

						continue;
					}

					if ((expectedSchemaVersion == null) ||
						(expectedSchemaVersion.compareTo(schemaVersion) < 0)) {

						expectedSchemaVersion = schemaVersion;
					}

					break;
				}
			}
		}

		if (expectedSchemaVersion != null) {
			return expectedSchemaVersion.toString();
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BNDSchemaVersionCheck.class);

}