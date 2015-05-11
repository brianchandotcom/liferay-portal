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

package com.liferay.wiki.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.wiki.exception.NoSuchNodeException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.persistence.WikiNodePersistence;
import com.liferay.wiki.service.persistence.WikiNodeUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class WikiNodePersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = WikiNodeUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<WikiNode> iterator = _wikiNodes.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WikiNode wikiNode = _persistence.create(pk);

		Assert.assertNotNull(wikiNode);

		Assert.assertEquals(wikiNode.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WikiNode newWikiNode = addWikiNode();

		_persistence.remove(newWikiNode);

		WikiNode existingWikiNode = _persistence.fetchByPrimaryKey(newWikiNode.getPrimaryKey());

		Assert.assertNull(existingWikiNode);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWikiNode();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WikiNode newWikiNode = _persistence.create(pk);

		newWikiNode.setUuid(RandomTestUtil.randomString());

		newWikiNode.setGroupId(RandomTestUtil.nextLong());

		newWikiNode.setCompanyId(RandomTestUtil.nextLong());

		newWikiNode.setUserId(RandomTestUtil.nextLong());

		newWikiNode.setUserName(RandomTestUtil.randomString());

		newWikiNode.setCreateDate(RandomTestUtil.nextDate());

		newWikiNode.setModifiedDate(RandomTestUtil.nextDate());

		newWikiNode.setName(RandomTestUtil.randomString());

		newWikiNode.setDescription(RandomTestUtil.randomString());

		newWikiNode.setLastPostDate(RandomTestUtil.nextDate());

		newWikiNode.setStatus(RandomTestUtil.nextInt());

		newWikiNode.setStatusByUserId(RandomTestUtil.nextLong());

		newWikiNode.setStatusByUserName(RandomTestUtil.randomString());

		newWikiNode.setStatusDate(RandomTestUtil.nextDate());

		_wikiNodes.add(_persistence.update(newWikiNode));

		WikiNode existingWikiNode = _persistence.findByPrimaryKey(newWikiNode.getPrimaryKey());

		Assert.assertEquals(existingWikiNode.getUuid(), newWikiNode.getUuid());
		Assert.assertEquals(existingWikiNode.getNodeId(),
			newWikiNode.getNodeId());
		Assert.assertEquals(existingWikiNode.getGroupId(),
			newWikiNode.getGroupId());
		Assert.assertEquals(existingWikiNode.getCompanyId(),
			newWikiNode.getCompanyId());
		Assert.assertEquals(existingWikiNode.getUserId(),
			newWikiNode.getUserId());
		Assert.assertEquals(existingWikiNode.getUserName(),
			newWikiNode.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWikiNode.getCreateDate()),
			Time.getShortTimestamp(newWikiNode.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingWikiNode.getModifiedDate()),
			Time.getShortTimestamp(newWikiNode.getModifiedDate()));
		Assert.assertEquals(existingWikiNode.getName(), newWikiNode.getName());
		Assert.assertEquals(existingWikiNode.getDescription(),
			newWikiNode.getDescription());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWikiNode.getLastPostDate()),
			Time.getShortTimestamp(newWikiNode.getLastPostDate()));
		Assert.assertEquals(existingWikiNode.getStatus(),
			newWikiNode.getStatus());
		Assert.assertEquals(existingWikiNode.getStatusByUserId(),
			newWikiNode.getStatusByUserId());
		Assert.assertEquals(existingWikiNode.getStatusByUserName(),
			newWikiNode.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWikiNode.getStatusDate()),
			Time.getShortTimestamp(newWikiNode.getStatusDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUUID_G(StringPool.NULL, 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByG_N() throws Exception {
		_persistence.countByG_N(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByG_N(0L, StringPool.NULL);

		_persistence.countByG_N(0L, (String)null);
	}

	@Test
	public void testCountByG_S() throws Exception {
		_persistence.countByG_S(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByG_S(0L, 0);
	}

	@Test
	public void testCountByC_S() throws Exception {
		_persistence.countByC_S(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByC_S(0L, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		WikiNode newWikiNode = addWikiNode();

		WikiNode existingWikiNode = _persistence.findByPrimaryKey(newWikiNode.getPrimaryKey());

		Assert.assertEquals(existingWikiNode, newWikiNode);
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

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<WikiNode> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("WikiNode", "uuid", true,
			"nodeId", true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true, "name",
			true, "description", true, "lastPostDate", true, "status", true,
			"statusByUserId", true, "statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WikiNode newWikiNode = addWikiNode();

		WikiNode existingWikiNode = _persistence.fetchByPrimaryKey(newWikiNode.getPrimaryKey());

		Assert.assertEquals(existingWikiNode, newWikiNode);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WikiNode missingWikiNode = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWikiNode);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		WikiNode newWikiNode1 = addWikiNode();
		WikiNode newWikiNode2 = addWikiNode();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWikiNode1.getPrimaryKey());
		primaryKeys.add(newWikiNode2.getPrimaryKey());

		Map<Serializable, WikiNode> wikiNodes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, wikiNodes.size());
		Assert.assertEquals(newWikiNode1,
			wikiNodes.get(newWikiNode1.getPrimaryKey()));
		Assert.assertEquals(newWikiNode2,
			wikiNodes.get(newWikiNode2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, WikiNode> wikiNodes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(wikiNodes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		WikiNode newWikiNode = addWikiNode();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWikiNode.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, WikiNode> wikiNodes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, wikiNodes.size());
		Assert.assertEquals(newWikiNode,
			wikiNodes.get(newWikiNode.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, WikiNode> wikiNodes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(wikiNodes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		WikiNode newWikiNode = addWikiNode();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWikiNode.getPrimaryKey());

		Map<Serializable, WikiNode> wikiNodes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, wikiNodes.size());
		Assert.assertEquals(newWikiNode,
			wikiNodes.get(newWikiNode.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = WikiNodeLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					WikiNode wikiNode = (WikiNode)object;

					Assert.assertNotNull(wikiNode);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WikiNode newWikiNode = addWikiNode();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiNode.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("nodeId",
				newWikiNode.getNodeId()));

		List<WikiNode> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		WikiNode existingWikiNode = result.get(0);

		Assert.assertEquals(existingWikiNode, newWikiNode);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiNode.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("nodeId",
				RandomTestUtil.nextLong()));

		List<WikiNode> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		WikiNode newWikiNode = addWikiNode();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiNode.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("nodeId"));

		Object newNodeId = newWikiNode.getNodeId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("nodeId",
				new Object[] { newNodeId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingNodeId = result.get(0);

		Assert.assertEquals(existingNodeId, newNodeId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiNode.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("nodeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("nodeId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		WikiNode newWikiNode = addWikiNode();

		_persistence.clearCache();

		WikiNode existingWikiNode = _persistence.findByPrimaryKey(newWikiNode.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingWikiNode.getUuid(),
				ReflectionTestUtil.invoke(existingWikiNode, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(existingWikiNode.getGroupId(),
			ReflectionTestUtil.invoke(existingWikiNode, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(existingWikiNode.getGroupId(),
			ReflectionTestUtil.invoke(existingWikiNode, "getOriginalGroupId",
				new Class<?>[0]));
		Assert.assertTrue(Validator.equals(existingWikiNode.getName(),
				ReflectionTestUtil.invoke(existingWikiNode, "getOriginalName",
					new Class<?>[0])));
	}

	protected WikiNode addWikiNode() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WikiNode wikiNode = _persistence.create(pk);

		wikiNode.setUuid(RandomTestUtil.randomString());

		wikiNode.setGroupId(RandomTestUtil.nextLong());

		wikiNode.setCompanyId(RandomTestUtil.nextLong());

		wikiNode.setUserId(RandomTestUtil.nextLong());

		wikiNode.setUserName(RandomTestUtil.randomString());

		wikiNode.setCreateDate(RandomTestUtil.nextDate());

		wikiNode.setModifiedDate(RandomTestUtil.nextDate());

		wikiNode.setName(RandomTestUtil.randomString());

		wikiNode.setDescription(RandomTestUtil.randomString());

		wikiNode.setLastPostDate(RandomTestUtil.nextDate());

		wikiNode.setStatus(RandomTestUtil.nextInt());

		wikiNode.setStatusByUserId(RandomTestUtil.nextLong());

		wikiNode.setStatusByUserName(RandomTestUtil.randomString());

		wikiNode.setStatusDate(RandomTestUtil.nextDate());

		_wikiNodes.add(_persistence.update(wikiNode));

		return wikiNode;
	}

	private List<WikiNode> _wikiNodes = new ArrayList<WikiNode>();
	private WikiNodePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}