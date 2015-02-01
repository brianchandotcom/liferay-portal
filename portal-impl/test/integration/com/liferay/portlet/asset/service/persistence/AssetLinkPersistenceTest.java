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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.asset.NoSuchLinkException;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.impl.AssetLinkModelImpl;
import com.liferay.portlet.asset.service.AssetLinkLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

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
public class AssetLinkPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@After
	public void tearDown() throws Exception {
		Iterator<AssetLink> iterator = _assetLinks.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetLink assetLink = _persistence.create(pk);

		Assert.assertNotNull(assetLink);

		Assert.assertEquals(assetLink.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetLink newAssetLink = addAssetLink();

		_persistence.remove(newAssetLink);

		AssetLink existingAssetLink = _persistence.fetchByPrimaryKey(newAssetLink.getPrimaryKey());

		Assert.assertNull(existingAssetLink);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetLink();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetLink newAssetLink = _persistence.create(pk);

		newAssetLink.setCompanyId(RandomTestUtil.nextLong());

		newAssetLink.setUserId(RandomTestUtil.nextLong());

		newAssetLink.setUserName(RandomTestUtil.randomString());

		newAssetLink.setCreateDate(RandomTestUtil.nextDate());

		newAssetLink.setEntryId1(RandomTestUtil.nextLong());

		newAssetLink.setEntryId2(RandomTestUtil.nextLong());

		newAssetLink.setType(RandomTestUtil.nextInt());

		newAssetLink.setWeight(RandomTestUtil.nextInt());

		_assetLinks.add(_persistence.update(newAssetLink));

		AssetLink existingAssetLink = _persistence.findByPrimaryKey(newAssetLink.getPrimaryKey());

		Assert.assertEquals(existingAssetLink.getLinkId(),
			newAssetLink.getLinkId());
		Assert.assertEquals(existingAssetLink.getCompanyId(),
			newAssetLink.getCompanyId());
		Assert.assertEquals(existingAssetLink.getUserId(),
			newAssetLink.getUserId());
		Assert.assertEquals(existingAssetLink.getUserName(),
			newAssetLink.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetLink.getCreateDate()),
			Time.getShortTimestamp(newAssetLink.getCreateDate()));
		Assert.assertEquals(existingAssetLink.getEntryId1(),
			newAssetLink.getEntryId1());
		Assert.assertEquals(existingAssetLink.getEntryId2(),
			newAssetLink.getEntryId2());
		Assert.assertEquals(existingAssetLink.getType(), newAssetLink.getType());
		Assert.assertEquals(existingAssetLink.getWeight(),
			newAssetLink.getWeight());
	}

	@Test
	public void testCountByE1() {
		try {
			_persistence.countByE1(RandomTestUtil.nextLong());

			_persistence.countByE1(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByE2() {
		try {
			_persistence.countByE2(RandomTestUtil.nextLong());

			_persistence.countByE2(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByE_E() {
		try {
			_persistence.countByE_E(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByE_E(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByE1_T() {
		try {
			_persistence.countByE1_T(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByE1_T(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByE2_T() {
		try {
			_persistence.countByE2_T(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByE2_T(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByE_E_T() {
		try {
			_persistence.countByE_E_T(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

			_persistence.countByE_E_T(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetLink newAssetLink = addAssetLink();

		AssetLink existingAssetLink = _persistence.findByPrimaryKey(newAssetLink.getPrimaryKey());

		Assert.assertEquals(existingAssetLink, newAssetLink);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchLinkException");
		}
		catch (NoSuchLinkException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator<AssetLink> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetLink", "linkId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "entryId1", true, "entryId2", true, "type", true, "weight",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetLink newAssetLink = addAssetLink();

		AssetLink existingAssetLink = _persistence.fetchByPrimaryKey(newAssetLink.getPrimaryKey());

		Assert.assertEquals(existingAssetLink, newAssetLink);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetLink missingAssetLink = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetLink);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AssetLink newAssetLink1 = addAssetLink();
		AssetLink newAssetLink2 = addAssetLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetLink1.getPrimaryKey());
		primaryKeys.add(newAssetLink2.getPrimaryKey());

		Map<Serializable, AssetLink> assetLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetLinks.size());
		Assert.assertEquals(newAssetLink1,
			assetLinks.get(newAssetLink1.getPrimaryKey()));
		Assert.assertEquals(newAssetLink2,
			assetLinks.get(newAssetLink2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetLink> assetLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AssetLink newAssetLink = addAssetLink();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetLink.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetLink> assetLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetLinks.size());
		Assert.assertEquals(newAssetLink,
			assetLinks.get(newAssetLink.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetLink> assetLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AssetLink newAssetLink = addAssetLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetLink.getPrimaryKey());

		Map<Serializable, AssetLink> assetLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetLinks.size());
		Assert.assertEquals(newAssetLink,
			assetLinks.get(newAssetLink.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AssetLinkLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					AssetLink assetLink = (AssetLink)object;

					Assert.assertNotNull(assetLink);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetLink newAssetLink = addAssetLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetLink.class,
				AssetLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("linkId",
				newAssetLink.getLinkId()));

		List<AssetLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetLink existingAssetLink = result.get(0);

		Assert.assertEquals(existingAssetLink, newAssetLink);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetLink.class,
				AssetLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("linkId",
				RandomTestUtil.nextLong()));

		List<AssetLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetLink newAssetLink = addAssetLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetLink.class,
				AssetLink.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("linkId"));

		Object newLinkId = newAssetLink.getLinkId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("linkId",
				new Object[] { newLinkId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLinkId = result.get(0);

		Assert.assertEquals(existingLinkId, newLinkId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetLink.class,
				AssetLink.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("linkId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("linkId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		AssetLink newAssetLink = addAssetLink();

		_persistence.clearCache();

		AssetLinkModelImpl existingAssetLinkModelImpl = (AssetLinkModelImpl)_persistence.findByPrimaryKey(newAssetLink.getPrimaryKey());

		Assert.assertEquals(existingAssetLinkModelImpl.getEntryId1(),
			existingAssetLinkModelImpl.getOriginalEntryId1());
		Assert.assertEquals(existingAssetLinkModelImpl.getEntryId2(),
			existingAssetLinkModelImpl.getOriginalEntryId2());
		Assert.assertEquals(existingAssetLinkModelImpl.getType(),
			existingAssetLinkModelImpl.getOriginalType());
	}

	protected AssetLink addAssetLink() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetLink assetLink = _persistence.create(pk);

		assetLink.setCompanyId(RandomTestUtil.nextLong());

		assetLink.setUserId(RandomTestUtil.nextLong());

		assetLink.setUserName(RandomTestUtil.randomString());

		assetLink.setCreateDate(RandomTestUtil.nextDate());

		assetLink.setEntryId1(RandomTestUtil.nextLong());

		assetLink.setEntryId2(RandomTestUtil.nextLong());

		assetLink.setType(RandomTestUtil.nextInt());

		assetLink.setWeight(RandomTestUtil.nextInt());

		_assetLinks.add(_persistence.update(assetLink));

		return assetLink;
	}

	private List<AssetLink> _assetLinks = new ArrayList<AssetLink>();
	private AssetLinkPersistence _persistence = AssetLinkUtil.getPersistence();
}