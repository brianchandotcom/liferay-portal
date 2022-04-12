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

import java.util.Objects;

/**
 * @author Seiphon Wang
 */
public class IncorrectFilePahCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		String[] fileNames = absolutePath.split("/");

		for (int i = 1; i < (fileNames.length - 1); i++) {
			String firstChar = fileNames[i].substring(0, 1);

			if (Objects.equals(firstChar, String.valueOf(CharPool.SPACE))) {
				addMessage(fileName, "A file name can not start with a space.");
			}

			String lastChar = fileNames[i].substring(fileNames[i].length() - 1);

			if (Objects.equals(lastChar, String.valueOf(CharPool.SPACE))) {
				addMessage(fileName, "A file name can not end with a space.");
			}

			for (char charactor : _ILLEGALCHARACTORS) {
				if (fileNames[i].contains(String.valueOf(charactor))) {
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