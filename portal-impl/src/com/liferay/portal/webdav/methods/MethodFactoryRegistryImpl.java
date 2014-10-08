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

package com.liferay.portal.webdav.methods;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.webdav.methods.MethodFactory;
import com.liferay.portal.kernel.webdav.methods.MethodFactoryRegistry;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class MethodFactoryRegistryImpl implements MethodFactoryRegistry {

	@Override
	public MethodFactory getDefaultMethodFactory() {
		return _defaultMethodFactory;
	}

	@Override
	public List<MethodFactory> getMethodFactories() {
		return ListUtil.fromMapValues(_methodFactories);
	}

	@Override
	public MethodFactory getMethodFactory(String className) {
		return _methodFactories.get(className);
	}

	@Override
	public void registerMethodFactory(MethodFactory methodFactory) {
		Class<?> clazz = methodFactory.getClass();

		MethodFactory previousMethodFactory = _methodFactories.put(
			clazz.getName(), methodFactory);

		if (previousMethodFactory == _defaultMethodFactory) {
			_defaultMethodFactory = methodFactory;
		}

		if (_log.isWarnEnabled() && (previousMethodFactory != null)) {
			_log.warn(
				"Replacing " + previousMethodFactory + " for class name " +
					clazz.getName() + " with " + methodFactory);
		}
	}

	public void setDefaultMethodFactory(MethodFactory defaultMethodFactory) {
		_defaultMethodFactory = defaultMethodFactory;
	}

	@Override
	public void unregisterMethodFactory(MethodFactory methodFactory) {
		Class<?> clazz = methodFactory.getClass();

		_methodFactories.remove(clazz.getName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MethodFactoryRegistryImpl.class);

	private MethodFactory _defaultMethodFactory;
	private final Map<String, MethodFactory> _methodFactories =
		new ConcurrentHashMap<String, MethodFactory>();

}