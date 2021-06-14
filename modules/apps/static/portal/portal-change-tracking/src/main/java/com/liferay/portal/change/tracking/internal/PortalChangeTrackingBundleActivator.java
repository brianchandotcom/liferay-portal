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

package com.liferay.portal.change.tracking.internal;

import com.liferay.portal.change.tracking.sql.CTSQLTransformer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Preston Crary
 */
public class PortalChangeTrackingBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_ctSQLTransformerImpl = new CTSQLTransformerImpl();

		_ctSQLTransformerImpl.activate(bundleContext);

		_serviceRegistration = bundleContext.registerService(
			CTSQLTransformer.class, _ctSQLTransformerImpl, null);
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_serviceRegistration.unregister();

		_ctSQLTransformerImpl.deactivate();
	}

	private CTSQLTransformerImpl _ctSQLTransformerImpl;
	private ServiceRegistration<?> _serviceRegistration;

}