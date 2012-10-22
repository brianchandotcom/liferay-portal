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

package com.liferay.portlet.softwarecatalog.service.persistence;

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

import com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl;

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
public class SCProductEntryPersistenceTest {
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

		SCProductEntry scProductEntry = _persistence.create(pk);

		Assert.assertNotNull(scProductEntry);

		Assert.assertEquals(scProductEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		_persistence.remove(newSCProductEntry);

		SCProductEntry existingSCProductEntry = _persistence.fetchByPrimaryKey(newSCProductEntry.getPrimaryKey());

		Assert.assertNull(existingSCProductEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSCProductEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SCProductEntry newSCProductEntry = _persistence.create(pk);

		newSCProductEntry.setGroupId(ServiceTestUtil.nextLong());

		newSCProductEntry.setCompanyId(ServiceTestUtil.nextLong());

		newSCProductEntry.setUserId(ServiceTestUtil.nextLong());

		newSCProductEntry.setUserName(ServiceTestUtil.randomString());

		newSCProductEntry.setCreateDate(ServiceTestUtil.nextDate());

		newSCProductEntry.setModifiedDate(ServiceTestUtil.nextDate());

		newSCProductEntry.setName(ServiceTestUtil.randomString());

		newSCProductEntry.setType(ServiceTestUtil.randomString());

		newSCProductEntry.setTags(ServiceTestUtil.randomString());

		newSCProductEntry.setShortDescription(ServiceTestUtil.randomString());

		newSCProductEntry.setLongDescription(ServiceTestUtil.randomString());

		newSCProductEntry.setPageURL(ServiceTestUtil.randomString());

		newSCProductEntry.setAuthor(ServiceTestUtil.randomString());

		newSCProductEntry.setRepoGroupId(ServiceTestUtil.randomString());

		newSCProductEntry.setRepoArtifactId(ServiceTestUtil.randomString());

		_persistence.update(newSCProductEntry);

		SCProductEntry existingSCProductEntry = _persistence.findByPrimaryKey(newSCProductEntry.getPrimaryKey());

		Assert.assertEquals(existingSCProductEntry.getProductEntryId(),
			newSCProductEntry.getProductEntryId());
		Assert.assertEquals(existingSCProductEntry.getGroupId(),
			newSCProductEntry.getGroupId());
		Assert.assertEquals(existingSCProductEntry.getCompanyId(),
			newSCProductEntry.getCompanyId());
		Assert.assertEquals(existingSCProductEntry.getUserId(),
			newSCProductEntry.getUserId());
		Assert.assertEquals(existingSCProductEntry.getUserName(),
			newSCProductEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingSCProductEntry.getCreateDate()),
			Time.getShortTimestamp(newSCProductEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingSCProductEntry.getModifiedDate()),
			Time.getShortTimestamp(newSCProductEntry.getModifiedDate()));
		Assert.assertEquals(existingSCProductEntry.getName(),
			newSCProductEntry.getName());
		Assert.assertEquals(existingSCProductEntry.getType(),
			newSCProductEntry.getType());
		Assert.assertEquals(existingSCProductEntry.getTags(),
			newSCProductEntry.getTags());
		Assert.assertEquals(existingSCProductEntry.getShortDescription(),
			newSCProductEntry.getShortDescription());
		Assert.assertEquals(existingSCProductEntry.getLongDescription(),
			newSCProductEntry.getLongDescription());
		Assert.assertEquals(existingSCProductEntry.getPageURL(),
			newSCProductEntry.getPageURL());
		Assert.assertEquals(existingSCProductEntry.getAuthor(),
			newSCProductEntry.getAuthor());
		Assert.assertEquals(existingSCProductEntry.getRepoGroupId(),
			newSCProductEntry.getRepoGroupId());
		Assert.assertEquals(existingSCProductEntry.getRepoArtifactId(),
			newSCProductEntry.getRepoArtifactId());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		SCProductEntry existingSCProductEntry = _persistence.findByPrimaryKey(newSCProductEntry.getPrimaryKey());

		Assert.assertEquals(existingSCProductEntry, newSCProductEntry);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchProductEntryException");
		}
		catch (NoSuchProductEntryException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		SCProductEntry existingSCProductEntry = _persistence.fetchByPrimaryKey(newSCProductEntry.getPrimaryKey());

		Assert.assertEquals(existingSCProductEntry, newSCProductEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SCProductEntry missingSCProductEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSCProductEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductEntry.class,
				SCProductEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("productEntryId",
				newSCProductEntry.getProductEntryId()));

		List<SCProductEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SCProductEntry existingSCProductEntry = result.get(0);

		Assert.assertEquals(existingSCProductEntry, newSCProductEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductEntry.class,
				SCProductEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("productEntryId",
				ServiceTestUtil.nextLong()));

		List<SCProductEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductEntry.class,
				SCProductEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"productEntryId"));

		Object newProductEntryId = newSCProductEntry.getProductEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("productEntryId",
				new Object[] { newProductEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingProductEntryId = result.get(0);

		Assert.assertEquals(existingProductEntryId, newProductEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductEntry.class,
				SCProductEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"productEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("productEntryId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SCProductEntry newSCProductEntry = addSCProductEntry();

		_persistence.clearCache();

		SCProductEntryModelImpl existingSCProductEntryModelImpl = (SCProductEntryModelImpl)_persistence.findByPrimaryKey(newSCProductEntry.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingSCProductEntryModelImpl.getRepoGroupId(),
				existingSCProductEntryModelImpl.getOriginalRepoGroupId()));
		Assert.assertTrue(Validator.equals(
				existingSCProductEntryModelImpl.getRepoArtifactId(),
				existingSCProductEntryModelImpl.getOriginalRepoArtifactId()));
	}

	protected SCProductEntry addSCProductEntry() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SCProductEntry scProductEntry = _persistence.create(pk);

		scProductEntry.setGroupId(ServiceTestUtil.nextLong());

		scProductEntry.setCompanyId(ServiceTestUtil.nextLong());

		scProductEntry.setUserId(ServiceTestUtil.nextLong());

		scProductEntry.setUserName(ServiceTestUtil.randomString());

		scProductEntry.setCreateDate(ServiceTestUtil.nextDate());

		scProductEntry.setModifiedDate(ServiceTestUtil.nextDate());

		scProductEntry.setName(ServiceTestUtil.randomString());

		scProductEntry.setType(ServiceTestUtil.randomString());

		scProductEntry.setTags(ServiceTestUtil.randomString());

		scProductEntry.setShortDescription(ServiceTestUtil.randomString());

		scProductEntry.setLongDescription(ServiceTestUtil.randomString());

		scProductEntry.setPageURL(ServiceTestUtil.randomString());

		scProductEntry.setAuthor(ServiceTestUtil.randomString());

		scProductEntry.setRepoGroupId(ServiceTestUtil.randomString());

		scProductEntry.setRepoArtifactId(ServiceTestUtil.randomString());

		_persistence.update(scProductEntry);

		return scProductEntry;
	}

	private static Log _log = LogFactoryUtil.getLog(SCProductEntryPersistenceTest.class);
	private SCProductEntryPersistence _persistence = (SCProductEntryPersistence)PortalBeanLocatorUtil.locate(SCProductEntryPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}