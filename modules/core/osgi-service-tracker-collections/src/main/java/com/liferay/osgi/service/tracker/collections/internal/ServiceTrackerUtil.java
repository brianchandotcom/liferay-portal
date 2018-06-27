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

package com.liferay.osgi.service.tracker.collections.internal;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceTrackerUtil {

	public static <SR, TS> ServiceTracker<SR, TS> createServiceTracker(
		BundleContext bundleContext, Class<SR> clazz, String filterString,
		ServiceTrackerCustomizer<SR, TS> serviceTrackerCustomizer) {

		if (filterString != null) {
			if (clazz != null) {
				filterString =
					"(&" + filterString + "(objectClass=" + clazz.getName() +
						"))";
			}

			try {
				Filter filter = bundleContext.createFilter(filterString);

				return new ServiceTracker<>(
					bundleContext, filter, serviceTrackerCustomizer);
			}
			catch (InvalidSyntaxException ise) {
				throwException(ise);

				return null;
			}
		}

		if (clazz != null) {
			return new ServiceTracker<>(
				bundleContext, clazz, serviceTrackerCustomizer);
		}

		throw new IllegalArgumentException(
			"Filter string and class are both null");
	}

	public static <T> T throwException(Throwable throwable) {
		return ServiceTrackerUtil.<T, RuntimeException>_throwException(
			throwable);
	}

	private static <T, E extends Throwable> T _throwException(
			Throwable throwable)
		throws E {

		throw (E)throwable;
	}

}