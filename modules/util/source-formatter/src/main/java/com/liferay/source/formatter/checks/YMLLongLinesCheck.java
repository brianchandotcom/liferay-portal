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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;

import java.io.IOException;

/**
 * @author Alan Huang
 */
public class YMLLongLinesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		int maxLineLength = 0;

		try {
			maxLineLength = Integer.parseInt(
				getAttributeValue(_MAX_LINE_LENGTH, absolutePath));
		}
		catch (Exception e) {
			return content;
		}

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			int lineNumber = 0;

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.matches(" +-")) {
					continue;
				}

				lineNumber++;

				if (getLineLength(line) > maxLineLength) {
					addMessage(fileName, "> " + maxLineLength, lineNumber);
				}
			}
		}

		return content;
	}

	private static final String _MAX_LINE_LENGTH = "maxLineLength";

}