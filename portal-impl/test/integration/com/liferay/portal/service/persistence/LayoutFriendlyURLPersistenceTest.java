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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchLayoutFriendlyURLException;
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
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

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
public class LayoutFriendlyURLPersistenceTest {
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

		LayoutFriendlyURL layoutFriendlyURL = _persistence.create(pk);

		Assert.assertNotNull(layoutFriendlyURL);

		Assert.assertEquals(layoutFriendlyURL.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutFriendlyURL newLayoutFriendlyURL = addLayoutFriendlyURL();

		_persistence.remove(newLayoutFriendlyURL);

		LayoutFriendlyURL existingLayoutFriendlyURL = _persistence.fetchByPrimaryKey(newLayoutFriendlyURL.getPrimaryKey());

		Assert.assertNull(existingLayoutFriendlyURL);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutFriendlyURL();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		LayoutFriendlyURL newLayoutFriendlyURL = _persistence.create(pk);

		newLayoutFriendlyURL.setUuid(ServiceTestUtil.randomString());

		newLayoutFriendlyURL.setGroupId(ServiceTestUtil.nextLong());

		newLayoutFriendlyURL.setCompanyId(ServiceTestUtil.nextLong());

		newLayoutFriendlyURL.setUserId(ServiceTestUtil.nextLong());

		newLayoutFriendlyURL.setUserName(ServiceTestUtil.randomString());

		newLayoutFriendlyURL.setCreateDate(ServiceTestUtil.nextDate());

		newLayoutFriendlyURL.setModifiedDate(ServiceTestUtil.nextDate());

		newLayoutFriendlyURL.setPlid(ServiceTestUtil.nextLong());

		newLayoutFriendlyURL.setPrivateLayout(ServiceTestUtil.randomBoolean());

		newLayoutFriendlyURL.setFriendlyURL(ServiceTestUtil.randomString());

		newLayoutFriendlyURL.setLanguageId(ServiceTestUtil.randomString());

		newLayoutFriendlyURL.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newLayoutFriendlyURL);

		LayoutFriendlyURL existingLayoutFriendlyURL = _persistence.findByPrimaryKey(newLayoutFriendlyURL.getPrimaryKey());

