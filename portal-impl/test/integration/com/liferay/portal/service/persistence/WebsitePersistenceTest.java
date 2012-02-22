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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchWebsiteException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class WebsitePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (WebsitePersistence)PortalBeanLocatorUtil.locate(WebsitePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Website website = _persistence.create(pk);

		assertNotNull(website);

		assertEquals(website.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Website newWebsite = addWebsite();

		_persistence.remove(newWebsite);

		Website existingWebsite = _persistence.fetchByPrimaryKey(newWebsite.getPrimaryKey());

		assertNull(existingWebsite);
	}

	public void testUpdateNew() throws Exception {
		addWebsite();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Website newWebsite = _persistence.create(pk);

		newWebsite.setCompanyId(nextLong());

		newWebsite.setUserId(nextLong());

		newWebsite.setUserName(randomString());

		newWebsite.setCreateDate(nextDate());

		newWebsite.setModifiedDate(nextDate());

		newWebsite.setClassNameId(nextLong());

		newWebsite.setClassPK(nextLong());

		newWebsite.setUrl(randomString());

		newWebsite.setTypeId(nextInt());

		newWebsite.setPrimary(randomBoolean());

		_persistence.update(newWebsite, false);

		Website existingWebsite = _persistence.findByPrimaryKey(newWebsite.getPrimaryKey());

		assertEquals(existingWebsite.getWebsiteId(), newWebsite.getWebsiteId());
		assertEquals(existingWebsite.getCompanyId(), newWebsite.getCompanyId());
		assertEquals(existingWebsite.getUserId(), newWebsite.getUserId());
		assertEquals(existingWebsite.getUserName(), newWebsite.getUserName());
		assertEquals(Time.getShortTimestamp(existingWebsite.getCreateDate()),
			Time.getShortTimestamp(newWebsite.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingWebsite.getModifiedDate()),
			Time.getShortTimestamp(newWebsite.getModifiedDate()));
		assertEquals(existingWebsite.getClassNameId(),
			newWebsite.getClassNameId());
		assertEquals(existingWebsite.getClassPK(), newWebsite.getClassPK());
		assertEquals(existingWebsite.getUrl(), newWebsite.getUrl());
		assertEquals(existingWebsite.getTypeId(), newWebsite.getTypeId());
		assertEquals(existingWebsite.getPrimary(), newWebsite.getPrimary());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Website newWebsite = addWebsite();

		Website existingWebsite = _persistence.findByPrimaryKey(newWebsite.getPrimaryKey());

		assertEquals(existingWebsite, newWebsite);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchWebsiteException");
		}
		catch (NoSuchWebsiteException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Website newWebsite = addWebsite();

		Website existingWebsite = _persistence.fetchByPrimaryKey(newWebsite.getPrimaryKey());

		assertEquals(existingWebsite, newWebsite);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Website missingWebsite = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingWebsite);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Website newWebsite = addWebsite();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Website.class,
				Website.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("websiteId",
				newWebsite.getWebsiteId()));

		List<Website> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Website existingWebsite = result.get(0);

		assertEquals(existingWebsite, newWebsite);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Website.class,
				Website.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("websiteId", nextLong()));

		List<Website> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Website newWebsite = addWebsite();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Website.class,
				Website.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("websiteId"));

		Object newWebsiteId = newWebsite.getWebsiteId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("websiteId",
				new Object[] { newWebsiteId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingWebsiteId = result.get(0);

		assertEquals(existingWebsiteId, newWebsiteId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Website.class,
				Website.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("websiteId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("websiteId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Website addWebsite() throws Exception {
		long pk = nextLong();

		Website website = _persistence.create(pk);

		website.setCompanyId(nextLong());

		website.setUserId(nextLong());

		website.setUserName(randomString());

		website.setCreateDate(nextDate());

		website.setModifiedDate(nextDate());

		website.setClassNameId(nextLong());

		website.setClassPK(nextLong());

		website.setUrl(randomString());

		website.setTypeId(nextInt());

		website.setPrimary(randomBoolean());

		_persistence.update(website, false);

		return website;
	}

	private WebsitePersistence _persistence;
}