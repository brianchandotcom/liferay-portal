/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.internal.stock.activity.comparator;

import com.liferay.commerce.stock.activity.CommerceLowStockActivity;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceLowStockActivityServiceWrapperPriorityComparator
	implements Comparator<ServiceWrapper<CommerceLowStockActivity>>,
			   Serializable {

	public CommerceLowStockActivityServiceWrapperPriorityComparator() {
		this(true);
	}

	public CommerceLowStockActivityServiceWrapperPriorityComparator(
		boolean ascending) {

		_ascending = ascending;
	}

	@Override
	public int compare(
		ServiceWrapper<CommerceLowStockActivity> serviceWrapper1,
		ServiceWrapper<CommerceLowStockActivity> serviceWrapper2) {

		int priority1 = MapUtil.getInteger(
			serviceWrapper1.getProperties(),
			"commerce.low.stock.activity.priority", Integer.MAX_VALUE);
		int priority2 = MapUtil.getInteger(
			serviceWrapper2.getProperties(),
			"commerce.low.stock.activity.priority", Integer.MAX_VALUE);

		int value = Integer.compare(priority1, priority2);

		if (_ascending) {
			return value;
		}
		else {
			return Math.negateExact(value);
		}
	}

	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}