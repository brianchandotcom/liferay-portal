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

package com.liferay.portal.compound.session.id;

import com.liferay.portal.kernel.servlet.filters.compoundsessionid.CompoundSessionIdSplitterUtil;
import com.liferay.portal.servlet.filters.compoundsessionid.CompoundSessionIdServletRequestFactory;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true)
public class CompoundSessionIdSupport {

	@Activate
	public void activate(BundleContext bundleContext) {
		if (!CompoundSessionIdSplitterUtil.hasSessionDelimiter()) {
			return;
		}

		_serviceRegistration = bundleContext.registerService(
			CompoundSessionIdServletRequestFactory.class,
			new CompoundSessionIdServletRequestFactoryImpl(), null);
	}

	@Deactivate
	public void deactivate() {
		if (!CompoundSessionIdSplitterUtil.hasSessionDelimiter()) {
			return;
		}

		_serviceRegistration.unregister();
	}

	private ServiceRegistration<CompoundSessionIdServletRequestFactory>
		_serviceRegistration;

}