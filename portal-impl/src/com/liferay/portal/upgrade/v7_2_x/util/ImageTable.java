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

package com.liferay.portal.upgrade.v7_2_x.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class ImageTable {

	public static final String TABLE_NAME = "Image";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"imageId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"modifiedDate", Types.TIMESTAMP},
		{"type_", Types.VARCHAR},
		{"height", Types.INTEGER},
		{"width", Types.INTEGER},
		{"size_", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("imageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("height", Types.INTEGER);

TABLE_COLUMNS_MAP.put("width", Types.INTEGER);

TABLE_COLUMNS_MAP.put("size_", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE = "create table Image (mvccVersion LONG default 0 not null,imageId LONG not null primary key,companyId LONG,modifiedDate DATE null,type_ VARCHAR(75) null,height INTEGER,width INTEGER,size_ INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table Image";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_6A925A4D on Image (size_)"
	};

}