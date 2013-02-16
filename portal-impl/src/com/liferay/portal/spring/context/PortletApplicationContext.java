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

package com.liferay.portal.spring.context;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PreloadClassLoader;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.PACLPolicyManager;
import com.liferay.portal.spring.util.FilterClassLoader;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * <p>
 * This web application context will first load bean definitions in the
 * portalContextConfigLocation parameter in web.xml. Then, the context will load
 * bean definitions specified by the property "spring.configs" in
 * service.properties.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    PortletContextLoaderListener
 */
public class PortletApplicationContext extends XmlWebApplicationContext {

	public static ClassLoader getBeanClassLoader() {
		if (_isUseRestrictedClassLoader()) {
			boolean enabled = PortalSecurityManagerThreadLocal.isEnabled();

			try {
				PortalSecurityManagerThreadLocal.setEnabled(false);

				return new PreloadClassLoader(
					PortletClassLoaderUtil.getClassLoader(), _classes);
			}
			finally {
				PortalSecurityManagerThreadLocal.setEnabled(enabled);
			}
		}

		ClassLoader beanClassLoader =
			AggregateClassLoader.getAggregateClassLoader(
				new ClassLoader[] {
					PortletClassLoaderUtil.getClassLoader(),
					ClassLoaderUtil.getPortalClassLoader()
				});

		return new FilterClassLoader(beanClassLoader);
	}

	@Override
	protected String[] getDefaultConfigLocations() {
		return new String[0];
	}

	protected String[] getPortletConfigLocations() {
		String[] configLocations = getConfigLocations();

		ClassLoader classLoader = PortletClassLoaderUtil.getClassLoader();

		Configuration serviceBuilderPropertiesConfiguration = null;

		try {
			serviceBuilderPropertiesConfiguration =
				ConfigurationFactoryUtil.getConfiguration(
					classLoader, "service");
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to read service.properties");
			}

			return configLocations;
		}

		return ArrayUtil.append(
			configLocations,
			serviceBuilderPropertiesConfiguration.getArray(
				PropsKeys.SPRING_CONFIGS));
	}

	@Override
	protected void initBeanDefinitionReader(
		XmlBeanDefinitionReader xmlBeanDefinitionReader) {

		xmlBeanDefinitionReader.setBeanClassLoader(getBeanClassLoader());
	}

	@Override
	protected void loadBeanDefinitions(
		XmlBeanDefinitionReader xmlBeanDefinitionReader) {

		String[] configLocations = getPortletConfigLocations();

		if (configLocations == null) {
			return;
		}

		for (String configLocation : configLocations) {
			boolean checkReadFile =
				PortalSecurityManagerThreadLocal.isCheckReadFile();

			try {
				PortalSecurityManagerThreadLocal.setCheckReadFile(false);

				xmlBeanDefinitionReader.loadBeanDefinitions(configLocation);
			}
			catch (Exception e) {
				Throwable cause = e.getCause();

				if (cause instanceof FileNotFoundException) {
					if (_log.isWarnEnabled()) {
						_log.warn(cause.getMessage());
					}
				}
				else {
					_log.error(e, e);
				}
			}
			finally {
				PortalSecurityManagerThreadLocal.setCheckReadFile(
					checkReadFile);
			}
		}
	}

	private static boolean _isUseRestrictedClassLoader() {
		return PACLPolicyManager.isActive();
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletApplicationContext.class);

	private static Map<String, Class<?>> _classes =
		new HashMap<String, Class<?>>();

	static {
		for (String className :
				PropsValues.
					PORTAL_SECURITY_MANAGER_PRELOAD_CLASSLOADER_CLASSES) {

			Class<?> clazz = null;

			try {
				clazz = Class.forName(className);
			}
			catch (ClassNotFoundException cnfe) {
				_log.error(cnfe, cnfe);
			}

			_classes.put(clazz.getName(), clazz);
		}
	}

}