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

package com.liferay.portal.workflow.kaleo.service.persistence.test;

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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskForm;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskFormLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskFormPersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskFormUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class KaleoTaskFormPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.portal.workflow.kaleo.service"));

	@Before
	public void setUp() {
		_persistence = KaleoTaskFormUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<KaleoTaskForm> iterator = _kaleoTaskForms.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoTaskForm kaleoTaskForm = _persistence.create(pk);

		Assert.assertNotNull(kaleoTaskForm);

		Assert.assertEquals(kaleoTaskForm.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		KaleoTaskForm newKaleoTaskForm = addKaleoTaskForm();

		_persistence.remove(newKaleoTaskForm);

		KaleoTaskForm existingKaleoTaskForm = _persistence.fetchByPrimaryKey(newKaleoTaskForm.getPrimaryKey());

		Assert.assertNull(existingKaleoTaskForm);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addKaleoTaskForm();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoTaskForm newKaleoTaskForm = _persistence.create(pk);

		newKaleoTaskForm.setGroupId(RandomTestUtil.nextLong());

		newKaleoTaskForm.setCompanyId(RandomTestUtil.nextLong());

		newKaleoTaskForm.setUserId(RandomTestUtil.nextLong());

		newKaleoTaskForm.setUserName(RandomTestUtil.randomString());

		newKaleoTaskForm.setCreateDate(RandomTestUtil.nextDate());

		newKaleoTaskForm.setModifiedDate(RandomTestUtil.nextDate());

		newKaleoTaskForm.setKaleoDefinitionId(RandomTestUtil.nextLong());

		newKaleoTaskForm.setKaleoNodeId(RandomTestUtil.nextLong());

		newKaleoTaskForm.setKaleoTaskId(RandomTestUtil.nextLong());

		newKaleoTaskForm.setKaleoTaskName(RandomTestUtil.randomString());

		newKaleoTaskForm.setName(RandomTestUtil.randomString());

		newKaleoTaskForm.setDescription(RandomTestUtil.randomString());

		newKaleoTaskForm.setFormCompanyId(RandomTestUtil.nextLong());

		newKaleoTaskForm.setFormDefinition(RandomTestUtil.randomString());

		newKaleoTaskForm.setFormGroupId(RandomTestUtil.nextLong());

		newKaleoTaskForm.setFormId(RandomTestUtil.nextLong());

		newKaleoTaskForm.setFormUuid(RandomTestUtil.randomString());

		newKaleoTaskForm.setMetadata(RandomTestUtil.randomString());

		newKaleoTaskForm.setPriority(RandomTestUtil.nextInt());

		_kaleoTaskForms.add(_persistence.update(newKaleoTaskForm));

		KaleoTaskForm existingKaleoTaskForm = _persistence.findByPrimaryKey(newKaleoTaskForm.getPrimaryKey());

		Assert.assertEquals(existingKaleoTaskForm.getKaleoTaskFormId(),
			newKaleoTaskForm.getKaleoTaskFormId());
		Assert.assertEquals(existingKaleoTaskForm.getGroupId(),
			newKaleoTaskForm.getGroupId());
		Assert.assertEquals(existingKaleoTaskForm.getCompanyId(),
			newKaleoTaskForm.getCompanyId());
		Assert.assertEquals(existingKaleoTaskForm.getUserId(),
			newKaleoTaskForm.getUserId());
		Assert.assertEquals(existingKaleoTaskForm.getUserName(),
			newKaleoTaskForm.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoTaskForm.getCreateDate()),
			Time.getShortTimestamp(newKaleoTaskForm.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoTaskForm.getModifiedDate()),
			Time.getShortTimestamp(newKaleoTaskForm.getModifiedDate()));
		Assert.assertEquals(existingKaleoTaskForm.getKaleoDefinitionId(),
			newKaleoTaskForm.getKaleoDefinitionId());
		Assert.assertEquals(existingKaleoTaskForm.getKaleoNodeId(),
			newKaleoTaskForm.getKaleoNodeId());
		Assert.assertEquals(existingKaleoTaskForm.getKaleoTaskId(),
			newKaleoTaskForm.getKaleoTaskId());
		Assert.assertEquals(existingKaleoTaskForm.getKaleoTaskName(),
			newKaleoTaskForm.getKaleoTaskName());
		Assert.assertEquals(existingKaleoTaskForm.getName(),
			newKaleoTaskForm.getName());
		Assert.assertEquals(existingKaleoTaskForm.getDescription(),
			newKaleoTaskForm.getDescription());
		Assert.assertEquals(existingKaleoTaskForm.getFormCompanyId(),
			newKaleoTaskForm.getFormCompanyId());
		Assert.assertEquals(existingKaleoTaskForm.getFormDefinition(),
			newKaleoTaskForm.getFormDefinition());
		Assert.assertEquals(existingKaleoTaskForm.getFormGroupId(),
			newKaleoTaskForm.getFormGroupId());
		Assert.assertEquals(existingKaleoTaskForm.getFormId(),
			newKaleoTaskForm.getFormId());
		Assert.assertEquals(existingKaleoTaskForm.getFormUuid(),
			newKaleoTaskForm.getFormUuid());
		Assert.assertEquals(existingKaleoTaskForm.getMetadata(),
			newKaleoTaskForm.getMetadata());
		Assert.assertEquals(existingKaleoTaskForm.getPriority(),
			newKaleoTaskForm.getPriority());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByKaleoDefinitionId() throws Exception {
		_persistence.countByKaleoDefinitionId(RandomTestUtil.nextLong());

		_persistence.countByKaleoDefinitionId(0L);
	}

	@Test
	public void testCountByKaleoNodeId() throws Exception {
		_persistence.countByKaleoNodeId(RandomTestUtil.nextLong());

		_persistence.countByKaleoNodeId(0L);
	}

	@Test
	public void testCountByKaleoTaskId() throws Exception {
		_persistence.countByKaleoTaskId(RandomTestUtil.nextLong());

		_persistence.countByKaleoTaskId(0L);
	}

	@Test
	public void testCountByFormUuid_KTI() throws Exception {
		_persistence.countByFormUuid_KTI(RandomTestUtil.nextLong(),
			StringPool.BLANK);

		_persistence.countByFormUuid_KTI(0L, StringPool.NULL);

		_persistence.countByFormUuid_KTI(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		KaleoTaskForm newKaleoTaskForm = addKaleoTaskForm();

		KaleoTaskForm existingKaleoTaskForm = _persistence.findByPrimaryKey(newKaleoTaskForm.getPrimaryKey());

		Assert.assertEquals(existingKaleoTaskForm, newKaleoTaskForm);
	}

	@Test(expected = NoSuchTaskFormException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<KaleoTaskForm> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("KaleoTaskForm",
			"kaleoTaskFormId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "kaleoDefinitionId", true, "kaleoNodeId",
			true, "kaleoTaskId", true, "kaleoTaskName", true, "name", true,
			"description", true, "formCompanyId", true, "formDefinition", true,
			"formGroupId", true, "formId", true, "formUuid", true, "metadata",
			true, "priority", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		KaleoTaskForm newKaleoTaskForm = addKaleoTaskForm();

		KaleoTaskForm existingKaleoTaskForm = _persistence.fetchByPrimaryKey(newKaleoTaskForm.getPrimaryKey());

		Assert.assertEquals(existingKaleoTaskForm, newKaleoTaskForm);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoTaskForm missingKaleoTaskForm = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingKaleoTaskForm);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		KaleoTaskForm newKaleoTaskForm1 = addKaleoTaskForm();
		KaleoTaskForm newKaleoTaskForm2 = addKaleoTaskForm();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoTaskForm1.getPrimaryKey());
		primaryKeys.add(newKaleoTaskForm2.getPrimaryKey());

		Map<Serializable, KaleoTaskForm> kaleoTaskForms = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, kaleoTaskForms.size());
		Assert.assertEquals(newKaleoTaskForm1,
			kaleoTaskForms.get(newKaleoTaskForm1.getPrimaryKey()));
		Assert.assertEquals(newKaleoTaskForm2,
			kaleoTaskForms.get(newKaleoTaskForm2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, KaleoTaskForm> kaleoTaskForms = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(kaleoTaskForms.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		KaleoTaskForm newKaleoTaskForm = addKaleoTaskForm();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoTaskForm.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, KaleoTaskForm> kaleoTaskForms = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, kaleoTaskForms.size());
		Assert.assertEquals(newKaleoTaskForm,
			kaleoTaskForms.get(newKaleoTaskForm.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, KaleoTaskForm> kaleoTaskForms = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(kaleoTaskForms.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		KaleoTaskForm newKaleoTaskForm = addKaleoTaskForm();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoTaskForm.getPrimaryKey());

		Map<Serializable, KaleoTaskForm> kaleoTaskForms = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, kaleoTaskForms.size());
		Assert.assertEquals(newKaleoTaskForm,
			kaleoTaskForms.get(newKaleoTaskForm.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = KaleoTaskFormLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<KaleoTaskForm>() {
				@Override
				public void performAction(KaleoTaskForm kaleoTaskForm) {
					Assert.assertNotNull(kaleoTaskForm);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		KaleoTaskForm newKaleoTaskForm = addKaleoTaskForm();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoTaskForm.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("kaleoTaskFormId",
				newKaleoTaskForm.getKaleoTaskFormId()));

		List<KaleoTaskForm> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		KaleoTaskForm existingKaleoTaskForm = result.get(0);

		Assert.assertEquals(existingKaleoTaskForm, newKaleoTaskForm);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoTaskForm.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("kaleoTaskFormId",
				RandomTestUtil.nextLong()));

		List<KaleoTaskForm> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		KaleoTaskForm newKaleoTaskForm = addKaleoTaskForm();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoTaskForm.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"kaleoTaskFormId"));

		Object newKaleoTaskFormId = newKaleoTaskForm.getKaleoTaskFormId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("kaleoTaskFormId",
				new Object[] { newKaleoTaskFormId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingKaleoTaskFormId = result.get(0);

		Assert.assertEquals(existingKaleoTaskFormId, newKaleoTaskFormId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoTaskForm.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"kaleoTaskFormId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("kaleoTaskFormId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		KaleoTaskForm newKaleoTaskForm = addKaleoTaskForm();

		_persistence.clearCache();

		KaleoTaskForm existingKaleoTaskForm = _persistence.findByPrimaryKey(newKaleoTaskForm.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(existingKaleoTaskForm.getKaleoTaskId()),
			ReflectionTestUtil.<Long>invoke(existingKaleoTaskForm,
				"getOriginalKaleoTaskId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(existingKaleoTaskForm.getFormUuid(),
				ReflectionTestUtil.invoke(existingKaleoTaskForm,
					"getOriginalFormUuid", new Class<?>[0])));
	}

	protected KaleoTaskForm addKaleoTaskForm() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoTaskForm kaleoTaskForm = _persistence.create(pk);

		kaleoTaskForm.setGroupId(RandomTestUtil.nextLong());

		kaleoTaskForm.setCompanyId(RandomTestUtil.nextLong());

		kaleoTaskForm.setUserId(RandomTestUtil.nextLong());

		kaleoTaskForm.setUserName(RandomTestUtil.randomString());

		kaleoTaskForm.setCreateDate(RandomTestUtil.nextDate());

		kaleoTaskForm.setModifiedDate(RandomTestUtil.nextDate());

		kaleoTaskForm.setKaleoDefinitionId(RandomTestUtil.nextLong());

		kaleoTaskForm.setKaleoNodeId(RandomTestUtil.nextLong());

		kaleoTaskForm.setKaleoTaskId(RandomTestUtil.nextLong());

		kaleoTaskForm.setKaleoTaskName(RandomTestUtil.randomString());

		kaleoTaskForm.setName(RandomTestUtil.randomString());

		kaleoTaskForm.setDescription(RandomTestUtil.randomString());

		kaleoTaskForm.setFormCompanyId(RandomTestUtil.nextLong());

		kaleoTaskForm.setFormDefinition(RandomTestUtil.randomString());

		kaleoTaskForm.setFormGroupId(RandomTestUtil.nextLong());

		kaleoTaskForm.setFormId(RandomTestUtil.nextLong());

		kaleoTaskForm.setFormUuid(RandomTestUtil.randomString());

		kaleoTaskForm.setMetadata(RandomTestUtil.randomString());

		kaleoTaskForm.setPriority(RandomTestUtil.nextInt());

		_kaleoTaskForms.add(_persistence.update(kaleoTaskForm));

		return kaleoTaskForm;
	}

	private List<KaleoTaskForm> _kaleoTaskForms = new ArrayList<KaleoTaskForm>();
	private KaleoTaskFormPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}