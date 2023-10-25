/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.context;

import java.util.EventListener;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.eclipse.equinox.http.servlet.internal.HttpServletEndpointController;
import org.eclipse.equinox.http.servlet.internal.context.ContextController;
import org.eclipse.equinox.http.servlet.internal.context.DispatchTargets;
import org.eclipse.equinox.http.servlet.internal.context.ServletContextHelperDataContext;
import org.eclipse.equinox.http.servlet.internal.registration.EndpointRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.FilterRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.ListenerRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.ResourceRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.ServletRegistration;
import org.eclipse.equinox.http.servlet.internal.servlet.HttpSessionAdaptor;
import org.eclipse.equinox.http.servlet.internal.servlet.Match;
import org.eclipse.equinox.http.servlet.internal.util.EventListeners;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Dante Wang
 */
public class LiferayContextController extends ContextController {

	public LiferayContextController(
		BundleContext bundleContext,
		ServiceReference<ServletContextHelper> serviceReference,
		ServletContextHelperDataContext servletContextHelperDataContext,
		HttpServletEndpointController httpServletEndpointController,
		String contextName, String contextPath) {

		_contextController = new ContextController(
			bundleContext, bundleContext, serviceReference,
			servletContextHelperDataContext, httpServletEndpointController,
			contextName, contextPath);
	}

	@Override
	public FilterRegistration addFilterRegistration(
			ServiceReference<Filter> serviceReference)
		throws ServletException {

		return _contextController.addFilterRegistration(serviceReference);
	}

	@Override
	public ListenerRegistration addListenerRegistration(
			ServiceReference<EventListener> serviceReference)
		throws ServletException {

		return _contextController.addListenerRegistration(serviceReference);
	}

	@Override
	public ResourceRegistration addResourceRegistration(
		ServiceReference<?> serviceReference) {

		return _contextController.addResourceRegistration(serviceReference);
	}

	@Override
	public ServletRegistration addServletRegistration(
			ServiceReference<Servlet> serviceReference)
		throws ServletException {

		return _contextController.addServletRegistration(serviceReference);
	}

	@Override
	public void destroy() {
		_contextController.destroy();
	}

	@Override
	public Map<String, HttpSessionAdaptor> getActiveSessions() {
		return _contextController.getActiveSessions();
	}

	@Override
	public String getContextName() {
		return _contextController.getContextName();
	}

	@Override
	public String getContextPath() {
		return _contextController.getContextPath();
	}

	@Override
	public DispatchTargets getDispatchTargets(String pathString) {
		return _contextController.getDispatchTargets(pathString);
	}

	@Override
	public DispatchTargets getDispatchTargets(
		String servletName, String requestURI, String servletPath,
		String pathInfo, String extension, String queryString, Match match) {

		return _contextController.getDispatchTargets(
			servletName, requestURI, servletPath, pathInfo, extension,
			queryString, match);
	}

	@Override
	public Set<EndpointRegistration<?>> getEndpointRegistrations() {
		return _contextController.getEndpointRegistrations();
	}

	@Override
	public EventListeners getEventListeners() {
		return _contextController.getEventListeners();
	}

	@Override
	public Set<FilterRegistration> getFilterRegistrations() {
		return _contextController.getFilterRegistrations();
	}

	@Override
	public String getFullContextPath() {
		return _contextController.getFullContextPath();
	}

	@Override
	public HttpServletEndpointController getHttpServletEndpointController() {
		return _contextController.getHttpServletEndpointController();
	}

	@Override
	public Map<String, String> getInitParams() {
		return _contextController.getInitParams();
	}

	@Override
	public Set<ListenerRegistration> getListenerRegistrations() {
		return _contextController.getListenerRegistrations();
	}

	@Override
	public HttpSessionAdaptor getSessionAdaptor(
		HttpSession httpSession, ServletContext servletContext) {

		return _contextController.getSessionAdaptor(
			httpSession, servletContext);
	}

	@Override
	public boolean matches(org.osgi.framework.Filter filter) {
		return _contextController.matches(filter);
	}

	@Override
	public boolean matches(ServiceReference<?> serviceReference) {
		return _contextController.matches(serviceReference);
	}

	@Override
	public void removeActiveSession(String sessionId) {
		_contextController.removeActiveSession(sessionId);
	}

	@Override
	public void ungetServletContextHelper(Bundle bundle) {
		_contextController.ungetServletContextHelper(bundle);
	}

	private final ContextController _contextController;

}