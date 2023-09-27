/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;TemplateEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see TemplateEntry
 * @generated
 */
public class TemplateEntryTable extends BaseTable<TemplateEntryTable> {

	public static final TemplateEntryTable INSTANCE = new TemplateEntryTable();

	public final Column<TemplateEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<TemplateEntryTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<TemplateEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<TemplateEntryTable, Long> templateEntryId =
		createColumn(
			"templateEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<TemplateEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TemplateEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TemplateEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TemplateEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<TemplateEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<TemplateEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<TemplateEntryTable, Long> ddmTemplateId = createColumn(
		"ddmTemplateId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TemplateEntryTable, String> infoItemClassName =
		createColumn(
			"infoItemClassName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<TemplateEntryTable, String> infoItemFormVariationKey =
		createColumn(
			"infoItemFormVariationKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<TemplateEntryTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private TemplateEntryTable() {
		super("TemplateEntry", TemplateEntryTable::new);
	}

}