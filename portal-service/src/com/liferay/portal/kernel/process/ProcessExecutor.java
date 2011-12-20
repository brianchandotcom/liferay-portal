/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

	public void destroy() {
		if (_executorService != null) {
			synchronized (ProcessExecutor.class) {
				if (_executorService != null) {
					_executorService.shutdownNow();
					_executorService = null;
				}
			}
		}
	}

	public static <T extends Serializable> T execute(
			ProcessCallable<T> processCallable, String classPath)
		throws ProcessException {

		try {
			ProcessBuilder processBuilder = new ProcessBuilder(
				"java", "-cp", classPath, ProcessExecutor.class.getName());

			Process process = processBuilder.start();

			_writeObject(process.getOutputStream(), processCallable);

			ExecutorService executorService = _getExecutorService();

			SubProcessReactor subProcessReactor = new SubProcessReactor(
				process.getInputStream());

			Future<ProcessCallable<?>> futureResponseProcessCallable =
				executorService.submit(subProcessReactor);

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
					"SubProcessReactor quited without a valid return " +
					"ProcessCallable, this means sub-process terminated " +
					"exceptionally.");
			}

			return null;
		}
		catch (ProcessException pe) {
			// Don't wrap ProcessException
			throw pe;
		}
		catch (Exception e) {
			throw new ProcessException(e);
		}
	}

	public static void main(String[] arguments)
		throws ClassNotFoundException, IOException {

		// Use default System.out as communication channel
		PrintStream oldOut = System.out;

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(oldOut);

		// Install std-out logging
		ProcessOutputStream processOut = new ProcessOutputStream(
			objectOutputStream, false);
		PrintStream newOut = new PrintStream(processOut, true);

		System.setOut(newOut);

		// Install std-err logging
		ProcessOutputStream processErr = new ProcessOutputStream(
			objectOutputStream, true);
		PrintStream newErr = new PrintStream(processErr, true);

		System.setErr(newErr);

		try {
			ProcessCallable<?> processCallable =
				(ProcessCallable<?>)_readObject(System.in, false);

			Serializable result = processCallable.call();

			newOut.flush();

			processOut.writeProcessCallable(
				new ReturnProcessCallable<Serializable>(result));

			processOut.close();
		}
		catch (ProcessException pe) {
			newErr.flush();

			processErr.writeProcessCallable(new ExceptionProcessCallable(pe));

			processErr.close();
		}
	}

	private static ExecutorService _getExecutorService() {
		if (_executorService == null) {
			synchronized (ProcessExecutor.class) {
				if (_executorService == null) {
					_executorService = Executors.newCachedThreadPool(
						new NamedThreadFactory(
							"ProcessExecutor Reactor Thread",
							Thread.MIN_PRIORITY,
							PortalClassLoaderUtil.getClassLoader()));
				}
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

	private static class SubProcessReactor
		implements Callable<ProcessCallable<? extends Serializable>> {

		public SubProcessReactor(InputStream inputStream) {
			_ubis = new UnsyncBufferedInputStream(inputStream);
		}

		public ProcessCallable<? extends Serializable> call() throws Exception {
			try {
				ObjectInputStream objectInputStream = null;

				// Corrupted log collector
				UnsyncByteArrayOutputStream ubaos =
					new UnsyncByteArrayOutputStream();

				while (true) {
					try {
						// Be ready for bad header
						_ubis.mark(4);

						objectInputStream =
							new PortalClassLoaderObjectInputStream(_ubis);

						// Found the beginning of ObjectInputStream, flush out
						// corrupted log if there is any.
						if (ubaos.size() > 0) {
							if (_log.isWarnEnabled()) {
								_log.warn("Found corrupted leading log : " +
									ubaos.toString());
							}
						}

						ubaos = null;

						break;
					}
					catch (StreamCorruptedException sce) {
						// Collecting bad header as log data
						_ubis.reset();

						ubaos.write(_ubis.read());
					}
				}

				while (true) {
					ProcessCallable processCallable =
						(ProcessCallable)objectInputStream.readObject();

					if (processCallable instanceof ReturnProcessCallable<?>) {
						return processCallable;
					}

					if (processCallable instanceof ExceptionProcessCallable) {
						return processCallable;
					}

					Serializable result = processCallable.call();

					if (_log.isDebugEnabled()) {
						_log.debug("Invoked generic ProcessCallable : " +
							processCallable + ", with return value : " +
							result);
					}
				}
			}
			catch (EOFException eofe) {
			}

			return null;
		}

		private final UnsyncBufferedInputStream _ubis;

	}

}