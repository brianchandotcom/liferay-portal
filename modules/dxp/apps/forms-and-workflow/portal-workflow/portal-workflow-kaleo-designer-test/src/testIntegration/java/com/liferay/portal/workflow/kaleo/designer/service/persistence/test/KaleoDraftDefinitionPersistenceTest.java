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

package com.liferay.portal.workflow.kaleo.designer.service.persistence.test;

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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.workflow.kaleo.designer.exception.NoSuchKaleoDraftDefinitionException;
import com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition;
import com.liferay.portal.workflow.kaleo.designer.service.KaleoDraftDefinitionLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.designer.service.persistence.KaleoDraftDefinitionPersistence;
import com.liferay.portal.workflow.kaleo.designer.service.persistence.KaleoDraftDefinitionUtil;

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
public class KaleoDraftDefinitionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.portal.workflow.kaleo.designer.service"));

	@Before
	public void setUp() {
		_persistence = KaleoDraftDefinitionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<KaleoDraftDefinition> iterator = _kaleoDraftDefinitions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoDraftDefinition kaleoDraftDefinition = _persistence.create(pk);

		Assert.assertNotNull(kaleoDraftDefinition);

		Assert.assertEquals(kaleoDraftDefinition.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		KaleoDraftDefinition newKaleoDraftDefinition = addKaleoDraftDefinition();

		_persistence.remove(newKaleoDraftDefinition);

		KaleoDraftDefinition existingKaleoDraftDefinition = _persistence.fetchByPrimaryKey(newKaleoDraftDefinition.getPrimaryKey());

		Assert.assertNull(existingKaleoDraftDefinition);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addKaleoDraftDefinition();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoDraftDefinition newKaleoDraftDefinition = _persistence.create(pk);

		newKaleoDraftDefinition.setGroupId(RandomTestUtil.nextLong());

		newKaleoDraftDefinition.setCompanyId(RandomTestUtil.nextLong());

		newKaleoDraftDefinition.setUserId(RandomTestUtil.nextLong());

		newKaleoDraftDefinition.setUserName(RandomTestUtil.randomString());

		newKaleoDraftDefinition.setCreateDate(RandomTestUtil.nextDate());

		newKaleoDraftDefinition.setModifiedDate(RandomTestUtil.nextDate());

		newKaleoDraftDefinition.setName(RandomTestUtil.randomString());

		newKaleoDraftDefinition.setTitle(RandomTestUtil.randomString());

		newKaleoDraftDefinition.setContent(RandomTestUtil.randomString());

		newKaleoDraftDefinition.setVersion(RandomTestUtil.nextInt());

		newKaleoDraftDefinition.setDraftVersion(RandomTestUtil.nextInt());

		_kaleoDraftDefinitions.add(_persistence.update(newKaleoDraftDefinition));

		KaleoDraftDefinition existingKaleoDraftDefinition = _persistence.findByPrimaryKey(newKaleoDraftDefinition.getPrimaryKey());

		Assert.assertEquals(existingKaleoDraftDefinition.getKaleoDraftDefinitionId(),
			newKaleoDraftDefinition.getKaleoDraftDefinitionId());
		Assert.assertEquals(existingKaleoDraftDefinition.getGroupId(),
			newKaleoDraftDefinition.getGroupId());
		Assert.assertEquals(existingKaleoDraftDefinition.getCompanyId(),
			newKaleoDraftDefinition.getCompanyId());
		Assert.assertEquals(existingKaleoDraftDefinition.getUserId(),
			newKaleoDraftDefinition.getUserId());
		Assert.assertEquals(existingKaleoDraftDefinition.getUserName(),
			newKaleoDraftDefinition.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoDraftDefinition.getCreateDate()),
			Time.getShortTimestamp(newKaleoDraftDefinition.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoDraftDefinition.getModifiedDate()),
			Time.getShortTimestamp(newKaleoDraftDefinition.getModifiedDate()));
		Assert.assertEquals(existingKaleoDraftDefinition.getName(),
			newKaleoDraftDefinition.getName());
		Assert.assertEquals(existingKaleoDraftDefinition.getTitle(),
			newKaleoDraftDefinition.getTitle());
		Assert.assertEquals(existingKaleoDraftDefinition.getContent(),
			newKaleoDraftDefinition.getContent());
		Assert.assertEquals(existingKaleoDraftDefinition.getVersion(),
			newKaleoDraftDefinition.getVersion());
		Assert.assertEquals(existingKaleoDraftDefinition.getDraftVersion(),
			newKaleoDraftDefinition.getDraftVersion());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByC_N_V() throws Exception {
		_persistence.countByC_N_V(RandomTestUtil.nextLong(), StringPool.BLANK,
			RandomTestUtil.nextInt());

		_persistence.countByC_N_V(0L, StringPool.NULL, 0);

		_persistence.countByC_N_V(0L, (String)null, 0);
	}

	@Test
	public void testCountByC_N_V_D() throws Exception {
		_persistence.countByC_N_V_D(RandomTestUtil.nextLong(),
			StringPool.BLANK, RandomTestUtil.nextInt(), RandomTestUtil.nextInt());

		_persistence.countByC_N_V_D(0L, StringPool.NULL, 0, 0);

		_persistence.countByC_N_V_D(0L, (String)null, 0, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		KaleoDraftDefinition newKaleoDraftDefinition = addKaleoDraftDefinition();

		KaleoDraftDefinition existingKaleoDraftDefinition = _persistence.findByPrimaryKey(newKaleoDraftDefinition.getPrimaryKey());

		Assert.assertEquals(existingKaleoDraftDefinition,
			newKaleoDraftDefinition);
	}

	@Test(expected = NoSuchKaleoDraftDefinitionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<KaleoDraftDefinition> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("KaleoDraftDefinition",
			"kaleoDraftDefinitionId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "title", true, "version", true,
			"draftVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		KaleoDraftDefinition newKaleoDraftDefinition = addKaleoDraftDefinition();

		KaleoDraftDefinition existingKaleoDraftDefinition = _persistence.fetchByPrimaryKey(newKaleoDraftDefinition.getPrimaryKey());

		Assert.assertEquals(existingKaleoDraftDefinition,
			newKaleoDraftDefinition);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoDraftDefinition missingKaleoDraftDefinition = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingKaleoDraftDefinition);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		KaleoDraftDefinition newKaleoDraftDefinition1 = addKaleoDraftDefinition();
		KaleoDraftDefinition newKaleoDraftDefinition2 = addKaleoDraftDefinition();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoDraftDefinition1.getPrimaryKey());
		primaryKeys.add(newKaleoDraftDefinition2.getPrimaryKey());

		Map<Serializable, KaleoDraftDefinition> kaleoDraftDefinitions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, kaleoDraftDefinitions.size());
		Assert.assertEquals(newKaleoDraftDefinition1,
			kaleoDraftDefinitions.get(newKaleoDraftDefinition1.getPrimaryKey()));
		Assert.assertEquals(newKaleoDraftDefinition2,
			kaleoDraftDefinitions.get(newKaleoDraftDefinition2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, KaleoDraftDefinition> kaleoDraftDefinitions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(kaleoDraftDefinitions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		KaleoDraftDefinition newKaleoDraftDefinition = addKaleoDraftDefinition();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoDraftDefinition.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, KaleoDraftDefinition> kaleoDraftDefinitions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, kaleoDraftDefinitions.size());
		Assert.assertEquals(newKaleoDraftDefinition,
			kaleoDraftDefinitions.get(newKaleoDraftDefinition.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, KaleoDraftDefinition> kaleoDraftDefinitions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(kaleoDraftDefinitions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		KaleoDraftDefinition newKaleoDraftDefinition = addKaleoDraftDefinition();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoDraftDefinition.getPrimaryKey());

		Map<Serializable, KaleoDraftDefinition> kaleoDraftDefinitions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, kaleoDraftDefinitions.size());
		Assert.assertEquals(newKaleoDraftDefinition,
			kaleoDraftDefinitions.get(newKaleoDraftDefinition.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = KaleoDraftDefinitionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<KaleoDraftDefinition>() {
				@Override
				public void performAction(
					KaleoDraftDefinition kaleoDraftDefinition) {
					Assert.assertNotNull(kaleoDraftDefinition);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		KaleoDraftDefinition newKaleoDraftDefinition = addKaleoDraftDefinition();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoDraftDefinition.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("kaleoDraftDefinitionId",
				newKaleoDraftDefinition.getKaleoDraftDefinitionId()));

		List<KaleoDraftDefinition> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		KaleoDraftDefinition existingKaleoDraftDefinition = result.get(0);

		Assert.assertEquals(existingKaleoDraftDefinition,
			newKaleoDraftDefinition);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoDraftDefinition.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("kaleoDraftDefinitionId",
				RandomTestUtil.nextLong()));

		List<KaleoDraftDefinition> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		KaleoDraftDefinition newKaleoDraftDefinition = addKaleoDraftDefinition();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoDraftDefinition.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"kaleoDraftDefinitionId"));

		Object newKaleoDraftDefinitionId = newKaleoDraftDefinition.getKaleoDraftDefinitionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("kaleoDraftDefinitionId",
				new Object[] { newKaleoDraftDefinitionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingKaleoDraftDefinitionId = result.get(0);

		Assert.assertEquals(existingKaleoDraftDefinitionId,
			newKaleoDraftDefinitionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoDraftDefinition.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"kaleoDraftDefinitionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("kaleoDraftDefinitionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		KaleoDraftDefinition newKaleoDraftDefinition = addKaleoDraftDefinition();

		_persistence.clearCache();

		KaleoDraftDefinition existingKaleoDraftDefinition = _persistence.findByPrimaryKey(newKaleoDraftDefinition.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingKaleoDraftDefinition.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(existingKaleoDraftDefinition,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingKaleoDraftDefinition.getName(),
				ReflectionTestUtil.invoke(existingKaleoDraftDefinition,
					"getOriginalName", new Class<?>[0])));
		Assert.assertEquals(Integer.valueOf(
				existingKaleoDraftDefinition.getVersion()),
			ReflectionTestUtil.<Integer>invoke(existingKaleoDraftDefinition,
				"getOriginalVersion", new Class<?>[0]));
		Assert.assertEquals(Integer.valueOf(
				existingKaleoDraftDefinition.getDraftVersion()),
			ReflectionTestUtil.<Integer>invoke(existingKaleoDraftDefinition,
				"getOriginalDraftVersion", new Class<?>[0]));
	}

	protected KaleoDraftDefinition addKaleoDraftDefinition()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoDraftDefinition kaleoDraftDefinition = _persistence.create(pk);

		kaleoDraftDefinition.setGroupId(RandomTestUtil.nextLong());

		kaleoDraftDefinition.setCompanyId(RandomTestUtil.nextLong());

		kaleoDraftDefinition.setUserId(RandomTestUtil.nextLong());

		kaleoDraftDefinition.setUserName(RandomTestUtil.randomString());

		kaleoDraftDefinition.setCreateDate(RandomTestUtil.nextDate());

		kaleoDraftDefinition.setModifiedDate(RandomTestUtil.nextDate());

		kaleoDraftDefinition.setName(RandomTestUtil.randomString());

		kaleoDraftDefinition.setTitle(RandomTestUtil.randomString());

		kaleoDraftDefinition.setContent(RandomTestUtil.randomString());

		kaleoDraftDefinition.setVersion(RandomTestUtil.nextInt());

		kaleoDraftDefinition.setDraftVersion(RandomTestUtil.nextInt());

		_kaleoDraftDefinitions.add(_persistence.update(kaleoDraftDefinition));

		return kaleoDraftDefinition;
	}

	private List<KaleoDraftDefinition> _kaleoDraftDefinitions = new ArrayList<KaleoDraftDefinition>();
	private KaleoDraftDefinitionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}