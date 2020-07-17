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

package com.liferay.fragment.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.exception.NoSuchEntryException;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.fragment.service.persistence.FragmentEntryPersistence;
import com.liferay.fragment.service.persistence.FragmentEntryUtil;
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
public class FragmentEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.fragment.service"));

	@Before
	public void setUp() {
		_persistence = FragmentEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FragmentEntry> iterator = _fragmentEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentEntry fragmentEntry = _persistence.create(pk);

		Assert.assertNotNull(fragmentEntry);

		Assert.assertEquals(fragmentEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FragmentEntry newFragmentEntry = addFragmentEntry();

		_persistence.remove(newFragmentEntry);

		FragmentEntry existingFragmentEntry = _persistence.fetchByPrimaryKey(
			newFragmentEntry.getPrimaryKey());

		Assert.assertNull(existingFragmentEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFragmentEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentEntry newFragmentEntry = _persistence.create(pk);

		newFragmentEntry.setMvccVersion(RandomTestUtil.nextLong());

		newFragmentEntry.setCtCollectionId(RandomTestUtil.nextLong());

		newFragmentEntry.setUuid(RandomTestUtil.randomString());

		newFragmentEntry.setHeadId(RandomTestUtil.nextLong());

		newFragmentEntry.setGroupId(RandomTestUtil.nextLong());

		newFragmentEntry.setCompanyId(RandomTestUtil.nextLong());

		newFragmentEntry.setUserId(RandomTestUtil.nextLong());

		newFragmentEntry.setUserName(RandomTestUtil.randomString());

		newFragmentEntry.setCreateDate(RandomTestUtil.nextDate());

		newFragmentEntry.setModifiedDate(RandomTestUtil.nextDate());

		newFragmentEntry.setFragmentCollectionId(RandomTestUtil.nextLong());

		newFragmentEntry.setFragmentEntryKey(RandomTestUtil.randomString());

		newFragmentEntry.setName(RandomTestUtil.randomString());

		newFragmentEntry.setCss(RandomTestUtil.randomString());

		newFragmentEntry.setHtml(RandomTestUtil.randomString());

		newFragmentEntry.setJs(RandomTestUtil.randomString());

		newFragmentEntry.setCacheable(RandomTestUtil.randomBoolean());

		newFragmentEntry.setConfiguration(RandomTestUtil.randomString());

		newFragmentEntry.setPreviewFileEntryId(RandomTestUtil.nextLong());

		newFragmentEntry.setReadOnly(RandomTestUtil.randomBoolean());

		newFragmentEntry.setType(RandomTestUtil.nextInt());

		newFragmentEntry.setLastPublishDate(RandomTestUtil.nextDate());

		newFragmentEntry.setStatus(RandomTestUtil.nextInt());

		newFragmentEntry.setStatusByUserId(RandomTestUtil.nextLong());

		newFragmentEntry.setStatusByUserName(RandomTestUtil.randomString());

		newFragmentEntry.setStatusDate(RandomTestUtil.nextDate());

		_fragmentEntries.add(_persistence.update(newFragmentEntry));

		FragmentEntry existingFragmentEntry = _persistence.findByPrimaryKey(
			newFragmentEntry.getPrimaryKey());

		Assert.assertEquals(
			existingFragmentEntry.getMvccVersion(),
			newFragmentEntry.getMvccVersion());
		Assert.assertEquals(
			existingFragmentEntry.getCtCollectionId(),
			newFragmentEntry.getCtCollectionId());
		Assert.assertEquals(
			existingFragmentEntry.getUuid(), newFragmentEntry.getUuid());
		Assert.assertEquals(
			existingFragmentEntry.getHeadId(), newFragmentEntry.getHeadId());
		Assert.assertEquals(
			existingFragmentEntry.getFragmentEntryId(),
			newFragmentEntry.getFragmentEntryId());
		Assert.assertEquals(
			existingFragmentEntry.getGroupId(), newFragmentEntry.getGroupId());
		Assert.assertEquals(
			existingFragmentEntry.getCompanyId(),
			newFragmentEntry.getCompanyId());
		Assert.assertEquals(
			existingFragmentEntry.getUserId(), newFragmentEntry.getUserId());
		Assert.assertEquals(
			existingFragmentEntry.getUserName(),
			newFragmentEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingFragmentEntry.getCreateDate()),
			Time.getShortTimestamp(newFragmentEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingFragmentEntry.getModifiedDate()),
			Time.getShortTimestamp(newFragmentEntry.getModifiedDate()));
		Assert.assertEquals(
			existingFragmentEntry.getFragmentCollectionId(),
			newFragmentEntry.getFragmentCollectionId());
		Assert.assertEquals(
			existingFragmentEntry.getFragmentEntryKey(),
			newFragmentEntry.getFragmentEntryKey());
		Assert.assertEquals(
			existingFragmentEntry.getName(), newFragmentEntry.getName());
		Assert.assertEquals(
			existingFragmentEntry.getCss(), newFragmentEntry.getCss());
		Assert.assertEquals(
			existingFragmentEntry.getHtml(), newFragmentEntry.getHtml());
		Assert.assertEquals(
			existingFragmentEntry.getJs(), newFragmentEntry.getJs());
		Assert.assertEquals(
			existingFragmentEntry.isCacheable(),
			newFragmentEntry.isCacheable());
		Assert.assertEquals(
			existingFragmentEntry.getConfiguration(),
			newFragmentEntry.getConfiguration());
		Assert.assertEquals(
			existingFragmentEntry.getPreviewFileEntryId(),
			newFragmentEntry.getPreviewFileEntryId());
		Assert.assertEquals(
			existingFragmentEntry.isReadOnly(), newFragmentEntry.isReadOnly());
		Assert.assertEquals(
			existingFragmentEntry.getType(), newFragmentEntry.getType());
		Assert.assertEquals(
			Time.getShortTimestamp(existingFragmentEntry.getLastPublishDate()),
			Time.getShortTimestamp(newFragmentEntry.getLastPublishDate()));
		Assert.assertEquals(
			existingFragmentEntry.getStatus(), newFragmentEntry.getStatus());
		Assert.assertEquals(
			existingFragmentEntry.getStatusByUserId(),
			newFragmentEntry.getStatusByUserId());
		Assert.assertEquals(
			existingFragmentEntry.getStatusByUserName(),
			newFragmentEntry.getStatusByUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingFragmentEntry.getStatusDate()),
			Time.getShortTimestamp(newFragmentEntry.getStatusDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_Head() throws Exception {
		_persistence.countByUuid_Head("", RandomTestUtil.randomBoolean());

		_persistence.countByUuid_Head("null", RandomTestUtil.randomBoolean());

		_persistence.countByUuid_Head(
			(String)null, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUUID_G_Head() throws Exception {
		_persistence.countByUUID_G_Head(
			"", RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByUUID_G_Head(
			"null", 0L, RandomTestUtil.randomBoolean());

		_persistence.countByUUID_G_Head(
			(String)null, 0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C_Head() throws Exception {
		_persistence.countByUuid_C_Head(
			"", RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByUuid_C_Head(
			"null", 0L, RandomTestUtil.randomBoolean());

		_persistence.countByUuid_C_Head(
			(String)null, 0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByGroupId_Head() throws Exception {
		_persistence.countByGroupId_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByGroupId_Head(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByFragmentCollectionId() throws Exception {
		_persistence.countByFragmentCollectionId(RandomTestUtil.nextLong());

		_persistence.countByFragmentCollectionId(0L);
	}

	@Test
	public void testCountByFragmentCollectionId_Head() throws Exception {
		_persistence.countByFragmentCollectionId_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByFragmentCollectionId_Head(
			0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_FCI() throws Exception {
		_persistence.countByG_FCI(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByG_FCI(0L, 0L);
	}

	@Test
	public void testCountByG_FCI_Head() throws Exception {
		_persistence.countByG_FCI_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_FCI_Head(0L, 0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_FEK() throws Exception {
		_persistence.countByG_FEK(RandomTestUtil.nextLong(), "");

		_persistence.countByG_FEK(0L, "null");

		_persistence.countByG_FEK(0L, (String)null);
	}

	@Test
	public void testCountByG_FEK_Head() throws Exception {
		_persistence.countByG_FEK_Head(
			RandomTestUtil.nextLong(), "", RandomTestUtil.randomBoolean());

		_persistence.countByG_FEK_Head(
			0L, "null", RandomTestUtil.randomBoolean());

		_persistence.countByG_FEK_Head(
			0L, (String)null, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_FCI_LikeN() throws Exception {
		_persistence.countByG_FCI_LikeN(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "");

		_persistence.countByG_FCI_LikeN(0L, 0L, "null");

		_persistence.countByG_FCI_LikeN(0L, 0L, (String)null);
	}

	@Test
	public void testCountByG_FCI_LikeN_Head() throws Exception {
		_persistence.countByG_FCI_LikeN_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "",
			RandomTestUtil.randomBoolean());

		_persistence.countByG_FCI_LikeN_Head(
			0L, 0L, "null", RandomTestUtil.randomBoolean());

		_persistence.countByG_FCI_LikeN_Head(
			0L, 0L, (String)null, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_FCI_T() throws Exception {
		_persistence.countByG_FCI_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByG_FCI_T(0L, 0L, 0);
	}

	@Test
	public void testCountByG_FCI_T_Head() throws Exception {
		_persistence.countByG_FCI_T_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt(), RandomTestUtil.randomBoolean());

		_persistence.countByG_FCI_T_Head(
			0L, 0L, 0, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_FCI_S() throws Exception {
		_persistence.countByG_FCI_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByG_FCI_S(0L, 0L, 0);
	}

	@Test
	public void testCountByG_FCI_S_Head() throws Exception {
		_persistence.countByG_FCI_S_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt(), RandomTestUtil.randomBoolean());

		_persistence.countByG_FCI_S_Head(
			0L, 0L, 0, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_FCI_LikeN_S() throws Exception {
		_persistence.countByG_FCI_LikeN_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "",
			RandomTestUtil.nextInt());

		_persistence.countByG_FCI_LikeN_S(0L, 0L, "null", 0);

		_persistence.countByG_FCI_LikeN_S(0L, 0L, (String)null, 0);
	}

	@Test
	public void testCountByG_FCI_LikeN_S_Head() throws Exception {
		_persistence.countByG_FCI_LikeN_S_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "",
			RandomTestUtil.nextInt(), RandomTestUtil.randomBoolean());

		_persistence.countByG_FCI_LikeN_S_Head(
			0L, 0L, "null", 0, RandomTestUtil.randomBoolean());

		_persistence.countByG_FCI_LikeN_S_Head(
			0L, 0L, (String)null, 0, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_FCI_T_S() throws Exception {
		_persistence.countByG_FCI_T_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt(), RandomTestUtil.nextInt());

		_persistence.countByG_FCI_T_S(0L, 0L, 0, 0);
	}

	@Test
	public void testCountByG_FCI_T_S_Head() throws Exception {
		_persistence.countByG_FCI_T_S_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt(), RandomTestUtil.nextInt(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_FCI_T_S_Head(
			0L, 0L, 0, 0, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByHeadId() throws Exception {
		_persistence.countByHeadId(RandomTestUtil.nextLong());

		_persistence.countByHeadId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FragmentEntry newFragmentEntry = addFragmentEntry();

		FragmentEntry existingFragmentEntry = _persistence.findByPrimaryKey(
			newFragmentEntry.getPrimaryKey());

		Assert.assertEquals(existingFragmentEntry, newFragmentEntry);
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

	protected OrderByComparator<FragmentEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"FragmentEntry", "mvccVersion", true, "ctCollectionId", true,
			"uuid", true, "headId", true, "fragmentEntryId", true, "groupId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "fragmentCollectionId",
			true, "fragmentEntryKey", true, "name", true, "cacheable", true,
			"previewFileEntryId", true, "readOnly", true, "type", true,
			"lastPublishDate", true, "status", true, "statusByUserId", true,
			"statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FragmentEntry newFragmentEntry = addFragmentEntry();

		FragmentEntry existingFragmentEntry = _persistence.fetchByPrimaryKey(
			newFragmentEntry.getPrimaryKey());

		Assert.assertEquals(existingFragmentEntry, newFragmentEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentEntry missingFragmentEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFragmentEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		FragmentEntry newFragmentEntry1 = addFragmentEntry();
		FragmentEntry newFragmentEntry2 = addFragmentEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentEntry1.getPrimaryKey());
		primaryKeys.add(newFragmentEntry2.getPrimaryKey());

		Map<Serializable, FragmentEntry> fragmentEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, fragmentEntries.size());
		Assert.assertEquals(
			newFragmentEntry1,
			fragmentEntries.get(newFragmentEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newFragmentEntry2,
			fragmentEntries.get(newFragmentEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FragmentEntry> fragmentEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fragmentEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		FragmentEntry newFragmentEntry = addFragmentEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FragmentEntry> fragmentEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fragmentEntries.size());
		Assert.assertEquals(
			newFragmentEntry,
			fragmentEntries.get(newFragmentEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FragmentEntry> fragmentEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fragmentEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		FragmentEntry newFragmentEntry = addFragmentEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentEntry.getPrimaryKey());

		Map<Serializable, FragmentEntry> fragmentEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fragmentEntries.size());
		Assert.assertEquals(
			newFragmentEntry,
			fragmentEntries.get(newFragmentEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			FragmentEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<FragmentEntry>() {

				@Override
				public void performAction(FragmentEntry fragmentEntry) {
					Assert.assertNotNull(fragmentEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		FragmentEntry newFragmentEntry = addFragmentEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fragmentEntryId", newFragmentEntry.getFragmentEntryId()));

		List<FragmentEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		FragmentEntry existingFragmentEntry = result.get(0);

		Assert.assertEquals(existingFragmentEntry, newFragmentEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fragmentEntryId", RandomTestUtil.nextLong()));

		List<FragmentEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		FragmentEntry newFragmentEntry = addFragmentEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fragmentEntryId"));

		Object newFragmentEntryId = newFragmentEntry.getFragmentEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fragmentEntryId", new Object[] {newFragmentEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFragmentEntryId = result.get(0);

		Assert.assertEquals(existingFragmentEntryId, newFragmentEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fragmentEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fragmentEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		FragmentEntry newFragmentEntry = addFragmentEntry();

		_persistence.clearCache();

		FragmentEntry existingFragmentEntry = _persistence.findByPrimaryKey(
			newFragmentEntry.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingFragmentEntry.getUuid(),
				ReflectionTestUtil.invoke(
					existingFragmentEntry, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingFragmentEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingFragmentEntry, "getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingFragmentEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingFragmentEntry, "getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingFragmentEntry.getFragmentEntryKey(),
				ReflectionTestUtil.invoke(
					existingFragmentEntry, "getOriginalFragmentEntryKey",
					new Class<?>[0])));

		Assert.assertEquals(
			Long.valueOf(existingFragmentEntry.getHeadId()),
			ReflectionTestUtil.<Long>invoke(
				existingFragmentEntry, "getOriginalHeadId", new Class<?>[0]));
	}

	protected FragmentEntry addFragmentEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentEntry fragmentEntry = _persistence.create(pk);

		fragmentEntry.setMvccVersion(RandomTestUtil.nextLong());

		fragmentEntry.setCtCollectionId(RandomTestUtil.nextLong());

		fragmentEntry.setUuid(RandomTestUtil.randomString());

		fragmentEntry.setHeadId(RandomTestUtil.nextLong());

		fragmentEntry.setGroupId(RandomTestUtil.nextLong());

		fragmentEntry.setCompanyId(RandomTestUtil.nextLong());

		fragmentEntry.setUserId(RandomTestUtil.nextLong());

		fragmentEntry.setUserName(RandomTestUtil.randomString());

		fragmentEntry.setCreateDate(RandomTestUtil.nextDate());

		fragmentEntry.setModifiedDate(RandomTestUtil.nextDate());

		fragmentEntry.setFragmentCollectionId(RandomTestUtil.nextLong());

		fragmentEntry.setFragmentEntryKey(RandomTestUtil.randomString());

		fragmentEntry.setName(RandomTestUtil.randomString());

		fragmentEntry.setCss(RandomTestUtil.randomString());

		fragmentEntry.setHtml(RandomTestUtil.randomString());

		fragmentEntry.setJs(RandomTestUtil.randomString());

		fragmentEntry.setCacheable(RandomTestUtil.randomBoolean());

		fragmentEntry.setConfiguration(RandomTestUtil.randomString());

		fragmentEntry.setPreviewFileEntryId(RandomTestUtil.nextLong());

		fragmentEntry.setReadOnly(RandomTestUtil.randomBoolean());

		fragmentEntry.setType(RandomTestUtil.nextInt());

		fragmentEntry.setLastPublishDate(RandomTestUtil.nextDate());

		fragmentEntry.setStatus(RandomTestUtil.nextInt());

		fragmentEntry.setStatusByUserId(RandomTestUtil.nextLong());

		fragmentEntry.setStatusByUserName(RandomTestUtil.randomString());

		fragmentEntry.setStatusDate(RandomTestUtil.nextDate());

		_fragmentEntries.add(_persistence.update(fragmentEntry));

		return fragmentEntry;
	}

	private List<FragmentEntry> _fragmentEntries =
		new ArrayList<FragmentEntry>();
	private FragmentEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}