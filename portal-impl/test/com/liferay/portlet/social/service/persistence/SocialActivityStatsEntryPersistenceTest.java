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

import com.liferay.portlet.social.NoSuchActivityStatsEntryException;
import com.liferay.portlet.social.model.SocialActivityStatsEntry;
import com.liferay.portlet.social.model.impl.SocialActivityStatsEntryModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialActivityStatsEntryPersistenceTest
	extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (SocialActivityStatsEntryPersistence)PortalBeanLocatorUtil.locate(SocialActivityStatsEntryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		SocialActivityStatsEntry socialActivityStatsEntry = _persistence.create(pk);

		assertNotNull(socialActivityStatsEntry);

		assertEquals(socialActivityStatsEntry.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SocialActivityStatsEntry newSocialActivityStatsEntry = addSocialActivityStatsEntry();

		_persistence.remove(newSocialActivityStatsEntry);

		SocialActivityStatsEntry existingSocialActivityStatsEntry = _persistence.fetchByPrimaryKey(newSocialActivityStatsEntry.getPrimaryKey());

		assertNull(existingSocialActivityStatsEntry);
	}

	public void testUpdateNew() throws Exception {
		addSocialActivityStatsEntry();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SocialActivityStatsEntry newSocialActivityStatsEntry = _persistence.create(pk);

		newSocialActivityStatsEntry.setGroupId(nextLong());

		newSocialActivityStatsEntry.setCompanyId(nextLong());

		newSocialActivityStatsEntry.setClassNameId(nextLong());

		newSocialActivityStatsEntry.setClassPK(nextLong());

		newSocialActivityStatsEntry.setClassType(nextInt());

		newSocialActivityStatsEntry.setStatName(randomString());

		newSocialActivityStatsEntry.setCurrentValue(nextInt());

		newSocialActivityStatsEntry.setOverallValue(nextInt());

		newSocialActivityStatsEntry.setGraceValue(nextInt());

		newSocialActivityStatsEntry.setStatPeriodStart(nextInt());

		newSocialActivityStatsEntry.setStatPeriodEnd(nextInt());

		_persistence.update(newSocialActivityStatsEntry, false);

		SocialActivityStatsEntry existingSocialActivityStatsEntry = _persistence.findByPrimaryKey(newSocialActivityStatsEntry.getPrimaryKey());

		assertEquals(existingSocialActivityStatsEntry.getActivityStatsEntryId(),
			newSocialActivityStatsEntry.getActivityStatsEntryId());
		assertEquals(existingSocialActivityStatsEntry.getGroupId(),
			newSocialActivityStatsEntry.getGroupId());
		assertEquals(existingSocialActivityStatsEntry.getCompanyId(),
			newSocialActivityStatsEntry.getCompanyId());
		assertEquals(existingSocialActivityStatsEntry.getClassNameId(),
			newSocialActivityStatsEntry.getClassNameId());
		assertEquals(existingSocialActivityStatsEntry.getClassPK(),
			newSocialActivityStatsEntry.getClassPK());
		assertEquals(existingSocialActivityStatsEntry.getClassType(),
			newSocialActivityStatsEntry.getClassType());
		assertEquals(existingSocialActivityStatsEntry.getStatName(),
			newSocialActivityStatsEntry.getStatName());
		assertEquals(existingSocialActivityStatsEntry.getCurrentValue(),
			newSocialActivityStatsEntry.getCurrentValue());
		assertEquals(existingSocialActivityStatsEntry.getOverallValue(),
			newSocialActivityStatsEntry.getOverallValue());
		assertEquals(existingSocialActivityStatsEntry.getGraceValue(),
			newSocialActivityStatsEntry.getGraceValue());
		assertEquals(existingSocialActivityStatsEntry.getStatPeriodStart(),
			newSocialActivityStatsEntry.getStatPeriodStart());
		assertEquals(existingSocialActivityStatsEntry.getStatPeriodEnd(),
			newSocialActivityStatsEntry.getStatPeriodEnd());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialActivityStatsEntry newSocialActivityStatsEntry = addSocialActivityStatsEntry();

		SocialActivityStatsEntry existingSocialActivityStatsEntry = _persistence.findByPrimaryKey(newSocialActivityStatsEntry.getPrimaryKey());

		assertEquals(existingSocialActivityStatsEntry,
			newSocialActivityStatsEntry);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchActivityStatsEntryException");
		}
		catch (NoSuchActivityStatsEntryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialActivityStatsEntry newSocialActivityStatsEntry = addSocialActivityStatsEntry();

		SocialActivityStatsEntry existingSocialActivityStatsEntry = _persistence.fetchByPrimaryKey(newSocialActivityStatsEntry.getPrimaryKey());

		assertEquals(existingSocialActivityStatsEntry,
			newSocialActivityStatsEntry);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		SocialActivityStatsEntry missingSocialActivityStatsEntry = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSocialActivityStatsEntry);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialActivityStatsEntry newSocialActivityStatsEntry = addSocialActivityStatsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityStatsEntry.class,
				SocialActivityStatsEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityStatsEntryId",
				newSocialActivityStatsEntry.getActivityStatsEntryId()));

		List<SocialActivityStatsEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		SocialActivityStatsEntry existingSocialActivityStatsEntry = result.get(0);

		assertEquals(existingSocialActivityStatsEntry,
			newSocialActivityStatsEntry);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityStatsEntry.class,
				SocialActivityStatsEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityStatsEntryId",
				nextLong()));

		List<SocialActivityStatsEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialActivityStatsEntry newSocialActivityStatsEntry = addSocialActivityStatsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityStatsEntry.class,
				SocialActivityStatsEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activityStatsEntryId"));

		Object newActivityStatsEntryId = newSocialActivityStatsEntry.getActivityStatsEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityStatsEntryId",
				new Object[] { newActivityStatsEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingActivityStatsEntryId = result.get(0);

		assertEquals(existingActivityStatsEntryId, newActivityStatsEntryId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityStatsEntry.class,
				SocialActivityStatsEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activityStatsEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityStatsEntryId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SocialActivityStatsEntry newSocialActivityStatsEntry = addSocialActivityStatsEntry();

		_persistence.clearCache();

		SocialActivityStatsEntryModelImpl existingSocialActivityStatsEntryModelImpl =
			(SocialActivityStatsEntryModelImpl)_persistence.findByPrimaryKey(newSocialActivityStatsEntry.getPrimaryKey());

		assertEquals(existingSocialActivityStatsEntryModelImpl.getGroupId(),
			existingSocialActivityStatsEntryModelImpl.getOriginalGroupId());
		assertEquals(existingSocialActivityStatsEntryModelImpl.getClassNameId(),
			existingSocialActivityStatsEntryModelImpl.getOriginalClassNameId());
		assertEquals(existingSocialActivityStatsEntryModelImpl.getClassPK(),
			existingSocialActivityStatsEntryModelImpl.getOriginalClassPK());
		assertEquals(existingSocialActivityStatsEntryModelImpl.getClassType(),
			existingSocialActivityStatsEntryModelImpl.getOriginalClassType());
		assertTrue(Validator.equals(
				existingSocialActivityStatsEntryModelImpl.getStatName(),
				existingSocialActivityStatsEntryModelImpl.getOriginalStatName()));
		assertEquals(existingSocialActivityStatsEntryModelImpl.getStatPeriodEnd(),
			existingSocialActivityStatsEntryModelImpl.getOriginalStatPeriodEnd());

		assertEquals(existingSocialActivityStatsEntryModelImpl.getGroupId(),
			existingSocialActivityStatsEntryModelImpl.getOriginalGroupId());
		assertEquals(existingSocialActivityStatsEntryModelImpl.getClassNameId(),
			existingSocialActivityStatsEntryModelImpl.getOriginalClassNameId());
		assertEquals(existingSocialActivityStatsEntryModelImpl.getClassPK(),
			existingSocialActivityStatsEntryModelImpl.getOriginalClassPK());
		assertEquals(existingSocialActivityStatsEntryModelImpl.getClassType(),
			existingSocialActivityStatsEntryModelImpl.getOriginalClassType());
		assertTrue(Validator.equals(
				existingSocialActivityStatsEntryModelImpl.getStatName(),
				existingSocialActivityStatsEntryModelImpl.getOriginalStatName()));
		assertEquals(existingSocialActivityStatsEntryModelImpl.getStatPeriodStart(),
			existingSocialActivityStatsEntryModelImpl.getOriginalStatPeriodStart());
	}

	protected SocialActivityStatsEntry addSocialActivityStatsEntry()
		throws Exception {
		long pk = nextLong();

		SocialActivityStatsEntry socialActivityStatsEntry = _persistence.create(pk);

		socialActivityStatsEntry.setGroupId(nextLong());

		socialActivityStatsEntry.setCompanyId(nextLong());

		socialActivityStatsEntry.setClassNameId(nextLong());

		socialActivityStatsEntry.setClassPK(nextLong());

		socialActivityStatsEntry.setClassType(nextInt());

		socialActivityStatsEntry.setStatName(randomString());

		socialActivityStatsEntry.setCurrentValue(nextInt());

		socialActivityStatsEntry.setOverallValue(nextInt());

		socialActivityStatsEntry.setGraceValue(nextInt());

		socialActivityStatsEntry.setStatPeriodStart(nextInt());

		socialActivityStatsEntry.setStatPeriodEnd(nextInt());

		_persistence.update(socialActivityStatsEntry, false);

		return socialActivityStatsEntry;
	}

	private SocialActivityStatsEntryPersistence _persistence;
}