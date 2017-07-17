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

package com.liferay.friendly.url.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.friendly.url.exception.NoSuchFriendlyURLMappingException;
import com.liferay.friendly.url.model.FriendlyURLMapping;
import com.liferay.friendly.url.service.persistence.FriendlyURLMappingPK;
import com.liferay.friendly.url.service.persistence.FriendlyURLMappingPersistence;
import com.liferay.friendly.url.service.persistence.FriendlyURLMappingUtil;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
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
public class FriendlyURLMappingPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.friendly.url.service"));

	@Before
	public void setUp() {
		_persistence = FriendlyURLMappingUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FriendlyURLMapping> iterator = _friendlyURLMappings.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		FriendlyURLMappingPK pk = new FriendlyURLMappingPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

		FriendlyURLMapping friendlyURLMapping = _persistence.create(pk);

		Assert.assertNotNull(friendlyURLMapping);

		Assert.assertEquals(friendlyURLMapping.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FriendlyURLMapping newFriendlyURLMapping = addFriendlyURLMapping();

		_persistence.remove(newFriendlyURLMapping);

		FriendlyURLMapping existingFriendlyURLMapping = _persistence.fetchByPrimaryKey(newFriendlyURLMapping.getPrimaryKey());

		Assert.assertNull(existingFriendlyURLMapping);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFriendlyURLMapping();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		FriendlyURLMappingPK pk = new FriendlyURLMappingPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

		FriendlyURLMapping newFriendlyURLMapping = _persistence.create(pk);

		newFriendlyURLMapping.setFriendlyURLId(RandomTestUtil.nextLong());

		_friendlyURLMappings.add(_persistence.update(newFriendlyURLMapping));

		FriendlyURLMapping existingFriendlyURLMapping = _persistence.findByPrimaryKey(newFriendlyURLMapping.getPrimaryKey());

		Assert.assertEquals(existingFriendlyURLMapping.getClassNameId(),
			newFriendlyURLMapping.getClassNameId());
		Assert.assertEquals(existingFriendlyURLMapping.getClassPK(),
			newFriendlyURLMapping.getClassPK());
		Assert.assertEquals(existingFriendlyURLMapping.getFriendlyURLId(),
			newFriendlyURLMapping.getFriendlyURLId());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FriendlyURLMapping newFriendlyURLMapping = addFriendlyURLMapping();

		FriendlyURLMapping existingFriendlyURLMapping = _persistence.findByPrimaryKey(newFriendlyURLMapping.getPrimaryKey());

		Assert.assertEquals(existingFriendlyURLMapping, newFriendlyURLMapping);
	}

	@Test(expected = NoSuchFriendlyURLMappingException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		FriendlyURLMappingPK pk = new FriendlyURLMappingPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FriendlyURLMapping newFriendlyURLMapping = addFriendlyURLMapping();

		FriendlyURLMapping existingFriendlyURLMapping = _persistence.fetchByPrimaryKey(newFriendlyURLMapping.getPrimaryKey());

		Assert.assertEquals(existingFriendlyURLMapping, newFriendlyURLMapping);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		FriendlyURLMappingPK pk = new FriendlyURLMappingPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

		FriendlyURLMapping missingFriendlyURLMapping = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFriendlyURLMapping);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		FriendlyURLMapping newFriendlyURLMapping1 = addFriendlyURLMapping();
		FriendlyURLMapping newFriendlyURLMapping2 = addFriendlyURLMapping();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURLMapping1.getPrimaryKey());
		primaryKeys.add(newFriendlyURLMapping2.getPrimaryKey());

		Map<Serializable, FriendlyURLMapping> friendlyURLMappings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, friendlyURLMappings.size());
		Assert.assertEquals(newFriendlyURLMapping1,
			friendlyURLMappings.get(newFriendlyURLMapping1.getPrimaryKey()));
		Assert.assertEquals(newFriendlyURLMapping2,
			friendlyURLMappings.get(newFriendlyURLMapping2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		FriendlyURLMappingPK pk1 = new FriendlyURLMappingPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

		FriendlyURLMappingPK pk2 = new FriendlyURLMappingPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FriendlyURLMapping> friendlyURLMappings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(friendlyURLMappings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		FriendlyURLMapping newFriendlyURLMapping = addFriendlyURLMapping();

		FriendlyURLMappingPK pk = new FriendlyURLMappingPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURLMapping.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FriendlyURLMapping> friendlyURLMappings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, friendlyURLMappings.size());
		Assert.assertEquals(newFriendlyURLMapping,
			friendlyURLMappings.get(newFriendlyURLMapping.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FriendlyURLMapping> friendlyURLMappings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(friendlyURLMappings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		FriendlyURLMapping newFriendlyURLMapping = addFriendlyURLMapping();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURLMapping.getPrimaryKey());

		Map<Serializable, FriendlyURLMapping> friendlyURLMappings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, friendlyURLMappings.size());
		Assert.assertEquals(newFriendlyURLMapping,
			friendlyURLMappings.get(newFriendlyURLMapping.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		FriendlyURLMapping newFriendlyURLMapping = addFriendlyURLMapping();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURLMapping.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.classNameId",
				newFriendlyURLMapping.getClassNameId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.classPK",
				newFriendlyURLMapping.getClassPK()));

		List<FriendlyURLMapping> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		FriendlyURLMapping existingFriendlyURLMapping = result.get(0);

		Assert.assertEquals(existingFriendlyURLMapping, newFriendlyURLMapping);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURLMapping.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.classNameId",
				RandomTestUtil.nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.classPK",
				RandomTestUtil.nextLong()));

		List<FriendlyURLMapping> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		FriendlyURLMapping newFriendlyURLMapping = addFriendlyURLMapping();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURLMapping.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.classNameId"));

		Object newClassNameId = newFriendlyURLMapping.getClassNameId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.classNameId",
				new Object[] { newClassNameId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingClassNameId = result.get(0);

		Assert.assertEquals(existingClassNameId, newClassNameId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURLMapping.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.classNameId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.classNameId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected FriendlyURLMapping addFriendlyURLMapping()
		throws Exception {
		FriendlyURLMappingPK pk = new FriendlyURLMappingPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

		FriendlyURLMapping friendlyURLMapping = _persistence.create(pk);

		friendlyURLMapping.setFriendlyURLId(RandomTestUtil.nextLong());

		_friendlyURLMappings.add(_persistence.update(friendlyURLMapping));

		return friendlyURLMapping;
	}

	private List<FriendlyURLMapping> _friendlyURLMappings = new ArrayList<FriendlyURLMapping>();
	private FriendlyURLMappingPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}