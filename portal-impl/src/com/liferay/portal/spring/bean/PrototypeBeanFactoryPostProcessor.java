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

package com.liferay.portal.spring.bean;

import com.liferay.portal.kernel.util.PrototypeBean;
import com.liferay.portal.kernel.util.PrototypeBeanUtil;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;

/**
 * @author Shuyang Zhou
 */
public class PrototypeBeanFactoryPostProcessor
	implements BeanFactoryPostProcessor {

	public PrototypeBeanFactoryPostProcessor() {
		this(false);
	}

	public PrototypeBeanFactoryPostProcessor(boolean portletContext) {
		_portletContext = portletContext;
	}

	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory configurableListableBeanFactory)
		throws BeansException {

		String[] beanNames =
			configurableListableBeanFactory.getBeanNamesForType(
				PrototypeBean.class);

		AbstractBeanFactory abstractBeanFactory =
			(AbstractBeanFactory)configurableListableBeanFactory;

		for (String beanName : beanNames) {
			Object bean = configurableListableBeanFactory.getBean(beanName);

			if (_portletContext) {
				if (bean instanceof BeanPostProcessor) {
					abstractBeanFactory.addBeanPostProcessor(
						(BeanPostProcessor)bean);
				}
			}
			else {
				PrototypeBeanUtil.register(beanName, (PrototypeBean)bean);
			}
		}
	}

	private boolean _portletContext;

}