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

import com.liferay.portlet.asset.NoSuchTagPropertyException;
import com.liferay.portlet.asset.model.AssetTagProperty;
import com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetTagPropertyPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (AssetTagPropertyPersistence)PortalBeanLocatorUtil.locate(AssetTagPropertyPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AssetTagProperty assetTagProperty = _persistence.create(pk);

		assertNotNull(assetTagProperty);

		assertEquals(assetTagProperty.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		_persistence.remove(newAssetTagProperty);

		AssetTagProperty existingAssetTagProperty = _persistence.fetchByPrimaryKey(newAssetTagProperty.getPrimaryKey());

		assertNull(existingAssetTagProperty);
	}

	public void testUpdateNew() throws Exception {
		addAssetTagProperty();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AssetTagProperty newAssetTagProperty = _persistence.create(pk);

		newAssetTagProperty.setCompanyId(nextLong());

		newAssetTagProperty.setUserId(nextLong());

		newAssetTagProperty.setUserName(randomString());

		newAssetTagProperty.setCreateDate(nextDate());

		newAssetTagProperty.setModifiedDate(nextDate());

		newAssetTagProperty.setTagId(nextLong());

		newAssetTagProperty.setKey(randomString());

		newAssetTagProperty.setValue(randomString());

		_persistence.update(newAssetTagProperty, false);

		AssetTagProperty existingAssetTagProperty = _persistence.findByPrimaryKey(newAssetTagProperty.getPrimaryKey());

		assertEquals(existingAssetTagProperty.getTagPropertyId(),
			newAssetTagProperty.getTagPropertyId());
		assertEquals(existingAssetTagProperty.getCompanyId(),
			newAssetTagProperty.getCompanyId());
		assertEquals(existingAssetTagProperty.getUserId(),
			newAssetTagProperty.getUserId());
		assertEquals(existingAssetTagProperty.getUserName(),
			newAssetTagProperty.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingAssetTagProperty.getCreateDate()),
			Time.getShortTimestamp(newAssetTagProperty.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingAssetTagProperty.getModifiedDate()),
			Time.getShortTimestamp(newAssetTagProperty.getModifiedDate()));
		assertEquals(existingAssetTagProperty.getTagId(),
			newAssetTagProperty.getTagId());
		assertEquals(existingAssetTagProperty.getKey(),
			newAssetTagProperty.getKey());
		assertEquals(existingAssetTagProperty.getValue(),
			newAssetTagProperty.getValue());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		AssetTagProperty existingAssetTagProperty = _persistence.findByPrimaryKey(newAssetTagProperty.getPrimaryKey());

		assertEquals(existingAssetTagProperty, newAssetTagProperty);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchTagPropertyException");
		}
		catch (NoSuchTagPropertyException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		AssetTagProperty existingAssetTagProperty = _persistence.fetchByPrimaryKey(newAssetTagProperty.getPrimaryKey());

		assertEquals(existingAssetTagProperty, newAssetTagProperty);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AssetTagProperty missingAssetTagProperty = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAssetTagProperty);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagProperty.class,
				AssetTagProperty.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagPropertyId",
				newAssetTagProperty.getTagPropertyId()));

		List<AssetTagProperty> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		AssetTagProperty existingAssetTagProperty = result.get(0);

		assertEquals(existingAssetTagProperty, newAssetTagProperty);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagProperty.class,
				AssetTagProperty.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagPropertyId", nextLong()));

		List<AssetTagProperty> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingTagPropertyId = result.get(0);

		assertEquals(existingTagPropertyId, newTagPropertyId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagProperty.class,
				AssetTagProperty.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"tagPropertyId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("tagPropertyId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		_persistence.clearCache();

		AssetTagPropertyModelImpl existingAssetTagPropertyModelImpl = (AssetTagPropertyModelImpl)_persistence.findByPrimaryKey(newAssetTagProperty.getPrimaryKey());

		assertEquals(existingAssetTagPropertyModelImpl.getTagId(),
			existingAssetTagPropertyModelImpl.getOriginalTagId());
		assertTrue(Validator.equals(
				existingAssetTagPropertyModelImpl.getKey(),
				existingAssetTagPropertyModelImpl.getOriginalKey()));
	}

	protected AssetTagProperty addAssetTagProperty() throws Exception {
		long pk = nextLong();

		AssetTagProperty assetTagProperty = _persistence.create(pk);

		assetTagProperty.setCompanyId(nextLong());

		assetTagProperty.setUserId(nextLong());

		assetTagProperty.setUserName(randomString());

		assetTagProperty.setCreateDate(nextDate());

		assetTagProperty.setModifiedDate(nextDate());

		assetTagProperty.setTagId(nextLong());

		assetTagProperty.setKey(randomString());

		assetTagProperty.setValue(randomString());

		_persistence.update(assetTagProperty, false);

		return assetTagProperty;
	}

	private AssetTagPropertyPersistence _persistence;
}