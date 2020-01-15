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

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the Phone.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PhoneTable extends Table<PhoneTable> {

	public static final PhoneTable INSTANCE = new PhoneTable();

	public final Column<PhoneTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<PhoneTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<PhoneTable, Long> phoneId = createColumn(
		"phoneId", Long.class, Types.BIGINT);
	public final Column<PhoneTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<PhoneTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<PhoneTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<PhoneTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<PhoneTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<PhoneTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);
	public final Column<PhoneTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<PhoneTable, String> number = createColumn(
		"number_", String.class, Types.VARCHAR);
	public final Column<PhoneTable, String> extension = createColumn(
		"extension", String.class, Types.VARCHAR);
	public final Column<PhoneTable, Long> typeId = createColumn(
		"typeId", Long.class, Types.BIGINT);
	public final Column<PhoneTable, Boolean> primary = createColumn(
		"primary_", Boolean.class, Types.BOOLEAN);

	private PhoneTable() {
		super("Phone", PhoneTable::new);
	}

}