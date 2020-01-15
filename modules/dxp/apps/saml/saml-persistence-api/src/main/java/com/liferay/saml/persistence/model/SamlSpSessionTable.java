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
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the SamlSpSession.
 *
 * @author Mika Koivisto
 * @generated
 */
public class SamlSpSessionTable extends Table<SamlSpSessionTable> {

	public static final SamlSpSessionTable INSTANCE = new SamlSpSessionTable();

	public final Column<SamlSpSessionTable, Long> samlSpSessionId =
		createColumn("samlSpSessionId", Long.class, Types.BIGINT);
	public final Column<SamlSpSessionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<SamlSpSessionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<SamlSpSessionTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<SamlSpSessionTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<SamlSpSessionTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<SamlSpSessionTable, String> samlIdpEntityId =
		createColumn("samlIdpEntityId", String.class, Types.VARCHAR);
	public final Column<SamlSpSessionTable, String> samlSpSessionKey =
		createColumn("samlSpSessionKey", String.class, Types.VARCHAR);
	public final Column<SamlSpSessionTable, Clob> assertionXml = createColumn(
		"assertionXml", Clob.class, Types.CLOB);
	public final Column<SamlSpSessionTable, String> jSessionId = createColumn(
		"jSessionId", String.class, Types.VARCHAR);
	public final Column<SamlSpSessionTable, String> nameIdFormat = createColumn(
		"nameIdFormat", String.class, Types.VARCHAR);
	public final Column<SamlSpSessionTable, String> nameIdNameQualifier =
		createColumn("nameIdNameQualifier", String.class, Types.VARCHAR);
	public final Column<SamlSpSessionTable, String> nameIdSPNameQualifier =
		createColumn("nameIdSPNameQualifier", String.class, Types.VARCHAR);
	public final Column<SamlSpSessionTable, String> nameIdValue = createColumn(
		"nameIdValue", String.class, Types.VARCHAR);
	public final Column<SamlSpSessionTable, String> sessionIndex = createColumn(
		"sessionIndex", String.class, Types.VARCHAR);
	public final Column<SamlSpSessionTable, Boolean> terminated = createColumn(
		"terminated_", Boolean.class, Types.BOOLEAN);

	private SamlSpSessionTable() {
		super("SamlSpSession", SamlSpSessionTable::new);
	}

}