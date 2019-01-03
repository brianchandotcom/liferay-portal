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