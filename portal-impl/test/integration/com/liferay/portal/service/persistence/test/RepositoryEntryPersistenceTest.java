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

package com.liferay.portal.service.persistence.test;

import com.liferay.portal.NoSuchRepositoryEntryException;
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
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.service.RepositoryEntryLocalServiceUtil;
import com.liferay.portal.service.persistence.RepositoryEntryPersistence;
import com.liferay.portal.service.persistence.RepositoryEntryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

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
public class RepositoryEntryPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = RepositoryEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<RepositoryEntry> iterator = _repositoryEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RepositoryEntry repositoryEntry = _persistence.create(pk);

		Assert.assertNotNull(repositoryEntry);

		Assert.assertEquals(repositoryEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		RepositoryEntry newRepositoryEntry = addRepositoryEntry();

		_persistence.remove(newRepositoryEntry);

		RepositoryEntry existingRepositoryEntry = _persistence.fetchByPrimaryKey(newRepositoryEntry.getPrimaryKey());

		Assert.assertNull(existingRepositoryEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addRepositoryEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RepositoryEntry newRepositoryEntry = _persistence.create(pk);

		newRepositoryEntry.setMvccVersion(RandomTestUtil.nextLong());

		newRepositoryEntry.setUuid(RandomTestUtil.randomString());

		newRepositoryEntry.setGroupId(RandomTestUtil.nextLong());

		newRepositoryEntry.setCompanyId(RandomTestUtil.nextLong());

		newRepositoryEntry.setUserId(RandomTestUtil.nextLong());

		newRepositoryEntry.setUserName(RandomTestUtil.randomString());

		newRepositoryEntry.setCreateDate(RandomTestUtil.nextDate());

		newRepositoryEntry.setModifiedDate(RandomTestUtil.nextDate());

		newRepositoryEntry.setRepositoryId(RandomTestUtil.nextLong());

		newRepositoryEntry.setMappedId(RandomTestUtil.randomString());

		newRepositoryEntry.setManualCheckInRequired(RandomTestUtil.randomBoolean());

		_repositoryEntries.add(_persistence.update(newRepositoryEntry));

		RepositoryEntry existingRepositoryEntry = _persistence.findByPrimaryKey(newRepositoryEntry.getPrimaryKey());

		Assert.assertEquals(existingRepositoryEntry.getMvccVersion(),
			newRepositoryEntry.getMvccVersion());
		Assert.assertEquals(existingRepositoryEntry.getUuid(),
			newRepositoryEntry.getUuid());
		Assert.assertEquals(existingRepositoryEntry.getRepositoryEntryId(),
			newRepositoryEntry.getRepositoryEntryId());
		Assert.assertEquals(existingRepositoryEntry.getGroupId(),
			newRepositoryEntry.getGroupId());
		Assert.assertEquals(existingRepositoryEntry.getCompanyId(),
			newRepositoryEntry.getCompanyId());
		Assert.assertEquals(existingRepositoryEntry.getUserId(),
			newRepositoryEntry.getUserId());
		Assert.assertEquals(existingRepositoryEntry.getUserName(),
			newRepositoryEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingRepositoryEntry.getCreateDate()),
			Time.getShortTimestamp(newRepositoryEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingRepositoryEntry.getModifiedDate()),
			Time.getShortTimestamp(newRepositoryEntry.getModifiedDate()));
		Assert.assertEquals(existingRepositoryEntry.getRepositoryId(),
			newRepositoryEntry.getRepositoryId());
		Assert.assertEquals(existingRepositoryEntry.getMappedId(),
			newRepositoryEntry.getMappedId());
		Assert.assertEquals(existingRepositoryEntry.getManualCheckInRequired(),
			newRepositoryEntry.getManualCheckInRequired());
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
	public void testCountByRepositoryId() throws Exception {
		_persistence.countByRepositoryId(RandomTestUtil.nextLong());

		_persistence.countByRepositoryId(0L);
	}

	@Test
	public void testCountByR_M() throws Exception {
		_persistence.countByR_M(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByR_M(0L, StringPool.NULL);

		_persistence.countByR_M(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		RepositoryEntry newRepositoryEntry = addRepositoryEntry();

		RepositoryEntry existingRepositoryEntry = _persistence.findByPrimaryKey(newRepositoryEntry.getPrimaryKey());

		Assert.assertEquals(existingRepositoryEntry, newRepositoryEntry);
	}

	@Test(expected = NoSuchRepositoryEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<RepositoryEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("RepositoryEntry",
			"mvccVersion", true, "uuid", true, "repositoryEntryId", true,
			"groupId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "repositoryId",
			true, "mappedId", true, "manualCheckInRequired", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		RepositoryEntry newRepositoryEntry = addRepositoryEntry();

		RepositoryEntry existingRepositoryEntry = _persistence.fetchByPrimaryKey(newRepositoryEntry.getPrimaryKey());

		Assert.assertEquals(existingRepositoryEntry, newRepositoryEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RepositoryEntry missingRepositoryEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingRepositoryEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		RepositoryEntry newRepositoryEntry1 = addRepositoryEntry();
		RepositoryEntry newRepositoryEntry2 = addRepositoryEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRepositoryEntry1.getPrimaryKey());
		primaryKeys.add(newRepositoryEntry2.getPrimaryKey());

		Map<Serializable, RepositoryEntry> repositoryEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, repositoryEntries.size());
		Assert.assertEquals(newRepositoryEntry1,
			repositoryEntries.get(newRepositoryEntry1.getPrimaryKey()));
		Assert.assertEquals(newRepositoryEntry2,
			repositoryEntries.get(newRepositoryEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, RepositoryEntry> repositoryEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(repositoryEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		RepositoryEntry newRepositoryEntry = addRepositoryEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRepositoryEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, RepositoryEntry> repositoryEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, repositoryEntries.size());
		Assert.assertEquals(newRepositoryEntry,
			repositoryEntries.get(newRepositoryEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, RepositoryEntry> repositoryEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(repositoryEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		RepositoryEntry newRepositoryEntry = addRepositoryEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRepositoryEntry.getPrimaryKey());

		Map<Serializable, RepositoryEntry> repositoryEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, repositoryEntries.size());
		Assert.assertEquals(newRepositoryEntry,
			repositoryEntries.get(newRepositoryEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = RepositoryEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					RepositoryEntry repositoryEntry = (RepositoryEntry)object;

					Assert.assertNotNull(repositoryEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		RepositoryEntry newRepositoryEntry = addRepositoryEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RepositoryEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("repositoryEntryId",
				newRepositoryEntry.getRepositoryEntryId()));

		List<RepositoryEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		RepositoryEntry existingRepositoryEntry = result.get(0);

		Assert.assertEquals(existingRepositoryEntry, newRepositoryEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RepositoryEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("repositoryEntryId",
				RandomTestUtil.nextLong()));

		List<RepositoryEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		RepositoryEntry newRepositoryEntry = addRepositoryEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RepositoryEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"repositoryEntryId"));

		Object newRepositoryEntryId = newRepositoryEntry.getRepositoryEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("repositoryEntryId",
				new Object[] { newRepositoryEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRepositoryEntryId = result.get(0);

		Assert.assertEquals(existingRepositoryEntryId, newRepositoryEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RepositoryEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"repositoryEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("repositoryEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		RepositoryEntry newRepositoryEntry = addRepositoryEntry();

		_persistence.clearCache();

		RepositoryEntry existingRepositoryEntry = _persistence.findByPrimaryKey(newRepositoryEntry.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingRepositoryEntry.getUuid(),
				ReflectionTestUtil.invoke(existingRepositoryEntry,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(existingRepositoryEntry.getGroupId(),
			ReflectionTestUtil.invoke(existingRepositoryEntry,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(existingRepositoryEntry.getRepositoryId(),
			ReflectionTestUtil.invoke(existingRepositoryEntry,
				"getOriginalRepositoryId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(
				existingRepositoryEntry.getMappedId(),
				ReflectionTestUtil.invoke(existingRepositoryEntry,
					"getOriginalMappedId", new Class<?>[0])));
	}

	protected RepositoryEntry addRepositoryEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RepositoryEntry repositoryEntry = _persistence.create(pk);

		repositoryEntry.setMvccVersion(RandomTestUtil.nextLong());

		repositoryEntry.setUuid(RandomTestUtil.randomString());

		repositoryEntry.setGroupId(RandomTestUtil.nextLong());

		repositoryEntry.setCompanyId(RandomTestUtil.nextLong());

		repositoryEntry.setUserId(RandomTestUtil.nextLong());

		repositoryEntry.setUserName(RandomTestUtil.randomString());

		repositoryEntry.setCreateDate(RandomTestUtil.nextDate());

		repositoryEntry.setModifiedDate(RandomTestUtil.nextDate());

		repositoryEntry.setRepositoryId(RandomTestUtil.nextLong());

		repositoryEntry.setMappedId(RandomTestUtil.randomString());

		repositoryEntry.setManualCheckInRequired(RandomTestUtil.randomBoolean());

		_repositoryEntries.add(_persistence.update(repositoryEntry));

		return repositoryEntry;
	}

	private List<RepositoryEntry> _repositoryEntries = new ArrayList<RepositoryEntry>();
	private RepositoryEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}