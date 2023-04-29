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
import com.liferay.source.formatter.check.util.VelocityMigrationUtil;

/**
 * @author Nícolas Moura
 */
public class UpgradeVelocityIfStatementsMigrationCheck
	extends BaseUpgradeVelocityMigrationCheck {

	@Override
	protected String migrateContent(String content) {
		String[] lines = StringUtil.splitLines(content);

		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];

			String newLine = line;

			boolean parenthesis = false;

			if (line.contains(VelocityMigrationConstants.VELOCITY_IF_START) &&
				VelocityMigrationUtil.isVelocityStatement(
					line, VelocityMigrationConstants.VELOCITY_IF_START)) {

				parenthesis = true;

				newLine = StringUtil.replace(
					newLine, VelocityMigrationConstants.VELOCITY_IF_START,
					VelocityMigrationConstants.FREEMARKER_IF_START);

				VelocityMigrationUtil.replaceStatementEnd(
					i, lines, VelocityMigrationConstants.VELOCITY_IF_START);
			}
			else if (line.contains(
						VelocityMigrationConstants.VELOCITY_ELSEIF_START) &&
					 VelocityMigrationUtil.isVelocityStatement(
						 line,
						 VelocityMigrationConstants.VELOCITY_ELSEIF_START)) {

				parenthesis = true;

				newLine = StringUtil.replace(
					newLine, VelocityMigrationConstants.VELOCITY_ELSEIF_START,
					"<#elseif");
			}
			else if (line.contains(
						VelocityMigrationConstants.VELOCITY_ELSE_START) &&
					 !line.contains(
						 VelocityMigrationConstants.VELOCITY_ELSEIF_START) &&
					 VelocityMigrationUtil.isVelocityStatement(
						 line,
						 VelocityMigrationConstants.VELOCITY_ELSE_START)) {

				newLine = StringUtil.replace(
					newLine, VelocityMigrationConstants.VELOCITY_ELSE_START,
					"<#else>");
			}

			if (parenthesis) {
				if (_hasBreakLine(newLine)) {
					String nextLine = lines[i + 1];

					lines[i + 1] = StringUtil.replaceLast(
						nextLine, CharPool.CLOSE_PARENTHESIS,
						CharPool.GREATER_THAN);
				}
				else {
					newLine = StringUtil.replaceLast(
						newLine, CharPool.CLOSE_PARENTHESIS,
						CharPool.GREATER_THAN);
				}

				newLine = VelocityMigrationUtil.removeFirstParenthesis(newLine);
			}

			lines[i] = newLine;
		}

		return com.liferay.petra.string.StringUtil.merge(
			lines, StringPool.NEW_LINE);
	}

	private boolean _hasBreakLine(String line) {
		if (StringUtil.count(line, CharPool.OPEN_PARENTHESIS) >
				StringUtil.count(line, CharPool.CLOSE_PARENTHESIS)) {

			return true;
		}

		return false;
	}

}