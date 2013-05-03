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

package com.liferay.portal.kernel.resiliency;

import com.liferay.portal.kernel.resiliency.mpi.MPI;
import com.liferay.portal.kernel.resiliency.spi.SPI;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class RMISignatureTest {

	@Test
	public void testMPISignature() {
		_checkRMISignature(MPI.class, false);
	}

	@Test
	public void testSPISignature() {
		_checkRMISignature(SPI.class, true);
	}

	private void _checkRMISignature(
		Class<? extends Remote> rmiClass, boolean serializable) {

		Assert.assertTrue(
			rmiClass + " does not implement " + Remote.class,
			Remote.class.isAssignableFrom(rmiClass));

		if (serializable) {
			Assert.assertTrue(
				rmiClass + " does not implement " + Serializable.class,
				Serializable.class.isAssignableFrom(rmiClass));
		}

		Method[] methods = rmiClass.getDeclaredMethods();

		Method:
		for (Method method : methods) {
			Class<?>[] exceptionTypes = method.getExceptionTypes();

			for (Class<?> exceptionType : exceptionTypes) {
				if (RemoteException.class.isAssignableFrom(exceptionType)) {
					continue Method;
				}
			}

			Assert.fail(method + " does not throw " + RemoteException.class);
		}
	}

}