/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package org.eclipse.equinox.http.servlet.internal.context;

import java.util.EventListener;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.eclipse.equinox.http.servlet.internal.HttpServletEndpointController;
import org.eclipse.equinox.http.servlet.internal.registration.EndpointRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.FilterRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.ListenerRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.ResourceRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.ServletRegistration;
import org.eclipse.equinox.http.servlet.internal.servlet.HttpSessionAdaptor;
import org.eclipse.equinox.http.servlet.internal.servlet.Match;
import org.eclipse.equinox.http.servlet.internal.util.EventListeners;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

/**
 * @author Dante Wang
 */
public interface ServletContextHelperController {

	public FilterRegistration addFilterRegistration(
			ServiceReference<Filter> serviceReference)
		throws ServletException;

	public ListenerRegistration addListenerRegistration(
			ServiceReference<EventListener> serviceReference)
		throws ServletException;

	public ResourceRegistration addResourceRegistration(
		ServiceReference<?> serviceReference);

	public ServletRegistration addServletRegistration(
			ServiceReference<Servlet> serviceReference)
		throws ServletException;

	public void destroy();

	public Map<String, HttpSessionAdaptor> getActiveSessions();

	public String getContextName();

	public String getContextPath();

	public DispatchTargets getDispatchTargets(String pathString);

	public DispatchTargets getDispatchTargets(
		String servletName, String requestURI, String servletPath,
		String pathInfo, String extension, String queryString, Match match);

	public Set<EndpointRegistration<?>> getEndpointRegistrations();

	public EventListeners getEventListeners();

	public Set<FilterRegistration> getFilterRegistrations();

	public String getFullContextPath();

	public HttpServletEndpointController getHttpServletEndpointController();

	public Map<String, String> getInitParams();

	public Set<ListenerRegistration> getListenerRegistrations();

	public HttpSessionAdaptor getSessionAdaptor(
		HttpSession httpSession, ServletContext servletContext);

	public boolean matches(org.osgi.framework.Filter targetFilter);

	public boolean matches(ServiceReference<?> serviceReference);

	public void removeActiveSession(String sessionId);

	public void ungetServletContextHelper(Bundle bundle);

}