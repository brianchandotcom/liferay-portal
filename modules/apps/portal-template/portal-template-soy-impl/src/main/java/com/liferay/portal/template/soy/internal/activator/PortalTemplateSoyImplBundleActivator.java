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

package com.liferay.portal.template.soy.internal.activator;

import com.liferay.portal.template.soy.util.SoyContextFactory;
import com.liferay.portal.template.soy.util.SoyContextFactoryUtil;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Máté Thurzó
 */
public class PortalTemplateSoyImplBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_serviceTracker =
			new ServiceTracker<SoyContextFactory, SoyContextFactory>(
				bundleContext, SoyContextFactory.class.getName(), null) {

				@Override
				public SoyContextFactory addingService(
					ServiceReference<SoyContextFactory> serviceReference) {

					SoyContextFactory soyContextFactory =
						bundleContext.getService(serviceReference);

					SoyContextFactoryUtil.setSoyContextFactory(
						soyContextFactory);

					return soyContextFactory;
				}

			};

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_serviceTracker.close();
	}

	private ServiceTracker<SoyContextFactory, SoyContextFactory>
		_serviceTracker;

}