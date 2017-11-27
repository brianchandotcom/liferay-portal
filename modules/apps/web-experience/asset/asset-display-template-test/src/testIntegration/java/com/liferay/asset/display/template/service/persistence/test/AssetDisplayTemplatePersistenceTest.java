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

package com.liferay.asset.display.template.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.asset.display.template.exception.NoSuchDisplayTemplateException;
import com.liferay.asset.display.template.model.AssetDisplayTemplate;
import com.liferay.asset.display.template.service.AssetDisplayTemplateLocalServiceUtil;
import com.liferay.asset.display.template.service.persistence.AssetDisplayTemplatePersistence;
import com.liferay.asset.display.template.service.persistence.AssetDisplayTemplateUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class AssetDisplayTemplatePersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.asset.display.template.service"));

	@Before
	public void setUp() {
		_persistence = AssetDisplayTemplateUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetDisplayTemplate> iterator = _assetDisplayTemplates.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetDisplayTemplate assetDisplayTemplate = _persistence.create(pk);

		Assert.assertNotNull(assetDisplayTemplate);

		Assert.assertEquals(assetDisplayTemplate.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetDisplayTemplate newAssetDisplayTemplate = addAssetDisplayTemplate();

		_persistence.remove(newAssetDisplayTemplate);

		AssetDisplayTemplate existingAssetDisplayTemplate = _persistence.fetchByPrimaryKey(newAssetDisplayTemplate.getPrimaryKey());

		Assert.assertNull(existingAssetDisplayTemplate);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetDisplayTemplate();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetDisplayTemplate newAssetDisplayTemplate = _persistence.create(pk);

		newAssetDisplayTemplate.setGroupId(RandomTestUtil.nextLong());

		newAssetDisplayTemplate.setCompanyId(RandomTestUtil.nextLong());

		newAssetDisplayTemplate.setUserId(RandomTestUtil.nextLong());

		newAssetDisplayTemplate.setUserName(RandomTestUtil.randomString());

		newAssetDisplayTemplate.setCreateDate(RandomTestUtil.nextDate());

		newAssetDisplayTemplate.setModifiedDate(RandomTestUtil.nextDate());

		newAssetDisplayTemplate.setName(RandomTestUtil.randomString());

		newAssetDisplayTemplate.setClassNameId(RandomTestUtil.nextLong());

		newAssetDisplayTemplate.setDDMTemplateId(RandomTestUtil.nextLong());

		newAssetDisplayTemplate.setMain(RandomTestUtil.randomBoolean());

		_assetDisplayTemplates.add(_persistence.update(newAssetDisplayTemplate));

		AssetDisplayTemplate existingAssetDisplayTemplate = _persistence.findByPrimaryKey(newAssetDisplayTemplate.getPrimaryKey());

		Assert.assertEquals(existingAssetDisplayTemplate.getAssetDisplayTemplateId(),
			newAssetDisplayTemplate.getAssetDisplayTemplateId());
		Assert.assertEquals(existingAssetDisplayTemplate.getGroupId(),
			newAssetDisplayTemplate.getGroupId());
		Assert.assertEquals(existingAssetDisplayTemplate.getCompanyId(),
			newAssetDisplayTemplate.getCompanyId());
		Assert.assertEquals(existingAssetDisplayTemplate.getUserId(),
			newAssetDisplayTemplate.getUserId());
		Assert.assertEquals(existingAssetDisplayTemplate.getUserName(),
			newAssetDisplayTemplate.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetDisplayTemplate.getCreateDate()),
			Time.getShortTimestamp(newAssetDisplayTemplate.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetDisplayTemplate.getModifiedDate()),
			Time.getShortTimestamp(newAssetDisplayTemplate.getModifiedDate()));
		Assert.assertEquals(existingAssetDisplayTemplate.getName(),
			newAssetDisplayTemplate.getName());
		Assert.assertEquals(existingAssetDisplayTemplate.getClassNameId(),
			newAssetDisplayTemplate.getClassNameId());
		Assert.assertEquals(existingAssetDisplayTemplate.getDDMTemplateId(),
			newAssetDisplayTemplate.getDDMTemplateId());
		Assert.assertEquals(existingAssetDisplayTemplate.getMain(),
			newAssetDisplayTemplate.getMain());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByG_LikeN() throws Exception {
		_persistence.countByG_LikeN(RandomTestUtil.nextLong(), "");

		_persistence.countByG_LikeN(0L, "null");

		_persistence.countByG_LikeN(0L, (String)null);
	}

	@Test
	public void testCountByG_C() throws Exception {
		_persistence.countByG_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_C(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetDisplayTemplate newAssetDisplayTemplate = addAssetDisplayTemplate();

		AssetDisplayTemplate existingAssetDisplayTemplate = _persistence.findByPrimaryKey(newAssetDisplayTemplate.getPrimaryKey());

		Assert.assertEquals(existingAssetDisplayTemplate,
			newAssetDisplayTemplate);
	}

	@Test(expected = NoSuchDisplayTemplateException.class)
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

	protected OrderByComparator<AssetDisplayTemplate> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetDisplayTemplate",
			"assetDisplayTemplateId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "classNameId", true,
			"DDMTemplateId", true, "main", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetDisplayTemplate newAssetDisplayTemplate = addAssetDisplayTemplate();

		AssetDisplayTemplate existingAssetDisplayTemplate = _persistence.fetchByPrimaryKey(newAssetDisplayTemplate.getPrimaryKey());

		Assert.assertEquals(existingAssetDisplayTemplate,
			newAssetDisplayTemplate);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetDisplayTemplate missingAssetDisplayTemplate = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetDisplayTemplate);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AssetDisplayTemplate newAssetDisplayTemplate1 = addAssetDisplayTemplate();
		AssetDisplayTemplate newAssetDisplayTemplate2 = addAssetDisplayTemplate();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetDisplayTemplate1.getPrimaryKey());
		primaryKeys.add(newAssetDisplayTemplate2.getPrimaryKey());

		Map<Serializable, AssetDisplayTemplate> assetDisplayTemplates = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetDisplayTemplates.size());
		Assert.assertEquals(newAssetDisplayTemplate1,
			assetDisplayTemplates.get(newAssetDisplayTemplate1.getPrimaryKey()));
		Assert.assertEquals(newAssetDisplayTemplate2,
			assetDisplayTemplates.get(newAssetDisplayTemplate2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetDisplayTemplate> assetDisplayTemplates = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetDisplayTemplates.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AssetDisplayTemplate newAssetDisplayTemplate = addAssetDisplayTemplate();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetDisplayTemplate.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetDisplayTemplate> assetDisplayTemplates = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetDisplayTemplates.size());
		Assert.assertEquals(newAssetDisplayTemplate,
			assetDisplayTemplates.get(newAssetDisplayTemplate.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetDisplayTemplate> assetDisplayTemplates = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetDisplayTemplates.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AssetDisplayTemplate newAssetDisplayTemplate = addAssetDisplayTemplate();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetDisplayTemplate.getPrimaryKey());

		Map<Serializable, AssetDisplayTemplate> assetDisplayTemplates = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetDisplayTemplates.size());
		Assert.assertEquals(newAssetDisplayTemplate,
			assetDisplayTemplates.get(newAssetDisplayTemplate.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AssetDisplayTemplateLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<AssetDisplayTemplate>() {
				@Override
				public void performAction(
					AssetDisplayTemplate assetDisplayTemplate) {
					Assert.assertNotNull(assetDisplayTemplate);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetDisplayTemplate newAssetDisplayTemplate = addAssetDisplayTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetDisplayTemplate.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("assetDisplayTemplateId",
				newAssetDisplayTemplate.getAssetDisplayTemplateId()));

		List<AssetDisplayTemplate> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetDisplayTemplate existingAssetDisplayTemplate = result.get(0);

		Assert.assertEquals(existingAssetDisplayTemplate,
			newAssetDisplayTemplate);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetDisplayTemplate.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("assetDisplayTemplateId",
				RandomTestUtil.nextLong()));

		List<AssetDisplayTemplate> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetDisplayTemplate newAssetDisplayTemplate = addAssetDisplayTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetDisplayTemplate.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"assetDisplayTemplateId"));

		Object newAssetDisplayTemplateId = newAssetDisplayTemplate.getAssetDisplayTemplateId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("assetDisplayTemplateId",
				new Object[] { newAssetDisplayTemplateId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAssetDisplayTemplateId = result.get(0);

		Assert.assertEquals(existingAssetDisplayTemplateId,
			newAssetDisplayTemplateId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetDisplayTemplate.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"assetDisplayTemplateId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("assetDisplayTemplateId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected AssetDisplayTemplate addAssetDisplayTemplate()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetDisplayTemplate assetDisplayTemplate = _persistence.create(pk);

		assetDisplayTemplate.setGroupId(RandomTestUtil.nextLong());

		assetDisplayTemplate.setCompanyId(RandomTestUtil.nextLong());

		assetDisplayTemplate.setUserId(RandomTestUtil.nextLong());

		assetDisplayTemplate.setUserName(RandomTestUtil.randomString());

		assetDisplayTemplate.setCreateDate(RandomTestUtil.nextDate());

		assetDisplayTemplate.setModifiedDate(RandomTestUtil.nextDate());

		assetDisplayTemplate.setName(RandomTestUtil.randomString());

		assetDisplayTemplate.setClassNameId(RandomTestUtil.nextLong());

		assetDisplayTemplate.setDDMTemplateId(RandomTestUtil.nextLong());

		assetDisplayTemplate.setMain(RandomTestUtil.randomBoolean());

		_assetDisplayTemplates.add(_persistence.update(assetDisplayTemplate));

		return assetDisplayTemplate;
	}

	private List<AssetDisplayTemplate> _assetDisplayTemplates = new ArrayList<AssetDisplayTemplate>();
	private AssetDisplayTemplatePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}