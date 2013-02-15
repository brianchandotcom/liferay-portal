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

import com.liferay.httpservice.HttpServicePropsKeys;
import com.liferay.httpservice.internal.servlet.BundleServletContext;
import com.liferay.httpservice.internal.servlet.WebExtenderServlet;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class HttpSupport {

	public HttpSupport(
		BundleContext bundleContext, WebExtenderServlet webExtenderServlet) {

		_bundleContext = bundleContext;
		_webExtenderServlet = webExtenderServlet;
	}

	public BundleContext getBundleContext() {
		return _bundleContext;
	}

	public HttpContext getHttpContext(String contextId)
		throws InvalidSyntaxException {

		if (Validator.isNull(contextId)) {
			return null;
		}

		Filter filter = getHttpContextFilter(contextId);

		Collection<ServiceReference<HttpContext>>
			serviceReferences = _bundleContext.getServiceReferences(
				HttpContext.class, filter.toString());

		Iterator<ServiceReference<HttpContext>> iterator =
			serviceReferences.iterator();

		if (iterator.hasNext()) {
			ServiceReference<HttpContext> httpContextReference =
				iterator.next();

			return _bundleContext.getService(httpContextReference);
		}

		return null;
	}

	public Filter getHttpContextFilter(String contextId)
		throws InvalidSyntaxException {

		String filter =
			"(&(" + HttpServicePropsKeys.CONTEXT_ID + "=" + contextId + ")";

		return _bundleContext.createFilter(filter);
	}

	public BundleServletContext getNonWabServletContext(Bundle bundle) {
		String bundleContextName = BundleServletContext.getServletContextName(
			bundle, true);

		ServletContext servletContext = ServletContextPool.get(
			bundleContextName);

		if (servletContext == null) {
			BundleServletContext bundleServletContext =
				new BundleServletContext(
					bundle, bundleContextName, _webExtenderServlet);

			bundleServletContext.setServletContextName(bundleContextName);

			ServletContextPool.put(bundleContextName, bundleServletContext);

			servletContext = bundleServletContext;
		}

		return (BundleServletContext)servletContext;
	}

	public BundleServletContext getServletContext(Bundle bundle)
		throws InvalidSyntaxException {

		BundleServletContext bundleServletContext = getWabServletContext(
			bundle);

		if (bundleServletContext != null) {
			return bundleServletContext;
		}

		return getNonWabServletContext(bundle);
	}

	public BundleServletContext getWabServletContext(Bundle bundle)
		throws InvalidSyntaxException {

		Filter filter = getWabServletContextFilter(bundle);

		Collection<ServiceReference<BundleServletContext>>
			serviceReferences = _bundleContext.getServiceReferences(
				BundleServletContext.class, filter.toString());

		Iterator<ServiceReference<BundleServletContext>> iterator =
			serviceReferences.iterator();

		if (iterator.hasNext()) {
			ServiceReference<BundleServletContext> servletContextReference =
				iterator.next();

			return _bundleContext.getService(servletContextReference);
		}

		return null;
	}

	public Filter getWabServletContextFilter(Bundle bundle)
		throws InvalidSyntaxException {

		StringBundler sb = new StringBundler(15);

		sb.append("(&(");
		sb.append(HttpServicePropsKeys.BUNDLE_SYMBOLICNAME);
		sb.append("=");
		sb.append(bundle.getSymbolicName());
		sb.append(")(");
		sb.append(HttpServicePropsKeys.BUNDLE_VERSION);
		sb.append("=");
		sb.append(bundle.getVersion().toString());
		sb.append(")(");
		sb.append(HttpServicePropsKeys.BUNDLE_ID);
		sb.append("=");
		sb.append(bundle.getBundleId());
		sb.append(")(");
		sb.append(HttpServicePropsKeys.WEB_CONTEXTPATH);
		sb.append("=*))");

		return _bundleContext.createFilter(sb.toString());
	}

	public WebExtenderServlet getWebExtenderServlet() {
		return _webExtenderServlet;
	}

	private BundleContext _bundleContext;
	private WebExtenderServlet _webExtenderServlet;

}