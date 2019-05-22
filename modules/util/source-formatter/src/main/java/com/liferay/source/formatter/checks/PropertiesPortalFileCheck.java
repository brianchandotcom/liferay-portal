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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.PropertiesSourceProcessor;

import java.io.IOException;

import java.net.URL;

import org.apache.commons.io.IOUtils;

/**
 * @author Hugo Huijser
 */
public class PropertiesPortalFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (((isPortalSource() || isSubrepository()) &&
			 fileName.matches(".*portal-legacy-.*\\.properties")) ||
			(!isPortalSource() && !isSubrepository() &&
			 fileName.endsWith("portal.properties"))) {

			_checkPortalProperties(fileName, absolutePath, content);
		}

		return content;
	}

	private void _checkPortalProperties(
			String fileName, String absolutePath, String content)
		throws IOException {

		String portalPortalPropertiesContent =
			_getPortalPortalPropertiesContent(absolutePath);

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineNumber = 0;

			String line = null;

			int previousPos = -1;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineNumber++;

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
					addMessage(
						fileName,
						"Follow order as in portal-impl/src/portal.properties",
						lineNumber);
				}

				previousPos = pos;
			}
		}
	}

	private synchronized String _getPortalPortalPropertiesContent(
			String absolutePath)
		throws IOException {

		if (_portalPortalPropertiesContent != null) {
			return _portalPortalPropertiesContent;
		}

		if (isPortalSource() || isSubrepository()) {
			_portalPortalPropertiesContent = getPortalContent(
				"portal-impl/src/portal.properties", absolutePath);

			if (_portalPortalPropertiesContent == null) {
				_portalPortalPropertiesContent = StringPool.BLANK;
			}

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