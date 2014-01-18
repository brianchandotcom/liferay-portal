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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchReleaseException;
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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Release;
import com.liferay.portal.model.impl.ReleaseModelImpl;
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
public class ReleasePersistenceTest {
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

		Release release = _persistence.create(pk);

		Assert.assertNotNull(release);

		Assert.assertEquals(release.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Release newRelease = addRelease();

		_persistence.remove(newRelease);

		Release existingRelease = _persistence.fetchByPrimaryKey(newRelease.getPrimaryKey());

		Assert.assertNull(existingRelease);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addRelease();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Release newRelease = _persistence.create(pk);

		newRelease.setCreateDate(ServiceTestUtil.nextDate());

		newRelease.setModifiedDate(ServiceTestUtil.nextDate());

		newRelease.setServletContextName(ServiceTestUtil.randomString());

		newRelease.setBuildNumber(ServiceTestUtil.nextInt());

		newRelease.setBuildDate(ServiceTestUtil.nextDate());

		newRelease.setVerified(ServiceTestUtil.randomBoolean());

		newRelease.setState(ServiceTestUtil.nextInt());

		newRelease.setTestString(ServiceTestUtil.randomString());

		newRelease.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newRelease);

		Release existingRelease = _persistence.findByPrimaryKey(newRelease.getPrimaryKey());

		Assert.assertEquals(existingRelease.getReleaseId(),
			newRelease.getReleaseId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingRelease.getCreateDate()),
			Time.getShortTimestamp(newRelease.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingRelease.getModifiedDate()),
			Time.getShortTimestamp(newRelease.getModifiedDate()));
		Assert.assertEquals(existingRelease.getServletContextName(),
			newRelease.getServletContextName());
		Assert.assertEquals(existingRelease.getBuildNumber(),
			newRelease.getBuildNumber());
		Assert.assertEquals(Time.getShortTimestamp(
				existingRelease.getBuildDate()),
			Time.getShortTimestamp(newRelease.getBuildDate()));
		Assert.assertEquals(existingRelease.getVerified(),
			newRelease.getVerified());
		Assert.assertEquals(existingRelease.getState(), newRelease.getState());
		Assert.assertEquals(existingRelease.getTestString(),
			newRelease.getTestString());
		Assert.assertEquals(existingRelease.getMvccVersion(),
			newRelease.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Release newRelease = addRelease();

		Release existingRelease = _persistence.findByPrimaryKey(newRelease.getPrimaryKey());

		Assert.assertEquals(existingRelease, newRelease);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchReleaseException");
		}
		catch (NoSuchReleaseException nsee) {
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
		return OrderByComparatorFactoryUtil.create("Release_", "releaseId",
			true, "createDate", true, "modifiedDate", true,
			"servletContextName", true, "buildNumber", true, "buildDate", true,
			"verified", true, "state", true, "testString", true, "mvccVersion",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Release newRelease = addRelease();

		Release existingRelease = _persistence.fetchByPrimaryKey(newRelease.getPrimaryKey());

		Assert.assertEquals(existingRelease, newRelease);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Release missingRelease = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingRelease);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new ReleaseActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					Release release = (Release)object;

					Assert.assertNotNull(release);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Release newRelease = addRelease();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Release.class,
				Release.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("releaseId",
				newRelease.getReleaseId()));

		List<Release> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Release existingRelease = result.get(0);

		Assert.assertEquals(existingRelease, newRelease);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Release.class,
				Release.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("releaseId",
				ServiceTestUtil.nextLong()));

		List<Release> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Release newRelease = addRelease();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Release.class,
				Release.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("releaseId"));

		Object newReleaseId = newRelease.getReleaseId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("releaseId",
				new Object[] { newReleaseId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingReleaseId = result.get(0);

		Assert.assertEquals(existingReleaseId, newReleaseId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Release.class,
				Release.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("releaseId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("releaseId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Release newRelease = addRelease();

		_persistence.clearCache();

		ReleaseModelImpl existingReleaseModelImpl = (ReleaseModelImpl)_persistence.findByPrimaryKey(newRelease.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingReleaseModelImpl.getServletContextName(),
				existingReleaseModelImpl.getOriginalServletContextName()));
	}

	protected Release addRelease() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Release release = _persistence.create(pk);

		release.setCreateDate(ServiceTestUtil.nextDate());

		release.setModifiedDate(ServiceTestUtil.nextDate());

		release.setServletContextName(ServiceTestUtil.randomString());

		release.setBuildNumber(ServiceTestUtil.nextInt());

		release.setBuildDate(ServiceTestUtil.nextDate());

		release.setVerified(ServiceTestUtil.randomBoolean());

		release.setState(ServiceTestUtil.nextInt());

		release.setTestString(ServiceTestUtil.randomString());

		release.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(release);

		return release;
	}

	private static Log _log = LogFactoryUtil.getLog(ReleasePersistenceTest.class);
	private ReleasePersistence _persistence = (ReleasePersistence)PortalBeanLocatorUtil.locate(ReleasePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}