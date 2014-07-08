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

import com.liferay.portal.NoSuchWorkflowInstanceLinkException;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.WorkflowInstanceLink;
import com.liferay.portal.service.WorkflowInstanceLinkLocalServiceUtil;
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
public class WorkflowInstanceLinkPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<WorkflowInstanceLink> modelListener : _modelListeners) {
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

		for (ModelListener<WorkflowInstanceLink> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WorkflowInstanceLink workflowInstanceLink = _persistence.create(pk);

		Assert.assertNotNull(workflowInstanceLink);

		Assert.assertEquals(workflowInstanceLink.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WorkflowInstanceLink newWorkflowInstanceLink = addWorkflowInstanceLink();

		_persistence.remove(newWorkflowInstanceLink);

		WorkflowInstanceLink existingWorkflowInstanceLink = _persistence.fetchByPrimaryKey(newWorkflowInstanceLink.getPrimaryKey());

		Assert.assertNull(existingWorkflowInstanceLink);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWorkflowInstanceLink();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WorkflowInstanceLink newWorkflowInstanceLink = _persistence.create(pk);

		newWorkflowInstanceLink.setMvccVersion(RandomTestUtil.nextLong());

		newWorkflowInstanceLink.setGroupId(RandomTestUtil.nextLong());

		newWorkflowInstanceLink.setCompanyId(RandomTestUtil.nextLong());

		newWorkflowInstanceLink.setUserId(RandomTestUtil.nextLong());

		newWorkflowInstanceLink.setUserName(RandomTestUtil.randomString());

		newWorkflowInstanceLink.setCreateDate(RandomTestUtil.nextDate());

		newWorkflowInstanceLink.setModifiedDate(RandomTestUtil.nextDate());

		newWorkflowInstanceLink.setClassNameId(RandomTestUtil.nextLong());

		newWorkflowInstanceLink.setClassPK(RandomTestUtil.nextLong());

		newWorkflowInstanceLink.setWorkflowInstanceId(RandomTestUtil.nextLong());

		_persistence.update(newWorkflowInstanceLink);

		WorkflowInstanceLink existingWorkflowInstanceLink = _persistence.findByPrimaryKey(newWorkflowInstanceLink.getPrimaryKey());

		Assert.assertEquals(existingWorkflowInstanceLink.getMvccVersion(),
			newWorkflowInstanceLink.getMvccVersion());
		Assert.assertEquals(existingWorkflowInstanceLink.getWorkflowInstanceLinkId(),
			newWorkflowInstanceLink.getWorkflowInstanceLinkId());
		Assert.assertEquals(existingWorkflowInstanceLink.getGroupId(),
			newWorkflowInstanceLink.getGroupId());
		Assert.assertEquals(existingWorkflowInstanceLink.getCompanyId(),
			newWorkflowInstanceLink.getCompanyId());
		Assert.assertEquals(existingWorkflowInstanceLink.getUserId(),
			newWorkflowInstanceLink.getUserId());
		Assert.assertEquals(existingWorkflowInstanceLink.getUserName(),
			newWorkflowInstanceLink.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWorkflowInstanceLink.getCreateDate()),
			Time.getShortTimestamp(newWorkflowInstanceLink.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingWorkflowInstanceLink.getModifiedDate()),
			Time.getShortTimestamp(newWorkflowInstanceLink.getModifiedDate()));
		Assert.assertEquals(existingWorkflowInstanceLink.getClassNameId(),
			newWorkflowInstanceLink.getClassNameId());
		Assert.assertEquals(existingWorkflowInstanceLink.getClassPK(),
			newWorkflowInstanceLink.getClassPK());
		Assert.assertEquals(existingWorkflowInstanceLink.getWorkflowInstanceId(),
			newWorkflowInstanceLink.getWorkflowInstanceId());
	}

