/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

/**
 * The table class for the PasswordPolicyRel.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PasswordPolicyRelTable extends Table<PasswordPolicyRelTable> {

	public static final PasswordPolicyRelTable INSTANCE =
		new PasswordPolicyRelTable();

	public final Column<PasswordPolicyRelTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<PasswordPolicyRelTable, Long> passwordPolicyRelId =
		createColumn("passwordPolicyRelId", Long.class, Types.BIGINT);
	public final Column<PasswordPolicyRelTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<PasswordPolicyRelTable, Long> passwordPolicyId =
		createColumn("passwordPolicyId", Long.class, Types.BIGINT);
	public final Column<PasswordPolicyRelTable, Long> classNameId =
		createColumn("classNameId", Long.class, Types.BIGINT);
	public final Column<PasswordPolicyRelTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);

	private PasswordPolicyRelTable() {
		super("PasswordPolicyRel", PasswordPolicyRelTable::new);
	}

}