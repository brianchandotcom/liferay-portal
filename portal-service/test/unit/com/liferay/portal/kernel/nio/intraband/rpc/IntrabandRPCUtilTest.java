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

package com.liferay.portal.kernel.nio.intraband.rpc;

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.nio.intraband.CompletionHandler;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.DatagramHelper;
import com.liferay.portal.kernel.nio.intraband.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.nio.intraband.rpc.IntrabandRPCUtil.FutureCompletionHandler;
import com.liferay.portal.kernel.nio.intraband.rpc.IntrabandRPCUtil.FutureResult;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.IOException;
import java.io.Serializable;

import java.lang.reflect.Method;

import java.nio.ByteBuffer;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class IntrabandRPCUtilTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testConstructor() {
		new IntrabandRPCUtil();
	}

	@Test
	public void testEmptyCallable() throws Exception {
		Assert.assertNull(IntrabandRPCUtil.EMPTY_CALLABLE.call());
	}

	@Test
	public void testExecuteFail() {
		PortalClassLoaderUtil.setClassLoader(getClass().getClassLoader());

		MockIntraband mockIntraband = new MockIntraband() {

			@Override
			protected void doSendDatagram(
				RegistrationReference registrationReference,
				Datagram datagram) {

				throw new RuntimeException();
			}

		};

		try {
			IntrabandRPCUtil.execute(
				new MockRegistrationReference(mockIntraband),
				new TestProcessCallable());

			Assert.fail();
		}
		catch (IntrabandRPCException ibrpce) {
			Throwable throwable = ibrpce.getCause();

			Assert.assertSame(RuntimeException.class, throwable.getClass());
		}
	}

	@Test
	public void testExecuteSuccess() throws Exception {
		PortalClassLoaderUtil.setClassLoader(getClass().getClassLoader());

		MockIntraband mockIntraband = new MockIntraband() {

			@Override
			protected void doSendDatagram(
				RegistrationReference registrationReference,
				Datagram datagram) {

				Deserializer deserializer = new Deserializer(
					datagram.getDataByteBuffer());

				try {
					ProcessCallable<Serializable> processCallable =
						deserializer.readObject();

					Serializable result = processCallable.call();

					Serializer serializer = new Serializer();

					serializer.writeObject(result);

					CompletionHandler<Object> completionHandler =
						DatagramHelper.getCompletionHandler(datagram);

					completionHandler.replied(
						null,
						Datagram.createResponseDatagram(
							datagram, serializer.toByteBuffer()));
				}
				catch (Exception e) {
					Assert.fail(e.getMessage());
				}
			}

		};

		MockRegistrationReference mockRegistrationReference =
			new MockRegistrationReference(mockIntraband);

		Future<String> futureResult = IntrabandRPCUtil.execute(
			mockRegistrationReference, new TestProcessCallable());

		Assert.assertEquals(
			TestProcessCallable.class.getName(), futureResult.get());
	}

	@Test
	public void testFutureCompletionHandler() throws Exception {

		// Failed

		FutureResult<String> futureResult = new FutureResult<String>();

		FutureCompletionHandler<String> futureCompletionHandler =
			new FutureCompletionHandler<String>(futureResult);

		futureCompletionHandler.delivered(null);
		futureCompletionHandler.submitted(null);

		IOException ioe = new IOException();

		futureCompletionHandler.failed(null, ioe);

		try {
			futureResult.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(ioe, ee.getCause());
		}

		// ClassNotFoundException

		futureResult = new FutureResult<String>();

		futureCompletionHandler = new FutureCompletionHandler<String>(
			futureResult);

		Serializer serializer = new Serializer();

		serializer.writeObject(new TestProcessCallable());

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		byteBuffer.put(76, (byte)CharPool.UPPER_CASE_S);

		futureCompletionHandler.replied(
			null, Datagram.createRequestDatagram(SystemDataType.RPC.getValue(),
			byteBuffer));

		try {
			futureResult.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			Assert.assertSame(
				ClassNotFoundException.class, throwable.getClass());
		}

		// Timeouted

		futureResult = new FutureResult<String>();

		futureCompletionHandler = new FutureCompletionHandler<String>(
			futureResult);

		futureCompletionHandler.timeouted(null);

		try {
			futureResult.get();

			Assert.fail();
		}
		catch (CancellationException ce) {
		}
	}

	@Test
	public void testFutureResult() throws Exception {

		// Bridge set

		Method bridgeSetMethod = ReflectionUtil.getDeclaredBridgeMethod(
			FutureResult.class, "set", Serializable.class);

		FutureResult<String> futureResult = new FutureResult<String>();

		String s = new String();

		bridgeSetMethod.invoke(futureResult, s);

		Assert.assertSame(s, futureResult.get());

		// Setter

		futureResult = new FutureResult<String>();

		Exception exception = new Exception();

		futureResult.setException(exception);

		try {
			futureResult.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(exception, ee.getCause());
		}
	}

	private static class TestProcessCallable
		implements ProcessCallable<String> {

		@Override
		public String call() {
			return TestProcessCallable.class.getName();
		}

		private static final long serialVersionUID = 1L;
	}

}