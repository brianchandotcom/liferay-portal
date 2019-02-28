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
import com.liferay.portal.workflow.metrics.exception.NoSuchSLACalendarException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLACalendar;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLACalendarLocalServiceUtil;
import com.liferay.portal.workflow.metrics.service.persistence.WorkflowMetricsSLACalendarPersistence;
import com.liferay.portal.workflow.metrics.service.persistence.WorkflowMetricsSLACalendarUtil;

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
public class WorkflowMetricsSLACalendarPersistenceTest {

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
		_persistence = WorkflowMetricsSLACalendarUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<WorkflowMetricsSLACalendar> iterator =
			_workflowMetricsSLACalendars.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WorkflowMetricsSLACalendar workflowMetricsSLACalendar =
			_persistence.create(pk);

		Assert.assertNotNull(workflowMetricsSLACalendar);

		Assert.assertEquals(workflowMetricsSLACalendar.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WorkflowMetricsSLACalendar newWorkflowMetricsSLACalendar =
			addWorkflowMetricsSLACalendar();

		_persistence.remove(newWorkflowMetricsSLACalendar);

		WorkflowMetricsSLACalendar existingWorkflowMetricsSLACalendar =
			_persistence.fetchByPrimaryKey(
				newWorkflowMetricsSLACalendar.getPrimaryKey());

		Assert.assertNull(existingWorkflowMetricsSLACalendar);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWorkflowMetricsSLACalendar();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WorkflowMetricsSLACalendar newWorkflowMetricsSLACalendar =
			_persistence.create(pk);

		newWorkflowMetricsSLACalendar.setMvccVersion(RandomTestUtil.nextLong());

		newWorkflowMetricsSLACalendar.setUuid(RandomTestUtil.randomString());

		newWorkflowMetricsSLACalendar.setGroupId(RandomTestUtil.nextLong());

		newWorkflowMetricsSLACalendar.setCompanyId(RandomTestUtil.nextLong());

		newWorkflowMetricsSLACalendar.setUserId(RandomTestUtil.nextLong());

		newWorkflowMetricsSLACalendar.setUserName(
			RandomTestUtil.randomString());

		newWorkflowMetricsSLACalendar.setCreateDate(RandomTestUtil.nextDate());

		newWorkflowMetricsSLACalendar.setModifiedDate(
			RandomTestUtil.nextDate());

		_workflowMetricsSLACalendars.add(
			_persistence.update(newWorkflowMetricsSLACalendar));

		WorkflowMetricsSLACalendar existingWorkflowMetricsSLACalendar =
			_persistence.findByPrimaryKey(
				newWorkflowMetricsSLACalendar.getPrimaryKey());

		Assert.assertEquals(
			existingWorkflowMetricsSLACalendar.getMvccVersion(),
			newWorkflowMetricsSLACalendar.getMvccVersion());
		Assert.assertEquals(
			existingWorkflowMetricsSLACalendar.getUuid(),
			newWorkflowMetricsSLACalendar.getUuid());
		Assert.assertEquals(
			existingWorkflowMetricsSLACalendar.
				getWorkflowMetricsSLACalendarId(),
			newWorkflowMetricsSLACalendar.getWorkflowMetricsSLACalendarId());
		Assert.assertEquals(
			existingWorkflowMetricsSLACalendar.getGroupId(),
			newWorkflowMetricsSLACalendar.getGroupId());
		Assert.assertEquals(
			existingWorkflowMetricsSLACalendar.getCompanyId(),
			newWorkflowMetricsSLACalendar.getCompanyId());
		Assert.assertEquals(
			existingWorkflowMetricsSLACalendar.getUserId(),
			newWorkflowMetricsSLACalendar.getUserId());
		Assert.assertEquals(
			existingWorkflowMetricsSLACalendar.getUserName(),
			newWorkflowMetricsSLACalendar.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingWorkflowMetricsSLACalendar.getCreateDate()),
			Time.getShortTimestamp(
				newWorkflowMetricsSLACalendar.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingWorkflowMetricsSLACalendar.getModifiedDate()),
			Time.getShortTimestamp(
				newWorkflowMetricsSLACalendar.getModifiedDate()));
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
	public void testFindByPrimaryKeyExisting() throws Exception {
		WorkflowMetricsSLACalendar newWorkflowMetricsSLACalendar =
			addWorkflowMetricsSLACalendar();

		WorkflowMetricsSLACalendar existingWorkflowMetricsSLACalendar =
			_persistence.findByPrimaryKey(
				newWorkflowMetricsSLACalendar.getPrimaryKey());

		Assert.assertEquals(
			existingWorkflowMetricsSLACalendar, newWorkflowMetricsSLACalendar);
	}

