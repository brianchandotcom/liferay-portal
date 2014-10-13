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

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;

/**
 * <p>
 * A class loader proxy able to serialize simple POJOs between two class
 * loaders. It only works for simple POJOs following the Java Beans semantics.
 * The local and remote classes do not have to match or even be derived from
 * each other as long as their properties match. Any bean properties that the
 * source bean exposes but the target bean does not will silently be ignored.
 * </p>
 *
 * @author Micha Kiener
 * @author Brian Wing Shun Chan
 */
public class SimplePojoClp<T> {

	public SimplePojoClp(
			Class<? extends T> localImplementationClass,
			ClassLoader remoteClassLoader)
		throws ClassNotFoundException {

		this(
			localImplementationClass, remoteClassLoader,
			localImplementationClass.getName());
	}

	public SimplePojoClp(
			Class<? extends T> localImplementationClass,
			ClassLoader remoteClassLoader, String remoteImplementationClassName)
		throws ClassNotFoundException {

		_localImplementationClass = localImplementationClass;
		_remoteClassLoader = remoteClassLoader;
		_remoteImplementationClass = _remoteClassLoader.loadClass(
			remoteImplementationClassName);
	}

	public T createLocalObject(Object remoteInstance)
		throws IllegalAccessException, InstantiationException {

		T localInstance = _localImplementationClass.newInstance();

		BeanPropertiesUtil.copyProperties(
			remoteInstance, localInstance, _localImplementationClass);

		return localInstance;
	}

	public Object createRemoteObject(T localInstance)
		throws IllegalAccessException, InstantiationException {

		Object remoteInstance = _remoteImplementationClass.newInstance();

		BeanPropertiesUtil.copyProperties(
			localInstance, remoteInstance, _remoteImplementationClass);

		return remoteInstance;
	}

	private final Class<? extends T> _localImplementationClass;
	private final ClassLoader _remoteClassLoader;
	private final Class<?> _remoteImplementationClass;

}