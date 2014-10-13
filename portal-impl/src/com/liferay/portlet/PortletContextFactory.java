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

package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.security.lang.DoPrivilegedUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletContext;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletContextFactory {

	public static PortletContext create(
		Portlet portlet, ServletContext servletContext) {

		return _instance._create(portlet, servletContext);
	}

	public static void destroy(Portlet portlet) {
		_instance._destroy(portlet);
	}

	private PortletContextFactory() {
		_pool = new ConcurrentHashMap<String, Map<String, PortletContext>>();
	}

	private PortletContext _create(
		Portlet portlet, ServletContext servletContext) {

		Map<String, PortletContext> portletContexts = _pool.get(
			portlet.getRootPortletId());

		if (portletContexts == null) {
			portletContexts = new ConcurrentHashMap<String, PortletContext>();

			_pool.put(portlet.getRootPortletId(), portletContexts);
		}

		PortletContext portletContext = portletContexts.get(
			portlet.getPortletId());

		if (portletContext != null) {
			return DoPrivilegedUtil.wrap(portletContext);
		}

		PortletApp portletApp = portlet.getPortletApp();

		if (portletApp.isWARFile()) {
			PortletBag portletBag = PortletBagPool.get(
				portlet.getRootPortletId());

			if (portletBag == null) {
				_log.error(
					"Portlet " + portlet.getRootPortletId() +
						" has a null portlet bag");
			}

			//String mainPath = (String)ctx.getAttribute(WebKeys.MAIN_PATH);

			servletContext = portletBag.getServletContext();

			// Context path for the portal must be passed to individual portlets

			//ctx.setAttribute(WebKeys.MAIN_PATH, mainPath);
		}

		portletContext = new PortletContextImpl(portlet, servletContext);

		portletContexts.put(portlet.getPortletId(), portletContext);

		return DoPrivilegedUtil.wrap(portletContext);
	}

	private void _destroy(Portlet portlet) {
		_pool.remove(portlet.getRootPortletId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletContextFactory.class);

	private static final PortletContextFactory _instance =
		new PortletContextFactory();

	private final Map<String, Map<String, PortletContext>> _pool;

}