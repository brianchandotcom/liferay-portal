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

package com.liferay.portal.tools.service.builder.test.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryContentVersion;
import com.liferay.portal.tools.service.builder.test.service.persistence.VersionedEntryContentVersionPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.VersionedEntryContentVersionUtil;

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
public class VersionedEntryContentVersionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.portal.tools.service.builder.test.service"));

	@Before
	public void setUp() {
		_persistence = VersionedEntryContentVersionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<VersionedEntryContentVersion> iterator = _versionedEntryContentVersions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VersionedEntryContentVersion versionedEntryContentVersion = _persistence.create(pk);

		Assert.assertNotNull(versionedEntryContentVersion);

		Assert.assertEquals(versionedEntryContentVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		VersionedEntryContentVersion newVersionedEntryContentVersion = addVersionedEntryContentVersion();

		_persistence.remove(newVersionedEntryContentVersion);

		VersionedEntryContentVersion existingVersionedEntryContentVersion = _persistence.fetchByPrimaryKey(newVersionedEntryContentVersion.getPrimaryKey());

		Assert.assertNull(existingVersionedEntryContentVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addVersionedEntryContentVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VersionedEntryContentVersion newVersionedEntryContentVersion = _persistence.create(pk);

		newVersionedEntryContentVersion.setVersion(RandomTestUtil.nextInt());

		newVersionedEntryContentVersion.setVersionedEntryContentId(RandomTestUtil.nextLong());

		newVersionedEntryContentVersion.setVersionedEntryId(RandomTestUtil.nextLong());

		newVersionedEntryContentVersion.setLanguageId(RandomTestUtil.randomString());

		newVersionedEntryContentVersion.setContent(RandomTestUtil.randomString());

		_versionedEntryContentVersions.add(_persistence.update(
				newVersionedEntryContentVersion));

		VersionedEntryContentVersion existingVersionedEntryContentVersion = _persistence.findByPrimaryKey(newVersionedEntryContentVersion.getPrimaryKey());

		Assert.assertEquals(existingVersionedEntryContentVersion.getVersionedEntryContentVersionId(),
			newVersionedEntryContentVersion.getVersionedEntryContentVersionId());
		Assert.assertEquals(existingVersionedEntryContentVersion.getVersion(),
			newVersionedEntryContentVersion.getVersion());
		Assert.assertEquals(existingVersionedEntryContentVersion.getVersionedEntryContentId(),
			newVersionedEntryContentVersion.getVersionedEntryContentId());
		Assert.assertEquals(existingVersionedEntryContentVersion.getVersionedEntryId(),
			newVersionedEntryContentVersion.getVersionedEntryId());
		Assert.assertEquals(existingVersionedEntryContentVersion.getLanguageId(),
			newVersionedEntryContentVersion.getLanguageId());
		Assert.assertEquals(existingVersionedEntryContentVersion.getContent(),
			newVersionedEntryContentVersion.getContent());
	}

	@Test
	public void testCountByVersionedEntryContentId() throws Exception {
		_persistence.countByVersionedEntryContentId(RandomTestUtil.nextLong());

		_persistence.countByVersionedEntryContentId(0L);
	}

	@Test
	public void testCountByVersionedEntryContentId_Version()
		throws Exception {
		_persistence.countByVersionedEntryContentId_Version(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByVersionedEntryContentId_Version(0L, 0);
	}

	@Test
	public void testCountByVersionedEntryId() throws Exception {
		_persistence.countByVersionedEntryId(RandomTestUtil.nextLong());

		_persistence.countByVersionedEntryId(0L);
	}

	@Test
	public void testCountByVersionedEntryId_Version() throws Exception {
		_persistence.countByVersionedEntryId_Version(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByVersionedEntryId_Version(0L, 0);
	}

	@Test
	public void testCountByVersionedEntryId_LanguageId()
		throws Exception {
		_persistence.countByVersionedEntryId_LanguageId(RandomTestUtil.nextLong(),
			"");

		_persistence.countByVersionedEntryId_LanguageId(0L, "null");

		_persistence.countByVersionedEntryId_LanguageId(0L, (String)null);
	}

	@Test
	public void testCountByVersionedEntryId_LanguageId_Version()
		throws Exception {
		_persistence.countByVersionedEntryId_LanguageId_Version(RandomTestUtil.nextLong(),
			"", RandomTestUtil.nextInt());

		_persistence.countByVersionedEntryId_LanguageId_Version(0L, "null", 0);

		_persistence.countByVersionedEntryId_LanguageId_Version(0L,
			(String)null, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		VersionedEntryContentVersion newVersionedEntryContentVersion = addVersionedEntryContentVersion();

		VersionedEntryContentVersion existingVersionedEntryContentVersion = _persistence.findByPrimaryKey(newVersionedEntryContentVersion.getPrimaryKey());

		Assert.assertEquals(existingVersionedEntryContentVersion,
			newVersionedEntryContentVersion);
	}

	@Test(expected = NoSuchVersionedEntryContentVersionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<VersionedEntryContentVersion> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("VersionedEntryContentVersion",
			"versionedEntryContentVersionId", true, "version", true,
			"versionedEntryContentId", true, "versionedEntryId", true,
			"languageId", true, "content", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		VersionedEntryContentVersion newVersionedEntryContentVersion = addVersionedEntryContentVersion();

		VersionedEntryContentVersion existingVersionedEntryContentVersion = _persistence.fetchByPrimaryKey(newVersionedEntryContentVersion.getPrimaryKey());

		Assert.assertEquals(existingVersionedEntryContentVersion,
			newVersionedEntryContentVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VersionedEntryContentVersion missingVersionedEntryContentVersion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingVersionedEntryContentVersion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		VersionedEntryContentVersion newVersionedEntryContentVersion1 = addVersionedEntryContentVersion();
		VersionedEntryContentVersion newVersionedEntryContentVersion2 = addVersionedEntryContentVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVersionedEntryContentVersion1.getPrimaryKey());
		primaryKeys.add(newVersionedEntryContentVersion2.getPrimaryKey());

		Map<Serializable, VersionedEntryContentVersion> versionedEntryContentVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, versionedEntryContentVersions.size());
		Assert.assertEquals(newVersionedEntryContentVersion1,
			versionedEntryContentVersions.get(
				newVersionedEntryContentVersion1.getPrimaryKey()));
		Assert.assertEquals(newVersionedEntryContentVersion2,
			versionedEntryContentVersions.get(
				newVersionedEntryContentVersion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, VersionedEntryContentVersion> versionedEntryContentVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(versionedEntryContentVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		VersionedEntryContentVersion newVersionedEntryContentVersion = addVersionedEntryContentVersion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVersionedEntryContentVersion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, VersionedEntryContentVersion> versionedEntryContentVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, versionedEntryContentVersions.size());
		Assert.assertEquals(newVersionedEntryContentVersion,
			versionedEntryContentVersions.get(
				newVersionedEntryContentVersion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, VersionedEntryContentVersion> versionedEntryContentVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(versionedEntryContentVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		VersionedEntryContentVersion newVersionedEntryContentVersion = addVersionedEntryContentVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVersionedEntryContentVersion.getPrimaryKey());

		Map<Serializable, VersionedEntryContentVersion> versionedEntryContentVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, versionedEntryContentVersions.size());
		Assert.assertEquals(newVersionedEntryContentVersion,
			versionedEntryContentVersions.get(
				newVersionedEntryContentVersion.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		VersionedEntryContentVersion newVersionedEntryContentVersion = addVersionedEntryContentVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VersionedEntryContentVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"versionedEntryContentVersionId",
				newVersionedEntryContentVersion.getVersionedEntryContentVersionId()));

		List<VersionedEntryContentVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		VersionedEntryContentVersion existingVersionedEntryContentVersion = result.get(0);

		Assert.assertEquals(existingVersionedEntryContentVersion,
			newVersionedEntryContentVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VersionedEntryContentVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"versionedEntryContentVersionId", RandomTestUtil.nextLong()));

		List<VersionedEntryContentVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		VersionedEntryContentVersion newVersionedEntryContentVersion = addVersionedEntryContentVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VersionedEntryContentVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"versionedEntryContentVersionId"));

		Object newVersionedEntryContentVersionId = newVersionedEntryContentVersion.getVersionedEntryContentVersionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"versionedEntryContentVersionId",
				new Object[] { newVersionedEntryContentVersionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingVersionedEntryContentVersionId = result.get(0);

		Assert.assertEquals(existingVersionedEntryContentVersionId,
			newVersionedEntryContentVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VersionedEntryContentVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"versionedEntryContentVersionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"versionedEntryContentVersionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		VersionedEntryContentVersion newVersionedEntryContentVersion = addVersionedEntryContentVersion();

		_persistence.clearCache();

		VersionedEntryContentVersion existingVersionedEntryContentVersion = _persistence.findByPrimaryKey(newVersionedEntryContentVersion.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingVersionedEntryContentVersion.getVersionedEntryContentId()),
			ReflectionTestUtil.<Long>invoke(
				existingVersionedEntryContentVersion,
				"getOriginalVersionedEntryContentId", new Class<?>[0]));
		Assert.assertEquals(Integer.valueOf(
				existingVersionedEntryContentVersion.getVersion()),
			ReflectionTestUtil.<Integer>invoke(
				existingVersionedEntryContentVersion, "getOriginalVersion",
				new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingVersionedEntryContentVersion.getVersionedEntryId()),
			ReflectionTestUtil.<Long>invoke(
				existingVersionedEntryContentVersion,
				"getOriginalVersionedEntryId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingVersionedEntryContentVersion.getLanguageId(),
				ReflectionTestUtil.invoke(
					existingVersionedEntryContentVersion,
					"getOriginalLanguageId", new Class<?>[0])));
		Assert.assertEquals(Integer.valueOf(
				existingVersionedEntryContentVersion.getVersion()),
			ReflectionTestUtil.<Integer>invoke(
				existingVersionedEntryContentVersion, "getOriginalVersion",
				new Class<?>[0]));
	}

	protected VersionedEntryContentVersion addVersionedEntryContentVersion()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		VersionedEntryContentVersion versionedEntryContentVersion = _persistence.create(pk);

		versionedEntryContentVersion.setVersion(RandomTestUtil.nextInt());

		versionedEntryContentVersion.setVersionedEntryContentId(RandomTestUtil.nextLong());

		versionedEntryContentVersion.setVersionedEntryId(RandomTestUtil.nextLong());

		versionedEntryContentVersion.setLanguageId(RandomTestUtil.randomString());

		versionedEntryContentVersion.setContent(RandomTestUtil.randomString());

		_versionedEntryContentVersions.add(_persistence.update(
				versionedEntryContentVersion));

		return versionedEntryContentVersion;
	}

	private List<VersionedEntryContentVersion> _versionedEntryContentVersions = new ArrayList<VersionedEntryContentVersion>();
	private VersionedEntryContentVersionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}