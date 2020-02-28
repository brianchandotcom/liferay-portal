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
 * The table class for the &quot;SamlIdpSsoSession&quot; database table.
 *
 * @author Mika Koivisto
 * @see SamlIdpSsoSession
 * @generated
 */
public class SamlIdpSsoSessionTable extends BaseTable<SamlIdpSsoSessionTable> {

	public static final SamlIdpSsoSessionTable INSTANCE =
		new SamlIdpSsoSessionTable();

	public final Column<SamlIdpSsoSessionTable, Long> samlIdpSsoSessionId =
		createColumn(
			"samlIdpSsoSessionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<SamlIdpSsoSessionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSsoSessionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSsoSessionTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSsoSessionTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSsoSessionTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SamlIdpSsoSessionTable, String> samlIdpSsoSessionKey =
		createColumn(
			"samlIdpSsoSessionKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private SamlIdpSsoSessionTable() {
		super("SamlIdpSsoSession", SamlIdpSsoSessionTable::new);
	}

}