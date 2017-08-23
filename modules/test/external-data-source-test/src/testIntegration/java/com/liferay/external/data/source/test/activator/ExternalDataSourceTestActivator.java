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

package com.liferay.external.data.source.test.activator;

import com.liferay.external.data.source.test.DataSourceTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.notification.RunListener;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Preston Crary
 */
public class ExternalDataSourceTestActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		ServiceReference<RunListener> serviceReference =
			bundleContext.getServiceReference(RunListener.class);

		RunListener runListener = bundleContext.getService(serviceReference);

		try {
			if (runListener == null) {
				throw new IllegalStateException();
			}

			JUnitCore junitCore = new JUnitCore();

			junitCore.addListener(runListener);

			junitCore.run(DataSourceTest.class);
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
	}

}