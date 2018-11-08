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

package com.liferay.portal.spring.aop;

import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;

/**
 * @author Shuyang Zhou
 */
public class ChainableMethodAdviceAssembler implements BeanFactoryAware {

	public void afterPropertiesSet() {
		ListableBeanFactory listableBeanFactory =
			(ListableBeanFactory)_beanFactory;

		Map<String, ChainableMethodAdviceInjector>
			chainableMethodAdviceInjectors = listableBeanFactory.getBeansOfType(
				ChainableMethodAdviceInjector.class);

		for (ChainableMethodAdviceInjector chainableMethodAdviceInjector :
				chainableMethodAdviceInjectors.values()) {

			chainableMethodAdviceInjector.inject();
		}
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		_beanFactory = beanFactory;
	}

	private BeanFactory _beanFactory;

}