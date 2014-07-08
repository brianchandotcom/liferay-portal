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

import com.liferay.portal.NoSuchRepositoryException;
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
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.impl.RepositoryModelImpl;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
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
public class RepositoryPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<Repository> modelListener : _modelListeners) {
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

		for (ModelListener<Repository> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Repository repository = _persistence.create(pk);

		Assert.assertNotNull(repository);

		Assert.assertEquals(repository.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Repository newRepository = addRepository();

		_persistence.remove(newRepository);

		Repository existingRepository = _persistence.fetchByPrimaryKey(newRepository.getPrimaryKey());

		Assert.assertNull(existingRepository);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addRepository();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Repository newRepository = _persistence.create(pk);

		newRepository.setMvccVersion(RandomTestUtil.nextLong());

		newRepository.setUuid(RandomTestUtil.randomString());

		newRepository.setGroupId(RandomTestUtil.nextLong());

		newRepository.setCompanyId(RandomTestUtil.nextLong());

		newRepository.setUserId(RandomTestUtil.nextLong());

		newRepository.setUserName(RandomTestUtil.randomString());

		newRepository.setCreateDate(RandomTestUtil.nextDate());

		newRepository.setModifiedDate(RandomTestUtil.nextDate());

		newRepository.setClassNameId(RandomTestUtil.nextLong());

		newRepository.setName(RandomTestUtil.randomString());

		newRepository.setDescription(RandomTestUtil.randomString());

		newRepository.setPortletId(RandomTestUtil.randomString());

		newRepository.setTypeSettings(RandomTestUtil.randomString());

		newRepository.setDlFolderId(RandomTestUtil.nextLong());

		_persistence.update(newRepository);

		Repository existingRepository = _persistence.findByPrimaryKey(newRepository.getPrimaryKey());

		Assert.assertEquals(existingRepository.getMvccVersion(),
			newRepository.getMvccVersion());
		Assert.assertEquals(existingRepository.getUuid(),
			newRepository.getUuid());
		Assert.assertEquals(existingRepository.getRepositoryId(),
			newRepository.getRepositoryId());
		Assert.assertEquals(existingRepository.getGroupId(),
			newRepository.getGroupId());
		Assert.assertEquals(existingRepository.getCompanyId(),
			newRepository.getCompanyId());
		Assert.assertEquals(existingRepository.getUserId(),
			newRepository.getUserId());
		Assert.assertEquals(existingRepository.getUserName(),
			newRepository.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingRepository.getCreateDate()),
			Time.getShortTimestamp(newRepository.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingRepository.getModifiedDate()),
			Time.getShortTimestamp(newRepository.getModifiedDate()));
		Assert.assertEquals(existingRepository.getClassNameId(),
			newRepository.getClassNameId());
		Assert.assertEquals(existingRepository.getName(),
			newRepository.getName());
		Assert.assertEquals(existingRepository.getDescription(),
			newRepository.getDescription());
		Assert.assertEquals(existingRepository.getPortletId(),
			newRepository.getPortletId());
		Assert.assertEquals(existingRepository.getTypeSettings(),
			newRepository.getTypeSettings());
		Assert.assertEquals(existingRepository.getDlFolderId(),
			newRepository.getDlFolderId());
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
	public void testCountByUUID_G() {
		try {
			_persistence.countByUUID_G(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUUID_G(StringPool.NULL, 0L);

			_persistence.countByUUID_G((String)null, 0L);
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
	public void testCountByG_N_P() {
		try {
			_persistence.countByG_N_P(RandomTestUtil.nextLong(),
				StringPool.BLANK, StringPool.BLANK);

			_persistence.countByG_N_P(0L, StringPool.NULL, StringPool.NULL);

			_persistence.countByG_N_P(0L, (String)null, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Repository newRepository = addRepository();

		Repository existingRepository = _persistence.findByPrimaryKey(newRepository.getPrimaryKey());

		Assert.assertEquals(existingRepository, newRepository);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchRepositoryException");
		}
		catch (NoSuchRepositoryException nsee) {
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
		return OrderByComparatorFactoryUtil.create("Repository", "mvccVersion",
			true, "uuid", true, "repositoryId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "classNameId", true, "name", true,
			"description", true, "portletId", true, "typeSettings", true,
			"dlFolderId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Repository newRepository = addRepository();

		Repository existingRepository = _persistence.fetchByPrimaryKey(newRepository.getPrimaryKey());

		Assert.assertEquals(existingRepository, newRepository);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Repository missingRepository = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingRepository);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Repository newRepository1 = addRepository();
		Repository newRepository2 = addRepository();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRepository1.getPrimaryKey());
		primaryKeys.add(newRepository2.getPrimaryKey());

		Map<Serializable, Repository> repositories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, repositories.size());
		Assert.assertEquals(newRepository1,
			repositories.get(newRepository1.getPrimaryKey()));
		Assert.assertEquals(newRepository2,
			repositories.get(newRepository2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Repository> repositories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(repositories.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Repository newRepository = addRepository();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRepository.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Repository> repositories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, repositories.size());
		Assert.assertEquals(newRepository,
			repositories.get(newRepository.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Repository> repositories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(repositories.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Repository newRepository = addRepository();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRepository.getPrimaryKey());

		Map<Serializable, Repository> repositories = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, repositories.size());
		Assert.assertEquals(newRepository,
			repositories.get(newRepository.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = RepositoryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					Repository repository = (Repository)object;

					Assert.assertNotNull(repository);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Repository newRepository = addRepository();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Repository.class,
				Repository.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("repositoryId",
				newRepository.getRepositoryId()));

		List<Repository> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Repository existingRepository = result.get(0);

		Assert.assertEquals(existingRepository, newRepository);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Repository.class,
				Repository.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("repositoryId",
				RandomTestUtil.nextLong()));

		List<Repository> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Repository newRepository = addRepository();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Repository.class,
				Repository.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"repositoryId"));

		Object newRepositoryId = newRepository.getRepositoryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("repositoryId",
				new Object[] { newRepositoryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRepositoryId = result.get(0);

		Assert.assertEquals(existingRepositoryId, newRepositoryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Repository.class,
				Repository.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"repositoryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("repositoryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Repository newRepository = addRepository();

		_persistence.clearCache();

		RepositoryModelImpl existingRepositoryModelImpl = (RepositoryModelImpl)_persistence.findByPrimaryKey(newRepository.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingRepositoryModelImpl.getUuid(),
				existingRepositoryModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingRepositoryModelImpl.getGroupId(),
			existingRepositoryModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingRepositoryModelImpl.getGroupId(),
			existingRepositoryModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingRepositoryModelImpl.getName(),
				existingRepositoryModelImpl.getOriginalName()));
		Assert.assertTrue(Validator.equals(
				existingRepositoryModelImpl.getPortletId(),
				existingRepositoryModelImpl.getOriginalPortletId()));
	}

	protected Repository addRepository() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Repository repository = _persistence.create(pk);

		repository.setMvccVersion(RandomTestUtil.nextLong());

		repository.setUuid(RandomTestUtil.randomString());

		repository.setGroupId(RandomTestUtil.nextLong());

		repository.setCompanyId(RandomTestUtil.nextLong());

		repository.setUserId(RandomTestUtil.nextLong());

		repository.setUserName(RandomTestUtil.randomString());

		repository.setCreateDate(RandomTestUtil.nextDate());

		repository.setModifiedDate(RandomTestUtil.nextDate());

		repository.setClassNameId(RandomTestUtil.nextLong());

		repository.setName(RandomTestUtil.randomString());

		repository.setDescription(RandomTestUtil.randomString());

		repository.setPortletId(RandomTestUtil.randomString());

		repository.setTypeSettings(RandomTestUtil.randomString());

		repository.setDlFolderId(RandomTestUtil.nextLong());

		_persistence.update(repository);

		return repository;
	}

	private static Log _log = LogFactoryUtil.getLog(RepositoryPersistenceTest.class);
	private ModelListener<Repository>[] _modelListeners;
	private RepositoryPersistence _persistence = (RepositoryPersistence)PortalBeanLocatorUtil.locate(RepositoryPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}