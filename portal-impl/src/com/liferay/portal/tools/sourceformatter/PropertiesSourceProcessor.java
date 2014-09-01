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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.ContentUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;

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

		formatPortalProperties(fileName, content);

		return content;
	}

	@Override
	protected void format() throws Exception {
		_portalPortalPropertiesContent = formatPortalPortalProperties();

		String[] includes = null;

		if (portalSource) {
			includes = new String[] {
				"**\\portal-ext.properties", "**\\portal-legacy-*.properties"
			};
		}
		else {
			includes = new String[] {
				"**\\portal.properties", "**\\portal-ext.properties",
				"**\\portlet.properties"
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

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));

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

		unsyncBufferedReader.close();

		String newContent = sb.toString();

		if (newContent.endsWith("\n")) {
			newContent = newContent.substring(0, newContent.length() - 1);
		}

		processFormattedFile(file, fileName, content, newContent);

		return newContent;
	}

	protected void formatPortalProperties(String fileName, String content)
		throws IOException {

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

	private String _portalPortalPropertiesContent;

}