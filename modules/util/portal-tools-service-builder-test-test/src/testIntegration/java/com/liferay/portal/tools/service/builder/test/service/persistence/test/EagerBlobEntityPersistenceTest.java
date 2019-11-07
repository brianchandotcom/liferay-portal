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
import com.liferay.portal.kernel.dao.orm.Session;
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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchEagerBlobEntityException;
import com.liferay.portal.tools.service.builder.test.model.EagerBlobEntity;
import com.liferay.portal.tools.service.builder.test.service.EagerBlobEntityLocalServiceUtil;
import com.liferay.portal.tools.service.builder.test.service.persistence.EagerBlobEntityPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.EagerBlobEntityUtil;

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
public class EagerBlobEntityPersistenceTest {

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
		_persistence = EagerBlobEntityUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<EagerBlobEntity> iterator = _eagerBlobEntities.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		EagerBlobEntity eagerBlobEntity = _persistence.create(pk);

		Assert.assertNotNull(eagerBlobEntity);

		Assert.assertEquals(eagerBlobEntity.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		EagerBlobEntity newEagerBlobEntity = addEagerBlobEntity();

		_persistence.remove(newEagerBlobEntity);

		EagerBlobEntity existingEagerBlobEntity =
			_persistence.fetchByPrimaryKey(newEagerBlobEntity.getPrimaryKey());

		Assert.assertNull(existingEagerBlobEntity);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addEagerBlobEntity();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		EagerBlobEntity newEagerBlobEntity = _persistence.create(pk);

		newEagerBlobEntity.setUuid(RandomTestUtil.randomString());

		newEagerBlobEntity.setGroupId(RandomTestUtil.nextLong());
		String newBlobString = RandomTestUtil.randomString();

		byte[] newBlobBytes = newBlobString.getBytes("UTF-8");

		Blob newBlobBlob = new OutputBlob(
			new ByteArrayInputStream(newBlobBytes), newBlobBytes.length);

		newEagerBlobEntity.setBlob(newBlobBlob);

		_eagerBlobEntities.add(_persistence.update(newEagerBlobEntity));

		Session session = _persistence.openSession();

		session.flush();

		session.clear();

		EagerBlobEntity existingEagerBlobEntity = _persistence.findByPrimaryKey(
			newEagerBlobEntity.getPrimaryKey());

		Assert.assertEquals(
			existingEagerBlobEntity.getUuid(), newEagerBlobEntity.getUuid());
		Assert.assertEquals(
			existingEagerBlobEntity.getEagerBlobEntityId(),
			newEagerBlobEntity.getEagerBlobEntityId());
		Assert.assertEquals(
			existingEagerBlobEntity.getGroupId(),
			newEagerBlobEntity.getGroupId());
		Blob existingBlob = existingEagerBlobEntity.getBlob();

		Assert.assertTrue(
			Arrays.equals(
				existingBlob.getBytes(1, (int)existingBlob.length()),
				newBlobBytes));
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
		EagerBlobEntity newEagerBlobEntity = addEagerBlobEntity();

		EagerBlobEntity existingEagerBlobEntity = _persistence.findByPrimaryKey(
			newEagerBlobEntity.getPrimaryKey());

		Assert.assertEquals(existingEagerBlobEntity, newEagerBlobEntity);
	}

