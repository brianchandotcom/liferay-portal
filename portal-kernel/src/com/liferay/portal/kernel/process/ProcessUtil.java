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

import com.liferay.portal.kernel.concurrent.AbortPolicy;
import com.liferay.portal.kernel.concurrent.BaseFutureListener;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.FutureListener;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.concurrent.ThreadPoolHandlerAdapter;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @author Shuyang Zhou
 */
public class ProcessUtil {

	public static final CollectorOutputProcessor COLLECTOR_OUTPUT_PROCESSOR =
		new CollectorOutputProcessor();

	public static final ConsumerOutputProcessor CONSUMER_OUTPUT_PROCESSOR =
		new ConsumerOutputProcessor();

	public static final EchoOutputProcessor ECHO_OUTPUT_PROCESSOR =
		new EchoOutputProcessor();

	public static final LoggingOutputProcessor LOGGING_OUTPUT_PROCESSOR =
		new LoggingOutputProcessor();

	public static <O, E> NoticeableFuture<ObjectValuePair<O, E>> execute(
			OutputProcessor<O, E> outputProcessor, List<String> arguments)
		throws ProcessException {

		if (outputProcessor == null) {
			throw new NullPointerException("Output processor is null");
		}

		if (arguments == null) {
			throw new NullPointerException("Arguments is null");
		}

		ProcessBuilder processBuilder = new ProcessBuilder(arguments);

		try {
			Process process = processBuilder.start();

			ThreadPoolExecutor threadPoolExecutor = _getThreadPoolExecutor();

			try {
				NoticeableFuture<O> stdOutNoticeableFuture =
					threadPoolExecutor.submit(
						new ProcessStdOutCallable<O>(outputProcessor, process));

				NoticeableFuture<E> stdErrNoticeableFuture =
					threadPoolExecutor.submit(
						new ProcessStdErrCallable<E>(outputProcessor, process));

				return _wrapNoticeableFuture(
					stdOutNoticeableFuture, stdErrNoticeableFuture, process);
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

	public static <O, E> NoticeableFuture<ObjectValuePair<O, E>> execute(
			OutputProcessor<O, E> outputProcessor, String... arguments)
		throws ProcessException {

		return execute(outputProcessor, Arrays.asList(arguments));
	}

	public void destroy() {
		if (_threadPoolExecutor == null) {
			return;
		}

		synchronized (ProcessUtil.class) {
			if (_threadPoolExecutor != null) {
				_threadPoolExecutor.shutdownNow();

				_threadPoolExecutor = null;
			}
		}
	}

	private static ThreadPoolExecutor _getThreadPoolExecutor() {
		if (_threadPoolExecutor != null) {
			return _threadPoolExecutor;
		}

		synchronized (ProcessUtil.class) {
			if (_threadPoolExecutor == null) {
				_threadPoolExecutor = new ThreadPoolExecutor(
					0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, true,
					Integer.MAX_VALUE, new AbortPolicy(),
					new NamedThreadFactory(
						ProcessUtil.class.getName(), Thread.MIN_PRIORITY,
						PortalClassLoaderUtil.getClassLoader()),
					new ThreadPoolHandlerAdapter());
			}
		}

		return _threadPoolExecutor;
	}

	private static <O, E> NoticeableFuture<ObjectValuePair<O, E>>
		_wrapNoticeableFuture(
			final NoticeableFuture<O> stdOutNoticeableFuture,
			final NoticeableFuture<E> stdErrNoticeableFuture,
			final Process process) {

		final DefaultNoticeableFuture<ObjectValuePair<O, E>>
			defaultNoticeableFuture = new DefaultNoticeableFuture<>();

		defaultNoticeableFuture.addFutureListener(
			new FutureListener<ObjectValuePair<O, E>>() {

				@Override
				public void complete(Future<ObjectValuePair<O, E>> future) {
					if (!future.isCancelled()) {
						return;
					}

					stdOutNoticeableFuture.cancel(true);

					stdErrNoticeableFuture.cancel(true);

					process.destroy();
				}

			});

		final AtomicMarkableReference<O> stdOutReference =
			new AtomicMarkableReference<>(null, false);

		final AtomicMarkableReference<E> stdErrReference =
			new AtomicMarkableReference<>(null, false);

		stdOutNoticeableFuture.addFutureListener(
			new BaseFutureListener<O>() {

				@Override
				public void completeWithCancel(Future<O> future) {
					defaultNoticeableFuture.cancel(true);
				}

				@Override
				public void completeWithException(
					Future<O> future, Throwable throwable) {

					defaultNoticeableFuture.setException(throwable);
				}

				@Override
				public void completeWithResult(Future<O> future, O stdOut) {
					stdOutReference.set(stdOut, true);

					boolean[] markHolder = new boolean[1];

					E stdErr = stdErrReference.get(markHolder);

					if (markHolder[0]) {
						defaultNoticeableFuture.set(
							new ObjectValuePair<O, E>(stdOut, stdErr));
					}
				}

			});

		stdErrNoticeableFuture.addFutureListener(
			new BaseFutureListener<E>() {

				@Override
				public void completeWithCancel(Future<E> future) {
					defaultNoticeableFuture.cancel(true);
				}

				@Override
				public void completeWithException(
					Future<E> future, Throwable throwable) {

					defaultNoticeableFuture.setException(throwable);
				}

				@Override
				public void completeWithResult(Future<E> future, E stdErr) {
					stdErrReference.set(stdErr, true);

					boolean[] markHolder = new boolean[1];

					O stdOut = stdOutReference.get(markHolder);

					if (markHolder[0]) {
						defaultNoticeableFuture.set(
							new ObjectValuePair<O, E>(stdOut, stdErr));
					}
				}

			});

		return defaultNoticeableFuture;
	}

	private static volatile ThreadPoolExecutor _threadPoolExecutor;

	private static class ProcessStdErrCallable<T> implements Callable<T> {

		public ProcessStdErrCallable(
			OutputProcessor<?, T> outputProcessor, Process process) {

			_outputProcessor = outputProcessor;
			_process = process;
		}

		@Override
		public T call() throws Exception {
			return _outputProcessor.processStdErr(_process.getErrorStream());
		}

		private final OutputProcessor<?, T> _outputProcessor;
		private final Process _process;

	}

	private static class ProcessStdOutCallable<T> implements Callable<T> {

		public ProcessStdOutCallable(
			OutputProcessor<T, ?> outputProcessor, Process process) {

			_outputProcessor = outputProcessor;
			_process = process;
		}

		@Override
		public T call() throws Exception {
			try {
				return _outputProcessor.processStdOut(
					_process.getInputStream());
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
			}
		}

		private final OutputProcessor<T, ?> _outputProcessor;
		private final Process _process;

	}

}