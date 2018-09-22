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

package com.liferay.portal.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchUserGroupException;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.UserGroupPersistence;
import com.liferay.portal.kernel.service.persistence.UserGroupUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
public class UserGroupPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = UserGroupUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<UserGroup> iterator = _userGroups.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserGroup userGroup = _persistence.create(pk);

		Assert.assertNotNull(userGroup);

		Assert.assertEquals(userGroup.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		UserGroup newUserGroup = addUserGroup();

		_persistence.remove(newUserGroup);

		UserGroup existingUserGroup = _persistence.fetchByPrimaryKey(newUserGroup.getPrimaryKey());

		Assert.assertNull(existingUserGroup);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addUserGroup();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserGroup newUserGroup = _persistence.create(pk);

		newUserGroup.setMvccVersion(RandomTestUtil.nextLong());

		newUserGroup.setUuid(RandomTestUtil.randomString());

		newUserGroup.setExternalReferenceCode(RandomTestUtil.randomString());

		newUserGroup.setCompanyId(RandomTestUtil.nextLong());

		newUserGroup.setUserId(RandomTestUtil.nextLong());

		newUserGroup.setUserName(RandomTestUtil.randomString());

		newUserGroup.setCreateDate(RandomTestUtil.nextDate());

		newUserGroup.setModifiedDate(RandomTestUtil.nextDate());

		newUserGroup.setParentUserGroupId(RandomTestUtil.nextLong());

		newUserGroup.setName(RandomTestUtil.randomString());

		newUserGroup.setDescription(RandomTestUtil.randomString());

		newUserGroup.setAddedByLDAPImport(RandomTestUtil.randomBoolean());

		_userGroups.add(_persistence.update(newUserGroup));

		UserGroup existingUserGroup = _persistence.findByPrimaryKey(newUserGroup.getPrimaryKey());

		Assert.assertEquals(existingUserGroup.getMvccVersion(),
			newUserGroup.getMvccVersion());
		Assert.assertEquals(existingUserGroup.getUuid(), newUserGroup.getUuid());
		Assert.assertEquals(existingUserGroup.getExternalReferenceCode(),
			newUserGroup.getExternalReferenceCode());
		Assert.assertEquals(existingUserGroup.getUserGroupId(),
			newUserGroup.getUserGroupId());
		Assert.assertEquals(existingUserGroup.getCompanyId(),
			newUserGroup.getCompanyId());
		Assert.assertEquals(existingUserGroup.getUserId(),
			newUserGroup.getUserId());
		Assert.assertEquals(existingUserGroup.getUserName(),
			newUserGroup.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingUserGroup.getCreateDate()),
			Time.getShortTimestamp(newUserGroup.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingUserGroup.getModifiedDate()),
			Time.getShortTimestamp(newUserGroup.getModifiedDate()));
		Assert.assertEquals(existingUserGroup.getParentUserGroupId(),
			newUserGroup.getParentUserGroupId());
		Assert.assertEquals(existingUserGroup.getName(), newUserGroup.getName());
		Assert.assertEquals(existingUserGroup.getDescription(),
			newUserGroup.getDescription());
		Assert.assertEquals(existingUserGroup.isAddedByLDAPImport(),
			newUserGroup.isAddedByLDAPImport());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
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
	public void testCountByC_P() throws Exception {
		_persistence.countByC_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_P(0L, 0L);
	}

	@Test
	public void testCountByC_N() throws Exception {
		_persistence.countByC_N(RandomTestUtil.nextLong(), "");

		_persistence.countByC_N(0L, "null");

		_persistence.countByC_N(0L, (String)null);
	}

	@Test
	public void testCountByC_LikeN() throws Exception {
		_persistence.countByC_LikeN(RandomTestUtil.nextLong(), "");

		_persistence.countByC_LikeN(0L, "null");

		_persistence.countByC_LikeN(0L, (String)null);
	}

	@Test
	public void testCountByU_C_P() throws Exception {
		_persistence.countByU_C_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByU_C_P(0L, 0L, 0L);
	}

	@Test
	public void testCountByC_ERC() throws Exception {
		_persistence.countByC_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByC_ERC(0L, "null");

		_persistence.countByC_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		UserGroup newUserGroup = addUserGroup();

		UserGroup existingUserGroup = _persistence.findByPrimaryKey(newUserGroup.getPrimaryKey());

		Assert.assertEquals(existingUserGroup, newUserGroup);
	}

	@Test(expected = NoSuchUserGroupException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<UserGroup> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("UserGroup", "mvccVersion",
			true, "uuid", true, "externalReferenceCode", true, "userGroupId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "parentUserGroupId",
			true, "name", true, "description", true, "addedByLDAPImport", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserGroup newUserGroup = addUserGroup();

		UserGroup existingUserGroup = _persistence.fetchByPrimaryKey(newUserGroup.getPrimaryKey());

		Assert.assertEquals(existingUserGroup, newUserGroup);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserGroup missingUserGroup = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingUserGroup);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		UserGroup newUserGroup1 = addUserGroup();
		UserGroup newUserGroup2 = addUserGroup();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserGroup1.getPrimaryKey());
		primaryKeys.add(newUserGroup2.getPrimaryKey());

		Map<Serializable, UserGroup> userGroups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, userGroups.size());
		Assert.assertEquals(newUserGroup1,
			userGroups.get(newUserGroup1.getPrimaryKey()));
		Assert.assertEquals(newUserGroup2,
			userGroups.get(newUserGroup2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, UserGroup> userGroups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(userGroups.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		UserGroup newUserGroup = addUserGroup();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserGroup.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, UserGroup> userGroups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, userGroups.size());
		Assert.assertEquals(newUserGroup,
			userGroups.get(newUserGroup.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, UserGroup> userGroups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(userGroups.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		UserGroup newUserGroup = addUserGroup();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserGroup.getPrimaryKey());

		Map<Serializable, UserGroup> userGroups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, userGroups.size());
		Assert.assertEquals(newUserGroup,
			userGroups.get(newUserGroup.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = UserGroupLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<UserGroup>() {
				@Override
				public void performAction(UserGroup userGroup) {
					Assert.assertNotNull(userGroup);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		UserGroup newUserGroup = addUserGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroup.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userGroupId",
				newUserGroup.getUserGroupId()));

		List<UserGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		UserGroup existingUserGroup = result.get(0);

		Assert.assertEquals(existingUserGroup, newUserGroup);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroup.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userGroupId",
				RandomTestUtil.nextLong()));

		List<UserGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		UserGroup newUserGroup = addUserGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroup.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("userGroupId"));

		Object newUserGroupId = newUserGroup.getUserGroupId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("userGroupId",
				new Object[] { newUserGroupId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingUserGroupId = result.get(0);

		Assert.assertEquals(existingUserGroupId, newUserGroupId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserGroup.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("userGroupId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("userGroupId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		UserGroup newUserGroup = addUserGroup();

		_persistence.clearCache();

		UserGroup existingUserGroup = _persistence.findByPrimaryKey(newUserGroup.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(existingUserGroup.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(existingUserGroup,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(existingUserGroup.getName(),
				ReflectionTestUtil.invoke(existingUserGroup, "getOriginalName",
					new Class<?>[0])));

		Assert.assertEquals(Long.valueOf(existingUserGroup.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(existingUserGroup,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingUserGroup.getExternalReferenceCode(),
				ReflectionTestUtil.invoke(existingUserGroup,
					"getOriginalExternalReferenceCode", new Class<?>[0])));
	}

	protected UserGroup addUserGroup() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserGroup userGroup = _persistence.create(pk);

		userGroup.setMvccVersion(RandomTestUtil.nextLong());

		userGroup.setUuid(RandomTestUtil.randomString());

		userGroup.setExternalReferenceCode(RandomTestUtil.randomString());

		userGroup.setCompanyId(RandomTestUtil.nextLong());

		userGroup.setUserId(RandomTestUtil.nextLong());

		userGroup.setUserName(RandomTestUtil.randomString());

		userGroup.setCreateDate(RandomTestUtil.nextDate());

		userGroup.setModifiedDate(RandomTestUtil.nextDate());

		userGroup.setParentUserGroupId(RandomTestUtil.nextLong());

		userGroup.setName(RandomTestUtil.randomString());

		userGroup.setDescription(RandomTestUtil.randomString());

		userGroup.setAddedByLDAPImport(RandomTestUtil.randomBoolean());

		_userGroups.add(_persistence.update(userGroup));

		return userGroup;
	}

	private List<UserGroup> _userGroups = new ArrayList<UserGroup>();
	private UserGroupPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}