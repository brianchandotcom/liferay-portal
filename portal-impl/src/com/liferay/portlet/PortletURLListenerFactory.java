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

import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletURLListener;
import com.liferay.portal.kernel.util.InstanceFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletException;
import javax.portlet.PortletURLGenerationListener;
import javax.portlet.UnavailableException;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletURLListenerFactory {

	public static PortletURLGenerationListener create(
			PortletURLListener portletURLListener)
		throws PortletException {

		return _portletURLListenerFactory._create(portletURLListener);
	}

	public static void destroy(PortletURLListener portletURLListener) {
		_portletURLListenerFactory._destroy(portletURLListener);
	}

	private PortletURLListenerFactory() {
		_pool = new ConcurrentHashMap<>();
	}

	private PortletURLGenerationListener _create(
			PortletURLListener portletURLListener)
		throws PortletException {

		PortletApp portletApp = portletURLListener.getPortletApp();

		Map<String, PortletURLGenerationListener>
			portletURLGenerationListeners = _pool.get(
				portletApp.getServletContextName());

		if (portletURLGenerationListeners == null) {
			portletURLGenerationListeners = new ConcurrentHashMap<>();

			_pool.put(
				portletApp.getServletContextName(),
				portletURLGenerationListeners);
		}

		PortletURLGenerationListener portletURLGenerationListener =
			portletURLGenerationListeners.get(
				portletURLListener.getListenerClass());

		if (portletURLGenerationListener != null) {
			return portletURLGenerationListener;
		}

		if (portletApp.isWARFile()) {
			ServletContext servletContext = portletApp.getServletContext();

			PortletContextBag portletContextBag = PortletContextBagPool.get(
				portletApp.getServletContextName());

			Map<String, PortletURLGenerationListener> portletURLListenersMap =
				portletContextBag.getPortletURLListeners();

			portletURLGenerationListener = portletURLListenersMap.get(
				portletURLListener.getListenerClass());

			portletURLGenerationListener = _init(
				servletContext.getClassLoader(), portletURLListener,
				portletURLGenerationListener);
		}
		else {
			portletURLGenerationListener = _init(portletURLListener);
		}

		portletURLGenerationListeners.put(
			portletURLListener.getListenerClass(),
			portletURLGenerationListener);

		return portletURLGenerationListener;
	}

	private void _destroy(PortletURLListener portletURLListener) {
		PortletApp portletApp = portletURLListener.getPortletApp();

		Map<String, PortletURLGenerationListener>
			portletURLGenerationListeners = _pool.get(
				portletApp.getServletContextName());

		if (portletURLGenerationListeners == null) {
			return;
		}

		PortletURLGenerationListener portletURLGenerationListener =
			portletURLGenerationListeners.get(
				portletURLListener.getListenerClass());

		if (portletURLGenerationListener == null) {
			return;
		}

		portletURLGenerationListeners.remove(
			portletURLListener.getListenerClass());
	}

	private PortletURLGenerationListener _init(
			ClassLoader classLoader, PortletURLListener portletURLListener,
			PortletURLGenerationListener portletURLGenerationListener)
		throws PortletException {

		try {
			if (portletURLGenerationListener == null) {
				portletURLGenerationListener =
					(PortletURLGenerationListener)InstanceFactory.newInstance(
						classLoader, portletURLListener.getListenerClass());
			}
		}
		catch (Exception e) {
			throw new UnavailableException(e.getMessage());
		}

		return portletURLGenerationListener;
	}

	private PortletURLGenerationListener _init(
			PortletURLListener portletURLListener)
		throws PortletException {

		return _init(null, portletURLListener, null);
	}

	private static final PortletURLListenerFactory _portletURLListenerFactory =
		new PortletURLListenerFactory();

	private final Map<String, Map<String, PortletURLGenerationListener>> _pool;

}