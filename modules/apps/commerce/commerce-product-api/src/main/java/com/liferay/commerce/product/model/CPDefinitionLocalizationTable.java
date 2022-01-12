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

import java.sql.Clob;
import java.sql.Types;

/**
 * The table class for the &quot;CPDefinitionLocalization&quot; database table.
 *
 * @author Marco Leo
 * @see CPDefinitionLocalization
 * @generated
 */
public class CPDefinitionLocalizationTable
	extends BaseTable<CPDefinitionLocalizationTable> {

	public static final CPDefinitionLocalizationTable INSTANCE =
		new CPDefinitionLocalizationTable();

	public final Column<CPDefinitionLocalizationTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CPDefinitionLocalizationTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CPDefinitionLocalizationTable, Long>
		cpDefinitionLocalizationId = createColumn(
			"cpDefinitionLocalizationId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CPDefinitionLocalizationTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionLocalizationTable, Long> CPDefinitionId =
		createColumn(
			"CPDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionLocalizationTable, String> languageId =
		createColumn(
			"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionLocalizationTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionLocalizationTable, String>
		shortDescription = createColumn(
			"shortDescription", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CPDefinitionLocalizationTable, Clob> description =
		createColumn(
			"description", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionLocalizationTable, String> metaTitle =
		createColumn(
			"metaTitle", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CPDefinitionLocalizationTable, String> metaDescription =
		createColumn(
			"metaDescription", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CPDefinitionLocalizationTable, String> metaKeywords =
		createColumn(
			"metaKeywords", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CPDefinitionLocalizationTable() {
		super("CPDefinitionLocalization", CPDefinitionLocalizationTable::new);
	}

}