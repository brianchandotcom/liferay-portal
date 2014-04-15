/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.social.NoSuchActivityCounterException;
import com.liferay.portlet.social.model.SocialActivityCounter;
import com.liferay.portlet.social.model.impl.SocialActivityCounterModelImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class SocialActivityCounterPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<SocialActivityCounter> modelListener : _modelListeners) {
			_persistence.unregisterListener(modelListener);
		}
	}

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

		for (ModelListener<SocialActivityCounter> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivityCounter socialActivityCounter = _persistence.create(pk);

		Assert.assertNotNull(socialActivityCounter);

		Assert.assertEquals(socialActivityCounter.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		_persistence.remove(newSocialActivityCounter);

		SocialActivityCounter existingSocialActivityCounter = _persistence.fetchByPrimaryKey(newSocialActivityCounter.getPrimaryKey());

		Assert.assertNull(existingSocialActivityCounter);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSocialActivityCounter();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivityCounter newSocialActivityCounter = _persistence.create(pk);

		newSocialActivityCounter.setGroupId(ServiceTestUtil.nextLong());

		newSocialActivityCounter.setCompanyId(ServiceTestUtil.nextLong());

		newSocialActivityCounter.setClassNameId(ServiceTestUtil.nextLong());

		newSocialActivityCounter.setClassPK(ServiceTestUtil.nextLong());

		newSocialActivityCounter.setName(ServiceTestUtil.randomString());

		newSocialActivityCounter.setOwnerType(ServiceTestUtil.nextInt());

		newSocialActivityCounter.setCurrentValue(ServiceTestUtil.nextInt());

		newSocialActivityCounter.setTotalValue(ServiceTestUtil.nextInt());

		newSocialActivityCounter.setGraceValue(ServiceTestUtil.nextInt());

		newSocialActivityCounter.setStartPeriod(ServiceTestUtil.nextInt());

		newSocialActivityCounter.setEndPeriod(ServiceTestUtil.nextInt());

		newSocialActivityCounter.setActive(ServiceTestUtil.randomBoolean());

		_persistence.update(newSocialActivityCounter);

		SocialActivityCounter existingSocialActivityCounter = _persistence.findByPrimaryKey(newSocialActivityCounter.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityCounter.getActivityCounterId(),
			newSocialActivityCounter.getActivityCounterId());
		Assert.assertEquals(existingSocialActivityCounter.getGroupId(),
			newSocialActivityCounter.getGroupId());
		Assert.assertEquals(existingSocialActivityCounter.getCompanyId(),
			newSocialActivityCounter.getCompanyId());
		Assert.assertEquals(existingSocialActivityCounter.getClassNameId(),
			newSocialActivityCounter.getClassNameId());
		Assert.assertEquals(existingSocialActivityCounter.getClassPK(),
			newSocialActivityCounter.getClassPK());
		Assert.assertEquals(existingSocialActivityCounter.getName(),
			newSocialActivityCounter.getName());
		Assert.assertEquals(existingSocialActivityCounter.getOwnerType(),
			newSocialActivityCounter.getOwnerType());
		Assert.assertEquals(existingSocialActivityCounter.getCurrentValue(),
			newSocialActivityCounter.getCurrentValue());
		Assert.assertEquals(existingSocialActivityCounter.getTotalValue(),
			newSocialActivityCounter.getTotalValue());
		Assert.assertEquals(existingSocialActivityCounter.getGraceValue(),
			newSocialActivityCounter.getGraceValue());
		Assert.assertEquals(existingSocialActivityCounter.getStartPeriod(),
			newSocialActivityCounter.getStartPeriod());
		Assert.assertEquals(existingSocialActivityCounter.getEndPeriod(),
			newSocialActivityCounter.getEndPeriod());
		Assert.assertEquals(existingSocialActivityCounter.getActive(),
			newSocialActivityCounter.getActive());
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(ServiceTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_C() {
		try {
			_persistence.countByC_C(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong());

			_persistence.countByC_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_C_O() {
		try {
			_persistence.countByG_C_C_O(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextInt());

			_persistence.countByG_C_C_O(0L, 0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_C_N_O_S() {
		try {
			_persistence.countByG_C_C_N_O_S(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong(),
				StringPool.BLANK, ServiceTestUtil.nextInt(),
				ServiceTestUtil.nextInt());

			_persistence.countByG_C_C_N_O_S(0L, 0L, 0L, StringPool.NULL, 0, 0);

			_persistence.countByG_C_C_N_O_S(0L, 0L, 0L, (String)null, 0, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_C_C_N_O_E() {
		try {
			_persistence.countByG_C_C_N_O_E(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong(),
				StringPool.BLANK, ServiceTestUtil.nextInt(),
				ServiceTestUtil.nextInt());

			_persistence.countByG_C_C_N_O_E(0L, 0L, 0L, StringPool.NULL, 0, 0);

			_persistence.countByG_C_C_N_O_E(0L, 0L, 0L, (String)null, 0, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		SocialActivityCounter existingSocialActivityCounter = _persistence.findByPrimaryKey(newSocialActivityCounter.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityCounter,
			newSocialActivityCounter);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchActivityCounterException");
		}
		catch (NoSuchActivityCounterException nsee) {
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
		return OrderByComparatorFactoryUtil.create("SocialActivityCounter",
			"activityCounterId", true, "groupId", true, "companyId", true,
			"classNameId", true, "classPK", true, "name", true, "ownerType",
			true, "currentValue", true, "totalValue", true, "graceValue", true,
			"startPeriod", true, "endPeriod", true, "active", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		SocialActivityCounter existingSocialActivityCounter = _persistence.fetchByPrimaryKey(newSocialActivityCounter.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityCounter,
			newSocialActivityCounter);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivityCounter missingSocialActivityCounter = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSocialActivityCounter);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new SocialActivityCounterActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					SocialActivityCounter socialActivityCounter = (SocialActivityCounter)object;

					Assert.assertNotNull(socialActivityCounter);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityCounter.class,
				SocialActivityCounter.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityCounterId",
				newSocialActivityCounter.getActivityCounterId()));

		List<SocialActivityCounter> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SocialActivityCounter existingSocialActivityCounter = result.get(0);

		Assert.assertEquals(existingSocialActivityCounter,
			newSocialActivityCounter);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityCounter.class,
				SocialActivityCounter.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityCounterId",
				ServiceTestUtil.nextLong()));

		List<SocialActivityCounter> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingActivityCounterId = result.get(0);

		Assert.assertEquals(existingActivityCounterId, newActivityCounterId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityCounter.class,
				SocialActivityCounter.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activityCounterId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityCounterId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SocialActivityCounter newSocialActivityCounter = addSocialActivityCounter();

		_persistence.clearCache();

		SocialActivityCounterModelImpl existingSocialActivityCounterModelImpl = (SocialActivityCounterModelImpl)_persistence.findByPrimaryKey(newSocialActivityCounter.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityCounterModelImpl.getGroupId(),
			existingSocialActivityCounterModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingSocialActivityCounterModelImpl.getClassNameId(),
			existingSocialActivityCounterModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingSocialActivityCounterModelImpl.getClassPK(),
			existingSocialActivityCounterModelImpl.getOriginalClassPK());
		Assert.assertTrue(Validator.equals(
				existingSocialActivityCounterModelImpl.getName(),
				existingSocialActivityCounterModelImpl.getOriginalName()));
		Assert.assertEquals(existingSocialActivityCounterModelImpl.getOwnerType(),
			existingSocialActivityCounterModelImpl.getOriginalOwnerType());
		Assert.assertEquals(existingSocialActivityCounterModelImpl.getStartPeriod(),
			existingSocialActivityCounterModelImpl.getOriginalStartPeriod());

		Assert.assertEquals(existingSocialActivityCounterModelImpl.getGroupId(),
			existingSocialActivityCounterModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingSocialActivityCounterModelImpl.getClassNameId(),
			existingSocialActivityCounterModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingSocialActivityCounterModelImpl.getClassPK(),
			existingSocialActivityCounterModelImpl.getOriginalClassPK());
		Assert.assertTrue(Validator.equals(
				existingSocialActivityCounterModelImpl.getName(),
				existingSocialActivityCounterModelImpl.getOriginalName()));
		Assert.assertEquals(existingSocialActivityCounterModelImpl.getOwnerType(),
			existingSocialActivityCounterModelImpl.getOriginalOwnerType());
		Assert.assertEquals(existingSocialActivityCounterModelImpl.getEndPeriod(),
			existingSocialActivityCounterModelImpl.getOriginalEndPeriod());
	}

	protected SocialActivityCounter addSocialActivityCounter()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivityCounter socialActivityCounter = _persistence.create(pk);

		socialActivityCounter.setGroupId(ServiceTestUtil.nextLong());

		socialActivityCounter.setCompanyId(ServiceTestUtil.nextLong());

		socialActivityCounter.setClassNameId(ServiceTestUtil.nextLong());

		socialActivityCounter.setClassPK(ServiceTestUtil.nextLong());

		socialActivityCounter.setName(ServiceTestUtil.randomString());

		socialActivityCounter.setOwnerType(ServiceTestUtil.nextInt());

		socialActivityCounter.setCurrentValue(ServiceTestUtil.nextInt());

		socialActivityCounter.setTotalValue(ServiceTestUtil.nextInt());

		socialActivityCounter.setGraceValue(ServiceTestUtil.nextInt());

		socialActivityCounter.setStartPeriod(ServiceTestUtil.nextInt());

		socialActivityCounter.setEndPeriod(ServiceTestUtil.nextInt());

		socialActivityCounter.setActive(ServiceTestUtil.randomBoolean());

		_persistence.update(socialActivityCounter);

		return socialActivityCounter;
	}

	private static Log _log = LogFactoryUtil.getLog(SocialActivityCounterPersistenceTest.class);
	private ModelListener<SocialActivityCounter>[] _modelListeners;
	private SocialActivityCounterPersistence _persistence = (SocialActivityCounterPersistence)PortalBeanLocatorUtil.locate(SocialActivityCounterPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}