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
 * The table class for the Address.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AddressTable extends Table<AddressTable> {

	public static final AddressTable INSTANCE = new AddressTable();

	public final Column<AddressTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<AddressTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<AddressTable, Long> addressId = createColumn(
		"addressId", Long.class, Types.BIGINT);
	public final Column<AddressTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<AddressTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<AddressTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<AddressTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<AddressTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<AddressTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);
	public final Column<AddressTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<AddressTable, String> street1 = createColumn(
		"street1", String.class, Types.VARCHAR);
	public final Column<AddressTable, String> street2 = createColumn(
		"street2", String.class, Types.VARCHAR);
	public final Column<AddressTable, String> street3 = createColumn(
		"street3", String.class, Types.VARCHAR);
	public final Column<AddressTable, String> city = createColumn(
		"city", String.class, Types.VARCHAR);
	public final Column<AddressTable, String> zip = createColumn(
		"zip", String.class, Types.VARCHAR);
	public final Column<AddressTable, Long> regionId = createColumn(
		"regionId", Long.class, Types.BIGINT);
	public final Column<AddressTable, Long> countryId = createColumn(
		"countryId", Long.class, Types.BIGINT);
	public final Column<AddressTable, Long> typeId = createColumn(
		"typeId", Long.class, Types.BIGINT);
	public final Column<AddressTable, Boolean> mailing = createColumn(
		"mailing", Boolean.class, Types.BOOLEAN);
	public final Column<AddressTable, Boolean> primary = createColumn(
		"primary_", Boolean.class, Types.BOOLEAN);

	private AddressTable() {
		super("Address", AddressTable::new);
	}

}