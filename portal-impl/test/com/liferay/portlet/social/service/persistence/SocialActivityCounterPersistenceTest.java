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

import com.liferay.portlet.social.NoSuchActivityCounterException;
import com.liferay.portlet.social.model.SocialActivityCounter;
import com.liferay.portlet.social.model.impl.SocialActivityCounterModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialActivityCounterPersistenceTest
	extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (SocialActivityCounterPersistence)PortalBeanLocatorUtil.locate(SocialActivityCounterPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		SocialActivityCounter socialActivityCounter = _persistence.create(pk);

		assertNotNull(socialActivityCounter);

		assertEquals(socialActivityCounter.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		_persistence.remove(newSocialActivityCounter);

		SocialActivityCounter existingSocialActivityCounter = _persistence.fetchByPrimaryKey(newSocialActivityCounter.getPrimaryKey());

		assertNull(existingSocialActivityCounter);
	}

	public void testUpdateNew() throws Exception {
		addSocialActivityCounter();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SocialActivityCounter newSocialActivityCounter = _persistence.create(pk);

		newSocialActivityCounter.setGroupId(nextLong());

		newSocialActivityCounter.setCompanyId(nextLong());

		newSocialActivityCounter.setClassNameId(nextLong());

		newSocialActivityCounter.setClassPK(nextLong());

		newSocialActivityCounter.setName(randomString());

		newSocialActivityCounter.setOwnerType(nextInt());

		newSocialActivityCounter.setCurrentValue(nextInt());

		newSocialActivityCounter.setTotalValue(nextInt());

		newSocialActivityCounter.setGraceValue(nextInt());

		newSocialActivityCounter.setStartPeriod(nextInt());

		newSocialActivityCounter.setEndPeriod(nextInt());

		_persistence.update(newSocialActivityCounter, false);

		SocialActivityCounter existingSocialActivityCounter = _persistence.findByPrimaryKey(newSocialActivityCounter.getPrimaryKey());

		assertEquals(existingSocialActivityCounter.getActivityCounterId(),
			newSocialActivityCounter.getActivityCounterId());
		assertEquals(existingSocialActivityCounter.getGroupId(),
			newSocialActivityCounter.getGroupId());
		assertEquals(existingSocialActivityCounter.getCompanyId(),
			newSocialActivityCounter.getCompanyId());
		assertEquals(existingSocialActivityCounter.getClassNameId(),
			newSocialActivityCounter.getClassNameId());
		assertEquals(existingSocialActivityCounter.getClassPK(),
			newSocialActivityCounter.getClassPK());
		assertEquals(existingSocialActivityCounter.getName(),
			newSocialActivityCounter.getName());
		assertEquals(existingSocialActivityCounter.getOwnerType(),
			newSocialActivityCounter.getOwnerType());
		assertEquals(existingSocialActivityCounter.getCurrentValue(),
			newSocialActivityCounter.getCurrentValue());
		assertEquals(existingSocialActivityCounter.getTotalValue(),
			newSocialActivityCounter.getTotalValue());
		assertEquals(existingSocialActivityCounter.getGraceValue(),
			newSocialActivityCounter.getGraceValue());
		assertEquals(existingSocialActivityCounter.getStartPeriod(),
			newSocialActivityCounter.getStartPeriod());
		assertEquals(existingSocialActivityCounter.getEndPeriod(),
			newSocialActivityCounter.getEndPeriod());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		SocialActivityCounter existingSocialActivityCounter = _persistence.findByPrimaryKey(newSocialActivityCounter.getPrimaryKey());

		assertEquals(existingSocialActivityCounter, newSocialActivityCounter);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchActivityCounterException");
		}
		catch (NoSuchActivityCounterException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		SocialActivityCounter existingSocialActivityCounter = _persistence.fetchByPrimaryKey(newSocialActivityCounter.getPrimaryKey());

		assertEquals(existingSocialActivityCounter, newSocialActivityCounter);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		SocialActivityCounter missingSocialActivityCounter = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSocialActivityCounter);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityCounter.class,
				SocialActivityCounter.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityCounterId",
				newSocialActivityCounter.getActivityCounterId()));

		List<SocialActivityCounter> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		SocialActivityCounter existingSocialActivityCounter = result.get(0);

		assertEquals(existingSocialActivityCounter, newSocialActivityCounter);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityCounter.class,
				SocialActivityCounter.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityCounterId",
				nextLong()));

		List<SocialActivityCounter> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityCounter.class,
				SocialActivityCounter.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activityCounterId"));

		Object newActivityCounterId = newSocialActivityCounter.getActivityCounterId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityCounterId",
				new Object[] { newActivityCounterId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingActivityCounterId = result.get(0);

		assertEquals(existingActivityCounterId, newActivityCounterId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityCounter.class,
				SocialActivityCounter.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activityCounterId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityCounterId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		_persistence.clearCache();

		SocialActivityCounterModelImpl existingSocialActivityCounterModelImpl = (SocialActivityCounterModelImpl)_persistence.findByPrimaryKey(newSocialActivityCounter.getPrimaryKey());

		assertEquals(existingSocialActivityCounterModelImpl.getGroupId(),
			existingSocialActivityCounterModelImpl.getOriginalGroupId());
		assertEquals(existingSocialActivityCounterModelImpl.getClassNameId(),
			existingSocialActivityCounterModelImpl.getOriginalClassNameId());
		assertEquals(existingSocialActivityCounterModelImpl.getClassPK(),
			existingSocialActivityCounterModelImpl.getOriginalClassPK());
		assertTrue(Validator.equals(
				existingSocialActivityCounterModelImpl.getName(),
				existingSocialActivityCounterModelImpl.getOriginalName()));
		assertEquals(existingSocialActivityCounterModelImpl.getOwnerType(),
			existingSocialActivityCounterModelImpl.getOriginalOwnerType());
		assertEquals(existingSocialActivityCounterModelImpl.getStartPeriod(),
			existingSocialActivityCounterModelImpl.getOriginalStartPeriod());

		assertEquals(existingSocialActivityCounterModelImpl.getGroupId(),
			existingSocialActivityCounterModelImpl.getOriginalGroupId());
		assertEquals(existingSocialActivityCounterModelImpl.getClassNameId(),
			existingSocialActivityCounterModelImpl.getOriginalClassNameId());
		assertEquals(existingSocialActivityCounterModelImpl.getClassPK(),
			existingSocialActivityCounterModelImpl.getOriginalClassPK());
		assertTrue(Validator.equals(
				existingSocialActivityCounterModelImpl.getName(),
				existingSocialActivityCounterModelImpl.getOriginalName()));
		assertEquals(existingSocialActivityCounterModelImpl.getOwnerType(),
			existingSocialActivityCounterModelImpl.getOriginalOwnerType());
		assertEquals(existingSocialActivityCounterModelImpl.getEndPeriod(),
			existingSocialActivityCounterModelImpl.getOriginalEndPeriod());
	}

	protected SocialActivityCounter addSocialActivityCounter()
		throws Exception {
		long pk = nextLong();

		SocialActivityCounter socialActivityCounter = _persistence.create(pk);

		socialActivityCounter.setGroupId(nextLong());

		socialActivityCounter.setCompanyId(nextLong());

		socialActivityCounter.setClassNameId(nextLong());

		socialActivityCounter.setClassPK(nextLong());

		socialActivityCounter.setName(randomString());

		socialActivityCounter.setOwnerType(nextInt());

		socialActivityCounter.setCurrentValue(nextInt());

		socialActivityCounter.setTotalValue(nextInt());

		socialActivityCounter.setGraceValue(nextInt());

		socialActivityCounter.setStartPeriod(nextInt());

		socialActivityCounter.setEndPeriod(nextInt());

		_persistence.update(socialActivityCounter, false);

		return socialActivityCounter;
	}

	private SocialActivityCounterPersistence _persistence;
}