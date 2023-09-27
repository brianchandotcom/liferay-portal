/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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