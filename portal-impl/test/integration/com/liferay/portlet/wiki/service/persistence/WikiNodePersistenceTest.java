/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.PersistenceEnvConfigTestListener;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.impl.WikiNodeModelImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceEnvConfigTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class WikiNodePersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (WikiNodePersistence)PortalBeanLocatorUtil.locate(WikiNodePersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

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
		long pk = ServiceTestUtil.nextLong();

		WikiNode newWikiNode = _persistence.create(pk);

		newWikiNode.setUuid(ServiceTestUtil.randomString());

		newWikiNode.setGroupId(ServiceTestUtil.nextLong());

		newWikiNode.setCompanyId(ServiceTestUtil.nextLong());

		newWikiNode.setUserId(ServiceTestUtil.nextLong());

		newWikiNode.setUserName(ServiceTestUtil.randomString());

		newWikiNode.setCreateDate(ServiceTestUtil.nextDate());

		newWikiNode.setModifiedDate(ServiceTestUtil.nextDate());

		newWikiNode.setName(ServiceTestUtil.randomString());

		newWikiNode.setDescription(ServiceTestUtil.randomString());

		newWikiNode.setLastPostDate(ServiceTestUtil.nextDate());

		_persistence.update(newWikiNode, false);

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
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		WikiNode newWikiNode = addWikiNode();

		WikiNode existingWikiNode = _persistence.findByPrimaryKey(newWikiNode.getPrimaryKey());

		Assert.assertEquals(existingWikiNode, newWikiNode);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchNodeException");
		}
		catch (NoSuchNodeException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WikiNode newWikiNode = addWikiNode();

		WikiNode existingWikiNode = _persistence.fetchByPrimaryKey(newWikiNode.getPrimaryKey());

		Assert.assertEquals(existingWikiNode, newWikiNode);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		WikiNode missingWikiNode = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWikiNode);
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
				ServiceTestUtil.nextLong()));

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
				new Object[] { ServiceTestUtil.nextLong() }));

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
		long pk = ServiceTestUtil.nextLong();

		WikiNode wikiNode = _persistence.create(pk);

		wikiNode.setUuid(ServiceTestUtil.randomString());

		wikiNode.setGroupId(ServiceTestUtil.nextLong());

		wikiNode.setCompanyId(ServiceTestUtil.nextLong());

		wikiNode.setUserId(ServiceTestUtil.nextLong());

		wikiNode.setUserName(ServiceTestUtil.randomString());

		wikiNode.setCreateDate(ServiceTestUtil.nextDate());

		wikiNode.setModifiedDate(ServiceTestUtil.nextDate());

		wikiNode.setName(ServiceTestUtil.randomString());

		wikiNode.setDescription(ServiceTestUtil.randomString());

		wikiNode.setLastPostDate(ServiceTestUtil.nextDate());

		_persistence.update(wikiNode, false);

		return wikiNode;
	}

	private WikiNodePersistence _persistence;
}