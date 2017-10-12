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
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Michael Bowerman
 * @author Preston Crary
 */
public class SQLFunctionTransformer {

	public SQLFunctionTransformer(
		String functionPrefix, String replacementPrefix,
		String replacementDelimiter, String replacementSuffix) {

		if (replacementDelimiter.contains(functionPrefix)) {
			throw new IllegalArgumentException(replacementDelimiter);
		}

		if (replacementSuffix.contains(functionPrefix)) {
			throw new IllegalArgumentException(replacementSuffix);
		}

		_functionPrefix = functionPrefix;
		_replacementPrefix = replacementPrefix;
		_replacementDelimiter = replacementDelimiter;
		_replacementSuffix = replacementSuffix;
	}

	public String transform(String sql) {
		int pos = sql.indexOf(_functionPrefix);

		if (pos < 0) {
			return sql;
		}

		StringBuilder sb = new StringBuilder(sql);

		while (pos >= 0) {
			sb.replace(pos, pos + _functionPrefix.length(), _replacementPrefix);

			int next = pos + _replacementPrefix.length();

			int openParentheses = 1;

			while (true) {
				if (next == sb.length()) {
					throw new IllegalArgumentException(
						"Unclosed function in: " + sql);
				}

				char c = sb.charAt(next);

				if (c == CharPool.COMMA) {
					if (openParentheses == 1) {
						sb.replace(next, next + 1, _replacementDelimiter);

						next = next + _replacementDelimiter.length();
					}
				}
				else if (c == CharPool.CLOSE_PARENTHESIS) {
					openParentheses--;

					if (openParentheses == 0) {
						break;
					}
				}
				else if (c == CharPool.OPEN_PARENTHESIS) {
					openParentheses++;
				}
				else if (c == CharPool.APOSTROPHE) {
					next = sb.indexOf(StringPool.APOSTROPHE, next + 1);

					if (next < 0) {
						throw new IllegalArgumentException(
							"Unclosed string literal in: " + sql);
					}
				}

				next++;
			}

			sb.replace(next, next + 1, _replacementSuffix);

			pos = sb.indexOf(
				_functionPrefix, pos + _replacementPrefix.length() + 1);
		}

		return sb.toString();
	}

	private final String _functionPrefix;
	private final String _replacementDelimiter;
	private final String _replacementPrefix;
	private final String _replacementSuffix;

}