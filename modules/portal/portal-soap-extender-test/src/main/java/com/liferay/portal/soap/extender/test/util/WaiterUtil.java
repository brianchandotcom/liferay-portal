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
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Carlos Sierra Andrés
 */
public class WaiterUtil {

	public static boolean waitForFilter(
			BundleContext bundleContext, String filterString, long timeout)
		throws InterruptedException, InvalidSyntaxException, TimeoutException {

		Filter filter = bundleContext.createFilter(filterString);

		ServiceTracker<?, ?> serviceTracker = new ServiceTracker<>(
			bundleContext, filter, null);

		serviceTracker.open();

		Object object = serviceTracker.waitForService(timeout);

		serviceTracker.close();

		if (object == null) {
			return false;
		}

		return true;
	}

	public static Waiter waitForFilterToDisappear(
			BundleContext bundleContext, String filterString, int number)
		throws InterruptedException, InvalidSyntaxException {

		final CountDownLatch countDownLatch = new CountDownLatch(number);

		Filter filter = bundleContext.createFilter(filterString);

		ServiceTracker<?, ?> serviceTracker =
			new ServiceTracker<Object, Object>(bundleContext, filter, null) {

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
			public boolean waitFor(long timeout) throws InterruptedException {
				if (!countDownLatch.await(timeout, TimeUnit.MILLISECONDS)) {
					return false;
				}

				return true;
			}
		};
	}

	public interface Waiter {
		public boolean waitFor(long timeout) throws InterruptedException;

	}

}