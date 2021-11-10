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

package com.liferay.remote.app.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.remote.app.exception.NoSuchRemoteAppEntryLocalizationException;
import com.liferay.remote.app.model.RemoteAppEntryLocalization;
import com.liferay.remote.app.service.persistence.RemoteAppEntryLocalizationPersistence;
import com.liferay.remote.app.service.persistence.RemoteAppEntryLocalizationUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
public class RemoteAppEntryLocalizationPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.remote.app.service"));

	@Before
	public void setUp() {
		_persistence = RemoteAppEntryLocalizationUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<RemoteAppEntryLocalization> iterator =
			_remoteAppEntryLocalizations.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RemoteAppEntryLocalization remoteAppEntryLocalization =
			_persistence.create(pk);

		Assert.assertNotNull(remoteAppEntryLocalization);

		Assert.assertEquals(remoteAppEntryLocalization.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		RemoteAppEntryLocalization newRemoteAppEntryLocalization =
			addRemoteAppEntryLocalization();

		_persistence.remove(newRemoteAppEntryLocalization);

		RemoteAppEntryLocalization existingRemoteAppEntryLocalization =
			_persistence.fetchByPrimaryKey(
				newRemoteAppEntryLocalization.getPrimaryKey());

		Assert.assertNull(existingRemoteAppEntryLocalization);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addRemoteAppEntryLocalization();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RemoteAppEntryLocalization newRemoteAppEntryLocalization =
			_persistence.create(pk);

		newRemoteAppEntryLocalization.setMvccVersion(RandomTestUtil.nextLong());

		newRemoteAppEntryLocalization.setCompanyId(RandomTestUtil.nextLong());

		newRemoteAppEntryLocalization.setRemoteAppEntryId(
			RandomTestUtil.nextLong());

		newRemoteAppEntryLocalization.setLanguageId(
			RandomTestUtil.randomString());

		newRemoteAppEntryLocalization.setDescription(
			RandomTestUtil.randomString());

		newRemoteAppEntryLocalization.setName(RandomTestUtil.randomString());

		_remoteAppEntryLocalizations.add(
			_persistence.update(newRemoteAppEntryLocalization));

		RemoteAppEntryLocalization existingRemoteAppEntryLocalization =
			_persistence.findByPrimaryKey(
				newRemoteAppEntryLocalization.getPrimaryKey());

		Assert.assertEquals(
			existingRemoteAppEntryLocalization.getMvccVersion(),
			newRemoteAppEntryLocalization.getMvccVersion());
		Assert.assertEquals(
			existingRemoteAppEntryLocalization.
				getRemoteAppEntryLocalizationId(),
			newRemoteAppEntryLocalization.getRemoteAppEntryLocalizationId());
		Assert.assertEquals(
			existingRemoteAppEntryLocalization.getCompanyId(),
			newRemoteAppEntryLocalization.getCompanyId());
		Assert.assertEquals(
			existingRemoteAppEntryLocalization.getRemoteAppEntryId(),
			newRemoteAppEntryLocalization.getRemoteAppEntryId());
		Assert.assertEquals(
			existingRemoteAppEntryLocalization.getLanguageId(),
			newRemoteAppEntryLocalization.getLanguageId());
		Assert.assertEquals(
			existingRemoteAppEntryLocalization.getDescription(),
			newRemoteAppEntryLocalization.getDescription());
		Assert.assertEquals(
			existingRemoteAppEntryLocalization.getName(),
			newRemoteAppEntryLocalization.getName());
	}

	@Test
	public void testCountByRemoteAppEntryId() throws Exception {
		_persistence.countByRemoteAppEntryId(RandomTestUtil.nextLong());

		_persistence.countByRemoteAppEntryId(0L);
	}

	@Test
	public void testCountByRemoteAppEntryId_LanguageId() throws Exception {
		_persistence.countByRemoteAppEntryId_LanguageId(
			RandomTestUtil.nextLong(), "");

		_persistence.countByRemoteAppEntryId_LanguageId(0L, "null");

		_persistence.countByRemoteAppEntryId_LanguageId(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		RemoteAppEntryLocalization newRemoteAppEntryLocalization =
			addRemoteAppEntryLocalization();

		RemoteAppEntryLocalization existingRemoteAppEntryLocalization =
			_persistence.findByPrimaryKey(
				newRemoteAppEntryLocalization.getPrimaryKey());

		Assert.assertEquals(
			existingRemoteAppEntryLocalization, newRemoteAppEntryLocalization);
	}

	@Test(expected = NoSuchRemoteAppEntryLocalizationException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<RemoteAppEntryLocalization>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"RemoteAppEntryLocalization", "mvccVersion", true,
			"remoteAppEntryLocalizationId", true, "companyId", true,
			"remoteAppEntryId", true, "languageId", true, "name", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		RemoteAppEntryLocalization newRemoteAppEntryLocalization =
			addRemoteAppEntryLocalization();

		RemoteAppEntryLocalization existingRemoteAppEntryLocalization =
			_persistence.fetchByPrimaryKey(
				newRemoteAppEntryLocalization.getPrimaryKey());

		Assert.assertEquals(
			existingRemoteAppEntryLocalization, newRemoteAppEntryLocalization);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RemoteAppEntryLocalization missingRemoteAppEntryLocalization =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingRemoteAppEntryLocalization);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		RemoteAppEntryLocalization newRemoteAppEntryLocalization1 =
			addRemoteAppEntryLocalization();
		RemoteAppEntryLocalization newRemoteAppEntryLocalization2 =
			addRemoteAppEntryLocalization();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRemoteAppEntryLocalization1.getPrimaryKey());
		primaryKeys.add(newRemoteAppEntryLocalization2.getPrimaryKey());

		Map<Serializable, RemoteAppEntryLocalization>
			remoteAppEntryLocalizations = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, remoteAppEntryLocalizations.size());
		Assert.assertEquals(
			newRemoteAppEntryLocalization1,
			remoteAppEntryLocalizations.get(
				newRemoteAppEntryLocalization1.getPrimaryKey()));
		Assert.assertEquals(
			newRemoteAppEntryLocalization2,
			remoteAppEntryLocalizations.get(
				newRemoteAppEntryLocalization2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, RemoteAppEntryLocalization>
			remoteAppEntryLocalizations = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(remoteAppEntryLocalizations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		RemoteAppEntryLocalization newRemoteAppEntryLocalization =
			addRemoteAppEntryLocalization();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRemoteAppEntryLocalization.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, RemoteAppEntryLocalization>
			remoteAppEntryLocalizations = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, remoteAppEntryLocalizations.size());
		Assert.assertEquals(
			newRemoteAppEntryLocalization,
			remoteAppEntryLocalizations.get(
				newRemoteAppEntryLocalization.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, RemoteAppEntryLocalization>
			remoteAppEntryLocalizations = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(remoteAppEntryLocalizations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		RemoteAppEntryLocalization newRemoteAppEntryLocalization =
			addRemoteAppEntryLocalization();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRemoteAppEntryLocalization.getPrimaryKey());

		Map<Serializable, RemoteAppEntryLocalization>
			remoteAppEntryLocalizations = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, remoteAppEntryLocalizations.size());
		Assert.assertEquals(
			newRemoteAppEntryLocalization,
			remoteAppEntryLocalizations.get(
				newRemoteAppEntryLocalization.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		RemoteAppEntryLocalization newRemoteAppEntryLocalization =
			addRemoteAppEntryLocalization();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RemoteAppEntryLocalization.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"remoteAppEntryLocalizationId",
				newRemoteAppEntryLocalization.
					getRemoteAppEntryLocalizationId()));

		List<RemoteAppEntryLocalization> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		RemoteAppEntryLocalization existingRemoteAppEntryLocalization =
			result.get(0);

		Assert.assertEquals(
			existingRemoteAppEntryLocalization, newRemoteAppEntryLocalization);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RemoteAppEntryLocalization.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"remoteAppEntryLocalizationId", RandomTestUtil.nextLong()));

		List<RemoteAppEntryLocalization> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		RemoteAppEntryLocalization newRemoteAppEntryLocalization =
			addRemoteAppEntryLocalization();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RemoteAppEntryLocalization.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("remoteAppEntryLocalizationId"));

		Object newRemoteAppEntryLocalizationId =
			newRemoteAppEntryLocalization.getRemoteAppEntryLocalizationId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"remoteAppEntryLocalizationId",
				new Object[] {newRemoteAppEntryLocalizationId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRemoteAppEntryLocalizationId = result.get(0);

		Assert.assertEquals(
			existingRemoteAppEntryLocalizationId,
			newRemoteAppEntryLocalizationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RemoteAppEntryLocalization.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("remoteAppEntryLocalizationId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"remoteAppEntryLocalizationId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		RemoteAppEntryLocalization newRemoteAppEntryLocalization =
			addRemoteAppEntryLocalization();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newRemoteAppEntryLocalization.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		RemoteAppEntryLocalization newRemoteAppEntryLocalization =
			addRemoteAppEntryLocalization();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			RemoteAppEntryLocalization.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"remoteAppEntryLocalizationId",
				newRemoteAppEntryLocalization.
					getRemoteAppEntryLocalizationId()));

		List<RemoteAppEntryLocalization> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		RemoteAppEntryLocalization remoteAppEntryLocalization) {

		Assert.assertEquals(
			Long.valueOf(remoteAppEntryLocalization.getRemoteAppEntryId()),
			ReflectionTestUtil.<Long>invoke(
				remoteAppEntryLocalization, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "remoteAppEntryId"));
		Assert.assertEquals(
			remoteAppEntryLocalization.getLanguageId(),
			ReflectionTestUtil.invoke(
				remoteAppEntryLocalization, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "languageId"));
	}

	protected RemoteAppEntryLocalization addRemoteAppEntryLocalization()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		RemoteAppEntryLocalization remoteAppEntryLocalization =
			_persistence.create(pk);

		remoteAppEntryLocalization.setMvccVersion(RandomTestUtil.nextLong());

		remoteAppEntryLocalization.setCompanyId(RandomTestUtil.nextLong());

		remoteAppEntryLocalization.setRemoteAppEntryId(
			RandomTestUtil.nextLong());

		remoteAppEntryLocalization.setLanguageId(RandomTestUtil.randomString());

		remoteAppEntryLocalization.setDescription(
			RandomTestUtil.randomString());

		remoteAppEntryLocalization.setName(RandomTestUtil.randomString());

		_remoteAppEntryLocalizations.add(
			_persistence.update(remoteAppEntryLocalization));

		return remoteAppEntryLocalization;
	}

	private List<RemoteAppEntryLocalization> _remoteAppEntryLocalizations =
		new ArrayList<RemoteAppEntryLocalization>();
	private RemoteAppEntryLocalizationPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}