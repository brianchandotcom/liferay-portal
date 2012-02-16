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
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.log.ProcessOutputStream;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Shuyang Zhou
 */
public class ProcessExecutor {

	public static <T extends Serializable> Future<T> execute(
			String classPath, List<String> arguments,
			ProcessCallable<? extends Serializable> processCallable)
		throws ProcessException {

		return execute("java", classPath, arguments, processCallable);
	}

	public static <T extends Serializable> Future<T> execute(
			String classPath,
			ProcessCallable<? extends Serializable> processCallable)
		throws ProcessException {

		return execute("java", classPath, Collections.<String>emptyList(),
			processCallable);
	}

	public static <T extends Serializable> Future<T> execute(
			String java, String classPath, List<String> arguments,
			ProcessCallable<? extends Serializable> processCallable)
		throws ProcessException {

		try {
			List<String> commandList = new ArrayList<String>(
				arguments.size() + 4);

			commandList.add(java);
			commandList.add("-cp");
			commandList.add(classPath);
			commandList.addAll(arguments);
			commandList.add(ProcessExecutor.class.getName());

			ProcessBuilder processBuilder = new ProcessBuilder(
				commandList.toArray(new String[commandList.size()]));

			Process process = processBuilder.start();

			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				process.getOutputStream());

			try {
				objectOutputStream.writeObject(processCallable);
			}
			finally {
				objectOutputStream.close();
			}

			ExecutorService executorService = _getExecutorService();

			SubprocessReactor subprocessReactor = new SubprocessReactor(
				process);

			Future<ProcessCallable<? extends Serializable>>
				futureResponseProcessCallable =
					executorService.submit(subprocessReactor);

			return new ProcessExecutionFutureResult<T>(
				futureResponseProcessCallable, process);
		}
		catch (IOException ioe) {
			throw new ProcessException(ioe);
		}
	}

	public static void main(String[] arguments)
		throws ClassNotFoundException, IOException {

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
			new UnsyncBufferedOutputStream(System.out));

		ProcessOutputStream outProcessOutputStream = new ProcessOutputStream(
			objectOutputStream, false);

		ProcessContext.setProcessOutputStream(outProcessOutputStream);

		PrintStream outPrintStream = new PrintStream(
			outProcessOutputStream, true);

		System.setOut(outPrintStream);

		ProcessOutputStream errProcessOutputStream = new ProcessOutputStream(
			objectOutputStream, true);

		PrintStream errPrintStream = new PrintStream(
			errProcessOutputStream, true);

		System.setErr(errPrintStream);

		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
				System.in);

			ProcessCallable<?> processCallable =
				(ProcessCallable<?>)objectInputStream.readObject();

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

	/**
	 * ProcessContext needs to be initialied at the beginning of main method,
	 * before invoking the ProcessCallable. This will ensure all context
	 * variables are setted before main thread forking other threads.
	 * Therefore variables setting happens-before any reading, memory visibility
	 * ensured. QED
	 */
	public static class ProcessContext {

		private ProcessContext() {
		}

		public static ProcessOutputStream getProcessOutputStream() {
			return _processOutputStream;
		}

		private static void setProcessOutputStream(
			ProcessOutputStream processOutputStream) {
			_processOutputStream = processOutputStream;
		}

		private static ProcessOutputStream _processOutputStream;
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

	private static Log _log = LogFactoryUtil.getLog(ProcessExecutor.class);

	private static volatile ExecutorService _executorService;

	private static class ProcessExecutionFutureResult<T> implements Future<T> {

		public ProcessExecutionFutureResult(
			Future<ProcessCallable<? extends Serializable>>
				futureResponseProcessCallable,
			Process process) {
			_futureResponseProcessCallable = futureResponseProcessCallable;
			_process = process;
		}

		public boolean cancel(boolean mayInterruptIfRunning) {
			if (_futureResponseProcessCallable.isCancelled() ||
				_futureResponseProcessCallable.isDone()) {
				return false;
			}

			_futureResponseProcessCallable.cancel(true);
			_process.destroy();

			return true;
		}

		public boolean isCancelled() {
			return _futureResponseProcessCallable.isCancelled();
		}

		public boolean isDone() {
			return _futureResponseProcessCallable.isDone();
		}

		public T get() throws InterruptedException, ExecutionException {
			ProcessCallable<?> responseProcessCallable =
				_futureResponseProcessCallable.get();

			return get(responseProcessCallable);
		}

		public T get(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {

			ProcessCallable<?> responseProcessCallable =
				_futureResponseProcessCallable.get(timeout, unit);

			return get(responseProcessCallable);
		}

		private T get(ProcessCallable<?> responseProcessCallable)
			throws ExecutionException {

			try {
				if (responseProcessCallable instanceof
					ReturnProcessCallable<?>) {
					return (T)responseProcessCallable.call();
				}
				else {
					ExceptionProcessCallable exceptionProcessCallable =
						(ExceptionProcessCallable)responseProcessCallable;

					throw exceptionProcessCallable.call();
				}
			}
			catch (ProcessException pe) {
				throw new ExecutionException(pe);
			}
		}

		private final Future<ProcessCallable<?>> _futureResponseProcessCallable;
		private final Process _process;

	}

	private static class SubprocessReactor
		implements Callable<ProcessCallable<? extends Serializable>> {

		public SubprocessReactor(Process process) {
			_process = process;
		}

		public ProcessCallable<? extends Serializable> call() throws Exception {
			try {
				ObjectInputStream objectInputStream = null;

				UnsyncBufferedInputStream unsyncBufferedInputStream =
					new UnsyncBufferedInputStream(_process.getInputStream());

				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
					new UnsyncByteArrayOutputStream();

				while (true) {
					try {

						// Be ready for a bad header

						unsyncBufferedInputStream.mark(4);

						objectInputStream =
							new PortalClassLoaderObjectInputStream(
								unsyncBufferedInputStream);

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

						unsyncBufferedInputStream.reset();

						unsyncByteArrayOutputStream.write(
							unsyncBufferedInputStream.read());
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
				throw new ProcessException(
					"Subprocess piping back ended prematurely.", eofe);
			}
			finally {
				try {
					int exitCode = _process.waitFor();

					if (exitCode != 0) {
						throw new ProcessException(
							"Subprocess terminated with exit code " + exitCode);
					}
				}
				catch (InterruptedException ie) {
					_process.destroy();

					throw new ProcessException(
						"Force killed Subprocess on interruption", ie);
				}
			}
		}

		private final Process _process;

	}

}