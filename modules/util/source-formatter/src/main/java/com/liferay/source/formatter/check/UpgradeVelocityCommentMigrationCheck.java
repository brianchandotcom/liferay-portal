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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.constants.VelocityMigrationConstants;

/**
 * @author Nícolas Moura
 */
public class UpgradeVelocityCommentMigrationCheck
	extends BaseUpgradeVelocityMigrationCheck {

	@Override
	protected String migrateContent(String content) {
		String[] lines = content.split(StringPool.NEW_LINE);

		for (String line : lines) {
			if (line.contains(
					VelocityMigrationConstants.VELOCITY_COMMENT_LINE) &&
				(line.length() != 2)) {

				String newLineStart = line.replace(
					VelocityMigrationConstants.VELOCITY_COMMENT_LINE,
					VelocityMigrationConstants.FREEMARKER_COMMENT_START);

				String newLine =
					newLineStart + CharPool.SPACE +
						VelocityMigrationConstants.FREEMARKER_COMMENT_END;

				if (newLine.contains(
						VelocityMigrationConstants.
							VELOCITY_TEMPLATE_DECLARATION)) {

					newLine = StringUtil.replace(
						newLine,
						VelocityMigrationConstants.
							VELOCITY_TEMPLATE_DECLARATION,
						"FreeMarker Template");
				}

				content = StringUtil.replace(content, line, newLine);
			}
		}

		StringUtil.replace(
			content, "#*", VelocityMigrationConstants.FREEMARKER_COMMENT_START);
		StringUtil.replace(
			content, "*#", VelocityMigrationConstants.FREEMARKER_COMMENT_END);

		return StringUtil.removeSubstring(
			content, VelocityMigrationConstants.VELOCITY_COMMENT_LINE);
	}

}