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

package com.liferay.portlet.trash.service.persistence;

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

import com.liferay.portlet.trash.NoSuchVersionException;
import com.liferay.portlet.trash.model.TrashVersion;

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
public class TrashVersionPersistenceTest {
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

		TrashVersion trashVersion = _persistence.create(pk);

		Assert.assertNotNull(trashVersion);

		Assert.assertEquals(trashVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		_persistence.remove(newTrashVersion);

		TrashVersion existingTrashVersion = _persistence.fetchByPrimaryKey(newTrashVersion.getPrimaryKey());

		Assert.assertNull(existingTrashVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addTrashVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		TrashVersion newTrashVersion = _persistence.create(pk);

		newTrashVersion.setEntryId(ServiceTestUtil.nextLong());

		newTrashVersion.setClassNameId(ServiceTestUtil.nextLong());

		newTrashVersion.setClassPK(ServiceTestUtil.nextLong());

		newTrashVersion.setStatus(ServiceTestUtil.nextInt());

		_persistence.update(newTrashVersion);

		TrashVersion existingTrashVersion = _persistence.findByPrimaryKey(newTrashVersion.getPrimaryKey());

		Assert.assertEquals(existingTrashVersion.getVersionId(),
			newTrashVersion.getVersionId());
		Assert.assertEquals(existingTrashVersion.getEntryId(),
			newTrashVersion.getEntryId());
		Assert.assertEquals(existingTrashVersion.getClassNameId(),
			newTrashVersion.getClassNameId());
		Assert.assertEquals(existingTrashVersion.getClassPK(),
			newTrashVersion.getClassPK());
		Assert.assertEquals(existingTrashVersion.getStatus(),
			newTrashVersion.getStatus());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		TrashVersion existingTrashVersion = _persistence.findByPrimaryKey(newTrashVersion.getPrimaryKey());

		Assert.assertEquals(existingTrashVersion, newTrashVersion);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchVersionException");
		}
		catch (NoSuchVersionException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		TrashVersion existingTrashVersion = _persistence.fetchByPrimaryKey(newTrashVersion.getPrimaryKey());

		Assert.assertEquals(existingTrashVersion, newTrashVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		TrashVersion missingTrashVersion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingTrashVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashVersion.class,
				TrashVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("versionId",
				newTrashVersion.getVersionId()));

		List<TrashVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		TrashVersion existingTrashVersion = result.get(0);

		Assert.assertEquals(existingTrashVersion, newTrashVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashVersion.class,
				TrashVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("versionId",
				ServiceTestUtil.nextLong()));

		List<TrashVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashVersion.class,
				TrashVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("versionId"));

		Object newVersionId = newTrashVersion.getVersionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("versionId",
				new Object[] { newVersionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingVersionId = result.get(0);

		Assert.assertEquals(existingVersionId, newVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashVersion.class,
				TrashVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("versionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("versionId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected TrashVersion addTrashVersion() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		TrashVersion trashVersion = _persistence.create(pk);

		trashVersion.setEntryId(ServiceTestUtil.nextLong());

		trashVersion.setClassNameId(ServiceTestUtil.nextLong());

		trashVersion.setClassPK(ServiceTestUtil.nextLong());

		trashVersion.setStatus(ServiceTestUtil.nextInt());

		_persistence.update(trashVersion);

		return trashVersion;
	}

	private static Log _log = LogFactoryUtil.getLog(TrashVersionPersistenceTest.class);
	private TrashVersionPersistence _persistence = (TrashVersionPersistence)PortalBeanLocatorUtil.locate(TrashVersionPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}