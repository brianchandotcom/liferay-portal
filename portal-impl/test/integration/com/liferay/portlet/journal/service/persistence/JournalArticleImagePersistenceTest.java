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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.journal.NoSuchArticleImageException;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.model.impl.JournalArticleImageModelImpl;

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
public class JournalArticleImagePersistenceTest {
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

		JournalArticleImage journalArticleImage = _persistence.create(pk);

		Assert.assertNotNull(journalArticleImage);

		Assert.assertEquals(journalArticleImage.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		_persistence.remove(newJournalArticleImage);

		JournalArticleImage existingJournalArticleImage = _persistence.fetchByPrimaryKey(newJournalArticleImage.getPrimaryKey());

		Assert.assertNull(existingJournalArticleImage);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addJournalArticleImage();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalArticleImage newJournalArticleImage = _persistence.create(pk);

		newJournalArticleImage.setGroupId(ServiceTestUtil.nextLong());

		newJournalArticleImage.setArticleId(ServiceTestUtil.randomString());

		newJournalArticleImage.setVersion(ServiceTestUtil.nextDouble());

		newJournalArticleImage.setElInstanceId(ServiceTestUtil.randomString());

		newJournalArticleImage.setElName(ServiceTestUtil.randomString());

		newJournalArticleImage.setLanguageId(ServiceTestUtil.randomString());

		newJournalArticleImage.setTempImage(ServiceTestUtil.randomBoolean());

		_persistence.update(newJournalArticleImage);

		JournalArticleImage existingJournalArticleImage = _persistence.findByPrimaryKey(newJournalArticleImage.getPrimaryKey());

		Assert.assertEquals(existingJournalArticleImage.getArticleImageId(),
			newJournalArticleImage.getArticleImageId());
		Assert.assertEquals(existingJournalArticleImage.getGroupId(),
			newJournalArticleImage.getGroupId());
		Assert.assertEquals(existingJournalArticleImage.getArticleId(),
			newJournalArticleImage.getArticleId());
		AssertUtils.assertEquals(
			existingJournalArticleImage.getVersion(),
			newJournalArticleImage.getVersion());
		Assert.assertEquals(existingJournalArticleImage.getElInstanceId(),
			newJournalArticleImage.getElInstanceId());
		Assert.assertEquals(existingJournalArticleImage.getElName(),
			newJournalArticleImage.getElName());
		Assert.assertEquals(existingJournalArticleImage.getLanguageId(),
			newJournalArticleImage.getLanguageId());
		Assert.assertEquals(existingJournalArticleImage.getTempImage(),
			newJournalArticleImage.getTempImage());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		JournalArticleImage existingJournalArticleImage = _persistence.findByPrimaryKey(newJournalArticleImage.getPrimaryKey());

		Assert.assertEquals(existingJournalArticleImage, newJournalArticleImage);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchArticleImageException");
		}
		catch (NoSuchArticleImageException nsee) {
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

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("JournalArticleImage",
			"articleImageId", true, "groupId", true, "articleId", true,
			"version", true, "elInstanceId", true, "elName", true,
			"languageId", true, "tempImage", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		JournalArticleImage existingJournalArticleImage = _persistence.fetchByPrimaryKey(newJournalArticleImage.getPrimaryKey());

		Assert.assertEquals(existingJournalArticleImage, newJournalArticleImage);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalArticleImage missingJournalArticleImage = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingJournalArticleImage);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new JournalArticleImageActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					JournalArticleImage journalArticleImage = (JournalArticleImage)object;

					Assert.assertNotNull(journalArticleImage);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticleImage.class,
				JournalArticleImage.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("articleImageId",
				newJournalArticleImage.getArticleImageId()));

		List<JournalArticleImage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		JournalArticleImage existingJournalArticleImage = result.get(0);

		Assert.assertEquals(existingJournalArticleImage, newJournalArticleImage);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticleImage.class,
				JournalArticleImage.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("articleImageId",
				ServiceTestUtil.nextLong()));

		List<JournalArticleImage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingArticleImageId = result.get(0);

		Assert.assertEquals(existingArticleImageId, newArticleImageId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticleImage.class,
				JournalArticleImage.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"articleImageId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("articleImageId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		JournalArticleImage newJournalArticleImage = addJournalArticleImage();

		_persistence.clearCache();

		JournalArticleImageModelImpl existingJournalArticleImageModelImpl = (JournalArticleImageModelImpl)_persistence.findByPrimaryKey(newJournalArticleImage.getPrimaryKey());

		Assert.assertEquals(existingJournalArticleImageModelImpl.getGroupId(),
			existingJournalArticleImageModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingJournalArticleImageModelImpl.getArticleId(),
				existingJournalArticleImageModelImpl.getOriginalArticleId()));
		AssertUtils.assertEquals(existingJournalArticleImageModelImpl.getVersion(),
			existingJournalArticleImageModelImpl.getOriginalVersion());
		Assert.assertTrue(Validator.equals(
				existingJournalArticleImageModelImpl.getElInstanceId(),
				existingJournalArticleImageModelImpl.getOriginalElInstanceId()));
		Assert.assertTrue(Validator.equals(
				existingJournalArticleImageModelImpl.getElName(),
				existingJournalArticleImageModelImpl.getOriginalElName()));
		Assert.assertTrue(Validator.equals(
				existingJournalArticleImageModelImpl.getLanguageId(),
				existingJournalArticleImageModelImpl.getOriginalLanguageId()));
	}

	protected JournalArticleImage addJournalArticleImage()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalArticleImage journalArticleImage = _persistence.create(pk);

		journalArticleImage.setGroupId(ServiceTestUtil.nextLong());

		journalArticleImage.setArticleId(ServiceTestUtil.randomString());

		journalArticleImage.setVersion(ServiceTestUtil.nextDouble());

		journalArticleImage.setElInstanceId(ServiceTestUtil.randomString());

		journalArticleImage.setElName(ServiceTestUtil.randomString());

		journalArticleImage.setLanguageId(ServiceTestUtil.randomString());

		journalArticleImage.setTempImage(ServiceTestUtil.randomBoolean());

		_persistence.update(journalArticleImage);

		return journalArticleImage;
	}

	private static Log _log = LogFactoryUtil.getLog(JournalArticleImagePersistenceTest.class);
	private JournalArticleImagePersistence _persistence = (JournalArticleImagePersistence)PortalBeanLocatorUtil.locate(JournalArticleImagePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}