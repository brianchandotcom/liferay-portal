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

import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessChannel;
import com.liferay.portal.kernel.process.ProcessConfig;
import com.liferay.portal.kernel.process.ProcessConfig.Builder;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.process.TerminationProcessException;
import com.liferay.portal.kernel.process.local.LocalProcessLauncher.ProcessContext;
import com.liferay.portal.kernel.process.local.LocalProcessLauncher.ShutdownHook;
import com.liferay.portal.kernel.process.log.ProcessOutputStream;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.SyncThrowableThread;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.SocketUtil;
import com.liferay.portal.kernel.util.SocketUtil.ServerSocketConfigurator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.WriteAbortedException;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.channels.ServerSocketChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class LocalProcessExecutorTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(ProcessConfig.class);
				assertClasses.addAll(
					Arrays.asList(ProcessConfig.class.getDeclaredClasses()));
				assertClasses.add(LocalProcessLauncher.class);
				assertClasses.addAll(
					Arrays.asList(
						LocalProcessLauncher.class.getDeclaredClasses()));
			}

		};

	@After
	public void tearDown() throws Exception {
		ExecutorService executorService = _getThreadPoolExecutor();

		if (executorService != null) {
			executorService.shutdownNow();

			executorService.awaitTermination(10, TimeUnit.SECONDS);

			_nullOutThreadPoolExecutor();
		}
	}

	@Test
	public void testAttach1() throws Exception {

		// No attach

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				InetAddressUtil.getLoopbackInetAddress(), 12342,
				_serverSocketConfigurator);

		try (ServerSocket serverSocket = serverSocketChannel.socket()) {
			int port = serverSocket.getLocalPort();

			_localProcessExecutor.execute(
				_createJPDAProcessConfig(_JPDA_OPTIONS1),
				new AttachParentProcessCallable(
					AttachChildProcessCallable1.class.getName(), port));

			Socket parentSocket = serverSocket.accept();

			Assert.assertTrue(ServerThread.isAlive(parentSocket));

			Socket childSocket = serverSocket.accept();

			Assert.assertTrue(ServerThread.isAlive(childSocket));

			// Kill parent

			ServerThread.exit(parentSocket);

			// Test alive 10 times for child process

			for (int i = 0; i < 10; i++) {
				Thread.sleep(100);

				Assert.assertTrue(ServerThread.isAlive(childSocket));
			}

			// Kill child

			ServerThread.exit(childSocket);
		}
	}

	@Test
	public void testAttach2() throws Exception {

		// Attach

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				InetAddressUtil.getLoopbackInetAddress(), 12342,
				_serverSocketConfigurator);

		try (ServerSocket serverSocket = serverSocketChannel.socket()) {
			int port = serverSocket.getLocalPort();

			_localProcessExecutor.execute(
				_createJPDAProcessConfig(_JPDA_OPTIONS1),
				new AttachParentProcessCallable(
					AttachChildProcessCallable2.class.getName(), port));

			Socket parentSocket = serverSocket.accept();

			Assert.assertTrue(ServerThread.isAlive(parentSocket));

			Socket childSocket = serverSocket.accept();

			Assert.assertTrue(ServerThread.isAlive(childSocket));

			// Kill parent

			ServerThread.exit(parentSocket);

			if (_log.isInfoEnabled()) {
				_log.info("Waiting subprocess to exit");
			}

			long startTime = System.currentTimeMillis();

			while (true) {
				Thread.sleep(10);

				if (!ServerThread.isAlive(childSocket)) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Subprocess exited. Waited " +
								(System.currentTimeMillis() - startTime) +
									" ms");
					}

					return;
				}
			}
		}
	}

	@Test
	public void testAttach3() throws Exception {

		// Detach

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				InetAddressUtil.getLoopbackInetAddress(), 12342,
				_serverSocketConfigurator);

		try (ServerSocket serverSocket = serverSocketChannel.socket()) {
			int port = serverSocket.getLocalPort();

			_localProcessExecutor.execute(
				_createJPDAProcessConfig(_JPDA_OPTIONS1),
				new AttachParentProcessCallable(
					AttachChildProcessCallable3.class.getName(), port));

			Socket parentSocket = serverSocket.accept();

			Assert.assertTrue(ServerThread.isAlive(parentSocket));

			Socket childSocket = serverSocket.accept();

			Assert.assertTrue(ServerThread.isAlive(childSocket));

			// Kill parent

			ServerThread.exit(parentSocket);

			if (_log.isInfoEnabled()) {
				_log.info("Waiting subprocess to exit...");
			}

			long startTime = System.currentTimeMillis();

			while (true) {
				Thread.sleep(10);

				if (!ServerThread.isAlive(childSocket)) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Subprocess exited. Waited " +
								(System.currentTimeMillis() - startTime) +
									" ms");
					}

					return;
				}
			}
		}
	}

	@Test
	public void testAttach4() throws Exception {

		// Shutdown by interruption

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				InetAddressUtil.getLoopbackInetAddress(), 12342,
				_serverSocketConfigurator);

		try (ServerSocket serverSocket = serverSocketChannel.socket()) {
			int port = serverSocket.getLocalPort();

			_localProcessExecutor.execute(
				_createJPDAProcessConfig(_JPDA_OPTIONS1),
				new AttachParentProcessCallable(
					AttachChildProcessCallable4.class.getName(), port));

			Socket parentSocket = serverSocket.accept();

			Assert.assertTrue(ServerThread.isAlive(parentSocket));

			Socket childSocket = serverSocket.accept();

			Assert.assertTrue(ServerThread.isAlive(childSocket));

			// Interrupt child process heartbeat thread

			ServerThread.interruptHeartbeatThread(childSocket);

			// Kill parent

			ServerThread.exit(parentSocket);
		}
	}

	@Test
	public void testAttach5() throws Exception {

		// Bad shutdown hook

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				InetAddressUtil.getLoopbackInetAddress(), 12342,
				_serverSocketConfigurator);

		try (ServerSocket serverSocket = serverSocketChannel.socket()) {
			int port = serverSocket.getLocalPort();

			_localProcessExecutor.execute(
				_createJPDAProcessConfig(_JPDA_OPTIONS1),
				new AttachParentProcessCallable(
					AttachChildProcessCallable5.class.getName(), port));

			Socket parentSocket = serverSocket.accept();

			Assert.assertTrue(ServerThread.isAlive(parentSocket));

			Socket childSocket = serverSocket.accept();

			Assert.assertTrue(ServerThread.isAlive(childSocket));

			// Interrupt child process heartbeat thread

			ServerThread.interruptHeartbeatThread(childSocket);

			// Kill parent

			ServerThread.exit(parentSocket);
		}
	}

	@Test
	public void testAttach6() throws Exception {

		// NPE on heartbeat piping back

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				InetAddressUtil.getLoopbackInetAddress(), 12342,
				_serverSocketConfigurator);

		try (ServerSocket serverSocket = serverSocketChannel.socket()) {
			int port = serverSocket.getLocalPort();

			_localProcessExecutor.execute(
				_createJPDAProcessConfig(_JPDA_OPTIONS1),
				new AttachParentProcessCallable(
					AttachChildProcessCallable6.class.getName(), port));

			Socket parentSocket = serverSocket.accept();

			Assert.assertTrue(ServerThread.isAlive(parentSocket));

			Socket childSocket = serverSocket.accept();

			Assert.assertTrue(ServerThread.isAlive(childSocket));

			// Null out child process' OOS to cause NPE in heartbeat thread

			ServerThread.nullOutOOS(childSocket);

			if (_log.isInfoEnabled()) {
				_log.info("Waiting subprocess to exit");
			}

			long startTime = System.currentTimeMillis();

			while (true) {
				Thread.sleep(10);

				if (!ServerThread.isAlive(childSocket)) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Subprocess exited. Waited " +
								(System.currentTimeMillis() - startTime) +
									" ms");
					}

					break;
				}
			}

			// Kill parent

			ServerThread.exit(parentSocket);
		}
	}

	@Test
	public void testBrokenPiping() throws Exception {
		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					LocalProcessExecutor.class.getName(), Level.SEVERE)) {

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			BrokenPipingProcessCallable brokenPipingProcessCallable =
				new BrokenPipingProcessCallable();

			ProcessChannel<Serializable> processChannel =
				_localProcessExecutor.execute(
					_createJPDAProcessConfig(_JPDA_OPTIONS1),
					brokenPipingProcessCallable);

			Future<Serializable> future =
				processChannel.getProcessNoticeableFuture();

			try {
				future.get();

				Assert.fail();
			}
			catch (ExecutionException ee) {
				Throwable cause = ee.getCause();

				Assert.assertTrue(cause instanceof ProcessException);

				Assert.assertEquals(
					"Corrupted object input stream", cause.getMessage());
			}

			Assert.assertFalse(future.isCancelled());
			Assert.assertTrue(future.isDone());

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			String message = logRecords.get(0).getMessage();

			int index = message.lastIndexOf(' ');

			Assert.assertTrue(index != -1);
			Assert.assertEquals(
				"Dumping content of corrupted object input stream to",
				message.substring(0, index));

			File file = new File(message.substring(index + 1));

			Assert.assertTrue(file.exists());

			file.delete();
		}
	}

	@Test
	public void testCancel() throws Exception {
		ReturnWithoutExitProcessCallable returnWithoutExitProcessCallable =
			new ReturnWithoutExitProcessCallable("");

		ProcessChannel<String> processChannel = _localProcessExecutor.execute(
			_createJPDAProcessConfig(_JPDA_OPTIONS1),
			returnWithoutExitProcessCallable);

		Future<String> future = processChannel.getProcessNoticeableFuture();

		Assert.assertFalse(future.isCancelled());
		Assert.assertFalse(future.isDone());

		Assert.assertTrue(future.cancel(true));

		try {
			future.get();

			Assert.fail();
		}
		catch (CancellationException ce) {
		}

		Assert.assertTrue(future.isCancelled());
		Assert.assertTrue(future.isDone());
		Assert.assertFalse(future.cancel(true));
	}

	@Test
	public void testConcurrentCreateExecutorService() throws Exception {
		final AtomicReference<ExecutorService> atomicReference =
			new AtomicReference<>();

		SyncThrowableThread<Void> syncThrowableThread =
			new SyncThrowableThread<>(
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						ExecutorService executorService =
							_invokeGetThreadPoolExecutor();

						atomicReference.set(executorService);

						return null;
					}

				});

		ExecutorService executorService = null;

		synchronized (_localProcessExecutor) {
			syncThrowableThread.start();

			while (syncThrowableThread.getState() != Thread.State.BLOCKED);

			executorService = _invokeGetThreadPoolExecutor();
		}

		syncThrowableThread.sync();

		Assert.assertSame(executorService, atomicReference.get());
	}

	@Test
	public void testConstructor() {
		new LocalProcessLauncher();
	}

	@Test
	public void testCrash() throws Exception {
		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					LocalProcessExecutor.class.getName(), Level.OFF)) {

			// One crash

			KillJVMProcessCallable killJVMProcessCallable =
				new KillJVMProcessCallable(1);

			ProcessChannel<Serializable> processChannel =
				_localProcessExecutor.execute(
					_createJPDAProcessConfig(_JPDA_OPTIONS1),
					killJVMProcessCallable);

			Future<Serializable> future =
				processChannel.getProcessNoticeableFuture();

			try {
				future.get();

				Assert.fail();
			}
			catch (ExecutionException ee) {
				Assert.assertFalse(future.isCancelled());
				Assert.assertTrue(future.isDone());

				Throwable throwable = ee.getCause();

				Assert.assertSame(
					TerminationProcessException.class, throwable.getClass());
				Assert.assertEquals(
					"Subprocess terminated with exit code 1",
					throwable.getMessage());

				TerminationProcessException terminationProcessException =
					(TerminationProcessException)throwable;

				Assert.assertEquals(
					1, terminationProcessException.getExitCode());
			}

			// Zero crash

			killJVMProcessCallable = new KillJVMProcessCallable(0);

			processChannel = _localProcessExecutor.execute(
				_createJPDAProcessConfig(_JPDA_OPTIONS1),
				killJVMProcessCallable);

			future = processChannel.getProcessNoticeableFuture();

			try {
				future.get();

				Assert.fail();
			}
			catch (ExecutionException ee) {
				Assert.assertFalse(future.isCancelled());
				Assert.assertTrue(future.isDone());

				Throwable throwable = ee.getCause();

				Assert.assertSame(ProcessException.class, throwable.getClass());

				throwable = throwable.getCause();

				Assert.assertSame(EOFException.class, throwable.getClass());
			}
		}
	}

	@Test
	public void testCreateProcessContext() throws Exception {
		Constructor<ProcessContext> constructor =
			ProcessContext.class.getDeclaredConstructor();

		constructor.setAccessible(true);

		constructor.newInstance();

		Assert.assertNotNull(ProcessContext.getAttributes());
	}

	@Test
	public void testDestroy() throws Exception {

		// Clean destroy

		_localProcessExecutor.destroy();

		Assert.assertNull(_getThreadPoolExecutor());

		// Idle destroy

		ExecutorService executorService = _invokeGetThreadPoolExecutor();

		Assert.assertNotNull(executorService);

		Assert.assertNotNull(_getThreadPoolExecutor());

		_localProcessExecutor.destroy();

		Assert.assertNull(_getThreadPoolExecutor());

		// Busy destroy

		executorService = _invokeGetThreadPoolExecutor();

		Assert.assertNotNull(executorService);

		Assert.assertNotNull(_getThreadPoolExecutor());

		DummyJob dummyJob = new DummyJob();

		Future<Void> future = executorService.submit(dummyJob);

		dummyJob.waitUntilStarted();

		_localProcessExecutor.destroy();

		try {
			future.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			Assert.assertTrue(throwable instanceof InterruptedException);
		}

		Assert.assertNull(_getThreadPoolExecutor());

		// Concurrent destroy

		_invokeGetThreadPoolExecutor();

		final LocalProcessExecutor referenceProcessExecutor =
			_localProcessExecutor;

		Thread thread = new Thread() {

			@Override
			public void run() {
				referenceProcessExecutor.destroy();
			}

		};

		synchronized (_localProcessExecutor) {
			thread.start();

			while (thread.getState() != Thread.State.BLOCKED);

			_localProcessExecutor.destroy();
		}

		thread.join();

		_invokeGetThreadPoolExecutor();

		_localProcessExecutor.destroy();

		// Destroy after destroyed

		_localProcessExecutor.destroy();

		Assert.assertNull(_getThreadPoolExecutor());
	}

	@Test
	public void testException() throws Exception {
		DummyExceptionProcessCallable dummyExceptionProcessCallable =
			new DummyExceptionProcessCallable();

		ProcessChannel<Serializable> processChannel =
			_localProcessExecutor.execute(
				_createJPDAProcessConfig(_JPDA_OPTIONS1),
				dummyExceptionProcessCallable);

		Future<Serializable> future =
			processChannel.getProcessNoticeableFuture();

		try {
			future.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertFalse(future.isCancelled());
			Assert.assertTrue(future.isDone());

			Throwable throwable = ee.getCause();

			Assert.assertSame(ProcessException.class, throwable.getClass());
			Assert.assertEquals(
				DummyExceptionProcessCallable.class.getName(),
				throwable.getMessage());
		}

		RuntimeExceptionProcessCallable runtimeExceptionProcessCallable =
			new RuntimeExceptionProcessCallable();

		processChannel = _localProcessExecutor.execute(
			_createJPDAProcessConfig(_JPDA_OPTIONS1),
			runtimeExceptionProcessCallable);

		future = processChannel.getProcessNoticeableFuture();

		try {
			future.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertFalse(future.isCancelled());
			Assert.assertTrue(future.isDone());

			Throwable throwable = ee.getCause();

			Assert.assertSame(ProcessException.class, throwable.getClass());

			throwable = throwable.getCause();

			Assert.assertSame(RuntimeException.class, throwable.getClass());
			Assert.assertEquals(
				RuntimeExceptionProcessCallable.class.getName(),
				throwable.getMessage());
		}
	}

	@Test
	public void testExceptionPipingBackProcessCallable() throws Exception {
		ExceptionPipingBackProcessCallable exceptionPipingBackProcessCallable =
			new ExceptionPipingBackProcessCallable();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					LocalProcessExecutor.class.getName(), Level.SEVERE)) {

			ProcessChannel<Serializable> processChannel =
				_localProcessExecutor.execute(
					_createJPDAProcessConfig(_JPDA_OPTIONS1),
					exceptionPipingBackProcessCallable);

			NoticeableFuture<Serializable> noticeableFuture =
				processChannel.getProcessNoticeableFuture();

			Assert.assertNull(noticeableFuture.get());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to invoke generic process callable",
				logRecord.getMessage());

			Throwable throwable = logRecord.getThrown();

			Assert.assertSame(ProcessException.class, throwable.getClass());
			Assert.assertEquals(
				DummyExceptionProcessCallable.class.getName(),
				throwable.getMessage());
		}
	}

	@Test
	public void testExecuteOnDestroy() throws Exception {
		ExecutorService executorService = _invokeGetThreadPoolExecutor();

		executorService.shutdownNow();

		boolean result = executorService.awaitTermination(10, TimeUnit.SECONDS);

		Assert.assertTrue(result);

		DummyReturnProcessCallable dummyReturnProcessCallable =
			new DummyReturnProcessCallable();

		try {
			_localProcessExecutor.execute(
				_createJPDAProcessConfig(_JPDA_OPTIONS1),
				dummyReturnProcessCallable);

			Assert.fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			Assert.assertEquals(
				throwable.getClass(), RejectedExecutionException.class);
		}
	}

	@Test
	public void testGetWithTimeout() throws Exception {

		// Success return

		DummyReturnProcessCallable dummyReturnProcessCallable =
			new DummyReturnProcessCallable();

		ProcessChannel<String> processChannel = _localProcessExecutor.execute(
			_createJPDAProcessConfig(_JPDA_OPTIONS1),
			dummyReturnProcessCallable);

		Future<String> future = processChannel.getProcessNoticeableFuture();

		String returnValue = future.get(100, TimeUnit.SECONDS);

		Assert.assertEquals(
			DummyReturnProcessCallable.class.getName(), returnValue);

		Assert.assertFalse(future.isCancelled());
		Assert.assertTrue(future.isDone());

		// Timeout return

		ReturnWithoutExitProcessCallable returnWithoutExitProcessCallable =
			new ReturnWithoutExitProcessCallable("");

		processChannel = _localProcessExecutor.execute(
			_createJPDAProcessConfig(_JPDA_OPTIONS1),
			returnWithoutExitProcessCallable);

		future = processChannel.getProcessNoticeableFuture();

		try {
			future.get(1, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (TimeoutException te) {
		}

		Assert.assertFalse(future.isCancelled());
		Assert.assertFalse(future.isDone());

		future.cancel(true);

		ExecutorService executorService = _getThreadPoolExecutor();

		executorService.shutdownNow();

		executorService.awaitTermination(10, TimeUnit.SECONDS);

		Assert.assertTrue(future.isCancelled());
		Assert.assertTrue(future.isDone());
	}

	@Test
	public void testLargeProcessCallable() throws Exception {
		byte[] largePayload = new byte[100 * 1024 * 1024];

		Random random = new Random();

		random.nextBytes(largePayload);

		EchoPayloadProcessCallable echoPayloadProcessCallable =
			new EchoPayloadProcessCallable(largePayload);

		ProcessChannel<byte[]> processChannel = _localProcessExecutor.execute(
			_createJPDAProcessConfig(_JPDA_OPTIONS1),
			echoPayloadProcessCallable);

		Future<byte[]> future = processChannel.getProcessNoticeableFuture();

		Assert.assertArrayEquals(largePayload, future.get());
		Assert.assertFalse(future.isCancelled());
		Assert.assertTrue(future.isDone());
	}

	@Test
	public void testLargeRuntimeClassPath() throws Exception {
		Builder builder = new Builder();

		builder.setArguments(_createArguments(_JPDA_OPTIONS1));

		char[] largeFileNameChars = new char[10 * 1024 * 1024];

		largeFileNameChars[0] = CharPool.SLASH;

		for (int i = 1; i < largeFileNameChars.length; i++) {
			largeFileNameChars[i] = (char)('a' + (i % 26));
		}

		String largeFileName = new String(largeFileNameChars);

		builder.setRuntimeClassPath(largeFileName);

		ProcessChannel<String> processChannel = _localProcessExecutor.execute(
			builder.build(), new EchoRuntimeClassPathProcessCallable());

		Future<String> future = processChannel.getProcessNoticeableFuture();

		Assert.assertEquals(largeFileName, future.get());
		Assert.assertFalse(future.isCancelled());
		Assert.assertTrue(future.isDone());
	}

	@Test
	public void testLeadingLog() throws Exception {
		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					LocalProcessExecutor.class.getName(), Level.WARNING)) {

			// Warn level

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			String leadingLog = "Test leading log.\n";
			String bodyLog = "Test body log.\n";

			LeadingLogProcessCallable leadingLogProcessCallable =
				new LeadingLogProcessCallable(leadingLog, bodyLog);

			ProcessChannel<Serializable> processChannel =
				_localProcessExecutor.execute(
					_createJPDAProcessConfig(_JPDA_OPTIONS1),
					leadingLogProcessCallable);

			Future<Serializable> future =
				processChannel.getProcessNoticeableFuture();

			future.get();

			Assert.assertFalse(future.isCancelled());
			Assert.assertTrue(future.isDone());

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Found corrupt leading log " + leadingLog,
				logRecord.getMessage());

			// Fine level

			logRecords = captureHandler.resetLogLevel(Level.FINE);

			leadingLogProcessCallable = new LeadingLogProcessCallable(
				leadingLog, bodyLog);

			processChannel = _localProcessExecutor.execute(
				_createJPDAProcessConfig(_JPDA_OPTIONS1),
				leadingLogProcessCallable);

			future = processChannel.getProcessNoticeableFuture();

			future.get();

			Assert.assertFalse(future.isCancelled());
			Assert.assertTrue(future.isDone());

			Assert.assertEquals(logRecords.toString(), 2, logRecords.size());

			LogRecord logRecord1 = logRecords.get(0);

			Assert.assertEquals(
				"Found corrupt leading log " + leadingLog,
				logRecord1.getMessage());

			LogRecord logRecord2 = logRecords.get(1);

			String message = logRecord2.getMessage();

			Assert.assertTrue(
				message.contains("Invoked generic process callable"));

			// Severe level

			logRecords = captureHandler.resetLogLevel(Level.SEVERE);

			leadingLogProcessCallable = new LeadingLogProcessCallable(
				leadingLog, bodyLog);

			processChannel = _localProcessExecutor.execute(
				_createJPDAProcessConfig(_JPDA_OPTIONS1),
				leadingLogProcessCallable);

			future = processChannel.getProcessNoticeableFuture();

			future.get();

			Assert.assertFalse(future.isCancelled());
			Assert.assertTrue(future.isDone());

			Assert.assertEquals(logRecords.toString(), 0, logRecords.size());
		}
	}

	@Test
	public void testLogging() throws Exception {
		PrintStream oldOutPrintStream = System.out;

		ByteArrayOutputStream outByteArrayOutputStream =
			new ByteArrayOutputStream();

		PrintStream newOutPrintStream = new PrintStream(
			outByteArrayOutputStream, true);

		System.setOut(newOutPrintStream);

		PrintStream oldErrPrintStream = System.err;

		ByteArrayOutputStream errByteArrayOutputStream =
			new ByteArrayOutputStream();

		PrintStream newErrPrintStream = new PrintStream(
			errByteArrayOutputStream, true);

		System.setErr(newErrPrintStream);

		File signalFile = new File("signal");

		signalFile.delete();

		try {
			String logMessage = "Log Message";

			final LoggingProcessCallable loggingProcessCallable =
				new LoggingProcessCallable(logMessage, signalFile);

			final AtomicReference<Exception> exceptionAtomicReference =
				new AtomicReference<>();

			Thread thread = new Thread() {

				@Override
				public void run() {
					try {
						ProcessChannel<Serializable> processChannel =
							_localProcessExecutor.execute(
								_createJPDAProcessConfig(_JPDA_OPTIONS1),
								loggingProcessCallable);

						Future<Serializable> future =
							processChannel.getProcessNoticeableFuture();

						future.get();

						Assert.assertFalse(future.isCancelled());
						Assert.assertTrue(future.isDone());
					}
					catch (Exception e) {
						exceptionAtomicReference.set(e);
					}
				}

			};

			thread.start();

			Assert.assertTrue(signalFile.createNewFile());

			_waitForSignalFile(signalFile, false);

			Assert.assertTrue(signalFile.createNewFile());

			thread.join();

			Exception e = exceptionAtomicReference.get();

			if (e != null) {
				throw e;
			}

			String outByteArrayOutputStreamString =
				outByteArrayOutputStream.toString();

			Assert.assertTrue(
				outByteArrayOutputStreamString.contains(logMessage));

			String errByteArrayOutputStreamString =
				errByteArrayOutputStream.toString();

			Assert.assertTrue(
				errByteArrayOutputStreamString.contains(logMessage));
		}
		finally {
			System.setOut(oldOutPrintStream);
			System.setErr(oldErrPrintStream);

			signalFile.delete();
		}
	}

	@Test
	public void testNonprocessCallablePipingBackProcessCallable()
		throws Exception {

		NonprocessCallablePipingBackProcessCallable
			nonprocessCallablePipingBackProcessCallable =
				new NonprocessCallablePipingBackProcessCallable();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					LocalProcessExecutor.class.getName(), Level.INFO)) {

			ProcessChannel<Serializable> processChannel =
				_localProcessExecutor.execute(
					_createJPDAProcessConfig(_JPDA_OPTIONS1),
					nonprocessCallablePipingBackProcessCallable);

			NoticeableFuture<Serializable> noticeableFuture =
				processChannel.getProcessNoticeableFuture();

			noticeableFuture.get();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Received a nonprocess callable piping back string piping " +
					"back object",
				logRecord.getMessage());
		}

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					LocalProcessExecutor.class.getName(), Level.OFF)) {

			ProcessChannel<Serializable> processChannel =
				_localProcessExecutor.execute(
					_createJPDAProcessConfig(_JPDA_OPTIONS1),
					nonprocessCallablePipingBackProcessCallable);

			NoticeableFuture<Serializable> noticeableFuture =
				processChannel.getProcessNoticeableFuture();

			noticeableFuture.get();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.isEmpty());
		}
	}

	@Test
	public void testProcessChannelPiping() throws Exception {
		ReturnWithoutExitProcessCallable returnWithoutExitProcessCallable =
			new ReturnWithoutExitProcessCallable("Premature return value");

		ProcessChannel<String> processChannel = _localProcessExecutor.execute(
			_createJPDAProcessConfig(_JPDA_OPTIONS1),
			returnWithoutExitProcessCallable);

		Future<String> resultFuture = processChannel.write(
			new DummyReturnProcessCallable());

		Assert.assertEquals(
			DummyReturnProcessCallable.class.getName(), resultFuture.get());

		PrintStream oldErrPrintStream = System.err;

		ByteArrayOutputStream errByteArrayOutputStream =
			new ByteArrayOutputStream();

		PrintStream newErrPrintStream = new PrintStream(
			errByteArrayOutputStream, true);

		System.setErr(newErrPrintStream);

		try {
			Future<Serializable> exceptionFuture = processChannel.write(
				new DummyExceptionProcessCallable());

			try {
				exceptionFuture.get();

				Assert.fail();
			}
			catch (ExecutionException ee) {
				Throwable throwable = ee.getCause();

				Assert.assertEquals(
					DummyExceptionProcessCallable.class.getName(),
					throwable.getMessage());
			}

			Future<Serializable> interruptFuture = processChannel.write(
				new InterruptProcessCallable());

			try {
				Assert.assertNull(interruptFuture.get());
			}
			catch (CancellationException ce) {
			}
		}
		finally {
			System.setErr(oldErrPrintStream);

			String errLog = errByteArrayOutputStream.toString();

			Assert.assertTrue(
				errLog.startsWith(
					"[" + returnWithoutExitProcessCallable.toString() + "]" +
						new ProcessException(
							DummyExceptionProcessCallable.class.getName())));
		}

		Future<String> processFuture =
			processChannel.getProcessNoticeableFuture();

		try {
			Assert.fail(processFuture.get());
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			throwable = throwable.getCause();

			Assert.assertSame(InterruptedException.class, throwable.getClass());
		}
	}

	@Test
	public void testPropertyPassing() throws Exception {
		Builder builder = new Builder();

		List<String> arguments = _createArguments(_JPDA_OPTIONS1);

		String propertyKey = "test-key";
		String propertyValue = "test-value";

		arguments.add("-D" + propertyKey + "=" + propertyValue);

		builder.setArguments(arguments);

		ProcessChannel<String> processChannel = _localProcessExecutor.execute(
			builder.build(), new ReadPropertyProcessCallable(propertyKey));

		Future<String> future = processChannel.getProcessNoticeableFuture();

		Assert.assertEquals(propertyValue, future.get());
		Assert.assertFalse(future.isCancelled());
		Assert.assertTrue(future.isDone());
	}

	@Test
	public void testReturn() throws Exception {
		DummyReturnProcessCallable dummyReturnProcessCallable =
			new DummyReturnProcessCallable();

		ProcessChannel<String> processChannel = _localProcessExecutor.execute(
			_createJPDAProcessConfig(_JPDA_OPTIONS1),
			dummyReturnProcessCallable);

		Future<String> future = processChannel.getProcessNoticeableFuture();

		Assert.assertEquals(
			DummyReturnProcessCallable.class.getName(), future.get());
		Assert.assertFalse(future.isCancelled());
		Assert.assertTrue(future.isDone());
		Assert.assertFalse(future.cancel(true));
	}

	@Test
	public void testReturnWithoutExit() throws Exception {
		ReturnWithoutExitProcessCallable returnWithoutExitProcessCallable =
			new ReturnWithoutExitProcessCallable("Premature return value");

		ProcessChannel<String> processChannel = _localProcessExecutor.execute(
			_createJPDAProcessConfig(_JPDA_OPTIONS1),
			returnWithoutExitProcessCallable);

		Future<String> future = processChannel.getProcessNoticeableFuture();

		for (int i = 0; i < 10; i++) {
			try {
				future.get(1, TimeUnit.SECONDS);

				Assert.fail();
			}
			catch (TimeoutException te) {
			}
		}

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					LocalProcessExecutor.class.getName(), Level.OFF)) {

			Set<Process> processes = _localProcessExecutor.destroy();

			Assert.assertEquals(processes.toString(), 1, processes.size());

			try {
				future.get();

				Assert.fail();
			}
			catch (CancellationException ce) {
				Assert.assertTrue(future.isCancelled());
				Assert.assertTrue(future.isDone());
			}

			Iterator<Process> iterator = processes.iterator();

			Process process = iterator.next();

			Assert.assertTrue(process.waitFor() > 0);
		}
	}

	@Test
	public void testUnserializablePipingBackProcessCallable() throws Exception {
		UnserializablePipingBackProcessCallable
			unserializablePipingBackProcessCallable =
				new UnserializablePipingBackProcessCallable();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					LocalProcessExecutor.class.getName(), Level.WARNING)) {

			ProcessChannel<Serializable> processChannel =
				_localProcessExecutor.execute(
					_createJPDAProcessConfig(_JPDA_OPTIONS1),
					unserializablePipingBackProcessCallable);

			NoticeableFuture<Serializable> noticeableFuture =
				processChannel.getProcessNoticeableFuture();

			try {
				noticeableFuture.get();

				Assert.fail();
			}
			catch (ExecutionException ee) {
				Throwable cause = ee.getCause();

				Assert.assertSame(ProcessException.class, cause.getClass());

				cause = cause.getCause();

				Assert.assertSame(
					NotSerializableException.class, cause.getClass());

				List<LogRecord> logRecords = captureHandler.getLogRecords();

				Assert.assertEquals(
					logRecords.toString(), 1, logRecords.size());

				LogRecord logRecord = logRecords.get(0);

				Assert.assertEquals(
					"Caught a write aborted exception", logRecord.getMessage());

				cause = logRecord.getThrown();

				Assert.assertSame(
					WriteAbortedException.class, cause.getClass());

				cause = cause.getCause();

				Assert.assertSame(
					NotSerializableException.class, cause.getClass());
			}
		}

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					LocalProcessExecutor.class.getName(), Level.OFF)) {

			ProcessChannel<Serializable> processChannel =
				_localProcessExecutor.execute(
					_createJPDAProcessConfig(_JPDA_OPTIONS1),
					unserializablePipingBackProcessCallable);

			NoticeableFuture<Serializable> noticeableFuture =
				processChannel.getProcessNoticeableFuture();

			try {
				noticeableFuture.get();

				Assert.fail();
			}
			catch (ExecutionException ee) {
				Throwable cause = ee.getCause();

				Assert.assertSame(ProcessException.class, cause.getClass());

				cause = cause.getCause();

				Assert.assertSame(
					NotSerializableException.class, cause.getClass());

				List<LogRecord> logRecords = captureHandler.getLogRecords();

				Assert.assertTrue(logRecords.isEmpty());
			}
		}
	}

	@Test
	public void testUnserializableProcessCallable() {
		UnserializableProcessCallable unserializableProcessCallable =
			new UnserializableProcessCallable();

		try {
			_localProcessExecutor.execute(
				_createJPDAProcessConfig(_JPDA_OPTIONS1),
				unserializableProcessCallable);

			Assert.fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			Assert.assertTrue(throwable instanceof NotSerializableException);
		}
	}

	@Test
	public void testWrongJavaExecutable() {
		try {
			Builder builder = new Builder();

			builder.setJavaExecutable("javax");

			_localProcessExecutor.execute(
				builder.build(), new DummyReturnProcessCallable());

			Assert.fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			Assert.assertTrue(throwable instanceof IOException);
		}
	}

	private static List<String> _createArguments(String jpdaOptions) {
		List<String> arguments = new ArrayList<>();

		arguments.add(
			"-D" + SystemProperties.SYSTEM_PROPERTIES_QUIET + "=true");

		if (Boolean.getBoolean("jvm.debug")) {
			arguments.add(jpdaOptions);
			arguments.add("-Djvm.debug=true");
		}

		arguments.add("-Dliferay.mode=test");
		arguments.add("-Dsun.zip.disableMemoryMapping=true");

		String whipAgentLine = System.getProperty("whip.agent");

		if (Validator.isNotNull(whipAgentLine)) {
			arguments.add(whipAgentLine);
			arguments.add("-Dwhip.agent=" + whipAgentLine);
		}

		String fileName = System.getProperty("whip.datafile");

		if (fileName != null) {
			arguments.add("-Dwhip.datafile=" + fileName);
		}

		if (Boolean.getBoolean("whip.instrument.dump")) {
			arguments.add("-Dwhip.instrument.dump=true");
		}

		arguments.add("-Dwhip.static.instrument=true");
		arguments.add("-Dwhip.static.instrument.use.data.file=true");

		return arguments;
	}

	private static ProcessConfig _createJPDAProcessConfig(String jpdaOption) {
		Builder builder = new Builder();

		builder.setArguments(_createArguments(jpdaOption));
		builder.setBootstrapClassPath(System.getProperty("java.class.path"));
		builder.setReactClassLoader(
			LocalProcessExecutorTest.class.getClassLoader());

		return builder.build();
	}

	private static Thread _getHeartbeatThread(boolean remove) {
		AtomicReference<? extends Thread> heartbeatThreadReference =
			ReflectionTestUtil.getFieldValue(
				ProcessContext.class, "_heartbeatThreadReference");

		if (remove) {
			return heartbeatThreadReference.getAndSet(null);
		}
		else {
			return heartbeatThreadReference.get();
		}
	}

	private static byte[] _toHeadlessSerializationData(
			Serializable serializable)
		throws IOException {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (ObjectOutputStream objectOutputStream =
				new ObjectOutputStream(unsyncByteArrayOutputStream) {

					@Override
					protected void writeStreamHeader() {
					}

				}) {

			objectOutputStream.reset();

			objectOutputStream.writeUnshared(serializable);
		}

		return unsyncByteArrayOutputStream.toByteArray();
	}

	private static void _waitForSignalFile(
			File signalFile, boolean expectedExists)
		throws Exception {

		while (expectedExists != signalFile.exists()) {
			Thread.sleep(100);
		}
	}

	private ThreadPoolExecutor _getThreadPoolExecutor() throws Exception {
		Field field = LocalProcessExecutor.class.getDeclaredField(
			"_threadPoolExecutor");

		field.setAccessible(true);

		return (ThreadPoolExecutor)field.get(_localProcessExecutor);
	}

	private ExecutorService _invokeGetThreadPoolExecutor() throws Exception {
		Method method = LocalProcessExecutor.class.getDeclaredMethod(
			"_getThreadPoolExecutor");

		method.setAccessible(true);

		return (ExecutorService)method.invoke(_localProcessExecutor);
	}

	private void _nullOutThreadPoolExecutor() throws Exception {
		Field field = LocalProcessExecutor.class.getDeclaredField(
			"_threadPoolExecutor");

		field.setAccessible(true);

		field.set(_localProcessExecutor, null);
	}

	private static final String _JPDA_OPTIONS1 =
		"-agentlib:jdwp=transport=dt_socket,address=8001,server=y,suspend=y";

	private static final String _JPDA_OPTIONS2 =
		"-agentlib:jdwp=transport=dt_socket,address=8002,server=y,suspend=y";

	private static final Log _log = LogFactoryUtil.getLog(
		LocalProcessExecutorTest.class);

	private static final ServerSocketConfigurator _serverSocketConfigurator =
		new ServerSocketConfigurator() {

			@Override
			public void configure(ServerSocket serverSocket)
				throws SocketException {

				serverSocket.setReuseAddress(true);
			}

		};

	private final LocalProcessExecutor _localProcessExecutor =
		new LocalProcessExecutor();

	private static class AttachChildProcessCallable1
		implements ProcessCallable<Serializable> {

		public AttachChildProcessCallable1(int serverPort) {
			_serverPort = serverPort;
		}

		@Override
		public Serializable call() throws ProcessException {
			try {
				ServerThread serverThread = new ServerThread(
					Thread.currentThread(), "Child Server Thread", _serverPort);

				serverThread.start();
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			try {
				Thread.sleep(Long.MAX_VALUE);
			}
			catch (InterruptedException ie) {
			}

			return null;
		}

		@Override
		public String toString() {
			Class<?> clazz = getClass();

			return clazz.getSimpleName();
		}

		private static final long serialVersionUID = 1L;

		private int _serverPort;

	}

	private static class AttachChildProcessCallable2
		extends AttachChildProcessCallable1 {

		public AttachChildProcessCallable2(int serverPort) {
			super(serverPort);
		}

		@Override
		public Serializable call() throws ProcessException {
			try {
				try {
					ProcessContext.attach("Child Process", 100, null);

					throw new ProcessException("Shutdown hook is null");
				}
				catch (IllegalArgumentException iae) {
				}

				boolean result = ProcessContext.attach(
					"Child Process", 100, new TestShutdownHook());

				if (!result || !ProcessContext.isAttached()) {
					throw new ProcessException("Unable to attach");
				}

				Thread.sleep(1000);

				result = ProcessContext.attach(
					"Child Process", 100, new TestShutdownHook());

				if (result) {
					throw new ProcessException("Duplicate attach");
				}

				super.call();
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return null;
		}

		private static final long serialVersionUID = 1L;

	}

	private static class AttachChildProcessCallable3
		extends AttachChildProcessCallable1 {

		public AttachChildProcessCallable3(int serverPort) {
			super(serverPort);
		}

		@Override
		public Serializable call() throws ProcessException {
			try {
				ProcessContext.detach();

				boolean result = ProcessContext.attach(
					"Child Process", Long.MAX_VALUE, new TestShutdownHook());

				if (!result || !ProcessContext.isAttached()) {
					throw new ProcessException("Unable to attach");
				}

				Thread heartbeatThread = _getHeartbeatThread(false);

				while (heartbeatThread.getState() !=
							Thread.State.TIMED_WAITING);

				ProcessContext.detach();

				if (ProcessContext.isAttached()) {
					throw new ProcessException("Unable to detach");
				}

				result = ProcessContext.attach(
					"Child Process", 100, new TestShutdownHook());

				if (!result || !ProcessContext.isAttached()) {
					throw new ProcessException("Unable to attach");
				}

				heartbeatThread = _getHeartbeatThread(true);

				ReflectionTestUtil.setFieldValue(
					heartbeatThread, "_detach", true);

				heartbeatThread.join();

				if (ProcessContext.isAttached()) {
					throw new ProcessException("Unable to detach");
				}

				result = ProcessContext.attach(
					"Child Process", 100, new TestShutdownHook());

				if (!result || !ProcessContext.isAttached()) {
					throw new ProcessException("Unable to attach");
				}

				super.call();
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return null;
		}

		private static final long serialVersionUID = 1L;

	}

	private static class AttachChildProcessCallable4
		extends AttachChildProcessCallable1 {

		public AttachChildProcessCallable4(int serverPort) {
			super(serverPort);
		}

		@Override
		public Serializable call() throws ProcessException {
			try {
				boolean result = ProcessContext.attach(
					"Child Process", Long.MAX_VALUE, new TestShutdownHook());

				if (!result || !ProcessContext.isAttached()) {
					throw new ProcessException("Unable to attach");
				}

				super.call();
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return null;
		}

		private static final long serialVersionUID = 1L;

	}

	private static class AttachChildProcessCallable5
		extends AttachChildProcessCallable1 {

		public AttachChildProcessCallable5(int serverPort) {
			super(serverPort);
		}

		@Override
		public Serializable call() throws ProcessException {
			try {
				boolean result = ProcessContext.attach(
					"Child Process", Long.MAX_VALUE,
					new TestShutdownHook(true));

				Thread heartbeatThread = _getHeartbeatThread(false);

				heartbeatThread.setUncaughtExceptionHandler(
					new UncaughtExceptionHandler() {

						@Override
						public void uncaughtException(Thread t, Throwable e) {

							// Swallow unconcerned uncaught exception

						}

					});

				if (!result || !ProcessContext.isAttached()) {
					throw new ProcessException("Unable to attach");
				}

				super.call();
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return null;
		}

		private static final long serialVersionUID = 1L;

	}

	private static class AttachChildProcessCallable6
		extends AttachChildProcessCallable1 {

		public AttachChildProcessCallable6(int serverPort) {
			super(serverPort);
		}

		@Override
		public Serializable call() throws ProcessException {
			try {
				boolean result = ProcessContext.attach(
					"Child Process", 100, new NPEOOSShutdownHook());

				if (!result || !ProcessContext.isAttached()) {
					throw new ProcessException("Unable to attach");
				}

				super.call();
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return null;
		}

		private static final long serialVersionUID = 1L;

	}

	private static class AttachParentProcessCallable
		implements ProcessCallable<Serializable> {

		public AttachParentProcessCallable(String className, int serverPort)
			throws Exception {

			_serverPort = serverPort;

			_processCallableClass = (Class<ProcessCallable<?>>)Class.forName(
				className);
		}

		@Override
		public Serializable call() throws ProcessException {
			Logger logger = Logger.getLogger("");

			logger.setLevel(Level.FINE);

			try {
				ServerThread serverThread = new ServerThread(
					Thread.currentThread(), "Parent Server Thread",
					_serverPort);

				serverThread.start();

				Constructor<ProcessCallable<?>> constructor =
					_processCallableClass.getConstructor(int.class);

				ProcessExecutor processExecutor = new LocalProcessExecutor();

				processExecutor.execute(
					_createJPDAProcessConfig(_JPDA_OPTIONS2),
					constructor.newInstance(_serverPort));
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			try {
				Thread.sleep(Long.MAX_VALUE);
			}
			catch (InterruptedException ie) {
			}

			return null;
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(7);

			Class<?> clazz = getClass();

			sb.append(clazz.getSimpleName());

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append("className=");
			sb.append(_processCallableClass.getSimpleName());
			sb.append(", serverPort=");
			sb.append(_serverPort);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}

		private static final long serialVersionUID = 1L;

		private final Class<ProcessCallable<?>> _processCallableClass;
		private int _serverPort;

	}

	private static class BrokenPipingProcessCallable
		implements ProcessCallable<Serializable> {

		public BrokenPipingProcessCallable() throws IOException {
			DummyReturnProcessCallable dummyReturnProcessCallable =
				new DummyReturnProcessCallable();

			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					unsyncByteArrayOutputStream)) {

				objectOutputStream.writeObject(dummyReturnProcessCallable);
			}

			byte[] serializedData = unsyncByteArrayOutputStream.toByteArray();

			serializedData[5] = (byte)(serializedData[5] + 1);

			_brokenPipingData = serializedData;
		}

		@Override
		public Serializable call() throws ProcessException {
			try {
				FileOutputStream fileOutputStream = new FileOutputStream(
					FileDescriptor.out);

				fileOutputStream.write(_brokenPipingData);

				fileOutputStream.flush();
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return null;
		}

		@Override
		public String toString() {
			Class<?> clazz = getClass();

			return clazz.getSimpleName();
		}

		private static final long serialVersionUID = 1L;

		private final byte[] _brokenPipingData;

	}

	private static class DummyExceptionProcessCallable
		implements ProcessCallable<Serializable> {

		@Override
		public Serializable call() throws ProcessException {
			throw new ProcessException(
				DummyExceptionProcessCallable.class.getName());
		}

		@Override
		public String toString() {
			Class<?> clazz = getClass();

			return clazz.getSimpleName();
		}

		private static final long serialVersionUID = 1L;

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

		private final CountDownLatch _countDownLatch;

	}

	private static class DummyReturnProcessCallable
		implements ProcessCallable<String> {

		@Override
		public String call() {
			return DummyReturnProcessCallable.class.getName();
		}

		@Override
		public String toString() {
			Class<?> clazz = getClass();

			return clazz.getSimpleName();
		}

		private static final long serialVersionUID = 1L;

	}

	private static class EchoPayloadProcessCallable
		implements ProcessCallable<byte[]> {

		public EchoPayloadProcessCallable(byte[] payload) {
			_payload = payload;
		}

		@Override
		public byte[] call() {
			return _payload;
		}

		private final byte[] _payload;

	}

	private static class EchoRuntimeClassPathProcessCallable
		implements ProcessCallable<String> {

		@Override
		public String call() {
			Thread currentThread = Thread.currentThread();

			URLClassLoader urlClassLoader =
				(URLClassLoader)currentThread.getContextClassLoader();

			URL[] urls = urlClassLoader.getURLs();

			StringBundler sb = new StringBundler(urls.length * 2);

			for (URL url : urls) {
				String path = url.getPath();

				int index = path.indexOf(":/");

				if (index != -1) {
					path = path.substring(index + 1);
				}

				if (path.endsWith(StringPool.SLASH)) {
					path = path.substring(0, path.length() - 1);
				}

				sb.append(path);
				sb.append(File.pathSeparator);
			}

			if (sb.index() > 0) {
				sb.setIndex(sb.index() - 1);
			}

			return sb.toString();
		}

	}

	private static class ExceptionPipingBackProcessCallable
		implements ProcessCallable<Serializable> {

		@Override
		public Serializable call() throws ProcessException {
			ProcessOutputStream processOutputStream =
				ProcessContext.getProcessOutputStream();

			try {
				processOutputStream.writeProcessCallable(
					new DummyExceptionProcessCallable());
			}
			catch (IOException ioe) {
				throw new ProcessException(ioe);
			}

			return null;
		}

		private static final long serialVersionUID = 1L;

	}

	private static class InterruptProcessCallable
		implements ProcessCallable<Serializable> {

		@Override
		public Serializable call() throws ProcessException {
			BlockingQueue<Thread> threadBlockingQueue =
				ReturnWithoutExitProcessCallable._threadBlockingQueue;

			try {
				Thread thread = threadBlockingQueue.take();

				thread.interrupt();
			}
			catch (InterruptedException ie) {
				throw new ProcessException(ie);
			}

			return null;
		}

	}

	private static class KillJVMProcessCallable
		implements ProcessCallable<Serializable> {

		public KillJVMProcessCallable(int exitCode) {
			_exitCode = exitCode;
		}

		@Override
		public Serializable call() {
			System.exit(_exitCode);

			return null;
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(5);

			Class<?> clazz = getClass();

			sb.append(clazz.getSimpleName());

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append("exitCode=");
			sb.append(_exitCode);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}

		private static final long serialVersionUID = 1L;

		private final int _exitCode;

	}

	private static class LeadingLogProcessCallable
		implements ProcessCallable<Serializable> {

		public LeadingLogProcessCallable(String leadingLog, String bodyLog) {
			_leadingLog = leadingLog;
			_bodyLog = bodyLog;
		}

		@Override
		public Serializable call() throws ProcessException {
			try {
				FileOutputStream fileOutputStream = new FileOutputStream(
					FileDescriptor.out);

				fileOutputStream.write(_leadingLog.getBytes(StringPool.UTF8));

				fileOutputStream.flush();

				System.out.print(_bodyLog);

				System.out.flush();

				// Forcibly restore System.out. This is a necessary protection
				// for code coverage. Cobertura's collector thread will output
				// to System.out after the subprocess's main thread has exited.
				// That information will be captured by the parent unit test
				// process which will cause an assert Assert.failure.

				System.setOut(new PrintStream(fileOutputStream));
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return null;
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(7);

			Class<?> clazz = getClass();

			sb.append(clazz.getSimpleName());

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append("leadingLog=");
			sb.append(_leadingLog);
			sb.append(", bodyLog=");
			sb.append(_bodyLog);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}

		private static final long serialVersionUID = 1L;

		private final String _bodyLog;
		private final String _leadingLog;

	}

	private static class LoggingProcessCallable
		implements ProcessCallable<Serializable> {

		public LoggingProcessCallable(String logMessage, File signalFile) {
			_logMessage = logMessage;
			_signalFile = signalFile;
		}

		@Override
		public Serializable call() throws ProcessException {
			try {
				_waitForSignalFile(_signalFile, true);

				System.out.print(_logMessage);
				System.err.print(_logMessage);

				boolean result = _signalFile.delete();

				if (!result) {
					throw new ProcessException(
						"Unable to remove file " +
							_signalFile.getAbsolutePath());
				}

				_waitForSignalFile(_signalFile, true);
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return null;
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(5);

			Class<?> clazz = getClass();

			sb.append(clazz.getSimpleName());

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append("logMessage=");
			sb.append(_logMessage);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}

		private static final long serialVersionUID = 1L;

		private final String _logMessage;
		private final File _signalFile;

	}

	private static class NonprocessCallablePipingBackProcessCallable
		implements ProcessCallable<Serializable> {

		@Override
		public Serializable call() throws ProcessException {
			try {
				synchronized (System.out) {
					System.out.flush();

					OutputStream outputStream = new FileOutputStream(
						FileDescriptor.out);

					outputStream.write(
						_toHeadlessSerializationData(
							"string piping back object"));
				}
			}
			catch (IOException ioe) {
				throw new ProcessException(ioe);
			}

			return null;
		}

		private static final long serialVersionUID = 1L;

	}

	private static class NPEOOSShutdownHook implements ShutdownHook {

		public NPEOOSShutdownHook() {
			ProcessOutputStream processOutputStream =
				ProcessContext.getProcessOutputStream();

			_oldObjectOutputStream = ReflectionTestUtil.getFieldValue(
				processOutputStream, "_objectOutputStream");

			_thread = Thread.currentThread();
		}

		@Override
		public boolean shutdown(int shutdownCode, Throwable shutdownError) {
			try {
				ProcessOutputStream processOutputStream =
					ProcessContext.getProcessOutputStream();

				ReflectionTestUtil.setFieldValue(
					processOutputStream, "_objectOutputStream",
					_oldObjectOutputStream);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}

			_thread.interrupt();

			return true;
		}

		private final ObjectOutputStream _oldObjectOutputStream;
		private Thread _thread;

	}

	private static class ReadPropertyProcessCallable
		implements ProcessCallable<String> {

		public ReadPropertyProcessCallable(String propertyKey) {
			_propertyKey = propertyKey;
		}

		@Override
		public String call() {
			return System.getProperty(_propertyKey);
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(5);

			Class<?> clazz = getClass();

			sb.append(clazz.getSimpleName());

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append("propertyKey=");
			sb.append(_propertyKey);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}

		private static final long serialVersionUID = 1L;

		private final String _propertyKey;

	}

	private static class ReturnWithoutExitProcessCallable
		implements ProcessCallable<String> {

		public ReturnWithoutExitProcessCallable(String returnValue) {
			_returnValue = returnValue;
		}

		@Override
		public String call() throws ProcessException {
			try {
				ProcessOutputStream processOutputStream =
					ProcessContext.getProcessOutputStream();

				// Forcibly write a premature ReturnProcessCallable

				processOutputStream.writeProcessCallable(
					new ReturnProcessCallable<String>(_returnValue));

				_threadBlockingQueue.put(Thread.currentThread());

				Thread.sleep(Long.MAX_VALUE);
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return null;
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(5);

			Class<?> clazz = getClass();

			sb.append(clazz.getSimpleName());

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append("returnValue=");
			sb.append(_returnValue);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}

		private static final BlockingQueue<Thread> _threadBlockingQueue =
			new SynchronousQueue<>();
		private static final long serialVersionUID = 1L;

		private final String _returnValue;

	}

	private static class RuntimeExceptionProcessCallable
		implements ProcessCallable<Serializable> {

		@Override
		public Serializable call() {
			throw new RuntimeException(
				RuntimeExceptionProcessCallable.class.getName());
		}

		@Override
		public String toString() {
			Class<?> clazz = getClass();

			return clazz.getSimpleName();
		}

		private static final long serialVersionUID = 1L;

	}

	private static class ServerThread extends Thread {

		public static void exit(Socket socket) throws Exception {
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			outputStream.write(_CODE_EXIT);

			socket.shutdownOutput();

			int code = inputStream.read();

			Assert.assertEquals(-1, code);

			socket.close();
		}

		public static void interruptHeartbeatThread(Socket socket)
			throws Exception {

			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			outputStream.write(_CODE_INTERRUPT);

			socket.shutdownOutput();

			int code = inputStream.read();

			Assert.assertEquals(-1, code);

			socket.close();
		}

		public static boolean isAlive(Socket socket) {
			try {
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();

				outputStream.write(_CODE_ECHO);

				outputStream.flush();

				if (inputStream.read() == _CODE_ECHO) {
					return true;
				}
				else {
					return false;
				}
			}
			catch (Exception e) {
				return false;
			}
		}

		public static void nullOutOOS(Socket socket) throws Exception {
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			outputStream.write(_CODE_NULL_OUT_OOS);
			outputStream.flush();

			int code = inputStream.read();

			Assert.assertEquals(
				"Unable to null out OOS because of code " + code,
				_CODE_NULL_OUT_OOS, code);
		}

		public ServerThread(Thread mainThread, String name, int serverPort)
			throws Exception {

			_mainThread = mainThread;
			_socket = new Socket(
				InetAddressUtil.getLoopbackInetAddress(), serverPort);

			setName(name);
		}

		@Override
		public void run() {
			try {
				InputStream inputStream = _socket.getInputStream();
				OutputStream outputStream = _socket.getOutputStream();

				int command = 0;

				while (((command = inputStream.read()) != -1) &&
					   _mainThread.isAlive()) {

					switch (command) {
						case _CODE_ECHO :
							outputStream.write(_CODE_ECHO);

							outputStream.flush();

							break;

						case _CODE_EXIT :

							break;

						case _CODE_INTERRUPT :
							Thread heartbeatThread = _getHeartbeatThread(false);

							heartbeatThread.interrupt();
							heartbeatThread.join();

							break;

						case _CODE_NULL_OUT_OOS :
							ReflectionTestUtil.setFieldValue(
								ProcessContext.getProcessOutputStream(),
								"_objectOutputStream", null);

							outputStream.write(_CODE_NULL_OUT_OOS);

							outputStream.flush();

							break;
					}
				}
			}
			catch (Exception e) {
			}
			finally {
				try {
					_socket.close();

					_mainThread.interrupt();
					_mainThread.join();
				}
				catch (Exception e) {
				}
			}
		}

		private static final int _CODE_ECHO = 1;

		private static final int _CODE_EXIT = 2;

		private static final int _CODE_INTERRUPT = 3;

		private static final int _CODE_NULL_OUT_OOS = 4;

		private final Thread _mainThread;
		private final Socket _socket;

	}

	private static class TestShutdownHook implements ShutdownHook {

		public TestShutdownHook() {
			this(false);
		}

		public TestShutdownHook(boolean failToShutdown) {
			_failToShutdown = failToShutdown;
			_thread = Thread.currentThread();
		}

		@Override
		public boolean shutdown(int shutdownCode, Throwable shutdownThrowable) {
			_thread.interrupt();

			if (_failToShutdown) {
				throw new RuntimeException();
			}

			return true;
		}

		private final boolean _failToShutdown;
		private Thread _thread;

	}

	private static class UnserializablePipingBackProcessCallable
		implements ProcessCallable<Serializable> {

		@Override
		public Serializable call() throws ProcessException {
			ProcessOutputStream processOutputStream =
				ProcessContext.getProcessOutputStream();

			try {
				processOutputStream.writeProcessCallable(
					new UnserializableProcessCallable());
			}
			catch (IOException ioe) {
				throw new ProcessException(ioe);
			}

			return null;
		}

		private static final long serialVersionUID = 1L;

	}

	private static class UnserializableProcessCallable
		implements ProcessCallable<Serializable> {

		@Override
		public Serializable call() {
			return UnserializableProcessCallable.class.getName();
		}

		@Override
		public String toString() {
			Class<?> clazz = getClass();

			return clazz.getSimpleName();
		}

		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unused")
		private Object _unserializableObject = new Object();

	}

}