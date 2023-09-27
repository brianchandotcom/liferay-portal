/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.query;

import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.HavingStep;
import com.liferay.petra.sql.dsl.query.OrderByStep;

/**
 * @author Preston Crary
 */
public interface DefaultHavingStep extends DefaultOrderByStep, HavingStep {

	@Override
	public default OrderByStep having(Predicate predicate) {
		if (predicate == null) {
			return this;
		}

		return new Having(this, predicate);
	}

}