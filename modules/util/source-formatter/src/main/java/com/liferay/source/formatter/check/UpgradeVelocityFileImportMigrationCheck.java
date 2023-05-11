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
import com.liferay.source.formatter.check.constants.VelocityMigrationConstants;
import com.liferay.source.formatter.check.util.VelocityMigrationUtil;

/**
 * @author Nícolas Moura
 */
public class UpgradeVelocityFileImportMigrationCheck
	extends BaseUpgradeVelocityMigrationCheck {

	@Override
	protected String migrateContent(String content) {
		String[] lines = StringUtil.splitLines(content);

		for (String line : lines) {
			if (line.contains(VelocityMigrationConstants.VELOCITY_PARSE)) {
				String newLine = line.replace(
					VelocityMigrationConstants.VELOCITY_PARSE, "<#include");

				newLine = VelocityMigrationUtil.removeFirstParenthesis(newLine);
				newLine = StringUtil.replace(
					newLine, EXTENSION_VELOCITY, EXTENSION_FREEMARKER);
				newLine = StringUtil.replaceLast(
					newLine, CharPool.CLOSE_PARENTHESIS,
					CharPool.SPACE +
						VelocityMigrationConstants.FREEMARKER_TAG_END);

				content = StringUtil.replace(content, line, newLine);
			}
		}

		return content;
	}

}