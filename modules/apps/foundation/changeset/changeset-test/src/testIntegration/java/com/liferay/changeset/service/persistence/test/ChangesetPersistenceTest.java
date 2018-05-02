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

package com.liferay.changeset.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.changeset.exception.NoSuchChangesetException;
import com.liferay.changeset.model.Changeset;
import com.liferay.changeset.service.ChangesetLocalServiceUtil;
import com.liferay.changeset.service.persistence.ChangesetPersistence;
import com.liferay.changeset.service.persistence.ChangesetUtil;

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
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class ChangesetPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.changeset.service"));

	@Before
	public void setUp() {
		_persistence = ChangesetUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Changeset> iterator = _changesets.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Changeset changeset = _persistence.create(pk);

		Assert.assertNotNull(changeset);

		Assert.assertEquals(changeset.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Changeset newChangeset = addChangeset();

		_persistence.remove(newChangeset);

		Changeset existingChangeset = _persistence.fetchByPrimaryKey(newChangeset.getPrimaryKey());

		Assert.assertNull(existingChangeset);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addChangeset();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Changeset newChangeset = _persistence.create(pk);

		newChangeset.setGroupId(RandomTestUtil.nextLong());

		newChangeset.setCompanyId(RandomTestUtil.nextLong());

		newChangeset.setUserId(RandomTestUtil.nextLong());

		newChangeset.setUserName(RandomTestUtil.randomString());

		newChangeset.setCreateDate(RandomTestUtil.nextDate());

		newChangeset.setModifiedDate(RandomTestUtil.nextDate());

		newChangeset.setName(RandomTestUtil.randomString());

		newChangeset.setDescription(RandomTestUtil.randomString());

		_changesets.add(_persistence.update(newChangeset));

		Changeset existingChangeset = _persistence.findByPrimaryKey(newChangeset.getPrimaryKey());

		Assert.assertEquals(existingChangeset.getChangesetId(),
			newChangeset.getChangesetId());
		Assert.assertEquals(existingChangeset.getGroupId(),
			newChangeset.getGroupId());
		Assert.assertEquals(existingChangeset.getCompanyId(),
			newChangeset.getCompanyId());
		Assert.assertEquals(existingChangeset.getUserId(),
			newChangeset.getUserId());
		Assert.assertEquals(existingChangeset.getUserName(),
			newChangeset.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingChangeset.getCreateDate()),
			Time.getShortTimestamp(newChangeset.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingChangeset.getModifiedDate()),
			Time.getShortTimestamp(newChangeset.getModifiedDate()));
		Assert.assertEquals(existingChangeset.getName(), newChangeset.getName());
		Assert.assertEquals(existingChangeset.getDescription(),
			newChangeset.getDescription());
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
	public void testCountByG_U() throws Exception {
		_persistence.countByG_U(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_U(0L, 0L);
	}

	@Test
	public void testCountByG_N() throws Exception {
		_persistence.countByG_N(RandomTestUtil.nextLong(), "");

		_persistence.countByG_N(0L, "null");

		_persistence.countByG_N(0L, (String)null);
	}

	@Test
	public void testCountByC_N() throws Exception {
		_persistence.countByC_N(RandomTestUtil.nextLong(), "");

		_persistence.countByC_N(0L, "null");

		_persistence.countByC_N(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Changeset newChangeset = addChangeset();

		Changeset existingChangeset = _persistence.findByPrimaryKey(newChangeset.getPrimaryKey());

		Assert.assertEquals(existingChangeset, newChangeset);
	}

	@Test(expected = NoSuchChangesetException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<Changeset> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Changeset", "changesetId",
			true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true, "name",
			true, "description", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Changeset newChangeset = addChangeset();

		Changeset existingChangeset = _persistence.fetchByPrimaryKey(newChangeset.getPrimaryKey());

		Assert.assertEquals(existingChangeset, newChangeset);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Changeset missingChangeset = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingChangeset);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Changeset newChangeset1 = addChangeset();
		Changeset newChangeset2 = addChangeset();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangeset1.getPrimaryKey());
		primaryKeys.add(newChangeset2.getPrimaryKey());

		Map<Serializable, Changeset> changesets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, changesets.size());
		Assert.assertEquals(newChangeset1,
			changesets.get(newChangeset1.getPrimaryKey()));
		Assert.assertEquals(newChangeset2,
			changesets.get(newChangeset2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Changeset> changesets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(changesets.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Changeset newChangeset = addChangeset();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangeset.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Changeset> changesets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, changesets.size());
		Assert.assertEquals(newChangeset,
			changesets.get(newChangeset.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Changeset> changesets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(changesets.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Changeset newChangeset = addChangeset();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newChangeset.getPrimaryKey());

		Map<Serializable, Changeset> changesets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, changesets.size());
		Assert.assertEquals(newChangeset,
			changesets.get(newChangeset.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ChangesetLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<Changeset>() {
				@Override
				public void performAction(Changeset changeset) {
					Assert.assertNotNull(changeset);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Changeset newChangeset = addChangeset();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Changeset.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("changesetId",
				newChangeset.getChangesetId()));

		List<Changeset> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Changeset existingChangeset = result.get(0);

		Assert.assertEquals(existingChangeset, newChangeset);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Changeset.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("changesetId",
				RandomTestUtil.nextLong()));

		List<Changeset> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Changeset newChangeset = addChangeset();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Changeset.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("changesetId"));

		Object newChangesetId = newChangeset.getChangesetId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("changesetId",
				new Object[] { newChangesetId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingChangesetId = result.get(0);

		Assert.assertEquals(existingChangesetId, newChangesetId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Changeset.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("changesetId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("changesetId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Changeset newChangeset = addChangeset();

		_persistence.clearCache();

		Changeset existingChangeset = _persistence.findByPrimaryKey(newChangeset.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(existingChangeset.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingChangeset,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(existingChangeset.getName(),
				ReflectionTestUtil.invoke(existingChangeset, "getOriginalName",
					new Class<?>[0])));
	}

	protected Changeset addChangeset() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Changeset changeset = _persistence.create(pk);

		changeset.setGroupId(RandomTestUtil.nextLong());

		changeset.setCompanyId(RandomTestUtil.nextLong());

		changeset.setUserId(RandomTestUtil.nextLong());

		changeset.setUserName(RandomTestUtil.randomString());

		changeset.setCreateDate(RandomTestUtil.nextDate());

		changeset.setModifiedDate(RandomTestUtil.nextDate());

		changeset.setName(RandomTestUtil.randomString());

		changeset.setDescription(RandomTestUtil.randomString());

		_changesets.add(_persistence.update(changeset));

		return changeset;
	}

	private List<Changeset> _changesets = new ArrayList<Changeset>();
	private ChangesetPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}