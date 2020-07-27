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

import com.liferay.app.builder.exception.NoSuchAppVersionException;
import com.liferay.app.builder.model.AppBuilderAppVersion;
import com.liferay.app.builder.service.AppBuilderAppVersionLocalServiceUtil;
import com.liferay.app.builder.service.persistence.AppBuilderAppVersionPersistence;
import com.liferay.app.builder.service.persistence.AppBuilderAppVersionUtil;
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
public class AppBuilderAppVersionPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.app.builder.service"));

	@Before
	public void setUp() {
		_persistence = AppBuilderAppVersionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AppBuilderAppVersion> iterator =
			_appBuilderAppVersions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AppBuilderAppVersion appBuilderAppVersion = _persistence.create(pk);

		Assert.assertNotNull(appBuilderAppVersion);

		Assert.assertEquals(appBuilderAppVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AppBuilderAppVersion newAppBuilderAppVersion =
			addAppBuilderAppVersion();

		_persistence.remove(newAppBuilderAppVersion);

		AppBuilderAppVersion existingAppBuilderAppVersion =
			_persistence.fetchByPrimaryKey(
				newAppBuilderAppVersion.getPrimaryKey());

		Assert.assertNull(existingAppBuilderAppVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAppBuilderAppVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AppBuilderAppVersion newAppBuilderAppVersion = _persistence.create(pk);

		newAppBuilderAppVersion.setUuid(RandomTestUtil.randomString());

		newAppBuilderAppVersion.setGroupId(RandomTestUtil.nextLong());

		newAppBuilderAppVersion.setCompanyId(RandomTestUtil.nextLong());

		newAppBuilderAppVersion.setUserId(RandomTestUtil.nextLong());

		newAppBuilderAppVersion.setUserName(RandomTestUtil.randomString());

		newAppBuilderAppVersion.setCreateDate(RandomTestUtil.nextDate());

		newAppBuilderAppVersion.setModifiedDate(RandomTestUtil.nextDate());

		newAppBuilderAppVersion.setAppBuilderAppId(RandomTestUtil.nextLong());

		newAppBuilderAppVersion.setDdlRecordSetId(RandomTestUtil.nextLong());

		newAppBuilderAppVersion.setDdmStructureId(RandomTestUtil.nextLong());

		newAppBuilderAppVersion.setDdmStructureLayoutId(
			RandomTestUtil.nextLong());

		newAppBuilderAppVersion.setVersion(RandomTestUtil.randomString());

		_appBuilderAppVersions.add(
			_persistence.update(newAppBuilderAppVersion));

		AppBuilderAppVersion existingAppBuilderAppVersion =
			_persistence.findByPrimaryKey(
				newAppBuilderAppVersion.getPrimaryKey());

		Assert.assertEquals(
			existingAppBuilderAppVersion.getUuid(),
			newAppBuilderAppVersion.getUuid());
		Assert.assertEquals(
			existingAppBuilderAppVersion.getAppBuilderAppVersionId(),
			newAppBuilderAppVersion.getAppBuilderAppVersionId());
		Assert.assertEquals(
			existingAppBuilderAppVersion.getGroupId(),
			newAppBuilderAppVersion.getGroupId());
		Assert.assertEquals(
			existingAppBuilderAppVersion.getCompanyId(),
			newAppBuilderAppVersion.getCompanyId());
		Assert.assertEquals(
			existingAppBuilderAppVersion.getUserId(),
			newAppBuilderAppVersion.getUserId());
		Assert.assertEquals(
			existingAppBuilderAppVersion.getUserName(),
			newAppBuilderAppVersion.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingAppBuilderAppVersion.getCreateDate()),
			Time.getShortTimestamp(newAppBuilderAppVersion.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingAppBuilderAppVersion.getModifiedDate()),
			Time.getShortTimestamp(newAppBuilderAppVersion.getModifiedDate()));
		Assert.assertEquals(
			existingAppBuilderAppVersion.getAppBuilderAppId(),
			newAppBuilderAppVersion.getAppBuilderAppId());
		Assert.assertEquals(
			existingAppBuilderAppVersion.getDdlRecordSetId(),
			newAppBuilderAppVersion.getDdlRecordSetId());
		Assert.assertEquals(
			existingAppBuilderAppVersion.getDdmStructureId(),
			newAppBuilderAppVersion.getDdmStructureId());
		Assert.assertEquals(
			existingAppBuilderAppVersion.getDdmStructureLayoutId(),
			newAppBuilderAppVersion.getDdmStructureLayoutId());
		Assert.assertEquals(
			existingAppBuilderAppVersion.getVersion(),
			newAppBuilderAppVersion.getVersion());
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
	public void testCountByAppBuilderAppId() throws Exception {
		_persistence.countByAppBuilderAppId(RandomTestUtil.nextLong());

		_persistence.countByAppBuilderAppId(0L);
	}

	@Test
	public void testCountByA_V() throws Exception {
		_persistence.countByA_V(RandomTestUtil.nextLong(), "");

		_persistence.countByA_V(0L, "null");

		_persistence.countByA_V(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AppBuilderAppVersion newAppBuilderAppVersion =
			addAppBuilderAppVersion();

		AppBuilderAppVersion existingAppBuilderAppVersion =
			_persistence.findByPrimaryKey(
				newAppBuilderAppVersion.getPrimaryKey());

		Assert.assertEquals(
			existingAppBuilderAppVersion, newAppBuilderAppVersion);
	}

	@Test(expected = NoSuchAppVersionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AppBuilderAppVersion> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"AppBuilderAppVersion", "uuid", true, "appBuilderAppVersionId",
			true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"appBuilderAppId", true, "ddlRecordSetId", true, "ddmStructureId",
			true, "ddmStructureLayoutId", true, "version", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AppBuilderAppVersion newAppBuilderAppVersion =
			addAppBuilderAppVersion();

		AppBuilderAppVersion existingAppBuilderAppVersion =
			_persistence.fetchByPrimaryKey(
				newAppBuilderAppVersion.getPrimaryKey());

		Assert.assertEquals(
			existingAppBuilderAppVersion, newAppBuilderAppVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AppBuilderAppVersion missingAppBuilderAppVersion =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAppBuilderAppVersion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AppBuilderAppVersion newAppBuilderAppVersion1 =
			addAppBuilderAppVersion();
		AppBuilderAppVersion newAppBuilderAppVersion2 =
			addAppBuilderAppVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAppBuilderAppVersion1.getPrimaryKey());
		primaryKeys.add(newAppBuilderAppVersion2.getPrimaryKey());

		Map<Serializable, AppBuilderAppVersion> appBuilderAppVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, appBuilderAppVersions.size());
		Assert.assertEquals(
			newAppBuilderAppVersion1,
			appBuilderAppVersions.get(
				newAppBuilderAppVersion1.getPrimaryKey()));
		Assert.assertEquals(
			newAppBuilderAppVersion2,
			appBuilderAppVersions.get(
				newAppBuilderAppVersion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AppBuilderAppVersion> appBuilderAppVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(appBuilderAppVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AppBuilderAppVersion newAppBuilderAppVersion =
			addAppBuilderAppVersion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAppBuilderAppVersion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AppBuilderAppVersion> appBuilderAppVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, appBuilderAppVersions.size());
		Assert.assertEquals(
			newAppBuilderAppVersion,
			appBuilderAppVersions.get(newAppBuilderAppVersion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AppBuilderAppVersion> appBuilderAppVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(appBuilderAppVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AppBuilderAppVersion newAppBuilderAppVersion =
			addAppBuilderAppVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAppBuilderAppVersion.getPrimaryKey());

		Map<Serializable, AppBuilderAppVersion> appBuilderAppVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, appBuilderAppVersions.size());
		Assert.assertEquals(
			newAppBuilderAppVersion,
			appBuilderAppVersions.get(newAppBuilderAppVersion.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AppBuilderAppVersionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<AppBuilderAppVersion>() {

				@Override
				public void performAction(
					AppBuilderAppVersion appBuilderAppVersion) {

					Assert.assertNotNull(appBuilderAppVersion);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AppBuilderAppVersion newAppBuilderAppVersion =
			addAppBuilderAppVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderAppVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"appBuilderAppVersionId",
				newAppBuilderAppVersion.getAppBuilderAppVersionId()));

		List<AppBuilderAppVersion> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		AppBuilderAppVersion existingAppBuilderAppVersion = result.get(0);

		Assert.assertEquals(
			existingAppBuilderAppVersion, newAppBuilderAppVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderAppVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"appBuilderAppVersionId", RandomTestUtil.nextLong()));

		List<AppBuilderAppVersion> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AppBuilderAppVersion newAppBuilderAppVersion =
			addAppBuilderAppVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderAppVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("appBuilderAppVersionId"));

		Object newAppBuilderAppVersionId =
			newAppBuilderAppVersion.getAppBuilderAppVersionId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"appBuilderAppVersionId",
				new Object[] {newAppBuilderAppVersionId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAppBuilderAppVersionId = result.get(0);

		Assert.assertEquals(
			existingAppBuilderAppVersionId, newAppBuilderAppVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderAppVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("appBuilderAppVersionId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"appBuilderAppVersionId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AppBuilderAppVersion newAppBuilderAppVersion =
			addAppBuilderAppVersion();

		_persistence.clearCache();

		AppBuilderAppVersion existingAppBuilderAppVersion =
			_persistence.findByPrimaryKey(
				newAppBuilderAppVersion.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingAppBuilderAppVersion.getUuid(),
				ReflectionTestUtil.invoke(
					existingAppBuilderAppVersion, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingAppBuilderAppVersion.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingAppBuilderAppVersion, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingAppBuilderAppVersion.getAppBuilderAppId()),
			ReflectionTestUtil.<Long>invoke(
				existingAppBuilderAppVersion, "getOriginalAppBuilderAppId",
				new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingAppBuilderAppVersion.getVersion(),
				ReflectionTestUtil.invoke(
					existingAppBuilderAppVersion, "getOriginalVersion",
					new Class<?>[0])));
	}

	protected AppBuilderAppVersion addAppBuilderAppVersion() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AppBuilderAppVersion appBuilderAppVersion = _persistence.create(pk);

		appBuilderAppVersion.setUuid(RandomTestUtil.randomString());

		appBuilderAppVersion.setGroupId(RandomTestUtil.nextLong());

		appBuilderAppVersion.setCompanyId(RandomTestUtil.nextLong());

		appBuilderAppVersion.setUserId(RandomTestUtil.nextLong());

		appBuilderAppVersion.setUserName(RandomTestUtil.randomString());

		appBuilderAppVersion.setCreateDate(RandomTestUtil.nextDate());

		appBuilderAppVersion.setModifiedDate(RandomTestUtil.nextDate());

		appBuilderAppVersion.setAppBuilderAppId(RandomTestUtil.nextLong());

		appBuilderAppVersion.setDdlRecordSetId(RandomTestUtil.nextLong());

		appBuilderAppVersion.setDdmStructureId(RandomTestUtil.nextLong());

		appBuilderAppVersion.setDdmStructureLayoutId(RandomTestUtil.nextLong());

		appBuilderAppVersion.setVersion(RandomTestUtil.randomString());

		_appBuilderAppVersions.add(_persistence.update(appBuilderAppVersion));

		return appBuilderAppVersion;
	}

	private List<AppBuilderAppVersion> _appBuilderAppVersions =
		new ArrayList<AppBuilderAppVersion>();
	private AppBuilderAppVersionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}