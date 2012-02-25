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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SCProductEntryPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (SCProductEntryPersistence)PortalBeanLocatorUtil.locate(SCProductEntryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		SCProductEntry scProductEntry = _persistence.create(pk);

		assertNotNull(scProductEntry);

		assertEquals(scProductEntry.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		_persistence.remove(newSCProductEntry);

		SCProductEntry existingSCProductEntry = _persistence.fetchByPrimaryKey(newSCProductEntry.getPrimaryKey());

		assertNull(existingSCProductEntry);
	}

	public void testUpdateNew() throws Exception {
		addSCProductEntry();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SCProductEntry newSCProductEntry = _persistence.create(pk);

		newSCProductEntry.setGroupId(nextLong());

		newSCProductEntry.setCompanyId(nextLong());

		newSCProductEntry.setUserId(nextLong());

		newSCProductEntry.setUserName(randomString());

		newSCProductEntry.setCreateDate(nextDate());

		newSCProductEntry.setModifiedDate(nextDate());

		newSCProductEntry.setName(randomString());

		newSCProductEntry.setType(randomString());

		newSCProductEntry.setTags(randomString());

		newSCProductEntry.setShortDescription(randomString());

		newSCProductEntry.setLongDescription(randomString());

		newSCProductEntry.setPageURL(randomString());

		newSCProductEntry.setAuthor(randomString());

		newSCProductEntry.setRepoGroupId(randomString());

		newSCProductEntry.setRepoArtifactId(randomString());

		_persistence.update(newSCProductEntry, false);

		SCProductEntry existingSCProductEntry = _persistence.findByPrimaryKey(newSCProductEntry.getPrimaryKey());

		assertEquals(existingSCProductEntry.getProductEntryId(),
			newSCProductEntry.getProductEntryId());
		assertEquals(existingSCProductEntry.getGroupId(),
			newSCProductEntry.getGroupId());
		assertEquals(existingSCProductEntry.getCompanyId(),
			newSCProductEntry.getCompanyId());
		assertEquals(existingSCProductEntry.getUserId(),
			newSCProductEntry.getUserId());
		assertEquals(existingSCProductEntry.getUserName(),
			newSCProductEntry.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingSCProductEntry.getCreateDate()),
			Time.getShortTimestamp(newSCProductEntry.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingSCProductEntry.getModifiedDate()),
			Time.getShortTimestamp(newSCProductEntry.getModifiedDate()));
		assertEquals(existingSCProductEntry.getName(),
			newSCProductEntry.getName());
		assertEquals(existingSCProductEntry.getType(),
			newSCProductEntry.getType());
		assertEquals(existingSCProductEntry.getTags(),
			newSCProductEntry.getTags());
		assertEquals(existingSCProductEntry.getShortDescription(),
			newSCProductEntry.getShortDescription());
		assertEquals(existingSCProductEntry.getLongDescription(),
			newSCProductEntry.getLongDescription());
		assertEquals(existingSCProductEntry.getPageURL(),
			newSCProductEntry.getPageURL());
		assertEquals(existingSCProductEntry.getAuthor(),
			newSCProductEntry.getAuthor());
		assertEquals(existingSCProductEntry.getRepoGroupId(),
			newSCProductEntry.getRepoGroupId());
		assertEquals(existingSCProductEntry.getRepoArtifactId(),
			newSCProductEntry.getRepoArtifactId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		SCProductEntry existingSCProductEntry = _persistence.findByPrimaryKey(newSCProductEntry.getPrimaryKey());

		assertEquals(existingSCProductEntry, newSCProductEntry);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchProductEntryException");
		}
		catch (NoSuchProductEntryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		SCProductEntry existingSCProductEntry = _persistence.fetchByPrimaryKey(newSCProductEntry.getPrimaryKey());

		assertEquals(existingSCProductEntry, newSCProductEntry);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		SCProductEntry missingSCProductEntry = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSCProductEntry);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductEntry.class,
				SCProductEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("productEntryId",
				newSCProductEntry.getProductEntryId()));

		List<SCProductEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		SCProductEntry existingSCProductEntry = result.get(0);

		assertEquals(existingSCProductEntry, newSCProductEntry);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductEntry.class,
				SCProductEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("productEntryId", nextLong()));

		List<SCProductEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SCProductEntry newSCProductEntry = addSCProductEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductEntry.class,
				SCProductEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"productEntryId"));

		Object newProductEntryId = newSCProductEntry.getProductEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("productEntryId",
				new Object[] { newProductEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingProductEntryId = result.get(0);

		assertEquals(existingProductEntryId, newProductEntryId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductEntry.class,
				SCProductEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"productEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("productEntryId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SCProductEntry newSCProductEntry = addSCProductEntry();

		_persistence.clearCache();

		SCProductEntryModelImpl existingSCProductEntryModelImpl = (SCProductEntryModelImpl)_persistence.findByPrimaryKey(newSCProductEntry.getPrimaryKey());

		assertTrue(Validator.equals(
				existingSCProductEntryModelImpl.getRepoGroupId(),
				existingSCProductEntryModelImpl.getOriginalRepoGroupId()));
		assertTrue(Validator.equals(
				existingSCProductEntryModelImpl.getRepoArtifactId(),
				existingSCProductEntryModelImpl.getOriginalRepoArtifactId()));
	}

	protected SCProductEntry addSCProductEntry() throws Exception {
		long pk = nextLong();

		SCProductEntry scProductEntry = _persistence.create(pk);

		scProductEntry.setGroupId(nextLong());

		scProductEntry.setCompanyId(nextLong());

		scProductEntry.setUserId(nextLong());

		scProductEntry.setUserName(randomString());

		scProductEntry.setCreateDate(nextDate());

		scProductEntry.setModifiedDate(nextDate());

		scProductEntry.setName(randomString());

		scProductEntry.setType(randomString());

		scProductEntry.setTags(randomString());

		scProductEntry.setShortDescription(randomString());

		scProductEntry.setLongDescription(randomString());

		scProductEntry.setPageURL(randomString());

		scProductEntry.setAuthor(randomString());

		scProductEntry.setRepoGroupId(randomString());

		scProductEntry.setRepoArtifactId(randomString());

		_persistence.update(scProductEntry, false);

		return scProductEntry;
	}

	private SCProductEntryPersistence _persistence;
}