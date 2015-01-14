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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.ContentUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * @author Hugo Huijser
 */
public class PropertiesSourceProcessor extends BaseSourceProcessor {

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		if (!portalSource && fileName.endsWith("portlet.properties")) {
			return formatPortletProperties(content);
		}

		if (fileName.endsWith("source-formatter.properties")) {
			formatSourceFormatterProperties(fileName, content);
		}
		else {
			formatPortalProperties(fileName, content);
		}

		return content;
	}

	@Override
	protected void format() throws Exception {
		_portalPortalPropertiesContent = formatPortalPortalProperties();

		String[] includes = null;

		if (portalSource) {
			includes = new String[] {
				"**\\portal-ext.properties", "**\\portal-legacy-*.properties",
				"**\\source-formatter.properties"
			};
		}
		else {
			includes = new String[] {
				"**\\portal.properties", "**\\portal-ext.properties",
				"**\\portlet.properties", "**\\source-formatter.properties"
			};
		}

		List<String> fileNames = getFileNames(new String[0], includes);

		for (String fileName : fileNames) {
			format(fileName);
		}
	}

	protected String formatPortalPortalProperties() throws Exception {
		if (!portalSource) {
			return ContentUtil.get("portal.properties");
		}

		String fileName = "portal-impl/src/portal.properties";

		File file = getFile(fileName, 4);

		String content = fileUtil.read(file);

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = trimLine(line, true);

				if (line.startsWith(StringPool.TAB)) {
					line = line.replaceFirst(
						StringPool.TAB, StringPool.FOUR_SPACES);
				}

				sb.append(line);
				sb.append("\n");
			}
		}

		String newContent = sb.toString();

		if (newContent.endsWith("\n")) {
			newContent = newContent.substring(0, newContent.length() - 1);
		}

		processFormattedFile(file, fileName, content, newContent);

		return newContent;
	}

	protected void formatPortalProperties(String fileName, String content)
		throws Exception {

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));

		int lineCount = 0;

		String line = null;

		int previousPos = -1;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			lineCount++;

			int pos = line.indexOf(StringPool.EQUAL);

			if (pos == -1) {
				continue;
			}

			String property = line.substring(0, pos + 1);

			property = property.trim();

			pos = _portalPortalPropertiesContent.indexOf(
				StringPool.FOUR_SPACES + property);

			if (pos == -1) {
				continue;
			}

			if (pos < previousPos) {
				processErrorMessage(
					fileName, "sort " + fileName + " " + lineCount);
			}

			previousPos = pos;
		}
	}

	protected String formatPortletProperties(String content) {
		if (!content.contains("include-and-override=portlet-ext.properties")) {
			content =
				"include-and-override=portlet-ext.properties" + "\n\n" +
					content;
		}

		return content;
	}

	protected void formatSourceFormatterProperties(
			String fileName, String content)
		throws Exception {

		String path = StringPool.BLANK;

		int pos = fileName.lastIndexOf(StringPool.SLASH);

		if (pos != -1) {
			path = fileName.substring(0, pos + 1);
		}

		Properties properties = new Properties();

		InputStream inputStream = new FileInputStream(fileName);

		properties.load(inputStream);

		Enumeration<String> enu =
			(Enumeration<String>)properties.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();

			if (!key.endsWith("excludes.files")) {
				continue;
			}

			String value = properties.getProperty(key);

			if (Validator.isNull(value)) {
				continue;
			}

			List<String> propertyFileNames = ListUtil.fromString(
				value, StringPool.COMMA);

			for (String propertyFileName : propertyFileNames) {
				pos = propertyFileName.indexOf(StringPool.AT);

				if (pos != -1) {
					propertyFileName = propertyFileName.substring(0, pos);
				}

				if (!fileUtil.exists(path + propertyFileName)) {
					processErrorMessage(
						fileName,
						"Incorrect property value: " + propertyFileName + " " +
							fileName);
				}
			}
		}
	}

	private String _portalPortalPropertiesContent;

}