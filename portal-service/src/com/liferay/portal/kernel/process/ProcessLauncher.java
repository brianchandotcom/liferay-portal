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

package com.liferay.portal.kernel.process;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedOutputStream;
import com.liferay.portal.kernel.process.log.ProcessOutputStream;
import com.liferay.portal.kernel.util.ClassLoaderObjectInputStream;
import com.liferay.portal.kernel.util.StringPool;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;

import java.net.URLClassLoader;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Shuyang Zhou
 */
public class ProcessLauncher {

	public static void main(String[] arguments)
		throws ClassNotFoundException, IOException {

		PrintStream oldOutPrintStream = System.out;

		ObjectOutputStream objectOutputStream = null;
		ProcessOutputStream outProcessOutputStream = null;

		synchronized (oldOutPrintStream) {
			oldOutPrintStream.flush();

			FileOutputStream fileOutputStream = new FileOutputStream(
				FileDescriptor.out);

			objectOutputStream = new ObjectOutputStream(
				new UnsyncBufferedOutputStream(fileOutputStream));

			outProcessOutputStream = new ProcessOutputStream(
				objectOutputStream, false);

			ProcessContext._setProcessOutputStream(outProcessOutputStream);

			PrintStream newOutPrintStream = new PrintStream(
				outProcessOutputStream, true);

			System.setOut(newOutPrintStream);
		}

		ProcessOutputStream errProcessOutputStream = new ProcessOutputStream(
			objectOutputStream, true);

		PrintStream errPrintStream = new PrintStream(
			errProcessOutputStream, true);

		System.setErr(errPrintStream);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			ObjectInputStream bootstrapObjectInputStream =
				new ObjectInputStream(System.in);

			String processCallableName =
				(String)bootstrapObjectInputStream.readObject();

			String logPrefixString =
				StringPool.OPEN_BRACKET.concat(processCallableName).concat(
					StringPool.CLOSE_BRACKET);

			byte[] logPrefix = logPrefixString.getBytes(StringPool.UTF8);

			outProcessOutputStream.setLogPrefix(logPrefix);
			errProcessOutputStream.setLogPrefix(logPrefix);

			String classPath = (String)bootstrapObjectInputStream.readObject();

			ClassLoader classLoader = new URLClassLoader(
				ClassPathUtil.getClassPathURLs(classPath));

			currentThread.setContextClassLoader(classLoader);

			ObjectInputStream objectInputStream =
				new ClassLoaderObjectInputStream(
					bootstrapObjectInputStream, classLoader);

			ProcessCallable<?> processCallable =
				(ProcessCallable<?>)objectInputStream.readObject();

			Serializable result = processCallable.call();

			System.out.flush();

			outProcessOutputStream.writeProcessCallable(
				new ReturnProcessCallable<Serializable>(result));

			outProcessOutputStream.flush();
		}
		catch (ProcessException pe) {
			errPrintStream.flush();

			errProcessOutputStream.writeProcessCallable(
				new ExceptionProcessCallable(pe));

			errProcessOutputStream.flush();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public static class ProcessContext {

		public static boolean attach(
			String message, long interval, ShutdownHook shutdownHook) {

			HeartbeatThread heartbeatThread = new HeartbeatThread(
				message, interval, shutdownHook);

			boolean value = _heartbeatThreadReference.compareAndSet(
				null, heartbeatThread);

			if (value) {
				heartbeatThread.start();
			}

			return value;
		}

		public static void detach() throws InterruptedException {
			HeartbeatThread heartbeatThread =
				_heartbeatThreadReference.getAndSet(null);

			if (heartbeatThread != null) {
				heartbeatThread.detach();
				heartbeatThread.join();
			}
		}

		public static ConcurrentMap<String, Object> getAttributes() {
			return _attributes;
		}

		public static ProcessOutputStream getProcessOutputStream() {
			return _processOutputStream;
		}

		public static boolean isAttached() {
			HeartbeatThread attachThread = _heartbeatThreadReference.get();

			if (attachThread != null) {
				return true;
			}
			else {
				return false;
			}
		}

		private static void _setProcessOutputStream(
			ProcessOutputStream processOutputStream) {

			_processOutputStream = processOutputStream;
		}

		private ProcessContext() {
		}

		private static ConcurrentMap<String, Object> _attributes =
			new ConcurrentHashMap<String, Object>();
		private static AtomicReference<HeartbeatThread>
			_heartbeatThreadReference = new AtomicReference<HeartbeatThread>();
		private static ProcessOutputStream _processOutputStream;

	}

	public interface ShutdownHook {

		public static final int BROKEN_PIPE_CODE = 1;

		public static final int INTERRUPTION_CODE = 2;

		public static final int UNKNOWN_CODE = 3;

		public boolean shutdown(int shutdownCode, Throwable shutdownThrowable);

	}

	private static class HeartbeatThread extends Thread {

		public HeartbeatThread(
			String message, long interval, ShutdownHook shutdownHook) {

			if (shutdownHook == null) {
				throw new IllegalArgumentException("Shutdown hook is null");
			}

			_interval = interval;
			_shutdownHook = shutdownHook;

			_pringBackProcessCallable = new PingbackProcessCallable(message);

			setDaemon(true);
			setName(HeartbeatThread.class.getSimpleName());
		}

		public void detach() {
			_detach = true;

			interrupt();
		}

		@Override
		public void run() {
			ProcessOutputStream processOutputStream =
				ProcessContext.getProcessOutputStream();

			int shutdownCode = 0;
			Throwable shutdownThrowable = null;

			while (!_detach) {
				try {
					sleep(_interval);

					processOutputStream.writeProcessCallable(
						_pringBackProcessCallable);
				}
				catch (InterruptedException ie) {
					if (_detach) {
						return;
					}
					else {
						shutdownThrowable = ie;

						shutdownCode = ShutdownHook.INTERRUPTION_CODE;
					}
				}
				catch (IOException ioe) {
					shutdownThrowable = ioe;

					shutdownCode = ShutdownHook.BROKEN_PIPE_CODE;
				}
				catch (Throwable throwable) {
					shutdownThrowable = throwable;

					shutdownCode = ShutdownHook.UNKNOWN_CODE;
				}

				if (shutdownCode != 0) {
					_detach = _shutdownHook.shutdown(
						shutdownCode, shutdownThrowable);
				}
			}
		}

		private volatile boolean _detach;
		private long _interval;
		private ProcessCallable<String> _pringBackProcessCallable;
		private ShutdownHook _shutdownHook;

	}

	private static class PingbackProcessCallable
		implements ProcessCallable<String> {

		public PingbackProcessCallable(String message) {
			_message = message;
		}

		@Override
		public String call() {
			return _message;
		}

		private static final long serialVersionUID = 1L;

		private String _message;

	}

}