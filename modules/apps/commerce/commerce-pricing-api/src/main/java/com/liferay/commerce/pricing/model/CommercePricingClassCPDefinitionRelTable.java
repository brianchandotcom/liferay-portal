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

package com.liferay.commerce.pricing.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CPricingClassCPDefinitionRel&quot; database table.
 *
 * @author Riccardo Alberti
 * @see CommercePricingClassCPDefinitionRel
 * @generated
 */
public class CommercePricingClassCPDefinitionRelTable
	extends BaseTable<CommercePricingClassCPDefinitionRelTable> {

	public static final CommercePricingClassCPDefinitionRelTable INSTANCE =
		new CommercePricingClassCPDefinitionRelTable();

	public final Column<CommercePricingClassCPDefinitionRelTable, Long>
		mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommercePricingClassCPDefinitionRelTable, Long>
		ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommercePricingClassCPDefinitionRelTable, Long>
		CommercePricingClassCPDefinitionRelId = createColumn(
			"CPricingClassCPDefinitionRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommercePricingClassCPDefinitionRelTable, Long>
		companyId = createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePricingClassCPDefinitionRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePricingClassCPDefinitionRelTable, String>
		userName = createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePricingClassCPDefinitionRelTable, Date>
		createDate = createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePricingClassCPDefinitionRelTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePricingClassCPDefinitionRelTable, Long>
		commercePricingClassId = createColumn(
			"commercePricingClassId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommercePricingClassCPDefinitionRelTable, Long>
		CPDefinitionId = createColumn(
			"CPDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private CommercePricingClassCPDefinitionRelTable() {
		super(
			"CPricingClassCPDefinitionRel",
			CommercePricingClassCPDefinitionRelTable::new);
	}

}