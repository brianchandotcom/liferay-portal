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

package com.liferay.portal.kernel.nio.intraband.rpc;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.nio.intraband.test.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.test.MockRegistrationReference;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.io.IOException;
import java.io.Serializable;

import java.nio.ByteBuffer;

import java.util.List;
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
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(RPCResponse.class);
			}

		};

	@Test
	public void testConstructor() {
		new IntrabandRPCUtil();
	}

	@Test
	public void testExecuteFail() throws Exception {
		Class<?> clazz = getClass();

		PortalClassLoaderUtil.setClassLoader(clazz.getClassLoader());

		final Exception exception = new Exception("Execution error");

		MockIntraband mockIntraband = new MockIntraband() {

			@Override
			protected Datagram processDatagram(Datagram datagram) {
				try {
					Serializer serializer = new Serializer();

					serializer.writeObject(new RPCResponse(exception));

					return Datagram.createResponseDatagram(
						datagram, serializer.toByteBuffer());
				}
				catch (Exception e) {
					throw new RuntimeException();
				}
			}

		};

		MockRegistrationReference mockRegistrationReference =
			new MockRegistrationReference(mockIntraband);

		Future<String> futureResult = IntrabandRPCUtil.execute(
			mockRegistrationReference, new TestProcessCallable());

		try {
			futureResult.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Throwable t = ee.getCause();

			Assert.assertEquals(exception.getMessage(), t.getMessage());
		}
	}

	@Test
	public void testExecuteSuccess() throws Exception {
		Class<?> clazz = getClass();

		PortalClassLoaderUtil.setClassLoader(clazz.getClassLoader());

		MockIntraband mockIntraband = new MockIntraband() {

			@Override
			protected Datagram processDatagram(Datagram datagram) {
				Deserializer deserializer = new Deserializer(
					datagram.getDataByteBuffer());

				try {
					Serializer serializer = new Serializer();

					ProcessCallable<Serializable> processCallable =
						deserializer.readObject();

					serializer.writeObject(
						new RPCResponse(processCallable.call()));

					return Datagram.createResponseDatagram(
						datagram, serializer.toByteBuffer());
				}
				catch (Exception e) {
					throw new RuntimeException(e);
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

		DefaultNoticeableFuture<String> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		IntrabandRPCUtil.FutureCompletionHandler<String>
			futureCompletionHandler =
				new IntrabandRPCUtil.FutureCompletionHandler<>(
					defaultNoticeableFuture);

		futureCompletionHandler.delivered(null);
		futureCompletionHandler.submitted(null);

		IOException ioe = new IOException();

		futureCompletionHandler.failed(null, ioe);

		try {
			defaultNoticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(ioe, ee.getCause());
		}

		// Class not found exception

		defaultNoticeableFuture = new DefaultNoticeableFuture<>();

		futureCompletionHandler =
			new IntrabandRPCUtil.FutureCompletionHandler<>(
				defaultNoticeableFuture);

		Serializer serializer = new Serializer();

		serializer.writeObject(new TestProcessCallable());

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		byteBuffer.put(76, (byte)CharPool.UPPER_CASE_S);

		futureCompletionHandler.replied(
			null,
			Datagram.createRequestDatagram(
				SystemDataType.RPC.getValue(), byteBuffer));

		try {
			defaultNoticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			Assert.assertSame(
				ClassNotFoundException.class, throwable.getClass());
		}

		// Timed out

		defaultNoticeableFuture = new DefaultNoticeableFuture<>();

		futureCompletionHandler =
			new IntrabandRPCUtil.FutureCompletionHandler<>(
				defaultNoticeableFuture);

		futureCompletionHandler.timedOut(null);

		try {
			defaultNoticeableFuture.get();

			Assert.fail();
		}
		catch (CancellationException ce) {
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