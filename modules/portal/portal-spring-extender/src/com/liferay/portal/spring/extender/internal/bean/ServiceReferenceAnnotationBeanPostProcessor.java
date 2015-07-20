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

package com.liferay.portal.spring.extender.internal.bean;

import com.liferay.portal.spring.extender.internal.context.ModuleApplicationContext;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.lang.reflect.Field;

import org.osgi.framework.BundleContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

/**
 * @author Miguel Pastor
 */
public class ServiceReferenceAnnotationBeanPostProcessor
	implements ApplicationContextAware, BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
		throws BeansException {

		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
		throws BeansException {

		autoInject(bean, bean.getClass());

		return bean;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
		throws BeansException {

		ModuleApplicationContext moduleApplicationContext =
			(ModuleApplicationContext)applicationContext;

		_bundleContext = moduleApplicationContext.getBundleContext();
	}

	protected void autoInject(Object targetBean, Class<?> beanClass) {
		if ((beanClass == null) || beanClass.isInterface()) {
			return;
		}

		Field[] fields = beanClass.getDeclaredFields();

		for (Field field : fields) {
			ServiceReference serviceReference = field.getAnnotation(
				ServiceReference.class);

			if (serviceReference == null) {
				continue;
			}

			Class<?> typeClass = serviceReference.type();

			org.osgi.framework.ServiceReference<?> osgiServiceReference =
				_bundleContext.getServiceReference(typeClass.getName());

			ReflectionUtils.makeAccessible(field);

			try {
				field.set(
					targetBean,
					_bundleContext.getService(osgiServiceReference));
			}
			catch (Throwable t) {
				throw new BeanCreationException(
					beanClass.getName(),
					"Unable to inject bean reference fields", t);
			}
		}

		autoInject(targetBean, beanClass.getSuperclass());
	}

	private BundleContext _bundleContext;

}