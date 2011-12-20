/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Shuyang Zhou
 */
public class ProcessExecutorTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		PortalClassLoaderUtil.setClassLoader(getClass().getClassLoader());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		PortalClassLoaderUtil.setClassLoader(null);
	}

	public void testCrash() throws Exception {
		// Non Zero crash
		KillJVMProcessCallable killJVMProcessCallable =
			new KillJVMProcessCallable(-1);

		try {
			ProcessExecutor.execute(killJVMProcessCallable, _classPath);
			fail();
		}
		catch (ProcessException pe) {
		}

		// Zero crash
		killJVMProcessCallable = new KillJVMProcessCallable(0);

		Serializable result = ProcessExecutor.execute(killJVMProcessCallable,
			_classPath);

		assertNull(result);
	}

	public void testDestroy() throws Exception {
		// Clean destroy

		ProcessExecutor processExecutor = new ProcessExecutor();

		processExecutor.destroy();

		assertNull(getExecutorService());

		// Idle destroy

		ExecutorService executorService = invokeGetExecutorService();

		assertNotNull(executorService);

		assertNotNull(getExecutorService());

		processExecutor.destroy();

		assertNull(getExecutorService());

		// Busy destroy

		executorService = invokeGetExecutorService();

		Future<Void> futureResult = executorService.submit(new DummyJob());

		assertNotNull(executorService);

		assertNotNull(getExecutorService());

		processExecutor.destroy();

		try {
			futureResult.get();

			fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			assertTrue(throwable instanceof InterruptedException);
		}

		assertNull(getExecutorService());
	}

	public void testException() throws Exception {
		DummyExceptionProcessCallable dummyExceptionProcessCallable =
			new DummyExceptionProcessCallable();

		try {
			ProcessExecutor.execute(dummyExceptionProcessCallable, _classPath);
			fail();
		}
		catch (ProcessException pe) {
			assertEquals(DummyExceptionProcessCallable.class.getName(),
				pe.getMessage());
		}
	}

	public void testLogging() throws Exception {
		File signalFile = new File("signal");

		signalFile.delete();

		String logMessage= "Log Message";

		PrintStream oldOut = System.out;
		PrintStream oldErr = System.err;

		ByteArrayOutputStream outBaos = new ByteArrayOutputStream();
		ByteArrayOutputStream errBaos = new ByteArrayOutputStream();

		PrintStream newOut = new PrintStream(outBaos, true);
		PrintStream newErr = new PrintStream(errBaos, true);

		System.setOut(newOut);
		System.setErr(newErr);

		try {
			final LoggingProcessCallable loggingProcessCallable =
				new LoggingProcessCallable(logMessage, signalFile);

			final AtomicReference<Exception> exceptionReference =
				new AtomicReference<Exception>();

			Thread launchThread = new Thread() {

				public void run() {
					try {
						ProcessExecutor.execute(loggingProcessCallable,
							_classPath);
					}
					catch (ProcessException pe) {
						exceptionReference.set(pe);
					}
				}
			};

			launchThread.start();

			// Notify the sub-process to log

			boolean result = signalFile.createNewFile();
			assertTrue(result);

			// Wait signalFile to be removed, indicating log is done.

			waitForSignalFile(signalFile, false);

			assertTrue(outBaos.toString().contains(logMessage));
			assertTrue(errBaos.toString().contains(logMessage));

			result = signalFile.createNewFile();
			assertTrue(result);

			launchThread.join();

			Exception e = exceptionReference.get();

			if (e != null) {
				throw e;
			}
		}
		finally {
			System.setOut(oldOut);
			System.setErr(oldErr);
			signalFile.delete();
		}
	}

	public void testReturn() throws Exception {
		DummyReturnProcessCallable dummyReturnProcessCallable =
			new DummyReturnProcessCallable();

		String result = ProcessExecutor.execute(dummyReturnProcessCallable,
			_classPath);

		assertEquals(DummyReturnProcessCallable.class.getName(), result);
	}

	private static ExecutorService getExecutorService() throws Exception {
		Field field =ProcessExecutor.class.getDeclaredField("_executorService");

		field.setAccessible(true);

		return (ExecutorService)field.get(null);
	}

	private static ExecutorService invokeGetExecutorService() throws Exception {
		Method method = ProcessExecutor.class.getDeclaredMethod(
			"_getExecutorService");

		method.setAccessible(true);

		return (ExecutorService)method.invoke(method);
	}

	private static void waitForSignalFile(File signalFile, boolean expectExist)
		throws Exception {

		while (signalFile.exists() != expectExist) {
			Thread.sleep(100);
		}
	}

	private static class DummyExceptionProcessCallable
		implements ProcessCallable<Serializable> {

		public Serializable call() throws ProcessException {
			throw new ProcessException(
				DummyExceptionProcessCallable.class.getName());
		}

	}

	private static class DummyJob implements Callable<Void> {

		public Void call() throws Exception {
			Thread.sleep(Long.MAX_VALUE);

			return null;
		}

	}

	private static class DummyReturnProcessCallable
		implements ProcessCallable<String> {

		public String call() throws ProcessException {
			return DummyReturnProcessCallable.class.getName();
		}

	}

	private static class KillJVMProcessCallable
		implements ProcessCallable<Serializable> {

		public KillJVMProcessCallable(int exitCode) {
			_exitCode = exitCode;
		}

		public Serializable call() throws ProcessException {
			System.exit(_exitCode);

			return null;
		}

		private final int _exitCode;

	}

	private static class LoggingProcessCallable
		implements ProcessCallable<Serializable> {

		public LoggingProcessCallable(String logMessage, File signalFile) {
			_logMessage = logMessage;
			_signalFile = signalFile;
		}

		public Serializable call() throws ProcessException {
			try {
				waitForSignalFile(_signalFile, true);

				System.out.print(_logMessage);
				System.err.print(_logMessage);

				boolean result = _signalFile.delete();

				if (!result) {
					throw new ProcessException("Failed to remove file : " +
						_signalFile.getAbsolutePath());
				}

				waitForSignalFile(_signalFile, true);
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return null;
		}

		private final String _logMessage;
		private final File _signalFile;
	}

	private final String _classPath = System.getProperty("java.class.path");

}