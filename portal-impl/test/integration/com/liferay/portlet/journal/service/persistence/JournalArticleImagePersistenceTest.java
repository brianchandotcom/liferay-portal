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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.journal.NoSuchArticleImageException;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.model.impl.JournalArticleImageModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalArticleImagePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (JournalArticleImagePersistence)PortalBeanLocatorUtil.locate(JournalArticleImagePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		JournalArticleImage journalArticleImage = _persistence.create(pk);

		assertNotNull(journalArticleImage);

		assertEquals(journalArticleImage.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		_persistence.remove(newJournalArticleImage);

		JournalArticleImage existingJournalArticleImage = _persistence.fetchByPrimaryKey(newJournalArticleImage.getPrimaryKey());

		assertNull(existingJournalArticleImage);
	}

	public void testUpdateNew() throws Exception {
		addJournalArticleImage();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		JournalArticleImage newJournalArticleImage = _persistence.create(pk);

		newJournalArticleImage.setGroupId(nextLong());

		newJournalArticleImage.setArticleId(randomString());

		newJournalArticleImage.setVersion(nextDouble());

		newJournalArticleImage.setElInstanceId(randomString());

		newJournalArticleImage.setElName(randomString());

		newJournalArticleImage.setLanguageId(randomString());

		newJournalArticleImage.setTempImage(randomBoolean());

		_persistence.update(newJournalArticleImage, false);

		JournalArticleImage existingJournalArticleImage = _persistence.findByPrimaryKey(newJournalArticleImage.getPrimaryKey());

		assertEquals(existingJournalArticleImage.getArticleImageId(),
			newJournalArticleImage.getArticleImageId());
		assertEquals(existingJournalArticleImage.getGroupId(),
			newJournalArticleImage.getGroupId());
		assertEquals(existingJournalArticleImage.getArticleId(),
			newJournalArticleImage.getArticleId());
		assertEquals(existingJournalArticleImage.getVersion(),
			newJournalArticleImage.getVersion());
		assertEquals(existingJournalArticleImage.getElInstanceId(),
			newJournalArticleImage.getElInstanceId());
		assertEquals(existingJournalArticleImage.getElName(),
			newJournalArticleImage.getElName());
		assertEquals(existingJournalArticleImage.getLanguageId(),
			newJournalArticleImage.getLanguageId());
		assertEquals(existingJournalArticleImage.getTempImage(),
			newJournalArticleImage.getTempImage());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		JournalArticleImage existingJournalArticleImage = _persistence.findByPrimaryKey(newJournalArticleImage.getPrimaryKey());

		assertEquals(existingJournalArticleImage, newJournalArticleImage);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchArticleImageException");
		}
		catch (NoSuchArticleImageException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		JournalArticleImage existingJournalArticleImage = _persistence.fetchByPrimaryKey(newJournalArticleImage.getPrimaryKey());

		assertEquals(existingJournalArticleImage, newJournalArticleImage);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		JournalArticleImage missingJournalArticleImage = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingJournalArticleImage);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticleImage.class,
				JournalArticleImage.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("articleImageId",
				newJournalArticleImage.getArticleImageId()));

		List<JournalArticleImage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		JournalArticleImage existingJournalArticleImage = result.get(0);

		assertEquals(existingJournalArticleImage, newJournalArticleImage);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticleImage.class,
				JournalArticleImage.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("articleImageId", nextLong()));

		List<JournalArticleImage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticleImage.class,
				JournalArticleImage.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"articleImageId"));

		Object newArticleImageId = newJournalArticleImage.getArticleImageId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("articleImageId",
				new Object[] { newArticleImageId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingArticleImageId = result.get(0);

		assertEquals(existingArticleImageId, newArticleImageId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticleImage.class,
				JournalArticleImage.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"articleImageId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("articleImageId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		_persistence.clearCache();

		JournalArticleImageModelImpl existingJournalArticleImageModelImpl = (JournalArticleImageModelImpl)_persistence.findByPrimaryKey(newJournalArticleImage.getPrimaryKey());

		assertEquals(existingJournalArticleImageModelImpl.getGroupId(),
			existingJournalArticleImageModelImpl.getOriginalGroupId());
		assertTrue(Validator.equals(
				existingJournalArticleImageModelImpl.getArticleId(),
				existingJournalArticleImageModelImpl.getOriginalArticleId()));
		assertEquals(existingJournalArticleImageModelImpl.getVersion(),
			existingJournalArticleImageModelImpl.getOriginalVersion());
		assertTrue(Validator.equals(
				existingJournalArticleImageModelImpl.getElInstanceId(),
				existingJournalArticleImageModelImpl.getOriginalElInstanceId()));
		assertTrue(Validator.equals(
				existingJournalArticleImageModelImpl.getElName(),
				existingJournalArticleImageModelImpl.getOriginalElName()));
		assertTrue(Validator.equals(
				existingJournalArticleImageModelImpl.getLanguageId(),
				existingJournalArticleImageModelImpl.getOriginalLanguageId()));
	}

	protected JournalArticleImage addJournalArticleImage()
		throws Exception {
		long pk = nextLong();

		JournalArticleImage journalArticleImage = _persistence.create(pk);

		journalArticleImage.setGroupId(nextLong());

		journalArticleImage.setArticleId(randomString());

		journalArticleImage.setVersion(nextDouble());

		journalArticleImage.setElInstanceId(randomString());

		journalArticleImage.setElName(randomString());

		journalArticleImage.setLanguageId(randomString());

		journalArticleImage.setTempImage(randomBoolean());

		_persistence.update(journalArticleImage, false);

		return journalArticleImage;
	}

	private JournalArticleImagePersistence _persistence;
}