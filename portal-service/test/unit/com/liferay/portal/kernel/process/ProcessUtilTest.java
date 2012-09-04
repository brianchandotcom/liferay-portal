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

import com.liferay.portal.kernel.log.Jdk14LogImpl;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogWrapper;
import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * @author Shuyang Zhou
 */
public class ProcessUtilTest extends TestCase {

	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		ExecutorService executorService = _getExecutorService();

		if (executorService != null) {
			executorService.shutdownNow();

			executorService.awaitTermination(10, TimeUnit.SECONDS);

			_nullOutExecutorService();
		}
	}

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
					fail();
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

		assertSame(executorService, atomicReference.get());

		_invokeGetExecutorService();
	}

	public void testDestroy() throws Exception {

		// Clean destroy

		ProcessUtil processUtil = new ProcessUtil();

		processUtil.destroy();

		assertNull(_getExecutorService());

		// Idle destroy

		ExecutorService executorService = _invokeGetExecutorService();

		assertNotNull(executorService);
		assertNotNull(_getExecutorService());

		processUtil.destroy();

		assertNull(_getExecutorService());

		// Busy destroy

		executorService = _invokeGetExecutorService();

		assertNotNull(executorService);
		assertNotNull(_getExecutorService());

		DummyJob dummyJob = new DummyJob();

		Future<Void> future = executorService.submit(dummyJob);

		dummyJob.waitUntilStarted();

		processUtil.destroy();

		try {
			future.get();

			fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			assertTrue(throwable instanceof InterruptedException);
		}

		assertNull(_getExecutorService());

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

		assertNull(_getExecutorService());
	}

	public void testEchoLogging() throws Exception {
		Logger logger = _getLogger();

		Level level = logger.getLevel();

		logger.setLevel(Level.INFO);

		CaptureHandler captureHandler = new CaptureHandler();

		logger.addHandler(captureHandler);

		try {
			Future<?> future = ProcessUtil.execute(
				ProcessUtil.LOGGING_OUTPUT_PROCESSOR,
				_buildArguments(Echo.class, "2"));

			future.get();

			future.cancel(true);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			List<String> messageRecords = new ArrayList<String>();

			for (LogRecord logRecord : logRecords) {
				messageRecords.add(logRecord.getMessage());
			}

			assertTrue(
				messageRecords.contains(
					"{stdErr}" + Echo.class.getName() + "0"));
			assertTrue(
				messageRecords.contains(
					"{stdErr}" + Echo.class.getName() + "1"));
			assertTrue(
				messageRecords.contains(
					"{stdOut}" + Echo.class.getName() + "0"));
			assertTrue(
				messageRecords.contains(
					"{stdOut}" + Echo.class.getName() + "1"));
		}
		finally {
			logger.removeHandler(captureHandler);

			logger.setLevel(level);
		}
	}

	public void testErrorExit() throws Exception {
		Future<?> future = ProcessUtil.execute(
			ProcessUtil.CONSUMER_OUTPUT_PROCESSOR,
			_buildArguments(ErrorExit.class));

		try {
			future.get();

			fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			assertEquals(ProcessException.class, throwable.getClass());
			assertEquals(
				"Subprocess terminated with exit code " + ErrorExit.EXIT_CODE,
				throwable.getMessage());
		}
	}

	public void testErrorOutputProcessor() throws Exception {
		String[] arguments = _buildArguments(Echo.class, "1");

		Future<?> future = ProcessUtil.execute(
			new ErrorStderrOutputProcessor(), arguments);

		try {
			future.get();

			fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			assertEquals(ProcessException.class, throwable.getClass());
			assertEquals(
				ErrorStderrOutputProcessor.class.getName(),
				throwable.getMessage());
		}

		future = ProcessUtil.execute(
			new ErrorStdoutOutputProcessor(), arguments);

		try {
			future.get();

			fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			assertEquals(ProcessException.class, throwable.getClass());
			assertEquals(
				ErrorStdoutOutputProcessor.class.getName(),
				throwable.getMessage());
		}
	}

	public void testExecuteAfterShutdown() throws Exception {
		ExecutorService executorService = _invokeGetExecutorService();

		executorService.shutdown();

		try {
			ProcessUtil.execute(
				ProcessUtil.LOGGING_OUTPUT_PROCESSOR,
				_buildArguments(Echo.class, "2"));

			fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			assertEquals(
				RejectedExecutionException.class, throwable.getClass());
		}

	}

	public void testFuture() throws Exception {

		// Time out on standard error processing

		String[] arguments = _buildArguments(Pause.class);

		Future<?> future = ProcessUtil.execute(
			ProcessUtil.CONSUMER_OUTPUT_PROCESSOR, arguments);

		assertFalse(future.isCancelled());
		assertFalse(future.isDone());

		try {
			future.get(1, TimeUnit.SECONDS);

			fail();
		}
		catch (TimeoutException te) {
		}

		future.cancel(true);

		// Cancel after cancel, satisfy code coverage
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

		assertFalse(future.isCancelled());
		assertFalse(future.isDone());

		try {
			future.get(1, TimeUnit.SECONDS);

			fail();
		}
		catch (TimeoutException te) {
		}

		future.cancel(true);

		// Success time out get

		future = ProcessUtil.execute(
			ProcessUtil.CONSUMER_OUTPUT_PROCESSOR,
			_buildArguments(Echo.class, "0"));

		future.get(1, TimeUnit.SECONDS);
	}

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
					fail();
				}
			}

		};

		interruptThread.start();

		final Future<?> future = ProcessUtil.execute(
			new OutputProcessor<Void, Void>() {

				public Void processStdErr(InputStream stdErrInputStream) {
					return null;
				}

				public Void processStdOut(InputStream stdOutInputStream) {
					return null;
				}
			},
			_buildArguments(Pause.class));

		try {
			countDownLatch.countDown();

			future.get();

			fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			assertEquals(ProcessException.class, throwable.getClass());
			assertEquals(
				"Forcibly killed subprocess on interruption",
				throwable.getMessage());
		}
	}

	public void testWrongArguments() throws ProcessException {
		try {
			ProcessUtil.execute(null, (List<String>)null);

			fail();
		}
		catch (NullPointerException npe) {
			assertEquals("Output processor is null", npe.getMessage());
		}

		try {
			ProcessUtil.execute(
				ProcessUtil.CONSUMER_OUTPUT_PROCESSOR, (List<String>)null);

			fail();
		}
		catch (NullPointerException npe) {
			assertEquals("Arguments is null", npe.getMessage());
		}

		try {
			ProcessUtil.execute(
				ProcessUtil.CONSUMER_OUTPUT_PROCESSOR, "commandNotExist");

			fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			assertEquals(IOException.class, throwable.getClass());
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

	private static Logger _getLogger() throws Exception {
		LogWrapper loggerWrapper = (LogWrapper)LogFactoryUtil.getLog(
			LoggingOutputProcessor.class);

		Field field = ReflectionUtil.getDeclaredField(LogWrapper.class, "_log");

		Jdk14LogImpl jdk14LogImpl = (Jdk14LogImpl)field.get(loggerWrapper);

		field = ReflectionUtil.getDeclaredField(Jdk14LogImpl.class, "_log");

		return (Logger)field.get(jdk14LogImpl);
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

	private static class CaptureHandler extends Handler {

		@Override
		public void close() throws SecurityException {
			_logRecords.clear();
		}

		@Override
		public void flush() {
			_logRecords.clear();
		}

		public List<LogRecord> getLogRecords() {
			return _logRecords;
		}

		@Override
		public boolean isLoggable(LogRecord logRecord) {
			return true;
		}

		@Override
		public void publish(LogRecord logRecord) {
			_logRecords.add(logRecord);
		}

		private List<LogRecord> _logRecords =
			new CopyOnWriteArrayList<LogRecord>();

	}

	private static class DummyJob implements Callable<Void> {

		public DummyJob() {
			_countDownLatch = new CountDownLatch(1);
		}

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

		@SuppressWarnings("unused")
		public static void main(String[] arguments) {
			int times = Integer.parseInt(arguments[0]);

			for (int i = 0; i < times; i++) {
				System.err.println("{stdErr}" + Echo.class.getName() + i);
				System.out.println("{stdOut}" + Echo.class.getName() + i);
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

		public Void processStdErr(InputStream stdErrInputStream)
			throws ProcessException {

			throw new ProcessException(
				ErrorStderrOutputProcessor.class.getName());
		}

		public Void processStdOut(InputStream stdOutInputStream) {
			return null;
		}

	}

	private static class ErrorStdoutOutputProcessor
		implements OutputProcessor<Void, Void> {

		public Void processStdErr(InputStream stdErrInputStream) {
			return null;
		}

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