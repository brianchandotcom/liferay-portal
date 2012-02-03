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

package com.liferay.portal.kernel.process;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.log.ProcessOutputStream;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
public class ProcessExecutor {

	public static <T extends Serializable> T execute(
			ProcessCallable<T> processCallable, String classPath)
		throws ProcessException {

		try {
			ProcessBuilder processBuilder = new ProcessBuilder(
				"java", "-cp", classPath, ProcessExecutor.class.getName());

			Process process = processBuilder.start();

			_writeObject(process.getOutputStream(), processCallable);

			ExecutorService executorService = _getExecutorService();

			SubprocessReactor subprocessReactor = new SubprocessReactor(
				process.getInputStream());

			Future<ProcessCallable<?>> futureResponseProcessCallable =
				executorService.submit(subprocessReactor);

			int exitCode = process.waitFor();

			if (exitCode != 0) {
				throw new ProcessException(
					"Subprocess terminated with exit code " + exitCode);
			}

			ProcessCallable<?> responseProcessCallable =
				futureResponseProcessCallable.get();

			if (responseProcessCallable instanceof ReturnProcessCallable<?>) {
				return (T)responseProcessCallable.call();
			}

			if (responseProcessCallable instanceof ExceptionProcessCallable) {
				ExceptionProcessCallable exceptionProcessCallable =
					(ExceptionProcessCallable)responseProcessCallable;

				throw exceptionProcessCallable.call();
			}

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Subprocess reactor exited without a valid return " +
						"because the subprocess terminated with an exception");
			}

			return null;
		}
		catch (ProcessException pe) {
			throw pe;
		}
		catch (Exception e) {
			throw new ProcessException(e);
		}
	}

	public static void main(String[] arguments)
		throws ClassNotFoundException, IOException {

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			System.out);

		ProcessOutputStream outProcessOutputStream = new ProcessOutputStream(
			objectOutputStream, false);

		PrintStream outPrintStream = new PrintStream(
			outProcessOutputStream, true);

		System.setOut(outPrintStream);

		ProcessOutputStream errProcessOutputStream = new ProcessOutputStream(
			objectOutputStream, true);

		PrintStream errPrintStream = new PrintStream(
			errProcessOutputStream, true);

		System.setErr(errPrintStream);

		try {
			ProcessCallable<?> processCallable =
				(ProcessCallable<?>)_readObject(System.in, false);

			Serializable result = processCallable.call();

			outPrintStream.flush();

			outProcessOutputStream.writeProcessCallable(
				new ReturnProcessCallable<Serializable>(result));

			outProcessOutputStream.close();
		}
		catch (ProcessException pe) {
			errPrintStream.flush();

			errProcessOutputStream.writeProcessCallable(
				new ExceptionProcessCallable(pe));

			errProcessOutputStream.close();
		}
	}

	public void destroy() {
		if (_executorService == null) {
			return;
		}

		synchronized (ProcessExecutor.class) {
			if (_executorService != null) {
				_executorService.shutdownNow();

				_executorService = null;
			}
		}
	}

	private static ExecutorService _getExecutorService() {
		if (_executorService != null) {
			return _executorService;
		}

		synchronized (ProcessExecutor.class) {
			if (_executorService == null) {
				_executorService = Executors.newCachedThreadPool(
					new NamedThreadFactory(
						ProcessExecutor.class.getName(), Thread.MIN_PRIORITY,
						PortalClassLoaderUtil.getClassLoader()));
			}
		}

		return _executorService;
	}

	private static Object _readObject(InputStream inputStream, boolean close)
		throws ClassNotFoundException, IOException {

		ObjectInputStream objectInputStream = new ObjectInputStream(
			inputStream);

		try {
			return objectInputStream.readObject();
		}
		finally {
			if (close) {
				objectInputStream.close();
			}
		}
	}

	private static void _writeObject(OutputStream outputStream, Object object)
		throws IOException {

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			outputStream);

		try {
			objectOutputStream.writeObject(object);
		}
		finally {
			objectOutputStream.close();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ProcessExecutor.class);

	private static volatile ExecutorService _executorService;

	private static class SubprocessReactor
		implements Callable<ProcessCallable<? extends Serializable>> {

		public SubprocessReactor(InputStream inputStream) {
			_unsyncBufferedInputStream = new UnsyncBufferedInputStream(
				inputStream);
		}

		public ProcessCallable<? extends Serializable> call() throws Exception {
			try {
				ObjectInputStream objectInputStream = null;

				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
					new UnsyncByteArrayOutputStream();

				while (true) {
					try {

						// Be ready for a bad header

						_unsyncBufferedInputStream.mark(4);

						objectInputStream =
							new PortalClassLoaderObjectInputStream(
								_unsyncBufferedInputStream);

						// Found the beginning of the object input stream. Flush
						// out corrupted log if necessary.

						if (unsyncByteArrayOutputStream.size() > 0) {
							if (_log.isWarnEnabled()) {
								_log.warn(
									"Found corrupted leading log: " +
										unsyncByteArrayOutputStream.toString());
							}
						}

						unsyncByteArrayOutputStream = null;

						break;
					}
					catch (StreamCorruptedException sce) {

						// Collecting bad header as log information

						_unsyncBufferedInputStream.reset();

						unsyncByteArrayOutputStream.write(
							_unsyncBufferedInputStream.read());
					}
				}

				while (true) {
					ProcessCallable<?> processCallable =
						(ProcessCallable<?>)objectInputStream.readObject();

					if (processCallable instanceof ExceptionProcessCallable) {
						return processCallable;
					}

					if (processCallable instanceof ReturnProcessCallable<?>) {
						return processCallable;
					}

					Serializable result = processCallable.call();

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Invoked generic process callable " +
								processCallable + " with return value " +
									result);
					}
				}
			}
			catch (EOFException eofe) {
			}

			return null;
		}

		private final UnsyncBufferedInputStream _unsyncBufferedInputStream;

	}

}