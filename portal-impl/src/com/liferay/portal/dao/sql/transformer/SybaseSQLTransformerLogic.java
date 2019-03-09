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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;

/**
 * @author Manuel de la Peña
 */
public class SybaseSQLTransformerLogic extends BaseSQLTransformerLogic {

	public SybaseSQLTransformerLogic(DB db) {
		List<Function<String, String>> functions = new ArrayList<>(
			Arrays.asList(
				this::replaceBitwiseCheck,
				sql -> replaceBoolean(
					sql, db.getTemplateFalse(), db.getTemplateTrue()),
				this::_replaceCastClobText, this::_replaceCastLong,
				this::_replaceCastText, this::replaceConcat,
				this::replaceDropTableIfExistsText, this::replaceInstr,
				this::replaceIntegerDivision, this::replaceLength,
				this::replaceMod, this::replaceNullDate, this::replaceSubstr,
				this::_replaceCrossJoin, this::_replaceReplace));

		if (!db.isSupportsStringCaseSensitiveQuery()) {
			functions.add(this::replaceLower);
		}

		setFunctions(functions);
	}

	private String _replaceCastClobText(String sql) {
		Matcher matcher = castClobTestPattern.matcher(sql);

		return matcher.replaceAll("CAST($1 AS NVARCHAR(5461))");
	}

	private String _replaceCastLong(String sql) {
		Matcher matcher = castLongPattern.matcher(sql);

		return matcher.replaceAll("CONVERT(BIGINT, $1)");
	}

	private String _replaceCastText(String sql) {
		Matcher matcher = castTextPattern.matcher(sql);

		return matcher.replaceAll("CAST($1 AS NVARCHAR(5461))");
	}

	private String _replaceCrossJoin(String sql) {
		return StringUtil.replace(sql, "CROSS JOIN", StringPool.COMMA);
	}

	private String _replaceReplace(String sql) {
		return sql.replaceAll("(?i)replace\\(", "str_replace(");
	}

}