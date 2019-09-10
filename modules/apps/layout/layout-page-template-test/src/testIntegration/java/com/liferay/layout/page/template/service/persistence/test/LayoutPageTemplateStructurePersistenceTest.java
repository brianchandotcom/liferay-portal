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
import com.liferay.layout.page.template.exception.NoSuchPageTemplateStructureException;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateStructurePersistence;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateStructureUtil;
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
public class LayoutPageTemplateStructurePersistenceTest {

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
		_persistence = LayoutPageTemplateStructureUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutPageTemplateStructure> iterator =
			_layoutPageTemplateStructures.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_persistence.create(pk);

		Assert.assertNotNull(layoutPageTemplateStructure);

		Assert.assertEquals(layoutPageTemplateStructure.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutPageTemplateStructure newLayoutPageTemplateStructure =
			addLayoutPageTemplateStructure();

		_persistence.remove(newLayoutPageTemplateStructure);

		LayoutPageTemplateStructure existingLayoutPageTemplateStructure =
			_persistence.fetchByPrimaryKey(
				newLayoutPageTemplateStructure.getPrimaryKey());

		Assert.assertNull(existingLayoutPageTemplateStructure);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutPageTemplateStructure();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateStructure newLayoutPageTemplateStructure =
			_persistence.create(pk);

		newLayoutPageTemplateStructure.setMvccVersion(
			RandomTestUtil.nextLong());

		newLayoutPageTemplateStructure.setUuid(RandomTestUtil.randomString());

		newLayoutPageTemplateStructure.setGroupId(RandomTestUtil.nextLong());

		newLayoutPageTemplateStructure.setCompanyId(RandomTestUtil.nextLong());

		newLayoutPageTemplateStructure.setUserId(RandomTestUtil.nextLong());

		newLayoutPageTemplateStructure.setUserName(
			RandomTestUtil.randomString());

		newLayoutPageTemplateStructure.setCreateDate(RandomTestUtil.nextDate());

		newLayoutPageTemplateStructure.setModifiedDate(
			RandomTestUtil.nextDate());

		newLayoutPageTemplateStructure.setClassNameId(
			RandomTestUtil.nextLong());

		newLayoutPageTemplateStructure.setClassPK(RandomTestUtil.nextLong());

		_layoutPageTemplateStructures.add(
			_persistence.update(newLayoutPageTemplateStructure));

		LayoutPageTemplateStructure existingLayoutPageTemplateStructure =
			_persistence.findByPrimaryKey(
				newLayoutPageTemplateStructure.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateStructure.getMvccVersion(),
			newLayoutPageTemplateStructure.getMvccVersion());
		Assert.assertEquals(
			existingLayoutPageTemplateStructure.getUuid(),
			newLayoutPageTemplateStructure.getUuid());
		Assert.assertEquals(
			existingLayoutPageTemplateStructure.
				getLayoutPageTemplateStructureId(),
			newLayoutPageTemplateStructure.getLayoutPageTemplateStructureId());
		Assert.assertEquals(
			existingLayoutPageTemplateStructure.getGroupId(),
			newLayoutPageTemplateStructure.getGroupId());
		Assert.assertEquals(
			existingLayoutPageTemplateStructure.getCompanyId(),
			newLayoutPageTemplateStructure.getCompanyId());
		Assert.assertEquals(
			existingLayoutPageTemplateStructure.getUserId(),
			newLayoutPageTemplateStructure.getUserId());
		Assert.assertEquals(
			existingLayoutPageTemplateStructure.getUserName(),
			newLayoutPageTemplateStructure.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutPageTemplateStructure.getCreateDate()),
			Time.getShortTimestamp(
				newLayoutPageTemplateStructure.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutPageTemplateStructure.getModifiedDate()),
			Time.getShortTimestamp(
				newLayoutPageTemplateStructure.getModifiedDate()));
		Assert.assertEquals(
			existingLayoutPageTemplateStructure.getClassNameId(),
			newLayoutPageTemplateStructure.getClassNameId());
		Assert.assertEquals(
			existingLayoutPageTemplateStructure.getClassPK(),
			newLayoutPageTemplateStructure.getClassPK());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByG_C_C() throws Exception {
		_persistence.countByG_C_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_C_C(0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateStructure newLayoutPageTemplateStructure =
			addLayoutPageTemplateStructure();

		LayoutPageTemplateStructure existingLayoutPageTemplateStructure =
			_persistence.findByPrimaryKey(
				newLayoutPageTemplateStructure.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateStructure,
			newLayoutPageTemplateStructure);
	}

	@Test(expected = NoSuchPageTemplateStructureException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LayoutPageTemplateStructure>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"LayoutPageTemplateStructure", "mvccVersion", true, "uuid", true,
			"layoutPageTemplateStructureId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "classNameId", true, "classPK", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateStructure newLayoutPageTemplateStructure =
			addLayoutPageTemplateStructure();

		LayoutPageTemplateStructure existingLayoutPageTemplateStructure =
			_persistence.fetchByPrimaryKey(
				newLayoutPageTemplateStructure.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateStructure,
			newLayoutPageTemplateStructure);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateStructure missingLayoutPageTemplateStructure =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutPageTemplateStructure);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LayoutPageTemplateStructure newLayoutPageTemplateStructure1 =
			addLayoutPageTemplateStructure();
		LayoutPageTemplateStructure newLayoutPageTemplateStructure2 =
			addLayoutPageTemplateStructure();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateStructure1.getPrimaryKey());
		primaryKeys.add(newLayoutPageTemplateStructure2.getPrimaryKey());

