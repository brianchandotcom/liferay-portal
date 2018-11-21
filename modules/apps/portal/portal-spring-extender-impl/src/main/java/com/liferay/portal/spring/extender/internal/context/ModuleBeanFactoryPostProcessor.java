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

import com.liferay.portal.spring.extender.internal.bean.ServiceReferenceAnnotationBeanPostProcessor;

import org.osgi.framework.BundleContext;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author Miguel Pastor
 */
public class ModuleBeanFactoryPostProcessor
	implements BeanFactoryPostProcessor {

	public ModuleBeanFactoryPostProcessor(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public void postProcessBeanFactory(
		ConfigurableListableBeanFactory configurableListableBeanFactory) {

		BeanPostProcessor beanPostProcessor =
			new ServiceReferenceAnnotationBeanPostProcessor(_bundleContext);

		configurableListableBeanFactory.registerSingleton(
			ServiceReferenceAnnotationBeanPostProcessor.class.getName(),
			beanPostProcessor);

		configurableListableBeanFactory.addBeanPostProcessor(beanPostProcessor);
	}

	private final BundleContext _bundleContext;

}