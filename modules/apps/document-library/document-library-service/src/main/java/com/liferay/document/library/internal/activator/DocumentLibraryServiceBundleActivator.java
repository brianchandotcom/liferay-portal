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

package com.liferay.document.library.internal.activator;

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.document.library.util.DLURLHelperUtil;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Adolfo Pérez
 */
public class DocumentLibraryServiceBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		_serviceTracker = new ServiceTracker<DLURLHelper, DLURLHelper>(
			bundleContext, DLURLHelper.class, null) {

			@Override
			public DLURLHelper addingService(
				ServiceReference<DLURLHelper> serviceReference) {

				DLURLHelper dlURLHelper = bundleContext.getService(
					serviceReference);

				DLURLHelperUtil.setDLURLHelper(dlURLHelper);

				return dlURLHelper;
			}

		};

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_serviceTracker.close();
	}

	private ServiceTracker<DLURLHelper, DLURLHelper> _serviceTracker;

}