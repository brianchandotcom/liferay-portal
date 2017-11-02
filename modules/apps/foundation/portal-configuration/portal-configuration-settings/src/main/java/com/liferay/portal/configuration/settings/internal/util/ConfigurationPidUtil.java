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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Iván Zaera
 */
public class ConfigurationPidUtil {

	public static String getConfigurationPid(Class<?> configurationBeanClass) {
		Object ocdId = null;

		for (Annotation annotation : configurationBeanClass.getAnnotations()) {
			Class<? extends Annotation> type = annotation.annotationType();

			if (type.getName().equals(Meta.OCD.class.getName())) {
				for (Method method : type.getDeclaredMethods()) {
					if (method.getName().equals("id")) {
						try {
							ocdId = method.invoke(annotation);
						}
						catch (IllegalAccessException iae) {
							iae.printStackTrace();
						}
						catch (IllegalArgumentException iae) {
							iae.printStackTrace();
						}
						catch (InvocationTargetException ite) {
							ite.printStackTrace();
						}
					}
				}
			}
		}

		return (String)ocdId;
	}

}