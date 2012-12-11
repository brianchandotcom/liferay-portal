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

package com.liferay.portal.kernel.nio.intraband.welder;

import com.liferay.portal.kernel.nio.intraband.IntraBand;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.welder.fifo.FIFOWelder;
import com.liferay.portal.kernel.nio.intraband.welder.socket.SocketWelder;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewClassLoaderJUnitTestRunner;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class WelderFactoryUtilTest {

	@Test
	public void testCreateWelder() throws Exception {
		System.setProperty(
			PropsKeys.INTRABAND_WELDER_IMPL_CLASS, MockWelder.class.getName());

		try {
			Welder welder = WelderFactoryUtil.createWelder();

			Assert.assertNotNull(welder);
			Assert.assertEquals(MockWelder.class, welder.getClass());
		}
		finally {
			System.clearProperty(PropsKeys.INTRABAND_WELDER_IMPL_CLASS);
		}
	}

	@Test
	public void testCreateWelderFailed() throws Exception {
		System.setProperty(
			PropsKeys.INTRABAND_WELDER_IMPL_CLASS,
			WrongMockWelder.class.getName());

		try {
			WelderFactoryUtil.createWelder();

			Assert.fail();
		}
		catch (RuntimeException re) {
		}
		finally {
			System.clearProperty(PropsKeys.INTRABAND_WELDER_IMPL_CLASS);
		}
	}

	@Test
	public void testGetWelderClassClassNotFound() {
		System.setProperty(
			PropsKeys.INTRABAND_WELDER_IMPL_CLASS, "NoSuchClass");

		try {
			WelderFactoryUtil.getWelderClass();

			Assert.fail();
		}
		catch (RuntimeException re) {
			Assert.assertTrue(
				re.getMessage().startsWith(
					"Failed to load Welder class with name "));

			Assert.assertTrue(re.getCause() instanceof ClassNotFoundException);
		}
		finally {
			System.clearProperty(PropsKeys.INTRABAND_WELDER_IMPL_CLASS);
		}
	}

	@Test
	public void testGetWelderClassCustomizedImpl() {
		System.setProperty(
			PropsKeys.INTRABAND_WELDER_IMPL_CLASS, MockWelder.class.getName());

		try {
			Assert.assertEquals(
				MockWelder.class, WelderFactoryUtil.getWelderClass());
		}
		finally {
			System.clearProperty(PropsKeys.INTRABAND_WELDER_IMPL_CLASS);
		}
	}

	@AdviseWith(adviceClasses = {FIFOUtilAdvice.class, OSDetectorAdvice.class})
	@Test
	public void testGetWelderClassOnNonWindowsWithFIFO() {
		OSDetectorAdvice._isWindows = false;
		FIFOUtilAdvice._isFIFOSupported = true;

		Assert.assertEquals(
			FIFOWelder.class, WelderFactoryUtil.getWelderClass());
	}

	@AdviseWith(adviceClasses = {FIFOUtilAdvice.class, OSDetectorAdvice.class})
	@Test
	public void testGetWelderClassOnNonWindowsWithoutFIFO() {
		OSDetectorAdvice._isWindows = false;
		FIFOUtilAdvice._isFIFOSupported = false;

		Assert.assertEquals(
			SocketWelder.class, WelderFactoryUtil.getWelderClass());
	}

	@AdviseWith(adviceClasses = {OSDetectorAdvice.class})
	@Test
	public void testGetWelderClassOnWindows() {
		OSDetectorAdvice._isWindows = true;

		Assert.assertEquals(
			SocketWelder.class, WelderFactoryUtil.getWelderClass());
	}

	@Aspect
	public static class FIFOUtilAdvice {

		@Around("execution(public static boolean isFIFOSupported())")
		public boolean isFIFOSupported() {
			return _isFIFOSupported;
		}

		private static boolean _isFIFOSupported;

	}

	@Aspect
	public static class OSDetectorAdvice {

		@Around("execution(public static boolean isWindows())")
		public boolean isWindows() {
			return _isWindows;
		}

		private static boolean _isWindows;

	}

	protected static class MockWelder implements Welder {

		public RegistrationReference weldClient(IntraBand intraBand) {
			return null;
		}

		public RegistrationReference weldServer(IntraBand intraBand) {
			return null;
		}

	}

	private static class WrongMockWelder implements Welder {

		public RegistrationReference weldClient(IntraBand intraBand) {
			return null;
		}

		public RegistrationReference weldServer(IntraBand intraBand) {
			return null;
		}

	}

}