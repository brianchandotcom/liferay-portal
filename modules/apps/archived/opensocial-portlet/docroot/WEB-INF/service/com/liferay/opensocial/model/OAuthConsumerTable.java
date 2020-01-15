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

package com.liferay.opensocial.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the OAuthConsumer.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OAuthConsumerTable extends Table<OAuthConsumerTable> {

	public static final OAuthConsumerTable INSTANCE = new OAuthConsumerTable();

	public final Column<OAuthConsumerTable, Long> oAuthConsumerId =
		createColumn("oAuthConsumerId", Long.class, Types.BIGINT);
	public final Column<OAuthConsumerTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<OAuthConsumerTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<OAuthConsumerTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<OAuthConsumerTable, String> gadgetKey = createColumn(
		"gadgetKey", String.class, Types.VARCHAR);
	public final Column<OAuthConsumerTable, String> serviceName = createColumn(
		"serviceName", String.class, Types.VARCHAR);
	public final Column<OAuthConsumerTable, String> consumerKey = createColumn(
		"consumerKey", String.class, Types.VARCHAR);
	public final Column<OAuthConsumerTable, Clob> consumerSecret = createColumn(
		"consumerSecret", Clob.class, Types.CLOB);
	public final Column<OAuthConsumerTable, String> keyType = createColumn(
		"keyType", String.class, Types.VARCHAR);

	private OAuthConsumerTable() {
		super("OpenSocial_OAuthConsumer", OAuthConsumerTable::new);
	}

}