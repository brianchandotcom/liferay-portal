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
 * The table class for the WeDeployAuthApp.
 *
 * @author Supritha Sundaram
 * @generated
 */
public class WeDeployAuthAppTable extends Table<WeDeployAuthAppTable> {

	public static final WeDeployAuthAppTable INSTANCE =
		new WeDeployAuthAppTable();

	public final Column<WeDeployAuthAppTable, Long> weDeployAuthAppId =
		createColumn("weDeployAuthAppId", Long.class, Types.BIGINT);
	public final Column<WeDeployAuthAppTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<WeDeployAuthAppTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<WeDeployAuthAppTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<WeDeployAuthAppTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<WeDeployAuthAppTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<WeDeployAuthAppTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<WeDeployAuthAppTable, String> redirectURI =
		createColumn("redirectURI", String.class, Types.VARCHAR);
	public final Column<WeDeployAuthAppTable, String> clientId = createColumn(
		"clientId", String.class, Types.VARCHAR);
	public final Column<WeDeployAuthAppTable, String> clientSecret =
		createColumn("clientSecret", String.class, Types.VARCHAR);

	private WeDeployAuthAppTable() {
		super("WeDeployAuth_WeDeployAuthApp", WeDeployAuthAppTable::new);
	}

}