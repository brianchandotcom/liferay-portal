/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tamyris Bernardo
 */
public class UpgradeGetImagePreviewURLMethodCheck extends BaseUpgradeCheck {

	@Override
	protected String afterFormat(
		String fileName, String absolutePath, String content,
		String newContent) {

		if (fileName.endsWith(".java")) {
			newContent = addNewImports(newContent);
			newContent = StringUtil.replaceLast(
				newContent, CharPool.CLOSE_CURLY_BRACE,
				"\t@Reference\n\tprivate DLURLHelper _dlURLHelper;\n\n}");
		}
		else {
			newContent = StringBundler.concat(
				"<%@ page import=\"com.liferay.document.library.util.",
				"DLURLHelperUtil\" %>\n\n", newContent);
		}

		return newContent;
	}

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		String newContent = content;

		Matcher matcher = _getImagePreviewPattern.matcher(content);

		while (matcher.find()) {
			String methodCall = matcher.group();

			if (fileName.endsWith(".java")) {
				newContent = StringUtil.replace(
					newContent, methodCall,
					StringUtil.replace(methodCall, "DLUtil", "_dlURLHelper"));
			}
			else {
				newContent = StringUtil.replace(
					newContent, methodCall,
					StringUtil.replace(
						methodCall, "DLUtil", "DLURLHelperUtil"));
			}
		}

		return newContent;
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {"com.liferay.document.library.util.DLURLHelper"};
	}

	@Override
	protected String[] getValidExtensions() {
		return new String[] {"java", "jsp"};
	}

	private static final Pattern _getImagePreviewPattern = Pattern.compile(
		"DLUtil\\.\\s*getImagePreviewURL\\(");

}