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

package com.liferay.friendly.url.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;FriendlyURLEntryLocalization&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryLocalization
 * @generated
 */
public class FriendlyURLEntryLocalizationTable
	extends BaseTable<FriendlyURLEntryLocalizationTable> {

	public static final FriendlyURLEntryLocalizationTable INSTANCE =
		new FriendlyURLEntryLocalizationTable();

	public final Column<FriendlyURLEntryLocalizationTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<FriendlyURLEntryLocalizationTable, Long>
		ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<FriendlyURLEntryLocalizationTable, Long>
		friendlyURLEntryLocalizationId = createColumn(
			"friendlyURLEntryLocalizationId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<FriendlyURLEntryLocalizationTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FriendlyURLEntryLocalizationTable, Long>
		friendlyURLEntryId = createColumn(
			"friendlyURLEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<FriendlyURLEntryLocalizationTable, String> languageId =
		createColumn(
			"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FriendlyURLEntryLocalizationTable, String> urlTitle =
		createColumn(
			"urlTitle", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FriendlyURLEntryLocalizationTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FriendlyURLEntryLocalizationTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FriendlyURLEntryLocalizationTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private FriendlyURLEntryLocalizationTable() {
		super(
			"FriendlyURLEntryLocalization",
			FriendlyURLEntryLocalizationTable::new);
	}

}