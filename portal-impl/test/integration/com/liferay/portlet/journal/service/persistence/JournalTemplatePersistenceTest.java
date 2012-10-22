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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalTemplateModelImpl;

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
public class JournalTemplatePersistenceTest {
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

		JournalTemplate journalTemplate = _persistence.create(pk);

		Assert.assertNotNull(journalTemplate);

		Assert.assertEquals(journalTemplate.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		JournalTemplate newJournalTemplate = addJournalTemplate();

		_persistence.remove(newJournalTemplate);

		JournalTemplate existingJournalTemplate = _persistence.fetchByPrimaryKey(newJournalTemplate.getPrimaryKey());

		Assert.assertNull(existingJournalTemplate);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addJournalTemplate();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalTemplate newJournalTemplate = _persistence.create(pk);

		newJournalTemplate.setUuid(ServiceTestUtil.randomString());

		newJournalTemplate.setGroupId(ServiceTestUtil.nextLong());

		newJournalTemplate.setCompanyId(ServiceTestUtil.nextLong());

		newJournalTemplate.setUserId(ServiceTestUtil.nextLong());

		newJournalTemplate.setUserName(ServiceTestUtil.randomString());

		newJournalTemplate.setCreateDate(ServiceTestUtil.nextDate());

		newJournalTemplate.setModifiedDate(ServiceTestUtil.nextDate());

		newJournalTemplate.setTemplateId(ServiceTestUtil.randomString());

		newJournalTemplate.setStructureId(ServiceTestUtil.randomString());

		newJournalTemplate.setName(ServiceTestUtil.randomString());

		newJournalTemplate.setDescription(ServiceTestUtil.randomString());

		newJournalTemplate.setXsl(ServiceTestUtil.randomString());

		newJournalTemplate.setLangType(ServiceTestUtil.randomString());

		newJournalTemplate.setCacheable(ServiceTestUtil.randomBoolean());

		newJournalTemplate.setSmallImage(ServiceTestUtil.randomBoolean());

		newJournalTemplate.setSmallImageId(ServiceTestUtil.nextLong());

		newJournalTemplate.setSmallImageURL(ServiceTestUtil.randomString());

		_persistence.update(newJournalTemplate);

		JournalTemplate existingJournalTemplate = _persistence.findByPrimaryKey(newJournalTemplate.getPrimaryKey());

		Assert.assertEquals(existingJournalTemplate.getUuid(),
			newJournalTemplate.getUuid());
		Assert.assertEquals(existingJournalTemplate.getId(),
			newJournalTemplate.getId());
		Assert.assertEquals(existingJournalTemplate.getGroupId(),
			newJournalTemplate.getGroupId());
		Assert.assertEquals(existingJournalTemplate.getCompanyId(),
			newJournalTemplate.getCompanyId());
		Assert.assertEquals(existingJournalTemplate.getUserId(),
			newJournalTemplate.getUserId());
		Assert.assertEquals(existingJournalTemplate.getUserName(),
			newJournalTemplate.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalTemplate.getCreateDate()),
			Time.getShortTimestamp(newJournalTemplate.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingJournalTemplate.getModifiedDate()),
			Time.getShortTimestamp(newJournalTemplate.getModifiedDate()));
		Assert.assertEquals(existingJournalTemplate.getTemplateId(),
			newJournalTemplate.getTemplateId());
		Assert.assertEquals(existingJournalTemplate.getStructureId(),
			newJournalTemplate.getStructureId());
		Assert.assertEquals(existingJournalTemplate.getName(),
			newJournalTemplate.getName());
		Assert.assertEquals(existingJournalTemplate.getDescription(),
			newJournalTemplate.getDescription());
		Assert.assertEquals(existingJournalTemplate.getXsl(),
			newJournalTemplate.getXsl());
		Assert.assertEquals(existingJournalTemplate.getLangType(),
			newJournalTemplate.getLangType());
		Assert.assertEquals(existingJournalTemplate.getCacheable(),
			newJournalTemplate.getCacheable());
		Assert.assertEquals(existingJournalTemplate.getSmallImage(),
			newJournalTemplate.getSmallImage());
		Assert.assertEquals(existingJournalTemplate.getSmallImageId(),
			newJournalTemplate.getSmallImageId());
		Assert.assertEquals(existingJournalTemplate.getSmallImageURL(),
			newJournalTemplate.getSmallImageURL());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalTemplate newJournalTemplate = addJournalTemplate();

