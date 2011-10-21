/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.persistence;

import java.util.regex.Pattern;

/**
 * @author Shuyang Zhou
 */
public class SQLRecorderUtil {

	public static String stripSQL(String sql) {
		sql = _continueSpace.matcher(sql).replaceAll(" ");
		sql = _openParenthesis.matcher(sql).replaceAll("(");
		sql = _closeParenthesis.matcher(sql).replaceAll(")");

		return sql.trim();
	}

	private static Pattern _closeParenthesis = Pattern.compile("\\s*\\)\\s*");
	private static Pattern _continueSpace = Pattern.compile("\\s+");
	private static Pattern _openParenthesis = Pattern.compile("\\s*\\(\\s*");

}