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

package com.liferay.portlet.messageboards.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.portlet.messageboards.NoSuchMailingListException;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.service.MBMailingListLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMailingListPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBMailingListUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class MBMailingListPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = MBMailingListUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<MBMailingList> iterator = _mbMailingLists.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBMailingList mbMailingList = _persistence.create(pk);

		Assert.assertNotNull(mbMailingList);

		Assert.assertEquals(mbMailingList.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MBMailingList newMBMailingList = addMBMailingList();

		_persistence.remove(newMBMailingList);

		MBMailingList existingMBMailingList = _persistence.fetchByPrimaryKey(newMBMailingList.getPrimaryKey());

		Assert.assertNull(existingMBMailingList);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMBMailingList();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBMailingList newMBMailingList = _persistence.create(pk);

		newMBMailingList.setUuid(RandomTestUtil.randomString());

		newMBMailingList.setGroupId(RandomTestUtil.nextLong());

		newMBMailingList.setCompanyId(RandomTestUtil.nextLong());

		newMBMailingList.setUserId(RandomTestUtil.nextLong());

		newMBMailingList.setUserName(RandomTestUtil.randomString());

		newMBMailingList.setCreateDate(RandomTestUtil.nextDate());

		newMBMailingList.setModifiedDate(RandomTestUtil.nextDate());

		newMBMailingList.setCategoryId(RandomTestUtil.nextLong());

		newMBMailingList.setEmailAddress(RandomTestUtil.randomString());

		newMBMailingList.setInProtocol(RandomTestUtil.randomString());

		newMBMailingList.setInServerName(RandomTestUtil.randomString());

		newMBMailingList.setInServerPort(RandomTestUtil.nextInt());

		newMBMailingList.setInUseSSL(RandomTestUtil.randomBoolean());

		newMBMailingList.setInUserName(RandomTestUtil.randomString());

		newMBMailingList.setInPassword(RandomTestUtil.randomString());

		newMBMailingList.setInReadInterval(RandomTestUtil.nextInt());

		newMBMailingList.setOutEmailAddress(RandomTestUtil.randomString());

		newMBMailingList.setOutCustom(RandomTestUtil.randomBoolean());

		newMBMailingList.setOutServerName(RandomTestUtil.randomString());

		newMBMailingList.setOutServerPort(RandomTestUtil.nextInt());

		newMBMailingList.setOutUseSSL(RandomTestUtil.randomBoolean());

		newMBMailingList.setOutUserName(RandomTestUtil.randomString());

		newMBMailingList.setOutPassword(RandomTestUtil.randomString());

		newMBMailingList.setAllowAnonymous(RandomTestUtil.randomBoolean());

		newMBMailingList.setActive(RandomTestUtil.randomBoolean());

		_mbMailingLists.add(_persistence.update(newMBMailingList));

		MBMailingList existingMBMailingList = _persistence.findByPrimaryKey(newMBMailingList.getPrimaryKey());

		Assert.assertEquals(existingMBMailingList.getUuid(),
			newMBMailingList.getUuid());
		Assert.assertEquals(existingMBMailingList.getMailingListId(),
			newMBMailingList.getMailingListId());
		Assert.assertEquals(existingMBMailingList.getGroupId(),
			newMBMailingList.getGroupId());
		Assert.assertEquals(existingMBMailingList.getCompanyId(),
			newMBMailingList.getCompanyId());
		Assert.assertEquals(existingMBMailingList.getUserId(),
			newMBMailingList.getUserId());
		Assert.assertEquals(existingMBMailingList.getUserName(),
			newMBMailingList.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBMailingList.getCreateDate()),
			Time.getShortTimestamp(newMBMailingList.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBMailingList.getModifiedDate()),
			Time.getShortTimestamp(newMBMailingList.getModifiedDate()));
		Assert.assertEquals(existingMBMailingList.getCategoryId(),
			newMBMailingList.getCategoryId());
		Assert.assertEquals(existingMBMailingList.getEmailAddress(),
			newMBMailingList.getEmailAddress());
		Assert.assertEquals(existingMBMailingList.getInProtocol(),
			newMBMailingList.getInProtocol());
		Assert.assertEquals(existingMBMailingList.getInServerName(),
			newMBMailingList.getInServerName());
		Assert.assertEquals(existingMBMailingList.getInServerPort(),
			newMBMailingList.getInServerPort());
		Assert.assertEquals(existingMBMailingList.getInUseSSL(),
			newMBMailingList.getInUseSSL());
		Assert.assertEquals(existingMBMailingList.getInUserName(),
			newMBMailingList.getInUserName());
		Assert.assertEquals(existingMBMailingList.getInPassword(),
			newMBMailingList.getInPassword());
		Assert.assertEquals(existingMBMailingList.getInReadInterval(),
			newMBMailingList.getInReadInterval());
		Assert.assertEquals(existingMBMailingList.getOutEmailAddress(),
			newMBMailingList.getOutEmailAddress());
		Assert.assertEquals(existingMBMailingList.getOutCustom(),
			newMBMailingList.getOutCustom());
		Assert.assertEquals(existingMBMailingList.getOutServerName(),
			newMBMailingList.getOutServerName());
		Assert.assertEquals(existingMBMailingList.getOutServerPort(),
			newMBMailingList.getOutServerPort());
		Assert.assertEquals(existingMBMailingList.getOutUseSSL(),
			newMBMailingList.getOutUseSSL());
		Assert.assertEquals(existingMBMailingList.getOutUserName(),
			newMBMailingList.getOutUserName());
		Assert.assertEquals(existingMBMailingList.getOutPassword(),
			newMBMailingList.getOutPassword());
		Assert.assertEquals(existingMBMailingList.getAllowAnonymous(),
			newMBMailingList.getAllowAnonymous());
		Assert.assertEquals(existingMBMailingList.getActive(),
			newMBMailingList.getActive());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUUID_G(StringPool.NULL, 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByActive() throws Exception {
		_persistence.countByActive(RandomTestUtil.randomBoolean());

		_persistence.countByActive(RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_C() throws Exception {
		_persistence.countByG_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_C(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MBMailingList newMBMailingList = addMBMailingList();

		MBMailingList existingMBMailingList = _persistence.findByPrimaryKey(newMBMailingList.getPrimaryKey());

		Assert.assertEquals(existingMBMailingList, newMBMailingList);
	}

	@Test(expected = NoSuchMailingListException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<MBMailingList> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("MBMailingList", "uuid",
			true, "mailingListId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "categoryId", true, "emailAddress", true,
			"inProtocol", true, "inServerName", true, "inServerPort", true,
			"inUseSSL", true, "inUserName", true, "inPassword", true,
			"inReadInterval", true, "outEmailAddress", true, "outCustom", true,
			"outServerName", true, "outServerPort", true, "outUseSSL", true,
			"outUserName", true, "outPassword", true, "allowAnonymous", true,
			"active", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBMailingList newMBMailingList = addMBMailingList();

		MBMailingList existingMBMailingList = _persistence.fetchByPrimaryKey(newMBMailingList.getPrimaryKey());

		Assert.assertEquals(existingMBMailingList, newMBMailingList);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBMailingList missingMBMailingList = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMBMailingList);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		MBMailingList newMBMailingList1 = addMBMailingList();
		MBMailingList newMBMailingList2 = addMBMailingList();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBMailingList1.getPrimaryKey());
		primaryKeys.add(newMBMailingList2.getPrimaryKey());

		Map<Serializable, MBMailingList> mbMailingLists = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, mbMailingLists.size());
		Assert.assertEquals(newMBMailingList1,
			mbMailingLists.get(newMBMailingList1.getPrimaryKey()));
		Assert.assertEquals(newMBMailingList2,
			mbMailingLists.get(newMBMailingList2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, MBMailingList> mbMailingLists = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mbMailingLists.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		MBMailingList newMBMailingList = addMBMailingList();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBMailingList.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, MBMailingList> mbMailingLists = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mbMailingLists.size());
		Assert.assertEquals(newMBMailingList,
			mbMailingLists.get(newMBMailingList.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, MBMailingList> mbMailingLists = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mbMailingLists.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		MBMailingList newMBMailingList = addMBMailingList();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMBMailingList.getPrimaryKey());

		Map<Serializable, MBMailingList> mbMailingLists = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mbMailingLists.size());
		Assert.assertEquals(newMBMailingList,
			mbMailingLists.get(newMBMailingList.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = MBMailingListLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					MBMailingList mbMailingList = (MBMailingList)object;

					Assert.assertNotNull(mbMailingList);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBMailingList newMBMailingList = addMBMailingList();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBMailingList.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("mailingListId",
				newMBMailingList.getMailingListId()));

		List<MBMailingList> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MBMailingList existingMBMailingList = result.get(0);

		Assert.assertEquals(existingMBMailingList, newMBMailingList);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBMailingList.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("mailingListId",
				RandomTestUtil.nextLong()));

		List<MBMailingList> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBMailingList newMBMailingList = addMBMailingList();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBMailingList.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"mailingListId"));

		Object newMailingListId = newMBMailingList.getMailingListId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("mailingListId",
				new Object[] { newMailingListId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingMailingListId = result.get(0);

		Assert.assertEquals(existingMailingListId, newMailingListId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBMailingList.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"mailingListId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("mailingListId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		MBMailingList newMBMailingList = addMBMailingList();

		_persistence.clearCache();

		MBMailingList existingMBMailingList = _persistence.findByPrimaryKey(newMBMailingList.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingMBMailingList.getUuid(),
				ReflectionTestUtil.invoke(existingMBMailingList,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(existingMBMailingList.getGroupId(),
			ReflectionTestUtil.invoke(existingMBMailingList,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(existingMBMailingList.getGroupId(),
			ReflectionTestUtil.invoke(existingMBMailingList,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(existingMBMailingList.getCategoryId(),
			ReflectionTestUtil.invoke(existingMBMailingList,
				"getOriginalCategoryId", new Class<?>[0]));
	}

	protected MBMailingList addMBMailingList() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBMailingList mbMailingList = _persistence.create(pk);

		mbMailingList.setUuid(RandomTestUtil.randomString());

		mbMailingList.setGroupId(RandomTestUtil.nextLong());

		mbMailingList.setCompanyId(RandomTestUtil.nextLong());

		mbMailingList.setUserId(RandomTestUtil.nextLong());

		mbMailingList.setUserName(RandomTestUtil.randomString());

		mbMailingList.setCreateDate(RandomTestUtil.nextDate());

		mbMailingList.setModifiedDate(RandomTestUtil.nextDate());

		mbMailingList.setCategoryId(RandomTestUtil.nextLong());

		mbMailingList.setEmailAddress(RandomTestUtil.randomString());

		mbMailingList.setInProtocol(RandomTestUtil.randomString());

		mbMailingList.setInServerName(RandomTestUtil.randomString());

		mbMailingList.setInServerPort(RandomTestUtil.nextInt());

		mbMailingList.setInUseSSL(RandomTestUtil.randomBoolean());

		mbMailingList.setInUserName(RandomTestUtil.randomString());

		mbMailingList.setInPassword(RandomTestUtil.randomString());

		mbMailingList.setInReadInterval(RandomTestUtil.nextInt());

		mbMailingList.setOutEmailAddress(RandomTestUtil.randomString());

		mbMailingList.setOutCustom(RandomTestUtil.randomBoolean());

		mbMailingList.setOutServerName(RandomTestUtil.randomString());

		mbMailingList.setOutServerPort(RandomTestUtil.nextInt());

		mbMailingList.setOutUseSSL(RandomTestUtil.randomBoolean());

		mbMailingList.setOutUserName(RandomTestUtil.randomString());

		mbMailingList.setOutPassword(RandomTestUtil.randomString());

		mbMailingList.setAllowAnonymous(RandomTestUtil.randomBoolean());

		mbMailingList.setActive(RandomTestUtil.randomBoolean());

		_mbMailingLists.add(_persistence.update(mbMailingList));

		return mbMailingList;
	}

	private List<MBMailingList> _mbMailingLists = new ArrayList<MBMailingList>();
	private MBMailingListPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}