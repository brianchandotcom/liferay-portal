/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.filter;

/**
 * @author Mikel Lorza
 */
public abstract class BaseDateTimeRangeFDSFilter implements FDSFilter {

	public abstract DateTimeFDSFilterItem getMaxDateTimeFDSFilterItem();

	public abstract DateTimeFDSFilterItem getMinDateTimeFDSFilterItem();

	@Override
	public String getType() {
		return "dateTimeRange";
	}

}