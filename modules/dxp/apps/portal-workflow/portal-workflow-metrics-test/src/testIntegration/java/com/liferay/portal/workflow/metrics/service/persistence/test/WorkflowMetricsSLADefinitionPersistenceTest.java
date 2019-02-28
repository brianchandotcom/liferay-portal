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
import com.liferay.portal.workflow.metrics.exception.NoSuchSLADefinitionException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalServiceUtil;
import com.liferay.portal.workflow.metrics.service.persistence.WorkflowMetricsSLADefinitionPersistence;
import com.liferay.portal.workflow.metrics.service.persistence.WorkflowMetricsSLADefinitionUtil;

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
public class WorkflowMetricsSLADefinitionPersistenceTest {

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
		_persistence = WorkflowMetricsSLADefinitionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<WorkflowMetricsSLADefinition> iterator =
			_workflowMetricsSLADefinitions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			_persistence.create(pk);

		Assert.assertNotNull(workflowMetricsSLADefinition);

		Assert.assertEquals(workflowMetricsSLADefinition.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WorkflowMetricsSLADefinition newWorkflowMetricsSLADefinition =
			addWorkflowMetricsSLADefinition();

		_persistence.remove(newWorkflowMetricsSLADefinition);

		WorkflowMetricsSLADefinition existingWorkflowMetricsSLADefinition =
			_persistence.fetchByPrimaryKey(
				newWorkflowMetricsSLADefinition.getPrimaryKey());

		Assert.assertNull(existingWorkflowMetricsSLADefinition);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWorkflowMetricsSLADefinition();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WorkflowMetricsSLADefinition newWorkflowMetricsSLADefinition =
			_persistence.create(pk);

		newWorkflowMetricsSLADefinition.setMvccVersion(
			RandomTestUtil.nextLong());

		newWorkflowMetricsSLADefinition.setUuid(RandomTestUtil.randomString());

		newWorkflowMetricsSLADefinition.setGroupId(RandomTestUtil.nextLong());

		newWorkflowMetricsSLADefinition.setCompanyId(RandomTestUtil.nextLong());

		newWorkflowMetricsSLADefinition.setUserId(RandomTestUtil.nextLong());

		newWorkflowMetricsSLADefinition.setUserName(
			RandomTestUtil.randomString());

		newWorkflowMetricsSLADefinition.setCreateDate(
			RandomTestUtil.nextDate());

		newWorkflowMetricsSLADefinition.setModifiedDate(
			RandomTestUtil.nextDate());

		newWorkflowMetricsSLADefinition.setName(RandomTestUtil.randomString());

		newWorkflowMetricsSLADefinition.setDescription(
			RandomTestUtil.randomString());

		newWorkflowMetricsSLADefinition.setDuration(RandomTestUtil.nextLong());

		newWorkflowMetricsSLADefinition.setProcessId(RandomTestUtil.nextLong());

		newWorkflowMetricsSLADefinition.setPauseNodeNames(
			RandomTestUtil.randomString());

		newWorkflowMetricsSLADefinition.setStartNodeNames(
			RandomTestUtil.randomString());

		newWorkflowMetricsSLADefinition.setStopNodeNames(
			RandomTestUtil.randomString());

		_workflowMetricsSLADefinitions.add(
			_persistence.update(newWorkflowMetricsSLADefinition));

		WorkflowMetricsSLADefinition existingWorkflowMetricsSLADefinition =
			_persistence.findByPrimaryKey(
				newWorkflowMetricsSLADefinition.getPrimaryKey());

		Assert.assertEquals(
			existingWorkflowMetricsSLADefinition.getMvccVersion(),
			newWorkflowMetricsSLADefinition.getMvccVersion());
		Assert.assertEquals(
			existingWorkflowMetricsSLADefinition.getUuid(),
			newWorkflowMetricsSLADefinition.getUuid());
		Assert.assertEquals(
			existingWorkflowMetricsSLADefinition.
				getWorkflowMetricsSLADefinitionId(),
			newWorkflowMetricsSLADefinition.
				getWorkflowMetricsSLADefinitionId());
		Assert.assertEquals(
			existingWorkflowMetricsSLADefinition.getGroupId(),
			newWorkflowMetricsSLADefinition.getGroupId());
		Assert.assertEquals(
			existingWorkflowMetricsSLADefinition.getCompanyId(),
			newWorkflowMetricsSLADefinition.getCompanyId());
		Assert.assertEquals(
			existingWorkflowMetricsSLADefinition.getUserId(),
			newWorkflowMetricsSLADefinition.getUserId());
		Assert.assertEquals(
			existingWorkflowMetricsSLADefinition.getUserName(),
			newWorkflowMetricsSLADefinition.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingWorkflowMetricsSLADefinition.getCreateDate()),
			Time.getShortTimestamp(
				newWorkflowMetricsSLADefinition.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingWorkflowMetricsSLADefinition.getModifiedDate()),
			Time.getShortTimestamp(
				newWorkflowMetricsSLADefinition.getModifiedDate()));
		Assert.assertEquals(
			existingWorkflowMetricsSLADefinition.getName(),
			newWorkflowMetricsSLADefinition.getName());
		Assert.assertEquals(
			existingWorkflowMetricsSLADefinition.getDescription(),
			newWorkflowMetricsSLADefinition.getDescription());
		Assert.assertEquals(
			existingWorkflowMetricsSLADefinition.getDuration(),
			newWorkflowMetricsSLADefinition.getDuration());
		Assert.assertEquals(
			existingWorkflowMetricsSLADefinition.getProcessId(),
			newWorkflowMetricsSLADefinition.getProcessId());
		Assert.assertEquals(
			existingWorkflowMetricsSLADefinition.getPauseNodeNames(),
			newWorkflowMetricsSLADefinition.getPauseNodeNames());
		Assert.assertEquals(
			existingWorkflowMetricsSLADefinition.getStartNodeNames(),
			newWorkflowMetricsSLADefinition.getStartNodeNames());
		Assert.assertEquals(
			existingWorkflowMetricsSLADefinition.getStopNodeNames(),
			newWorkflowMetricsSLADefinition.getStopNodeNames());
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
	public void testCountByC_P() throws Exception {
		_persistence.countByC_P(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_P(0L, 0L);
	}

	@Test
	public void testCountByC_N_P() throws Exception {
		_persistence.countByC_N_P(
			RandomTestUtil.nextLong(), "", RandomTestUtil.nextLong());

		_persistence.countByC_N_P(0L, "null", 0L);

		_persistence.countByC_N_P(0L, (String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		WorkflowMetricsSLADefinition newWorkflowMetricsSLADefinition =
			addWorkflowMetricsSLADefinition();

		WorkflowMetricsSLADefinition existingWorkflowMetricsSLADefinition =
			_persistence.findByPrimaryKey(
				newWorkflowMetricsSLADefinition.getPrimaryKey());

		Assert.assertEquals(
			existingWorkflowMetricsSLADefinition,
			newWorkflowMetricsSLADefinition);
	}

	@Test(expected = NoSuchSLADefinitionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<WorkflowMetricsSLADefinition>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"WorkflowMetricsSLADefinition", "mvccVersion", true, "uuid", true,
			"workflowMetricsSLADefinitionId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "name", true, "description", true,
			"duration", true, "processId", true, "pauseNodeNames", true,
			"startNodeNames", true, "stopNodeNames", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WorkflowMetricsSLADefinition newWorkflowMetricsSLADefinition =
			addWorkflowMetricsSLADefinition();

		WorkflowMetricsSLADefinition existingWorkflowMetricsSLADefinition =
			_persistence.fetchByPrimaryKey(
				newWorkflowMetricsSLADefinition.getPrimaryKey());

		Assert.assertEquals(
			existingWorkflowMetricsSLADefinition,
			newWorkflowMetricsSLADefinition);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WorkflowMetricsSLADefinition missingWorkflowMetricsSLADefinition =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWorkflowMetricsSLADefinition);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		WorkflowMetricsSLADefinition newWorkflowMetricsSLADefinition1 =
			addWorkflowMetricsSLADefinition();
		WorkflowMetricsSLADefinition newWorkflowMetricsSLADefinition2 =
			addWorkflowMetricsSLADefinition();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWorkflowMetricsSLADefinition1.getPrimaryKey());
		primaryKeys.add(newWorkflowMetricsSLADefinition2.getPrimaryKey());

		Map<Serializable, WorkflowMetricsSLADefinition>
			workflowMetricsSLADefinitions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, workflowMetricsSLADefinitions.size());
		Assert.assertEquals(
			newWorkflowMetricsSLADefinition1,
			workflowMetricsSLADefinitions.get(
				newWorkflowMetricsSLADefinition1.getPrimaryKey()));
		Assert.assertEquals(
			newWorkflowMetricsSLADefinition2,
			workflowMetricsSLADefinitions.get(
				newWorkflowMetricsSLADefinition2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, WorkflowMetricsSLADefinition>
			workflowMetricsSLADefinitions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(workflowMetricsSLADefinitions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		WorkflowMetricsSLADefinition newWorkflowMetricsSLADefinition =
			addWorkflowMetricsSLADefinition();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWorkflowMetricsSLADefinition.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, WorkflowMetricsSLADefinition>
			workflowMetricsSLADefinitions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, workflowMetricsSLADefinitions.size());
		Assert.assertEquals(
			newWorkflowMetricsSLADefinition,
			workflowMetricsSLADefinitions.get(
				newWorkflowMetricsSLADefinition.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, WorkflowMetricsSLADefinition>
			workflowMetricsSLADefinitions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(workflowMetricsSLADefinitions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		WorkflowMetricsSLADefinition newWorkflowMetricsSLADefinition =
			addWorkflowMetricsSLADefinition();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWorkflowMetricsSLADefinition.getPrimaryKey());

		Map<Serializable, WorkflowMetricsSLADefinition>
			workflowMetricsSLADefinitions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, workflowMetricsSLADefinitions.size());
		Assert.assertEquals(
			newWorkflowMetricsSLADefinition,
			workflowMetricsSLADefinitions.get(
				newWorkflowMetricsSLADefinition.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			WorkflowMetricsSLADefinitionLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<WorkflowMetricsSLADefinition>() {

				@Override
				public void performAction(
					WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

					Assert.assertNotNull(workflowMetricsSLADefinition);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		WorkflowMetricsSLADefinition newWorkflowMetricsSLADefinition =
			addWorkflowMetricsSLADefinition();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WorkflowMetricsSLADefinition.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"workflowMetricsSLADefinitionId",
				newWorkflowMetricsSLADefinition.
					getWorkflowMetricsSLADefinitionId()));

		List<WorkflowMetricsSLADefinition> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		WorkflowMetricsSLADefinition existingWorkflowMetricsSLADefinition =
			result.get(0);

		Assert.assertEquals(
			existingWorkflowMetricsSLADefinition,
			newWorkflowMetricsSLADefinition);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WorkflowMetricsSLADefinition.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"workflowMetricsSLADefinitionId", RandomTestUtil.nextLong()));

		List<WorkflowMetricsSLADefinition> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		WorkflowMetricsSLADefinition newWorkflowMetricsSLADefinition =
			addWorkflowMetricsSLADefinition();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WorkflowMetricsSLADefinition.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("workflowMetricsSLADefinitionId"));

		Object newWorkflowMetricsSLADefinitionId =
			newWorkflowMetricsSLADefinition.getWorkflowMetricsSLADefinitionId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"workflowMetricsSLADefinitionId",
				new Object[] {newWorkflowMetricsSLADefinitionId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingWorkflowMetricsSLADefinitionId = result.get(0);

		Assert.assertEquals(
			existingWorkflowMetricsSLADefinitionId,
			newWorkflowMetricsSLADefinitionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WorkflowMetricsSLADefinition.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("workflowMetricsSLADefinitionId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"workflowMetricsSLADefinitionId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		WorkflowMetricsSLADefinition newWorkflowMetricsSLADefinition =
			addWorkflowMetricsSLADefinition();

		_persistence.clearCache();

		WorkflowMetricsSLADefinition existingWorkflowMetricsSLADefinition =
			_persistence.findByPrimaryKey(
				newWorkflowMetricsSLADefinition.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingWorkflowMetricsSLADefinition.getUuid(),
				ReflectionTestUtil.invoke(
					existingWorkflowMetricsSLADefinition, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingWorkflowMetricsSLADefinition.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingWorkflowMetricsSLADefinition, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingWorkflowMetricsSLADefinition.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				existingWorkflowMetricsSLADefinition, "getOriginalCompanyId",
				new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingWorkflowMetricsSLADefinition.getName(),
				ReflectionTestUtil.invoke(
					existingWorkflowMetricsSLADefinition, "getOriginalName",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingWorkflowMetricsSLADefinition.getProcessId()),
			ReflectionTestUtil.<Long>invoke(
				existingWorkflowMetricsSLADefinition, "getOriginalProcessId",
				new Class<?>[0]));
	}

	protected WorkflowMetricsSLADefinition addWorkflowMetricsSLADefinition()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			_persistence.create(pk);

		workflowMetricsSLADefinition.setMvccVersion(RandomTestUtil.nextLong());

		workflowMetricsSLADefinition.setUuid(RandomTestUtil.randomString());

		workflowMetricsSLADefinition.setGroupId(RandomTestUtil.nextLong());

		workflowMetricsSLADefinition.setCompanyId(RandomTestUtil.nextLong());

		workflowMetricsSLADefinition.setUserId(RandomTestUtil.nextLong());

		workflowMetricsSLADefinition.setUserName(RandomTestUtil.randomString());

		workflowMetricsSLADefinition.setCreateDate(RandomTestUtil.nextDate());

		workflowMetricsSLADefinition.setModifiedDate(RandomTestUtil.nextDate());

		workflowMetricsSLADefinition.setName(RandomTestUtil.randomString());

		workflowMetricsSLADefinition.setDescription(
			RandomTestUtil.randomString());

		workflowMetricsSLADefinition.setDuration(RandomTestUtil.nextLong());

		workflowMetricsSLADefinition.setProcessId(RandomTestUtil.nextLong());

		workflowMetricsSLADefinition.setPauseNodeNames(
			RandomTestUtil.randomString());

		workflowMetricsSLADefinition.setStartNodeNames(
			RandomTestUtil.randomString());

		workflowMetricsSLADefinition.setStopNodeNames(
			RandomTestUtil.randomString());

		_workflowMetricsSLADefinitions.add(
			_persistence.update(workflowMetricsSLADefinition));

		return workflowMetricsSLADefinition;
	}

	private List<WorkflowMetricsSLADefinition> _workflowMetricsSLADefinitions =
		new ArrayList<WorkflowMetricsSLADefinition>();
	private WorkflowMetricsSLADefinitionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}