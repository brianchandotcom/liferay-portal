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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchWebDAVPropsException;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.WebDAVProps;
import com.liferay.portal.model.impl.WebDAVPropsModelImpl;
import com.liferay.portal.service.WebDAVPropsLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class WebDAVPropsPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<WebDAVProps> modelListener : _modelListeners) {
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

		for (ModelListener<WebDAVProps> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WebDAVProps webDAVProps = _persistence.create(pk);

		Assert.assertNotNull(webDAVProps);

		Assert.assertEquals(webDAVProps.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WebDAVProps newWebDAVProps = addWebDAVProps();

		_persistence.remove(newWebDAVProps);

		WebDAVProps existingWebDAVProps = _persistence.fetchByPrimaryKey(newWebDAVProps.getPrimaryKey());

		Assert.assertNull(existingWebDAVProps);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWebDAVProps();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WebDAVProps newWebDAVProps = _persistence.create(pk);

		newWebDAVProps.setMvccVersion(RandomTestUtil.nextLong());

		newWebDAVProps.setCompanyId(RandomTestUtil.nextLong());

		newWebDAVProps.setCreateDate(RandomTestUtil.nextDate());

		newWebDAVProps.setModifiedDate(RandomTestUtil.nextDate());

		newWebDAVProps.setClassNameId(RandomTestUtil.nextLong());

		newWebDAVProps.setClassPK(RandomTestUtil.nextLong());

		newWebDAVProps.setProps(RandomTestUtil.randomString());

		_persistence.update(newWebDAVProps);

		WebDAVProps existingWebDAVProps = _persistence.findByPrimaryKey(newWebDAVProps.getPrimaryKey());

		Assert.assertEquals(existingWebDAVProps.getMvccVersion(),
			newWebDAVProps.getMvccVersion());
		Assert.assertEquals(existingWebDAVProps.getWebDavPropsId(),
			newWebDAVProps.getWebDavPropsId());
		Assert.assertEquals(existingWebDAVProps.getCompanyId(),
			newWebDAVProps.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWebDAVProps.getCreateDate()),
			Time.getShortTimestamp(newWebDAVProps.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingWebDAVProps.getModifiedDate()),
			Time.getShortTimestamp(newWebDAVProps.getModifiedDate()));
		Assert.assertEquals(existingWebDAVProps.getClassNameId(),
			newWebDAVProps.getClassNameId());
		Assert.assertEquals(existingWebDAVProps.getClassPK(),
			newWebDAVProps.getClassPK());
		Assert.assertEquals(existingWebDAVProps.getProps(),
			newWebDAVProps.getProps());
	}

	@Test
	public void testCountByC_C() {
		try {
			_persistence.countByC_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByC_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		WebDAVProps newWebDAVProps = addWebDAVProps();

		WebDAVProps existingWebDAVProps = _persistence.findByPrimaryKey(newWebDAVProps.getPrimaryKey());

		Assert.assertEquals(existingWebDAVProps, newWebDAVProps);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchWebDAVPropsException");
		}
		catch (NoSuchWebDAVPropsException nsee) {
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
		return OrderByComparatorFactoryUtil.create("WebDAVProps",
			"mvccVersion", true, "webDavPropsId", true, "companyId", true,
			"createDate", true, "modifiedDate", true, "classNameId", true,
			"classPK", true, "props", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WebDAVProps newWebDAVProps = addWebDAVProps();

		WebDAVProps existingWebDAVProps = _persistence.fetchByPrimaryKey(newWebDAVProps.getPrimaryKey());

		Assert.assertEquals(existingWebDAVProps, newWebDAVProps);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WebDAVProps missingWebDAVProps = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWebDAVProps);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		WebDAVProps newWebDAVProps1 = addWebDAVProps();
		WebDAVProps newWebDAVProps2 = addWebDAVProps();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWebDAVProps1.getPrimaryKey());
		primaryKeys.add(newWebDAVProps2.getPrimaryKey());

		Map<Serializable, WebDAVProps> webDAVPropses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, webDAVPropses.size());
		Assert.assertEquals(newWebDAVProps1,
			webDAVPropses.get(newWebDAVProps1.getPrimaryKey()));
		Assert.assertEquals(newWebDAVProps2,
			webDAVPropses.get(newWebDAVProps2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, WebDAVProps> webDAVPropses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(webDAVPropses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		WebDAVProps newWebDAVProps = addWebDAVProps();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWebDAVProps.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, WebDAVProps> webDAVPropses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, webDAVPropses.size());
		Assert.assertEquals(newWebDAVProps,
			webDAVPropses.get(newWebDAVProps.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, WebDAVProps> webDAVPropses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(webDAVPropses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		WebDAVProps newWebDAVProps = addWebDAVProps();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWebDAVProps.getPrimaryKey());

		Map<Serializable, WebDAVProps> webDAVPropses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, webDAVPropses.size());
		Assert.assertEquals(newWebDAVProps,
			webDAVPropses.get(newWebDAVProps.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = WebDAVPropsLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					WebDAVProps webDAVProps = (WebDAVProps)object;

					Assert.assertNotNull(webDAVProps);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WebDAVProps newWebDAVProps = addWebDAVProps();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WebDAVProps.class,
				WebDAVProps.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("webDavPropsId",
				newWebDAVProps.getWebDavPropsId()));

		List<WebDAVProps> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		WebDAVProps existingWebDAVProps = result.get(0);

		Assert.assertEquals(existingWebDAVProps, newWebDAVProps);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WebDAVProps.class,
				WebDAVProps.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("webDavPropsId",
				RandomTestUtil.nextLong()));

		List<WebDAVProps> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		WebDAVProps newWebDAVProps = addWebDAVProps();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WebDAVProps.class,
				WebDAVProps.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"webDavPropsId"));

		Object newWebDavPropsId = newWebDAVProps.getWebDavPropsId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("webDavPropsId",
				new Object[] { newWebDavPropsId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingWebDavPropsId = result.get(0);

		Assert.assertEquals(existingWebDavPropsId, newWebDavPropsId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WebDAVProps.class,
				WebDAVProps.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"webDavPropsId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("webDavPropsId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		WebDAVProps newWebDAVProps = addWebDAVProps();

		_persistence.clearCache();

		WebDAVPropsModelImpl existingWebDAVPropsModelImpl = (WebDAVPropsModelImpl)_persistence.findByPrimaryKey(newWebDAVProps.getPrimaryKey());

		Assert.assertEquals(existingWebDAVPropsModelImpl.getClassNameId(),
			existingWebDAVPropsModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingWebDAVPropsModelImpl.getClassPK(),
			existingWebDAVPropsModelImpl.getOriginalClassPK());
	}

	protected WebDAVProps addWebDAVProps() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WebDAVProps webDAVProps = _persistence.create(pk);

		webDAVProps.setMvccVersion(RandomTestUtil.nextLong());

		webDAVProps.setCompanyId(RandomTestUtil.nextLong());

		webDAVProps.setCreateDate(RandomTestUtil.nextDate());

		webDAVProps.setModifiedDate(RandomTestUtil.nextDate());

		webDAVProps.setClassNameId(RandomTestUtil.nextLong());

		webDAVProps.setClassPK(RandomTestUtil.nextLong());

		webDAVProps.setProps(RandomTestUtil.randomString());

		_persistence.update(webDAVProps);

		return webDAVProps;
	}

	private static Log _log = LogFactoryUtil.getLog(WebDAVPropsPersistenceTest.class);
	private ModelListener<WebDAVProps>[] _modelListeners;
	private WebDAVPropsPersistence _persistence = (WebDAVPropsPersistence)PortalBeanLocatorUtil.locate(WebDAVPropsPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}