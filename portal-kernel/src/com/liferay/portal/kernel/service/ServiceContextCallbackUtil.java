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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author     Akos Thurzo
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class ServiceContextCallbackUtil {

	public static void registerPopCallback(String name, Callable<?> callable) {
		_popCallbackMap.put(name, callable);
	}

	public static void registerPushCallback(String name, Callable<?> callable) {
		_pushCallbackMap.put(name, callable);
	}

	public static void runPopCallbacks() {
		_runCallbacks(_popCallbackMap.values());
	}

	public static void runPushCallbacks() {
		_runCallbacks(_pushCallbackMap.values());
	}

	public static Callable<?> unRegisterPopCallback(String name) {
		return _popCallbackMap.remove(name);
	}

	public static Callable<?> unRegisterPushCallback(String name) {
		return _pushCallbackMap.remove(name);
	}

	private static void _runCallbacks(Collection<Callable<?>> callbackList) {
		for (Callable<?> callable : callbackList) {
			try {
				callable.call();
			}
			catch (Exception e) {
				_log.error("Unable to execute service context callback", e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceContextCallbackUtil.class);

	private static final Map<String, Callable<?>> _popCallbackMap =
		new ConcurrentSkipListMap<>();
	private static final Map<String, Callable<?>> _pushCallbackMap =
		new ConcurrentSkipListMap<>();

}