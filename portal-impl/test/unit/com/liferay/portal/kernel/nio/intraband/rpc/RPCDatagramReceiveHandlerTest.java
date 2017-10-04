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

import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.PortalExecutorManagerInvocationHandler;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.nio.intraband.test.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.test.MockRegistrationReference;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class RPCDatagramReceiveHandlerTest {

	@ClassRule
	@Rule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(RPCResponse.class);
			}

		};

	@Before
	public void setUp() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(
			PortalExecutorManager.class,
			(PortalExecutorManager)ProxyUtil.newProxyInstance(
				RPCDatagramReceiveHandlerTest.class.getClassLoader(),
				new Class<?>[] {PortalExecutorManager.class},
				new PortalExecutorManagerInvocationHandler()));
	}

	@Test
	public void testDoReceive() throws Exception {
		Class<?> clazz = getClass();

		PortalClassLoaderUtil.setClassLoader(clazz.getClassLoader());

		RPCDatagramReceiveHandler rpcDatagramReceiveHandler =
			new RPCDatagramReceiveHandler();

		MockIntraband mockIntraband = new MockIntraband();

		Serializer serializer = new Serializer();

		serializer.writeObject(new TestProcessCallable());

		SystemDataType systemDataType = SystemDataType.RPC;

		rpcDatagramReceiveHandler.doReceive(
			new MockRegistrationReference(mockIntraband),
			Datagram.createRequestDatagram(
				systemDataType.getValue(), serializer.toByteBuffer()));

		Datagram responseDatagram = mockIntraband.getDatagram();

		Deserializer deserializer = new Deserializer(
			responseDatagram.getDataByteBuffer());

		RPCResponse rpcResponse = deserializer.readObject();

		Assert.assertEquals(
			TestProcessCallable.class.getName(), rpcResponse.getResult());
		Assert.assertNull(rpcResponse.getException());

		serializer = new Serializer();

		serializer.writeObject(new ErrorTestProcessCallable());

		rpcDatagramReceiveHandler.receive(
			new MockRegistrationReference(mockIntraband),
			Datagram.createRequestDatagram(
				systemDataType.getValue(), serializer.toByteBuffer()));

		responseDatagram = mockIntraband.getDatagram();

		deserializer = new Deserializer(responseDatagram.getDataByteBuffer());

		rpcResponse = deserializer.readObject();

		Assert.assertNull(rpcResponse.getResult());

		Exception exception = rpcResponse.getException();

		Assert.assertSame(ProcessException.class, exception.getClass());
		Assert.assertEquals(
			ErrorTestProcessCallable.class.getName(), exception.getMessage());
	}

	private static class ErrorTestProcessCallable
		implements ProcessCallable<String> {

		@Override
		public String call() throws ProcessException {
			throw new ProcessException(
				ErrorTestProcessCallable.class.getName());
		}

		private static final long serialVersionUID = 1L;

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