/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.query;

import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.JoinStep;

/**
 * @author Preston Crary
 */
public interface DefaultJoinStep extends DefaultWhereStep, JoinStep {

	@Override
	public default JoinStep innerJoinON(Table<?> table, Predicate predicate) {
		if (predicate == null) {
			return this;
		}

		return new Join(this, JoinType.INNER, table, predicate);
	}

	@Override
	public default JoinStep leftJoinOn(Table<?> table, Predicate predicate) {
		if (predicate == null) {
			return this;
		}

		return new Join(this, JoinType.LEFT, table, predicate);
	}

}