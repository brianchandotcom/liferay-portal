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

package com.liferay.batch.engine.internal;

import com.liferay.batch.engine.ClassRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(service = ClassRegistry.class)
public class ClassRegistryImpl implements ClassRegistry {

	@Override
	public Class<?> getClass(String className) {
		return _classMap.get(className);
	}

	@Override
	public void register(Class<?>... classes) {
		for (Class<?> clazz : classes) {
			Class<?> oldClass = _classMap.put(clazz.getName(), clazz);

			if ((oldClass != null) && _log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						oldClass, " from ", oldClass.getClassLoader(),
						" is overrided by ", clazz, "from ",
						clazz.getClassLoader()));
			}
		}
	}

	@Override
	public void unregister(Class<?>... classes) {
		for (Class<?> clazz : classes) {
			_classMap.remove(clazz.getName(), clazz);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClassRegistryImpl.class);

	private final Map<String, Class<?>> _classMap = new ConcurrentHashMap<>();

}