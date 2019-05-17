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

package com.liferay.portal.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchRegionException;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.persistence.RegionPersistence;
import com.liferay.portal.kernel.service.persistence.RegionUtil;
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
public class RegionPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = RegionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Region> iterator = _regions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Region region = _persistence.create(pk);

		Assert.assertNotNull(region);

		Assert.assertEquals(region.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Region newRegion = addRegion();

		_persistence.remove(newRegion);

		Region existingRegion = _persistence.fetchByPrimaryKey(
			newRegion.getPrimaryKey());

		Assert.assertNull(existingRegion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addRegion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Region newRegion = _persistence.create(pk);

		newRegion.setMvccVersion(RandomTestUtil.nextLong());

		newRegion.setCountryId(RandomTestUtil.nextLong());

		newRegion.setRegionCode(RandomTestUtil.randomString());

		newRegion.setName(RandomTestUtil.randomString());

		newRegion.setActive(RandomTestUtil.randomBoolean());

		_regions.add(_persistence.update(newRegion));

		Region existingRegion = _persistence.findByPrimaryKey(
			newRegion.getPrimaryKey());

		Assert.assertEquals(
			existingRegion.getMvccVersion(), newRegion.getMvccVersion());
		Assert.assertEquals(
			existingRegion.getRegionId(), newRegion.getRegionId());
		Assert.assertEquals(
			existingRegion.getCountryId(), newRegion.getCountryId());
		Assert.assertEquals(
			existingRegion.getRegionCode(), newRegion.getRegionCode());
		Assert.assertEquals(existingRegion.getName(), newRegion.getName());
		Assert.assertEquals(existingRegion.isActive(), newRegion.isActive());
	}

	@Test
	public void testCountByCountryId() throws Exception {
		_persistence.countByCountryId(RandomTestUtil.nextLong());

		_persistence.countByCountryId(0L);
	}

	@Test
	public void testCountByActive() throws Exception {
		_persistence.countByActive(RandomTestUtil.randomBoolean());

		_persistence.countByActive(RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByC_R() throws Exception {
		_persistence.countByC_R(RandomTestUtil.nextLong(), "");

		_persistence.countByC_R(0L, "null");

		_persistence.countByC_R(0L, (String)null);
	}

	@Test
	public void testCountByC_A() throws Exception {
		_persistence.countByC_A(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByC_A(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Region newRegion = addRegion();

		Region existingRegion = _persistence.findByPrimaryKey(
			newRegion.getPrimaryKey());

		Assert.assertEquals(existingRegion, newRegion);
	}

	@Test(expected = NoSuchRegionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Region> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Region", "mvccVersion", true, "regionId", true, "countryId", true,
			"regionCode", true, "name", true, "active", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Region newRegion = addRegion();

		Region existingRegion = _persistence.fetchByPrimaryKey(
			newRegion.getPrimaryKey());

		Assert.assertEquals(existingRegion, newRegion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Region missingRegion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingRegion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Region newRegion1 = addRegion();
		Region newRegion2 = addRegion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRegion1.getPrimaryKey());
		primaryKeys.add(newRegion2.getPrimaryKey());

		Map<Serializable, Region> regions = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, regions.size());
		Assert.assertEquals(
			newRegion1, regions.get(newRegion1.getPrimaryKey()));
		Assert.assertEquals(
			newRegion2, regions.get(newRegion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Region> regions = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(regions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Region newRegion = addRegion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRegion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Region> regions = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, regions.size());
		Assert.assertEquals(newRegion, regions.get(newRegion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Region> regions = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(regions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Region newRegion = addRegion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRegion.getPrimaryKey());

		Map<Serializable, Region> regions = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, regions.size());
		Assert.assertEquals(newRegion, regions.get(newRegion.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Region newRegion = addRegion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Region.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("regionId", newRegion.getRegionId()));

		List<Region> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Region existingRegion = result.get(0);

		Assert.assertEquals(existingRegion, newRegion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Region.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("regionId", RandomTestUtil.nextLong()));

		List<Region> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Region newRegion = addRegion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Region.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("regionId"));

		Object newRegionId = newRegion.getRegionId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in("regionId", new Object[] {newRegionId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRegionId = result.get(0);

		Assert.assertEquals(existingRegionId, newRegionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Region.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("regionId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"regionId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Region newRegion = addRegion();

		_persistence.clearCache();

		Region existingRegion = _persistence.findByPrimaryKey(
			newRegion.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(existingRegion.getCountryId()),
			ReflectionTestUtil.<Long>invoke(
				existingRegion, "getOriginalCountryId", new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingRegion.getRegionCode(),
				ReflectionTestUtil.invoke(
					existingRegion, "getOriginalRegionCode", new Class<?>[0])));
	}

	protected Region addRegion() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Region region = _persistence.create(pk);

		region.setMvccVersion(RandomTestUtil.nextLong());

		region.setCountryId(RandomTestUtil.nextLong());

		region.setRegionCode(RandomTestUtil.randomString());

		region.setName(RandomTestUtil.randomString());

		region.setActive(RandomTestUtil.randomBoolean());

		_regions.add(_persistence.update(region));

		return region;
	}

	private List<Region> _regions = new ArrayList<Region>();
	private RegionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}