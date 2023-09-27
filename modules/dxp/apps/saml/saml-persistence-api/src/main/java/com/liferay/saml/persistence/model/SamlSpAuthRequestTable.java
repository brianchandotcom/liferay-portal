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
 * The table class for the &quot;SamlSpAuthRequest&quot; database table.
 *
 * @author Mika Koivisto
 * @see SamlSpAuthRequest
 * @generated
 */
public class SamlSpAuthRequestTable extends BaseTable<SamlSpAuthRequestTable> {

	public static final SamlSpAuthRequestTable INSTANCE =
		new SamlSpAuthRequestTable();

	public final Column<SamlSpAuthRequestTable, Long> samlSpAuthnRequestId =
		createColumn(
			"samlSpAuthnRequestId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<SamlSpAuthRequestTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SamlSpAuthRequestTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SamlSpAuthRequestTable, String> samlIdpEntityId =
		createColumn(
			"samlIdpEntityId", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<SamlSpAuthRequestTable, String> samlSpAuthRequestKey =
		createColumn(
			"samlSpAuthRequestKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private SamlSpAuthRequestTable() {
		super("SamlSpAuthRequest", SamlSpAuthRequestTable::new);
	}

}