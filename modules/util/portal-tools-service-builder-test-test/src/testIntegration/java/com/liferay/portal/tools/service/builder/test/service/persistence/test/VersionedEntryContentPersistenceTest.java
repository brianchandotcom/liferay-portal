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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentException;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryContent;
import com.liferay.portal.tools.service.builder.test.service.persistence.VersionedEntryContentPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.VersionedEntryContentUtil;

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
public class VersionedEntryContentPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.portal.tools.service.builder.test.service"));

	@Before
	public void setUp() {
		_persistence = VersionedEntryContentUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<VersionedEntryContent> iterator = _versionedEntryContents.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VersionedEntryContent versionedEntryContent = _persistence.create(pk);

		Assert.assertNotNull(versionedEntryContent);

		Assert.assertEquals(versionedEntryContent.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		VersionedEntryContent newVersionedEntryContent = addVersionedEntryContent();

		_persistence.remove(newVersionedEntryContent);

		VersionedEntryContent existingVersionedEntryContent = _persistence.fetchByPrimaryKey(newVersionedEntryContent.getPrimaryKey());

		Assert.assertNull(existingVersionedEntryContent);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addVersionedEntryContent();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VersionedEntryContent newVersionedEntryContent = _persistence.create(pk);

		newVersionedEntryContent.setMvccVersion(RandomTestUtil.nextLong());

		newVersionedEntryContent.setVersionedEntryId(RandomTestUtil.nextLong());

		newVersionedEntryContent.setLanguageId(RandomTestUtil.randomString());

		newVersionedEntryContent.setContent(RandomTestUtil.randomString());

		newVersionedEntryContent.setHeadId(RandomTestUtil.nextLong());

		_versionedEntryContents.add(_persistence.update(
				newVersionedEntryContent));

		VersionedEntryContent existingVersionedEntryContent = _persistence.findByPrimaryKey(newVersionedEntryContent.getPrimaryKey());

		Assert.assertEquals(existingVersionedEntryContent.getMvccVersion(),
			newVersionedEntryContent.getMvccVersion());
		Assert.assertEquals(existingVersionedEntryContent.getVersionedEntryContentId(),
			newVersionedEntryContent.getVersionedEntryContentId());
		Assert.assertEquals(existingVersionedEntryContent.getVersionedEntryId(),
			newVersionedEntryContent.getVersionedEntryId());
		Assert.assertEquals(existingVersionedEntryContent.getLanguageId(),
			newVersionedEntryContent.getLanguageId());
		Assert.assertEquals(existingVersionedEntryContent.getContent(),
			newVersionedEntryContent.getContent());
		Assert.assertEquals(existingVersionedEntryContent.getHeadId(),
			newVersionedEntryContent.getHeadId());
	}

	@Test
	public void testCountByVersionedEntryId() throws Exception {
		_persistence.countByVersionedEntryId(RandomTestUtil.nextLong());

		_persistence.countByVersionedEntryId(0L);
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
	public void testCountByHeadId() throws Exception {
		_persistence.countByHeadId(RandomTestUtil.nextLong());

		_persistence.countByHeadId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		VersionedEntryContent newVersionedEntryContent = addVersionedEntryContent();

		VersionedEntryContent existingVersionedEntryContent = _persistence.findByPrimaryKey(newVersionedEntryContent.getPrimaryKey());

		Assert.assertEquals(existingVersionedEntryContent,
			newVersionedEntryContent);
	}

	@Test(expected = NoSuchVersionedEntryContentException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<VersionedEntryContent> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("VersionedEntryContent",
			"mvccVersion", true, "versionedEntryContentId", true,
			"versionedEntryId", true, "languageId", true, "content", true,
			"headId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		VersionedEntryContent newVersionedEntryContent = addVersionedEntryContent();

		VersionedEntryContent existingVersionedEntryContent = _persistence.fetchByPrimaryKey(newVersionedEntryContent.getPrimaryKey());

		Assert.assertEquals(existingVersionedEntryContent,
			newVersionedEntryContent);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VersionedEntryContent missingVersionedEntryContent = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingVersionedEntryContent);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		VersionedEntryContent newVersionedEntryContent1 = addVersionedEntryContent();
		VersionedEntryContent newVersionedEntryContent2 = addVersionedEntryContent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVersionedEntryContent1.getPrimaryKey());
		primaryKeys.add(newVersionedEntryContent2.getPrimaryKey());

		Map<Serializable, VersionedEntryContent> versionedEntryContents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, versionedEntryContents.size());
		Assert.assertEquals(newVersionedEntryContent1,
			versionedEntryContents.get(
				newVersionedEntryContent1.getPrimaryKey()));
		Assert.assertEquals(newVersionedEntryContent2,
			versionedEntryContents.get(
				newVersionedEntryContent2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, VersionedEntryContent> versionedEntryContents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(versionedEntryContents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		VersionedEntryContent newVersionedEntryContent = addVersionedEntryContent();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVersionedEntryContent.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, VersionedEntryContent> versionedEntryContents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, versionedEntryContents.size());
		Assert.assertEquals(newVersionedEntryContent,
			versionedEntryContents.get(newVersionedEntryContent.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, VersionedEntryContent> versionedEntryContents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(versionedEntryContents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		VersionedEntryContent newVersionedEntryContent = addVersionedEntryContent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVersionedEntryContent.getPrimaryKey());

		Map<Serializable, VersionedEntryContent> versionedEntryContents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, versionedEntryContents.size());
		Assert.assertEquals(newVersionedEntryContent,
			versionedEntryContents.get(newVersionedEntryContent.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		VersionedEntryContent newVersionedEntryContent = addVersionedEntryContent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VersionedEntryContent.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("versionedEntryContentId",
				newVersionedEntryContent.getVersionedEntryContentId()));

		List<VersionedEntryContent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		VersionedEntryContent existingVersionedEntryContent = result.get(0);

		Assert.assertEquals(existingVersionedEntryContent,
			newVersionedEntryContent);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VersionedEntryContent.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("versionedEntryContentId",
				RandomTestUtil.nextLong()));

		List<VersionedEntryContent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		VersionedEntryContent newVersionedEntryContent = addVersionedEntryContent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VersionedEntryContent.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"versionedEntryContentId"));

		Object newVersionedEntryContentId = newVersionedEntryContent.getVersionedEntryContentId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("versionedEntryContentId",
				new Object[] { newVersionedEntryContentId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingVersionedEntryContentId = result.get(0);

		Assert.assertEquals(existingVersionedEntryContentId,
			newVersionedEntryContentId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VersionedEntryContent.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"versionedEntryContentId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("versionedEntryContentId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		VersionedEntryContent newVersionedEntryContent = addVersionedEntryContent();

		_persistence.clearCache();

		VersionedEntryContent existingVersionedEntryContent = _persistence.findByPrimaryKey(newVersionedEntryContent.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingVersionedEntryContent.getVersionedEntryId()),
			ReflectionTestUtil.<Long>invoke(existingVersionedEntryContent,
				"getOriginalVersionedEntryId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingVersionedEntryContent.getLanguageId(),
				ReflectionTestUtil.invoke(existingVersionedEntryContent,
					"getOriginalLanguageId", new Class<?>[0])));

		Assert.assertEquals(Long.valueOf(
				existingVersionedEntryContent.getHeadId()),
			ReflectionTestUtil.<Long>invoke(existingVersionedEntryContent,
				"getOriginalHeadId", new Class<?>[0]));
	}

	protected VersionedEntryContent addVersionedEntryContent()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		VersionedEntryContent versionedEntryContent = _persistence.create(pk);

		versionedEntryContent.setMvccVersion(RandomTestUtil.nextLong());

		versionedEntryContent.setVersionedEntryId(RandomTestUtil.nextLong());

		versionedEntryContent.setLanguageId(RandomTestUtil.randomString());

		versionedEntryContent.setContent(RandomTestUtil.randomString());

		versionedEntryContent.setHeadId(RandomTestUtil.nextLong());

		_versionedEntryContents.add(_persistence.update(versionedEntryContent));

		return versionedEntryContent;
	}

	private List<VersionedEntryContent> _versionedEntryContents = new ArrayList<VersionedEntryContent>();
	private VersionedEntryContentPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}