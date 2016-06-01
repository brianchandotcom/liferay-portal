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

package com.liferay.portal.configuration.metatype.util;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;

import java.util.Map;

/**
 * @author Jorge Ferrer
 */
public class ParameterMapUtil {

	public static <T> T setParameterMap(
			Class<T> clazz, T configurationBean,
			Map<String, String[]> parameterMap)
		throws ConfigurationException {

		ParameterMapInvocationHandler<T> parameterMapInvocationHandler =
			new ParameterMapInvocationHandler<>(
				clazz, configurationBean, parameterMap);

		return parameterMapInvocationHandler.createProxy();
	}

	public static <T> T setParameterMap(
			Class<T> clazz, T configurationBean,
			Map<String, String[]> parameterMap, String parameterPrefix,
			String parameterSuffix)
		throws ConfigurationException {

		ParameterMapInvocationHandler<T> parameterMapInvocationHandler =
			new ParameterMapInvocationHandler<>(
				clazz, configurationBean, parameterMap, parameterPrefix,
				parameterSuffix);

		return parameterMapInvocationHandler.createProxy();
	}

}