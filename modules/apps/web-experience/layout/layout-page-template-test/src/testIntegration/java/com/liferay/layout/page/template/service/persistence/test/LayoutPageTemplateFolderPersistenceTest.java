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

import com.liferay.layout.page.template.exception.NoSuchPageTemplateFolderException;
import com.liferay.layout.page.template.model.LayoutPageTemplateFolder;
import com.liferay.layout.page.template.service.LayoutPageTemplateFolderLocalServiceUtil;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateFolderPersistence;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateFolderUtil;

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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class LayoutPageTemplateFolderPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.layout.page.template.service"));

	@Before
	public void setUp() {
		_persistence = LayoutPageTemplateFolderUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutPageTemplateFolder> iterator = _layoutPageTemplateFolders.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateFolder layoutPageTemplateFolder = _persistence.create(pk);

		Assert.assertNotNull(layoutPageTemplateFolder);

		Assert.assertEquals(layoutPageTemplateFolder.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutPageTemplateFolder newLayoutPageTemplateFolder = addLayoutPageTemplateFolder();

		_persistence.remove(newLayoutPageTemplateFolder);

		LayoutPageTemplateFolder existingLayoutPageTemplateFolder = _persistence.fetchByPrimaryKey(newLayoutPageTemplateFolder.getPrimaryKey());

		Assert.assertNull(existingLayoutPageTemplateFolder);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutPageTemplateFolder();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateFolder newLayoutPageTemplateFolder = _persistence.create(pk);

		newLayoutPageTemplateFolder.setGroupId(RandomTestUtil.nextLong());

		newLayoutPageTemplateFolder.setCompanyId(RandomTestUtil.nextLong());

		newLayoutPageTemplateFolder.setUserId(RandomTestUtil.nextLong());

		newLayoutPageTemplateFolder.setUserName(RandomTestUtil.randomString());

		newLayoutPageTemplateFolder.setCreateDate(RandomTestUtil.nextDate());

		newLayoutPageTemplateFolder.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutPageTemplateFolder.setName(RandomTestUtil.randomString());

		newLayoutPageTemplateFolder.setDescription(RandomTestUtil.randomString());

		_layoutPageTemplateFolders.add(_persistence.update(
				newLayoutPageTemplateFolder));

		LayoutPageTemplateFolder existingLayoutPageTemplateFolder = _persistence.findByPrimaryKey(newLayoutPageTemplateFolder.getPrimaryKey());

		Assert.assertEquals(existingLayoutPageTemplateFolder.getLayoutPageTemplateFolderId(),
			newLayoutPageTemplateFolder.getLayoutPageTemplateFolderId());
		Assert.assertEquals(existingLayoutPageTemplateFolder.getGroupId(),
			newLayoutPageTemplateFolder.getGroupId());
		Assert.assertEquals(existingLayoutPageTemplateFolder.getCompanyId(),
			newLayoutPageTemplateFolder.getCompanyId());
		Assert.assertEquals(existingLayoutPageTemplateFolder.getUserId(),
			newLayoutPageTemplateFolder.getUserId());
		Assert.assertEquals(existingLayoutPageTemplateFolder.getUserName(),
			newLayoutPageTemplateFolder.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutPageTemplateFolder.getCreateDate()),
			Time.getShortTimestamp(newLayoutPageTemplateFolder.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutPageTemplateFolder.getModifiedDate()),
			Time.getShortTimestamp(
				newLayoutPageTemplateFolder.getModifiedDate()));
		Assert.assertEquals(existingLayoutPageTemplateFolder.getName(),
			newLayoutPageTemplateFolder.getName());
		Assert.assertEquals(existingLayoutPageTemplateFolder.getDescription(),
			newLayoutPageTemplateFolder.getDescription());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByG_N() throws Exception {
		_persistence.countByG_N(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByG_N(0L, StringPool.NULL);

		_persistence.countByG_N(0L, (String)null);
	}

	@Test
	public void testCountByG_LikeN() throws Exception {
		_persistence.countByG_LikeN(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByG_LikeN(0L, StringPool.NULL);

		_persistence.countByG_LikeN(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateFolder newLayoutPageTemplateFolder = addLayoutPageTemplateFolder();

		LayoutPageTemplateFolder existingLayoutPageTemplateFolder = _persistence.findByPrimaryKey(newLayoutPageTemplateFolder.getPrimaryKey());

		Assert.assertEquals(existingLayoutPageTemplateFolder,
			newLayoutPageTemplateFolder);
	}

	@Test(expected = NoSuchPageTemplateFolderException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<LayoutPageTemplateFolder> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("LayoutPageTemplateFolder",
			"layoutPageTemplateFolderId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "description", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateFolder newLayoutPageTemplateFolder = addLayoutPageTemplateFolder();

		LayoutPageTemplateFolder existingLayoutPageTemplateFolder = _persistence.fetchByPrimaryKey(newLayoutPageTemplateFolder.getPrimaryKey());

		Assert.assertEquals(existingLayoutPageTemplateFolder,
			newLayoutPageTemplateFolder);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateFolder missingLayoutPageTemplateFolder = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutPageTemplateFolder);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		LayoutPageTemplateFolder newLayoutPageTemplateFolder1 = addLayoutPageTemplateFolder();
		LayoutPageTemplateFolder newLayoutPageTemplateFolder2 = addLayoutPageTemplateFolder();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateFolder1.getPrimaryKey());
		primaryKeys.add(newLayoutPageTemplateFolder2.getPrimaryKey());

		Map<Serializable, LayoutPageTemplateFolder> layoutPageTemplateFolders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutPageTemplateFolders.size());
		Assert.assertEquals(newLayoutPageTemplateFolder1,
			layoutPageTemplateFolders.get(
				newLayoutPageTemplateFolder1.getPrimaryKey()));
		Assert.assertEquals(newLayoutPageTemplateFolder2,
			layoutPageTemplateFolders.get(
				newLayoutPageTemplateFolder2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutPageTemplateFolder> layoutPageTemplateFolders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutPageTemplateFolders.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		LayoutPageTemplateFolder newLayoutPageTemplateFolder = addLayoutPageTemplateFolder();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateFolder.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutPageTemplateFolder> layoutPageTemplateFolders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutPageTemplateFolders.size());
		Assert.assertEquals(newLayoutPageTemplateFolder,
			layoutPageTemplateFolders.get(
				newLayoutPageTemplateFolder.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutPageTemplateFolder> layoutPageTemplateFolders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutPageTemplateFolders.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		LayoutPageTemplateFolder newLayoutPageTemplateFolder = addLayoutPageTemplateFolder();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateFolder.getPrimaryKey());

		Map<Serializable, LayoutPageTemplateFolder> layoutPageTemplateFolders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutPageTemplateFolders.size());
		Assert.assertEquals(newLayoutPageTemplateFolder,
			layoutPageTemplateFolders.get(
				newLayoutPageTemplateFolder.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = LayoutPageTemplateFolderLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<LayoutPageTemplateFolder>() {
				@Override
				public void performAction(
					LayoutPageTemplateFolder layoutPageTemplateFolder) {
					Assert.assertNotNull(layoutPageTemplateFolder);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutPageTemplateFolder newLayoutPageTemplateFolder = addLayoutPageTemplateFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutPageTemplateFolder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"layoutPageTemplateFolderId",
				newLayoutPageTemplateFolder.getLayoutPageTemplateFolderId()));

		List<LayoutPageTemplateFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutPageTemplateFolder existingLayoutPageTemplateFolder = result.get(0);

		Assert.assertEquals(existingLayoutPageTemplateFolder,
			newLayoutPageTemplateFolder);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutPageTemplateFolder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"layoutPageTemplateFolderId", RandomTestUtil.nextLong()));

		List<LayoutPageTemplateFolder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		LayoutPageTemplateFolder newLayoutPageTemplateFolder = addLayoutPageTemplateFolder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutPageTemplateFolder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutPageTemplateFolderId"));

		Object newLayoutPageTemplateFolderId = newLayoutPageTemplateFolder.getLayoutPageTemplateFolderId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"layoutPageTemplateFolderId",
				new Object[] { newLayoutPageTemplateFolderId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutPageTemplateFolderId = result.get(0);

		Assert.assertEquals(existingLayoutPageTemplateFolderId,
			newLayoutPageTemplateFolderId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutPageTemplateFolder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutPageTemplateFolderId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"layoutPageTemplateFolderId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutPageTemplateFolder newLayoutPageTemplateFolder = addLayoutPageTemplateFolder();

		_persistence.clearCache();

		LayoutPageTemplateFolder existingLayoutPageTemplateFolder = _persistence.findByPrimaryKey(newLayoutPageTemplateFolder.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingLayoutPageTemplateFolder.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingLayoutPageTemplateFolder,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingLayoutPageTemplateFolder.getName(),
				ReflectionTestUtil.invoke(existingLayoutPageTemplateFolder,
					"getOriginalName", new Class<?>[0])));
	}

	protected LayoutPageTemplateFolder addLayoutPageTemplateFolder()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateFolder layoutPageTemplateFolder = _persistence.create(pk);

		layoutPageTemplateFolder.setGroupId(RandomTestUtil.nextLong());

		layoutPageTemplateFolder.setCompanyId(RandomTestUtil.nextLong());

		layoutPageTemplateFolder.setUserId(RandomTestUtil.nextLong());

		layoutPageTemplateFolder.setUserName(RandomTestUtil.randomString());

		layoutPageTemplateFolder.setCreateDate(RandomTestUtil.nextDate());

		layoutPageTemplateFolder.setModifiedDate(RandomTestUtil.nextDate());

		layoutPageTemplateFolder.setName(RandomTestUtil.randomString());

		layoutPageTemplateFolder.setDescription(RandomTestUtil.randomString());

		_layoutPageTemplateFolders.add(_persistence.update(
				layoutPageTemplateFolder));

		return layoutPageTemplateFolder;
	}

	private List<LayoutPageTemplateFolder> _layoutPageTemplateFolders = new ArrayList<LayoutPageTemplateFolder>();
	private LayoutPageTemplateFolderPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}