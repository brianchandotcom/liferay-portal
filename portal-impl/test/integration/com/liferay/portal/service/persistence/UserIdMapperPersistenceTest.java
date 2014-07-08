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

import com.liferay.portal.NoSuchUserIdMapperException;
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
import com.liferay.portal.model.UserIdMapper;
import com.liferay.portal.model.impl.UserIdMapperModelImpl;
import com.liferay.portal.service.UserIdMapperLocalServiceUtil;
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
public class UserIdMapperPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<UserIdMapper> modelListener : _modelListeners) {
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

		for (ModelListener<UserIdMapper> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserIdMapper userIdMapper = _persistence.create(pk);

		Assert.assertNotNull(userIdMapper);

		Assert.assertEquals(userIdMapper.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		UserIdMapper newUserIdMapper = addUserIdMapper();

		_persistence.remove(newUserIdMapper);

		UserIdMapper existingUserIdMapper = _persistence.fetchByPrimaryKey(newUserIdMapper.getPrimaryKey());

		Assert.assertNull(existingUserIdMapper);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addUserIdMapper();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserIdMapper newUserIdMapper = _persistence.create(pk);

		newUserIdMapper.setMvccVersion(RandomTestUtil.nextLong());

		newUserIdMapper.setUserId(RandomTestUtil.nextLong());

		newUserIdMapper.setType(RandomTestUtil.randomString());

		newUserIdMapper.setDescription(RandomTestUtil.randomString());

		newUserIdMapper.setExternalUserId(RandomTestUtil.randomString());

		_persistence.update(newUserIdMapper);

		UserIdMapper existingUserIdMapper = _persistence.findByPrimaryKey(newUserIdMapper.getPrimaryKey());

		Assert.assertEquals(existingUserIdMapper.getMvccVersion(),
			newUserIdMapper.getMvccVersion());
		Assert.assertEquals(existingUserIdMapper.getUserIdMapperId(),
			newUserIdMapper.getUserIdMapperId());
		Assert.assertEquals(existingUserIdMapper.getUserId(),
			newUserIdMapper.getUserId());
		Assert.assertEquals(existingUserIdMapper.getType(),
			newUserIdMapper.getType());
		Assert.assertEquals(existingUserIdMapper.getDescription(),
			newUserIdMapper.getDescription());
		Assert.assertEquals(existingUserIdMapper.getExternalUserId(),
			newUserIdMapper.getExternalUserId());
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
	public void testCountByU_T() {
		try {
			_persistence.countByU_T(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByU_T(0L, StringPool.NULL);

			_persistence.countByU_T(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByT_E() {
		try {
			_persistence.countByT_E(StringPool.BLANK, StringPool.BLANK);

			_persistence.countByT_E(StringPool.NULL, StringPool.NULL);

			_persistence.countByT_E((String)null, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		UserIdMapper newUserIdMapper = addUserIdMapper();

		UserIdMapper existingUserIdMapper = _persistence.findByPrimaryKey(newUserIdMapper.getPrimaryKey());

		Assert.assertEquals(existingUserIdMapper, newUserIdMapper);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchUserIdMapperException");
		}
		catch (NoSuchUserIdMapperException nsee) {
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
		return OrderByComparatorFactoryUtil.create("UserIdMapper",
			"mvccVersion", true, "userIdMapperId", true, "userId", true,
			"type", true, "description", true, "externalUserId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserIdMapper newUserIdMapper = addUserIdMapper();

		UserIdMapper existingUserIdMapper = _persistence.fetchByPrimaryKey(newUserIdMapper.getPrimaryKey());

		Assert.assertEquals(existingUserIdMapper, newUserIdMapper);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserIdMapper missingUserIdMapper = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingUserIdMapper);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		UserIdMapper newUserIdMapper1 = addUserIdMapper();
		UserIdMapper newUserIdMapper2 = addUserIdMapper();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserIdMapper1.getPrimaryKey());
		primaryKeys.add(newUserIdMapper2.getPrimaryKey());

		Map<Serializable, UserIdMapper> userIdMappers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, userIdMappers.size());
		Assert.assertEquals(newUserIdMapper1,
			userIdMappers.get(newUserIdMapper1.getPrimaryKey()));
		Assert.assertEquals(newUserIdMapper2,
			userIdMappers.get(newUserIdMapper2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, UserIdMapper> userIdMappers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(userIdMappers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		UserIdMapper newUserIdMapper = addUserIdMapper();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserIdMapper.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, UserIdMapper> userIdMappers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, userIdMappers.size());
		Assert.assertEquals(newUserIdMapper,
			userIdMappers.get(newUserIdMapper.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, UserIdMapper> userIdMappers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(userIdMappers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		UserIdMapper newUserIdMapper = addUserIdMapper();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserIdMapper.getPrimaryKey());

		Map<Serializable, UserIdMapper> userIdMappers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, userIdMappers.size());
		Assert.assertEquals(newUserIdMapper,
			userIdMappers.get(newUserIdMapper.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = UserIdMapperLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					UserIdMapper userIdMapper = (UserIdMapper)object;

					Assert.assertNotNull(userIdMapper);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		UserIdMapper newUserIdMapper = addUserIdMapper();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserIdMapper.class,
				UserIdMapper.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userIdMapperId",
				newUserIdMapper.getUserIdMapperId()));

		List<UserIdMapper> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		UserIdMapper existingUserIdMapper = result.get(0);

		Assert.assertEquals(existingUserIdMapper, newUserIdMapper);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserIdMapper.class,
				UserIdMapper.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userIdMapperId",
				RandomTestUtil.nextLong()));

		List<UserIdMapper> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		UserIdMapper newUserIdMapper = addUserIdMapper();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserIdMapper.class,
				UserIdMapper.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userIdMapperId"));

		Object newUserIdMapperId = newUserIdMapper.getUserIdMapperId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("userIdMapperId",
				new Object[] { newUserIdMapperId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingUserIdMapperId = result.get(0);

		Assert.assertEquals(existingUserIdMapperId, newUserIdMapperId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserIdMapper.class,
				UserIdMapper.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userIdMapperId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("userIdMapperId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		UserIdMapper newUserIdMapper = addUserIdMapper();

		_persistence.clearCache();

		UserIdMapperModelImpl existingUserIdMapperModelImpl = (UserIdMapperModelImpl)_persistence.findByPrimaryKey(newUserIdMapper.getPrimaryKey());

		Assert.assertEquals(existingUserIdMapperModelImpl.getUserId(),
			existingUserIdMapperModelImpl.getOriginalUserId());
		Assert.assertTrue(Validator.equals(
				existingUserIdMapperModelImpl.getType(),
				existingUserIdMapperModelImpl.getOriginalType()));

		Assert.assertTrue(Validator.equals(
				existingUserIdMapperModelImpl.getType(),
				existingUserIdMapperModelImpl.getOriginalType()));
		Assert.assertTrue(Validator.equals(
				existingUserIdMapperModelImpl.getExternalUserId(),
				existingUserIdMapperModelImpl.getOriginalExternalUserId()));
	}

	protected UserIdMapper addUserIdMapper() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserIdMapper userIdMapper = _persistence.create(pk);

		userIdMapper.setMvccVersion(RandomTestUtil.nextLong());

		userIdMapper.setUserId(RandomTestUtil.nextLong());

		userIdMapper.setType(RandomTestUtil.randomString());

		userIdMapper.setDescription(RandomTestUtil.randomString());

		userIdMapper.setExternalUserId(RandomTestUtil.randomString());

		_persistence.update(userIdMapper);

		return userIdMapper;
	}

	private static Log _log = LogFactoryUtil.getLog(UserIdMapperPersistenceTest.class);
	private ModelListener<UserIdMapper>[] _modelListeners;
	private UserIdMapperPersistence _persistence = (UserIdMapperPersistence)PortalBeanLocatorUtil.locate(UserIdMapperPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}