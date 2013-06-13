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
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.journal.NoSuchFeedException;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.impl.JournalFeedModelImpl;

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
public class JournalFeedPersistenceTest {
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

		JournalFeed journalFeed = _persistence.create(pk);

		Assert.assertNotNull(journalFeed);

		Assert.assertEquals(journalFeed.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		JournalFeed newJournalFeed = addJournalFeed();

		_persistence.remove(newJournalFeed);

		JournalFeed existingJournalFeed = _persistence.fetchByPrimaryKey(newJournalFeed.getPrimaryKey());

		Assert.assertNull(existingJournalFeed);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addJournalFeed();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalFeed newJournalFeed = _persistence.create(pk);

		newJournalFeed.setUuid(ServiceTestUtil.randomString());

		newJournalFeed.setGroupId(ServiceTestUtil.nextLong());

		newJournalFeed.setCompanyId(ServiceTestUtil.nextLong());

		newJournalFeed.setUserId(ServiceTestUtil.nextLong());

		newJournalFeed.setUserName(ServiceTestUtil.randomString());

		newJournalFeed.setCreateDate(ServiceTestUtil.nextDate());

		newJournalFeed.setModifiedDate(ServiceTestUtil.nextDate());

		newJournalFeed.setFeedId(ServiceTestUtil.randomString());

		newJournalFeed.setName(ServiceTestUtil.randomString());

		newJournalFeed.setDescription(ServiceTestUtil.randomString());

		newJournalFeed.setType(ServiceTestUtil.randomString());

		newJournalFeed.setStructureId(ServiceTestUtil.randomString());

		newJournalFeed.setTemplateId(ServiceTestUtil.randomString());

		newJournalFeed.setRendererTemplateId(ServiceTestUtil.randomString());

		newJournalFeed.setDelta(ServiceTestUtil.nextInt());

		newJournalFeed.setOrderByCol(ServiceTestUtil.randomString());

		newJournalFeed.setOrderByType(ServiceTestUtil.randomString());

		newJournalFeed.setTargetLayoutFriendlyUrl(ServiceTestUtil.randomString());

		newJournalFeed.setTargetPortletId(ServiceTestUtil.randomString());

		newJournalFeed.setContentField(ServiceTestUtil.randomString());

		newJournalFeed.setFeedFormat(ServiceTestUtil.randomString());

		newJournalFeed.setFeedVersion(ServiceTestUtil.nextDouble());

		_persistence.update(newJournalFeed);

		JournalFeed existingJournalFeed = _persistence.findByPrimaryKey(newJournalFeed.getPrimaryKey());

		Assert.assertEquals(existingJournalFeed.getUuid(),
			newJournalFeed.getUuid());
		Assert.assertEquals(existingJournalFeed.getId(), newJournalFeed.getId());
		Assert.assertEquals(existingJournalFeed.getGroupId(),
			newJournalFeed.getGroupId());
		Assert.assertEquals(existingJournalFeed.getCompanyId(),
			newJournalFeed.getCompanyId());
		Assert.assertEquals(existingJournalFeed.getUserId(),
			newJournalFeed.getUserId());
		Assert.assertEquals(existingJournalFeed.getUserName(),
			newJournalFeed.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalFeed.getCreateDate()),
			Time.getShortTimestamp(newJournalFeed.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalFeed.getModifiedDate()),
			Time.getShortTimestamp(newJournalFeed.getModifiedDate()));
		Assert.assertEquals(existingJournalFeed.getFeedId(),
			newJournalFeed.getFeedId());
		Assert.assertEquals(existingJournalFeed.getName(),
			newJournalFeed.getName());
		Assert.assertEquals(existingJournalFeed.getDescription(),
			newJournalFeed.getDescription());
		Assert.assertEquals(existingJournalFeed.getType(),
			newJournalFeed.getType());
		Assert.assertEquals(existingJournalFeed.getStructureId(),
			newJournalFeed.getStructureId());
		Assert.assertEquals(existingJournalFeed.getTemplateId(),
			newJournalFeed.getTemplateId());
		Assert.assertEquals(existingJournalFeed.getRendererTemplateId(),
			newJournalFeed.getRendererTemplateId());
		Assert.assertEquals(existingJournalFeed.getDelta(),
			newJournalFeed.getDelta());
		Assert.assertEquals(existingJournalFeed.getOrderByCol(),
			newJournalFeed.getOrderByCol());
		Assert.assertEquals(existingJournalFeed.getOrderByType(),
			newJournalFeed.getOrderByType());
		Assert.assertEquals(existingJournalFeed.getTargetLayoutFriendlyUrl(),
			newJournalFeed.getTargetLayoutFriendlyUrl());
		Assert.assertEquals(existingJournalFeed.getTargetPortletId(),
			newJournalFeed.getTargetPortletId());
		Assert.assertEquals(existingJournalFeed.getContentField(),
			newJournalFeed.getContentField());
		Assert.assertEquals(existingJournalFeed.getFeedFormat(),
			newJournalFeed.getFeedFormat());
		AssertUtils.assertEquals(existingJournalFeed.getFeedVersion(),
			newJournalFeed.getFeedVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalFeed newJournalFeed = addJournalFeed();

		JournalFeed existingJournalFeed = _persistence.findByPrimaryKey(newJournalFeed.getPrimaryKey());

		Assert.assertEquals(existingJournalFeed, newJournalFeed);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchFeedException");
		}
		catch (NoSuchFeedException nsee) {
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
		return OrderByComparatorFactoryUtil.create("JournalFeed", "uuid", true,
			"id", true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"feedId", true, "name", true, "description", true, "type", true,
			"structureId", true, "templateId", true, "rendererTemplateId",
			true, "delta", true, "orderByCol", true, "orderByType", true,
			"targetLayoutFriendlyUrl", true, "targetPortletId", true,
			"contentField", true, "feedFormat", true, "feedVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		JournalFeed newJournalFeed = addJournalFeed();

		JournalFeed existingJournalFeed = _persistence.fetchByPrimaryKey(newJournalFeed.getPrimaryKey());

		Assert.assertEquals(existingJournalFeed, newJournalFeed);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalFeed missingJournalFeed = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingJournalFeed);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new JournalFeedActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					JournalFeed journalFeed = (JournalFeed)object;

					Assert.assertNotNull(journalFeed);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalFeed newJournalFeed = addJournalFeed();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalFeed.class,
				JournalFeed.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id", newJournalFeed.getId()));

		List<JournalFeed> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		JournalFeed existingJournalFeed = result.get(0);

		Assert.assertEquals(existingJournalFeed, newJournalFeed);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalFeed.class,
				JournalFeed.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id",
				ServiceTestUtil.nextLong()));

		List<JournalFeed> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		JournalFeed newJournalFeed = addJournalFeed();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalFeed.class,
				JournalFeed.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id"));

		Object newId = newJournalFeed.getId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id", new Object[] { newId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingId = result.get(0);

		Assert.assertEquals(existingId, newId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalFeed.class,
				JournalFeed.class.getClassLoader());

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

		JournalFeed newJournalFeed = addJournalFeed();

		_persistence.clearCache();

		JournalFeedModelImpl existingJournalFeedModelImpl = (JournalFeedModelImpl)_persistence.findByPrimaryKey(newJournalFeed.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingJournalFeedModelImpl.getUuid(),
				existingJournalFeedModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingJournalFeedModelImpl.getGroupId(),
			existingJournalFeedModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingJournalFeedModelImpl.getGroupId(),
			existingJournalFeedModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingJournalFeedModelImpl.getFeedId(),
				existingJournalFeedModelImpl.getOriginalFeedId()));
	}

	protected JournalFeed addJournalFeed() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalFeed journalFeed = _persistence.create(pk);

		journalFeed.setUuid(ServiceTestUtil.randomString());

		journalFeed.setGroupId(ServiceTestUtil.nextLong());

		journalFeed.setCompanyId(ServiceTestUtil.nextLong());

		journalFeed.setUserId(ServiceTestUtil.nextLong());

		journalFeed.setUserName(ServiceTestUtil.randomString());

		journalFeed.setCreateDate(ServiceTestUtil.nextDate());

		journalFeed.setModifiedDate(ServiceTestUtil.nextDate());

		journalFeed.setFeedId(ServiceTestUtil.randomString());

		journalFeed.setName(ServiceTestUtil.randomString());

		journalFeed.setDescription(ServiceTestUtil.randomString());

		journalFeed.setType(ServiceTestUtil.randomString());

		journalFeed.setStructureId(ServiceTestUtil.randomString());

		journalFeed.setTemplateId(ServiceTestUtil.randomString());

		journalFeed.setRendererTemplateId(ServiceTestUtil.randomString());

		journalFeed.setDelta(ServiceTestUtil.nextInt());

		journalFeed.setOrderByCol(ServiceTestUtil.randomString());

		journalFeed.setOrderByType(ServiceTestUtil.randomString());

		journalFeed.setTargetLayoutFriendlyUrl(ServiceTestUtil.randomString());

		journalFeed.setTargetPortletId(ServiceTestUtil.randomString());

		journalFeed.setContentField(ServiceTestUtil.randomString());

		journalFeed.setFeedFormat(ServiceTestUtil.randomString());

		journalFeed.setFeedVersion(ServiceTestUtil.nextDouble());

		_persistence.update(journalFeed);

		return journalFeed;
	}

	private static Log _log = LogFactoryUtil.getLog(JournalFeedPersistenceTest.class);
	private JournalFeedPersistence _persistence = (JournalFeedPersistence)PortalBeanLocatorUtil.locate(JournalFeedPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}