/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public final Column<SamlSpSessionTable, Long> samlPeerBindingId =
		createColumn(
			"samlPeerBindingId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, Clob> assertionXml = createColumn(
		"assertionXml", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, String> jSessionId = createColumn(
		"jSessionId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, String> samlSpSessionKey =
		createColumn(
			"samlSpSessionKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, String> sessionIndex = createColumn(
		"sessionIndex", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SamlSpSessionTable, Boolean> terminated = createColumn(
		"terminated_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private SamlSpSessionTable() {
		super("SamlSpSession", SamlSpSessionTable::new);
	}

}