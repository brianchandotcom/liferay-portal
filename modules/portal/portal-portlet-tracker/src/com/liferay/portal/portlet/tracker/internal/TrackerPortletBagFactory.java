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
package com.liferay.portal.portlet.tracker.internal;

import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portlet.PortletBagFactory;

import javax.portlet.Portlet;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;

public class TrackerPortletBagFactory extends PortletBagFactory {

	public TrackerPortletBagFactory(
		Bundle bundle, javax.portlet.Portlet portletInstance) {

		_bundle = bundle;
		_portletInstance = portletInstance;
	}

	@Override
	public PortletBag create(com.liferay.portal.model.Portlet portlet)
		throws Exception {

		if (_bundle == null) {
			throw new IllegalStateException("Bundle is null");
		}

		super.setClassLoader(_classLoader);

		ServletContextBuilder servletContextBuilder =
			new ServletContextBuilder(
				_servletContext, _bundle, _classLoader);

		ServletContext servletContext = servletContextBuilder.build();

		super.setServletContext(servletContext);

		return super.create(portlet);
	}

	@Override
	public void setClassLoader(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Override
	protected Portlet getPortletInstance(
			com.liferay.portal.model.Portlet portlet)
		throws InstantiationException, IllegalAccessException {

		return _portletInstance;
	}

	private final Bundle _bundle;
	private ClassLoader _classLoader;
	private final javax.portlet.Portlet _portletInstance;
	private ServletContext _servletContext;

}