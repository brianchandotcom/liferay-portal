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

package com.liferay.headless.builder.application.publisher.test.util;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.application.publisher.APIApplicationPublisher;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Application;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Luis Miguel Barcos
 */
public class APIApplicationPublisherUtil {

	public static void publishApplications(
			APIApplicationPublisher apiApplicationPublisher,
			APIApplication... apiApplications)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			APIApplicationPublisherUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		CountDownLatch addedCountLatch = new CountDownLatch(
			apiApplications.length);

		ServiceTracker<?, ?> serviceTracker =
			new ServiceTracker<Application, Application>(
				bundleContext, Application.class, null) {

				@Override
				public Application addingService(
					ServiceReference<Application> serviceReference) {

					if (GetterUtil.getBoolean(
							serviceReference.getProperty(
								"liferay.headless.builder.application"))) {

						addedCountLatch.countDown();

						return super.addingService(serviceReference);
					}

					return null;
				}

			};

		try {
			serviceTracker.open();

			for (APIApplication apiApplication : apiApplications) {
				apiApplicationPublisher.publish(apiApplication);
			}

			boolean published = addedCountLatch.await(1, TimeUnit.MINUTES);

			if (!published) {
				throw new Exception(
					"Something went wrong publishing the applications");
			}

			Collections.addAll(_publishedAPIApplications, apiApplications);
		}
		finally {
			serviceTracker.close();
		}
	}

	public static void unpublishApplications(
			APIApplicationPublisher apiApplicationPublisher,
			APIApplication... apiApplications)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			APIApplicationPublisherUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		CountDownLatch removedCountLatch = new CountDownLatch(
			apiApplications.length);

		ServiceTracker<?, ?> serviceTracker =
			new ServiceTracker<Application, Application>(
				bundleContext, Application.class, null) {

				@Override
				public void removedService(
					ServiceReference<Application> serviceReference,
					Application service) {

					if (GetterUtil.getBoolean(
							serviceReference.getProperty(
								"liferay.headless.builder.application"))) {

						removedCountLatch.countDown();

						super.removedService(serviceReference, service);
					}
				}

			};

		try {
			serviceTracker.open();

			for (APIApplication apiApplication : apiApplications) {
				apiApplicationPublisher.unpublish(apiApplication);
			}

			boolean unpublished = removedCountLatch.await(1, TimeUnit.MINUTES);

			if (!unpublished) {
				throw new Exception(
					"Something went wrong unpublishing the applications");
			}

			_publishedAPIApplications.removeAll(Arrays.asList(apiApplications));
		}
		finally {
			serviceTracker.close();
		}
	}

	public static void unpublishRemainingAPIApplications(
			APIApplicationPublisher apiApplicationPublisher)
		throws Exception {

		for (APIApplication apiApplication : _publishedAPIApplications) {
			apiApplicationPublisher.unpublish(apiApplication);
		}
	}

	private static final List<APIApplication> _publishedAPIApplications =
		new ArrayList<>();

}