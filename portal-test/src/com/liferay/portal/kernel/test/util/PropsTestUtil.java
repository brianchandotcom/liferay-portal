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

package com.liferay.portal.kernel.test.util;

import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.Method;

import java.util.Collections;
import java.util.Map;

/**
 * @author Tina Tian
 */
public class PropsTestUtil {

	public static Props setProps(Map<String, Object> propertie) {
		Props props = (Props)ProxyUtil.newProxyInstance(
			Props.class.getClassLoader(), new Class<?>[] {Props.class},
			(Object proxy, Method method, Object[] args) -> {
				if (args.length > 0) {
					return propertie.get(args[0]);
				}

				return null;
			});

		PropsUtil.setProps(props);

		return props;
	}

	public static Props setProps(String key, Object value) {
		return setProps(Collections.singletonMap(key, value));
	}

}