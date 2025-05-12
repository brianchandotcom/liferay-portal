/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.elasticsearch.cli.ExitCodes;
import org.elasticsearch.common.hash.MessageDigests;
import org.elasticsearch.common.io.stream.OutputStreamStreamOutput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.settings.KeyStoreWrapper;

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

	public static Object start(String[] arguments) throws ProcessException {
		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();
			StreamOutput streamOutput = new OutputStreamStreamOutput(
				unsyncByteArrayOutputStream)) {

			_writeServerArgs(streamOutput, arguments);

			InputStream originalSystemInInputStream = System.in;

			try (UnsyncByteArrayInputStream unsyncByteArrayInputStream =
					new UnsyncByteArrayInputStream(
						unsyncByteArrayOutputStream.toByteArray())) {

				System.setIn(unsyncByteArrayInputStream);

				_mainMethod.invoke(null, (Object)null);
			}
			finally {
				System.setIn(originalSystemInInputStream);
			}

			System.setSecurityManager(null);

			_addShutdownHook();

			return _nodeField.get(_instanceField.get(null));
		}
		catch (Exception exception) {
			throw new ProcessException(
				"Unable to start elasticsearch server", exception);
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
						if (_logger.isDebugEnabled()) {
							_logger.debug(interruptedException);
						}
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
								if (_logger.isDebugEnabled()) {
									_logger.debug(interruptedException);
								}
							}
						}
					}
				},
				"Elasticsearch Server Shutdown Hook");

			hooks.put(shutdownHook, shutdownHook);
		}
	}

	private static void _writeServerArgs(
			StreamOutput streamOutput, String[] arguments)
		throws IOException {

		streamOutput.writeBoolean(false);
		streamOutput.writeBoolean(false);
		streamOutput.writeOptionalString(null);
		streamOutput.writeString(KeyStoreWrapper.class.getName());

		try (KeyStoreWrapper keyStoreWrapper = KeyStoreWrapper.create()) {
			streamOutput.writeInt(keyStoreWrapper.getFormatVersion());
			streamOutput.writeBoolean(keyStoreWrapper.hasPassword());
			streamOutput.writeBoolean(false);
			streamOutput.writeVInt(1);
			streamOutput.writeString(KeyStoreWrapper.SEED_SETTING.getKey());

			ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(
				ElasticsearchServerUtil.class.getSimpleName());

			byte[] bytes = byteBuffer.array();

			MessageDigest messageDigest = MessageDigests.sha256();

			streamOutput.writeByteArray(bytes);
			streamOutput.writeByteArray(messageDigest.digest(bytes));
			streamOutput.writeBoolean(false);
		}

		streamOutput.writeVInt(arguments.length);

		for (String argument : arguments) {
			List<String> keyValues = StringUtil.split(argument, CharPool.EQUAL);

			if (keyValues.size() != 2) {
				continue;
			}

			String key = keyValues.get(0);

			streamOutput.writeString(key);

			String value = keyValues.get(1);

			List<String> values = StringUtil.split(value);

			if (values.size() == 1) {
				streamOutput.writeGenericValue(value);
			}
			else {
				streamOutput.writeGenericValue(values);
			}
		}

		streamOutput.writeString(System.getProperty("es.path.conf"));
		streamOutput.writeString("logs");

		streamOutput.flush();
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

			Class<?> elasticsearchClass = classLoader.loadClass(
				"org.elasticsearch.bootstrap.Elasticsearch");

			_mainMethod = ReflectionUtil.getDeclaredMethod(
				elasticsearchClass, "main", String[].class);

			_instanceField = ReflectionUtil.getDeclaredField(
				elasticsearchClass, "INSTANCE");

			_nodeField = ReflectionUtil.getDeclaredField(
				elasticsearchClass, "node");

			_stopMethod = ReflectionUtil.getDeclaredMethod(
				elasticsearchClass, "shutdown");
		}
		catch (Exception exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

}