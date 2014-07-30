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

package com.liferay.portal.deploy.hot.extender.internal.handler;

import com.liferay.portal.deploy.hot.MessagingHotDeployListener;
import com.liferay.portal.deploy.hot.PortletHotDeployListener;
import com.liferay.portal.deploy.hot.extender.internal.event.ModuleHotDeployEvent;
import com.liferay.portal.deploy.hot.extender.internal.listener.ServicesHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.deploy.hot.HotDeployListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Miguel Pastor
 */
public class ApplicationHandler {

	public static ApplicationHandler getInstance() {
		return _instance;
	}

	public void registerApplication(
		Bundle bundle, ServletContext servletContext) {

		ModuleHotDeployEvent moduleHotDeployEvent = _buildHotDeployEvent(
			bundle, servletContext);

		_invokeDeploy(moduleHotDeployEvent);
	}

	public void unregisterApplication(
		Bundle bundle, ServletContext servletContext) {

		ModuleHotDeployEvent moduleHotDeployEvent = _buildHotDeployEvent(
			bundle, servletContext);

		_invokeUndeploy(moduleHotDeployEvent);
	}

	private ApplicationHandler() {
		_hotDeployListeners = new ArrayList<HotDeployListener>();

		_hotDeployListeners.add(new ServicesHotDeployListener());

		_hotDeployListeners.add(new PortletHotDeployListener());

		_hotDeployListeners.add(new MessagingHotDeployListener());
	}

	private ModuleHotDeployEvent _buildHotDeployEvent(
		Bundle bundle, ServletContext servletContext) {

		ClassLoader classLoader = _getClassLoader(bundle);

		return new ModuleHotDeployEvent(servletContext, classLoader, bundle);
	}

	private ClassLoader _getClassLoader(Bundle bundle) {
		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		return bundleWiring.getClassLoader();
	}

	private void _invokeDeploy(
		HotDeployListener hotDeployListener, HotDeployEvent hotDeployEvent) {

		PortletClassLoaderUtil.setServletContextName(
			hotDeployEvent.getServletContextName());

		try {
			hotDeployListener.invokeDeploy(hotDeployEvent);
		}
		catch (HotDeployException hde) {
			_log.error(hde, hde);
		}
		finally {
			PortletClassLoaderUtil.setServletContextName(null);
		}
	}

	private void _invokeDeploy(ModuleHotDeployEvent moduleHotDeployEvent) {
		for (HotDeployListener hotDeployListener : _hotDeployListeners) {
			_invokeDeploy(hotDeployListener, moduleHotDeployEvent);
		}
	}

	private void _invokeUndeploy(
		HotDeployListener hotDeployListener, HotDeployEvent hotDeployEvent) {

		PortletClassLoaderUtil.setServletContextName(
			hotDeployEvent.getServletContextName());

		try {
			hotDeployListener.invokeUndeploy(hotDeployEvent);
		}
		catch (HotDeployException hde) {
			_log.error(hde, hde);
		}
		finally {
			PortletClassLoaderUtil.setServletContextName(null);
		}
	}

	private void _invokeUndeploy(ModuleHotDeployEvent moduleHotDeployEvent) {
		for (HotDeployListener hotDeployListener : _hotDeployListeners) {
			_invokeUndeploy(hotDeployListener, moduleHotDeployEvent);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ApplicationHandler.class);

	private static ApplicationHandler _instance = new ApplicationHandler();

	private List<HotDeployListener> _hotDeployListeners;

}