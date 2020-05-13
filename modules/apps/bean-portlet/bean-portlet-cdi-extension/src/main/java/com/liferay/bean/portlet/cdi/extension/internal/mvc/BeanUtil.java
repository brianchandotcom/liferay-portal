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

package com.liferay.bean.portlet.cdi.extension.internal.mvc;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

import javax.mvc.binding.BindingResult;

/**
 * @author  Neil Griffin
 */
public class BeanUtil {

	public static <T> List<T> getBeanInstances(
		BeanManager beanManager, Class<T> clazz, Annotation... qualifiers) {

		List<T> beanInstances = new ArrayList<>();

		if (qualifiers == null) {
			qualifiers = new Annotation[0];
		}

		Set<Bean<?>> beans = beanManager.getBeans(clazz, qualifiers);

		for (Bean<?> bean : beans) {
			beanInstances.add(
				clazz.cast(
					beanManager.getReference(
						bean, clazz,
						beanManager.createCreationalContext(bean))));
		}

		return beanInstances;
	}

	public static MutableBindingResult getMutableBindingResult(
		BeanManager beanManager) {

		Bean<?> bean = beanManager.resolve(
			beanManager.getBeans(BindingResult.class));

		BindingResult bindingResult = (BindingResult)beanManager.getReference(
			bean, BindingResult.class,
			beanManager.createCreationalContext(bean));

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(
				bindingResult.getClass());

			PropertyDescriptor[] propertyDescriptors =
				beanInfo.getPropertyDescriptors();

			Object targetInstance = null;

			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String propertyDescriptorName = propertyDescriptor.getName();

				if (propertyDescriptorName.equals("targetInstance")) {
					Method method = propertyDescriptor.getReadMethod();

					targetInstance = method.invoke(bindingResult);
				}
			}

			if (targetInstance instanceof MutableBindingResult) {
				return (MutableBindingResult)targetInstance;
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(BeanUtil.class);

}