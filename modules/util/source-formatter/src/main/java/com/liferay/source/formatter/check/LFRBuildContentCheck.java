/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

/**
 * @author Peter Shin
 */
public class LFRBuildContentCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (_isNonemptyMarkerFileName(absolutePath)) {
			content = StringUtil.trim(content);
		}
		else {
			content = StringPool.BLANK;
		}

		return content;
	}

	private boolean _isNonemptyMarkerFileName(String absolutePath) {
		List<String> nonemptyMarkerFileNames = getAttributeValues(
			_NONEMPTY_MARKER_FILE_NAMES_KEY, absolutePath);

		for (String nonemptyMarkerFileName : nonemptyMarkerFileNames) {
			if (absolutePath.endsWith(nonemptyMarkerFileName)) {
				return true;
			}
		}

		return false;
	}

	private static final String _NONEMPTY_MARKER_FILE_NAMES_KEY =
		"nonemptyMarkerFileNames";

}