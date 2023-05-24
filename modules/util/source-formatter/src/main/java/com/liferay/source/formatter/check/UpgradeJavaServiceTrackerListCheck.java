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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

/**
 * @author Michael Cavalcanti
 */
public class UpgradeJavaServiceTrackerListCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		boolean checkNextLine = false;
		String[] lines = content.split(StringPool.NEW_LINE);

		for (String line : lines) {
			if (line.contains("ServiceTrackerList") || checkNextLine) {
				if (line.contains(StringPool.LESS_THAN)) {
					checkNextLine = false;

					if (_hasTwoElements(line)) {
						content = StringUtil.replace(
							content, line, _replaceLine(line));
					}
				}
				else {
					checkNextLine = !checkNextLine;
				}
			}
		}

		return content;
	}

	private boolean _hasTwoElements(String line) {
		if ((line.indexOf(StringPool.COMMA) > line.indexOf(
				StringPool.LESS_THAN)) &&
			(line.indexOf(StringPool.COMMA) < line.indexOf(
				StringPool.GREATER_THAN))) {

			return true;
		}

		return false;
	}

	private String _replaceLine(String line) {
		String newLine = StringPool.BLANK;
		char[] lineArray = line.toCharArray();

		for (int i = 0; i < line.length(); i++) {
			if ((i < line.indexOf(StringPool.COMMA)) ||
				(i >= line.indexOf(StringPool.GREATER_THAN))) {

				newLine += lineArray[i];
			}
		}

		return newLine;
	}

}