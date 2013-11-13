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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

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
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateModelImpl;

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
public class DDMTemplatePersistenceTest {
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

		DDMTemplate ddmTemplate = _persistence.create(pk);

		Assert.assertNotNull(ddmTemplate);

		Assert.assertEquals(ddmTemplate.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDMTemplate newDDMTemplate = addDDMTemplate();

		_persistence.remove(newDDMTemplate);

		DDMTemplate existingDDMTemplate = _persistence.fetchByPrimaryKey(newDDMTemplate.getPrimaryKey());

		Assert.assertNull(existingDDMTemplate);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDMTemplate();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMTemplate newDDMTemplate = _persistence.create(pk);

		newDDMTemplate.setUuid(ServiceTestUtil.randomString());

		newDDMTemplate.setGroupId(ServiceTestUtil.nextLong());

		newDDMTemplate.setCompanyId(ServiceTestUtil.nextLong());

		newDDMTemplate.setUserId(ServiceTestUtil.nextLong());

		newDDMTemplate.setUserName(ServiceTestUtil.randomString());

		newDDMTemplate.setCreateDate(ServiceTestUtil.nextDate());

		newDDMTemplate.setModifiedDate(ServiceTestUtil.nextDate());

		newDDMTemplate.setClassNameId(ServiceTestUtil.nextLong());

		newDDMTemplate.setClassPK(ServiceTestUtil.nextLong());

		newDDMTemplate.setTemplateKey(ServiceTestUtil.randomString());

		newDDMTemplate.setName(ServiceTestUtil.randomString());

		newDDMTemplate.setDescription(ServiceTestUtil.randomString());

		newDDMTemplate.setType(ServiceTestUtil.randomString());

		newDDMTemplate.setMode(ServiceTestUtil.randomString());

		newDDMTemplate.setLanguage(ServiceTestUtil.randomString());

		newDDMTemplate.setScript(ServiceTestUtil.randomString());

		newDDMTemplate.setCacheable(ServiceTestUtil.randomBoolean());

		newDDMTemplate.setSmallImage(ServiceTestUtil.randomBoolean());

		newDDMTemplate.setSmallImageId(ServiceTestUtil.nextLong());

		newDDMTemplate.setSmallImageURL(ServiceTestUtil.randomString());

		_persistence.update(newDDMTemplate);

		DDMTemplate existingDDMTemplate = _persistence.findByPrimaryKey(newDDMTemplate.getPrimaryKey());

		Assert.assertEquals(existingDDMTemplate.getUuid(),
			newDDMTemplate.getUuid());
		Assert.assertEquals(existingDDMTemplate.getTemplateId(),
			newDDMTemplate.getTemplateId());
		Assert.assertEquals(existingDDMTemplate.getGroupId(),
			newDDMTemplate.getGroupId());
		Assert.assertEquals(existingDDMTemplate.getCompanyId(),
			newDDMTemplate.getCompanyId());
		Assert.assertEquals(existingDDMTemplate.getUserId(),
			newDDMTemplate.getUserId());
		Assert.assertEquals(existingDDMTemplate.getUserName(),
			newDDMTemplate.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDMTemplate.getCreateDate()),
			Time.getShortTimestamp(newDDMTemplate.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDMTemplate.getModifiedDate()),
			Time.getShortTimestamp(newDDMTemplate.getModifiedDate()));
		Assert.assertEquals(existingDDMTemplate.getClassNameId(),
			newDDMTemplate.getClassNameId());
		Assert.assertEquals(existingDDMTemplate.getClassPK(),
			newDDMTemplate.getClassPK());
		Assert.assertEquals(existingDDMTemplate.getTemplateKey(),
			newDDMTemplate.getTemplateKey());
		Assert.assertEquals(existingDDMTemplate.getName(),
			newDDMTemplate.getName());
		Assert.assertEquals(existingDDMTemplate.getDescription(),
			newDDMTemplate.getDescription());
		Assert.assertEquals(existingDDMTemplate.getType(),
			newDDMTemplate.getType());
		Assert.assertEquals(existingDDMTemplate.getMode(),
			newDDMTemplate.getMode());
		Assert.assertEquals(existingDDMTemplate.getLanguage(),
			newDDMTemplate.getLanguage());
		Assert.assertEquals(existingDDMTemplate.getScript(),
			newDDMTemplate.getScript());
		Assert.assertEquals(existingDDMTemplate.getCacheable(),
			newDDMTemplate.getCacheable());
		Assert.assertEquals(existingDDMTemplate.getSmallImage(),
			newDDMTemplate.getSmallImage());
		Assert.assertEquals(existingDDMTemplate.getSmallImageId(),
			newDDMTemplate.getSmallImageId());
		Assert.assertEquals(existingDDMTemplate.getSmallImageURL(),
			newDDMTemplate.getSmallImageURL());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMTemplate newDDMTemplate = addDDMTemplate();

