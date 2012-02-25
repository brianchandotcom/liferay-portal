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

package com.liferay.portlet.ratings.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.ratings.NoSuchStatsException;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.model.impl.RatingsStatsModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class RatingsStatsPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (RatingsStatsPersistence)PortalBeanLocatorUtil.locate(RatingsStatsPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		RatingsStats ratingsStats = _persistence.create(pk);

		assertNotNull(ratingsStats);

		assertEquals(ratingsStats.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		RatingsStats newRatingsStats = addRatingsStats();

		_persistence.remove(newRatingsStats);

		RatingsStats existingRatingsStats = _persistence.fetchByPrimaryKey(newRatingsStats.getPrimaryKey());

		assertNull(existingRatingsStats);
	}

	public void testUpdateNew() throws Exception {
		addRatingsStats();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		RatingsStats newRatingsStats = _persistence.create(pk);

		newRatingsStats.setClassNameId(nextLong());

		newRatingsStats.setClassPK(nextLong());

		newRatingsStats.setTotalEntries(nextInt());

		newRatingsStats.setTotalScore(nextDouble());

		newRatingsStats.setAverageScore(nextDouble());

		_persistence.update(newRatingsStats, false);

		RatingsStats existingRatingsStats = _persistence.findByPrimaryKey(newRatingsStats.getPrimaryKey());

		assertEquals(existingRatingsStats.getStatsId(),
			newRatingsStats.getStatsId());
		assertEquals(existingRatingsStats.getClassNameId(),
			newRatingsStats.getClassNameId());
		assertEquals(existingRatingsStats.getClassPK(),
			newRatingsStats.getClassPK());
		assertEquals(existingRatingsStats.getTotalEntries(),
			newRatingsStats.getTotalEntries());
		assertEquals(existingRatingsStats.getTotalScore(),
			newRatingsStats.getTotalScore());
		assertEquals(existingRatingsStats.getAverageScore(),
			newRatingsStats.getAverageScore());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		RatingsStats newRatingsStats = addRatingsStats();

		RatingsStats existingRatingsStats = _persistence.findByPrimaryKey(newRatingsStats.getPrimaryKey());

		assertEquals(existingRatingsStats, newRatingsStats);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchStatsException");
		}
		catch (NoSuchStatsException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		RatingsStats newRatingsStats = addRatingsStats();

		RatingsStats existingRatingsStats = _persistence.fetchByPrimaryKey(newRatingsStats.getPrimaryKey());

		assertEquals(existingRatingsStats, newRatingsStats);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		RatingsStats missingRatingsStats = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingRatingsStats);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		RatingsStats newRatingsStats = addRatingsStats();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RatingsStats.class,
				RatingsStats.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsId",
				newRatingsStats.getStatsId()));

		List<RatingsStats> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		RatingsStats existingRatingsStats = result.get(0);

		assertEquals(existingRatingsStats, newRatingsStats);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RatingsStats.class,
				RatingsStats.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsId", nextLong()));

		List<RatingsStats> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		RatingsStats newRatingsStats = addRatingsStats();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RatingsStats.class,
				RatingsStats.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("statsId"));

		Object newStatsId = newRatingsStats.getStatsId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("statsId",
				new Object[] { newStatsId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingStatsId = result.get(0);

		assertEquals(existingStatsId, newStatsId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RatingsStats.class,
				RatingsStats.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("statsId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("statsId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		RatingsStats newRatingsStats = addRatingsStats();

		_persistence.clearCache();

		RatingsStatsModelImpl existingRatingsStatsModelImpl = (RatingsStatsModelImpl)_persistence.findByPrimaryKey(newRatingsStats.getPrimaryKey());

		assertEquals(existingRatingsStatsModelImpl.getClassNameId(),
			existingRatingsStatsModelImpl.getOriginalClassNameId());
		assertEquals(existingRatingsStatsModelImpl.getClassPK(),
			existingRatingsStatsModelImpl.getOriginalClassPK());
	}

	protected RatingsStats addRatingsStats() throws Exception {
		long pk = nextLong();

		RatingsStats ratingsStats = _persistence.create(pk);

		ratingsStats.setClassNameId(nextLong());

		ratingsStats.setClassPK(nextLong());

		ratingsStats.setTotalEntries(nextInt());

		ratingsStats.setTotalScore(nextDouble());

		ratingsStats.setAverageScore(nextDouble());

		_persistence.update(ratingsStats, false);

		return ratingsStats;
	}

	private RatingsStatsPersistence _persistence;
}