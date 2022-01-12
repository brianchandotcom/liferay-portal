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

package com.liferay.commerce.product.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CPDSpecificationOptionValue&quot; database table.
 *
 * @author Marco Leo
 * @see CPDefinitionSpecificationOptionValue
 * @generated
 */
public class CPDefinitionSpecificationOptionValueTable
	extends BaseTable<CPDefinitionSpecificationOptionValueTable> {

	public static final CPDefinitionSpecificationOptionValueTable INSTANCE =
		new CPDefinitionSpecificationOptionValueTable();

	public final Column<CPDefinitionSpecificationOptionValueTable, Long>
		mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CPDefinitionSpecificationOptionValueTable, Long>
		ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CPDefinitionSpecificationOptionValueTable, String>
		uuid = createColumn(
			"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionSpecificationOptionValueTable, Long>
		CPDefinitionSpecificationOptionValueId = createColumn(
			"CPDSpecificationOptionValueId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CPDefinitionSpecificationOptionValueTable, Long>
		groupId = createColumn(
			"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionSpecificationOptionValueTable, Long>
		companyId = createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionSpecificationOptionValueTable, Long>
		userId = createColumn(
			"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionSpecificationOptionValueTable, String>
		userName = createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionSpecificationOptionValueTable, Date>
		createDate = createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionSpecificationOptionValueTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionSpecificationOptionValueTable, Long>
		CPDefinitionId = createColumn(
			"CPDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionSpecificationOptionValueTable, Long>
		CPSpecificationOptionId = createColumn(
			"CPSpecificationOptionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CPDefinitionSpecificationOptionValueTable, Long>
		CPOptionCategoryId = createColumn(
			"CPOptionCategoryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CPDefinitionSpecificationOptionValueTable, String>
		value = createColumn(
			"value", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionSpecificationOptionValueTable, Double>
		priority = createColumn(
			"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionSpecificationOptionValueTable, Date>
		lastPublishDate = createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private CPDefinitionSpecificationOptionValueTable() {
		super(
			"CPDSpecificationOptionValue",
			CPDefinitionSpecificationOptionValueTable::new);
	}

}