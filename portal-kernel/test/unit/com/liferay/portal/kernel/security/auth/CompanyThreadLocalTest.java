/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionIdSupplier;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.monitoring.DataSample;
import com.liferay.portal.kernel.monitoring.DataSampleThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.TimeZoneThreadLocal;

import java.util.List;
import java.util.TimeZone;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.Mockito;

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
	public void testCentralizedCompanyThreadLocalsWithSetWithSafeCloseable() {
		DataSample dataSample = Mockito.mock(DataSample.class);

		DataSampleThreadLocal.addDataSample(dataSample);

		GroupThreadLocal.setGroupId(1L);

		LocaleThreadLocal.setDefaultLocale(LocaleUtil.GERMAN);

		PasswordModificationThreadLocal.setPasswordUnencrypted("passwordTest");

		PrincipalThreadLocal.setName("userTest");
		PrincipalThreadLocal.setPassword("passwordTest");

		ServiceContext serviceContext = Mockito.mock(ServiceContext.class);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		TimeZone pstTimeZone = TimeZone.getTimeZone("PST");

		TimeZoneThreadLocal.setDefaultTimeZone(pstTimeZone);

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(1L)) {

			List<DataSample> dataSamples =
				DataSampleThreadLocal.getDataSamples();

			Assert.assertFalse(dataSamples.contains(dataSample));

			Assert.assertNotEquals(
				1L,
				GroupThreadLocal.getGroupId(
				).longValue());

			Assert.assertNotEquals(
				LocaleUtil.GERMAN, LocaleThreadLocal.getDefaultLocale());

			Assert.assertNotEquals(
				"passwordTest",
				PasswordModificationThreadLocal.getPasswordUnencrypted());

			Assert.assertNotEquals("userTest", PrincipalThreadLocal.getName());

			Assert.assertNotEquals(
				"passwordTest", PrincipalThreadLocal.getPassword());

			Assert.assertNotEquals(
				serviceContext, ServiceContextThreadLocal.getServiceContext());

			Assert.assertNotEquals(
				pstTimeZone, TimeZoneThreadLocal.getDefaultTimeZone());
		}

		List<DataSample> dataSamples = DataSampleThreadLocal.getDataSamples();

		Assert.assertTrue(dataSamples.contains(dataSample));

		Assert.assertEquals(
			1L,
			GroupThreadLocal.getGroupId(
			).longValue());

		Assert.assertEquals(
			LocaleUtil.GERMAN, LocaleThreadLocal.getDefaultLocale());

		Assert.assertEquals(
			"passwordTest",
			PasswordModificationThreadLocal.getPasswordUnencrypted());

		Assert.assertEquals("userTest", PrincipalThreadLocal.getName());

		Assert.assertEquals("passwordTest", PrincipalThreadLocal.getPassword());

		Assert.assertEquals(
			serviceContext, ServiceContextThreadLocal.getServiceContext());

		Assert.assertEquals(
			pstTimeZone, TimeZoneThreadLocal.getDefaultTimeZone());
	}

	@Test
	public void testLock() {
		_testLock(CompanyThreadLocal::setCompanyId);
	}

	@Test
	public void testLockWithSetWithSafeCloseable() {
		_testLock(CompanyThreadLocal::setCompanyIdWithSafeCloseable);
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