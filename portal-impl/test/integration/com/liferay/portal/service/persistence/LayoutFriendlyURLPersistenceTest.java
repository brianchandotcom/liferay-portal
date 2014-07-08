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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl;
import com.liferay.portal.service.LayoutFriendlyURLLocalServiceUtil;
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
public class LayoutFriendlyURLPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<LayoutFriendlyURL> modelListener : _modelListeners) {
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

		for (ModelListener<LayoutFriendlyURL> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

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
		long pk = RandomTestUtil.nextLong();

		LayoutFriendlyURL newLayoutFriendlyURL = _persistence.create(pk);

		newLayoutFriendlyURL.setMvccVersion(RandomTestUtil.nextLong());

		newLayoutFriendlyURL.setUuid(RandomTestUtil.randomString());

		newLayoutFriendlyURL.setGroupId(RandomTestUtil.nextLong());

		newLayoutFriendlyURL.setCompanyId(RandomTestUtil.nextLong());

		newLayoutFriendlyURL.setUserId(RandomTestUtil.nextLong());

		newLayoutFriendlyURL.setUserName(RandomTestUtil.randomString());

		newLayoutFriendlyURL.setCreateDate(RandomTestUtil.nextDate());

		newLayoutFriendlyURL.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutFriendlyURL.setPlid(RandomTestUtil.nextLong());

		newLayoutFriendlyURL.setPrivateLayout(RandomTestUtil.randomBoolean());

		newLayoutFriendlyURL.setFriendlyURL(RandomTestUtil.randomString());

		newLayoutFriendlyURL.setLanguageId(RandomTestUtil.randomString());

		_persistence.update(newLayoutFriendlyURL);

		LayoutFriendlyURL existingLayoutFriendlyURL = _persistence.findByPrimaryKey(newLayoutFriendlyURL.getPrimaryKey());

		Assert.assertEquals(existingLayoutFriendlyURL.getMvccVersion(),
			newLayoutFriendlyURL.getMvccVersion());
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
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(RandomTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByPlid() {
		try {
			_persistence.countByPlid(RandomTestUtil.nextLong());

			_persistence.countByPlid(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByP_F() {
		try {
			_persistence.countByP_F(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByP_F(0L, StringPool.NULL);

			_persistence.countByP_F(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByP_L() {
		try {
			_persistence.countByP_L(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByP_L(0L, StringPool.NULL);

			_persistence.countByP_L(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P_F() {
		try {
			_persistence.countByG_P_F(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), StringPool.BLANK);

			_persistence.countByG_P_F(0L, RandomTestUtil.randomBoolean(),
				StringPool.NULL);

			_persistence.countByG_P_F(0L, RandomTestUtil.randomBoolean(),
				(String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P_F_L() {
		try {
			_persistence.countByG_P_F_L(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), StringPool.BLANK,
				StringPool.BLANK);

			_persistence.countByG_P_F_L(0L, RandomTestUtil.randomBoolean(),
				StringPool.NULL, StringPool.NULL);

			_persistence.countByG_P_F_L(0L, RandomTestUtil.randomBoolean(),
				(String)null, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutFriendlyURL newLayoutFriendlyURL = addLayoutFriendlyURL();

		LayoutFriendlyURL existingLayoutFriendlyURL = _persistence.findByPrimaryKey(newLayoutFriendlyURL.getPrimaryKey());

		Assert.assertEquals(existingLayoutFriendlyURL, newLayoutFriendlyURL);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

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
		return OrderByComparatorFactoryUtil.create("LayoutFriendlyURL",
			"mvccVersion", true, "uuid", true, "layoutFriendlyURLId", true,
			"groupId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "plid", true,
			"privateLayout", true, "friendlyURL", true, "languageId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutFriendlyURL newLayoutFriendlyURL = addLayoutFriendlyURL();

		LayoutFriendlyURL existingLayoutFriendlyURL = _persistence.fetchByPrimaryKey(newLayoutFriendlyURL.getPrimaryKey());

		Assert.assertEquals(existingLayoutFriendlyURL, newLayoutFriendlyURL);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutFriendlyURL missingLayoutFriendlyURL = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutFriendlyURL);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		LayoutFriendlyURL newLayoutFriendlyURL1 = addLayoutFriendlyURL();
		LayoutFriendlyURL newLayoutFriendlyURL2 = addLayoutFriendlyURL();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutFriendlyURL1.getPrimaryKey());
		primaryKeys.add(newLayoutFriendlyURL2.getPrimaryKey());

		Map<Serializable, LayoutFriendlyURL> layoutFriendlyURLs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutFriendlyURLs.size());
		Assert.assertEquals(newLayoutFriendlyURL1,
			layoutFriendlyURLs.get(newLayoutFriendlyURL1.getPrimaryKey()));
		Assert.assertEquals(newLayoutFriendlyURL2,
			layoutFriendlyURLs.get(newLayoutFriendlyURL2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutFriendlyURL> layoutFriendlyURLs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutFriendlyURLs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		LayoutFriendlyURL newLayoutFriendlyURL = addLayoutFriendlyURL();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutFriendlyURL.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutFriendlyURL> layoutFriendlyURLs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutFriendlyURLs.size());
		Assert.assertEquals(newLayoutFriendlyURL,
			layoutFriendlyURLs.get(newLayoutFriendlyURL.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutFriendlyURL> layoutFriendlyURLs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutFriendlyURLs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		LayoutFriendlyURL newLayoutFriendlyURL = addLayoutFriendlyURL();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutFriendlyURL.getPrimaryKey());

		Map<Serializable, LayoutFriendlyURL> layoutFriendlyURLs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutFriendlyURLs.size());
		Assert.assertEquals(newLayoutFriendlyURL,
			layoutFriendlyURLs.get(newLayoutFriendlyURL.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = LayoutFriendlyURLLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					LayoutFriendlyURL layoutFriendlyURL = (LayoutFriendlyURL)object;

					Assert.assertNotNull(layoutFriendlyURL);

					count.increment();
				}
			});

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
				RandomTestUtil.nextLong()));

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
				new Object[] { RandomTestUtil.nextLong() }));

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
		long pk = RandomTestUtil.nextLong();

		LayoutFriendlyURL layoutFriendlyURL = _persistence.create(pk);

		layoutFriendlyURL.setMvccVersion(RandomTestUtil.nextLong());

		layoutFriendlyURL.setUuid(RandomTestUtil.randomString());

		layoutFriendlyURL.setGroupId(RandomTestUtil.nextLong());

		layoutFriendlyURL.setCompanyId(RandomTestUtil.nextLong());

		layoutFriendlyURL.setUserId(RandomTestUtil.nextLong());

		layoutFriendlyURL.setUserName(RandomTestUtil.randomString());

		layoutFriendlyURL.setCreateDate(RandomTestUtil.nextDate());

		layoutFriendlyURL.setModifiedDate(RandomTestUtil.nextDate());

		layoutFriendlyURL.setPlid(RandomTestUtil.nextLong());

		layoutFriendlyURL.setPrivateLayout(RandomTestUtil.randomBoolean());

		layoutFriendlyURL.setFriendlyURL(RandomTestUtil.randomString());

		layoutFriendlyURL.setLanguageId(RandomTestUtil.randomString());

		_persistence.update(layoutFriendlyURL);

		return layoutFriendlyURL;
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutFriendlyURLPersistenceTest.class);
	private ModelListener<LayoutFriendlyURL>[] _modelListeners;
	private LayoutFriendlyURLPersistence _persistence = (LayoutFriendlyURLPersistence)PortalBeanLocatorUtil.locate(LayoutFriendlyURLPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}