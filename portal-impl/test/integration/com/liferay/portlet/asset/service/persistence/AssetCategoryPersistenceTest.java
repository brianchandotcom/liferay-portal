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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.asset.NoSuchCategoryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class AssetCategoryPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<AssetCategory> modelListener : _modelListeners) {
			_persistence.unregisterListener(modelListener);
		}
	}

	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();

		for (ModelListener<AssetCategory> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetCategory assetCategory = _persistence.create(pk);

		Assert.assertNotNull(assetCategory);

		Assert.assertEquals(assetCategory.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetCategory newAssetCategory = addAssetCategory();

		_persistence.remove(newAssetCategory);

		AssetCategory existingAssetCategory = _persistence.fetchByPrimaryKey(newAssetCategory.getPrimaryKey());

		Assert.assertNull(existingAssetCategory);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetCategory();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetCategory newAssetCategory = _persistence.create(pk);

		newAssetCategory.setUuid(ServiceTestUtil.randomString());

		newAssetCategory.setGroupId(ServiceTestUtil.nextLong());

		newAssetCategory.setCompanyId(ServiceTestUtil.nextLong());

		newAssetCategory.setUserId(ServiceTestUtil.nextLong());

		newAssetCategory.setUserName(ServiceTestUtil.randomString());

		newAssetCategory.setCreateDate(ServiceTestUtil.nextDate());

		newAssetCategory.setModifiedDate(ServiceTestUtil.nextDate());

		newAssetCategory.setLeftCategoryId(ServiceTestUtil.nextLong());

		newAssetCategory.setRightCategoryId(ServiceTestUtil.nextLong());

		newAssetCategory.setName(ServiceTestUtil.randomString());

		newAssetCategory.setTitle(ServiceTestUtil.randomString());

		newAssetCategory.setDescription(ServiceTestUtil.randomString());

		newAssetCategory.setVocabularyId(ServiceTestUtil.nextLong());

		_persistence.update(newAssetCategory);

		AssetCategory existingAssetCategory = _persistence.findByPrimaryKey(newAssetCategory.getPrimaryKey());

		Assert.assertEquals(existingAssetCategory.getUuid(),
			newAssetCategory.getUuid());
		Assert.assertEquals(existingAssetCategory.getCategoryId(),
			newAssetCategory.getCategoryId());
		Assert.assertEquals(existingAssetCategory.getGroupId(),
			newAssetCategory.getGroupId());
		Assert.assertEquals(existingAssetCategory.getCompanyId(),
			newAssetCategory.getCompanyId());
		Assert.assertEquals(existingAssetCategory.getUserId(),
			newAssetCategory.getUserId());
		Assert.assertEquals(existingAssetCategory.getUserName(),
			newAssetCategory.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetCategory.getCreateDate()),
			Time.getShortTimestamp(newAssetCategory.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetCategory.getModifiedDate()),
			Time.getShortTimestamp(newAssetCategory.getModifiedDate()));
		Assert.assertEquals(existingAssetCategory.getParentCategoryId(),
			newAssetCategory.getParentCategoryId());
		Assert.assertEquals(existingAssetCategory.getLeftCategoryId(),
			newAssetCategory.getLeftCategoryId());
		Assert.assertEquals(existingAssetCategory.getRightCategoryId(),
			newAssetCategory.getRightCategoryId());
		Assert.assertEquals(existingAssetCategory.getName(),
			newAssetCategory.getName());
		Assert.assertEquals(existingAssetCategory.getTitle(),
			newAssetCategory.getTitle());
		Assert.assertEquals(existingAssetCategory.getDescription(),
			newAssetCategory.getDescription());
		Assert.assertEquals(existingAssetCategory.getVocabularyId(),
			newAssetCategory.getVocabularyId());
	}

	@Test
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUUID_G() {
		try {
			_persistence.countByUUID_G(StringPool.BLANK,
				ServiceTestUtil.nextLong());

			_persistence.countByUUID_G(StringPool.NULL, 0L);

			_persistence.countByUUID_G((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				ServiceTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(ServiceTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByParentCategoryId() {
		try {
			_persistence.countByParentCategoryId(ServiceTestUtil.nextLong());

			_persistence.countByParentCategoryId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByVocabularyId() {
		try {
			_persistence.countByVocabularyId(ServiceTestUtil.nextLong());

			_persistence.countByVocabularyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_V() {
		try {
			_persistence.countByG_V(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong());

			_persistence.countByG_V(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_VArrayable() {
		try {
			_persistence.countByG_V(ServiceTestUtil.nextLong(),
				new long[] { ServiceTestUtil.nextLong(), 0L });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByP_N() {
		try {
			_persistence.countByP_N(ServiceTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByP_N(0L, StringPool.NULL);

			_persistence.countByP_N(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByP_V() {
		try {
			_persistence.countByP_V(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong());

			_persistence.countByP_V(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_V() {
		try {
			_persistence.countByN_V(StringPool.BLANK, ServiceTestUtil.nextLong());

			_persistence.countByN_V(StringPool.NULL, 0L);

			_persistence.countByN_V((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P_V() {
		try {
			_persistence.countByG_P_V(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

			_persistence.countByG_P_V(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_LikeN_V() {
		try {
			_persistence.countByG_LikeN_V(ServiceTestUtil.nextLong(),
				StringPool.BLANK, ServiceTestUtil.nextLong());

			_persistence.countByG_LikeN_V(0L, StringPool.NULL, 0L);

			_persistence.countByG_LikeN_V(0L, (String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_LikeN_VArrayable() {
		try {
			_persistence.countByG_LikeN_V(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomString(),
				new long[] { ServiceTestUtil.nextLong(), 0L });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByP_N_V() {
		try {
			_persistence.countByP_N_V(ServiceTestUtil.nextLong(),
				StringPool.BLANK, ServiceTestUtil.nextLong());

			_persistence.countByP_N_V(0L, StringPool.NULL, 0L);

			_persistence.countByP_N_V(0L, (String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P_N_V() {
		try {
			_persistence.countByG_P_N_V(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), StringPool.BLANK,
				ServiceTestUtil.nextLong());

			_persistence.countByG_P_N_V(0L, 0L, StringPool.NULL, 0L);

			_persistence.countByG_P_N_V(0L, 0L, (String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetCategory newAssetCategory = addAssetCategory();

		AssetCategory existingAssetCategory = _persistence.findByPrimaryKey(newAssetCategory.getPrimaryKey());

		Assert.assertEquals(existingAssetCategory, newAssetCategory);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchCategoryException");
		}
		catch (NoSuchCategoryException nsee) {
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

	@Test
	public void testFilterFindByGroupId() throws Exception {
		try {
			_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetCategory", "uuid",
			true, "categoryId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "parentCategoryId", true, "leftCategoryId",
			true, "rightCategoryId", true, "name", true, "title", true,
			"description", true, "vocabularyId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetCategory newAssetCategory = addAssetCategory();

		AssetCategory existingAssetCategory = _persistence.fetchByPrimaryKey(newAssetCategory.getPrimaryKey());

		Assert.assertEquals(existingAssetCategory, newAssetCategory);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetCategory missingAssetCategory = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetCategory);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new AssetCategoryActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					AssetCategory assetCategory = (AssetCategory)object;

					Assert.assertNotNull(assetCategory);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetCategory newAssetCategory = addAssetCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetCategory.class,
				AssetCategory.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId",
				newAssetCategory.getCategoryId()));

		List<AssetCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetCategory existingAssetCategory = result.get(0);

		Assert.assertEquals(existingAssetCategory, newAssetCategory);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetCategory.class,
				AssetCategory.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId",
				ServiceTestUtil.nextLong()));

		List<AssetCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetCategory newAssetCategory = addAssetCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetCategory.class,
				AssetCategory.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("categoryId"));

		Object newCategoryId = newAssetCategory.getCategoryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryId",
				new Object[] { newCategoryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCategoryId = result.get(0);

		Assert.assertEquals(existingCategoryId, newCategoryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetCategory.class,
				AssetCategory.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("categoryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		AssetCategory newAssetCategory = addAssetCategory();

		_persistence.clearCache();

		AssetCategoryModelImpl existingAssetCategoryModelImpl = (AssetCategoryModelImpl)_persistence.findByPrimaryKey(newAssetCategory.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingAssetCategoryModelImpl.getUuid(),
				existingAssetCategoryModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingAssetCategoryModelImpl.getGroupId(),
			existingAssetCategoryModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingAssetCategoryModelImpl.getParentCategoryId(),
			existingAssetCategoryModelImpl.getOriginalParentCategoryId());
		Assert.assertTrue(Validator.equals(
				existingAssetCategoryModelImpl.getName(),
				existingAssetCategoryModelImpl.getOriginalName()));
		Assert.assertEquals(existingAssetCategoryModelImpl.getVocabularyId(),
			existingAssetCategoryModelImpl.getOriginalVocabularyId());
	}

	protected AssetCategory addAssetCategory() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetCategory assetCategory = _persistence.create(pk);

		assetCategory.setUuid(ServiceTestUtil.randomString());

		assetCategory.setGroupId(ServiceTestUtil.nextLong());

		assetCategory.setCompanyId(ServiceTestUtil.nextLong());

		assetCategory.setUserId(ServiceTestUtil.nextLong());

		assetCategory.setUserName(ServiceTestUtil.randomString());

		assetCategory.setCreateDate(ServiceTestUtil.nextDate());

		assetCategory.setModifiedDate(ServiceTestUtil.nextDate());

		assetCategory.setLeftCategoryId(ServiceTestUtil.nextLong());

		assetCategory.setRightCategoryId(ServiceTestUtil.nextLong());

		assetCategory.setName(ServiceTestUtil.randomString());

		assetCategory.setTitle(ServiceTestUtil.randomString());

		assetCategory.setDescription(ServiceTestUtil.randomString());

		assetCategory.setVocabularyId(ServiceTestUtil.nextLong());

		_persistence.update(assetCategory);

		return assetCategory;
	}

	@Test
	public void testMoveTree() throws Exception {
		long groupId = ServiceTestUtil.nextLong();

		AssetCategory rootAssetCategory = addAssetCategory(groupId, null);

		long previousRootLeftCategoryId = rootAssetCategory.getLeftCategoryId();
		long previousRootRightCategoryId = rootAssetCategory.getRightCategoryId();

		AssetCategory childAssetCategory = addAssetCategory(groupId,
				rootAssetCategory.getCategoryId());

		rootAssetCategory = _persistence.fetchByPrimaryKey(rootAssetCategory.getPrimaryKey());

		Assert.assertEquals(previousRootLeftCategoryId,
			rootAssetCategory.getLeftCategoryId());
		Assert.assertEquals(previousRootRightCategoryId + 2,
			rootAssetCategory.getRightCategoryId());
		Assert.assertEquals(rootAssetCategory.getLeftCategoryId() + 1,
			childAssetCategory.getLeftCategoryId());
		Assert.assertEquals(rootAssetCategory.getRightCategoryId() - 1,
			childAssetCategory.getRightCategoryId());
	}

	@Test
	public void testMoveTreeFromLeft() throws Exception {
		long groupId = ServiceTestUtil.nextLong();

		AssetCategory parentAssetCategory = addAssetCategory(groupId, null);

		AssetCategory childAssetCategory = addAssetCategory(groupId,
				parentAssetCategory.getCategoryId());

		parentAssetCategory = _persistence.fetchByPrimaryKey(parentAssetCategory.getPrimaryKey());

		AssetCategory rootAssetCategory = addAssetCategory(groupId, null);

		long previousRootLeftCategoryId = rootAssetCategory.getLeftCategoryId();
		long previousRootRightCategoryId = rootAssetCategory.getRightCategoryId();

		parentAssetCategory.setParentCategoryId(rootAssetCategory.getCategoryId());

		_persistence.update(parentAssetCategory);

		rootAssetCategory = _persistence.fetchByPrimaryKey(rootAssetCategory.getPrimaryKey());
		childAssetCategory = _persistence.fetchByPrimaryKey(childAssetCategory.getPrimaryKey());

		Assert.assertEquals(previousRootLeftCategoryId - 4,
			rootAssetCategory.getLeftCategoryId());
		Assert.assertEquals(previousRootRightCategoryId,
			rootAssetCategory.getRightCategoryId());
		Assert.assertEquals(rootAssetCategory.getLeftCategoryId() + 1,
			parentAssetCategory.getLeftCategoryId());
		Assert.assertEquals(rootAssetCategory.getRightCategoryId() - 1,
			parentAssetCategory.getRightCategoryId());
		Assert.assertEquals(parentAssetCategory.getLeftCategoryId() + 1,
			childAssetCategory.getLeftCategoryId());
		Assert.assertEquals(parentAssetCategory.getRightCategoryId() - 1,
			childAssetCategory.getRightCategoryId());
	}

	@Test
	public void testMoveTreeFromRight() throws Exception {
		long groupId = ServiceTestUtil.nextLong();

		AssetCategory rootAssetCategory = addAssetCategory(groupId, null);

		long previousRootLeftCategoryId = rootAssetCategory.getLeftCategoryId();
		long previousRootRightCategoryId = rootAssetCategory.getRightCategoryId();

		AssetCategory parentAssetCategory = addAssetCategory(groupId, null);

		AssetCategory childAssetCategory = addAssetCategory(groupId,
				parentAssetCategory.getCategoryId());

		parentAssetCategory = _persistence.fetchByPrimaryKey(parentAssetCategory.getPrimaryKey());

		parentAssetCategory.setParentCategoryId(rootAssetCategory.getCategoryId());

		_persistence.update(parentAssetCategory);

		rootAssetCategory = _persistence.fetchByPrimaryKey(rootAssetCategory.getPrimaryKey());
		childAssetCategory = _persistence.fetchByPrimaryKey(childAssetCategory.getPrimaryKey());

		Assert.assertEquals(previousRootLeftCategoryId,
			rootAssetCategory.getLeftCategoryId());
		Assert.assertEquals(previousRootRightCategoryId + 4,
			rootAssetCategory.getRightCategoryId());
		Assert.assertEquals(rootAssetCategory.getLeftCategoryId() + 1,
			parentAssetCategory.getLeftCategoryId());
		Assert.assertEquals(rootAssetCategory.getRightCategoryId() - 1,
			parentAssetCategory.getRightCategoryId());
		Assert.assertEquals(parentAssetCategory.getLeftCategoryId() + 1,
			childAssetCategory.getLeftCategoryId());
		Assert.assertEquals(parentAssetCategory.getRightCategoryId() - 1,
			childAssetCategory.getRightCategoryId());
	}

	@Test
	public void testMoveTreeIntoTreeFromLeft() throws Exception {
		long groupId = ServiceTestUtil.nextLong();

		AssetCategory parentAssetCategory = addAssetCategory(groupId, null);

		AssetCategory parentChildAssetCategory = addAssetCategory(groupId,
				parentAssetCategory.getCategoryId());

		parentAssetCategory = _persistence.fetchByPrimaryKey(parentAssetCategory.getPrimaryKey());

		AssetCategory rootAssetCategory = addAssetCategory(groupId, null);

		AssetCategory leftRootChildAssetCategory = addAssetCategory(groupId,
				rootAssetCategory.getCategoryId());

		rootAssetCategory = _persistence.fetchByPrimaryKey(rootAssetCategory.getPrimaryKey());

		AssetCategory rightRootChildAssetCategory = addAssetCategory(groupId,
				rootAssetCategory.getCategoryId());

		rootAssetCategory = _persistence.fetchByPrimaryKey(rootAssetCategory.getPrimaryKey());

		long previousRootLeftCategoryId = rootAssetCategory.getLeftCategoryId();
		long previousRootRightCategoryId = rootAssetCategory.getRightCategoryId();

		parentAssetCategory.setParentCategoryId(rightRootChildAssetCategory.getCategoryId());

		_persistence.update(parentAssetCategory);

		rootAssetCategory = _persistence.fetchByPrimaryKey(rootAssetCategory.getPrimaryKey());
		leftRootChildAssetCategory = _persistence.fetchByPrimaryKey(leftRootChildAssetCategory.getPrimaryKey());
		rightRootChildAssetCategory = _persistence.fetchByPrimaryKey(rightRootChildAssetCategory.getPrimaryKey());
		parentChildAssetCategory = _persistence.fetchByPrimaryKey(parentChildAssetCategory.getPrimaryKey());

		Assert.assertEquals(previousRootLeftCategoryId - 4,
			rootAssetCategory.getLeftCategoryId());
		Assert.assertEquals(previousRootRightCategoryId,
			rootAssetCategory.getRightCategoryId());
		Assert.assertEquals(rootAssetCategory.getLeftCategoryId() + 1,
			leftRootChildAssetCategory.getLeftCategoryId());
		Assert.assertEquals(rootAssetCategory.getRightCategoryId() - 7,
			leftRootChildAssetCategory.getRightCategoryId());
		Assert.assertEquals(rootAssetCategory.getLeftCategoryId() + 3,
			rightRootChildAssetCategory.getLeftCategoryId());
		Assert.assertEquals(rootAssetCategory.getRightCategoryId() - 1,
			rightRootChildAssetCategory.getRightCategoryId());
		Assert.assertEquals(rightRootChildAssetCategory.getLeftCategoryId() +
			1, parentAssetCategory.getLeftCategoryId());
		Assert.assertEquals(rightRootChildAssetCategory.getRightCategoryId() -
			1, parentAssetCategory.getRightCategoryId());
		Assert.assertEquals(parentAssetCategory.getLeftCategoryId() + 1,
			parentChildAssetCategory.getLeftCategoryId());
		Assert.assertEquals(parentAssetCategory.getRightCategoryId() - 1,
			parentChildAssetCategory.getRightCategoryId());
	}

	@Test
	public void testMoveTreeIntoTreeFromRight() throws Exception {
		long groupId = ServiceTestUtil.nextLong();

		AssetCategory rootAssetCategory = addAssetCategory(groupId, null);

		AssetCategory leftRootChildAssetCategory = addAssetCategory(groupId,
				rootAssetCategory.getCategoryId());

		rootAssetCategory = _persistence.fetchByPrimaryKey(rootAssetCategory.getPrimaryKey());

		AssetCategory rightRootChildAssetCategory = addAssetCategory(groupId,
				rootAssetCategory.getCategoryId());

		rootAssetCategory = _persistence.fetchByPrimaryKey(rootAssetCategory.getPrimaryKey());

		long previousRootLeftCategoryId = rootAssetCategory.getLeftCategoryId();
		long previousRootRightCategoryId = rootAssetCategory.getRightCategoryId();

		AssetCategory parentAssetCategory = addAssetCategory(groupId, null);

		AssetCategory parentChildAssetCategory = addAssetCategory(groupId,
				parentAssetCategory.getCategoryId());

		parentAssetCategory = _persistence.fetchByPrimaryKey(parentAssetCategory.getPrimaryKey());

		parentAssetCategory.setParentCategoryId(leftRootChildAssetCategory.getCategoryId());

		_persistence.update(parentAssetCategory);

		rootAssetCategory = _persistence.fetchByPrimaryKey(rootAssetCategory.getPrimaryKey());
		leftRootChildAssetCategory = _persistence.fetchByPrimaryKey(leftRootChildAssetCategory.getPrimaryKey());
		rightRootChildAssetCategory = _persistence.fetchByPrimaryKey(rightRootChildAssetCategory.getPrimaryKey());
		parentChildAssetCategory = _persistence.fetchByPrimaryKey(parentChildAssetCategory.getPrimaryKey());

		Assert.assertEquals(previousRootLeftCategoryId,
			rootAssetCategory.getLeftCategoryId());
		Assert.assertEquals(previousRootRightCategoryId + 4,
			rootAssetCategory.getRightCategoryId());
		Assert.assertEquals(rootAssetCategory.getLeftCategoryId() + 1,
			leftRootChildAssetCategory.getLeftCategoryId());
		Assert.assertEquals(rootAssetCategory.getRightCategoryId() - 3,
			leftRootChildAssetCategory.getRightCategoryId());
		Assert.assertEquals(rootAssetCategory.getLeftCategoryId() + 7,
			rightRootChildAssetCategory.getLeftCategoryId());
		Assert.assertEquals(rootAssetCategory.getRightCategoryId() - 1,
			rightRootChildAssetCategory.getRightCategoryId());
		Assert.assertEquals(leftRootChildAssetCategory.getLeftCategoryId() + 1,
			parentAssetCategory.getLeftCategoryId());
		Assert.assertEquals(leftRootChildAssetCategory.getRightCategoryId() -
			1, parentAssetCategory.getRightCategoryId());
		Assert.assertEquals(parentAssetCategory.getLeftCategoryId() + 1,
			parentChildAssetCategory.getLeftCategoryId());
		Assert.assertEquals(parentAssetCategory.getRightCategoryId() - 1,
			parentChildAssetCategory.getRightCategoryId());
	}

	protected AssetCategory addAssetCategory(long groupId, Long parentCategoryId)
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetCategory assetCategory = _persistence.create(pk);

		assetCategory.setUuid(ServiceTestUtil.randomString());
		assetCategory.setGroupId(groupId);

		assetCategory.setCompanyId(ServiceTestUtil.nextLong());

		assetCategory.setUserId(ServiceTestUtil.nextLong());

		assetCategory.setUserName(ServiceTestUtil.randomString());

		assetCategory.setCreateDate(ServiceTestUtil.nextDate());

		assetCategory.setModifiedDate(ServiceTestUtil.nextDate());

		assetCategory.setLeftCategoryId(ServiceTestUtil.nextLong());

		assetCategory.setRightCategoryId(ServiceTestUtil.nextLong());

		assetCategory.setName(ServiceTestUtil.randomString());

		assetCategory.setTitle(ServiceTestUtil.randomString());

		assetCategory.setDescription(ServiceTestUtil.randomString());

		assetCategory.setVocabularyId(ServiceTestUtil.nextLong());

		if (parentCategoryId != null) {
			assetCategory.setParentCategoryId(parentCategoryId);
		}

		_persistence.update(assetCategory);

		return assetCategory;
	}

	private static Log _log = LogFactoryUtil.getLog(AssetCategoryPersistenceTest.class);
	private ModelListener<AssetCategory>[] _modelListeners;
	private AssetCategoryPersistence _persistence = (AssetCategoryPersistence)PortalBeanLocatorUtil.locate(AssetCategoryPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}