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

package com.liferay.social.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;SocialRelation&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SocialRelation
 * @generated
 */
public class SocialRelationTable extends BaseTable<SocialRelationTable> {

	public static final SocialRelationTable INSTANCE =
		new SocialRelationTable();

	public final Column<SocialRelationTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SocialRelationTable, Long> relationId = createColumn(
		"relationId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SocialRelationTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialRelationTable, Long> createDate = createColumn(
		"createDate", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialRelationTable, Long> userId1 = createColumn(
		"userId1", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialRelationTable, Long> userId2 = createColumn(
		"userId2", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialRelationTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private SocialRelationTable() {
		super("SocialRelation", SocialRelationTable::new);
	}

}