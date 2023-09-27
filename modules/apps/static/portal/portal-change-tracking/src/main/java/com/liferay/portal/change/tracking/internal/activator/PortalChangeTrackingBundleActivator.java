/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.change.tracking.internal.activator;

import com.liferay.portal.change.tracking.internal.CTSQLTransformerImpl;
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