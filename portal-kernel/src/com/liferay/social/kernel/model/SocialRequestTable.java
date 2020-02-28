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
 * The table class for the &quot;SocialRequest&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SocialRequest
 * @generated
 */
public class SocialRequestTable extends BaseTable<SocialRequestTable> {

	public static final SocialRequestTable INSTANCE = new SocialRequestTable();

	public final Column<SocialRequestTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SocialRequestTable, Long> requestId = createColumn(
		"requestId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SocialRequestTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialRequestTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialRequestTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialRequestTable, Long> createDate = createColumn(
		"createDate", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialRequestTable, Long> modifiedDate = createColumn(
		"modifiedDate", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialRequestTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialRequestTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialRequestTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SocialRequestTable, String> extraData = createColumn(
		"extraData", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SocialRequestTable, Long> receiverUserId = createColumn(
		"receiverUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SocialRequestTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private SocialRequestTable() {
		super("SocialRequest", SocialRequestTable::new);
	}

}