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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException;
import com.liferay.portal.kernel.model.LayoutSetVersion;
import com.liferay.portal.kernel.service.persistence.LayoutSetVersionPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutSetVersionUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
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
public class LayoutSetVersionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = LayoutSetVersionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutSetVersion> iterator = _layoutSetVersions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSetVersion layoutSetVersion = _persistence.create(pk);

		Assert.assertNotNull(layoutSetVersion);

		Assert.assertEquals(layoutSetVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutSetVersion newLayoutSetVersion = addLayoutSetVersion();

		_persistence.remove(newLayoutSetVersion);

		LayoutSetVersion existingLayoutSetVersion = _persistence.fetchByPrimaryKey(newLayoutSetVersion.getPrimaryKey());

		Assert.assertNull(existingLayoutSetVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutSetVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSetVersion newLayoutSetVersion = _persistence.create(pk);

		newLayoutSetVersion.setVersion(RandomTestUtil.nextInt());

		newLayoutSetVersion.setLayoutSetId(RandomTestUtil.nextLong());

		newLayoutSetVersion.setGroupId(RandomTestUtil.nextLong());

		newLayoutSetVersion.setCompanyId(RandomTestUtil.nextLong());

		newLayoutSetVersion.setCreateDate(RandomTestUtil.nextDate());

		newLayoutSetVersion.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutSetVersion.setPrivateLayout(RandomTestUtil.randomBoolean());

		newLayoutSetVersion.setLogoId(RandomTestUtil.nextLong());

		newLayoutSetVersion.setThemeId(RandomTestUtil.randomString());

		newLayoutSetVersion.setColorSchemeId(RandomTestUtil.randomString());

		newLayoutSetVersion.setCss(RandomTestUtil.randomString());

		newLayoutSetVersion.setPageCount(RandomTestUtil.nextInt());

		newLayoutSetVersion.setSettings(RandomTestUtil.randomString());

		newLayoutSetVersion.setLayoutSetPrototypeUuid(RandomTestUtil.randomString());

		newLayoutSetVersion.setLayoutSetPrototypeLinkEnabled(RandomTestUtil.randomBoolean());

		_layoutSetVersions.add(_persistence.update(newLayoutSetVersion));

		LayoutSetVersion existingLayoutSetVersion = _persistence.findByPrimaryKey(newLayoutSetVersion.getPrimaryKey());

		Assert.assertEquals(existingLayoutSetVersion.getLayoutSetVersionId(),
			newLayoutSetVersion.getLayoutSetVersionId());
		Assert.assertEquals(existingLayoutSetVersion.getVersion(),
			newLayoutSetVersion.getVersion());
		Assert.assertEquals(existingLayoutSetVersion.getLayoutSetId(),
			newLayoutSetVersion.getLayoutSetId());
		Assert.assertEquals(existingLayoutSetVersion.getGroupId(),
			newLayoutSetVersion.getGroupId());
		Assert.assertEquals(existingLayoutSetVersion.getCompanyId(),
			newLayoutSetVersion.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutSetVersion.getCreateDate()),
			Time.getShortTimestamp(newLayoutSetVersion.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutSetVersion.getModifiedDate()),
			Time.getShortTimestamp(newLayoutSetVersion.getModifiedDate()));
		Assert.assertEquals(existingLayoutSetVersion.isPrivateLayout(),
			newLayoutSetVersion.isPrivateLayout());
		Assert.assertEquals(existingLayoutSetVersion.getLogoId(),
			newLayoutSetVersion.getLogoId());
		Assert.assertEquals(existingLayoutSetVersion.getThemeId(),
			newLayoutSetVersion.getThemeId());
		Assert.assertEquals(existingLayoutSetVersion.getColorSchemeId(),
			newLayoutSetVersion.getColorSchemeId());
		Assert.assertEquals(existingLayoutSetVersion.getCss(),
			newLayoutSetVersion.getCss());
		Assert.assertEquals(existingLayoutSetVersion.getPageCount(),
			newLayoutSetVersion.getPageCount());
		Assert.assertEquals(existingLayoutSetVersion.getSettings(),
			newLayoutSetVersion.getSettings());
		Assert.assertEquals(existingLayoutSetVersion.getLayoutSetPrototypeUuid(),
			newLayoutSetVersion.getLayoutSetPrototypeUuid());
		Assert.assertEquals(existingLayoutSetVersion.isLayoutSetPrototypeLinkEnabled(),
			newLayoutSetVersion.isLayoutSetPrototypeLinkEnabled());
	}

	@Test
	public void testCountByLayoutSetId() throws Exception {
		_persistence.countByLayoutSetId(RandomTestUtil.nextLong());

		_persistence.countByLayoutSetId(0L);
	}

	@Test
	public void testCountByLayoutSetId_Version() throws Exception {
		_persistence.countByLayoutSetId_Version(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByLayoutSetId_Version(0L, 0);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByGroupId_Version() throws Exception {
		_persistence.countByGroupId_Version(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByGroupId_Version(0L, 0);
	}

	@Test
	public void testCountByLayoutSetPrototypeUuid() throws Exception {
		_persistence.countByLayoutSetPrototypeUuid("");

		_persistence.countByLayoutSetPrototypeUuid("null");

		_persistence.countByLayoutSetPrototypeUuid((String)null);
	}

	@Test
	public void testCountByLayoutSetPrototypeUuid_Version()
		throws Exception {
		_persistence.countByLayoutSetPrototypeUuid_Version("",
			RandomTestUtil.nextInt());

		_persistence.countByLayoutSetPrototypeUuid_Version("null", 0);

		_persistence.countByLayoutSetPrototypeUuid_Version((String)null, 0);
	}

	@Test
	public void testCountByG_P() throws Exception {
		_persistence.countByG_P(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_P(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_P_Version() throws Exception {
		_persistence.countByG_P_Version(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.nextInt());

		_persistence.countByG_P_Version(0L, RandomTestUtil.randomBoolean(), 0);
	}

	@Test
	public void testCountByP_L() throws Exception {
		_persistence.countByP_L(RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong());

		_persistence.countByP_L(RandomTestUtil.randomBoolean(), 0L);
	}

	@Test
	public void testCountByP_L_Version() throws Exception {
		_persistence.countByP_L_Version(RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByP_L_Version(RandomTestUtil.randomBoolean(), 0L, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutSetVersion newLayoutSetVersion = addLayoutSetVersion();

		LayoutSetVersion existingLayoutSetVersion = _persistence.findByPrimaryKey(newLayoutSetVersion.getPrimaryKey());

		Assert.assertEquals(existingLayoutSetVersion, newLayoutSetVersion);
	}

	@Test(expected = NoSuchLayoutSetVersionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<LayoutSetVersion> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("LayoutSetVersion",
			"layoutSetVersionId", true, "version", true, "layoutSetId", true,
			"groupId", true, "companyId", true, "createDate", true,
			"modifiedDate", true, "privateLayout", true, "logoId", true,
			"themeId", true, "colorSchemeId", true, "pageCount", true,
			"layoutSetPrototypeUuid", true, "layoutSetPrototypeLinkEnabled",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutSetVersion newLayoutSetVersion = addLayoutSetVersion();

		LayoutSetVersion existingLayoutSetVersion = _persistence.fetchByPrimaryKey(newLayoutSetVersion.getPrimaryKey());

		Assert.assertEquals(existingLayoutSetVersion, newLayoutSetVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSetVersion missingLayoutSetVersion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutSetVersion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		LayoutSetVersion newLayoutSetVersion1 = addLayoutSetVersion();
		LayoutSetVersion newLayoutSetVersion2 = addLayoutSetVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSetVersion1.getPrimaryKey());
		primaryKeys.add(newLayoutSetVersion2.getPrimaryKey());

		Map<Serializable, LayoutSetVersion> layoutSetVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutSetVersions.size());
		Assert.assertEquals(newLayoutSetVersion1,
			layoutSetVersions.get(newLayoutSetVersion1.getPrimaryKey()));
		Assert.assertEquals(newLayoutSetVersion2,
			layoutSetVersions.get(newLayoutSetVersion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutSetVersion> layoutSetVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSetVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		LayoutSetVersion newLayoutSetVersion = addLayoutSetVersion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSetVersion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutSetVersion> layoutSetVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSetVersions.size());
		Assert.assertEquals(newLayoutSetVersion,
			layoutSetVersions.get(newLayoutSetVersion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutSetVersion> layoutSetVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSetVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		LayoutSetVersion newLayoutSetVersion = addLayoutSetVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSetVersion.getPrimaryKey());

		Map<Serializable, LayoutSetVersion> layoutSetVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSetVersions.size());
		Assert.assertEquals(newLayoutSetVersion,
			layoutSetVersions.get(newLayoutSetVersion.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutSetVersion newLayoutSetVersion = addLayoutSetVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetVersionId",
				newLayoutSetVersion.getLayoutSetVersionId()));

		List<LayoutSetVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutSetVersion existingLayoutSetVersion = result.get(0);

		Assert.assertEquals(existingLayoutSetVersion, newLayoutSetVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetVersionId",
				RandomTestUtil.nextLong()));

		List<LayoutSetVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		LayoutSetVersion newLayoutSetVersion = addLayoutSetVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutSetVersionId"));

		Object newLayoutSetVersionId = newLayoutSetVersion.getLayoutSetVersionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutSetVersionId",
				new Object[] { newLayoutSetVersionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutSetVersionId = result.get(0);

		Assert.assertEquals(existingLayoutSetVersionId, newLayoutSetVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutSetVersionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutSetVersionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutSetVersion newLayoutSetVersion = addLayoutSetVersion();

		_persistence.clearCache();

		LayoutSetVersion existingLayoutSetVersion = _persistence.findByPrimaryKey(newLayoutSetVersion.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingLayoutSetVersion.getLayoutSetId()),
			ReflectionTestUtil.<Long>invoke(existingLayoutSetVersion,
				"getOriginalLayoutSetId", new Class<?>[0]));
		Assert.assertEquals(Integer.valueOf(
				existingLayoutSetVersion.getVersion()),
			ReflectionTestUtil.<Integer>invoke(existingLayoutSetVersion,
				"getOriginalVersion", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(existingLayoutSetVersion.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingLayoutSetVersion,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Boolean.valueOf(
				existingLayoutSetVersion.getPrivateLayout()),
			ReflectionTestUtil.<Boolean>invoke(existingLayoutSetVersion,
				"getOriginalPrivateLayout", new Class<?>[0]));
		Assert.assertEquals(Integer.valueOf(
				existingLayoutSetVersion.getVersion()),
			ReflectionTestUtil.<Integer>invoke(existingLayoutSetVersion,
				"getOriginalVersion", new Class<?>[0]));
	}

	protected LayoutSetVersion addLayoutSetVersion() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSetVersion layoutSetVersion = _persistence.create(pk);

		layoutSetVersion.setVersion(RandomTestUtil.nextInt());

		layoutSetVersion.setLayoutSetId(RandomTestUtil.nextLong());

		layoutSetVersion.setGroupId(RandomTestUtil.nextLong());

		layoutSetVersion.setCompanyId(RandomTestUtil.nextLong());

		layoutSetVersion.setCreateDate(RandomTestUtil.nextDate());

		layoutSetVersion.setModifiedDate(RandomTestUtil.nextDate());

		layoutSetVersion.setPrivateLayout(RandomTestUtil.randomBoolean());

		layoutSetVersion.setLogoId(RandomTestUtil.nextLong());

		layoutSetVersion.setThemeId(RandomTestUtil.randomString());

		layoutSetVersion.setColorSchemeId(RandomTestUtil.randomString());

		layoutSetVersion.setCss(RandomTestUtil.randomString());

		layoutSetVersion.setPageCount(RandomTestUtil.nextInt());

		layoutSetVersion.setSettings(RandomTestUtil.randomString());

		layoutSetVersion.setLayoutSetPrototypeUuid(RandomTestUtil.randomString());

		layoutSetVersion.setLayoutSetPrototypeLinkEnabled(RandomTestUtil.randomBoolean());

		_layoutSetVersions.add(_persistence.update(layoutSetVersion));

		return layoutSetVersion;
	}

	private List<LayoutSetVersion> _layoutSetVersions = new ArrayList<LayoutSetVersion>();
	private LayoutSetVersionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}