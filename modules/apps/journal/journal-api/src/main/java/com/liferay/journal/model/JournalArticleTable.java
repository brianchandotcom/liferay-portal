/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;JournalArticle&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticle
 * @generated
 */
public class JournalArticleTable extends BaseTable<JournalArticleTable> {

	public static final JournalArticleTable INSTANCE =
		new JournalArticleTable();

	public final Column<JournalArticleTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<JournalArticleTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<JournalArticleTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Long> id = createColumn(
		"id_", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<JournalArticleTable, Long> resourcePrimKey =
		createColumn(
			"resourcePrimKey", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Long> folderId = createColumn(
		"folderId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, String> treePath = createColumn(
		"treePath", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, String> articleId = createColumn(
		"articleId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Double> version = createColumn(
		"version", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, String> urlTitle = createColumn(
		"urlTitle", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Long> DDMStructureId =
		createColumn(
			"DDMStructureId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, String> DDMTemplateKey =
		createColumn(
			"DDMTemplateKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, String> defaultLanguageId =
		createColumn(
			"defaultLanguageId", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, String> layoutUuid = createColumn(
		"layoutUuid", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Date> displayDate = createColumn(
		"displayDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Date> expirationDate =
		createColumn(
			"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Date> reviewDate = createColumn(
		"reviewDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Boolean> indexable = createColumn(
		"indexable", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Boolean> smallImage = createColumn(
		"smallImage", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Long> smallImageId = createColumn(
		"smallImageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Integer> smallImageSource =
		createColumn(
			"smallImageSource", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, String> smallImageURL =
		createColumn(
			"smallImageURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Long> statusByUserId =
		createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<JournalArticleTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private JournalArticleTable() {
		super("JournalArticle", JournalArticleTable::new);
	}

}