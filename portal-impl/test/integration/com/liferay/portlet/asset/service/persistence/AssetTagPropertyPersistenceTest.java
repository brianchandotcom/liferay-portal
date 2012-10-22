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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.asset.NoSuchTagPropertyException;
import com.liferay.portlet.asset.model.AssetTagProperty;
import com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl;

import org.junit.After;
import org.junit.Assert;
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
public class AssetTagPropertyPersistenceTest {
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
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

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
		long pk = ServiceTestUtil.nextLong();

		AssetTagProperty newAssetTagProperty = _persistence.create(pk);

		newAssetTagProperty.setCompanyId(ServiceTestUtil.nextLong());

		newAssetTagProperty.setUserId(ServiceTestUtil.nextLong());

		newAssetTagProperty.setUserName(ServiceTestUtil.randomString());

		newAssetTagProperty.setCreateDate(ServiceTestUtil.nextDate());

		newAssetTagProperty.setModifiedDate(ServiceTestUtil.nextDate());

		newAssetTagProperty.setTagId(ServiceTestUtil.nextLong());

		newAssetTagProperty.setKey(ServiceTestUtil.randomString());

		newAssetTagProperty.setValue(ServiceTestUtil.randomString());

		_persistence.update(newAssetTagProperty);

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
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		AssetTagProperty existingAssetTagProperty = _persistence.findByPrimaryKey(newAssetTagProperty.getPrimaryKey());

		Assert.assertEquals(existingAssetTagProperty, newAssetTagProperty);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchTagPropertyException");
		}
		catch (NoSuchTagPropertyException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		AssetTagProperty existingAssetTagProperty = _persistence.fetchByPrimaryKey(newAssetTagProperty.getPrimaryKey());

		Assert.assertEquals(existingAssetTagProperty, newAssetTagProperty);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetTagProperty missingAssetTagProperty = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetTagProperty);
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
				ServiceTestUtil.nextLong()));

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
				new Object[] { ServiceTestUtil.nextLong() }));

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
		long pk = ServiceTestUtil.nextLong();

		AssetTagProperty assetTagProperty = _persistence.create(pk);

		assetTagProperty.setCompanyId(ServiceTestUtil.nextLong());

		assetTagProperty.setUserId(ServiceTestUtil.nextLong());

		assetTagProperty.setUserName(ServiceTestUtil.randomString());

		assetTagProperty.setCreateDate(ServiceTestUtil.nextDate());

		assetTagProperty.setModifiedDate(ServiceTestUtil.nextDate());

		assetTagProperty.setTagId(ServiceTestUtil.nextLong());

		assetTagProperty.setKey(ServiceTestUtil.randomString());

		assetTagProperty.setValue(ServiceTestUtil.randomString());

		_persistence.update(assetTagProperty);

		return assetTagProperty;
	}

	private static Log _log = LogFactoryUtil.getLog(AssetTagPropertyPersistenceTest.class);
	private AssetTagPropertyPersistence _persistence = (AssetTagPropertyPersistence)PortalBeanLocatorUtil.locate(AssetTagPropertyPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}