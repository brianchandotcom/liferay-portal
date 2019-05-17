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
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.LayoutPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutUtil;
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
public class LayoutPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = LayoutUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Layout> iterator = _layouts.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Layout layout = _persistence.create(pk);

		Assert.assertNotNull(layout);

		Assert.assertEquals(layout.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Layout newLayout = addLayout();

		_persistence.remove(newLayout);

		Layout existingLayout = _persistence.fetchByPrimaryKey(
			newLayout.getPrimaryKey());

		Assert.assertNull(existingLayout);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayout();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Layout newLayout = _persistence.create(pk);

		newLayout.setMvccVersion(RandomTestUtil.nextLong());

		newLayout.setUuid(RandomTestUtil.randomString());

		newLayout.setHeadId(RandomTestUtil.nextLong());

		newLayout.setGroupId(RandomTestUtil.nextLong());

		newLayout.setCompanyId(RandomTestUtil.nextLong());

		newLayout.setUserId(RandomTestUtil.nextLong());

		newLayout.setUserName(RandomTestUtil.randomString());

		newLayout.setCreateDate(RandomTestUtil.nextDate());

		newLayout.setModifiedDate(RandomTestUtil.nextDate());

		newLayout.setParentPlid(RandomTestUtil.nextLong());

		newLayout.setPrivateLayout(RandomTestUtil.randomBoolean());

		newLayout.setLayoutId(RandomTestUtil.nextLong());

		newLayout.setParentLayoutId(RandomTestUtil.nextLong());

		newLayout.setClassNameId(RandomTestUtil.nextLong());

		newLayout.setClassPK(RandomTestUtil.nextLong());

		newLayout.setName(RandomTestUtil.randomString());

		newLayout.setTitle(RandomTestUtil.randomString());

		newLayout.setDescription(RandomTestUtil.randomString());

		newLayout.setKeywords(RandomTestUtil.randomString());

		newLayout.setRobots(RandomTestUtil.randomString());

		newLayout.setType(RandomTestUtil.randomString());

		newLayout.setTypeSettings(RandomTestUtil.randomString());

		newLayout.setHidden(RandomTestUtil.randomBoolean());

		newLayout.setSystem(RandomTestUtil.randomBoolean());

		newLayout.setFriendlyURL(RandomTestUtil.randomString());

		newLayout.setIconImageId(RandomTestUtil.nextLong());

		newLayout.setThemeId(RandomTestUtil.randomString());

		newLayout.setColorSchemeId(RandomTestUtil.randomString());

		newLayout.setCss(RandomTestUtil.randomString());

		newLayout.setPriority(RandomTestUtil.nextInt());

		newLayout.setLayoutPrototypeUuid(RandomTestUtil.randomString());

		newLayout.setLayoutPrototypeLinkEnabled(RandomTestUtil.randomBoolean());

		newLayout.setSourcePrototypeLayoutUuid(RandomTestUtil.randomString());

		newLayout.setPublishDate(RandomTestUtil.nextDate());

		newLayout.setLastPublishDate(RandomTestUtil.nextDate());

		_layouts.add(_persistence.update(newLayout));

		Layout existingLayout = _persistence.findByPrimaryKey(
			newLayout.getPrimaryKey());

		Assert.assertEquals(
			existingLayout.getMvccVersion(), newLayout.getMvccVersion());
		Assert.assertEquals(existingLayout.getUuid(), newLayout.getUuid());
		Assert.assertEquals(existingLayout.getHeadId(), newLayout.getHeadId());
		Assert.assertEquals(existingLayout.getPlid(), newLayout.getPlid());
		Assert.assertEquals(
			existingLayout.getGroupId(), newLayout.getGroupId());
		Assert.assertEquals(
			existingLayout.getCompanyId(), newLayout.getCompanyId());
		Assert.assertEquals(existingLayout.getUserId(), newLayout.getUserId());
		Assert.assertEquals(
			existingLayout.getUserName(), newLayout.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingLayout.getCreateDate()),
			Time.getShortTimestamp(newLayout.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingLayout.getModifiedDate()),
			Time.getShortTimestamp(newLayout.getModifiedDate()));
		Assert.assertEquals(
			existingLayout.getParentPlid(), newLayout.getParentPlid());
		Assert.assertEquals(
			existingLayout.isPrivateLayout(), newLayout.isPrivateLayout());
		Assert.assertEquals(
			existingLayout.getLayoutId(), newLayout.getLayoutId());
		Assert.assertEquals(
			existingLayout.getParentLayoutId(), newLayout.getParentLayoutId());
		Assert.assertEquals(
			existingLayout.getClassNameId(), newLayout.getClassNameId());
		Assert.assertEquals(
			existingLayout.getClassPK(), newLayout.getClassPK());
		Assert.assertEquals(existingLayout.getName(), newLayout.getName());
		Assert.assertEquals(existingLayout.getTitle(), newLayout.getTitle());
		Assert.assertEquals(
			existingLayout.getDescription(), newLayout.getDescription());
		Assert.assertEquals(
			existingLayout.getKeywords(), newLayout.getKeywords());
		Assert.assertEquals(existingLayout.getRobots(), newLayout.getRobots());
		Assert.assertEquals(existingLayout.getType(), newLayout.getType());
		Assert.assertEquals(
			existingLayout.getTypeSettings(), newLayout.getTypeSettings());
		Assert.assertEquals(existingLayout.isHidden(), newLayout.isHidden());
		Assert.assertEquals(existingLayout.isSystem(), newLayout.isSystem());
		Assert.assertEquals(
			existingLayout.getFriendlyURL(), newLayout.getFriendlyURL());
		Assert.assertEquals(
			existingLayout.getIconImageId(), newLayout.getIconImageId());
		Assert.assertEquals(
			existingLayout.getThemeId(), newLayout.getThemeId());
		Assert.assertEquals(
			existingLayout.getColorSchemeId(), newLayout.getColorSchemeId());
		Assert.assertEquals(existingLayout.getCss(), newLayout.getCss());
		Assert.assertEquals(
			existingLayout.getPriority(), newLayout.getPriority());
		Assert.assertEquals(
			existingLayout.getLayoutPrototypeUuid(),
			newLayout.getLayoutPrototypeUuid());
		Assert.assertEquals(
			existingLayout.isLayoutPrototypeLinkEnabled(),
			newLayout.isLayoutPrototypeLinkEnabled());
		Assert.assertEquals(
			existingLayout.getSourcePrototypeLayoutUuid(),
			newLayout.getSourcePrototypeLayoutUuid());
		Assert.assertEquals(
			Time.getShortTimestamp(existingLayout.getPublishDate()),
			Time.getShortTimestamp(newLayout.getPublishDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingLayout.getLastPublishDate()),
			Time.getShortTimestamp(newLayout.getLastPublishDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_Head() throws Exception {
		_persistence.countByUuid_Head("", RandomTestUtil.randomBoolean());

		_persistence.countByUuid_Head("null", RandomTestUtil.randomBoolean());

		_persistence.countByUuid_Head(
			(String)null, RandomTestUtil.randomBoolean());
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
	public void testCountByUUID_G_P_Head() throws Exception {
		_persistence.countByUUID_G_P_Head(
			"", RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean());

		_persistence.countByUUID_G_P_Head(
			"null", 0L, RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean());

		_persistence.countByUUID_G_P_Head(
			(String)null, 0L, RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C_Head() throws Exception {
		_persistence.countByUuid_C_Head(
			"", RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByUuid_C_Head(
			"null", 0L, RandomTestUtil.randomBoolean());

		_persistence.countByUuid_C_Head(
			(String)null, 0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByGroupId_Head() throws Exception {
		_persistence.countByGroupId_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByGroupId_Head(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByCompanyId_Head() throws Exception {
		_persistence.countByCompanyId_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByCompanyId_Head(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByParentPlid() throws Exception {
		_persistence.countByParentPlid(RandomTestUtil.nextLong());

		_persistence.countByParentPlid(0L);
	}

	@Test
	public void testCountByParentPlid_Head() throws Exception {
		_persistence.countByParentPlid_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByParentPlid_Head(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByIconImageId() throws Exception {
		_persistence.countByIconImageId(RandomTestUtil.nextLong());

		_persistence.countByIconImageId(0L);
	}

	@Test
	public void testCountByIconImageId_Head() throws Exception {
		_persistence.countByIconImageId_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByIconImageId_Head(
			0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByLayoutPrototypeUuid() throws Exception {
		_persistence.countByLayoutPrototypeUuid("");

		_persistence.countByLayoutPrototypeUuid("null");

		_persistence.countByLayoutPrototypeUuid((String)null);
	}

	@Test
	public void testCountByLayoutPrototypeUuid_Head() throws Exception {
		_persistence.countByLayoutPrototypeUuid_Head(
			"", RandomTestUtil.randomBoolean());

		_persistence.countByLayoutPrototypeUuid_Head(
			"null", RandomTestUtil.randomBoolean());

		_persistence.countByLayoutPrototypeUuid_Head(
			(String)null, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountBySourcePrototypeLayoutUuid() throws Exception {
		_persistence.countBySourcePrototypeLayoutUuid("");

		_persistence.countBySourcePrototypeLayoutUuid("null");

		_persistence.countBySourcePrototypeLayoutUuid((String)null);
	}

	@Test
	public void testCountBySourcePrototypeLayoutUuid_Head() throws Exception {
		_persistence.countBySourcePrototypeLayoutUuid_Head(
			"", RandomTestUtil.randomBoolean());

		_persistence.countBySourcePrototypeLayoutUuid_Head(
			"null", RandomTestUtil.randomBoolean());

		_persistence.countBySourcePrototypeLayoutUuid_Head(
			(String)null, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_P() throws Exception {
		_persistence.countByG_P(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByG_P(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_P_Head() throws Exception {
		_persistence.countByG_P_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_P_Head(
			0L, RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_T() throws Exception {
		_persistence.countByG_T(RandomTestUtil.nextLong(), "");

		_persistence.countByG_T(0L, "null");

		_persistence.countByG_T(0L, (String)null);
	}

	@Test
	public void testCountByG_T_Head() throws Exception {
		_persistence.countByG_T_Head(
			RandomTestUtil.nextLong(), "", RandomTestUtil.randomBoolean());

		_persistence.countByG_T_Head(
			0L, "null", RandomTestUtil.randomBoolean());

		_persistence.countByG_T_Head(
			0L, (String)null, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByC_L() throws Exception {
		_persistence.countByC_L(RandomTestUtil.nextLong(), "");

		_persistence.countByC_L(0L, "null");

		_persistence.countByC_L(0L, (String)null);
	}

	@Test
	public void testCountByC_L_Head() throws Exception {
		_persistence.countByC_L_Head(
			RandomTestUtil.nextLong(), "", RandomTestUtil.randomBoolean());

		_persistence.countByC_L_Head(
			0L, "null", RandomTestUtil.randomBoolean());

		_persistence.countByC_L_Head(
			0L, (String)null, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByP_I() throws Exception {
		_persistence.countByP_I(
			RandomTestUtil.randomBoolean(), RandomTestUtil.nextLong());

		_persistence.countByP_I(RandomTestUtil.randomBoolean(), 0L);
	}

	@Test
	public void testCountByP_I_Head() throws Exception {
		_persistence.countByP_I_Head(
			RandomTestUtil.randomBoolean(), RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByP_I_Head(
			RandomTestUtil.randomBoolean(), 0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testCountByC_C_Head() throws Exception {
		_persistence.countByC_C_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByC_C_Head(0L, 0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_P_L() throws Exception {
		_persistence.countByG_P_L(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong());

		_persistence.countByG_P_L(0L, RandomTestUtil.randomBoolean(), 0L);
	}

	@Test
	public void testCountByG_P_L_Head() throws Exception {
		_persistence.countByG_P_L_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByG_P_L_Head(
			0L, RandomTestUtil.randomBoolean(), 0L,
			RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_P_P() throws Exception {
		_persistence.countByG_P_P(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong());

		_persistence.countByG_P_P(0L, RandomTestUtil.randomBoolean(), 0L);
	}

	@Test
	public void testCountByG_P_PArrayable() throws Exception {
		_persistence.countByG_P_P(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			new long[] {RandomTestUtil.nextLong(), 0L});
	}

	@Test
	public void testCountByG_P_P_Head() throws Exception {
		_persistence.countByG_P_P_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByG_P_P_Head(
			0L, RandomTestUtil.randomBoolean(), 0L,
			RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_P_P_HeadArrayable() throws Exception {
		_persistence.countByG_P_P_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			new long[] {RandomTestUtil.nextLong(), 0L},
			RandomTestUtil.randomBoolean());
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
	public void testCountByG_P_T_Head() throws Exception {
		_persistence.countByG_P_T_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(), "",
			RandomTestUtil.randomBoolean());

		_persistence.countByG_P_T_Head(
			0L, RandomTestUtil.randomBoolean(), "null",
			RandomTestUtil.randomBoolean());

		_persistence.countByG_P_T_Head(
			0L, RandomTestUtil.randomBoolean(), (String)null,
			RandomTestUtil.randomBoolean());
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
	public void testCountByG_P_F_Head() throws Exception {
		_persistence.countByG_P_F_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(), "",
			RandomTestUtil.randomBoolean());

		_persistence.countByG_P_F_Head(
			0L, RandomTestUtil.randomBoolean(), "null",
			RandomTestUtil.randomBoolean());

		_persistence.countByG_P_F_Head(
			0L, RandomTestUtil.randomBoolean(), (String)null,
			RandomTestUtil.randomBoolean());
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
	public void testCountByG_P_SPLU_Head() throws Exception {
		_persistence.countByG_P_SPLU_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(), "",
			RandomTestUtil.randomBoolean());

		_persistence.countByG_P_SPLU_Head(
			0L, RandomTestUtil.randomBoolean(), "null",
			RandomTestUtil.randomBoolean());

		_persistence.countByG_P_SPLU_Head(
			0L, RandomTestUtil.randomBoolean(), (String)null,
			RandomTestUtil.randomBoolean());
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
	public void testCountByG_P_P_HArrayable() throws Exception {
		_persistence.countByG_P_P_H(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			new long[] {RandomTestUtil.nextLong(), 0L},
			RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_P_P_H_Head() throws Exception {
		_persistence.countByG_P_P_H_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_P_P_H_Head(
			0L, RandomTestUtil.randomBoolean(), 0L,
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_P_P_H_HeadArrayable() throws Exception {
		_persistence.countByG_P_P_H_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			new long[] {RandomTestUtil.nextLong(), 0L},
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean());
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
	public void testCountByG_P_P_LtP_Head() throws Exception {
		_persistence.countByG_P_P_LtP_Head(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_P_P_LtP_Head(
			0L, RandomTestUtil.randomBoolean(), 0L, 0,
			RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByHeadId() throws Exception {
		_persistence.countByHeadId(RandomTestUtil.nextLong());

		_persistence.countByHeadId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Layout newLayout = addLayout();

		Layout existingLayout = _persistence.findByPrimaryKey(
			newLayout.getPrimaryKey());

		Assert.assertEquals(existingLayout, newLayout);
	}

	@Test(expected = NoSuchLayoutException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(
			0, QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Layout> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Layout", "mvccVersion", true, "uuid", true, "headId", true, "plid",
			true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
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
		Layout newLayout = addLayout();

		Layout existingLayout = _persistence.fetchByPrimaryKey(
			newLayout.getPrimaryKey());

		Assert.assertEquals(existingLayout, newLayout);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Layout missingLayout = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayout);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Layout newLayout1 = addLayout();
		Layout newLayout2 = addLayout();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayout1.getPrimaryKey());
		primaryKeys.add(newLayout2.getPrimaryKey());

		Map<Serializable, Layout> layouts = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, layouts.size());
		Assert.assertEquals(
			newLayout1, layouts.get(newLayout1.getPrimaryKey()));
		Assert.assertEquals(
			newLayout2, layouts.get(newLayout2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Layout> layouts = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(layouts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Layout newLayout = addLayout();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayout.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Layout> layouts = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, layouts.size());
		Assert.assertEquals(newLayout, layouts.get(newLayout.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Layout> layouts = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(layouts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Layout newLayout = addLayout();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayout.getPrimaryKey());

		Map<Serializable, Layout> layouts = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, layouts.size());
		Assert.assertEquals(newLayout, layouts.get(newLayout.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LayoutLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Layout>() {

				@Override
				public void performAction(Layout layout) {
					Assert.assertNotNull(layout);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Layout newLayout = addLayout();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Layout.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("plid", newLayout.getPlid()));

		List<Layout> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Layout existingLayout = result.get(0);

		Assert.assertEquals(existingLayout, newLayout);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Layout.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("plid", RandomTestUtil.nextLong()));

		List<Layout> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Layout newLayout = addLayout();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Layout.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("plid"));

		Object newPlid = newLayout.getPlid();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in("plid", new Object[] {newPlid}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPlid = result.get(0);

		Assert.assertEquals(existingPlid, newPlid);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Layout.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("plid"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"plid", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Layout newLayout = addLayout();

		_persistence.clearCache();

		Layout existingLayout = _persistence.findByPrimaryKey(
			newLayout.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingLayout.getUuid(),
				ReflectionTestUtil.invoke(
					existingLayout, "getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingLayout.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayout, "getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(
			Boolean.valueOf(existingLayout.getPrivateLayout()),
			ReflectionTestUtil.<Boolean>invoke(
				existingLayout, "getOriginalPrivateLayout", new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingLayout.getIconImageId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayout, "getOriginalIconImageId", new Class<?>[0]));

		Assert.assertEquals(
			Boolean.valueOf(existingLayout.getPrivateLayout()),
			ReflectionTestUtil.<Boolean>invoke(
				existingLayout, "getOriginalPrivateLayout", new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingLayout.getIconImageId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayout, "getOriginalIconImageId", new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingLayout.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayout, "getOriginalClassNameId", new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingLayout.getClassPK()),
			ReflectionTestUtil.<Long>invoke(
				existingLayout, "getOriginalClassPK", new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingLayout.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayout, "getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(
			Boolean.valueOf(existingLayout.getPrivateLayout()),
			ReflectionTestUtil.<Boolean>invoke(
				existingLayout, "getOriginalPrivateLayout", new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingLayout.getLayoutId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayout, "getOriginalLayoutId", new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingLayout.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayout, "getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(
			Boolean.valueOf(existingLayout.getPrivateLayout()),
			ReflectionTestUtil.<Boolean>invoke(
				existingLayout, "getOriginalPrivateLayout", new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingLayout.getFriendlyURL(),
				ReflectionTestUtil.invoke(
					existingLayout, "getOriginalFriendlyURL",
					new Class<?>[0])));

		Assert.assertEquals(
			Long.valueOf(existingLayout.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayout, "getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(
			Boolean.valueOf(existingLayout.getPrivateLayout()),
			ReflectionTestUtil.<Boolean>invoke(
				existingLayout, "getOriginalPrivateLayout", new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingLayout.getSourcePrototypeLayoutUuid(),
				ReflectionTestUtil.invoke(
					existingLayout, "getOriginalSourcePrototypeLayoutUuid",
					new Class<?>[0])));

		Assert.assertEquals(
			Long.valueOf(existingLayout.getHeadId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayout, "getOriginalHeadId", new Class<?>[0]));
	}

	protected Layout addLayout() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Layout layout = _persistence.create(pk);

		layout.setMvccVersion(RandomTestUtil.nextLong());

		layout.setUuid(RandomTestUtil.randomString());

		layout.setHeadId(RandomTestUtil.nextLong());

		layout.setGroupId(RandomTestUtil.nextLong());

		layout.setCompanyId(RandomTestUtil.nextLong());

		layout.setUserId(RandomTestUtil.nextLong());

		layout.setUserName(RandomTestUtil.randomString());

		layout.setCreateDate(RandomTestUtil.nextDate());

		layout.setModifiedDate(RandomTestUtil.nextDate());

		layout.setParentPlid(RandomTestUtil.nextLong());

		layout.setPrivateLayout(RandomTestUtil.randomBoolean());

		layout.setLayoutId(RandomTestUtil.nextLong());

		layout.setParentLayoutId(RandomTestUtil.nextLong());

		layout.setClassNameId(RandomTestUtil.nextLong());

		layout.setClassPK(RandomTestUtil.nextLong());

		layout.setName(RandomTestUtil.randomString());

		layout.setTitle(RandomTestUtil.randomString());

		layout.setDescription(RandomTestUtil.randomString());

		layout.setKeywords(RandomTestUtil.randomString());

		layout.setRobots(RandomTestUtil.randomString());

		layout.setType(RandomTestUtil.randomString());

		layout.setTypeSettings(RandomTestUtil.randomString());

		layout.setHidden(RandomTestUtil.randomBoolean());

		layout.setSystem(RandomTestUtil.randomBoolean());

		layout.setFriendlyURL(RandomTestUtil.randomString());

		layout.setIconImageId(RandomTestUtil.nextLong());

		layout.setThemeId(RandomTestUtil.randomString());

		layout.setColorSchemeId(RandomTestUtil.randomString());

		layout.setCss(RandomTestUtil.randomString());

		layout.setPriority(RandomTestUtil.nextInt());

		layout.setLayoutPrototypeUuid(RandomTestUtil.randomString());

		layout.setLayoutPrototypeLinkEnabled(RandomTestUtil.randomBoolean());

		layout.setSourcePrototypeLayoutUuid(RandomTestUtil.randomString());

		layout.setPublishDate(RandomTestUtil.nextDate());

		layout.setLastPublishDate(RandomTestUtil.nextDate());

		_layouts.add(_persistence.update(layout));

		return layout;
	}

	private List<Layout> _layouts = new ArrayList<Layout>();
	private LayoutPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}