/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;StyleBookTokenSet&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookTokenSet
 * @generated
 */
public class StyleBookTokenSetTable extends BaseTable<StyleBookTokenSetTable> {

	public static final StyleBookTokenSetTable INSTANCE =
		new StyleBookTokenSetTable();

	public final Column<StyleBookTokenSetTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<StyleBookTokenSetTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<StyleBookTokenSetTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<StyleBookTokenSetTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<StyleBookTokenSetTable, Long> styleBookTokenSetId =
		createColumn(
			"styleBookTokenSetId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<StyleBookTokenSetTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<StyleBookTokenSetTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<StyleBookTokenSetTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<StyleBookTokenSetTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<StyleBookTokenSetTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<StyleBookTokenSetTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<StyleBookTokenSetTable, Long> styleBookEntryId =
		createColumn(
			"styleBookEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<StyleBookTokenSetTable, String>
		frontendTokenDefinitionId = createColumn(
			"frontendTokenDefinitionId", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<StyleBookTokenSetTable, String>
		frontendTokenCategoryName = createColumn(
			"frontendTokenCategoryName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<StyleBookTokenSetTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<StyleBookTokenSetTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private StyleBookTokenSetTable() {
		super("StyleBookTokenSet", StyleBookTokenSetTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-919526019