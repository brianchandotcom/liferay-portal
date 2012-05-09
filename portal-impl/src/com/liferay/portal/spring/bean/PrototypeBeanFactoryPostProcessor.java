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

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author Shuyang Zhou
 */
public class PrototypeBeanFactoryPostProcessor
	implements BeanFactoryPostProcessor {

	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory configurableListableBeanFactory)
		throws BeansException {

		Map<String, PrototypeBean> pluginBeans =
			configurableListableBeanFactory.getBeansOfType(PrototypeBean.class);

		PrototypeBeanUtil.register(pluginBeans);
	}

}