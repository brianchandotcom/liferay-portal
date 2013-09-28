/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.spring.util.SpringFactoryUtil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.TypedStringValue;

/**
 * @author Shuyang Zhou
 */
public class ChainableMethodAdviceInjectorCollector {

	public static final String BEAN_NAME =
		ChainableMethodAdviceInjectorCollector.class.getName();

	public static void collect(
		ConfigurableListableBeanFactory configurableListableBeanFactory) {

		ChainableMethodAdviceInjectorCollector
			chainableMethodAdviceInjectorCollector =
				new ChainableMethodAdviceInjectorCollector();

		List<String> beanNames =
			chainableMethodAdviceInjectorCollector._beanNames;

		String[] names =
			configurableListableBeanFactory.getBeanDefinitionNames();

		for (String name : names) {
			if (!name.contains(SpringFactoryUtil.class.getName())) {
				continue;
			}

			BeanDefinition beanDefinition =
				configurableListableBeanFactory.getBeanDefinition(name);

			ConstructorArgumentValues constructorArgumentValues =
				beanDefinition.getConstructorArgumentValues();

			List<ConstructorArgumentValues.ValueHolder> valueHolders =
				constructorArgumentValues.getGenericArgumentValues();

			if (!valueHolders.isEmpty()) {
				ConstructorArgumentValues.ValueHolder valueHolder =
					valueHolders.get(0);

				TypedStringValue typedStringValue =
					(TypedStringValue)valueHolder.getValue();

				String className = typedStringValue.getValue();

				if (className.contains(
						ChainableMethodAdviceInjector.class.getSimpleName())) {

					beanNames.add(name);
				}
			}
		}

		if (!beanNames.isEmpty()) {
			configurableListableBeanFactory.registerSingleton(
				BEAN_NAME, chainableMethodAdviceInjectorCollector);
		}
	}

	public List<String> getBeanNames() {
		return _beanNames;
	}

	private ChainableMethodAdviceInjectorCollector() {
	}

	private List<String> _beanNames = new ArrayList<String>();

}