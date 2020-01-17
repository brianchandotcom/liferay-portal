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