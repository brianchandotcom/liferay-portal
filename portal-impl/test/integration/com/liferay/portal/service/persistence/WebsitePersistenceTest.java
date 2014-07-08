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

import com.liferay.portal.NoSuchWebsiteException;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.WebsiteLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
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
public class WebsitePersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<Website> modelListener : _modelListeners) {
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

		for (ModelListener<Website> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Website website = _persistence.create(pk);

		Assert.assertNotNull(website);

		Assert.assertEquals(website.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Website newWebsite = addWebsite();

		_persistence.remove(newWebsite);

		Website existingWebsite = _persistence.fetchByPrimaryKey(newWebsite.getPrimaryKey());

		Assert.assertNull(existingWebsite);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWebsite();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Website newWebsite = _persistence.create(pk);

		newWebsite.setMvccVersion(RandomTestUtil.nextLong());

		newWebsite.setUuid(RandomTestUtil.randomString());

		newWebsite.setCompanyId(RandomTestUtil.nextLong());

		newWebsite.setUserId(RandomTestUtil.nextLong());

		newWebsite.setUserName(RandomTestUtil.randomString());

		newWebsite.setCreateDate(RandomTestUtil.nextDate());

		newWebsite.setModifiedDate(RandomTestUtil.nextDate());

		newWebsite.setClassNameId(RandomTestUtil.nextLong());

		newWebsite.setClassPK(RandomTestUtil.nextLong());

		newWebsite.setUrl(RandomTestUtil.randomString());

		newWebsite.setTypeId(RandomTestUtil.nextInt());

		newWebsite.setPrimary(RandomTestUtil.randomBoolean());

		_persistence.update(newWebsite);

		Website existingWebsite = _persistence.findByPrimaryKey(newWebsite.getPrimaryKey());

		Assert.assertEquals(existingWebsite.getMvccVersion(),
			newWebsite.getMvccVersion());
		Assert.assertEquals(existingWebsite.getUuid(), newWebsite.getUuid());
		Assert.assertEquals(existingWebsite.getWebsiteId(),
			newWebsite.getWebsiteId());
		Assert.assertEquals(existingWebsite.getCompanyId(),
			newWebsite.getCompanyId());
		Assert.assertEquals(existingWebsite.getUserId(), newWebsite.getUserId());
		Assert.assertEquals(existingWebsite.getUserName(),
			newWebsite.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWebsite.getCreateDate()),
			Time.getShortTimestamp(newWebsite.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingWebsite.getModifiedDate()),
			Time.getShortTimestamp(newWebsite.getModifiedDate()));
		Assert.assertEquals(existingWebsite.getClassNameId(),
			newWebsite.getClassNameId());
		Assert.assertEquals(existingWebsite.getClassPK(),
			newWebsite.getClassPK());
		Assert.assertEquals(existingWebsite.getUrl(), newWebsite.getUrl());
		Assert.assertEquals(existingWebsite.getTypeId(), newWebsite.getTypeId());
		Assert.assertEquals(existingWebsite.getPrimary(),
			newWebsite.getPrimary());
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
	public void testCountByUserId() {
		try {
			_persistence.countByUserId(RandomTestUtil.nextLong());

			_persistence.countByUserId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
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
	public void testCountByC_C_C() {
		try {
			_persistence.countByC_C_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

			_persistence.countByC_C_C(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_C_C_P() {
		try {
			_persistence.countByC_C_C_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean());

			_persistence.countByC_C_C_P(0L, 0L, 0L,
				RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Website newWebsite = addWebsite();

		Website existingWebsite = _persistence.findByPrimaryKey(newWebsite.getPrimaryKey());

		Assert.assertEquals(existingWebsite, newWebsite);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchWebsiteException");
		}
		catch (NoSuchWebsiteException nsee) {
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
		return OrderByComparatorFactoryUtil.create("Website", "mvccVersion",
			true, "uuid", true, "websiteId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "url", true, "typeId", true,
			"primary", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Website newWebsite = addWebsite();

		Website existingWebsite = _persistence.fetchByPrimaryKey(newWebsite.getPrimaryKey());

		Assert.assertEquals(existingWebsite, newWebsite);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Website missingWebsite = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWebsite);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Website newWebsite1 = addWebsite();
		Website newWebsite2 = addWebsite();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWebsite1.getPrimaryKey());
		primaryKeys.add(newWebsite2.getPrimaryKey());

		Map<Serializable, Website> websites = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, websites.size());
		Assert.assertEquals(newWebsite1,
			websites.get(newWebsite1.getPrimaryKey()));
		Assert.assertEquals(newWebsite2,
			websites.get(newWebsite2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Website> websites = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(websites.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Website newWebsite = addWebsite();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWebsite.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Website> websites = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, websites.size());
		Assert.assertEquals(newWebsite, websites.get(newWebsite.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Website> websites = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(websites.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Website newWebsite = addWebsite();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWebsite.getPrimaryKey());

		Map<Serializable, Website> websites = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, websites.size());
		Assert.assertEquals(newWebsite, websites.get(newWebsite.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = WebsiteLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					Website website = (Website)object;

					Assert.assertNotNull(website);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Website newWebsite = addWebsite();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Website.class,
				Website.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("websiteId",
				newWebsite.getWebsiteId()));

		List<Website> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Website existingWebsite = result.get(0);

		Assert.assertEquals(existingWebsite, newWebsite);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Website.class,
				Website.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("websiteId",
				RandomTestUtil.nextLong()));

		List<Website> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Website newWebsite = addWebsite();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Website.class,
				Website.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("websiteId"));

		Object newWebsiteId = newWebsite.getWebsiteId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("websiteId",
				new Object[] { newWebsiteId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingWebsiteId = result.get(0);

		Assert.assertEquals(existingWebsiteId, newWebsiteId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Website.class,
				Website.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("websiteId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("websiteId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Website addWebsite() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Website website = _persistence.create(pk);

		website.setMvccVersion(RandomTestUtil.nextLong());

		website.setUuid(RandomTestUtil.randomString());

		website.setCompanyId(RandomTestUtil.nextLong());

		website.setUserId(RandomTestUtil.nextLong());

		website.setUserName(RandomTestUtil.randomString());

		website.setCreateDate(RandomTestUtil.nextDate());

		website.setModifiedDate(RandomTestUtil.nextDate());

		website.setClassNameId(RandomTestUtil.nextLong());

		website.setClassPK(RandomTestUtil.nextLong());

		website.setUrl(RandomTestUtil.randomString());

		website.setTypeId(RandomTestUtil.nextInt());

		website.setPrimary(RandomTestUtil.randomBoolean());

		_persistence.update(website);

		return website;
	}

	private static Log _log = LogFactoryUtil.getLog(WebsitePersistenceTest.class);
	private ModelListener<Website>[] _modelListeners;
	private WebsitePersistence _persistence = (WebsitePersistence)PortalBeanLocatorUtil.locate(WebsitePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}