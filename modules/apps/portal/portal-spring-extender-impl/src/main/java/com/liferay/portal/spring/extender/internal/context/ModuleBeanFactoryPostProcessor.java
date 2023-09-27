/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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