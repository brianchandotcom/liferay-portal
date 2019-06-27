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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.test.util.ThreadTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.SyncThrowableThread;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ProcessUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructor() {
		new ProcessUtil();
	}

	@Test
	public void testEcho() throws Exception {

		// Logging

		List<Map.Entry<Boolean, String>> logRecords =
			new CopyOnWriteArrayList<>();

		Future<Map.Entry<Void, Void>> loggingFuture = ProcessUtil.execute(
			new LoggingOutputProcessor(
				(stdErr, line) -> logRecords.add(
					new AbstractMap.SimpleImmutableEntry<>(stdErr, line))),
			_buildArguments(Echo.class, "2"));

		loggingFuture.get();

		loggingFuture.cancel(true);

		List<String> messageRecords = new ArrayList<>();

		for (Map.Entry<Boolean, String> logRecord : logRecords) {
			messageRecords.add(logRecord.getValue());
		}

		Assert.assertTrue(
			messageRecords.toString(),
			messageRecords.contains(Echo.buildMessage(false, 0)));
		Assert.assertTrue(
			messageRecords.toString(),
			messageRecords.contains(Echo.buildMessage(false, 1)));
		Assert.assertTrue(
			messageRecords.toString(),
			messageRecords.contains(Echo.buildMessage(true, 0)));
		Assert.assertTrue(
			messageRecords.toString(),
			messageRecords.contains(Echo.buildMessage(true, 1)));

		// Collector

		Future<Map.Entry<byte[], byte[]>> collectorFuture = ProcessUtil.execute(
			CollectorOutputProcessor.INSTANCE,
			_buildArguments(Echo.class, "2"));

		Map.Entry<byte[], byte[]> objectValuePair = collectorFuture.get();

		collectorFuture.cancel(true);

		Assert.assertEquals(
			StringBundler.concat(
				Echo.buildMessage(true, 0), "\n", Echo.buildMessage(true, 1),
				"\n"),
			new String(objectValuePair.getKey()));
		Assert.assertEquals(
			StringBundler.concat(
				Echo.buildMessage(false, 0), "\n", Echo.buildMessage(false, 1),
				"\n"),
			new String(objectValuePair.getValue()));
	}

	@Test
	public void testErrorExit() throws Exception {
		Future<?> future = ProcessUtil.execute(
			ConsumerOutputProcessor.INSTANCE, _buildArguments(ErrorExit.class));

		try {
			future.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			Assert.assertSame(
				TerminationProcessException.class, throwable.getClass());
			Assert.assertEquals(
				"Subprocess terminated with exit code " + ErrorExit.EXIT_CODE,
				throwable.getMessage());

			TerminationProcessException terminationProcessException =
				(TerminationProcessException)throwable;

			Assert.assertEquals(
				ErrorExit.EXIT_CODE, terminationProcessException.getExitCode());
		}
	}

	@Test
	public void testErrorOutputProcessor() throws Exception {
		String[] arguments = _buildArguments(Echo.class, "1");

		Future<?> future = ProcessUtil.execute(
			new ErrorStderrOutputProcessor(), arguments);

		try {
			future.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			Assert.assertEquals(ProcessException.class, throwable.getClass());
			Assert.assertEquals(
				ErrorStderrOutputProcessor.class.getName(),
				throwable.getMessage());
		}

		future = ProcessUtil.execute(
			new ErrorStdoutOutputProcessor(), arguments);

		try {
			future.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			Assert.assertEquals(ProcessException.class, throwable.getClass());
			Assert.assertEquals(
				ErrorStdoutOutputProcessor.class.getName(),
				throwable.getMessage());
		}
	}

	@Test
	public void testFuture() throws Exception {

		// Time out on standard error processing

		String[] arguments = _buildArguments(Pause.class);

		Future<?> future = ProcessUtil.execute(
			ConsumerOutputProcessor.INSTANCE, arguments);

		Assert.assertFalse(future.isCancelled());
		Assert.assertFalse(future.isDone());

		try {
			future.get(1, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (TimeoutException te) {
		}

		future.cancel(true);

		// Cancel twice to satisfy code coverage

		future.cancel(true);

		// Time out on standard out processing

		future = ProcessUtil.execute(
			new ConsumerOutputProcessor() {

				@Override
				public Void processStdErr(InputStream stdOutInputStream) {
					return null;
				}

			},
			arguments);

		Assert.assertFalse(future.isCancelled());
		Assert.assertFalse(future.isDone());

		try {
			future.get(1, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (TimeoutException te) {
		}

		future.cancel(true);

		// Success time out get

		future = ProcessUtil.execute(
			ConsumerOutputProcessor.INSTANCE, _buildArguments(Echo.class, "0"));

		future.get(1, TimeUnit.MINUTES);
	}

	@Test
	public void testInterruptPause() throws Exception {
		String threadNamePrefix = ReflectionTestUtil.invoke(
			ProcessUtil.class, "_buildThreadNamePrefix",
			new Class<?>[] {List.class},
			Arrays.asList(_buildArguments(Pause.class)));

		String threadName = threadNamePrefix + "StdOut";

		SyncThrowableThread<Void> syncThrowableThread =
			new SyncThrowableThread<>(
				() -> {
					while (true) {
						for (Thread thread : ThreadTestUtil.getThreads()) {
							if ((thread != null) &&
								threadName.equals(thread.getName())) {

								thread.interrupt();

								return null;
							}
						}
					}
				});

		syncThrowableThread.start();

		Future<?> future = ProcessUtil.execute(
			new OutputProcessor<Void, Void>() {

				@Override
				public Void processStdErr(InputStream stdErrInputStream) {
					return null;
				}

				@Override
				public Void processStdOut(InputStream stdOutInputStream) {
					return null;
				}

			},
			_buildArguments(Pause.class));

		try {
			future.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			Assert.assertSame(ProcessException.class, throwable.getClass());
			Assert.assertEquals(
				"Forcibly killed subprocess on interruption",
				throwable.getMessage());
		}
		finally {
			syncThrowableThread.sync();
		}
	}

	@Test
	public void testWrongArguments() throws ProcessException {
		try {
			ProcessUtil.execute(null, (List<String>)null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Output processor is null", npe.getMessage());
		}

		try {
			ProcessUtil.execute(
				ConsumerOutputProcessor.INSTANCE, (List<String>)null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Arguments is null", npe.getMessage());
		}

		try {
			ProcessUtil.execute(
				ConsumerOutputProcessor.INSTANCE, "commandNotExist");

			Assert.fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			Assert.assertSame(IOException.class, throwable.getClass());
		}
	}

	private static String[] _buildArguments(
		Class<?> clazz, String... arguments) {

		List<String> argumentsList = new ArrayList<>();

		argumentsList.add("java");
		argumentsList.add("-cp");
		argumentsList.add(_CLASS_PATH);
		argumentsList.add(clazz.getName());

		Collections.addAll(argumentsList, arguments);

		return argumentsList.toArray(new String[0]);
	}

	private static final String _CLASS_PATH;

	private static class DummyJob implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			_countDownLatch.countDown();

			Thread.sleep(Long.MAX_VALUE);

			return null;
		}

		public void waitUntilStarted() throws InterruptedException {
			_countDownLatch.await();
		}

		private DummyJob() {
			_countDownLatch = new CountDownLatch(1);
		}

		private final CountDownLatch _countDownLatch;

	}

	private static class Echo {

		public static String buildMessage(boolean stdOut, int number) {
			if (stdOut) {
				return "{stdOut}" + Echo.class.getName() + number;
			}

			return "{stdErr}" + Echo.class.getName() + number;
		}

		@SuppressWarnings("unused")
		public static void main(String[] arguments) {
			int times = Integer.parseInt(arguments[0]);

			for (int i = 0; i < times; i++) {
				System.err.println(buildMessage(false, i));
				System.out.println(buildMessage(true, i));
			}
		}

	}

	private static class ErrorExit {

		public static final int EXIT_CODE = 10;

		@SuppressWarnings("unused")
		public static void main(String[] arguments) {
			System.exit(EXIT_CODE);
		}

	}

	private static class ErrorStderrOutputProcessor
		implements OutputProcessor<Void, Void> {

		@Override
		public Void processStdErr(InputStream stdErrInputStream)
			throws ProcessException {

			throw new ProcessException(
				ErrorStderrOutputProcessor.class.getName());
		}

		@Override
		public Void processStdOut(InputStream stdOutInputStream) {
			return null;
		}

	}

	private static class ErrorStdoutOutputProcessor
		implements OutputProcessor<Void, Void> {

		@Override
		public Void processStdErr(InputStream stdErrInputStream) {
			return null;
		}

		@Override
		public Void processStdOut(InputStream stdOutInputStream)
			throws ProcessException {

			throw new ProcessException(
				ErrorStdoutOutputProcessor.class.getName());
		}

	}

	private static class Pause {

		@SuppressWarnings("unused")
		public static void main(String[] arguments) throws Exception {
			Thread.sleep(Long.MAX_VALUE);
		}

	}

	static {
		Class<?> clazz = Echo.class;

		ClassLoader classLoader = clazz.getClassLoader();

		String className = clazz.getName();

		String name = className.replace('.', '/') + ".class";

		URL url = classLoader.getResource(name);

		String path = url.getPath();

		int index = path.lastIndexOf(name);

		_CLASS_PATH = path.substring(0, index);
	}

}