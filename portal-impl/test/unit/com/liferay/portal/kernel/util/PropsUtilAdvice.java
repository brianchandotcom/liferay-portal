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

package com.liferay.portal.kernel.util;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author Shuyang Zhou
 */
@Aspect
public class PropsUtilAdvice {

	public static Map<String, String> getPropsMap() {
		return _propsMap;
	}

	public static void setProps(String name, String value) {
		_propsMap.put(name, value);
	}

	public static void setPropsMap(Map<String, String> propsMap) {
		_propsMap = propsMap;
	}

	@Around(
		"execution(public static String com.liferay.portal.kernel.util." +
			"PropsUtil.get(String)) && args(key)"
	)
	public String get(String key) {
		return _propsMap.get(key);
	}

	private static Map<String, String> _propsMap = new HashMap<>();

}