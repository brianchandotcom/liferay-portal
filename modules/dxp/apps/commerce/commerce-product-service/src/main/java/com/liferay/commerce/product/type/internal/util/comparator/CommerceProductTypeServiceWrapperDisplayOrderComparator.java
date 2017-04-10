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

package com.liferay.commerce.product.type.internal.util.comparator;

import com.liferay.commerce.product.type.CommerceProductType;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Comparator;

/**
 * @author Marco Leo
 */
public class CommerceProductTypeServiceWrapperDisplayOrderComparator
	implements Comparator<ServiceWrapper<CommerceProductType>> {

	public CommerceProductTypeServiceWrapperDisplayOrderComparator() {
		this(true);
	}

	public CommerceProductTypeServiceWrapperDisplayOrderComparator(
		boolean ascending) {

		_ascending = ascending;
	}

	@Override
	public int compare(
		ServiceWrapper<CommerceProductType> serviceWrapper1,
		ServiceWrapper<CommerceProductType> serviceWrapper2) {

		Integer displayOrder1 = MapUtil.getInteger(
			serviceWrapper1.getProperties(),
			"commerce.product.type.display.order", Integer.MAX_VALUE);
		Integer displayOrder2 = MapUtil.getInteger(
			serviceWrapper2.getProperties(),
			"commerce.product.type.display.order", Integer.MAX_VALUE);

		int value = displayOrder1.compareTo(displayOrder2);

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