/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionIdSupplier;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.TimeZoneThreadLocal;

import java.util.TimeZone;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.osgi.framework.BundleContext;

/**
 * @author Alberto Chaparro
 */
public class CompanyThreadLocalTest {

	@BeforeClass
	public static void setUpClass() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		bundleContext.registerService(
			CTCollectionIdSupplier.class,
			ProxyFactory.newDummyInstance(CTCollectionIdSupplier.class), null);

		PropsUtil.setProps(ProxyFactory.newDummyInstance(Props.class));
	}

	@Test
	public void testLock() {
		_testLock(CompanyThreadLocal::setCompanyId);
	}

	@Test
	public void testLockWithSetWithSafeCloseable() {
		_testLock(CompanyThreadLocal::setCompanyIdWithSafeCloseable);
	}

	@Test
	public void testUserThreadLocalsWithSetWithSafeCloseable() {
		TimeZone pstTimeZone = TimeZone.getTimeZone("PST");

		LocaleThreadLocal.setDefaultLocale(LocaleUtil.GERMAN);
		TimeZoneThreadLocal.setDefaultTimeZone(pstTimeZone);

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(1L)) {

			Assert.assertNotEquals(
				LocaleUtil.GERMAN, LocaleThreadLocal.getDefaultLocale());
			Assert.assertNotEquals(
				pstTimeZone, TimeZoneThreadLocal.getDefaultTimeZone());
		}

		Assert.assertEquals(
			LocaleUtil.GERMAN, LocaleThreadLocal.getDefaultLocale());
		Assert.assertEquals(
			pstTimeZone, TimeZoneThreadLocal.getDefaultTimeZone());
	}

	private void _testLock(Consumer<Long> consumer) {
		try (SafeCloseable safeCloseable = CompanyThreadLocal.lock(
				CompanyConstants.SYSTEM)) {

			consumer.accept(1L);

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
			Assert.assertNotNull(unsupportedOperationException);
		}
	}

}