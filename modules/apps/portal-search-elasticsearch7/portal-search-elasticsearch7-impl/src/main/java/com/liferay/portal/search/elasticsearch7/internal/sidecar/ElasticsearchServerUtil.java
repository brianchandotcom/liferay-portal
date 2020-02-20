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

import com.liferay.petra.process.ProcessException;
import com.liferay.petra.reflect.ReflectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.elasticsearch.cli.ExitCodes;
import org.elasticsearch.node.Node;

/**
 * @author Tina Tian
 */
public class ElasticsearchServerUtil {

	public static void shutdown() {
		try {
			_stopMethod.invoke(null);
		}
		catch (Exception exception) {
			if (_logger.isWarnEnabled()) {
				_logger.warn("Unable to invoke stop method", exception);
			}

			System.exit(ExitCodes.CODE_ERROR);
		}

		_shutdownCountDownLatch.countDown();
	}

	public static Node start(String[] arguments) throws ProcessException {
		try {
			_mainMethod.invoke(null, new Object[] {arguments});

			System.setSecurityManager(null);

			_addShutdownHook();

			return (Node)_nodeField.get(_instanceField.get(null));
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new ProcessException(
				"Unable to start elasticsearch server",
				reflectiveOperationException);
		}
	}

	public static void waitForShutdown() throws ProcessException {
		try {
			_shutdownCountDownLatch.await();
		}
		catch (InterruptedException interruptedException) {
			throw new ProcessException(
				"Sidecar main thread is interrupted", interruptedException);
		}
	}

	private static void _addShutdownHook() throws ReflectiveOperationException {
		synchronized (_hooksField.getDeclaringClass()) {
			Map<Thread, Thread> hooks = (Map<Thread, Thread>)_hooksField.get(
				null);

			Set<Thread> threads = new HashSet<>(hooks.keySet());

			hooks.clear();

			Thread shutdownHook = new Thread(
				() -> {
					try {
						_shutdownCountDownLatch.await();
					}
					catch (InterruptedException interruptedException) {
					}

					for (Thread thread : threads) {
						thread.start();
					}

					for (Thread thread : threads) {
						while (true) {
							try {
								thread.join();

								break;
							}
							catch (InterruptedException interruptedException) {
							}
						}
					}
				},
				"Elasticsearch Server Shutdown Hook");

			hooks.put(shutdownHook, shutdownHook);
		}
	}

	private static final Logger _logger = LogManager.getLogger(
		ElasticsearchServerUtil.class);

	private static final Field _hooksField;
	private static final Field _instanceField;
	private static final Method _mainMethod;
	private static final Field _nodeField;
	private static final CountDownLatch _shutdownCountDownLatch =
		new CountDownLatch(1);
	private static final Method _stopMethod;

	static {
		try {
			ClassLoader classLoader =
				ElasticsearchServerUtil.class.getClassLoader();

			_hooksField = ReflectionUtil.getDeclaredField(
				classLoader.loadClass("java.lang.ApplicationShutdownHooks"),
				"hooks");

			_mainMethod = ReflectionUtil.getDeclaredMethod(
				classLoader.loadClass(
					"org.elasticsearch.bootstrap.Elasticsearch"),
				"main", String[].class);

			Class<?> bootstrapClass = classLoader.loadClass(
				"org.elasticsearch.bootstrap.Bootstrap");

			_instanceField = ReflectionUtil.getDeclaredField(
				bootstrapClass, "INSTANCE");

			_nodeField = ReflectionUtil.getDeclaredField(
				bootstrapClass, "node");

			_stopMethod = ReflectionUtil.getDeclaredMethod(
				bootstrapClass, "stop");
		}
		catch (Exception exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

}