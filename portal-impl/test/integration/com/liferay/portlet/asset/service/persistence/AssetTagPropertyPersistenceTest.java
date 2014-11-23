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
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.PersistenceTestRule;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.asset.NoSuchTagPropertyException;
import com.liferay.portlet.asset.model.AssetTagProperty;
import com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl;
import com.liferay.portlet.asset.service.AssetTagPropertyLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
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
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class AssetTagPropertyPersistenceTest {
	@Rule
	public final PersistenceTestRule persistenceTestRule = PersistenceTestRule.INSTANCE;
	@Rule
	public final TransactionalTestRule transactionalTestRule = new TransactionalTestRule(Propagation.REQUIRED);

	@After
	public void tearDown() throws Exception {
		Iterator<AssetTagProperty> iterator = _assetTagProperties.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTagProperty assetTagProperty = _persistence.create(pk);

		Assert.assertNotNull(assetTagProperty);

		Assert.assertEquals(assetTagProperty.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		_persistence.remove(newAssetTagProperty);

		AssetTagProperty existingAssetTagProperty = _persistence.fetchByPrimaryKey(newAssetTagProperty.getPrimaryKey());

		Assert.assertNull(existingAssetTagProperty);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetTagProperty();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTagProperty newAssetTagProperty = _persistence.create(pk);

		newAssetTagProperty.setCompanyId(RandomTestUtil.nextLong());

		newAssetTagProperty.setUserId(RandomTestUtil.nextLong());

		newAssetTagProperty.setUserName(RandomTestUtil.randomString());

		newAssetTagProperty.setCreateDate(RandomTestUtil.nextDate());

		newAssetTagProperty.setModifiedDate(RandomTestUtil.nextDate());

		newAssetTagProperty.setTagId(RandomTestUtil.nextLong());

		newAssetTagProperty.setKey(RandomTestUtil.randomString());

		newAssetTagProperty.setValue(RandomTestUtil.randomString());

		_assetTagProperties.add(_persistence.update(newAssetTagProperty));

		AssetTagProperty existingAssetTagProperty = _persistence.findByPrimaryKey(newAssetTagProperty.getPrimaryKey());

		Assert.assertEquals(existingAssetTagProperty.getTagPropertyId(),
			newAssetTagProperty.getTagPropertyId());
		Assert.assertEquals(existingAssetTagProperty.getCompanyId(),
			newAssetTagProperty.getCompanyId());
		Assert.assertEquals(existingAssetTagProperty.getUserId(),
			newAssetTagProperty.getUserId());
		Assert.assertEquals(existingAssetTagProperty.getUserName(),
			newAssetTagProperty.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetTagProperty.getCreateDate()),
			Time.getShortTimestamp(newAssetTagProperty.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetTagProperty.getModifiedDate()),
			Time.getShortTimestamp(newAssetTagProperty.getModifiedDate()));
		Assert.assertEquals(existingAssetTagProperty.getTagId(),
			newAssetTagProperty.getTagId());
		Assert.assertEquals(existingAssetTagProperty.getKey(),
			newAssetTagProperty.getKey());
		Assert.assertEquals(existingAssetTagProperty.getValue(),
			newAssetTagProperty.getValue());
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(RandomTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByTagId() {
		try {
			_persistence.countByTagId(RandomTestUtil.nextLong());

			_persistence.countByTagId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_K() {
		try {
			_persistence.countByC_K(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByC_K(0L, StringPool.NULL);

			_persistence.countByC_K(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByT_K() {
		try {
			_persistence.countByT_K(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByT_K(0L, StringPool.NULL);

			_persistence.countByT_K(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		AssetTagProperty existingAssetTagProperty = _persistence.findByPrimaryKey(newAssetTagProperty.getPrimaryKey());

		Assert.assertEquals(existingAssetTagProperty, newAssetTagProperty);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchTagPropertyException");
		}
		catch (NoSuchTagPropertyException nsee) {
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

	protected OrderByComparator<AssetTagProperty> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetTagProperty",
			"tagPropertyId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"tagId", true, "key", true, "value", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		AssetTagProperty existingAssetTagProperty = _persistence.fetchByPrimaryKey(newAssetTagProperty.getPrimaryKey());

		Assert.assertEquals(existingAssetTagProperty, newAssetTagProperty);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTagProperty missingAssetTagProperty = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetTagProperty);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AssetTagProperty newAssetTagProperty1 = addAssetTagProperty();
		AssetTagProperty newAssetTagProperty2 = addAssetTagProperty();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTagProperty1.getPrimaryKey());
		primaryKeys.add(newAssetTagProperty2.getPrimaryKey());

		Map<Serializable, AssetTagProperty> assetTagProperties = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetTagProperties.size());
		Assert.assertEquals(newAssetTagProperty1,
			assetTagProperties.get(newAssetTagProperty1.getPrimaryKey()));
		Assert.assertEquals(newAssetTagProperty2,
			assetTagProperties.get(newAssetTagProperty2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetTagProperty> assetTagProperties = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetTagProperties.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTagProperty.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetTagProperty> assetTagProperties = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetTagProperties.size());
		Assert.assertEquals(newAssetTagProperty,
			assetTagProperties.get(newAssetTagProperty.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetTagProperty> assetTagProperties = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetTagProperties.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetTagProperty.getPrimaryKey());

		Map<Serializable, AssetTagProperty> assetTagProperties = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetTagProperties.size());
		Assert.assertEquals(newAssetTagProperty,
			assetTagProperties.get(newAssetTagProperty.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AssetTagPropertyLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					AssetTagProperty assetTagProperty = (AssetTagProperty)object;

					Assert.assertNotNull(assetTagProperty);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagProperty.class,
				AssetTagProperty.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagPropertyId",
				newAssetTagProperty.getTagPropertyId()));

		List<AssetTagProperty> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetTagProperty existingAssetTagProperty = result.get(0);

		Assert.assertEquals(existingAssetTagProperty, newAssetTagProperty);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagProperty.class,
				AssetTagProperty.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagPropertyId",
				RandomTestUtil.nextLong()));

		List<AssetTagProperty> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagProperty.class,
				AssetTagProperty.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"tagPropertyId"));

		Object newTagPropertyId = newAssetTagProperty.getTagPropertyId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("tagPropertyId",
				new Object[] { newTagPropertyId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingTagPropertyId = result.get(0);

		Assert.assertEquals(existingTagPropertyId, newTagPropertyId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagProperty.class,
				AssetTagProperty.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"tagPropertyId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("tagPropertyId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		_persistence.clearCache();

		AssetTagPropertyModelImpl existingAssetTagPropertyModelImpl = (AssetTagPropertyModelImpl)_persistence.findByPrimaryKey(newAssetTagProperty.getPrimaryKey());

		Assert.assertEquals(existingAssetTagPropertyModelImpl.getTagId(),
			existingAssetTagPropertyModelImpl.getOriginalTagId());
		Assert.assertTrue(Validator.equals(
				existingAssetTagPropertyModelImpl.getKey(),
				existingAssetTagPropertyModelImpl.getOriginalKey()));
	}

	protected AssetTagProperty addAssetTagProperty() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetTagProperty assetTagProperty = _persistence.create(pk);

		assetTagProperty.setCompanyId(RandomTestUtil.nextLong());

		assetTagProperty.setUserId(RandomTestUtil.nextLong());

		assetTagProperty.setUserName(RandomTestUtil.randomString());

		assetTagProperty.setCreateDate(RandomTestUtil.nextDate());

		assetTagProperty.setModifiedDate(RandomTestUtil.nextDate());

		assetTagProperty.setTagId(RandomTestUtil.nextLong());

		assetTagProperty.setKey(RandomTestUtil.randomString());

		assetTagProperty.setValue(RandomTestUtil.randomString());

		_assetTagProperties.add(_persistence.update(assetTagProperty));

		return assetTagProperty;
	}

	private List<AssetTagProperty> _assetTagProperties = new ArrayList<AssetTagProperty>();
	private AssetTagPropertyPersistence _persistence = AssetTagPropertyUtil.getPersistence();
}