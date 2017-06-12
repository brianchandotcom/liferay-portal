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

package com.liferay.commerce.product.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.product.exception.NoSuchCPTemplateLayoutEntryException;
import com.liferay.commerce.product.model.CPTemplateLayoutEntry;
import com.liferay.commerce.product.service.CPTemplateLayoutEntryLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPTemplateLayoutEntryPersistence;
import com.liferay.commerce.product.service.persistence.CPTemplateLayoutEntryUtil;

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
import com.liferay.portal.kernel.util.StringPool;
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
public class CPTemplateLayoutEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CPTemplateLayoutEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPTemplateLayoutEntry> iterator = _cpTemplateLayoutEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPTemplateLayoutEntry cpTemplateLayoutEntry = _persistence.create(pk);

		Assert.assertNotNull(cpTemplateLayoutEntry);

		Assert.assertEquals(cpTemplateLayoutEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPTemplateLayoutEntry newCPTemplateLayoutEntry = addCPTemplateLayoutEntry();

		_persistence.remove(newCPTemplateLayoutEntry);

		CPTemplateLayoutEntry existingCPTemplateLayoutEntry = _persistence.fetchByPrimaryKey(newCPTemplateLayoutEntry.getPrimaryKey());

		Assert.assertNull(existingCPTemplateLayoutEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPTemplateLayoutEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPTemplateLayoutEntry newCPTemplateLayoutEntry = _persistence.create(pk);

		newCPTemplateLayoutEntry.setUuid(RandomTestUtil.randomString());

		newCPTemplateLayoutEntry.setGroupId(RandomTestUtil.nextLong());

		newCPTemplateLayoutEntry.setCompanyId(RandomTestUtil.nextLong());

		newCPTemplateLayoutEntry.setUserId(RandomTestUtil.nextLong());

		newCPTemplateLayoutEntry.setUserName(RandomTestUtil.randomString());

		newCPTemplateLayoutEntry.setCreateDate(RandomTestUtil.nextDate());

		newCPTemplateLayoutEntry.setModifiedDate(RandomTestUtil.nextDate());

		newCPTemplateLayoutEntry.setClassNameId(RandomTestUtil.nextLong());

		newCPTemplateLayoutEntry.setClassPK(RandomTestUtil.nextLong());

		newCPTemplateLayoutEntry.setLayoutUuid(RandomTestUtil.randomString());

		_cpTemplateLayoutEntries.add(_persistence.update(
				newCPTemplateLayoutEntry));

		CPTemplateLayoutEntry existingCPTemplateLayoutEntry = _persistence.findByPrimaryKey(newCPTemplateLayoutEntry.getPrimaryKey());

		Assert.assertEquals(existingCPTemplateLayoutEntry.getUuid(),
			newCPTemplateLayoutEntry.getUuid());
		Assert.assertEquals(existingCPTemplateLayoutEntry.getCPFriendlyURLEntryId(),
			newCPTemplateLayoutEntry.getCPFriendlyURLEntryId());
		Assert.assertEquals(existingCPTemplateLayoutEntry.getGroupId(),
			newCPTemplateLayoutEntry.getGroupId());
		Assert.assertEquals(existingCPTemplateLayoutEntry.getCompanyId(),
			newCPTemplateLayoutEntry.getCompanyId());
		Assert.assertEquals(existingCPTemplateLayoutEntry.getUserId(),
			newCPTemplateLayoutEntry.getUserId());
		Assert.assertEquals(existingCPTemplateLayoutEntry.getUserName(),
			newCPTemplateLayoutEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPTemplateLayoutEntry.getCreateDate()),
			Time.getShortTimestamp(newCPTemplateLayoutEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPTemplateLayoutEntry.getModifiedDate()),
			Time.getShortTimestamp(newCPTemplateLayoutEntry.getModifiedDate()));
		Assert.assertEquals(existingCPTemplateLayoutEntry.getClassNameId(),
			newCPTemplateLayoutEntry.getClassNameId());
		Assert.assertEquals(existingCPTemplateLayoutEntry.getClassPK(),
			newCPTemplateLayoutEntry.getClassPK());
		Assert.assertEquals(existingCPTemplateLayoutEntry.getLayoutUuid(),
			newCPTemplateLayoutEntry.getLayoutUuid());
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
	public void testCountByG_C_C() throws Exception {
		_persistence.countByG_C_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByG_C_C(0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPTemplateLayoutEntry newCPTemplateLayoutEntry = addCPTemplateLayoutEntry();

		CPTemplateLayoutEntry existingCPTemplateLayoutEntry = _persistence.findByPrimaryKey(newCPTemplateLayoutEntry.getPrimaryKey());

		Assert.assertEquals(existingCPTemplateLayoutEntry,
			newCPTemplateLayoutEntry);
	}

	@Test(expected = NoSuchCPTemplateLayoutEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CPTemplateLayoutEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPTemplateLayoutEntry",
			"uuid", true, "CPFriendlyURLEntryId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "classNameId", true, "classPK", true,
			"layoutUuid", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPTemplateLayoutEntry newCPTemplateLayoutEntry = addCPTemplateLayoutEntry();

		CPTemplateLayoutEntry existingCPTemplateLayoutEntry = _persistence.fetchByPrimaryKey(newCPTemplateLayoutEntry.getPrimaryKey());

		Assert.assertEquals(existingCPTemplateLayoutEntry,
			newCPTemplateLayoutEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPTemplateLayoutEntry missingCPTemplateLayoutEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPTemplateLayoutEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CPTemplateLayoutEntry newCPTemplateLayoutEntry1 = addCPTemplateLayoutEntry();
		CPTemplateLayoutEntry newCPTemplateLayoutEntry2 = addCPTemplateLayoutEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPTemplateLayoutEntry1.getPrimaryKey());
		primaryKeys.add(newCPTemplateLayoutEntry2.getPrimaryKey());

		Map<Serializable, CPTemplateLayoutEntry> cpTemplateLayoutEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpTemplateLayoutEntries.size());
		Assert.assertEquals(newCPTemplateLayoutEntry1,
			cpTemplateLayoutEntries.get(
				newCPTemplateLayoutEntry1.getPrimaryKey()));
		Assert.assertEquals(newCPTemplateLayoutEntry2,
			cpTemplateLayoutEntries.get(
				newCPTemplateLayoutEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPTemplateLayoutEntry> cpTemplateLayoutEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpTemplateLayoutEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CPTemplateLayoutEntry newCPTemplateLayoutEntry = addCPTemplateLayoutEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPTemplateLayoutEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPTemplateLayoutEntry> cpTemplateLayoutEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpTemplateLayoutEntries.size());
		Assert.assertEquals(newCPTemplateLayoutEntry,
			cpTemplateLayoutEntries.get(
				newCPTemplateLayoutEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPTemplateLayoutEntry> cpTemplateLayoutEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpTemplateLayoutEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CPTemplateLayoutEntry newCPTemplateLayoutEntry = addCPTemplateLayoutEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPTemplateLayoutEntry.getPrimaryKey());

		Map<Serializable, CPTemplateLayoutEntry> cpTemplateLayoutEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpTemplateLayoutEntries.size());
		Assert.assertEquals(newCPTemplateLayoutEntry,
			cpTemplateLayoutEntries.get(
				newCPTemplateLayoutEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CPTemplateLayoutEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CPTemplateLayoutEntry>() {
				@Override
				public void performAction(
					CPTemplateLayoutEntry cpTemplateLayoutEntry) {
					Assert.assertNotNull(cpTemplateLayoutEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CPTemplateLayoutEntry newCPTemplateLayoutEntry = addCPTemplateLayoutEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPTemplateLayoutEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPFriendlyURLEntryId",
				newCPTemplateLayoutEntry.getCPFriendlyURLEntryId()));

		List<CPTemplateLayoutEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPTemplateLayoutEntry existingCPTemplateLayoutEntry = result.get(0);

		Assert.assertEquals(existingCPTemplateLayoutEntry,
			newCPTemplateLayoutEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPTemplateLayoutEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPFriendlyURLEntryId",
				RandomTestUtil.nextLong()));

		List<CPTemplateLayoutEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CPTemplateLayoutEntry newCPTemplateLayoutEntry = addCPTemplateLayoutEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPTemplateLayoutEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPFriendlyURLEntryId"));

		Object newCPFriendlyURLEntryId = newCPTemplateLayoutEntry.getCPFriendlyURLEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPFriendlyURLEntryId",
				new Object[] { newCPFriendlyURLEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPFriendlyURLEntryId = result.get(0);

		Assert.assertEquals(existingCPFriendlyURLEntryId,
			newCPFriendlyURLEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPTemplateLayoutEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPFriendlyURLEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPFriendlyURLEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPTemplateLayoutEntry newCPTemplateLayoutEntry = addCPTemplateLayoutEntry();

		_persistence.clearCache();

		CPTemplateLayoutEntry existingCPTemplateLayoutEntry = _persistence.findByPrimaryKey(newCPTemplateLayoutEntry.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingCPTemplateLayoutEntry.getUuid(),
				ReflectionTestUtil.invoke(existingCPTemplateLayoutEntry,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCPTemplateLayoutEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPTemplateLayoutEntry,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingCPTemplateLayoutEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPTemplateLayoutEntry,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCPTemplateLayoutEntry.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(existingCPTemplateLayoutEntry,
				"getOriginalClassNameId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCPTemplateLayoutEntry.getClassPK()),
			ReflectionTestUtil.<Long>invoke(existingCPTemplateLayoutEntry,
				"getOriginalClassPK", new Class<?>[0]));
	}

	protected CPTemplateLayoutEntry addCPTemplateLayoutEntry()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPTemplateLayoutEntry cpTemplateLayoutEntry = _persistence.create(pk);

		cpTemplateLayoutEntry.setUuid(RandomTestUtil.randomString());

		cpTemplateLayoutEntry.setGroupId(RandomTestUtil.nextLong());

		cpTemplateLayoutEntry.setCompanyId(RandomTestUtil.nextLong());

		cpTemplateLayoutEntry.setUserId(RandomTestUtil.nextLong());

		cpTemplateLayoutEntry.setUserName(RandomTestUtil.randomString());

		cpTemplateLayoutEntry.setCreateDate(RandomTestUtil.nextDate());

		cpTemplateLayoutEntry.setModifiedDate(RandomTestUtil.nextDate());

		cpTemplateLayoutEntry.setClassNameId(RandomTestUtil.nextLong());

		cpTemplateLayoutEntry.setClassPK(RandomTestUtil.nextLong());

		cpTemplateLayoutEntry.setLayoutUuid(RandomTestUtil.randomString());

		_cpTemplateLayoutEntries.add(_persistence.update(cpTemplateLayoutEntry));

		return cpTemplateLayoutEntry;
	}

	private List<CPTemplateLayoutEntry> _cpTemplateLayoutEntries = new ArrayList<CPTemplateLayoutEntry>();
	private CPTemplateLayoutEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}