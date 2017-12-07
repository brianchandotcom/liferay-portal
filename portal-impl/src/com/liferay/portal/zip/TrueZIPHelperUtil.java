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

package com.liferay.portal.zip;

import com.liferay.petra.reflect.ReflectionUtil;

import java.lang.reflect.Field;

/**
 * @author Shuyang Zhou
 */
public class TrueZIPHelperUtil {

	public static void initialize() {
		try {
			Class.forName(
				"de.schlichtherle.io.ArchiveControllers", true,
				TrueZIPHelperUtil.class.getClassLoader());

			Class<?> clazz = Class.forName(
				"de.schlichtherle.io.ArchiveControllers$ShutdownHook");

			Field field = ReflectionUtil.getDeclaredField(clazz, "SINGLETON");

			Thread thread = (Thread)field.get(null);

			Runtime runtime = Runtime.getRuntime();

			if (runtime.removeShutdownHook(thread)) {
				_thread = thread;
			}
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}
	}

	public static void shutdown() {
		Thread thread = _thread;

		if (thread != null) {
			thread.start();

			try {
				thread.join();
			}
			catch (InterruptedException ie) {
				ReflectionUtil.throwException(ie);
			}
		}
	}

	private static volatile Thread _thread;

}