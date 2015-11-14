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

package com.liferay.portal.soap.extender.test.util;

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

		ServiceTracker<?, ?> serviceTracker = new ServiceTracker<>(
			bundleContext, bundleContext.createFilter(filterString), null);

		serviceTracker.open();

		try {
			if (serviceTracker.waitForService(timeout) == null) {
				throw new TimeoutException(
					"Time out on waiting for " + filterString + " after " +
						timeout + "ms");
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
				ServiceReference<Object> reference, Object service) {

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
						"Time out on waiting for " + filterString +
							" to disappear after " + timeout);
				}
			}
		};
	}

	public interface Waiter {

		public void waitFor(long timeout) throws Exception;

	}

}