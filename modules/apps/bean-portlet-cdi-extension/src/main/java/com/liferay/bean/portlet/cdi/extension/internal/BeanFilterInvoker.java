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

	public BeanFilterInvoker(
		Class<? extends PortletFilter> beanClass, BeanManager beanManager) {

		_beanManager = beanManager;

		_bean = beanManager.resolve(beanManager.getBeans(beanClass));

		_destroyMethod = _getMethod("destroy", beanClass);
		_initMethod = _getMethod("init", beanClass, FilterConfig.class);

		if (ActionFilter.class.isAssignableFrom(beanClass)) {
			_doFilterActionMethod = _getMethod(
				"doFilter", beanClass, ActionRequest.class,
				ActionResponse.class, FilterChain.class);
		}
		else {
			_doFilterActionMethod = null;
		}

		if (EventFilter.class.isAssignableFrom(beanClass)) {
			_doFilterEventMethod = _getMethod(
				"doFilter", beanClass, EventRequest.class, EventResponse.class,
				FilterChain.class);
		}
		else {
			_doFilterEventMethod = null;
		}

		if (HeaderFilter.class.isAssignableFrom(beanClass)) {
			_doFilterHeaderMethod = _getMethod(
				"doFilter", beanClass, HeaderRequest.class,
				HeaderResponse.class, FilterChain.class);
		}
		else {
			_doFilterHeaderMethod = null;
		}

		if (RenderFilter.class.isAssignableFrom(beanClass)) {
			_doFilterRenderMethod = _getMethod(
				"doFilter", beanClass, RenderRequest.class,
				RenderResponse.class, FilterChain.class);
		}
		else {
			_doFilterRenderMethod = null;
		}

		if (ResourceFilter.class.isAssignableFrom(beanClass)) {
			_doFilterResourceMethod = _getMethod(
				"doFilter", beanClass, ResourceRequest.class,
				ResourceResponse.class, FilterChain.class);
		}
		else {
			_doFilterResourceMethod = null;
		}
	}

	@Override
	public void destroy() {
		try {
			if (_destroyMethod != null) {
				_invokeMethod(_destroyMethod);
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
			_invokeMethod(
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
			_invokeMethod(
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
			_invokeMethod(
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
			_invokeMethod(
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
			_invokeMethod(
				_doFilterResourceMethod, resourceRequest, resourceResponse,
				filterChain);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws PortletException {
		if (_initMethod != null) {
			_invokeMethod(_initMethod, filterConfig);
		}
	}

	private Method _getMethod(
		String methodName, Class<?> beanClass, Class<?>... args) {

		try {
			return beanClass.getMethod(methodName, args);
		}
		catch (NoSuchMethodException nsme) {
			_log.error(nsme, nsme);

			return null;
		}
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
			throw new PortletException(ite.getCause());
		}
		catch (IllegalAccessException iae) {
			throw new PortletException(iae);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BeanFilterInvoker.class);

	private final Bean<?> _bean;
	private final BeanManager _beanManager;
	private final Method _destroyMethod;
	private final Method _doFilterActionMethod;
	private final Method _doFilterEventMethod;
	private final Method _doFilterHeaderMethod;
	private final Method _doFilterRenderMethod;
	private final Method _doFilterResourceMethod;
	private final Method _initMethod;

}