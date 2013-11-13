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

package com.liferay.portlet.social.service.persistence;

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
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.social.NoSuchActivityException;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.impl.SocialActivityModelImpl;

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
public class SocialActivityPersistenceTest {
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

		SocialActivity socialActivity = _persistence.create(pk);

		Assert.assertNotNull(socialActivity);

		Assert.assertEquals(socialActivity.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SocialActivity newSocialActivity = addSocialActivity();

		_persistence.remove(newSocialActivity);

		SocialActivity existingSocialActivity = _persistence.fetchByPrimaryKey(newSocialActivity.getPrimaryKey());

		Assert.assertNull(existingSocialActivity);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSocialActivity();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivity newSocialActivity = _persistence.create(pk);

		newSocialActivity.setGroupId(ServiceTestUtil.nextLong());

		newSocialActivity.setCompanyId(ServiceTestUtil.nextLong());

		newSocialActivity.setUserId(ServiceTestUtil.nextLong());

		newSocialActivity.setCreateDate(ServiceTestUtil.nextLong());

		newSocialActivity.setActivitySetId(ServiceTestUtil.nextLong());

		newSocialActivity.setMirrorActivityId(ServiceTestUtil.nextLong());

		newSocialActivity.setClassNameId(ServiceTestUtil.nextLong());

		newSocialActivity.setClassPK(ServiceTestUtil.nextLong());

		newSocialActivity.setParentClassNameId(ServiceTestUtil.nextLong());

		newSocialActivity.setParentClassPK(ServiceTestUtil.nextLong());

		newSocialActivity.setType(ServiceTestUtil.nextInt());

		newSocialActivity.setExtraData(ServiceTestUtil.randomString());

		newSocialActivity.setReceiverUserId(ServiceTestUtil.nextLong());

		_persistence.update(newSocialActivity);

		SocialActivity existingSocialActivity = _persistence.findByPrimaryKey(newSocialActivity.getPrimaryKey());

		Assert.assertEquals(existingSocialActivity.getActivityId(),
			newSocialActivity.getActivityId());
		Assert.assertEquals(existingSocialActivity.getGroupId(),
			newSocialActivity.getGroupId());
		Assert.assertEquals(existingSocialActivity.getCompanyId(),
			newSocialActivity.getCompanyId());
		Assert.assertEquals(existingSocialActivity.getUserId(),
			newSocialActivity.getUserId());
		Assert.assertEquals(existingSocialActivity.getCreateDate(),
			newSocialActivity.getCreateDate());
		Assert.assertEquals(existingSocialActivity.getActivitySetId(),
			newSocialActivity.getActivitySetId());
		Assert.assertEquals(existingSocialActivity.getMirrorActivityId(),
			newSocialActivity.getMirrorActivityId());
		Assert.assertEquals(existingSocialActivity.getClassNameId(),
			newSocialActivity.getClassNameId());
		Assert.assertEquals(existingSocialActivity.getClassPK(),
			newSocialActivity.getClassPK());
		Assert.assertEquals(existingSocialActivity.getParentClassNameId(),
			newSocialActivity.getParentClassNameId());
		Assert.assertEquals(existingSocialActivity.getParentClassPK(),
			newSocialActivity.getParentClassPK());
		Assert.assertEquals(existingSocialActivity.getType(),
			newSocialActivity.getType());
		Assert.assertEquals(existingSocialActivity.getExtraData(),
			newSocialActivity.getExtraData());
		Assert.assertEquals(existingSocialActivity.getReceiverUserId(),
			newSocialActivity.getReceiverUserId());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialActivity newSocialActivity = addSocialActivity();

		SocialActivity existingSocialActivity = _persistence.findByPrimaryKey(newSocialActivity.getPrimaryKey());

		Assert.assertEquals(existingSocialActivity, newSocialActivity);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchActivityException");
		}
		catch (NoSuchActivityException nsee) {
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
		return OrderByComparatorFactoryUtil.create("SocialActivity",
			"activityId", true, "groupId", true, "companyId", true, "userId",
			true, "createDate", true, "activitySetId", true,
			"mirrorActivityId", true, "classNameId", true, "classPK", true,
			"parentClassNameId", true, "parentClassPK", true, "type", true,
			"extraData", true, "receiverUserId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialActivity newSocialActivity = addSocialActivity();

		SocialActivity existingSocialActivity = _persistence.fetchByPrimaryKey(newSocialActivity.getPrimaryKey());

		Assert.assertEquals(existingSocialActivity, newSocialActivity);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivity missingSocialActivity = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSocialActivity);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new SocialActivityActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					SocialActivity socialActivity = (SocialActivity)object;

					Assert.assertNotNull(socialActivity);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialActivity newSocialActivity = addSocialActivity();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivity.class,
				SocialActivity.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityId",
				newSocialActivity.getActivityId()));

		List<SocialActivity> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SocialActivity existingSocialActivity = result.get(0);

		Assert.assertEquals(existingSocialActivity, newSocialActivity);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivity.class,
				SocialActivity.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityId",
				ServiceTestUtil.nextLong()));

		List<SocialActivity> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialActivity newSocialActivity = addSocialActivity();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivity.class,
				SocialActivity.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("activityId"));

		Object newActivityId = newSocialActivity.getActivityId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityId",
				new Object[] { newActivityId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingActivityId = result.get(0);

		Assert.assertEquals(existingActivityId, newActivityId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivity.class,
				SocialActivity.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("activityId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SocialActivity newSocialActivity = addSocialActivity();

		_persistence.clearCache();

		SocialActivityModelImpl existingSocialActivityModelImpl = (SocialActivityModelImpl)_persistence.findByPrimaryKey(newSocialActivity.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityModelImpl.getMirrorActivityId(),
			existingSocialActivityModelImpl.getOriginalMirrorActivityId());

		Assert.assertEquals(existingSocialActivityModelImpl.getGroupId(),
			existingSocialActivityModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingSocialActivityModelImpl.getUserId(),
			existingSocialActivityModelImpl.getOriginalUserId());
		Assert.assertEquals(existingSocialActivityModelImpl.getCreateDate(),
			existingSocialActivityModelImpl.getOriginalCreateDate());
		Assert.assertEquals(existingSocialActivityModelImpl.getClassNameId(),
			existingSocialActivityModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingSocialActivityModelImpl.getClassPK(),
			existingSocialActivityModelImpl.getOriginalClassPK());
		Assert.assertEquals(existingSocialActivityModelImpl.getType(),
			existingSocialActivityModelImpl.getOriginalType());
		Assert.assertEquals(existingSocialActivityModelImpl.getReceiverUserId(),
			existingSocialActivityModelImpl.getOriginalReceiverUserId());
	}

	protected SocialActivity addSocialActivity() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivity socialActivity = _persistence.create(pk);

		socialActivity.setGroupId(ServiceTestUtil.nextLong());

		socialActivity.setCompanyId(ServiceTestUtil.nextLong());

		socialActivity.setUserId(ServiceTestUtil.nextLong());

		socialActivity.setCreateDate(ServiceTestUtil.nextLong());

		socialActivity.setActivitySetId(ServiceTestUtil.nextLong());

		socialActivity.setMirrorActivityId(ServiceTestUtil.nextLong());

		socialActivity.setClassNameId(ServiceTestUtil.nextLong());

		socialActivity.setClassPK(ServiceTestUtil.nextLong());

		socialActivity.setParentClassNameId(ServiceTestUtil.nextLong());

		socialActivity.setParentClassPK(ServiceTestUtil.nextLong());

		socialActivity.setType(ServiceTestUtil.nextInt());

		socialActivity.setExtraData(ServiceTestUtil.randomString());

		socialActivity.setReceiverUserId(ServiceTestUtil.nextLong());

		_persistence.update(socialActivity);

		return socialActivity;
	}

	private static Log _log = LogFactoryUtil.getLog(SocialActivityPersistenceTest.class);
	private SocialActivityPersistence _persistence = (SocialActivityPersistence)PortalBeanLocatorUtil.locate(SocialActivityPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}