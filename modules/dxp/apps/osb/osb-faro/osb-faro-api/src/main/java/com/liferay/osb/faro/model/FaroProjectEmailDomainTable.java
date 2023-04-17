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