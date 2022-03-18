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

package com.liferay.portal.kernel.security.auth;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionIdSupplier;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.ProxyFactory;

import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.osgi.framework.BundleContext;

/**
 * @author Alberto Chaparro
 */
public class CompanyThreadLocalTest {

	@Before
	public void setUp() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		bundleContext.registerService(
			CTCollectionIdSupplier.class,
			ProxyFactory.newDummyInstance(CTCollectionIdSupplier.class), null);
	}

	@Test
	public void testLock() {
		_testLock(companyId -> CompanyThreadLocal.setCompanyId(companyId));
	}

	@Test
	public void testLockWithSetWithSafeCloseable() {
		_testLock(
			companyId -> CompanyThreadLocal.setWithSafeCloseable(companyId));
	}

	private void _testLock(Consumer<Long> consumer) {
		SafeCloseable safeCloseable = CompanyThreadLocal.lock(
			CompanyConstants.SYSTEM);

		try {
			consumer.accept(1L);

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
			Assert.assertNotNull(unsupportedOperationException);
		}
		finally {
			safeCloseable.close();
		}
	}

}