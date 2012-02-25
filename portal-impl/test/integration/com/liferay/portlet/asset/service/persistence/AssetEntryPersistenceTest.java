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

import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.impl.AssetEntryModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetEntryPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (AssetEntryPersistence)PortalBeanLocatorUtil.locate(AssetEntryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AssetEntry assetEntry = _persistence.create(pk);

		assertNotNull(assetEntry);

		assertEquals(assetEntry.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AssetEntry newAssetEntry = addAssetEntry();

		_persistence.remove(newAssetEntry);

		AssetEntry existingAssetEntry = _persistence.fetchByPrimaryKey(newAssetEntry.getPrimaryKey());

		assertNull(existingAssetEntry);
	}

	public void testUpdateNew() throws Exception {
		addAssetEntry();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AssetEntry newAssetEntry = _persistence.create(pk);

		newAssetEntry.setGroupId(nextLong());

		newAssetEntry.setCompanyId(nextLong());

		newAssetEntry.setUserId(nextLong());

		newAssetEntry.setUserName(randomString());

		newAssetEntry.setCreateDate(nextDate());

		newAssetEntry.setModifiedDate(nextDate());

		newAssetEntry.setClassNameId(nextLong());

		newAssetEntry.setClassPK(nextLong());

		newAssetEntry.setClassUuid(randomString());

		newAssetEntry.setClassTypeId(nextLong());

		newAssetEntry.setVisible(randomBoolean());

		newAssetEntry.setStartDate(nextDate());

		newAssetEntry.setEndDate(nextDate());

		newAssetEntry.setPublishDate(nextDate());

		newAssetEntry.setExpirationDate(nextDate());

		newAssetEntry.setMimeType(randomString());

		newAssetEntry.setTitle(randomString());

		newAssetEntry.setDescription(randomString());

		newAssetEntry.setSummary(randomString());

		newAssetEntry.setUrl(randomString());

		newAssetEntry.setLayoutUuid(randomString());

		newAssetEntry.setHeight(nextInt());

		newAssetEntry.setWidth(nextInt());

		newAssetEntry.setPriority(nextDouble());

		newAssetEntry.setViewCount(nextInt());

		_persistence.update(newAssetEntry, false);

		AssetEntry existingAssetEntry = _persistence.findByPrimaryKey(newAssetEntry.getPrimaryKey());

		assertEquals(existingAssetEntry.getEntryId(), newAssetEntry.getEntryId());
		assertEquals(existingAssetEntry.getGroupId(), newAssetEntry.getGroupId());
		assertEquals(existingAssetEntry.getCompanyId(),
			newAssetEntry.getCompanyId());
		assertEquals(existingAssetEntry.getUserId(), newAssetEntry.getUserId());
		assertEquals(existingAssetEntry.getUserName(),
			newAssetEntry.getUserName());
		assertEquals(Time.getShortTimestamp(existingAssetEntry.getCreateDate()),
			Time.getShortTimestamp(newAssetEntry.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingAssetEntry.getModifiedDate()),
			Time.getShortTimestamp(newAssetEntry.getModifiedDate()));
		assertEquals(existingAssetEntry.getClassNameId(),
			newAssetEntry.getClassNameId());
		assertEquals(existingAssetEntry.getClassPK(), newAssetEntry.getClassPK());
		assertEquals(existingAssetEntry.getClassUuid(),
			newAssetEntry.getClassUuid());
		assertEquals(existingAssetEntry.getClassTypeId(),
			newAssetEntry.getClassTypeId());
		assertEquals(existingAssetEntry.getVisible(), newAssetEntry.getVisible());
		assertEquals(Time.getShortTimestamp(existingAssetEntry.getStartDate()),
			Time.getShortTimestamp(newAssetEntry.getStartDate()));
		assertEquals(Time.getShortTimestamp(existingAssetEntry.getEndDate()),
			Time.getShortTimestamp(newAssetEntry.getEndDate()));
		assertEquals(Time.getShortTimestamp(existingAssetEntry.getPublishDate()),
			Time.getShortTimestamp(newAssetEntry.getPublishDate()));
		assertEquals(Time.getShortTimestamp(
				existingAssetEntry.getExpirationDate()),
			Time.getShortTimestamp(newAssetEntry.getExpirationDate()));
		assertEquals(existingAssetEntry.getMimeType(),
			newAssetEntry.getMimeType());
		assertEquals(existingAssetEntry.getTitle(), newAssetEntry.getTitle());
		assertEquals(existingAssetEntry.getDescription(),
			newAssetEntry.getDescription());
		assertEquals(existingAssetEntry.getSummary(), newAssetEntry.getSummary());
		assertEquals(existingAssetEntry.getUrl(), newAssetEntry.getUrl());
		assertEquals(existingAssetEntry.getLayoutUuid(),
			newAssetEntry.getLayoutUuid());
		assertEquals(existingAssetEntry.getHeight(), newAssetEntry.getHeight());
		assertEquals(existingAssetEntry.getWidth(), newAssetEntry.getWidth());
		assertEquals(existingAssetEntry.getPriority(),
			newAssetEntry.getPriority());
		assertEquals(existingAssetEntry.getViewCount(),
			newAssetEntry.getViewCount());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetEntry newAssetEntry = addAssetEntry();

		AssetEntry existingAssetEntry = _persistence.findByPrimaryKey(newAssetEntry.getPrimaryKey());

		assertEquals(existingAssetEntry, newAssetEntry);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchEntryException");
		}
		catch (NoSuchEntryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetEntry newAssetEntry = addAssetEntry();

		AssetEntry existingAssetEntry = _persistence.fetchByPrimaryKey(newAssetEntry.getPrimaryKey());

		assertEquals(existingAssetEntry, newAssetEntry);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AssetEntry missingAssetEntry = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAssetEntry);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetEntry newAssetEntry = addAssetEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntry.class,
				AssetEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newAssetEntry.getEntryId()));

		List<AssetEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		AssetEntry existingAssetEntry = result.get(0);

		assertEquals(existingAssetEntry, newAssetEntry);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntry.class,
				AssetEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId", nextLong()));

		List<AssetEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetEntry newAssetEntry = addAssetEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntry.class,
				AssetEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		Object newEntryId = newAssetEntry.getEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { newEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingEntryId = result.get(0);

		assertEquals(existingEntryId, newEntryId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntry.class,
				AssetEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		AssetEntry newAssetEntry = addAssetEntry();

		_persistence.clearCache();

		AssetEntryModelImpl existingAssetEntryModelImpl = (AssetEntryModelImpl)_persistence.findByPrimaryKey(newAssetEntry.getPrimaryKey());

		assertEquals(existingAssetEntryModelImpl.getGroupId(),
			existingAssetEntryModelImpl.getOriginalGroupId());
		assertTrue(Validator.equals(
				existingAssetEntryModelImpl.getClassUuid(),
				existingAssetEntryModelImpl.getOriginalClassUuid()));

		assertEquals(existingAssetEntryModelImpl.getClassNameId(),
			existingAssetEntryModelImpl.getOriginalClassNameId());
		assertEquals(existingAssetEntryModelImpl.getClassPK(),
			existingAssetEntryModelImpl.getOriginalClassPK());
	}

	protected AssetEntry addAssetEntry() throws Exception {
		long pk = nextLong();

		AssetEntry assetEntry = _persistence.create(pk);

		assetEntry.setGroupId(nextLong());

		assetEntry.setCompanyId(nextLong());

		assetEntry.setUserId(nextLong());

		assetEntry.setUserName(randomString());

		assetEntry.setCreateDate(nextDate());

		assetEntry.setModifiedDate(nextDate());

		assetEntry.setClassNameId(nextLong());

		assetEntry.setClassPK(nextLong());

		assetEntry.setClassUuid(randomString());

		assetEntry.setClassTypeId(nextLong());

		assetEntry.setVisible(randomBoolean());

		assetEntry.setStartDate(nextDate());

		assetEntry.setEndDate(nextDate());

		assetEntry.setPublishDate(nextDate());

		assetEntry.setExpirationDate(nextDate());

		assetEntry.setMimeType(randomString());

		assetEntry.setTitle(randomString());

		assetEntry.setDescription(randomString());

		assetEntry.setSummary(randomString());

		assetEntry.setUrl(randomString());

		assetEntry.setLayoutUuid(randomString());

		assetEntry.setHeight(nextInt());

		assetEntry.setWidth(nextInt());

		assetEntry.setPriority(nextDouble());

		assetEntry.setViewCount(nextInt());

		_persistence.update(assetEntry, false);

		return assetEntry;
	}

	private AssetEntryPersistence _persistence;
}