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

package com.liferay.portal.kernel.nio.intraband;

import com.liferay.portal.kernel.nio.intraband.blocking.ExecutorIntraBand;
import com.liferay.portal.kernel.nio.intraband.nonblocking.SelectorIntraBand;
import com.liferay.portal.kernel.nio.intraband.welder.fifo.FIFOUtil;
import com.liferay.portal.kernel.nio.intraband.welder.fifo.FIFOWelder;
import com.liferay.portal.kernel.nio.intraband.welder.socket.SocketWelder;
import com.liferay.portal.kernel.test.NewClassLoaderJUnitTestRunner;
import com.liferay.portal.kernel.util.PropsKeys;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(NewClassLoaderJUnitTestRunner.class)
public class IntraBandFactoryUtilTest {

	@Test
	public void testCreateIntraBandClassClassNotFound() throws IOException {
		System.setProperty(PropsKeys.INTRABAND_IMPL_CLASS, "NoSuchClass");

		try {
			IntraBandFactoryUtil.createIntraBand();

			Assert.fail();
		}
		catch (RuntimeException re) {
			Assert.assertTrue(
				re.getMessage().startsWith(
					"Failed to create IntraBand with class name "));

			Assert.assertTrue(re.getCause() instanceof ClassNotFoundException);
		}
		finally {
			System.clearProperty(PropsKeys.INTRABAND_IMPL_CLASS);
		}
	}

	@Test
	public void testCreateIntraBandCustomizedImpl() throws Exception {
		System.setProperty(
			PropsKeys.INTRABAND_IMPL_CLASS, SelectorIntraBand.class.getName());

		IntraBand intraBand = null;

		try {
			intraBand = IntraBandFactoryUtil.createIntraBand();

			Assert.assertEquals(SelectorIntraBand.class, intraBand.getClass());
		}
		finally {
			if (intraBand != null) {
				intraBand.close();
			}

			System.clearProperty(PropsKeys.INTRABAND_IMPL_CLASS);
		}
	}

	@Test
	public void testCreateIntraBandDefaultToFIFO() throws Exception {
		if (FIFOUtil.isFIFOSupported()) {
			System.setProperty(
				PropsKeys.INTRABAND_WELDER_IMPL_CLASS,
				FIFOWelder.class.getName());

			IntraBand intraBand = null;

			try {
				intraBand = IntraBandFactoryUtil.createIntraBand();

				Assert.assertEquals(
					ExecutorIntraBand.class, intraBand.getClass());
			}
			finally {
				if (intraBand != null) {
					intraBand.close();
				}

				System.clearProperty(PropsKeys.INTRABAND_WELDER_IMPL_CLASS);
			}
		}
	}

	@Test
	public void testCreateIntraBandDefaultToSocket() throws Exception {
		System.setProperty(
			PropsKeys.INTRABAND_WELDER_IMPL_CLASS,
			SocketWelder.class.getName());

		IntraBand intraBand = null;

		try {
			intraBand = IntraBandFactoryUtil.createIntraBand();

			Assert.assertEquals(SelectorIntraBand.class, intraBand.getClass());
		}
		finally {
			if (intraBand != null) {
				intraBand.close();
			}

			System.clearProperty(PropsKeys.INTRABAND_WELDER_IMPL_CLASS);
		}
	}

}