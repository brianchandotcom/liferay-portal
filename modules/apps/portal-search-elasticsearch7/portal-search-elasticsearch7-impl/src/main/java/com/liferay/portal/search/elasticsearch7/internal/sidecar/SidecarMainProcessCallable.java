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

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.process.local.LocalProcessLauncher;
import com.liferay.petra.reflect.ReflectionUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Map;

/**
 * @author Tina Tian
 */
public class SidecarMainProcessCallable
	implements ProcessCallable<Serializable> {

	public SidecarMainProcessCallable(
		long heartbeatInterval, Map<String, byte[]> modifiedClasses) {

		_heartbeatInterval = heartbeatInterval;
		_modifiedClasses = modifiedClasses;
	}

	@Override
	public Serializable call() throws ProcessException {
		LocalProcessLauncher.ProcessContext.attach(
			"SidecarMainProcessCallable", _heartbeatInterval,
			(shutdownCode, shutdownThrowable) -> {
				ElasticsearchServerUtil.shutdown();

				return true;
			});

		_loadModifiedClasses();

		ElasticsearchServerUtil.waitForShutdown();

		return null;
	}

	private void _loadModifiedClasses() throws ProcessException {
		ClassLoader classLoader =
			SidecarMainProcessCallable.class.getClassLoader();

		try {
			Method defineClassMethod = ReflectionUtil.getDeclaredMethod(
				ClassLoader.class, "defineClass", String.class, byte[].class,
				int.class, int.class);

			for (Map.Entry<String, byte[]> entry :
					_modifiedClasses.entrySet()) {

				byte[] modifiedClassBytes = entry.getValue();

				defineClassMethod.invoke(
					classLoader, entry.getKey(), modifiedClassBytes, 0,
					modifiedClassBytes.length);
			}
		}
		catch (Exception exception) {
			throw new ProcessException(
				"Unable to load modified classes", exception);
		}
	}

	private static final long serialVersionUID = 1L;

	private final long _heartbeatInterval;
	private final Map<String, byte[]> _modifiedClasses;

}