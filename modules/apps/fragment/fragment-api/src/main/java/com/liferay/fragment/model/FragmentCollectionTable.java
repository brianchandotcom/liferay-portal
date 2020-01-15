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

package com.liferay.fragment.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the FragmentCollection.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FragmentCollectionTable extends Table<FragmentCollectionTable> {

	public static final FragmentCollectionTable INSTANCE =
		new FragmentCollectionTable();

	public final Column<FragmentCollectionTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<FragmentCollectionTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<FragmentCollectionTable, Long> fragmentCollectionId =
		createColumn("fragmentCollectionId", Long.class, Types.BIGINT);
	public final Column<FragmentCollectionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<FragmentCollectionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<FragmentCollectionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<FragmentCollectionTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<FragmentCollectionTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<FragmentCollectionTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<FragmentCollectionTable, String> fragmentCollectionKey =
		createColumn("fragmentCollectionKey", String.class, Types.VARCHAR);
	public final Column<FragmentCollectionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<FragmentCollectionTable, String> description =
		createColumn("description", String.class, Types.VARCHAR);
	public final Column<FragmentCollectionTable, Date> lastPublishDate =
		createColumn("lastPublishDate", Date.class, Types.TIMESTAMP);

	private FragmentCollectionTable() {
		super("FragmentCollection", FragmentCollectionTable::new);
	}

}