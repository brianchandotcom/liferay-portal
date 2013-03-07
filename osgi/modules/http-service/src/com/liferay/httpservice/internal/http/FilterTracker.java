/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.httpservice.internal.http;

import com.liferay.httpservice.internal.servlet.BundleServletContext;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Dictionary;

import javax.servlet.Filter;

import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class FilterTracker extends
	BaseServiceTrackerCustomizer<Filter, Filter> {

	public FilterTracker(HttpSupport httpSupport) {
		super(httpSupport);
	}

	@Override
	protected void registerService(
			BundleServletContext bundleServletContext,
			ServiceReference<Filter> serviceReference, Filter filter,
			Dictionary<String, String> initParameters, HttpContext httpContext)
		throws Exception {

		String pattern = GetterUtil.getString(
			serviceReference.getProperty("pattern"));

		bundleServletContext.registerFilter(
			pattern, filter, initParameters, httpContext);
	}

	@Override
	protected void unregisterService(
		BundleServletContext bundleServletContext,
		ServiceReference<Filter> serviceReference) {

		String pattern = GetterUtil.getString(
			serviceReference.getProperty("pattern"));

		bundleServletContext.unregisterFilter(pattern);
	}

}