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

package com.liferay.changeset.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the ChangesetCollection.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ChangesetCollectionTable extends Table<ChangesetCollectionTable> {

	public static final ChangesetCollectionTable INSTANCE =
		new ChangesetCollectionTable();

	public final Column<ChangesetCollectionTable, Long> changesetCollectionId =
		createColumn("changesetCollectionId", Long.class, Types.BIGINT);
	public final Column<ChangesetCollectionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<ChangesetCollectionTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<ChangesetCollectionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<ChangesetCollectionTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<ChangesetCollectionTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<ChangesetCollectionTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<ChangesetCollectionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<ChangesetCollectionTable, String> description =
		createColumn("description", String.class, Types.VARCHAR);

	private ChangesetCollectionTable() {
		super("ChangesetCollection", ChangesetCollectionTable::new);
	}

}