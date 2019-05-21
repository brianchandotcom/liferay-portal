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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

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

		String readmeMarkdownContent = _getModulesReadmeMarkdownContent();

		if (Validator.isNotNull(readmeMarkdownContent) &&
			!readmeMarkdownContent.contains(shortFileName)) {

			String message = StringBundler.concat(
				"Please document the \"", shortFileName, "\" marker file in ",
				_MODULES_README_MARKDOWN_URL);

			addMessage(fileName, message);
		}

		return content;
	}

	private synchronized String _getModulesReadmeMarkdownContent()
		throws IOException {

		if (_modulesReadmeMarkdownContent != null) {
			return _modulesReadmeMarkdownContent;
		}

		_modulesReadmeMarkdownContent = GetterUtil.getString(
			getGitContent(_MODULES_README_MARKDOWN_FILE_NAME, "master"));

		return _modulesReadmeMarkdownContent;
	}

	private static final String _MODULES_README_MARKDOWN_FILE_NAME =
		"modules/README.markdown";

	private static final String _MODULES_README_MARKDOWN_URL =
		"https://github.com/liferay/liferay-portal/blob/master/" +
			_MODULES_README_MARKDOWN_FILE_NAME + "#marker-files";

	private String _modulesReadmeMarkdownContent;

}