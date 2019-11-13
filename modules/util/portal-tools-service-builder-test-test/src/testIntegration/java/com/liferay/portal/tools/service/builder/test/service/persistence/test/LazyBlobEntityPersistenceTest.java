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

package com.liferay.portal.tools.service.builder.test.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
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
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLazyBlobEntityException;
import com.liferay.portal.tools.service.builder.test.model.LazyBlobEntity;
import com.liferay.portal.tools.service.builder.test.service.LazyBlobEntityLocalServiceUtil;
import com.liferay.portal.tools.service.builder.test.service.persistence.LazyBlobEntityPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.LazyBlobEntityUtil;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import java.sql.Blob;

import java.util.ArrayList;
import java.util.Arrays;
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
public class LazyBlobEntityPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.tools.service.builder.test.service"));

	@Before
	public void setUp() {
		_persistence = LazyBlobEntityUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LazyBlobEntity> iterator = _lazyBlobEntities.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LazyBlobEntity lazyBlobEntity = _persistence.create(pk);

		Assert.assertNotNull(lazyBlobEntity);

		Assert.assertEquals(lazyBlobEntity.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LazyBlobEntity newLazyBlobEntity = addLazyBlobEntity();

		_persistence.remove(newLazyBlobEntity);

		LazyBlobEntity existingLazyBlobEntity = _persistence.fetchByPrimaryKey(
			newLazyBlobEntity.getPrimaryKey());

		Assert.assertNull(existingLazyBlobEntity);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLazyBlobEntity();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LazyBlobEntity newLazyBlobEntity = _persistence.create(pk);

		newLazyBlobEntity.setUuid(RandomTestUtil.randomString());

		newLazyBlobEntity.setGroupId(RandomTestUtil.nextLong());
		String newBlob1String = RandomTestUtil.randomString();

		byte[] newBlob1Bytes = newBlob1String.getBytes("UTF-8");

		Blob newBlob1Blob = new OutputBlob(
			new ByteArrayInputStream(newBlob1Bytes), newBlob1Bytes.length);

		newLazyBlobEntity.setBlob1(newBlob1Blob);
		String newBlob2String = RandomTestUtil.randomString();

		byte[] newBlob2Bytes = newBlob2String.getBytes("UTF-8");

		Blob newBlob2Blob = new OutputBlob(
			new ByteArrayInputStream(newBlob2Bytes), newBlob2Bytes.length);

		newLazyBlobEntity.setBlob2(newBlob2Blob);

		_lazyBlobEntities.add(_persistence.update(newLazyBlobEntity));

		LazyBlobEntity existingLazyBlobEntity = _persistence.findByPrimaryKey(
			newLazyBlobEntity.getPrimaryKey());

		Assert.assertEquals(
			existingLazyBlobEntity.getUuid(), newLazyBlobEntity.getUuid());
		Assert.assertEquals(
			existingLazyBlobEntity.getLazyBlobEntityId(),
			newLazyBlobEntity.getLazyBlobEntityId());
		Assert.assertEquals(
			existingLazyBlobEntity.getGroupId(),
			newLazyBlobEntity.getGroupId());
		Blob existingBlob1 = existingLazyBlobEntity.getBlob1();

		Assert.assertTrue(
			Arrays.equals(
				existingBlob1.getBytes(1, (int)existingBlob1.length()),
				newBlob1Bytes));
		Blob existingBlob2 = existingLazyBlobEntity.getBlob2();

		Assert.assertTrue(
			Arrays.equals(
				existingBlob2.getBytes(1, (int)existingBlob2.length()),
				newBlob2Bytes));
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
	public void testFindByPrimaryKeyExisting() throws Exception {
		LazyBlobEntity newLazyBlobEntity = addLazyBlobEntity();

		LazyBlobEntity existingLazyBlobEntity = _persistence.findByPrimaryKey(
			newLazyBlobEntity.getPrimaryKey());

		Assert.assertEquals(existingLazyBlobEntity, newLazyBlobEntity);
	}

	@Test(expected = NoSuchLazyBlobEntityException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LazyBlobEntity> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"LazyBlobEntity", "uuid", true, "lazyBlobEntityId", true, "groupId",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LazyBlobEntity newLazyBlobEntity = addLazyBlobEntity();

		LazyBlobEntity existingLazyBlobEntity = _persistence.fetchByPrimaryKey(
			newLazyBlobEntity.getPrimaryKey());

		Assert.assertEquals(existingLazyBlobEntity, newLazyBlobEntity);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LazyBlobEntity missingLazyBlobEntity = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingLazyBlobEntity);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LazyBlobEntity newLazyBlobEntity1 = addLazyBlobEntity();
		LazyBlobEntity newLazyBlobEntity2 = addLazyBlobEntity();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLazyBlobEntity1.getPrimaryKey());
		primaryKeys.add(newLazyBlobEntity2.getPrimaryKey());

		Map<Serializable, LazyBlobEntity> lazyBlobEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, lazyBlobEntities.size());
		Assert.assertEquals(
			newLazyBlobEntity1,
			lazyBlobEntities.get(newLazyBlobEntity1.getPrimaryKey()));
		Assert.assertEquals(
			newLazyBlobEntity2,
			lazyBlobEntities.get(newLazyBlobEntity2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LazyBlobEntity> lazyBlobEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(lazyBlobEntities.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LazyBlobEntity newLazyBlobEntity = addLazyBlobEntity();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLazyBlobEntity.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LazyBlobEntity> lazyBlobEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, lazyBlobEntities.size());
		Assert.assertEquals(
			newLazyBlobEntity,
			lazyBlobEntities.get(newLazyBlobEntity.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LazyBlobEntity> lazyBlobEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(lazyBlobEntities.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LazyBlobEntity newLazyBlobEntity = addLazyBlobEntity();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLazyBlobEntity.getPrimaryKey());

		Map<Serializable, LazyBlobEntity> lazyBlobEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, lazyBlobEntities.size());
		Assert.assertEquals(
			newLazyBlobEntity,
			lazyBlobEntities.get(newLazyBlobEntity.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LazyBlobEntityLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<LazyBlobEntity>() {

				@Override
				public void performAction(LazyBlobEntity lazyBlobEntity) {
					Assert.assertNotNull(lazyBlobEntity);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LazyBlobEntity newLazyBlobEntity = addLazyBlobEntity();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LazyBlobEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"lazyBlobEntityId", newLazyBlobEntity.getLazyBlobEntityId()));

		List<LazyBlobEntity> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		LazyBlobEntity existingLazyBlobEntity = result.get(0);

		Assert.assertEquals(existingLazyBlobEntity, newLazyBlobEntity);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LazyBlobEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"lazyBlobEntityId", RandomTestUtil.nextLong()));

		List<LazyBlobEntity> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LazyBlobEntity newLazyBlobEntity = addLazyBlobEntity();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LazyBlobEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("lazyBlobEntityId"));

		Object newLazyBlobEntityId = newLazyBlobEntity.getLazyBlobEntityId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"lazyBlobEntityId", new Object[] {newLazyBlobEntityId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLazyBlobEntityId = result.get(0);

		Assert.assertEquals(existingLazyBlobEntityId, newLazyBlobEntityId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LazyBlobEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("lazyBlobEntityId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"lazyBlobEntityId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LazyBlobEntity newLazyBlobEntity = addLazyBlobEntity();

		_persistence.clearCache();

		LazyBlobEntity existingLazyBlobEntity = _persistence.findByPrimaryKey(
			newLazyBlobEntity.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingLazyBlobEntity.getUuid(),
				ReflectionTestUtil.invoke(
					existingLazyBlobEntity, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingLazyBlobEntity.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLazyBlobEntity, "getOriginalGroupId", new Class<?>[0]));
	}

	protected LazyBlobEntity addLazyBlobEntity() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LazyBlobEntity lazyBlobEntity = _persistence.create(pk);

		lazyBlobEntity.setUuid(RandomTestUtil.randomString());

		lazyBlobEntity.setGroupId(RandomTestUtil.nextLong());
		String blob1String = RandomTestUtil.randomString();

		byte[] blob1Bytes = blob1String.getBytes("UTF-8");

		Blob blob1Blob = new OutputBlob(
			new ByteArrayInputStream(blob1Bytes), blob1Bytes.length);

		lazyBlobEntity.setBlob1(blob1Blob);
		String blob2String = RandomTestUtil.randomString();

		byte[] blob2Bytes = blob2String.getBytes("UTF-8");

		Blob blob2Blob = new OutputBlob(
			new ByteArrayInputStream(blob2Bytes), blob2Bytes.length);

		lazyBlobEntity.setBlob2(blob2Blob);

		_lazyBlobEntities.add(_persistence.update(lazyBlobEntity));

		return lazyBlobEntity;
	}

	private List<LazyBlobEntity> _lazyBlobEntities =
		new ArrayList<LazyBlobEntity>();
	private LazyBlobEntityPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}