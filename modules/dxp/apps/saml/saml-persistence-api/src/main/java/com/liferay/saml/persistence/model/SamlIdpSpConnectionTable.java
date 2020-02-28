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
 * The table class for the &quot;SamlIdpSpConnection&quot; database table.
 *
 * @author Mika Koivisto
 * @see SamlIdpSpConnection
 * @generated
 */
public class SamlIdpSpConnectionTable
	extends BaseTable<SamlIdpSpConnectionTable> {

	public static final SamlIdpSpConnectionTable INSTANCE =
		new SamlIdpSpConnectionTable();

	public final Column<SamlIdpSpConnectionTable, Long> samlIdpSpConnectionId =
		createColumn(
			"samlIdpSpConnectionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<SamlIdpSpConnectionTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpConnectionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpConnectionTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpConnectionTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpConnectionTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpConnectionTable, String> samlSpEntityId =
		createColumn(
			"samlSpEntityId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpConnectionTable, Integer> assertionLifetime =
		createColumn(
			"assertionLifetime", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpConnectionTable, String> attributeNames =
		createColumn(
			"attributeNames", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpConnectionTable, Boolean> attributesEnabled =
		createColumn(
			"attributesEnabled", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpConnectionTable, Boolean>
		attributesNamespaceEnabled = createColumn(
			"attributesNamespaceEnabled", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpConnectionTable, Boolean> enabled =
		createColumn(
			"enabled", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpConnectionTable, Boolean> encryptionForced =
		createColumn(
			"encryptionForced", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpConnectionTable, String> metadataUrl =
		createColumn(
			"metadataUrl", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpConnectionTable, Clob> metadataXml =
		createColumn(
			"metadataXml", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpConnectionTable, Date> metadataUpdatedDate =
		createColumn(
			"metadataUpdatedDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpConnectionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpConnectionTable, String> nameIdAttribute =
		createColumn(
			"nameIdAttribute", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpConnectionTable, String> nameIdFormat =
		createColumn(
			"nameIdFormat", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private SamlIdpSpConnectionTable() {
		super("SamlIdpSpConnection", SamlIdpSpConnectionTable::new);
	}

}