	@Test(expected = NoSuchSLACalendarException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<WorkflowMetricsSLACalendar>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"WorkflowMetricsSLACalendar", "mvccVersion", true, "uuid", true,
			"workflowMetricsSLACalendarId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WorkflowMetricsSLACalendar newWorkflowMetricsSLACalendar =
			addWorkflowMetricsSLACalendar();

		WorkflowMetricsSLACalendar existingWorkflowMetricsSLACalendar =
			_persistence.fetchByPrimaryKey(
				newWorkflowMetricsSLACalendar.getPrimaryKey());

		Assert.assertEquals(
			existingWorkflowMetricsSLACalendar, newWorkflowMetricsSLACalendar);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WorkflowMetricsSLACalendar missingWorkflowMetricsSLACalendar =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWorkflowMetricsSLACalendar);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		WorkflowMetricsSLACalendar newWorkflowMetricsSLACalendar1 =
			addWorkflowMetricsSLACalendar();
		WorkflowMetricsSLACalendar newWorkflowMetricsSLACalendar2 =
			addWorkflowMetricsSLACalendar();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWorkflowMetricsSLACalendar1.getPrimaryKey());
		primaryKeys.add(newWorkflowMetricsSLACalendar2.getPrimaryKey());

		Map<Serializable, WorkflowMetricsSLACalendar>
			workflowMetricsSLACalendars = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, workflowMetricsSLACalendars.size());
		Assert.assertEquals(
			newWorkflowMetricsSLACalendar1,
			workflowMetricsSLACalendars.get(
				newWorkflowMetricsSLACalendar1.getPrimaryKey()));
		Assert.assertEquals(
			newWorkflowMetricsSLACalendar2,
			workflowMetricsSLACalendars.get(
				newWorkflowMetricsSLACalendar2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, WorkflowMetricsSLACalendar>
			workflowMetricsSLACalendars = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(workflowMetricsSLACalendars.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		WorkflowMetricsSLACalendar newWorkflowMetricsSLACalendar =
			addWorkflowMetricsSLACalendar();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWorkflowMetricsSLACalendar.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, WorkflowMetricsSLACalendar>
			workflowMetricsSLACalendars = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, workflowMetricsSLACalendars.size());
		Assert.assertEquals(
			newWorkflowMetricsSLACalendar,
			workflowMetricsSLACalendars.get(
				newWorkflowMetricsSLACalendar.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, WorkflowMetricsSLACalendar>
			workflowMetricsSLACalendars = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(workflowMetricsSLACalendars.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		WorkflowMetricsSLACalendar newWorkflowMetricsSLACalendar =
			addWorkflowMetricsSLACalendar();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWorkflowMetricsSLACalendar.getPrimaryKey());

		Map<Serializable, WorkflowMetricsSLACalendar>
			workflowMetricsSLACalendars = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, workflowMetricsSLACalendars.size());
		Assert.assertEquals(
			newWorkflowMetricsSLACalendar,
			workflowMetricsSLACalendars.get(
				newWorkflowMetricsSLACalendar.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			WorkflowMetricsSLACalendarLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<WorkflowMetricsSLACalendar>() {

				@Override
				public void performAction(
					WorkflowMetricsSLACalendar workflowMetricsSLACalendar) {

					Assert.assertNotNull(workflowMetricsSLACalendar);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		WorkflowMetricsSLACalendar newWorkflowMetricsSLACalendar =
			addWorkflowMetricsSLACalendar();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WorkflowMetricsSLACalendar.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"workflowMetricsSLACalendarId",
				newWorkflowMetricsSLACalendar.
					getWorkflowMetricsSLACalendarId()));

		List<WorkflowMetricsSLACalendar> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		WorkflowMetricsSLACalendar existingWorkflowMetricsSLACalendar =
			result.get(0);

		Assert.assertEquals(
			existingWorkflowMetricsSLACalendar, newWorkflowMetricsSLACalendar);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WorkflowMetricsSLACalendar.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"workflowMetricsSLACalendarId", RandomTestUtil.nextLong()));

		List<WorkflowMetricsSLACalendar> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		WorkflowMetricsSLACalendar newWorkflowMetricsSLACalendar =
			addWorkflowMetricsSLACalendar();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WorkflowMetricsSLACalendar.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("workflowMetricsSLACalendarId"));

		Object newWorkflowMetricsSLACalendarId =
			newWorkflowMetricsSLACalendar.getWorkflowMetricsSLACalendarId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"workflowMetricsSLACalendarId",
				new Object[] {newWorkflowMetricsSLACalendarId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingWorkflowMetricsSLACalendarId = result.get(0);

		Assert.assertEquals(
			existingWorkflowMetricsSLACalendarId,
			newWorkflowMetricsSLACalendarId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			WorkflowMetricsSLACalendar.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("workflowMetricsSLACalendarId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"workflowMetricsSLACalendarId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		WorkflowMetricsSLACalendar newWorkflowMetricsSLACalendar =
			addWorkflowMetricsSLACalendar();

		_persistence.clearCache();

		WorkflowMetricsSLACalendar existingWorkflowMetricsSLACalendar =
			_persistence.findByPrimaryKey(
				newWorkflowMetricsSLACalendar.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingWorkflowMetricsSLACalendar.getUuid(),
				ReflectionTestUtil.invoke(
					existingWorkflowMetricsSLACalendar, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingWorkflowMetricsSLACalendar.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingWorkflowMetricsSLACalendar, "getOriginalGroupId",
				new Class<?>[0]));
	}

	protected WorkflowMetricsSLACalendar addWorkflowMetricsSLACalendar()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		WorkflowMetricsSLACalendar workflowMetricsSLACalendar =
			_persistence.create(pk);

		workflowMetricsSLACalendar.setMvccVersion(RandomTestUtil.nextLong());

		workflowMetricsSLACalendar.setUuid(RandomTestUtil.randomString());

		workflowMetricsSLACalendar.setGroupId(RandomTestUtil.nextLong());

		workflowMetricsSLACalendar.setCompanyId(RandomTestUtil.nextLong());

		workflowMetricsSLACalendar.setUserId(RandomTestUtil.nextLong());

		workflowMetricsSLACalendar.setUserName(RandomTestUtil.randomString());

		workflowMetricsSLACalendar.setCreateDate(RandomTestUtil.nextDate());

		workflowMetricsSLACalendar.setModifiedDate(RandomTestUtil.nextDate());

		_workflowMetricsSLACalendars.add(
			_persistence.update(workflowMetricsSLACalendar));

		return workflowMetricsSLACalendar;
	}

	private List<WorkflowMetricsSLACalendar> _workflowMetricsSLACalendars =
		new ArrayList<WorkflowMetricsSLACalendar>();
	private WorkflowMetricsSLACalendarPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}