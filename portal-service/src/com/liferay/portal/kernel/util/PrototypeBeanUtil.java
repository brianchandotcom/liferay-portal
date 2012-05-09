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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class PrototypeBeanUtil {

	public static PrototypeBean lookup(String name) {
		return lookup(name, new Object[0]);
	}

	public static PrototypeBean lookup(String name, Object... args) {
		PrototypeBean prototypeBean = _prototypeBeanMap.get(name);

		if (prototypeBean == null) {
			throw new IllegalArgumentException(
				"No such prototype bean with name : " + name);
		}

		prototypeBean = prototypeBean.create(args);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Lookup prototype bean name : " + name + ", value : " +
				prototypeBean);
		}

		return prototypeBean;
	}

	public static void register(Map<String, PrototypeBean> prototypeBeanMap) {
		if (_log.isInfoEnabled()) {
			_log.info("Register prototype bean map : " + prototypeBeanMap);
		}

		_prototypeBeanMap.putAll(prototypeBeanMap);
	}

	public static void register(String name, PrototypeBean prototypeBean) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Register single prototype bean name : " + name +
				", value : " + prototypeBean);
		}

		_prototypeBeanMap.put(name, prototypeBean);
	}

	public void destroy() {
		_prototypeBeanMap.clear();
	}

	public void setPluginPrototypeBeanClassNames(
			List<String> prototypeBeanClassNames)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		for (String className : prototypeBeanClassNames) {
			Class<?> clazz = classLoader.loadClass(className);

			if (PrototypeBean.class.isAssignableFrom(clazz)) {
				PrototypeBean prototypeBean =
					(PrototypeBean)clazz.newInstance();

				_prototypeBeanMap.put(className, prototypeBean);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Register single prototype bean name : " + className +
						", value : " + prototypeBean);
				}
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					"Class " + className + " does not implement " +
					PrototypeBean.class.getName());
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PrototypeBeanUtil.class);

	private static Map<String, PrototypeBean> _prototypeBeanMap =
		new HashMap<String, PrototypeBean>();

}