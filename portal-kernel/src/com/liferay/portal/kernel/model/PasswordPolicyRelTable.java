/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;PasswordPolicyRel&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyRel
 * @generated
 */
public class PasswordPolicyRelTable extends BaseTable<PasswordPolicyRelTable> {

	public static final PasswordPolicyRelTable INSTANCE =
		new PasswordPolicyRelTable();

	public final Column<PasswordPolicyRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<PasswordPolicyRelTable, Long> passwordPolicyRelId =
		createColumn(
			"passwordPolicyRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<PasswordPolicyRelTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyRelTable, Long> passwordPolicyId =
		createColumn(
			"passwordPolicyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyRelTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyRelTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private PasswordPolicyRelTable() {
		super("PasswordPolicyRel", PasswordPolicyRelTable::new);
	}

}