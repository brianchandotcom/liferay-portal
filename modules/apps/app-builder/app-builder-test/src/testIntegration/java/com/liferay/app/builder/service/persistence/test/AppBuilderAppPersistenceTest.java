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

package com.liferay.app.builder.service.persistence.test;

import com.liferay.app.builder.exception.NoSuchAppException;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.service.AppBuilderAppLocalServiceUtil;
import com.liferay.app.builder.service.persistence.AppBuilderAppPersistence;
import com.liferay.app.builder.service.persistence.AppBuilderAppUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class AppBuilderAppPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.app.builder.service"));

	@Before
	public void setUp() {
		_persistence = AppBuilderAppUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AppBuilderApp> iterator = _appBuilderApps.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AppBuilderApp appBuilderApp = _persistence.create(pk);

		Assert.assertNotNull(appBuilderApp);

		Assert.assertEquals(appBuilderApp.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AppBuilderApp newAppBuilderApp = addAppBuilderApp();

		_persistence.remove(newAppBuilderApp);

		AppBuilderApp existingAppBuilderApp = _persistence.fetchByPrimaryKey(
			newAppBuilderApp.getPrimaryKey());

		Assert.assertNull(existingAppBuilderApp);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAppBuilderApp();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AppBuilderApp newAppBuilderApp = _persistence.create(pk);

		newAppBuilderApp.setUuid(RandomTestUtil.randomString());

		newAppBuilderApp.setGroupId(RandomTestUtil.nextLong());

		newAppBuilderApp.setCompanyId(RandomTestUtil.nextLong());

		newAppBuilderApp.setUserId(RandomTestUtil.nextLong());

		newAppBuilderApp.setUserName(RandomTestUtil.randomString());

		newAppBuilderApp.setCreateDate(RandomTestUtil.nextDate());

		newAppBuilderApp.setModifiedDate(RandomTestUtil.nextDate());

		newAppBuilderApp.setActive(RandomTestUtil.randomBoolean());

		newAppBuilderApp.setDdlRecordSetId(RandomTestUtil.nextLong());

		newAppBuilderApp.setDdmStructureId(RandomTestUtil.nextLong());

		newAppBuilderApp.setDdmStructureLayoutId(RandomTestUtil.nextLong());

		newAppBuilderApp.setDeDataListViewId(RandomTestUtil.nextLong());

		newAppBuilderApp.setName(RandomTestUtil.randomString());

		newAppBuilderApp.setScope(RandomTestUtil.randomString());

		_appBuilderApps.add(_persistence.update(newAppBuilderApp));

		AppBuilderApp existingAppBuilderApp = _persistence.findByPrimaryKey(
			newAppBuilderApp.getPrimaryKey());

		Assert.assertEquals(
			existingAppBuilderApp.getUuid(), newAppBuilderApp.getUuid());
		Assert.assertEquals(
			existingAppBuilderApp.getAppBuilderAppId(),
			newAppBuilderApp.getAppBuilderAppId());
		Assert.assertEquals(
			existingAppBuilderApp.getGroupId(), newAppBuilderApp.getGroupId());
		Assert.assertEquals(
			existingAppBuilderApp.getCompanyId(),
			newAppBuilderApp.getCompanyId());
		Assert.assertEquals(
			existingAppBuilderApp.getUserId(), newAppBuilderApp.getUserId());
		Assert.assertEquals(
			existingAppBuilderApp.getUserName(),
			newAppBuilderApp.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingAppBuilderApp.getCreateDate()),
			Time.getShortTimestamp(newAppBuilderApp.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingAppBuilderApp.getModifiedDate()),
			Time.getShortTimestamp(newAppBuilderApp.getModifiedDate()));
		Assert.assertEquals(
			existingAppBuilderApp.isActive(), newAppBuilderApp.isActive());
		Assert.assertEquals(
			existingAppBuilderApp.getDdlRecordSetId(),
			newAppBuilderApp.getDdlRecordSetId());
		Assert.assertEquals(
			existingAppBuilderApp.getDdmStructureId(),
			newAppBuilderApp.getDdmStructureId());
		Assert.assertEquals(
			existingAppBuilderApp.getDdmStructureLayoutId(),
			newAppBuilderApp.getDdmStructureLayoutId());
		Assert.assertEquals(
			existingAppBuilderApp.getDeDataListViewId(),
			newAppBuilderApp.getDeDataListViewId());
		Assert.assertEquals(
			existingAppBuilderApp.getName(), newAppBuilderApp.getName());
		Assert.assertEquals(
			existingAppBuilderApp.getScope(), newAppBuilderApp.getScope());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByDDMStructureId() throws Exception {
		_persistence.countByDDMStructureId(RandomTestUtil.nextLong());

		_persistence.countByDDMStructureId(0L);
	}

	@Test
	public void testCountByG_S() throws Exception {
		_persistence.countByG_S(RandomTestUtil.nextLong(), "");

		_persistence.countByG_S(0L, "null");

		_persistence.countByG_S(0L, (String)null);
	}

	@Test
	public void testCountByC_A() throws Exception {
		_persistence.countByC_A(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByC_A(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByC_S() throws Exception {
		_persistence.countByC_S(RandomTestUtil.nextLong(), "");

		_persistence.countByC_S(0L, "null");

		_persistence.countByC_S(0L, (String)null);
	}

	@Test
	public void testCountByG_C_D() throws Exception {
		_persistence.countByG_C_D(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_C_D(0L, 0L, 0L);
	}

	@Test
	public void testCountByC_A_S() throws Exception {
		_persistence.countByC_A_S(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(), "");

		_persistence.countByC_A_S(0L, RandomTestUtil.randomBoolean(), "null");

		_persistence.countByC_A_S(
			0L, RandomTestUtil.randomBoolean(), (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AppBuilderApp newAppBuilderApp = addAppBuilderApp();

		AppBuilderApp existingAppBuilderApp = _persistence.findByPrimaryKey(
			newAppBuilderApp.getPrimaryKey());

		Assert.assertEquals(existingAppBuilderApp, newAppBuilderApp);
	}

	@Test(expected = NoSuchAppException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(
			0, QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AppBuilderApp> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"AppBuilderApp", "uuid", true, "appBuilderAppId", true, "groupId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "active", true,
			"ddlRecordSetId", true, "ddmStructureId", true,
			"ddmStructureLayoutId", true, "deDataListViewId", true, "name",
			true, "scope", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AppBuilderApp newAppBuilderApp = addAppBuilderApp();

		AppBuilderApp existingAppBuilderApp = _persistence.fetchByPrimaryKey(
			newAppBuilderApp.getPrimaryKey());

		Assert.assertEquals(existingAppBuilderApp, newAppBuilderApp);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AppBuilderApp missingAppBuilderApp = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAppBuilderApp);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AppBuilderApp newAppBuilderApp1 = addAppBuilderApp();
		AppBuilderApp newAppBuilderApp2 = addAppBuilderApp();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAppBuilderApp1.getPrimaryKey());
		primaryKeys.add(newAppBuilderApp2.getPrimaryKey());

		Map<Serializable, AppBuilderApp> appBuilderApps =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, appBuilderApps.size());
		Assert.assertEquals(
			newAppBuilderApp1,
			appBuilderApps.get(newAppBuilderApp1.getPrimaryKey()));
		Assert.assertEquals(
			newAppBuilderApp2,
			appBuilderApps.get(newAppBuilderApp2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AppBuilderApp> appBuilderApps =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(appBuilderApps.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AppBuilderApp newAppBuilderApp = addAppBuilderApp();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAppBuilderApp.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AppBuilderApp> appBuilderApps =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, appBuilderApps.size());
		Assert.assertEquals(
			newAppBuilderApp,
			appBuilderApps.get(newAppBuilderApp.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AppBuilderApp> appBuilderApps =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(appBuilderApps.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AppBuilderApp newAppBuilderApp = addAppBuilderApp();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAppBuilderApp.getPrimaryKey());

		Map<Serializable, AppBuilderApp> appBuilderApps =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, appBuilderApps.size());
		Assert.assertEquals(
			newAppBuilderApp,
			appBuilderApps.get(newAppBuilderApp.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AppBuilderAppLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<AppBuilderApp>() {

				@Override
				public void performAction(AppBuilderApp appBuilderApp) {
					Assert.assertNotNull(appBuilderApp);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AppBuilderApp newAppBuilderApp = addAppBuilderApp();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderApp.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"appBuilderAppId", newAppBuilderApp.getAppBuilderAppId()));

		List<AppBuilderApp> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		AppBuilderApp existingAppBuilderApp = result.get(0);

		Assert.assertEquals(existingAppBuilderApp, newAppBuilderApp);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderApp.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"appBuilderAppId", RandomTestUtil.nextLong()));

		List<AppBuilderApp> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AppBuilderApp newAppBuilderApp = addAppBuilderApp();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderApp.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("appBuilderAppId"));

		Object newAppBuilderAppId = newAppBuilderApp.getAppBuilderAppId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"appBuilderAppId", new Object[] {newAppBuilderAppId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAppBuilderAppId = result.get(0);

		Assert.assertEquals(existingAppBuilderAppId, newAppBuilderAppId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderApp.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("appBuilderAppId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"appBuilderAppId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AppBuilderApp newAppBuilderApp = addAppBuilderApp();

		_persistence.clearCache();

		AppBuilderApp existingAppBuilderApp = _persistence.findByPrimaryKey(
			newAppBuilderApp.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingAppBuilderApp.getUuid(),
				ReflectionTestUtil.invoke(
					existingAppBuilderApp, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingAppBuilderApp.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingAppBuilderApp, "getOriginalGroupId", new Class<?>[0]));
	}

	protected AppBuilderApp addAppBuilderApp() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AppBuilderApp appBuilderApp = _persistence.create(pk);

		appBuilderApp.setUuid(RandomTestUtil.randomString());

		appBuilderApp.setGroupId(RandomTestUtil.nextLong());

		appBuilderApp.setCompanyId(RandomTestUtil.nextLong());

		appBuilderApp.setUserId(RandomTestUtil.nextLong());

		appBuilderApp.setUserName(RandomTestUtil.randomString());

		appBuilderApp.setCreateDate(RandomTestUtil.nextDate());

		appBuilderApp.setModifiedDate(RandomTestUtil.nextDate());

		appBuilderApp.setActive(RandomTestUtil.randomBoolean());

		appBuilderApp.setDdlRecordSetId(RandomTestUtil.nextLong());

		appBuilderApp.setDdmStructureId(RandomTestUtil.nextLong());

		appBuilderApp.setDdmStructureLayoutId(RandomTestUtil.nextLong());

		appBuilderApp.setDeDataListViewId(RandomTestUtil.nextLong());

		appBuilderApp.setName(RandomTestUtil.randomString());

		appBuilderApp.setScope(RandomTestUtil.randomString());

		_appBuilderApps.add(_persistence.update(appBuilderApp));

		return appBuilderApp;
	}

	private List<AppBuilderApp> _appBuilderApps =
		new ArrayList<AppBuilderApp>();
	private AppBuilderAppPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}