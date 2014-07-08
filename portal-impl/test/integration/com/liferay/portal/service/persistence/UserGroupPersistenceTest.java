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

import com.liferay.portal.NoSuchUserGroupException;
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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.UserGroupModelImpl;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;
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
public class UserGroupPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<UserGroup> modelListener : _modelListeners) {
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

		for (ModelListener<UserGroup> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
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

		newUserGroup.setCompanyId(RandomTestUtil.nextLong());

		newUserGroup.setUserId(RandomTestUtil.nextLong());

		newUserGroup.setUserName(RandomTestUtil.randomString());

		newUserGroup.setCreateDate(RandomTestUtil.nextDate());

		newUserGroup.setModifiedDate(RandomTestUtil.nextDate());

		newUserGroup.setParentUserGroupId(RandomTestUtil.nextLong());

		newUserGroup.setName(RandomTestUtil.randomString());

		newUserGroup.setDescription(RandomTestUtil.randomString());

		newUserGroup.setAddedByLDAPImport(RandomTestUtil.randomBoolean());

		_persistence.update(newUserGroup);

		UserGroup existingUserGroup = _persistence.findByPrimaryKey(newUserGroup.getPrimaryKey());

		Assert.assertEquals(existingUserGroup.getMvccVersion(),
			newUserGroup.getMvccVersion());
		Assert.assertEquals(existingUserGroup.getUuid(), newUserGroup.getUuid());
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
		Assert.assertEquals(existingUserGroup.getAddedByLDAPImport(),
			newUserGroup.getAddedByLDAPImport());
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
				RandomTestUtil.nextLong());

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
			_persistence.countByCompanyId(RandomTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_P() {
		try {
			_persistence.countByC_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByC_P(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_N() {
		try {
			_persistence.countByC_N(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByC_N(0L, StringPool.NULL);

			_persistence.countByC_N(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		UserGroup newUserGroup = addUserGroup();

		UserGroup existingUserGroup = _persistence.findByPrimaryKey(newUserGroup.getPrimaryKey());

		Assert.assertEquals(existingUserGroup, newUserGroup);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchUserGroupException");
		}
		catch (NoSuchUserGroupException nsee) {
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
		return OrderByComparatorFactoryUtil.create("UserGroup", "mvccVersion",
			true, "uuid", true, "userGroupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "parentUserGroupId", true, "name", true,
			"description", true, "addedByLDAPImport", true);
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

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					UserGroup userGroup = (UserGroup)object;

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
				UserGroup.class.getClassLoader());

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
				UserGroup.class.getClassLoader());

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
				UserGroup.class.getClassLoader());

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
				UserGroup.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("userGroupId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("userGroupId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		UserGroup newUserGroup = addUserGroup();

		_persistence.clearCache();

		UserGroupModelImpl existingUserGroupModelImpl = (UserGroupModelImpl)_persistence.findByPrimaryKey(newUserGroup.getPrimaryKey());

		Assert.assertEquals(existingUserGroupModelImpl.getCompanyId(),
			existingUserGroupModelImpl.getOriginalCompanyId());
		Assert.assertTrue(Validator.equals(
				existingUserGroupModelImpl.getName(),
				existingUserGroupModelImpl.getOriginalName()));
	}

	protected UserGroup addUserGroup() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserGroup userGroup = _persistence.create(pk);

		userGroup.setMvccVersion(RandomTestUtil.nextLong());

		userGroup.setUuid(RandomTestUtil.randomString());

		userGroup.setCompanyId(RandomTestUtil.nextLong());

		userGroup.setUserId(RandomTestUtil.nextLong());

		userGroup.setUserName(RandomTestUtil.randomString());

		userGroup.setCreateDate(RandomTestUtil.nextDate());

		userGroup.setModifiedDate(RandomTestUtil.nextDate());

		userGroup.setParentUserGroupId(RandomTestUtil.nextLong());

		userGroup.setName(RandomTestUtil.randomString());

		userGroup.setDescription(RandomTestUtil.randomString());

		userGroup.setAddedByLDAPImport(RandomTestUtil.randomBoolean());

		_persistence.update(userGroup);

		return userGroup;
	}

	private static Log _log = LogFactoryUtil.getLog(UserGroupPersistenceTest.class);
	private ModelListener<UserGroup>[] _modelListeners;
	private UserGroupPersistence _persistence = (UserGroupPersistence)PortalBeanLocatorUtil.locate(UserGroupPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}