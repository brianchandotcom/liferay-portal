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

import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletURLListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletException;
import javax.portlet.PortletURLGenerationListener;
import javax.portlet.UnavailableException;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletURLListenerFactory {

	public static PortletURLGenerationListener create(
			PortletURLListener portletURLListener)
		throws PortletException {

		return _instance._create(portletURLListener);
	}

	public static void destroy(PortletURLListener portletURLListener) {
		_instance._destroy(portletURLListener);
	}

	private PortletURLListenerFactory() {
		_pool = new ConcurrentHashMap
			<String, Map<String, PortletURLGenerationListener>>();
	}

	private PortletURLGenerationListener _create(
			PortletURLListener portletURLListener)
		throws PortletException {

		PortletApp portletApp = portletURLListener.getPortletApp();

		Map<String, PortletURLGenerationListener>
			portletURLGenerationListeners = _pool.get(
				portletApp.getServletContextName());

		if (portletURLGenerationListeners == null) {
			portletURLGenerationListeners =
				new ConcurrentHashMap<String, PortletURLGenerationListener>();

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
			PortletContextBag portletContextBag = PortletContextBagPool.get(
				portletApp.getServletContextName());

			portletURLGenerationListener =
				portletContextBag.getPortletURLListeners().get(
					portletURLListener.getListenerClass());

			portletURLGenerationListener = _init(
				portletURLListener, portletURLGenerationListener);
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
			PortletURLListener portletURLListener)
		throws PortletException {

		return _init(portletURLListener, null);
	}

	private PortletURLGenerationListener _init(
			PortletURLListener portletURLListener,
			PortletURLGenerationListener portletURLGenerationListener)
		throws PortletException {

		try {
			if (portletURLGenerationListener == null) {
				portletURLGenerationListener =
					(PortletURLGenerationListener)InstanceFactory.newInstance(
						portletURLListener.getListenerClass());
			}
		}
		catch (Exception e) {
			throw new UnavailableException(e.getMessage());
		}

		return portletURLGenerationListener;
	}

	private static final PortletURLListenerFactory _instance =
		new PortletURLListenerFactory();

	private final Map<String, Map<String, PortletURLGenerationListener>> _pool;

}