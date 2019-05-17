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

package com.liferay.portlet.social.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.social.kernel.exception.NoSuchRequestException;
import com.liferay.social.kernel.model.SocialRequest;
import com.liferay.social.kernel.service.SocialRequestLocalServiceUtil;
import com.liferay.social.kernel.service.persistence.SocialRequestPersistence;
import com.liferay.social.kernel.service.persistence.SocialRequestUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class SocialRequestPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = SocialRequestUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SocialRequest> iterator = _socialRequests.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialRequest socialRequest = _persistence.create(pk);

		Assert.assertNotNull(socialRequest);

		Assert.assertEquals(socialRequest.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SocialRequest newSocialRequest = addSocialRequest();

		_persistence.remove(newSocialRequest);

		SocialRequest existingSocialRequest = _persistence.fetchByPrimaryKey(
			newSocialRequest.getPrimaryKey());

		Assert.assertNull(existingSocialRequest);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSocialRequest();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialRequest newSocialRequest = _persistence.create(pk);

		newSocialRequest.setUuid(RandomTestUtil.randomString());

		newSocialRequest.setGroupId(RandomTestUtil.nextLong());

		newSocialRequest.setCompanyId(RandomTestUtil.nextLong());

		newSocialRequest.setUserId(RandomTestUtil.nextLong());

		newSocialRequest.setCreateDate(RandomTestUtil.nextLong());

		newSocialRequest.setModifiedDate(RandomTestUtil.nextLong());

		newSocialRequest.setClassNameId(RandomTestUtil.nextLong());

		newSocialRequest.setClassPK(RandomTestUtil.nextLong());

		newSocialRequest.setType(RandomTestUtil.nextInt());

		newSocialRequest.setExtraData(RandomTestUtil.randomString());

		newSocialRequest.setReceiverUserId(RandomTestUtil.nextLong());

		newSocialRequest.setStatus(RandomTestUtil.nextInt());

		_socialRequests.add(_persistence.update(newSocialRequest));

		SocialRequest existingSocialRequest = _persistence.findByPrimaryKey(
			newSocialRequest.getPrimaryKey());

		Assert.assertEquals(
			existingSocialRequest.getUuid(), newSocialRequest.getUuid());
		Assert.assertEquals(
			existingSocialRequest.getRequestId(),
			newSocialRequest.getRequestId());
		Assert.assertEquals(
			existingSocialRequest.getGroupId(), newSocialRequest.getGroupId());
		Assert.assertEquals(
			existingSocialRequest.getCompanyId(),
			newSocialRequest.getCompanyId());
		Assert.assertEquals(
			existingSocialRequest.getUserId(), newSocialRequest.getUserId());
		Assert.assertEquals(
			existingSocialRequest.getCreateDate(),
			newSocialRequest.getCreateDate());
		Assert.assertEquals(
			existingSocialRequest.getModifiedDate(),
			newSocialRequest.getModifiedDate());
		Assert.assertEquals(
			existingSocialRequest.getClassNameId(),
			newSocialRequest.getClassNameId());
		Assert.assertEquals(
			existingSocialRequest.getClassPK(), newSocialRequest.getClassPK());
		Assert.assertEquals(
			existingSocialRequest.getType(), newSocialRequest.getType());
		Assert.assertEquals(
			existingSocialRequest.getExtraData(),
			newSocialRequest.getExtraData());
		Assert.assertEquals(
			existingSocialRequest.getReceiverUserId(),
			newSocialRequest.getReceiverUserId());
		Assert.assertEquals(
			existingSocialRequest.getStatus(), newSocialRequest.getStatus());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(RandomTestUtil.nextLong());

		_persistence.countByUserId(0L);
	}

	@Test
	public void testCountByReceiverUserId() throws Exception {
		_persistence.countByReceiverUserId(RandomTestUtil.nextLong());

		_persistence.countByReceiverUserId(0L);
	}

	@Test
	public void testCountByU_S() throws Exception {
		_persistence.countByU_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByU_S(0L, 0);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testCountByR_S() throws Exception {
		_persistence.countByR_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByR_S(0L, 0);
	}

	@Test
	public void testCountByU_C_C_T_R() throws Exception {
		_persistence.countByU_C_C_T_R(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextLong());

		_persistence.countByU_C_C_T_R(0L, 0L, 0L, 0, 0L);
	}

	@Test
	public void testCountByU_C_C_T_S() throws Exception {
		_persistence.countByU_C_C_T_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt());

		_persistence.countByU_C_C_T_S(0L, 0L, 0L, 0, 0);
	}

	@Test
	public void testCountByC_C_T_R_S() throws Exception {
		_persistence.countByC_C_T_R_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByC_C_T_R_S(0L, 0L, 0, 0L, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialRequest newSocialRequest = addSocialRequest();

		SocialRequest existingSocialRequest = _persistence.findByPrimaryKey(
			newSocialRequest.getPrimaryKey());

		Assert.assertEquals(existingSocialRequest, newSocialRequest);
	}

	@Test(expected = NoSuchRequestException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<SocialRequest> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"SocialRequest", "uuid", true, "requestId", true, "groupId", true,
			"companyId", true, "userId", true, "createDate", true,
			"modifiedDate", true, "classNameId", true, "classPK", true, "type",
			true, "extraData", true, "receiverUserId", true, "status", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialRequest newSocialRequest = addSocialRequest();

		SocialRequest existingSocialRequest = _persistence.fetchByPrimaryKey(
			newSocialRequest.getPrimaryKey());

		Assert.assertEquals(existingSocialRequest, newSocialRequest);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialRequest missingSocialRequest = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSocialRequest);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		SocialRequest newSocialRequest1 = addSocialRequest();
		SocialRequest newSocialRequest2 = addSocialRequest();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSocialRequest1.getPrimaryKey());
		primaryKeys.add(newSocialRequest2.getPrimaryKey());

		Map<Serializable, SocialRequest> socialRequests =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, socialRequests.size());
		Assert.assertEquals(
			newSocialRequest1,
			socialRequests.get(newSocialRequest1.getPrimaryKey()));
		Assert.assertEquals(
			newSocialRequest2,
			socialRequests.get(newSocialRequest2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SocialRequest> socialRequests =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(socialRequests.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		SocialRequest newSocialRequest = addSocialRequest();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSocialRequest.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SocialRequest> socialRequests =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, socialRequests.size());
		Assert.assertEquals(
			newSocialRequest,
			socialRequests.get(newSocialRequest.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SocialRequest> socialRequests =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(socialRequests.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		SocialRequest newSocialRequest = addSocialRequest();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSocialRequest.getPrimaryKey());

		Map<Serializable, SocialRequest> socialRequests =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, socialRequests.size());
		Assert.assertEquals(
			newSocialRequest,
			socialRequests.get(newSocialRequest.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			SocialRequestLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<SocialRequest>() {

				@Override
				public void performAction(SocialRequest socialRequest) {
					Assert.assertNotNull(socialRequest);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		SocialRequest newSocialRequest = addSocialRequest();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SocialRequest.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"requestId", newSocialRequest.getRequestId()));

		List<SocialRequest> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		SocialRequest existingSocialRequest = result.get(0);

		Assert.assertEquals(existingSocialRequest, newSocialRequest);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SocialRequest.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("requestId", RandomTestUtil.nextLong()));

		List<SocialRequest> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		SocialRequest newSocialRequest = addSocialRequest();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SocialRequest.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("requestId"));

		Object newRequestId = newSocialRequest.getRequestId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"requestId", new Object[] {newRequestId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRequestId = result.get(0);

		Assert.assertEquals(existingRequestId, newRequestId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SocialRequest.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("requestId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"requestId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		SocialRequest newSocialRequest = addSocialRequest();

		_persistence.clearCache();

		SocialRequest existingSocialRequest = _persistence.findByPrimaryKey(
			newSocialRequest.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingSocialRequest.getUuid(),
				ReflectionTestUtil.invoke(
					existingSocialRequest, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingSocialRequest.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingSocialRequest, "getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingSocialRequest.getUserId()),
			ReflectionTestUtil.<Long>invoke(
				existingSocialRequest, "getOriginalUserId", new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingSocialRequest.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(
				existingSocialRequest, "getOriginalClassNameId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingSocialRequest.getClassPK()),
			ReflectionTestUtil.<Long>invoke(
				existingSocialRequest, "getOriginalClassPK", new Class<?>[0]));
		Assert.assertEquals(
			Integer.valueOf(existingSocialRequest.getType()),
			ReflectionTestUtil.<Integer>invoke(
				existingSocialRequest, "getOriginalType", new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingSocialRequest.getReceiverUserId()),
			ReflectionTestUtil.<Long>invoke(
				existingSocialRequest, "getOriginalReceiverUserId",
				new Class<?>[0]));
	}

	protected SocialRequest addSocialRequest() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialRequest socialRequest = _persistence.create(pk);

		socialRequest.setUuid(RandomTestUtil.randomString());

		socialRequest.setGroupId(RandomTestUtil.nextLong());

		socialRequest.setCompanyId(RandomTestUtil.nextLong());

		socialRequest.setUserId(RandomTestUtil.nextLong());

		socialRequest.setCreateDate(RandomTestUtil.nextLong());

		socialRequest.setModifiedDate(RandomTestUtil.nextLong());

		socialRequest.setClassNameId(RandomTestUtil.nextLong());

		socialRequest.setClassPK(RandomTestUtil.nextLong());

		socialRequest.setType(RandomTestUtil.nextInt());

		socialRequest.setExtraData(RandomTestUtil.randomString());

		socialRequest.setReceiverUserId(RandomTestUtil.nextLong());

		socialRequest.setStatus(RandomTestUtil.nextInt());

		_socialRequests.add(_persistence.update(socialRequest));

		return socialRequest;
	}

	private List<SocialRequest> _socialRequests =
		new ArrayList<SocialRequest>();
	private SocialRequestPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}