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

import com.liferay.httpservice.HttpServicePropsKeys.Action;
import com.liferay.httpservice.HttpServicePropsKeys;
import com.liferay.httpservice.internal.servlet.BundleServletContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.Filter;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class FilterTracker implements ServiceTrackerCustomizer<Filter, Filter> {

	public FilterTracker(HttpSupport httpSupport) {
		_httpSupport = httpSupport;
	}

	public Filter addingService(ServiceReference<Filter> serviceReference) {
		BundleContext bundleContext = _httpSupport.getBundleContext();

		Filter filter = bundleContext.getService(serviceReference);

		return doAction(serviceReference, filter, Action.ADDING);
	}

	public void modifiedService(
		ServiceReference<Filter> serviceReference, Filter filter) {

		doAction(serviceReference, filter, Action.MODIFIED);
	}

	public void removedService(
		ServiceReference<Filter> serviceReference, Filter filter) {

		doAction(serviceReference, filter, Action.REMOVED);
	}

	protected Filter doAction(
		ServiceReference<Filter> serviceReference, Filter filter,
		Action action) {

		String pattern = GetterUtil.getString(
			serviceReference.getProperty("pattern"));

		Map<String, String> initParameters = new Hashtable<String, String>();

		if (action != Action.REMOVED) {
			for (String key : serviceReference.getPropertyKeys()) {
				if (key.startsWith(HttpServicePropsKeys.INIT_PREFIX)) {
					String value = GetterUtil.getString(
						serviceReference.getProperty(key));

					initParameters.put(
						key.substring(
							HttpServicePropsKeys.INIT_PREFIX.length()), value);
				}
			}

			int serviceRanking = GetterUtil.getInteger(
				serviceReference.getProperty(
					HttpServicePropsKeys.SERVICE_RANKING));

			initParameters.put(
				HttpServicePropsKeys.SERVICE_RANKING,
				String.valueOf(serviceRanking));
		}

		Bundle bundle = serviceReference.getBundle();

		try {
			BundleServletContext bundleServletContext =
				_httpSupport.getBundleServletContext(bundle);

			if (action != Action.ADDING) {
				bundleServletContext.unregisterFilter(pattern);
			}

			if (action != Action.REMOVED) {
				String contextId = GetterUtil.getString(
					serviceReference.getProperty(
						HttpServicePropsKeys.CONTEXT_ID));

				HttpContext httpContext = _httpSupport.getHttpContext(
					contextId);

				if (httpContext == null) {
					httpContext = bundleServletContext.getHttpContext();
				}

				bundleServletContext.registerFilter(
					pattern, filter, initParameters, httpContext);
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return filter;
	}

	private static Log _log = LogFactoryUtil.getLog(FilterTracker.class);

	private HttpSupport _httpSupport;

}