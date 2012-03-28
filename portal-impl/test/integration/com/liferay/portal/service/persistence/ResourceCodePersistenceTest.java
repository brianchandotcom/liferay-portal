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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchResourceCodeException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.impl.ResourceCodeModelImpl;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PropsValues;

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
public class ResourceCodePersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (ResourceCodePersistence)PortalBeanLocatorUtil.locate(ResourceCodePersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourceCode resourceCode = _persistence.create(pk);

		Assert.assertNotNull(resourceCode);

		Assert.assertEquals(resourceCode.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ResourceCode newResourceCode = addResourceCode();

		_persistence.remove(newResourceCode);

		ResourceCode existingResourceCode = _persistence.fetchByPrimaryKey(newResourceCode.getPrimaryKey());

		Assert.assertNull(existingResourceCode);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addResourceCode();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourceCode newResourceCode = _persistence.create(pk);

		newResourceCode.setCompanyId(ServiceTestUtil.nextLong());

		newResourceCode.setName(ServiceTestUtil.randomString());

		newResourceCode.setScope(ServiceTestUtil.nextInt());

		_persistence.update(newResourceCode, false);

		ResourceCode existingResourceCode = _persistence.findByPrimaryKey(newResourceCode.getPrimaryKey());

		Assert.assertEquals(existingResourceCode.getCodeId(),
			newResourceCode.getCodeId());
		Assert.assertEquals(existingResourceCode.getCompanyId(),
			newResourceCode.getCompanyId());
		Assert.assertEquals(existingResourceCode.getName(),
			newResourceCode.getName());
		Assert.assertEquals(existingResourceCode.getScope(),
			newResourceCode.getScope());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourceCode newResourceCode = addResourceCode();

		ResourceCode existingResourceCode = _persistence.findByPrimaryKey(newResourceCode.getPrimaryKey());

		Assert.assertEquals(existingResourceCode, newResourceCode);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchResourceCodeException");
		}
		catch (NoSuchResourceCodeException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourceCode newResourceCode = addResourceCode();

		ResourceCode existingResourceCode = _persistence.fetchByPrimaryKey(newResourceCode.getPrimaryKey());

		Assert.assertEquals(existingResourceCode, newResourceCode);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourceCode missingResourceCode = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingResourceCode);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ResourceCode newResourceCode = addResourceCode();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceCode.class,
				ResourceCode.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("codeId",
				newResourceCode.getCodeId()));

		List<ResourceCode> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ResourceCode existingResourceCode = result.get(0);

		Assert.assertEquals(existingResourceCode, newResourceCode);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceCode.class,
				ResourceCode.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("codeId",
				ServiceTestUtil.nextLong()));

		List<ResourceCode> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ResourceCode newResourceCode = addResourceCode();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceCode.class,
				ResourceCode.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("codeId"));

		Object newCodeId = newResourceCode.getCodeId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("codeId",
				new Object[] { newCodeId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCodeId = result.get(0);

		Assert.assertEquals(existingCodeId, newCodeId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceCode.class,
				ResourceCode.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("codeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("codeId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ResourceCode newResourceCode = addResourceCode();

		_persistence.clearCache();

		ResourceCodeModelImpl existingResourceCodeModelImpl = (ResourceCodeModelImpl)_persistence.findByPrimaryKey(newResourceCode.getPrimaryKey());

		Assert.assertEquals(existingResourceCodeModelImpl.getCompanyId(),
			existingResourceCodeModelImpl.getOriginalCompanyId());
		Assert.assertTrue(Validator.equals(
				existingResourceCodeModelImpl.getName(),
				existingResourceCodeModelImpl.getOriginalName()));
		Assert.assertEquals(existingResourceCodeModelImpl.getScope(),
			existingResourceCodeModelImpl.getOriginalScope());
	}

	protected ResourceCode addResourceCode() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ResourceCode resourceCode = _persistence.create(pk);

		resourceCode.setCompanyId(ServiceTestUtil.nextLong());

		resourceCode.setName(ServiceTestUtil.randomString());

		resourceCode.setScope(ServiceTestUtil.nextInt());

		_persistence.update(resourceCode, false);

		return resourceCode;
	}

	private ResourceCodePersistence _persistence;
}