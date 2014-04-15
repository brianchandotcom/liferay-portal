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

import com.liferay.portlet.asset.NoSuchVocabularyException;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.model.impl.AssetVocabularyModelImpl;

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
public class AssetVocabularyPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<AssetVocabulary> modelListener : _modelListeners) {
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

		for (ModelListener<AssetVocabulary> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetVocabulary assetVocabulary = _persistence.create(pk);

		Assert.assertNotNull(assetVocabulary);

		Assert.assertEquals(assetVocabulary.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		_persistence.remove(newAssetVocabulary);

		AssetVocabulary existingAssetVocabulary = _persistence.fetchByPrimaryKey(newAssetVocabulary.getPrimaryKey());

		Assert.assertNull(existingAssetVocabulary);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetVocabulary();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetVocabulary newAssetVocabulary = _persistence.create(pk);

		newAssetVocabulary.setUuid(ServiceTestUtil.randomString());

		newAssetVocabulary.setGroupId(ServiceTestUtil.nextLong());

		newAssetVocabulary.setCompanyId(ServiceTestUtil.nextLong());

		newAssetVocabulary.setUserId(ServiceTestUtil.nextLong());

		newAssetVocabulary.setUserName(ServiceTestUtil.randomString());

		newAssetVocabulary.setCreateDate(ServiceTestUtil.nextDate());

		newAssetVocabulary.setModifiedDate(ServiceTestUtil.nextDate());

		newAssetVocabulary.setName(ServiceTestUtil.randomString());

		newAssetVocabulary.setTitle(ServiceTestUtil.randomString());

		newAssetVocabulary.setDescription(ServiceTestUtil.randomString());

		newAssetVocabulary.setSettings(ServiceTestUtil.randomString());

		_persistence.update(newAssetVocabulary);

		AssetVocabulary existingAssetVocabulary = _persistence.findByPrimaryKey(newAssetVocabulary.getPrimaryKey());

		Assert.assertEquals(existingAssetVocabulary.getUuid(),
			newAssetVocabulary.getUuid());
		Assert.assertEquals(existingAssetVocabulary.getVocabularyId(),
			newAssetVocabulary.getVocabularyId());
		Assert.assertEquals(existingAssetVocabulary.getGroupId(),
			newAssetVocabulary.getGroupId());
		Assert.assertEquals(existingAssetVocabulary.getCompanyId(),
			newAssetVocabulary.getCompanyId());
		Assert.assertEquals(existingAssetVocabulary.getUserId(),
			newAssetVocabulary.getUserId());
		Assert.assertEquals(existingAssetVocabulary.getUserName(),
			newAssetVocabulary.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetVocabulary.getCreateDate()),
			Time.getShortTimestamp(newAssetVocabulary.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetVocabulary.getModifiedDate()),
			Time.getShortTimestamp(newAssetVocabulary.getModifiedDate()));
		Assert.assertEquals(existingAssetVocabulary.getName(),
			newAssetVocabulary.getName());
		Assert.assertEquals(existingAssetVocabulary.getTitle(),
			newAssetVocabulary.getTitle());
		Assert.assertEquals(existingAssetVocabulary.getDescription(),
			newAssetVocabulary.getDescription());
		Assert.assertEquals(existingAssetVocabulary.getSettings(),
			newAssetVocabulary.getSettings());
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
	public void testCountByGroupIdArrayable() {
		try {
			_persistence.countByGroupId(new long[] {
					ServiceTestUtil.nextLong(), 0L
				});
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(ServiceTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_N() {
		try {
			_persistence.countByG_N(ServiceTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_N(0L, StringPool.NULL);

			_persistence.countByG_N(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_LikeN() {
		try {
			_persistence.countByG_LikeN(ServiceTestUtil.nextLong(),
				StringPool.BLANK);

			_persistence.countByG_LikeN(0L, StringPool.NULL);

			_persistence.countByG_LikeN(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		AssetVocabulary existingAssetVocabulary = _persistence.findByPrimaryKey(newAssetVocabulary.getPrimaryKey());

		Assert.assertEquals(existingAssetVocabulary, newAssetVocabulary);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchVocabularyException");
		}
		catch (NoSuchVocabularyException nsee) {
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
		return OrderByComparatorFactoryUtil.create("AssetVocabulary", "uuid",
			true, "vocabularyId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "title", true, "description",
			true, "settings", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		AssetVocabulary existingAssetVocabulary = _persistence.fetchByPrimaryKey(newAssetVocabulary.getPrimaryKey());

		Assert.assertEquals(existingAssetVocabulary, newAssetVocabulary);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetVocabulary missingAssetVocabulary = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetVocabulary);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new AssetVocabularyActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					AssetVocabulary assetVocabulary = (AssetVocabulary)object;

					Assert.assertNotNull(assetVocabulary);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetVocabulary.class,
				AssetVocabulary.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("vocabularyId",
				newAssetVocabulary.getVocabularyId()));

		List<AssetVocabulary> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetVocabulary existingAssetVocabulary = result.get(0);

		Assert.assertEquals(existingAssetVocabulary, newAssetVocabulary);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetVocabulary.class,
				AssetVocabulary.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("vocabularyId",
				ServiceTestUtil.nextLong()));

		List<AssetVocabulary> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingVocabularyId = result.get(0);

		Assert.assertEquals(existingVocabularyId, newVocabularyId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetVocabulary.class,
				AssetVocabulary.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"vocabularyId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("vocabularyId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		AssetVocabulary newAssetVocabulary = addAssetVocabulary();

		_persistence.clearCache();

		AssetVocabularyModelImpl existingAssetVocabularyModelImpl = (AssetVocabularyModelImpl)_persistence.findByPrimaryKey(newAssetVocabulary.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingAssetVocabularyModelImpl.getUuid(),
				existingAssetVocabularyModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingAssetVocabularyModelImpl.getGroupId(),
			existingAssetVocabularyModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingAssetVocabularyModelImpl.getGroupId(),
			existingAssetVocabularyModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingAssetVocabularyModelImpl.getName(),
				existingAssetVocabularyModelImpl.getOriginalName()));
	}

	protected AssetVocabulary addAssetVocabulary() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetVocabulary assetVocabulary = _persistence.create(pk);

		assetVocabulary.setUuid(ServiceTestUtil.randomString());

		assetVocabulary.setGroupId(ServiceTestUtil.nextLong());

		assetVocabulary.setCompanyId(ServiceTestUtil.nextLong());

		assetVocabulary.setUserId(ServiceTestUtil.nextLong());

		assetVocabulary.setUserName(ServiceTestUtil.randomString());

		assetVocabulary.setCreateDate(ServiceTestUtil.nextDate());

		assetVocabulary.setModifiedDate(ServiceTestUtil.nextDate());

		assetVocabulary.setName(ServiceTestUtil.randomString());

		assetVocabulary.setTitle(ServiceTestUtil.randomString());

		assetVocabulary.setDescription(ServiceTestUtil.randomString());

		assetVocabulary.setSettings(ServiceTestUtil.randomString());

		_persistence.update(assetVocabulary);

		return assetVocabulary;
	}

	private static Log _log = LogFactoryUtil.getLog(AssetVocabularyPersistenceTest.class);
	private ModelListener<AssetVocabulary>[] _modelListeners;
	private AssetVocabularyPersistence _persistence = (AssetVocabularyPersistence)PortalBeanLocatorUtil.locate(AssetVocabularyPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}