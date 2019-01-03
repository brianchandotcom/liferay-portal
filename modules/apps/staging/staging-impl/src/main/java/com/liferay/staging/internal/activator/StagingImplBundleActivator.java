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

package com.liferay.staging.internal.activator;

import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Máté Thurzó
 */
public class StagingImplBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_serviceTracker =
			new ServiceTracker<StagingGroupHelper, StagingGroupHelper>(
				bundleContext, StagingGroupHelper.class.getName(), null) {

				@Override
				public StagingGroupHelper addingService(
					ServiceReference<StagingGroupHelper> serviceReference) {

					StagingGroupHelper stagingGroupHelper =
						bundleContext.getService(serviceReference);

					StagingGroupHelperUtil.setStagingGroupHelper(
						stagingGroupHelper);

					return stagingGroupHelper;
				}

			};

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_serviceTracker.close();
	}

	private ServiceTracker<StagingGroupHelper, StagingGroupHelper>
		_serviceTracker;

}