		Map<Serializable, LayoutPageTemplateStructure>
			layoutPageTemplateStructures = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, layoutPageTemplateStructures.size());
		Assert.assertEquals(
			newLayoutPageTemplateStructure1,
			layoutPageTemplateStructures.get(
				newLayoutPageTemplateStructure1.getPrimaryKey()));
		Assert.assertEquals(
			newLayoutPageTemplateStructure2,
			layoutPageTemplateStructures.get(
				newLayoutPageTemplateStructure2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutPageTemplateStructure>
			layoutPageTemplateStructures = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(layoutPageTemplateStructures.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LayoutPageTemplateStructure newLayoutPageTemplateStructure =
			addLayoutPageTemplateStructure();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateStructure.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutPageTemplateStructure>
			layoutPageTemplateStructures = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, layoutPageTemplateStructures.size());
		Assert.assertEquals(
			newLayoutPageTemplateStructure,
			layoutPageTemplateStructures.get(
				newLayoutPageTemplateStructure.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutPageTemplateStructure>
			layoutPageTemplateStructures = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(layoutPageTemplateStructures.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LayoutPageTemplateStructure newLayoutPageTemplateStructure =
			addLayoutPageTemplateStructure();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateStructure.getPrimaryKey());

		Map<Serializable, LayoutPageTemplateStructure>
			layoutPageTemplateStructures = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, layoutPageTemplateStructures.size());
		Assert.assertEquals(
			newLayoutPageTemplateStructure,
			layoutPageTemplateStructures.get(
				newLayoutPageTemplateStructure.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LayoutPageTemplateStructureLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<LayoutPageTemplateStructure>() {

				@Override
				public void performAction(
					LayoutPageTemplateStructure layoutPageTemplateStructure) {

					Assert.assertNotNull(layoutPageTemplateStructure);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateStructure newLayoutPageTemplateStructure =
			addLayoutPageTemplateStructure();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateStructure.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutPageTemplateStructureId",
				newLayoutPageTemplateStructure.
					getLayoutPageTemplateStructureId()));

		List<LayoutPageTemplateStructure> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutPageTemplateStructure existingLayoutPageTemplateStructure =
			result.get(0);

		Assert.assertEquals(
			existingLayoutPageTemplateStructure,
			newLayoutPageTemplateStructure);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateStructure.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutPageTemplateStructureId", RandomTestUtil.nextLong()));

		List<LayoutPageTemplateStructure> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LayoutPageTemplateStructure newLayoutPageTemplateStructure =
			addLayoutPageTemplateStructure();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateStructure.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutPageTemplateStructureId"));

		Object newLayoutPageTemplateStructureId =
			newLayoutPageTemplateStructure.getLayoutPageTemplateStructureId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutPageTemplateStructureId",
				new Object[] {newLayoutPageTemplateStructureId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutPageTemplateStructureId = result.get(0);

		Assert.assertEquals(
			existingLayoutPageTemplateStructureId,
			newLayoutPageTemplateStructureId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateStructure.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutPageTemplateStructureId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutPageTemplateStructureId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutPageTemplateStructure newLayoutPageTemplateStructure =
			addLayoutPageTemplateStructure();

		_persistence.clearCache();

		LayoutPageTemplateStructure existingLayoutPageTemplateStructure =
			_persistence.findByPrimaryKey(
				newLayoutPageTemplateStructure.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingLayoutPageTemplateStructure.getUuid(),
				ReflectionTestUtil.invoke(
					existingLayoutPageTemplateStructure, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingLayoutPageTemplateStructure.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutPageTemplateStructure, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingLayoutPageTemplateStructure.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutPageTemplateStructure, "getOriginalGroupId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingLayoutPageTemplateStructure.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutPageTemplateStructure, "getOriginalClassNameId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingLayoutPageTemplateStructure.getClassPK()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutPageTemplateStructure, "getOriginalClassPK",
				new Class<?>[0]));
	}

	protected LayoutPageTemplateStructure addLayoutPageTemplateStructure()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_persistence.create(pk);

		layoutPageTemplateStructure.setMvccVersion(RandomTestUtil.nextLong());

		layoutPageTemplateStructure.setUuid(RandomTestUtil.randomString());

		layoutPageTemplateStructure.setGroupId(RandomTestUtil.nextLong());

		layoutPageTemplateStructure.setCompanyId(RandomTestUtil.nextLong());

		layoutPageTemplateStructure.setUserId(RandomTestUtil.nextLong());

		layoutPageTemplateStructure.setUserName(RandomTestUtil.randomString());

		layoutPageTemplateStructure.setCreateDate(RandomTestUtil.nextDate());

		layoutPageTemplateStructure.setModifiedDate(RandomTestUtil.nextDate());

		layoutPageTemplateStructure.setClassNameId(RandomTestUtil.nextLong());

		layoutPageTemplateStructure.setClassPK(RandomTestUtil.nextLong());

		_layoutPageTemplateStructures.add(
			_persistence.update(layoutPageTemplateStructure));

		return layoutPageTemplateStructure;
	}

	private List<LayoutPageTemplateStructure> _layoutPageTemplateStructures =
		new ArrayList<LayoutPageTemplateStructure>();
	private LayoutPageTemplateStructurePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}