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

import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.impl.JournalArticleModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalArticlePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (JournalArticlePersistence)PortalBeanLocatorUtil.locate(JournalArticlePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		JournalArticle journalArticle = _persistence.create(pk);

		assertNotNull(journalArticle);

		assertEquals(journalArticle.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		_persistence.remove(newJournalArticle);

		JournalArticle existingJournalArticle = _persistence.fetchByPrimaryKey(newJournalArticle.getPrimaryKey());

		assertNull(existingJournalArticle);
	}

	public void testUpdateNew() throws Exception {
		addJournalArticle();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		JournalArticle newJournalArticle = _persistence.create(pk);

		newJournalArticle.setUuid(randomString());

		newJournalArticle.setResourcePrimKey(nextLong());

		newJournalArticle.setGroupId(nextLong());

		newJournalArticle.setCompanyId(nextLong());

		newJournalArticle.setUserId(nextLong());

		newJournalArticle.setUserName(randomString());

		newJournalArticle.setCreateDate(nextDate());

		newJournalArticle.setModifiedDate(nextDate());

		newJournalArticle.setFolderId(nextLong());

		newJournalArticle.setClassNameId(nextLong());

		newJournalArticle.setClassPK(nextLong());

		newJournalArticle.setArticleId(randomString());

		newJournalArticle.setVersion(nextDouble());

		newJournalArticle.setTitle(randomString());

		newJournalArticle.setUrlTitle(randomString());

		newJournalArticle.setDescription(randomString());

		newJournalArticle.setContent(randomString());

		newJournalArticle.setType(randomString());

		newJournalArticle.setStructureId(randomString());

		newJournalArticle.setTemplateId(randomString());

		newJournalArticle.setLayoutUuid(randomString());

		newJournalArticle.setDisplayDate(nextDate());

		newJournalArticle.setExpirationDate(nextDate());

		newJournalArticle.setReviewDate(nextDate());

		newJournalArticle.setIndexable(randomBoolean());

		newJournalArticle.setSmallImage(randomBoolean());

		newJournalArticle.setSmallImageId(nextLong());

		newJournalArticle.setSmallImageURL(randomString());

		newJournalArticle.setStatus(nextInt());

		newJournalArticle.setStatusByUserId(nextLong());

		newJournalArticle.setStatusByUserName(randomString());

		newJournalArticle.setStatusDate(nextDate());

		_persistence.update(newJournalArticle, false);

		JournalArticle existingJournalArticle = _persistence.findByPrimaryKey(newJournalArticle.getPrimaryKey());

		assertEquals(existingJournalArticle.getUuid(),
			newJournalArticle.getUuid());
		assertEquals(existingJournalArticle.getId(), newJournalArticle.getId());
		assertEquals(existingJournalArticle.getResourcePrimKey(),
			newJournalArticle.getResourcePrimKey());
		assertEquals(existingJournalArticle.getGroupId(),
			newJournalArticle.getGroupId());
		assertEquals(existingJournalArticle.getCompanyId(),
			newJournalArticle.getCompanyId());
		assertEquals(existingJournalArticle.getUserId(),
			newJournalArticle.getUserId());
		assertEquals(existingJournalArticle.getUserName(),
			newJournalArticle.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getCreateDate()),
			Time.getShortTimestamp(newJournalArticle.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getModifiedDate()),
			Time.getShortTimestamp(newJournalArticle.getModifiedDate()));
		assertEquals(existingJournalArticle.getFolderId(),
			newJournalArticle.getFolderId());
		assertEquals(existingJournalArticle.getClassNameId(),
			newJournalArticle.getClassNameId());
		assertEquals(existingJournalArticle.getClassPK(),
			newJournalArticle.getClassPK());
		assertEquals(existingJournalArticle.getArticleId(),
			newJournalArticle.getArticleId());
		assertEquals(existingJournalArticle.getVersion(),
			newJournalArticle.getVersion());
		assertEquals(existingJournalArticle.getTitle(),
			newJournalArticle.getTitle());
		assertEquals(existingJournalArticle.getUrlTitle(),
			newJournalArticle.getUrlTitle());
		assertEquals(existingJournalArticle.getDescription(),
			newJournalArticle.getDescription());
		assertEquals(existingJournalArticle.getContent(),
			newJournalArticle.getContent());
		assertEquals(existingJournalArticle.getType(),
			newJournalArticle.getType());
		assertEquals(existingJournalArticle.getStructureId(),
			newJournalArticle.getStructureId());
		assertEquals(existingJournalArticle.getTemplateId(),
			newJournalArticle.getTemplateId());
		assertEquals(existingJournalArticle.getLayoutUuid(),
			newJournalArticle.getLayoutUuid());
		assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getDisplayDate()),
			Time.getShortTimestamp(newJournalArticle.getDisplayDate()));
		assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getExpirationDate()),
			Time.getShortTimestamp(newJournalArticle.getExpirationDate()));
		assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getReviewDate()),
			Time.getShortTimestamp(newJournalArticle.getReviewDate()));
		assertEquals(existingJournalArticle.getIndexable(),
			newJournalArticle.getIndexable());
		assertEquals(existingJournalArticle.getSmallImage(),
			newJournalArticle.getSmallImage());
		assertEquals(existingJournalArticle.getSmallImageId(),
			newJournalArticle.getSmallImageId());
		assertEquals(existingJournalArticle.getSmallImageURL(),
			newJournalArticle.getSmallImageURL());
		assertEquals(existingJournalArticle.getStatus(),
			newJournalArticle.getStatus());
		assertEquals(existingJournalArticle.getStatusByUserId(),
			newJournalArticle.getStatusByUserId());
		assertEquals(existingJournalArticle.getStatusByUserName(),
			newJournalArticle.getStatusByUserName());
		assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getStatusDate()),
			Time.getShortTimestamp(newJournalArticle.getStatusDate()));
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		JournalArticle existingJournalArticle = _persistence.findByPrimaryKey(newJournalArticle.getPrimaryKey());

		assertEquals(existingJournalArticle, newJournalArticle);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchArticleException");
		}
		catch (NoSuchArticleException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		JournalArticle existingJournalArticle = _persistence.fetchByPrimaryKey(newJournalArticle.getPrimaryKey());

		assertEquals(existingJournalArticle, newJournalArticle);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		JournalArticle missingJournalArticle = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingJournalArticle);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticle.class,
				JournalArticle.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id",
				newJournalArticle.getId()));

		List<JournalArticle> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		JournalArticle existingJournalArticle = result.get(0);

		assertEquals(existingJournalArticle, newJournalArticle);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticle.class,
				JournalArticle.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id", nextLong()));

		List<JournalArticle> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticle.class,
				JournalArticle.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id"));

		Object newId = newJournalArticle.getId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id", new Object[] { newId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingId = result.get(0);

		assertEquals(existingId, newId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticle.class,
				JournalArticle.class.getClassLoader());

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

		JournalArticle newJournalArticle = addJournalArticle();

		_persistence.clearCache();

		JournalArticleModelImpl existingJournalArticleModelImpl = (JournalArticleModelImpl)_persistence.findByPrimaryKey(newJournalArticle.getPrimaryKey());

		assertTrue(Validator.equals(existingJournalArticleModelImpl.getUuid(),
				existingJournalArticleModelImpl.getOriginalUuid()));
		assertEquals(existingJournalArticleModelImpl.getGroupId(),
			existingJournalArticleModelImpl.getOriginalGroupId());

		assertEquals(existingJournalArticleModelImpl.getGroupId(),
			existingJournalArticleModelImpl.getOriginalGroupId());
		assertEquals(existingJournalArticleModelImpl.getClassNameId(),
			existingJournalArticleModelImpl.getOriginalClassNameId());
		assertTrue(Validator.equals(
				existingJournalArticleModelImpl.getStructureId(),
				existingJournalArticleModelImpl.getOriginalStructureId()));

		assertEquals(existingJournalArticleModelImpl.getGroupId(),
			existingJournalArticleModelImpl.getOriginalGroupId());
		assertTrue(Validator.equals(
				existingJournalArticleModelImpl.getArticleId(),
				existingJournalArticleModelImpl.getOriginalArticleId()));
		assertEquals(existingJournalArticleModelImpl.getVersion(),
			existingJournalArticleModelImpl.getOriginalVersion());
	}

	protected JournalArticle addJournalArticle() throws Exception {
		long pk = nextLong();

		JournalArticle journalArticle = _persistence.create(pk);

		journalArticle.setUuid(randomString());

		journalArticle.setResourcePrimKey(nextLong());

		journalArticle.setGroupId(nextLong());

		journalArticle.setCompanyId(nextLong());

		journalArticle.setUserId(nextLong());

		journalArticle.setUserName(randomString());

		journalArticle.setCreateDate(nextDate());

		journalArticle.setModifiedDate(nextDate());

		journalArticle.setFolderId(nextLong());

		journalArticle.setClassNameId(nextLong());

		journalArticle.setClassPK(nextLong());

		journalArticle.setArticleId(randomString());

		journalArticle.setVersion(nextDouble());

		journalArticle.setTitle(randomString());

		journalArticle.setUrlTitle(randomString());

		journalArticle.setDescription(randomString());

		journalArticle.setContent(randomString());

		journalArticle.setType(randomString());

		journalArticle.setStructureId(randomString());

		journalArticle.setTemplateId(randomString());

		journalArticle.setLayoutUuid(randomString());

		journalArticle.setDisplayDate(nextDate());

		journalArticle.setExpirationDate(nextDate());

		journalArticle.setReviewDate(nextDate());

		journalArticle.setIndexable(randomBoolean());

		journalArticle.setSmallImage(randomBoolean());

		journalArticle.setSmallImageId(nextLong());

		journalArticle.setSmallImageURL(randomString());

		journalArticle.setStatus(nextInt());

		journalArticle.setStatusByUserId(nextLong());

		journalArticle.setStatusByUserName(randomString());

		journalArticle.setStatusDate(nextDate());

		_persistence.update(journalArticle, false);

		return journalArticle;
	}

	private JournalArticlePersistence _persistence;
}