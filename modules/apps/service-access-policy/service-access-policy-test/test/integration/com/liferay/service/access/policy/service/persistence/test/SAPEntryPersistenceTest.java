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

package com.liferay.service.access.policy.service.persistence.test;

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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.service.access.policy.exception.NoSuchEntryException;
import com.liferay.service.access.policy.model.SAPEntry;
import com.liferay.service.access.policy.service.SAPEntryLocalServiceUtil;
import com.liferay.service.access.policy.service.persistence.SAPEntryPersistence;
import com.liferay.service.access.policy.service.persistence.SAPEntryUtil;

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
public class SAPEntryPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = SAPEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SAPEntry> iterator = _sapEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SAPEntry sapEntry = _persistence.create(pk);

		Assert.assertNotNull(sapEntry);

		Assert.assertEquals(sapEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SAPEntry newSAPEntry = addSAPEntry();

		_persistence.remove(newSAPEntry);

		SAPEntry existingSAPEntry = _persistence.fetchByPrimaryKey(newSAPEntry.getPrimaryKey());

		Assert.assertNull(existingSAPEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSAPEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SAPEntry newSAPEntry = _persistence.create(pk);

		newSAPEntry.setUuid(RandomTestUtil.randomString());

		newSAPEntry.setCompanyId(RandomTestUtil.nextLong());

		newSAPEntry.setUserId(RandomTestUtil.nextLong());

		newSAPEntry.setUserName(RandomTestUtil.randomString());

		newSAPEntry.setCreateDate(RandomTestUtil.nextDate());

		newSAPEntry.setModifiedDate(RandomTestUtil.nextDate());

		newSAPEntry.setAllowedServiceSignatures(RandomTestUtil.randomString());

		newSAPEntry.setDefaultSAPEntry(RandomTestUtil.randomBoolean());

		newSAPEntry.setEnabled(RandomTestUtil.randomBoolean());

		newSAPEntry.setName(RandomTestUtil.randomString());

		newSAPEntry.setTitle(RandomTestUtil.randomString());

		_sapEntries.add(_persistence.update(newSAPEntry));

		SAPEntry existingSAPEntry = _persistence.findByPrimaryKey(newSAPEntry.getPrimaryKey());

		Assert.assertEquals(existingSAPEntry.getUuid(), newSAPEntry.getUuid());
		Assert.assertEquals(existingSAPEntry.getSapEntryId(),
			newSAPEntry.getSapEntryId());
		Assert.assertEquals(existingSAPEntry.getCompanyId(),
			newSAPEntry.getCompanyId());
		Assert.assertEquals(existingSAPEntry.getUserId(),
			newSAPEntry.getUserId());
		Assert.assertEquals(existingSAPEntry.getUserName(),
			newSAPEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingSAPEntry.getCreateDate()),
			Time.getShortTimestamp(newSAPEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingSAPEntry.getModifiedDate()),
			Time.getShortTimestamp(newSAPEntry.getModifiedDate()));
		Assert.assertEquals(existingSAPEntry.getAllowedServiceSignatures(),
			newSAPEntry.getAllowedServiceSignatures());
		Assert.assertEquals(existingSAPEntry.getDefaultSAPEntry(),
			newSAPEntry.getDefaultSAPEntry());
		Assert.assertEquals(existingSAPEntry.getEnabled(),
			newSAPEntry.getEnabled());
		Assert.assertEquals(existingSAPEntry.getName(), newSAPEntry.getName());
		Assert.assertEquals(existingSAPEntry.getTitle(), newSAPEntry.getTitle());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByC_N() throws Exception {
		_persistence.countByC_N(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByC_N(0L, StringPool.NULL);

		_persistence.countByC_N(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SAPEntry newSAPEntry = addSAPEntry();

		SAPEntry existingSAPEntry = _persistence.findByPrimaryKey(newSAPEntry.getPrimaryKey());

		Assert.assertEquals(existingSAPEntry, newSAPEntry);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<SAPEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("SAPEntry", "uuid", true,
			"sapEntryId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true,
			"allowedServiceSignatures", true, "defaultSAPEntry", true,
			"enabled", true, "name", true, "title", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SAPEntry newSAPEntry = addSAPEntry();

		SAPEntry existingSAPEntry = _persistence.fetchByPrimaryKey(newSAPEntry.getPrimaryKey());

		Assert.assertEquals(existingSAPEntry, newSAPEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SAPEntry missingSAPEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSAPEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		SAPEntry newSAPEntry1 = addSAPEntry();
		SAPEntry newSAPEntry2 = addSAPEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSAPEntry1.getPrimaryKey());
		primaryKeys.add(newSAPEntry2.getPrimaryKey());

		Map<Serializable, SAPEntry> sapEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, sapEntries.size());
		Assert.assertEquals(newSAPEntry1,
			sapEntries.get(newSAPEntry1.getPrimaryKey()));
		Assert.assertEquals(newSAPEntry2,
			sapEntries.get(newSAPEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SAPEntry> sapEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(sapEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		SAPEntry newSAPEntry = addSAPEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSAPEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SAPEntry> sapEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, sapEntries.size());
		Assert.assertEquals(newSAPEntry,
			sapEntries.get(newSAPEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SAPEntry> sapEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(sapEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		SAPEntry newSAPEntry = addSAPEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSAPEntry.getPrimaryKey());

		Map<Serializable, SAPEntry> sapEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, sapEntries.size());
		Assert.assertEquals(newSAPEntry,
			sapEntries.get(newSAPEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = SAPEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					SAPEntry sapEntry = (SAPEntry)object;

					Assert.assertNotNull(sapEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SAPEntry newSAPEntry = addSAPEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SAPEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("sapEntryId",
				newSAPEntry.getSapEntryId()));

		List<SAPEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SAPEntry existingSAPEntry = result.get(0);

		Assert.assertEquals(existingSAPEntry, newSAPEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SAPEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("sapEntryId",
				RandomTestUtil.nextLong()));

		List<SAPEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SAPEntry newSAPEntry = addSAPEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SAPEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("sapEntryId"));

		Object newSapEntryId = newSAPEntry.getSapEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("sapEntryId",
				new Object[] { newSapEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSapEntryId = result.get(0);

		Assert.assertEquals(existingSapEntryId, newSapEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SAPEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("sapEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("sapEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		SAPEntry newSAPEntry = addSAPEntry();

		_persistence.clearCache();

		SAPEntry existingSAPEntry = _persistence.findByPrimaryKey(newSAPEntry.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(existingSAPEntry.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(existingSAPEntry,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(existingSAPEntry.getName(),
				ReflectionTestUtil.invoke(existingSAPEntry, "getOriginalName",
					new Class<?>[0])));
	}

	protected SAPEntry addSAPEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SAPEntry sapEntry = _persistence.create(pk);

		sapEntry.setUuid(RandomTestUtil.randomString());

		sapEntry.setCompanyId(RandomTestUtil.nextLong());

		sapEntry.setUserId(RandomTestUtil.nextLong());

		sapEntry.setUserName(RandomTestUtil.randomString());

		sapEntry.setCreateDate(RandomTestUtil.nextDate());

		sapEntry.setModifiedDate(RandomTestUtil.nextDate());

		sapEntry.setAllowedServiceSignatures(RandomTestUtil.randomString());

		sapEntry.setDefaultSAPEntry(RandomTestUtil.randomBoolean());

		sapEntry.setEnabled(RandomTestUtil.randomBoolean());

		sapEntry.setName(RandomTestUtil.randomString());

		sapEntry.setTitle(RandomTestUtil.randomString());

		_sapEntries.add(_persistence.update(sapEntry));

		return sapEntry;
	}

	private List<SAPEntry> _sapEntries = new ArrayList<SAPEntry>();
	private SAPEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}