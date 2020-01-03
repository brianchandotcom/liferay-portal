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

package com.liferay.portal.spring.compat;

import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.trash.kernel.service.TrashEntryLocalService;
import com.liferay.trash.kernel.service.TrashEntryService;
import com.liferay.trash.kernel.service.TrashVersionLocalService;
import com.liferay.trash.kernel.service.persistence.TrashEntryPersistence;
import com.liferay.trash.kernel.service.persistence.TrashVersionPersistence;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * @author Dante Wang
 */
public class CompatBeanDefinitionRegistryPostProcessor
	implements BeanDefinitionRegistryPostProcessor {

	@Override
	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry beanDefinitionRegistry)
		throws BeansException {

		for (Class<?> clazz : _COMPAT_CLASSES) {
			BeanDefinitionBuilder beanDefinitionBuilder =
				BeanDefinitionBuilder.genericBeanDefinition(ProxyFactory.class);

			beanDefinitionBuilder.setFactoryMethod("newDummyInstance");
			beanDefinitionBuilder.addConstructorArgValue(clazz);

			beanDefinitionRegistry.registerBeanDefinition(
				clazz.getName(), beanDefinitionBuilder.getBeanDefinition());
		}
	}

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory configurableListableBeanFactory)
		throws BeansException {
	}

	private static final Class<?>[] _COMPAT_CLASSES = {
		TrashEntryLocalService.class, TrashEntryService.class,
		TrashEntryPersistence.class, TrashVersionLocalService.class,
		TrashVersionPersistence.class
	};

}