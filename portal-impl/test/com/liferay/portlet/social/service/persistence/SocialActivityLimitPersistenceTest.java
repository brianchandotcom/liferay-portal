/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.social.NoSuchActivityLimitException;
import com.liferay.portlet.social.model.SocialActivityLimit;
import com.liferay.portlet.social.model.impl.SocialActivityLimitModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialActivityLimitPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (SocialActivityLimitPersistence)PortalBeanLocatorUtil.locate(SocialActivityLimitPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		SocialActivityLimit socialActivityLimit = _persistence.create(pk);

		assertNotNull(socialActivityLimit);

		assertEquals(socialActivityLimit.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		_persistence.remove(newSocialActivityLimit);

		SocialActivityLimit existingSocialActivityLimit = _persistence.fetchByPrimaryKey(newSocialActivityLimit.getPrimaryKey());

		assertNull(existingSocialActivityLimit);
	}

	public void testUpdateNew() throws Exception {
		addSocialActivityLimit();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SocialActivityLimit newSocialActivityLimit = _persistence.create(pk);

		newSocialActivityLimit.setGroupId(nextLong());

		newSocialActivityLimit.setCompanyId(nextLong());

		newSocialActivityLimit.setUserId(nextLong());

		newSocialActivityLimit.setClassNameId(nextLong());

		newSocialActivityLimit.setClassPK(nextLong());

		newSocialActivityLimit.setActivityType(nextInt());

		newSocialActivityLimit.setActivityCounterName(randomString());

		newSocialActivityLimit.setValue(randomString());

		_persistence.update(newSocialActivityLimit, false);

		SocialActivityLimit existingSocialActivityLimit = _persistence.findByPrimaryKey(newSocialActivityLimit.getPrimaryKey());

		assertEquals(existingSocialActivityLimit.getActivityLimitId(),
			newSocialActivityLimit.getActivityLimitId());
		assertEquals(existingSocialActivityLimit.getGroupId(),
			newSocialActivityLimit.getGroupId());
		assertEquals(existingSocialActivityLimit.getCompanyId(),
			newSocialActivityLimit.getCompanyId());
		assertEquals(existingSocialActivityLimit.getUserId(),
			newSocialActivityLimit.getUserId());
		assertEquals(existingSocialActivityLimit.getClassNameId(),
			newSocialActivityLimit.getClassNameId());
		assertEquals(existingSocialActivityLimit.getClassPK(),
			newSocialActivityLimit.getClassPK());
		assertEquals(existingSocialActivityLimit.getActivityType(),
			newSocialActivityLimit.getActivityType());
		assertEquals(existingSocialActivityLimit.getActivityCounterName(),
			newSocialActivityLimit.getActivityCounterName());
		assertEquals(existingSocialActivityLimit.getValue(),
			newSocialActivityLimit.getValue());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		SocialActivityLimit existingSocialActivityLimit = _persistence.findByPrimaryKey(newSocialActivityLimit.getPrimaryKey());

		assertEquals(existingSocialActivityLimit, newSocialActivityLimit);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchActivityLimitException");
		}
		catch (NoSuchActivityLimitException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		SocialActivityLimit existingSocialActivityLimit = _persistence.fetchByPrimaryKey(newSocialActivityLimit.getPrimaryKey());

		assertEquals(existingSocialActivityLimit, newSocialActivityLimit);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		SocialActivityLimit missingSocialActivityLimit = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSocialActivityLimit);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityLimit.class,
				SocialActivityLimit.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityLimitId",
				newSocialActivityLimit.getActivityLimitId()));

		List<SocialActivityLimit> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		SocialActivityLimit existingSocialActivityLimit = result.get(0);

		assertEquals(existingSocialActivityLimit, newSocialActivityLimit);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityLimit.class,
				SocialActivityLimit.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityLimitId",
				nextLong()));

		List<SocialActivityLimit> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingActivityLimitId = result.get(0);

		assertEquals(existingActivityLimitId, newActivityLimitId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityLimit.class,
				SocialActivityLimit.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activityLimitId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityLimitId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		_persistence.clearCache();

		SocialActivityLimitModelImpl existingSocialActivityLimitModelImpl = (SocialActivityLimitModelImpl)_persistence.findByPrimaryKey(newSocialActivityLimit.getPrimaryKey());

		assertEquals(existingSocialActivityLimitModelImpl.getGroupId(),
			existingSocialActivityLimitModelImpl.getOriginalGroupId());
		assertEquals(existingSocialActivityLimitModelImpl.getUserId(),
			existingSocialActivityLimitModelImpl.getOriginalUserId());
		assertEquals(existingSocialActivityLimitModelImpl.getClassNameId(),
			existingSocialActivityLimitModelImpl.getOriginalClassNameId());
		assertEquals(existingSocialActivityLimitModelImpl.getClassPK(),
			existingSocialActivityLimitModelImpl.getOriginalClassPK());
		assertEquals(existingSocialActivityLimitModelImpl.getActivityType(),
			existingSocialActivityLimitModelImpl.getOriginalActivityType());
		assertTrue(Validator.equals(
				existingSocialActivityLimitModelImpl.getActivityCounterName(),
				existingSocialActivityLimitModelImpl.getOriginalActivityCounterName()));
	}

	protected SocialActivityLimit addSocialActivityLimit()
		throws Exception {
		long pk = nextLong();

		SocialActivityLimit socialActivityLimit = _persistence.create(pk);

		socialActivityLimit.setGroupId(nextLong());

		socialActivityLimit.setCompanyId(nextLong());

		socialActivityLimit.setUserId(nextLong());

		socialActivityLimit.setClassNameId(nextLong());

		socialActivityLimit.setClassPK(nextLong());

		socialActivityLimit.setActivityType(nextInt());

		socialActivityLimit.setActivityCounterName(randomString());

		socialActivityLimit.setValue(randomString());

		_persistence.update(socialActivityLimit, false);

		return socialActivityLimit;
	}

	private SocialActivityLimitPersistence _persistence;
}