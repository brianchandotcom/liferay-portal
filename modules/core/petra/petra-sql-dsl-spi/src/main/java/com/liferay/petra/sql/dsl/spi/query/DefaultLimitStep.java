/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.query;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.LimitStep;

/**
 * @author Preston Crary
 */
public interface DefaultLimitStep extends DefaultDSLQuery, LimitStep {

	@Override
	public default DSLQuery limit(int start, int end) {
		return new Limit(this, start, end);
	}

}