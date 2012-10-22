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

import com.liferay.portlet.asset.NoSuchCategoryPropertyException;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.model.impl.AssetCategoryPropertyModelImpl;

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
public class AssetCategoryPropertyPersistenceTest {
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

		AssetCategoryProperty assetCategoryProperty = _persistence.create(pk);

		Assert.assertNotNull(assetCategoryProperty);

		Assert.assertEquals(assetCategoryProperty.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetCategoryProperty newAssetCategoryProperty = addAssetCategoryProperty();

		_persistence.remove(newAssetCategoryProperty);

		AssetCategoryProperty existingAssetCategoryProperty = _persistence.fetchByPrimaryKey(newAssetCategoryProperty.getPrimaryKey());

		Assert.assertNull(existingAssetCategoryProperty);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetCategoryProperty();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetCategoryProperty newAssetCategoryProperty = _persistence.create(pk);

		newAssetCategoryProperty.setCompanyId(ServiceTestUtil.nextLong());

		newAssetCategoryProperty.setUserId(ServiceTestUtil.nextLong());

		newAssetCategoryProperty.setUserName(ServiceTestUtil.randomString());

		newAssetCategoryProperty.setCreateDate(ServiceTestUtil.nextDate());

		newAssetCategoryProperty.setModifiedDate(ServiceTestUtil.nextDate());

		newAssetCategoryProperty.setCategoryId(ServiceTestUtil.nextLong());

		newAssetCategoryProperty.setKey(ServiceTestUtil.randomString());

		newAssetCategoryProperty.setValue(ServiceTestUtil.randomString());

		_persistence.update(newAssetCategoryProperty);

		AssetCategoryProperty existingAssetCategoryProperty = _persistence.findByPrimaryKey(newAssetCategoryProperty.getPrimaryKey());

		Assert.assertEquals(existingAssetCategoryProperty.getCategoryPropertyId(),
			newAssetCategoryProperty.getCategoryPropertyId());
		Assert.assertEquals(existingAssetCategoryProperty.getCompanyId(),
			newAssetCategoryProperty.getCompanyId());
		Assert.assertEquals(existingAssetCategoryProperty.getUserId(),
			newAssetCategoryProperty.getUserId());
		Assert.assertEquals(existingAssetCategoryProperty.getUserName(),
			newAssetCategoryProperty.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetCategoryProperty.getCreateDate()),
			Time.getShortTimestamp(newAssetCategoryProperty.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetCategoryProperty.getModifiedDate()),
			Time.getShortTimestamp(newAssetCategoryProperty.getModifiedDate()));
		Assert.assertEquals(existingAssetCategoryProperty.getCategoryId(),
			newAssetCategoryProperty.getCategoryId());
		Assert.assertEquals(existingAssetCategoryProperty.getKey(),
			newAssetCategoryProperty.getKey());
		Assert.assertEquals(existingAssetCategoryProperty.getValue(),
			newAssetCategoryProperty.getValue());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetCategoryProperty newAssetCategoryProperty = addAssetCategoryProperty();

		AssetCategoryProperty existingAssetCategoryProperty = _persistence.findByPrimaryKey(newAssetCategoryProperty.getPrimaryKey());

		Assert.assertEquals(existingAssetCategoryProperty,
			newAssetCategoryProperty);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchCategoryPropertyException");
		}
		catch (NoSuchCategoryPropertyException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetCategoryProperty newAssetCategoryProperty = addAssetCategoryProperty();

		AssetCategoryProperty existingAssetCategoryProperty = _persistence.fetchByPrimaryKey(newAssetCategoryProperty.getPrimaryKey());

		Assert.assertEquals(existingAssetCategoryProperty,
			newAssetCategoryProperty);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetCategoryProperty missingAssetCategoryProperty = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetCategoryProperty);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetCategoryProperty newAssetCategoryProperty = addAssetCategoryProperty();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetCategoryProperty.class,
				AssetCategoryProperty.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryPropertyId",
				newAssetCategoryProperty.getCategoryPropertyId()));

		List<AssetCategoryProperty> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetCategoryProperty existingAssetCategoryProperty = result.get(0);

		Assert.assertEquals(existingAssetCategoryProperty,
			newAssetCategoryProperty);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetCategoryProperty.class,
				AssetCategoryProperty.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryPropertyId",
				ServiceTestUtil.nextLong()));

		List<AssetCategoryProperty> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetCategoryProperty newAssetCategoryProperty = addAssetCategoryProperty();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetCategoryProperty.class,
				AssetCategoryProperty.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"categoryPropertyId"));

		Object newCategoryPropertyId = newAssetCategoryProperty.getCategoryPropertyId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryPropertyId",
				new Object[] { newCategoryPropertyId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCategoryPropertyId = result.get(0);

		Assert.assertEquals(existingCategoryPropertyId, newCategoryPropertyId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetCategoryProperty.class,
				AssetCategoryProperty.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"categoryPropertyId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryPropertyId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		AssetCategoryProperty newAssetCategoryProperty = addAssetCategoryProperty();

		_persistence.clearCache();

		AssetCategoryPropertyModelImpl existingAssetCategoryPropertyModelImpl = (AssetCategoryPropertyModelImpl)_persistence.findByPrimaryKey(newAssetCategoryProperty.getPrimaryKey());

		Assert.assertEquals(existingAssetCategoryPropertyModelImpl.getCategoryId(),
			existingAssetCategoryPropertyModelImpl.getOriginalCategoryId());
		Assert.assertTrue(Validator.equals(
				existingAssetCategoryPropertyModelImpl.getKey(),
				existingAssetCategoryPropertyModelImpl.getOriginalKey()));
	}

	protected AssetCategoryProperty addAssetCategoryProperty()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetCategoryProperty assetCategoryProperty = _persistence.create(pk);

		assetCategoryProperty.setCompanyId(ServiceTestUtil.nextLong());

		assetCategoryProperty.setUserId(ServiceTestUtil.nextLong());

		assetCategoryProperty.setUserName(ServiceTestUtil.randomString());

		assetCategoryProperty.setCreateDate(ServiceTestUtil.nextDate());

		assetCategoryProperty.setModifiedDate(ServiceTestUtil.nextDate());

		assetCategoryProperty.setCategoryId(ServiceTestUtil.nextLong());

		assetCategoryProperty.setKey(ServiceTestUtil.randomString());

		assetCategoryProperty.setValue(ServiceTestUtil.randomString());

		_persistence.update(assetCategoryProperty);

		return assetCategoryProperty;
	}

	private static Log _log = LogFactoryUtil.getLog(AssetCategoryPropertyPersistenceTest.class);
	private AssetCategoryPropertyPersistence _persistence = (AssetCategoryPropertyPersistence)PortalBeanLocatorUtil.locate(AssetCategoryPropertyPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}