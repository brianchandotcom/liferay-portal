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