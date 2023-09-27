/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.expression.step;

import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;

/**
 * @author Preston Crary
 */
public interface WhenThenStep<T> extends ElseEndStep<T> {

	public WhenThenStep<T> whenThen(
		Predicate predicate, Expression<T> expression);

	public WhenThenStep<T> whenThen(Predicate predicate, T value);

}