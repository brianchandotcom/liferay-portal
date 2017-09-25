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

import com.liferay.layout.page.template.exception.NoSuchPageTemplateException;
import com.liferay.layout.page.template.model.LayoutPageTemplate;
import com.liferay.layout.page.template.service.LayoutPageTemplateLocalServiceUtil;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplatePersistence;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateUtil;

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
public class LayoutPageTemplatePersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.layout.page.template.service"));

	@Before
	public void setUp() {
		_persistence = LayoutPageTemplateUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutPageTemplate> iterator = _layoutPageTemplates.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplate layoutPageTemplate = _persistence.create(pk);

		Assert.assertNotNull(layoutPageTemplate);

		Assert.assertEquals(layoutPageTemplate.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutPageTemplate newLayoutPageTemplate = addLayoutPageTemplate();

		_persistence.remove(newLayoutPageTemplate);

		LayoutPageTemplate existingLayoutPageTemplate = _persistence.fetchByPrimaryKey(newLayoutPageTemplate.getPrimaryKey());

		Assert.assertNull(existingLayoutPageTemplate);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutPageTemplate();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplate newLayoutPageTemplate = _persistence.create(pk);

		newLayoutPageTemplate.setGroupId(RandomTestUtil.nextLong());

		newLayoutPageTemplate.setCompanyId(RandomTestUtil.nextLong());

		newLayoutPageTemplate.setUserId(RandomTestUtil.nextLong());

		newLayoutPageTemplate.setUserName(RandomTestUtil.randomString());

		newLayoutPageTemplate.setCreateDate(RandomTestUtil.nextDate());

		newLayoutPageTemplate.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutPageTemplate.setLayoutPageTemplateFolderId(RandomTestUtil.nextLong());

		newLayoutPageTemplate.setName(RandomTestUtil.randomString());

		_layoutPageTemplates.add(_persistence.update(newLayoutPageTemplate));

		LayoutPageTemplate existingLayoutPageTemplate = _persistence.findByPrimaryKey(newLayoutPageTemplate.getPrimaryKey());

		Assert.assertEquals(existingLayoutPageTemplate.getLayoutPageTemplateId(),
			newLayoutPageTemplate.getLayoutPageTemplateId());
		Assert.assertEquals(existingLayoutPageTemplate.getGroupId(),
			newLayoutPageTemplate.getGroupId());
		Assert.assertEquals(existingLayoutPageTemplate.getCompanyId(),
			newLayoutPageTemplate.getCompanyId());
		Assert.assertEquals(existingLayoutPageTemplate.getUserId(),
			newLayoutPageTemplate.getUserId());
		Assert.assertEquals(existingLayoutPageTemplate.getUserName(),
			newLayoutPageTemplate.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutPageTemplate.getCreateDate()),
			Time.getShortTimestamp(newLayoutPageTemplate.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutPageTemplate.getModifiedDate()),
			Time.getShortTimestamp(newLayoutPageTemplate.getModifiedDate()));
		Assert.assertEquals(existingLayoutPageTemplate.getLayoutPageTemplateFolderId(),
			newLayoutPageTemplate.getLayoutPageTemplateFolderId());
		Assert.assertEquals(existingLayoutPageTemplate.getName(),
			newLayoutPageTemplate.getName());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByLayoutPageTemplateFolderId()
		throws Exception {
		_persistence.countByLayoutPageTemplateFolderId(RandomTestUtil.nextLong());

		_persistence.countByLayoutPageTemplateFolderId(0L);
	}

	@Test
	public void testCountByG_L() throws Exception {
		_persistence.countByG_L(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_L(0L, 0L);
	}

	@Test
	public void testCountByG_N() throws Exception {
		_persistence.countByG_N(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByG_N(0L, StringPool.NULL);

		_persistence.countByG_N(0L, (String)null);
	}

	@Test
	public void testCountByG_L_LikeN() throws Exception {
		_persistence.countByG_L_LikeN(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByG_L_LikeN(0L, 0L, StringPool.NULL);

		_persistence.countByG_L_LikeN(0L, 0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplate newLayoutPageTemplate = addLayoutPageTemplate();

		LayoutPageTemplate existingLayoutPageTemplate = _persistence.findByPrimaryKey(newLayoutPageTemplate.getPrimaryKey());

		Assert.assertEquals(existingLayoutPageTemplate, newLayoutPageTemplate);
	}

	@Test(expected = NoSuchPageTemplateException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LayoutPageTemplate> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("LayoutPageTemplate",
			"layoutPageTemplateId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "layoutPageTemplateFolderId", true, "name",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplate newLayoutPageTemplate = addLayoutPageTemplate();

		LayoutPageTemplate existingLayoutPageTemplate = _persistence.fetchByPrimaryKey(newLayoutPageTemplate.getPrimaryKey());

		Assert.assertEquals(existingLayoutPageTemplate, newLayoutPageTemplate);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplate missingLayoutPageTemplate = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutPageTemplate);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		LayoutPageTemplate newLayoutPageTemplate1 = addLayoutPageTemplate();
		LayoutPageTemplate newLayoutPageTemplate2 = addLayoutPageTemplate();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplate1.getPrimaryKey());
		primaryKeys.add(newLayoutPageTemplate2.getPrimaryKey());

		Map<Serializable, LayoutPageTemplate> layoutPageTemplates = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutPageTemplates.size());
		Assert.assertEquals(newLayoutPageTemplate1,
			layoutPageTemplates.get(newLayoutPageTemplate1.getPrimaryKey()));
		Assert.assertEquals(newLayoutPageTemplate2,
			layoutPageTemplates.get(newLayoutPageTemplate2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutPageTemplate> layoutPageTemplates = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutPageTemplates.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		LayoutPageTemplate newLayoutPageTemplate = addLayoutPageTemplate();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplate.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutPageTemplate> layoutPageTemplates = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutPageTemplates.size());
		Assert.assertEquals(newLayoutPageTemplate,
			layoutPageTemplates.get(newLayoutPageTemplate.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutPageTemplate> layoutPageTemplates = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutPageTemplates.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		LayoutPageTemplate newLayoutPageTemplate = addLayoutPageTemplate();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplate.getPrimaryKey());

		Map<Serializable, LayoutPageTemplate> layoutPageTemplates = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutPageTemplates.size());
		Assert.assertEquals(newLayoutPageTemplate,
			layoutPageTemplates.get(newLayoutPageTemplate.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = LayoutPageTemplateLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<LayoutPageTemplate>() {
				@Override
				public void performAction(LayoutPageTemplate layoutPageTemplate) {
					Assert.assertNotNull(layoutPageTemplate);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutPageTemplate newLayoutPageTemplate = addLayoutPageTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutPageTemplate.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutPageTemplateId",
				newLayoutPageTemplate.getLayoutPageTemplateId()));

		List<LayoutPageTemplate> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutPageTemplate existingLayoutPageTemplate = result.get(0);

		Assert.assertEquals(existingLayoutPageTemplate, newLayoutPageTemplate);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutPageTemplate.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutPageTemplateId",
				RandomTestUtil.nextLong()));

		List<LayoutPageTemplate> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		LayoutPageTemplate newLayoutPageTemplate = addLayoutPageTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutPageTemplate.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutPageTemplateId"));

		Object newLayoutPageTemplateId = newLayoutPageTemplate.getLayoutPageTemplateId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutPageTemplateId",
				new Object[] { newLayoutPageTemplateId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutPageTemplateId = result.get(0);

		Assert.assertEquals(existingLayoutPageTemplateId,
			newLayoutPageTemplateId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutPageTemplate.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutPageTemplateId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutPageTemplateId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutPageTemplate newLayoutPageTemplate = addLayoutPageTemplate();

		_persistence.clearCache();

		LayoutPageTemplate existingLayoutPageTemplate = _persistence.findByPrimaryKey(newLayoutPageTemplate.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingLayoutPageTemplate.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingLayoutPageTemplate,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(existingLayoutPageTemplate.getName(),
				ReflectionTestUtil.invoke(existingLayoutPageTemplate,
					"getOriginalName", new Class<?>[0])));
	}

	protected LayoutPageTemplate addLayoutPageTemplate()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplate layoutPageTemplate = _persistence.create(pk);

		layoutPageTemplate.setGroupId(RandomTestUtil.nextLong());

		layoutPageTemplate.setCompanyId(RandomTestUtil.nextLong());

		layoutPageTemplate.setUserId(RandomTestUtil.nextLong());

		layoutPageTemplate.setUserName(RandomTestUtil.randomString());

		layoutPageTemplate.setCreateDate(RandomTestUtil.nextDate());

		layoutPageTemplate.setModifiedDate(RandomTestUtil.nextDate());

		layoutPageTemplate.setLayoutPageTemplateFolderId(RandomTestUtil.nextLong());

		layoutPageTemplate.setName(RandomTestUtil.randomString());

		_layoutPageTemplates.add(_persistence.update(layoutPageTemplate));

		return layoutPageTemplate;
	}

	private List<LayoutPageTemplate> _layoutPageTemplates = new ArrayList<LayoutPageTemplate>();
	private LayoutPageTemplatePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}