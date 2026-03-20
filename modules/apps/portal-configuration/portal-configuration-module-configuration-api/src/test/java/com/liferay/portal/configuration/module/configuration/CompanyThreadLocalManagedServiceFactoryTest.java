/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.module.configuration;

import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author István András Dézsi
 */
public class CompanyThreadLocalManagedServiceFactoryTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDeletedDefaultSystemCompanyId() {
		TestManagedServiceFactory managedServiceFactory =
			new TestManagedServiceFactory();

		managedServiceFactory.deleted("test-pid");

		Assert.assertEquals(
			CompanyConstants.SYSTEM,
			managedServiceFactory.getExecutedDeletedCompanyId());

		Assert.assertEquals(
			CompanyConstants.SYSTEM, (long)CompanyThreadLocal.getCompanyId());
	}

	@Test
	public void testDeletedSetsCompanyId() {
		TestManagedServiceFactory managedServiceFactory =
			new TestManagedServiceFactory();

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("companyId", 12345L);

		managedServiceFactory.updated("test-pid", properties);

		managedServiceFactory.deleted("test-pid");

		Assert.assertEquals(
			12345L, managedServiceFactory.getExecutedDeletedCompanyId());

		Assert.assertEquals(
			CompanyConstants.SYSTEM, (long)CompanyThreadLocal.getCompanyId());
	}

	@Test
	public void testUpdatedDefaultSystemCompanyId() {
		TestManagedServiceFactory managedServiceFactory =
			new TestManagedServiceFactory();

		managedServiceFactory.updated("test-pid", new Hashtable<>());

		Assert.assertEquals(
			CompanyConstants.SYSTEM,
			managedServiceFactory.getExecutedCompanyId());

		Assert.assertEquals(
			CompanyConstants.SYSTEM, (long)CompanyThreadLocal.getCompanyId());
	}

	@Test
	public void testUpdatedSetsCompanyId() {
		TestManagedServiceFactory managedServiceFactory =
			new TestManagedServiceFactory();

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("companyId", 12345L);

		managedServiceFactory.updated("test-pid", properties);

		Assert.assertEquals(
			12345L, managedServiceFactory.getExecutedCompanyId());
		Assert.assertEquals(
			CompanyConstants.SYSTEM, (long)CompanyThreadLocal.getCompanyId());
	}

	private class TestManagedServiceFactory
		extends CompanyThreadLocalManagedServiceFactory {

		public long getExecutedCompanyId() {
			return _executedCompanyId;
		}

		public long getExecutedDeletedCompanyId() {
			return _executedDeletedCompanyId;
		}

		@Override
		public String getName() {
			return "TestManagedServiceFactory";
		}

		@Override
		protected void doDeleted(long companyId, String pid) {
			_executedDeletedCompanyId = CompanyThreadLocal.getCompanyId();
		}

		@Override
		protected void doUpdated(
			long companyId, Dictionary<String, ?> dictionary, String pid) {

			_executedCompanyId = CompanyThreadLocal.getCompanyId();
		}

		private long _executedCompanyId;
		private long _executedDeletedCompanyId;

	}

}