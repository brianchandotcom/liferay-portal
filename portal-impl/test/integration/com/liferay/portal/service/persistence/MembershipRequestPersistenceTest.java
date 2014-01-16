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
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;

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
public class MembershipRequestPersistenceTest {
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
		long pk = ServiceTestUtil.nextLong();

		MembershipRequest newMembershipRequest = _persistence.create(pk);

		newMembershipRequest.setGroupId(ServiceTestUtil.nextLong());

		newMembershipRequest.setCompanyId(ServiceTestUtil.nextLong());

		newMembershipRequest.setUserId(ServiceTestUtil.nextLong());

		newMembershipRequest.setCreateDate(ServiceTestUtil.nextDate());

		newMembershipRequest.setComments(ServiceTestUtil.randomString());

		newMembershipRequest.setReplyComments(ServiceTestUtil.randomString());

		newMembershipRequest.setReplyDate(ServiceTestUtil.nextDate());

		newMembershipRequest.setReplierUserId(ServiceTestUtil.nextLong());

		newMembershipRequest.setStatusId(ServiceTestUtil.nextInt());

		newMembershipRequest.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newMembershipRequest);

		MembershipRequest existingMembershipRequest = _persistence.findByPrimaryKey(newMembershipRequest.getPrimaryKey());

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
		Assert.assertEquals(existingMembershipRequest.getMvccVersion(),
			newMembershipRequest.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MembershipRequest newMembershipRequest = addMembershipRequest();

		MembershipRequest existingMembershipRequest = _persistence.findByPrimaryKey(newMembershipRequest.getPrimaryKey());

		Assert.assertEquals(existingMembershipRequest, newMembershipRequest);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

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
			"membershipRequestId", true, "groupId", true, "companyId", true,
			"userId", true, "createDate", true, "comments", true,
			"replyComments", true, "replyDate", true, "replierUserId", true,
			"statusId", true, "mvccVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MembershipRequest newMembershipRequest = addMembershipRequest();

		MembershipRequest existingMembershipRequest = _persistence.fetchByPrimaryKey(newMembershipRequest.getPrimaryKey());

		Assert.assertEquals(existingMembershipRequest, newMembershipRequest);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MembershipRequest missingMembershipRequest = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMembershipRequest);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new MembershipRequestActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					MembershipRequest membershipRequest = (MembershipRequest)object;

					Assert.assertNotNull(membershipRequest);

					count.increment();
				}
			};

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
				ServiceTestUtil.nextLong()));

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
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected MembershipRequest addMembershipRequest()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MembershipRequest membershipRequest = _persistence.create(pk);

		membershipRequest.setGroupId(ServiceTestUtil.nextLong());

		membershipRequest.setCompanyId(ServiceTestUtil.nextLong());

		membershipRequest.setUserId(ServiceTestUtil.nextLong());

		membershipRequest.setCreateDate(ServiceTestUtil.nextDate());

		membershipRequest.setComments(ServiceTestUtil.randomString());

		membershipRequest.setReplyComments(ServiceTestUtil.randomString());

		membershipRequest.setReplyDate(ServiceTestUtil.nextDate());

		membershipRequest.setReplierUserId(ServiceTestUtil.nextLong());

		membershipRequest.setStatusId(ServiceTestUtil.nextInt());

		membershipRequest.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(membershipRequest);

		return membershipRequest;
	}

	private static Log _log = LogFactoryUtil.getLog(MembershipRequestPersistenceTest.class);
	private MembershipRequestPersistence _persistence = (MembershipRequestPersistence)PortalBeanLocatorUtil.locate(MembershipRequestPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}