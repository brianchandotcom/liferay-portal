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

package com.liferay.portlet.shopping.model.impl;

import com.liferay.portal.kernel.util.CalendarUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingCouponImpl extends ShoppingCouponBaseImpl {

	@Override
	public boolean hasValidDateRange() {
		if (hasValidStartDate() && hasValidEndDate()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasValidEndDate() {
		if (getEndDate() != null) {
			Date now = new Date();

			if (now.after(getEndDate())) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean hasValidStartDate() {
		Date now = new Date();

		if (CalendarUtil.beforeByDay(now, getStartDate())) {
			return false;
		}
		else {
			return true;
		}
	}

}