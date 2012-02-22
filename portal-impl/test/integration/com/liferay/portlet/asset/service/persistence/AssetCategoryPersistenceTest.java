/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.asset.NoSuchCategoryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetCategoryPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (AssetCategoryPersistence)PortalBeanLocatorUtil.locate(AssetCategoryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AssetCategory assetCategory = _persistence.create(pk);

		assertNotNull(assetCategory);

		assertEquals(assetCategory.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AssetCategory newAssetCategory = addAssetCategory();

		_persistence.remove(newAssetCategory);

		AssetCategory existingAssetCategory = _persistence.fetchByPrimaryKey(newAssetCategory.getPrimaryKey());

		assertNull(existingAssetCategory);
	}

	public void testUpdateNew() throws Exception {
		addAssetCategory();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AssetCategory newAssetCategory = _persistence.create(pk);

		newAssetCategory.setUuid(randomString());

		newAssetCategory.setGroupId(nextLong());

		newAssetCategory.setCompanyId(nextLong());

		newAssetCategory.setUserId(nextLong());

		newAssetCategory.setUserName(randomString());

		newAssetCategory.setCreateDate(nextDate());

		newAssetCategory.setModifiedDate(nextDate());

		newAssetCategory.setLeftCategoryId(nextLong());

		newAssetCategory.setRightCategoryId(nextLong());

		newAssetCategory.setName(randomString());

		newAssetCategory.setTitle(randomString());

		newAssetCategory.setDescription(randomString());

		newAssetCategory.setVocabularyId(nextLong());

		_persistence.update(newAssetCategory, false);

		AssetCategory existingAssetCategory = _persistence.findByPrimaryKey(newAssetCategory.getPrimaryKey());

		assertEquals(existingAssetCategory.getUuid(), newAssetCategory.getUuid());
		assertEquals(existingAssetCategory.getCategoryId(),
			newAssetCategory.getCategoryId());
		assertEquals(existingAssetCategory.getGroupId(),
			newAssetCategory.getGroupId());
		assertEquals(existingAssetCategory.getCompanyId(),
			newAssetCategory.getCompanyId());
		assertEquals(existingAssetCategory.getUserId(),
			newAssetCategory.getUserId());
		assertEquals(existingAssetCategory.getUserName(),
			newAssetCategory.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingAssetCategory.getCreateDate()),
			Time.getShortTimestamp(newAssetCategory.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingAssetCategory.getModifiedDate()),
			Time.getShortTimestamp(newAssetCategory.getModifiedDate()));
		assertEquals(existingAssetCategory.getParentCategoryId(),
			newAssetCategory.getParentCategoryId());
		assertEquals(existingAssetCategory.getLeftCategoryId(),
			newAssetCategory.getLeftCategoryId());
		assertEquals(existingAssetCategory.getRightCategoryId(),
			newAssetCategory.getRightCategoryId());
		assertEquals(existingAssetCategory.getName(), newAssetCategory.getName());
		assertEquals(existingAssetCategory.getTitle(),
			newAssetCategory.getTitle());
		assertEquals(existingAssetCategory.getDescription(),
			newAssetCategory.getDescription());
		assertEquals(existingAssetCategory.getVocabularyId(),
			newAssetCategory.getVocabularyId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetCategory newAssetCategory = addAssetCategory();

		AssetCategory existingAssetCategory = _persistence.findByPrimaryKey(newAssetCategory.getPrimaryKey());

		assertEquals(existingAssetCategory, newAssetCategory);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchCategoryException");
		}
		catch (NoSuchCategoryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetCategory newAssetCategory = addAssetCategory();

		AssetCategory existingAssetCategory = _persistence.fetchByPrimaryKey(newAssetCategory.getPrimaryKey());

		assertEquals(existingAssetCategory, newAssetCategory);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AssetCategory missingAssetCategory = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAssetCategory);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetCategory newAssetCategory = addAssetCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetCategory.class,
				AssetCategory.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId",
				newAssetCategory.getCategoryId()));

		List<AssetCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		AssetCategory existingAssetCategory = result.get(0);

		assertEquals(existingAssetCategory, newAssetCategory);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetCategory.class,
				AssetCategory.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId", nextLong()));

		List<AssetCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingCategoryId = result.get(0);

		assertEquals(existingCategoryId, newCategoryId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetCategory.class,
				AssetCategory.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("categoryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		AssetCategory newAssetCategory = addAssetCategory();

		_persistence.clearCache();

		AssetCategoryModelImpl existingAssetCategoryModelImpl = (AssetCategoryModelImpl)_persistence.findByPrimaryKey(newAssetCategory.getPrimaryKey());

		assertTrue(Validator.equals(existingAssetCategoryModelImpl.getUuid(),
				existingAssetCategoryModelImpl.getOriginalUuid()));
		assertEquals(existingAssetCategoryModelImpl.getGroupId(),
			existingAssetCategoryModelImpl.getOriginalGroupId());

		assertEquals(existingAssetCategoryModelImpl.getParentCategoryId(),
			existingAssetCategoryModelImpl.getOriginalParentCategoryId());
		assertTrue(Validator.equals(existingAssetCategoryModelImpl.getName(),
				existingAssetCategoryModelImpl.getOriginalName()));
		assertEquals(existingAssetCategoryModelImpl.getVocabularyId(),
			existingAssetCategoryModelImpl.getOriginalVocabularyId());
	}

	protected AssetCategory addAssetCategory() throws Exception {
		long pk = nextLong();

		AssetCategory assetCategory = _persistence.create(pk);

		assetCategory.setUuid(randomString());

		assetCategory.setGroupId(nextLong());

		assetCategory.setCompanyId(nextLong());

		assetCategory.setUserId(nextLong());

		assetCategory.setUserName(randomString());

		assetCategory.setCreateDate(nextDate());

		assetCategory.setModifiedDate(nextDate());

		assetCategory.setLeftCategoryId(nextLong());

		assetCategory.setRightCategoryId(nextLong());

		assetCategory.setName(randomString());

		assetCategory.setTitle(randomString());

		assetCategory.setDescription(randomString());

		assetCategory.setVocabularyId(nextLong());

		_persistence.update(assetCategory, false);

		return assetCategory;
	}

	public void testMoveTree() throws Exception {
		long groupId = nextLong();

		AssetCategory rootAssetCategory = addAssetCategory(groupId, null);

		long previousRootLeftCategoryId = rootAssetCategory.getLeftCategoryId();
		long previousRootRightCategoryId = rootAssetCategory.getRightCategoryId();

		AssetCategory childAssetCategory = addAssetCategory(groupId,
				rootAssetCategory.getCategoryId());

		rootAssetCategory = _persistence.fetchByPrimaryKey(rootAssetCategory.getPrimaryKey());

		assertEquals(previousRootLeftCategoryId,
			rootAssetCategory.getLeftCategoryId());
		assertEquals(previousRootRightCategoryId + 2,
			rootAssetCategory.getRightCategoryId());
		assertEquals(rootAssetCategory.getLeftCategoryId() + 1,
			childAssetCategory.getLeftCategoryId());
		assertEquals(rootAssetCategory.getRightCategoryId() - 1,
			childAssetCategory.getRightCategoryId());
	}

	public void testMoveTreeFromLeft() throws Exception {
		long groupId = nextLong();

		AssetCategory parentAssetCategory = addAssetCategory(groupId, null);

		AssetCategory childAssetCategory = addAssetCategory(groupId,
				parentAssetCategory.getCategoryId());

		parentAssetCategory = _persistence.fetchByPrimaryKey(parentAssetCategory.getPrimaryKey());

		AssetCategory rootAssetCategory = addAssetCategory(groupId, null);

		long previousRootLeftCategoryId = rootAssetCategory.getLeftCategoryId();
		long previousRootRightCategoryId = rootAssetCategory.getRightCategoryId();

		parentAssetCategory.setParentCategoryId(rootAssetCategory.getCategoryId());

		_persistence.update(parentAssetCategory, false);

		rootAssetCategory = _persistence.fetchByPrimaryKey(rootAssetCategory.getPrimaryKey());
		childAssetCategory = _persistence.fetchByPrimaryKey(childAssetCategory.getPrimaryKey());

		assertEquals(previousRootLeftCategoryId - 4,
			rootAssetCategory.getLeftCategoryId());
		assertEquals(previousRootRightCategoryId,
			rootAssetCategory.getRightCategoryId());
		assertEquals(rootAssetCategory.getLeftCategoryId() + 1,
			parentAssetCategory.getLeftCategoryId());
		assertEquals(rootAssetCategory.getRightCategoryId() - 1,
			parentAssetCategory.getRightCategoryId());
		assertEquals(parentAssetCategory.getLeftCategoryId() + 1,
			childAssetCategory.getLeftCategoryId());
		assertEquals(parentAssetCategory.getRightCategoryId() - 1,
			childAssetCategory.getRightCategoryId());
	}

	public void testMoveTreeFromRight() throws Exception {
		long groupId = nextLong();

		AssetCategory rootAssetCategory = addAssetCategory(groupId, null);

		long previousRootLeftCategoryId = rootAssetCategory.getLeftCategoryId();
		long previousRootRightCategoryId = rootAssetCategory.getRightCategoryId();

		AssetCategory parentAssetCategory = addAssetCategory(groupId, null);

		AssetCategory childAssetCategory = addAssetCategory(groupId,
				parentAssetCategory.getCategoryId());

		parentAssetCategory = _persistence.fetchByPrimaryKey(parentAssetCategory.getPrimaryKey());

		parentAssetCategory.setParentCategoryId(rootAssetCategory.getCategoryId());

		_persistence.update(parentAssetCategory, false);

		rootAssetCategory = _persistence.fetchByPrimaryKey(rootAssetCategory.getPrimaryKey());
		childAssetCategory = _persistence.fetchByPrimaryKey(childAssetCategory.getPrimaryKey());

		assertEquals(previousRootLeftCategoryId,
			rootAssetCategory.getLeftCategoryId());
		assertEquals(previousRootRightCategoryId + 4,
			rootAssetCategory.getRightCategoryId());
		assertEquals(rootAssetCategory.getLeftCategoryId() + 1,
			parentAssetCategory.getLeftCategoryId());
		assertEquals(rootAssetCategory.getRightCategoryId() - 1,
			parentAssetCategory.getRightCategoryId());
		assertEquals(parentAssetCategory.getLeftCategoryId() + 1,
			childAssetCategory.getLeftCategoryId());
		assertEquals(parentAssetCategory.getRightCategoryId() - 1,
			childAssetCategory.getRightCategoryId());
	}

	public void testMoveTreeIntoTreeFromLeft() throws Exception {
		long groupId = nextLong();

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

		_persistence.update(parentAssetCategory, false);

		rootAssetCategory = _persistence.fetchByPrimaryKey(rootAssetCategory.getPrimaryKey());
		leftRootChildAssetCategory = _persistence.fetchByPrimaryKey(leftRootChildAssetCategory.getPrimaryKey());
		rightRootChildAssetCategory = _persistence.fetchByPrimaryKey(rightRootChildAssetCategory.getPrimaryKey());
		parentChildAssetCategory = _persistence.fetchByPrimaryKey(parentChildAssetCategory.getPrimaryKey());

		assertEquals(previousRootLeftCategoryId - 4,
			rootAssetCategory.getLeftCategoryId());
		assertEquals(previousRootRightCategoryId,
			rootAssetCategory.getRightCategoryId());
		assertEquals(rootAssetCategory.getLeftCategoryId() + 1,
			leftRootChildAssetCategory.getLeftCategoryId());
		assertEquals(rootAssetCategory.getRightCategoryId() - 7,
			leftRootChildAssetCategory.getRightCategoryId());
		assertEquals(rootAssetCategory.getLeftCategoryId() + 3,
			rightRootChildAssetCategory.getLeftCategoryId());
		assertEquals(rootAssetCategory.getRightCategoryId() - 1,
			rightRootChildAssetCategory.getRightCategoryId());
		assertEquals(rightRootChildAssetCategory.getLeftCategoryId() + 1,
			parentAssetCategory.getLeftCategoryId());
		assertEquals(rightRootChildAssetCategory.getRightCategoryId() - 1,
			parentAssetCategory.getRightCategoryId());
		assertEquals(parentAssetCategory.getLeftCategoryId() + 1,
			parentChildAssetCategory.getLeftCategoryId());
		assertEquals(parentAssetCategory.getRightCategoryId() - 1,
			parentChildAssetCategory.getRightCategoryId());
	}

	public void testMoveTreeIntoTreeFromRight() throws Exception {
		long groupId = nextLong();

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

		_persistence.update(parentAssetCategory, false);

		rootAssetCategory = _persistence.fetchByPrimaryKey(rootAssetCategory.getPrimaryKey());
		leftRootChildAssetCategory = _persistence.fetchByPrimaryKey(leftRootChildAssetCategory.getPrimaryKey());
		rightRootChildAssetCategory = _persistence.fetchByPrimaryKey(rightRootChildAssetCategory.getPrimaryKey());
		parentChildAssetCategory = _persistence.fetchByPrimaryKey(parentChildAssetCategory.getPrimaryKey());

		assertEquals(previousRootLeftCategoryId,
			rootAssetCategory.getLeftCategoryId());
		assertEquals(previousRootRightCategoryId + 4,
			rootAssetCategory.getRightCategoryId());
		assertEquals(rootAssetCategory.getLeftCategoryId() + 1,
			leftRootChildAssetCategory.getLeftCategoryId());
		assertEquals(rootAssetCategory.getRightCategoryId() - 3,
			leftRootChildAssetCategory.getRightCategoryId());
		assertEquals(rootAssetCategory.getLeftCategoryId() + 7,
			rightRootChildAssetCategory.getLeftCategoryId());
		assertEquals(rootAssetCategory.getRightCategoryId() - 1,
			rightRootChildAssetCategory.getRightCategoryId());
		assertEquals(leftRootChildAssetCategory.getLeftCategoryId() + 1,
			parentAssetCategory.getLeftCategoryId());
		assertEquals(leftRootChildAssetCategory.getRightCategoryId() - 1,
			parentAssetCategory.getRightCategoryId());
		assertEquals(parentAssetCategory.getLeftCategoryId() + 1,
			parentChildAssetCategory.getLeftCategoryId());
		assertEquals(parentAssetCategory.getRightCategoryId() - 1,
			parentChildAssetCategory.getRightCategoryId());
	}

	protected AssetCategory addAssetCategory(long groupId, Long parentCategoryId)
		throws Exception {
		long pk = nextLong();

		AssetCategory assetCategory = _persistence.create(pk);

		assetCategory.setUuid(randomString());
		assetCategory.setGroupId(groupId);

		assetCategory.setCompanyId(nextLong());

		assetCategory.setUserId(nextLong());

		assetCategory.setUserName(randomString());

		assetCategory.setCreateDate(nextDate());

		assetCategory.setModifiedDate(nextDate());

		assetCategory.setLeftCategoryId(nextLong());

		assetCategory.setRightCategoryId(nextLong());

		assetCategory.setName(randomString());

		assetCategory.setTitle(randomString());

		assetCategory.setDescription(randomString());

		assetCategory.setVocabularyId(nextLong());

		if (parentCategoryId != null) {
			assetCategory.setParentCategoryId(parentCategoryId);
		}

		_persistence.update(assetCategory, false);

		return assetCategory;
	}

	private AssetCategoryPersistence _persistence;
}