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

package com.liferay.portal.kernel.nio.intraband.rpc;

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.MockIntraBand;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.PortalExecutorManagerUtilAdvice;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.nio.ByteBuffer;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class RPCDatagramReceiveHandlerTest {

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testDoReceive() throws Exception {
		PortalClassLoaderUtil.setClassLoader(getClass().getClassLoader());

		MockIntraBand mockIntraBand = new MockIntraBand();
		MockRegistrationReference mockRegistrationReference =
			new MockRegistrationReference(mockIntraBand);

		ProcessCallable<String> testProcessCallable = new TestProcessCallable();

		Serializer serializer = new Serializer();

		serializer.writeObject(testProcessCallable);

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		Datagram datagram = Datagram.createRequestDatagram(
			SystemDataType.RPC.getValue(), byteBuffer);

		RPCDatagramReceiveHandler rpcDatagramReceiveHandler =
			new RPCDatagramReceiveHandler();

		rpcDatagramReceiveHandler.doReceive(
			mockRegistrationReference, datagram);

		Datagram responseDatagram = mockIntraBand.getDatagram();

		Deserializer deserializer = new Deserializer(
			responseDatagram.getData());

		Assert.assertEquals(
			TestProcessCallable.class.getName(), deserializer.readObject());
	}

	private static class TestProcessCallable
		implements ProcessCallable<String> {

		public String call() throws ProcessException {
			return TestProcessCallable.class.getName();
		}

	}

}