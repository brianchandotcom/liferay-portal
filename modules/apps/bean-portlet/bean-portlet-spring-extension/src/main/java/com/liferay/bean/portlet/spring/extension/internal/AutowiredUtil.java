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

package com.liferay.bean.portlet.spring.extension.internal;

import java.util.Set;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.util.Assert;

/**
 * @author Neil Griffin
 */
public class AutowiredUtil {

	public static void registerBeans(
		String beanName, Set<String> beanNames,
		ConfigurableBeanFactory configurableBeanFactory) {

		if (beanName != null) {
			for (String curBeanName : beanNames) {
				if ((configurableBeanFactory != null) &&
					configurableBeanFactory.containsBean(curBeanName)) {

					configurableBeanFactory.registerDependentBean(
						curBeanName, beanName);
				}
			}
		}
	}

	public static Object resolveDependency(
		AutowireCapableBeanFactory autowireCapableBeanFactory, String beanName,
		Object dependency) {

		if (dependency instanceof DependencyDescriptor) {
			DependencyDescriptor descriptor = (DependencyDescriptor)dependency;
			Assert.state(
				autowireCapableBeanFactory != null, "Bean factory unavailable");

			return autowireCapableBeanFactory.resolveDependency(
				descriptor, beanName, null, null);
		}

		return dependency;
	}

}