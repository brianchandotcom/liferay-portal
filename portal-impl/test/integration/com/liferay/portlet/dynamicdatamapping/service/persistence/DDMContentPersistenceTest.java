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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.dynamicdatamapping.NoSuchContentException;
import com.liferay.portlet.dynamicdatamapping.model.DDMContent;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMContentModelImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DDMContentPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (DDMContentPersistence)PortalBeanLocatorUtil.locate(DDMContentPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMContent ddmContent = _persistence.create(pk);

		Assert.assertNotNull(ddmContent);

		Assert.assertEquals(ddmContent.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDMContent newDDMContent = addDDMContent();

		_persistence.remove(newDDMContent);

		DDMContent existingDDMContent = _persistence.fetchByPrimaryKey(newDDMContent.getPrimaryKey());

		Assert.assertNull(existingDDMContent);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDMContent();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMContent newDDMContent = _persistence.create(pk);

		newDDMContent.setUuid(ServiceTestUtil.randomString());

		newDDMContent.setGroupId(ServiceTestUtil.nextLong());

		newDDMContent.setCompanyId(ServiceTestUtil.nextLong());

		newDDMContent.setUserId(ServiceTestUtil.nextLong());

		newDDMContent.setUserName(ServiceTestUtil.randomString());

		newDDMContent.setCreateDate(ServiceTestUtil.nextDate());

		newDDMContent.setModifiedDate(ServiceTestUtil.nextDate());

		newDDMContent.setName(ServiceTestUtil.randomString());

		newDDMContent.setDescription(ServiceTestUtil.randomString());

		newDDMContent.setXml(ServiceTestUtil.randomString());

		_persistence.update(newDDMContent, false);

		DDMContent existingDDMContent = _persistence.findByPrimaryKey(newDDMContent.getPrimaryKey());

		Assert.assertEquals(existingDDMContent.getUuid(),
			newDDMContent.getUuid());
		Assert.assertEquals(existingDDMContent.getContentId(),
			newDDMContent.getContentId());
		Assert.assertEquals(existingDDMContent.getGroupId(),
			newDDMContent.getGroupId());
		Assert.assertEquals(existingDDMContent.getCompanyId(),
			newDDMContent.getCompanyId());
		Assert.assertEquals(existingDDMContent.getUserId(),
			newDDMContent.getUserId());
		Assert.assertEquals(existingDDMContent.getUserName(),
			newDDMContent.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDMContent.getCreateDate()),
			Time.getShortTimestamp(newDDMContent.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDMContent.getModifiedDate()),
			Time.getShortTimestamp(newDDMContent.getModifiedDate()));
		Assert.assertEquals(existingDDMContent.getName(),
			newDDMContent.getName());
		Assert.assertEquals(existingDDMContent.getDescription(),
			newDDMContent.getDescription());
		Assert.assertEquals(existingDDMContent.getXml(), newDDMContent.getXml());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMContent newDDMContent = addDDMContent();

		DDMContent existingDDMContent = _persistence.findByPrimaryKey(newDDMContent.getPrimaryKey());

		Assert.assertEquals(existingDDMContent, newDDMContent);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchContentException");
		}
		catch (NoSuchContentException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMContent newDDMContent = addDDMContent();

		DDMContent existingDDMContent = _persistence.fetchByPrimaryKey(newDDMContent.getPrimaryKey());

		Assert.assertEquals(existingDDMContent, newDDMContent);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMContent missingDDMContent = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDMContent);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMContent newDDMContent = addDDMContent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMContent.class,
				DDMContent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentId",
				newDDMContent.getContentId()));

		List<DDMContent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDMContent existingDDMContent = result.get(0);

		Assert.assertEquals(existingDDMContent, newDDMContent);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMContent.class,
				DDMContent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentId",
				ServiceTestUtil.nextLong()));

		List<DDMContent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMContent newDDMContent = addDDMContent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMContent.class,
				DDMContent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("contentId"));

		Object newContentId = newDDMContent.getContentId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("contentId",
				new Object[] { newContentId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingContentId = result.get(0);

		Assert.assertEquals(existingContentId, newContentId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMContent.class,
				DDMContent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("contentId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("contentId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDMContent newDDMContent = addDDMContent();

		_persistence.clearCache();

		DDMContentModelImpl existingDDMContentModelImpl = (DDMContentModelImpl)_persistence.findByPrimaryKey(newDDMContent.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingDDMContentModelImpl.getUuid(),
				existingDDMContentModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingDDMContentModelImpl.getGroupId(),
			existingDDMContentModelImpl.getOriginalGroupId());
	}

	protected DDMContent addDDMContent() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMContent ddmContent = _persistence.create(pk);

		ddmContent.setUuid(ServiceTestUtil.randomString());

		ddmContent.setGroupId(ServiceTestUtil.nextLong());

		ddmContent.setCompanyId(ServiceTestUtil.nextLong());

		ddmContent.setUserId(ServiceTestUtil.nextLong());

		ddmContent.setUserName(ServiceTestUtil.randomString());

		ddmContent.setCreateDate(ServiceTestUtil.nextDate());

		ddmContent.setModifiedDate(ServiceTestUtil.nextDate());

		ddmContent.setName(ServiceTestUtil.randomString());

		ddmContent.setDescription(ServiceTestUtil.randomString());

		ddmContent.setXml(ServiceTestUtil.randomString());

		_persistence.update(ddmContent, false);

		return ddmContent;
	}

	private DDMContentPersistence _persistence;
}