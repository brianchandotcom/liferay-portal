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

package com.liferay.journal.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;JournalArticleLocalization&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleLocalization
 * @generated
 */
public class JournalArticleLocalizationTable
	extends BaseTable<JournalArticleLocalizationTable> {

	public static final JournalArticleLocalizationTable INSTANCE =
		new JournalArticleLocalizationTable();

	public final Column<JournalArticleLocalizationTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<JournalArticleLocalizationTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<JournalArticleLocalizationTable, Long>
		articleLocalizationId = createColumn(
			"articleLocalizationId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<JournalArticleLocalizationTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JournalArticleLocalizationTable, Long> articlePK =
		createColumn(
			"articlePK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JournalArticleLocalizationTable, String> title =
		createColumn("title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<JournalArticleLocalizationTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<JournalArticleLocalizationTable, String> languageId =
		createColumn(
			"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private JournalArticleLocalizationTable() {
		super(
			"JournalArticleLocalization", JournalArticleLocalizationTable::new);
	}

}