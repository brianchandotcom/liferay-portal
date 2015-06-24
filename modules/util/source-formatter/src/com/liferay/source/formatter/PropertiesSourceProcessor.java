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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

/**
 * @author Hugo Huijser
 */
public class PropertiesSourceProcessor extends BaseSourceProcessor {

	@Override
	public String[] getIncludes() {
		if (portalSource) {
			return new String[] {
				"**/portal.properties", "**/portal-ext.properties",
				"**/portal-legacy-*.properties", "**/portlet.properties",
				"**/source-formatter.properties"
			};
		}

		return new String[] {
			"**/portal.properties", "**/portal-ext.properties",
			"**/portlet.properties", "**/source-formatter.properties"
		};
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		String newContent = content;

		if (fileName.endsWith("portlet.properties")) {
			newContent = formatPortletProperties(fileName, content);
		}
		else if (fileName.endsWith("source-formatter.properties")) {
			formatSourceFormatterProperties(fileName, content);
		}
		else if (portalSource && fileName.endsWith("portal.properties")) {
			newContent = formatPortalPortalProperties(fileName, content);
		}
		else {
			formatPortalProperties(fileName, content);
		}

		return newContent;
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		return getFileNames(new String[0], getIncludes());
	}

	protected String formatPortalPortalProperties(
			String fileName, String content)
		throws Exception {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = trimLine(line, true);

				if (line.startsWith(StringPool.TAB)) {
					line = line.replace(
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

		return newContent;
	}

	protected void formatPortalProperties(String fileName, String content)
		throws Exception {

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineCount = 0;

			String line = null;

			int previousPos = -1;

			String portalPortalPropertiesContent = getPortalPortalProperties();

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				int pos = line.indexOf(CharPool.EQUAL);

				if (pos == -1) {
					continue;
				}

				String property = StringUtil.trim(line.substring(0, pos + 1));

				pos = portalPortalPropertiesContent.indexOf(
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
	}

	protected String formatPortletProperties(String fileName, String content)
		throws Exception {

		if (!content.contains("include-and-override=portlet-ext.properties")) {
			content =
				"include-and-override=portlet-ext.properties" + "\n\n" +
					content;
		}

		if (!portalSource) {
			return content;
		}

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineCount = 0;

			String line = null;

			String previousProperty = StringPool.BLANK;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				if (lineCount == 1) {
					continue;
				}

				if (line.startsWith(StringPool.POUND) ||
					line.startsWith(StringPool.SPACE) ||
					line.startsWith(StringPool.TAB)) {

					continue;
				}

				int pos = line.indexOf(CharPool.EQUAL);

				if (pos == -1) {
					continue;
				}

				String property = StringUtil.trim(line.substring(0, pos));

				pos = property.indexOf(CharPool.OPEN_BRACKET);

				if (pos != -1) {
					property = property.substring(0, pos);
				}

				if (Validator.isNotNull(previousProperty) &&
					(previousProperty.compareToIgnoreCase(property) > 0)) {

					processErrorMessage(
						fileName, "sort: " + fileName + " " + lineCount);
				}

				previousProperty = property;
			}
		}

		return content;
	}

	protected void formatSourceFormatterProperties(
			String fileName, String content)
		throws Exception {

		String path = StringPool.BLANK;

		int pos = fileName.lastIndexOf(CharPool.SLASH);

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
				pos = propertyFileName.indexOf(CharPool.AT);

				if (pos != -1) {
					propertyFileName = propertyFileName.substring(0, pos);
				}

				File file = new File(path + propertyFileName);

				if (!file.exists()) {
					processErrorMessage(
						fileName,
						"Incorrect property value: " + propertyFileName + " " +
							fileName);
				}
			}
		}
	}

	protected String getPortalPortalProperties() throws Exception {
		if (_portalPortalPropertiesContent != null) {
			return _portalPortalPropertiesContent;
		}

		if (portalSource) {
			File file = getFile("portal-impl/src/portal.properties", 4);

			_portalPortalPropertiesContent = FileUtil.read(file);

			return _portalPortalPropertiesContent;
		}

		ClassLoader classLoader =
			PropertiesSourceProcessor.class.getClassLoader();

		URL url = classLoader.getResource("portal.properties");

		if (url != null) {
			_portalPortalPropertiesContent = IOUtils.toString(url);
		}
		else {
			_portalPortalPropertiesContent = StringPool.BLANK;
		}

		return _portalPortalPropertiesContent;
	}

	private String _portalPortalPropertiesContent;

}