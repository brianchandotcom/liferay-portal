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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Field;

import javax.servlet.ServletContext;

/**
 * @author Igor Spasic
 */
public class ServerHotDeployDetector {

	public static ServerHotDeployDetector getInstance() {
		return _instance;
	}

	public static void init(ServletContext servletContext) {
		if (_instance != null) {
			return;
		}

		_instance = new ServerHotDeployDetector();

		_instance._init(servletContext);
	}

	public static boolean isHotDeployEnabled() {
		return getInstance()._hotDeployEnabled;
	}

	private static boolean _isHotDeployEnabledOnTomcat(
		ServletContext servletContext) throws Exception {

		// 1 ApplicationContextFacade
		Class type = servletContext.getClass();

		Field field = ReflectionUtil.getDeclaredField(type, "context");

		Object applicationContextFacade = field.get(servletContext);

		// 2 ApplicationContext
		type = field.getType();

		field = ReflectionUtil.getDeclaredField(type, "context");

		Object applicationContext = field.get(applicationContextFacade);

		// 3 StandardContext
		type = field.getType();

		// 4 ContextBase
		type = type.getSuperclass();

		field = ReflectionUtil.getDeclaredField(type, "parent");

		Object standardContext = field.get(applicationContext);

		type = standardContext.getClass();

		field = ReflectionUtil.getDeclaredField(type, "autoDeploy");

		Boolean autoDeploy = (Boolean)field.get(standardContext);

		return autoDeploy.booleanValue();
	}

	private void _init(ServletContext servletContext) {
		if (ServerDetector.isTomcat()) {
			try {
				_hotDeployEnabled = _isHotDeployEnabledOnTomcat(servletContext);
			}
			catch (Exception e) {
				_log.error("Tomcat Hot Deploy detection failed", e);
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to detect hot deploy mode of app server");
			}
		}

		if (_log.isInfoEnabled()) {
			if (_hotDeployEnabled) {
				_log.info("Hot Deploy enabled");
			}
			else {
				_log.info("Hot Deploy NOT enabled");
			}
		}
	}

	private static ServerHotDeployDetector _instance;

	private static Log _log = LogFactoryUtil.getLog(
		ServerHotDeployDetector.class);

	private boolean _hotDeployEnabled;

}