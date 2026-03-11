/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.query;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;

/**
 * @author Preston Crary
 * @author Roberto Díaz
 */
public interface JoinStep extends WhereStep {

	public JoinStep innerJoinON(Table<?> table, Predicate predicate);

	public <T extends Throwable> JoinStep innerJoinON(
			Table<?> table, UnsafeSupplier<Predicate, T> unsafeSupplier)
		throws T;

	public JoinStep leftJoinOn(Table<?> table, Predicate predicate);

	public <T extends Throwable> JoinStep leftJoinOn(
			Table<?> table, UnsafeSupplier<Predicate, T> unsafeSupplier)
		throws T;

}