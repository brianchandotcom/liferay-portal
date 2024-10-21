/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;CPSOListTypeDefinitionRel&quot; database table.
 *
 * @author Marco Leo
 * @see CPSOListTypeDefinitionRel
 * @generated
 */
public class CPSOListTypeDefinitionRelTable
	extends BaseTable<CPSOListTypeDefinitionRelTable> {

	public static final CPSOListTypeDefinitionRelTable INSTANCE =
		new CPSOListTypeDefinitionRelTable();

	public final Column<CPSOListTypeDefinitionRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CPSOListTypeDefinitionRelTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CPSOListTypeDefinitionRelTable, Long>
		CPSOListTypeDefinitionRelId = createColumn(
			"CPSOListTypeDefinitionRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CPSOListTypeDefinitionRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPSOListTypeDefinitionRelTable, Long>
		CPSpecificationOptionId = createColumn(
			"CPSpecificationOptionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CPSOListTypeDefinitionRelTable, Long>
		listTypeDefinitionId = createColumn(
			"listTypeDefinitionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private CPSOListTypeDefinitionRelTable() {
		super("CPSOListTypeDefinitionRel", CPSOListTypeDefinitionRelTable::new);
	}

}