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

package com.liferay.portal.weblogic.support.internal;

import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.servlet.filters.weblogic.WebLogicIncludeServletResponseFactory;
import com.liferay.portal.weblogic.support.internal.include.WebLogicIncludeServletResponseFactoryImpl;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = {})
public class WebLogicSupport {

	@Activate
	protected void activate(BundleContext bundleContext) {
		if (!ServerDetector.isWebLogic()) {
			return;
		}

		_serviceRegistration = bundleContext.registerService(
			WebLogicIncludeServletResponseFactory.class,
			new WebLogicIncludeServletResponseFactoryImpl(), null);
	}

	@Deactivate
	protected void deactivate() {
		if (!ServerDetector.isWebLogic()) {
			return;
		}

		_serviceRegistration.unregister();
	}

	private ServiceRegistration<WebLogicIncludeServletResponseFactory>
		_serviceRegistration;

}