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

package com.liferay.layout.page.template.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryVersionException;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntryVersion;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryVersionLocalServiceUtil;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateEntryVersionPersistence;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateEntryVersionUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
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
public class LayoutPageTemplateEntryVersionPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.layout.page.template.service"));

	@Before
	public void setUp() {
		_persistence = LayoutPageTemplateEntryVersionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutPageTemplateEntryVersion> iterator =
			_layoutPageTemplateEntryVersions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion =
			_persistence.create(pk);

		Assert.assertNotNull(layoutPageTemplateEntryVersion);

		Assert.assertEquals(layoutPageTemplateEntryVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutPageTemplateEntryVersion newLayoutPageTemplateEntryVersion =
			addLayoutPageTemplateEntryVersion();

		_persistence.remove(newLayoutPageTemplateEntryVersion);

		LayoutPageTemplateEntryVersion existingLayoutPageTemplateEntryVersion =
			_persistence.fetchByPrimaryKey(
				newLayoutPageTemplateEntryVersion.getPrimaryKey());

		Assert.assertNull(existingLayoutPageTemplateEntryVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutPageTemplateEntryVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateEntryVersion newLayoutPageTemplateEntryVersion =
			_persistence.create(pk);

		newLayoutPageTemplateEntryVersion.setGroupId(RandomTestUtil.nextLong());

		newLayoutPageTemplateEntryVersion.setCompanyId(
			RandomTestUtil.nextLong());

		newLayoutPageTemplateEntryVersion.setUserId(RandomTestUtil.nextLong());

		newLayoutPageTemplateEntryVersion.setUserName(
			RandomTestUtil.randomString());

		newLayoutPageTemplateEntryVersion.setCreateDate(
			RandomTestUtil.nextDate());

		newLayoutPageTemplateEntryVersion.setModifiedDate(
			RandomTestUtil.nextDate());

		newLayoutPageTemplateEntryVersion.setLayoutPageTemplateEntryId(
			RandomTestUtil.nextLong());

		newLayoutPageTemplateEntryVersion.setPlid(RandomTestUtil.nextLong());

		newLayoutPageTemplateEntryVersion.setVersion(
			RandomTestUtil.nextDouble());

		_layoutPageTemplateEntryVersions.add(
			_persistence.update(newLayoutPageTemplateEntryVersion));

		LayoutPageTemplateEntryVersion existingLayoutPageTemplateEntryVersion =
			_persistence.findByPrimaryKey(
				newLayoutPageTemplateEntryVersion.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateEntryVersion.
				getLayoutPageTemplateEntryVersionId(),
			newLayoutPageTemplateEntryVersion.
				getLayoutPageTemplateEntryVersionId());
		Assert.assertEquals(
			existingLayoutPageTemplateEntryVersion.getGroupId(),
			newLayoutPageTemplateEntryVersion.getGroupId());
		Assert.assertEquals(
			existingLayoutPageTemplateEntryVersion.getCompanyId(),
			newLayoutPageTemplateEntryVersion.getCompanyId());
		Assert.assertEquals(
			existingLayoutPageTemplateEntryVersion.getUserId(),
			newLayoutPageTemplateEntryVersion.getUserId());
		Assert.assertEquals(
			existingLayoutPageTemplateEntryVersion.getUserName(),
			newLayoutPageTemplateEntryVersion.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutPageTemplateEntryVersion.getCreateDate()),
			Time.getShortTimestamp(
				newLayoutPageTemplateEntryVersion.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutPageTemplateEntryVersion.getModifiedDate()),
			Time.getShortTimestamp(
				newLayoutPageTemplateEntryVersion.getModifiedDate()));
		Assert.assertEquals(
			existingLayoutPageTemplateEntryVersion.
				getLayoutPageTemplateEntryId(),
			newLayoutPageTemplateEntryVersion.getLayoutPageTemplateEntryId());
		Assert.assertEquals(
			existingLayoutPageTemplateEntryVersion.getPlid(),
			newLayoutPageTemplateEntryVersion.getPlid());
		AssertUtils.assertEquals(
			existingLayoutPageTemplateEntryVersion.getVersion(),
			newLayoutPageTemplateEntryVersion.getVersion());
	}

	@Test
	public void testCountBylayoutPageTemplateEntryId() throws Exception {
		_persistence.countBylayoutPageTemplateEntryId(
			RandomTestUtil.nextLong());

		_persistence.countBylayoutPageTemplateEntryId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateEntryVersion newLayoutPageTemplateEntryVersion =
			addLayoutPageTemplateEntryVersion();

		LayoutPageTemplateEntryVersion existingLayoutPageTemplateEntryVersion =
			_persistence.findByPrimaryKey(
				newLayoutPageTemplateEntryVersion.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateEntryVersion,
			newLayoutPageTemplateEntryVersion);
	}

	@Test(expected = NoSuchPageTemplateEntryVersionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LayoutPageTemplateEntryVersion>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"LayoutPageTemplateEntryVersion",
			"layoutPageTemplateEntryVersionId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "layoutPageTemplateEntryId", true,
			"plid", true, "version", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateEntryVersion newLayoutPageTemplateEntryVersion =
			addLayoutPageTemplateEntryVersion();

		LayoutPageTemplateEntryVersion existingLayoutPageTemplateEntryVersion =
			_persistence.fetchByPrimaryKey(
				newLayoutPageTemplateEntryVersion.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateEntryVersion,
			newLayoutPageTemplateEntryVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateEntryVersion missingLayoutPageTemplateEntryVersion =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutPageTemplateEntryVersion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LayoutPageTemplateEntryVersion newLayoutPageTemplateEntryVersion1 =
			addLayoutPageTemplateEntryVersion();
		LayoutPageTemplateEntryVersion newLayoutPageTemplateEntryVersion2 =
			addLayoutPageTemplateEntryVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateEntryVersion1.getPrimaryKey());
		primaryKeys.add(newLayoutPageTemplateEntryVersion2.getPrimaryKey());

		Map<Serializable, LayoutPageTemplateEntryVersion>
			layoutPageTemplateEntryVersions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, layoutPageTemplateEntryVersions.size());
		Assert.assertEquals(
			newLayoutPageTemplateEntryVersion1,
			layoutPageTemplateEntryVersions.get(
				newLayoutPageTemplateEntryVersion1.getPrimaryKey()));
		Assert.assertEquals(
			newLayoutPageTemplateEntryVersion2,
			layoutPageTemplateEntryVersions.get(
				newLayoutPageTemplateEntryVersion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutPageTemplateEntryVersion>
			layoutPageTemplateEntryVersions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(layoutPageTemplateEntryVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LayoutPageTemplateEntryVersion newLayoutPageTemplateEntryVersion =
			addLayoutPageTemplateEntryVersion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateEntryVersion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutPageTemplateEntryVersion>
			layoutPageTemplateEntryVersions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, layoutPageTemplateEntryVersions.size());
		Assert.assertEquals(
			newLayoutPageTemplateEntryVersion,
			layoutPageTemplateEntryVersions.get(
				newLayoutPageTemplateEntryVersion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutPageTemplateEntryVersion>
			layoutPageTemplateEntryVersions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(layoutPageTemplateEntryVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LayoutPageTemplateEntryVersion newLayoutPageTemplateEntryVersion =
			addLayoutPageTemplateEntryVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateEntryVersion.getPrimaryKey());

		Map<Serializable, LayoutPageTemplateEntryVersion>
			layoutPageTemplateEntryVersions = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, layoutPageTemplateEntryVersions.size());
		Assert.assertEquals(
			newLayoutPageTemplateEntryVersion,
			layoutPageTemplateEntryVersions.get(
				newLayoutPageTemplateEntryVersion.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LayoutPageTemplateEntryVersionLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<LayoutPageTemplateEntryVersion>() {

				@Override
				public void performAction(
					LayoutPageTemplateEntryVersion
						layoutPageTemplateEntryVersion) {

					Assert.assertNotNull(layoutPageTemplateEntryVersion);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateEntryVersion newLayoutPageTemplateEntryVersion =
			addLayoutPageTemplateEntryVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateEntryVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutPageTemplateEntryVersionId",
				newLayoutPageTemplateEntryVersion.
					getLayoutPageTemplateEntryVersionId()));

		List<LayoutPageTemplateEntryVersion> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutPageTemplateEntryVersion existingLayoutPageTemplateEntryVersion =
			result.get(0);

		Assert.assertEquals(
			existingLayoutPageTemplateEntryVersion,
			newLayoutPageTemplateEntryVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateEntryVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutPageTemplateEntryVersionId", RandomTestUtil.nextLong()));

		List<LayoutPageTemplateEntryVersion> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LayoutPageTemplateEntryVersion newLayoutPageTemplateEntryVersion =
			addLayoutPageTemplateEntryVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateEntryVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutPageTemplateEntryVersionId"));

		Object newLayoutPageTemplateEntryVersionId =
			newLayoutPageTemplateEntryVersion.
				getLayoutPageTemplateEntryVersionId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutPageTemplateEntryVersionId",
				new Object[] {newLayoutPageTemplateEntryVersionId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutPageTemplateEntryVersionId = result.get(0);

		Assert.assertEquals(
			existingLayoutPageTemplateEntryVersionId,
			newLayoutPageTemplateEntryVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateEntryVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutPageTemplateEntryVersionId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutPageTemplateEntryVersionId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected LayoutPageTemplateEntryVersion addLayoutPageTemplateEntryVersion()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateEntryVersion layoutPageTemplateEntryVersion =
			_persistence.create(pk);

		layoutPageTemplateEntryVersion.setGroupId(RandomTestUtil.nextLong());

		layoutPageTemplateEntryVersion.setCompanyId(RandomTestUtil.nextLong());

		layoutPageTemplateEntryVersion.setUserId(RandomTestUtil.nextLong());

		layoutPageTemplateEntryVersion.setUserName(
			RandomTestUtil.randomString());

		layoutPageTemplateEntryVersion.setCreateDate(RandomTestUtil.nextDate());

		layoutPageTemplateEntryVersion.setModifiedDate(
			RandomTestUtil.nextDate());

		layoutPageTemplateEntryVersion.setLayoutPageTemplateEntryId(
			RandomTestUtil.nextLong());

		layoutPageTemplateEntryVersion.setPlid(RandomTestUtil.nextLong());

		layoutPageTemplateEntryVersion.setVersion(RandomTestUtil.nextDouble());

		_layoutPageTemplateEntryVersions.add(
			_persistence.update(layoutPageTemplateEntryVersion));

		return layoutPageTemplateEntryVersion;
	}

	private List<LayoutPageTemplateEntryVersion>
		_layoutPageTemplateEntryVersions =
			new ArrayList<LayoutPageTemplateEntryVersion>();
	private LayoutPageTemplateEntryVersionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}