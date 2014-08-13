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

package com.liferay.portlet.wiki.service.persistence;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.test.runners.PersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.impl.WikiNodeModelImpl;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
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
@RunWith(PersistenceIntegrationJUnitTestRunner.class)
public class WikiNodePersistenceTest {
	@ClassRule
	public static TransactionalTestRule transactionalTestRule = new TransactionalTestRule(Propagation.REQUIRED);

	@BeforeClass
	public static void setupClass() throws TemplateException {
		try {
			DBUpgrader.upgrade();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		TemplateManagerUtil.init();
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
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUUID_G() {
		try {
			_persistence.countByUUID_G(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUUID_G(StringPool.NULL, 0L);

			_persistence.countByUUID_G((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(RandomTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(RandomTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_N() {
		try {
			_persistence.countByG_N(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_N(0L, StringPool.NULL);

			_persistence.countByG_N(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_S() {
		try {
			_persistence.countByG_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByG_S(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_S() {
		try {
			_persistence.countByC_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByC_S(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		WikiNode newWikiNode = addWikiNode();

		WikiNode existingWikiNode = _persistence.findByPrimaryKey(newWikiNode.getPrimaryKey());

		Assert.assertEquals(existingWikiNode, newWikiNode);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchNodeException");
		}
		catch (NoSuchNodeException nsee) {
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

	@Test
	public void testFilterFindByGroupId() throws Exception {
		try {
			_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
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
				WikiNode.class.getClassLoader());

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
				WikiNode.class.getClassLoader());

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
				WikiNode.class.getClassLoader());

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
				WikiNode.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("nodeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("nodeId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		WikiNode newWikiNode = addWikiNode();

		_persistence.clearCache();

		WikiNodeModelImpl existingWikiNodeModelImpl = (WikiNodeModelImpl)_persistence.findByPrimaryKey(newWikiNode.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingWikiNodeModelImpl.getUuid(),
				existingWikiNodeModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingWikiNodeModelImpl.getGroupId(),
			existingWikiNodeModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingWikiNodeModelImpl.getGroupId(),
			existingWikiNodeModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingWikiNodeModelImpl.getName(),
				existingWikiNodeModelImpl.getOriginalName()));
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

	private static Log _log = LogFactoryUtil.getLog(WikiNodePersistenceTest.class);
	private List<WikiNode> _wikiNodes = new ArrayList<WikiNode>();
	private WikiNodePersistence _persistence = WikiNodeUtil.getPersistence();
}