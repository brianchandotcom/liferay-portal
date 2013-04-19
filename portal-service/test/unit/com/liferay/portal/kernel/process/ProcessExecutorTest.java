/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ProcessExecutor.ProcessContext;
import com.liferay.portal.kernel.process.ProcessExecutor.ShutdownHook;
import com.liferay.portal.kernel.process.log.ProcessOutputStream;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SocketUtil.ServerSocketConfigurator;
import com.liferay.portal.kernel.util.SocketUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
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

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.nio.channels.ServerSocketChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
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
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ProcessExecutorTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() throws Exception {
		Class<?> clazz = getClass();

		PortalClassLoaderUtil.setClassLoader(clazz.getClassLoader());
	}

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
	public void testAttach1() throws Exception {

		// No attach

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				InetAddress.getLocalHost(), 12342, _serverSocketConfigurator);

		ServerSocket serverSocket = serverSocketChannel.socket();

		try {
			int port = serverSocket.getLocalPort();

			ProcessExecutor.execute(
				_classPath, _createArguments(_JPDA_OPTIONS1),
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
		finally {
			serverSocket.close();
		}
	}

	@Test
	public void testAttach2() throws Exception {

		// Attach

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				InetAddress.getLocalHost(), 12342, _serverSocketConfigurator);

		ServerSocket serverSocket = serverSocketChannel.socket();

		try {
			int port = serverSocket.getLocalPort();

			ProcessExecutor.execute(
				_classPath, _createArguments(_JPDA_OPTIONS1),
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
		finally {
			serverSocket.close();
		}
	}

	@Test
	public void testAttach3() throws Exception {

		// Detach

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				InetAddress.getLocalHost(), 12342, _serverSocketConfigurator);

		ServerSocket serverSocket = serverSocketChannel.socket();

		try {
			int port = serverSocket.getLocalPort();

			ProcessExecutor.execute(
				_classPath, _createArguments(_JPDA_OPTIONS1),
				new AttachParentProcessCallable(
					AttachChildProcessCallable3.class.getName(), port));

			Socket parentSocket = serverSocket.accept();

			Assert.assertTrue(ServerThread.isAlive(parentSocket));

			Socket childSocket = serverSocket.accept();

			Assert.assertTrue(ServerThread.isAlive(childSocket));

			// Kill parent

			ServerThread.exit(parentSocket);

			_log.info("Waiting subprocess to exit...");

			long startTime = System.currentTimeMillis();

			while (true) {
				Thread.sleep(10);

				if (!ServerThread.isAlive(childSocket)) {
					_log.info(
						"Subprocess exited. Waited " +
							(System.currentTimeMillis() - startTime) + " ms");

					return;
				}
			}
		}
		finally {
			serverSocket.close();
		}
	}

	@Test
	public void testAttach4() throws Exception {

		// Shutdown by interruption

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				InetAddress.getLocalHost(), 12342, _serverSocketConfigurator);

		ServerSocket serverSocket = serverSocketChannel.socket();

		try {
			int port = serverSocket.getLocalPort();

			ProcessExecutor.execute(
				_classPath, _createArguments(_JPDA_OPTIONS1),
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
		finally {
			serverSocket.close();
		}
	}

	@Test
	public void testAttach5() throws Exception {

		// Bad shutdown hook

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				InetAddress.getLocalHost(), 12342, _serverSocketConfigurator);

		ServerSocket serverSocket = serverSocketChannel.socket();

		try {
			int port = serverSocket.getLocalPort();

			ProcessExecutor.execute(
				_classPath, _createArguments(_JPDA_OPTIONS1),
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
		finally {
			serverSocket.close();
		}
	}

	@Test
	public void testAttach6() throws Exception {

		// NPE on heartbeat piping back

		ServerSocketChannel serverSocketChannel =
			SocketUtil.createServerSocketChannel(
				InetAddress.getLocalHost(), 12342, _serverSocketConfigurator);

		ServerSocket serverSocket = serverSocketChannel.socket();

		try {
			int port = serverSocket.getLocalPort();

			ProcessExecutor.execute(
				_classPath, _createArguments(_JPDA_OPTIONS1),
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
		finally {
			serverSocket.close();
		}
	}

	@Test
	public void testBrokenPiping() throws Exception {
		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			ProcessExecutor.class.getName(), Level.SEVERE);

		BrokenPipingProcessCallable brokenPipingProcessCallable =
			new BrokenPipingProcessCallable();

		Future<String> future = ProcessExecutor.execute(
			_classPath, brokenPipingProcessCallable);

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

		Assert.assertEquals(1, logRecords.size());

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

	@Test
	public void testCancel() throws Exception {
		ReturnWithoutExitProcessCallable returnWithoutExitProcessCallable =
			new ReturnWithoutExitProcessCallable("");

		Future<String> future = ProcessExecutor.execute(
			_classPath, returnWithoutExitProcessCallable);

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

		synchronized (ProcessExecutor.class) {
			thread.start();

			while (thread.getState() != Thread.State.BLOCKED);

			executorService = _invokeGetExecutorService();
		}

		thread.join();

		Assert.assertSame(executorService, atomicReference.get());
	}

	@Test
	public void testCrash() throws Exception {
		JDKLoggerTestUtil.configureJDKLogger(
			ProcessExecutor.class.getName(), Level.OFF);

		// Negative one crash

		KillJVMProcessCallable killJVMProcessCallable =
			new KillJVMProcessCallable(-1);

		Future<Serializable> future = ProcessExecutor.execute(
			_classPath, killJVMProcessCallable);

		try {
			future.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertFalse(future.isCancelled());
			Assert.assertTrue(future.isDone());

			Throwable throwable = ee.getCause();

			Assert.assertTrue(throwable instanceof ProcessException);
		}

		// Zero crash

		killJVMProcessCallable = new KillJVMProcessCallable(0);

		future = ProcessExecutor.execute(_classPath, killJVMProcessCallable);

		try {
			future.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertFalse(future.isCancelled());
			Assert.assertTrue(future.isDone());

			Throwable throwable = ee.getCause();

			Assert.assertTrue(throwable instanceof ProcessException);

			throwable = throwable.getCause();

			Assert.assertTrue(throwable instanceof EOFException);
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

		ProcessExecutor processExecutor = new ProcessExecutor();

		processExecutor.destroy();

		Assert.assertNull(_getExecutorService());

		// Idle destroy

		ExecutorService executorService = _invokeGetExecutorService();

		Assert.assertNotNull(executorService);
		Assert.assertNotNull(_getExecutorService());

		processExecutor.destroy();

		Assert.assertNull(_getExecutorService());

		// Busy destroy

		executorService = _invokeGetExecutorService();

		Assert.assertNotNull(executorService);
		Assert.assertNotNull(_getExecutorService());

		DummyJob dummyJob = new DummyJob();

		Future<Void> future = executorService.submit(dummyJob);

		dummyJob.waitUntilStarted();

		processExecutor.destroy();

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

		final ProcessExecutor referenceProcessExecutor = processExecutor;

		Thread thread = new Thread() {

			@Override
			public void run() {
				referenceProcessExecutor.destroy();
			}

		};

		synchronized (ProcessExecutor.class) {
			thread.start();

			while (thread.getState() != Thread.State.BLOCKED);

			processExecutor.destroy();
		}

		thread.join();

		_invokeGetExecutorService();

		processExecutor.destroy();

		// Destroy after destroyed

		processExecutor.destroy();

		Assert.assertNull(_getExecutorService());
	}

	@Test
	public void testException() throws Exception {
		DummyExceptionProcessCallable dummyExceptionProcessCallable =
			new DummyExceptionProcessCallable();

		Future<Serializable> future = ProcessExecutor.execute(
			_classPath, _createArguments(_JPDA_OPTIONS1),
			dummyExceptionProcessCallable);

		try {
			future.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertFalse(future.isCancelled());
			Assert.assertTrue(future.isDone());

			Throwable throwable = ee.getCause();

			Assert.assertEquals(
				DummyExceptionProcessCallable.class.getName(),
				throwable.getMessage());
		}
	}

	@Test
	public void testExecuteOnDestroy() throws Exception {
		ExecutorService executorService = _invokeGetExecutorService();

		executorService.shutdownNow();

		boolean result = executorService.awaitTermination(10, TimeUnit.SECONDS);

		Assert.assertTrue(result);

		DummyReturnProcessCallable dummyReturnProcessCallable =
			new DummyReturnProcessCallable();

		try {
			ProcessExecutor.execute(_classPath, dummyReturnProcessCallable);

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

		Future<String> future = ProcessExecutor.execute(
			_classPath, dummyReturnProcessCallable);

		String returnValue = future.get(100, TimeUnit.SECONDS);

		Assert.assertEquals(
			DummyReturnProcessCallable.class.getName(), returnValue);
		Assert.assertFalse(future.isCancelled());
		Assert.assertTrue(future.isDone());

		// Timeout return

		ReturnWithoutExitProcessCallable returnWithoutExitProcessCallable =
			new ReturnWithoutExitProcessCallable("");

		future = ProcessExecutor.execute(
			_classPath, returnWithoutExitProcessCallable);

		try {
			future.get(1, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (TimeoutException te) {
		}

		Assert.assertFalse(future.isCancelled());
		Assert.assertFalse(future.isDone());

		future.cancel(true);

		ExecutorService executorService = _getExecutorService();

		executorService.shutdownNow();

		executorService.awaitTermination(10, TimeUnit.SECONDS);

		Assert.assertTrue(future.isCancelled());
		Assert.assertTrue(future.isDone());
	}

	@Test
	public void testLeadingLog() throws Exception {

		// Warn level

		String leadingLog = "Test leading log.\n";
		String bodyLog = "Test body log.\n";

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			ProcessExecutor.class.getName(), Level.WARNING);

		LeadingLogProcessCallable leadingLogProcessCallable =
			new LeadingLogProcessCallable(leadingLog, bodyLog);

		List<String> arguments = _createArguments(_JPDA_OPTIONS1);

		Future<String> future = ProcessExecutor.execute(
			_classPath, arguments, leadingLogProcessCallable);

		future.get();

		Assert.assertFalse(future.isCancelled());
		Assert.assertTrue(future.isDone());

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Found corrupt leading log " + leadingLog, logRecord.getMessage());

		// Fine level

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			ProcessExecutor.class.getName(), Level.FINE);

		leadingLogProcessCallable = new LeadingLogProcessCallable(
			leadingLog, bodyLog);

		arguments = _createArguments(_JPDA_OPTIONS1);

		future = ProcessExecutor.execute(
			_classPath, arguments, leadingLogProcessCallable);

		future.get();

		Assert.assertFalse(future.isCancelled());
		Assert.assertTrue(future.isDone());

		Assert.assertEquals(2, logRecords.size());

		LogRecord logRecord1 = logRecords.get(0);

		Assert.assertEquals(
			"Found corrupt leading log " + leadingLog, logRecord1.getMessage());

		LogRecord logRecord2 = logRecords.get(1);

		String message = logRecord2.getMessage();

		Assert.assertTrue(message.contains("Invoked generic process callable"));

		// Severe level

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			ProcessExecutor.class.getName(), Level.SEVERE);

		leadingLogProcessCallable = new LeadingLogProcessCallable(
			leadingLog, bodyLog);

		arguments = _createArguments(_JPDA_OPTIONS1);

		future = ProcessExecutor.execute(
			_classPath, arguments, leadingLogProcessCallable);

		future.get();

		Assert.assertFalse(future.isCancelled());
		Assert.assertTrue(future.isDone());

		Assert.assertEquals(0, logRecords.size());
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
			String logMessage= "Log Message";

			final LoggingProcessCallable loggingProcessCallable =
				new LoggingProcessCallable(logMessage, signalFile);

			final AtomicReference<Exception> exceptionAtomicReference =
				new AtomicReference<Exception>();

			Thread thread = new Thread() {

				@Override
				public void run() {
					try {
						Future<Serializable> future = ProcessExecutor.execute(
							_classPath, loggingProcessCallable);

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

			String outByteArrayOutputStreamString =
				outByteArrayOutputStream.toString();

			Assert.assertTrue(
				outByteArrayOutputStreamString.contains(logMessage));

			String errByteArrayOutputStreamString =
				errByteArrayOutputStream.toString();

			Assert.assertTrue(
				errByteArrayOutputStreamString.contains(logMessage));
			Assert.assertTrue(signalFile.createNewFile());

			thread.join();

			Exception e = exceptionAtomicReference.get();

			if (e != null) {
				throw e;
			}
		}
		finally {
			System.setOut(oldOutPrintStream);
			System.setErr(oldErrPrintStream);

			signalFile.delete();
		}
	}

	@Test
	public void testPropertyPassing() throws Exception {
		String propertyKey = "test-key";
		String propertyValue = "test-value";

		ReadPropertyProcessCallable readPropertyProcessCallable =
			new ReadPropertyProcessCallable(propertyKey);

		List<String> arguments = _createArguments(_JPDA_OPTIONS1);

		arguments.add("-D" + propertyKey + "=" + propertyValue);

		Future<String> future = ProcessExecutor.execute(
			_classPath, arguments, readPropertyProcessCallable);

		Assert.assertEquals(propertyValue, future.get());
		Assert.assertFalse(future.isCancelled());
		Assert.assertTrue(future.isDone());
	}

	@Test
	public void testReturn() throws Exception {
		DummyReturnProcessCallable dummyReturnProcessCallable =
			new DummyReturnProcessCallable();

		Future<String> future = ProcessExecutor.execute(
			_classPath, dummyReturnProcessCallable);

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

		Future<String> future = ProcessExecutor.execute(
			_classPath, returnWithoutExitProcessCallable);

		for (int i = 0; i < 10; i++) {
			try {
				future.get(1, TimeUnit.SECONDS);

				Assert.fail();
			}
			catch (TimeoutException te) {
			}
		}

		JDKLoggerTestUtil.configureJDKLogger(
			ProcessExecutor.class.getName(), Level.OFF);

		ProcessExecutor processExecutor = new ProcessExecutor();

		processExecutor.destroy();

		try {
			future.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertFalse(future.isCancelled());
			Assert.assertTrue(future.isDone());

			Throwable throwable = ee.getCause();

			Assert.assertTrue(throwable instanceof ProcessException);
		}
	}

	@Test
	public void testUnserializableProcessCallable() {
		UnserializableProcessCallable unserializableProcessCallable =
			new UnserializableProcessCallable();

		try {
			ProcessExecutor.execute(_classPath, unserializableProcessCallable);

			Assert.fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			Assert.assertTrue(throwable instanceof NotSerializableException);
		}
	}

	@Test
	public void testWrongJavaExecutable() {
		DummyReturnProcessCallable dummyReturnProcessCallable =
			new DummyReturnProcessCallable();

		try {
			ProcessExecutor.execute(
				"javax", _classPath, Collections.<String>emptyList(),
				dummyReturnProcessCallable);

			Assert.fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			Assert.assertTrue(throwable instanceof IOException);
		}
	}

	private static List<String> _createArguments(String jpdaOptions) {
		List<String> arguments = new ArrayList<String>();

		boolean coberturaParentDynamicallyInstrumented = Boolean.getBoolean(
			"cobertura.parent.dynamically.instrumented");

		if (coberturaParentDynamicallyInstrumented) {
			arguments.add("-Dcobertura.parent.dynamically.instrumented=true");
		}

		String agentLine = System.getProperty("junit.cobertura.agent");

		if (Validator.isNotNull(agentLine)) {
			arguments.add(agentLine);
			arguments.add("-Djunit.cobertura.agent=" + agentLine);
		}

		boolean junitCodeCoverage = Boolean.getBoolean("junit.code.coverage");

		if (junitCodeCoverage) {
			arguments.add("-Djunit.code.coverage=true");
		}

		boolean junitDebug = Boolean.getBoolean("junit.debug");

		if (junitDebug) {
			arguments.add(jpdaOptions);
			arguments.add("-Djunit.debug=true");
		}

		String fileName = System.getProperty(
			"net.sourceforge.cobertura.datafile");

		if (fileName != null) {
			arguments.add("-Dnet.sourceforge.cobertura.datafile=" + fileName);
		}

		return arguments;
	}

	private static ExecutorService _getExecutorService() throws Exception {
		Field field = ProcessExecutor.class.getDeclaredField(
			"_executorService");

		field.setAccessible(true);

		return (ExecutorService)field.get(null);
	}

	private static Thread _getHeartbeatThread(boolean remove) throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			ProcessContext.class, "_heartbeatThreadReference");

		AtomicReference<? extends Thread> heartbeatThreadReference =
			(AtomicReference<? extends Thread>)field.get(null);

		if (remove) {
			return heartbeatThreadReference.getAndSet(null);
		}
		else {
			return heartbeatThreadReference.get();
		}
	}

	private static Field _getObjectOutputStreamField() throws Exception {
		Field objectOutputStreamField = ReflectionUtil.getDeclaredField(
			ProcessOutputStream.class, "_objectOutputStream");

		int modifiers = objectOutputStreamField.getModifiers();

		Field modifiersField = ReflectionUtil.getDeclaredField(
			Field.class, "modifiers");

		modifiersField.setInt(
			objectOutputStreamField, modifiers & ~Modifier.FINAL);

		return objectOutputStreamField;
	}

	private static ExecutorService _invokeGetExecutorService()
		throws Exception {

		Method method = ProcessExecutor.class.getDeclaredMethod(
			"_getExecutorService");

		method.setAccessible(true);

		return (ExecutorService)method.invoke(method);
	}

	private static void _nullOutExecutorService() throws Exception {
		Field field = ProcessExecutor.class.getDeclaredField(
			"_executorService");

		field.setAccessible(true);

		field.set(null, null);
	}

	private static void _setDetachField(Thread heartbeatThread, boolean detach)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			heartbeatThread.getClass(), "_detach");

		field.set(heartbeatThread, detach);
	}

	private static void _waitForSignalFile(
			File signalFile, boolean expectedExists)
		throws Exception {

		while (expectedExists != signalFile.exists()) {
			Thread.sleep(100);
		}
	}

	private static final String _JPDA_OPTIONS1 =
		"-agentlib:jdwp=transport=dt_socket,address=8001,server=y,suspend=y";

	private static final String _JPDA_OPTIONS2 =
		"-agentlib:jdwp=transport=dt_socket,address=8002,server=y,suspend=y";

	private static Log _log = LogFactoryUtil.getLog(ProcessExecutorTest.class);

	private static String _classPath = System.getProperty("java.class.path");

	private static ServerSocketConfigurator _serverSocketConfigurator =
		new ServerSocketConfigurator() {

		public void configure(ServerSocket serverSocket)
			throws SocketException {

			serverSocket.setReuseAddress(true);
		}

	};

	private static class AttachChildProcessCallable1
		implements ProcessCallable<Serializable> {

		public AttachChildProcessCallable1(int serverPort) {
			_serverPort = serverPort;
		}

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

				_setDetachField(heartbeatThread, true);

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

		public Serializable call() throws ProcessException {
			Class<?> clazz = getClass();

			PortalClassLoaderUtil.setClassLoader(clazz.getClassLoader());

			Logger logger = Logger.getLogger("");

			logger.setLevel(Level.FINE);

			try {
				ServerThread serverThread = new ServerThread(
					Thread.currentThread(), "Parent Server Thread",
					_serverPort);

				serverThread.start();

				Constructor<ProcessCallable<?>> constructor =
					_processCallableClass.getConstructor(int.class);

				ProcessExecutor.execute(
					_classPath, _createArguments(_JPDA_OPTIONS2),
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

		private Class<ProcessCallable<?>> _processCallableClass;
		private int _serverPort;

	}

	private static class BrokenPipingProcessCallable
		implements ProcessCallable<Serializable> {

		public BrokenPipingProcessCallable() throws IOException {
			DummyReturnProcessCallable dummyReturnProcessCallable =
				new DummyReturnProcessCallable();

			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				unsyncByteArrayOutputStream);

			objectOutputStream.writeObject(dummyReturnProcessCallable);

			objectOutputStream.close();

			byte[] serializedData = unsyncByteArrayOutputStream.toByteArray();

			serializedData[5] = (byte) (serializedData[5] + 1);

			_brokenPipingData = serializedData;
		}

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

	private static class DummyReturnProcessCallable
		implements ProcessCallable<String> {

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

	private static class KillJVMProcessCallable
		implements ProcessCallable<Serializable> {

		public KillJVMProcessCallable(int exitCode) {
			_exitCode = exitCode;
		}

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

		private int _exitCode;

	}

	private static class LeadingLogProcessCallable
		implements ProcessCallable<Serializable> {

		public LeadingLogProcessCallable(String leadingLog, String bodyLog) {
			_leadingLog = leadingLog;
			_bodyLog = bodyLog;
		}

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

		private String _bodyLog;
		private String _leadingLog;

	}

	private static class LoggingProcessCallable
		implements ProcessCallable<Serializable> {

		public LoggingProcessCallable(String logMessage, File signalFile) {
			_logMessage = logMessage;
			_signalFile = signalFile;
		}

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

		private String _logMessage;
		private File _signalFile;

	}

	private static class NPEOOSShutdownHook implements ShutdownHook {

		public NPEOOSShutdownHook() throws Exception {
			ProcessOutputStream processOutputStream =
				ProcessContext.getProcessOutputStream();

			Field objectOutputStreamField = _getObjectOutputStreamField();

			_oldObjectOutputStream =
				(ObjectOutputStream)objectOutputStreamField.get(
					processOutputStream);

			_thread = Thread.currentThread();
		}

		public boolean shutdown(int shutdownCode, Throwable shutdownError) {
			try {
				ProcessOutputStream processOutputStream =
					ProcessContext.getProcessOutputStream();

				Field objectOutputStreamField = _getObjectOutputStreamField();

				objectOutputStreamField.set(
					processOutputStream, _oldObjectOutputStream);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}

			_thread.interrupt();

			return true;
		}

		private ObjectOutputStream _oldObjectOutputStream;
		private Thread _thread;

	}

	private static class ReadPropertyProcessCallable
		implements ProcessCallable<String> {

		public ReadPropertyProcessCallable(String propertyKey) {
			_propertyKey = propertyKey;
		}

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

		private String _propertyKey;

	}

	private static class ReturnWithoutExitProcessCallable
		implements ProcessCallable<String> {

		public ReturnWithoutExitProcessCallable(String returnValue) {
			_returnValue = returnValue;
		}

		public String call() throws ProcessException {
			try {
				ProcessOutputStream processOutputStream =
					ProcessContext.getProcessOutputStream();

				// Forcibly write a premature ReturnProcessCallable

				processOutputStream.writeProcessCallable(
					new ReturnProcessCallable<String>(_returnValue));

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

		private static final long serialVersionUID = 1L;

		private String _returnValue;

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

			if (code != _CODE_NULL_OUT_OOS) {
				Assert.fail("Unable to null out OOS because of code " + code);
			}
		}

		public ServerThread(Thread mainThread, String name, int serverPort)
			throws Exception {

			_mainThread = mainThread;
			_socket = new Socket(InetAddress.getLocalHost(), serverPort);

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

						case  _CODE_INTERRUPT :
							Thread heartbeatThread = _getHeartbeatThread(false);

							heartbeatThread.interrupt();
							heartbeatThread.join();

							break;

						case _CODE_NULL_OUT_OOS :
							Field objectOutputStreamField =
								_getObjectOutputStreamField();

							ProcessOutputStream processOutputStream =
								ProcessContext.getProcessOutputStream();

							objectOutputStreamField.set(
								processOutputStream, null);

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

		private Thread _mainThread;
		private Socket _socket;

	}

	private static class TestShutdownHook implements ShutdownHook {

		public TestShutdownHook() {
			this(false);
		}

		public TestShutdownHook(boolean failToShutdown) {
			_failToShutdown = failToShutdown;
			_thread = Thread.currentThread();
		}

		public boolean shutdown(int shutdownCode, Throwable shutdownThrowable) {
			_thread.interrupt();

			if (_failToShutdown) {
				throw new RuntimeException();
			}

			return true;
		}

		private boolean _failToShutdown;
		private Thread _thread;

	}

	private static class UnserializableProcessCallable
		implements ProcessCallable<Serializable> {

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