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

package com.liferay.portal.kernel.bean;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public class RendererUtil {

	public static Renderer getRenderer() {
		PortalRuntimePermission.checkGetBeanProperty(RendererUtil.class);

		return _renderer;
	}

	public static String renderBean(
			HttpServletRequest request, HttpServletResponse response,
			Object bean)
		throws RendererException {

		return getRenderer().renderBean(request, response, bean);
	}

	public static String renderBean(
			HttpServletRequest request, HttpServletResponse response,
			Object bean, String varientSuffix)
		throws RendererException {

		return getRenderer().renderBean(request, response, bean, varientSuffix);
	}

	public static String renderBean(
			HttpServletRequest request, HttpServletResponse response,
			String servletContextName, Object bean)
		throws RendererException {

		return getRenderer().renderBean(
			request, response, servletContextName, bean);
	}

	public static String renderBean(
			HttpServletRequest request, HttpServletResponse response,
			String servletContextName, Object bean, String varientSuffix)
		throws RendererException {

		return getRenderer().renderBean(
			request, response, servletContextName, bean, varientSuffix);
	}

	public static String renderBean(
			PortletRequest portletRequest, PortletResponse portletResponse,
			Object bean)
		throws RendererException {

		return getRenderer().renderBean(portletRequest, portletResponse, bean);
	}

	public static String renderBean(
			PortletRequest portletRequest, PortletResponse portletResponse,
			Object bean, String varientSuffix)
		throws RendererException {

		return getRenderer().renderBean(
			portletRequest, portletResponse, bean, varientSuffix);
	}

	public static String renderBean(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String servletContextName, Object bean)
		throws RendererException {

		return getRenderer().renderBean(
			portletRequest, portletResponse, servletContextName, bean);
	}

	public static String renderBean(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String servletContextName, Object bean, String varientSuffix)
		throws RendererException {

		return getRenderer().renderBean(
			portletRequest, portletResponse, servletContextName, bean,
			varientSuffix);
	}

	public static String renderEntity(
			HttpServletRequest request, HttpServletResponse response,
			String className, Object classPK)
		throws RendererException {

		return getRenderer().renderEntity(
			request, response, className, classPK);
	}

	public static String renderEntity(
			HttpServletRequest request, HttpServletResponse response,
			String className, Object classPK, String varientSuffix)
		throws RendererException {

		return getRenderer().renderEntity(
			request, response, className, classPK, varientSuffix);
	}

	public static String renderEntity(
			HttpServletRequest request, HttpServletResponse response,
			String servletContextName, String className, Object classPK)
		throws RendererException {

		return getRenderer().renderEntity(
			request, response, servletContextName, className, classPK);
	}

	public static String renderEntity(
			HttpServletRequest request, HttpServletResponse response,
			String servletContextName, String className, Object classPK,
			String varientSuffix)
		throws RendererException {

		return getRenderer().renderEntity(
			request, response, servletContextName, className, classPK,
			varientSuffix);
	}

	public static String renderEntity(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String className, Object classPK)
		throws RendererException {

		return getRenderer().renderEntity(
			portletRequest, portletResponse, className, classPK);
	}

	public static String renderEntity(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String className, Object classPK, String varientSuffix)
		throws RendererException {

		return getRenderer().renderEntity(
			portletRequest, portletResponse, className, classPK, varientSuffix);
	}

	public static String renderEntity(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String servletContextName, String className, Object classPK)
		throws RendererException {

		return getRenderer().renderEntity(
			portletRequest, portletResponse, servletContextName, className,
			classPK);
	}

	public static String renderEntity(
			PortletRequest portletRequest, PortletResponse portletResponse,
			String servletContextName, String className, Object classPK,
			String varientSuffix)
		throws RendererException {

		return getRenderer().renderEntity(
			portletRequest, portletResponse, servletContextName, className,
			classPK, varientSuffix);
	}

	public void setRenderer(Renderer renderer) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_renderer = renderer;
	}

	private static Renderer _renderer;

}