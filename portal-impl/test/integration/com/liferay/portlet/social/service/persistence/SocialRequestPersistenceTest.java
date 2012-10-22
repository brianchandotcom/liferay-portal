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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.social.NoSuchRequestException;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.model.impl.SocialRequestModelImpl;

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
public class SocialRequestPersistenceTest {
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

		SocialRequest socialRequest = _persistence.create(pk);

		Assert.assertNotNull(socialRequest);

		Assert.assertEquals(socialRequest.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SocialRequest newSocialRequest = addSocialRequest();

		_persistence.remove(newSocialRequest);

		SocialRequest existingSocialRequest = _persistence.fetchByPrimaryKey(newSocialRequest.getPrimaryKey());

		Assert.assertNull(existingSocialRequest);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSocialRequest();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialRequest newSocialRequest = _persistence.create(pk);

		newSocialRequest.setUuid(ServiceTestUtil.randomString());

		newSocialRequest.setGroupId(ServiceTestUtil.nextLong());

		newSocialRequest.setCompanyId(ServiceTestUtil.nextLong());

		newSocialRequest.setUserId(ServiceTestUtil.nextLong());

		newSocialRequest.setCreateDate(ServiceTestUtil.nextLong());

		newSocialRequest.setModifiedDate(ServiceTestUtil.nextLong());

		newSocialRequest.setClassNameId(ServiceTestUtil.nextLong());

		newSocialRequest.setClassPK(ServiceTestUtil.nextLong());

		newSocialRequest.setType(ServiceTestUtil.nextInt());

		newSocialRequest.setExtraData(ServiceTestUtil.randomString());

		newSocialRequest.setReceiverUserId(ServiceTestUtil.nextLong());

		newSocialRequest.setStatus(ServiceTestUtil.nextInt());

		_persistence.update(newSocialRequest);

		SocialRequest existingSocialRequest = _persistence.findByPrimaryKey(newSocialRequest.getPrimaryKey());

		Assert.assertEquals(existingSocialRequest.getUuid(),
			newSocialRequest.getUuid());
		Assert.assertEquals(existingSocialRequest.getRequestId(),
			newSocialRequest.getRequestId());
		Assert.assertEquals(existingSocialRequest.getGroupId(),
			newSocialRequest.getGroupId());
		Assert.assertEquals(existingSocialRequest.getCompanyId(),
			newSocialRequest.getCompanyId());
		Assert.assertEquals(existingSocialRequest.getUserId(),
			newSocialRequest.getUserId());
		Assert.assertEquals(existingSocialRequest.getCreateDate(),
			newSocialRequest.getCreateDate());
		Assert.assertEquals(existingSocialRequest.getModifiedDate(),
			newSocialRequest.getModifiedDate());
		Assert.assertEquals(existingSocialRequest.getClassNameId(),
			newSocialRequest.getClassNameId());
		Assert.assertEquals(existingSocialRequest.getClassPK(),
			newSocialRequest.getClassPK());
		Assert.assertEquals(existingSocialRequest.getType(),
			newSocialRequest.getType());
		Assert.assertEquals(existingSocialRequest.getExtraData(),
			newSocialRequest.getExtraData());
		Assert.assertEquals(existingSocialRequest.getReceiverUserId(),
			newSocialRequest.getReceiverUserId());
		Assert.assertEquals(existingSocialRequest.getStatus(),
			newSocialRequest.getStatus());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialRequest newSocialRequest = addSocialRequest();

		SocialRequest existingSocialRequest = _persistence.findByPrimaryKey(newSocialRequest.getPrimaryKey());

		Assert.assertEquals(existingSocialRequest, newSocialRequest);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchRequestException");
		}
		catch (NoSuchRequestException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialRequest newSocialRequest = addSocialRequest();

		SocialRequest existingSocialRequest = _persistence.fetchByPrimaryKey(newSocialRequest.getPrimaryKey());

		Assert.assertEquals(existingSocialRequest, newSocialRequest);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialRequest missingSocialRequest = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSocialRequest);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialRequest newSocialRequest = addSocialRequest();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRequest.class,
				SocialRequest.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("requestId",
				newSocialRequest.getRequestId()));

		List<SocialRequest> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SocialRequest existingSocialRequest = result.get(0);

		Assert.assertEquals(existingSocialRequest, newSocialRequest);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRequest.class,
				SocialRequest.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("requestId",
				ServiceTestUtil.nextLong()));

		List<SocialRequest> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialRequest newSocialRequest = addSocialRequest();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRequest.class,
				SocialRequest.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("requestId"));

		Object newRequestId = newSocialRequest.getRequestId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("requestId",
				new Object[] { newRequestId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRequestId = result.get(0);

		Assert.assertEquals(existingRequestId, newRequestId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRequest.class,
				SocialRequest.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("requestId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("requestId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SocialRequest newSocialRequest = addSocialRequest();

		_persistence.clearCache();

		SocialRequestModelImpl existingSocialRequestModelImpl = (SocialRequestModelImpl)_persistence.findByPrimaryKey(newSocialRequest.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingSocialRequestModelImpl.getUuid(),
				existingSocialRequestModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingSocialRequestModelImpl.getGroupId(),
			existingSocialRequestModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingSocialRequestModelImpl.getUserId(),
			existingSocialRequestModelImpl.getOriginalUserId());
		Assert.assertEquals(existingSocialRequestModelImpl.getClassNameId(),
			existingSocialRequestModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingSocialRequestModelImpl.getClassPK(),
			existingSocialRequestModelImpl.getOriginalClassPK());
		Assert.assertEquals(existingSocialRequestModelImpl.getType(),
			existingSocialRequestModelImpl.getOriginalType());
		Assert.assertEquals(existingSocialRequestModelImpl.getReceiverUserId(),
			existingSocialRequestModelImpl.getOriginalReceiverUserId());
	}

	protected SocialRequest addSocialRequest() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SocialRequest socialRequest = _persistence.create(pk);

		socialRequest.setUuid(ServiceTestUtil.randomString());

		socialRequest.setGroupId(ServiceTestUtil.nextLong());

		socialRequest.setCompanyId(ServiceTestUtil.nextLong());

		socialRequest.setUserId(ServiceTestUtil.nextLong());

		socialRequest.setCreateDate(ServiceTestUtil.nextLong());

		socialRequest.setModifiedDate(ServiceTestUtil.nextLong());

		socialRequest.setClassNameId(ServiceTestUtil.nextLong());

		socialRequest.setClassPK(ServiceTestUtil.nextLong());

		socialRequest.setType(ServiceTestUtil.nextInt());

		socialRequest.setExtraData(ServiceTestUtil.randomString());

		socialRequest.setReceiverUserId(ServiceTestUtil.nextLong());

		socialRequest.setStatus(ServiceTestUtil.nextInt());

		_persistence.update(socialRequest);

		return socialRequest;
	}

	private static Log _log = LogFactoryUtil.getLog(SocialRequestPersistenceTest.class);
	private SocialRequestPersistence _persistence = (SocialRequestPersistence)PortalBeanLocatorUtil.locate(SocialRequestPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}