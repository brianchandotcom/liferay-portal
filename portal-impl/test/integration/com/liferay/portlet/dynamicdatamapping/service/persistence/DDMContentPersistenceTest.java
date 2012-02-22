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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.dynamicdatamapping.NoSuchContentException;
import com.liferay.portlet.dynamicdatamapping.model.DDMContent;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMContentModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMContentPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DDMContentPersistence)PortalBeanLocatorUtil.locate(DDMContentPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DDMContent ddmContent = _persistence.create(pk);

		assertNotNull(ddmContent);

		assertEquals(ddmContent.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DDMContent newDDMContent = addDDMContent();

		_persistence.remove(newDDMContent);

		DDMContent existingDDMContent = _persistence.fetchByPrimaryKey(newDDMContent.getPrimaryKey());

		assertNull(existingDDMContent);
	}

	public void testUpdateNew() throws Exception {
		addDDMContent();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DDMContent newDDMContent = _persistence.create(pk);

		newDDMContent.setUuid(randomString());

		newDDMContent.setGroupId(nextLong());

		newDDMContent.setCompanyId(nextLong());

		newDDMContent.setUserId(nextLong());

		newDDMContent.setUserName(randomString());

		newDDMContent.setCreateDate(nextDate());

		newDDMContent.setModifiedDate(nextDate());

		newDDMContent.setName(randomString());

		newDDMContent.setDescription(randomString());

		newDDMContent.setXml(randomString());

		_persistence.update(newDDMContent, false);

		DDMContent existingDDMContent = _persistence.findByPrimaryKey(newDDMContent.getPrimaryKey());

		assertEquals(existingDDMContent.getUuid(), newDDMContent.getUuid());
		assertEquals(existingDDMContent.getContentId(),
			newDDMContent.getContentId());
		assertEquals(existingDDMContent.getGroupId(), newDDMContent.getGroupId());
		assertEquals(existingDDMContent.getCompanyId(),
			newDDMContent.getCompanyId());
		assertEquals(existingDDMContent.getUserId(), newDDMContent.getUserId());
		assertEquals(existingDDMContent.getUserName(),
			newDDMContent.getUserName());
		assertEquals(Time.getShortTimestamp(existingDDMContent.getCreateDate()),
			Time.getShortTimestamp(newDDMContent.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingDDMContent.getModifiedDate()),
			Time.getShortTimestamp(newDDMContent.getModifiedDate()));
		assertEquals(existingDDMContent.getName(), newDDMContent.getName());
		assertEquals(existingDDMContent.getDescription(),
			newDDMContent.getDescription());
		assertEquals(existingDDMContent.getXml(), newDDMContent.getXml());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMContent newDDMContent = addDDMContent();

		DDMContent existingDDMContent = _persistence.findByPrimaryKey(newDDMContent.getPrimaryKey());

		assertEquals(existingDDMContent, newDDMContent);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchContentException");
		}
		catch (NoSuchContentException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMContent newDDMContent = addDDMContent();

		DDMContent existingDDMContent = _persistence.fetchByPrimaryKey(newDDMContent.getPrimaryKey());

		assertEquals(existingDDMContent, newDDMContent);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DDMContent missingDDMContent = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDDMContent);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMContent newDDMContent = addDDMContent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMContent.class,
				DDMContent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentId",
				newDDMContent.getContentId()));

		List<DDMContent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DDMContent existingDDMContent = result.get(0);

		assertEquals(existingDDMContent, newDDMContent);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMContent.class,
				DDMContent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentId", nextLong()));

		List<DDMContent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMContent newDDMContent = addDDMContent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMContent.class,
				DDMContent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("contentId"));

		Object newContentId = newDDMContent.getContentId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("contentId",
				new Object[] { newContentId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingContentId = result.get(0);

		assertEquals(existingContentId, newContentId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMContent.class,
				DDMContent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("contentId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("contentId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDMContent newDDMContent = addDDMContent();

		_persistence.clearCache();

		DDMContentModelImpl existingDDMContentModelImpl = (DDMContentModelImpl)_persistence.findByPrimaryKey(newDDMContent.getPrimaryKey());

		assertTrue(Validator.equals(existingDDMContentModelImpl.getUuid(),
				existingDDMContentModelImpl.getOriginalUuid()));
		assertEquals(existingDDMContentModelImpl.getGroupId(),
			existingDDMContentModelImpl.getOriginalGroupId());
	}

	protected DDMContent addDDMContent() throws Exception {
		long pk = nextLong();

		DDMContent ddmContent = _persistence.create(pk);

		ddmContent.setUuid(randomString());

		ddmContent.setGroupId(nextLong());

		ddmContent.setCompanyId(nextLong());

		ddmContent.setUserId(nextLong());

		ddmContent.setUserName(randomString());

		ddmContent.setCreateDate(nextDate());

		ddmContent.setModifiedDate(nextDate());

		ddmContent.setName(randomString());

		ddmContent.setDescription(randomString());

		ddmContent.setXml(randomString());

		_persistence.update(ddmContent, false);

		return ddmContent;
	}

	private DDMContentPersistence _persistence;
}