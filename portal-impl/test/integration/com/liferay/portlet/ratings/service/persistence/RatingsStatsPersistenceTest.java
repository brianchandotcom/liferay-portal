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

package com.liferay.portlet.ratings.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
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

import com.liferay.portlet.ratings.NoSuchStatsException;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.model.impl.RatingsStatsModelImpl;

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
public class RatingsStatsPersistenceTest {
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

		RatingsStats ratingsStats = _persistence.create(pk);

		Assert.assertNotNull(ratingsStats);

		Assert.assertEquals(ratingsStats.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		RatingsStats newRatingsStats = addRatingsStats();

		_persistence.remove(newRatingsStats);

		RatingsStats existingRatingsStats = _persistence.fetchByPrimaryKey(newRatingsStats.getPrimaryKey());

		Assert.assertNull(existingRatingsStats);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addRatingsStats();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		RatingsStats newRatingsStats = _persistence.create(pk);

		newRatingsStats.setClassNameId(ServiceTestUtil.nextLong());

		newRatingsStats.setClassPK(ServiceTestUtil.nextLong());

		newRatingsStats.setTotalEntries(ServiceTestUtil.nextInt());

		newRatingsStats.setTotalScore(ServiceTestUtil.nextDouble());

		newRatingsStats.setAverageScore(ServiceTestUtil.nextDouble());

		_persistence.update(newRatingsStats);

		RatingsStats existingRatingsStats = _persistence.findByPrimaryKey(newRatingsStats.getPrimaryKey());

		Assert.assertEquals(existingRatingsStats.getStatsId(),
			newRatingsStats.getStatsId());
		Assert.assertEquals(existingRatingsStats.getClassNameId(),
			newRatingsStats.getClassNameId());
		Assert.assertEquals(existingRatingsStats.getClassPK(),
			newRatingsStats.getClassPK());
		Assert.assertEquals(existingRatingsStats.getTotalEntries(),
			newRatingsStats.getTotalEntries());
		AssertUtils.assertEquals(
			existingRatingsStats.getTotalScore(),
			newRatingsStats.getTotalScore());
		AssertUtils.assertEquals(existingRatingsStats.getAverageScore(),
			newRatingsStats.getAverageScore());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		RatingsStats newRatingsStats = addRatingsStats();

		RatingsStats existingRatingsStats = _persistence.findByPrimaryKey(newRatingsStats.getPrimaryKey());

		Assert.assertEquals(existingRatingsStats, newRatingsStats);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchStatsException");
		}
		catch (NoSuchStatsException nsee) {
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
		return OrderByComparatorFactoryUtil.create("RatingsStats", "statsId",
			true, "classNameId", true, "classPK", true, "totalEntries", true,
			"totalScore", true, "averageScore", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		RatingsStats newRatingsStats = addRatingsStats();

		RatingsStats existingRatingsStats = _persistence.fetchByPrimaryKey(newRatingsStats.getPrimaryKey());

		Assert.assertEquals(existingRatingsStats, newRatingsStats);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		RatingsStats missingRatingsStats = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingRatingsStats);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new RatingsStatsActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					RatingsStats ratingsStats = (RatingsStats)object;

					Assert.assertNotNull(ratingsStats);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		RatingsStats newRatingsStats = addRatingsStats();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RatingsStats.class,
				RatingsStats.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsId",
				newRatingsStats.getStatsId()));

		List<RatingsStats> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		RatingsStats existingRatingsStats = result.get(0);

		Assert.assertEquals(existingRatingsStats, newRatingsStats);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RatingsStats.class,
				RatingsStats.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsId",
				ServiceTestUtil.nextLong()));

		List<RatingsStats> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingStatsId = result.get(0);

		Assert.assertEquals(existingStatsId, newStatsId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RatingsStats.class,
				RatingsStats.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("statsId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("statsId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		RatingsStats newRatingsStats = addRatingsStats();

		_persistence.clearCache();

		RatingsStatsModelImpl existingRatingsStatsModelImpl = (RatingsStatsModelImpl)_persistence.findByPrimaryKey(newRatingsStats.getPrimaryKey());

		Assert.assertEquals(existingRatingsStatsModelImpl.getClassNameId(),
			existingRatingsStatsModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingRatingsStatsModelImpl.getClassPK(),
			existingRatingsStatsModelImpl.getOriginalClassPK());
	}

	protected RatingsStats addRatingsStats() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		RatingsStats ratingsStats = _persistence.create(pk);

		ratingsStats.setClassNameId(ServiceTestUtil.nextLong());

		ratingsStats.setClassPK(ServiceTestUtil.nextLong());

		ratingsStats.setTotalEntries(ServiceTestUtil.nextInt());

		ratingsStats.setTotalScore(ServiceTestUtil.nextDouble());

		ratingsStats.setAverageScore(ServiceTestUtil.nextDouble());

		_persistence.update(ratingsStats);

		return ratingsStats;
	}

	private static Log _log = LogFactoryUtil.getLog(RatingsStatsPersistenceTest.class);
	private RatingsStatsPersistence _persistence = (RatingsStatsPersistence)PortalBeanLocatorUtil.locate(RatingsStatsPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}