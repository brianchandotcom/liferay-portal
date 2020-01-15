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

package com.liferay.portal.security.wedeploy.auth.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the WeDeployAuthToken.
 *
 * @author Supritha Sundaram
 * @generated
 */
public class WeDeployAuthTokenTable extends Table<WeDeployAuthTokenTable> {

	public static final WeDeployAuthTokenTable INSTANCE =
		new WeDeployAuthTokenTable();

	public final Column<WeDeployAuthTokenTable, Long> weDeployAuthTokenId =
		createColumn("weDeployAuthTokenId", Long.class, Types.BIGINT);
	public final Column<WeDeployAuthTokenTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<WeDeployAuthTokenTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<WeDeployAuthTokenTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<WeDeployAuthTokenTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<WeDeployAuthTokenTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<WeDeployAuthTokenTable, String> clientId = createColumn(
		"clientId", String.class, Types.VARCHAR);
	public final Column<WeDeployAuthTokenTable, String> token = createColumn(
		"token", String.class, Types.VARCHAR);
	public final Column<WeDeployAuthTokenTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER);

	private WeDeployAuthTokenTable() {
		super("WeDeployAuth_WeDeployAuthToken", WeDeployAuthTokenTable::new);
	}

}