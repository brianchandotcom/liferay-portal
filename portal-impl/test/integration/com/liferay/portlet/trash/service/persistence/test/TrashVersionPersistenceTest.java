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

package com.liferay.portlet.trash.service.persistence.test;

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
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.portlet.trash.NoSuchVersionException;
import com.liferay.portlet.trash.model.TrashVersion;
import com.liferay.portlet.trash.service.TrashVersionLocalServiceUtil;
import com.liferay.portlet.trash.service.persistence.TrashVersionPersistence;
import com.liferay.portlet.trash.service.persistence.TrashVersionUtil;

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
public class TrashVersionPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = TrashVersionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<TrashVersion> iterator = _trashVersions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TrashVersion trashVersion = _persistence.create(pk);

		Assert.assertNotNull(trashVersion);

		Assert.assertEquals(trashVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		_persistence.remove(newTrashVersion);

		TrashVersion existingTrashVersion = _persistence.fetchByPrimaryKey(newTrashVersion.getPrimaryKey());

		Assert.assertNull(existingTrashVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addTrashVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TrashVersion newTrashVersion = _persistence.create(pk);

		newTrashVersion.setEntryId(RandomTestUtil.nextLong());

		newTrashVersion.setClassNameId(RandomTestUtil.nextLong());

		newTrashVersion.setClassPK(RandomTestUtil.nextLong());

		newTrashVersion.setTypeSettings(RandomTestUtil.randomString());

		newTrashVersion.setStatus(RandomTestUtil.nextInt());

		_trashVersions.add(_persistence.update(newTrashVersion));

		TrashVersion existingTrashVersion = _persistence.findByPrimaryKey(newTrashVersion.getPrimaryKey());

		Assert.assertEquals(existingTrashVersion.getVersionId(),
			newTrashVersion.getVersionId());
		Assert.assertEquals(existingTrashVersion.getEntryId(),
			newTrashVersion.getEntryId());
		Assert.assertEquals(existingTrashVersion.getClassNameId(),
			newTrashVersion.getClassNameId());
		Assert.assertEquals(existingTrashVersion.getClassPK(),
			newTrashVersion.getClassPK());
		Assert.assertEquals(existingTrashVersion.getTypeSettings(),
			newTrashVersion.getTypeSettings());
		Assert.assertEquals(existingTrashVersion.getStatus(),
			newTrashVersion.getStatus());
	}

	@Test
	public void testCountByEntryId() throws Exception {
		_persistence.countByEntryId(RandomTestUtil.nextLong());

		_persistence.countByEntryId(0L);
	}

	@Test
	public void testCountByE_C() throws Exception {
		_persistence.countByE_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByE_C(0L, 0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		TrashVersion existingTrashVersion = _persistence.findByPrimaryKey(newTrashVersion.getPrimaryKey());

		Assert.assertEquals(existingTrashVersion, newTrashVersion);
	}

	@Test(expected = NoSuchVersionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<TrashVersion> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("TrashVersion", "versionId",
			true, "entryId", true, "classNameId", true, "classPK", true,
			"typeSettings", true, "status", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		TrashVersion existingTrashVersion = _persistence.fetchByPrimaryKey(newTrashVersion.getPrimaryKey());

		Assert.assertEquals(existingTrashVersion, newTrashVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TrashVersion missingTrashVersion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingTrashVersion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		TrashVersion newTrashVersion1 = addTrashVersion();
		TrashVersion newTrashVersion2 = addTrashVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTrashVersion1.getPrimaryKey());
		primaryKeys.add(newTrashVersion2.getPrimaryKey());

		Map<Serializable, TrashVersion> trashVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, trashVersions.size());
		Assert.assertEquals(newTrashVersion1,
			trashVersions.get(newTrashVersion1.getPrimaryKey()));
		Assert.assertEquals(newTrashVersion2,
			trashVersions.get(newTrashVersion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, TrashVersion> trashVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(trashVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTrashVersion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, TrashVersion> trashVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, trashVersions.size());
		Assert.assertEquals(newTrashVersion,
			trashVersions.get(newTrashVersion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, TrashVersion> trashVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(trashVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTrashVersion.getPrimaryKey());

		Map<Serializable, TrashVersion> trashVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, trashVersions.size());
		Assert.assertEquals(newTrashVersion,
			trashVersions.get(newTrashVersion.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = TrashVersionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					TrashVersion trashVersion = (TrashVersion)object;

					Assert.assertNotNull(trashVersion);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("versionId",
				newTrashVersion.getVersionId()));

		List<TrashVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		TrashVersion existingTrashVersion = result.get(0);

		Assert.assertEquals(existingTrashVersion, newTrashVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("versionId",
				RandomTestUtil.nextLong()));

		List<TrashVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("versionId"));

		Object newVersionId = newTrashVersion.getVersionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("versionId",
				new Object[] { newVersionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingVersionId = result.get(0);

		Assert.assertEquals(existingVersionId, newVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("versionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("versionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		_persistence.clearCache();

		TrashVersion existingTrashVersion = _persistence.findByPrimaryKey(newTrashVersion.getPrimaryKey());

		Assert.assertEquals(existingTrashVersion.getClassNameId(),
			ReflectionTestUtil.invoke(existingTrashVersion,
				"getOriginalClassNameId", new Class<?>[0]));
		Assert.assertEquals(existingTrashVersion.getClassPK(),
			ReflectionTestUtil.invoke(existingTrashVersion,
				"getOriginalClassPK", new Class<?>[0]));
	}

	protected TrashVersion addTrashVersion() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TrashVersion trashVersion = _persistence.create(pk);

		trashVersion.setEntryId(RandomTestUtil.nextLong());

		trashVersion.setClassNameId(RandomTestUtil.nextLong());

		trashVersion.setClassPK(RandomTestUtil.nextLong());

		trashVersion.setTypeSettings(RandomTestUtil.randomString());

		trashVersion.setStatus(RandomTestUtil.nextInt());

		_trashVersions.add(_persistence.update(trashVersion));

		return trashVersion;
	}

	private List<TrashVersion> _trashVersions = new ArrayList<TrashVersion>();
	private TrashVersionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}