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

package com.liferay.osgi.service.tracker.map;

import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
public class ServiceRankingPropertyServiceReferenceComparator<T>
	extends PropertyServiceReferenceComparator<T> {

	public ServiceRankingPropertyServiceReferenceComparator() {
		super(Constants.SERVICE_RANKING);
	}

	@Override
	public int compare(
		ServiceReference<T> serviceReference1,
		ServiceReference<T> serviceReference2) {

		return -(super.compare(serviceReference1, serviceReference2));
	}

}