		DDMTemplate existingDDMTemplate = _persistence.findByPrimaryKey(newDDMTemplate.getPrimaryKey());

		Assert.assertEquals(existingDDMTemplate, newDDMTemplate);
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
		return OrderByComparatorFactoryUtil.create("DDMTemplate", "uuid", true,
			"templateId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "templateKey", true, "name",
			true, "description", true, "type", true, "mode", true, "language",
			true, "script", true, "cacheable", true, "smallImage", true,
			"smallImageId", true, "smallImageURL", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMTemplate newDDMTemplate = addDDMTemplate();

		DDMTemplate existingDDMTemplate = _persistence.fetchByPrimaryKey(newDDMTemplate.getPrimaryKey());

		Assert.assertEquals(existingDDMTemplate, newDDMTemplate);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMTemplate missingDDMTemplate = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDMTemplate);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new DDMTemplateActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					DDMTemplate ddmTemplate = (DDMTemplate)object;

					Assert.assertNotNull(ddmTemplate);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMTemplate newDDMTemplate = addDDMTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMTemplate.class,
				DDMTemplate.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("templateId",
				newDDMTemplate.getTemplateId()));

		List<DDMTemplate> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDMTemplate existingDDMTemplate = result.get(0);

		Assert.assertEquals(existingDDMTemplate, newDDMTemplate);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMTemplate.class,
				DDMTemplate.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("templateId",
				ServiceTestUtil.nextLong()));

		List<DDMTemplate> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingTemplateId = result.get(0);

		Assert.assertEquals(existingTemplateId, newTemplateId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMTemplate.class,
				DDMTemplate.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("templateId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("templateId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDMTemplate newDDMTemplate = addDDMTemplate();

		_persistence.clearCache();

		DDMTemplateModelImpl existingDDMTemplateModelImpl = (DDMTemplateModelImpl)_persistence.findByPrimaryKey(newDDMTemplate.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingDDMTemplateModelImpl.getUuid(),
				existingDDMTemplateModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingDDMTemplateModelImpl.getGroupId(),
			existingDDMTemplateModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingDDMTemplateModelImpl.getSmallImageId(),
			existingDDMTemplateModelImpl.getOriginalSmallImageId());

		Assert.assertEquals(existingDDMTemplateModelImpl.getGroupId(),
			existingDDMTemplateModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingDDMTemplateModelImpl.getClassNameId(),
			existingDDMTemplateModelImpl.getOriginalClassNameId());
		Assert.assertTrue(Validator.equals(
				existingDDMTemplateModelImpl.getTemplateKey(),
				existingDDMTemplateModelImpl.getOriginalTemplateKey()));
	}

	protected DDMTemplate addDDMTemplate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMTemplate ddmTemplate = _persistence.create(pk);

		ddmTemplate.setUuid(ServiceTestUtil.randomString());

		ddmTemplate.setGroupId(ServiceTestUtil.nextLong());

		ddmTemplate.setCompanyId(ServiceTestUtil.nextLong());

		ddmTemplate.setUserId(ServiceTestUtil.nextLong());

		ddmTemplate.setUserName(ServiceTestUtil.randomString());

		ddmTemplate.setCreateDate(ServiceTestUtil.nextDate());

		ddmTemplate.setModifiedDate(ServiceTestUtil.nextDate());

		ddmTemplate.setClassNameId(ServiceTestUtil.nextLong());

		ddmTemplate.setClassPK(ServiceTestUtil.nextLong());

		ddmTemplate.setTemplateKey(ServiceTestUtil.randomString());

		ddmTemplate.setName(ServiceTestUtil.randomString());

		ddmTemplate.setDescription(ServiceTestUtil.randomString());

		ddmTemplate.setType(ServiceTestUtil.randomString());

		ddmTemplate.setMode(ServiceTestUtil.randomString());

		ddmTemplate.setLanguage(ServiceTestUtil.randomString());

		ddmTemplate.setScript(ServiceTestUtil.randomString());

		ddmTemplate.setCacheable(ServiceTestUtil.randomBoolean());

		ddmTemplate.setSmallImage(ServiceTestUtil.randomBoolean());

		ddmTemplate.setSmallImageId(ServiceTestUtil.nextLong());

		ddmTemplate.setSmallImageURL(ServiceTestUtil.randomString());

		_persistence.update(ddmTemplate);

		return ddmTemplate;
	}

	private static Log _log = LogFactoryUtil.getLog(DDMTemplatePersistenceTest.class);
	private DDMTemplatePersistence _persistence = (DDMTemplatePersistence)PortalBeanLocatorUtil.locate(DDMTemplatePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}