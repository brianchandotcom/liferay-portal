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

import com.liferay.portal.kernel.dao.db.DB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;

/**
 * @author Manuel de la Peña
 * @author Brian Wing Shun Chan
 */
public class HypersonicSQLTransformerLogic extends BaseSQLTransformerLogic {

	public HypersonicSQLTransformerLogic(DB db) {
		List<Function<String, String>> functions = new ArrayList<>(
			Arrays.asList(
				sql -> replaceBoolean(
					sql, db.getTemplateFalse(), db.getTemplateTrue()),
				this::_replaceCastClobText, this::_replaceCastLong,
				this::_replaceCastText, this::_replaceDropTableIfExistsText,
				this::replaceIntegerDivision, this::replaceNullDate));

		if (!db.isSupportsStringCaseSensitiveQuery()) {
			functions.add(this::replaceLower);
		}

		setFunctions(functions);
	}

	private String _replaceCastClobText(String sql) {
		Matcher matcher = castClobTestPattern.matcher(sql);

		return matcher.replaceAll("CONVERT($1, SQL_VARCHAR)");
	}

	private String _replaceCastLong(String sql) {
		Matcher matcher = castLongPattern.matcher(sql);

		return matcher.replaceAll("CONVERT($1, SQL_BIGINT)");
	}

	private String _replaceCastText(String sql) {
		Matcher matcher = castTextPattern.matcher(sql);

		return matcher.replaceAll("CONVERT($1, SQL_VARCHAR)");
	}

	private String _replaceDropTableIfExistsText(String sql) {
		Matcher matcher = dropTableIfExistsTextPattern.matcher(sql);

		return matcher.replaceAll("DROP TABLE $1 IF EXISTS");
	}

}