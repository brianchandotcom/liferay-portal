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
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class BootstrapRPCDatagramReceiveHandlerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testReceive() throws Exception {
		PortalClassLoaderUtil.setClassLoader(getClass().getClassLoader());

		BootstrapRPCDatagramReceiveHandler bootstrapRPCDatagramReceiveHandler =
			new BootstrapRPCDatagramReceiveHandler();

		MockIntraband mockIntraband = new MockIntraband();

		Serializer serializer = new Serializer();

		serializer.writeObject(new TestProcessCallable());

		SystemDataType systemDataType = SystemDataType.RPC;

		bootstrapRPCDatagramReceiveHandler.receive(
			new MockRegistrationReference(mockIntraband),
			Datagram.createRequestDatagram(
				systemDataType.getValue(), serializer.toByteBuffer()));

		Datagram responseDatagram = mockIntraband.getDatagram();

		Deserializer deserializer = new Deserializer(
			responseDatagram.getDataByteBuffer());

		Assert.assertEquals(
			TestProcessCallable.class.getName(), deserializer.readObject());

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			BootstrapRPCDatagramReceiveHandler.class.getName(), Level.SEVERE);

		serializer = new Serializer();

		serializer.writeObject(new ErrorTestProcessCallable());

		bootstrapRPCDatagramReceiveHandler.receive(
			new MockRegistrationReference(mockIntraband),
			Datagram.createRequestDatagram(
				systemDataType.getValue(), serializer.toByteBuffer()));

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Unable to execute", logRecord.getMessage());

		Throwable throwable = logRecord.getThrown();

		Assert.assertSame(ProcessException.class, throwable.getClass());
		Assert.assertEquals("Execution error", throwable.getMessage());
	}

	private static class ErrorTestProcessCallable
		implements ProcessCallable<String> {

		@Override
		public String call() throws ProcessException {
			throw new ProcessException("Execution error");
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