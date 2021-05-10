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

package com.liferay.frontend.view.state.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;FrontendViewStateEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see FrontendViewStateEntry
 * @generated
 */
public class FrontendViewStateEntryTable
	extends BaseTable<FrontendViewStateEntryTable> {

	public static final FrontendViewStateEntryTable INSTANCE =
		new FrontendViewStateEntryTable();

	public final Column<FrontendViewStateEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<FrontendViewStateEntryTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FrontendViewStateEntryTable, Long>
		frontendViewStateEntryId = createColumn(
			"frontendViewStateEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<FrontendViewStateEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FrontendViewStateEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FrontendViewStateEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FrontendViewStateEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FrontendViewStateEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FrontendViewStateEntryTable, Clob> viewState =
		createColumn("viewState", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private FrontendViewStateEntryTable() {
		super("FrontendViewStateEntry", FrontendViewStateEntryTable::new);
	}

}