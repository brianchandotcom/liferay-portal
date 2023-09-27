/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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