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

package com.liferay.petra.process;

import com.liferay.petra.concurrent.BaseFutureListener;
import com.liferay.petra.concurrent.DefaultNoticeableFuture;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.io.IOException;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @author Shuyang Zhou
 */
public class ProcessUtil {

	public static <O, E> NoticeableFuture<Map.Entry<O, E>> execute(
			OutputProcessor<O, E> outputProcessor, List<String> arguments)
		throws ProcessException {

		if (outputProcessor == null) {
			throw new NullPointerException("Output processor is null");
		}

		if (arguments == null) {
			throw new NullPointerException("Arguments is null");
		}

		ProcessBuilder processBuilder = new ProcessBuilder(arguments);

		String threadNamePrefix = _buildThreadNamePrefix(arguments);

		try {
			Process process = processBuilder.start();

			NoticeableFuture<O> stdOutNoticeableFuture = _submit(
				threadNamePrefix.concat("StdOut"),
				new ProcessStdOutCallable<>(outputProcessor, process));

			NoticeableFuture<E> stdErrNoticeableFuture = _submit(
				threadNamePrefix.concat("StdErr"),
				new ProcessStdErrCallable<>(outputProcessor, process));

			return _wrapNoticeableFuture(
				stdOutNoticeableFuture, stdErrNoticeableFuture, process);
		}
		catch (IOException ioe) {
			throw new ProcessException(ioe);
		}
	}

	public static <O, E> NoticeableFuture<Map.Entry<O, E>> execute(
			OutputProcessor<O, E> outputProcessor, String... arguments)
		throws ProcessException {

		return execute(outputProcessor, Arrays.asList(arguments));
	}

	private static String _buildThreadNamePrefix(List<String> arguments) {
		StringBundler sb = new StringBundler(arguments.size() * 2 + 1);

		sb.append(StringPool.OPEN_BRACKET);

		for (String argument : arguments) {
			sb.append(argument);
			sb.append(StringPool.SPACE);
		}

		sb.setStringAt(StringPool.CLOSE_BRACKET, sb.index() - 1);

		sb.append("-");

		return sb.toString();
	}

	private static <T> NoticeableFuture<T> _submit(
		String threadName, Callable<T> callable) {

		DefaultNoticeableFuture<T> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>(callable);

		Thread thread = new Thread(defaultNoticeableFuture, threadName);

		thread.setDaemon(true);

		thread.start();

		return defaultNoticeableFuture;
	}

	private static <O, E> NoticeableFuture<Map.Entry<O, E>>
		_wrapNoticeableFuture(
			NoticeableFuture<O> stdOutNoticeableFuture,
			NoticeableFuture<E> stdErrNoticeableFuture, Process process) {

		DefaultNoticeableFuture<Map.Entry<O, E>> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		defaultNoticeableFuture.addFutureListener(
			future -> {
				if (!future.isCancelled()) {
					return;
				}

				stdOutNoticeableFuture.cancel(true);

				stdErrNoticeableFuture.cancel(true);

				process.destroy();
			});

		AtomicMarkableReference<O> stdOutReference =
			new AtomicMarkableReference<>(null, false);

		AtomicMarkableReference<E> stdErrReference =
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
							new AbstractMap.SimpleEntry<>(stdOut, stdErr));
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
							new AbstractMap.SimpleEntry<>(stdOut, stdErr));
					}
				}

			});

		return defaultNoticeableFuture;
	}

	private static class ProcessStdErrCallable<T> implements Callable<T> {

		@Override
		public T call() throws Exception {
			return _outputProcessor.processStdErr(_process.getErrorStream());
		}

		private ProcessStdErrCallable(
			OutputProcessor<?, T> outputProcessor, Process process) {

			_outputProcessor = outputProcessor;
			_process = process;
		}

		private final OutputProcessor<?, T> _outputProcessor;
		private final Process _process;

	}

	private static class ProcessStdOutCallable<T> implements Callable<T> {

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

		private ProcessStdOutCallable(
			OutputProcessor<T, ?> outputProcessor, Process process) {

			_outputProcessor = outputProcessor;
			_process = process;
		}

		private final OutputProcessor<T, ?> _outputProcessor;
		private final Process _process;

	}

}