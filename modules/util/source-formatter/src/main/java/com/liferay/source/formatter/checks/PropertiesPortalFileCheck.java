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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.PropertiesSourceProcessor;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.net.URL;

import org.apache.commons.io.IOUtils;

/**
 * @author Hugo Huijser
 */
public class PropertiesPortalFileCheck extends BaseFileCheck {

	public PropertiesPortalFileCheck() throws Exception {
		_portalPortalPropertiesContent = _getPortalPortalPropertiesContent();
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (((isPortalSource() || isSubrepository()) &&
			 fileName.matches(".*portal-legacy-.*\\.properties")) ||
			(!isPortalSource() && !isSubrepository() &&
			 fileName.endsWith("portal.properties"))) {

			_checkPortalProperties(fileName, content);
		}

		return content;
	}

	private void _checkPortalProperties(String fileName, String content)
		throws Exception {

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineCount = 0;

			String line = null;

			int previousPos = -1;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				int pos = line.indexOf(CharPool.EQUAL);

				if (pos == -1) {
					continue;
				}

				String property = StringUtil.trim(line.substring(0, pos + 1));

				pos = _portalPortalPropertiesContent.indexOf(
					StringPool.FOUR_SPACES + property);

				if (pos == -1) {
					continue;
				}

				if (pos < previousPos) {
					addMessage(
						fileName,
						"Follow order as in portal-impl/src/portal.properties",
						lineCount);
				}

				previousPos = pos;
			}
		}
	}

	private String _getPortalPortalPropertiesContent() throws Exception {
		String portalPortalPropertiesContent = null;

		if (isPortalSource()) {
			File file = getFile(
				"portal-impl/src/portal.properties",
				ToolsUtil.PORTAL_MAX_DIR_LEVEL);

			return FileUtil.read(file);
		}

		ClassLoader classLoader =
			PropertiesSourceProcessor.class.getClassLoader();

		URL url = classLoader.getResource("portal.properties");

		if (url != null) {
			portalPortalPropertiesContent = IOUtils.toString(url);
		}
		else {
			portalPortalPropertiesContent = StringPool.BLANK;
		}

		return portalPortalPropertiesContent;
	}

	private final String _portalPortalPropertiesContent;

}