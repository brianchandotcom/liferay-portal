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

import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.impl.AssetTagModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetTagPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (AssetTagPersistence)PortalBeanLocatorUtil.locate(AssetTagPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AssetTag assetTag = _persistence.create(pk);

		assertNotNull(assetTag);

		assertEquals(assetTag.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AssetTag newAssetTag = addAssetTag();

		_persistence.remove(newAssetTag);

		AssetTag existingAssetTag = _persistence.fetchByPrimaryKey(newAssetTag.getPrimaryKey());

		assertNull(existingAssetTag);
	}

	public void testUpdateNew() throws Exception {
		addAssetTag();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AssetTag newAssetTag = _persistence.create(pk);

		newAssetTag.setGroupId(nextLong());

		newAssetTag.setCompanyId(nextLong());

		newAssetTag.setUserId(nextLong());

		newAssetTag.setUserName(randomString());

		newAssetTag.setCreateDate(nextDate());

		newAssetTag.setModifiedDate(nextDate());

		newAssetTag.setName(randomString());

		newAssetTag.setAssetCount(nextInt());

		_persistence.update(newAssetTag, false);

		AssetTag existingAssetTag = _persistence.findByPrimaryKey(newAssetTag.getPrimaryKey());

		assertEquals(existingAssetTag.getTagId(), newAssetTag.getTagId());
		assertEquals(existingAssetTag.getGroupId(), newAssetTag.getGroupId());
		assertEquals(existingAssetTag.getCompanyId(), newAssetTag.getCompanyId());
		assertEquals(existingAssetTag.getUserId(), newAssetTag.getUserId());
		assertEquals(existingAssetTag.getUserName(), newAssetTag.getUserName());
		assertEquals(Time.getShortTimestamp(existingAssetTag.getCreateDate()),
			Time.getShortTimestamp(newAssetTag.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingAssetTag.getModifiedDate()),
			Time.getShortTimestamp(newAssetTag.getModifiedDate()));
		assertEquals(existingAssetTag.getName(), newAssetTag.getName());
		assertEquals(existingAssetTag.getAssetCount(),
			newAssetTag.getAssetCount());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetTag newAssetTag = addAssetTag();

		AssetTag existingAssetTag = _persistence.findByPrimaryKey(newAssetTag.getPrimaryKey());

		assertEquals(existingAssetTag, newAssetTag);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchTagException");
		}
		catch (NoSuchTagException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetTag newAssetTag = addAssetTag();

		AssetTag existingAssetTag = _persistence.fetchByPrimaryKey(newAssetTag.getPrimaryKey());

		assertEquals(existingAssetTag, newAssetTag);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AssetTag missingAssetTag = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAssetTag);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetTag newAssetTag = addAssetTag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTag.class,
				AssetTag.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagId",
				newAssetTag.getTagId()));

		List<AssetTag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		AssetTag existingAssetTag = result.get(0);

		assertEquals(existingAssetTag, newAssetTag);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTag.class,
				AssetTag.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagId", nextLong()));

		List<AssetTag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetTag newAssetTag = addAssetTag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTag.class,
				AssetTag.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("tagId"));

		Object newTagId = newAssetTag.getTagId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("tagId",
				new Object[] { newTagId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingTagId = result.get(0);

		assertEquals(existingTagId, newTagId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTag.class,
				AssetTag.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("tagId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("tagId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		AssetTag newAssetTag = addAssetTag();

		_persistence.clearCache();

		AssetTagModelImpl existingAssetTagModelImpl = (AssetTagModelImpl)_persistence.findByPrimaryKey(newAssetTag.getPrimaryKey());

		assertEquals(existingAssetTagModelImpl.getGroupId(),
			existingAssetTagModelImpl.getOriginalGroupId());
		assertTrue(Validator.equals(existingAssetTagModelImpl.getName(),
				existingAssetTagModelImpl.getOriginalName()));
	}

	protected AssetTag addAssetTag() throws Exception {
		long pk = nextLong();

		AssetTag assetTag = _persistence.create(pk);

		assetTag.setGroupId(nextLong());

		assetTag.setCompanyId(nextLong());

		assetTag.setUserId(nextLong());

		assetTag.setUserName(randomString());

		assetTag.setCreateDate(nextDate());

		assetTag.setModifiedDate(nextDate());

		assetTag.setName(randomString());

		assetTag.setAssetCount(nextInt());

		_persistence.update(assetTag, false);

		return assetTag;
	}

	private AssetTagPersistence _persistence;
}