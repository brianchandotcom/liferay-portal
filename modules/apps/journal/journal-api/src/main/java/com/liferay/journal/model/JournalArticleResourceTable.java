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
 * The table class for the &quot;JournalArticleResource&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleResource
 * @generated
 */
public class JournalArticleResourceTable
	extends BaseTable<JournalArticleResourceTable> {

	public static final JournalArticleResourceTable INSTANCE =
		new JournalArticleResourceTable();

	public final Column<JournalArticleResourceTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<JournalArticleResourceTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<JournalArticleResourceTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<JournalArticleResourceTable, Long> resourcePrimKey =
		createColumn(
			"resourcePrimKey", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<JournalArticleResourceTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JournalArticleResourceTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JournalArticleResourceTable, String> articleId =
		createColumn(
			"articleId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private JournalArticleResourceTable() {
		super("JournalArticleResource", JournalArticleResourceTable::new);
	}

}