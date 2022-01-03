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

package com.liferay.portal.osgi.web.wab.extender.internal;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.spring.util.FilterClassLoader;

import java.io.FileNotFoundException;

import java.net.URL;

import java.util.List;

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

	public PortletApplicationContext() {
		ClassLoader beanClassLoader =
			AggregateClassLoader.getAggregateClassLoader(
				new ClassLoader[] {
					PortletClassLoaderUtil.getClassLoader(),
					PortalClassLoaderUtil.getClassLoader()
				});

		setClassLoader(new FilterClassLoader(beanClassLoader));
	}

	@Override
	protected String[] getDefaultConfigLocations() {
		return new String[0];
	}

	@Override
	protected void initBeanDefinitionReader(
		XmlBeanDefinitionReader xmlBeanDefinitionReader) {

		xmlBeanDefinitionReader.setBeanClassLoader(getClassLoader());
	}

	@Override
	protected void loadBeanDefinitions(
		XmlBeanDefinitionReader xmlBeanDefinitionReader) {

		for (String configLocation : _getPortletConfigLocations()) {
			try {
				xmlBeanDefinitionReader.loadBeanDefinitions(configLocation);
			}
			catch (Exception exception) {
				Throwable throwable = exception.getCause();

				if (throwable instanceof FileNotFoundException) {
					if (_log.isDebugEnabled()) {
						_log.debug(throwable.getMessage());
					}
				}
				else {
					_log.error(throwable, throwable);
				}
			}
		}
	}

	private String[] _getPortletConfigLocations() {
		String[] configLocations = getConfigLocations();

		ClassLoader classLoader = PortletClassLoaderUtil.getClassLoader();

		URL url = classLoader.getResource("service.properties");

		if (url == null) {
			return configLocations;
		}

		Configuration serviceBuilderPropertiesConfiguration =
			ConfigurationFactoryUtil.getConfiguration(classLoader, "service");

		// Remove old spring XMLs to ensure they are not read

		List<String> serviceBuilderPropertiesConfigLocations =
			ListUtil.fromArray(
				serviceBuilderPropertiesConfiguration.getArray(
					PropsKeys.SPRING_CONFIGS));

		serviceBuilderPropertiesConfigLocations.remove(
			"WEB-INF/classes/META-INF/base-spring.xml");
		serviceBuilderPropertiesConfigLocations.remove(
			"WEB-INF/classes/META-INF/cluster-spring.xml");
		serviceBuilderPropertiesConfigLocations.remove(
			"WEB-INF/classes/META-INF/hibernate-spring.xml");
		serviceBuilderPropertiesConfigLocations.remove(
			"WEB-INF/classes/META-INF/infrastructure-spring.xml");

		return ArrayUtil.append(
			configLocations,
			serviceBuilderPropertiesConfigLocations.toArray(new String[0]));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletApplicationContext.class);

}