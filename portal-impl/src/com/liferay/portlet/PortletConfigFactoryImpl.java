/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.lang.DoPrivilegedUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class PortletConfigFactoryImpl implements PortletConfigFactory {

	public PortletConfigFactoryImpl() {
		_pool = new ConcurrentHashMap<String, Map<String, PortletConfig>>();
	}

	@Override
	public PortletConfig create(
		Portlet portlet, ServletContext servletContext) {

		Map<String, PortletConfig> portletConfigs = _pool.get(
			portlet.getRootPortletId());

		if (portletConfigs == null) {
			portletConfigs = new ConcurrentHashMap<String, PortletConfig>();

			_pool.put(portlet.getRootPortletId(), portletConfigs);
		}

		PortletConfig portletConfig = portletConfigs.get(
			portlet.getPortletId());

		if (portletConfig == null) {
			PortletContext portletContext = PortletContextFactory.create(
				portlet, servletContext);

			portletConfig = new PortletConfigImpl(portlet, portletContext);

			portletConfigs.put(portlet.getPortletId(), portletConfig);
		}

		return DoPrivilegedUtil.wrap(portletConfig);
	}

	@Override
	public void destroy(Portlet portlet) {
		_pool.remove(portlet.getRootPortletId());
	}

	@Override
	public PortletConfig update(Portlet portlet) {
		Map<String, PortletConfig> portletConfigs = _pool.get(
			portlet.getRootPortletId());

		if (portletConfigs == null) {
			return null;
		}

		PortletConfig portletConfig = portletConfigs.get(
			portlet.getPortletId());

		PortletContext portletContext = portletConfig.getPortletContext();

		portletConfig = new PortletConfigImpl(portlet, portletContext);

		portletConfigs.put(portlet.getPortletId(), portletConfig);

		return DoPrivilegedUtil.wrap(portletConfig);
	}

	private Map<String, Map<String, PortletConfig>> _pool;

}