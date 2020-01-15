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
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

/**
 * The table class for the JournalContentSearch.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class JournalContentSearchTable
	extends Table<JournalContentSearchTable> {

	public static final JournalContentSearchTable INSTANCE =
		new JournalContentSearchTable();

	public final Column<JournalContentSearchTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<JournalContentSearchTable, Long> contentSearchId =
		createColumn("contentSearchId", Long.class, Types.BIGINT);
	public final Column<JournalContentSearchTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<JournalContentSearchTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<JournalContentSearchTable, Boolean> privateLayout =
		createColumn("privateLayout", Boolean.class, Types.BOOLEAN);
	public final Column<JournalContentSearchTable, Long> layoutId =
		createColumn("layoutId", Long.class, Types.BIGINT);
	public final Column<JournalContentSearchTable, String> portletId =
		createColumn("portletId", String.class, Types.VARCHAR);
	public final Column<JournalContentSearchTable, String> articleId =
		createColumn("articleId", String.class, Types.VARCHAR);

	private JournalContentSearchTable() {
		super("JournalContentSearch", JournalContentSearchTable::new);
	}

}