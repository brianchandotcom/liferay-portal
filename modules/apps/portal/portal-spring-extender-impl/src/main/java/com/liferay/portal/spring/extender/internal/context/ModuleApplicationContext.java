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

package com.liferay.portal.spring.extender.internal.context;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

/**
 * @author Miguel Pastor
 */
public class ModuleApplicationContext extends ClassPathXmlApplicationContext {

	public ModuleApplicationContext(
		Bundle bundle, ClassLoader classLoader, String[] configLocations) {

		super(configLocations, false, null);

		this.bundle = bundle;

		setClassLoader(classLoader);
	}

	public BundleContext getBundleContext() {
		return bundle.getBundleContext();
	}

	@Override
	public Resource[] getResources(String locationPattern) {
		Enumeration<URL> enumeration = bundle.findEntries(
			locationPattern, "*.xml", true);

		List<Resource> resources = new ArrayList<>();

		while (enumeration.hasMoreElements()) {
			resources.add(new UrlResource(enumeration.nextElement()));
		}

		return resources.toArray(new Resource[0]);
	}

	@Override
	protected DefaultListableBeanFactory createBeanFactory() {
		return new DefaultListableBeanFactory(getInternalParentBeanFactory()) {

			@Override
			protected Object getEarlyBeanReference(
				String beanName, RootBeanDefinition rootBeanDefinition,
				Object bean) {

				if ((bean == null) || rootBeanDefinition.isSynthetic()) {
					return bean;
				}

				Object exposedObject = bean;

				for (BeanPostProcessor beanPostProcessor :
						getBeanPostProcessors()) {

					if (!(beanPostProcessor instanceof
							SmartInstantiationAwareBeanPostProcessor)) {

						continue;
					}

					SmartInstantiationAwareBeanPostProcessor
						smartInstantiationAwareBeanPostProcessor =
							(SmartInstantiationAwareBeanPostProcessor)
								beanPostProcessor;

					exposedObject =
						smartInstantiationAwareBeanPostProcessor.
							getEarlyBeanReference(exposedObject, beanName);

					if (exposedObject == null) {
						return exposedObject;
					}
				}

				return exposedObject;
			}

			@Override
			protected boolean hasInstantiationAwareBeanPostProcessors() {
				return false;
			}

		};
	}

	protected final Bundle bundle;

}