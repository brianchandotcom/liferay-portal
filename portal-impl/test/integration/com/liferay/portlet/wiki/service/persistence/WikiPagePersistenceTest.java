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
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.impl.WikiPageModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class WikiPagePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (WikiPagePersistence)PortalBeanLocatorUtil.locate(WikiPagePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		WikiPage wikiPage = _persistence.create(pk);

		assertNotNull(wikiPage);

		assertEquals(wikiPage.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		WikiPage newWikiPage = addWikiPage();

		_persistence.remove(newWikiPage);

		WikiPage existingWikiPage = _persistence.fetchByPrimaryKey(newWikiPage.getPrimaryKey());

		assertNull(existingWikiPage);
	}

	public void testUpdateNew() throws Exception {
		addWikiPage();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		WikiPage newWikiPage = _persistence.create(pk);

		newWikiPage.setUuid(randomString());

		newWikiPage.setResourcePrimKey(nextLong());

		newWikiPage.setGroupId(nextLong());

		newWikiPage.setCompanyId(nextLong());

		newWikiPage.setUserId(nextLong());

		newWikiPage.setUserName(randomString());

		newWikiPage.setCreateDate(nextDate());

		newWikiPage.setModifiedDate(nextDate());

		newWikiPage.setNodeId(nextLong());

		newWikiPage.setTitle(randomString());

		newWikiPage.setVersion(nextDouble());

		newWikiPage.setMinorEdit(randomBoolean());

		newWikiPage.setContent(randomString());

		newWikiPage.setSummary(randomString());

		newWikiPage.setFormat(randomString());

		newWikiPage.setHead(randomBoolean());

		newWikiPage.setParentTitle(randomString());

		newWikiPage.setRedirectTitle(randomString());

		newWikiPage.setStatus(nextInt());

		newWikiPage.setStatusByUserId(nextLong());

		newWikiPage.setStatusByUserName(randomString());

		newWikiPage.setStatusDate(nextDate());

		_persistence.update(newWikiPage, false);

		WikiPage existingWikiPage = _persistence.findByPrimaryKey(newWikiPage.getPrimaryKey());

		assertEquals(existingWikiPage.getUuid(), newWikiPage.getUuid());
		assertEquals(existingWikiPage.getPageId(), newWikiPage.getPageId());
		assertEquals(existingWikiPage.getResourcePrimKey(),
			newWikiPage.getResourcePrimKey());
		assertEquals(existingWikiPage.getGroupId(), newWikiPage.getGroupId());
		assertEquals(existingWikiPage.getCompanyId(), newWikiPage.getCompanyId());
		assertEquals(existingWikiPage.getUserId(), newWikiPage.getUserId());
		assertEquals(existingWikiPage.getUserName(), newWikiPage.getUserName());
		assertEquals(Time.getShortTimestamp(existingWikiPage.getCreateDate()),
			Time.getShortTimestamp(newWikiPage.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingWikiPage.getModifiedDate()),
			Time.getShortTimestamp(newWikiPage.getModifiedDate()));
		assertEquals(existingWikiPage.getNodeId(), newWikiPage.getNodeId());
		assertEquals(existingWikiPage.getTitle(), newWikiPage.getTitle());
		assertEquals(existingWikiPage.getVersion(), newWikiPage.getVersion());
		assertEquals(existingWikiPage.getMinorEdit(), newWikiPage.getMinorEdit());
		assertEquals(existingWikiPage.getContent(), newWikiPage.getContent());
		assertEquals(existingWikiPage.getSummary(), newWikiPage.getSummary());
		assertEquals(existingWikiPage.getFormat(), newWikiPage.getFormat());
		assertEquals(existingWikiPage.getHead(), newWikiPage.getHead());
		assertEquals(existingWikiPage.getParentTitle(),
			newWikiPage.getParentTitle());
		assertEquals(existingWikiPage.getRedirectTitle(),
			newWikiPage.getRedirectTitle());
		assertEquals(existingWikiPage.getStatus(), newWikiPage.getStatus());
		assertEquals(existingWikiPage.getStatusByUserId(),
			newWikiPage.getStatusByUserId());
		assertEquals(existingWikiPage.getStatusByUserName(),
			newWikiPage.getStatusByUserName());
		assertEquals(Time.getShortTimestamp(existingWikiPage.getStatusDate()),
			Time.getShortTimestamp(newWikiPage.getStatusDate()));
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		WikiPage newWikiPage = addWikiPage();

		WikiPage existingWikiPage = _persistence.findByPrimaryKey(newWikiPage.getPrimaryKey());

		assertEquals(existingWikiPage, newWikiPage);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchPageException");
		}
		catch (NoSuchPageException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		WikiPage newWikiPage = addWikiPage();

		WikiPage existingWikiPage = _persistence.fetchByPrimaryKey(newWikiPage.getPrimaryKey());

		assertEquals(existingWikiPage, newWikiPage);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		WikiPage missingWikiPage = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingWikiPage);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WikiPage newWikiPage = addWikiPage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPage.class,
				WikiPage.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("pageId",
				newWikiPage.getPageId()));

		List<WikiPage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		WikiPage existingWikiPage = result.get(0);

		assertEquals(existingWikiPage, newWikiPage);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPage.class,
				WikiPage.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("pageId", nextLong()));

		List<WikiPage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingPageId = result.get(0);

		assertEquals(existingPageId, newPageId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPage.class,
				WikiPage.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("pageId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("pageId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		WikiPage newWikiPage = addWikiPage();

		_persistence.clearCache();

		WikiPageModelImpl existingWikiPageModelImpl = (WikiPageModelImpl)_persistence.findByPrimaryKey(newWikiPage.getPrimaryKey());

		assertTrue(Validator.equals(existingWikiPageModelImpl.getUuid(),
				existingWikiPageModelImpl.getOriginalUuid()));
		assertEquals(existingWikiPageModelImpl.getGroupId(),
			existingWikiPageModelImpl.getOriginalGroupId());

		assertEquals(existingWikiPageModelImpl.getResourcePrimKey(),
			existingWikiPageModelImpl.getOriginalResourcePrimKey());
		assertEquals(existingWikiPageModelImpl.getNodeId(),
			existingWikiPageModelImpl.getOriginalNodeId());
		assertEquals(existingWikiPageModelImpl.getVersion(),
			existingWikiPageModelImpl.getOriginalVersion());

		assertEquals(existingWikiPageModelImpl.getNodeId(),
			existingWikiPageModelImpl.getOriginalNodeId());
		assertTrue(Validator.equals(existingWikiPageModelImpl.getTitle(),
				existingWikiPageModelImpl.getOriginalTitle()));
		assertEquals(existingWikiPageModelImpl.getVersion(),
			existingWikiPageModelImpl.getOriginalVersion());
	}

	protected WikiPage addWikiPage() throws Exception {
		long pk = nextLong();

		WikiPage wikiPage = _persistence.create(pk);

		wikiPage.setUuid(randomString());

		wikiPage.setResourcePrimKey(nextLong());

		wikiPage.setGroupId(nextLong());

		wikiPage.setCompanyId(nextLong());

		wikiPage.setUserId(nextLong());

		wikiPage.setUserName(randomString());

		wikiPage.setCreateDate(nextDate());

		wikiPage.setModifiedDate(nextDate());

		wikiPage.setNodeId(nextLong());

		wikiPage.setTitle(randomString());

		wikiPage.setVersion(nextDouble());

		wikiPage.setMinorEdit(randomBoolean());

		wikiPage.setContent(randomString());

		wikiPage.setSummary(randomString());

		wikiPage.setFormat(randomString());

		wikiPage.setHead(randomBoolean());

		wikiPage.setParentTitle(randomString());

		wikiPage.setRedirectTitle(randomString());

		wikiPage.setStatus(nextInt());

		wikiPage.setStatusByUserId(nextLong());

		wikiPage.setStatusByUserName(randomString());

		wikiPage.setStatusDate(nextDate());

		_persistence.update(wikiPage, false);

		return wikiPage;
	}

	private WikiPagePersistence _persistence;
}