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

import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ProcessUtilTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@After
	public void tearDown() throws Exception {
		ExecutorService executorService = _getExecutorService();

		if (executorService != null) {
			executorService.shutdownNow();

			executorService.awaitTermination(10, TimeUnit.SECONDS);

			_nullOutExecutorService();
		}
	}

	@Test
	public void testConcurrentCreateExecutorService() throws Exception {
		final AtomicReference<ExecutorService> atomicReference =
			new AtomicReference<ExecutorService>();

		Thread thread = new Thread() {

			@Override
			public void run() {
				try {
					ExecutorService executorService =
						_invokeGetExecutorService();

					atomicReference.set(executorService);
				}
				catch (Exception e) {
					Assert.fail();
				}
			}

		};

		ExecutorService executorService = null;

		synchronized (ProcessUtil.class) {
			thread.start();

			while (thread.getState() != Thread.State.BLOCKED);

			executorService = _invokeGetExecutorService();
		}

		thread.join();

		Assert.assertSame(executorService, atomicReference.get());

		_invokeGetExecutorService();
	}

	@Test
	public void testDestroy() throws Exception {

		// Clean destroy

		ProcessUtil processUtil = new ProcessUtil();

		processUtil.destroy();

		Assert.assertNull(_getExecutorService());

		// Idle destroy

		ExecutorService executorService = _invokeGetExecutorService();

		Assert.assertNotNull(executorService);
		Assert.assertNotNull(_getExecutorService());

		processUtil.destroy();

		Assert.assertNull(_getExecutorService());

		// Busy destroy

		executorService = _invokeGetExecutorService();

		Assert.assertNotNull(executorService);
		Assert.assertNotNull(_getExecutorService());

		DummyJob dummyJob = new DummyJob();

		Future<Void> future = executorService.submit(dummyJob);

		dummyJob.waitUntilStarted();

		processUtil.destroy();

		try {
			future.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			Assert.assertTrue(throwable instanceof InterruptedException);
		}

		Assert.assertNull(_getExecutorService());

		// Concurrent destroy

		_invokeGetExecutorService();

		final ProcessUtil referenceProcessUtil = processUtil;

		Thread thread = new Thread() {

			@Override
			public void run() {
				referenceProcessUtil.destroy();
			}

		};

		synchronized (ProcessUtil.class) {
			thread.start();

			while (thread.getState() != Thread.State.BLOCKED);

			processUtil.destroy();
		}

		thread.join();

		_invokeGetExecutorService();

		processUtil.destroy();

		// Destroy after destroyed

		processUtil.destroy();

		Assert.assertNull(_getExecutorService());
	}

	@Test
	public void testEcho() throws Exception {

		// Logging

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			LoggingOutputProcessor.class.getName(), Level.INFO);

		try {
			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Future<ObjectValuePair<Void, Void>> loggingFuture =
				ProcessUtil.execute(
					ProcessUtil.LOGGING_OUTPUT_PROCESSOR,
					_buildArguments(Echo.class, "2"));

			loggingFuture.get();

			loggingFuture.cancel(true);

			List<String> messageRecords = new ArrayList<String>();

			for (LogRecord logRecord : logRecords) {
				messageRecords.add(logRecord.getMessage());
			}

			Assert.assertTrue(
				messageRecords.contains(Echo.buildMessage(false, 0)));
			Assert.assertTrue(
				messageRecords.contains(Echo.buildMessage(false, 1)));
			Assert.assertTrue(
				messageRecords.contains(Echo.buildMessage(true, 0)));
			Assert.assertTrue(
				messageRecords.contains(Echo.buildMessage(true, 1)));
		}
		finally {
			captureHandler.close();
		}

		// Collector

		Future<ObjectValuePair<byte[], byte[]>> collectorFuture =
			ProcessUtil.execute(
				ProcessUtil.COLLECTOR_OUTPUT_PROCESSOR,
				_buildArguments(Echo.class, "2"));

		ObjectValuePair<byte[], byte[]> objectValuePair = collectorFuture.get();

		collectorFuture.cancel(true);

		Assert.assertEquals(
			Echo.buildMessage(true, 0) + "\n" + Echo.buildMessage(true, 1) +
				"\n",
			new String(objectValuePair.getKey()));
		Assert.assertEquals(
			Echo.buildMessage(false, 0) + "\n" + Echo.buildMessage(false, 1) +
				"\n",
			new String(objectValuePair.getValue()));
	}

	@Test
	public void testErrorExit() throws Exception {
		Future<?> future = ProcessUtil.execute(
			ProcessUtil.CONSUMER_OUTPUT_PROCESSOR,
			_buildArguments(ErrorExit.class));

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
	public void testExecuteAfterShutdown() throws Exception {
		ExecutorService executorService = _invokeGetExecutorService();

		executorService.shutdown();

		try {
			ProcessUtil.execute(
				ProcessUtil.LOGGING_OUTPUT_PROCESSOR,
				_buildArguments(Echo.class, "2"));

			Assert.fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			Assert.assertEquals(
				RejectedExecutionException.class, throwable.getClass());
		}
	}

	@Test
	public void testFuture() throws Exception {

		// Time out on standard error processing

		String[] arguments = _buildArguments(Pause.class);

		Future<?> future = ProcessUtil.execute(
			ProcessUtil.CONSUMER_OUTPUT_PROCESSOR, arguments);

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
			ProcessUtil.CONSUMER_OUTPUT_PROCESSOR,
			_buildArguments(Echo.class, "0"));

		future.get(1, TimeUnit.MINUTES);
	}

	@Test
	public void testInterruptPause() throws Exception {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		final Thread mainThread = Thread.currentThread();

		Thread interruptThread = new Thread() {

			@Override
			public void run() {
				try {
					countDownLatch.await();

					while (mainThread.getState() != State.WAITING);

					ExecutorService executorService = _getExecutorService();

					executorService.shutdownNow();
				}
				catch (Exception e) {
					Assert.fail();
				}
			}

		};

		interruptThread.start();

		final Future<?> future = ProcessUtil.execute(
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
			countDownLatch.countDown();

			future.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			Assert.assertEquals(ProcessException.class, throwable.getClass());
			Assert.assertEquals(
				"Forcibly killed subprocess on interruption",
				throwable.getMessage());
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
				ProcessUtil.CONSUMER_OUTPUT_PROCESSOR, (List<String>)null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Arguments is null", npe.getMessage());
		}

		try {
			ProcessUtil.execute(
				ProcessUtil.CONSUMER_OUTPUT_PROCESSOR, "commandNotExist");

			Assert.fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			Assert.assertEquals(IOException.class, throwable.getClass());
		}
	}

	private static String[] _buildArguments(
		Class<?> clazz, String... arguments) {

		List<String> argumentsList = new ArrayList<String>();

		argumentsList.add("java");
		argumentsList.add("-cp");
		argumentsList.add(_CLASS_PATH);
		argumentsList.add(clazz.getName());
		argumentsList.addAll(Arrays.asList(arguments));

		return argumentsList.toArray(new String[argumentsList.size()]);
	}

	private static ExecutorService _getExecutorService() throws Exception {
		Field field = ProcessUtil.class.getDeclaredField("_executorService");

		field.setAccessible(true);

		return (ExecutorService)field.get(null);
	}

	private static ExecutorService _invokeGetExecutorService()
		throws Exception {

		Method method = ProcessUtil.class.getDeclaredMethod(
			"_getExecutorService");

		method.setAccessible(true);

		return (ExecutorService)method.invoke(method);
	}

	private static void _nullOutExecutorService() throws Exception {
		Field field = ProcessUtil.class.getDeclaredField("_executorService");

		field.setAccessible(true);

		field.set(null, null);
	}

	private static final String _CLASS_PATH;

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

	private static class DummyJob implements Callable<Void> {

		public DummyJob() {
			_countDownLatch = new CountDownLatch(1);
		}

		@Override
		public Void call() throws Exception {
			_countDownLatch.countDown();

			Thread.sleep(Long.MAX_VALUE);

			return null;
		}

		public void waitUntilStarted() throws InterruptedException {
			_countDownLatch.await();
		}

		private CountDownLatch _countDownLatch;

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

}