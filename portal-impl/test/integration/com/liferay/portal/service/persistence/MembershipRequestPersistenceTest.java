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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchMembershipRequestException;
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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class MembershipRequestPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<MembershipRequest> modelListener : _modelListeners) {
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

		for (ModelListener<MembershipRequest> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MembershipRequest membershipRequest = _persistence.create(pk);

		Assert.assertNotNull(membershipRequest);

		Assert.assertEquals(membershipRequest.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MembershipRequest newMembershipRequest = addMembershipRequest();

		_persistence.remove(newMembershipRequest);

		MembershipRequest existingMembershipRequest = _persistence.fetchByPrimaryKey(newMembershipRequest.getPrimaryKey());

		Assert.assertNull(existingMembershipRequest);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMembershipRequest();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MembershipRequest newMembershipRequest = _persistence.create(pk);

		newMembershipRequest.setMvccVersion(RandomTestUtil.nextLong());

		newMembershipRequest.setGroupId(RandomTestUtil.nextLong());

		newMembershipRequest.setCompanyId(RandomTestUtil.nextLong());

		newMembershipRequest.setUserId(RandomTestUtil.nextLong());

		newMembershipRequest.setCreateDate(RandomTestUtil.nextDate());

		newMembershipRequest.setComments(RandomTestUtil.randomString());

		newMembershipRequest.setReplyComments(RandomTestUtil.randomString());

		newMembershipRequest.setReplyDate(RandomTestUtil.nextDate());

		newMembershipRequest.setReplierUserId(RandomTestUtil.nextLong());

		newMembershipRequest.setStatusId(RandomTestUtil.nextInt());

		_persistence.update(newMembershipRequest);

		MembershipRequest existingMembershipRequest = _persistence.findByPrimaryKey(newMembershipRequest.getPrimaryKey());

		Assert.assertEquals(existingMembershipRequest.getMvccVersion(),
			newMembershipRequest.getMvccVersion());
		Assert.assertEquals(existingMembershipRequest.getMembershipRequestId(),
			newMembershipRequest.getMembershipRequestId());
		Assert.assertEquals(existingMembershipRequest.getGroupId(),
			newMembershipRequest.getGroupId());
		Assert.assertEquals(existingMembershipRequest.getCompanyId(),
			newMembershipRequest.getCompanyId());
		Assert.assertEquals(existingMembershipRequest.getUserId(),
			newMembershipRequest.getUserId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMembershipRequest.getCreateDate()),
			Time.getShortTimestamp(newMembershipRequest.getCreateDate()));
		Assert.assertEquals(existingMembershipRequest.getComments(),
			newMembershipRequest.getComments());
		Assert.assertEquals(existingMembershipRequest.getReplyComments(),
			newMembershipRequest.getReplyComments());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMembershipRequest.getReplyDate()),
			Time.getShortTimestamp(newMembershipRequest.getReplyDate()));
		Assert.assertEquals(existingMembershipRequest.getReplierUserId(),
			newMembershipRequest.getReplierUserId());
		Assert.assertEquals(existingMembershipRequest.getStatusId(),
			newMembershipRequest.getStatusId());
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(RandomTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUserId() {
		try {
			_persistence.countByUserId(RandomTestUtil.nextLong());

			_persistence.countByUserId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_S() {
		try {
			_persistence.countByG_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByG_S(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_S() {
		try {
			_persistence.countByG_U_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

			_persistence.countByG_U_S(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MembershipRequest newMembershipRequest = addMembershipRequest();

		MembershipRequest existingMembershipRequest = _persistence.findByPrimaryKey(newMembershipRequest.getPrimaryKey());

		Assert.assertEquals(existingMembershipRequest, newMembershipRequest);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchMembershipRequestException");
		}
		catch (NoSuchMembershipRequestException nsee) {
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
		return OrderByComparatorFactoryUtil.create("MembershipRequest",
			"mvccVersion", true, "membershipRequestId", true, "groupId", true,
			"companyId", true, "userId", true, "createDate", true, "comments",
			true, "replyComments", true, "replyDate", true, "replierUserId",
			true, "statusId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MembershipRequest newMembershipRequest = addMembershipRequest();

		MembershipRequest existingMembershipRequest = _persistence.fetchByPrimaryKey(newMembershipRequest.getPrimaryKey());

		Assert.assertEquals(existingMembershipRequest, newMembershipRequest);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MembershipRequest missingMembershipRequest = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMembershipRequest);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		MembershipRequest newMembershipRequest1 = addMembershipRequest();
		MembershipRequest newMembershipRequest2 = addMembershipRequest();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMembershipRequest1.getPrimaryKey());
		primaryKeys.add(newMembershipRequest2.getPrimaryKey());

		Map<Serializable, MembershipRequest> membershipRequests = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, membershipRequests.size());
		Assert.assertEquals(newMembershipRequest1,
			membershipRequests.get(newMembershipRequest1.getPrimaryKey()));
		Assert.assertEquals(newMembershipRequest2,
			membershipRequests.get(newMembershipRequest2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, MembershipRequest> membershipRequests = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(membershipRequests.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		MembershipRequest newMembershipRequest = addMembershipRequest();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMembershipRequest.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, MembershipRequest> membershipRequests = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, membershipRequests.size());
		Assert.assertEquals(newMembershipRequest,
			membershipRequests.get(newMembershipRequest.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, MembershipRequest> membershipRequests = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(membershipRequests.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		MembershipRequest newMembershipRequest = addMembershipRequest();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMembershipRequest.getPrimaryKey());

		Map<Serializable, MembershipRequest> membershipRequests = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, membershipRequests.size());
		Assert.assertEquals(newMembershipRequest,
			membershipRequests.get(newMembershipRequest.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = MembershipRequestLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					MembershipRequest membershipRequest = (MembershipRequest)object;

					Assert.assertNotNull(membershipRequest);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MembershipRequest newMembershipRequest = addMembershipRequest();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MembershipRequest.class,
				MembershipRequest.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("membershipRequestId",
				newMembershipRequest.getMembershipRequestId()));

		List<MembershipRequest> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MembershipRequest existingMembershipRequest = result.get(0);

		Assert.assertEquals(existingMembershipRequest, newMembershipRequest);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MembershipRequest.class,
				MembershipRequest.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("membershipRequestId",
				RandomTestUtil.nextLong()));

		List<MembershipRequest> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MembershipRequest newMembershipRequest = addMembershipRequest();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MembershipRequest.class,
				MembershipRequest.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"membershipRequestId"));

		Object newMembershipRequestId = newMembershipRequest.getMembershipRequestId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("membershipRequestId",
				new Object[] { newMembershipRequestId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingMembershipRequestId = result.get(0);

		Assert.assertEquals(existingMembershipRequestId, newMembershipRequestId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MembershipRequest.class,
				MembershipRequest.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"membershipRequestId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("membershipRequestId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected MembershipRequest addMembershipRequest()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		MembershipRequest membershipRequest = _persistence.create(pk);

		membershipRequest.setMvccVersion(RandomTestUtil.nextLong());

		membershipRequest.setGroupId(RandomTestUtil.nextLong());

		membershipRequest.setCompanyId(RandomTestUtil.nextLong());

		membershipRequest.setUserId(RandomTestUtil.nextLong());

		membershipRequest.setCreateDate(RandomTestUtil.nextDate());

		membershipRequest.setComments(RandomTestUtil.randomString());

		membershipRequest.setReplyComments(RandomTestUtil.randomString());

		membershipRequest.setReplyDate(RandomTestUtil.nextDate());

		membershipRequest.setReplierUserId(RandomTestUtil.nextLong());

		membershipRequest.setStatusId(RandomTestUtil.nextInt());

		_persistence.update(membershipRequest);

		return membershipRequest;
	}

	private static Log _log = LogFactoryUtil.getLog(MembershipRequestPersistenceTest.class);
	private ModelListener<MembershipRequest>[] _modelListeners;
	private MembershipRequestPersistence _persistence = (MembershipRequestPersistence)PortalBeanLocatorUtil.locate(MembershipRequestPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}