	@Test(expected = NoSuchEagerBlobEntityException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<EagerBlobEntity> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"EagerBlobEntity", "uuid", true, "eagerBlobEntityId", true,
			"groupId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		EagerBlobEntity newEagerBlobEntity = addEagerBlobEntity();

		EagerBlobEntity existingEagerBlobEntity =
			_persistence.fetchByPrimaryKey(newEagerBlobEntity.getPrimaryKey());

		Assert.assertEquals(existingEagerBlobEntity, newEagerBlobEntity);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		EagerBlobEntity missingEagerBlobEntity = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingEagerBlobEntity);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		EagerBlobEntity newEagerBlobEntity1 = addEagerBlobEntity();
		EagerBlobEntity newEagerBlobEntity2 = addEagerBlobEntity();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newEagerBlobEntity1.getPrimaryKey());
		primaryKeys.add(newEagerBlobEntity2.getPrimaryKey());

		Map<Serializable, EagerBlobEntity> eagerBlobEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, eagerBlobEntities.size());
		Assert.assertEquals(
			newEagerBlobEntity1,
			eagerBlobEntities.get(newEagerBlobEntity1.getPrimaryKey()));
		Assert.assertEquals(
			newEagerBlobEntity2,
			eagerBlobEntities.get(newEagerBlobEntity2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, EagerBlobEntity> eagerBlobEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(eagerBlobEntities.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		EagerBlobEntity newEagerBlobEntity = addEagerBlobEntity();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newEagerBlobEntity.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, EagerBlobEntity> eagerBlobEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, eagerBlobEntities.size());
		Assert.assertEquals(
			newEagerBlobEntity,
			eagerBlobEntities.get(newEagerBlobEntity.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, EagerBlobEntity> eagerBlobEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(eagerBlobEntities.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		EagerBlobEntity newEagerBlobEntity = addEagerBlobEntity();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newEagerBlobEntity.getPrimaryKey());

		Map<Serializable, EagerBlobEntity> eagerBlobEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, eagerBlobEntities.size());
		Assert.assertEquals(
			newEagerBlobEntity,
			eagerBlobEntities.get(newEagerBlobEntity.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			EagerBlobEntityLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<EagerBlobEntity>() {

				@Override
				public void performAction(EagerBlobEntity eagerBlobEntity) {
					Assert.assertNotNull(eagerBlobEntity);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		EagerBlobEntity newEagerBlobEntity = addEagerBlobEntity();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			EagerBlobEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"eagerBlobEntityId",
				newEagerBlobEntity.getEagerBlobEntityId()));

		List<EagerBlobEntity> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		EagerBlobEntity existingEagerBlobEntity = result.get(0);

		Assert.assertEquals(existingEagerBlobEntity, newEagerBlobEntity);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			EagerBlobEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"eagerBlobEntityId", RandomTestUtil.nextLong()));

		List<EagerBlobEntity> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		EagerBlobEntity newEagerBlobEntity = addEagerBlobEntity();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			EagerBlobEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("eagerBlobEntityId"));

		Object newEagerBlobEntityId = newEagerBlobEntity.getEagerBlobEntityId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"eagerBlobEntityId", new Object[] {newEagerBlobEntityId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingEagerBlobEntityId = result.get(0);

		Assert.assertEquals(existingEagerBlobEntityId, newEagerBlobEntityId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			EagerBlobEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("eagerBlobEntityId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"eagerBlobEntityId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		EagerBlobEntity newEagerBlobEntity = addEagerBlobEntity();

		_persistence.clearCache();

		EagerBlobEntity existingEagerBlobEntity = _persistence.findByPrimaryKey(
			newEagerBlobEntity.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingEagerBlobEntity.getUuid(),
				ReflectionTestUtil.invoke(
					existingEagerBlobEntity, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingEagerBlobEntity.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingEagerBlobEntity, "getOriginalGroupId",
				new Class<?>[0]));
	}

	protected EagerBlobEntity addEagerBlobEntity() throws Exception {
		long pk = RandomTestUtil.nextLong();

		EagerBlobEntity eagerBlobEntity = _persistence.create(pk);

		eagerBlobEntity.setUuid(RandomTestUtil.randomString());

		eagerBlobEntity.setGroupId(RandomTestUtil.nextLong());
		String blobString = RandomTestUtil.randomString();

		byte[] blobBytes = blobString.getBytes("UTF-8");

		Blob blobBlob = new OutputBlob(
			new ByteArrayInputStream(blobBytes), blobBytes.length);

		eagerBlobEntity.setBlob(blobBlob);

		_eagerBlobEntities.add(_persistence.update(eagerBlobEntity));

		return eagerBlobEntity;
	}

	private List<EagerBlobEntity> _eagerBlobEntities =
		new ArrayList<EagerBlobEntity>();
	private EagerBlobEntityPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}