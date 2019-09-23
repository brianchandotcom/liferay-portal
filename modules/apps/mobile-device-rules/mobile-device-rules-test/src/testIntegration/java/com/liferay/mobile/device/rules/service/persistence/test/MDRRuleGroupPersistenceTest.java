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

package com.liferay.mobile.device.rules.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.mobile.device.rules.exception.NoSuchRuleGroupException;
import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.service.MDRRuleGroupLocalServiceUtil;
import com.liferay.mobile.device.rules.service.persistence.MDRRuleGroupPersistence;
import com.liferay.mobile.device.rules.service.persistence.MDRRuleGroupUtil;
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
public class MDRRuleGroupPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.mobile.device.rules.service"));

	@Before
	public void setUp() {
		_persistence = MDRRuleGroupUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<MDRRuleGroup> iterator = _mdrRuleGroups.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRRuleGroup mdrRuleGroup = _persistence.create(pk);

		Assert.assertNotNull(mdrRuleGroup);

		Assert.assertEquals(mdrRuleGroup.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		_persistence.remove(newMDRRuleGroup);

		MDRRuleGroup existingMDRRuleGroup = _persistence.fetchByPrimaryKey(
			newMDRRuleGroup.getPrimaryKey());

		Assert.assertNull(existingMDRRuleGroup);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMDRRuleGroup();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRRuleGroup newMDRRuleGroup = _persistence.create(pk);

		newMDRRuleGroup.setMvccVersion(RandomTestUtil.nextLong());

		newMDRRuleGroup.setUuid(RandomTestUtil.randomString());

		newMDRRuleGroup.setGroupId(RandomTestUtil.nextLong());

		newMDRRuleGroup.setCompanyId(RandomTestUtil.nextLong());

		newMDRRuleGroup.setUserId(RandomTestUtil.nextLong());

		newMDRRuleGroup.setUserName(RandomTestUtil.randomString());

		newMDRRuleGroup.setCreateDate(RandomTestUtil.nextDate());

		newMDRRuleGroup.setModifiedDate(RandomTestUtil.nextDate());

		newMDRRuleGroup.setName(RandomTestUtil.randomString());

		newMDRRuleGroup.setDescription(RandomTestUtil.randomString());

		newMDRRuleGroup.setLastPublishDate(RandomTestUtil.nextDate());

		_mdrRuleGroups.add(_persistence.update(newMDRRuleGroup));

		MDRRuleGroup existingMDRRuleGroup = _persistence.findByPrimaryKey(
			newMDRRuleGroup.getPrimaryKey());

		Assert.assertEquals(
			existingMDRRuleGroup.getMvccVersion(),
			newMDRRuleGroup.getMvccVersion());
		Assert.assertEquals(
			existingMDRRuleGroup.getUuid(), newMDRRuleGroup.getUuid());
		Assert.assertEquals(
			existingMDRRuleGroup.getRuleGroupId(),
			newMDRRuleGroup.getRuleGroupId());
		Assert.assertEquals(
			existingMDRRuleGroup.getGroupId(), newMDRRuleGroup.getGroupId());
		Assert.assertEquals(
			existingMDRRuleGroup.getCompanyId(),
			newMDRRuleGroup.getCompanyId());
		Assert.assertEquals(
			existingMDRRuleGroup.getUserId(), newMDRRuleGroup.getUserId());
		Assert.assertEquals(
			existingMDRRuleGroup.getUserName(), newMDRRuleGroup.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingMDRRuleGroup.getCreateDate()),
			Time.getShortTimestamp(newMDRRuleGroup.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingMDRRuleGroup.getModifiedDate()),
			Time.getShortTimestamp(newMDRRuleGroup.getModifiedDate()));
		Assert.assertEquals(
			existingMDRRuleGroup.getName(), newMDRRuleGroup.getName());
		Assert.assertEquals(
			existingMDRRuleGroup.getDescription(),
			newMDRRuleGroup.getDescription());
		Assert.assertEquals(
			Time.getShortTimestamp(existingMDRRuleGroup.getLastPublishDate()),
			Time.getShortTimestamp(newMDRRuleGroup.getLastPublishDate()));
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
	public void testCountByGroupIdArrayable() throws Exception {
		_persistence.countByGroupId(new long[] {RandomTestUtil.nextLong(), 0L});
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		MDRRuleGroup existingMDRRuleGroup = _persistence.findByPrimaryKey(
			newMDRRuleGroup.getPrimaryKey());

		Assert.assertEquals(existingMDRRuleGroup, newMDRRuleGroup);
	}

	@Test(expected = NoSuchRuleGroupException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(
			0, QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<MDRRuleGroup> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"MDRRuleGroup", "mvccVersion", true, "uuid", true, "ruleGroupId",
			true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true, "name",
			true, "description", true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		MDRRuleGroup existingMDRRuleGroup = _persistence.fetchByPrimaryKey(
			newMDRRuleGroup.getPrimaryKey());

		Assert.assertEquals(existingMDRRuleGroup, newMDRRuleGroup);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRRuleGroup missingMDRRuleGroup = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMDRRuleGroup);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		MDRRuleGroup newMDRRuleGroup1 = addMDRRuleGroup();
		MDRRuleGroup newMDRRuleGroup2 = addMDRRuleGroup();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMDRRuleGroup1.getPrimaryKey());
		primaryKeys.add(newMDRRuleGroup2.getPrimaryKey());

		Map<Serializable, MDRRuleGroup> mdrRuleGroups =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, mdrRuleGroups.size());
		Assert.assertEquals(
			newMDRRuleGroup1,
			mdrRuleGroups.get(newMDRRuleGroup1.getPrimaryKey()));
		Assert.assertEquals(
			newMDRRuleGroup2,
			mdrRuleGroups.get(newMDRRuleGroup2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, MDRRuleGroup> mdrRuleGroups =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mdrRuleGroups.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMDRRuleGroup.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, MDRRuleGroup> mdrRuleGroups =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mdrRuleGroups.size());
		Assert.assertEquals(
			newMDRRuleGroup,
			mdrRuleGroups.get(newMDRRuleGroup.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, MDRRuleGroup> mdrRuleGroups =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mdrRuleGroups.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMDRRuleGroup.getPrimaryKey());

		Map<Serializable, MDRRuleGroup> mdrRuleGroups =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mdrRuleGroups.size());
		Assert.assertEquals(
			newMDRRuleGroup,
			mdrRuleGroups.get(newMDRRuleGroup.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			MDRRuleGroupLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<MDRRuleGroup>() {

				@Override
				public void performAction(MDRRuleGroup mdrRuleGroup) {
					Assert.assertNotNull(mdrRuleGroup);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MDRRuleGroup.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"ruleGroupId", newMDRRuleGroup.getRuleGroupId()));

		List<MDRRuleGroup> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		MDRRuleGroup existingMDRRuleGroup = result.get(0);

		Assert.assertEquals(existingMDRRuleGroup, newMDRRuleGroup);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MDRRuleGroup.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"ruleGroupId", RandomTestUtil.nextLong()));

		List<MDRRuleGroup> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MDRRuleGroup.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("ruleGroupId"));

		Object newRuleGroupId = newMDRRuleGroup.getRuleGroupId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"ruleGroupId", new Object[] {newRuleGroupId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRuleGroupId = result.get(0);

		Assert.assertEquals(existingRuleGroupId, newRuleGroupId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MDRRuleGroup.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("ruleGroupId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"ruleGroupId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		_persistence.clearCache();

		MDRRuleGroup existingMDRRuleGroup = _persistence.findByPrimaryKey(
			newMDRRuleGroup.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingMDRRuleGroup.getUuid(),
				ReflectionTestUtil.invoke(
					existingMDRRuleGroup, "getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingMDRRuleGroup.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingMDRRuleGroup, "getOriginalGroupId", new Class<?>[0]));
	}

	protected MDRRuleGroup addMDRRuleGroup() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRRuleGroup mdrRuleGroup = _persistence.create(pk);

		mdrRuleGroup.setMvccVersion(RandomTestUtil.nextLong());

		mdrRuleGroup.setUuid(RandomTestUtil.randomString());

		mdrRuleGroup.setGroupId(RandomTestUtil.nextLong());

		mdrRuleGroup.setCompanyId(RandomTestUtil.nextLong());

		mdrRuleGroup.setUserId(RandomTestUtil.nextLong());

		mdrRuleGroup.setUserName(RandomTestUtil.randomString());

		mdrRuleGroup.setCreateDate(RandomTestUtil.nextDate());

		mdrRuleGroup.setModifiedDate(RandomTestUtil.nextDate());

		mdrRuleGroup.setName(RandomTestUtil.randomString());

		mdrRuleGroup.setDescription(RandomTestUtil.randomString());

		mdrRuleGroup.setLastPublishDate(RandomTestUtil.nextDate());

		_mdrRuleGroups.add(_persistence.update(mdrRuleGroup));

		return mdrRuleGroup;
	}

	private List<MDRRuleGroup> _mdrRuleGroups = new ArrayList<MDRRuleGroup>();
	private MDRRuleGroupPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}