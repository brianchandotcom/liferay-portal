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

package com.liferay.osgi.util;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Adolfo Pérez
 */
public class ServiceTrackerFactory {

	public static <T> ServiceTracker<T, T> create(Class<T> clazz) {
		Bundle bundle = FrameworkUtil.getBundle(clazz);

		return new ServiceTracker<>(bundle.getBundleContext(), clazz, null);
	}

	public static <T> ServiceTracker<T, T> open(Class<T> clazz) {
		ServiceTracker<T, T> serviceTracker = create(clazz);

		serviceTracker.open();

		return serviceTracker;
	}

}