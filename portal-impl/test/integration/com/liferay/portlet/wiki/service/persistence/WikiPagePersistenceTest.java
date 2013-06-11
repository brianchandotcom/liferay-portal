/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.impl.WikiPageModelImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class WikiPagePersistenceTest {
	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		WikiPage wikiPage = _persistence.create(pk);

		Assert.assertNotNull(wikiPage);

		Assert.assertEquals(wikiPage.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WikiPage newWikiPage = addWikiPage();

		_persistence.remove(newWikiPage);

		WikiPage existingWikiPage = _persistence.fetchByPrimaryKey(newWikiPage.getPrimaryKey());

		Assert.assertNull(existingWikiPage);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWikiPage();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		WikiPage newWikiPage = _persistence.create(pk);

		newWikiPage.setUuid(ServiceTestUtil.randomString());

		newWikiPage.setResourcePrimKey(ServiceTestUtil.nextLong());

		newWikiPage.setGroupId(ServiceTestUtil.nextLong());

		newWikiPage.setCompanyId(ServiceTestUtil.nextLong());

		newWikiPage.setUserId(ServiceTestUtil.nextLong());

		newWikiPage.setUserName(ServiceTestUtil.randomString());

		newWikiPage.setCreateDate(ServiceTestUtil.nextDate());

		newWikiPage.setModifiedDate(ServiceTestUtil.nextDate());

		newWikiPage.setNodeId(ServiceTestUtil.nextLong());

		newWikiPage.setTitle(ServiceTestUtil.randomString());

		newWikiPage.setVersion(ServiceTestUtil.nextDouble());

		newWikiPage.setMinorEdit(ServiceTestUtil.randomBoolean());

		newWikiPage.setContent(ServiceTestUtil.randomString());

		newWikiPage.setSummary(ServiceTestUtil.randomString());

		newWikiPage.setFormat(ServiceTestUtil.randomString());

		newWikiPage.setHead(ServiceTestUtil.randomBoolean());

		newWikiPage.setParentTitle(ServiceTestUtil.randomString());

		newWikiPage.setRedirectTitle(ServiceTestUtil.randomString());

		newWikiPage.setStatus(ServiceTestUtil.nextInt());

		newWikiPage.setStatusByUserId(ServiceTestUtil.nextLong());

		newWikiPage.setStatusByUserName(ServiceTestUtil.randomString());

		newWikiPage.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(newWikiPage);

		WikiPage existingWikiPage = _persistence.findByPrimaryKey(newWikiPage.getPrimaryKey());

		Assert.assertEquals(existingWikiPage.getUuid(), newWikiPage.getUuid());
		Assert.assertEquals(existingWikiPage.getPageId(),
			newWikiPage.getPageId());
		Assert.assertEquals(existingWikiPage.getResourcePrimKey(),
			newWikiPage.getResourcePrimKey());
		Assert.assertEquals(existingWikiPage.getGroupId(),
			newWikiPage.getGroupId());
		Assert.assertEquals(existingWikiPage.getCompanyId(),
			newWikiPage.getCompanyId());
		Assert.assertEquals(existingWikiPage.getUserId(),
			newWikiPage.getUserId());
		Assert.assertEquals(existingWikiPage.getUserName(),
			newWikiPage.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWikiPage.getCreateDate()),
			Time.getShortTimestamp(newWikiPage.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingWikiPage.getModifiedDate()),
			Time.getShortTimestamp(newWikiPage.getModifiedDate()));
		Assert.assertEquals(existingWikiPage.getNodeId(),
			newWikiPage.getNodeId());
		Assert.assertEquals(existingWikiPage.getTitle(), newWikiPage.getTitle());
		AssertUtils.assertEquals(
			existingWikiPage.getVersion(),
			newWikiPage.getVersion());
		Assert.assertEquals(existingWikiPage.getMinorEdit(),
			newWikiPage.getMinorEdit());
		Assert.assertEquals(existingWikiPage.getContent(),
			newWikiPage.getContent());
		Assert.assertEquals(existingWikiPage.getSummary(),
			newWikiPage.getSummary());
		Assert.assertEquals(existingWikiPage.getFormat(),
			newWikiPage.getFormat());
		Assert.assertEquals(existingWikiPage.getHead(), newWikiPage.getHead());
		Assert.assertEquals(existingWikiPage.getParentTitle(),
			newWikiPage.getParentTitle());
		Assert.assertEquals(existingWikiPage.getRedirectTitle(),
			newWikiPage.getRedirectTitle());
		Assert.assertEquals(existingWikiPage.getStatus(),
			newWikiPage.getStatus());
		Assert.assertEquals(existingWikiPage.getStatusByUserId(),
			newWikiPage.getStatusByUserId());
		Assert.assertEquals(existingWikiPage.getStatusByUserName(),
			newWikiPage.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWikiPage.getStatusDate()),
			Time.getShortTimestamp(newWikiPage.getStatusDate()));
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		WikiPage newWikiPage = addWikiPage();

		WikiPage existingWikiPage = _persistence.findByPrimaryKey(newWikiPage.getPrimaryKey());

		Assert.assertEquals(existingWikiPage, newWikiPage);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchPageException");
		}
		catch (NoSuchPageException nsee) {
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

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("WikiPage", "uuid", true,
			"pageId", true, "resourcePrimKey", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "nodeId", true, "title", true,
			"version", true, "minorEdit", true, "content", true, "summary",
			true, "format", true, "head", true, "parentTitle", true,
			"redirectTitle", true, "status", true, "statusByUserId", true,
			"statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WikiPage newWikiPage = addWikiPage();

		WikiPage existingWikiPage = _persistence.fetchByPrimaryKey(newWikiPage.getPrimaryKey());

		Assert.assertEquals(existingWikiPage, newWikiPage);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		WikiPage missingWikiPage = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWikiPage);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new WikiPageActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					WikiPage wikiPage = (WikiPage)object;

					Assert.assertNotNull(wikiPage);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WikiPage newWikiPage = addWikiPage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPage.class,
				WikiPage.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("pageId",
				newWikiPage.getPageId()));

		List<WikiPage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		WikiPage existingWikiPage = result.get(0);

		Assert.assertEquals(existingWikiPage, newWikiPage);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPage.class,
				WikiPage.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("pageId",
				ServiceTestUtil.nextLong()));

		List<WikiPage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		WikiPage newWikiPage = addWikiPage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPage.class,
				WikiPage.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("pageId"));

		Object newPageId = newWikiPage.getPageId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("pageId",
				new Object[] { newPageId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPageId = result.get(0);

		Assert.assertEquals(existingPageId, newPageId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPage.class,
				WikiPage.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("pageId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("pageId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		WikiPage newWikiPage = addWikiPage();

		_persistence.clearCache();

		WikiPageModelImpl existingWikiPageModelImpl = (WikiPageModelImpl)_persistence.findByPrimaryKey(newWikiPage.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingWikiPageModelImpl.getUuid(),
				existingWikiPageModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingWikiPageModelImpl.getGroupId(),
			existingWikiPageModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingWikiPageModelImpl.getResourcePrimKey(),
			existingWikiPageModelImpl.getOriginalResourcePrimKey());
		Assert.assertEquals(existingWikiPageModelImpl.getNodeId(),
			existingWikiPageModelImpl.getOriginalNodeId());
		AssertUtils.assertEquals(existingWikiPageModelImpl.getVersion(),
			existingWikiPageModelImpl.getOriginalVersion());

		Assert.assertEquals(existingWikiPageModelImpl.getNodeId(),
			existingWikiPageModelImpl.getOriginalNodeId());
		Assert.assertTrue(Validator.equals(
				existingWikiPageModelImpl.getTitle(),
				existingWikiPageModelImpl.getOriginalTitle()));
		AssertUtils.assertEquals(existingWikiPageModelImpl.getVersion(),
			existingWikiPageModelImpl.getOriginalVersion());
	}

	protected WikiPage addWikiPage() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		WikiPage wikiPage = _persistence.create(pk);

		wikiPage.setUuid(ServiceTestUtil.randomString());

		wikiPage.setResourcePrimKey(ServiceTestUtil.nextLong());

		wikiPage.setGroupId(ServiceTestUtil.nextLong());

		wikiPage.setCompanyId(ServiceTestUtil.nextLong());

		wikiPage.setUserId(ServiceTestUtil.nextLong());

		wikiPage.setUserName(ServiceTestUtil.randomString());

		wikiPage.setCreateDate(ServiceTestUtil.nextDate());

		wikiPage.setModifiedDate(ServiceTestUtil.nextDate());

		wikiPage.setNodeId(ServiceTestUtil.nextLong());

		wikiPage.setTitle(ServiceTestUtil.randomString());

		wikiPage.setVersion(ServiceTestUtil.nextDouble());

		wikiPage.setMinorEdit(ServiceTestUtil.randomBoolean());

		wikiPage.setContent(ServiceTestUtil.randomString());

		wikiPage.setSummary(ServiceTestUtil.randomString());

		wikiPage.setFormat(ServiceTestUtil.randomString());

		wikiPage.setHead(ServiceTestUtil.randomBoolean());

		wikiPage.setParentTitle(ServiceTestUtil.randomString());

		wikiPage.setRedirectTitle(ServiceTestUtil.randomString());

		wikiPage.setStatus(ServiceTestUtil.nextInt());

		wikiPage.setStatusByUserId(ServiceTestUtil.nextLong());

		wikiPage.setStatusByUserName(ServiceTestUtil.randomString());

		wikiPage.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(wikiPage);

		return wikiPage;
	}

	private static Log _log = LogFactoryUtil.getLog(WikiPagePersistenceTest.class);
	private WikiPagePersistence _persistence = (WikiPagePersistence)PortalBeanLocatorUtil.locate(WikiPagePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}