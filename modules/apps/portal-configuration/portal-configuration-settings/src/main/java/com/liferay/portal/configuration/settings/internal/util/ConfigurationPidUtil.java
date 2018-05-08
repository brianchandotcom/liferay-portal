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

package com.liferay.portal.configuration.settings.internal.util;

import aQute.bnd.annotation.metatype.Meta;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Iván Zaera
 */
public class ConfigurationPidUtil {

	public static String getConfigurationPid(Class<?> configurationBeanClass) {
		for (Annotation annotation : configurationBeanClass.getAnnotations()) {
			Class<? extends Annotation> clazz = annotation.annotationType();

			String name = clazz.getName();

			if (name.equals(Meta.OCD.class.getName())) {
				try {
					Method method = clazz.getMethod("id");

					method.setAccessible(true);

					return (String)method.invoke(annotation);
				}
				catch (ReflectiveOperationException roe) {
					throw new IllegalArgumentException(
						"Unable to obtain configuration PID", roe);
				}
			}
		}

		throw new IllegalArgumentException(
			"Invalid configuration bean class: " +
				configurationBeanClass.getName());
	}

}