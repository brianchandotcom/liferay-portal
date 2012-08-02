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

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;

/**
 * @author Ivica Cardic
 */
public class NativeProcessExecutor {

	public static <T extends Serializable> Future<T> execute(
			List<String> arguments)
		throws ProcessException {

		return execute(arguments, null);
	}

	public static <T> Future<T> execute(
		List<String> arguments, NativeProcessOutputConsumer<T> outputConsumer)
		throws ProcessException {

		ProcessBuilder processBuilder = new ProcessBuilder(
			arguments.toArray(new String[arguments.size()]));

		processBuilder.redirectErrorStream(true);

		Process process;
		try {
			process = processBuilder.start();
		} catch (IOException e) {
			throw new ProcessException(e);
		}

		ProcessFutureTask<T> futureTask = new ProcessFutureTask<T>(
			process, outputConsumer);

		try {
			ExecutorService executorService = _getExecutorService();

			executorService.execute(futureTask);

			if (_log.isInfoEnabled()) {
				executorService.execute(new ProcessLogRunnable(process));
			}

			_managedProcesses.add(process);

			return futureTask;
		} catch (RejectedExecutionException e) {
			process.destroy();

			throw new ProcessException(
				"Cancelled execution because of a concurrent destroy", e);
		}
	}

	public void destroy() {
		synchronized (NativeProcessExecutor.class) {
			for (Process _managedProcess : _managedProcesses) {
				_managedProcess.destroy();
			}
		}

		_managedProcesses.clear();

		_executorService = null;
	}

	private static ExecutorService _getExecutorService() {
		if (_executorService != null) {
			return _executorService;
		}

		synchronized (ProcessExecutor.class) {
			if (_executorService == null) {
				_executorService = Executors.newCachedThreadPool();
			}
		}

		return _executorService;
	}

	private static Log _log = LogFactoryUtil.getLog(
		NativeProcessExecutor.class);

	private static volatile ExecutorService _executorService;
	private static Set<Process> _managedProcesses =
		new ConcurrentHashSet<Process>();

	private static class ProcessFutureTask<V> extends FutureTask<V> {

		public ProcessFutureTask(
			final Process process,
			final NativeProcessOutputConsumer<V> outputConsumer) {

			super(new Callable<V>() {
				public V call() throws Exception {
					return waitProcess(process, outputConsumer);
				}
			});

			this._process = process;
		}

		public boolean cancel(boolean mayInterruptIfRunning) {
			if (isDone()) {
				return super.cancel(mayInterruptIfRunning);
			}

			_process.destroy();

			_managedProcesses.remove(_process);

			return super.cancel(mayInterruptIfRunning);
		}

		private static <V> V waitProcess(
				Process process, NativeProcessOutputConsumer<V> outputConsumer)
			throws Exception {

			try {
				V output = null;

				if (outputConsumer != null) {
					InputStream is = process.getInputStream();

					try {
						output = outputConsumer.consumeOutput(is);
					} catch (Exception e) {
						process.destroy();

						throw new ProcessException(e);
					}
				}

				try {
					int exitCode = process.waitFor();

					if (exitCode != 0) {
						_log.warn(
							"Subprocess terminated with exit code " + exitCode);
					}
				}
				catch (InterruptedException ie) {
					process.destroy();

					_log.warn("Forcibly killed subprocess on interruption", ie);
				}

				return output;
			} finally {
				_managedProcesses.remove(process);

				try {
					process.getInputStream().close();
					process.getOutputStream().close();
					process.getErrorStream().close();
				} catch (Exception ignored) {
				}
			}
		}

		private Process _process;
	}

	private static class ProcessLogRunnable implements Runnable {

		public ProcessLogRunnable(Process process) {
			_process = process;
		}

		public void run() {
			InputStream is = _process.getInputStream();

			InputStreamReader isr = new InputStreamReader(is);
			final BufferedReader br = new BufferedReader(isr);

			String line;
			try {
				while ((line = br.readLine()) != null) {
					_log.info(line);
				}
			}catch(IOException ignored){}
		}

		private Process _process;
	}

}