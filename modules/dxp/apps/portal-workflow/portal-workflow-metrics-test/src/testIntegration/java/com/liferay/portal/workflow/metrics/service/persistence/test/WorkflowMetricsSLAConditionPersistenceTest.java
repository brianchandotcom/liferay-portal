/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.service.persistence.test;

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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.workflow.metrics.exception.NoSuchSLAConditionException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACondition;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLAConditionLocalServiceUtil;
import com.liferay.portal.workflow.metrics.service.persistence.WorkflowMetricsSLAConditionPersistence;
import com.liferay.portal.workflow.metrics.service.persistence.WorkflowMetricsSLAConditionUtil;

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
public class WorkflowMetricsSLAConditionPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.workflow.metrics.service"));

	@Before
	public void setUp() {
		_persistence = WorkflowMetricsSLAConditionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<WorkflowMetricsSLACondition> iterator =
			_workflowMetricsSLAConditions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WorkflowMetricsSLACondition workflowMetricsSLACondition =
			_persistence.create(pk);

		Assert.assertNotNull(workflowMetricsSLACondition);

		Assert.assertEquals(workflowMetricsSLACondition.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WorkflowMetricsSLACondition newWorkflowMetricsSLACondition =
			addWorkflowMetricsSLACondition();

		_persistence.remove(newWorkflowMetricsSLACondition);

		WorkflowMetricsSLACondition existingWorkflowMetricsSLACondition =
			_persistence.fetchByPrimaryKey(
				newWorkflowMetricsSLACondition.getPrimaryKey());

		Assert.assertNull(existingWorkflowMetricsSLACondition);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWorkflowMetricsSLACondition();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WorkflowMetricsSLACondition newWorkflowMetricsSLACondition =
			_persistence.create(pk);

		newWorkflowMetricsSLACondition.setMvccVersion(
			RandomTestUtil.nextLong());

		newWorkflowMetricsSLACondition.setUuid(RandomTestUtil.randomString());

		newWorkflowMetricsSLACondition.setGroupId(RandomTestUtil.nextLong());

		newWorkflowMetricsSLACondition.setCompanyId(RandomTestUtil.nextLong());

		newWorkflowMetricsSLACondition.setUserId(RandomTestUtil.nextLong());

		newWorkflowMetricsSLACondition.setUserName(
			RandomTestUtil.randomString());

		newWorkflowMetricsSLACondition.setCreateDate(RandomTestUtil.nextDate());

		newWorkflowMetricsSLACondition.setModifiedDate(
			RandomTestUtil.nextDate());

		newWorkflowMetricsSLACondition.setWorkflowMetricsSLADefinitionId(
			RandomTestUtil.nextLong());

		_workflowMetricsSLAConditions.add(
			_persistence.update(newWorkflowMetricsSLACondition));

		WorkflowMetricsSLACondition existingWorkflowMetricsSLACondition =
			_persistence.findByPrimaryKey(
				newWorkflowMetricsSLACondition.getPrimaryKey());

		Assert.assertEquals(
			existingWorkflowMetricsSLACondition.getMvccVersion(),
			newWorkflowMetricsSLACondition.getMvccVersion());
		Assert.assertEquals(
			existingWorkflowMetricsSLACondition.getUuid(),
			newWorkflowMetricsSLACondition.getUuid());
		Assert.assertEquals(
			existingWorkflowMetricsSLACondition.
				getWorkflowMetricsSLAConditionId(),
			newWorkflowMetricsSLACondition.getWorkflowMetricsSLAConditionId());
		Assert.assertEquals(
			existingWorkflowMetricsSLACondition.getGroupId(),
			newWorkflowMetricsSLACondition.getGroupId());
		Assert.assertEquals(
			existingWorkflowMetricsSLACondition.getCompanyId(),
			newWorkflowMetricsSLACondition.getCompanyId());
		Assert.assertEquals(
			existingWorkflowMetricsSLACondition.getUserId(),
			newWorkflowMetricsSLACondition.getUserId());
		Assert.assertEquals(
			existingWorkflowMetricsSLACondition.getUserName(),
			newWorkflowMetricsSLACondition.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingWorkflowMetricsSLACondition.getCreateDate()),
			Time.getShortTimestamp(
				newWorkflowMetricsSLACondition.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingWorkflowMetricsSLACondition.getModifiedDate()),
			Time.getShortTimestamp(
				newWorkflowMetricsSLACondition.getModifiedDate()));
		Assert.assertEquals(
			existingWorkflowMetricsSLACondition.
				getWorkflowMetricsSLADefinitionId(),
			newWorkflowMetricsSLACondition.getWorkflowMetricsSLADefinitionId());
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
	public void testCountByC_WMSLADI() throws Exception {
		_persistence.countByC_WMSLADI(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_WMSLADI(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		WorkflowMetricsSLACondition newWorkflowMetricsSLACondition =
			addWorkflowMetricsSLACondition();

		WorkflowMetricsSLACondition existingWorkflowMetricsSLACondition =
			_persistence.findByPrimaryKey(
				newWorkflowMetricsSLACondition.getPrimaryKey());

		Assert.assertEquals(
			existingWorkflowMetricsSLACondition,
			newWorkflowMetricsSLACondition);
	}

	@Test(expected = NoSuchSLAConditionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<WorkflowMetricsSLACondition>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"WorkflowMetricsSLACondition", "mvccVersion", true, "uuid", true,
			"workflowMetricsSLAConditionId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "workflowMetricsSLADefinitionId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WorkflowMetricsSLACondition newWorkflowMetricsSLACondition =
			addWorkflowMetricsSLACondition();

		WorkflowMetricsSLACondition existingWorkflowMetricsSLACondition =
			_persistence.fetchByPrimaryKey(
				newWorkflowMetricsSLACondition.getPrimaryKey());

		Assert.assertEquals(
			existingWorkflowMetricsSLACondition,
			newWorkflowMetricsSLACondition);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WorkflowMetricsSLACondition missingWorkflowMetricsSLACondition =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWorkflowMetricsSLACondition);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		WorkflowMetricsSLACondition newWorkflowMetricsSLACondition1 =
			addWorkflowMetricsSLACondition();
		WorkflowMetricsSLACondition newWorkflowMetricsSLACondition2 =
			addWorkflowMetricsSLACondition();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWorkflowMetricsSLACondition1.getPrimaryKey());
		primaryKeys.add(newWorkflowMetricsSLACondition2.getPrimaryKey());

		Map<Serializable, WorkflowMetricsSLACondition>
			workflowMetricsSLAConditions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, workflowMetricsSLAConditions.size());
		Assert.assertEquals(
			newWorkflowMetricsSLACondition1,
			workflowMetricsSLAConditions.get(
				newWorkflowMetricsSLACondition1.getPrimaryKey()));
		Assert.assertEquals(
			newWorkflowMetricsSLACondition2,
			workflowMetricsSLAConditions.get(
				newWorkflowMetricsSLACondition2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, WorkflowMetricsSLACondition>
			workflowMetricsSLAConditions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(workflowMetricsSLAConditions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		WorkflowMetricsSLACondition newWorkflowMetricsSLACondition =
			addWorkflowMetricsSLACondition();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWorkflowMetricsSLACondition.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, WorkflowMetricsSLACondition>
			workflowMetricsSLAConditions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, workflowMetricsSLAConditions.size());
		Assert.assertEquals(
			newWorkflowMetricsSLACondition,
			workflowMetricsSLAConditions.get(
				newWorkflowMetricsSLACondition.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, WorkflowMetricsSLACondition>
			workflowMetricsSLAConditions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(workflowMetricsSLAConditions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		WorkflowMetricsSLACondition newWorkflowMetricsSLACondition =
			addWorkflowMetricsSLACondition();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWorkflowMetricsSLACondition.getPrimaryKey());

		Map<Serializable, WorkflowMetricsSLACondition>
			workflowMetricsSLAConditions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, workflowMetricsSLAConditions.size());
		Assert.assertEquals(
			newWorkflowMetricsSLACondition,
			workflowMetricsSLAConditions.get(
				newWorkflowMetricsSLACondition.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			WorkflowMetricsSLAConditionLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<WorkflowMetricsSLACondition>() {

				@Override
				public void performAction(
					WorkflowMetricsSLACondition workflowMetricsSLACondition) {

					Assert.assertNotNull(workflowMetricsSLACondition);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		WorkflowMetricsSLACondition newWorkflowMetricsSLACondition =
			addWorkflowMetricsSLACondition();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WorkflowMetricsSLACondition.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"workflowMetricsSLAConditionId",
				newWorkflowMetricsSLACondition.
					getWorkflowMetricsSLAConditionId()));

		List<WorkflowMetricsSLACondition> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		WorkflowMetricsSLACondition existingWorkflowMetricsSLACondition =
			result.get(0);

		Assert.assertEquals(
			existingWorkflowMetricsSLACondition,
			newWorkflowMetricsSLACondition);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WorkflowMetricsSLACondition.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"workflowMetricsSLAConditionId", RandomTestUtil.nextLong()));

		List<WorkflowMetricsSLACondition> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		WorkflowMetricsSLACondition newWorkflowMetricsSLACondition =
			addWorkflowMetricsSLACondition();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WorkflowMetricsSLACondition.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("workflowMetricsSLAConditionId"));

		Object newWorkflowMetricsSLAConditionId =
			newWorkflowMetricsSLACondition.getWorkflowMetricsSLAConditionId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"workflowMetricsSLAConditionId",
				new Object[] {newWorkflowMetricsSLAConditionId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingWorkflowMetricsSLAConditionId = result.get(0);

		Assert.assertEquals(
			existingWorkflowMetricsSLAConditionId,
			newWorkflowMetricsSLAConditionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WorkflowMetricsSLACondition.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("workflowMetricsSLAConditionId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"workflowMetricsSLAConditionId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		WorkflowMetricsSLACondition newWorkflowMetricsSLACondition =
			addWorkflowMetricsSLACondition();

		_persistence.clearCache();

		WorkflowMetricsSLACondition existingWorkflowMetricsSLACondition =
			_persistence.findByPrimaryKey(
				newWorkflowMetricsSLACondition.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingWorkflowMetricsSLACondition.getUuid(),
				ReflectionTestUtil.invoke(
					existingWorkflowMetricsSLACondition, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingWorkflowMetricsSLACondition.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingWorkflowMetricsSLACondition, "getOriginalGroupId",
				new Class<?>[0]));
	}

	protected WorkflowMetricsSLACondition addWorkflowMetricsSLACondition()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		WorkflowMetricsSLACondition workflowMetricsSLACondition =
			_persistence.create(pk);

		workflowMetricsSLACondition.setMvccVersion(RandomTestUtil.nextLong());

		workflowMetricsSLACondition.setUuid(RandomTestUtil.randomString());

		workflowMetricsSLACondition.setGroupId(RandomTestUtil.nextLong());

		workflowMetricsSLACondition.setCompanyId(RandomTestUtil.nextLong());

		workflowMetricsSLACondition.setUserId(RandomTestUtil.nextLong());

		workflowMetricsSLACondition.setUserName(RandomTestUtil.randomString());

		workflowMetricsSLACondition.setCreateDate(RandomTestUtil.nextDate());

		workflowMetricsSLACondition.setModifiedDate(RandomTestUtil.nextDate());

		workflowMetricsSLACondition.setWorkflowMetricsSLADefinitionId(
			RandomTestUtil.nextLong());

		_workflowMetricsSLAConditions.add(
			_persistence.update(workflowMetricsSLACondition));

		return workflowMetricsSLACondition;
	}

	private List<WorkflowMetricsSLACondition> _workflowMetricsSLAConditions =
		new ArrayList<WorkflowMetricsSLACondition>();
	private WorkflowMetricsSLAConditionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}