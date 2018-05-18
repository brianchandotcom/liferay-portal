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

package com.liferay.commerce.product.internal.type.comparator;

import com.liferay.commerce.product.type.CPType;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Marco Leo
 */
public class CPTypeServiceWrapperDisplayOrderComparator
	implements Comparator<ServiceWrapper<CPType>>, Serializable {

	public CPTypeServiceWrapperDisplayOrderComparator() {
		this(true);
	}

	public CPTypeServiceWrapperDisplayOrderComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		ServiceWrapper<CPType> serviceWrapper1,
		ServiceWrapper<CPType> serviceWrapper2) {

		int displayOrder1 = MapUtil.getInteger(
			serviceWrapper1.getProperties(),
			"commerce.product.type.display.order", Integer.MAX_VALUE);
		int displayOrder2 = MapUtil.getInteger(
			serviceWrapper2.getProperties(),
			"commerce.product.type.display.order", Integer.MAX_VALUE);

		int value = Integer.compare(displayOrder1, displayOrder2);

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