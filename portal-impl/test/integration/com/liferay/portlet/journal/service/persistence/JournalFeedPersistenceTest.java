/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.journal.NoSuchFeedException;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.impl.JournalFeedModelImpl;
import com.liferay.portlet.journal.service.JournalFeedLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<JournalFeed> modelListener : _modelListeners) {
			_persistence.unregisterListener(modelListener);
		}
	}

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

		for (ModelListener<JournalFeed> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

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
		long pk = RandomTestUtil.nextLong();

		JournalFeed newJournalFeed = _persistence.create(pk);

		newJournalFeed.setUuid(RandomTestUtil.randomString());

		newJournalFeed.setGroupId(RandomTestUtil.nextLong());

		newJournalFeed.setCompanyId(RandomTestUtil.nextLong());

		newJournalFeed.setUserId(RandomTestUtil.nextLong());

		newJournalFeed.setUserName(RandomTestUtil.randomString());

		newJournalFeed.setCreateDate(RandomTestUtil.nextDate());

		newJournalFeed.setModifiedDate(RandomTestUtil.nextDate());

		newJournalFeed.setFeedId(RandomTestUtil.randomString());

		newJournalFeed.setName(RandomTestUtil.randomString());

		newJournalFeed.setDescription(RandomTestUtil.randomString());

		newJournalFeed.setType(RandomTestUtil.randomString());

		newJournalFeed.setStructureId(RandomTestUtil.randomString());

		newJournalFeed.setTemplateId(RandomTestUtil.randomString());

		newJournalFeed.setRendererTemplateId(RandomTestUtil.randomString());

		newJournalFeed.setDelta(RandomTestUtil.nextInt());

		newJournalFeed.setOrderByCol(RandomTestUtil.randomString());

		newJournalFeed.setOrderByType(RandomTestUtil.randomString());

		newJournalFeed.setTargetLayoutFriendlyUrl(RandomTestUtil.randomString());

		newJournalFeed.setTargetPortletId(RandomTestUtil.randomString());

		newJournalFeed.setContentField(RandomTestUtil.randomString());

		newJournalFeed.setFeedFormat(RandomTestUtil.randomString());

		newJournalFeed.setFeedVersion(RandomTestUtil.nextDouble());

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
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUUID_G() {
		try {
			_persistence.countByUUID_G(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUUID_G(StringPool.NULL, 0L);

			_persistence.countByUUID_G((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(RandomTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_F() {
		try {
			_persistence.countByG_F(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_F(0L, StringPool.NULL);

			_persistence.countByG_F(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalFeed newJournalFeed = addJournalFeed();

		JournalFeed existingJournalFeed = _persistence.findByPrimaryKey(newJournalFeed.getPrimaryKey());

		Assert.assertEquals(existingJournalFeed, newJournalFeed);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

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
		long pk = RandomTestUtil.nextLong();

		JournalFeed missingJournalFeed = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingJournalFeed);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = JournalFeedLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					JournalFeed journalFeed = (JournalFeed)object;

					Assert.assertNotNull(journalFeed);

					count.increment();
				}
			});

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
				RandomTestUtil.nextLong()));

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
				new Object[] { RandomTestUtil.nextLong() }));

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
		long pk = RandomTestUtil.nextLong();

		JournalFeed journalFeed = _persistence.create(pk);

		journalFeed.setUuid(RandomTestUtil.randomString());

		journalFeed.setGroupId(RandomTestUtil.nextLong());

		journalFeed.setCompanyId(RandomTestUtil.nextLong());

		journalFeed.setUserId(RandomTestUtil.nextLong());

		journalFeed.setUserName(RandomTestUtil.randomString());

		journalFeed.setCreateDate(RandomTestUtil.nextDate());

		journalFeed.setModifiedDate(RandomTestUtil.nextDate());

		journalFeed.setFeedId(RandomTestUtil.randomString());

		journalFeed.setName(RandomTestUtil.randomString());

		journalFeed.setDescription(RandomTestUtil.randomString());

		journalFeed.setType(RandomTestUtil.randomString());

		journalFeed.setStructureId(RandomTestUtil.randomString());

		journalFeed.setTemplateId(RandomTestUtil.randomString());

		journalFeed.setRendererTemplateId(RandomTestUtil.randomString());

		journalFeed.setDelta(RandomTestUtil.nextInt());

		journalFeed.setOrderByCol(RandomTestUtil.randomString());

		journalFeed.setOrderByType(RandomTestUtil.randomString());

		journalFeed.setTargetLayoutFriendlyUrl(RandomTestUtil.randomString());

		journalFeed.setTargetPortletId(RandomTestUtil.randomString());

		journalFeed.setContentField(RandomTestUtil.randomString());

		journalFeed.setFeedFormat(RandomTestUtil.randomString());

		journalFeed.setFeedVersion(RandomTestUtil.nextDouble());

		_persistence.update(journalFeed);

		return journalFeed;
	}

	private static Log _log = LogFactoryUtil.getLog(JournalFeedPersistenceTest.class);
	private ModelListener<JournalFeed>[] _modelListeners;
	private JournalFeedPersistence _persistence = (JournalFeedPersistence)PortalBeanLocatorUtil.locate(JournalFeedPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}