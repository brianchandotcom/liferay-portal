/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.query;

import com.liferay.petra.sql.dsl.expression.Predicate;

import java.util.function.Supplier;

/**
 * @author Preston Crary
 */
public interface WhereStep extends GroupByStep {

	public GroupByStep where(Predicate predicate);

	public default GroupByStep where(Supplier<Predicate> supplier) {
		return where(supplier.get());
	}

}