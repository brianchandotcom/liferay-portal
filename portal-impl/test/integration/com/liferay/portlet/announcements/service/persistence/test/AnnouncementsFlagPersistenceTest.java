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

package com.liferay.portlet.announcements.service.persistence.test;

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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.portlet.announcements.NoSuchFlagException;
import com.liferay.portlet.announcements.model.AnnouncementsFlag;
import com.liferay.portlet.announcements.service.AnnouncementsFlagLocalServiceUtil;
import com.liferay.portlet.announcements.service.persistence.AnnouncementsFlagPersistence;
import com.liferay.portlet.announcements.service.persistence.AnnouncementsFlagUtil;

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
public class AnnouncementsFlagPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = AnnouncementsFlagUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AnnouncementsFlag> iterator = _announcementsFlags.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AnnouncementsFlag announcementsFlag = _persistence.create(pk);

		Assert.assertNotNull(announcementsFlag);

		Assert.assertEquals(announcementsFlag.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AnnouncementsFlag newAnnouncementsFlag = addAnnouncementsFlag();

		_persistence.remove(newAnnouncementsFlag);

		AnnouncementsFlag existingAnnouncementsFlag = _persistence.fetchByPrimaryKey(newAnnouncementsFlag.getPrimaryKey());

		Assert.assertNull(existingAnnouncementsFlag);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAnnouncementsFlag();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AnnouncementsFlag newAnnouncementsFlag = _persistence.create(pk);

		newAnnouncementsFlag.setUserId(RandomTestUtil.nextLong());

		newAnnouncementsFlag.setCreateDate(RandomTestUtil.nextDate());

		newAnnouncementsFlag.setEntryId(RandomTestUtil.nextLong());

		newAnnouncementsFlag.setValue(RandomTestUtil.nextInt());

		_announcementsFlags.add(_persistence.update(newAnnouncementsFlag));

		AnnouncementsFlag existingAnnouncementsFlag = _persistence.findByPrimaryKey(newAnnouncementsFlag.getPrimaryKey());

		Assert.assertEquals(existingAnnouncementsFlag.getFlagId(),
			newAnnouncementsFlag.getFlagId());
		Assert.assertEquals(existingAnnouncementsFlag.getUserId(),
			newAnnouncementsFlag.getUserId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAnnouncementsFlag.getCreateDate()),
			Time.getShortTimestamp(newAnnouncementsFlag.getCreateDate()));
		Assert.assertEquals(existingAnnouncementsFlag.getEntryId(),
			newAnnouncementsFlag.getEntryId());
		Assert.assertEquals(existingAnnouncementsFlag.getValue(),
			newAnnouncementsFlag.getValue());
	}

	@Test
	public void testCountByEntryId() throws Exception {
		_persistence.countByEntryId(RandomTestUtil.nextLong());

		_persistence.countByEntryId(0L);
	}

	@Test
	public void testCountByU_E_V() throws Exception {
		_persistence.countByU_E_V(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByU_E_V(0L, 0L, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AnnouncementsFlag newAnnouncementsFlag = addAnnouncementsFlag();

		AnnouncementsFlag existingAnnouncementsFlag = _persistence.findByPrimaryKey(newAnnouncementsFlag.getPrimaryKey());

		Assert.assertEquals(existingAnnouncementsFlag, newAnnouncementsFlag);
	}

	@Test(expected = NoSuchFlagException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<AnnouncementsFlag> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AnnouncementsFlag",
			"flagId", true, "userId", true, "createDate", true, "entryId",
			true, "value", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AnnouncementsFlag newAnnouncementsFlag = addAnnouncementsFlag();

		AnnouncementsFlag existingAnnouncementsFlag = _persistence.fetchByPrimaryKey(newAnnouncementsFlag.getPrimaryKey());

		Assert.assertEquals(existingAnnouncementsFlag, newAnnouncementsFlag);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AnnouncementsFlag missingAnnouncementsFlag = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAnnouncementsFlag);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AnnouncementsFlag newAnnouncementsFlag1 = addAnnouncementsFlag();
		AnnouncementsFlag newAnnouncementsFlag2 = addAnnouncementsFlag();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAnnouncementsFlag1.getPrimaryKey());
		primaryKeys.add(newAnnouncementsFlag2.getPrimaryKey());

		Map<Serializable, AnnouncementsFlag> announcementsFlags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, announcementsFlags.size());
		Assert.assertEquals(newAnnouncementsFlag1,
			announcementsFlags.get(newAnnouncementsFlag1.getPrimaryKey()));
		Assert.assertEquals(newAnnouncementsFlag2,
			announcementsFlags.get(newAnnouncementsFlag2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AnnouncementsFlag> announcementsFlags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(announcementsFlags.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AnnouncementsFlag newAnnouncementsFlag = addAnnouncementsFlag();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAnnouncementsFlag.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AnnouncementsFlag> announcementsFlags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, announcementsFlags.size());
		Assert.assertEquals(newAnnouncementsFlag,
			announcementsFlags.get(newAnnouncementsFlag.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AnnouncementsFlag> announcementsFlags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(announcementsFlags.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AnnouncementsFlag newAnnouncementsFlag = addAnnouncementsFlag();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAnnouncementsFlag.getPrimaryKey());

		Map<Serializable, AnnouncementsFlag> announcementsFlags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, announcementsFlags.size());
		Assert.assertEquals(newAnnouncementsFlag,
			announcementsFlags.get(newAnnouncementsFlag.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AnnouncementsFlagLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					AnnouncementsFlag announcementsFlag = (AnnouncementsFlag)object;

					Assert.assertNotNull(announcementsFlag);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AnnouncementsFlag newAnnouncementsFlag = addAnnouncementsFlag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsFlag.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("flagId",
				newAnnouncementsFlag.getFlagId()));

		List<AnnouncementsFlag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AnnouncementsFlag existingAnnouncementsFlag = result.get(0);

		Assert.assertEquals(existingAnnouncementsFlag, newAnnouncementsFlag);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsFlag.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("flagId",
				RandomTestUtil.nextLong()));

		List<AnnouncementsFlag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AnnouncementsFlag newAnnouncementsFlag = addAnnouncementsFlag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsFlag.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("flagId"));

		Object newFlagId = newAnnouncementsFlag.getFlagId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("flagId",
				new Object[] { newFlagId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFlagId = result.get(0);

		Assert.assertEquals(existingFlagId, newFlagId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AnnouncementsFlag.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("flagId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("flagId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AnnouncementsFlag newAnnouncementsFlag = addAnnouncementsFlag();

		_persistence.clearCache();

		AnnouncementsFlag existingAnnouncementsFlag = _persistence.findByPrimaryKey(newAnnouncementsFlag.getPrimaryKey());

		Assert.assertEquals(existingAnnouncementsFlag.getUserId(),
			ReflectionTestUtil.invoke(existingAnnouncementsFlag,
				"getOriginalUserId", new Class<?>[0]));
		Assert.assertEquals(existingAnnouncementsFlag.getEntryId(),
			ReflectionTestUtil.invoke(existingAnnouncementsFlag,
				"getOriginalEntryId", new Class<?>[0]));
		Assert.assertEquals(existingAnnouncementsFlag.getValue(),
			ReflectionTestUtil.invoke(existingAnnouncementsFlag,
				"getOriginalValue", new Class<?>[0]));
	}

	protected AnnouncementsFlag addAnnouncementsFlag()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		AnnouncementsFlag announcementsFlag = _persistence.create(pk);

		announcementsFlag.setUserId(RandomTestUtil.nextLong());

		announcementsFlag.setCreateDate(RandomTestUtil.nextDate());

		announcementsFlag.setEntryId(RandomTestUtil.nextLong());

		announcementsFlag.setValue(RandomTestUtil.nextInt());

		_announcementsFlags.add(_persistence.update(announcementsFlag));

		return announcementsFlag;
	}

	private List<AnnouncementsFlag> _announcementsFlags = new ArrayList<AnnouncementsFlag>();
	private AnnouncementsFlagPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}