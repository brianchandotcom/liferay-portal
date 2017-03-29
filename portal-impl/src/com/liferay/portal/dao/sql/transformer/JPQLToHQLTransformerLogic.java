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

package com.liferay.portal.dao.sql.transformer;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manuel de la Peña
 */
public class JPQLToHQLTransformerLogic {

	public static final Function<String, String> getCountFunction() {
		return (String sql) -> {
			Matcher matcher = _jpqlCountPattern.matcher(sql);

			if (matcher.find()) {
				String countExpression = matcher.group(1);
				String entityAlias = matcher.group(3);

				if (entityAlias.equals(countExpression)) {
					return matcher.replaceFirst(_HQL_COUNT_SQL);
				}
			}

			return sql;
		};
	}

	private static final String _HQL_COUNT_SQL = "SELECT COUNT(*) FROM $2 $3";

	private static final Pattern _jpqlCountPattern = Pattern.compile(
		"SELECT COUNT\\((\\S+)\\) FROM (\\S+) (\\S+)");

}