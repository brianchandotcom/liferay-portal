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
public class BeanFilterInvoker
	implements ActionFilter, EventFilter, HeaderFilter, RenderFilter,
			   ResourceFilter {

	public BeanFilterInvoker(Class<?> beanClass, BeanManager beanManager) {
		_beanManager = beanManager;

		_bean = beanManager.resolve(beanManager.getBeans(beanClass));

		if (ActionFilter.class.isAssignableFrom(beanClass)) {
			_doFilterActionMethod = getMethod(
				"doFilter", beanClass, ActionRequest.class,
				ActionResponse.class, FilterChain.class);
		}

		if (EventFilter.class.isAssignableFrom(beanClass)) {
			_doFilterEventMethod = getMethod(
				"doFilter", beanClass, EventRequest.class, EventResponse.class,
				FilterChain.class);
		}

		if (HeaderFilter.class.isAssignableFrom(beanClass)) {
			_doFilterHeaderMethod = getMethod(
				"doFilter", beanClass, HeaderRequest.class,
				HeaderResponse.class, FilterChain.class);
		}

		if (PortletFilter.class.isAssignableFrom(beanClass)) {
			_destroyMethod = getMethod("destroy", beanClass);
			_initMethod = getMethod("init", beanClass, FilterConfig.class);
		}
		else {
			_destroyMethod = null;
			_initMethod = null;
		}

		if (RenderFilter.class.isAssignableFrom(beanClass)) {
			_doFilterRenderMethod = getMethod(
				"doFilter", beanClass, RenderRequest.class,
				RenderResponse.class, FilterChain.class);
		}

		if (ResourceFilter.class.isAssignableFrom(beanClass)) {
			_doFilterResourceMethod = getMethod(
				"doFilter", beanClass, ResourceRequest.class,
				ResourceResponse.class, FilterChain.class);
		}
	}

	@Override
	public void destroy() {
		try {
			if (_destroyMethod != null) {
				invokeMethod(_destroyMethod);
			}
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

		if (_doFilterActionMethod == null) {
			filterChain.doFilter(actionRequest, actionResponse);
		}
		else {
			invokeMethod(
				_doFilterActionMethod, actionRequest, actionResponse,
				filterChain);
		}
	}

	@Override
	public void doFilter(
			EventRequest eventRequest, EventResponse eventResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		if (_doFilterEventMethod == null) {
			filterChain.doFilter(eventRequest, eventResponse);
		}
		else {
			invokeMethod(
				_doFilterEventMethod, eventRequest, eventResponse, filterChain);
		}
	}

	@Override
	public void doFilter(
			HeaderRequest headerRequest, HeaderResponse headerResponse,
			HeaderFilterChain filterChain)
		throws IOException, PortletException {

		if (_doFilterHeaderMethod == null) {
			filterChain.doFilter(headerRequest, headerResponse);
		}
		else {
			invokeMethod(
				_doFilterHeaderMethod, headerRequest, headerResponse,
				filterChain);
		}
	}

	@Override
	public void doFilter(
			RenderRequest renderRequest, RenderResponse renderResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		if (_doFilterRenderMethod == null) {
			filterChain.doFilter(renderRequest, renderResponse);
		}
		else {
			invokeMethod(
				_doFilterRenderMethod, renderRequest, renderResponse,
				filterChain);
		}
	}

	@Override
	public void doFilter(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		if (_doFilterResourceMethod == null) {
			filterChain.doFilter(resourceRequest, resourceResponse);
		}
		else {
			invokeMethod(
				_doFilterResourceMethod, resourceRequest, resourceResponse,
				filterChain);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws PortletException {
		if (_initMethod != null) {
			invokeMethod(_initMethod, filterConfig);
		}
	}

	protected Method getMethod(
		String methodName, Class<?> beanClass, Class<?>... args) {

		try {
			return beanClass.getMethod(methodName, args);
		}
		catch (NoSuchMethodException nsme) {
			_log.error(nsme, nsme);

			return null;
		}
	}

	protected void invokeMethod(Method method, Object... args)
		throws PortletException {

		try {
			Object beanInstance = _beanManager.getReference(
				_bean, _bean.getBeanClass(),
				_beanManager.createCreationalContext(_bean));

			method.invoke(beanInstance, args);
		}
		catch (IllegalAccessException | InvocationTargetException e) {
			throw new PortletException(e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BeanFilterInvoker.class);

	private final Bean<?> _bean;
	private final BeanManager _beanManager;
	private final Method _destroyMethod;
	private Method _doFilterActionMethod;
	private Method _doFilterEventMethod;
	private Method _doFilterHeaderMethod;
	private Method _doFilterRenderMethod;
	private Method _doFilterResourceMethod;
	private final Method _initMethod;

}