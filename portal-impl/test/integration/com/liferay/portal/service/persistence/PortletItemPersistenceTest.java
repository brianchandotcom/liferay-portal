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

import com.liferay.portal.NoSuchPortletItemException;
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
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.impl.PortletItemModelImpl;
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
public class PortletItemPersistenceTest {
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

		PortletItem portletItem = _persistence.create(pk);

		Assert.assertNotNull(portletItem);

		Assert.assertEquals(portletItem.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PortletItem newPortletItem = addPortletItem();

		_persistence.remove(newPortletItem);

		PortletItem existingPortletItem = _persistence.fetchByPrimaryKey(newPortletItem.getPrimaryKey());

		Assert.assertNull(existingPortletItem);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPortletItem();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		PortletItem newPortletItem = _persistence.create(pk);

		newPortletItem.setGroupId(ServiceTestUtil.nextLong());

		newPortletItem.setCompanyId(ServiceTestUtil.nextLong());

		newPortletItem.setUserId(ServiceTestUtil.nextLong());

		newPortletItem.setUserName(ServiceTestUtil.randomString());

		newPortletItem.setCreateDate(ServiceTestUtil.nextDate());

		newPortletItem.setModifiedDate(ServiceTestUtil.nextDate());

		newPortletItem.setName(ServiceTestUtil.randomString());

		newPortletItem.setPortletId(ServiceTestUtil.randomString());

		newPortletItem.setClassNameId(ServiceTestUtil.nextLong());

		newPortletItem.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newPortletItem);

		PortletItem existingPortletItem = _persistence.findByPrimaryKey(newPortletItem.getPrimaryKey());

		Assert.assertEquals(existingPortletItem.getPortletItemId(),
			newPortletItem.getPortletItemId());
		Assert.assertEquals(existingPortletItem.getGroupId(),
			newPortletItem.getGroupId());
		Assert.assertEquals(existingPortletItem.getCompanyId(),
			newPortletItem.getCompanyId());
		Assert.assertEquals(existingPortletItem.getUserId(),
			newPortletItem.getUserId());
		Assert.assertEquals(existingPortletItem.getUserName(),
			newPortletItem.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingPortletItem.getCreateDate()),
			Time.getShortTimestamp(newPortletItem.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingPortletItem.getModifiedDate()),
			Time.getShortTimestamp(newPortletItem.getModifiedDate()));
		Assert.assertEquals(existingPortletItem.getName(),
			newPortletItem.getName());
		Assert.assertEquals(existingPortletItem.getPortletId(),
			newPortletItem.getPortletId());
		Assert.assertEquals(existingPortletItem.getClassNameId(),
			newPortletItem.getClassNameId());
		Assert.assertEquals(existingPortletItem.getMvccVersion(),
			newPortletItem.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PortletItem newPortletItem = addPortletItem();

		PortletItem existingPortletItem = _persistence.findByPrimaryKey(newPortletItem.getPrimaryKey());

		Assert.assertEquals(existingPortletItem, newPortletItem);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchPortletItemException");
		}
		catch (NoSuchPortletItemException nsee) {
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
		return OrderByComparatorFactoryUtil.create("PortletItem",
			"portletItemId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "portletId", true,
			"classNameId", true, "mvccVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PortletItem newPortletItem = addPortletItem();

		PortletItem existingPortletItem = _persistence.fetchByPrimaryKey(newPortletItem.getPrimaryKey());

		Assert.assertEquals(existingPortletItem, newPortletItem);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		PortletItem missingPortletItem = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPortletItem);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new PortletItemActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					PortletItem portletItem = (PortletItem)object;

					Assert.assertNotNull(portletItem);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PortletItem newPortletItem = addPortletItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				PortletItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portletItemId",
				newPortletItem.getPortletItemId()));

		List<PortletItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		PortletItem existingPortletItem = result.get(0);

		Assert.assertEquals(existingPortletItem, newPortletItem);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				PortletItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portletItemId",
				ServiceTestUtil.nextLong()));

		List<PortletItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PortletItem newPortletItem = addPortletItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				PortletItem.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portletItemId"));

		Object newPortletItemId = newPortletItem.getPortletItemId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("portletItemId",
				new Object[] { newPortletItemId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPortletItemId = result.get(0);

		Assert.assertEquals(existingPortletItemId, newPortletItemId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				PortletItem.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portletItemId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("portletItemId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		PortletItem newPortletItem = addPortletItem();

		_persistence.clearCache();

		PortletItemModelImpl existingPortletItemModelImpl = (PortletItemModelImpl)_persistence.findByPrimaryKey(newPortletItem.getPrimaryKey());

		Assert.assertEquals(existingPortletItemModelImpl.getGroupId(),
			existingPortletItemModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingPortletItemModelImpl.getName(),
				existingPortletItemModelImpl.getOriginalName()));
		Assert.assertTrue(Validator.equals(
				existingPortletItemModelImpl.getPortletId(),
				existingPortletItemModelImpl.getOriginalPortletId()));
		Assert.assertEquals(existingPortletItemModelImpl.getClassNameId(),
			existingPortletItemModelImpl.getOriginalClassNameId());
	}

	protected PortletItem addPortletItem() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		PortletItem portletItem = _persistence.create(pk);

		portletItem.setGroupId(ServiceTestUtil.nextLong());

		portletItem.setCompanyId(ServiceTestUtil.nextLong());

		portletItem.setUserId(ServiceTestUtil.nextLong());

		portletItem.setUserName(ServiceTestUtil.randomString());

		portletItem.setCreateDate(ServiceTestUtil.nextDate());

		portletItem.setModifiedDate(ServiceTestUtil.nextDate());

		portletItem.setName(ServiceTestUtil.randomString());

		portletItem.setPortletId(ServiceTestUtil.randomString());

		portletItem.setClassNameId(ServiceTestUtil.nextLong());

		portletItem.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(portletItem);

		return portletItem;
	}

	private static Log _log = LogFactoryUtil.getLog(PortletItemPersistenceTest.class);
	private PortletItemPersistence _persistence = (PortletItemPersistence)PortalBeanLocatorUtil.locate(PortletItemPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}