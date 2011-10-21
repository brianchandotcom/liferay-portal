/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMTemplatePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DDMTemplatePersistence)PortalBeanLocatorUtil.locate(DDMTemplatePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DDMTemplate ddmTemplate = _persistence.create(pk);

		assertNotNull(ddmTemplate);

		assertEquals(ddmTemplate.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DDMTemplate newDDMTemplate = addDDMTemplate();

		_persistence.remove(newDDMTemplate);

		DDMTemplate existingDDMTemplate = _persistence.fetchByPrimaryKey(newDDMTemplate.getPrimaryKey());

		assertNull(existingDDMTemplate);
	}

	public void testUpdateNew() throws Exception {
		addDDMTemplate();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DDMTemplate newDDMTemplate = _persistence.create(pk);

		newDDMTemplate.setUuid(randomString());

		newDDMTemplate.setGroupId(nextLong());

		newDDMTemplate.setCompanyId(nextLong());

		newDDMTemplate.setUserId(nextLong());

		newDDMTemplate.setUserName(randomString());

		newDDMTemplate.setCreateDate(nextDate());

		newDDMTemplate.setModifiedDate(nextDate());

		newDDMTemplate.setStructureId(nextLong());

		newDDMTemplate.setName(randomString());

		newDDMTemplate.setDescription(randomString());

		newDDMTemplate.setType(randomString());

		newDDMTemplate.setMode(randomString());

		newDDMTemplate.setLanguage(randomString());

		newDDMTemplate.setScript(randomString());

		_persistence.update(newDDMTemplate, false);

		DDMTemplate existingDDMTemplate = _persistence.findByPrimaryKey(newDDMTemplate.getPrimaryKey());

		assertEquals(existingDDMTemplate.getUuid(), newDDMTemplate.getUuid());
		assertEquals(existingDDMTemplate.getTemplateId(),
			newDDMTemplate.getTemplateId());
		assertEquals(existingDDMTemplate.getGroupId(),
			newDDMTemplate.getGroupId());
		assertEquals(existingDDMTemplate.getCompanyId(),
			newDDMTemplate.getCompanyId());
		assertEquals(existingDDMTemplate.getUserId(), newDDMTemplate.getUserId());
		assertEquals(existingDDMTemplate.getUserName(),
			newDDMTemplate.getUserName());
		assertEquals(Time.getShortTimestamp(existingDDMTemplate.getCreateDate()),
			Time.getShortTimestamp(newDDMTemplate.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingDDMTemplate.getModifiedDate()),
			Time.getShortTimestamp(newDDMTemplate.getModifiedDate()));
		assertEquals(existingDDMTemplate.getStructureId(),
			newDDMTemplate.getStructureId());
		assertEquals(existingDDMTemplate.getName(), newDDMTemplate.getName());
		assertEquals(existingDDMTemplate.getDescription(),
			newDDMTemplate.getDescription());
		assertEquals(existingDDMTemplate.getType(), newDDMTemplate.getType());
		assertEquals(existingDDMTemplate.getMode(), newDDMTemplate.getMode());
		assertEquals(existingDDMTemplate.getLanguage(),
			newDDMTemplate.getLanguage());
		assertEquals(existingDDMTemplate.getScript(), newDDMTemplate.getScript());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMTemplate newDDMTemplate = addDDMTemplate();

		DDMTemplate existingDDMTemplate = _persistence.findByPrimaryKey(newDDMTemplate.getPrimaryKey());

		assertEquals(existingDDMTemplate, newDDMTemplate);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchTemplateException");
		}
		catch (NoSuchTemplateException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMTemplate newDDMTemplate = addDDMTemplate();

		DDMTemplate existingDDMTemplate = _persistence.fetchByPrimaryKey(newDDMTemplate.getPrimaryKey());

		assertEquals(existingDDMTemplate, newDDMTemplate);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DDMTemplate missingDDMTemplate = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDDMTemplate);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMTemplate newDDMTemplate = addDDMTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMTemplate.class,
				DDMTemplate.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("templateId",
				newDDMTemplate.getTemplateId()));

		List<DDMTemplate> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DDMTemplate existingDDMTemplate = result.get(0);

		assertEquals(existingDDMTemplate, newDDMTemplate);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMTemplate.class,
				DDMTemplate.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("templateId", nextLong()));

		List<DDMTemplate> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMTemplate newDDMTemplate = addDDMTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMTemplate.class,
				DDMTemplate.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("templateId"));

		Object newTemplateId = newDDMTemplate.getTemplateId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("templateId",
				new Object[] { newTemplateId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingTemplateId = result.get(0);

		assertEquals(existingTemplateId, newTemplateId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMTemplate.class,
				DDMTemplate.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("templateId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("templateId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDMTemplate newDDMTemplate = addDDMTemplate();

		_persistence.clearCache();

		DDMTemplateModelImpl existingDDMTemplateModelImpl = (DDMTemplateModelImpl)_persistence.findByPrimaryKey(newDDMTemplate.getPrimaryKey());

		assertTrue(Validator.equals(existingDDMTemplateModelImpl.getUuid(),
				existingDDMTemplateModelImpl.getOriginalUuid()));
		assertEquals(existingDDMTemplateModelImpl.getGroupId(),
			existingDDMTemplateModelImpl.getOriginalGroupId());
	}

	protected DDMTemplate addDDMTemplate() throws Exception {
		long pk = nextLong();

		DDMTemplate ddmTemplate = _persistence.create(pk);

		ddmTemplate.setUuid(randomString());

		ddmTemplate.setGroupId(nextLong());

		ddmTemplate.setCompanyId(nextLong());

		ddmTemplate.setUserId(nextLong());

		ddmTemplate.setUserName(randomString());

		ddmTemplate.setCreateDate(nextDate());

		ddmTemplate.setModifiedDate(nextDate());

		ddmTemplate.setStructureId(nextLong());

		ddmTemplate.setName(randomString());

		ddmTemplate.setDescription(randomString());

		ddmTemplate.setType(randomString());

		ddmTemplate.setMode(randomString());

		ddmTemplate.setLanguage(randomString());

		ddmTemplate.setScript(randomString());

		_persistence.update(ddmTemplate, false);

		return ddmTemplate;
	}

	private DDMTemplatePersistence _persistence;
}