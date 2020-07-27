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

package com.liferay.app.builder.service.persistence.test;

import com.liferay.app.builder.exception.NoSuchAppDataRecordLinkException;
import com.liferay.app.builder.model.AppBuilderAppDataRecordLink;
import com.liferay.app.builder.service.AppBuilderAppDataRecordLinkLocalServiceUtil;
import com.liferay.app.builder.service.persistence.AppBuilderAppDataRecordLinkPersistence;
import com.liferay.app.builder.service.persistence.AppBuilderAppDataRecordLinkUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
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
public class AppBuilderAppDataRecordLinkPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.app.builder.service"));

	@Before
	public void setUp() {
		_persistence = AppBuilderAppDataRecordLinkUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AppBuilderAppDataRecordLink> iterator =
			_appBuilderAppDataRecordLinks.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			_persistence.create(pk);

		Assert.assertNotNull(appBuilderAppDataRecordLink);

		Assert.assertEquals(appBuilderAppDataRecordLink.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AppBuilderAppDataRecordLink newAppBuilderAppDataRecordLink =
			addAppBuilderAppDataRecordLink();

		_persistence.remove(newAppBuilderAppDataRecordLink);

		AppBuilderAppDataRecordLink existingAppBuilderAppDataRecordLink =
			_persistence.fetchByPrimaryKey(
				newAppBuilderAppDataRecordLink.getPrimaryKey());

		Assert.assertNull(existingAppBuilderAppDataRecordLink);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAppBuilderAppDataRecordLink();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AppBuilderAppDataRecordLink newAppBuilderAppDataRecordLink =
			_persistence.create(pk);

		newAppBuilderAppDataRecordLink.setGroupId(RandomTestUtil.nextLong());

		newAppBuilderAppDataRecordLink.setCompanyId(RandomTestUtil.nextLong());

		newAppBuilderAppDataRecordLink.setAppBuilderAppId(
			RandomTestUtil.nextLong());

		newAppBuilderAppDataRecordLink.setAppBuilderAppVersionId(
			RandomTestUtil.nextLong());

		newAppBuilderAppDataRecordLink.setDdlRecordId(
			RandomTestUtil.nextLong());

		_appBuilderAppDataRecordLinks.add(
			_persistence.update(newAppBuilderAppDataRecordLink));

		AppBuilderAppDataRecordLink existingAppBuilderAppDataRecordLink =
			_persistence.findByPrimaryKey(
				newAppBuilderAppDataRecordLink.getPrimaryKey());

		Assert.assertEquals(
			existingAppBuilderAppDataRecordLink.
				getAppBuilderAppDataRecordLinkId(),
			newAppBuilderAppDataRecordLink.getAppBuilderAppDataRecordLinkId());
		Assert.assertEquals(
			existingAppBuilderAppDataRecordLink.getGroupId(),
			newAppBuilderAppDataRecordLink.getGroupId());
		Assert.assertEquals(
			existingAppBuilderAppDataRecordLink.getCompanyId(),
			newAppBuilderAppDataRecordLink.getCompanyId());
		Assert.assertEquals(
			existingAppBuilderAppDataRecordLink.getAppBuilderAppId(),
			newAppBuilderAppDataRecordLink.getAppBuilderAppId());
		Assert.assertEquals(
			existingAppBuilderAppDataRecordLink.getAppBuilderAppVersionId(),
			newAppBuilderAppDataRecordLink.getAppBuilderAppVersionId());
		Assert.assertEquals(
			existingAppBuilderAppDataRecordLink.getDdlRecordId(),
			newAppBuilderAppDataRecordLink.getDdlRecordId());
	}

	@Test
	public void testCountByAppBuilderAppId() throws Exception {
		_persistence.countByAppBuilderAppId(RandomTestUtil.nextLong());

		_persistence.countByAppBuilderAppId(0L);
	}

	@Test
	public void testCountByDDLRecordId() throws Exception {
		_persistence.countByDDLRecordId(RandomTestUtil.nextLong());

		_persistence.countByDDLRecordId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AppBuilderAppDataRecordLink newAppBuilderAppDataRecordLink =
			addAppBuilderAppDataRecordLink();

		AppBuilderAppDataRecordLink existingAppBuilderAppDataRecordLink =
			_persistence.findByPrimaryKey(
				newAppBuilderAppDataRecordLink.getPrimaryKey());

		Assert.assertEquals(
			existingAppBuilderAppDataRecordLink,
			newAppBuilderAppDataRecordLink);
	}

	@Test(expected = NoSuchAppDataRecordLinkException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AppBuilderAppDataRecordLink>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"AppBuilderAppDataRecordLink", "appBuilderAppDataRecordLinkId",
			true, "groupId", true, "companyId", true, "appBuilderAppId", true,
			"appBuilderAppVersionId", true, "ddlRecordId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AppBuilderAppDataRecordLink newAppBuilderAppDataRecordLink =
			addAppBuilderAppDataRecordLink();

		AppBuilderAppDataRecordLink existingAppBuilderAppDataRecordLink =
			_persistence.fetchByPrimaryKey(
				newAppBuilderAppDataRecordLink.getPrimaryKey());

		Assert.assertEquals(
			existingAppBuilderAppDataRecordLink,
			newAppBuilderAppDataRecordLink);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AppBuilderAppDataRecordLink missingAppBuilderAppDataRecordLink =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAppBuilderAppDataRecordLink);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AppBuilderAppDataRecordLink newAppBuilderAppDataRecordLink1 =
			addAppBuilderAppDataRecordLink();
		AppBuilderAppDataRecordLink newAppBuilderAppDataRecordLink2 =
			addAppBuilderAppDataRecordLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAppBuilderAppDataRecordLink1.getPrimaryKey());
		primaryKeys.add(newAppBuilderAppDataRecordLink2.getPrimaryKey());

		Map<Serializable, AppBuilderAppDataRecordLink>
			appBuilderAppDataRecordLinks = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, appBuilderAppDataRecordLinks.size());
		Assert.assertEquals(
			newAppBuilderAppDataRecordLink1,
			appBuilderAppDataRecordLinks.get(
				newAppBuilderAppDataRecordLink1.getPrimaryKey()));
		Assert.assertEquals(
			newAppBuilderAppDataRecordLink2,
			appBuilderAppDataRecordLinks.get(
				newAppBuilderAppDataRecordLink2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AppBuilderAppDataRecordLink>
			appBuilderAppDataRecordLinks = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(appBuilderAppDataRecordLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AppBuilderAppDataRecordLink newAppBuilderAppDataRecordLink =
			addAppBuilderAppDataRecordLink();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAppBuilderAppDataRecordLink.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AppBuilderAppDataRecordLink>
			appBuilderAppDataRecordLinks = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, appBuilderAppDataRecordLinks.size());
		Assert.assertEquals(
			newAppBuilderAppDataRecordLink,
			appBuilderAppDataRecordLinks.get(
				newAppBuilderAppDataRecordLink.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AppBuilderAppDataRecordLink>
			appBuilderAppDataRecordLinks = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(appBuilderAppDataRecordLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AppBuilderAppDataRecordLink newAppBuilderAppDataRecordLink =
			addAppBuilderAppDataRecordLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAppBuilderAppDataRecordLink.getPrimaryKey());

		Map<Serializable, AppBuilderAppDataRecordLink>
			appBuilderAppDataRecordLinks = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, appBuilderAppDataRecordLinks.size());
		Assert.assertEquals(
			newAppBuilderAppDataRecordLink,
			appBuilderAppDataRecordLinks.get(
				newAppBuilderAppDataRecordLink.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AppBuilderAppDataRecordLinkLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<AppBuilderAppDataRecordLink>() {

				@Override
				public void performAction(
					AppBuilderAppDataRecordLink appBuilderAppDataRecordLink) {

					Assert.assertNotNull(appBuilderAppDataRecordLink);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AppBuilderAppDataRecordLink newAppBuilderAppDataRecordLink =
			addAppBuilderAppDataRecordLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderAppDataRecordLink.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"appBuilderAppDataRecordLinkId",
				newAppBuilderAppDataRecordLink.
					getAppBuilderAppDataRecordLinkId()));

		List<AppBuilderAppDataRecordLink> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AppBuilderAppDataRecordLink existingAppBuilderAppDataRecordLink =
			result.get(0);

		Assert.assertEquals(
			existingAppBuilderAppDataRecordLink,
			newAppBuilderAppDataRecordLink);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderAppDataRecordLink.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"appBuilderAppDataRecordLinkId", RandomTestUtil.nextLong()));

		List<AppBuilderAppDataRecordLink> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AppBuilderAppDataRecordLink newAppBuilderAppDataRecordLink =
			addAppBuilderAppDataRecordLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderAppDataRecordLink.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("appBuilderAppDataRecordLinkId"));

		Object newAppBuilderAppDataRecordLinkId =
			newAppBuilderAppDataRecordLink.getAppBuilderAppDataRecordLinkId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"appBuilderAppDataRecordLinkId",
				new Object[] {newAppBuilderAppDataRecordLinkId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAppBuilderAppDataRecordLinkId = result.get(0);

		Assert.assertEquals(
			existingAppBuilderAppDataRecordLinkId,
			newAppBuilderAppDataRecordLinkId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderAppDataRecordLink.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("appBuilderAppDataRecordLinkId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"appBuilderAppDataRecordLinkId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AppBuilderAppDataRecordLink newAppBuilderAppDataRecordLink =
			addAppBuilderAppDataRecordLink();

		_persistence.clearCache();

		AppBuilderAppDataRecordLink existingAppBuilderAppDataRecordLink =
			_persistence.findByPrimaryKey(
				newAppBuilderAppDataRecordLink.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(existingAppBuilderAppDataRecordLink.getDdlRecordId()),
			ReflectionTestUtil.<Long>invoke(
				existingAppBuilderAppDataRecordLink, "getOriginalDdlRecordId",
				new Class<?>[0]));
	}

	protected AppBuilderAppDataRecordLink addAppBuilderAppDataRecordLink()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			_persistence.create(pk);

		appBuilderAppDataRecordLink.setGroupId(RandomTestUtil.nextLong());

		appBuilderAppDataRecordLink.setCompanyId(RandomTestUtil.nextLong());

		appBuilderAppDataRecordLink.setAppBuilderAppId(
			RandomTestUtil.nextLong());

		appBuilderAppDataRecordLink.setAppBuilderAppVersionId(
			RandomTestUtil.nextLong());

		appBuilderAppDataRecordLink.setDdlRecordId(RandomTestUtil.nextLong());

		_appBuilderAppDataRecordLinks.add(
			_persistence.update(appBuilderAppDataRecordLink));

		return appBuilderAppDataRecordLink;
	}

	private List<AppBuilderAppDataRecordLink> _appBuilderAppDataRecordLinks =
		new ArrayList<AppBuilderAppDataRecordLink>();
	private AppBuilderAppDataRecordLinkPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}