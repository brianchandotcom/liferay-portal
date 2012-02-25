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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.wiki.NoSuchPageResourceException;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.model.impl.WikiPageResourceModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class WikiPageResourcePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (WikiPageResourcePersistence)PortalBeanLocatorUtil.locate(WikiPageResourcePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		WikiPageResource wikiPageResource = _persistence.create(pk);

		assertNotNull(wikiPageResource);

		assertEquals(wikiPageResource.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		WikiPageResource newWikiPageResource = addWikiPageResource();

		_persistence.remove(newWikiPageResource);

		WikiPageResource existingWikiPageResource = _persistence.fetchByPrimaryKey(newWikiPageResource.getPrimaryKey());

		assertNull(existingWikiPageResource);
	}

	public void testUpdateNew() throws Exception {
		addWikiPageResource();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		WikiPageResource newWikiPageResource = _persistence.create(pk);

		newWikiPageResource.setUuid(randomString());

		newWikiPageResource.setNodeId(nextLong());

		newWikiPageResource.setTitle(randomString());

		_persistence.update(newWikiPageResource, false);

		WikiPageResource existingWikiPageResource = _persistence.findByPrimaryKey(newWikiPageResource.getPrimaryKey());

		assertEquals(existingWikiPageResource.getUuid(),
			newWikiPageResource.getUuid());
		assertEquals(existingWikiPageResource.getResourcePrimKey(),
			newWikiPageResource.getResourcePrimKey());
		assertEquals(existingWikiPageResource.getNodeId(),
			newWikiPageResource.getNodeId());
		assertEquals(existingWikiPageResource.getTitle(),
			newWikiPageResource.getTitle());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		WikiPageResource newWikiPageResource = addWikiPageResource();

		WikiPageResource existingWikiPageResource = _persistence.findByPrimaryKey(newWikiPageResource.getPrimaryKey());

		assertEquals(existingWikiPageResource, newWikiPageResource);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchPageResourceException");
		}
		catch (NoSuchPageResourceException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		WikiPageResource newWikiPageResource = addWikiPageResource();

		WikiPageResource existingWikiPageResource = _persistence.fetchByPrimaryKey(newWikiPageResource.getPrimaryKey());

		assertEquals(existingWikiPageResource, newWikiPageResource);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		WikiPageResource missingWikiPageResource = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingWikiPageResource);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WikiPageResource newWikiPageResource = addWikiPageResource();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPageResource.class,
				WikiPageResource.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourcePrimKey",
				newWikiPageResource.getResourcePrimKey()));

		List<WikiPageResource> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		WikiPageResource existingWikiPageResource = result.get(0);

		assertEquals(existingWikiPageResource, newWikiPageResource);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPageResource.class,
				WikiPageResource.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourcePrimKey",
				nextLong()));

		List<WikiPageResource> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		WikiPageResource newWikiPageResource = addWikiPageResource();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPageResource.class,
				WikiPageResource.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourcePrimKey"));

		Object newResourcePrimKey = newWikiPageResource.getResourcePrimKey();

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourcePrimKey",
				new Object[] { newResourcePrimKey }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingResourcePrimKey = result.get(0);

		assertEquals(existingResourcePrimKey, newResourcePrimKey);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WikiPageResource.class,
				WikiPageResource.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourcePrimKey"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourcePrimKey",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		WikiPageResource newWikiPageResource = addWikiPageResource();

		_persistence.clearCache();

		WikiPageResourceModelImpl existingWikiPageResourceModelImpl = (WikiPageResourceModelImpl)_persistence.findByPrimaryKey(newWikiPageResource.getPrimaryKey());

		assertEquals(existingWikiPageResourceModelImpl.getNodeId(),
			existingWikiPageResourceModelImpl.getOriginalNodeId());
		assertTrue(Validator.equals(
				existingWikiPageResourceModelImpl.getTitle(),
				existingWikiPageResourceModelImpl.getOriginalTitle()));
	}

	protected WikiPageResource addWikiPageResource() throws Exception {
		long pk = nextLong();

		WikiPageResource wikiPageResource = _persistence.create(pk);

		wikiPageResource.setUuid(randomString());

		wikiPageResource.setNodeId(nextLong());

		wikiPageResource.setTitle(randomString());

		_persistence.update(wikiPageResource, false);

		return wikiPageResource;
	}

	private WikiPageResourcePersistence _persistence;
}