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
import com.liferay.portal.kernel.nio.intraband.MockIntraBand;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.io.Serializable;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class IntraBandRPCUtilTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testConstructor() {
		new IntraBandRPCUtil();
	}

	@Test
	public void testExecuteFail() {
		PortalClassLoaderUtil.setClassLoader(getClass().getClassLoader());

		MockIntraBand mockIntraBand = new MockIntraBand() {

			@Override
			protected void doSendDatagram(
				RegistrationReference registrationReference,
				Datagram datagram) {

				throw new RuntimeException();
			}

		};

		try {
			IntraBandRPCUtil.execute(
				new MockRegistrationReference(mockIntraBand),
				new TestProcessCallable());

			Assert.fail();
		}
		catch (IntraBandRPCException ibrpce) {
			Throwable throwable = ibrpce.getCause();

			Assert.assertSame(RuntimeException.class, throwable.getClass());
		}

		try {
			IntraBandRPCUtil.execute(
				new MockRegistrationReference(mockIntraBand),
				new TestProcessCallable(), 1, TimeUnit.MILLISECONDS);

			Assert.fail();
		}
		catch (IntraBandRPCException ibrpce) {
			Throwable throwable = ibrpce.getCause();

			Assert.assertSame(RuntimeException.class, throwable.getClass());
		}
	}

	@Test
	public void testExecuteSuccess() throws IntraBandRPCException {
		PortalClassLoaderUtil.setClassLoader(getClass().getClassLoader());

		MockIntraBand mockIntraBand = new MockIntraBand() {

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
			new MockRegistrationReference(mockIntraBand);

		String result = IntraBandRPCUtil.execute(
			mockRegistrationReference, new TestProcessCallable());

		Assert.assertEquals(TestProcessCallable.class.getName(), result);

		result = IntraBandRPCUtil.execute(
			mockRegistrationReference, new TestProcessCallable(), 1,
			TimeUnit.MILLISECONDS);

		Assert.assertEquals(TestProcessCallable.class.getName(), result);
	}

	private static class TestProcessCallable
		implements ProcessCallable<String> {

		@Override
		public String call() throws ProcessException {
			return TestProcessCallable.class.getName();
		}

		private static final long serialVersionUID = 1L;
	}

}