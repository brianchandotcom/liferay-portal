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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.social.NoSuchRelationException;
import com.liferay.portlet.social.model.SocialRelation;
import com.liferay.portlet.social.model.impl.SocialRelationModelImpl;

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
public class SocialRelationPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<SocialRelation> modelListener : _modelListeners) {
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

		for (ModelListener<SocialRelation> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialRelation socialRelation = _persistence.create(pk);

		Assert.assertNotNull(socialRelation);

		Assert.assertEquals(socialRelation.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		_persistence.remove(newSocialRelation);

		SocialRelation existingSocialRelation = _persistence.fetchByPrimaryKey(newSocialRelation.getPrimaryKey());

		Assert.assertNull(existingSocialRelation);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSocialRelation();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialRelation newSocialRelation = _persistence.create(pk);

		newSocialRelation.setUuid(ServiceTestUtil.randomString());

		newSocialRelation.setCompanyId(ServiceTestUtil.nextLong());

		newSocialRelation.setCreateDate(ServiceTestUtil.nextLong());

		newSocialRelation.setUserId1(ServiceTestUtil.nextLong());

		newSocialRelation.setUserId2(ServiceTestUtil.nextLong());

		newSocialRelation.setType(ServiceTestUtil.nextInt());

		_persistence.update(newSocialRelation);

		SocialRelation existingSocialRelation = _persistence.findByPrimaryKey(newSocialRelation.getPrimaryKey());

		Assert.assertEquals(existingSocialRelation.getUuid(),
			newSocialRelation.getUuid());
		Assert.assertEquals(existingSocialRelation.getRelationId(),
			newSocialRelation.getRelationId());
		Assert.assertEquals(existingSocialRelation.getCompanyId(),
			newSocialRelation.getCompanyId());
		Assert.assertEquals(existingSocialRelation.getCreateDate(),
			newSocialRelation.getCreateDate());
		Assert.assertEquals(existingSocialRelation.getUserId1(),
			newSocialRelation.getUserId1());
		Assert.assertEquals(existingSocialRelation.getUserId2(),
			newSocialRelation.getUserId2());
		Assert.assertEquals(existingSocialRelation.getType(),
			newSocialRelation.getType());
	}

	@Test
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				ServiceTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(ServiceTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUserId1() {
		try {
			_persistence.countByUserId1(ServiceTestUtil.nextLong());

			_persistence.countByUserId1(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUserId2() {
		try {
			_persistence.countByUserId2(ServiceTestUtil.nextLong());

			_persistence.countByUserId2(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByType() {
		try {
			_persistence.countByType(ServiceTestUtil.nextInt());

			_persistence.countByType(0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_T() {
		try {
			_persistence.countByC_T(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextInt());

			_persistence.countByC_T(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByU1_U2() {
		try {
			_persistence.countByU1_U2(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong());

			_persistence.countByU1_U2(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByU1_T() {
		try {
			_persistence.countByU1_T(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextInt());

			_persistence.countByU1_T(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByU2_T() {
		try {
			_persistence.countByU2_T(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextInt());

			_persistence.countByU2_T(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByU1_U2_T() {
		try {
			_persistence.countByU1_U2_T(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextInt());

			_persistence.countByU1_U2_T(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		SocialRelation existingSocialRelation = _persistence.findByPrimaryKey(newSocialRelation.getPrimaryKey());

		Assert.assertEquals(existingSocialRelation, newSocialRelation);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchRelationException");
		}
		catch (NoSuchRelationException nsee) {
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
		return OrderByComparatorFactoryUtil.create("SocialRelation", "uuid",
			true, "relationId", true, "companyId", true, "createDate", true,
			"userId1", true, "userId2", true, "type", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		SocialRelation existingSocialRelation = _persistence.fetchByPrimaryKey(newSocialRelation.getPrimaryKey());

		Assert.assertEquals(existingSocialRelation, newSocialRelation);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialRelation missingSocialRelation = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSocialRelation);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new SocialRelationActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					SocialRelation socialRelation = (SocialRelation)object;

					Assert.assertNotNull(socialRelation);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRelation.class,
				SocialRelation.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("relationId",
				newSocialRelation.getRelationId()));

		List<SocialRelation> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SocialRelation existingSocialRelation = result.get(0);

		Assert.assertEquals(existingSocialRelation, newSocialRelation);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRelation.class,
				SocialRelation.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("relationId",
				ServiceTestUtil.nextLong()));

		List<SocialRelation> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRelation.class,
				SocialRelation.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("relationId"));

		Object newRelationId = newSocialRelation.getRelationId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("relationId",
				new Object[] { newRelationId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRelationId = result.get(0);

		Assert.assertEquals(existingRelationId, newRelationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRelation.class,
				SocialRelation.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("relationId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("relationId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SocialRelation newSocialRelation = addSocialRelation();

		_persistence.clearCache();

		SocialRelationModelImpl existingSocialRelationModelImpl = (SocialRelationModelImpl)_persistence.findByPrimaryKey(newSocialRelation.getPrimaryKey());

		Assert.assertEquals(existingSocialRelationModelImpl.getUserId1(),
			existingSocialRelationModelImpl.getOriginalUserId1());
		Assert.assertEquals(existingSocialRelationModelImpl.getUserId2(),
			existingSocialRelationModelImpl.getOriginalUserId2());
		Assert.assertEquals(existingSocialRelationModelImpl.getType(),
			existingSocialRelationModelImpl.getOriginalType());
	}

	protected SocialRelation addSocialRelation() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialRelation socialRelation = _persistence.create(pk);

		socialRelation.setUuid(ServiceTestUtil.randomString());

		socialRelation.setCompanyId(ServiceTestUtil.nextLong());

		socialRelation.setCreateDate(ServiceTestUtil.nextLong());

		socialRelation.setUserId1(ServiceTestUtil.nextLong());

		socialRelation.setUserId2(ServiceTestUtil.nextLong());

		socialRelation.setType(ServiceTestUtil.nextInt());

		_persistence.update(socialRelation);

		return socialRelation;
	}

	private static Log _log = LogFactoryUtil.getLog(SocialRelationPersistenceTest.class);
	private ModelListener<SocialRelation>[] _modelListeners;
	private SocialRelationPersistence _persistence = (SocialRelationPersistence)PortalBeanLocatorUtil.locate(SocialRelationPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}