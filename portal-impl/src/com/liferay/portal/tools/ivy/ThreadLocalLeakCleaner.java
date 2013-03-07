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

package com.liferay.portal.tools.ivy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Stack;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalLeakCleaner {

	public static void cleanUpIvyThreadLocalLeak() throws Exception {
		Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
		threadLocalsField.setAccessible(true);

		Thread currentThread = Thread.currentThread();

		Object threadLocalMap = threadLocalsField.get(currentThread);

		if (threadLocalMap == null) {
			return;
		}

		Class<?> threadLocalMapClass = Class.forName(
			"java.lang.ThreadLocal$ThreadLocalMap");

		Method expungeStaleEntriesMethod =
			threadLocalMapClass.getDeclaredMethod("expungeStaleEntries");
		expungeStaleEntriesMethod.setAccessible(true);

		expungeStaleEntriesMethod.invoke(threadLocalMap);

		Field tableField = threadLocalMapClass.getDeclaredField("table");
		tableField.setAccessible(true);

		Object[] table = (Object[])tableField.get(threadLocalMap);

		for (Object entry : table) {
			if (entry != null) {
				Class<?> entryClass = entry.getClass();

				Field valueField = entryClass.getDeclaredField("value");
				valueField.setAccessible(true);

				Object value = valueField.get(entry);

				if (value instanceof Stack) {
					Stack<?> stack = (Stack<?>)value;

					if (!stack.isEmpty()) {
						Object topElement = stack.peek();

						Class<?> topElementClass = topElement.getClass();

						if (topElementClass.getName().equals(
								"org.apache.ivy.core.IvyContext")) {

							stack.clear();
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		cleanUpIvyThreadLocalLeak();
	}

}