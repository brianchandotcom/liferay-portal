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

package com.liferay.commerce.internal.cart.comparator;

import com.liferay.commerce.cart.CommerceCartValidator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Comparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCartValidatorServiceWrapperPriorityComparator
	implements Comparator<ServiceWrapper<CommerceCartValidator>> {

	public CommerceCartValidatorServiceWrapperPriorityComparator() {
		this(true);
	}

	public CommerceCartValidatorServiceWrapperPriorityComparator(
		boolean ascending) {

		_ascending = ascending;
	}

	@Override
	public int compare(
		ServiceWrapper<CommerceCartValidator> serviceWrapper1,
		ServiceWrapper<CommerceCartValidator> serviceWrapper2) {

		int priority1 = MapUtil.getInteger(
			serviceWrapper1.getProperties(), "commerce.cart.validator.priority",
			Integer.MAX_VALUE);
		int priority2 = MapUtil.getInteger(
			serviceWrapper2.getProperties(), "commerce.cart.validator.priority",
			Integer.MAX_VALUE);

		int value = Integer.compare(priority1, priority2);

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}