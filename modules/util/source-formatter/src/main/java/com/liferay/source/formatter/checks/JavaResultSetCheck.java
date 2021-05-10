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

import com.liferay.petra.string.StringPool;

/**
 * @author Hugo Huijser
 */
public class JavaResultSetCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		for (int pos1 = -1;;) {
			pos1 = content.indexOf(StringPool.TAB + "try {", pos1 + 1);

			if (pos1 == -1) {
				break;
			}

			int pos2 = content.indexOf(StringPool.TAB + "try {", pos1 + 1);
			int pos3 = content.indexOf("\"select count(", pos1);

			if ((pos2 != -1) && (pos3 != -1) && (pos2 < pos3)) {
				continue;
			}

			int pos4 = content.indexOf("resultSet.getLong(1)", pos1);
			int pos5 = content.indexOf(StringPool.TAB + "finally {", pos1);

			if ((pos3 == -1) || (pos4 == -1) || (pos5 == -1)) {
				break;
			}

			if ((pos3 < pos4) && (pos4 < pos5)) {
				addMessage(fileName, "Use resultSet.getInt(1) for count");
			}
		}

		return content;
	}

}