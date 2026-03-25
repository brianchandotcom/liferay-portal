/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.module.configuration;

import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
public class BaseManagedServiceFactoryTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDeletedDefaultSystemCompanyId() {
		TestManagedServiceFactory managedServiceFactory =
			new TestManagedServiceFactory();

		managedServiceFactory.deleted(RandomTestUtil.randomString());

		Assert.assertEquals(
			CompanyConstants.SYSTEM, (long)CompanyThreadLocal.getCompanyId());
		Assert.assertEquals(
			CompanyConstants.SYSTEM,
			managedServiceFactory.getExecutedDeletedCompanyId());
	}

	@Test
	public void testDeletedSetsCompanyId() {
		long companyId = RandomTestUtil.randomLong();

		TestManagedServiceFactory managedServiceFactory =
			new TestManagedServiceFactory();

		String pid = RandomTestUtil.randomString();

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("companyId", companyId);

		managedServiceFactory.updated(pid, properties);

		managedServiceFactory.deleted(pid);

		Assert.assertEquals(
			CompanyConstants.SYSTEM, (long)CompanyThreadLocal.getCompanyId());
		Assert.assertEquals(
			companyId, managedServiceFactory.getExecutedDeletedCompanyId());
	}

	@Test
	public void testUpdatedDefaultSystemCompanyId() {
		TestManagedServiceFactory managedServiceFactory =
			new TestManagedServiceFactory();

		managedServiceFactory.updated(
			RandomTestUtil.randomString(), new Hashtable<>());

		Assert.assertEquals(
			CompanyConstants.SYSTEM, (long)CompanyThreadLocal.getCompanyId());
		Assert.assertEquals(
			CompanyConstants.SYSTEM,
			managedServiceFactory.getExecutedCompanyId());
	}

	@Test
	public void testUpdatedSetsCompanyId() {
		long companyId = RandomTestUtil.randomLong();

		TestManagedServiceFactory managedServiceFactory =
			new TestManagedServiceFactory();

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("companyId", companyId);

		managedServiceFactory.updated(
			RandomTestUtil.randomString(), properties);

		Assert.assertEquals(
			CompanyConstants.SYSTEM, (long)CompanyThreadLocal.getCompanyId());
		Assert.assertEquals(
			companyId, managedServiceFactory.getExecutedCompanyId());
	}

	private class TestManagedServiceFactory extends BaseManagedServiceFactory {

		public long getExecutedCompanyId() {
			return _executedCompanyId;
		}

		public long getExecutedDeletedCompanyId() {
			return _executedDeletedCompanyId;
		}

		@Override
		public String getName() {
			return RandomTestUtil.randomString();
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