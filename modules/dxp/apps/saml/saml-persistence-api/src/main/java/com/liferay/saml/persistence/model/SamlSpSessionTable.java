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

package com.liferay.saml.persistence.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;SamlSpSession&quot; database table.
 *
 * @author Mika Koivisto
 * @see SamlSpSession
 * @generated
 */
public class SamlSpSessionTable extends BaseTable<SamlSpSessionTable> {

	public static final SamlSpSessionTable INSTANCE = new SamlSpSessionTable();

	public final Column<SamlSpSessionTable, Long> samlSpSessionId =
		createColumn(
			"samlSpSessionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SamlSpSessionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, String> samlIdpEntityId =
		createColumn(
			"samlIdpEntityId", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, String> samlSpSessionKey =
		createColumn(
			"samlSpSessionKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, Clob> assertionXml = createColumn(
		"assertionXml", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, String> jSessionId = createColumn(
		"jSessionId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, String> nameIdFormat = createColumn(
		"nameIdFormat", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, String> nameIdNameQualifier =
		createColumn(
			"nameIdNameQualifier", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, String> nameIdSPNameQualifier =
		createColumn(
			"nameIdSPNameQualifier", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, String> nameIdValue = createColumn(
		"nameIdValue", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, String> sessionIndex = createColumn(
		"sessionIndex", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, Boolean> terminated = createColumn(
		"terminated_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private SamlSpSessionTable() {
		super("SamlSpSession", SamlSpSessionTable::new);
	}

}