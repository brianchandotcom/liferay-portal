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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.model.impl.RepositoryEntryModelImpl;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

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
public class RepositoryEntryPersistenceTest {
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

		RepositoryEntry repositoryEntry = _persistence.create(pk);

		Assert.assertNotNull(repositoryEntry);

		Assert.assertEquals(repositoryEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		RepositoryEntry newRepositoryEntry = addRepositoryEntry();

		_persistence.remove(newRepositoryEntry);

		RepositoryEntry existingRepositoryEntry = _persistence.fetchByPrimaryKey(newRepositoryEntry.getPrimaryKey());

		Assert.assertNull(existingRepositoryEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addRepositoryEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		RepositoryEntry newRepositoryEntry = _persistence.create(pk);

		newRepositoryEntry.setUuid(ServiceTestUtil.randomString());

		newRepositoryEntry.setGroupId(ServiceTestUtil.nextLong());

		newRepositoryEntry.setRepositoryId(ServiceTestUtil.nextLong());

		newRepositoryEntry.setMappedId(ServiceTestUtil.randomString());

		newRepositoryEntry.setManualCheckInRequired(ServiceTestUtil.randomBoolean());

		_persistence.update(newRepositoryEntry);

		RepositoryEntry existingRepositoryEntry = _persistence.findByPrimaryKey(newRepositoryEntry.getPrimaryKey());

		Assert.assertEquals(existingRepositoryEntry.getUuid(),
			newRepositoryEntry.getUuid());
		Assert.assertEquals(existingRepositoryEntry.getRepositoryEntryId(),
			newRepositoryEntry.getRepositoryEntryId());
		Assert.assertEquals(existingRepositoryEntry.getGroupId(),
			newRepositoryEntry.getGroupId());
		Assert.assertEquals(existingRepositoryEntry.getRepositoryId(),
			newRepositoryEntry.getRepositoryId());
		Assert.assertEquals(existingRepositoryEntry.getMappedId(),
			newRepositoryEntry.getMappedId());
		Assert.assertEquals(existingRepositoryEntry.getManualCheckInRequired(),
			newRepositoryEntry.getManualCheckInRequired());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		RepositoryEntry newRepositoryEntry = addRepositoryEntry();

		RepositoryEntry existingRepositoryEntry = _persistence.findByPrimaryKey(newRepositoryEntry.getPrimaryKey());

		Assert.assertEquals(existingRepositoryEntry, newRepositoryEntry);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchRepositoryEntryException");
		}
		catch (NoSuchRepositoryEntryException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		RepositoryEntry newRepositoryEntry = addRepositoryEntry();

		RepositoryEntry existingRepositoryEntry = _persistence.fetchByPrimaryKey(newRepositoryEntry.getPrimaryKey());

		Assert.assertEquals(existingRepositoryEntry, newRepositoryEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		RepositoryEntry missingRepositoryEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingRepositoryEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		RepositoryEntry newRepositoryEntry = addRepositoryEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RepositoryEntry.class,
				RepositoryEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("repositoryEntryId",
				newRepositoryEntry.getRepositoryEntryId()));

		List<RepositoryEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		RepositoryEntry existingRepositoryEntry = result.get(0);

		Assert.assertEquals(existingRepositoryEntry, newRepositoryEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RepositoryEntry.class,
				RepositoryEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("repositoryEntryId",
				ServiceTestUtil.nextLong()));

		List<RepositoryEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		RepositoryEntry newRepositoryEntry = addRepositoryEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RepositoryEntry.class,
				RepositoryEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"repositoryEntryId"));

		Object newRepositoryEntryId = newRepositoryEntry.getRepositoryEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("repositoryEntryId",
				new Object[] { newRepositoryEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRepositoryEntryId = result.get(0);

		Assert.assertEquals(existingRepositoryEntryId, newRepositoryEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RepositoryEntry.class,
				RepositoryEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"repositoryEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("repositoryEntryId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		RepositoryEntry newRepositoryEntry = addRepositoryEntry();

		_persistence.clearCache();

		RepositoryEntryModelImpl existingRepositoryEntryModelImpl = (RepositoryEntryModelImpl)_persistence.findByPrimaryKey(newRepositoryEntry.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingRepositoryEntryModelImpl.getUuid(),
				existingRepositoryEntryModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingRepositoryEntryModelImpl.getGroupId(),
			existingRepositoryEntryModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingRepositoryEntryModelImpl.getRepositoryId(),
			existingRepositoryEntryModelImpl.getOriginalRepositoryId());
		Assert.assertTrue(Validator.equals(
				existingRepositoryEntryModelImpl.getMappedId(),
				existingRepositoryEntryModelImpl.getOriginalMappedId()));
	}

	protected RepositoryEntry addRepositoryEntry() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		RepositoryEntry repositoryEntry = _persistence.create(pk);

		repositoryEntry.setUuid(ServiceTestUtil.randomString());

		repositoryEntry.setGroupId(ServiceTestUtil.nextLong());

		repositoryEntry.setRepositoryId(ServiceTestUtil.nextLong());

		repositoryEntry.setMappedId(ServiceTestUtil.randomString());

		repositoryEntry.setManualCheckInRequired(ServiceTestUtil.randomBoolean());

		_persistence.update(repositoryEntry);

		return repositoryEntry;
	}

	private static Log _log = LogFactoryUtil.getLog(RepositoryEntryPersistenceTest.class);
	private RepositoryEntryPersistence _persistence = (RepositoryEntryPersistence)PortalBeanLocatorUtil.locate(RepositoryEntryPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}