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

import com.liferay.portal.NoSuchWorkflowDefinitionLinkException;
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
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence;
import com.liferay.portal.service.persistence.WorkflowDefinitionLinkUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

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
public class WorkflowDefinitionLinkPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = WorkflowDefinitionLinkUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<WorkflowDefinitionLink> iterator = _workflowDefinitionLinks.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WorkflowDefinitionLink workflowDefinitionLink = _persistence.create(pk);

		Assert.assertNotNull(workflowDefinitionLink);

		Assert.assertEquals(workflowDefinitionLink.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		_persistence.remove(newWorkflowDefinitionLink);

		WorkflowDefinitionLink existingWorkflowDefinitionLink = _persistence.fetchByPrimaryKey(newWorkflowDefinitionLink.getPrimaryKey());

		Assert.assertNull(existingWorkflowDefinitionLink);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWorkflowDefinitionLink();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WorkflowDefinitionLink newWorkflowDefinitionLink = _persistence.create(pk);

		newWorkflowDefinitionLink.setMvccVersion(RandomTestUtil.nextLong());

		newWorkflowDefinitionLink.setGroupId(RandomTestUtil.nextLong());

		newWorkflowDefinitionLink.setCompanyId(RandomTestUtil.nextLong());

		newWorkflowDefinitionLink.setUserId(RandomTestUtil.nextLong());

		newWorkflowDefinitionLink.setUserName(RandomTestUtil.randomString());

		newWorkflowDefinitionLink.setCreateDate(RandomTestUtil.nextDate());

		newWorkflowDefinitionLink.setModifiedDate(RandomTestUtil.nextDate());

		newWorkflowDefinitionLink.setClassNameId(RandomTestUtil.nextLong());

		newWorkflowDefinitionLink.setClassPK(RandomTestUtil.nextLong());

		newWorkflowDefinitionLink.setTypePK(RandomTestUtil.nextLong());

		newWorkflowDefinitionLink.setWorkflowDefinitionName(RandomTestUtil.randomString());

		newWorkflowDefinitionLink.setWorkflowDefinitionVersion(RandomTestUtil.nextInt());

		_workflowDefinitionLinks.add(_persistence.update(
				newWorkflowDefinitionLink));

		WorkflowDefinitionLink existingWorkflowDefinitionLink = _persistence.findByPrimaryKey(newWorkflowDefinitionLink.getPrimaryKey());

		Assert.assertEquals(existingWorkflowDefinitionLink.getMvccVersion(),
			newWorkflowDefinitionLink.getMvccVersion());
		Assert.assertEquals(existingWorkflowDefinitionLink.getWorkflowDefinitionLinkId(),
			newWorkflowDefinitionLink.getWorkflowDefinitionLinkId());
		Assert.assertEquals(existingWorkflowDefinitionLink.getGroupId(),
			newWorkflowDefinitionLink.getGroupId());
		Assert.assertEquals(existingWorkflowDefinitionLink.getCompanyId(),
			newWorkflowDefinitionLink.getCompanyId());
		Assert.assertEquals(existingWorkflowDefinitionLink.getUserId(),
			newWorkflowDefinitionLink.getUserId());
		Assert.assertEquals(existingWorkflowDefinitionLink.getUserName(),
			newWorkflowDefinitionLink.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWorkflowDefinitionLink.getCreateDate()),
			Time.getShortTimestamp(newWorkflowDefinitionLink.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingWorkflowDefinitionLink.getModifiedDate()),
			Time.getShortTimestamp(newWorkflowDefinitionLink.getModifiedDate()));
		Assert.assertEquals(existingWorkflowDefinitionLink.getClassNameId(),
			newWorkflowDefinitionLink.getClassNameId());
		Assert.assertEquals(existingWorkflowDefinitionLink.getClassPK(),
			newWorkflowDefinitionLink.getClassPK());
		Assert.assertEquals(existingWorkflowDefinitionLink.getTypePK(),
			newWorkflowDefinitionLink.getTypePK());
		Assert.assertEquals(existingWorkflowDefinitionLink.getWorkflowDefinitionName(),
			newWorkflowDefinitionLink.getWorkflowDefinitionName());
		Assert.assertEquals(existingWorkflowDefinitionLink.getWorkflowDefinitionVersion(),
			newWorkflowDefinitionLink.getWorkflowDefinitionVersion());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByG_C_C() throws Exception {
		_persistence.countByG_C_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByG_C_C(0L, 0L, 0L);
	}

	@Test
	public void testCountByC_W_W() throws Exception {
		_persistence.countByC_W_W(RandomTestUtil.nextLong(), StringPool.BLANK,
			RandomTestUtil.nextInt());

		_persistence.countByC_W_W(0L, StringPool.NULL, 0);

		_persistence.countByC_W_W(0L, (String)null, 0);
	}

	@Test
	public void testCountByG_C_C_C_T() throws Exception {
		_persistence.countByG_C_C_C_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByG_C_C_C_T(0L, 0L, 0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		WorkflowDefinitionLink existingWorkflowDefinitionLink = _persistence.findByPrimaryKey(newWorkflowDefinitionLink.getPrimaryKey());

		Assert.assertEquals(existingWorkflowDefinitionLink,
			newWorkflowDefinitionLink);
	}

	@Test(expected = NoSuchWorkflowDefinitionLinkException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<WorkflowDefinitionLink> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("WorkflowDefinitionLink",
			"mvccVersion", true, "workflowDefinitionLinkId", true, "groupId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "classNameId", true,
			"classPK", true, "typePK", true, "workflowDefinitionName", true,
			"workflowDefinitionVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		WorkflowDefinitionLink existingWorkflowDefinitionLink = _persistence.fetchByPrimaryKey(newWorkflowDefinitionLink.getPrimaryKey());

		Assert.assertEquals(existingWorkflowDefinitionLink,
			newWorkflowDefinitionLink);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WorkflowDefinitionLink missingWorkflowDefinitionLink = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWorkflowDefinitionLink);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink1 = addWorkflowDefinitionLink();
		WorkflowDefinitionLink newWorkflowDefinitionLink2 = addWorkflowDefinitionLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWorkflowDefinitionLink1.getPrimaryKey());
		primaryKeys.add(newWorkflowDefinitionLink2.getPrimaryKey());

		Map<Serializable, WorkflowDefinitionLink> workflowDefinitionLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, workflowDefinitionLinks.size());
		Assert.assertEquals(newWorkflowDefinitionLink1,
			workflowDefinitionLinks.get(
				newWorkflowDefinitionLink1.getPrimaryKey()));
		Assert.assertEquals(newWorkflowDefinitionLink2,
			workflowDefinitionLinks.get(
				newWorkflowDefinitionLink2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, WorkflowDefinitionLink> workflowDefinitionLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(workflowDefinitionLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWorkflowDefinitionLink.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, WorkflowDefinitionLink> workflowDefinitionLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, workflowDefinitionLinks.size());
		Assert.assertEquals(newWorkflowDefinitionLink,
			workflowDefinitionLinks.get(
				newWorkflowDefinitionLink.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, WorkflowDefinitionLink> workflowDefinitionLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(workflowDefinitionLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWorkflowDefinitionLink.getPrimaryKey());

		Map<Serializable, WorkflowDefinitionLink> workflowDefinitionLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, workflowDefinitionLinks.size());
		Assert.assertEquals(newWorkflowDefinitionLink,
			workflowDefinitionLinks.get(
				newWorkflowDefinitionLink.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = WorkflowDefinitionLinkLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					WorkflowDefinitionLink workflowDefinitionLink = (WorkflowDefinitionLink)object;

					Assert.assertNotNull(workflowDefinitionLink);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowDefinitionLink.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"workflowDefinitionLinkId",
				newWorkflowDefinitionLink.getWorkflowDefinitionLinkId()));

		List<WorkflowDefinitionLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		WorkflowDefinitionLink existingWorkflowDefinitionLink = result.get(0);

		Assert.assertEquals(existingWorkflowDefinitionLink,
			newWorkflowDefinitionLink);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowDefinitionLink.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"workflowDefinitionLinkId", RandomTestUtil.nextLong()));

		List<WorkflowDefinitionLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowDefinitionLink.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"workflowDefinitionLinkId"));

		Object newWorkflowDefinitionLinkId = newWorkflowDefinitionLink.getWorkflowDefinitionLinkId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"workflowDefinitionLinkId",
				new Object[] { newWorkflowDefinitionLinkId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingWorkflowDefinitionLinkId = result.get(0);

		Assert.assertEquals(existingWorkflowDefinitionLinkId,
			newWorkflowDefinitionLinkId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowDefinitionLink.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"workflowDefinitionLinkId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"workflowDefinitionLinkId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		_persistence.clearCache();

		WorkflowDefinitionLink existingWorkflowDefinitionLink = _persistence.findByPrimaryKey(newWorkflowDefinitionLink.getPrimaryKey());

		Assert.assertEquals(existingWorkflowDefinitionLink.getGroupId(),
			ReflectionTestUtil.invoke(existingWorkflowDefinitionLink,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(existingWorkflowDefinitionLink.getCompanyId(),
			ReflectionTestUtil.invoke(existingWorkflowDefinitionLink,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertEquals(existingWorkflowDefinitionLink.getClassNameId(),
			ReflectionTestUtil.invoke(existingWorkflowDefinitionLink,
				"getOriginalClassNameId", new Class<?>[0]));
		Assert.assertEquals(existingWorkflowDefinitionLink.getClassPK(),
			ReflectionTestUtil.invoke(existingWorkflowDefinitionLink,
				"getOriginalClassPK", new Class<?>[0]));
		Assert.assertEquals(existingWorkflowDefinitionLink.getTypePK(),
			ReflectionTestUtil.invoke(existingWorkflowDefinitionLink,
				"getOriginalTypePK", new Class<?>[0]));
	}

	protected WorkflowDefinitionLink addWorkflowDefinitionLink()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		WorkflowDefinitionLink workflowDefinitionLink = _persistence.create(pk);

		workflowDefinitionLink.setMvccVersion(RandomTestUtil.nextLong());

		workflowDefinitionLink.setGroupId(RandomTestUtil.nextLong());

		workflowDefinitionLink.setCompanyId(RandomTestUtil.nextLong());

		workflowDefinitionLink.setUserId(RandomTestUtil.nextLong());

		workflowDefinitionLink.setUserName(RandomTestUtil.randomString());

		workflowDefinitionLink.setCreateDate(RandomTestUtil.nextDate());

		workflowDefinitionLink.setModifiedDate(RandomTestUtil.nextDate());

		workflowDefinitionLink.setClassNameId(RandomTestUtil.nextLong());

		workflowDefinitionLink.setClassPK(RandomTestUtil.nextLong());

		workflowDefinitionLink.setTypePK(RandomTestUtil.nextLong());

		workflowDefinitionLink.setWorkflowDefinitionName(RandomTestUtil.randomString());

		workflowDefinitionLink.setWorkflowDefinitionVersion(RandomTestUtil.nextInt());

		_workflowDefinitionLinks.add(_persistence.update(workflowDefinitionLink));

		return workflowDefinitionLink;
	}

	private List<WorkflowDefinitionLink> _workflowDefinitionLinks = new ArrayList<WorkflowDefinitionLink>();
	private WorkflowDefinitionLinkPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}