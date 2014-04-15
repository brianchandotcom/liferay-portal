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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.documentlibrary.NoSuchContentException;
import com.liferay.portlet.documentlibrary.model.DLContent;
import com.liferay.portlet.documentlibrary.model.impl.DLContentModelImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.sql.Blob;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class DLContentPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<DLContent> modelListener : _modelListeners) {
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

		for (ModelListener<DLContent> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLContent dlContent = _persistence.create(pk);

		Assert.assertNotNull(dlContent);

		Assert.assertEquals(dlContent.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DLContent newDLContent = addDLContent();

		_persistence.remove(newDLContent);

		DLContent existingDLContent = _persistence.fetchByPrimaryKey(newDLContent.getPrimaryKey());

		Assert.assertNull(existingDLContent);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDLContent();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLContent newDLContent = _persistence.create(pk);

		newDLContent.setGroupId(ServiceTestUtil.nextLong());

		newDLContent.setCompanyId(ServiceTestUtil.nextLong());

		newDLContent.setRepositoryId(ServiceTestUtil.nextLong());

		newDLContent.setPath(ServiceTestUtil.randomString());

		newDLContent.setVersion(ServiceTestUtil.randomString());

		String newDataString = ServiceTestUtil.randomString();

		byte[] newDataBytes = newDataString.getBytes(StringPool.UTF8);

		Blob newDataBlob = new OutputBlob(new UnsyncByteArrayInputStream(
					newDataBytes), newDataBytes.length);

		newDLContent.setData(newDataBlob);

		newDLContent.setSize(ServiceTestUtil.nextLong());

		_persistence.update(newDLContent);

		DLContent existingDLContent = _persistence.findByPrimaryKey(newDLContent.getPrimaryKey());

		Assert.assertEquals(existingDLContent.getContentId(),
			newDLContent.getContentId());
		Assert.assertEquals(existingDLContent.getGroupId(),
			newDLContent.getGroupId());
		Assert.assertEquals(existingDLContent.getCompanyId(),
			newDLContent.getCompanyId());
		Assert.assertEquals(existingDLContent.getRepositoryId(),
			newDLContent.getRepositoryId());
		Assert.assertEquals(existingDLContent.getPath(), newDLContent.getPath());
		Assert.assertEquals(existingDLContent.getVersion(),
			newDLContent.getVersion());

		Blob existingData = existingDLContent.getData();

		Assert.assertTrue(Arrays.equals(existingData.getBytes(1,
					(int)existingData.length()), newDataBytes));
		Assert.assertEquals(existingDLContent.getSize(), newDLContent.getSize());
	}

	@Test
	public void testCountByC_R() {
		try {
			_persistence.countByC_R(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong());

			_persistence.countByC_R(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_R_P() {
		try {
			_persistence.countByC_R_P(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByC_R_P(0L, 0L, StringPool.NULL);

			_persistence.countByC_R_P(0L, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_R_LikeP() {
		try {
			_persistence.countByC_R_LikeP(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByC_R_LikeP(0L, 0L, StringPool.NULL);

			_persistence.countByC_R_LikeP(0L, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_R_P_V() {
		try {
			_persistence.countByC_R_P_V(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), StringPool.BLANK, StringPool.BLANK);

			_persistence.countByC_R_P_V(0L, 0L, StringPool.NULL, StringPool.NULL);

			_persistence.countByC_R_P_V(0L, 0L, (String)null, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DLContent newDLContent = addDLContent();

		DLContent existingDLContent = _persistence.findByPrimaryKey(newDLContent.getPrimaryKey());

		Assert.assertEquals(existingDLContent, newDLContent);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchContentException");
		}
		catch (NoSuchContentException nsee) {
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
		return OrderByComparatorFactoryUtil.create("DLContent", "contentId",
			true, "groupId", true, "companyId", true, "repositoryId", true,
			"path", true, "version", true, "size", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLContent newDLContent = addDLContent();

		DLContent existingDLContent = _persistence.fetchByPrimaryKey(newDLContent.getPrimaryKey());

		Assert.assertEquals(existingDLContent, newDLContent);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLContent missingDLContent = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDLContent);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new DLContentActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					DLContent dlContent = (DLContent)object;

					Assert.assertNotNull(dlContent);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLContent newDLContent = addDLContent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLContent.class,
				DLContent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentId",
				newDLContent.getContentId()));

		List<DLContent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DLContent existingDLContent = result.get(0);

		Assert.assertEquals(existingDLContent, newDLContent);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLContent.class,
				DLContent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentId",
				ServiceTestUtil.nextLong()));

		List<DLContent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLContent newDLContent = addDLContent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLContent.class,
				DLContent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("contentId"));

		Object newContentId = newDLContent.getContentId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("contentId",
				new Object[] { newContentId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingContentId = result.get(0);

		Assert.assertEquals(existingContentId, newContentId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLContent.class,
				DLContent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("contentId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("contentId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLContent newDLContent = addDLContent();

		_persistence.clearCache();

		DLContentModelImpl existingDLContentModelImpl = (DLContentModelImpl)_persistence.findByPrimaryKey(newDLContent.getPrimaryKey());

		Assert.assertEquals(existingDLContentModelImpl.getCompanyId(),
			existingDLContentModelImpl.getOriginalCompanyId());
		Assert.assertEquals(existingDLContentModelImpl.getRepositoryId(),
			existingDLContentModelImpl.getOriginalRepositoryId());
		Assert.assertTrue(Validator.equals(
				existingDLContentModelImpl.getPath(),
				existingDLContentModelImpl.getOriginalPath()));
		Assert.assertTrue(Validator.equals(
				existingDLContentModelImpl.getVersion(),
				existingDLContentModelImpl.getOriginalVersion()));
	}

	protected DLContent addDLContent() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLContent dlContent = _persistence.create(pk);

		dlContent.setGroupId(ServiceTestUtil.nextLong());

		dlContent.setCompanyId(ServiceTestUtil.nextLong());

		dlContent.setRepositoryId(ServiceTestUtil.nextLong());

		dlContent.setPath(ServiceTestUtil.randomString());

		dlContent.setVersion(ServiceTestUtil.randomString());

		String dataString = ServiceTestUtil.randomString();

		byte[] dataBytes = dataString.getBytes(StringPool.UTF8);

		Blob dataBlob = new OutputBlob(new UnsyncByteArrayInputStream(dataBytes),
				dataBytes.length);

		dlContent.setData(dataBlob);

		dlContent.setSize(ServiceTestUtil.nextLong());

		_persistence.update(dlContent);

		return dlContent;
	}

	private static Log _log = LogFactoryUtil.getLog(DLContentPersistenceTest.class);
	private ModelListener<DLContent>[] _modelListeners;
	private DLContentPersistence _persistence = (DLContentPersistence)PortalBeanLocatorUtil.locate(DLContentPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}