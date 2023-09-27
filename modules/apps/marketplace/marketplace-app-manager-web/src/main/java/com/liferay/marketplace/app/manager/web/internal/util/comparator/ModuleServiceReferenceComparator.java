/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.app.manager.web.internal.util.comparator;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;

import org.osgi.framework.ServiceReference;

/**
 * @author Douglas Wong
 */
public class ModuleServiceReferenceComparator<T>
	extends PropertyServiceReferenceComparator<T> {

	public ModuleServiceReferenceComparator(
		String propertyKey, String orderByType) {

		super(propertyKey);

		if (!orderByType.equals("asc")) {
			_ascending = false;
		}
		else {
			_ascending = true;
		}
	}

	@Override
	public int compare(
		ServiceReference<T> serviceReference1,
		ServiceReference<T> serviceReference2) {

		int value = super.compare(serviceReference1, serviceReference2);

		if (_ascending) {
			return -value;
		}

		return value;
	}

	private final boolean _ascending;

}