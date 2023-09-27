/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;OSBFaro_FaroProjectEmailDomain&quot; database table.
 *
 * @author Matthew Kong
 * @see FaroProjectEmailDomain
 * @generated
 */
public class FaroProjectEmailDomainTable
	extends BaseTable<FaroProjectEmailDomainTable> {

	public static final FaroProjectEmailDomainTable INSTANCE =
		new FaroProjectEmailDomainTable();

	public final Column<FaroProjectEmailDomainTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<FaroProjectEmailDomainTable, Long>
		faroProjectEmailDomainId = createColumn(
			"faroProjectEmailDomainId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<FaroProjectEmailDomainTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroProjectEmailDomainTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroProjectEmailDomainTable, Long> faroProjectId =
		createColumn(
			"faroProjectId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroProjectEmailDomainTable, String> emailDomain =
		createColumn(
			"emailDomain", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private FaroProjectEmailDomainTable() {
		super(
			"OSBFaro_FaroProjectEmailDomain", FaroProjectEmailDomainTable::new);
	}

}