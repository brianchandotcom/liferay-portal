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

package com.liferay.journal.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.exception.NoSuchArticleLocalizationException;
import com.liferay.journal.model.JournalArticleLocalization;
import com.liferay.journal.service.persistence.JournalArticleLocalizationPersistence;
import com.liferay.journal.service.persistence.JournalArticleLocalizationUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class JournalArticleLocalizationPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.journal.service"));

	@Before
	public void setUp() {
		_persistence = JournalArticleLocalizationUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<JournalArticleLocalization> iterator =
			_journalArticleLocalizations.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		JournalArticleLocalization journalArticleLocalization =
			_persistence.create(pk);

		Assert.assertNotNull(journalArticleLocalization);

		Assert.assertEquals(journalArticleLocalization.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		JournalArticleLocalization newJournalArticleLocalization =
			addJournalArticleLocalization();

		_persistence.remove(newJournalArticleLocalization);

		JournalArticleLocalization existingJournalArticleLocalization =
			_persistence.fetchByPrimaryKey(
				newJournalArticleLocalization.getPrimaryKey());

		Assert.assertNull(existingJournalArticleLocalization);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addJournalArticleLocalization();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		JournalArticleLocalization newJournalArticleLocalization =
			_persistence.create(pk);

		newJournalArticleLocalization.setMvccVersion(RandomTestUtil.nextLong());

		newJournalArticleLocalization.setCompanyId(RandomTestUtil.nextLong());

		newJournalArticleLocalization.setArticlePK(RandomTestUtil.nextLong());

		newJournalArticleLocalization.setTitle(RandomTestUtil.randomString());

		newJournalArticleLocalization.setDescription(
			RandomTestUtil.randomString());

		newJournalArticleLocalization.setLanguageId(
			RandomTestUtil.randomString());

		_journalArticleLocalizations.add(
			_persistence.update(newJournalArticleLocalization));

		JournalArticleLocalization existingJournalArticleLocalization =
			_persistence.findByPrimaryKey(
				newJournalArticleLocalization.getPrimaryKey());

		Assert.assertEquals(
			existingJournalArticleLocalization.getMvccVersion(),
			newJournalArticleLocalization.getMvccVersion());
		Assert.assertEquals(
			existingJournalArticleLocalization.getArticleLocalizationId(),
			newJournalArticleLocalization.getArticleLocalizationId());
		Assert.assertEquals(
			existingJournalArticleLocalization.getCompanyId(),
			newJournalArticleLocalization.getCompanyId());
		Assert.assertEquals(
			existingJournalArticleLocalization.getArticlePK(),
			newJournalArticleLocalization.getArticlePK());
		Assert.assertEquals(
			existingJournalArticleLocalization.getTitle(),
			newJournalArticleLocalization.getTitle());
		Assert.assertEquals(
			existingJournalArticleLocalization.getDescription(),
			newJournalArticleLocalization.getDescription());
		Assert.assertEquals(
			existingJournalArticleLocalization.getLanguageId(),
			newJournalArticleLocalization.getLanguageId());
	}

	@Test
	public void testCountByArticlePK() throws Exception {
		_persistence.countByArticlePK(RandomTestUtil.nextLong());

		_persistence.countByArticlePK(0L);
	}

	@Test
	public void testCountByA_L() throws Exception {
		_persistence.countByA_L(RandomTestUtil.nextLong(), "");

		_persistence.countByA_L(0L, "null");

		_persistence.countByA_L(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalArticleLocalization newJournalArticleLocalization =
			addJournalArticleLocalization();

		JournalArticleLocalization existingJournalArticleLocalization =
			_persistence.findByPrimaryKey(
				newJournalArticleLocalization.getPrimaryKey());

		Assert.assertEquals(
			existingJournalArticleLocalization, newJournalArticleLocalization);
	}

	@Test(expected = NoSuchArticleLocalizationException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<JournalArticleLocalization>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"JournalArticleLocalization", "mvccVersion", true,
			"articleLocalizationId", true, "companyId", true, "articlePK", true,
			"title", true, "description", true, "languageId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		JournalArticleLocalization newJournalArticleLocalization =
			addJournalArticleLocalization();

		JournalArticleLocalization existingJournalArticleLocalization =
			_persistence.fetchByPrimaryKey(
				newJournalArticleLocalization.getPrimaryKey());

		Assert.assertEquals(
			existingJournalArticleLocalization, newJournalArticleLocalization);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		JournalArticleLocalization missingJournalArticleLocalization =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingJournalArticleLocalization);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		JournalArticleLocalization newJournalArticleLocalization1 =
			addJournalArticleLocalization();
		JournalArticleLocalization newJournalArticleLocalization2 =
			addJournalArticleLocalization();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newJournalArticleLocalization1.getPrimaryKey());
		primaryKeys.add(newJournalArticleLocalization2.getPrimaryKey());

		Map<Serializable, JournalArticleLocalization>
			journalArticleLocalizations = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, journalArticleLocalizations.size());
		Assert.assertEquals(
			newJournalArticleLocalization1,
			journalArticleLocalizations.get(
				newJournalArticleLocalization1.getPrimaryKey()));
		Assert.assertEquals(
			newJournalArticleLocalization2,
			journalArticleLocalizations.get(
				newJournalArticleLocalization2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, JournalArticleLocalization>
			journalArticleLocalizations = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(journalArticleLocalizations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		JournalArticleLocalization newJournalArticleLocalization =
			addJournalArticleLocalization();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newJournalArticleLocalization.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, JournalArticleLocalization>
			journalArticleLocalizations = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, journalArticleLocalizations.size());
		Assert.assertEquals(
			newJournalArticleLocalization,
			journalArticleLocalizations.get(
				newJournalArticleLocalization.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, JournalArticleLocalization>
			journalArticleLocalizations = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(journalArticleLocalizations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		JournalArticleLocalization newJournalArticleLocalization =
			addJournalArticleLocalization();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newJournalArticleLocalization.getPrimaryKey());

		Map<Serializable, JournalArticleLocalization>
			journalArticleLocalizations = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, journalArticleLocalizations.size());
		Assert.assertEquals(
			newJournalArticleLocalization,
			journalArticleLocalizations.get(
				newJournalArticleLocalization.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		JournalArticleLocalization newJournalArticleLocalization =
			addJournalArticleLocalization();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			JournalArticleLocalization.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"articleLocalizationId",
				newJournalArticleLocalization.getArticleLocalizationId()));

		List<JournalArticleLocalization> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		JournalArticleLocalization existingJournalArticleLocalization =
			result.get(0);

		Assert.assertEquals(
			existingJournalArticleLocalization, newJournalArticleLocalization);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			JournalArticleLocalization.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"articleLocalizationId", RandomTestUtil.nextLong()));

		List<JournalArticleLocalization> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		JournalArticleLocalization newJournalArticleLocalization =
			addJournalArticleLocalization();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			JournalArticleLocalization.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("articleLocalizationId"));

		Object newArticleLocalizationId =
			newJournalArticleLocalization.getArticleLocalizationId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"articleLocalizationId",
				new Object[] {newArticleLocalizationId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingArticleLocalizationId = result.get(0);

		Assert.assertEquals(
			existingArticleLocalizationId, newArticleLocalizationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			JournalArticleLocalization.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("articleLocalizationId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"articleLocalizationId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		JournalArticleLocalization newJournalArticleLocalization =
			addJournalArticleLocalization();

		_persistence.clearCache();

		JournalArticleLocalization existingJournalArticleLocalization =
			_persistence.findByPrimaryKey(
				newJournalArticleLocalization.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(existingJournalArticleLocalization.getArticlePK()),
			ReflectionTestUtil.<Long>invoke(
				existingJournalArticleLocalization, "getOriginalArticlePK",
				new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingJournalArticleLocalization.getLanguageId(),
				ReflectionTestUtil.invoke(
					existingJournalArticleLocalization, "getOriginalLanguageId",
					new Class<?>[0])));
	}

	protected JournalArticleLocalization addJournalArticleLocalization()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		JournalArticleLocalization journalArticleLocalization =
			_persistence.create(pk);

		journalArticleLocalization.setMvccVersion(RandomTestUtil.nextLong());

		journalArticleLocalization.setCompanyId(RandomTestUtil.nextLong());

		journalArticleLocalization.setArticlePK(RandomTestUtil.nextLong());

		journalArticleLocalization.setTitle(RandomTestUtil.randomString());

		journalArticleLocalization.setDescription(
			RandomTestUtil.randomString());

		journalArticleLocalization.setLanguageId(RandomTestUtil.randomString());

		_journalArticleLocalizations.add(
			_persistence.update(journalArticleLocalization));

		return journalArticleLocalization;
	}

	private List<JournalArticleLocalization> _journalArticleLocalizations =
		new ArrayList<JournalArticleLocalization>();
	private JournalArticleLocalizationPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}