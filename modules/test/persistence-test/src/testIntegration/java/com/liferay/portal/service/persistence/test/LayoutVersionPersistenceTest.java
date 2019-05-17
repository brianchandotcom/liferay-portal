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
import com.liferay.portal.kernel.exception.NoSuchLayoutVersionException;
import com.liferay.portal.kernel.model.LayoutVersion;
import com.liferay.portal.kernel.service.persistence.LayoutVersionPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutVersionUtil;
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
public class LayoutVersionPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = LayoutVersionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutVersion> iterator = _layoutVersions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutVersion layoutVersion = _persistence.create(pk);

		Assert.assertNotNull(layoutVersion);

		Assert.assertEquals(layoutVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutVersion newLayoutVersion = addLayoutVersion();

		_persistence.remove(newLayoutVersion);

		LayoutVersion existingLayoutVersion = _persistence.fetchByPrimaryKey(
			newLayoutVersion.getPrimaryKey());

		Assert.assertNull(existingLayoutVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutVersion newLayoutVersion = _persistence.create(pk);

		newLayoutVersion.setVersion(RandomTestUtil.nextInt());

		newLayoutVersion.setUuid(RandomTestUtil.randomString());

		newLayoutVersion.setPlid(RandomTestUtil.nextLong());

		newLayoutVersion.setGroupId(RandomTestUtil.nextLong());

		newLayoutVersion.setCompanyId(RandomTestUtil.nextLong());

		newLayoutVersion.setUserId(RandomTestUtil.nextLong());

		newLayoutVersion.setUserName(RandomTestUtil.randomString());

		newLayoutVersion.setCreateDate(RandomTestUtil.nextDate());

		newLayoutVersion.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutVersion.setParentPlid(RandomTestUtil.nextLong());

		newLayoutVersion.setPrivateLayout(RandomTestUtil.randomBoolean());

		newLayoutVersion.setLayoutId(RandomTestUtil.nextLong());

		newLayoutVersion.setParentLayoutId(RandomTestUtil.nextLong());

		newLayoutVersion.setClassNameId(RandomTestUtil.nextLong());

		newLayoutVersion.setClassPK(RandomTestUtil.nextLong());

		newLayoutVersion.setName(RandomTestUtil.randomString());

		newLayoutVersion.setTitle(RandomTestUtil.randomString());

		newLayoutVersion.setDescription(RandomTestUtil.randomString());

		newLayoutVersion.setKeywords(RandomTestUtil.randomString());

		newLayoutVersion.setRobots(RandomTestUtil.randomString());

		newLayoutVersion.setType(RandomTestUtil.randomString());

		newLayoutVersion.setTypeSettings(RandomTestUtil.randomString());

		newLayoutVersion.setHidden(RandomTestUtil.randomBoolean());

		newLayoutVersion.setSystem(RandomTestUtil.randomBoolean());

		newLayoutVersion.setFriendlyURL(RandomTestUtil.randomString());

		newLayoutVersion.setIconImageId(RandomTestUtil.nextLong());

		newLayoutVersion.setThemeId(RandomTestUtil.randomString());

		newLayoutVersion.setColorSchemeId(RandomTestUtil.randomString());

		newLayoutVersion.setCss(RandomTestUtil.randomString());

		newLayoutVersion.setPriority(RandomTestUtil.nextInt());

		newLayoutVersion.setLayoutPrototypeUuid(RandomTestUtil.randomString());

		newLayoutVersion.setLayoutPrototypeLinkEnabled(
			RandomTestUtil.randomBoolean());

		newLayoutVersion.setSourcePrototypeLayoutUuid(
			RandomTestUtil.randomString());

		newLayoutVersion.setPublishDate(RandomTestUtil.nextDate());

		newLayoutVersion.setLastPublishDate(RandomTestUtil.nextDate());

		_layoutVersions.add(_persistence.update(newLayoutVersion));

		LayoutVersion existingLayoutVersion = _persistence.findByPrimaryKey(
			newLayoutVersion.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutVersion.getLayoutVersionId(),
			newLayoutVersion.getLayoutVersionId());
		Assert.assertEquals(
			existingLayoutVersion.getVersion(), newLayoutVersion.getVersion());
		Assert.assertEquals(
			existingLayoutVersion.getUuid(), newLayoutVersion.getUuid());
		Assert.assertEquals(
			existingLayoutVersion.getPlid(), newLayoutVersion.getPlid());
		Assert.assertEquals(
			existingLayoutVersion.getGroupId(), newLayoutVersion.getGroupId());
		Assert.assertEquals(
			existingLayoutVersion.getCompanyId(),
			newLayoutVersion.getCompanyId());
		Assert.assertEquals(
			existingLayoutVersion.getUserId(), newLayoutVersion.getUserId());
		Assert.assertEquals(
			existingLayoutVersion.getUserName(),
			newLayoutVersion.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingLayoutVersion.getCreateDate()),
			Time.getShortTimestamp(newLayoutVersion.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingLayoutVersion.getModifiedDate()),
			Time.getShortTimestamp(newLayoutVersion.getModifiedDate()));
		Assert.assertEquals(
			existingLayoutVersion.getParentPlid(),
			newLayoutVersion.getParentPlid());
		Assert.assertEquals(
			existingLayoutVersion.isPrivateLayout(),
			newLayoutVersion.isPrivateLayout());
		Assert.assertEquals(
			existingLayoutVersion.getLayoutId(),
			newLayoutVersion.getLayoutId());
		Assert.assertEquals(
			existingLayoutVersion.getParentLayoutId(),
			newLayoutVersion.getParentLayoutId());
		Assert.assertEquals(
			existingLayoutVersion.getClassNameId(),
			newLayoutVersion.getClassNameId());
		Assert.assertEquals(
			existingLayoutVersion.getClassPK(), newLayoutVersion.getClassPK());
		Assert.assertEquals(
			existingLayoutVersion.getName(), newLayoutVersion.getName());
		Assert.assertEquals(
			existingLayoutVersion.getTitle(), newLayoutVersion.getTitle());
		Assert.assertEquals(
			existingLayoutVersion.getDescription(),
			newLayoutVersion.getDescription());
		Assert.assertEquals(
			existingLayoutVersion.getKeywords(),
			newLayoutVersion.getKeywords());
		Assert.assertEquals(
			existingLayoutVersion.getRobots(), newLayoutVersion.getRobots());
		Assert.assertEquals(
			existingLayoutVersion.getType(), newLayoutVersion.getType());
		Assert.assertEquals(
			existingLayoutVersion.getTypeSettings(),
			newLayoutVersion.getTypeSettings());
		Assert.assertEquals(
			existingLayoutVersion.isHidden(), newLayoutVersion.isHidden());
		Assert.assertEquals(
			existingLayoutVersion.isSystem(), newLayoutVersion.isSystem());
		Assert.assertEquals(
			existingLayoutVersion.getFriendlyURL(),
			newLayoutVersion.getFriendlyURL());
		Assert.assertEquals(
			existingLayoutVersion.getIconImageId(),
			newLayoutVersion.getIconImageId());
		Assert.assertEquals(
			existingLayoutVersion.getThemeId(), newLayoutVersion.getThemeId());
		Assert.assertEquals(
			existingLayoutVersion.getColorSchemeId(),
			newLayoutVersion.getColorSchemeId());
		Assert.assertEquals(
			existingLayoutVersion.getCss(), newLayoutVersion.getCss());
		Assert.assertEquals(
			existingLayoutVersion.getPriority(),
			newLayoutVersion.getPriority());
		Assert.assertEquals(
			existingLayoutVersion.getLayoutPrototypeUuid(),
			newLayoutVersion.getLayoutPrototypeUuid());
		Assert.assertEquals(
			existingLayoutVersion.isLayoutPrototypeLinkEnabled(),
			newLayoutVersion.isLayoutPrototypeLinkEnabled());
		Assert.assertEquals(
			existingLayoutVersion.getSourcePrototypeLayoutUuid(),
			newLayoutVersion.getSourcePrototypeLayoutUuid());
		Assert.assertEquals(
			Time.getShortTimestamp(existingLayoutVersion.getPublishDate()),
			Time.getShortTimestamp(newLayoutVersion.getPublishDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingLayoutVersion.getLastPublishDate()),
			Time.getShortTimestamp(newLayoutVersion.getLastPublishDate()));
	}

	@Test
	public void testCountByPlid() throws Exception {
		_persistence.countByPlid(RandomTestUtil.nextLong());

		_persistence.countByPlid(0L);
	}

	@Test
	public void testCountByPlid_Version() throws Exception {
		_persistence.countByPlid_Version(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByPlid_Version(0L, 0);
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_Version() throws Exception {
		_persistence.countByUuid_Version("", RandomTestUtil.nextInt());

		_persistence.countByUuid_Version("null", 0);

		_persistence.countByUuid_Version((String)null, 0);
	}

	@Test
	public void testCountByUUID_G_P() throws Exception {
		_persistence.countByUUID_G_P(
			"", RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByUUID_G_P(
			"null", 0L, RandomTestUtil.randomBoolean());

		_persistence.countByUUID_G_P(
			(String)null, 0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByUUID_G_P_Version() throws Exception {
		_persistence.countByUUID_G_P_Version(
			"", RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextInt());

		_persistence.countByUUID_G_P_Version(
			"null", 0L, RandomTestUtil.randomBoolean(), 0);

		_persistence.countByUUID_G_P_Version(
			(String)null, 0L, RandomTestUtil.randomBoolean(), 0);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C_Version() throws Exception {
		_persistence.countByUuid_C_Version(
			"", RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByUuid_C_Version("null", 0L, 0);

		_persistence.countByUuid_C_Version((String)null, 0L, 0);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByGroupId_Version() throws Exception {
		_persistence.countByGroupId_Version(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByGroupId_Version(0L, 0);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByCompanyId_Version() throws Exception {
		_persistence.countByCompanyId_Version(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByCompanyId_Version(0L, 0);
	}

	@Test
	public void testCountByParentPlid() throws Exception {
		_persistence.countByParentPlid(RandomTestUtil.nextLong());

		_persistence.countByParentPlid(0L);
	}

	@Test
	public void testCountByParentPlid_Version() throws Exception {
		_persistence.countByParentPlid_Version(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByParentPlid_Version(0L, 0);
	}

	@Test
	public void testCountByIconImageId() throws Exception {
		_persistence.countByIconImageId(RandomTestUtil.nextLong());

		_persistence.countByIconImageId(0L);
	}

	@Test
	public void testCountByIconImageId_Version() throws Exception {
		_persistence.countByIconImageId_Version(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByIconImageId_Version(0L, 0);
	}

	@Test
	public void testCountByLayoutPrototypeUuid() throws Exception {
		_persistence.countByLayoutPrototypeUuid("");

		_persistence.countByLayoutPrototypeUuid("null");

		_persistence.countByLayoutPrototypeUuid((String)null);
	}

	@Test
	public void testCountByLayoutPrototypeUuid_Version() throws Exception {
		_persistence.countByLayoutPrototypeUuid_Version(
			"", RandomTestUtil.nextInt());

		_persistence.countByLayoutPrototypeUuid_Version("null", 0);

		_persistence.countByLayoutPrototypeUuid_Version((String)null, 0);
	}

	@Test
	public void testCountBySourcePrototypeLayoutUuid() throws Exception {
		_persistence.countBySourcePrototypeLayoutUuid("");

		_persistence.countBySourcePrototypeLayoutUuid("null");

		_persistence.countBySourcePrototypeLayoutUuid((String)null);
	}

	@Test
	public void testCountBySourcePrototypeLayoutUuid_Version()
		throws Exception {

		_persistence.countBySourcePrototypeLayoutUuid_Version(
			"", RandomTestUtil.nextInt());

		_persistence.countBySourcePrototypeLayoutUuid_Version("null", 0);

		_persistence.countBySourcePrototypeLayoutUuid_Version((String)null, 0);
	}

	@Test
	public void testCountByG_P() throws Exception {
		_persistence.countByG_P(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByG_P(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_P_Version() throws Exception {
		_persistence.countByG_P_Version(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextInt());

		_persistence.countByG_P_Version(0L, RandomTestUtil.randomBoolean(), 0);
	}

	@Test
	public void testCountByG_T() throws Exception {
		_persistence.countByG_T(RandomTestUtil.nextLong(), "");

		_persistence.countByG_T(0L, "null");

		_persistence.countByG_T(0L, (String)null);
	}

	@Test
	public void testCountByG_T_Version() throws Exception {
		_persistence.countByG_T_Version(
			RandomTestUtil.nextLong(), "", RandomTestUtil.nextInt());

		_persistence.countByG_T_Version(0L, "null", 0);

		_persistence.countByG_T_Version(0L, (String)null, 0);
	}

	@Test
	public void testCountByC_L() throws Exception {
		_persistence.countByC_L(RandomTestUtil.nextLong(), "");

		_persistence.countByC_L(0L, "null");

		_persistence.countByC_L(0L, (String)null);
	}

	@Test
	public void testCountByC_L_Version() throws Exception {
		_persistence.countByC_L_Version(
			RandomTestUtil.nextLong(), "", RandomTestUtil.nextInt());

		_persistence.countByC_L_Version(0L, "null", 0);

		_persistence.countByC_L_Version(0L, (String)null, 0);
	}

	@Test
	public void testCountByP_I() throws Exception {
		_persistence.countByP_I(
			RandomTestUtil.randomBoolean(), RandomTestUtil.nextLong());

		_persistence.countByP_I(RandomTestUtil.randomBoolean(), 0L);
	}

	@Test
	public void testCountByP_I_Version() throws Exception {
		_persistence.countByP_I_Version(
			RandomTestUtil.randomBoolean(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByP_I_Version(RandomTestUtil.randomBoolean(), 0L, 0);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testCountByC_C_Version() throws Exception {
		_persistence.countByC_C_Version(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByC_C_Version(0L, 0L, 0);
	}

	@Test
	public void testCountByG_P_L() throws Exception {
		_persistence.countByG_P_L(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong());

		_persistence.countByG_P_L(0L, RandomTestUtil.randomBoolean(), 0L);
	}

	@Test
	public void testCountByG_P_L_Version() throws Exception {
		_persistence.countByG_P_L_Version(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByG_P_L_Version(
			0L, RandomTestUtil.randomBoolean(), 0L, 0);
	}

	@Test
	public void testCountByG_P_P() throws Exception {
		_persistence.countByG_P_P(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong());

		_persistence.countByG_P_P(0L, RandomTestUtil.randomBoolean(), 0L);
	}

	@Test
	public void testCountByG_P_P_Version() throws Exception {
		_persistence.countByG_P_P_Version(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByG_P_P_Version(
			0L, RandomTestUtil.randomBoolean(), 0L, 0);
	}

	@Test
	public void testCountByG_P_T() throws Exception {
		_persistence.countByG_P_T(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(), "");

		_persistence.countByG_P_T(0L, RandomTestUtil.randomBoolean(), "null");

		_persistence.countByG_P_T(
			0L, RandomTestUtil.randomBoolean(), (String)null);
	}

	@Test
	public void testCountByG_P_T_Version() throws Exception {
		_persistence.countByG_P_T_Version(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(), "",
			RandomTestUtil.nextInt());

		_persistence.countByG_P_T_Version(
			0L, RandomTestUtil.randomBoolean(), "null", 0);

		_persistence.countByG_P_T_Version(
			0L, RandomTestUtil.randomBoolean(), (String)null, 0);
	}

	@Test
	public void testCountByG_P_F() throws Exception {
		_persistence.countByG_P_F(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(), "");

		_persistence.countByG_P_F(0L, RandomTestUtil.randomBoolean(), "null");

		_persistence.countByG_P_F(
			0L, RandomTestUtil.randomBoolean(), (String)null);
	}

	@Test
	public void testCountByG_P_F_Version() throws Exception {
		_persistence.countByG_P_F_Version(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(), "",
			RandomTestUtil.nextInt());

		_persistence.countByG_P_F_Version(
			0L, RandomTestUtil.randomBoolean(), "null", 0);

		_persistence.countByG_P_F_Version(
			0L, RandomTestUtil.randomBoolean(), (String)null, 0);
	}

	@Test
	public void testCountByG_P_SPLU() throws Exception {
		_persistence.countByG_P_SPLU(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(), "");

		_persistence.countByG_P_SPLU(
			0L, RandomTestUtil.randomBoolean(), "null");

		_persistence.countByG_P_SPLU(
			0L, RandomTestUtil.randomBoolean(), (String)null);
	}

	@Test
	public void testCountByG_P_SPLU_Version() throws Exception {
		_persistence.countByG_P_SPLU_Version(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(), "",
			RandomTestUtil.nextInt());

		_persistence.countByG_P_SPLU_Version(
			0L, RandomTestUtil.randomBoolean(), "null", 0);

		_persistence.countByG_P_SPLU_Version(
			0L, RandomTestUtil.randomBoolean(), (String)null, 0);
	}

	@Test
	public void testCountByG_P_P_H() throws Exception {
		_persistence.countByG_P_P_H(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByG_P_P_H(
			0L, RandomTestUtil.randomBoolean(), 0L,
			RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_P_P_H_Version() throws Exception {
		_persistence.countByG_P_P_H_Version(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextInt());

		_persistence.countByG_P_P_H_Version(
			0L, RandomTestUtil.randomBoolean(), 0L,
			RandomTestUtil.randomBoolean(), 0);
	}

	@Test
	public void testCountByG_P_P_LtP() throws Exception {
		_persistence.countByG_P_P_LtP(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByG_P_P_LtP(
			0L, RandomTestUtil.randomBoolean(), 0L, 0);
	}

	@Test
	public void testCountByG_P_P_LtP_Version() throws Exception {
		_persistence.countByG_P_P_LtP_Version(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextInt());

		_persistence.countByG_P_P_LtP_Version(
			0L, RandomTestUtil.randomBoolean(), 0L, 0, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutVersion newLayoutVersion = addLayoutVersion();

		LayoutVersion existingLayoutVersion = _persistence.findByPrimaryKey(
			newLayoutVersion.getPrimaryKey());

		Assert.assertEquals(existingLayoutVersion, newLayoutVersion);
	}

	@Test(expected = NoSuchLayoutVersionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LayoutVersion> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"LayoutVersion", "layoutVersionId", true, "version", true, "uuid",
			true, "plid", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"parentPlid", true, "privateLayout", true, "layoutId", true,
			"parentLayoutId", true, "classNameId", true, "classPK", true,
			"name", true, "title", true, "description", true, "keywords", true,
			"robots", true, "type", true, "hidden", true, "system", true,
			"friendlyURL", true, "iconImageId", true, "themeId", true,
			"colorSchemeId", true, "priority", true, "layoutPrototypeUuid",
			true, "layoutPrototypeLinkEnabled", true,
			"sourcePrototypeLayoutUuid", true, "publishDate", true,
			"lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutVersion newLayoutVersion = addLayoutVersion();

		LayoutVersion existingLayoutVersion = _persistence.fetchByPrimaryKey(
			newLayoutVersion.getPrimaryKey());

		Assert.assertEquals(existingLayoutVersion, newLayoutVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutVersion missingLayoutVersion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutVersion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LayoutVersion newLayoutVersion1 = addLayoutVersion();
		LayoutVersion newLayoutVersion2 = addLayoutVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutVersion1.getPrimaryKey());
		primaryKeys.add(newLayoutVersion2.getPrimaryKey());

		Map<Serializable, LayoutVersion> layoutVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutVersions.size());
		Assert.assertEquals(
			newLayoutVersion1,
			layoutVersions.get(newLayoutVersion1.getPrimaryKey()));
		Assert.assertEquals(
			newLayoutVersion2,
			layoutVersions.get(newLayoutVersion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutVersion> layoutVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LayoutVersion newLayoutVersion = addLayoutVersion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutVersion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutVersion> layoutVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutVersions.size());
		Assert.assertEquals(
			newLayoutVersion,
			layoutVersions.get(newLayoutVersion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutVersion> layoutVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LayoutVersion newLayoutVersion = addLayoutVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutVersion.getPrimaryKey());

		Map<Serializable, LayoutVersion> layoutVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutVersions.size());
		Assert.assertEquals(
			newLayoutVersion,
			layoutVersions.get(newLayoutVersion.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LayoutVersion newLayoutVersion = addLayoutVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutVersionId", newLayoutVersion.getLayoutVersionId()));

		List<LayoutVersion> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutVersion existingLayoutVersion = result.get(0);

		Assert.assertEquals(existingLayoutVersion, newLayoutVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutVersionId", RandomTestUtil.nextLong()));

		List<LayoutVersion> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LayoutVersion newLayoutVersion = addLayoutVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutVersionId"));

		Object newLayoutVersionId = newLayoutVersion.getLayoutVersionId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutVersionId", new Object[] {newLayoutVersionId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutVersionId = result.get(0);

		Assert.assertEquals(existingLayoutVersionId, newLayoutVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutVersionId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutVersionId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutVersion newLayoutVersion = addLayoutVersion();

		_persistence.clearCache();

		LayoutVersion existingLayoutVersion = _persistence.findByPrimaryKey(
			newLayoutVersion.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(existingLayoutVersion.getPlid()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutVersion, "getOriginalPlid", new Class<?>[0]));
		Assert.assertEquals(
			Integer.valueOf(existingLayoutVersion.getVersion()),
			ReflectionTestUtil.<Integer>invoke(
				existingLayoutVersion, "getOriginalVersion", new Class<?>[0]));

		Assert.assertTrue(
			Objects.equals(
				existingLayoutVersion.getUuid(),
				ReflectionTestUtil.invoke(
					existingLayoutVersion, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingLayoutVersion.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutVersion, "getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(
			Boolean.valueOf(existingLayoutVersion.getPrivateLayout()),
			ReflectionTestUtil.<Boolean>invoke(
				existingLayoutVersion, "getOriginalPrivateLayout",
				new Class<?>[0]));
		Assert.assertEquals(
			Integer.valueOf(existingLayoutVersion.getVersion()),
			ReflectionTestUtil.<Integer>invoke(
				existingLayoutVersion, "getOriginalVersion", new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingLayoutVersion.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutVersion, "getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(
			Boolean.valueOf(existingLayoutVersion.getPrivateLayout()),
			ReflectionTestUtil.<Boolean>invoke(
				existingLayoutVersion, "getOriginalPrivateLayout",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingLayoutVersion.getLayoutId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutVersion, "getOriginalLayoutId", new Class<?>[0]));
		Assert.assertEquals(
			Integer.valueOf(existingLayoutVersion.getVersion()),
			ReflectionTestUtil.<Integer>invoke(
				existingLayoutVersion, "getOriginalVersion", new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingLayoutVersion.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutVersion, "getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(
			Boolean.valueOf(existingLayoutVersion.getPrivateLayout()),
			ReflectionTestUtil.<Boolean>invoke(
				existingLayoutVersion, "getOriginalPrivateLayout",
				new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingLayoutVersion.getFriendlyURL(),
				ReflectionTestUtil.invoke(
					existingLayoutVersion, "getOriginalFriendlyURL",
					new Class<?>[0])));
		Assert.assertEquals(
			Integer.valueOf(existingLayoutVersion.getVersion()),
			ReflectionTestUtil.<Integer>invoke(
				existingLayoutVersion, "getOriginalVersion", new Class<?>[0]));
	}

	protected LayoutVersion addLayoutVersion() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutVersion layoutVersion = _persistence.create(pk);

		layoutVersion.setVersion(RandomTestUtil.nextInt());

		layoutVersion.setUuid(RandomTestUtil.randomString());

		layoutVersion.setPlid(RandomTestUtil.nextLong());

		layoutVersion.setGroupId(RandomTestUtil.nextLong());

		layoutVersion.setCompanyId(RandomTestUtil.nextLong());

		layoutVersion.setUserId(RandomTestUtil.nextLong());

		layoutVersion.setUserName(RandomTestUtil.randomString());

		layoutVersion.setCreateDate(RandomTestUtil.nextDate());

		layoutVersion.setModifiedDate(RandomTestUtil.nextDate());

		layoutVersion.setParentPlid(RandomTestUtil.nextLong());

		layoutVersion.setPrivateLayout(RandomTestUtil.randomBoolean());

		layoutVersion.setLayoutId(RandomTestUtil.nextLong());

		layoutVersion.setParentLayoutId(RandomTestUtil.nextLong());

		layoutVersion.setClassNameId(RandomTestUtil.nextLong());

		layoutVersion.setClassPK(RandomTestUtil.nextLong());

		layoutVersion.setName(RandomTestUtil.randomString());

		layoutVersion.setTitle(RandomTestUtil.randomString());

		layoutVersion.setDescription(RandomTestUtil.randomString());

		layoutVersion.setKeywords(RandomTestUtil.randomString());

		layoutVersion.setRobots(RandomTestUtil.randomString());

		layoutVersion.setType(RandomTestUtil.randomString());

		layoutVersion.setTypeSettings(RandomTestUtil.randomString());

		layoutVersion.setHidden(RandomTestUtil.randomBoolean());

		layoutVersion.setSystem(RandomTestUtil.randomBoolean());

		layoutVersion.setFriendlyURL(RandomTestUtil.randomString());

		layoutVersion.setIconImageId(RandomTestUtil.nextLong());

		layoutVersion.setThemeId(RandomTestUtil.randomString());

		layoutVersion.setColorSchemeId(RandomTestUtil.randomString());

		layoutVersion.setCss(RandomTestUtil.randomString());

		layoutVersion.setPriority(RandomTestUtil.nextInt());

		layoutVersion.setLayoutPrototypeUuid(RandomTestUtil.randomString());

		layoutVersion.setLayoutPrototypeLinkEnabled(
			RandomTestUtil.randomBoolean());

		layoutVersion.setSourcePrototypeLayoutUuid(
			RandomTestUtil.randomString());

		layoutVersion.setPublishDate(RandomTestUtil.nextDate());

		layoutVersion.setLastPublishDate(RandomTestUtil.nextDate());

		_layoutVersions.add(_persistence.update(layoutVersion));

		return layoutVersion;
	}

	private List<LayoutVersion> _layoutVersions =
		new ArrayList<LayoutVersion>();
	private LayoutVersionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}