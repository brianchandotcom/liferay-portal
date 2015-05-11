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

package com.liferay.portlet.documentlibrary.service.persistence.test;

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

import com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryTypePersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryTypeUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
public class DLFileEntryTypePersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = DLFileEntryTypeUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DLFileEntryType> iterator = _dlFileEntryTypes.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntryType dlFileEntryType = _persistence.create(pk);

		Assert.assertNotNull(dlFileEntryType);

		Assert.assertEquals(dlFileEntryType.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		_persistence.remove(newDLFileEntryType);

		DLFileEntryType existingDLFileEntryType = _persistence.fetchByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		Assert.assertNull(existingDLFileEntryType);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDLFileEntryType();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntryType newDLFileEntryType = _persistence.create(pk);

		newDLFileEntryType.setUuid(RandomTestUtil.randomString());

		newDLFileEntryType.setGroupId(RandomTestUtil.nextLong());

		newDLFileEntryType.setCompanyId(RandomTestUtil.nextLong());

		newDLFileEntryType.setUserId(RandomTestUtil.nextLong());

		newDLFileEntryType.setUserName(RandomTestUtil.randomString());

		newDLFileEntryType.setCreateDate(RandomTestUtil.nextDate());

		newDLFileEntryType.setModifiedDate(RandomTestUtil.nextDate());

		newDLFileEntryType.setFileEntryTypeKey(RandomTestUtil.randomString());

		newDLFileEntryType.setName(RandomTestUtil.randomString());

		newDLFileEntryType.setDescription(RandomTestUtil.randomString());

		_dlFileEntryTypes.add(_persistence.update(newDLFileEntryType));

		DLFileEntryType existingDLFileEntryType = _persistence.findByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntryType.getUuid(),
			newDLFileEntryType.getUuid());
		Assert.assertEquals(existingDLFileEntryType.getFileEntryTypeId(),
			newDLFileEntryType.getFileEntryTypeId());
		Assert.assertEquals(existingDLFileEntryType.getGroupId(),
			newDLFileEntryType.getGroupId());
		Assert.assertEquals(existingDLFileEntryType.getCompanyId(),
			newDLFileEntryType.getCompanyId());
		Assert.assertEquals(existingDLFileEntryType.getUserId(),
			newDLFileEntryType.getUserId());
		Assert.assertEquals(existingDLFileEntryType.getUserName(),
			newDLFileEntryType.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFileEntryType.getCreateDate()),
			Time.getShortTimestamp(newDLFileEntryType.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFileEntryType.getModifiedDate()),
			Time.getShortTimestamp(newDLFileEntryType.getModifiedDate()));
		Assert.assertEquals(existingDLFileEntryType.getFileEntryTypeKey(),
			newDLFileEntryType.getFileEntryTypeKey());
		Assert.assertEquals(existingDLFileEntryType.getName(),
			newDLFileEntryType.getName());
		Assert.assertEquals(existingDLFileEntryType.getDescription(),
			newDLFileEntryType.getDescription());
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
	public void testCountByG_F() throws Exception {
		_persistence.countByG_F(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByG_F(0L, StringPool.NULL);

		_persistence.countByG_F(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		DLFileEntryType existingDLFileEntryType = _persistence.findByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntryType, newDLFileEntryType);
	}

	@Test(expected = NoSuchFileEntryTypeException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<DLFileEntryType> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("DLFileEntryType", "uuid",
			true, "fileEntryTypeId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "fileEntryTypeKey", true, "name", true,
			"description", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		DLFileEntryType existingDLFileEntryType = _persistence.fetchByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntryType, newDLFileEntryType);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntryType missingDLFileEntryType = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDLFileEntryType);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		DLFileEntryType newDLFileEntryType1 = addDLFileEntryType();
		DLFileEntryType newDLFileEntryType2 = addDLFileEntryType();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLFileEntryType1.getPrimaryKey());
		primaryKeys.add(newDLFileEntryType2.getPrimaryKey());

		Map<Serializable, DLFileEntryType> dlFileEntryTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, dlFileEntryTypes.size());
		Assert.assertEquals(newDLFileEntryType1,
			dlFileEntryTypes.get(newDLFileEntryType1.getPrimaryKey()));
		Assert.assertEquals(newDLFileEntryType2,
			dlFileEntryTypes.get(newDLFileEntryType2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DLFileEntryType> dlFileEntryTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(dlFileEntryTypes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLFileEntryType.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DLFileEntryType> dlFileEntryTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, dlFileEntryTypes.size());
		Assert.assertEquals(newDLFileEntryType,
			dlFileEntryTypes.get(newDLFileEntryType.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DLFileEntryType> dlFileEntryTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(dlFileEntryTypes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDLFileEntryType.getPrimaryKey());

		Map<Serializable, DLFileEntryType> dlFileEntryTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, dlFileEntryTypes.size());
		Assert.assertEquals(newDLFileEntryType,
			dlFileEntryTypes.get(newDLFileEntryType.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = DLFileEntryTypeLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					DLFileEntryType dlFileEntryType = (DLFileEntryType)object;

					Assert.assertNotNull(dlFileEntryType);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryType.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryTypeId",
				newDLFileEntryType.getFileEntryTypeId()));

		List<DLFileEntryType> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DLFileEntryType existingDLFileEntryType = result.get(0);

		Assert.assertEquals(existingDLFileEntryType, newDLFileEntryType);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryType.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryTypeId",
				RandomTestUtil.nextLong()));

		List<DLFileEntryType> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryType.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fileEntryTypeId"));

		Object newFileEntryTypeId = newDLFileEntryType.getFileEntryTypeId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryTypeId",
				new Object[] { newFileEntryTypeId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFileEntryTypeId = result.get(0);

		Assert.assertEquals(existingFileEntryTypeId, newFileEntryTypeId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryType.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fileEntryTypeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryTypeId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		_persistence.clearCache();

		DLFileEntryType existingDLFileEntryType = _persistence.findByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingDLFileEntryType.getUuid(),
				ReflectionTestUtil.invoke(existingDLFileEntryType,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(existingDLFileEntryType.getGroupId(),
			ReflectionTestUtil.invoke(existingDLFileEntryType,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(existingDLFileEntryType.getGroupId(),
			ReflectionTestUtil.invoke(existingDLFileEntryType,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(
				existingDLFileEntryType.getFileEntryTypeKey(),
				ReflectionTestUtil.invoke(existingDLFileEntryType,
					"getOriginalFileEntryTypeKey", new Class<?>[0])));
	}

	protected DLFileEntryType addDLFileEntryType() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DLFileEntryType dlFileEntryType = _persistence.create(pk);

		dlFileEntryType.setUuid(RandomTestUtil.randomString());

		dlFileEntryType.setGroupId(RandomTestUtil.nextLong());

		dlFileEntryType.setCompanyId(RandomTestUtil.nextLong());

		dlFileEntryType.setUserId(RandomTestUtil.nextLong());

		dlFileEntryType.setUserName(RandomTestUtil.randomString());

		dlFileEntryType.setCreateDate(RandomTestUtil.nextDate());

		dlFileEntryType.setModifiedDate(RandomTestUtil.nextDate());

		dlFileEntryType.setFileEntryTypeKey(RandomTestUtil.randomString());

		dlFileEntryType.setName(RandomTestUtil.randomString());

		dlFileEntryType.setDescription(RandomTestUtil.randomString());

		_dlFileEntryTypes.add(_persistence.update(dlFileEntryType));

		return dlFileEntryType;
	}

	private List<DLFileEntryType> _dlFileEntryTypes = new ArrayList<DLFileEntryType>();
	private DLFileEntryTypePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}