		JournalTemplate existingJournalTemplate = _persistence.findByPrimaryKey(newJournalTemplate.getPrimaryKey());

		Assert.assertEquals(existingJournalTemplate, newJournalTemplate);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchTemplateException");
		}
		catch (NoSuchTemplateException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		JournalTemplate newJournalTemplate = addJournalTemplate();

		JournalTemplate existingJournalTemplate = _persistence.fetchByPrimaryKey(newJournalTemplate.getPrimaryKey());

		Assert.assertEquals(existingJournalTemplate, newJournalTemplate);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalTemplate missingJournalTemplate = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingJournalTemplate);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalTemplate newJournalTemplate = addJournalTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalTemplate.class,
				JournalTemplate.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id",
				newJournalTemplate.getId()));

		List<JournalTemplate> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		JournalTemplate existingJournalTemplate = result.get(0);

		Assert.assertEquals(existingJournalTemplate, newJournalTemplate);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalTemplate.class,
				JournalTemplate.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id",
				ServiceTestUtil.nextLong()));

		List<JournalTemplate> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		JournalTemplate newJournalTemplate = addJournalTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalTemplate.class,
				JournalTemplate.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id"));

		Object newId = newJournalTemplate.getId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id", new Object[] { newId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingId = result.get(0);

		Assert.assertEquals(existingId, newId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalTemplate.class,
				JournalTemplate.class.getClassLoader());

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

		JournalTemplate newJournalTemplate = addJournalTemplate();

		_persistence.clearCache();

		JournalTemplateModelImpl existingJournalTemplateModelImpl = (JournalTemplateModelImpl)_persistence.findByPrimaryKey(newJournalTemplate.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingJournalTemplateModelImpl.getUuid(),
				existingJournalTemplateModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingJournalTemplateModelImpl.getGroupId(),
			existingJournalTemplateModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingJournalTemplateModelImpl.getSmallImageId(),
			existingJournalTemplateModelImpl.getOriginalSmallImageId());

		Assert.assertEquals(existingJournalTemplateModelImpl.getGroupId(),
			existingJournalTemplateModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingJournalTemplateModelImpl.getTemplateId(),
				existingJournalTemplateModelImpl.getOriginalTemplateId()));
	}

	protected JournalTemplate addJournalTemplate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalTemplate journalTemplate = _persistence.create(pk);

		journalTemplate.setUuid(ServiceTestUtil.randomString());

		journalTemplate.setGroupId(ServiceTestUtil.nextLong());

		journalTemplate.setCompanyId(ServiceTestUtil.nextLong());

		journalTemplate.setUserId(ServiceTestUtil.nextLong());

		journalTemplate.setUserName(ServiceTestUtil.randomString());

		journalTemplate.setCreateDate(ServiceTestUtil.nextDate());

		journalTemplate.setModifiedDate(ServiceTestUtil.nextDate());

		journalTemplate.setTemplateId(ServiceTestUtil.randomString());

		journalTemplate.setStructureId(ServiceTestUtil.randomString());

		journalTemplate.setName(ServiceTestUtil.randomString());

		journalTemplate.setDescription(ServiceTestUtil.randomString());

		journalTemplate.setXsl(ServiceTestUtil.randomString());

		journalTemplate.setLangType(ServiceTestUtil.randomString());

		journalTemplate.setCacheable(ServiceTestUtil.randomBoolean());

		journalTemplate.setSmallImage(ServiceTestUtil.randomBoolean());

		journalTemplate.setSmallImageId(ServiceTestUtil.nextLong());

		journalTemplate.setSmallImageURL(ServiceTestUtil.randomString());

		_persistence.update(journalTemplate);

		return journalTemplate;
	}

	private static Log _log = LogFactoryUtil.getLog(JournalTemplatePersistenceTest.class);
	private JournalTemplatePersistence _persistence = (JournalTemplatePersistence)PortalBeanLocatorUtil.locate(JournalTemplatePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}