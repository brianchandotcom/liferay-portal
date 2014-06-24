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

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassLoaderObjectInputStream;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StreamUtil;

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Shuyang Zhou
 */
public class ProcessExecutor {

	public static <T extends Serializable> Future<T> execute(
			String bootstrapClassPath, String classPath, List<String> arguments,
			ProcessCallable<? extends Serializable> processCallable)
		throws ProcessException {

		return execute(
			"java", bootstrapClassPath, classPath, arguments, processCallable);
	}

	public static <T extends Serializable> Future<T> execute(
			String bootstrapClassPath, String classPath,
			ProcessCallable<? extends Serializable> processCallable)
		throws ProcessException {

		return execute(
			"java", bootstrapClassPath, classPath,
			Collections.<String>emptyList(), processCallable);
	}

	public static <T extends Serializable> Future<T> execute(
			String java, String bootstrapClassPath, String classPath,
			List<String> arguments,
			ProcessCallable<? extends Serializable> processCallable)
		throws ProcessException {

		try {
			List<String> commands = new ArrayList<String>(arguments.size() + 4);

			commands.add(java);
			commands.add("-cp");
			commands.add(bootstrapClassPath);
			commands.addAll(arguments);
			commands.add(ProcessLauncher.class.getName());

			ProcessBuilder processBuilder = new ProcessBuilder(commands);

			Process process = processBuilder.start();

			ObjectOutputStream bootstrapObjectOutputStream =
				new ObjectOutputStream(process.getOutputStream());

			bootstrapObjectOutputStream.writeObject(processCallable.toString());
			bootstrapObjectOutputStream.writeObject(classPath);

			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				bootstrapObjectOutputStream);

			try {
				objectOutputStream.writeObject(processCallable);
			}
			finally {
				objectOutputStream.close();
			}

			ExecutorService executorService = _getExecutorService();

			SubprocessReactor subprocessReactor = new SubprocessReactor(
				process);

			try {
				Future<ProcessCallable<? extends Serializable>>
					futureResponseProcessCallable = executorService.submit(
						subprocessReactor);

				// Consider the newly created process as a managed process only
				// after the subprocess reactor is taken by the thread pool

				_managedProcesses.add(process);

				return new ProcessExecutionFutureResult<T>(
					futureResponseProcessCallable, process);
			}
			catch (RejectedExecutionException ree) {
				process.destroy();

				throw new ProcessException(
					"Cancelled execution because of a concurrent destroy", ree);
			}
		}
		catch (IOException ioe) {
			throw new ProcessException(ioe);
		}
	}

	public void destroy() {
		if (_executorService == null) {
			return;
		}

		synchronized (ProcessExecutor.class) {
			if (_executorService != null) {
				_executorService.shutdownNow();

				// At this point, the thread pool will no longer take in any
				// more subprocess reactors, so we know the list of managed
				// processes is in a safe state. The worst case is that the
				// destroyer thread and the thread pool thread concurrently
				// destroy the same process, but this is JDK's job to ensure
				// that processes are destroyed in a thread safe manner.

				Iterator<Process> iterator = _managedProcesses.iterator();

				while (iterator.hasNext()) {
					Process process = iterator.next();

					process.destroy();

					iterator.remove();
				}

				// The current thread has a more comprehensive view of the list
				// of managed processes than any thread pool thread. After the
				// previous iteration, we are safe to clear the list of managed
				// processes.

				_managedProcesses.clear();

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

	private static Log _log = LogFactoryUtil.getLog(ProcessExecutor.class);

	private static volatile ExecutorService _executorService;
	private static Set<Process> _managedProcesses =
		new ConcurrentHashSet<Process>();

	private static class ProcessExecutionFutureResult<T> implements Future<T> {

		public ProcessExecutionFutureResult(
			Future<ProcessCallable<? extends Serializable>> future,
			Process process) {

			_future = future;
			_process = process;
		}

		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			if (_future.isCancelled() || _future.isDone()) {
				return false;
			}

			_future.cancel(true);
			_process.destroy();

			return true;
		}

		@Override
		public boolean isCancelled() {
			return _future.isCancelled();
		}

		@Override
		public boolean isDone() {
			return _future.isDone();
		}

		@Override
		public T get() throws ExecutionException, InterruptedException {
			ProcessCallable<?> processCallable = _future.get();

			return get(processCallable);
		}

		@Override
		public T get(long timeout, TimeUnit timeUnit)
			throws ExecutionException, InterruptedException, TimeoutException {

			ProcessCallable<?> processCallable = _future.get(timeout, timeUnit);

			return get(processCallable);
		}

		private T get(ProcessCallable<?> processCallable)
			throws ExecutionException {

			try {
				if (processCallable instanceof ReturnProcessCallable<?>) {
					return (T)processCallable.call();
				}

				ExceptionProcessCallable exceptionProcessCallable =
					(ExceptionProcessCallable)processCallable;

				throw exceptionProcessCallable.call();
			}
			catch (ProcessException pe) {
				throw new ExecutionException(pe);
			}
		}

		private final Future<ProcessCallable<?>> _future;
		private final Process _process;

	}

	private static class SubprocessReactor
		implements Callable<ProcessCallable<? extends Serializable>> {

		public SubprocessReactor(Process process) {
			_process = process;
		}

		@Override
		public ProcessCallable<? extends Serializable> call() throws Exception {
			ProcessCallable<?> resultProcessCallable = null;

			UnsyncBufferedInputStream unsyncBufferedInputStream =
				new UnsyncBufferedInputStream(_process.getInputStream());

			try {
				ObjectInputStream objectInputStream = null;

				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
					new UnsyncByteArrayOutputStream();

				while (true) {
					try {

						// Be ready for a bad header

						unsyncBufferedInputStream.mark(4);

						objectInputStream = new ClassLoaderObjectInputStream(
							unsyncBufferedInputStream,
							PortalClassLoaderUtil.getClassLoader());

						// Found the beginning of the object input stream. Flush
						// out corrupted log if necessary.

						if (unsyncByteArrayOutputStream.size() > 0) {
							if (_log.isWarnEnabled()) {
								_log.warn(
									"Found corrupt leading log " +
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

					if ((processCallable instanceof ExceptionProcessCallable) ||
						(processCallable instanceof ReturnProcessCallable<?>)) {

						resultProcessCallable = processCallable;

						continue;
					}

					Serializable returnValue = processCallable.call();

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Invoked generic process callable " +
								processCallable + " with return value " +
									returnValue);
					}
				}
			}
			catch (StreamCorruptedException sce) {
				File file = File.createTempFile(
					"corrupted-stream-dump-" + System.currentTimeMillis(),
					".log");

				_log.error(
					"Dumping content of corrupted object input stream to " +
						file.getAbsolutePath(),
					sce);

				FileOutputStream fileOutputStream = new FileOutputStream(file);

				StreamUtil.transfer(
					unsyncBufferedInputStream, fileOutputStream);

				throw new ProcessException(
					"Corrupted object input stream", sce);
			}
			catch (EOFException eofe) {
				throw new ProcessException(
					"Subprocess piping back ended prematurely", eofe);
			}
			finally {
				try {
					int exitCode = _process.waitFor();

					if (exitCode != 0) {
						throw new TerminationProcessException(exitCode);
					}
				}
				catch (InterruptedException ie) {
					_process.destroy();

					throw new ProcessException(
						"Forcibly killed subprocess on interruption", ie);
				}

				_managedProcesses.remove(_process);

				if (resultProcessCallable != null) {

					// Override previous process exception if there was one

					return resultProcessCallable;
				}
			}
		}

		private final Process _process;

	}

}