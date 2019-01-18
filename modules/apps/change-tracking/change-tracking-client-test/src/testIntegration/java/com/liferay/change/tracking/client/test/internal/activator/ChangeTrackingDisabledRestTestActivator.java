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

package com.liferay.change.tracking.client.test.internal.activator;

import com.liferay.change.tracking.CTEngineManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Daniel Kocsis
 */
public class ChangeTrackingDisabledRestTestActivator
	implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws PortalException {
		ServiceReference<CTEngineManager> serviceReference =
			bundleContext.getServiceReference(CTEngineManager.class);

		_ctEngineManager = bundleContext.getService(serviceReference);

		_ctEngineManager.disableChangeTracking(TestPropsValues.getCompanyId());
	}

	@Override
	public void stop(BundleContext bundleContext) throws PortalException {
		_ctEngineManager.disableChangeTracking(TestPropsValues.getCompanyId());
	}

	private CTEngineManager _ctEngineManager;

}