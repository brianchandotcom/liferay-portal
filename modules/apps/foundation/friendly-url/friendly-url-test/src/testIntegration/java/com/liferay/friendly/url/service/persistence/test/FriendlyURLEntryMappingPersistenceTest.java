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

import com.liferay.friendly.url.exception.NoSuchFriendlyURLEntryMappingException;
import com.liferay.friendly.url.model.FriendlyURLEntryMapping;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryMappingPK;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryMappingPersistence;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryMappingUtil;

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
public class FriendlyURLEntryMappingPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.friendly.url.service"));

	@Before
	public void setUp() {
		_persistence = FriendlyURLEntryMappingUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FriendlyURLEntryMapping> iterator = _friendlyURLEntryMappings.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		FriendlyURLEntryMappingPK pk = new FriendlyURLEntryMappingPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

		FriendlyURLEntryMapping friendlyURLEntryMapping = _persistence.create(pk);

		Assert.assertNotNull(friendlyURLEntryMapping);

		Assert.assertEquals(friendlyURLEntryMapping.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FriendlyURLEntryMapping newFriendlyURLEntryMapping = addFriendlyURLEntryMapping();

		_persistence.remove(newFriendlyURLEntryMapping);

		FriendlyURLEntryMapping existingFriendlyURLEntryMapping = _persistence.fetchByPrimaryKey(newFriendlyURLEntryMapping.getPrimaryKey());

		Assert.assertNull(existingFriendlyURLEntryMapping);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFriendlyURLEntryMapping();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		FriendlyURLEntryMappingPK pk = new FriendlyURLEntryMappingPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

		FriendlyURLEntryMapping newFriendlyURLEntryMapping = _persistence.create(pk);

		newFriendlyURLEntryMapping.setMvccVersion(RandomTestUtil.nextLong());

		newFriendlyURLEntryMapping.setFriendlyURLEntryId(RandomTestUtil.nextLong());

		_friendlyURLEntryMappings.add(_persistence.update(
				newFriendlyURLEntryMapping));

		FriendlyURLEntryMapping existingFriendlyURLEntryMapping = _persistence.findByPrimaryKey(newFriendlyURLEntryMapping.getPrimaryKey());

		Assert.assertEquals(existingFriendlyURLEntryMapping.getMvccVersion(),
			newFriendlyURLEntryMapping.getMvccVersion());
		Assert.assertEquals(existingFriendlyURLEntryMapping.getClassNameId(),
			newFriendlyURLEntryMapping.getClassNameId());
		Assert.assertEquals(existingFriendlyURLEntryMapping.getClassPK(),
			newFriendlyURLEntryMapping.getClassPK());
		Assert.assertEquals(existingFriendlyURLEntryMapping.getFriendlyURLEntryId(),
			newFriendlyURLEntryMapping.getFriendlyURLEntryId());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FriendlyURLEntryMapping newFriendlyURLEntryMapping = addFriendlyURLEntryMapping();

		FriendlyURLEntryMapping existingFriendlyURLEntryMapping = _persistence.findByPrimaryKey(newFriendlyURLEntryMapping.getPrimaryKey());

		Assert.assertEquals(existingFriendlyURLEntryMapping,
			newFriendlyURLEntryMapping);
	}

	@Test(expected = NoSuchFriendlyURLEntryMappingException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		FriendlyURLEntryMappingPK pk = new FriendlyURLEntryMappingPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FriendlyURLEntryMapping newFriendlyURLEntryMapping = addFriendlyURLEntryMapping();

		FriendlyURLEntryMapping existingFriendlyURLEntryMapping = _persistence.fetchByPrimaryKey(newFriendlyURLEntryMapping.getPrimaryKey());

		Assert.assertEquals(existingFriendlyURLEntryMapping,
			newFriendlyURLEntryMapping);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		FriendlyURLEntryMappingPK pk = new FriendlyURLEntryMappingPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

		FriendlyURLEntryMapping missingFriendlyURLEntryMapping = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFriendlyURLEntryMapping);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		FriendlyURLEntryMapping newFriendlyURLEntryMapping1 = addFriendlyURLEntryMapping();
		FriendlyURLEntryMapping newFriendlyURLEntryMapping2 = addFriendlyURLEntryMapping();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURLEntryMapping1.getPrimaryKey());
		primaryKeys.add(newFriendlyURLEntryMapping2.getPrimaryKey());

		Map<Serializable, FriendlyURLEntryMapping> friendlyURLEntryMappings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, friendlyURLEntryMappings.size());
		Assert.assertEquals(newFriendlyURLEntryMapping1,
			friendlyURLEntryMappings.get(
				newFriendlyURLEntryMapping1.getPrimaryKey()));
		Assert.assertEquals(newFriendlyURLEntryMapping2,
			friendlyURLEntryMappings.get(
				newFriendlyURLEntryMapping2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		FriendlyURLEntryMappingPK pk1 = new FriendlyURLEntryMappingPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

		FriendlyURLEntryMappingPK pk2 = new FriendlyURLEntryMappingPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FriendlyURLEntryMapping> friendlyURLEntryMappings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(friendlyURLEntryMappings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		FriendlyURLEntryMapping newFriendlyURLEntryMapping = addFriendlyURLEntryMapping();

		FriendlyURLEntryMappingPK pk = new FriendlyURLEntryMappingPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURLEntryMapping.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FriendlyURLEntryMapping> friendlyURLEntryMappings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, friendlyURLEntryMappings.size());
		Assert.assertEquals(newFriendlyURLEntryMapping,
			friendlyURLEntryMappings.get(
				newFriendlyURLEntryMapping.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FriendlyURLEntryMapping> friendlyURLEntryMappings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(friendlyURLEntryMappings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		FriendlyURLEntryMapping newFriendlyURLEntryMapping = addFriendlyURLEntryMapping();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURLEntryMapping.getPrimaryKey());

		Map<Serializable, FriendlyURLEntryMapping> friendlyURLEntryMappings = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, friendlyURLEntryMappings.size());
		Assert.assertEquals(newFriendlyURLEntryMapping,
			friendlyURLEntryMappings.get(
				newFriendlyURLEntryMapping.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		FriendlyURLEntryMapping newFriendlyURLEntryMapping = addFriendlyURLEntryMapping();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURLEntryMapping.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.classNameId",
				newFriendlyURLEntryMapping.getClassNameId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.classPK",
				newFriendlyURLEntryMapping.getClassPK()));

		List<FriendlyURLEntryMapping> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		FriendlyURLEntryMapping existingFriendlyURLEntryMapping = result.get(0);

		Assert.assertEquals(existingFriendlyURLEntryMapping,
			newFriendlyURLEntryMapping);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURLEntryMapping.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.classNameId",
				RandomTestUtil.nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.classPK",
				RandomTestUtil.nextLong()));

		List<FriendlyURLEntryMapping> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		FriendlyURLEntryMapping newFriendlyURLEntryMapping = addFriendlyURLEntryMapping();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURLEntryMapping.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.classNameId"));

		Object newClassNameId = newFriendlyURLEntryMapping.getClassNameId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.classNameId",
				new Object[] { newClassNameId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingClassNameId = result.get(0);

		Assert.assertEquals(existingClassNameId, newClassNameId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURLEntryMapping.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.classNameId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.classNameId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected FriendlyURLEntryMapping addFriendlyURLEntryMapping()
		throws Exception {
		FriendlyURLEntryMappingPK pk = new FriendlyURLEntryMappingPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

		FriendlyURLEntryMapping friendlyURLEntryMapping = _persistence.create(pk);

		friendlyURLEntryMapping.setMvccVersion(RandomTestUtil.nextLong());

		friendlyURLEntryMapping.setFriendlyURLEntryId(RandomTestUtil.nextLong());

		_friendlyURLEntryMappings.add(_persistence.update(
				friendlyURLEntryMapping));

		return friendlyURLEntryMapping;
	}

	private List<FriendlyURLEntryMapping> _friendlyURLEntryMappings = new ArrayList<FriendlyURLEntryMapping>();
	private FriendlyURLEntryMappingPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}