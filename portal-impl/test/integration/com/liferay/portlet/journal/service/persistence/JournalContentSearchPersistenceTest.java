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

package com.liferay.portlet.journal.service.persistence;

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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.journal.NoSuchContentSearchException;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.model.impl.JournalContentSearchModelImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class JournalContentSearchPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<JournalContentSearch> modelListener : _modelListeners) {
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

		for (ModelListener<JournalContentSearch> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalContentSearch journalContentSearch = _persistence.create(pk);

		Assert.assertNotNull(journalContentSearch);

		Assert.assertEquals(journalContentSearch.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		JournalContentSearch newJournalContentSearch = addJournalContentSearch();

		_persistence.remove(newJournalContentSearch);

		JournalContentSearch existingJournalContentSearch = _persistence.fetchByPrimaryKey(newJournalContentSearch.getPrimaryKey());

		Assert.assertNull(existingJournalContentSearch);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addJournalContentSearch();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalContentSearch newJournalContentSearch = _persistence.create(pk);

		newJournalContentSearch.setGroupId(ServiceTestUtil.nextLong());

		newJournalContentSearch.setCompanyId(ServiceTestUtil.nextLong());

		newJournalContentSearch.setPrivateLayout(ServiceTestUtil.randomBoolean());

		newJournalContentSearch.setLayoutId(ServiceTestUtil.nextLong());

		newJournalContentSearch.setPortletId(ServiceTestUtil.randomString());

		newJournalContentSearch.setArticleId(ServiceTestUtil.randomString());

		_persistence.update(newJournalContentSearch);

		JournalContentSearch existingJournalContentSearch = _persistence.findByPrimaryKey(newJournalContentSearch.getPrimaryKey());

		Assert.assertEquals(existingJournalContentSearch.getContentSearchId(),
			newJournalContentSearch.getContentSearchId());
		Assert.assertEquals(existingJournalContentSearch.getGroupId(),
			newJournalContentSearch.getGroupId());
		Assert.assertEquals(existingJournalContentSearch.getCompanyId(),
			newJournalContentSearch.getCompanyId());
		Assert.assertEquals(existingJournalContentSearch.getPrivateLayout(),
			newJournalContentSearch.getPrivateLayout());
		Assert.assertEquals(existingJournalContentSearch.getLayoutId(),
			newJournalContentSearch.getLayoutId());
		Assert.assertEquals(existingJournalContentSearch.getPortletId(),
			newJournalContentSearch.getPortletId());
		Assert.assertEquals(existingJournalContentSearch.getArticleId(),
			newJournalContentSearch.getArticleId());
	}

	@Test
	public void testCountByPortletId() {
		try {
			_persistence.countByPortletId(StringPool.BLANK);

			_persistence.countByPortletId(StringPool.NULL);

			_persistence.countByPortletId((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByArticleId() {
		try {
			_persistence.countByArticleId(StringPool.BLANK);

			_persistence.countByArticleId(StringPool.NULL);

			_persistence.countByArticleId((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P() {
		try {
			_persistence.countByG_P(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean());

			_persistence.countByG_P(0L, ServiceTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_A() {
		try {
			_persistence.countByG_A(ServiceTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_A(0L, StringPool.NULL);

			_persistence.countByG_A(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P_L() {
		try {
			_persistence.countByG_P_L(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean(), ServiceTestUtil.nextLong());

			_persistence.countByG_P_L(0L, ServiceTestUtil.randomBoolean(), 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P_A() {
		try {
			_persistence.countByG_P_A(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean(), StringPool.BLANK);

			_persistence.countByG_P_A(0L, ServiceTestUtil.randomBoolean(),
				StringPool.NULL);

			_persistence.countByG_P_A(0L, ServiceTestUtil.randomBoolean(),
				(String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P_L_P() {
		try {
			_persistence.countByG_P_L_P(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean(), ServiceTestUtil.nextLong(),
				StringPool.BLANK);

			_persistence.countByG_P_L_P(0L, ServiceTestUtil.randomBoolean(),
				0L, StringPool.NULL);

			_persistence.countByG_P_L_P(0L, ServiceTestUtil.randomBoolean(),
				0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P_L_P_A() {
		try {
			_persistence.countByG_P_L_P_A(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean(), ServiceTestUtil.nextLong(),
				StringPool.BLANK, StringPool.BLANK);

			_persistence.countByG_P_L_P_A(0L, ServiceTestUtil.randomBoolean(),
				0L, StringPool.NULL, StringPool.NULL);

			_persistence.countByG_P_L_P_A(0L, ServiceTestUtil.randomBoolean(),
				0L, (String)null, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalContentSearch newJournalContentSearch = addJournalContentSearch();

		JournalContentSearch existingJournalContentSearch = _persistence.findByPrimaryKey(newJournalContentSearch.getPrimaryKey());

		Assert.assertEquals(existingJournalContentSearch,
			newJournalContentSearch);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchContentSearchException");
		}
		catch (NoSuchContentSearchException nsee) {
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
		return OrderByComparatorFactoryUtil.create("JournalContentSearch",
			"contentSearchId", true, "groupId", true, "companyId", true,
			"privateLayout", true, "layoutId", true, "portletId", true,
			"articleId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		JournalContentSearch newJournalContentSearch = addJournalContentSearch();

		JournalContentSearch existingJournalContentSearch = _persistence.fetchByPrimaryKey(newJournalContentSearch.getPrimaryKey());

		Assert.assertEquals(existingJournalContentSearch,
			newJournalContentSearch);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalContentSearch missingJournalContentSearch = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingJournalContentSearch);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new JournalContentSearchActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					JournalContentSearch journalContentSearch = (JournalContentSearch)object;

					Assert.assertNotNull(journalContentSearch);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalContentSearch newJournalContentSearch = addJournalContentSearch();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalContentSearch.class,
				JournalContentSearch.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentSearchId",
				newJournalContentSearch.getContentSearchId()));

		List<JournalContentSearch> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		JournalContentSearch existingJournalContentSearch = result.get(0);

		Assert.assertEquals(existingJournalContentSearch,
			newJournalContentSearch);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalContentSearch.class,
				JournalContentSearch.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentSearchId",
				ServiceTestUtil.nextLong()));

		List<JournalContentSearch> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		JournalContentSearch newJournalContentSearch = addJournalContentSearch();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalContentSearch.class,
				JournalContentSearch.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"contentSearchId"));

		Object newContentSearchId = newJournalContentSearch.getContentSearchId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("contentSearchId",
				new Object[] { newContentSearchId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingContentSearchId = result.get(0);

		Assert.assertEquals(existingContentSearchId, newContentSearchId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalContentSearch.class,
				JournalContentSearch.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"contentSearchId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("contentSearchId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		JournalContentSearch newJournalContentSearch = addJournalContentSearch();

		_persistence.clearCache();

		JournalContentSearchModelImpl existingJournalContentSearchModelImpl = (JournalContentSearchModelImpl)_persistence.findByPrimaryKey(newJournalContentSearch.getPrimaryKey());

		Assert.assertEquals(existingJournalContentSearchModelImpl.getGroupId(),
			existingJournalContentSearchModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingJournalContentSearchModelImpl.getPrivateLayout(),
			existingJournalContentSearchModelImpl.getOriginalPrivateLayout());
		Assert.assertEquals(existingJournalContentSearchModelImpl.getLayoutId(),
			existingJournalContentSearchModelImpl.getOriginalLayoutId());
		Assert.assertTrue(Validator.equals(
				existingJournalContentSearchModelImpl.getPortletId(),
				existingJournalContentSearchModelImpl.getOriginalPortletId()));
		Assert.assertTrue(Validator.equals(
				existingJournalContentSearchModelImpl.getArticleId(),
				existingJournalContentSearchModelImpl.getOriginalArticleId()));
	}

	protected JournalContentSearch addJournalContentSearch()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		JournalContentSearch journalContentSearch = _persistence.create(pk);

		journalContentSearch.setGroupId(ServiceTestUtil.nextLong());

		journalContentSearch.setCompanyId(ServiceTestUtil.nextLong());

		journalContentSearch.setPrivateLayout(ServiceTestUtil.randomBoolean());

		journalContentSearch.setLayoutId(ServiceTestUtil.nextLong());

		journalContentSearch.setPortletId(ServiceTestUtil.randomString());

		journalContentSearch.setArticleId(ServiceTestUtil.randomString());

		_persistence.update(journalContentSearch);

		return journalContentSearch;
	}

	private static Log _log = LogFactoryUtil.getLog(JournalContentSearchPersistenceTest.class);
	private ModelListener<JournalContentSearch>[] _modelListeners;
	private JournalContentSearchPersistence _persistence = (JournalContentSearchPersistence)PortalBeanLocatorUtil.locate(JournalContentSearchPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}