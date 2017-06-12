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

import com.liferay.commerce.product.exception.NoSuchCPFriendlyUrlEntryException;
import com.liferay.commerce.product.model.CPFriendlyUrlEntry;
import com.liferay.commerce.product.service.CPFriendlyUrlEntryLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPFriendlyUrlEntryPersistence;
import com.liferay.commerce.product.service.persistence.CPFriendlyUrlEntryUtil;

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
public class CPFriendlyUrlEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CPFriendlyUrlEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPFriendlyUrlEntry> iterator = _cpFriendlyUrlEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPFriendlyUrlEntry cpFriendlyUrlEntry = _persistence.create(pk);

		Assert.assertNotNull(cpFriendlyUrlEntry);

		Assert.assertEquals(cpFriendlyUrlEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPFriendlyUrlEntry newCPFriendlyUrlEntry = addCPFriendlyUrlEntry();

		_persistence.remove(newCPFriendlyUrlEntry);

		CPFriendlyUrlEntry existingCPFriendlyUrlEntry = _persistence.fetchByPrimaryKey(newCPFriendlyUrlEntry.getPrimaryKey());

		Assert.assertNull(existingCPFriendlyUrlEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPFriendlyUrlEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPFriendlyUrlEntry newCPFriendlyUrlEntry = _persistence.create(pk);

		newCPFriendlyUrlEntry.setUuid(RandomTestUtil.randomString());

		newCPFriendlyUrlEntry.setGroupId(RandomTestUtil.nextLong());

		newCPFriendlyUrlEntry.setCompanyId(RandomTestUtil.nextLong());

		newCPFriendlyUrlEntry.setUserId(RandomTestUtil.nextLong());

		newCPFriendlyUrlEntry.setUserName(RandomTestUtil.randomString());

		newCPFriendlyUrlEntry.setCreateDate(RandomTestUtil.nextDate());

		newCPFriendlyUrlEntry.setModifiedDate(RandomTestUtil.nextDate());

		newCPFriendlyUrlEntry.setClassNameId(RandomTestUtil.nextLong());

		newCPFriendlyUrlEntry.setClassPK(RandomTestUtil.nextLong());

		newCPFriendlyUrlEntry.setLanguageId(RandomTestUtil.randomString());

		newCPFriendlyUrlEntry.setUrlTitle(RandomTestUtil.randomString());

		newCPFriendlyUrlEntry.setMain(RandomTestUtil.randomBoolean());

		_cpFriendlyUrlEntries.add(_persistence.update(newCPFriendlyUrlEntry));

		CPFriendlyUrlEntry existingCPFriendlyUrlEntry = _persistence.findByPrimaryKey(newCPFriendlyUrlEntry.getPrimaryKey());

		Assert.assertEquals(existingCPFriendlyUrlEntry.getUuid(),
			newCPFriendlyUrlEntry.getUuid());
		Assert.assertEquals(existingCPFriendlyUrlEntry.getCPFriendlyUrlEntryId(),
			newCPFriendlyUrlEntry.getCPFriendlyUrlEntryId());
		Assert.assertEquals(existingCPFriendlyUrlEntry.getGroupId(),
			newCPFriendlyUrlEntry.getGroupId());
		Assert.assertEquals(existingCPFriendlyUrlEntry.getCompanyId(),
			newCPFriendlyUrlEntry.getCompanyId());
		Assert.assertEquals(existingCPFriendlyUrlEntry.getUserId(),
			newCPFriendlyUrlEntry.getUserId());
		Assert.assertEquals(existingCPFriendlyUrlEntry.getUserName(),
			newCPFriendlyUrlEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPFriendlyUrlEntry.getCreateDate()),
			Time.getShortTimestamp(newCPFriendlyUrlEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPFriendlyUrlEntry.getModifiedDate()),
			Time.getShortTimestamp(newCPFriendlyUrlEntry.getModifiedDate()));
		Assert.assertEquals(existingCPFriendlyUrlEntry.getClassNameId(),
			newCPFriendlyUrlEntry.getClassNameId());
		Assert.assertEquals(existingCPFriendlyUrlEntry.getClassPK(),
			newCPFriendlyUrlEntry.getClassPK());
		Assert.assertEquals(existingCPFriendlyUrlEntry.getLanguageId(),
			newCPFriendlyUrlEntry.getLanguageId());
		Assert.assertEquals(existingCPFriendlyUrlEntry.getUrlTitle(),
			newCPFriendlyUrlEntry.getUrlTitle());
		Assert.assertEquals(existingCPFriendlyUrlEntry.getMain(),
			newCPFriendlyUrlEntry.getMain());
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
	public void testCountByG_U_L() throws Exception {
		_persistence.countByG_U_L(RandomTestUtil.nextLong(), StringPool.BLANK,
			StringPool.BLANK);

		_persistence.countByG_U_L(0L, StringPool.NULL, StringPool.NULL);

		_persistence.countByG_U_L(0L, (String)null, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPFriendlyUrlEntry newCPFriendlyUrlEntry = addCPFriendlyUrlEntry();

		CPFriendlyUrlEntry existingCPFriendlyUrlEntry = _persistence.findByPrimaryKey(newCPFriendlyUrlEntry.getPrimaryKey());

		Assert.assertEquals(existingCPFriendlyUrlEntry, newCPFriendlyUrlEntry);
	}

	@Test(expected = NoSuchCPFriendlyUrlEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CPFriendlyUrlEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPFriendlyUrlEntry",
			"uuid", true, "CPFriendlyUrlEntryId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "classNameId", true, "classPK", true,
			"languageId", true, "urlTitle", true, "main", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPFriendlyUrlEntry newCPFriendlyUrlEntry = addCPFriendlyUrlEntry();

		CPFriendlyUrlEntry existingCPFriendlyUrlEntry = _persistence.fetchByPrimaryKey(newCPFriendlyUrlEntry.getPrimaryKey());

		Assert.assertEquals(existingCPFriendlyUrlEntry, newCPFriendlyUrlEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPFriendlyUrlEntry missingCPFriendlyUrlEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPFriendlyUrlEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CPFriendlyUrlEntry newCPFriendlyUrlEntry1 = addCPFriendlyUrlEntry();
		CPFriendlyUrlEntry newCPFriendlyUrlEntry2 = addCPFriendlyUrlEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPFriendlyUrlEntry1.getPrimaryKey());
		primaryKeys.add(newCPFriendlyUrlEntry2.getPrimaryKey());

		Map<Serializable, CPFriendlyUrlEntry> cpFriendlyUrlEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpFriendlyUrlEntries.size());
		Assert.assertEquals(newCPFriendlyUrlEntry1,
			cpFriendlyUrlEntries.get(newCPFriendlyUrlEntry1.getPrimaryKey()));
		Assert.assertEquals(newCPFriendlyUrlEntry2,
			cpFriendlyUrlEntries.get(newCPFriendlyUrlEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPFriendlyUrlEntry> cpFriendlyUrlEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpFriendlyUrlEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CPFriendlyUrlEntry newCPFriendlyUrlEntry = addCPFriendlyUrlEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPFriendlyUrlEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPFriendlyUrlEntry> cpFriendlyUrlEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpFriendlyUrlEntries.size());
		Assert.assertEquals(newCPFriendlyUrlEntry,
			cpFriendlyUrlEntries.get(newCPFriendlyUrlEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPFriendlyUrlEntry> cpFriendlyUrlEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpFriendlyUrlEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CPFriendlyUrlEntry newCPFriendlyUrlEntry = addCPFriendlyUrlEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPFriendlyUrlEntry.getPrimaryKey());

		Map<Serializable, CPFriendlyUrlEntry> cpFriendlyUrlEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpFriendlyUrlEntries.size());
		Assert.assertEquals(newCPFriendlyUrlEntry,
			cpFriendlyUrlEntries.get(newCPFriendlyUrlEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CPFriendlyUrlEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CPFriendlyUrlEntry>() {
				@Override
				public void performAction(CPFriendlyUrlEntry cpFriendlyUrlEntry) {
					Assert.assertNotNull(cpFriendlyUrlEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CPFriendlyUrlEntry newCPFriendlyUrlEntry = addCPFriendlyUrlEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPFriendlyUrlEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPFriendlyUrlEntryId",
				newCPFriendlyUrlEntry.getCPFriendlyUrlEntryId()));

		List<CPFriendlyUrlEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPFriendlyUrlEntry existingCPFriendlyUrlEntry = result.get(0);

		Assert.assertEquals(existingCPFriendlyUrlEntry, newCPFriendlyUrlEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPFriendlyUrlEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPFriendlyUrlEntryId",
				RandomTestUtil.nextLong()));

		List<CPFriendlyUrlEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CPFriendlyUrlEntry newCPFriendlyUrlEntry = addCPFriendlyUrlEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPFriendlyUrlEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPFriendlyUrlEntryId"));

		Object newCPFriendlyUrlEntryId = newCPFriendlyUrlEntry.getCPFriendlyUrlEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPFriendlyUrlEntryId",
				new Object[] { newCPFriendlyUrlEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPFriendlyUrlEntryId = result.get(0);

		Assert.assertEquals(existingCPFriendlyUrlEntryId,
			newCPFriendlyUrlEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPFriendlyUrlEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPFriendlyUrlEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPFriendlyUrlEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPFriendlyUrlEntry newCPFriendlyUrlEntry = addCPFriendlyUrlEntry();

		_persistence.clearCache();

		CPFriendlyUrlEntry existingCPFriendlyUrlEntry = _persistence.findByPrimaryKey(newCPFriendlyUrlEntry.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingCPFriendlyUrlEntry.getUuid(),
				ReflectionTestUtil.invoke(existingCPFriendlyUrlEntry,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCPFriendlyUrlEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPFriendlyUrlEntry,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingCPFriendlyUrlEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPFriendlyUrlEntry,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingCPFriendlyUrlEntry.getUrlTitle(),
				ReflectionTestUtil.invoke(existingCPFriendlyUrlEntry,
					"getOriginalUrlTitle", new Class<?>[0])));
		Assert.assertTrue(Objects.equals(
				existingCPFriendlyUrlEntry.getLanguageId(),
				ReflectionTestUtil.invoke(existingCPFriendlyUrlEntry,
					"getOriginalLanguageId", new Class<?>[0])));
	}

	protected CPFriendlyUrlEntry addCPFriendlyUrlEntry()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPFriendlyUrlEntry cpFriendlyUrlEntry = _persistence.create(pk);

		cpFriendlyUrlEntry.setUuid(RandomTestUtil.randomString());

		cpFriendlyUrlEntry.setGroupId(RandomTestUtil.nextLong());

		cpFriendlyUrlEntry.setCompanyId(RandomTestUtil.nextLong());

		cpFriendlyUrlEntry.setUserId(RandomTestUtil.nextLong());

		cpFriendlyUrlEntry.setUserName(RandomTestUtil.randomString());

		cpFriendlyUrlEntry.setCreateDate(RandomTestUtil.nextDate());

		cpFriendlyUrlEntry.setModifiedDate(RandomTestUtil.nextDate());

		cpFriendlyUrlEntry.setClassNameId(RandomTestUtil.nextLong());

		cpFriendlyUrlEntry.setClassPK(RandomTestUtil.nextLong());

		cpFriendlyUrlEntry.setLanguageId(RandomTestUtil.randomString());

		cpFriendlyUrlEntry.setUrlTitle(RandomTestUtil.randomString());

		cpFriendlyUrlEntry.setMain(RandomTestUtil.randomBoolean());

		_cpFriendlyUrlEntries.add(_persistence.update(cpFriendlyUrlEntry));

		return cpFriendlyUrlEntry;
	}

	private List<CPFriendlyUrlEntry> _cpFriendlyUrlEntries = new ArrayList<CPFriendlyUrlEntry>();
	private CPFriendlyUrlEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}