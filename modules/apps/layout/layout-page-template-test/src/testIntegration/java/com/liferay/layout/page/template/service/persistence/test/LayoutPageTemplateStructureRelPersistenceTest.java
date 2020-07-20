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
import com.liferay.layout.page.template.exception.NoSuchPageTemplateStructureRelException;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalServiceUtil;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateStructureRelPersistence;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateStructureRelUtil;
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
public class LayoutPageTemplateStructureRelPersistenceTest {

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
		_persistence = LayoutPageTemplateStructureRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutPageTemplateStructureRel> iterator =
			_layoutPageTemplateStructureRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel =
			_persistence.create(pk);

		Assert.assertNotNull(layoutPageTemplateStructureRel);

		Assert.assertEquals(layoutPageTemplateStructureRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutPageTemplateStructureRel newLayoutPageTemplateStructureRel =
			addLayoutPageTemplateStructureRel();

		_persistence.remove(newLayoutPageTemplateStructureRel);

		LayoutPageTemplateStructureRel existingLayoutPageTemplateStructureRel =
			_persistence.fetchByPrimaryKey(
				newLayoutPageTemplateStructureRel.getPrimaryKey());

		Assert.assertNull(existingLayoutPageTemplateStructureRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutPageTemplateStructureRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateStructureRel newLayoutPageTemplateStructureRel =
			_persistence.create(pk);

		newLayoutPageTemplateStructureRel.setMvccVersion(
			RandomTestUtil.nextLong());

		newLayoutPageTemplateStructureRel.setCtCollectionId(
			RandomTestUtil.nextLong());

		newLayoutPageTemplateStructureRel.setUuid(
			RandomTestUtil.randomString());

		newLayoutPageTemplateStructureRel.setGroupId(RandomTestUtil.nextLong());

		newLayoutPageTemplateStructureRel.setCompanyId(
			RandomTestUtil.nextLong());

		newLayoutPageTemplateStructureRel.setUserId(RandomTestUtil.nextLong());

		newLayoutPageTemplateStructureRel.setUserName(
			RandomTestUtil.randomString());

		newLayoutPageTemplateStructureRel.setCreateDate(
			RandomTestUtil.nextDate());

		newLayoutPageTemplateStructureRel.setModifiedDate(
			RandomTestUtil.nextDate());

		newLayoutPageTemplateStructureRel.setLayoutPageTemplateStructureId(
			RandomTestUtil.nextLong());

		newLayoutPageTemplateStructureRel.setSegmentsExperienceId(
			RandomTestUtil.nextLong());

		newLayoutPageTemplateStructureRel.setData(
			RandomTestUtil.randomString());

		_layoutPageTemplateStructureRels.add(
			_persistence.update(newLayoutPageTemplateStructureRel));

		LayoutPageTemplateStructureRel existingLayoutPageTemplateStructureRel =
			_persistence.findByPrimaryKey(
				newLayoutPageTemplateStructureRel.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateStructureRel.getMvccVersion(),
			newLayoutPageTemplateStructureRel.getMvccVersion());
		Assert.assertEquals(
			existingLayoutPageTemplateStructureRel.getCtCollectionId(),
			newLayoutPageTemplateStructureRel.getCtCollectionId());
		Assert.assertEquals(
			existingLayoutPageTemplateStructureRel.getUuid(),
			newLayoutPageTemplateStructureRel.getUuid());
		Assert.assertEquals(
			existingLayoutPageTemplateStructureRel.
				getLayoutPageTemplateStructureRelId(),
			newLayoutPageTemplateStructureRel.
				getLayoutPageTemplateStructureRelId());
		Assert.assertEquals(
			existingLayoutPageTemplateStructureRel.getGroupId(),
			newLayoutPageTemplateStructureRel.getGroupId());
		Assert.assertEquals(
			existingLayoutPageTemplateStructureRel.getCompanyId(),
			newLayoutPageTemplateStructureRel.getCompanyId());
		Assert.assertEquals(
			existingLayoutPageTemplateStructureRel.getUserId(),
			newLayoutPageTemplateStructureRel.getUserId());
		Assert.assertEquals(
			existingLayoutPageTemplateStructureRel.getUserName(),
			newLayoutPageTemplateStructureRel.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutPageTemplateStructureRel.getCreateDate()),
			Time.getShortTimestamp(
				newLayoutPageTemplateStructureRel.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutPageTemplateStructureRel.getModifiedDate()),
			Time.getShortTimestamp(
				newLayoutPageTemplateStructureRel.getModifiedDate()));
		Assert.assertEquals(
			existingLayoutPageTemplateStructureRel.
				getLayoutPageTemplateStructureId(),
			newLayoutPageTemplateStructureRel.
				getLayoutPageTemplateStructureId());
		Assert.assertEquals(
			existingLayoutPageTemplateStructureRel.getSegmentsExperienceId(),
			newLayoutPageTemplateStructureRel.getSegmentsExperienceId());
		Assert.assertEquals(
			existingLayoutPageTemplateStructureRel.getData(),
			newLayoutPageTemplateStructureRel.getData());
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
	public void testCountByLayoutPageTemplateStructureId() throws Exception {
		_persistence.countByLayoutPageTemplateStructureId(
			RandomTestUtil.nextLong());

		_persistence.countByLayoutPageTemplateStructureId(0L);
	}

	@Test
	public void testCountBySegmentsExperienceId() throws Exception {
		_persistence.countBySegmentsExperienceId(RandomTestUtil.nextLong());

		_persistence.countBySegmentsExperienceId(0L);
	}

	@Test
	public void testCountByL_S() throws Exception {
		_persistence.countByL_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByL_S(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateStructureRel newLayoutPageTemplateStructureRel =
			addLayoutPageTemplateStructureRel();

		LayoutPageTemplateStructureRel existingLayoutPageTemplateStructureRel =
			_persistence.findByPrimaryKey(
				newLayoutPageTemplateStructureRel.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateStructureRel,
			newLayoutPageTemplateStructureRel);
	}

	@Test(expected = NoSuchPageTemplateStructureRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LayoutPageTemplateStructureRel>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"LayoutPageTemplateStructureRel", "mvccVersion", true,
			"ctCollectionId", true, "uuid", true,
			"layoutPageTemplateStructureRelId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "layoutPageTemplateStructureId", true,
			"segmentsExperienceId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateStructureRel newLayoutPageTemplateStructureRel =
			addLayoutPageTemplateStructureRel();

		LayoutPageTemplateStructureRel existingLayoutPageTemplateStructureRel =
			_persistence.fetchByPrimaryKey(
				newLayoutPageTemplateStructureRel.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateStructureRel,
			newLayoutPageTemplateStructureRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateStructureRel missingLayoutPageTemplateStructureRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutPageTemplateStructureRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LayoutPageTemplateStructureRel newLayoutPageTemplateStructureRel1 =
			addLayoutPageTemplateStructureRel();
		LayoutPageTemplateStructureRel newLayoutPageTemplateStructureRel2 =
			addLayoutPageTemplateStructureRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateStructureRel1.getPrimaryKey());
		primaryKeys.add(newLayoutPageTemplateStructureRel2.getPrimaryKey());

		Map<Serializable, LayoutPageTemplateStructureRel>
			layoutPageTemplateStructureRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, layoutPageTemplateStructureRels.size());
		Assert.assertEquals(
			newLayoutPageTemplateStructureRel1,
			layoutPageTemplateStructureRels.get(
				newLayoutPageTemplateStructureRel1.getPrimaryKey()));
		Assert.assertEquals(
			newLayoutPageTemplateStructureRel2,
			layoutPageTemplateStructureRels.get(
				newLayoutPageTemplateStructureRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutPageTemplateStructureRel>
			layoutPageTemplateStructureRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(layoutPageTemplateStructureRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LayoutPageTemplateStructureRel newLayoutPageTemplateStructureRel =
			addLayoutPageTemplateStructureRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateStructureRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutPageTemplateStructureRel>
			layoutPageTemplateStructureRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, layoutPageTemplateStructureRels.size());
		Assert.assertEquals(
			newLayoutPageTemplateStructureRel,
			layoutPageTemplateStructureRels.get(
				newLayoutPageTemplateStructureRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutPageTemplateStructureRel>
			layoutPageTemplateStructureRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(layoutPageTemplateStructureRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LayoutPageTemplateStructureRel newLayoutPageTemplateStructureRel =
			addLayoutPageTemplateStructureRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateStructureRel.getPrimaryKey());

		Map<Serializable, LayoutPageTemplateStructureRel>
			layoutPageTemplateStructureRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, layoutPageTemplateStructureRels.size());
		Assert.assertEquals(
			newLayoutPageTemplateStructureRel,
			layoutPageTemplateStructureRels.get(
				newLayoutPageTemplateStructureRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LayoutPageTemplateStructureRelLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<LayoutPageTemplateStructureRel>() {

				@Override
				public void performAction(
					LayoutPageTemplateStructureRel
						layoutPageTemplateStructureRel) {

					Assert.assertNotNull(layoutPageTemplateStructureRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateStructureRel newLayoutPageTemplateStructureRel =
			addLayoutPageTemplateStructureRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateStructureRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutPageTemplateStructureRelId",
				newLayoutPageTemplateStructureRel.
					getLayoutPageTemplateStructureRelId()));

		List<LayoutPageTemplateStructureRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutPageTemplateStructureRel existingLayoutPageTemplateStructureRel =
			result.get(0);

		Assert.assertEquals(
			existingLayoutPageTemplateStructureRel,
			newLayoutPageTemplateStructureRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateStructureRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutPageTemplateStructureRelId", RandomTestUtil.nextLong()));

		List<LayoutPageTemplateStructureRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LayoutPageTemplateStructureRel newLayoutPageTemplateStructureRel =
			addLayoutPageTemplateStructureRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateStructureRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutPageTemplateStructureRelId"));

		Object newLayoutPageTemplateStructureRelId =
			newLayoutPageTemplateStructureRel.
				getLayoutPageTemplateStructureRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutPageTemplateStructureRelId",
				new Object[] {newLayoutPageTemplateStructureRelId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutPageTemplateStructureRelId = result.get(0);

		Assert.assertEquals(
			existingLayoutPageTemplateStructureRelId,
			newLayoutPageTemplateStructureRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateStructureRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutPageTemplateStructureRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutPageTemplateStructureRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutPageTemplateStructureRel newLayoutPageTemplateStructureRel =
			addLayoutPageTemplateStructureRel();

		_persistence.clearCache();

		LayoutPageTemplateStructureRel existingLayoutPageTemplateStructureRel =
			_persistence.findByPrimaryKey(
				newLayoutPageTemplateStructureRel.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingLayoutPageTemplateStructureRel.getUuid(),
				ReflectionTestUtil.invoke(
					existingLayoutPageTemplateStructureRel, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingLayoutPageTemplateStructureRel.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutPageTemplateStructureRel, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(
				existingLayoutPageTemplateStructureRel.
					getLayoutPageTemplateStructureId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutPageTemplateStructureRel,
				"getOriginalLayoutPageTemplateStructureId", new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(
				existingLayoutPageTemplateStructureRel.
					getSegmentsExperienceId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutPageTemplateStructureRel,
				"getOriginalSegmentsExperienceId", new Class<?>[0]));
	}

	protected LayoutPageTemplateStructureRel addLayoutPageTemplateStructureRel()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel =
			_persistence.create(pk);

		layoutPageTemplateStructureRel.setMvccVersion(
			RandomTestUtil.nextLong());

		layoutPageTemplateStructureRel.setCtCollectionId(
			RandomTestUtil.nextLong());

		layoutPageTemplateStructureRel.setUuid(RandomTestUtil.randomString());

		layoutPageTemplateStructureRel.setGroupId(RandomTestUtil.nextLong());

		layoutPageTemplateStructureRel.setCompanyId(RandomTestUtil.nextLong());

		layoutPageTemplateStructureRel.setUserId(RandomTestUtil.nextLong());

		layoutPageTemplateStructureRel.setUserName(
			RandomTestUtil.randomString());

		layoutPageTemplateStructureRel.setCreateDate(RandomTestUtil.nextDate());

		layoutPageTemplateStructureRel.setModifiedDate(
			RandomTestUtil.nextDate());

		layoutPageTemplateStructureRel.setLayoutPageTemplateStructureId(
			RandomTestUtil.nextLong());

		layoutPageTemplateStructureRel.setSegmentsExperienceId(
			RandomTestUtil.nextLong());

		layoutPageTemplateStructureRel.setData(RandomTestUtil.randomString());

		_layoutPageTemplateStructureRels.add(
			_persistence.update(layoutPageTemplateStructureRel));

		return layoutPageTemplateStructureRel;
	}

	private List<LayoutPageTemplateStructureRel>
		_layoutPageTemplateStructureRels =
			new ArrayList<LayoutPageTemplateStructureRel>();
	private LayoutPageTemplateStructureRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}