		Assert.assertEquals(existingLayoutFriendlyURL.getUuid(),
			newLayoutFriendlyURL.getUuid());
		Assert.assertEquals(existingLayoutFriendlyURL.getLayoutFriendlyURLId(),
			newLayoutFriendlyURL.getLayoutFriendlyURLId());
		Assert.assertEquals(existingLayoutFriendlyURL.getGroupId(),
			newLayoutFriendlyURL.getGroupId());
		Assert.assertEquals(existingLayoutFriendlyURL.getCompanyId(),
			newLayoutFriendlyURL.getCompanyId());
		Assert.assertEquals(existingLayoutFriendlyURL.getUserId(),
			newLayoutFriendlyURL.getUserId());
		Assert.assertEquals(existingLayoutFriendlyURL.getUserName(),
			newLayoutFriendlyURL.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutFriendlyURL.getCreateDate()),
			Time.getShortTimestamp(newLayoutFriendlyURL.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutFriendlyURL.getModifiedDate()),
			Time.getShortTimestamp(newLayoutFriendlyURL.getModifiedDate()));
		Assert.assertEquals(existingLayoutFriendlyURL.getPlid(),
			newLayoutFriendlyURL.getPlid());
		Assert.assertEquals(existingLayoutFriendlyURL.getPrivateLayout(),
			newLayoutFriendlyURL.getPrivateLayout());
		Assert.assertEquals(existingLayoutFriendlyURL.getFriendlyURL(),
			newLayoutFriendlyURL.getFriendlyURL());
		Assert.assertEquals(existingLayoutFriendlyURL.getLanguageId(),
			newLayoutFriendlyURL.getLanguageId());
		Assert.assertEquals(existingLayoutFriendlyURL.getMvccVersion(),
			newLayoutFriendlyURL.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutFriendlyURL newLayoutFriendlyURL = addLayoutFriendlyURL();

		LayoutFriendlyURL existingLayoutFriendlyURL = _persistence.findByPrimaryKey(newLayoutFriendlyURL.getPrimaryKey());

		Assert.assertEquals(existingLayoutFriendlyURL, newLayoutFriendlyURL);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchLayoutFriendlyURLException");
		}
		catch (NoSuchLayoutFriendlyURLException nsee) {
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
		return OrderByComparatorFactoryUtil.create("LayoutFriendlyURL", "uuid",
			true, "layoutFriendlyURLId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "plid", true, "privateLayout", true,
			"friendlyURL", true, "languageId", true, "mvccVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutFriendlyURL newLayoutFriendlyURL = addLayoutFriendlyURL();

		LayoutFriendlyURL existingLayoutFriendlyURL = _persistence.fetchByPrimaryKey(newLayoutFriendlyURL.getPrimaryKey());

		Assert.assertEquals(existingLayoutFriendlyURL, newLayoutFriendlyURL);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		LayoutFriendlyURL missingLayoutFriendlyURL = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutFriendlyURL);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new LayoutFriendlyURLActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					LayoutFriendlyURL layoutFriendlyURL = (LayoutFriendlyURL)object;

					Assert.assertNotNull(layoutFriendlyURL);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutFriendlyURL newLayoutFriendlyURL = addLayoutFriendlyURL();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutFriendlyURL.class,
				LayoutFriendlyURL.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutFriendlyURLId",
				newLayoutFriendlyURL.getLayoutFriendlyURLId()));

		List<LayoutFriendlyURL> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutFriendlyURL existingLayoutFriendlyURL = result.get(0);

		Assert.assertEquals(existingLayoutFriendlyURL, newLayoutFriendlyURL);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutFriendlyURL.class,
				LayoutFriendlyURL.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutFriendlyURLId",
				ServiceTestUtil.nextLong()));

		List<LayoutFriendlyURL> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		LayoutFriendlyURL newLayoutFriendlyURL = addLayoutFriendlyURL();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutFriendlyURL.class,
				LayoutFriendlyURL.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutFriendlyURLId"));

		Object newLayoutFriendlyURLId = newLayoutFriendlyURL.getLayoutFriendlyURLId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutFriendlyURLId",
				new Object[] { newLayoutFriendlyURLId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutFriendlyURLId = result.get(0);

		Assert.assertEquals(existingLayoutFriendlyURLId, newLayoutFriendlyURLId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutFriendlyURL.class,
				LayoutFriendlyURL.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutFriendlyURLId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutFriendlyURLId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		LayoutFriendlyURL newLayoutFriendlyURL = addLayoutFriendlyURL();

		_persistence.clearCache();

		LayoutFriendlyURLModelImpl existingLayoutFriendlyURLModelImpl = (LayoutFriendlyURLModelImpl)_persistence.findByPrimaryKey(newLayoutFriendlyURL.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingLayoutFriendlyURLModelImpl.getUuid(),
				existingLayoutFriendlyURLModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingLayoutFriendlyURLModelImpl.getGroupId(),
			existingLayoutFriendlyURLModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingLayoutFriendlyURLModelImpl.getPlid(),
			existingLayoutFriendlyURLModelImpl.getOriginalPlid());
		Assert.assertTrue(Validator.equals(
				existingLayoutFriendlyURLModelImpl.getLanguageId(),
				existingLayoutFriendlyURLModelImpl.getOriginalLanguageId()));

		Assert.assertEquals(existingLayoutFriendlyURLModelImpl.getGroupId(),
			existingLayoutFriendlyURLModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingLayoutFriendlyURLModelImpl.getPrivateLayout(),
			existingLayoutFriendlyURLModelImpl.getOriginalPrivateLayout());
		Assert.assertTrue(Validator.equals(
				existingLayoutFriendlyURLModelImpl.getFriendlyURL(),
				existingLayoutFriendlyURLModelImpl.getOriginalFriendlyURL()));
		Assert.assertTrue(Validator.equals(
				existingLayoutFriendlyURLModelImpl.getLanguageId(),
				existingLayoutFriendlyURLModelImpl.getOriginalLanguageId()));
	}

	protected LayoutFriendlyURL addLayoutFriendlyURL()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		LayoutFriendlyURL layoutFriendlyURL = _persistence.create(pk);

		layoutFriendlyURL.setUuid(ServiceTestUtil.randomString());

		layoutFriendlyURL.setGroupId(ServiceTestUtil.nextLong());

		layoutFriendlyURL.setCompanyId(ServiceTestUtil.nextLong());

		layoutFriendlyURL.setUserId(ServiceTestUtil.nextLong());

		layoutFriendlyURL.setUserName(ServiceTestUtil.randomString());

		layoutFriendlyURL.setCreateDate(ServiceTestUtil.nextDate());

		layoutFriendlyURL.setModifiedDate(ServiceTestUtil.nextDate());

		layoutFriendlyURL.setPlid(ServiceTestUtil.nextLong());

		layoutFriendlyURL.setPrivateLayout(ServiceTestUtil.randomBoolean());

		layoutFriendlyURL.setFriendlyURL(ServiceTestUtil.randomString());

		layoutFriendlyURL.setLanguageId(ServiceTestUtil.randomString());

		layoutFriendlyURL.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(layoutFriendlyURL);

		return layoutFriendlyURL;
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutFriendlyURLPersistenceTest.class);
	private LayoutFriendlyURLPersistence _persistence = (LayoutFriendlyURLPersistence)PortalBeanLocatorUtil.locate(LayoutFriendlyURLPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}