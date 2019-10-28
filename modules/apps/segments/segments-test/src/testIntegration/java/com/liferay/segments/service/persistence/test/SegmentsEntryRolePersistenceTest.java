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

package com.liferay.segments.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
import com.liferay.segments.exception.NoSuchEntryRoleException;
import com.liferay.segments.model.SegmentsEntryRole;
import com.liferay.segments.service.SegmentsEntryRoleLocalServiceUtil;
import com.liferay.segments.service.persistence.SegmentsEntryRolePersistence;
import com.liferay.segments.service.persistence.SegmentsEntryRoleUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
public class SegmentsEntryRolePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.segments.service"));

	@Before
	public void setUp() {
		_persistence = SegmentsEntryRoleUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SegmentsEntryRole> iterator = _segmentsEntryRoles.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SegmentsEntryRole segmentsEntryRole = _persistence.create(pk);

		Assert.assertNotNull(segmentsEntryRole);

		Assert.assertEquals(segmentsEntryRole.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SegmentsEntryRole newSegmentsEntryRole = addSegmentsEntryRole();

		_persistence.remove(newSegmentsEntryRole);

		SegmentsEntryRole existingSegmentsEntryRole =
			_persistence.fetchByPrimaryKey(
				newSegmentsEntryRole.getPrimaryKey());

		Assert.assertNull(existingSegmentsEntryRole);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSegmentsEntryRole();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SegmentsEntryRole newSegmentsEntryRole = _persistence.create(pk);

		newSegmentsEntryRole.setMvccVersion(RandomTestUtil.nextLong());

		newSegmentsEntryRole.setCompanyId(RandomTestUtil.nextLong());

		newSegmentsEntryRole.setUserId(RandomTestUtil.nextLong());

		newSegmentsEntryRole.setUserName(RandomTestUtil.randomString());

		newSegmentsEntryRole.setCreateDate(RandomTestUtil.nextDate());

		newSegmentsEntryRole.setModifiedDate(RandomTestUtil.nextDate());

		newSegmentsEntryRole.setRoleId(RandomTestUtil.nextLong());

		newSegmentsEntryRole.setSegmentsEntryId(RandomTestUtil.nextLong());

		_segmentsEntryRoles.add(_persistence.update(newSegmentsEntryRole));

		SegmentsEntryRole existingSegmentsEntryRole =
			_persistence.findByPrimaryKey(newSegmentsEntryRole.getPrimaryKey());

		Assert.assertEquals(
			existingSegmentsEntryRole.getMvccVersion(),
			newSegmentsEntryRole.getMvccVersion());
		Assert.assertEquals(
			existingSegmentsEntryRole.getSegmentsEntryRoleId(),
			newSegmentsEntryRole.getSegmentsEntryRoleId());
		Assert.assertEquals(
			existingSegmentsEntryRole.getCompanyId(),
			newSegmentsEntryRole.getCompanyId());
		Assert.assertEquals(
			existingSegmentsEntryRole.getUserId(),
			newSegmentsEntryRole.getUserId());
		Assert.assertEquals(
			existingSegmentsEntryRole.getUserName(),
			newSegmentsEntryRole.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingSegmentsEntryRole.getCreateDate()),
			Time.getShortTimestamp(newSegmentsEntryRole.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingSegmentsEntryRole.getModifiedDate()),
			Time.getShortTimestamp(newSegmentsEntryRole.getModifiedDate()));
		Assert.assertEquals(
			existingSegmentsEntryRole.getRoleId(),
			newSegmentsEntryRole.getRoleId());
		Assert.assertEquals(
			existingSegmentsEntryRole.getSegmentsEntryId(),
			newSegmentsEntryRole.getSegmentsEntryId());
	}

	@Test
	public void testCountByRoleId() throws Exception {
		_persistence.countByRoleId(RandomTestUtil.nextLong());

		_persistence.countByRoleId(0L);
	}

	@Test
	public void testCountBySegmentsEntryId() throws Exception {
		_persistence.countBySegmentsEntryId(RandomTestUtil.nextLong());

		_persistence.countBySegmentsEntryId(0L);
	}

	@Test
	public void testCountByR_S() throws Exception {
		_persistence.countByR_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByR_S(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SegmentsEntryRole newSegmentsEntryRole = addSegmentsEntryRole();

		SegmentsEntryRole existingSegmentsEntryRole =
			_persistence.findByPrimaryKey(newSegmentsEntryRole.getPrimaryKey());

		Assert.assertEquals(existingSegmentsEntryRole, newSegmentsEntryRole);
	}

	@Test(expected = NoSuchEntryRoleException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<SegmentsEntryRole> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"SegmentsEntryRole", "mvccVersion", true, "segmentsEntryRoleId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "roleId", true,
			"segmentsEntryId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SegmentsEntryRole newSegmentsEntryRole = addSegmentsEntryRole();

		SegmentsEntryRole existingSegmentsEntryRole =
			_persistence.fetchByPrimaryKey(
				newSegmentsEntryRole.getPrimaryKey());

		Assert.assertEquals(existingSegmentsEntryRole, newSegmentsEntryRole);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SegmentsEntryRole missingSegmentsEntryRole =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSegmentsEntryRole);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		SegmentsEntryRole newSegmentsEntryRole1 = addSegmentsEntryRole();
		SegmentsEntryRole newSegmentsEntryRole2 = addSegmentsEntryRole();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSegmentsEntryRole1.getPrimaryKey());
		primaryKeys.add(newSegmentsEntryRole2.getPrimaryKey());

		Map<Serializable, SegmentsEntryRole> segmentsEntryRoles =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, segmentsEntryRoles.size());
		Assert.assertEquals(
			newSegmentsEntryRole1,
			segmentsEntryRoles.get(newSegmentsEntryRole1.getPrimaryKey()));
		Assert.assertEquals(
			newSegmentsEntryRole2,
			segmentsEntryRoles.get(newSegmentsEntryRole2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SegmentsEntryRole> segmentsEntryRoles =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(segmentsEntryRoles.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		SegmentsEntryRole newSegmentsEntryRole = addSegmentsEntryRole();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSegmentsEntryRole.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SegmentsEntryRole> segmentsEntryRoles =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, segmentsEntryRoles.size());
		Assert.assertEquals(
			newSegmentsEntryRole,
			segmentsEntryRoles.get(newSegmentsEntryRole.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SegmentsEntryRole> segmentsEntryRoles =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(segmentsEntryRoles.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		SegmentsEntryRole newSegmentsEntryRole = addSegmentsEntryRole();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSegmentsEntryRole.getPrimaryKey());

		Map<Serializable, SegmentsEntryRole> segmentsEntryRoles =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, segmentsEntryRoles.size());
		Assert.assertEquals(
			newSegmentsEntryRole,
			segmentsEntryRoles.get(newSegmentsEntryRole.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			SegmentsEntryRoleLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<SegmentsEntryRole>() {

				@Override
				public void performAction(SegmentsEntryRole segmentsEntryRole) {
					Assert.assertNotNull(segmentsEntryRole);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		SegmentsEntryRole newSegmentsEntryRole = addSegmentsEntryRole();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SegmentsEntryRole.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"segmentsEntryRoleId",
				newSegmentsEntryRole.getSegmentsEntryRoleId()));

		List<SegmentsEntryRole> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		SegmentsEntryRole existingSegmentsEntryRole = result.get(0);

		Assert.assertEquals(existingSegmentsEntryRole, newSegmentsEntryRole);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SegmentsEntryRole.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"segmentsEntryRoleId", RandomTestUtil.nextLong()));

		List<SegmentsEntryRole> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		SegmentsEntryRole newSegmentsEntryRole = addSegmentsEntryRole();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SegmentsEntryRole.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("segmentsEntryRoleId"));

		Object newSegmentsEntryRoleId =
			newSegmentsEntryRole.getSegmentsEntryRoleId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"segmentsEntryRoleId", new Object[] {newSegmentsEntryRoleId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSegmentsEntryRoleId = result.get(0);

		Assert.assertEquals(
			existingSegmentsEntryRoleId, newSegmentsEntryRoleId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SegmentsEntryRole.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("segmentsEntryRoleId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"segmentsEntryRoleId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		SegmentsEntryRole newSegmentsEntryRole = addSegmentsEntryRole();

		_persistence.clearCache();

		SegmentsEntryRole existingSegmentsEntryRole =
			_persistence.findByPrimaryKey(newSegmentsEntryRole.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(existingSegmentsEntryRole.getRoleId()),
			ReflectionTestUtil.<Long>invoke(
				existingSegmentsEntryRole, "getOriginalRoleId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingSegmentsEntryRole.getSegmentsEntryId()),
			ReflectionTestUtil.<Long>invoke(
				existingSegmentsEntryRole, "getOriginalSegmentsEntryId",
				new Class<?>[0]));
	}

	protected SegmentsEntryRole addSegmentsEntryRole() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SegmentsEntryRole segmentsEntryRole = _persistence.create(pk);

		segmentsEntryRole.setMvccVersion(RandomTestUtil.nextLong());

		segmentsEntryRole.setCompanyId(RandomTestUtil.nextLong());

		segmentsEntryRole.setUserId(RandomTestUtil.nextLong());

		segmentsEntryRole.setUserName(RandomTestUtil.randomString());

		segmentsEntryRole.setCreateDate(RandomTestUtil.nextDate());

		segmentsEntryRole.setModifiedDate(RandomTestUtil.nextDate());

		segmentsEntryRole.setRoleId(RandomTestUtil.nextLong());

		segmentsEntryRole.setSegmentsEntryId(RandomTestUtil.nextLong());

		_segmentsEntryRoles.add(_persistence.update(segmentsEntryRole));

		return segmentsEntryRole;
	}

	private List<SegmentsEntryRole> _segmentsEntryRoles =
		new ArrayList<SegmentsEntryRole>();
	private SegmentsEntryRolePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}