	@Test
	public void testCountByG_C_C_C() {
		try {
			_persistence.countByG_C_C_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByG_C_C_C(0L, 0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		WorkflowInstanceLink newWorkflowInstanceLink = addWorkflowInstanceLink();

		WorkflowInstanceLink existingWorkflowInstanceLink = _persistence.findByPrimaryKey(newWorkflowInstanceLink.getPrimaryKey());

		Assert.assertEquals(existingWorkflowInstanceLink,
			newWorkflowInstanceLink);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchWorkflowInstanceLinkException");
		}
		catch (NoSuchWorkflowInstanceLinkException nsee) {
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
		return OrderByComparatorFactoryUtil.create("WorkflowInstanceLink",
			"mvccVersion", true, "workflowInstanceLinkId", true, "groupId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "classNameId", true,
			"classPK", true, "workflowInstanceId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WorkflowInstanceLink newWorkflowInstanceLink = addWorkflowInstanceLink();

		WorkflowInstanceLink existingWorkflowInstanceLink = _persistence.fetchByPrimaryKey(newWorkflowInstanceLink.getPrimaryKey());

		Assert.assertEquals(existingWorkflowInstanceLink,
			newWorkflowInstanceLink);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WorkflowInstanceLink missingWorkflowInstanceLink = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWorkflowInstanceLink);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		WorkflowInstanceLink newWorkflowInstanceLink1 = addWorkflowInstanceLink();
		WorkflowInstanceLink newWorkflowInstanceLink2 = addWorkflowInstanceLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWorkflowInstanceLink1.getPrimaryKey());
		primaryKeys.add(newWorkflowInstanceLink2.getPrimaryKey());

		Map<Serializable, WorkflowInstanceLink> workflowInstanceLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, workflowInstanceLinks.size());
		Assert.assertEquals(newWorkflowInstanceLink1,
			workflowInstanceLinks.get(newWorkflowInstanceLink1.getPrimaryKey()));
		Assert.assertEquals(newWorkflowInstanceLink2,
			workflowInstanceLinks.get(newWorkflowInstanceLink2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, WorkflowInstanceLink> workflowInstanceLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(workflowInstanceLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		WorkflowInstanceLink newWorkflowInstanceLink = addWorkflowInstanceLink();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWorkflowInstanceLink.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, WorkflowInstanceLink> workflowInstanceLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, workflowInstanceLinks.size());
		Assert.assertEquals(newWorkflowInstanceLink,
			workflowInstanceLinks.get(newWorkflowInstanceLink.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, WorkflowInstanceLink> workflowInstanceLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(workflowInstanceLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		WorkflowInstanceLink newWorkflowInstanceLink = addWorkflowInstanceLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWorkflowInstanceLink.getPrimaryKey());

		Map<Serializable, WorkflowInstanceLink> workflowInstanceLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, workflowInstanceLinks.size());
		Assert.assertEquals(newWorkflowInstanceLink,
			workflowInstanceLinks.get(newWorkflowInstanceLink.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = WorkflowInstanceLinkLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					WorkflowInstanceLink workflowInstanceLink = (WorkflowInstanceLink)object;

					Assert.assertNotNull(workflowInstanceLink);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WorkflowInstanceLink newWorkflowInstanceLink = addWorkflowInstanceLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowInstanceLink.class,
				WorkflowInstanceLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("workflowInstanceLinkId",
				newWorkflowInstanceLink.getWorkflowInstanceLinkId()));

		List<WorkflowInstanceLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		WorkflowInstanceLink existingWorkflowInstanceLink = result.get(0);

		Assert.assertEquals(existingWorkflowInstanceLink,
			newWorkflowInstanceLink);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowInstanceLink.class,
				WorkflowInstanceLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("workflowInstanceLinkId",
				RandomTestUtil.nextLong()));

		List<WorkflowInstanceLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		WorkflowInstanceLink newWorkflowInstanceLink = addWorkflowInstanceLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowInstanceLink.class,
				WorkflowInstanceLink.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"workflowInstanceLinkId"));

		Object newWorkflowInstanceLinkId = newWorkflowInstanceLink.getWorkflowInstanceLinkId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("workflowInstanceLinkId",
				new Object[] { newWorkflowInstanceLinkId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingWorkflowInstanceLinkId = result.get(0);

		Assert.assertEquals(existingWorkflowInstanceLinkId,
			newWorkflowInstanceLinkId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowInstanceLink.class,
				WorkflowInstanceLink.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"workflowInstanceLinkId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("workflowInstanceLinkId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected WorkflowInstanceLink addWorkflowInstanceLink()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		WorkflowInstanceLink workflowInstanceLink = _persistence.create(pk);

		workflowInstanceLink.setMvccVersion(RandomTestUtil.nextLong());

		workflowInstanceLink.setGroupId(RandomTestUtil.nextLong());

		workflowInstanceLink.setCompanyId(RandomTestUtil.nextLong());

		workflowInstanceLink.setUserId(RandomTestUtil.nextLong());

		workflowInstanceLink.setUserName(RandomTestUtil.randomString());

		workflowInstanceLink.setCreateDate(RandomTestUtil.nextDate());

		workflowInstanceLink.setModifiedDate(RandomTestUtil.nextDate());

		workflowInstanceLink.setClassNameId(RandomTestUtil.nextLong());

		workflowInstanceLink.setClassPK(RandomTestUtil.nextLong());

		workflowInstanceLink.setWorkflowInstanceId(RandomTestUtil.nextLong());

		_persistence.update(workflowInstanceLink);

		return workflowInstanceLink;
	}

	private static Log _log = LogFactoryUtil.getLog(WorkflowInstanceLinkPersistenceTest.class);
	private ModelListener<WorkflowInstanceLink>[] _modelListeners;
	private WorkflowInstanceLinkPersistence _persistence = (WorkflowInstanceLinkPersistence)PortalBeanLocatorUtil.locate(WorkflowInstanceLinkPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}