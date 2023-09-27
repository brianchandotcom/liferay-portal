/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.soap.extender.test;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Carlos Sierra Andrés
 */
public class WaiterUtil {

	public static void waitForFilter(
			BundleContext bundleContext, String filterString, long timeout)
		throws Exception {

		ServiceTracker<?, ?> serviceTracker = ServiceTrackerFactory.open(
			bundleContext, filterString);

		try {
			if (serviceTracker.waitForService(timeout) == null) {
				throw new TimeoutException(
					StringBundler.concat(
						"Time out on waiting for ", filterString, " after ",
						timeout, "ms"));
			}
		}
		finally {
			serviceTracker.close();
		}
	}

	public static Waiter waitForFilterToDisappear(
			BundleContext bundleContext, final String filterString)
		throws Exception {

		final CountDownLatch countDownLatch = new CountDownLatch(1);

		ServiceTracker<?, ?> serviceTracker =
			new ServiceTracker<Object, Object>(
				bundleContext, bundleContext.createFilter(filterString), null) {

				@Override
				public void removedService(
					ServiceReference<Object> serviceReference, Object service) {

					countDownLatch.countDown();

					close();
				}

			};

		serviceTracker.open();

		return new Waiter() {

			@Override
			public void waitFor(long timeout) throws Exception {
				if (!countDownLatch.await(timeout, TimeUnit.MILLISECONDS)) {
					throw new TimeoutException(
						StringBundler.concat(
							"Time out on waiting for ", filterString,
							" to disappear after ", timeout, "ms"));
				}
			}

		};
	}

	public interface Waiter {

		public void waitFor(long timeout) throws Exception;

	}

}