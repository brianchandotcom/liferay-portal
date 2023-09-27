/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.portlet.LiferayPortletContext;
import com.liferay.portal.kernel.portlet.PortletContextFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletContext;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletContextFactoryImpl implements PortletContextFactory {

	@Override
	public PortletContext create(
		Portlet portlet, ServletContext servletContext) {

		Map<String, PortletContext> portletContexts = _pool.get(
			portlet.getRootPortletId());

		if (portletContexts == null) {
			portletContexts = new ConcurrentHashMap<>();

			_pool.put(portlet.getRootPortletId(), portletContexts);
		}

		PortletContext portletContext = portletContexts.get(
			portlet.getPortletId());

		if ((portletContext != null) &&
			_isSamePortletDeployedStatus(portlet, portletContext)) {

			return portletContext;
		}

		PortletApp portletApp = portlet.getPortletApp();

		if (!portlet.isUndeployedPortlet() && portletApp.isWARFile()) {
			servletContext = portletApp.getServletContext();
		}

		portletContext = new PortletContextImpl(portlet, servletContext);

		portletContexts.put(portlet.getPortletId(), portletContext);

		return portletContext;
	}

	@Override
	public PortletContext createUntrackedInstance(
		Portlet portlet, ServletContext servletContext) {

		return new PortletContextImpl(portlet, servletContext);
	}

	@Override
	public void destroy(Portlet portlet) {
		_pool.remove(portlet.getRootPortletId());
	}

	private boolean _isSamePortletDeployedStatus(
		Portlet portlet, PortletContext portletContext) {

		LiferayPortletContext liferayPortletContext =
			(LiferayPortletContext)portletContext;

		Portlet existingPortlet = liferayPortletContext.getPortlet();

		if ((existingPortlet != null) &&
			(portlet.isUndeployedPortlet() ==
				existingPortlet.isUndeployedPortlet())) {

			return true;
		}

		return false;
	}

	private final Map<String, Map<String, PortletContext>> _pool =
		new ConcurrentHashMap<>();

}