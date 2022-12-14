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

package com.liferay.change.tracking.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CTCollectionTemplate&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see CTCollectionTemplate
 * @generated
 */
public class CTCollectionTemplateTable
	extends BaseTable<CTCollectionTemplateTable> {

	public static final CTCollectionTemplateTable INSTANCE =
		new CTCollectionTemplateTable();

	public final Column<CTCollectionTemplateTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CTCollectionTemplateTable, Long>
		ctCollectionTemplateId = createColumn(
			"ctCollectionTemplateId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CTCollectionTemplateTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTCollectionTemplateTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTCollectionTemplateTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CTCollectionTemplateTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CTCollectionTemplateTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CTCollectionTemplateTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CTCollectionTemplateTable() {
		super("CTCollectionTemplate", CTCollectionTemplateTable::new);
	}

}