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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalTemplatePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (JournalTemplatePersistence)PortalBeanLocatorUtil.locate(JournalTemplatePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		JournalTemplate journalTemplate = _persistence.create(pk);

		assertNotNull(journalTemplate);

		assertEquals(journalTemplate.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		JournalTemplate newJournalTemplate = addJournalTemplate();

		_persistence.remove(newJournalTemplate);

		JournalTemplate existingJournalTemplate = _persistence.fetchByPrimaryKey(newJournalTemplate.getPrimaryKey());

		assertNull(existingJournalTemplate);
	}

	public void testUpdateNew() throws Exception {
		addJournalTemplate();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		JournalTemplate newJournalTemplate = _persistence.create(pk);

		newJournalTemplate.setUuid(randomString());

		newJournalTemplate.setGroupId(nextLong());

		newJournalTemplate.setCompanyId(nextLong());

		newJournalTemplate.setUserId(nextLong());

		newJournalTemplate.setUserName(randomString());

		newJournalTemplate.setCreateDate(nextDate());

		newJournalTemplate.setModifiedDate(nextDate());

		newJournalTemplate.setTemplateId(randomString());

		newJournalTemplate.setStructureId(randomString());

		newJournalTemplate.setName(randomString());

		newJournalTemplate.setDescription(randomString());

		newJournalTemplate.setXsl(randomString());

		newJournalTemplate.setLangType(randomString());

		newJournalTemplate.setCacheable(randomBoolean());

		newJournalTemplate.setSmallImage(randomBoolean());

		newJournalTemplate.setSmallImageId(nextLong());

		newJournalTemplate.setSmallImageURL(randomString());

		_persistence.update(newJournalTemplate, false);

		JournalTemplate existingJournalTemplate = _persistence.findByPrimaryKey(newJournalTemplate.getPrimaryKey());

		assertEquals(existingJournalTemplate.getUuid(),
			newJournalTemplate.getUuid());
		assertEquals(existingJournalTemplate.getId(), newJournalTemplate.getId());
		assertEquals(existingJournalTemplate.getGroupId(),
			newJournalTemplate.getGroupId());
		assertEquals(existingJournalTemplate.getCompanyId(),
			newJournalTemplate.getCompanyId());
		assertEquals(existingJournalTemplate.getUserId(),
			newJournalTemplate.getUserId());
		assertEquals(existingJournalTemplate.getUserName(),
			newJournalTemplate.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingJournalTemplate.getCreateDate()),
			Time.getShortTimestamp(newJournalTemplate.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingJournalTemplate.getModifiedDate()),
			Time.getShortTimestamp(newJournalTemplate.getModifiedDate()));
		assertEquals(existingJournalTemplate.getTemplateId(),
			newJournalTemplate.getTemplateId());
		assertEquals(existingJournalTemplate.getStructureId(),
			newJournalTemplate.getStructureId());
		assertEquals(existingJournalTemplate.getName(),
			newJournalTemplate.getName());
		assertEquals(existingJournalTemplate.getDescription(),
			newJournalTemplate.getDescription());
		assertEquals(existingJournalTemplate.getXsl(),
			newJournalTemplate.getXsl());
		assertEquals(existingJournalTemplate.getLangType(),
			newJournalTemplate.getLangType());
		assertEquals(existingJournalTemplate.getCacheable(),
			newJournalTemplate.getCacheable());
		assertEquals(existingJournalTemplate.getSmallImage(),
			newJournalTemplate.getSmallImage());
		assertEquals(existingJournalTemplate.getSmallImageId(),
			newJournalTemplate.getSmallImageId());
		assertEquals(existingJournalTemplate.getSmallImageURL(),
			newJournalTemplate.getSmallImageURL());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalTemplate newJournalTemplate = addJournalTemplate();

		JournalTemplate existingJournalTemplate = _persistence.findByPrimaryKey(newJournalTemplate.getPrimaryKey());

		assertEquals(existingJournalTemplate, newJournalTemplate);
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
		JournalTemplate newJournalTemplate = addJournalTemplate();

		JournalTemplate existingJournalTemplate = _persistence.fetchByPrimaryKey(newJournalTemplate.getPrimaryKey());

		assertEquals(existingJournalTemplate, newJournalTemplate);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		JournalTemplate missingJournalTemplate = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingJournalTemplate);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalTemplate newJournalTemplate = addJournalTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalTemplate.class,
				JournalTemplate.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id",
				newJournalTemplate.getId()));

		List<JournalTemplate> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		JournalTemplate existingJournalTemplate = result.get(0);

		assertEquals(existingJournalTemplate, newJournalTemplate);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalTemplate.class,
				JournalTemplate.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id", nextLong()));

		List<JournalTemplate> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		JournalTemplate newJournalTemplate = addJournalTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalTemplate.class,
				JournalTemplate.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id"));

		Object newId = newJournalTemplate.getId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id", new Object[] { newId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingId = result.get(0);

		assertEquals(existingId, newId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalTemplate.class,
				JournalTemplate.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		JournalTemplate newJournalTemplate = addJournalTemplate();

		_persistence.clearCache();

		JournalTemplateModelImpl existingJournalTemplateModelImpl = (JournalTemplateModelImpl)_persistence.findByPrimaryKey(newJournalTemplate.getPrimaryKey());

		assertTrue(Validator.equals(
				existingJournalTemplateModelImpl.getUuid(),
				existingJournalTemplateModelImpl.getOriginalUuid()));
		assertEquals(existingJournalTemplateModelImpl.getGroupId(),
			existingJournalTemplateModelImpl.getOriginalGroupId());

		assertEquals(existingJournalTemplateModelImpl.getSmallImageId(),
			existingJournalTemplateModelImpl.getOriginalSmallImageId());

		assertEquals(existingJournalTemplateModelImpl.getGroupId(),
			existingJournalTemplateModelImpl.getOriginalGroupId());
		assertTrue(Validator.equals(
				existingJournalTemplateModelImpl.getTemplateId(),
				existingJournalTemplateModelImpl.getOriginalTemplateId()));
	}

	protected JournalTemplate addJournalTemplate() throws Exception {
		long pk = nextLong();

		JournalTemplate journalTemplate = _persistence.create(pk);

		journalTemplate.setUuid(randomString());

		journalTemplate.setGroupId(nextLong());

		journalTemplate.setCompanyId(nextLong());

		journalTemplate.setUserId(nextLong());

		journalTemplate.setUserName(randomString());

		journalTemplate.setCreateDate(nextDate());

		journalTemplate.setModifiedDate(nextDate());

		journalTemplate.setTemplateId(randomString());

		journalTemplate.setStructureId(randomString());

		journalTemplate.setName(randomString());

		journalTemplate.setDescription(randomString());

		journalTemplate.setXsl(randomString());

		journalTemplate.setLangType(randomString());

		journalTemplate.setCacheable(randomBoolean());

		journalTemplate.setSmallImage(randomBoolean());

		journalTemplate.setSmallImageId(nextLong());

		journalTemplate.setSmallImageURL(randomString());

		_persistence.update(journalTemplate, false);

		return journalTemplate;
	}

	private JournalTemplatePersistence _persistence;
}