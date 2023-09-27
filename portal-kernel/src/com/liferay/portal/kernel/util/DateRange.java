/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.Date;

/**
 * @author Eduardo García
 */
public class DateRange {

	public DateRange(Date startDate, Date endDate) {
		_startDate = startDate;
		_endDate = endDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	private Date _endDate;
	private Date _startDate;

}