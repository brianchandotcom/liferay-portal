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

import com.liferay.portlet.asset.NoSuchVocabularyException;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetVocabularyPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (AssetVocabularyPersistence)PortalBeanLocatorUtil.locate(AssetVocabularyPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AssetVocabulary assetVocabulary = _persistence.create(pk);

		assertNotNull(assetVocabulary);

		assertEquals(assetVocabulary.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		_persistence.remove(newAssetVocabulary);

		AssetVocabulary existingAssetVocabulary = _persistence.fetchByPrimaryKey(newAssetVocabulary.getPrimaryKey());

		assertNull(existingAssetVocabulary);
	}

	public void testUpdateNew() throws Exception {
		addAssetVocabulary();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AssetVocabulary newAssetVocabulary = _persistence.create(pk);

		newAssetVocabulary.setUuid(randomString());

		newAssetVocabulary.setGroupId(nextLong());

		newAssetVocabulary.setCompanyId(nextLong());

		newAssetVocabulary.setUserId(nextLong());

		newAssetVocabulary.setUserName(randomString());

		newAssetVocabulary.setCreateDate(nextDate());

		newAssetVocabulary.setModifiedDate(nextDate());

		newAssetVocabulary.setName(randomString());

		newAssetVocabulary.setTitle(randomString());

		newAssetVocabulary.setDescription(randomString());

		newAssetVocabulary.setSettings(randomString());

		_persistence.update(newAssetVocabulary, false);

		AssetVocabulary existingAssetVocabulary = _persistence.findByPrimaryKey(newAssetVocabulary.getPrimaryKey());

		assertEquals(existingAssetVocabulary.getUuid(),
			newAssetVocabulary.getUuid());
		assertEquals(existingAssetVocabulary.getVocabularyId(),
			newAssetVocabulary.getVocabularyId());
		assertEquals(existingAssetVocabulary.getGroupId(),
			newAssetVocabulary.getGroupId());
		assertEquals(existingAssetVocabulary.getCompanyId(),
			newAssetVocabulary.getCompanyId());
		assertEquals(existingAssetVocabulary.getUserId(),
			newAssetVocabulary.getUserId());
		assertEquals(existingAssetVocabulary.getUserName(),
			newAssetVocabulary.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingAssetVocabulary.getCreateDate()),
			Time.getShortTimestamp(newAssetVocabulary.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingAssetVocabulary.getModifiedDate()),
			Time.getShortTimestamp(newAssetVocabulary.getModifiedDate()));
		assertEquals(existingAssetVocabulary.getName(),
			newAssetVocabulary.getName());
		assertEquals(existingAssetVocabulary.getTitle(),
			newAssetVocabulary.getTitle());
		assertEquals(existingAssetVocabulary.getDescription(),
			newAssetVocabulary.getDescription());
		assertEquals(existingAssetVocabulary.getSettings(),
			newAssetVocabulary.getSettings());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		AssetVocabulary existingAssetVocabulary = _persistence.findByPrimaryKey(newAssetVocabulary.getPrimaryKey());

		assertEquals(existingAssetVocabulary, newAssetVocabulary);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchVocabularyException");
		}
		catch (NoSuchVocabularyException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		AssetVocabulary existingAssetVocabulary = _persistence.fetchByPrimaryKey(newAssetVocabulary.getPrimaryKey());

		assertEquals(existingAssetVocabulary, newAssetVocabulary);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AssetVocabulary missingAssetVocabulary = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAssetVocabulary);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetVocabulary.class,
				AssetVocabulary.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("vocabularyId",
				newAssetVocabulary.getVocabularyId()));

		List<AssetVocabulary> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		AssetVocabulary existingAssetVocabulary = result.get(0);

		assertEquals(existingAssetVocabulary, newAssetVocabulary);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetVocabulary.class,
				AssetVocabulary.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("vocabularyId", nextLong()));

		List<AssetVocabulary> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetVocabulary.class,
				AssetVocabulary.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"vocabularyId"));

		Object newVocabularyId = newAssetVocabulary.getVocabularyId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("vocabularyId",
				new Object[] { newVocabularyId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingVocabularyId = result.get(0);

		assertEquals(existingVocabularyId, newVocabularyId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetVocabulary.class,
				AssetVocabulary.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"vocabularyId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("vocabularyId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		_persistence.clearCache();

		AssetVocabularyModelImpl existingAssetVocabularyModelImpl = (AssetVocabularyModelImpl)_persistence.findByPrimaryKey(newAssetVocabulary.getPrimaryKey());

		assertTrue(Validator.equals(
				existingAssetVocabularyModelImpl.getUuid(),
				existingAssetVocabularyModelImpl.getOriginalUuid()));
		assertEquals(existingAssetVocabularyModelImpl.getGroupId(),
			existingAssetVocabularyModelImpl.getOriginalGroupId());

		assertEquals(existingAssetVocabularyModelImpl.getGroupId(),
			existingAssetVocabularyModelImpl.getOriginalGroupId());
		assertTrue(Validator.equals(
				existingAssetVocabularyModelImpl.getName(),
				existingAssetVocabularyModelImpl.getOriginalName()));
	}

	protected AssetVocabulary addAssetVocabulary() throws Exception {
		long pk = nextLong();

		AssetVocabulary assetVocabulary = _persistence.create(pk);

		assetVocabulary.setUuid(randomString());

		assetVocabulary.setGroupId(nextLong());

		assetVocabulary.setCompanyId(nextLong());

		assetVocabulary.setUserId(nextLong());

		assetVocabulary.setUserName(randomString());

		assetVocabulary.setCreateDate(nextDate());

		assetVocabulary.setModifiedDate(nextDate());

		assetVocabulary.setName(randomString());

		assetVocabulary.setTitle(randomString());

		assetVocabulary.setDescription(randomString());

		assetVocabulary.setSettings(randomString());

		_persistence.update(assetVocabulary, false);

		return assetVocabulary;
	}

	private AssetVocabularyPersistence _persistence;
}