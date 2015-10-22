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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.workflow.kaleo.exception.NoSuchNodeException;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoNodePersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoNodeUtil;

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
public class KaleoNodePersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = KaleoNodeUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<KaleoNode> iterator = _kaleoNodes.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoNode kaleoNode = _persistence.create(pk);

		Assert.assertNotNull(kaleoNode);

		Assert.assertEquals(kaleoNode.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		KaleoNode newKaleoNode = addKaleoNode();

		_persistence.remove(newKaleoNode);

		KaleoNode existingKaleoNode = _persistence.fetchByPrimaryKey(newKaleoNode.getPrimaryKey());

		Assert.assertNull(existingKaleoNode);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addKaleoNode();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoNode newKaleoNode = _persistence.create(pk);

		newKaleoNode.setGroupId(RandomTestUtil.nextLong());

		newKaleoNode.setCompanyId(RandomTestUtil.nextLong());

		newKaleoNode.setUserId(RandomTestUtil.nextLong());

		newKaleoNode.setUserName(RandomTestUtil.randomString());

		newKaleoNode.setCreateDate(RandomTestUtil.nextDate());

		newKaleoNode.setModifiedDate(RandomTestUtil.nextDate());

		newKaleoNode.setKaleoDefinitionId(RandomTestUtil.nextLong());

		newKaleoNode.setName(RandomTestUtil.randomString());

		newKaleoNode.setMetadata(RandomTestUtil.randomString());

		newKaleoNode.setDescription(RandomTestUtil.randomString());

		newKaleoNode.setType(RandomTestUtil.randomString());

		newKaleoNode.setInitial(RandomTestUtil.randomBoolean());

		newKaleoNode.setTerminal(RandomTestUtil.randomBoolean());

		_kaleoNodes.add(_persistence.update(newKaleoNode));

		KaleoNode existingKaleoNode = _persistence.findByPrimaryKey(newKaleoNode.getPrimaryKey());

		Assert.assertEquals(existingKaleoNode.getKaleoNodeId(),
			newKaleoNode.getKaleoNodeId());
		Assert.assertEquals(existingKaleoNode.getGroupId(),
			newKaleoNode.getGroupId());
		Assert.assertEquals(existingKaleoNode.getCompanyId(),
			newKaleoNode.getCompanyId());
		Assert.assertEquals(existingKaleoNode.getUserId(),
			newKaleoNode.getUserId());
		Assert.assertEquals(existingKaleoNode.getUserName(),
			newKaleoNode.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoNode.getCreateDate()),
			Time.getShortTimestamp(newKaleoNode.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoNode.getModifiedDate()),
			Time.getShortTimestamp(newKaleoNode.getModifiedDate()));
		Assert.assertEquals(existingKaleoNode.getKaleoDefinitionId(),
			newKaleoNode.getKaleoDefinitionId());
		Assert.assertEquals(existingKaleoNode.getName(), newKaleoNode.getName());
		Assert.assertEquals(existingKaleoNode.getMetadata(),
			newKaleoNode.getMetadata());
		Assert.assertEquals(existingKaleoNode.getDescription(),
			newKaleoNode.getDescription());
		Assert.assertEquals(existingKaleoNode.getType(), newKaleoNode.getType());
		Assert.assertEquals(existingKaleoNode.getInitial(),
			newKaleoNode.getInitial());
		Assert.assertEquals(existingKaleoNode.getTerminal(),
			newKaleoNode.getTerminal());
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
	public void testCountByC_KDI() throws Exception {
		_persistence.countByC_KDI(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_KDI(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		KaleoNode newKaleoNode = addKaleoNode();

		KaleoNode existingKaleoNode = _persistence.findByPrimaryKey(newKaleoNode.getPrimaryKey());

		Assert.assertEquals(existingKaleoNode, newKaleoNode);
	}

	@Test(expected = NoSuchNodeException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<KaleoNode> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("KaleoNode", "kaleoNodeId",
			true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"kaleoDefinitionId", true, "name", true, "metadata", true,
			"description", true, "type", true, "initial", true, "terminal", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		KaleoNode newKaleoNode = addKaleoNode();

		KaleoNode existingKaleoNode = _persistence.fetchByPrimaryKey(newKaleoNode.getPrimaryKey());

		Assert.assertEquals(existingKaleoNode, newKaleoNode);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoNode missingKaleoNode = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingKaleoNode);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		KaleoNode newKaleoNode1 = addKaleoNode();
		KaleoNode newKaleoNode2 = addKaleoNode();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoNode1.getPrimaryKey());
		primaryKeys.add(newKaleoNode2.getPrimaryKey());

		Map<Serializable, KaleoNode> kaleoNodes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, kaleoNodes.size());
		Assert.assertEquals(newKaleoNode1,
			kaleoNodes.get(newKaleoNode1.getPrimaryKey()));
		Assert.assertEquals(newKaleoNode2,
			kaleoNodes.get(newKaleoNode2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, KaleoNode> kaleoNodes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(kaleoNodes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		KaleoNode newKaleoNode = addKaleoNode();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoNode.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, KaleoNode> kaleoNodes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, kaleoNodes.size());
		Assert.assertEquals(newKaleoNode,
			kaleoNodes.get(newKaleoNode.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, KaleoNode> kaleoNodes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(kaleoNodes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		KaleoNode newKaleoNode = addKaleoNode();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoNode.getPrimaryKey());

		Map<Serializable, KaleoNode> kaleoNodes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, kaleoNodes.size());
		Assert.assertEquals(newKaleoNode,
			kaleoNodes.get(newKaleoNode.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = KaleoNodeLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<KaleoNode>() {
				@Override
				public void performAction(KaleoNode kaleoNode) {
					Assert.assertNotNull(kaleoNode);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		KaleoNode newKaleoNode = addKaleoNode();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoNode.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("kaleoNodeId",
				newKaleoNode.getKaleoNodeId()));

		List<KaleoNode> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		KaleoNode existingKaleoNode = result.get(0);

		Assert.assertEquals(existingKaleoNode, newKaleoNode);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoNode.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("kaleoNodeId",
				RandomTestUtil.nextLong()));

		List<KaleoNode> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		KaleoNode newKaleoNode = addKaleoNode();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoNode.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("kaleoNodeId"));

		Object newKaleoNodeId = newKaleoNode.getKaleoNodeId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("kaleoNodeId",
				new Object[] { newKaleoNodeId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingKaleoNodeId = result.get(0);

		Assert.assertEquals(existingKaleoNodeId, newKaleoNodeId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoNode.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("kaleoNodeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("kaleoNodeId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected KaleoNode addKaleoNode() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoNode kaleoNode = _persistence.create(pk);

		kaleoNode.setGroupId(RandomTestUtil.nextLong());

		kaleoNode.setCompanyId(RandomTestUtil.nextLong());

		kaleoNode.setUserId(RandomTestUtil.nextLong());

		kaleoNode.setUserName(RandomTestUtil.randomString());

		kaleoNode.setCreateDate(RandomTestUtil.nextDate());

		kaleoNode.setModifiedDate(RandomTestUtil.nextDate());

		kaleoNode.setKaleoDefinitionId(RandomTestUtil.nextLong());

		kaleoNode.setName(RandomTestUtil.randomString());

		kaleoNode.setMetadata(RandomTestUtil.randomString());

		kaleoNode.setDescription(RandomTestUtil.randomString());

		kaleoNode.setType(RandomTestUtil.randomString());

		kaleoNode.setInitial(RandomTestUtil.randomBoolean());

		kaleoNode.setTerminal(RandomTestUtil.randomBoolean());

		_kaleoNodes.add(_persistence.update(kaleoNode));

		return kaleoNode;
	}

	private List<KaleoNode> _kaleoNodes = new ArrayList<KaleoNode>();
	private KaleoNodePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}