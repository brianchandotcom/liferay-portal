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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.ratings.NoSuchEntryException;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl;

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
public class RatingsEntryPersistenceTest {
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

		RatingsEntry ratingsEntry = _persistence.create(pk);

		Assert.assertNotNull(ratingsEntry);

		Assert.assertEquals(ratingsEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		RatingsEntry newRatingsEntry = addRatingsEntry();

		_persistence.remove(newRatingsEntry);

		RatingsEntry existingRatingsEntry = _persistence.fetchByPrimaryKey(newRatingsEntry.getPrimaryKey());

		Assert.assertNull(existingRatingsEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addRatingsEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		RatingsEntry newRatingsEntry = _persistence.create(pk);

		newRatingsEntry.setCompanyId(ServiceTestUtil.nextLong());

		newRatingsEntry.setUserId(ServiceTestUtil.nextLong());

		newRatingsEntry.setUserName(ServiceTestUtil.randomString());

		newRatingsEntry.setCreateDate(ServiceTestUtil.nextDate());

		newRatingsEntry.setModifiedDate(ServiceTestUtil.nextDate());

		newRatingsEntry.setClassNameId(ServiceTestUtil.nextLong());

		newRatingsEntry.setClassPK(ServiceTestUtil.nextLong());

		newRatingsEntry.setScore(ServiceTestUtil.nextDouble());

		_persistence.update(newRatingsEntry);

		RatingsEntry existingRatingsEntry = _persistence.findByPrimaryKey(newRatingsEntry.getPrimaryKey());

		Assert.assertEquals(existingRatingsEntry.getEntryId(),
			newRatingsEntry.getEntryId());
		Assert.assertEquals(existingRatingsEntry.getCompanyId(),
			newRatingsEntry.getCompanyId());
		Assert.assertEquals(existingRatingsEntry.getUserId(),
			newRatingsEntry.getUserId());
		Assert.assertEquals(existingRatingsEntry.getUserName(),
			newRatingsEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingRatingsEntry.getCreateDate()),
			Time.getShortTimestamp(newRatingsEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingRatingsEntry.getModifiedDate()),
			Time.getShortTimestamp(newRatingsEntry.getModifiedDate()));
		Assert.assertEquals(existingRatingsEntry.getClassNameId(),
			newRatingsEntry.getClassNameId());
		Assert.assertEquals(existingRatingsEntry.getClassPK(),
			newRatingsEntry.getClassPK());
		AssertUtils.assertEquals(
			existingRatingsEntry.getScore(),
			newRatingsEntry.getScore());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		RatingsEntry newRatingsEntry = addRatingsEntry();

		RatingsEntry existingRatingsEntry = _persistence.findByPrimaryKey(newRatingsEntry.getPrimaryKey());

		Assert.assertEquals(existingRatingsEntry, newRatingsEntry);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchEntryException");
		}
		catch (NoSuchEntryException nsee) {
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
		return OrderByComparatorFactoryUtil.create("RatingsEntry", "entryId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "classNameId", true,
			"classPK", true, "score", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		RatingsEntry newRatingsEntry = addRatingsEntry();

		RatingsEntry existingRatingsEntry = _persistence.fetchByPrimaryKey(newRatingsEntry.getPrimaryKey());

		Assert.assertEquals(existingRatingsEntry, newRatingsEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		RatingsEntry missingRatingsEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingRatingsEntry);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new RatingsEntryActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					RatingsEntry ratingsEntry = (RatingsEntry)object;

					Assert.assertNotNull(ratingsEntry);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		RatingsEntry newRatingsEntry = addRatingsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RatingsEntry.class,
				RatingsEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newRatingsEntry.getEntryId()));

		List<RatingsEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		RatingsEntry existingRatingsEntry = result.get(0);

		Assert.assertEquals(existingRatingsEntry, newRatingsEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RatingsEntry.class,
				RatingsEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				ServiceTestUtil.nextLong()));

		List<RatingsEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		RatingsEntry newRatingsEntry = addRatingsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RatingsEntry.class,
				RatingsEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		Object newEntryId = newRatingsEntry.getEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { newEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingEntryId = result.get(0);

		Assert.assertEquals(existingEntryId, newEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RatingsEntry.class,
				RatingsEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		RatingsEntry newRatingsEntry = addRatingsEntry();

		_persistence.clearCache();

		RatingsEntryModelImpl existingRatingsEntryModelImpl = (RatingsEntryModelImpl)_persistence.findByPrimaryKey(newRatingsEntry.getPrimaryKey());

		Assert.assertEquals(existingRatingsEntryModelImpl.getUserId(),
			existingRatingsEntryModelImpl.getOriginalUserId());
		Assert.assertEquals(existingRatingsEntryModelImpl.getClassNameId(),
			existingRatingsEntryModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingRatingsEntryModelImpl.getClassPK(),
			existingRatingsEntryModelImpl.getOriginalClassPK());
	}

	protected RatingsEntry addRatingsEntry() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		RatingsEntry ratingsEntry = _persistence.create(pk);

		ratingsEntry.setCompanyId(ServiceTestUtil.nextLong());

		ratingsEntry.setUserId(ServiceTestUtil.nextLong());

		ratingsEntry.setUserName(ServiceTestUtil.randomString());

		ratingsEntry.setCreateDate(ServiceTestUtil.nextDate());

		ratingsEntry.setModifiedDate(ServiceTestUtil.nextDate());

		ratingsEntry.setClassNameId(ServiceTestUtil.nextLong());

		ratingsEntry.setClassPK(ServiceTestUtil.nextLong());

		ratingsEntry.setScore(ServiceTestUtil.nextDouble());

		_persistence.update(ratingsEntry);

		return ratingsEntry;
	}

	private static Log _log = LogFactoryUtil.getLog(RatingsEntryPersistenceTest.class);
	private RatingsEntryPersistence _persistence = (RatingsEntryPersistence)PortalBeanLocatorUtil.locate(RatingsEntryPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}