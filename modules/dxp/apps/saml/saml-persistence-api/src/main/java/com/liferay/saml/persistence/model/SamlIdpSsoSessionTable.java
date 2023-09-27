/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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