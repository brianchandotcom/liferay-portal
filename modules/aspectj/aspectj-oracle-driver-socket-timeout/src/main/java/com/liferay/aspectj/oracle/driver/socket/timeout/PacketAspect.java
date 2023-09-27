/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.aspectj.oracle.driver.socket.timeout;

import java.lang.reflect.Constructor;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.SuppressAjWarnings;

/**
 * @author Shuyang Zhou
 */
@Aspect
@SuppressAjWarnings("adviceDidNotMatch")
public class PacketAspect {

	@Before(
		"handler(java.io.InterruptedIOException) &&" +
			"withincode(void oracle.net.ns.Packet.receive()) &&" +
				"args(exception1) && this(packet)"
	)
	public void addSuppressedInterruptedIOException(
			Object packet, Exception exception1)
		throws Exception {

		Class<?> clazz = packet.getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		clazz = classLoader.loadClass("oracle.net.ns.NetException");

		Constructor<? extends Exception> constructor =
			(Constructor<? extends Exception>)clazz.getConstructor(int.class);

		Exception exception2 = constructor.newInstance(504);

		exception2.addSuppressed(exception1);

		throw exception2;
	}

}