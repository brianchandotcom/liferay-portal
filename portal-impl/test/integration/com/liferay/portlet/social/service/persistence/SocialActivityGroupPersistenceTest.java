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
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;

import com.liferay.portlet.social.NoSuchActivityGroupException;
import com.liferay.portlet.social.model.SocialActivityGroup;

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
public class SocialActivityGroupPersistenceTest {
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

		SocialActivityGroup socialActivityGroup = _persistence.create(pk);

		Assert.assertNotNull(socialActivityGroup);

		Assert.assertEquals(socialActivityGroup.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SocialActivityGroup newSocialActivityGroup = addSocialActivityGroup();

		_persistence.remove(newSocialActivityGroup);

		SocialActivityGroup existingSocialActivityGroup = _persistence.fetchByPrimaryKey(newSocialActivityGroup.getPrimaryKey());

		Assert.assertNull(existingSocialActivityGroup);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSocialActivityGroup();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivityGroup newSocialActivityGroup = _persistence.create(pk);

		newSocialActivityGroup.setGroupId(ServiceTestUtil.nextLong());

		newSocialActivityGroup.setCompanyId(ServiceTestUtil.nextLong());

		newSocialActivityGroup.setUserId(ServiceTestUtil.nextLong());

		newSocialActivityGroup.setCreateDate(ServiceTestUtil.nextLong());

		newSocialActivityGroup.setModifiedDate(ServiceTestUtil.nextLong());

		newSocialActivityGroup.setClassNameId(ServiceTestUtil.nextLong());

		newSocialActivityGroup.setClassPK(ServiceTestUtil.nextLong());

		newSocialActivityGroup.setType(ServiceTestUtil.nextInt());

		_persistence.update(newSocialActivityGroup);

		SocialActivityGroup existingSocialActivityGroup = _persistence.findByPrimaryKey(newSocialActivityGroup.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityGroup.getActivityGroupId(),
			newSocialActivityGroup.getActivityGroupId());
		Assert.assertEquals(existingSocialActivityGroup.getGroupId(),
			newSocialActivityGroup.getGroupId());
		Assert.assertEquals(existingSocialActivityGroup.getCompanyId(),
			newSocialActivityGroup.getCompanyId());
		Assert.assertEquals(existingSocialActivityGroup.getUserId(),
			newSocialActivityGroup.getUserId());
		Assert.assertEquals(existingSocialActivityGroup.getCreateDate(),
			newSocialActivityGroup.getCreateDate());
		Assert.assertEquals(existingSocialActivityGroup.getModifiedDate(),
			newSocialActivityGroup.getModifiedDate());
		Assert.assertEquals(existingSocialActivityGroup.getClassNameId(),
			newSocialActivityGroup.getClassNameId());
		Assert.assertEquals(existingSocialActivityGroup.getClassPK(),
			newSocialActivityGroup.getClassPK());
		Assert.assertEquals(existingSocialActivityGroup.getType(),
			newSocialActivityGroup.getType());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialActivityGroup newSocialActivityGroup = addSocialActivityGroup();

		SocialActivityGroup existingSocialActivityGroup = _persistence.findByPrimaryKey(newSocialActivityGroup.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityGroup, newSocialActivityGroup);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchActivityGroupException");
		}
		catch (NoSuchActivityGroupException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialActivityGroup newSocialActivityGroup = addSocialActivityGroup();

		SocialActivityGroup existingSocialActivityGroup = _persistence.fetchByPrimaryKey(newSocialActivityGroup.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityGroup, newSocialActivityGroup);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivityGroup missingSocialActivityGroup = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSocialActivityGroup);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialActivityGroup newSocialActivityGroup = addSocialActivityGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityGroup.class,
				SocialActivityGroup.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityGroupId",
				newSocialActivityGroup.getActivityGroupId()));

		List<SocialActivityGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SocialActivityGroup existingSocialActivityGroup = result.get(0);

		Assert.assertEquals(existingSocialActivityGroup, newSocialActivityGroup);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityGroup.class,
				SocialActivityGroup.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityGroupId",
				ServiceTestUtil.nextLong()));

		List<SocialActivityGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialActivityGroup newSocialActivityGroup = addSocialActivityGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityGroup.class,
				SocialActivityGroup.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activityGroupId"));

		Object newActivityGroupId = newSocialActivityGroup.getActivityGroupId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityGroupId",
				new Object[] { newActivityGroupId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingActivityGroupId = result.get(0);

		Assert.assertEquals(existingActivityGroupId, newActivityGroupId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityGroup.class,
				SocialActivityGroup.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activityGroupId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityGroupId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected SocialActivityGroup addSocialActivityGroup()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivityGroup socialActivityGroup = _persistence.create(pk);

		socialActivityGroup.setGroupId(ServiceTestUtil.nextLong());

		socialActivityGroup.setCompanyId(ServiceTestUtil.nextLong());

		socialActivityGroup.setUserId(ServiceTestUtil.nextLong());

		socialActivityGroup.setCreateDate(ServiceTestUtil.nextLong());

		socialActivityGroup.setModifiedDate(ServiceTestUtil.nextLong());

		socialActivityGroup.setClassNameId(ServiceTestUtil.nextLong());

		socialActivityGroup.setClassPK(ServiceTestUtil.nextLong());

		socialActivityGroup.setType(ServiceTestUtil.nextInt());

		_persistence.update(socialActivityGroup);

		return socialActivityGroup;
	}

	private static Log _log = LogFactoryUtil.getLog(SocialActivityGroupPersistenceTest.class);
	private SocialActivityGroupPersistence _persistence = (SocialActivityGroupPersistence)PortalBeanLocatorUtil.locate(SocialActivityGroupPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}