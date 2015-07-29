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

package com.liferay.portal.nio.intraband.cache;

import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.proxy.ExceptionHandler;
import com.liferay.portal.kernel.nio.intraband.test.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.test.MockRegistrationReference;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.nio.intraband.proxy.IntrabandProxyUtil;
import com.liferay.portal.nio.intraband.proxy.WarnLogExceptionHandler;
import com.liferay.portal.util.FileImpl;

import java.lang.reflect.Constructor;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class BaseIntrabandPortalCacheManagerTest {

	@Test
	public void testStubGeneration() throws Exception {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		Class<?> stubClass = IntrabandProxyUtil.getStubClass(
			BaseIntrabandPortalCacheManager.class,
			PortalCacheManager.class.getName());

		Constructor<?> constructor = stubClass.getConstructor(
			String.class, RegistrationReference.class, ExceptionHandler.class);

		PortalCacheManager<?, ?> portalCacheManager =
			(PortalCacheManager<?, ?>)constructor.newInstance(
				"PortalCacheManager",
				new MockRegistrationReference(new MockIntraband()),
				WarnLogExceptionHandler.INSTANCE);

		Assert.assertNotNull(portalCacheManager.getPortalCache("PortalCache"));
	}

}