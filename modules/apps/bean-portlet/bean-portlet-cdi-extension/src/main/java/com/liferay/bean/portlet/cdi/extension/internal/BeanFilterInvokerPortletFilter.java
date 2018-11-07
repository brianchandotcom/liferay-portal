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

package com.liferay.bean.portlet.cdi.extension.internal;

import com.liferay.bean.portlet.cdi.extension.internal.scope.ScopedBeanManager;
import com.liferay.bean.portlet.cdi.extension.internal.scope.ScopedBeanManagerThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.HeaderFilter;
import javax.portlet.filter.HeaderFilterChain;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

/**
 * @author Neil Griffin
 */
public class BeanFilterInvokerPortletFilter
	implements ActionFilter, EventFilter, HeaderFilter, RenderFilter,
			   ResourceFilter {

	public BeanFilterInvokerPortletFilter(
		Class<? extends PortletFilter> filterClass, BeanManager beanManager) {

		_beanManager = beanManager;

		_bean = beanManager.resolve(beanManager.getBeans(filterClass));
	}

	@Override
	public void destroy() {
		try {
			_invokeMethod(_destroyMethod);
		}
		catch (PortletException pe) {
			_log.error(pe, pe);
		}
	}

	@Override
	public void doFilter(
			ActionRequest actionRequest, ActionResponse actionResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		if (ActionFilter.class.isAssignableFrom(_bean.getBeanClass())) {
			_invokeMethodWithActiveScopes(
				_actionDoFilterMethod, actionRequest, actionResponse,
				filterChain);
		}
		else {
			filterChain.doFilter(actionRequest, actionResponse);
		}
	}

	@Override
	public void doFilter(
			EventRequest eventRequest, EventResponse eventResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		if (EventFilter.class.isAssignableFrom(_bean.getBeanClass())) {
			_invokeMethodWithActiveScopes(
				_eventDoFilterMethod, eventRequest, eventResponse, filterChain);
		}
		else {
			filterChain.doFilter(eventRequest, eventResponse);
		}
	}

	@Override
	public void doFilter(
			HeaderRequest headerRequest, HeaderResponse headerResponse,
			HeaderFilterChain headerFilterChain)
		throws IOException, PortletException {

		if (HeaderFilter.class.isAssignableFrom(_bean.getBeanClass())) {
			_invokeMethodWithActiveScopes(
				_headerDoFilterMethod, headerRequest, headerResponse,
				headerFilterChain);
		}
		else {
			headerFilterChain.doFilter(headerRequest, headerResponse);
		}
	}

	@Override
	public void doFilter(
			RenderRequest renderRequest, RenderResponse renderResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		if (RenderFilter.class.isAssignableFrom(_bean.getBeanClass())) {
			_invokeMethodWithActiveScopes(
				_renderDoFilterMethod, renderRequest, renderResponse,
				filterChain);
		}
		else {
			filterChain.doFilter(renderRequest, renderResponse);
		}
	}

	@Override
	public void doFilter(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		if (ResourceFilter.class.isAssignableFrom(_bean.getBeanClass())) {
			_invokeMethodWithActiveScopes(
				_resourceDoFilterMethod, resourceRequest, resourceResponse,
				filterChain);
		}
		else {
			filterChain.doFilter(resourceRequest, resourceResponse);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws PortletException {
		_invokeMethod(_initMethod, filterConfig);
	}

	private void _invokeMethod(Method method, Object... args)
		throws PortletException {

		try {
			Object beanInstance = _beanManager.getReference(
				_bean, _bean.getBeanClass(),
				_beanManager.createCreationalContext(_bean));

			method.invoke(beanInstance, args);
		}
		catch (InvocationTargetException ite) {
			Throwable cause = ite.getCause();

			if (cause instanceof PortletException) {
				throw (PortletException)cause;
			}

			throw new PortletException(cause);
		}
		catch (IllegalAccessException iae) {
			throw new PortletException(iae);
		}
	}

	private void _invokeMethodWithActiveScopes(
			Method method, PortletRequest portletRequest,
			PortletResponse portletResponse, Object filterChain)
		throws PortletException {

		ScopedBeanManagerThreadLocal.invokeWithScopedBeanManager(
			() -> _invokeMethod(
				method, portletRequest, portletResponse, filterChain),
			() -> new ScopedBeanManager(portletRequest, portletResponse, null));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BeanFilterInvokerPortletFilter.class);

	private static final Method _actionDoFilterMethod;
	private static final Method _destroyMethod;
	private static final Method _eventDoFilterMethod;
	private static final Method _headerDoFilterMethod;
	private static final Method _initMethod;
	private static final Method _renderDoFilterMethod;
	private static final Method _resourceDoFilterMethod;

	static {
		try {
			_destroyMethod = PortletFilter.class.getMethod("destroy");
			_actionDoFilterMethod = ActionFilter.class.getMethod(
				"doFilter", ActionRequest.class, ActionResponse.class,
				FilterChain.class);
			_eventDoFilterMethod = EventFilter.class.getMethod(
				"doFilter", EventRequest.class, EventResponse.class,
				FilterChain.class);
			_headerDoFilterMethod = HeaderFilter.class.getMethod(
				"doFilter", HeaderRequest.class, HeaderResponse.class,
				HeaderFilterChain.class);
			_renderDoFilterMethod = RenderFilter.class.getMethod(
				"doFilter", RenderRequest.class, RenderResponse.class,
				FilterChain.class);
			_resourceDoFilterMethod = ResourceFilter.class.getMethod(
				"doFilter", ResourceRequest.class, ResourceResponse.class,
				FilterChain.class);
			_initMethod = PortletFilter.class.getMethod(
				"init", FilterConfig.class);
		}
		catch (NoSuchMethodException nsme) {
			throw new ExceptionInInitializerError(nsme);
		}
	}

	private final Bean<?> _bean;
	private final BeanManager _beanManager;

}