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
	public final Column<SamlIdpSpSessionTable, Long> samlPeerBindingId =
		createColumn(
			"samlPeerBindingId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private SamlIdpSpSessionTable() {
		super("SamlIdpSpSession", SamlIdpSpSessionTable::new);
	}

}