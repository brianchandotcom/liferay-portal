/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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