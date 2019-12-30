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

package com.liferay.opensocial.shindig.servlet;

import com.liferay.exportimport.kernel.xstream.XStreamAliasRegistryUtil;
import com.liferay.opensocial.model.impl.GadgetImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BasePortalLifecycle;

import java.lang.reflect.Field;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Michael Young
 */
public class GuiceServletContextListener
	extends BasePortalLifecycle implements ServletContextListener {

	public GuiceServletContextListener() {
		_guiceServletContextListener =
			new org.apache.shindig.common.servlet.GuiceServletContextListener();

		try {
			Class<?> clazz = _guiceServletContextListener.getClass();

			Field field = clazz.getDeclaredField("jmxInitialized");

			field.setAccessible(true);

			field.set(_guiceServletContextListener, true);
		}
		catch (ReflectiveOperationException roe) {
			if (_log.isWarnEnabled()) {
				_log.warn(roe, roe);
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		_destroyedServletContextEvent = servletContextEvent;

		XStreamAliasRegistryUtil.unregister(GadgetImpl.class, "Gadget");

		portalDestroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		_initializedServletContextEvent = servletContextEvent;

		XStreamAliasRegistryUtil.register(GadgetImpl.class, "Gadget");

		registerPortalLifecycle();
	}

	@Override
	protected void doPortalDestroy() throws Exception {
		_guiceServletContextListener.contextDestroyed(
			_destroyedServletContextEvent);
	}

	@Override
	protected void doPortalInit() throws Exception {
		_guiceServletContextListener.contextInitialized(
			_initializedServletContextEvent);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GuiceServletContextListener.class);

	private static ServletContextEvent _initializedServletContextEvent;

	private ServletContextEvent _destroyedServletContextEvent;
	private final ServletContextListener _guiceServletContextListener;

}