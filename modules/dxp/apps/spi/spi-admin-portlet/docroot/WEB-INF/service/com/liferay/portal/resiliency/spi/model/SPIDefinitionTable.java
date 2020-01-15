/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.resiliency.spi.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the SPIDefinition.
 *
 * @author Michael C. Han
 * @generated
 */
public class SPIDefinitionTable extends Table<SPIDefinitionTable> {

	public static final SPIDefinitionTable INSTANCE = new SPIDefinitionTable();

	public final Column<SPIDefinitionTable, Long> spiDefinitionId =
		createColumn("spiDefinitionId", Long.class, Types.BIGINT);
	public final Column<SPIDefinitionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<SPIDefinitionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<SPIDefinitionTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<SPIDefinitionTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<SPIDefinitionTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<SPIDefinitionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<SPIDefinitionTable, String> connectorAddress =
		createColumn("connectorAddress", String.class, Types.VARCHAR);
	public final Column<SPIDefinitionTable, Integer> connectorPort =
		createColumn("connectorPort", Integer.class, Types.INTEGER);
	public final Column<SPIDefinitionTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR);
	public final Column<SPIDefinitionTable, String> jvmArguments = createColumn(
		"jvmArguments", String.class, Types.VARCHAR);
	public final Column<SPIDefinitionTable, String> portletIds = createColumn(
		"portletIds", String.class, Types.VARCHAR);
	public final Column<SPIDefinitionTable, String> servletContextNames =
		createColumn("servletContextNames", String.class, Types.VARCHAR);
	public final Column<SPIDefinitionTable, Clob> typeSettings = createColumn(
		"typeSettings", Clob.class, Types.CLOB);
	public final Column<SPIDefinitionTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER);
	public final Column<SPIDefinitionTable, String> statusMessage =
		createColumn("statusMessage", String.class, Types.VARCHAR);

	private SPIDefinitionTable() {
		super("SPIDefinition", SPIDefinitionTable::new);
	}

}