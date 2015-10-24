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
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.workflow.kaleo.exception.NoSuchInstanceException;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoInstancePersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoInstanceUtil;

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
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class KaleoInstancePersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = KaleoInstanceUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<KaleoInstance> iterator = _kaleoInstances.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoInstance kaleoInstance = _persistence.create(pk);

		Assert.assertNotNull(kaleoInstance);

		Assert.assertEquals(kaleoInstance.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		KaleoInstance newKaleoInstance = addKaleoInstance();

		_persistence.remove(newKaleoInstance);

		KaleoInstance existingKaleoInstance = _persistence.fetchByPrimaryKey(newKaleoInstance.getPrimaryKey());

		Assert.assertNull(existingKaleoInstance);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addKaleoInstance();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoInstance newKaleoInstance = _persistence.create(pk);

		newKaleoInstance.setGroupId(RandomTestUtil.nextLong());

		newKaleoInstance.setCompanyId(RandomTestUtil.nextLong());

		newKaleoInstance.setUserId(RandomTestUtil.nextLong());

		newKaleoInstance.setUserName(RandomTestUtil.randomString());

		newKaleoInstance.setCreateDate(RandomTestUtil.nextDate());

		newKaleoInstance.setModifiedDate(RandomTestUtil.nextDate());

		newKaleoInstance.setKaleoDefinitionId(RandomTestUtil.nextLong());

		newKaleoInstance.setKaleoDefinitionName(RandomTestUtil.randomString());

		newKaleoInstance.setKaleoDefinitionVersion(RandomTestUtil.nextInt());

		newKaleoInstance.setRootKaleoInstanceTokenId(RandomTestUtil.nextLong());

		newKaleoInstance.setClassName(RandomTestUtil.randomString());

		newKaleoInstance.setClassPK(RandomTestUtil.nextLong());

		newKaleoInstance.setCompleted(RandomTestUtil.randomBoolean());

		newKaleoInstance.setCompletionDate(RandomTestUtil.nextDate());

		newKaleoInstance.setWorkflowContext(RandomTestUtil.randomString());

		_kaleoInstances.add(_persistence.update(newKaleoInstance));

		KaleoInstance existingKaleoInstance = _persistence.findByPrimaryKey(newKaleoInstance.getPrimaryKey());

		Assert.assertEquals(existingKaleoInstance.getKaleoInstanceId(),
			newKaleoInstance.getKaleoInstanceId());
		Assert.assertEquals(existingKaleoInstance.getGroupId(),
			newKaleoInstance.getGroupId());
		Assert.assertEquals(existingKaleoInstance.getCompanyId(),
			newKaleoInstance.getCompanyId());
		Assert.assertEquals(existingKaleoInstance.getUserId(),
			newKaleoInstance.getUserId());
		Assert.assertEquals(existingKaleoInstance.getUserName(),
			newKaleoInstance.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoInstance.getCreateDate()),
			Time.getShortTimestamp(newKaleoInstance.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoInstance.getModifiedDate()),
			Time.getShortTimestamp(newKaleoInstance.getModifiedDate()));
		Assert.assertEquals(existingKaleoInstance.getKaleoDefinitionId(),
			newKaleoInstance.getKaleoDefinitionId());
		Assert.assertEquals(existingKaleoInstance.getKaleoDefinitionName(),
			newKaleoInstance.getKaleoDefinitionName());
		Assert.assertEquals(existingKaleoInstance.getKaleoDefinitionVersion(),
			newKaleoInstance.getKaleoDefinitionVersion());
		Assert.assertEquals(existingKaleoInstance.getRootKaleoInstanceTokenId(),
			newKaleoInstance.getRootKaleoInstanceTokenId());
		Assert.assertEquals(existingKaleoInstance.getClassName(),
			newKaleoInstance.getClassName());
		Assert.assertEquals(existingKaleoInstance.getClassPK(),
			newKaleoInstance.getClassPK());
		Assert.assertEquals(existingKaleoInstance.getCompleted(),
			newKaleoInstance.getCompleted());
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoInstance.getCompletionDate()),
			Time.getShortTimestamp(newKaleoInstance.getCompletionDate()));
		Assert.assertEquals(existingKaleoInstance.getWorkflowContext(),
			newKaleoInstance.getWorkflowContext());
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
	public void testCountByC_U() throws Exception {
		_persistence.countByC_U(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_U(0L, 0L);
	}

	@Test
	public void testCountByKDI_C() throws Exception {
		_persistence.countByKDI_C(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByKDI_C(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByCN_CPK() throws Exception {
		_persistence.countByCN_CPK(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByCN_CPK(StringPool.NULL, 0L);

		_persistence.countByCN_CPK((String)null, 0L);
	}

	@Test
	public void testCountByC_KDN_KDV_CD() throws Exception {
		_persistence.countByC_KDN_KDV_CD(RandomTestUtil.nextLong(),
			StringPool.BLANK, RandomTestUtil.nextInt(),
			RandomTestUtil.nextDate());

		_persistence.countByC_KDN_KDV_CD(0L, StringPool.NULL, 0,
			RandomTestUtil.nextDate());

		_persistence.countByC_KDN_KDV_CD(0L, (String)null, 0,
			RandomTestUtil.nextDate());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		KaleoInstance newKaleoInstance = addKaleoInstance();

		KaleoInstance existingKaleoInstance = _persistence.findByPrimaryKey(newKaleoInstance.getPrimaryKey());

		Assert.assertEquals(existingKaleoInstance, newKaleoInstance);
	}

	@Test(expected = NoSuchInstanceException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<KaleoInstance> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("KaleoInstance",
			"kaleoInstanceId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "kaleoDefinitionId", true,
			"kaleoDefinitionName", true, "kaleoDefinitionVersion", true,
			"rootKaleoInstanceTokenId", true, "className", true, "classPK",
			true, "completed", true, "completionDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		KaleoInstance newKaleoInstance = addKaleoInstance();

		KaleoInstance existingKaleoInstance = _persistence.fetchByPrimaryKey(newKaleoInstance.getPrimaryKey());

		Assert.assertEquals(existingKaleoInstance, newKaleoInstance);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoInstance missingKaleoInstance = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingKaleoInstance);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		KaleoInstance newKaleoInstance1 = addKaleoInstance();
		KaleoInstance newKaleoInstance2 = addKaleoInstance();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoInstance1.getPrimaryKey());
		primaryKeys.add(newKaleoInstance2.getPrimaryKey());

		Map<Serializable, KaleoInstance> kaleoInstances = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, kaleoInstances.size());
		Assert.assertEquals(newKaleoInstance1,
			kaleoInstances.get(newKaleoInstance1.getPrimaryKey()));
		Assert.assertEquals(newKaleoInstance2,
			kaleoInstances.get(newKaleoInstance2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, KaleoInstance> kaleoInstances = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(kaleoInstances.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		KaleoInstance newKaleoInstance = addKaleoInstance();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoInstance.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, KaleoInstance> kaleoInstances = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, kaleoInstances.size());
		Assert.assertEquals(newKaleoInstance,
			kaleoInstances.get(newKaleoInstance.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, KaleoInstance> kaleoInstances = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(kaleoInstances.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		KaleoInstance newKaleoInstance = addKaleoInstance();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoInstance.getPrimaryKey());

		Map<Serializable, KaleoInstance> kaleoInstances = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, kaleoInstances.size());
		Assert.assertEquals(newKaleoInstance,
			kaleoInstances.get(newKaleoInstance.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = KaleoInstanceLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<KaleoInstance>() {
				@Override
				public void performAction(KaleoInstance kaleoInstance) {
					Assert.assertNotNull(kaleoInstance);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		KaleoInstance newKaleoInstance = addKaleoInstance();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoInstance.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("kaleoInstanceId",
				newKaleoInstance.getKaleoInstanceId()));

		List<KaleoInstance> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		KaleoInstance existingKaleoInstance = result.get(0);

		Assert.assertEquals(existingKaleoInstance, newKaleoInstance);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoInstance.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("kaleoInstanceId",
				RandomTestUtil.nextLong()));

		List<KaleoInstance> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		KaleoInstance newKaleoInstance = addKaleoInstance();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoInstance.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"kaleoInstanceId"));

		Object newKaleoInstanceId = newKaleoInstance.getKaleoInstanceId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("kaleoInstanceId",
				new Object[] { newKaleoInstanceId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingKaleoInstanceId = result.get(0);

		Assert.assertEquals(existingKaleoInstanceId, newKaleoInstanceId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoInstance.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"kaleoInstanceId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("kaleoInstanceId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected KaleoInstance addKaleoInstance() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoInstance kaleoInstance = _persistence.create(pk);

		kaleoInstance.setGroupId(RandomTestUtil.nextLong());

		kaleoInstance.setCompanyId(RandomTestUtil.nextLong());

		kaleoInstance.setUserId(RandomTestUtil.nextLong());

		kaleoInstance.setUserName(RandomTestUtil.randomString());

		kaleoInstance.setCreateDate(RandomTestUtil.nextDate());

		kaleoInstance.setModifiedDate(RandomTestUtil.nextDate());

		kaleoInstance.setKaleoDefinitionId(RandomTestUtil.nextLong());

		kaleoInstance.setKaleoDefinitionName(RandomTestUtil.randomString());

		kaleoInstance.setKaleoDefinitionVersion(RandomTestUtil.nextInt());

		kaleoInstance.setRootKaleoInstanceTokenId(RandomTestUtil.nextLong());

		kaleoInstance.setClassName(RandomTestUtil.randomString());

		kaleoInstance.setClassPK(RandomTestUtil.nextLong());

		kaleoInstance.setCompleted(RandomTestUtil.randomBoolean());

		kaleoInstance.setCompletionDate(RandomTestUtil.nextDate());

		kaleoInstance.setWorkflowContext(RandomTestUtil.randomString());

		_kaleoInstances.add(_persistence.update(kaleoInstance));

		return kaleoInstance;
	}

	private List<KaleoInstance> _kaleoInstances = new ArrayList<KaleoInstance>();
	private KaleoInstancePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}