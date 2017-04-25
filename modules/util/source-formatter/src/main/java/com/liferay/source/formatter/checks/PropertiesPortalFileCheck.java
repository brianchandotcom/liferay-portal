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

/**
 * @author Hugo Huijser
 */
public class PropertiesPortalFileCheck extends BaseFileCheck {

	public PropertiesPortalFileCheck(
		boolean portalSource, boolean subrepository,
		String portalPortalPropertiesContent) {

		_portalSource = portalSource;
		_subrepository = subrepository;
		_portalPortalPropertiesContent = portalPortalPropertiesContent;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (((_portalSource || _subrepository) &&
			 fileName.matches(".*portal-legacy-.*\\.properties")) ||
			(!_portalSource && !_subrepository &&
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

	private final String _portalPortalPropertiesContent;
	private final boolean _portalSource;
	private final boolean _subrepository;

}