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

package com.liferay.social.networking.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

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

import com.liferay.social.networking.exception.NoSuchMeetupsRegistrationException;
import com.liferay.social.networking.model.MeetupsRegistration;
import com.liferay.social.networking.service.MeetupsRegistrationLocalServiceUtil;
import com.liferay.social.networking.service.persistence.MeetupsRegistrationPersistence;
import com.liferay.social.networking.service.persistence.MeetupsRegistrationUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class MeetupsRegistrationPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = MeetupsRegistrationUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<MeetupsRegistration> iterator = _meetupsRegistrations.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MeetupsRegistration meetupsRegistration = _persistence.create(pk);

		Assert.assertNotNull(meetupsRegistration);

		Assert.assertEquals(meetupsRegistration.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MeetupsRegistration newMeetupsRegistration = addMeetupsRegistration();

		_persistence.remove(newMeetupsRegistration);

		MeetupsRegistration existingMeetupsRegistration = _persistence.fetchByPrimaryKey(newMeetupsRegistration.getPrimaryKey());

		Assert.assertNull(existingMeetupsRegistration);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMeetupsRegistration();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MeetupsRegistration newMeetupsRegistration = _persistence.create(pk);

		newMeetupsRegistration.setCompanyId(RandomTestUtil.nextLong());

		newMeetupsRegistration.setUserId(RandomTestUtil.nextLong());

		newMeetupsRegistration.setUserName(RandomTestUtil.randomString());

		newMeetupsRegistration.setCreateDate(RandomTestUtil.nextDate());

		newMeetupsRegistration.setModifiedDate(RandomTestUtil.nextDate());

		newMeetupsRegistration.setMeetupsEntryId(RandomTestUtil.nextLong());

		newMeetupsRegistration.setStatus(RandomTestUtil.nextInt());

		newMeetupsRegistration.setComments(RandomTestUtil.randomString());

		_meetupsRegistrations.add(_persistence.update(newMeetupsRegistration));

		MeetupsRegistration existingMeetupsRegistration = _persistence.findByPrimaryKey(newMeetupsRegistration.getPrimaryKey());

		Assert.assertEquals(existingMeetupsRegistration.getMeetupsRegistrationId(),
			newMeetupsRegistration.getMeetupsRegistrationId());
		Assert.assertEquals(existingMeetupsRegistration.getCompanyId(),
			newMeetupsRegistration.getCompanyId());
		Assert.assertEquals(existingMeetupsRegistration.getUserId(),
			newMeetupsRegistration.getUserId());
		Assert.assertEquals(existingMeetupsRegistration.getUserName(),
			newMeetupsRegistration.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMeetupsRegistration.getCreateDate()),
			Time.getShortTimestamp(newMeetupsRegistration.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMeetupsRegistration.getModifiedDate()),
			Time.getShortTimestamp(newMeetupsRegistration.getModifiedDate()));
		Assert.assertEquals(existingMeetupsRegistration.getMeetupsEntryId(),
			newMeetupsRegistration.getMeetupsEntryId());
		Assert.assertEquals(existingMeetupsRegistration.getStatus(),
			newMeetupsRegistration.getStatus());
		Assert.assertEquals(existingMeetupsRegistration.getComments(),
			newMeetupsRegistration.getComments());
	}

	@Test
	public void testCountByMeetupsEntryId() throws Exception {
		_persistence.countByMeetupsEntryId(RandomTestUtil.nextLong());

		_persistence.countByMeetupsEntryId(0L);
	}

	@Test
	public void testCountByU_ME() throws Exception {
		_persistence.countByU_ME(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByU_ME(0L, 0L);
	}

	@Test
	public void testCountByME_S() throws Exception {
		_persistence.countByME_S(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByME_S(0L, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MeetupsRegistration newMeetupsRegistration = addMeetupsRegistration();

		MeetupsRegistration existingMeetupsRegistration = _persistence.findByPrimaryKey(newMeetupsRegistration.getPrimaryKey());

		Assert.assertEquals(existingMeetupsRegistration, newMeetupsRegistration);
	}

	@Test(expected = NoSuchMeetupsRegistrationException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<MeetupsRegistration> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("MeetupsRegistration",
			"meetupsRegistrationId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"meetupsEntryId", true, "status", true, "comments", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MeetupsRegistration newMeetupsRegistration = addMeetupsRegistration();

		MeetupsRegistration existingMeetupsRegistration = _persistence.fetchByPrimaryKey(newMeetupsRegistration.getPrimaryKey());

		Assert.assertEquals(existingMeetupsRegistration, newMeetupsRegistration);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MeetupsRegistration missingMeetupsRegistration = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMeetupsRegistration);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		MeetupsRegistration newMeetupsRegistration1 = addMeetupsRegistration();
		MeetupsRegistration newMeetupsRegistration2 = addMeetupsRegistration();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMeetupsRegistration1.getPrimaryKey());
		primaryKeys.add(newMeetupsRegistration2.getPrimaryKey());

		Map<Serializable, MeetupsRegistration> meetupsRegistrations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, meetupsRegistrations.size());
		Assert.assertEquals(newMeetupsRegistration1,
			meetupsRegistrations.get(newMeetupsRegistration1.getPrimaryKey()));
		Assert.assertEquals(newMeetupsRegistration2,
			meetupsRegistrations.get(newMeetupsRegistration2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, MeetupsRegistration> meetupsRegistrations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(meetupsRegistrations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		MeetupsRegistration newMeetupsRegistration = addMeetupsRegistration();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMeetupsRegistration.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, MeetupsRegistration> meetupsRegistrations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, meetupsRegistrations.size());
		Assert.assertEquals(newMeetupsRegistration,
			meetupsRegistrations.get(newMeetupsRegistration.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, MeetupsRegistration> meetupsRegistrations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(meetupsRegistrations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		MeetupsRegistration newMeetupsRegistration = addMeetupsRegistration();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMeetupsRegistration.getPrimaryKey());

		Map<Serializable, MeetupsRegistration> meetupsRegistrations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, meetupsRegistrations.size());
		Assert.assertEquals(newMeetupsRegistration,
			meetupsRegistrations.get(newMeetupsRegistration.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = MeetupsRegistrationLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					MeetupsRegistration meetupsRegistration = (MeetupsRegistration)object;

					Assert.assertNotNull(meetupsRegistration);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MeetupsRegistration newMeetupsRegistration = addMeetupsRegistration();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MeetupsRegistration.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("meetupsRegistrationId",
				newMeetupsRegistration.getMeetupsRegistrationId()));

		List<MeetupsRegistration> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MeetupsRegistration existingMeetupsRegistration = result.get(0);

		Assert.assertEquals(existingMeetupsRegistration, newMeetupsRegistration);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MeetupsRegistration.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("meetupsRegistrationId",
				RandomTestUtil.nextLong()));

		List<MeetupsRegistration> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MeetupsRegistration newMeetupsRegistration = addMeetupsRegistration();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MeetupsRegistration.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"meetupsRegistrationId"));

		Object newMeetupsRegistrationId = newMeetupsRegistration.getMeetupsRegistrationId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("meetupsRegistrationId",
				new Object[] { newMeetupsRegistrationId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingMeetupsRegistrationId = result.get(0);

		Assert.assertEquals(existingMeetupsRegistrationId,
			newMeetupsRegistrationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MeetupsRegistration.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"meetupsRegistrationId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("meetupsRegistrationId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		MeetupsRegistration newMeetupsRegistration = addMeetupsRegistration();

		_persistence.clearCache();

		MeetupsRegistration existingMeetupsRegistration = _persistence.findByPrimaryKey(newMeetupsRegistration.getPrimaryKey());

		Assert.assertEquals(existingMeetupsRegistration.getUserId(),
			ReflectionTestUtil.invoke(existingMeetupsRegistration,
				"getOriginalUserId", new Class<?>[0]));
		Assert.assertEquals(existingMeetupsRegistration.getMeetupsEntryId(),
			ReflectionTestUtil.invoke(existingMeetupsRegistration,
				"getOriginalMeetupsEntryId", new Class<?>[0]));
	}

	protected MeetupsRegistration addMeetupsRegistration()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		MeetupsRegistration meetupsRegistration = _persistence.create(pk);

		meetupsRegistration.setCompanyId(RandomTestUtil.nextLong());

		meetupsRegistration.setUserId(RandomTestUtil.nextLong());

		meetupsRegistration.setUserName(RandomTestUtil.randomString());

		meetupsRegistration.setCreateDate(RandomTestUtil.nextDate());

		meetupsRegistration.setModifiedDate(RandomTestUtil.nextDate());

		meetupsRegistration.setMeetupsEntryId(RandomTestUtil.nextLong());

		meetupsRegistration.setStatus(RandomTestUtil.nextInt());

		meetupsRegistration.setComments(RandomTestUtil.randomString());

		_meetupsRegistrations.add(_persistence.update(meetupsRegistration));

		return meetupsRegistration;
	}

	private List<MeetupsRegistration> _meetupsRegistrations = new ArrayList<MeetupsRegistration>();
	private MeetupsRegistrationPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}