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

/**
 * The table class for the Country.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CountryTable extends Table<CountryTable> {

	public static final CountryTable INSTANCE = new CountryTable();

	public final Column<CountryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<CountryTable, Long> countryId = createColumn(
		"countryId", Long.class, Types.BIGINT);
	public final Column<CountryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<CountryTable, String> a2 = createColumn(
		"a2", String.class, Types.VARCHAR);
	public final Column<CountryTable, String> a3 = createColumn(
		"a3", String.class, Types.VARCHAR);
	public final Column<CountryTable, String> number = createColumn(
		"number_", String.class, Types.VARCHAR);
	public final Column<CountryTable, String> idd = createColumn(
		"idd_", String.class, Types.VARCHAR);
	public final Column<CountryTable, Boolean> zipRequired = createColumn(
		"zipRequired", Boolean.class, Types.BOOLEAN);
	public final Column<CountryTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN);

	private CountryTable() {
		super("Country", CountryTable::new);
	}

}