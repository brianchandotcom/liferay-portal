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

package com.liferay.portal.kernel.nio.intraband.mailbox;

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.MockIntraBand;
import com.liferay.portal.kernel.nio.intraband.MockRegistrationReference;
import com.liferay.portal.kernel.nio.intraband.PortalExecutorManagerUtilAdvice;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.PropsUtilAdvice;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class MailboxDatagramReceiveHandlerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@AdviseWith(
		adviceClasses = {
			PropsUtilAdvice.class, PortalExecutorManagerUtilAdvice.class
		}
	)
	@Test
	public void testDoReceive() {
		MailboxDatagramReceiveHandler mailboxDatagramReceiveHandler =
			new MailboxDatagramReceiveHandler();

		MockIntraBand mockIntraBand = new MockIntraBand();

		SystemDataType systemDataType = SystemDataType.MAILBOX;

		mailboxDatagramReceiveHandler.doReceive(
			new MockRegistrationReference(mockIntraBand),
			Datagram.createRequestDatagram(
				systemDataType.getValue(), ByteBuffer.allocate(0)));

		Datagram responseDatagram = mockIntraBand.getDatagram();

		Deserializer deserializer = new Deserializer(
			responseDatagram.getDataByteBuffer());

		Assert.assertEquals(0, deserializer.readLong());
	}

}