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
 * The table class for the &quot;SamlSpIdpConnection&quot; database table.
 *
 * @author Mika Koivisto
 * @see SamlSpIdpConnection
 * @generated
 */
public class SamlSpIdpConnectionTable
	extends BaseTable<SamlSpIdpConnectionTable> {

	public static final SamlSpIdpConnectionTable INSTANCE =
		new SamlSpIdpConnectionTable();

	public final Column<SamlSpIdpConnectionTable, Long> samlSpIdpConnectionId =
		createColumn(
			"samlSpIdpConnectionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<SamlSpIdpConnectionTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, String> samlIdpEntityId =
		createColumn(
			"samlIdpEntityId", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, Boolean>
		assertionSignatureRequired = createColumn(
			"assertionSignatureRequired", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, Long> clockSkew =
		createColumn(
			"clockSkew", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, Boolean> enabled =
		createColumn(
			"enabled", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, Boolean> forceAuthn =
		createColumn(
			"forceAuthn", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, Boolean> ldapImportEnabled =
		createColumn(
			"ldapImportEnabled", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, Date> metadataUpdatedDate =
		createColumn(
			"metadataUpdatedDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, String> metadataUrl =
		createColumn(
			"metadataUrl", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, Clob> metadataXml =
		createColumn(
			"metadataXml", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, String> nameIdFormat =
		createColumn(
			"nameIdFormat", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, Boolean> signAuthnRequest =
		createColumn(
			"signAuthnRequest", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, Boolean>
		unknownUsersAreStrangers = createColumn(
			"unknownUsersAreStrangers", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<SamlSpIdpConnectionTable, String>
		userAttributeMappings = createColumn(
			"userAttributeMappings", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private SamlSpIdpConnectionTable() {
		super("SamlSpIdpConnection", SamlSpIdpConnectionTable::new);
	}

}