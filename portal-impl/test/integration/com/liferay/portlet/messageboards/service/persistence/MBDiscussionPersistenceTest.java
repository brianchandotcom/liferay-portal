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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.impl.MBDiscussionModelImpl;

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
public class MBDiscussionPersistenceTest {
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

		MBDiscussion mbDiscussion = _persistence.create(pk);

		Assert.assertNotNull(mbDiscussion);

		Assert.assertEquals(mbDiscussion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MBDiscussion newMBDiscussion = addMBDiscussion();

		_persistence.remove(newMBDiscussion);

		MBDiscussion existingMBDiscussion = _persistence.fetchByPrimaryKey(newMBDiscussion.getPrimaryKey());

		Assert.assertNull(existingMBDiscussion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMBDiscussion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBDiscussion newMBDiscussion = _persistence.create(pk);

		newMBDiscussion.setClassNameId(ServiceTestUtil.nextLong());

		newMBDiscussion.setClassPK(ServiceTestUtil.nextLong());

		newMBDiscussion.setThreadId(ServiceTestUtil.nextLong());

		_persistence.update(newMBDiscussion);

		MBDiscussion existingMBDiscussion = _persistence.findByPrimaryKey(newMBDiscussion.getPrimaryKey());

		Assert.assertEquals(existingMBDiscussion.getDiscussionId(),
			newMBDiscussion.getDiscussionId());
		Assert.assertEquals(existingMBDiscussion.getClassNameId(),
			newMBDiscussion.getClassNameId());
		Assert.assertEquals(existingMBDiscussion.getClassPK(),
			newMBDiscussion.getClassPK());
		Assert.assertEquals(existingMBDiscussion.getThreadId(),
			newMBDiscussion.getThreadId());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MBDiscussion newMBDiscussion = addMBDiscussion();

		MBDiscussion existingMBDiscussion = _persistence.findByPrimaryKey(newMBDiscussion.getPrimaryKey());

		Assert.assertEquals(existingMBDiscussion, newMBDiscussion);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchDiscussionException");
		}
		catch (NoSuchDiscussionException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBDiscussion newMBDiscussion = addMBDiscussion();

		MBDiscussion existingMBDiscussion = _persistence.fetchByPrimaryKey(newMBDiscussion.getPrimaryKey());

		Assert.assertEquals(existingMBDiscussion, newMBDiscussion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBDiscussion missingMBDiscussion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMBDiscussion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBDiscussion newMBDiscussion = addMBDiscussion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBDiscussion.class,
				MBDiscussion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("discussionId",
				newMBDiscussion.getDiscussionId()));

		List<MBDiscussion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MBDiscussion existingMBDiscussion = result.get(0);

		Assert.assertEquals(existingMBDiscussion, newMBDiscussion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBDiscussion.class,
				MBDiscussion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("discussionId",
				ServiceTestUtil.nextLong()));

		List<MBDiscussion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBDiscussion newMBDiscussion = addMBDiscussion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBDiscussion.class,
				MBDiscussion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"discussionId"));

		Object newDiscussionId = newMBDiscussion.getDiscussionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("discussionId",
				new Object[] { newDiscussionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingDiscussionId = result.get(0);

		Assert.assertEquals(existingDiscussionId, newDiscussionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBDiscussion.class,
				MBDiscussion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"discussionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("discussionId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MBDiscussion newMBDiscussion = addMBDiscussion();

		_persistence.clearCache();

		MBDiscussionModelImpl existingMBDiscussionModelImpl = (MBDiscussionModelImpl)_persistence.findByPrimaryKey(newMBDiscussion.getPrimaryKey());

		Assert.assertEquals(existingMBDiscussionModelImpl.getThreadId(),
			existingMBDiscussionModelImpl.getOriginalThreadId());

		Assert.assertEquals(existingMBDiscussionModelImpl.getClassNameId(),
			existingMBDiscussionModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingMBDiscussionModelImpl.getClassPK(),
			existingMBDiscussionModelImpl.getOriginalClassPK());
	}

	protected MBDiscussion addMBDiscussion() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBDiscussion mbDiscussion = _persistence.create(pk);

		mbDiscussion.setClassNameId(ServiceTestUtil.nextLong());

		mbDiscussion.setClassPK(ServiceTestUtil.nextLong());

		mbDiscussion.setThreadId(ServiceTestUtil.nextLong());

		_persistence.update(mbDiscussion);

		return mbDiscussion;
	}

	private static Log _log = LogFactoryUtil.getLog(MBDiscussionPersistenceTest.class);
	private MBDiscussionPersistence _persistence = (MBDiscussionPersistence)PortalBeanLocatorUtil.locate(MBDiscussionPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}