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

package com.liferay.portlet.asset.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.exception.NoSuchTagException;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetTagPersistence;
import com.liferay.asset.kernel.service.persistence.AssetTagUtil;
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
public class AssetTagPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = AssetTagUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetTag> iterator = _assetTags.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTag assetTag = _persistence.create(pk);

		Assert.assertNotNull(assetTag);

		Assert.assertEquals(assetTag.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetTag newAssetTag = addAssetTag();

		_persistence.remove(newAssetTag);

		AssetTag existingAssetTag = _persistence.fetchByPrimaryKey(
			newAssetTag.getPrimaryKey());

		Assert.assertNull(existingAssetTag);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetTag();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTag newAssetTag = _persistence.create(pk);

		newAssetTag.setUuid(RandomTestUtil.randomString());

		newAssetTag.setGroupId(RandomTestUtil.nextLong());

		newAssetTag.setCompanyId(RandomTestUtil.nextLong());

		newAssetTag.setUserId(RandomTestUtil.nextLong());

		newAssetTag.setUserName(RandomTestUtil.randomString());

		newAssetTag.setCreateDate(RandomTestUtil.nextDate());

		newAssetTag.setModifiedDate(RandomTestUtil.nextDate());

		newAssetTag.setName(RandomTestUtil.randomString());

		newAssetTag.setAssetCount(RandomTestUtil.nextInt());

		newAssetTag.setLastPublishDate(RandomTestUtil.nextDate());

		_assetTags.add(_persistence.update(newAssetTag));

		AssetTag existingAssetTag = _persistence.findByPrimaryKey(
			newAssetTag.getPrimaryKey());

		Assert.assertEquals(existingAssetTag.getUuid(), newAssetTag.getUuid());
		Assert.assertEquals(
			existingAssetTag.getTagId(), newAssetTag.getTagId());
		Assert.assertEquals(
			existingAssetTag.getGroupId(), newAssetTag.getGroupId());
		Assert.assertEquals(
			existingAssetTag.getCompanyId(), newAssetTag.getCompanyId());
		Assert.assertEquals(
			existingAssetTag.getUserId(), newAssetTag.getUserId());
		Assert.assertEquals(
			existingAssetTag.getUserName(), newAssetTag.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingAssetTag.getCreateDate()),
			Time.getShortTimestamp(newAssetTag.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingAssetTag.getModifiedDate()),
			Time.getShortTimestamp(newAssetTag.getModifiedDate()));
		Assert.assertEquals(existingAssetTag.getName(), newAssetTag.getName());
		Assert.assertEquals(
			existingAssetTag.getAssetCount(), newAssetTag.getAssetCount());
		Assert.assertEquals(
			Time.getShortTimestamp(existingAssetTag.getLastPublishDate()),
			Time.getShortTimestamp(newAssetTag.getLastPublishDate()));
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
	public void testCountByName() throws Exception {
		_persistence.countByName("");

		_persistence.countByName("null");

		_persistence.countByName((String)null);
	}

	@Test
	public void testCountByNameArrayable() throws Exception {
		_persistence.countByName(
			new String[] {
				RandomTestUtil.randomString(), "", "null", null, null
			});
	}

	@Test
	public void testCountByG_N() throws Exception {
		_persistence.countByG_N(RandomTestUtil.nextLong(), "");

		_persistence.countByG_N(0L, "null");

		_persistence.countByG_N(0L, (String)null);
	}

	@Test
	public void testCountByG_LikeN() throws Exception {
		_persistence.countByG_LikeN(RandomTestUtil.nextLong(), "");

		_persistence.countByG_LikeN(0L, "null");

		_persistence.countByG_LikeN(0L, (String)null);
	}

	@Test
	public void testCountByG_LikeNArrayable() throws Exception {
		_persistence.countByG_LikeN(
			new long[] {RandomTestUtil.nextLong(), 0L},
			RandomTestUtil.randomString());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetTag newAssetTag = addAssetTag();

		AssetTag existingAssetTag = _persistence.findByPrimaryKey(
			newAssetTag.getPrimaryKey());

		Assert.assertEquals(existingAssetTag, newAssetTag);
	}

	@Test(expected = NoSuchTagException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AssetTag> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"AssetTag", "uuid", true, "tagId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "name", true, "assetCount", true,
			"lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetTag newAssetTag = addAssetTag();

		AssetTag existingAssetTag = _persistence.fetchByPrimaryKey(
			newAssetTag.getPrimaryKey());

		Assert.assertEquals(existingAssetTag, newAssetTag);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTag missingAssetTag = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetTag);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AssetTag newAssetTag1 = addAssetTag();
		AssetTag newAssetTag2 = addAssetTag();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTag1.getPrimaryKey());
		primaryKeys.add(newAssetTag2.getPrimaryKey());

		Map<Serializable, AssetTag> assetTags = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, assetTags.size());
		Assert.assertEquals(
			newAssetTag1, assetTags.get(newAssetTag1.getPrimaryKey()));
		Assert.assertEquals(
			newAssetTag2, assetTags.get(newAssetTag2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetTag> assetTags = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(assetTags.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AssetTag newAssetTag = addAssetTag();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTag.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetTag> assetTags = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, assetTags.size());
		Assert.assertEquals(
			newAssetTag, assetTags.get(newAssetTag.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetTag> assetTags = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(assetTags.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AssetTag newAssetTag = addAssetTag();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTag.getPrimaryKey());

		Map<Serializable, AssetTag> assetTags = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, assetTags.size());
		Assert.assertEquals(
			newAssetTag, assetTags.get(newAssetTag.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AssetTagLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<AssetTag>() {

				@Override
				public void performAction(AssetTag assetTag) {
					Assert.assertNotNull(assetTag);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AssetTag newAssetTag = addAssetTag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetTag.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("tagId", newAssetTag.getTagId()));

		List<AssetTag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetTag existingAssetTag = result.get(0);

		Assert.assertEquals(existingAssetTag, newAssetTag);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetTag.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("tagId", RandomTestUtil.nextLong()));

		List<AssetTag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AssetTag newAssetTag = addAssetTag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetTag.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("tagId"));

		Object newTagId = newAssetTag.getTagId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in("tagId", new Object[] {newTagId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingTagId = result.get(0);

		Assert.assertEquals(existingTagId, newTagId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetTag.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("tagId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"tagId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AssetTag newAssetTag = addAssetTag();

		_persistence.clearCache();

		AssetTag existingAssetTag = _persistence.findByPrimaryKey(
			newAssetTag.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingAssetTag.getUuid(),
				ReflectionTestUtil.invoke(
					existingAssetTag, "getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingAssetTag.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetTag, "getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingAssetTag.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetTag, "getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingAssetTag.getName(),
				ReflectionTestUtil.invoke(
					existingAssetTag, "getOriginalName", new Class<?>[0])));
	}

	protected AssetTag addAssetTag() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTag assetTag = _persistence.create(pk);

		assetTag.setUuid(RandomTestUtil.randomString());

		assetTag.setGroupId(RandomTestUtil.nextLong());

		assetTag.setCompanyId(RandomTestUtil.nextLong());

		assetTag.setUserId(RandomTestUtil.nextLong());

		assetTag.setUserName(RandomTestUtil.randomString());

		assetTag.setCreateDate(RandomTestUtil.nextDate());

		assetTag.setModifiedDate(RandomTestUtil.nextDate());

		assetTag.setName(RandomTestUtil.randomString());

		assetTag.setAssetCount(RandomTestUtil.nextInt());

		assetTag.setLastPublishDate(RandomTestUtil.nextDate());

		_assetTags.add(_persistence.update(assetTag));

		return assetTag;
	}

	private List<AssetTag> _assetTags = new ArrayList<AssetTag>();
	private AssetTagPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}