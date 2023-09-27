/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tamyris Bernardo
 */
public class UpgradeGetImagePreviewURLMethodCheck
	extends BaseUpgradeMatcherReplacementCheck {

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

		return newContent;
	}

	@Override
	protected String formatMatcherIteration(
		String content, String newContent, Matcher matcher) {

		String methodCall = matcher.group();

		return StringUtil.replace(
			newContent, methodCall,
			StringUtil.replace(methodCall, "DLUtil", "_dlURLHelper"));
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {"com.liferay.document.library.util.DLURLHelper"};
	}

	@Override
	protected Pattern getPattern() {
		return Pattern.compile("DLUtil\\.\\s*getImagePreviewURL\\(");
	}

	@Override
	protected String[] getValidExtensions() {
		return new String[] {"java", "jsp"};
	}

}