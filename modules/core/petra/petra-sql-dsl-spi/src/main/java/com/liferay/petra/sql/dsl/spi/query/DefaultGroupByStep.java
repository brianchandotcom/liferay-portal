/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.query;

import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.HavingStep;

/**
 * @author Preston Crary
 */
public interface DefaultGroupByStep extends DefaultOrderByStep, GroupByStep {

	@Override
	public default HavingStep groupBy(Expression<?>... expressions) {
		return new GroupBy(this, expressions);
	}

}