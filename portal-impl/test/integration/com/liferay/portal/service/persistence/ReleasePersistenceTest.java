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

import com.liferay.portal.NoSuchReleaseException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Release;
import com.liferay.portal.model.impl.ReleaseModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ReleasePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ReleasePersistence)PortalBeanLocatorUtil.locate(ReleasePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Release release = _persistence.create(pk);

		assertNotNull(release);

		assertEquals(release.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Release newRelease = addRelease();

		_persistence.remove(newRelease);

		Release existingRelease = _persistence.fetchByPrimaryKey(newRelease.getPrimaryKey());

		assertNull(existingRelease);
	}

	public void testUpdateNew() throws Exception {
		addRelease();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Release newRelease = _persistence.create(pk);

		newRelease.setCreateDate(nextDate());

		newRelease.setModifiedDate(nextDate());

		newRelease.setServletContextName(randomString());

		newRelease.setBuildNumber(nextInt());

		newRelease.setBuildDate(nextDate());

		newRelease.setVerified(randomBoolean());

		newRelease.setTestString(randomString());

		_persistence.update(newRelease, false);

		Release existingRelease = _persistence.findByPrimaryKey(newRelease.getPrimaryKey());

		assertEquals(existingRelease.getReleaseId(), newRelease.getReleaseId());
		assertEquals(Time.getShortTimestamp(existingRelease.getCreateDate()),
			Time.getShortTimestamp(newRelease.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingRelease.getModifiedDate()),
			Time.getShortTimestamp(newRelease.getModifiedDate()));
		assertEquals(existingRelease.getServletContextName(),
			newRelease.getServletContextName());
		assertEquals(existingRelease.getBuildNumber(),
			newRelease.getBuildNumber());
		assertEquals(Time.getShortTimestamp(existingRelease.getBuildDate()),
			Time.getShortTimestamp(newRelease.getBuildDate()));
		assertEquals(existingRelease.getVerified(), newRelease.getVerified());
		assertEquals(existingRelease.getTestString(), newRelease.getTestString());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Release newRelease = addRelease();

		Release existingRelease = _persistence.findByPrimaryKey(newRelease.getPrimaryKey());

		assertEquals(existingRelease, newRelease);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchReleaseException");
		}
		catch (NoSuchReleaseException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Release newRelease = addRelease();

		Release existingRelease = _persistence.fetchByPrimaryKey(newRelease.getPrimaryKey());

		assertEquals(existingRelease, newRelease);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Release missingRelease = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingRelease);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Release newRelease = addRelease();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Release.class,
				Release.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("releaseId",
				newRelease.getReleaseId()));

		List<Release> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Release existingRelease = result.get(0);

		assertEquals(existingRelease, newRelease);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Release.class,
				Release.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("releaseId", nextLong()));

		List<Release> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Release newRelease = addRelease();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Release.class,
				Release.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("releaseId"));

		Object newReleaseId = newRelease.getReleaseId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("releaseId",
				new Object[] { newReleaseId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingReleaseId = result.get(0);

		assertEquals(existingReleaseId, newReleaseId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Release.class,
				Release.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("releaseId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("releaseId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Release newRelease = addRelease();

		_persistence.clearCache();

		ReleaseModelImpl existingReleaseModelImpl = (ReleaseModelImpl)_persistence.findByPrimaryKey(newRelease.getPrimaryKey());

		assertTrue(Validator.equals(
				existingReleaseModelImpl.getServletContextName(),
				existingReleaseModelImpl.getOriginalServletContextName()));
	}

	protected Release addRelease() throws Exception {
		long pk = nextLong();

		Release release = _persistence.create(pk);

		release.setCreateDate(nextDate());

		release.setModifiedDate(nextDate());

		release.setServletContextName(randomString());

		release.setBuildNumber(nextInt());

		release.setBuildDate(nextDate());

		release.setVerified(randomBoolean());

		release.setTestString(randomString());

		_persistence.update(release, false);

		return release;
	}

	private ReleasePersistence _persistence;
}