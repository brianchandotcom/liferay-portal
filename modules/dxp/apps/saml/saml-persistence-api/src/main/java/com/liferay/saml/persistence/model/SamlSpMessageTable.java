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

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the SamlSpMessage.
 *
 * @author Mika Koivisto
 * @generated
 */
public class SamlSpMessageTable extends Table<SamlSpMessageTable> {

	public static final SamlSpMessageTable INSTANCE = new SamlSpMessageTable();

	public final Column<SamlSpMessageTable, Long> samlSpMessageId =
		createColumn("samlSpMessageId", Long.class, Types.BIGINT);
	public final Column<SamlSpMessageTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<SamlSpMessageTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<SamlSpMessageTable, String> samlIdpEntityId =
		createColumn("samlIdpEntityId", String.class, Types.VARCHAR);
	public final Column<SamlSpMessageTable, String> samlIdpResponseKey =
		createColumn("samlIdpResponseKey", String.class, Types.VARCHAR);
	public final Column<SamlSpMessageTable, Date> expirationDate = createColumn(
		"expirationDate", Date.class, Types.TIMESTAMP);

	private SamlSpMessageTable() {
		super("SamlSpMessage", SamlSpMessageTable::new);
	}

}