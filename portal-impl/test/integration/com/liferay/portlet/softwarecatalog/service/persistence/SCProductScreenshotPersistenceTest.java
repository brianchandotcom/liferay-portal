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

package com.liferay.portlet.softwarecatalog.service.persistence;

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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;
import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductScreenshotModelImpl;
import com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalServiceUtil;

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
public class SCProductScreenshotPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<SCProductScreenshot> modelListener : _modelListeners) {
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

		for (ModelListener<SCProductScreenshot> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCProductScreenshot scProductScreenshot = _persistence.create(pk);

		Assert.assertNotNull(scProductScreenshot);

		Assert.assertEquals(scProductScreenshot.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		_persistence.remove(newSCProductScreenshot);

		SCProductScreenshot existingSCProductScreenshot = _persistence.fetchByPrimaryKey(newSCProductScreenshot.getPrimaryKey());

		Assert.assertNull(existingSCProductScreenshot);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSCProductScreenshot();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCProductScreenshot newSCProductScreenshot = _persistence.create(pk);

		newSCProductScreenshot.setCompanyId(RandomTestUtil.nextLong());

		newSCProductScreenshot.setGroupId(RandomTestUtil.nextLong());

		newSCProductScreenshot.setProductEntryId(RandomTestUtil.nextLong());

		newSCProductScreenshot.setThumbnailId(RandomTestUtil.nextLong());

		newSCProductScreenshot.setFullImageId(RandomTestUtil.nextLong());

		newSCProductScreenshot.setPriority(RandomTestUtil.nextInt());

		_persistence.update(newSCProductScreenshot);

		SCProductScreenshot existingSCProductScreenshot = _persistence.findByPrimaryKey(newSCProductScreenshot.getPrimaryKey());

		Assert.assertEquals(existingSCProductScreenshot.getProductScreenshotId(),
			newSCProductScreenshot.getProductScreenshotId());
		Assert.assertEquals(existingSCProductScreenshot.getCompanyId(),
			newSCProductScreenshot.getCompanyId());
		Assert.assertEquals(existingSCProductScreenshot.getGroupId(),
			newSCProductScreenshot.getGroupId());
		Assert.assertEquals(existingSCProductScreenshot.getProductEntryId(),
			newSCProductScreenshot.getProductEntryId());
		Assert.assertEquals(existingSCProductScreenshot.getThumbnailId(),
			newSCProductScreenshot.getThumbnailId());
		Assert.assertEquals(existingSCProductScreenshot.getFullImageId(),
			newSCProductScreenshot.getFullImageId());
		Assert.assertEquals(existingSCProductScreenshot.getPriority(),
			newSCProductScreenshot.getPriority());
	}

	@Test
	public void testCountByProductEntryId() {
		try {
			_persistence.countByProductEntryId(RandomTestUtil.nextLong());

			_persistence.countByProductEntryId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByThumbnailId() {
		try {
			_persistence.countByThumbnailId(RandomTestUtil.nextLong());

			_persistence.countByThumbnailId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByFullImageId() {
		try {
			_persistence.countByFullImageId(RandomTestUtil.nextLong());

			_persistence.countByFullImageId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByP_P() {
		try {
			_persistence.countByP_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByP_P(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		SCProductScreenshot existingSCProductScreenshot = _persistence.findByPrimaryKey(newSCProductScreenshot.getPrimaryKey());

		Assert.assertEquals(existingSCProductScreenshot, newSCProductScreenshot);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchProductScreenshotException");
		}
		catch (NoSuchProductScreenshotException nsee) {
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
		return OrderByComparatorFactoryUtil.create("SCProductScreenshot",
			"productScreenshotId", true, "companyId", true, "groupId", true,
			"productEntryId", true, "thumbnailId", true, "fullImageId", true,
			"priority", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		SCProductScreenshot existingSCProductScreenshot = _persistence.fetchByPrimaryKey(newSCProductScreenshot.getPrimaryKey());

		Assert.assertEquals(existingSCProductScreenshot, newSCProductScreenshot);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCProductScreenshot missingSCProductScreenshot = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSCProductScreenshot);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = SCProductScreenshotLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					SCProductScreenshot scProductScreenshot = (SCProductScreenshot)object;

					Assert.assertNotNull(scProductScreenshot);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductScreenshot.class,
				SCProductScreenshot.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("productScreenshotId",
				newSCProductScreenshot.getProductScreenshotId()));

		List<SCProductScreenshot> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SCProductScreenshot existingSCProductScreenshot = result.get(0);

		Assert.assertEquals(existingSCProductScreenshot, newSCProductScreenshot);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductScreenshot.class,
				SCProductScreenshot.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("productScreenshotId",
				RandomTestUtil.nextLong()));

		List<SCProductScreenshot> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductScreenshot.class,
				SCProductScreenshot.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"productScreenshotId"));

		Object newProductScreenshotId = newSCProductScreenshot.getProductScreenshotId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("productScreenshotId",
				new Object[] { newProductScreenshotId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingProductScreenshotId = result.get(0);

		Assert.assertEquals(existingProductScreenshotId, newProductScreenshotId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductScreenshot.class,
				SCProductScreenshot.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"productScreenshotId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("productScreenshotId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		_persistence.clearCache();

		SCProductScreenshotModelImpl existingSCProductScreenshotModelImpl = (SCProductScreenshotModelImpl)_persistence.findByPrimaryKey(newSCProductScreenshot.getPrimaryKey());

		Assert.assertEquals(existingSCProductScreenshotModelImpl.getThumbnailId(),
			existingSCProductScreenshotModelImpl.getOriginalThumbnailId());

		Assert.assertEquals(existingSCProductScreenshotModelImpl.getFullImageId(),
			existingSCProductScreenshotModelImpl.getOriginalFullImageId());

		Assert.assertEquals(existingSCProductScreenshotModelImpl.getProductEntryId(),
			existingSCProductScreenshotModelImpl.getOriginalProductEntryId());
		Assert.assertEquals(existingSCProductScreenshotModelImpl.getPriority(),
			existingSCProductScreenshotModelImpl.getOriginalPriority());
	}

	protected SCProductScreenshot addSCProductScreenshot()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCProductScreenshot scProductScreenshot = _persistence.create(pk);

		scProductScreenshot.setCompanyId(RandomTestUtil.nextLong());

		scProductScreenshot.setGroupId(RandomTestUtil.nextLong());

		scProductScreenshot.setProductEntryId(RandomTestUtil.nextLong());

		scProductScreenshot.setThumbnailId(RandomTestUtil.nextLong());

		scProductScreenshot.setFullImageId(RandomTestUtil.nextLong());

		scProductScreenshot.setPriority(RandomTestUtil.nextInt());

		_persistence.update(scProductScreenshot);

		return scProductScreenshot;
	}

	private static Log _log = LogFactoryUtil.getLog(SCProductScreenshotPersistenceTest.class);
	private ModelListener<SCProductScreenshot>[] _modelListeners;
	private SCProductScreenshotPersistence _persistence = (SCProductScreenshotPersistence)PortalBeanLocatorUtil.locate(SCProductScreenshotPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}