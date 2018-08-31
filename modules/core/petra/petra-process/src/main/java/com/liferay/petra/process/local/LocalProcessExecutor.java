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

package com.liferay.petra.process.local;

import com.liferay.petra.concurrent.AsyncBroker;
import com.liferay.petra.concurrent.DefaultNoticeableFuture;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.io.ClassLoaderObjectInputStream;
import com.liferay.petra.io.unsync.UnsyncBufferedInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessChannel;
import com.liferay.petra.process.ProcessConfig;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.process.ProcessExecutor;
import com.liferay.petra.process.ProcessLog;
import com.liferay.petra.process.TerminationProcessException;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.io.WriteAbortedException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * @author Shuyang Zhou
 */
public class LocalProcessExecutor implements ProcessExecutor {

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

			AsyncBroker<Long, Serializable> asyncBroker = new AsyncBroker<>();

			SubprocessReactor<T> subprocessReactor = new SubprocessReactor<>(
				process, processConfig.getProcessLogConsumer(),
				processConfig.getReactClassLoader(), asyncBroker);

			NoticeableFuture<T> noticeableFuture = _submit(
				_buildThreadName(processCallable, arguments),
				subprocessReactor);

			noticeableFuture.addFutureListener(
				future -> {
					if (future.isCancelled()) {
						process.destroy();
					}
				});

			return new LocalProcessChannel<>(
				noticeableFuture, objectOutputStream, asyncBroker);
		}
		catch (IOException ioe) {
			throw new ProcessException(ioe);
		}
	}

	private static String _buildThreadName(
		ProcessCallable<?> processCallable, List<String> arguments) {

		StringBundler sb = new StringBundler(arguments.size() * 2 + 2);

		sb.append(processCallable);
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

	private class SubprocessReactor<T extends Serializable>
		implements Callable<T> {

		@Override
		public T call() throws Exception {
			ProcessCallable<T> resultProcessCallable = null;

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
							_processLogConsumer.accept(
								new LocalProcessLog(
									ProcessLog.Level.WARN,
									"Found corrupt leading log " +
										unsyncByteArrayOutputStream.toString(),
									null));
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
						_processLogConsumer.accept(
							new LocalProcessLog(
								ProcessLog.Level.WARN,
								"Caught a write aborted exception", wae));

						continue;
					}

					if (!(obj instanceof ProcessCallable)) {
						_processLogConsumer.accept(
							new LocalProcessLog(
								ProcessLog.Level.INFO,
								"Received a nonprocess callable piping back " +
									obj,
								null));

						continue;
					}

					ProcessCallable<?> processCallable =
						(ProcessCallable<?>)obj;

					if (processCallable instanceof ResultProcessCallable) {
						resultProcessCallable =
							(ProcessCallable<T>)processCallable;

						continue;
					}

					try {
						Serializable returnValue = processCallable.call();

						_processLogConsumer.accept(
							new LocalProcessLog(
								ProcessLog.Level.DEBUG,
								StringBundler.concat(
									"Invoked generic process callable ",
									processCallable, " with return value ",
									returnValue),
								null));
					}
					catch (Throwable t) {
						_processLogConsumer.accept(
							new LocalProcessLog(
								ProcessLog.Level.ERROR,
								"Unable to invoke generic process callable",
								t));
					}
				}
			}
			catch (StreamCorruptedException sce) {
				Path path = Files.createTempFile(
					"corrupted-stream-dump-", ".log");

				_processLogConsumer.accept(
					new LocalProcessLog(
						ProcessLog.Level.ERROR,
						"Dumping content of corrupted object input stream to " +
							path.toAbsolutePath(),
						sce));

				Files.copy(
					unsyncBufferedInputStream, path,
					StandardCopyOption.REPLACE_EXISTING);

				throw new ProcessException(
					"Corrupted object input stream", sce);
			}
			catch (EOFException eofe) {
				throw new ProcessException(
					"Subprocess piping back ended prematurely", eofe);
			}
			catch (Throwable t) {
				_processLogConsumer.accept(
					new LocalProcessLog(
						ProcessLog.Level.ERROR, "Abort subprocess piping", t));

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

				AsyncBrokerThreadLocal.removeAsyncBroker();

				if (resultProcessCallable != null) {

					// Override previous process exception if there was one

					return resultProcessCallable.call();
				}
			}
		}

		private SubprocessReactor(
			Process process, Consumer<ProcessLog> processLogConsumer,
			ClassLoader reactClassLoader,
			AsyncBroker<Long, Serializable> asyncBroker) {

			_process = process;
			_processLogConsumer = processLogConsumer;
			_reactClassLoader = reactClassLoader;
			_asyncBroker = asyncBroker;
		}

		private final AsyncBroker<Long, Serializable> _asyncBroker;
		private final Process _process;
		private final Consumer<ProcessLog> _processLogConsumer;
		private final ClassLoader _reactClassLoader;

	}

}