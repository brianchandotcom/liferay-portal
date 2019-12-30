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

package com.liferay.microblogs.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.microblogs.exception.NoSuchEntryException;
import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.microblogs.service.MicroblogsEntryLocalServiceUtil;
import com.liferay.microblogs.service.persistence.MicroblogsEntryPersistence;
import com.liferay.microblogs.service.persistence.MicroblogsEntryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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
public class MicroblogsEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.microblogs.service"));

	@Before
	public void setUp() {
		_persistence = MicroblogsEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<MicroblogsEntry> iterator = _microblogsEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MicroblogsEntry microblogsEntry = _persistence.create(pk);

		Assert.assertNotNull(microblogsEntry);

		Assert.assertEquals(microblogsEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MicroblogsEntry newMicroblogsEntry = addMicroblogsEntry();

		_persistence.remove(newMicroblogsEntry);

		MicroblogsEntry existingMicroblogsEntry =
			_persistence.fetchByPrimaryKey(newMicroblogsEntry.getPrimaryKey());

		Assert.assertNull(existingMicroblogsEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMicroblogsEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MicroblogsEntry newMicroblogsEntry = _persistence.create(pk);

		newMicroblogsEntry.setCompanyId(RandomTestUtil.nextLong());

		newMicroblogsEntry.setUserId(RandomTestUtil.nextLong());

		newMicroblogsEntry.setUserName(RandomTestUtil.randomString());

		newMicroblogsEntry.setCreateDate(RandomTestUtil.nextDate());

		newMicroblogsEntry.setModifiedDate(RandomTestUtil.nextDate());

		newMicroblogsEntry.setCreatorClassNameId(RandomTestUtil.nextLong());

		newMicroblogsEntry.setCreatorClassPK(RandomTestUtil.nextLong());

		newMicroblogsEntry.setContent(RandomTestUtil.randomString());

		newMicroblogsEntry.setType(RandomTestUtil.nextInt());

		newMicroblogsEntry.setParentMicroblogsEntryId(
			RandomTestUtil.nextLong());

		newMicroblogsEntry.setSocialRelationType(RandomTestUtil.nextInt());

		_microblogsEntries.add(_persistence.update(newMicroblogsEntry));

		MicroblogsEntry existingMicroblogsEntry = _persistence.findByPrimaryKey(
			newMicroblogsEntry.getPrimaryKey());

		Assert.assertEquals(
			existingMicroblogsEntry.getMicroblogsEntryId(),
			newMicroblogsEntry.getMicroblogsEntryId());
		Assert.assertEquals(
			existingMicroblogsEntry.getCompanyId(),
			newMicroblogsEntry.getCompanyId());
		Assert.assertEquals(
			existingMicroblogsEntry.getUserId(),
			newMicroblogsEntry.getUserId());
		Assert.assertEquals(
			existingMicroblogsEntry.getUserName(),
			newMicroblogsEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingMicroblogsEntry.getCreateDate()),
			Time.getShortTimestamp(newMicroblogsEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingMicroblogsEntry.getModifiedDate()),
			Time.getShortTimestamp(newMicroblogsEntry.getModifiedDate()));
		Assert.assertEquals(
			existingMicroblogsEntry.getCreatorClassNameId(),
			newMicroblogsEntry.getCreatorClassNameId());
		Assert.assertEquals(
			existingMicroblogsEntry.getCreatorClassPK(),
			newMicroblogsEntry.getCreatorClassPK());
		Assert.assertEquals(
			existingMicroblogsEntry.getContent(),
			newMicroblogsEntry.getContent());
		Assert.assertEquals(
			existingMicroblogsEntry.getType(), newMicroblogsEntry.getType());
		Assert.assertEquals(
			existingMicroblogsEntry.getParentMicroblogsEntryId(),
			newMicroblogsEntry.getParentMicroblogsEntryId());
		Assert.assertEquals(
			existingMicroblogsEntry.getSocialRelationType(),
			newMicroblogsEntry.getSocialRelationType());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(RandomTestUtil.nextLong());

		_persistence.countByUserId(0L);
	}

	@Test
	public void testCountByU_T() throws Exception {
		_persistence.countByU_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByU_T(0L, 0);
	}

	@Test
	public void testCountByCCNI_CCPK() throws Exception {
		_persistence.countByCCNI_CCPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByCCNI_CCPK(0L, 0L);
	}

	@Test
	public void testCountByCCNI_CCPKArrayable() throws Exception {
		_persistence.countByCCNI_CCPK(
			RandomTestUtil.nextLong(),
			new long[] {RandomTestUtil.nextLong(), 0L});
	}

	@Test
	public void testCountByCCNI_T() throws Exception {
		_persistence.countByCCNI_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByCCNI_T(0L, 0);
	}

	@Test
	public void testCountByT_P() throws Exception {
		_persistence.countByT_P(
			RandomTestUtil.nextInt(), RandomTestUtil.nextLong());

		_persistence.countByT_P(0, 0L);
	}

	@Test
	public void testCountByC_CCNI_CCPK() throws Exception {
		_persistence.countByC_CCNI_CCPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_CCNI_CCPK(0L, 0L, 0L);
	}

	@Test
	public void testCountByC_CCNI_CCPKArrayable() throws Exception {
		_persistence.countByC_CCNI_CCPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			new long[] {RandomTestUtil.nextLong(), 0L});
	}

	@Test
	public void testCountByC_CCNI_T() throws Exception {
		_persistence.countByC_CCNI_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByC_CCNI_T(0L, 0L, 0);
	}

	@Test
	public void testCountByCCNI_CCPK_T() throws Exception {
		_persistence.countByCCNI_CCPK_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByCCNI_CCPK_T(0L, 0L, 0);
	}

	@Test
	public void testCountByCCNI_CCPK_TArrayable() throws Exception {
		_persistence.countByCCNI_CCPK_T(
			RandomTestUtil.nextLong(),
			new long[] {RandomTestUtil.nextLong(), 0L},
			RandomTestUtil.nextInt());
	}

	@Test
	public void testCountByC_CCNI_CCPK_T() throws Exception {
		_persistence.countByC_CCNI_CCPK_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByC_CCNI_CCPK_T(0L, 0L, 0L, 0);
	}

	@Test
	public void testCountByC_CCNI_CCPK_TArrayable() throws Exception {
		_persistence.countByC_CCNI_CCPK_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			new long[] {RandomTestUtil.nextLong(), 0L},
			RandomTestUtil.nextInt());
	}

	@Test
	public void testCountByU_C_T_S() throws Exception {
		_persistence.countByU_C_T_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextDate(),
			RandomTestUtil.nextInt(), RandomTestUtil.nextInt());

		_persistence.countByU_C_T_S(0L, RandomTestUtil.nextDate(), 0, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MicroblogsEntry newMicroblogsEntry = addMicroblogsEntry();

		MicroblogsEntry existingMicroblogsEntry = _persistence.findByPrimaryKey(
			newMicroblogsEntry.getPrimaryKey());

		Assert.assertEquals(existingMicroblogsEntry, newMicroblogsEntry);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<MicroblogsEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"MicroblogsEntry", "microblogsEntryId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "creatorClassNameId", true, "creatorClassPK",
			true, "content", true, "type", true, "parentMicroblogsEntryId",
			true, "socialRelationType", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MicroblogsEntry newMicroblogsEntry = addMicroblogsEntry();

		MicroblogsEntry existingMicroblogsEntry =
			_persistence.fetchByPrimaryKey(newMicroblogsEntry.getPrimaryKey());

		Assert.assertEquals(existingMicroblogsEntry, newMicroblogsEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MicroblogsEntry missingMicroblogsEntry = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingMicroblogsEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		MicroblogsEntry newMicroblogsEntry1 = addMicroblogsEntry();
		MicroblogsEntry newMicroblogsEntry2 = addMicroblogsEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMicroblogsEntry1.getPrimaryKey());
		primaryKeys.add(newMicroblogsEntry2.getPrimaryKey());

		Map<Serializable, MicroblogsEntry> microblogsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, microblogsEntries.size());
		Assert.assertEquals(
			newMicroblogsEntry1,
			microblogsEntries.get(newMicroblogsEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newMicroblogsEntry2,
			microblogsEntries.get(newMicroblogsEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, MicroblogsEntry> microblogsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(microblogsEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		MicroblogsEntry newMicroblogsEntry = addMicroblogsEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMicroblogsEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, MicroblogsEntry> microblogsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, microblogsEntries.size());
		Assert.assertEquals(
			newMicroblogsEntry,
			microblogsEntries.get(newMicroblogsEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, MicroblogsEntry> microblogsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(microblogsEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		MicroblogsEntry newMicroblogsEntry = addMicroblogsEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMicroblogsEntry.getPrimaryKey());

		Map<Serializable, MicroblogsEntry> microblogsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, microblogsEntries.size());
		Assert.assertEquals(
			newMicroblogsEntry,
			microblogsEntries.get(newMicroblogsEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			MicroblogsEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<MicroblogsEntry>() {

				@Override
				public void performAction(MicroblogsEntry microblogsEntry) {
					Assert.assertNotNull(microblogsEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		MicroblogsEntry newMicroblogsEntry = addMicroblogsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MicroblogsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"microblogsEntryId",
				newMicroblogsEntry.getMicroblogsEntryId()));

		List<MicroblogsEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		MicroblogsEntry existingMicroblogsEntry = result.get(0);

		Assert.assertEquals(existingMicroblogsEntry, newMicroblogsEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MicroblogsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"microblogsEntryId", RandomTestUtil.nextLong()));

		List<MicroblogsEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		MicroblogsEntry newMicroblogsEntry = addMicroblogsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MicroblogsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("microblogsEntryId"));

		Object newMicroblogsEntryId = newMicroblogsEntry.getMicroblogsEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"microblogsEntryId", new Object[] {newMicroblogsEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingMicroblogsEntryId = result.get(0);

		Assert.assertEquals(existingMicroblogsEntryId, newMicroblogsEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MicroblogsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("microblogsEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"microblogsEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected MicroblogsEntry addMicroblogsEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MicroblogsEntry microblogsEntry = _persistence.create(pk);

		microblogsEntry.setCompanyId(RandomTestUtil.nextLong());

		microblogsEntry.setUserId(RandomTestUtil.nextLong());

		microblogsEntry.setUserName(RandomTestUtil.randomString());

		microblogsEntry.setCreateDate(RandomTestUtil.nextDate());

		microblogsEntry.setModifiedDate(RandomTestUtil.nextDate());

		microblogsEntry.setCreatorClassNameId(RandomTestUtil.nextLong());

		microblogsEntry.setCreatorClassPK(RandomTestUtil.nextLong());

		microblogsEntry.setContent(RandomTestUtil.randomString());

		microblogsEntry.setType(RandomTestUtil.nextInt());

		microblogsEntry.setParentMicroblogsEntryId(RandomTestUtil.nextLong());

		microblogsEntry.setSocialRelationType(RandomTestUtil.nextInt());

		_microblogsEntries.add(_persistence.update(microblogsEntry));

		return microblogsEntry;
	}

	private List<MicroblogsEntry> _microblogsEntries =
		new ArrayList<MicroblogsEntry>();
	private MicroblogsEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}