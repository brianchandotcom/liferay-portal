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

package com.liferay.journal.internal.upgrade.v3_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Tina Tian
 * @generated
 */
public class JournalArticleLocalizationTable {

	public static final String TABLE_NAME = "JournalArticleLocalization";

	public static final Object[][] TABLE_COLUMNS = {
		{"articleLocalizationId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"articlePK", Types.BIGINT},
		{"title", Types.VARCHAR},
		{"description_", Types.VARCHAR},
		{"languageId", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("articleLocalizationId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("articlePK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("languageId", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE = "create table JournalArticleLocalization (articleLocalizationId LONG not null primary key,companyId LONG,articlePK LONG,title VARCHAR(400) null,description_ STRING null,languageId VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table JournalArticleLocalization";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_ACF2560A on JournalArticleLocalization (articlePK, languageId[$COLUMN_LENGTH:75$])"
	};

}