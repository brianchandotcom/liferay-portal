/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.impl.AssetEntryModelImpl;

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
public class AssetEntryPersistenceTest {
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

		AssetEntry assetEntry = _persistence.create(pk);

		Assert.assertNotNull(assetEntry);

		Assert.assertEquals(assetEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetEntry newAssetEntry = addAssetEntry();

		_persistence.remove(newAssetEntry);

		AssetEntry existingAssetEntry = _persistence.fetchByPrimaryKey(newAssetEntry.getPrimaryKey());

		Assert.assertNull(existingAssetEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetEntry newAssetEntry = _persistence.create(pk);

		newAssetEntry.setGroupId(ServiceTestUtil.nextLong());

		newAssetEntry.setCompanyId(ServiceTestUtil.nextLong());

		newAssetEntry.setUserId(ServiceTestUtil.nextLong());

		newAssetEntry.setUserName(ServiceTestUtil.randomString());

		newAssetEntry.setCreateDate(ServiceTestUtil.nextDate());

		newAssetEntry.setModifiedDate(ServiceTestUtil.nextDate());

		newAssetEntry.setClassNameId(ServiceTestUtil.nextLong());

		newAssetEntry.setClassPK(ServiceTestUtil.nextLong());

		newAssetEntry.setClassUuid(ServiceTestUtil.randomString());

		newAssetEntry.setClassTypeId(ServiceTestUtil.nextLong());

		newAssetEntry.setVisible(ServiceTestUtil.randomBoolean());

		newAssetEntry.setStartDate(ServiceTestUtil.nextDate());

		newAssetEntry.setEndDate(ServiceTestUtil.nextDate());

		newAssetEntry.setPublishDate(ServiceTestUtil.nextDate());

		newAssetEntry.setExpirationDate(ServiceTestUtil.nextDate());

		newAssetEntry.setMimeType(ServiceTestUtil.randomString());

		newAssetEntry.setTitle(ServiceTestUtil.randomString());

		newAssetEntry.setDescription(ServiceTestUtil.randomString());

		newAssetEntry.setSummary(ServiceTestUtil.randomString());

		newAssetEntry.setUrl(ServiceTestUtil.randomString());

		newAssetEntry.setLayoutUuid(ServiceTestUtil.randomString());

		newAssetEntry.setHeight(ServiceTestUtil.nextInt());

		newAssetEntry.setWidth(ServiceTestUtil.nextInt());

		newAssetEntry.setPriority(ServiceTestUtil.nextDouble());

		newAssetEntry.setViewCount(ServiceTestUtil.nextInt());

		_persistence.update(newAssetEntry);

		AssetEntry existingAssetEntry = _persistence.findByPrimaryKey(newAssetEntry.getPrimaryKey());

		Assert.assertEquals(existingAssetEntry.getEntryId(),
			newAssetEntry.getEntryId());
		Assert.assertEquals(existingAssetEntry.getGroupId(),
			newAssetEntry.getGroupId());
		Assert.assertEquals(existingAssetEntry.getCompanyId(),
			newAssetEntry.getCompanyId());
		Assert.assertEquals(existingAssetEntry.getUserId(),
			newAssetEntry.getUserId());
		Assert.assertEquals(existingAssetEntry.getUserName(),
			newAssetEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetEntry.getCreateDate()),
			Time.getShortTimestamp(newAssetEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetEntry.getModifiedDate()),
			Time.getShortTimestamp(newAssetEntry.getModifiedDate()));
		Assert.assertEquals(existingAssetEntry.getClassNameId(),
			newAssetEntry.getClassNameId());
		Assert.assertEquals(existingAssetEntry.getClassPK(),
			newAssetEntry.getClassPK());
		Assert.assertEquals(existingAssetEntry.getClassUuid(),
			newAssetEntry.getClassUuid());
		Assert.assertEquals(existingAssetEntry.getClassTypeId(),
			newAssetEntry.getClassTypeId());
		Assert.assertEquals(existingAssetEntry.getVisible(),
			newAssetEntry.getVisible());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetEntry.getStartDate()),
			Time.getShortTimestamp(newAssetEntry.getStartDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetEntry.getEndDate()),
			Time.getShortTimestamp(newAssetEntry.getEndDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetEntry.getPublishDate()),
			Time.getShortTimestamp(newAssetEntry.getPublishDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetEntry.getExpirationDate()),
			Time.getShortTimestamp(newAssetEntry.getExpirationDate()));
		Assert.assertEquals(existingAssetEntry.getMimeType(),
			newAssetEntry.getMimeType());
		Assert.assertEquals(existingAssetEntry.getTitle(),
			newAssetEntry.getTitle());
		Assert.assertEquals(existingAssetEntry.getDescription(),
			newAssetEntry.getDescription());
		Assert.assertEquals(existingAssetEntry.getSummary(),
			newAssetEntry.getSummary());
		Assert.assertEquals(existingAssetEntry.getUrl(), newAssetEntry.getUrl());
		Assert.assertEquals(existingAssetEntry.getLayoutUuid(),
			newAssetEntry.getLayoutUuid());
		Assert.assertEquals(existingAssetEntry.getHeight(),
			newAssetEntry.getHeight());
		Assert.assertEquals(existingAssetEntry.getWidth(),
			newAssetEntry.getWidth());
		AssertUtils.assertEquals(
			existingAssetEntry.getPriority(),
			newAssetEntry.getPriority());
		Assert.assertEquals(existingAssetEntry.getViewCount(),
			newAssetEntry.getViewCount());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetEntry newAssetEntry = addAssetEntry();

		AssetEntry existingAssetEntry = _persistence.findByPrimaryKey(newAssetEntry.getPrimaryKey());

		Assert.assertEquals(existingAssetEntry, newAssetEntry);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchEntryException");
		}
		catch (NoSuchEntryException nsee) {
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

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetEntry", "entryId",
			true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "classUuid", true,
			"classTypeId", true, "visible", true, "startDate", true, "endDate",
			true, "publishDate", true, "expirationDate", true, "mimeType",
			true, "title", true, "description", true, "summary", true, "url",
			true, "layoutUuid", true, "height", true, "width", true,
			"priority", true, "viewCount", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetEntry newAssetEntry = addAssetEntry();

		AssetEntry existingAssetEntry = _persistence.fetchByPrimaryKey(newAssetEntry.getPrimaryKey());

		Assert.assertEquals(existingAssetEntry, newAssetEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetEntry missingAssetEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetEntry);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new AssetEntryActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					AssetEntry assetEntry = (AssetEntry)object;

					Assert.assertNotNull(assetEntry);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetEntry newAssetEntry = addAssetEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntry.class,
				AssetEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newAssetEntry.getEntryId()));

		List<AssetEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetEntry existingAssetEntry = result.get(0);

		Assert.assertEquals(existingAssetEntry, newAssetEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntry.class,
				AssetEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				ServiceTestUtil.nextLong()));

		List<AssetEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingEntryId = result.get(0);

		Assert.assertEquals(existingEntryId, newEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntry.class,
				AssetEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		AssetEntry newAssetEntry = addAssetEntry();

		_persistence.clearCache();

		AssetEntryModelImpl existingAssetEntryModelImpl = (AssetEntryModelImpl)_persistence.findByPrimaryKey(newAssetEntry.getPrimaryKey());

		Assert.assertEquals(existingAssetEntryModelImpl.getGroupId(),
			existingAssetEntryModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingAssetEntryModelImpl.getClassUuid(),
				existingAssetEntryModelImpl.getOriginalClassUuid()));

		Assert.assertEquals(existingAssetEntryModelImpl.getClassNameId(),
			existingAssetEntryModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingAssetEntryModelImpl.getClassPK(),
			existingAssetEntryModelImpl.getOriginalClassPK());
	}

	protected AssetEntry addAssetEntry() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetEntry assetEntry = _persistence.create(pk);

		assetEntry.setGroupId(ServiceTestUtil.nextLong());

		assetEntry.setCompanyId(ServiceTestUtil.nextLong());

		assetEntry.setUserId(ServiceTestUtil.nextLong());

		assetEntry.setUserName(ServiceTestUtil.randomString());

		assetEntry.setCreateDate(ServiceTestUtil.nextDate());

		assetEntry.setModifiedDate(ServiceTestUtil.nextDate());

		assetEntry.setClassNameId(ServiceTestUtil.nextLong());

		assetEntry.setClassPK(ServiceTestUtil.nextLong());

		assetEntry.setClassUuid(ServiceTestUtil.randomString());

		assetEntry.setClassTypeId(ServiceTestUtil.nextLong());

		assetEntry.setVisible(ServiceTestUtil.randomBoolean());

		assetEntry.setStartDate(ServiceTestUtil.nextDate());

		assetEntry.setEndDate(ServiceTestUtil.nextDate());

		assetEntry.setPublishDate(ServiceTestUtil.nextDate());

		assetEntry.setExpirationDate(ServiceTestUtil.nextDate());

		assetEntry.setMimeType(ServiceTestUtil.randomString());

		assetEntry.setTitle(ServiceTestUtil.randomString());

		assetEntry.setDescription(ServiceTestUtil.randomString());

		assetEntry.setSummary(ServiceTestUtil.randomString());

		assetEntry.setUrl(ServiceTestUtil.randomString());

		assetEntry.setLayoutUuid(ServiceTestUtil.randomString());

		assetEntry.setHeight(ServiceTestUtil.nextInt());

		assetEntry.setWidth(ServiceTestUtil.nextInt());

		assetEntry.setPriority(ServiceTestUtil.nextDouble());

		assetEntry.setViewCount(ServiceTestUtil.nextInt());

		_persistence.update(assetEntry);

		return assetEntry;
	}

	private static Log _log = LogFactoryUtil.getLog(AssetEntryPersistenceTest.class);
	private AssetEntryPersistence _persistence = (AssetEntryPersistence)PortalBeanLocatorUtil.locate(AssetEntryPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}