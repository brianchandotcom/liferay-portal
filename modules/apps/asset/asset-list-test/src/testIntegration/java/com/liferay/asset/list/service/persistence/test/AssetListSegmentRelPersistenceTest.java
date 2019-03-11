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

package com.liferay.asset.list.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.exception.NoSuchSegmentRelException;
import com.liferay.asset.list.model.AssetListSegmentRel;
import com.liferay.asset.list.service.AssetListSegmentRelLocalServiceUtil;
import com.liferay.asset.list.service.persistence.AssetListSegmentRelPersistence;
import com.liferay.asset.list.service.persistence.AssetListSegmentRelUtil;
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
public class AssetListSegmentRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.asset.list.service"));

	@Before
	public void setUp() {
		_persistence = AssetListSegmentRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetListSegmentRel> iterator =
			_assetListSegmentRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetListSegmentRel assetListSegmentRel = _persistence.create(pk);

		Assert.assertNotNull(assetListSegmentRel);

		Assert.assertEquals(assetListSegmentRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetListSegmentRel newAssetListSegmentRel = addAssetListSegmentRel();

		_persistence.remove(newAssetListSegmentRel);

		AssetListSegmentRel existingAssetListSegmentRel =
			_persistence.fetchByPrimaryKey(
				newAssetListSegmentRel.getPrimaryKey());

		Assert.assertNull(existingAssetListSegmentRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetListSegmentRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetListSegmentRel newAssetListSegmentRel = _persistence.create(pk);

		newAssetListSegmentRel.setUuid(RandomTestUtil.randomString());

		newAssetListSegmentRel.setGroupId(RandomTestUtil.nextLong());

		newAssetListSegmentRel.setCompanyId(RandomTestUtil.nextLong());

		newAssetListSegmentRel.setUserId(RandomTestUtil.nextLong());

		newAssetListSegmentRel.setUserName(RandomTestUtil.randomString());

		newAssetListSegmentRel.setCreateDate(RandomTestUtil.nextDate());

		newAssetListSegmentRel.setModifiedDate(RandomTestUtil.nextDate());

		newAssetListSegmentRel.setAssetListEntryId(RandomTestUtil.nextLong());

		newAssetListSegmentRel.setSegmentsEntryId(RandomTestUtil.nextLong());

		newAssetListSegmentRel.setTypeSettings(RandomTestUtil.randomString());

		newAssetListSegmentRel.setLastPublishDate(RandomTestUtil.nextDate());

		_assetListSegmentRels.add(_persistence.update(newAssetListSegmentRel));

		AssetListSegmentRel existingAssetListSegmentRel =
			_persistence.findByPrimaryKey(
				newAssetListSegmentRel.getPrimaryKey());

		Assert.assertEquals(
			existingAssetListSegmentRel.getUuid(),
			newAssetListSegmentRel.getUuid());
		Assert.assertEquals(
			existingAssetListSegmentRel.getAssetListSegmentRelId(),
			newAssetListSegmentRel.getAssetListSegmentRelId());
		Assert.assertEquals(
			existingAssetListSegmentRel.getGroupId(),
			newAssetListSegmentRel.getGroupId());
		Assert.assertEquals(
			existingAssetListSegmentRel.getCompanyId(),
			newAssetListSegmentRel.getCompanyId());
		Assert.assertEquals(
			existingAssetListSegmentRel.getUserId(),
			newAssetListSegmentRel.getUserId());
		Assert.assertEquals(
			existingAssetListSegmentRel.getUserName(),
			newAssetListSegmentRel.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingAssetListSegmentRel.getCreateDate()),
			Time.getShortTimestamp(newAssetListSegmentRel.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingAssetListSegmentRel.getModifiedDate()),
			Time.getShortTimestamp(newAssetListSegmentRel.getModifiedDate()));
		Assert.assertEquals(
			existingAssetListSegmentRel.getAssetListEntryId(),
			newAssetListSegmentRel.getAssetListEntryId());
		Assert.assertEquals(
			existingAssetListSegmentRel.getSegmentsEntryId(),
			newAssetListSegmentRel.getSegmentsEntryId());
		Assert.assertEquals(
			existingAssetListSegmentRel.getTypeSettings(),
			newAssetListSegmentRel.getTypeSettings());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingAssetListSegmentRel.getLastPublishDate()),
			Time.getShortTimestamp(
				newAssetListSegmentRel.getLastPublishDate()));
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
	public void testCountByAssetListEntryId() throws Exception {
		_persistence.countByAssetListEntryId(RandomTestUtil.nextLong());

		_persistence.countByAssetListEntryId(0L);
	}

	@Test
	public void testCountBySegmentsEntryId() throws Exception {
		_persistence.countBySegmentsEntryId(RandomTestUtil.nextLong());

		_persistence.countBySegmentsEntryId(0L);
	}

	@Test
	public void testCountByA_S() throws Exception {
		_persistence.countByA_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByA_S(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetListSegmentRel newAssetListSegmentRel = addAssetListSegmentRel();

		AssetListSegmentRel existingAssetListSegmentRel =
			_persistence.findByPrimaryKey(
				newAssetListSegmentRel.getPrimaryKey());

		Assert.assertEquals(
			existingAssetListSegmentRel, newAssetListSegmentRel);
	}

	@Test(expected = NoSuchSegmentRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AssetListSegmentRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"AssetListSegmentRel", "uuid", true, "assetListSegmentRelId", true,
			"groupId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "assetListEntryId",
			true, "segmentsEntryId", true, "typeSettings", true,
			"lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetListSegmentRel newAssetListSegmentRel = addAssetListSegmentRel();

		AssetListSegmentRel existingAssetListSegmentRel =
			_persistence.fetchByPrimaryKey(
				newAssetListSegmentRel.getPrimaryKey());

		Assert.assertEquals(
			existingAssetListSegmentRel, newAssetListSegmentRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetListSegmentRel missingAssetListSegmentRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetListSegmentRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AssetListSegmentRel newAssetListSegmentRel1 = addAssetListSegmentRel();
		AssetListSegmentRel newAssetListSegmentRel2 = addAssetListSegmentRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetListSegmentRel1.getPrimaryKey());
		primaryKeys.add(newAssetListSegmentRel2.getPrimaryKey());

		Map<Serializable, AssetListSegmentRel> assetListSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetListSegmentRels.size());
		Assert.assertEquals(
			newAssetListSegmentRel1,
			assetListSegmentRels.get(newAssetListSegmentRel1.getPrimaryKey()));
		Assert.assertEquals(
			newAssetListSegmentRel2,
			assetListSegmentRels.get(newAssetListSegmentRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetListSegmentRel> assetListSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetListSegmentRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AssetListSegmentRel newAssetListSegmentRel = addAssetListSegmentRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetListSegmentRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetListSegmentRel> assetListSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetListSegmentRels.size());
		Assert.assertEquals(
			newAssetListSegmentRel,
			assetListSegmentRels.get(newAssetListSegmentRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetListSegmentRel> assetListSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetListSegmentRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AssetListSegmentRel newAssetListSegmentRel = addAssetListSegmentRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetListSegmentRel.getPrimaryKey());

		Map<Serializable, AssetListSegmentRel> assetListSegmentRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetListSegmentRels.size());
		Assert.assertEquals(
			newAssetListSegmentRel,
			assetListSegmentRels.get(newAssetListSegmentRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AssetListSegmentRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<AssetListSegmentRel>() {

				@Override
				public void performAction(
					AssetListSegmentRel assetListSegmentRel) {

					Assert.assertNotNull(assetListSegmentRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AssetListSegmentRel newAssetListSegmentRel = addAssetListSegmentRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetListSegmentRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assetListSegmentRelId",
				newAssetListSegmentRel.getAssetListSegmentRelId()));

		List<AssetListSegmentRel> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetListSegmentRel existingAssetListSegmentRel = result.get(0);

		Assert.assertEquals(
			existingAssetListSegmentRel, newAssetListSegmentRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetListSegmentRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assetListSegmentRelId", RandomTestUtil.nextLong()));

		List<AssetListSegmentRel> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AssetListSegmentRel newAssetListSegmentRel = addAssetListSegmentRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetListSegmentRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assetListSegmentRelId"));

		Object newAssetListSegmentRelId =
			newAssetListSegmentRel.getAssetListSegmentRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"assetListSegmentRelId",
				new Object[] {newAssetListSegmentRelId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAssetListSegmentRelId = result.get(0);

		Assert.assertEquals(
			existingAssetListSegmentRelId, newAssetListSegmentRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetListSegmentRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assetListSegmentRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"assetListSegmentRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AssetListSegmentRel newAssetListSegmentRel = addAssetListSegmentRel();

		_persistence.clearCache();

		AssetListSegmentRel existingAssetListSegmentRel =
			_persistence.findByPrimaryKey(
				newAssetListSegmentRel.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingAssetListSegmentRel.getUuid(),
				ReflectionTestUtil.invoke(
					existingAssetListSegmentRel, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingAssetListSegmentRel.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetListSegmentRel, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingAssetListSegmentRel.getAssetListEntryId()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetListSegmentRel, "getOriginalAssetListEntryId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingAssetListSegmentRel.getSegmentsEntryId()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetListSegmentRel, "getOriginalSegmentsEntryId",
				new Class<?>[0]));
	}

	protected AssetListSegmentRel addAssetListSegmentRel() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetListSegmentRel assetListSegmentRel = _persistence.create(pk);

		assetListSegmentRel.setUuid(RandomTestUtil.randomString());

		assetListSegmentRel.setGroupId(RandomTestUtil.nextLong());

		assetListSegmentRel.setCompanyId(RandomTestUtil.nextLong());

		assetListSegmentRel.setUserId(RandomTestUtil.nextLong());

		assetListSegmentRel.setUserName(RandomTestUtil.randomString());

		assetListSegmentRel.setCreateDate(RandomTestUtil.nextDate());

		assetListSegmentRel.setModifiedDate(RandomTestUtil.nextDate());

		assetListSegmentRel.setAssetListEntryId(RandomTestUtil.nextLong());

		assetListSegmentRel.setSegmentsEntryId(RandomTestUtil.nextLong());

		assetListSegmentRel.setTypeSettings(RandomTestUtil.randomString());

		assetListSegmentRel.setLastPublishDate(RandomTestUtil.nextDate());

		_assetListSegmentRels.add(_persistence.update(assetListSegmentRel));

		return assetListSegmentRel;
	}

	private List<AssetListSegmentRel> _assetListSegmentRels =
		new ArrayList<AssetListSegmentRel>();
	private AssetListSegmentRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}