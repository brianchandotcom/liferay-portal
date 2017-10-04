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

package com.liferay.portal.internal.dao.sql.transformer;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Michael Bowerman
 */
public class SQLTransformerHelper {

	public static final String CONCAT_OPEN = "CONCAT(";

	public static String buildConcatSQL(
		String sql, String begin, String middle, String end) {

		int concatIndex = sql.lastIndexOf(CONCAT_OPEN);

		while (concatIndex >= 0) {
			int expressionStartIndex = concatIndex + CONCAT_OPEN.length();

			int expressionEndIndex = expressionStartIndex;

			int endsToAppend = 0;

			StringBundler sb = new StringBundler();

			while ((expressionStartIndex < sql.length()) &&
				   (sql.charAt(expressionEndIndex) !=
					   CharPool.CLOSE_PARENTHESIS)) {

				expressionEndIndex = getEndOfNextExpression(
					sql, expressionStartIndex);

				if (sql.charAt(expressionEndIndex) !=
						CharPool.CLOSE_PARENTHESIS) {

					sb.append(begin);

					endsToAppend++;
				}

				sb.append(
					sql.substring(expressionStartIndex, expressionEndIndex));

				if (sql.charAt(expressionEndIndex) !=
						CharPool.CLOSE_PARENTHESIS) {

					sb.append(middle);
				}

				expressionStartIndex = getFirstNonWhitespaceIndex(
					sql, expressionEndIndex + 1);
			}

			for (int i = 0; i < endsToAppend; i++) {
				sb.append(end);
			}

			StringBundler sqlSB = new StringBundler(3);

			sqlSB.append(sql.substring(0, concatIndex));
			sqlSB.append(sb.toString());
			sqlSB.append(sql.substring(expressionEndIndex + 1));

			sql = sqlSB.toString();

			concatIndex = sql.lastIndexOf(CONCAT_OPEN, concatIndex - 1);
		}

		return sql;
	};

	public static int getEndOfNextExpression(String sql, int index) {
		boolean quote = false;
		int parentheses = 0;

		while (index < sql.length()) {
			if (!quote && (parentheses == 0)) {
				if ((sql.charAt(index) == CharPool.COMMA) ||
					(sql.charAt(index) == CharPool.CLOSE_PARENTHESIS)) {

					return index;
				}
			}

			if (quote) {
				index = sql.indexOf(CharPool.APOSTROPHE, index);

				quote = false;
			}
			else if (sql.charAt(index) == CharPool.APOSTROPHE) {
				quote = true;
			}
			else if (sql.charAt(index) == CharPool.OPEN_PARENTHESIS) {
				parentheses++;
			}
			else if (sql.charAt(index) == CharPool.CLOSE_PARENTHESIS) {
				parentheses--;
			}

			index++;
		}

		return index;
	}

	public static int getFirstNonWhitespaceIndex(String sql, int index) {
		while (index < sql.length()) {
			if (!Character.isWhitespace(sql.charAt(index))) {
				return index;
			}

			index++;
		}

		return index;
	}

}