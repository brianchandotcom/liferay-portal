/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;KBArticle&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see KBArticle
 * @generated
 */
public class KBArticleTable extends BaseTable<KBArticleTable> {

	public static final KBArticleTable INSTANCE = new KBArticleTable();

	public final Column<KBArticleTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<KBArticleTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KBArticleTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Long> kbArticleId = createColumn(
		"kbArticleId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KBArticleTable, Long> resourcePrimKey = createColumn(
		"resourcePrimKey", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Long> rootResourcePrimKey =
		createColumn(
			"rootResourcePrimKey", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Long> parentResourceClassNameId =
		createColumn(
			"parentResourceClassNameId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Long> parentResourcePrimKey =
		createColumn(
			"parentResourcePrimKey", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Long> kbFolderId = createColumn(
		"kbFolderId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Integer> version = createColumn(
		"version", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, String> urlTitle = createColumn(
		"urlTitle", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Clob> content = createColumn(
		"content", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Double> priority = createColumn(
		"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, String> sections = createColumn(
		"sections", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Boolean> latest = createColumn(
		"latest", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Boolean> main = createColumn(
		"main", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, String> sourceURL = createColumn(
		"sourceURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Date> displayDate = createColumn(
		"displayDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Date> expirationDate = createColumn(
		"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Date> reviewDate = createColumn(
		"reviewDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Long> statusByUserId = createColumn(
		"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, String> statusByUserName = createColumn(
		"statusByUserName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KBArticleTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private KBArticleTable() {
		super("KBArticle", KBArticleTable::new);
	}

}