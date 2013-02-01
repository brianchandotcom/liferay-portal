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
import com.liferay.osgi.bootstrap.ModuleFrameworkConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Hashtable;

import javax.servlet.Filter;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Raymond Augé
 */
public class FilterTracker
	implements ModuleFrameworkConstants,
		ServiceTrackerCustomizer<Filter, Filter> {

	public FilterTracker(HttpSupport httpSupport) {
		_httpSupport = httpSupport;
	}

	public Filter addingService(ServiceReference<Filter> reference) {
		BundleContext bundleContext = _httpSupport.getBundleContext();

		Filter filter = bundleContext.getService(reference);

		return doAction(reference, filter, Action.ADDING);
	}

	public void modifiedService(
		ServiceReference<Filter> reference, Filter filter) {

		doAction(reference, filter, Action.MODIFIED);
	}

	public void removedService(
		ServiceReference<Filter> reference, Filter filter) {

		doAction(reference, filter, Action.REMOVED);
	}

	private Filter doAction(
		ServiceReference<Filter> reference, Filter filter, Action action) {

		String pattern = GetterUtil.getString(reference.getProperty(
			PATTERN));
		String contextId = GetterUtil.getString(
			reference.getProperty(CONTEXT_ID));
		int serviceRanking = GetterUtil.getInteger(
			reference.getProperty(SERVICE_PROPERTY_KEY_SERVICE_RANKING));

		Hashtable<String, String> initParams =
			new Hashtable<String, String>();

		if (action != Action.REMOVED) {
			for (String key : reference.getPropertyKeys()) {
				if (key.startsWith(INIT_PREFIX)) {
					String value = GetterUtil.getString(
						reference.getProperty(key));

					initParams.put(
						key.substring(INIT_PREFIX.length()), value);
				}
			}

			initParams.put(
				SERVICE_PROPERTY_KEY_SERVICE_RANKING,
				String.valueOf(serviceRanking));
		}

		Bundle bundle = reference.getBundle();

		try {
			BundleServletContext servletContext =
				_httpSupport.getServletContext(bundle);

			if (action != Action.ADDING) {
				servletContext.unregisterFilter(pattern);
			}

			if (action != Action.REMOVED) {
				HttpContext httpContext = _httpSupport.getHttpContext(
					contextId);

				if (httpContext == null) {
					httpContext = servletContext.getHttpContext();
				}

				servletContext.registerFilter(
					pattern, filter, initParams, httpContext);
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return filter;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServletTracker.class);

	private HttpSupport _httpSupport;

}