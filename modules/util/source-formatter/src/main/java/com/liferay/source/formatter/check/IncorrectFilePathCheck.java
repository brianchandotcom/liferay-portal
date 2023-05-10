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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;

/**
 * @author Seiphon Wang
 */
public class IncorrectFilePathCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		String rootDirName = SourceUtil.getRootDirName(absolutePath);

		if (Validator.isNull(rootDirName)) {
			return content;
		}

		String relativePath = absolutePath.substring(rootDirName.length());

		for (String path : relativePath.split("/")) {
			if (Validator.isNull(path)) {
				continue;
			}

			if (path.endsWith(StringPool.SPACE) ||
				path.startsWith(StringPool.SPACE)) {

				addMessage(
					fileName,
					StringBundler.concat(
						"Do not add leading/trailing spaces in file or folder ",
						"names '", path, "'"));
			}

			for (String illegalCharacter : _ILLEGAL_CHARACTERS) {
				if (path.contains(illegalCharacter)) {
					addMessage(
						fileName,
						StringBundler.concat(
							"Do not use '", illegalCharacter, "' in file or ",
							"folder names '", path, "'"));
				}
			}
		}

		return content;
	}

	private static final String[] _ILLEGAL_CHARACTERS = {
		StringPool.BACK_SLASH, StringPool.COLON, StringPool.FORWARD_SLASH,
		StringPool.GREATER_THAN, StringPool.LESS_THAN, StringPool.PIPE,
		StringPool.QUESTION, StringPool.QUOTE, StringPool.STAR
	};

}