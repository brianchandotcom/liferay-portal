/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.util.SourceFormatterUtil;

/**
 * @author Peter Shin
 */
public class LFRBuildReadmeCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		int pos = fileName.lastIndexOf(StringPool.SLASH);

		String shortFileName = fileName.substring(pos + 1);

		String readmeMarkdownContent = _getModulesReadmeMarkdownContent(
			absolutePath);

		if (Validator.isNotNull(readmeMarkdownContent) &&
			!readmeMarkdownContent.contains(shortFileName)) {

			String message = StringBundler.concat(
				"Please document the \"", shortFileName, "\" marker file in ",
				"https://github.com/liferay/liferay-portal/blob/",
				getAttributeValue(
					SourceFormatterUtil.GIT_LIFERAY_PORTAL_BRANCH,
					absolutePath),
				"/", _MODULES_README_MARKDOWN_FILE_NAME, "#marker-files");

			addMessage(fileName, message);
		}

		return content;
	}

	private synchronized String _getModulesReadmeMarkdownContent(
			String absolutePath)
		throws Exception {

		if (_modulesReadmeMarkdownContent != null) {
			return _modulesReadmeMarkdownContent;
		}

		_modulesReadmeMarkdownContent = getPortalContent(
			_MODULES_README_MARKDOWN_FILE_NAME, absolutePath);

		return _modulesReadmeMarkdownContent;
	}

	private static final String _MODULES_README_MARKDOWN_FILE_NAME =
		"modules/README.markdown";

	private String _modulesReadmeMarkdownContent;

}