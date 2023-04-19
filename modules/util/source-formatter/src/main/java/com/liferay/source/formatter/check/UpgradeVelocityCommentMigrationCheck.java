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

/**
 * @author Nícolas Moura
 */
public class UpgradeVelocityCommentMigrationCheck
	extends BaseUpgradeVelocityMigrationCheck {

	@Override
	protected String migrateContent(String content) {
		String[] lines = content.split(StringPool.NEW_LINE);

		for (String line : lines) {
			if (line.contains("##") && (line.length() != 2)) {
				String newLine = line.replace("##", "<#--") + " -->";

				if (newLine.contains("Velocity Transform Template")) {
					newLine = StringUtil.replace(
						newLine, "Velocity Transform Template",
						"FreeMarker Template");
				}

				content = StringUtil.replace(content, line, newLine);
			}
		}

		StringUtil.replace(content, "#*", _COMMENT_START);
		StringUtil.replace(content, "*#", _COMMENT_END);

		return StringUtil.removeSubstring(content, "##");
	}

	private static final String _COMMENT_END = " -->";

	private static final String _COMMENT_START = "<#--";

}