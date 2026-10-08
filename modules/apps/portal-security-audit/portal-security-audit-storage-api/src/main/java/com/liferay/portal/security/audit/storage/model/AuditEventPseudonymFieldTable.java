/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.storage.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Audit_AuditEventPseudonymField&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AuditEventPseudonymField
 * @generated
 */
public class AuditEventPseudonymFieldTable
	extends BaseTable<AuditEventPseudonymFieldTable> {

	public static final AuditEventPseudonymFieldTable INSTANCE =
		new AuditEventPseudonymFieldTable();

	public final Column<AuditEventPseudonymFieldTable, Long>
		auditEventPseudonymFieldId = createColumn(
			"auditEventPseudonymFieldId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AuditEventPseudonymFieldTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AuditEventPseudonymFieldTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AuditEventPseudonymFieldTable, String> contextName =
		createColumn(
			"contextName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AuditEventPseudonymFieldTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AuditEventPseudonymFieldTable, String> value =
		createColumn("value", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AuditEventPseudonymFieldTable, String> valueHash =
		createColumn(
			"valueHash", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private AuditEventPseudonymFieldTable() {
		super(
			"Audit_AuditEventPseudonymField",
			AuditEventPseudonymFieldTable::new);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-740840291