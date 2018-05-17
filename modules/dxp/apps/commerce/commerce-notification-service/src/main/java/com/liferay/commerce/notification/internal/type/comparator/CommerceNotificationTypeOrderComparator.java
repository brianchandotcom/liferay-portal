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

package com.liferay.commerce.notification.internal.type.comparator;

import com.liferay.commerce.notification.type.CommerceNotificationType;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceNotificationTypeOrderComparator
	implements Comparator <ServiceWrapper<CommerceNotificationType>>,
			   Serializable {

	public CommerceNotificationTypeOrderComparator() {
		this(true);
	}

	public CommerceNotificationTypeOrderComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		ServiceWrapper<CommerceNotificationType> serviceWrapper1,
		ServiceWrapper<CommerceNotificationType> serviceWrapper2) {

		int displayOrder1 = MapUtil.getInteger(
			serviceWrapper1.getProperties(), "commerce.notification.type.order",
			Integer.MAX_VALUE);
		int displayOrder2 = MapUtil.getInteger(
			serviceWrapper2.getProperties(), "commerce.notification.type.order",
			Integer.MAX_VALUE);

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