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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SCFrameworkVersionPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (SCFrameworkVersionPersistence)PortalBeanLocatorUtil.locate(SCFrameworkVersionPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		SCFrameworkVersion scFrameworkVersion = _persistence.create(pk);

		assertNotNull(scFrameworkVersion);

		assertEquals(scFrameworkVersion.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		_persistence.remove(newSCFrameworkVersion);

		SCFrameworkVersion existingSCFrameworkVersion = _persistence.fetchByPrimaryKey(newSCFrameworkVersion.getPrimaryKey());

		assertNull(existingSCFrameworkVersion);
	}

	public void testUpdateNew() throws Exception {
		addSCFrameworkVersion();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SCFrameworkVersion newSCFrameworkVersion = _persistence.create(pk);

		newSCFrameworkVersion.setGroupId(nextLong());

		newSCFrameworkVersion.setCompanyId(nextLong());

		newSCFrameworkVersion.setUserId(nextLong());

		newSCFrameworkVersion.setUserName(randomString());

		newSCFrameworkVersion.setCreateDate(nextDate());

		newSCFrameworkVersion.setModifiedDate(nextDate());

		newSCFrameworkVersion.setName(randomString());

		newSCFrameworkVersion.setUrl(randomString());

		newSCFrameworkVersion.setActive(randomBoolean());

		newSCFrameworkVersion.setPriority(nextInt());

		_persistence.update(newSCFrameworkVersion, false);

		SCFrameworkVersion existingSCFrameworkVersion = _persistence.findByPrimaryKey(newSCFrameworkVersion.getPrimaryKey());

		assertEquals(existingSCFrameworkVersion.getFrameworkVersionId(),
			newSCFrameworkVersion.getFrameworkVersionId());
		assertEquals(existingSCFrameworkVersion.getGroupId(),
			newSCFrameworkVersion.getGroupId());
		assertEquals(existingSCFrameworkVersion.getCompanyId(),
			newSCFrameworkVersion.getCompanyId());
		assertEquals(existingSCFrameworkVersion.getUserId(),
			newSCFrameworkVersion.getUserId());
		assertEquals(existingSCFrameworkVersion.getUserName(),
			newSCFrameworkVersion.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingSCFrameworkVersion.getCreateDate()),
			Time.getShortTimestamp(newSCFrameworkVersion.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingSCFrameworkVersion.getModifiedDate()),
			Time.getShortTimestamp(newSCFrameworkVersion.getModifiedDate()));
		assertEquals(existingSCFrameworkVersion.getName(),
			newSCFrameworkVersion.getName());
		assertEquals(existingSCFrameworkVersion.getUrl(),
			newSCFrameworkVersion.getUrl());
		assertEquals(existingSCFrameworkVersion.getActive(),
			newSCFrameworkVersion.getActive());
		assertEquals(existingSCFrameworkVersion.getPriority(),
			newSCFrameworkVersion.getPriority());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		SCFrameworkVersion existingSCFrameworkVersion = _persistence.findByPrimaryKey(newSCFrameworkVersion.getPrimaryKey());

		assertEquals(existingSCFrameworkVersion, newSCFrameworkVersion);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchFrameworkVersionException");
		}
		catch (NoSuchFrameworkVersionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		SCFrameworkVersion existingSCFrameworkVersion = _persistence.fetchByPrimaryKey(newSCFrameworkVersion.getPrimaryKey());

		assertEquals(existingSCFrameworkVersion, newSCFrameworkVersion);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		SCFrameworkVersion missingSCFrameworkVersion = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSCFrameworkVersion);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCFrameworkVersion.class,
				SCFrameworkVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("frameworkVersionId",
				newSCFrameworkVersion.getFrameworkVersionId()));

		List<SCFrameworkVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		SCFrameworkVersion existingSCFrameworkVersion = result.get(0);

		assertEquals(existingSCFrameworkVersion, newSCFrameworkVersion);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCFrameworkVersion.class,
				SCFrameworkVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("frameworkVersionId",
				nextLong()));

		List<SCFrameworkVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCFrameworkVersion.class,
				SCFrameworkVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"frameworkVersionId"));

		Object newFrameworkVersionId = newSCFrameworkVersion.getFrameworkVersionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("frameworkVersionId",
				new Object[] { newFrameworkVersionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingFrameworkVersionId = result.get(0);

		assertEquals(existingFrameworkVersionId, newFrameworkVersionId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCFrameworkVersion.class,
				SCFrameworkVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"frameworkVersionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("frameworkVersionId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected SCFrameworkVersion addSCFrameworkVersion()
		throws Exception {
		long pk = nextLong();

		SCFrameworkVersion scFrameworkVersion = _persistence.create(pk);

		scFrameworkVersion.setGroupId(nextLong());

		scFrameworkVersion.setCompanyId(nextLong());

		scFrameworkVersion.setUserId(nextLong());

		scFrameworkVersion.setUserName(randomString());

		scFrameworkVersion.setCreateDate(nextDate());

		scFrameworkVersion.setModifiedDate(nextDate());

		scFrameworkVersion.setName(randomString());

		scFrameworkVersion.setUrl(randomString());

		scFrameworkVersion.setActive(randomBoolean());

		scFrameworkVersion.setPriority(nextInt());

		_persistence.update(scFrameworkVersion, false);

		return scFrameworkVersion;
	}

	private SCFrameworkVersionPersistence _persistence;
}