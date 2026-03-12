/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.unit.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Petteri Karttunen
 */
@RunWith(Arquillian.class)
public class BatchEngineUnitProcessorImplDBPartitionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		DBType dbType = DBManagerUtil.getDBType();

		Assume.assumeTrue(
			(dbType == DBType.MYSQL) || (dbType == DBType.POSTGRESQL));

		Assume.assumeTrue(PropsValues.DATABASE_PARTITION_ENABLED);
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();
	}

	@Test
	public void testGetAdminUserIdResolvesCorrectPartition() throws Exception {
		Assert.assertEquals(
			TestPropsValues.getUserId(),
			(long)ReflectionTestUtil.invoke(
				_batchEngineUnitProcessor, "_getAdminUserId",
				new Class<?>[] {long.class}, TestPropsValues.getCompanyId()));

		User user;

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					_company.getCompanyId())) {

			user = UserTestUtil.getAdminUser(_company.getCompanyId());
		}

		Assert.assertEquals(
			user.getUserId(),
			(long)ReflectionTestUtil.invoke(
				_batchEngineUnitProcessor, "_getAdminUserId",
				new Class<?>[] {long.class}, _company.getCompanyId()));

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					TestPropsValues.getCompanyId())) {

			Assert.assertEquals(
				TestPropsValues.getUserId(),
				(long)ReflectionTestUtil.invoke(
					_batchEngineUnitProcessor, "_getAdminUserId",
					new Class<?>[] {long.class},
					TestPropsValues.getCompanyId()));

			Assert.assertEquals(
				user.getUserId(),
				(long)ReflectionTestUtil.invoke(
					_batchEngineUnitProcessor, "_getAdminUserId",
					new Class<?>[] {long.class}, _company.getCompanyId()));
		}

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					_company.getCompanyId())) {

			Assert.assertEquals(
				TestPropsValues.getUserId(),
				(long)ReflectionTestUtil.invoke(
					_batchEngineUnitProcessor, "_getAdminUserId",
					new Class<?>[] {long.class},
					TestPropsValues.getCompanyId()));

			Assert.assertEquals(
				user.getUserId(),
				(long)ReflectionTestUtil.invoke(
					_batchEngineUnitProcessor, "_getAdminUserId",
					new Class<?>[] {long.class}, _company.getCompanyId()));
		}
	}

	@Test
	public void testProcessBatchEngineUnitsImportsSystemObjectEntriesForVirtualInstance()
		throws Exception {

		List<ObjectDefinition> objectDefinitions1 =
			_objectDefinitionLocalService.getSystemObjectDefinitions();

		int objectEntriesCount = _getObjectEntriesCount(objectDefinitions1);

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					_company.getCompanyId())) {

			List<ObjectDefinition> objectDefinitions2 =
				_objectDefinitionLocalService.getSystemObjectDefinitions();

			Assert.assertEquals(
				ListUtil.toString(objectDefinitions2, StringPool.BLANK),
				objectDefinitions1.size(), objectDefinitions2.size());
			Assert.assertEquals(
				objectEntriesCount, _getObjectEntriesCount(objectDefinitions2));
		}
	}

	private int _getObjectEntriesCount(
		List<ObjectDefinition> objectDefinitions) {

		int objectEntriesCount = 0;

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			objectEntriesCount +=
				_objectEntryLocalService.getObjectEntriesCount(
					objectDefinition.getObjectDefinitionId());
		}

		return objectEntriesCount;
	}

	private static Company _company;

	@Inject
	private BatchEngineUnitProcessor _batchEngineUnitProcessor;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private UserLocalService _userLocalService;

}