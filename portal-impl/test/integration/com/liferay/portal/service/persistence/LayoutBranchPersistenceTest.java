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

import com.liferay.portal.NoSuchLayoutBranchException;
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
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutBranchModelImpl;
import com.liferay.portal.service.LayoutBranchLocalServiceUtil;
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
public class LayoutBranchPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<LayoutBranch> modelListener : _modelListeners) {
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

		for (ModelListener<LayoutBranch> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutBranch layoutBranch = _persistence.create(pk);

		Assert.assertNotNull(layoutBranch);

		Assert.assertEquals(layoutBranch.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutBranch newLayoutBranch = addLayoutBranch();

		_persistence.remove(newLayoutBranch);

		LayoutBranch existingLayoutBranch = _persistence.fetchByPrimaryKey(newLayoutBranch.getPrimaryKey());

		Assert.assertNull(existingLayoutBranch);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutBranch();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutBranch newLayoutBranch = _persistence.create(pk);

		newLayoutBranch.setMvccVersion(RandomTestUtil.nextLong());

		newLayoutBranch.setGroupId(RandomTestUtil.nextLong());

		newLayoutBranch.setCompanyId(RandomTestUtil.nextLong());

		newLayoutBranch.setUserId(RandomTestUtil.nextLong());

		newLayoutBranch.setUserName(RandomTestUtil.randomString());

		newLayoutBranch.setLayoutSetBranchId(RandomTestUtil.nextLong());

		newLayoutBranch.setPlid(RandomTestUtil.nextLong());

		newLayoutBranch.setName(RandomTestUtil.randomString());

		newLayoutBranch.setDescription(RandomTestUtil.randomString());

		newLayoutBranch.setMaster(RandomTestUtil.randomBoolean());

		_persistence.update(newLayoutBranch);

		LayoutBranch existingLayoutBranch = _persistence.findByPrimaryKey(newLayoutBranch.getPrimaryKey());

		Assert.assertEquals(existingLayoutBranch.getMvccVersion(),
			newLayoutBranch.getMvccVersion());
		Assert.assertEquals(existingLayoutBranch.getLayoutBranchId(),
			newLayoutBranch.getLayoutBranchId());
		Assert.assertEquals(existingLayoutBranch.getGroupId(),
			newLayoutBranch.getGroupId());
		Assert.assertEquals(existingLayoutBranch.getCompanyId(),
			newLayoutBranch.getCompanyId());
		Assert.assertEquals(existingLayoutBranch.getUserId(),
			newLayoutBranch.getUserId());
		Assert.assertEquals(existingLayoutBranch.getUserName(),
			newLayoutBranch.getUserName());
		Assert.assertEquals(existingLayoutBranch.getLayoutSetBranchId(),
			newLayoutBranch.getLayoutSetBranchId());
		Assert.assertEquals(existingLayoutBranch.getPlid(),
			newLayoutBranch.getPlid());
		Assert.assertEquals(existingLayoutBranch.getName(),
			newLayoutBranch.getName());
		Assert.assertEquals(existingLayoutBranch.getDescription(),
			newLayoutBranch.getDescription());
		Assert.assertEquals(existingLayoutBranch.getMaster(),
			newLayoutBranch.getMaster());
	}

	@Test
	public void testCountByLayoutSetBranchId() {
		try {
			_persistence.countByLayoutSetBranchId(RandomTestUtil.nextLong());

			_persistence.countByLayoutSetBranchId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByL_P() {
		try {
			_persistence.countByL_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByL_P(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByL_P_N() {
		try {
			_persistence.countByL_P_N(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByL_P_N(0L, 0L, StringPool.NULL);

			_persistence.countByL_P_N(0L, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByL_P_M() {
		try {
			_persistence.countByL_P_M(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

			_persistence.countByL_P_M(0L, 0L, RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutBranch newLayoutBranch = addLayoutBranch();

		LayoutBranch existingLayoutBranch = _persistence.findByPrimaryKey(newLayoutBranch.getPrimaryKey());

		Assert.assertEquals(existingLayoutBranch, newLayoutBranch);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchLayoutBranchException");
		}
		catch (NoSuchLayoutBranchException nsee) {
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
		return OrderByComparatorFactoryUtil.create("LayoutBranch",
			"mvccVersion", true, "LayoutBranchId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true,
			"layoutSetBranchId", true, "plid", true, "name", true,
			"description", true, "master", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutBranch newLayoutBranch = addLayoutBranch();

		LayoutBranch existingLayoutBranch = _persistence.fetchByPrimaryKey(newLayoutBranch.getPrimaryKey());

		Assert.assertEquals(existingLayoutBranch, newLayoutBranch);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutBranch missingLayoutBranch = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutBranch);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		LayoutBranch newLayoutBranch1 = addLayoutBranch();
		LayoutBranch newLayoutBranch2 = addLayoutBranch();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutBranch1.getPrimaryKey());
		primaryKeys.add(newLayoutBranch2.getPrimaryKey());

		Map<Serializable, LayoutBranch> layoutBranchs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutBranchs.size());
		Assert.assertEquals(newLayoutBranch1,
			layoutBranchs.get(newLayoutBranch1.getPrimaryKey()));
		Assert.assertEquals(newLayoutBranch2,
			layoutBranchs.get(newLayoutBranch2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutBranch> layoutBranchs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutBranchs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		LayoutBranch newLayoutBranch = addLayoutBranch();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutBranch.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutBranch> layoutBranchs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutBranchs.size());
		Assert.assertEquals(newLayoutBranch,
			layoutBranchs.get(newLayoutBranch.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutBranch> layoutBranchs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutBranchs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		LayoutBranch newLayoutBranch = addLayoutBranch();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutBranch.getPrimaryKey());

		Map<Serializable, LayoutBranch> layoutBranchs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutBranchs.size());
		Assert.assertEquals(newLayoutBranch,
			layoutBranchs.get(newLayoutBranch.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = LayoutBranchLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					LayoutBranch layoutBranch = (LayoutBranch)object;

					Assert.assertNotNull(layoutBranch);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutBranch newLayoutBranch = addLayoutBranch();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutBranch.class,
				LayoutBranch.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("LayoutBranchId",
				newLayoutBranch.getLayoutBranchId()));

		List<LayoutBranch> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutBranch existingLayoutBranch = result.get(0);

		Assert.assertEquals(existingLayoutBranch, newLayoutBranch);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutBranch.class,
				LayoutBranch.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("LayoutBranchId",
				RandomTestUtil.nextLong()));

		List<LayoutBranch> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		LayoutBranch newLayoutBranch = addLayoutBranch();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutBranch.class,
				LayoutBranch.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"LayoutBranchId"));

		Object newLayoutBranchId = newLayoutBranch.getLayoutBranchId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("LayoutBranchId",
				new Object[] { newLayoutBranchId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutBranchId = result.get(0);

		Assert.assertEquals(existingLayoutBranchId, newLayoutBranchId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutBranch.class,
				LayoutBranch.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"LayoutBranchId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("LayoutBranchId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		LayoutBranch newLayoutBranch = addLayoutBranch();

		_persistence.clearCache();

		LayoutBranchModelImpl existingLayoutBranchModelImpl = (LayoutBranchModelImpl)_persistence.findByPrimaryKey(newLayoutBranch.getPrimaryKey());

		Assert.assertEquals(existingLayoutBranchModelImpl.getLayoutSetBranchId(),
			existingLayoutBranchModelImpl.getOriginalLayoutSetBranchId());
		Assert.assertEquals(existingLayoutBranchModelImpl.getPlid(),
			existingLayoutBranchModelImpl.getOriginalPlid());
		Assert.assertTrue(Validator.equals(
				existingLayoutBranchModelImpl.getName(),
				existingLayoutBranchModelImpl.getOriginalName()));
	}

	protected LayoutBranch addLayoutBranch() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutBranch layoutBranch = _persistence.create(pk);

		layoutBranch.setMvccVersion(RandomTestUtil.nextLong());

		layoutBranch.setGroupId(RandomTestUtil.nextLong());

		layoutBranch.setCompanyId(RandomTestUtil.nextLong());

		layoutBranch.setUserId(RandomTestUtil.nextLong());

		layoutBranch.setUserName(RandomTestUtil.randomString());

		layoutBranch.setLayoutSetBranchId(RandomTestUtil.nextLong());

		layoutBranch.setPlid(RandomTestUtil.nextLong());

		layoutBranch.setName(RandomTestUtil.randomString());

		layoutBranch.setDescription(RandomTestUtil.randomString());

		layoutBranch.setMaster(RandomTestUtil.randomBoolean());

		_persistence.update(layoutBranch);

		return layoutBranch;
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutBranchPersistenceTest.class);
	private ModelListener<LayoutBranch>[] _modelListeners;
	private LayoutBranchPersistence _persistence = (LayoutBranchPersistence)PortalBeanLocatorUtil.locate(LayoutBranchPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}