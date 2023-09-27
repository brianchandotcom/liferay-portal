/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;WebDAVProps&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see WebDAVProps
 * @generated
 */
public class WebDAVPropsTable extends BaseTable<WebDAVPropsTable> {

	public static final WebDAVPropsTable INSTANCE = new WebDAVPropsTable();

	public final Column<WebDAVPropsTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<WebDAVPropsTable, Long> webDavPropsId = createColumn(
		"webDavPropsId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<WebDAVPropsTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WebDAVPropsTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WebDAVPropsTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WebDAVPropsTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WebDAVPropsTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WebDAVPropsTable, Clob> props = createColumn(
		"props", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private WebDAVPropsTable() {
		super("WebDAVProps", WebDAVPropsTable::new);
	}

}