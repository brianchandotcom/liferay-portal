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

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the WebDAVProps.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WebDAVPropsTable extends Table<WebDAVPropsTable> {

	public static final WebDAVPropsTable INSTANCE = new WebDAVPropsTable();

	public final Column<WebDAVPropsTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<WebDAVPropsTable, Long> webDavPropsId = createColumn(
		"webDavPropsId", Long.class, Types.BIGINT);
	public final Column<WebDAVPropsTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<WebDAVPropsTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<WebDAVPropsTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<WebDAVPropsTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);
	public final Column<WebDAVPropsTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<WebDAVPropsTable, Clob> props = createColumn(
		"props", Clob.class, Types.CLOB);

	private WebDAVPropsTable() {
		super("WebDAVProps", WebDAVPropsTable::new);
	}

}