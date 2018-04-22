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

import com.liferay.commerce.product.exception.NoSuchCPFriendlyURLEntryException;
import com.liferay.commerce.product.model.CPFriendlyURLEntry;
import com.liferay.commerce.product.service.CPFriendlyURLEntryLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPFriendlyURLEntryPersistence;
import com.liferay.commerce.product.service.persistence.CPFriendlyURLEntryUtil;

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
public class CPFriendlyURLEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CPFriendlyURLEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPFriendlyURLEntry> iterator = _cpFriendlyURLEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPFriendlyURLEntry cpFriendlyURLEntry = _persistence.create(pk);

		Assert.assertNotNull(cpFriendlyURLEntry);

		Assert.assertEquals(cpFriendlyURLEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPFriendlyURLEntry newCPFriendlyURLEntry = addCPFriendlyURLEntry();

		_persistence.remove(newCPFriendlyURLEntry);

		CPFriendlyURLEntry existingCPFriendlyURLEntry = _persistence.fetchByPrimaryKey(newCPFriendlyURLEntry.getPrimaryKey());

		Assert.assertNull(existingCPFriendlyURLEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPFriendlyURLEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPFriendlyURLEntry newCPFriendlyURLEntry = _persistence.create(pk);

		newCPFriendlyURLEntry.setUuid(RandomTestUtil.randomString());

		newCPFriendlyURLEntry.setGroupId(RandomTestUtil.nextLong());

		newCPFriendlyURLEntry.setCompanyId(RandomTestUtil.nextLong());

		newCPFriendlyURLEntry.setUserId(RandomTestUtil.nextLong());

		newCPFriendlyURLEntry.setUserName(RandomTestUtil.randomString());

		newCPFriendlyURLEntry.setCreateDate(RandomTestUtil.nextDate());

		newCPFriendlyURLEntry.setModifiedDate(RandomTestUtil.nextDate());

		newCPFriendlyURLEntry.setClassNameId(RandomTestUtil.nextLong());

		newCPFriendlyURLEntry.setClassPK(RandomTestUtil.nextLong());

		newCPFriendlyURLEntry.setLanguageId(RandomTestUtil.randomString());

		newCPFriendlyURLEntry.setUrlTitle(RandomTestUtil.randomString());

		newCPFriendlyURLEntry.setMain(RandomTestUtil.randomBoolean());

		_cpFriendlyURLEntries.add(_persistence.update(newCPFriendlyURLEntry));

		CPFriendlyURLEntry existingCPFriendlyURLEntry = _persistence.findByPrimaryKey(newCPFriendlyURLEntry.getPrimaryKey());

		Assert.assertEquals(existingCPFriendlyURLEntry.getUuid(),
			newCPFriendlyURLEntry.getUuid());
		Assert.assertEquals(existingCPFriendlyURLEntry.getCPFriendlyURLEntryId(),
			newCPFriendlyURLEntry.getCPFriendlyURLEntryId());
		Assert.assertEquals(existingCPFriendlyURLEntry.getGroupId(),
			newCPFriendlyURLEntry.getGroupId());
		Assert.assertEquals(existingCPFriendlyURLEntry.getCompanyId(),
			newCPFriendlyURLEntry.getCompanyId());
		Assert.assertEquals(existingCPFriendlyURLEntry.getUserId(),
			newCPFriendlyURLEntry.getUserId());
		Assert.assertEquals(existingCPFriendlyURLEntry.getUserName(),
			newCPFriendlyURLEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPFriendlyURLEntry.getCreateDate()),
			Time.getShortTimestamp(newCPFriendlyURLEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPFriendlyURLEntry.getModifiedDate()),
			Time.getShortTimestamp(newCPFriendlyURLEntry.getModifiedDate()));
		Assert.assertEquals(existingCPFriendlyURLEntry.getClassNameId(),
			newCPFriendlyURLEntry.getClassNameId());
		Assert.assertEquals(existingCPFriendlyURLEntry.getClassPK(),
			newCPFriendlyURLEntry.getClassPK());
		Assert.assertEquals(existingCPFriendlyURLEntry.getLanguageId(),
			newCPFriendlyURLEntry.getLanguageId());
		Assert.assertEquals(existingCPFriendlyURLEntry.getUrlTitle(),
			newCPFriendlyURLEntry.getUrlTitle());
		Assert.assertEquals(existingCPFriendlyURLEntry.isMain(),
			newCPFriendlyURLEntry.isMain());
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
	public void testCountByG_C_C() throws Exception {
		_persistence.countByG_C_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByG_C_C(0L, 0L, 0L);
	}

	@Test
	public void testCountByG_C_U() throws Exception {
		_persistence.countByG_C_U(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), "");

		_persistence.countByG_C_U(0L, 0L, "null");

		_persistence.countByG_C_U(0L, 0L, (String)null);
	}

	@Test
	public void testCountByG_C_C_M() throws Exception {
		_persistence.countByG_C_C_M(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_C_C_M(0L, 0L, 0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_C_L_U() throws Exception {
		_persistence.countByG_C_L_U(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), "", "");

		_persistence.countByG_C_L_U(0L, 0L, "null", "null");

		_persistence.countByG_C_L_U(0L, 0L, (String)null, (String)null);
	}

	@Test
	public void testCountByG_C_C_L_U() throws Exception {
		_persistence.countByG_C_C_L_U(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "", "");

		_persistence.countByG_C_C_L_U(0L, 0L, 0L, "null", "null");

		_persistence.countByG_C_C_L_U(0L, 0L, 0L, (String)null, (String)null);
	}

	@Test
	public void testCountByG_C_C_L_M() throws Exception {
		_persistence.countByG_C_C_L_M(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "",
			RandomTestUtil.randomBoolean());

		_persistence.countByG_C_C_L_M(0L, 0L, 0L, "null",
			RandomTestUtil.randomBoolean());

		_persistence.countByG_C_C_L_M(0L, 0L, 0L, (String)null,
			RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPFriendlyURLEntry newCPFriendlyURLEntry = addCPFriendlyURLEntry();

		CPFriendlyURLEntry existingCPFriendlyURLEntry = _persistence.findByPrimaryKey(newCPFriendlyURLEntry.getPrimaryKey());

		Assert.assertEquals(existingCPFriendlyURLEntry, newCPFriendlyURLEntry);
	}

	@Test(expected = NoSuchCPFriendlyURLEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CPFriendlyURLEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPFriendlyURLEntry",
			"uuid", true, "CPFriendlyURLEntryId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "classNameId", true, "classPK", true,
			"languageId", true, "urlTitle", true, "main", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPFriendlyURLEntry newCPFriendlyURLEntry = addCPFriendlyURLEntry();

		CPFriendlyURLEntry existingCPFriendlyURLEntry = _persistence.fetchByPrimaryKey(newCPFriendlyURLEntry.getPrimaryKey());

		Assert.assertEquals(existingCPFriendlyURLEntry, newCPFriendlyURLEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPFriendlyURLEntry missingCPFriendlyURLEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPFriendlyURLEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CPFriendlyURLEntry newCPFriendlyURLEntry1 = addCPFriendlyURLEntry();
		CPFriendlyURLEntry newCPFriendlyURLEntry2 = addCPFriendlyURLEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPFriendlyURLEntry1.getPrimaryKey());
		primaryKeys.add(newCPFriendlyURLEntry2.getPrimaryKey());

		Map<Serializable, CPFriendlyURLEntry> cpFriendlyURLEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpFriendlyURLEntries.size());
		Assert.assertEquals(newCPFriendlyURLEntry1,
			cpFriendlyURLEntries.get(newCPFriendlyURLEntry1.getPrimaryKey()));
		Assert.assertEquals(newCPFriendlyURLEntry2,
			cpFriendlyURLEntries.get(newCPFriendlyURLEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPFriendlyURLEntry> cpFriendlyURLEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpFriendlyURLEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CPFriendlyURLEntry newCPFriendlyURLEntry = addCPFriendlyURLEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPFriendlyURLEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPFriendlyURLEntry> cpFriendlyURLEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpFriendlyURLEntries.size());
		Assert.assertEquals(newCPFriendlyURLEntry,
			cpFriendlyURLEntries.get(newCPFriendlyURLEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPFriendlyURLEntry> cpFriendlyURLEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpFriendlyURLEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CPFriendlyURLEntry newCPFriendlyURLEntry = addCPFriendlyURLEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPFriendlyURLEntry.getPrimaryKey());

		Map<Serializable, CPFriendlyURLEntry> cpFriendlyURLEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpFriendlyURLEntries.size());
		Assert.assertEquals(newCPFriendlyURLEntry,
			cpFriendlyURLEntries.get(newCPFriendlyURLEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CPFriendlyURLEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CPFriendlyURLEntry>() {
				@Override
				public void performAction(CPFriendlyURLEntry cpFriendlyURLEntry) {
					Assert.assertNotNull(cpFriendlyURLEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CPFriendlyURLEntry newCPFriendlyURLEntry = addCPFriendlyURLEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPFriendlyURLEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPFriendlyURLEntryId",
				newCPFriendlyURLEntry.getCPFriendlyURLEntryId()));

		List<CPFriendlyURLEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPFriendlyURLEntry existingCPFriendlyURLEntry = result.get(0);

		Assert.assertEquals(existingCPFriendlyURLEntry, newCPFriendlyURLEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPFriendlyURLEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPFriendlyURLEntryId",
				RandomTestUtil.nextLong()));

		List<CPFriendlyURLEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CPFriendlyURLEntry newCPFriendlyURLEntry = addCPFriendlyURLEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPFriendlyURLEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPFriendlyURLEntryId"));

		Object newCPFriendlyURLEntryId = newCPFriendlyURLEntry.getCPFriendlyURLEntryId();

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
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPFriendlyURLEntry.class,
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
		CPFriendlyURLEntry newCPFriendlyURLEntry = addCPFriendlyURLEntry();

		_persistence.clearCache();

		CPFriendlyURLEntry existingCPFriendlyURLEntry = _persistence.findByPrimaryKey(newCPFriendlyURLEntry.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingCPFriendlyURLEntry.getUuid(),
				ReflectionTestUtil.invoke(existingCPFriendlyURLEntry,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCPFriendlyURLEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPFriendlyURLEntry,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingCPFriendlyURLEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPFriendlyURLEntry,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCPFriendlyURLEntry.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(existingCPFriendlyURLEntry,
				"getOriginalClassNameId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingCPFriendlyURLEntry.getLanguageId(),
				ReflectionTestUtil.invoke(existingCPFriendlyURLEntry,
					"getOriginalLanguageId", new Class<?>[0])));
		Assert.assertTrue(Objects.equals(
				existingCPFriendlyURLEntry.getUrlTitle(),
				ReflectionTestUtil.invoke(existingCPFriendlyURLEntry,
					"getOriginalUrlTitle", new Class<?>[0])));

		Assert.assertEquals(Long.valueOf(
				existingCPFriendlyURLEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPFriendlyURLEntry,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCPFriendlyURLEntry.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(existingCPFriendlyURLEntry,
				"getOriginalClassNameId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCPFriendlyURLEntry.getClassPK()),
			ReflectionTestUtil.<Long>invoke(existingCPFriendlyURLEntry,
				"getOriginalClassPK", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingCPFriendlyURLEntry.getLanguageId(),
				ReflectionTestUtil.invoke(existingCPFriendlyURLEntry,
					"getOriginalLanguageId", new Class<?>[0])));
		Assert.assertTrue(Objects.equals(
				existingCPFriendlyURLEntry.getUrlTitle(),
				ReflectionTestUtil.invoke(existingCPFriendlyURLEntry,
					"getOriginalUrlTitle", new Class<?>[0])));

		Assert.assertEquals(Long.valueOf(
				existingCPFriendlyURLEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPFriendlyURLEntry,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCPFriendlyURLEntry.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(existingCPFriendlyURLEntry,
				"getOriginalClassNameId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCPFriendlyURLEntry.getClassPK()),
			ReflectionTestUtil.<Long>invoke(existingCPFriendlyURLEntry,
				"getOriginalClassPK", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingCPFriendlyURLEntry.getLanguageId(),
				ReflectionTestUtil.invoke(existingCPFriendlyURLEntry,
					"getOriginalLanguageId", new Class<?>[0])));
		Assert.assertEquals(Boolean.valueOf(
				existingCPFriendlyURLEntry.getMain()),
			ReflectionTestUtil.<Boolean>invoke(existingCPFriendlyURLEntry,
				"getOriginalMain", new Class<?>[0]));
	}

	protected CPFriendlyURLEntry addCPFriendlyURLEntry()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPFriendlyURLEntry cpFriendlyURLEntry = _persistence.create(pk);

		cpFriendlyURLEntry.setUuid(RandomTestUtil.randomString());

		cpFriendlyURLEntry.setGroupId(RandomTestUtil.nextLong());

		cpFriendlyURLEntry.setCompanyId(RandomTestUtil.nextLong());

		cpFriendlyURLEntry.setUserId(RandomTestUtil.nextLong());

		cpFriendlyURLEntry.setUserName(RandomTestUtil.randomString());

		cpFriendlyURLEntry.setCreateDate(RandomTestUtil.nextDate());

		cpFriendlyURLEntry.setModifiedDate(RandomTestUtil.nextDate());

		cpFriendlyURLEntry.setClassNameId(RandomTestUtil.nextLong());

		cpFriendlyURLEntry.setClassPK(RandomTestUtil.nextLong());

		cpFriendlyURLEntry.setLanguageId(RandomTestUtil.randomString());

		cpFriendlyURLEntry.setUrlTitle(RandomTestUtil.randomString());

		cpFriendlyURLEntry.setMain(RandomTestUtil.randomBoolean());

		_cpFriendlyURLEntries.add(_persistence.update(cpFriendlyURLEntry));

		return cpFriendlyURLEntry;
	}

	private List<CPFriendlyURLEntry> _cpFriendlyURLEntries = new ArrayList<CPFriendlyURLEntry>();
	private CPFriendlyURLEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}