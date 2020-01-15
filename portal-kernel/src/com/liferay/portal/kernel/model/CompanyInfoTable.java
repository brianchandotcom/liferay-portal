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

import java.sql.Clob;
import java.sql.Types;

/**
 * The table class for the CompanyInfo.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CompanyInfoTable extends Table<CompanyInfoTable> {

	public static final CompanyInfoTable INSTANCE = new CompanyInfoTable();

	public final Column<CompanyInfoTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<CompanyInfoTable, Long> companyInfoId = createColumn(
		"companyInfoId", Long.class, Types.BIGINT);
	public final Column<CompanyInfoTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<CompanyInfoTable, Clob> key = createColumn(
		"key_", Clob.class, Types.CLOB);

	private CompanyInfoTable() {
		super("CompanyInfo", CompanyInfoTable::new);
	}

}