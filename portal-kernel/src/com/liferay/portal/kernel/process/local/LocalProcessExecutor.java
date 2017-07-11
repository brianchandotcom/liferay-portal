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

package com.liferay.portal.kernel.process.local;

import com.liferay.portal.kernel.concurrent.AbortPolicy;
import com.liferay.portal.kernel.concurrent.AsyncBroker;
import com.liferay.portal.kernel.concurrent.FutureListener;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFutureConverter;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.concurrent.ThreadPoolHandlerAdapter;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessChannel;
import com.liferay.portal.kernel.process.ProcessConfig;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.process.TerminationProcessException;
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
import java.io.WriteAbortedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 */
public class LocalProcessExecutor implements ProcessExecutor {

	public Set<Process> destroy() {
		if (_threadPoolExecutor == null) {
			return Collections.emptySet();
		}

		Set<Process> processes = Collections.emptySet();

		synchronized (this) {
			if (_threadPoolExecutor != null) {
				processes = new HashSet<>();

				_threadPoolExecutor.shutdownNow();

				// At this point, the thread pool will no longer take in any
				// more subprocess reactors, so we know the list of managed
				// processes is in a safe state. The worst case is that the
				// destroyer thread and the thread pool thread concurrently
				// destroy the same process, but this is JDK's job to ensure
				// that processes are destroyed in a thread safe manner.

				Set<Entry<Process, NoticeableFuture<?>>> set =
					_managedProcesses.entrySet();

				Iterator<Entry<Process, NoticeableFuture<?>>> iterator =
					set.iterator();

				while (iterator.hasNext()) {
					Entry<Process, NoticeableFuture<?>> entry = iterator.next();

					processes.add(entry.getKey());

					NoticeableFuture<?> noticeableFuture = entry.getValue();

					noticeableFuture.cancel(true);

					iterator.remove();
				}

				// The current thread has a more comprehensive view of the list
				// of managed processes than any thread pool thread. After the
				// previous iteration, we are safe to clear the list of managed
				// processes.

				_managedProcesses.clear();

				_threadPoolExecutor = null;
			}
		}

		// Whip's instrument logic sees a label on a synchronized block exit and
		// asks for coverage, but it does not understand that this is actually
		// the same as exiting a method. To overcome this limitation, the code
		// logic has to explicitly leave the synchronized block before leaving
		// the method. This limitation will be removed in a future version of
		// Whip.

		return processes;
	}

