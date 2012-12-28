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

package com.liferay.portal.spring.util;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.spring.util.FactoryBean;
import com.liferay.portal.kernel.spring.util.SpringFactory;
import com.liferay.portal.kernel.spring.util.SpringFactoryException;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class SpringFactoryImpl implements SpringFactory {

	public Object newBean(String className) throws SpringFactoryException {
		return newBean(className, null);
	}

	public Object newBean(String className, Map<String, Object> properties)
		throws SpringFactoryException {

		try {
			return doNewBean(className, properties);
		}
		catch (SpringFactoryException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SpringFactoryException(e);
		}
	}

	public void setBeanDefinitions(Map<String, String> beanDefinitions) {
		_beanDefinitions = new HashMap<String, Set<String>>();

		for (Map.Entry<String, String> entry : beanDefinitions.entrySet()) {
			String className = entry.getKey();

			Set<String> properties = SetUtil.fromArray(
				StringUtil.split(entry.getValue()));

			_beanDefinitions.put(className, properties);
		}
	}

	protected Object doNewBean(String className, Map<String, Object> properties)
		throws Exception {

		Set<String> allowedProperties = _beanDefinitions.get(className);

		if (allowedProperties == null) {
			throw new SpringFactoryException("Undefined class " + className);
		}

		boolean enabled = PortalSecurityManagerThreadLocal.isEnabled();

		try {
			PortalSecurityManagerThreadLocal.setEnabled(false);

			Object bean = InstanceFactory.newInstance(
				PACLClassLoaderUtil.getPortalClassLoader(), className);

			if (bean instanceof FactoryBean) {
				FactoryBean<Object> factoryBean = (FactoryBean<Object>)bean;

				bean = factoryBean.create();
			}

			if (properties == null) {
				return bean;
			}

			for (Map.Entry<String, Object> entry : properties.entrySet()) {
				String name = entry.getKey();

				if (!allowedProperties.contains(name)) {
					throw new SpringFactoryException(
						"Undefined property " + name + " for class " +
							className);
				}

				Object value = entry.getValue();

				BeanPropertiesUtil.setProperty(bean, name, value);
			}

			return bean;
		}
		finally {
			PortalSecurityManagerThreadLocal.setEnabled(enabled);
		}
	}

	private Map<String, Set<String>> _beanDefinitions;

}