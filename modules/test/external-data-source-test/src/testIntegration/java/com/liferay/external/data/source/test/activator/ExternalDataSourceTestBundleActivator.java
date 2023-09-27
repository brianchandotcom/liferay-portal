/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class ExternalDataSourceTestBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		ServiceReference<RunListener> serviceReference =
			bundleContext.getServiceReference(RunListener.class);

		RunListener runListener = bundleContext.getService(serviceReference);

		try {
			if (runListener == null) {
				throw new IllegalStateException();
			}

			JUnitCore jUnitCore = new JUnitCore();

			jUnitCore.addListener(runListener);

			jUnitCore.run(DataSourceTest.class);
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
	}

}