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

package com.liferay.dynamic.data.mapping.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.dynamic.data.mapping.exception.NoSuchDataProviderException;
import com.liferay.dynamic.data.mapping.model.DDMDataProvider;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.persistence.DDMDataProviderPersistence;
import com.liferay.dynamic.data.mapping.service.persistence.DDMDataProviderUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class DDMDataProviderPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = DDMDataProviderUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DDMDataProvider> iterator = _ddmDataProviders.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMDataProvider ddmDataProvider = _persistence.create(pk);

		Assert.assertNotNull(ddmDataProvider);

		Assert.assertEquals(ddmDataProvider.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDMDataProvider newDDMDataProvider = addDDMDataProvider();

		_persistence.remove(newDDMDataProvider);

		DDMDataProvider existingDDMDataProvider = _persistence.fetchByPrimaryKey(newDDMDataProvider.getPrimaryKey());

		Assert.assertNull(existingDDMDataProvider);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDMDataProvider();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMDataProvider newDDMDataProvider = _persistence.create(pk);

		newDDMDataProvider.setUuid(RandomTestUtil.randomString());

		newDDMDataProvider.setGroupId(RandomTestUtil.nextLong());

		newDDMDataProvider.setCompanyId(RandomTestUtil.nextLong());

		newDDMDataProvider.setUserId(RandomTestUtil.nextLong());

		newDDMDataProvider.setUserName(RandomTestUtil.randomString());

		newDDMDataProvider.setCreateDate(RandomTestUtil.nextDate());

		newDDMDataProvider.setModifiedDate(RandomTestUtil.nextDate());

		newDDMDataProvider.setName(RandomTestUtil.randomString());

		newDDMDataProvider.setDescription(RandomTestUtil.randomString());

		newDDMDataProvider.setDefinition(RandomTestUtil.randomString());

		_ddmDataProviders.add(_persistence.update(newDDMDataProvider));

		DDMDataProvider existingDDMDataProvider = _persistence.findByPrimaryKey(newDDMDataProvider.getPrimaryKey());

		Assert.assertEquals(existingDDMDataProvider.getUuid(),
			newDDMDataProvider.getUuid());
		Assert.assertEquals(existingDDMDataProvider.getDataProviderId(),
			newDDMDataProvider.getDataProviderId());
		Assert.assertEquals(existingDDMDataProvider.getGroupId(),
			newDDMDataProvider.getGroupId());
		Assert.assertEquals(existingDDMDataProvider.getCompanyId(),
			newDDMDataProvider.getCompanyId());
		Assert.assertEquals(existingDDMDataProvider.getUserId(),
			newDDMDataProvider.getUserId());
		Assert.assertEquals(existingDDMDataProvider.getUserName(),
			newDDMDataProvider.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDMDataProvider.getCreateDate()),
			Time.getShortTimestamp(newDDMDataProvider.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDMDataProvider.getModifiedDate()),
			Time.getShortTimestamp(newDDMDataProvider.getModifiedDate()));
		Assert.assertEquals(existingDDMDataProvider.getName(),
			newDDMDataProvider.getName());
		Assert.assertEquals(existingDDMDataProvider.getDescription(),
			newDDMDataProvider.getDescription());
		Assert.assertEquals(existingDDMDataProvider.getDefinition(),
			newDDMDataProvider.getDefinition());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUUID_G(StringPool.NULL, 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByGroupIdArrayable() throws Exception {
		_persistence.countByGroupId(new long[] { RandomTestUtil.nextLong(), 0L });
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMDataProvider newDDMDataProvider = addDDMDataProvider();

		DDMDataProvider existingDDMDataProvider = _persistence.findByPrimaryKey(newDDMDataProvider.getPrimaryKey());

		Assert.assertEquals(existingDDMDataProvider, newDDMDataProvider);
	}

	@Test(expected = NoSuchDataProviderException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<DDMDataProvider> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("DDMDataProvider", "uuid",
			true, "dataProviderId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMDataProvider newDDMDataProvider = addDDMDataProvider();

		DDMDataProvider existingDDMDataProvider = _persistence.fetchByPrimaryKey(newDDMDataProvider.getPrimaryKey());

		Assert.assertEquals(existingDDMDataProvider, newDDMDataProvider);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMDataProvider missingDDMDataProvider = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDMDataProvider);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		DDMDataProvider newDDMDataProvider1 = addDDMDataProvider();
		DDMDataProvider newDDMDataProvider2 = addDDMDataProvider();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMDataProvider1.getPrimaryKey());
		primaryKeys.add(newDDMDataProvider2.getPrimaryKey());

		Map<Serializable, DDMDataProvider> ddmDataProviders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ddmDataProviders.size());
		Assert.assertEquals(newDDMDataProvider1,
			ddmDataProviders.get(newDDMDataProvider1.getPrimaryKey()));
		Assert.assertEquals(newDDMDataProvider2,
			ddmDataProviders.get(newDDMDataProvider2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DDMDataProvider> ddmDataProviders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddmDataProviders.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		DDMDataProvider newDDMDataProvider = addDDMDataProvider();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMDataProvider.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DDMDataProvider> ddmDataProviders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddmDataProviders.size());
		Assert.assertEquals(newDDMDataProvider,
			ddmDataProviders.get(newDDMDataProvider.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DDMDataProvider> ddmDataProviders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddmDataProviders.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		DDMDataProvider newDDMDataProvider = addDDMDataProvider();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMDataProvider.getPrimaryKey());

		Map<Serializable, DDMDataProvider> ddmDataProviders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddmDataProviders.size());
		Assert.assertEquals(newDDMDataProvider,
			ddmDataProviders.get(newDDMDataProvider.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = DDMDataProviderLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<DDMDataProvider>() {
				@Override
				public void performAction(DDMDataProvider ddmDataProvider) {
					Assert.assertNotNull(ddmDataProvider);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMDataProvider newDDMDataProvider = addDDMDataProvider();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMDataProvider.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("dataProviderId",
				newDDMDataProvider.getDataProviderId()));

		List<DDMDataProvider> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDMDataProvider existingDDMDataProvider = result.get(0);

		Assert.assertEquals(existingDDMDataProvider, newDDMDataProvider);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMDataProvider.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("dataProviderId",
				RandomTestUtil.nextLong()));

		List<DDMDataProvider> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMDataProvider newDDMDataProvider = addDDMDataProvider();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMDataProvider.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"dataProviderId"));

		Object newDataProviderId = newDDMDataProvider.getDataProviderId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("dataProviderId",
				new Object[] { newDataProviderId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingDataProviderId = result.get(0);

		Assert.assertEquals(existingDataProviderId, newDataProviderId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMDataProvider.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"dataProviderId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("dataProviderId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		DDMDataProvider newDDMDataProvider = addDDMDataProvider();

		_persistence.clearCache();

		DDMDataProvider existingDDMDataProvider = _persistence.findByPrimaryKey(newDDMDataProvider.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingDDMDataProvider.getUuid(),
				ReflectionTestUtil.invoke(existingDDMDataProvider,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingDDMDataProvider.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingDDMDataProvider,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected DDMDataProvider addDDMDataProvider() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMDataProvider ddmDataProvider = _persistence.create(pk);

		ddmDataProvider.setUuid(RandomTestUtil.randomString());

		ddmDataProvider.setGroupId(RandomTestUtil.nextLong());

		ddmDataProvider.setCompanyId(RandomTestUtil.nextLong());

		ddmDataProvider.setUserId(RandomTestUtil.nextLong());

		ddmDataProvider.setUserName(RandomTestUtil.randomString());

		ddmDataProvider.setCreateDate(RandomTestUtil.nextDate());

		ddmDataProvider.setModifiedDate(RandomTestUtil.nextDate());

		ddmDataProvider.setName(RandomTestUtil.randomString());

		ddmDataProvider.setDescription(RandomTestUtil.randomString());

		ddmDataProvider.setDefinition(RandomTestUtil.randomString());

		_ddmDataProviders.add(_persistence.update(ddmDataProvider));

		return ddmDataProvider;
	}

	private List<DDMDataProvider> _ddmDataProviders = new ArrayList<DDMDataProvider>();
	private DDMDataProviderPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}