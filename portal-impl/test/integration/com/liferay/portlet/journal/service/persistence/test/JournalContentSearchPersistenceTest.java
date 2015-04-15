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

package com.liferay.portlet.journal.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.journal.NoSuchContentSearchException;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence;
import com.liferay.portlet.journal.service.persistence.JournalContentSearchUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class JournalContentSearchPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = JournalContentSearchUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<JournalContentSearch> iterator = _journalContentSearchs.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

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
		long pk = RandomTestUtil.nextLong();

		JournalContentSearch newJournalContentSearch = _persistence.create(pk);

		newJournalContentSearch.setGroupId(RandomTestUtil.nextLong());

		newJournalContentSearch.setCompanyId(RandomTestUtil.nextLong());

		newJournalContentSearch.setLayoutId(RandomTestUtil.nextLong());

		newJournalContentSearch.setPortletId(RandomTestUtil.randomString());

		newJournalContentSearch.setArticleId(RandomTestUtil.randomString());

		_journalContentSearchs.add(_persistence.update(newJournalContentSearch));

		JournalContentSearch existingJournalContentSearch = _persistence.findByPrimaryKey(newJournalContentSearch.getPrimaryKey());

		Assert.assertEquals(existingJournalContentSearch.getContentSearchId(),
			newJournalContentSearch.getContentSearchId());
		Assert.assertEquals(existingJournalContentSearch.getGroupId(),
			newJournalContentSearch.getGroupId());
		Assert.assertEquals(existingJournalContentSearch.getCompanyId(),
			newJournalContentSearch.getCompanyId());
		Assert.assertEquals(existingJournalContentSearch.getLayoutId(),
			newJournalContentSearch.getLayoutId());
		Assert.assertEquals(existingJournalContentSearch.getPortletId(),
			newJournalContentSearch.getPortletId());
		Assert.assertEquals(existingJournalContentSearch.getArticleId(),
			newJournalContentSearch.getArticleId());
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
	public void testCountByG_L() {
		try {
			_persistence.countByG_L(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByG_L(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_A() {
		try {
			_persistence.countByG_A(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_A(0L, StringPool.NULL);

			_persistence.countByG_A(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_L_P() {
		try {
			_persistence.countByG_L_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_L_P(0L, 0L, StringPool.NULL);

			_persistence.countByG_L_P(0L, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_L_P_A() {
		try {
			_persistence.countByG_L_P_A(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), StringPool.BLANK, StringPool.BLANK);

			_persistence.countByG_L_P_A(0L, 0L, StringPool.NULL, StringPool.NULL);

			_persistence.countByG_L_P_A(0L, 0L, (String)null, (String)null);
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
		long pk = RandomTestUtil.nextLong();

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

	protected OrderByComparator<JournalContentSearch> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("JournalContentSearch",
			"contentSearchId", true, "groupId", true, "companyId", true,
			"layoutId", true, "portletId", true, "articleId", true);
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
		long pk = RandomTestUtil.nextLong();

		JournalContentSearch missingJournalContentSearch = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingJournalContentSearch);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		JournalContentSearch newJournalContentSearch1 = addJournalContentSearch();
		JournalContentSearch newJournalContentSearch2 = addJournalContentSearch();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newJournalContentSearch1.getPrimaryKey());
		primaryKeys.add(newJournalContentSearch2.getPrimaryKey());

		Map<Serializable, JournalContentSearch> journalContentSearchs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, journalContentSearchs.size());
		Assert.assertEquals(newJournalContentSearch1,
			journalContentSearchs.get(newJournalContentSearch1.getPrimaryKey()));
		Assert.assertEquals(newJournalContentSearch2,
			journalContentSearchs.get(newJournalContentSearch2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, JournalContentSearch> journalContentSearchs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(journalContentSearchs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		JournalContentSearch newJournalContentSearch = addJournalContentSearch();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newJournalContentSearch.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, JournalContentSearch> journalContentSearchs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, journalContentSearchs.size());
		Assert.assertEquals(newJournalContentSearch,
			journalContentSearchs.get(newJournalContentSearch.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, JournalContentSearch> journalContentSearchs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(journalContentSearchs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		JournalContentSearch newJournalContentSearch = addJournalContentSearch();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newJournalContentSearch.getPrimaryKey());

		Map<Serializable, JournalContentSearch> journalContentSearchs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, journalContentSearchs.size());
		Assert.assertEquals(newJournalContentSearch,
			journalContentSearchs.get(newJournalContentSearch.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = JournalContentSearchLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					JournalContentSearch journalContentSearch = (JournalContentSearch)object;

					Assert.assertNotNull(journalContentSearch);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalContentSearch newJournalContentSearch = addJournalContentSearch();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalContentSearch.class,
				_dynamicQueryClassLoader);

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
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contentSearchId",
				RandomTestUtil.nextLong()));

		List<JournalContentSearch> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		JournalContentSearch newJournalContentSearch = addJournalContentSearch();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalContentSearch.class,
				_dynamicQueryClassLoader);

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
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"contentSearchId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("contentSearchId",
				new Object[] { RandomTestUtil.nextLong() }));

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

		JournalContentSearch existingJournalContentSearch = _persistence.findByPrimaryKey(newJournalContentSearch.getPrimaryKey());

		Assert.assertEquals(existingJournalContentSearch.getGroupId(),
			ReflectionTestUtil.invoke(existingJournalContentSearch,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(existingJournalContentSearch.getLayoutId(),
			ReflectionTestUtil.invoke(existingJournalContentSearch,
				"getOriginalLayoutId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(
				existingJournalContentSearch.getPortletId(),
				ReflectionTestUtil.invoke(existingJournalContentSearch,
					"getOriginalPortletId", new Class<?>[0])));
		Assert.assertTrue(Validator.equals(
				existingJournalContentSearch.getArticleId(),
				ReflectionTestUtil.invoke(existingJournalContentSearch,
					"getOriginalArticleId", new Class<?>[0])));
	}

	protected JournalContentSearch addJournalContentSearch()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		JournalContentSearch journalContentSearch = _persistence.create(pk);

		journalContentSearch.setGroupId(RandomTestUtil.nextLong());

		journalContentSearch.setCompanyId(RandomTestUtil.nextLong());

		journalContentSearch.setLayoutId(RandomTestUtil.nextLong());

		journalContentSearch.setPortletId(RandomTestUtil.randomString());

		journalContentSearch.setArticleId(RandomTestUtil.randomString());

		_journalContentSearchs.add(_persistence.update(journalContentSearch));

		return journalContentSearch;
	}

	private List<JournalContentSearch> _journalContentSearchs = new ArrayList<JournalContentSearch>();
	private JournalContentSearchPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}