	@Override
	public <T extends Serializable> ProcessChannel<T> execute(
			ProcessConfig processConfig, ProcessCallable<T> processCallable)
		throws ProcessException {

		try {
			List<String> arguments = processConfig.getArguments();

			List<String> commands = new ArrayList<>(arguments.size() + 4);

			commands.add(processConfig.getJavaExecutable());
			commands.add("-cp");
			commands.add(processConfig.getBootstrapClassPath());
			commands.addAll(arguments);
			commands.add(LocalProcessLauncher.class.getName());

			ProcessBuilder processBuilder = new ProcessBuilder(commands);

			Map<String, String> environment = processConfig.getEnvironment();

			if (environment != null) {
				Map<String, String> currentEnvironment =
					processBuilder.environment();

				currentEnvironment.clear();

				currentEnvironment.putAll(environment);
			}

			final Process process = processBuilder.start();

			ObjectOutputStream bootstrapObjectOutputStream =
				new ObjectOutputStream(process.getOutputStream());

			bootstrapObjectOutputStream.writeObject(processCallable.toString());
			bootstrapObjectOutputStream.writeObject(
				processConfig.getRuntimeClassPath());

			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				bootstrapObjectOutputStream);

			objectOutputStream.writeObject(processCallable);

			objectOutputStream.flush();

			ThreadPoolExecutor threadPoolExecutor = _getThreadPoolExecutor();

			AsyncBroker<Long, Serializable> asyncBroker = new AsyncBroker<>();

			SubprocessReactor subprocessReactor = new SubprocessReactor(
				process, processConfig.getReactClassLoader(), asyncBroker);

			try {
				NoticeableFuture<ProcessCallable<? extends Serializable>>
					processCallableNoticeableFuture = threadPoolExecutor.submit(
						subprocessReactor);

				processCallableNoticeableFuture.addFutureListener(
					new FutureListener
						<ProcessCallable<? extends Serializable>>() {

						@Override
						public void complete(
							Future<ProcessCallable<? extends Serializable>>
								future) {

							if (future.isCancelled()) {
								process.destroy();
							}
						}

					});

				// Consider the newly created process as a managed process only
				// after the subprocess reactor is taken by the thread pool

				_managedProcesses.put(process, processCallableNoticeableFuture);

				NoticeableFuture<T> noticeableFuture =
					new NoticeableFutureConverter
						<T, ProcessCallable<? extends Serializable>>(
							processCallableNoticeableFuture) {

						@Override
						protected T convert(
								ProcessCallable<? extends Serializable>
									processCallable)
							throws ProcessException {

							if (processCallable instanceof
									ReturnProcessCallable<?>) {

								return (T)processCallable.call();
							}

							ExceptionProcessCallable exceptionProcessCallable =
								(ExceptionProcessCallable)processCallable;

							throw exceptionProcessCallable.call();
						}

					};

				return new LocalProcessChannel<>(
					noticeableFuture, objectOutputStream, asyncBroker);
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

	private ThreadPoolExecutor _getThreadPoolExecutor() {
		if (_threadPoolExecutor != null) {
			return _threadPoolExecutor;
		}

		synchronized (this) {
			if (_threadPoolExecutor == null) {
				_threadPoolExecutor = new ThreadPoolExecutor(
					0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, true,
					Integer.MAX_VALUE, new AbortPolicy(),
					new NamedThreadFactory(
						LocalProcessExecutor.class.getName(),
						Thread.MIN_PRIORITY,
						PortalClassLoaderUtil.getClassLoader()),
					new ThreadPoolHandlerAdapter());
			}
		}

		return _threadPoolExecutor;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LocalProcessExecutor.class);

	private final Map<Process, NoticeableFuture<?>> _managedProcesses =
		new ConcurrentHashMap<>();
	private volatile ThreadPoolExecutor _threadPoolExecutor;

	private class SubprocessReactor
		implements Callable<ProcessCallable<? extends Serializable>> {

		public SubprocessReactor(
			Process process, ClassLoader reactClassLoader,
			AsyncBroker<Long, Serializable> asyncBroker) {

			_process = process;
			_reactClassLoader = reactClassLoader;
			_asyncBroker = asyncBroker;
		}

		@Override
		public ProcessCallable<? extends Serializable> call() throws Exception {
			ProcessCallable<?> resultProcessCallable = null;

			AsyncBrokerThreadLocal.setAsyncBroker(_asyncBroker);

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
							unsyncBufferedInputStream, _reactClassLoader);

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
					Object obj = null;

					try {
						obj = objectInputStream.readObject();
					}
					catch (WriteAbortedException wae) {
						if (_log.isWarnEnabled()) {
							_log.warn("Caught a write aborted exception", wae);
						}

						continue;
					}

					if (!(obj instanceof ProcessCallable)) {
						if (_log.isInfoEnabled()) {
							_log.info(
								"Received a nonprocess callable piping back " +
									obj);
						}

						continue;
					}

					ProcessCallable<?> processCallable =
						(ProcessCallable<?>)obj;

					if ((processCallable instanceof ExceptionProcessCallable) ||
						(processCallable instanceof ReturnProcessCallable<?>)) {

						resultProcessCallable = processCallable;

						continue;
					}

					try {
						Serializable returnValue = processCallable.call();

						if (_log.isDebugEnabled()) {
							_log.debug(
								"Invoked generic process callable " +
									processCallable + " with return value " +
										returnValue);
						}
					}
					catch (Throwable t) {
						_log.error(
							"Unable to invoke generic process callable", t);
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
			catch (Throwable t) {
				_log.error("Abort subprocess piping", t);

				throw t;
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

				AsyncBrokerThreadLocal.removeAsyncBroker();
			}
		}

		private final AsyncBroker<Long, Serializable> _asyncBroker;
		private final Process _process;
		private final ClassLoader _reactClassLoader;

	}

}