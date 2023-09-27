/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			catch (InvalidSyntaxException invalidSyntaxException) {
				throwException(invalidSyntaxException);

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