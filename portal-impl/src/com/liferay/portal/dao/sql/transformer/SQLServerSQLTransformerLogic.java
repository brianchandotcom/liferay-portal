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

import java.util.regex.Matcher;

/**
 * @author Manuel de la Peña
 */
public class SQLServerSQLTransformerLogic extends BaseSQLTransformerLogic {

	public SQLServerSQLTransformerLogic(DB db) {
		super(db);

		setFunctions(
			getBitwiseCheckFunction(), getBooleanFunction(),
			getCastClobTextFunction(), getCastLongFunction(),
			getCastTextFunction(), getInstrFunction(),
			getIntegerDivisionFunction(), getModFunction(),
			getNullDateFunction(), getSubstrFunction());
	}

	@Override
	protected String replaceCastText(Matcher matcher) {
		return matcher.replaceAll("CAST($1 AS NVARCHAR(MAX))");
	}

}