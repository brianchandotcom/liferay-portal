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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.social.NoSuchActivityLimitException;
import com.liferay.portlet.social.model.SocialActivityLimit;
import com.liferay.portlet.social.model.impl.SocialActivityLimitModelImpl;

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
public class SocialActivityLimitPersistenceTest {
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

		SocialActivityLimit socialActivityLimit = _persistence.create(pk);

		Assert.assertNotNull(socialActivityLimit);

		Assert.assertEquals(socialActivityLimit.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		_persistence.remove(newSocialActivityLimit);

		SocialActivityLimit existingSocialActivityLimit = _persistence.fetchByPrimaryKey(newSocialActivityLimit.getPrimaryKey());

		Assert.assertNull(existingSocialActivityLimit);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSocialActivityLimit();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivityLimit newSocialActivityLimit = _persistence.create(pk);

		newSocialActivityLimit.setGroupId(ServiceTestUtil.nextLong());

		newSocialActivityLimit.setCompanyId(ServiceTestUtil.nextLong());

		newSocialActivityLimit.setUserId(ServiceTestUtil.nextLong());

		newSocialActivityLimit.setClassNameId(ServiceTestUtil.nextLong());

		newSocialActivityLimit.setClassPK(ServiceTestUtil.nextLong());

		newSocialActivityLimit.setActivityType(ServiceTestUtil.nextInt());

		newSocialActivityLimit.setActivityCounterName(ServiceTestUtil.randomString());

		newSocialActivityLimit.setValue(ServiceTestUtil.randomString());

		_persistence.update(newSocialActivityLimit);

		SocialActivityLimit existingSocialActivityLimit = _persistence.findByPrimaryKey(newSocialActivityLimit.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityLimit.getActivityLimitId(),
			newSocialActivityLimit.getActivityLimitId());
		Assert.assertEquals(existingSocialActivityLimit.getGroupId(),
			newSocialActivityLimit.getGroupId());
		Assert.assertEquals(existingSocialActivityLimit.getCompanyId(),
			newSocialActivityLimit.getCompanyId());
		Assert.assertEquals(existingSocialActivityLimit.getUserId(),
			newSocialActivityLimit.getUserId());
		Assert.assertEquals(existingSocialActivityLimit.getClassNameId(),
			newSocialActivityLimit.getClassNameId());
		Assert.assertEquals(existingSocialActivityLimit.getClassPK(),
			newSocialActivityLimit.getClassPK());
		Assert.assertEquals(existingSocialActivityLimit.getActivityType(),
			newSocialActivityLimit.getActivityType());
		Assert.assertEquals(existingSocialActivityLimit.getActivityCounterName(),
			newSocialActivityLimit.getActivityCounterName());
		Assert.assertEquals(existingSocialActivityLimit.getValue(),
			newSocialActivityLimit.getValue());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		SocialActivityLimit existingSocialActivityLimit = _persistence.findByPrimaryKey(newSocialActivityLimit.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityLimit, newSocialActivityLimit);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchActivityLimitException");
		}
		catch (NoSuchActivityLimitException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		SocialActivityLimit existingSocialActivityLimit = _persistence.fetchByPrimaryKey(newSocialActivityLimit.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityLimit, newSocialActivityLimit);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivityLimit missingSocialActivityLimit = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSocialActivityLimit);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityLimit.class,
				SocialActivityLimit.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityLimitId",
				newSocialActivityLimit.getActivityLimitId()));

		List<SocialActivityLimit> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SocialActivityLimit existingSocialActivityLimit = result.get(0);

		Assert.assertEquals(existingSocialActivityLimit, newSocialActivityLimit);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityLimit.class,
				SocialActivityLimit.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityLimitId",
				ServiceTestUtil.nextLong()));

		List<SocialActivityLimit> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityLimit.class,
				SocialActivityLimit.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activityLimitId"));

		Object newActivityLimitId = newSocialActivityLimit.getActivityLimitId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityLimitId",
				new Object[] { newActivityLimitId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingActivityLimitId = result.get(0);

		Assert.assertEquals(existingActivityLimitId, newActivityLimitId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityLimit.class,
				SocialActivityLimit.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activityLimitId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityLimitId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		_persistence.clearCache();

		SocialActivityLimitModelImpl existingSocialActivityLimitModelImpl = (SocialActivityLimitModelImpl)_persistence.findByPrimaryKey(newSocialActivityLimit.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityLimitModelImpl.getGroupId(),
			existingSocialActivityLimitModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingSocialActivityLimitModelImpl.getUserId(),
			existingSocialActivityLimitModelImpl.getOriginalUserId());
		Assert.assertEquals(existingSocialActivityLimitModelImpl.getClassNameId(),
			existingSocialActivityLimitModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingSocialActivityLimitModelImpl.getClassPK(),
			existingSocialActivityLimitModelImpl.getOriginalClassPK());
		Assert.assertEquals(existingSocialActivityLimitModelImpl.getActivityType(),
			existingSocialActivityLimitModelImpl.getOriginalActivityType());
		Assert.assertTrue(Validator.equals(
				existingSocialActivityLimitModelImpl.getActivityCounterName(),
				existingSocialActivityLimitModelImpl.getOriginalActivityCounterName()));
	}

	protected SocialActivityLimit addSocialActivityLimit()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivityLimit socialActivityLimit = _persistence.create(pk);

		socialActivityLimit.setGroupId(ServiceTestUtil.nextLong());

		socialActivityLimit.setCompanyId(ServiceTestUtil.nextLong());

		socialActivityLimit.setUserId(ServiceTestUtil.nextLong());

		socialActivityLimit.setClassNameId(ServiceTestUtil.nextLong());

		socialActivityLimit.setClassPK(ServiceTestUtil.nextLong());

		socialActivityLimit.setActivityType(ServiceTestUtil.nextInt());

		socialActivityLimit.setActivityCounterName(ServiceTestUtil.randomString());

		socialActivityLimit.setValue(ServiceTestUtil.randomString());

		_persistence.update(socialActivityLimit);

		return socialActivityLimit;
	}

	private static Log _log = LogFactoryUtil.getLog(SocialActivityLimitPersistenceTest.class);
	private SocialActivityLimitPersistence _persistence = (SocialActivityLimitPersistence)PortalBeanLocatorUtil.locate(SocialActivityLimitPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}