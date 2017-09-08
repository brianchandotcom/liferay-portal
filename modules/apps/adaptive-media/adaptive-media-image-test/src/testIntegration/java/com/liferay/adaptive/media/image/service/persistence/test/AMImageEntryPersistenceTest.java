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

package com.liferay.adaptive.media.image.service.persistence.test;

import com.liferay.adaptive.media.image.exception.NoSuchAMImageEntryException;
import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalServiceUtil;
import com.liferay.adaptive.media.image.service.persistence.AMImageEntryPersistence;
import com.liferay.adaptive.media.image.service.persistence.AMImageEntryUtil;

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
public class AMImageEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.adaptive.media.image.service"));

	@Before
	public void setUp() {
		_persistence = AMImageEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AMImageEntry> iterator = _amImageEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AMImageEntry amImageEntry = _persistence.create(pk);

		Assert.assertNotNull(amImageEntry);

		Assert.assertEquals(amImageEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AMImageEntry newAMImageEntry = addAMImageEntry();

		_persistence.remove(newAMImageEntry);

		AMImageEntry existingAMImageEntry = _persistence.fetchByPrimaryKey(newAMImageEntry.getPrimaryKey());

		Assert.assertNull(existingAMImageEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAMImageEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AMImageEntry newAMImageEntry = _persistence.create(pk);

		newAMImageEntry.setUuid(RandomTestUtil.randomString());

		newAMImageEntry.setGroupId(RandomTestUtil.nextLong());

		newAMImageEntry.setCompanyId(RandomTestUtil.nextLong());

		newAMImageEntry.setCreateDate(RandomTestUtil.nextDate());

		newAMImageEntry.setConfigurationUuid(RandomTestUtil.randomString());

		newAMImageEntry.setFileVersionId(RandomTestUtil.nextLong());

		newAMImageEntry.setMimeType(RandomTestUtil.randomString());

		newAMImageEntry.setHeight(RandomTestUtil.nextInt());

		newAMImageEntry.setWidth(RandomTestUtil.nextInt());

		newAMImageEntry.setSize(RandomTestUtil.nextLong());

		_amImageEntries.add(_persistence.update(newAMImageEntry));

		AMImageEntry existingAMImageEntry = _persistence.findByPrimaryKey(newAMImageEntry.getPrimaryKey());

		Assert.assertEquals(existingAMImageEntry.getUuid(),
			newAMImageEntry.getUuid());
		Assert.assertEquals(existingAMImageEntry.getAmImageEntryId(),
			newAMImageEntry.getAmImageEntryId());
		Assert.assertEquals(existingAMImageEntry.getGroupId(),
			newAMImageEntry.getGroupId());
		Assert.assertEquals(existingAMImageEntry.getCompanyId(),
			newAMImageEntry.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAMImageEntry.getCreateDate()),
			Time.getShortTimestamp(newAMImageEntry.getCreateDate()));
		Assert.assertEquals(existingAMImageEntry.getConfigurationUuid(),
			newAMImageEntry.getConfigurationUuid());
		Assert.assertEquals(existingAMImageEntry.getFileVersionId(),
			newAMImageEntry.getFileVersionId());
		Assert.assertEquals(existingAMImageEntry.getMimeType(),
			newAMImageEntry.getMimeType());
		Assert.assertEquals(existingAMImageEntry.getHeight(),
			newAMImageEntry.getHeight());
		Assert.assertEquals(existingAMImageEntry.getWidth(),
			newAMImageEntry.getWidth());
		Assert.assertEquals(existingAMImageEntry.getSize(),
			newAMImageEntry.getSize());
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
	public void testCountByConfigurationUuid() throws Exception {
		_persistence.countByConfigurationUuid(StringPool.BLANK);

		_persistence.countByConfigurationUuid(StringPool.NULL);

		_persistence.countByConfigurationUuid((String)null);
	}

	@Test
	public void testCountByFileVersionId() throws Exception {
		_persistence.countByFileVersionId(RandomTestUtil.nextLong());

		_persistence.countByFileVersionId(0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByC_C(0L, StringPool.NULL);

		_persistence.countByC_C(0L, (String)null);
	}

	@Test
	public void testCountByC_F() throws Exception {
		_persistence.countByC_F(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByC_F(StringPool.NULL, 0L);

		_persistence.countByC_F((String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AMImageEntry newAMImageEntry = addAMImageEntry();

		AMImageEntry existingAMImageEntry = _persistence.findByPrimaryKey(newAMImageEntry.getPrimaryKey());

		Assert.assertEquals(existingAMImageEntry, newAMImageEntry);
	}

	@Test(expected = NoSuchAMImageEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<AMImageEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AMImageEntry", "uuid",
			true, "amImageEntryId", true, "groupId", true, "companyId", true,
			"createDate", true, "configurationUuid", true, "fileVersionId",
			true, "mimeType", true, "height", true, "width", true, "size", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AMImageEntry newAMImageEntry = addAMImageEntry();

		AMImageEntry existingAMImageEntry = _persistence.fetchByPrimaryKey(newAMImageEntry.getPrimaryKey());

		Assert.assertEquals(existingAMImageEntry, newAMImageEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AMImageEntry missingAMImageEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAMImageEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AMImageEntry newAMImageEntry1 = addAMImageEntry();
		AMImageEntry newAMImageEntry2 = addAMImageEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAMImageEntry1.getPrimaryKey());
		primaryKeys.add(newAMImageEntry2.getPrimaryKey());

		Map<Serializable, AMImageEntry> amImageEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, amImageEntries.size());
		Assert.assertEquals(newAMImageEntry1,
			amImageEntries.get(newAMImageEntry1.getPrimaryKey()));
		Assert.assertEquals(newAMImageEntry2,
			amImageEntries.get(newAMImageEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AMImageEntry> amImageEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(amImageEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AMImageEntry newAMImageEntry = addAMImageEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAMImageEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AMImageEntry> amImageEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, amImageEntries.size());
		Assert.assertEquals(newAMImageEntry,
			amImageEntries.get(newAMImageEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AMImageEntry> amImageEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(amImageEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AMImageEntry newAMImageEntry = addAMImageEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAMImageEntry.getPrimaryKey());

		Map<Serializable, AMImageEntry> amImageEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, amImageEntries.size());
		Assert.assertEquals(newAMImageEntry,
			amImageEntries.get(newAMImageEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AMImageEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<AMImageEntry>() {
				@Override
				public void performAction(AMImageEntry amImageEntry) {
					Assert.assertNotNull(amImageEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AMImageEntry newAMImageEntry = addAMImageEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AMImageEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("amImageEntryId",
				newAMImageEntry.getAmImageEntryId()));

		List<AMImageEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AMImageEntry existingAMImageEntry = result.get(0);

		Assert.assertEquals(existingAMImageEntry, newAMImageEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AMImageEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("amImageEntryId",
				RandomTestUtil.nextLong()));

		List<AMImageEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AMImageEntry newAMImageEntry = addAMImageEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AMImageEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"amImageEntryId"));

		Object newAmImageEntryId = newAMImageEntry.getAmImageEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("amImageEntryId",
				new Object[] { newAmImageEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAmImageEntryId = result.get(0);

		Assert.assertEquals(existingAmImageEntryId, newAmImageEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AMImageEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"amImageEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("amImageEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AMImageEntry newAMImageEntry = addAMImageEntry();

		_persistence.clearCache();

		AMImageEntry existingAMImageEntry = _persistence.findByPrimaryKey(newAMImageEntry.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingAMImageEntry.getUuid(),
				ReflectionTestUtil.invoke(existingAMImageEntry,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingAMImageEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingAMImageEntry,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertTrue(Objects.equals(
				existingAMImageEntry.getConfigurationUuid(),
				ReflectionTestUtil.invoke(existingAMImageEntry,
					"getOriginalConfigurationUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingAMImageEntry.getFileVersionId()),
			ReflectionTestUtil.<Long>invoke(existingAMImageEntry,
				"getOriginalFileVersionId", new Class<?>[0]));
	}

	protected AMImageEntry addAMImageEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AMImageEntry amImageEntry = _persistence.create(pk);

		amImageEntry.setUuid(RandomTestUtil.randomString());

		amImageEntry.setGroupId(RandomTestUtil.nextLong());

		amImageEntry.setCompanyId(RandomTestUtil.nextLong());

		amImageEntry.setCreateDate(RandomTestUtil.nextDate());

		amImageEntry.setConfigurationUuid(RandomTestUtil.randomString());

		amImageEntry.setFileVersionId(RandomTestUtil.nextLong());

		amImageEntry.setMimeType(RandomTestUtil.randomString());

		amImageEntry.setHeight(RandomTestUtil.nextInt());

		amImageEntry.setWidth(RandomTestUtil.nextInt());

		amImageEntry.setSize(RandomTestUtil.nextLong());

		_amImageEntries.add(_persistence.update(amImageEntry));

		return amImageEntry;
	}

	private List<AMImageEntry> _amImageEntries = new ArrayList<AMImageEntry>();
	private AMImageEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}