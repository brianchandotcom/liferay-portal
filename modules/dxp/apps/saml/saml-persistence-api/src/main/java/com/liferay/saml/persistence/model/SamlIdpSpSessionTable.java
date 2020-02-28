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

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;SamlIdpSpSession&quot; database table.
 *
 * @author Mika Koivisto
 * @see SamlIdpSpSession
 * @generated
 */
public class SamlIdpSpSessionTable extends BaseTable<SamlIdpSpSessionTable> {

	public static final SamlIdpSpSessionTable INSTANCE =
		new SamlIdpSpSessionTable();

	public final Column<SamlIdpSpSessionTable, Long> samlIdpSpSessionId =
		createColumn(
			"samlIdpSpSessionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<SamlIdpSpSessionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpSessionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpSessionTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpSessionTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpSessionTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpSessionTable, Long> samlIdpSsoSessionId =
		createColumn(
			"samlIdpSsoSessionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpSessionTable, String> samlSpEntityId =
		createColumn(
			"samlSpEntityId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpSessionTable, String> nameIdFormat =
		createColumn(
			"nameIdFormat", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSpSessionTable, String> nameIdValue =
		createColumn(
			"nameIdValue", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private SamlIdpSpSessionTable() {
		super("SamlIdpSpSession", SamlIdpSpSessionTable::new);
	}

}