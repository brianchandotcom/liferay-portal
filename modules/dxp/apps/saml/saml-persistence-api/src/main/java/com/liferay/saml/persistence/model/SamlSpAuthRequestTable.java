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