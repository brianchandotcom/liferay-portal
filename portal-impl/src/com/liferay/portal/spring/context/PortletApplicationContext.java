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

package com.liferay.portal.spring.context;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.security.lang.DoPrivilegedFactory;
import com.liferay.portal.spring.util.FilterClassLoader;

import java.io.FileNotFoundException;

import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
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
		return _pacl.getBeanClassLoader();
	}

	public PortletApplicationContext() {
		setClassLoader(getBeanClassLoader());
	}

	public interface PACL {

		public ClassLoader getBeanClassLoader();

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
			serviceBuilderPropertiesConfigLocations.toArray(
				new String[serviceBuilderPropertiesConfigLocations.size()]));
	}

	@Override
	protected void initBeanDefinitionReader(
		XmlBeanDefinitionReader xmlBeanDefinitionReader) {

		xmlBeanDefinitionReader.setBeanClassLoader(getBeanClassLoader());
	}

	protected void injectExplicitBean(
		Class<?> clazz, BeanDefinitionRegistry beanDefinitionRegistry) {

		beanDefinitionRegistry.registerBeanDefinition(
			clazz.getName(), new RootBeanDefinition(clazz));
	}

	protected void injectExplicitBeans(
		BeanDefinitionRegistry beanDefinitionRegistry) {

		injectExplicitBean(DoPrivilegedFactory.class, beanDefinitionRegistry);
	}

	@Override
	protected void loadBeanDefinitions(
		XmlBeanDefinitionReader xmlBeanDefinitionReader) {

		String[] configLocations = getPortletConfigLocations();

		if (configLocations == null) {
			return;
		}

		BeanDefinitionRegistry beanDefinitionRegistry =
			xmlBeanDefinitionReader.getBeanFactory();

		injectExplicitBeans(beanDefinitionRegistry);

		for (String configLocation : configLocations) {
			try {
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
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletApplicationContext.class);

	private static final PACL _pacl = new NoPACL();

	private static class NoPACL implements PACL {

		@Override
		public ClassLoader getBeanClassLoader() {
			ClassLoader beanClassLoader =
				AggregateClassLoader.getAggregateClassLoader(
					new ClassLoader[] {
						PortletClassLoaderUtil.getClassLoader(),
						ClassLoaderUtil.getPortalClassLoader()
					});

			return new FilterClassLoader(beanClassLoader);
		}

	}

}