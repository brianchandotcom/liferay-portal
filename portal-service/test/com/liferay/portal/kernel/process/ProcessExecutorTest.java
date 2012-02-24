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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogWrapper;
import com.liferay.portal.kernel.process.ProcessExecutor.ProcessContext;
import com.liferay.portal.kernel.process.ProcessExecutor.ShutdownHook;
import com.liferay.portal.kernel.process.log.ProcessOutputStream;
import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;

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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
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
public class ProcessExecutorTest extends TestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		Class<?> clazz = getClass();

		PortalClassLoaderUtil.setClassLoader(clazz.getClassLoader());
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		PortalClassLoaderUtil.setClassLoader(null);
	}

	public void testAttach1() throws Exception {

		// No attach

		ServerSocket serverSocket = _createServerSocket(12342);

		try {
			int port = serverSocket.getLocalPort();

			ProcessExecutor.execute(
				_classPath, _createArguments(),
				new AttachParentProcessCallable(
					AttachChildProcessCallable1.class.getName(), port));

			Socket parentSocket = serverSocket.accept();

			assertTrue(ServerThread.isAlive(parentSocket));

			Socket childSocket = serverSocket.accept();

			assertTrue(ServerThread.isAlive(childSocket));

			// Kill parent

			ServerThread.exit(parentSocket);

			assertFalse(ServerThread.isAlive(parentSocket));

			// Test alive 10 times for child process

			for (int i = 0; i < 10; i++) {
				Thread.sleep(100);

				assertTrue(ServerThread.isAlive(childSocket));
			}

			// Kill child

			ServerThread.exit(childSocket);

			assertFalse(ServerThread.isAlive(childSocket));
		}
		finally {
			serverSocket.close();
		}
	}

	public void testAttach2() throws Exception {

		// Attach

		ServerSocket serverSocket = _createServerSocket(12342);

		try {
			int port = serverSocket.getLocalPort();

			ProcessExecutor.execute(
				_classPath, _createArguments(),
				new AttachParentProcessCallable(
					AttachChildProcessCallable2.class.getName(), port));

			Socket parentSocket = serverSocket.accept();

			assertTrue(ServerThread.isAlive(parentSocket));

			Socket childSocket = serverSocket.accept();

			assertTrue(ServerThread.isAlive(childSocket));

			// Kill parent

			ServerThread.exit(parentSocket);

			assertFalse(ServerThread.isAlive(parentSocket));

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

	public void testAttach3() throws Exception {

		// Detach

		ServerSocket serverSocket = _createServerSocket(12342);

		try {
			int port = serverSocket.getLocalPort();

			ProcessExecutor.execute(
				_classPath, _createArguments(),
				new AttachParentProcessCallable(
					AttachChildProcessCallable3.class.getName(), port));

			Socket parentSocket = serverSocket.accept();

			assertTrue(ServerThread.isAlive(parentSocket));

			Socket childSocket = serverSocket.accept();

			assertTrue(ServerThread.isAlive(childSocket));

			// Kill parent

			ServerThread.exit(parentSocket);

			assertFalse(ServerThread.isAlive(parentSocket));

			_log.info("Waiting subprocess to exit...");

			long startTime = System.currentTimeMillis();

			while (true) {
				Thread.sleep(10);

				if (!ServerThread.isAlive(childSocket)) {

					_log.info("Subprocess exited. Waited " +
						(System.currentTimeMillis() - startTime) + " ms");

					return;
				}
			}
		}
		finally {
			serverSocket.close();
		}
	}

	public void testAttach4() throws Exception {

		// Shutdown by interruption

		ServerSocket serverSocket = _createServerSocket(12342);

		try {
			int port = serverSocket.getLocalPort();

			ProcessExecutor.execute(
				_classPath, _createArguments(),
				new AttachParentProcessCallable(
					AttachChildProcessCallable4.class.getName(), port));

			Socket parentSocket = serverSocket.accept();

			assertTrue(ServerThread.isAlive(parentSocket));

			Socket childSocket = serverSocket.accept();

			assertTrue(ServerThread.isAlive(childSocket));

			// Interrupt child process heartbeat thread

			ServerThread.interruptHeartbeatThread(childSocket);

			assertFalse(ServerThread.isAlive(childSocket));

			// Kill parent

			ServerThread.exit(parentSocket);

			assertFalse(ServerThread.isAlive(parentSocket));
		}
		finally {
			serverSocket.close();
		}
	}

	public void testAttach5() throws Exception {

		// Bad shutdown hook

		ServerSocket serverSocket = _createServerSocket(12342);

		try {
			int port = serverSocket.getLocalPort();

			ProcessExecutor.execute(
				_classPath, _createArguments(),
				new AttachParentProcessCallable(
					AttachChildProcessCallable5.class.getName(), port));

			Socket parentSocket = serverSocket.accept();

			assertTrue(ServerThread.isAlive(parentSocket));

			Socket childSocket = serverSocket.accept();

			assertTrue(ServerThread.isAlive(childSocket));

			// Interrupt child process heartbeat thread

			ServerThread.interruptHeartbeatThread(childSocket);

			assertFalse(ServerThread.isAlive(childSocket));

			// Kill parent

			ServerThread.exit(parentSocket);

			assertFalse(ServerThread.isAlive(parentSocket));
		}
		finally {
			serverSocket.close();
		}
	}

	public void testAttach6() throws Exception {

		// NPE on heartbeat piping back

		ServerSocket serverSocket = _createServerSocket(12342);

		try {
			int port = serverSocket.getLocalPort();

			ProcessExecutor.execute(
				_classPath, _createArguments(),
				new AttachParentProcessCallable(
					AttachChildProcessCallable6.class.getName(), port));

			Socket parentSocket = serverSocket.accept();

			assertTrue(ServerThread.isAlive(parentSocket));

			Socket childSocket = serverSocket.accept();

			assertTrue(ServerThread.isAlive(childSocket));

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

			assertFalse(ServerThread.isAlive(parentSocket));
		}
		finally {
			serverSocket.close();
		}
	}

	public void testCancel() throws Exception {
		ReturnWithoutExitProcessCallable returnWithoutExitProcessCallable =
			new ReturnWithoutExitProcessCallable("");

		Future<String> future = ProcessExecutor.execute(
			_classPath, returnWithoutExitProcessCallable);

		assertFalse(future.isCancelled());
		assertFalse(future.isDone());

		assertTrue(future.cancel(true));

		try {
			future.get();

			fail();
		}
		catch (CancellationException ce) {
		}

		assertTrue(future.isCancelled());
		assertTrue(future.isDone());
		assertFalse(future.cancel(true));
	}

	public void testConcurrentCreateExecutorService() throws Exception {
		_nullOutExecutorService();

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

		synchronized (ProcessExecutor.class) {
			thread.start();

			while (thread.getState() != Thread.State.BLOCKED);

			executorService = _invokeGetExecutorService();
		}

		thread.join();

		assertSame(executorService, atomicReference.get());
	}

	public void testCrash() throws Exception {
		Logger logger = _getLogger();

		Level level = logger.getLevel();

		try {
			logger.setLevel(Level.OFF);

			// Negative one crash

			KillJVMProcessCallable killJVMProcessCallable =
				new KillJVMProcessCallable(-1);

			Future<Serializable> future = ProcessExecutor.execute(
				_classPath, killJVMProcessCallable);

			try {
				future.get();

				fail();
			}
			catch (ExecutionException ee) {
				assertFalse(future.isCancelled());
				assertTrue(future.isDone());

				Throwable throwable = ee.getCause();

				assertTrue(throwable instanceof ProcessException);
			}

			// Zero crash

			killJVMProcessCallable = new KillJVMProcessCallable(0);

			future = ProcessExecutor.execute(
				_classPath, killJVMProcessCallable);

			try {
				future.get();

				fail();
			}
			catch (ExecutionException ee) {
				assertFalse(future.isCancelled());
				assertTrue(future.isDone());

				Throwable throwable = ee.getCause();

				assertTrue(throwable instanceof ProcessException);

				throwable = throwable.getCause();

				assertTrue(throwable instanceof EOFException);
			}
		}
		finally {
			logger.setLevel(level);
		}
	}

	public void testCreateProcessContext() throws Exception {

		// Useless test to satisfy Cobertura

		Constructor<ProcessContext> constructor =
			ProcessContext.class.getDeclaredConstructor();

		constructor.setAccessible(true);

		constructor.newInstance();
	}

	public void testDestroy() throws Exception {

		// Clean destroy

		ProcessExecutor processExecutor = new ProcessExecutor();

		processExecutor.destroy();

		assertNull(_getExecutorService());

		// Idle destroy

		ExecutorService executorService = _invokeGetExecutorService();

		assertNotNull(executorService);
		assertNotNull(_getExecutorService());

		processExecutor.destroy();

		assertNull(_getExecutorService());

		// Busy destroy

		executorService = _invokeGetExecutorService();

		assertNotNull(executorService);
		assertNotNull(_getExecutorService());

		DummyJob dummyJob = new DummyJob();

		Future<Void> future = executorService.submit(dummyJob);

		dummyJob.waitUntilStarted();

		processExecutor.destroy();

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

		assertNull(_getExecutorService());
	}

	public void testException() throws Exception {
		DummyExceptionProcessCallable dummyExceptionProcessCallable =
			new DummyExceptionProcessCallable();

		Future<Serializable> future = ProcessExecutor.execute(
			_classPath, _createArguments(), dummyExceptionProcessCallable);

		try {
			future.get();

			fail();
		}
		catch (ExecutionException ee) {
			assertFalse(future.isCancelled());
			assertTrue(future.isDone());

			Throwable throwable = ee.getCause();

			assertEquals(
				DummyExceptionProcessCallable.class.getName(),
				throwable.getMessage());
		}
	}

	public void testGetWithTimeout() throws Exception {

		// Success return

		DummyReturnProcessCallable dummyReturnProcessCallable =
			new DummyReturnProcessCallable();

		Future<String> future = ProcessExecutor.execute(
			_classPath, dummyReturnProcessCallable);

		String returnValue = future.get(100, TimeUnit.SECONDS);

		assertEquals(DummyReturnProcessCallable.class.getName(), returnValue);
		assertFalse(future.isCancelled());
		assertTrue(future.isDone());

		// Timeout return

		ReturnWithoutExitProcessCallable returnWithoutExitProcessCallable =
			new ReturnWithoutExitProcessCallable("");

		future = ProcessExecutor.execute(
			_classPath, returnWithoutExitProcessCallable);

		try {
			future.get(1, TimeUnit.SECONDS);

			fail();
		}
		catch (TimeoutException te) {
		}

		assertFalse(future.isCancelled());
		assertFalse(future.isDone());

		ExecutorService executorService = _getExecutorService();

		executorService.shutdownNow();

		executorService.awaitTermination(10, TimeUnit.SECONDS);

		assertFalse(future.isCancelled());
		assertTrue(future.isDone());

		_nullOutExecutorService();
	}

	public void testLeadingLog() throws Exception {

		// Warn level

		String leadingLog = "Test leading log.\n";
		String bodyLog = "Test body log.\n";

		Logger logger = _getLogger();

		Level level = logger.getLevel();

		logger.setLevel(Level.WARNING);

		CaptureHandler captureHandler = new CaptureHandler();

		logger.addHandler(captureHandler);

		try {
			LeadingLogProcessCallable leadingLogProcessCallable =
				new LeadingLogProcessCallable(leadingLog, bodyLog);

			List<String> arguments = _createArguments();

			Future<String> future = ProcessExecutor.execute(
				_classPath, arguments, leadingLogProcessCallable);

			future.get();

			assertFalse(future.isCancelled());
			assertTrue(future.isDone());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			assertEquals(
				"Found corrupt leading log " + leadingLog,
				logRecord.getMessage());
		}
		finally {
			logger.removeHandler(captureHandler);

			logger.setLevel(level);
		}

		// Fine level

		logger.setLevel(Level.FINE);

		captureHandler = new CaptureHandler();

		logger.addHandler(captureHandler);

		try {
			LeadingLogProcessCallable leadingLogProcessCallable =
				new LeadingLogProcessCallable(leadingLog, bodyLog);

			List<String> arguments = _createArguments();

			Future<String> future = ProcessExecutor.execute(
				_classPath, arguments, leadingLogProcessCallable);

			future.get();

			assertFalse(future.isCancelled());
			assertTrue(future.isDone());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			assertEquals(2, logRecords.size());

			LogRecord logRecord1 = logRecords.get(0);

			assertEquals(
				"Found corrupt leading log " + leadingLog,
				logRecord1.getMessage());

			LogRecord logRecord2 = logRecords.get(1);

			String message = logRecord2.getMessage();

			assertTrue(message.contains("Invoked generic process callable "));
		}
		finally {
			logger.removeHandler(captureHandler);

			logger.setLevel(level);
		}

		// Severe level

		logger.setLevel(Level.SEVERE);

		captureHandler = new CaptureHandler();

		logger.addHandler(captureHandler);

		try {
			LeadingLogProcessCallable leadingLogProcessCallable =
				new LeadingLogProcessCallable(leadingLog, bodyLog);

			List<String> arguments = _createArguments();

			Future<String> future = ProcessExecutor.execute(
				_classPath, arguments, leadingLogProcessCallable);

			future.get();

			assertFalse(future.isCancelled());
			assertTrue(future.isDone());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			assertEquals(0, logRecords.size());
		}
		finally {
			logger.removeHandler(captureHandler);

			logger.setLevel(level);
		}
	}

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

						assertFalse(future.isCancelled());
						assertTrue(future.isDone());
					}
					catch (Exception e) {
						exceptionAtomicReference.set(e);
					}
				}

			};

			thread.start();

			assertTrue(signalFile.createNewFile());

			_waitForSignalFile(signalFile, false);

			String outByteArrayOutputStreamString =
				outByteArrayOutputStream.toString();

			assertTrue(outByteArrayOutputStreamString.contains(logMessage));

			String errByteArrayOutputStreamString =
				errByteArrayOutputStream.toString();

			assertTrue(errByteArrayOutputStreamString.contains(logMessage));
			assertTrue(signalFile.createNewFile());

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

	public void testPropertyPassing() throws Exception {
		String propertyKey = "test-key";
		String propertyValue = "test-value";

		ReadPropertyProcessCallable readPropertyProcessCallable =
			new ReadPropertyProcessCallable(propertyKey);

		List<String> arguments = _createArguments();

		arguments.add("-D" + propertyKey + "=" + propertyValue);

		Future<String> future = ProcessExecutor.execute(
			_classPath, arguments, readPropertyProcessCallable);

		assertEquals(propertyValue, future.get());
		assertFalse(future.isCancelled());
		assertTrue(future.isDone());
	}

	public void testReturn() throws Exception {
		DummyReturnProcessCallable dummyReturnProcessCallable =
			new DummyReturnProcessCallable();

		Future<String> future = ProcessExecutor.execute(
			_classPath, dummyReturnProcessCallable);

		assertEquals(DummyReturnProcessCallable.class.getName(), future.get());
		assertFalse(future.isCancelled());
		assertTrue(future.isDone());
		assertFalse(future.cancel(true));
	}

	public void testReturnWithoutExit() throws Exception {
		ReturnWithoutExitProcessCallable returnWithoutExitProcessCallable =
			new ReturnWithoutExitProcessCallable("Premature return value");

		ProcessExecutor processExecutor = new ProcessExecutor();

		_nullOutExecutorService();

		Future<String> future = ProcessExecutor.execute(
			_classPath, returnWithoutExitProcessCallable);

		ThreadPoolExecutor threadPoolExecutor =
			(ThreadPoolExecutor)_getExecutorService();

		Field workersField = ReflectionUtil.getDeclaredField(
			ThreadPoolExecutor.class, "workers");

		Set<?> workers = (Set<?>)workersField.get(threadPoolExecutor);

		assertEquals(1, workers.size());

		Object worker = workers.iterator().next();

		Field threadField = ReflectionUtil.getDeclaredField(
			worker.getClass(), "thread");

		Thread thread = (Thread)threadField.get(worker);

		Logger logger = _getLogger();

		if (OSDetector.isWindows()) {

			// Wait 10 seconds for the thread to be in a waiting state

			logger.log(
				Level.WARNING,
				"Windows does not properly update thread states. This test " +
					"may fail on slow machines.");

			Thread.sleep(10000);
		}
		else {
			while (thread.getState() != Thread.State.WAITING);
		}

		Level level = logger.getLevel();

		logger.setLevel(Level.OFF);

		try {
			processExecutor.destroy();

			try {
				future.get();

				fail();
			}
			catch (ExecutionException ee) {
				assertFalse(future.isCancelled());
				assertTrue(future.isDone());

				Throwable throwable = ee.getCause();

				assertTrue(throwable instanceof ProcessException);

				throwable = throwable.getCause();

				assertTrue(throwable instanceof InterruptedException);
			}
		}
		finally {
			logger.setLevel(level);
		}
	}

	public void testUnserializableProcessCallable() {
		UnserializableProcessCallable unserializableProcessCallable =
			new UnserializableProcessCallable();

		try {
			ProcessExecutor.execute(_classPath, unserializableProcessCallable);

			fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			assertTrue(throwable instanceof NotSerializableException);
		}
	}

	public void testWrongJavaExecutable() {
		DummyReturnProcessCallable dummyReturnProcessCallable =
			new DummyReturnProcessCallable();

		try {
			ProcessExecutor.execute(
				"javax", _classPath, Collections.<String>emptyList(),
				dummyReturnProcessCallable);

			fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			assertTrue(throwable instanceof IOException);
		}
	}

	private static List<String> _createArguments() {
		List<String> arguments = new ArrayList<String>();

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

	private static Logger _getLogger() throws Exception {
		LogWrapper loggerWrapper = (LogWrapper)LogFactoryUtil.getLog(
			ProcessExecutor.class);

		Field field = ReflectionUtil.getDeclaredField(LogWrapper.class, "_log");

		Jdk14LogImpl jdk14LogImpl = (Jdk14LogImpl)field.get(loggerWrapper);

		field = ReflectionUtil.getDeclaredField(Jdk14LogImpl.class, "_log");

		return (Logger)field.get(jdk14LogImpl);
	}

	private static Field _getObjectOutputStreamField() throws Exception {
		Field objectOutputStreamField =
			ReflectionUtil.getDeclaredField(
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

	private ServerSocket _createServerSocket(int startPort) {
		int port = startPort;

		while (true) {
			try {
				ServerSocket serverSocket = new ServerSocket();

				serverSocket.setReuseAddress(true);

				serverSocket.bind(new InetSocketAddress("localhost", port));

				return serverSocket;
			}
			catch (IOException ioe) {
				port++;
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ProcessExecutorTest.class);

	private static String _classPath = System.getProperty("java.class.path");

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
					throw new ProcessException("Attach failed!");
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
					throw new ProcessException("Attach failed!");
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

	}

	private static class AttachParentProcessCallable
		implements ProcessCallable<Serializable> {

		public AttachParentProcessCallable(String className, int serverPort) {
			_className = className;
			_serverPort = serverPort;
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

				Class<ProcessCallable<?>> processCallableClass =
					(Class<ProcessCallable<?>>)Class.forName(_className);

				Constructor<ProcessCallable<?>> constructor =
					processCallableClass.getConstructor(int.class);

				ProcessExecutor.execute(
					_classPath, _createArguments(),
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

		private String _className;
		private int _serverPort;

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

	private static class DummyExceptionProcessCallable
		implements ProcessCallable<Serializable> {

		public Serializable call() throws ProcessException {
			throw new ProcessException(
				DummyExceptionProcessCallable.class.getName());
		}

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
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return null;
		}

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

		private String _returnValue;

	}

	private static class ServerThread extends Thread {

		public static void exit(Socket socket) throws Exception {
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			outputStream.write(_CODE_EXIT);
			outputStream.close();

			try {
				int code = inputStream.read();

				fail("Failed to exit subprocess with response code " + code);
			}
			catch (SocketException se) {
			}
		}

		public static void interruptHeartbeatThread(Socket socket)
			throws Exception {

			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			outputStream.write(_CODE_INTERRUPT);
			outputStream.close();

			try {
				int code = inputStream.read();

				fail("Failed to exit subprocess with response code " + code);
			}
			catch (SocketException se) {
			}
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
				fail("Failed to null out oos, response code : " + code);
			}
		}

		public ServerThread(Thread mainThread, String name, int serverPort)
			throws Exception {

			_mainThread = mainThread;
			_socket = new Socket("localhost", serverPort);

			setDaemon(true);
			setName(name);
		}

		@Override
		public void run() {
			try {
				InputStream inputStream = _socket.getInputStream();
				OutputStream outputStream = _socket.getOutputStream();

				int command = 0;

				while ((command = inputStream.read()) != -1) {
					switch (command) {
						case _CODE_ECHO :
							outputStream.write(_CODE_ECHO);

							outputStream.flush();

							break;

						case _CODE_EXIT :
							_socket.close();

							break;

						case  _CODE_INTERRUPT :
							Thread heartbeatThread = _getHeartbeatThread(false);

							heartbeatThread.interrupt();
							heartbeatThread.join();

							_socket.close();

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
				}
				catch (IOException ioe) {
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

		@SuppressWarnings("unused")
		private Object _unserializableObject = new Object();

	}

}