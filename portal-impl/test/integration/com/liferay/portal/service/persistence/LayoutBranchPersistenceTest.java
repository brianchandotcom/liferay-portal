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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.impl.LayoutBranchModelImpl;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

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
public class LayoutBranchPersistenceTest {
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
		long pk = ServiceTestUtil.nextLong();

		LayoutBranch newLayoutBranch = _persistence.create(pk);

		newLayoutBranch.setGroupId(ServiceTestUtil.nextLong());

		newLayoutBranch.setCompanyId(ServiceTestUtil.nextLong());

		newLayoutBranch.setUserId(ServiceTestUtil.nextLong());

		newLayoutBranch.setUserName(ServiceTestUtil.randomString());

		newLayoutBranch.setLayoutSetBranchId(ServiceTestUtil.nextLong());

		newLayoutBranch.setPlid(ServiceTestUtil.nextLong());

		newLayoutBranch.setName(ServiceTestUtil.randomString());

		newLayoutBranch.setDescription(ServiceTestUtil.randomString());

		newLayoutBranch.setMaster(ServiceTestUtil.randomBoolean());

		newLayoutBranch.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newLayoutBranch);

		LayoutBranch existingLayoutBranch = _persistence.findByPrimaryKey(newLayoutBranch.getPrimaryKey());

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
		Assert.assertEquals(existingLayoutBranch.getMvccVersion(),
			newLayoutBranch.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutBranch newLayoutBranch = addLayoutBranch();

		LayoutBranch existingLayoutBranch = _persistence.findByPrimaryKey(newLayoutBranch.getPrimaryKey());

		Assert.assertEquals(existingLayoutBranch, newLayoutBranch);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

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
			"LayoutBranchId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "layoutSetBranchId", true,
			"plid", true, "name", true, "description", true, "master", true,
			"mvccVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutBranch newLayoutBranch = addLayoutBranch();

		LayoutBranch existingLayoutBranch = _persistence.fetchByPrimaryKey(newLayoutBranch.getPrimaryKey());

		Assert.assertEquals(existingLayoutBranch, newLayoutBranch);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		LayoutBranch missingLayoutBranch = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutBranch);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new LayoutBranchActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					LayoutBranch layoutBranch = (LayoutBranch)object;

					Assert.assertNotNull(layoutBranch);

					count.increment();
				}
			};

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
				ServiceTestUtil.nextLong()));

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
				new Object[] { ServiceTestUtil.nextLong() }));

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
		long pk = ServiceTestUtil.nextLong();

		LayoutBranch layoutBranch = _persistence.create(pk);

		layoutBranch.setGroupId(ServiceTestUtil.nextLong());

		layoutBranch.setCompanyId(ServiceTestUtil.nextLong());

		layoutBranch.setUserId(ServiceTestUtil.nextLong());

		layoutBranch.setUserName(ServiceTestUtil.randomString());

		layoutBranch.setLayoutSetBranchId(ServiceTestUtil.nextLong());

		layoutBranch.setPlid(ServiceTestUtil.nextLong());

		layoutBranch.setName(ServiceTestUtil.randomString());

		layoutBranch.setDescription(ServiceTestUtil.randomString());

		layoutBranch.setMaster(ServiceTestUtil.randomBoolean());

		layoutBranch.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(layoutBranch);

		return layoutBranch;
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutBranchPersistenceTest.class);
	private LayoutBranchPersistence _persistence = (LayoutBranchPersistence)PortalBeanLocatorUtil.locate(LayoutBranchPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}