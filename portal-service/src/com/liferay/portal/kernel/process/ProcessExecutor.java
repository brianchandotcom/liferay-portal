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
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.log.ProcessOutputStream;
import com.liferay.portal.kernel.util.ClassLoaderObjectInputStream;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.EOFException;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

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

		return execute(
			"java", classPath, Collections.<String>emptyList(),
			processCallable);
	}

	public static <T extends Serializable> Future<T> execute(
			String java, String classPath, List<String> arguments,
			ProcessCallable<? extends Serializable> processCallable)
		throws ProcessException {

		try {
			List<String> commands = new ArrayList<String>(arguments.size() + 4);

			commands.add(java);
			commands.add("-cp");
			commands.add(classPath);
			commands.addAll(arguments);
			commands.add(ProcessExecutor.class.getName());

			ProcessBuilder processBuilder = new ProcessBuilder(commands);

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

		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
				System.in);

			ProcessCallable<?> processCallable =
				(ProcessCallable<?>)objectInputStream.readObject();

			String logPrefixString =
				StringPool.OPEN_BRACKET.concat(
					processCallable.toString()).concat(
						StringPool.CLOSE_BRACKET);

			byte[] logPrefix = logPrefixString.getBytes(StringPool.UTF8);

			outProcessOutputStream.setLogPrefix(logPrefix);
			errProcessOutputStream.setLogPrefix(logPrefix);

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
		private final long _interval;
		private final ProcessCallable<String> _pringBackProcessCallable;
		private final ShutdownHook _shutdownHook;

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

		private final String _message;

	}

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