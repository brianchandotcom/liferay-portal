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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.util.SourceFormatterUtil;

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
		throws IOException {

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