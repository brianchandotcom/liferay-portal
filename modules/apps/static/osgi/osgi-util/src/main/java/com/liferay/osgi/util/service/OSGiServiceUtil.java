/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.util.service;

import com.liferay.petra.function.UnsafeFunction;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Shuyang Zhou
 */
public class OSGiServiceUtil {

	public static <S, R, E extends Throwable> R callService(
			BundleContext bundleContext, Class<S> serviceClass,
			UnsafeFunction<S, R, E> unsafeFunction)
		throws E {

		ServiceReference<S> serviceReference =
			bundleContext.getServiceReference(serviceClass);

		if (serviceReference == null) {
			return unsafeFunction.apply(null);
		}

		S service = bundleContext.getService(serviceReference);

		try {
			return unsafeFunction.apply(service);
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

}