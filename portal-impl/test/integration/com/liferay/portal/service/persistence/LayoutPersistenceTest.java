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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutModelImpl;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class LayoutPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<Layout> modelListener : _modelListeners) {
			_persistence.unregisterListener(modelListener);
		}
	}

	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();

		for (ModelListener<Layout> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
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

		Layout existingLayout = _persistence.fetchByPrimaryKey(newLayout.getPrimaryKey());

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

		newLayout.setGroupId(RandomTestUtil.nextLong());

		newLayout.setCompanyId(RandomTestUtil.nextLong());

		newLayout.setUserId(RandomTestUtil.nextLong());

		newLayout.setUserName(RandomTestUtil.randomString());

		newLayout.setCreateDate(RandomTestUtil.nextDate());

		newLayout.setModifiedDate(RandomTestUtil.nextDate());

		newLayout.setPrivateLayout(RandomTestUtil.randomBoolean());

		newLayout.setLayoutId(RandomTestUtil.nextLong());

		newLayout.setParentLayoutId(RandomTestUtil.nextLong());

		newLayout.setName(RandomTestUtil.randomString());

		newLayout.setTitle(RandomTestUtil.randomString());

		newLayout.setDescription(RandomTestUtil.randomString());

		newLayout.setKeywords(RandomTestUtil.randomString());

		newLayout.setRobots(RandomTestUtil.randomString());

		newLayout.setType(RandomTestUtil.randomString());

		newLayout.setTypeSettings(RandomTestUtil.randomString());

		newLayout.setHidden(RandomTestUtil.randomBoolean());

		newLayout.setFriendlyURL(RandomTestUtil.randomString());

		newLayout.setIconImageId(RandomTestUtil.nextLong());

		newLayout.setThemeId(RandomTestUtil.randomString());

		newLayout.setColorSchemeId(RandomTestUtil.randomString());

		newLayout.setWapThemeId(RandomTestUtil.randomString());

		newLayout.setWapColorSchemeId(RandomTestUtil.randomString());

		newLayout.setCss(RandomTestUtil.randomString());

		newLayout.setPriority(RandomTestUtil.nextInt());

		newLayout.setLayoutPrototypeUuid(RandomTestUtil.randomString());

		newLayout.setLayoutPrototypeLinkEnabled(RandomTestUtil.randomBoolean());

		newLayout.setSourcePrototypeLayoutUuid(RandomTestUtil.randomString());

		_persistence.update(newLayout);

		Layout existingLayout = _persistence.findByPrimaryKey(newLayout.getPrimaryKey());

		Assert.assertEquals(existingLayout.getMvccVersion(),
			newLayout.getMvccVersion());
		Assert.assertEquals(existingLayout.getUuid(), newLayout.getUuid());
		Assert.assertEquals(existingLayout.getPlid(), newLayout.getPlid());
		Assert.assertEquals(existingLayout.getGroupId(), newLayout.getGroupId());
		Assert.assertEquals(existingLayout.getCompanyId(),
			newLayout.getCompanyId());
		Assert.assertEquals(existingLayout.getUserId(), newLayout.getUserId());
		Assert.assertEquals(existingLayout.getUserName(),
			newLayout.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayout.getCreateDate()),
			Time.getShortTimestamp(newLayout.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayout.getModifiedDate()),
			Time.getShortTimestamp(newLayout.getModifiedDate()));
		Assert.assertEquals(existingLayout.getPrivateLayout(),
			newLayout.getPrivateLayout());
		Assert.assertEquals(existingLayout.getLayoutId(),
			newLayout.getLayoutId());
		Assert.assertEquals(existingLayout.getParentLayoutId(),
			newLayout.getParentLayoutId());
		Assert.assertEquals(existingLayout.getName(), newLayout.getName());
		Assert.assertEquals(existingLayout.getTitle(), newLayout.getTitle());
		Assert.assertEquals(existingLayout.getDescription(),
			newLayout.getDescription());
		Assert.assertEquals(existingLayout.getKeywords(),
			newLayout.getKeywords());
		Assert.assertEquals(existingLayout.getRobots(), newLayout.getRobots());
		Assert.assertEquals(existingLayout.getType(), newLayout.getType());
		Assert.assertEquals(existingLayout.getTypeSettings(),
			newLayout.getTypeSettings());
		Assert.assertEquals(existingLayout.getHidden(), newLayout.getHidden());
		Assert.assertEquals(existingLayout.getFriendlyURL(),
			newLayout.getFriendlyURL());
		Assert.assertEquals(existingLayout.getIconImageId(),
			newLayout.getIconImageId());
		Assert.assertEquals(existingLayout.getThemeId(), newLayout.getThemeId());
		Assert.assertEquals(existingLayout.getColorSchemeId(),
			newLayout.getColorSchemeId());
		Assert.assertEquals(existingLayout.getWapThemeId(),
			newLayout.getWapThemeId());
		Assert.assertEquals(existingLayout.getWapColorSchemeId(),
			newLayout.getWapColorSchemeId());
		Assert.assertEquals(existingLayout.getCss(), newLayout.getCss());
		Assert.assertEquals(existingLayout.getPriority(),
			newLayout.getPriority());
		Assert.assertEquals(existingLayout.getLayoutPrototypeUuid(),
			newLayout.getLayoutPrototypeUuid());
		Assert.assertEquals(existingLayout.getLayoutPrototypeLinkEnabled(),
			newLayout.getLayoutPrototypeLinkEnabled());
		Assert.assertEquals(existingLayout.getSourcePrototypeLayoutUuid(),
			newLayout.getSourcePrototypeLayoutUuid());
	}

	@Test
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUUID_G_P() {
		try {
			_persistence.countByUUID_G_P(StringPool.BLANK,
				RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

			_persistence.countByUUID_G_P(StringPool.NULL, 0L,
				RandomTestUtil.randomBoolean());

			_persistence.countByUUID_G_P((String)null, 0L,
				RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(RandomTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(RandomTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByIconImageId() {
		try {
			_persistence.countByIconImageId(RandomTestUtil.nextLong());

			_persistence.countByIconImageId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByLayoutPrototypeUuid() {
		try {
			_persistence.countByLayoutPrototypeUuid(StringPool.BLANK);

			_persistence.countByLayoutPrototypeUuid(StringPool.NULL);

			_persistence.countByLayoutPrototypeUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountBySourcePrototypeLayoutUuid() {
		try {
			_persistence.countBySourcePrototypeLayoutUuid(StringPool.BLANK);

			_persistence.countBySourcePrototypeLayoutUuid(StringPool.NULL);

			_persistence.countBySourcePrototypeLayoutUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P() {
		try {
			_persistence.countByG_P(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean());

			_persistence.countByG_P(0L, RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P_L() {
		try {
			_persistence.countByG_P_L(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), RandomTestUtil.nextLong());

			_persistence.countByG_P_L(0L, RandomTestUtil.randomBoolean(), 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P_P() {
		try {
			_persistence.countByG_P_P(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), RandomTestUtil.nextLong());

			_persistence.countByG_P_P(0L, RandomTestUtil.randomBoolean(), 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P_F() {
		try {
			_persistence.countByG_P_F(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), StringPool.BLANK);

			_persistence.countByG_P_F(0L, RandomTestUtil.randomBoolean(),
				StringPool.NULL);

			_persistence.countByG_P_F(0L, RandomTestUtil.randomBoolean(),
				(String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P_T() {
		try {
			_persistence.countByG_P_T(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), StringPool.BLANK);

			_persistence.countByG_P_T(0L, RandomTestUtil.randomBoolean(),
				StringPool.NULL);

			_persistence.countByG_P_T(0L, RandomTestUtil.randomBoolean(),
				(String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P_SPLU() {
		try {
			_persistence.countByG_P_SPLU(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), StringPool.BLANK);

			_persistence.countByG_P_SPLU(0L, RandomTestUtil.randomBoolean(),
				StringPool.NULL);

			_persistence.countByG_P_SPLU(0L, RandomTestUtil.randomBoolean(),
				(String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Layout newLayout = addLayout();

		Layout existingLayout = _persistence.findByPrimaryKey(newLayout.getPrimaryKey());

		Assert.assertEquals(existingLayout, newLayout);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchLayoutException");
		}
		catch (NoSuchLayoutException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		try {
			_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Layout", "mvccVersion",
			true, "uuid", true, "plid", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "privateLayout", true, "layoutId", true,
			"parentLayoutId", true, "name", true, "title", true, "description",
			true, "keywords", true, "robots", true, "type", true,
			"typeSettings", true, "hidden", true, "friendlyURL", true,
			"iconImageId", true, "themeId", true, "colorSchemeId", true,
			"wapThemeId", true, "wapColorSchemeId", true, "css", true,
			"priority", true, "layoutPrototypeUuid", true,
			"layoutPrototypeLinkEnabled", true, "sourcePrototypeLayoutUuid",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Layout newLayout = addLayout();

		Layout existingLayout = _persistence.fetchByPrimaryKey(newLayout.getPrimaryKey());

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

		Map<Serializable, Layout> layouts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layouts.size());
		Assert.assertEquals(newLayout1, layouts.get(newLayout1.getPrimaryKey()));
		Assert.assertEquals(newLayout2, layouts.get(newLayout2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Layout> layouts = _persistence.fetchByPrimaryKeys(primaryKeys);

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

		Map<Serializable, Layout> layouts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layouts.size());
		Assert.assertEquals(newLayout, layouts.get(newLayout.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Layout> layouts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layouts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Layout newLayout = addLayout();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayout.getPrimaryKey());

		Map<Serializable, Layout> layouts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layouts.size());
		Assert.assertEquals(newLayout, layouts.get(newLayout.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = LayoutLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					Layout layout = (Layout)object;

					Assert.assertNotNull(layout);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Layout newLayout = addLayout();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Layout.class,
				Layout.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("plid", newLayout.getPlid()));

		List<Layout> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Layout existingLayout = result.get(0);

		Assert.assertEquals(existingLayout, newLayout);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Layout.class,
				Layout.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("plid",
				RandomTestUtil.nextLong()));

		List<Layout> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Layout newLayout = addLayout();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Layout.class,
				Layout.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("plid"));

		Object newPlid = newLayout.getPlid();

		dynamicQuery.add(RestrictionsFactoryUtil.in("plid",
				new Object[] { newPlid }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPlid = result.get(0);

		Assert.assertEquals(existingPlid, newPlid);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Layout.class,
				Layout.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("plid"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("plid",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Layout newLayout = addLayout();

		_persistence.clearCache();

		LayoutModelImpl existingLayoutModelImpl = (LayoutModelImpl)_persistence.findByPrimaryKey(newLayout.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingLayoutModelImpl.getUuid(),
				existingLayoutModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingLayoutModelImpl.getGroupId(),
			existingLayoutModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingLayoutModelImpl.getPrivateLayout(),
			existingLayoutModelImpl.getOriginalPrivateLayout());

		Assert.assertEquals(existingLayoutModelImpl.getIconImageId(),
			existingLayoutModelImpl.getOriginalIconImageId());

		Assert.assertEquals(existingLayoutModelImpl.getGroupId(),
			existingLayoutModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingLayoutModelImpl.getPrivateLayout(),
			existingLayoutModelImpl.getOriginalPrivateLayout());
		Assert.assertEquals(existingLayoutModelImpl.getLayoutId(),
			existingLayoutModelImpl.getOriginalLayoutId());

		Assert.assertEquals(existingLayoutModelImpl.getGroupId(),
			existingLayoutModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingLayoutModelImpl.getPrivateLayout(),
			existingLayoutModelImpl.getOriginalPrivateLayout());
		Assert.assertTrue(Validator.equals(
				existingLayoutModelImpl.getFriendlyURL(),
				existingLayoutModelImpl.getOriginalFriendlyURL()));

		Assert.assertEquals(existingLayoutModelImpl.getGroupId(),
			existingLayoutModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingLayoutModelImpl.getPrivateLayout(),
			existingLayoutModelImpl.getOriginalPrivateLayout());
		Assert.assertTrue(Validator.equals(
				existingLayoutModelImpl.getSourcePrototypeLayoutUuid(),
				existingLayoutModelImpl.getOriginalSourcePrototypeLayoutUuid()));
	}

	protected Layout addLayout() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Layout layout = _persistence.create(pk);

		layout.setMvccVersion(RandomTestUtil.nextLong());

		layout.setUuid(RandomTestUtil.randomString());

		layout.setGroupId(RandomTestUtil.nextLong());

		layout.setCompanyId(RandomTestUtil.nextLong());

		layout.setUserId(RandomTestUtil.nextLong());

		layout.setUserName(RandomTestUtil.randomString());

		layout.setCreateDate(RandomTestUtil.nextDate());

		layout.setModifiedDate(RandomTestUtil.nextDate());

		layout.setPrivateLayout(RandomTestUtil.randomBoolean());

		layout.setLayoutId(RandomTestUtil.nextLong());

		layout.setParentLayoutId(RandomTestUtil.nextLong());

		layout.setName(RandomTestUtil.randomString());

		layout.setTitle(RandomTestUtil.randomString());

		layout.setDescription(RandomTestUtil.randomString());

		layout.setKeywords(RandomTestUtil.randomString());

		layout.setRobots(RandomTestUtil.randomString());

		layout.setType(RandomTestUtil.randomString());

		layout.setTypeSettings(RandomTestUtil.randomString());

		layout.setHidden(RandomTestUtil.randomBoolean());

		layout.setFriendlyURL(RandomTestUtil.randomString());

		layout.setIconImageId(RandomTestUtil.nextLong());

		layout.setThemeId(RandomTestUtil.randomString());

		layout.setColorSchemeId(RandomTestUtil.randomString());

		layout.setWapThemeId(RandomTestUtil.randomString());

		layout.setWapColorSchemeId(RandomTestUtil.randomString());

		layout.setCss(RandomTestUtil.randomString());

		layout.setPriority(RandomTestUtil.nextInt());

		layout.setLayoutPrototypeUuid(RandomTestUtil.randomString());

		layout.setLayoutPrototypeLinkEnabled(RandomTestUtil.randomBoolean());

		layout.setSourcePrototypeLayoutUuid(RandomTestUtil.randomString());

		_persistence.update(layout);

		return layout;
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutPersistenceTest.class);
	private ModelListener<Layout>[] _modelListeners;
	private LayoutPersistence _persistence = (LayoutPersistence)PortalBeanLocatorUtil.locate(LayoutPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}