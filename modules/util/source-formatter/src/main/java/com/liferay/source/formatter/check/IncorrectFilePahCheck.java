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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.util.SourceUtil;

/**
 * @author Seiphon Wang
 */
public class IncorrectFilePahCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

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

			for (char charactor : _ILLEGALCHARACTORS) {
				if (path.contains(String.valueOf(charactor))) {
					addMessage(
						fileName,
						"A file name can not contain any of the following " +
							"charactors: \\ / : * \" < > |");
				}
			}
		}

		return content;
	}

	private static final char[] _ILLEGALCHARACTORS = {
		CharPool.BACK_SLASH, CharPool.COLON, CharPool.FORWARD_SLASH,
		CharPool.GREATER_THAN, CharPool.LESS_THAN, CharPool.PIPE,
		CharPool.QUESTION, CharPool.QUOTE, CharPool.SPACE, CharPool.STAR
	};

}