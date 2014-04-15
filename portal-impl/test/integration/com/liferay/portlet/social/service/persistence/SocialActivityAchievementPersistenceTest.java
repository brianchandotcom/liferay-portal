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

import com.liferay.portlet.social.NoSuchActivityAchievementException;
import com.liferay.portlet.social.model.SocialActivityAchievement;
import com.liferay.portlet.social.model.impl.SocialActivityAchievementModelImpl;

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
public class SocialActivityAchievementPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<SocialActivityAchievement> modelListener : _modelListeners) {
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

		for (ModelListener<SocialActivityAchievement> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivityAchievement socialActivityAchievement = _persistence.create(pk);

		Assert.assertNotNull(socialActivityAchievement);

		Assert.assertEquals(socialActivityAchievement.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SocialActivityAchievement newSocialActivityAchievement = addSocialActivityAchievement();

		_persistence.remove(newSocialActivityAchievement);

		SocialActivityAchievement existingSocialActivityAchievement = _persistence.fetchByPrimaryKey(newSocialActivityAchievement.getPrimaryKey());

		Assert.assertNull(existingSocialActivityAchievement);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSocialActivityAchievement();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivityAchievement newSocialActivityAchievement = _persistence.create(pk);

		newSocialActivityAchievement.setGroupId(ServiceTestUtil.nextLong());

		newSocialActivityAchievement.setCompanyId(ServiceTestUtil.nextLong());

		newSocialActivityAchievement.setUserId(ServiceTestUtil.nextLong());

		newSocialActivityAchievement.setCreateDate(ServiceTestUtil.nextLong());

		newSocialActivityAchievement.setName(ServiceTestUtil.randomString());

		newSocialActivityAchievement.setFirstInGroup(ServiceTestUtil.randomBoolean());

		_persistence.update(newSocialActivityAchievement);

		SocialActivityAchievement existingSocialActivityAchievement = _persistence.findByPrimaryKey(newSocialActivityAchievement.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityAchievement.getActivityAchievementId(),
			newSocialActivityAchievement.getActivityAchievementId());
		Assert.assertEquals(existingSocialActivityAchievement.getGroupId(),
			newSocialActivityAchievement.getGroupId());
		Assert.assertEquals(existingSocialActivityAchievement.getCompanyId(),
			newSocialActivityAchievement.getCompanyId());
		Assert.assertEquals(existingSocialActivityAchievement.getUserId(),
			newSocialActivityAchievement.getUserId());
		Assert.assertEquals(existingSocialActivityAchievement.getCreateDate(),
			newSocialActivityAchievement.getCreateDate());
		Assert.assertEquals(existingSocialActivityAchievement.getName(),
			newSocialActivityAchievement.getName());
		Assert.assertEquals(existingSocialActivityAchievement.getFirstInGroup(),
			newSocialActivityAchievement.getFirstInGroup());
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
	public void testCountByG_U() {
		try {
			_persistence.countByG_U(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong());

			_persistence.countByG_U(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_N() {
		try {
			_persistence.countByG_N(ServiceTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_N(0L, StringPool.NULL);

			_persistence.countByG_N(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F() {
		try {
			_persistence.countByG_F(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean());

			_persistence.countByG_F(0L, ServiceTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_N() {
		try {
			_persistence.countByG_U_N(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_U_N(0L, 0L, StringPool.NULL);

			_persistence.countByG_U_N(0L, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_F() {
		try {
			_persistence.countByG_U_F(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.randomBoolean());

			_persistence.countByG_U_F(0L, 0L, ServiceTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialActivityAchievement newSocialActivityAchievement = addSocialActivityAchievement();

		SocialActivityAchievement existingSocialActivityAchievement = _persistence.findByPrimaryKey(newSocialActivityAchievement.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityAchievement,
			newSocialActivityAchievement);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchActivityAchievementException");
		}
		catch (NoSuchActivityAchievementException nsee) {
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
		return OrderByComparatorFactoryUtil.create("SocialActivityAchievement",
			"activityAchievementId", true, "groupId", true, "companyId", true,
			"userId", true, "createDate", true, "name", true, "firstInGroup",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialActivityAchievement newSocialActivityAchievement = addSocialActivityAchievement();

		SocialActivityAchievement existingSocialActivityAchievement = _persistence.fetchByPrimaryKey(newSocialActivityAchievement.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityAchievement,
			newSocialActivityAchievement);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivityAchievement missingSocialActivityAchievement = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSocialActivityAchievement);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new SocialActivityAchievementActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					SocialActivityAchievement socialActivityAchievement = (SocialActivityAchievement)object;

					Assert.assertNotNull(socialActivityAchievement);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialActivityAchievement newSocialActivityAchievement = addSocialActivityAchievement();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityAchievement.class,
				SocialActivityAchievement.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityAchievementId",
				newSocialActivityAchievement.getActivityAchievementId()));

		List<SocialActivityAchievement> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SocialActivityAchievement existingSocialActivityAchievement = result.get(0);

		Assert.assertEquals(existingSocialActivityAchievement,
			newSocialActivityAchievement);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityAchievement.class,
				SocialActivityAchievement.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityAchievementId",
				ServiceTestUtil.nextLong()));

		List<SocialActivityAchievement> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialActivityAchievement newSocialActivityAchievement = addSocialActivityAchievement();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityAchievement.class,
				SocialActivityAchievement.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activityAchievementId"));

		Object newActivityAchievementId = newSocialActivityAchievement.getActivityAchievementId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityAchievementId",
				new Object[] { newActivityAchievementId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingActivityAchievementId = result.get(0);

		Assert.assertEquals(existingActivityAchievementId,
			newActivityAchievementId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityAchievement.class,
				SocialActivityAchievement.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activityAchievementId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityAchievementId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SocialActivityAchievement newSocialActivityAchievement = addSocialActivityAchievement();

		_persistence.clearCache();

		SocialActivityAchievementModelImpl existingSocialActivityAchievementModelImpl =
			(SocialActivityAchievementModelImpl)_persistence.findByPrimaryKey(newSocialActivityAchievement.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityAchievementModelImpl.getGroupId(),
			existingSocialActivityAchievementModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingSocialActivityAchievementModelImpl.getUserId(),
			existingSocialActivityAchievementModelImpl.getOriginalUserId());
		Assert.assertTrue(Validator.equals(
				existingSocialActivityAchievementModelImpl.getName(),
				existingSocialActivityAchievementModelImpl.getOriginalName()));
	}

	protected SocialActivityAchievement addSocialActivityAchievement()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialActivityAchievement socialActivityAchievement = _persistence.create(pk);

		socialActivityAchievement.setGroupId(ServiceTestUtil.nextLong());

		socialActivityAchievement.setCompanyId(ServiceTestUtil.nextLong());

		socialActivityAchievement.setUserId(ServiceTestUtil.nextLong());

		socialActivityAchievement.setCreateDate(ServiceTestUtil.nextLong());

		socialActivityAchievement.setName(ServiceTestUtil.randomString());

		socialActivityAchievement.setFirstInGroup(ServiceTestUtil.randomBoolean());

		_persistence.update(socialActivityAchievement);

		return socialActivityAchievement;
	}

	private static Log _log = LogFactoryUtil.getLog(SocialActivityAchievementPersistenceTest.class);
	private ModelListener<SocialActivityAchievement>[] _modelListeners;
	private SocialActivityAchievementPersistence _persistence = (SocialActivityAchievementPersistence)PortalBeanLocatorUtil.locate(SocialActivityAchievementPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}