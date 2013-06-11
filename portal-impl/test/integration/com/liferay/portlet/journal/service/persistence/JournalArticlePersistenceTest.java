/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.impl.JournalArticleModelImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class JournalArticlePersistenceTest {
	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalArticle journalArticle = _persistence.create(pk);

		Assert.assertNotNull(journalArticle);

		Assert.assertEquals(journalArticle.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		_persistence.remove(newJournalArticle);

		JournalArticle existingJournalArticle = _persistence.fetchByPrimaryKey(newJournalArticle.getPrimaryKey());

		Assert.assertNull(existingJournalArticle);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addJournalArticle();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalArticle newJournalArticle = _persistence.create(pk);

		newJournalArticle.setUuid(ServiceTestUtil.randomString());

		newJournalArticle.setResourcePrimKey(ServiceTestUtil.nextLong());

		newJournalArticle.setGroupId(ServiceTestUtil.nextLong());

		newJournalArticle.setCompanyId(ServiceTestUtil.nextLong());

		newJournalArticle.setUserId(ServiceTestUtil.nextLong());

		newJournalArticle.setUserName(ServiceTestUtil.randomString());

		newJournalArticle.setCreateDate(ServiceTestUtil.nextDate());

		newJournalArticle.setModifiedDate(ServiceTestUtil.nextDate());

		newJournalArticle.setFolderId(ServiceTestUtil.nextLong());

		newJournalArticle.setClassNameId(ServiceTestUtil.nextLong());

		newJournalArticle.setClassPK(ServiceTestUtil.nextLong());

		newJournalArticle.setArticleId(ServiceTestUtil.randomString());

		newJournalArticle.setVersion(ServiceTestUtil.nextDouble());

		newJournalArticle.setTitle(ServiceTestUtil.randomString());

		newJournalArticle.setUrlTitle(ServiceTestUtil.randomString());

		newJournalArticle.setDescription(ServiceTestUtil.randomString());

		newJournalArticle.setContent(ServiceTestUtil.randomString());

		newJournalArticle.setType(ServiceTestUtil.randomString());

		newJournalArticle.setStructureId(ServiceTestUtil.randomString());

		newJournalArticle.setTemplateId(ServiceTestUtil.randomString());

		newJournalArticle.setLayoutUuid(ServiceTestUtil.randomString());

		newJournalArticle.setDisplayDate(ServiceTestUtil.nextDate());

		newJournalArticle.setExpirationDate(ServiceTestUtil.nextDate());

		newJournalArticle.setReviewDate(ServiceTestUtil.nextDate());

		newJournalArticle.setIndexable(ServiceTestUtil.randomBoolean());

		newJournalArticle.setSmallImage(ServiceTestUtil.randomBoolean());

		newJournalArticle.setSmallImageId(ServiceTestUtil.nextLong());

		newJournalArticle.setSmallImageURL(ServiceTestUtil.randomString());

		newJournalArticle.setStatus(ServiceTestUtil.nextInt());

		newJournalArticle.setStatusByUserId(ServiceTestUtil.nextLong());

		newJournalArticle.setStatusByUserName(ServiceTestUtil.randomString());

		newJournalArticle.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(newJournalArticle);

		JournalArticle existingJournalArticle = _persistence.findByPrimaryKey(newJournalArticle.getPrimaryKey());

		Assert.assertEquals(existingJournalArticle.getUuid(),
			newJournalArticle.getUuid());
		Assert.assertEquals(existingJournalArticle.getId(),
			newJournalArticle.getId());
		Assert.assertEquals(existingJournalArticle.getResourcePrimKey(),
			newJournalArticle.getResourcePrimKey());
		Assert.assertEquals(existingJournalArticle.getGroupId(),
			newJournalArticle.getGroupId());
		Assert.assertEquals(existingJournalArticle.getCompanyId(),
			newJournalArticle.getCompanyId());
		Assert.assertEquals(existingJournalArticle.getUserId(),
			newJournalArticle.getUserId());
		Assert.assertEquals(existingJournalArticle.getUserName(),
			newJournalArticle.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getCreateDate()),
			Time.getShortTimestamp(newJournalArticle.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getModifiedDate()),
			Time.getShortTimestamp(newJournalArticle.getModifiedDate()));
		Assert.assertEquals(existingJournalArticle.getFolderId(),
			newJournalArticle.getFolderId());
		Assert.assertEquals(existingJournalArticle.getClassNameId(),
			newJournalArticle.getClassNameId());
		Assert.assertEquals(existingJournalArticle.getClassPK(),
			newJournalArticle.getClassPK());
		Assert.assertEquals(existingJournalArticle.getArticleId(),
			newJournalArticle.getArticleId());
		AssertUtils.assertEquals(
			existingJournalArticle.getVersion(),
			newJournalArticle.getVersion());
		Assert.assertEquals(existingJournalArticle.getTitle(),
			newJournalArticle.getTitle());
		Assert.assertEquals(existingJournalArticle.getUrlTitle(),
			newJournalArticle.getUrlTitle());
		Assert.assertEquals(existingJournalArticle.getDescription(),
			newJournalArticle.getDescription());
		Assert.assertEquals(existingJournalArticle.getContent(),
			newJournalArticle.getContent());
		Assert.assertEquals(existingJournalArticle.getType(),
			newJournalArticle.getType());
		Assert.assertEquals(existingJournalArticle.getStructureId(),
			newJournalArticle.getStructureId());
		Assert.assertEquals(existingJournalArticle.getTemplateId(),
			newJournalArticle.getTemplateId());
		Assert.assertEquals(existingJournalArticle.getLayoutUuid(),
			newJournalArticle.getLayoutUuid());
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getDisplayDate()),
			Time.getShortTimestamp(newJournalArticle.getDisplayDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getExpirationDate()),
			Time.getShortTimestamp(newJournalArticle.getExpirationDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getReviewDate()),
			Time.getShortTimestamp(newJournalArticle.getReviewDate()));
		Assert.assertEquals(existingJournalArticle.getIndexable(),
			newJournalArticle.getIndexable());
		Assert.assertEquals(existingJournalArticle.getSmallImage(),
			newJournalArticle.getSmallImage());
		Assert.assertEquals(existingJournalArticle.getSmallImageId(),
			newJournalArticle.getSmallImageId());
		Assert.assertEquals(existingJournalArticle.getSmallImageURL(),
			newJournalArticle.getSmallImageURL());
		Assert.assertEquals(existingJournalArticle.getStatus(),
			newJournalArticle.getStatus());
		Assert.assertEquals(existingJournalArticle.getStatusByUserId(),
			newJournalArticle.getStatusByUserId());
		Assert.assertEquals(existingJournalArticle.getStatusByUserName(),
			newJournalArticle.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getStatusDate()),
			Time.getShortTimestamp(newJournalArticle.getStatusDate()));
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		JournalArticle existingJournalArticle = _persistence.findByPrimaryKey(newJournalArticle.getPrimaryKey());

		Assert.assertEquals(existingJournalArticle, newJournalArticle);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchArticleException");
		}
		catch (NoSuchArticleException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		try {
			_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("JournalArticle", "uuid",
			true, "id", true, "resourcePrimKey", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "folderId", true, "classNameId", true,
			"classPK", true, "articleId", true, "version", true, "title", true,
			"urlTitle", true, "description", true, "content", true, "type",
			true, "structureId", true, "templateId", true, "layoutUuid", true,
			"displayDate", true, "expirationDate", true, "reviewDate", true,
			"indexable", true, "smallImage", true, "smallImageId", true,
			"smallImageURL", true, "status", true, "statusByUserId", true,
			"statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		JournalArticle existingJournalArticle = _persistence.fetchByPrimaryKey(newJournalArticle.getPrimaryKey());

		Assert.assertEquals(existingJournalArticle, newJournalArticle);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalArticle missingJournalArticle = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingJournalArticle);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new JournalArticleActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					JournalArticle journalArticle = (JournalArticle)object;

					Assert.assertNotNull(journalArticle);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticle.class,
				JournalArticle.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id",
				newJournalArticle.getId()));

		List<JournalArticle> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		JournalArticle existingJournalArticle = result.get(0);

		Assert.assertEquals(existingJournalArticle, newJournalArticle);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticle.class,
				JournalArticle.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id",
				ServiceTestUtil.nextLong()));

		List<JournalArticle> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticle.class,
				JournalArticle.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id"));

		Object newId = newJournalArticle.getId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id", new Object[] { newId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingId = result.get(0);

		Assert.assertEquals(existingId, newId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticle.class,
				JournalArticle.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		JournalArticle newJournalArticle = addJournalArticle();

		_persistence.clearCache();

		JournalArticleModelImpl existingJournalArticleModelImpl = (JournalArticleModelImpl)_persistence.findByPrimaryKey(newJournalArticle.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingJournalArticleModelImpl.getUuid(),
				existingJournalArticleModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingJournalArticleModelImpl.getGroupId(),
			existingJournalArticleModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingJournalArticleModelImpl.getGroupId(),
			existingJournalArticleModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingJournalArticleModelImpl.getClassNameId(),
			existingJournalArticleModelImpl.getOriginalClassNameId());
		Assert.assertTrue(Validator.equals(
				existingJournalArticleModelImpl.getStructureId(),
				existingJournalArticleModelImpl.getOriginalStructureId()));

		Assert.assertEquals(existingJournalArticleModelImpl.getGroupId(),
			existingJournalArticleModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingJournalArticleModelImpl.getArticleId(),
				existingJournalArticleModelImpl.getOriginalArticleId()));
		AssertUtils.assertEquals(existingJournalArticleModelImpl.getVersion(),
			existingJournalArticleModelImpl.getOriginalVersion());
	}

	protected JournalArticle addJournalArticle() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalArticle journalArticle = _persistence.create(pk);

		journalArticle.setUuid(ServiceTestUtil.randomString());

		journalArticle.setResourcePrimKey(ServiceTestUtil.nextLong());

		journalArticle.setGroupId(ServiceTestUtil.nextLong());

		journalArticle.setCompanyId(ServiceTestUtil.nextLong());

		journalArticle.setUserId(ServiceTestUtil.nextLong());

		journalArticle.setUserName(ServiceTestUtil.randomString());

		journalArticle.setCreateDate(ServiceTestUtil.nextDate());

		journalArticle.setModifiedDate(ServiceTestUtil.nextDate());

		journalArticle.setFolderId(ServiceTestUtil.nextLong());

		journalArticle.setClassNameId(ServiceTestUtil.nextLong());

		journalArticle.setClassPK(ServiceTestUtil.nextLong());

		journalArticle.setArticleId(ServiceTestUtil.randomString());

		journalArticle.setVersion(ServiceTestUtil.nextDouble());

		journalArticle.setTitle(ServiceTestUtil.randomString());

		journalArticle.setUrlTitle(ServiceTestUtil.randomString());

		journalArticle.setDescription(ServiceTestUtil.randomString());

		journalArticle.setContent(ServiceTestUtil.randomString());

		journalArticle.setType(ServiceTestUtil.randomString());

		journalArticle.setStructureId(ServiceTestUtil.randomString());

		journalArticle.setTemplateId(ServiceTestUtil.randomString());

		journalArticle.setLayoutUuid(ServiceTestUtil.randomString());

		journalArticle.setDisplayDate(ServiceTestUtil.nextDate());

		journalArticle.setExpirationDate(ServiceTestUtil.nextDate());

		journalArticle.setReviewDate(ServiceTestUtil.nextDate());

		journalArticle.setIndexable(ServiceTestUtil.randomBoolean());

		journalArticle.setSmallImage(ServiceTestUtil.randomBoolean());

		journalArticle.setSmallImageId(ServiceTestUtil.nextLong());

		journalArticle.setSmallImageURL(ServiceTestUtil.randomString());

		journalArticle.setStatus(ServiceTestUtil.nextInt());

		journalArticle.setStatusByUserId(ServiceTestUtil.nextLong());

		journalArticle.setStatusByUserName(ServiceTestUtil.randomString());

		journalArticle.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(journalArticle);

		return journalArticle;
	}

	private static Log _log = LogFactoryUtil.getLog(JournalArticlePersistenceTest.class);
	private JournalArticlePersistence _persistence = (JournalArticlePersistence)PortalBeanLocatorUtil.locate(JournalArticlePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}