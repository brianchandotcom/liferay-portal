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

import com.liferay.portal.NoSuchLayoutSetBranchException;
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
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutSetBranchModelImpl;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
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
public class LayoutSetBranchPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<LayoutSetBranch> modelListener : _modelListeners) {
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

		for (ModelListener<LayoutSetBranch> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSetBranch layoutSetBranch = _persistence.create(pk);

		Assert.assertNotNull(layoutSetBranch);

		Assert.assertEquals(layoutSetBranch.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutSetBranch newLayoutSetBranch = addLayoutSetBranch();

		_persistence.remove(newLayoutSetBranch);

		LayoutSetBranch existingLayoutSetBranch = _persistence.fetchByPrimaryKey(newLayoutSetBranch.getPrimaryKey());

		Assert.assertNull(existingLayoutSetBranch);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutSetBranch();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSetBranch newLayoutSetBranch = _persistence.create(pk);

		newLayoutSetBranch.setMvccVersion(RandomTestUtil.nextLong());

		newLayoutSetBranch.setGroupId(RandomTestUtil.nextLong());

		newLayoutSetBranch.setCompanyId(RandomTestUtil.nextLong());

		newLayoutSetBranch.setUserId(RandomTestUtil.nextLong());

		newLayoutSetBranch.setUserName(RandomTestUtil.randomString());

		newLayoutSetBranch.setCreateDate(RandomTestUtil.nextDate());

		newLayoutSetBranch.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutSetBranch.setPrivateLayout(RandomTestUtil.randomBoolean());

		newLayoutSetBranch.setName(RandomTestUtil.randomString());

		newLayoutSetBranch.setDescription(RandomTestUtil.randomString());

		newLayoutSetBranch.setMaster(RandomTestUtil.randomBoolean());

		newLayoutSetBranch.setLogoId(RandomTestUtil.nextLong());

		newLayoutSetBranch.setThemeId(RandomTestUtil.randomString());

		newLayoutSetBranch.setColorSchemeId(RandomTestUtil.randomString());

		newLayoutSetBranch.setWapThemeId(RandomTestUtil.randomString());

		newLayoutSetBranch.setWapColorSchemeId(RandomTestUtil.randomString());

		newLayoutSetBranch.setCss(RandomTestUtil.randomString());

		newLayoutSetBranch.setSettings(RandomTestUtil.randomString());

		newLayoutSetBranch.setLayoutSetPrototypeUuid(RandomTestUtil.randomString());

		newLayoutSetBranch.setLayoutSetPrototypeLinkEnabled(RandomTestUtil.randomBoolean());

		_persistence.update(newLayoutSetBranch);

		LayoutSetBranch existingLayoutSetBranch = _persistence.findByPrimaryKey(newLayoutSetBranch.getPrimaryKey());

		Assert.assertEquals(existingLayoutSetBranch.getMvccVersion(),
			newLayoutSetBranch.getMvccVersion());
		Assert.assertEquals(existingLayoutSetBranch.getLayoutSetBranchId(),
			newLayoutSetBranch.getLayoutSetBranchId());
		Assert.assertEquals(existingLayoutSetBranch.getGroupId(),
			newLayoutSetBranch.getGroupId());
		Assert.assertEquals(existingLayoutSetBranch.getCompanyId(),
			newLayoutSetBranch.getCompanyId());
		Assert.assertEquals(existingLayoutSetBranch.getUserId(),
			newLayoutSetBranch.getUserId());
		Assert.assertEquals(existingLayoutSetBranch.getUserName(),
			newLayoutSetBranch.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutSetBranch.getCreateDate()),
			Time.getShortTimestamp(newLayoutSetBranch.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutSetBranch.getModifiedDate()),
			Time.getShortTimestamp(newLayoutSetBranch.getModifiedDate()));
		Assert.assertEquals(existingLayoutSetBranch.getPrivateLayout(),
			newLayoutSetBranch.getPrivateLayout());
		Assert.assertEquals(existingLayoutSetBranch.getName(),
			newLayoutSetBranch.getName());
		Assert.assertEquals(existingLayoutSetBranch.getDescription(),
			newLayoutSetBranch.getDescription());
		Assert.assertEquals(existingLayoutSetBranch.getMaster(),
			newLayoutSetBranch.getMaster());
		Assert.assertEquals(existingLayoutSetBranch.getLogoId(),
			newLayoutSetBranch.getLogoId());
		Assert.assertEquals(existingLayoutSetBranch.getThemeId(),
			newLayoutSetBranch.getThemeId());
		Assert.assertEquals(existingLayoutSetBranch.getColorSchemeId(),
			newLayoutSetBranch.getColorSchemeId());
		Assert.assertEquals(existingLayoutSetBranch.getWapThemeId(),
			newLayoutSetBranch.getWapThemeId());
		Assert.assertEquals(existingLayoutSetBranch.getWapColorSchemeId(),
			newLayoutSetBranch.getWapColorSchemeId());
		Assert.assertEquals(existingLayoutSetBranch.getCss(),
			newLayoutSetBranch.getCss());
		Assert.assertEquals(existingLayoutSetBranch.getSettings(),
			newLayoutSetBranch.getSettings());
		Assert.assertEquals(existingLayoutSetBranch.getLayoutSetPrototypeUuid(),
			newLayoutSetBranch.getLayoutSetPrototypeUuid());
		Assert.assertEquals(existingLayoutSetBranch.getLayoutSetPrototypeLinkEnabled(),
			newLayoutSetBranch.getLayoutSetPrototypeLinkEnabled());
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
	public void testCountByG_P_N() {
		try {
			_persistence.countByG_P_N(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), StringPool.BLANK);

			_persistence.countByG_P_N(0L, RandomTestUtil.randomBoolean(),
				StringPool.NULL);

			_persistence.countByG_P_N(0L, RandomTestUtil.randomBoolean(),
				(String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_P_M() {
		try {
			_persistence.countByG_P_M(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean());

			_persistence.countByG_P_M(0L, RandomTestUtil.randomBoolean(),
				RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutSetBranch newLayoutSetBranch = addLayoutSetBranch();

		LayoutSetBranch existingLayoutSetBranch = _persistence.findByPrimaryKey(newLayoutSetBranch.getPrimaryKey());

		Assert.assertEquals(existingLayoutSetBranch, newLayoutSetBranch);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchLayoutSetBranchException");
		}
		catch (NoSuchLayoutSetBranchException nsee) {
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
		return OrderByComparatorFactoryUtil.create("LayoutSetBranch",
			"mvccVersion", true, "layoutSetBranchId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "privateLayout", true, "name", true,
			"description", true, "master", true, "logoId", true, "themeId",
			true, "colorSchemeId", true, "wapThemeId", true,
			"wapColorSchemeId", true, "css", true, "settings", true,
			"layoutSetPrototypeUuid", true, "layoutSetPrototypeLinkEnabled",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutSetBranch newLayoutSetBranch = addLayoutSetBranch();

		LayoutSetBranch existingLayoutSetBranch = _persistence.fetchByPrimaryKey(newLayoutSetBranch.getPrimaryKey());

		Assert.assertEquals(existingLayoutSetBranch, newLayoutSetBranch);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSetBranch missingLayoutSetBranch = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutSetBranch);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		LayoutSetBranch newLayoutSetBranch1 = addLayoutSetBranch();
		LayoutSetBranch newLayoutSetBranch2 = addLayoutSetBranch();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSetBranch1.getPrimaryKey());
		primaryKeys.add(newLayoutSetBranch2.getPrimaryKey());

		Map<Serializable, LayoutSetBranch> layoutSetBranchs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutSetBranchs.size());
		Assert.assertEquals(newLayoutSetBranch1,
			layoutSetBranchs.get(newLayoutSetBranch1.getPrimaryKey()));
		Assert.assertEquals(newLayoutSetBranch2,
			layoutSetBranchs.get(newLayoutSetBranch2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutSetBranch> layoutSetBranchs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSetBranchs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		LayoutSetBranch newLayoutSetBranch = addLayoutSetBranch();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSetBranch.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutSetBranch> layoutSetBranchs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSetBranchs.size());
		Assert.assertEquals(newLayoutSetBranch,
			layoutSetBranchs.get(newLayoutSetBranch.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutSetBranch> layoutSetBranchs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSetBranchs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		LayoutSetBranch newLayoutSetBranch = addLayoutSetBranch();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSetBranch.getPrimaryKey());

		Map<Serializable, LayoutSetBranch> layoutSetBranchs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSetBranchs.size());
		Assert.assertEquals(newLayoutSetBranch,
			layoutSetBranchs.get(newLayoutSetBranch.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = LayoutSetBranchLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					LayoutSetBranch layoutSetBranch = (LayoutSetBranch)object;

					Assert.assertNotNull(layoutSetBranch);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutSetBranch newLayoutSetBranch = addLayoutSetBranch();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetBranch.class,
				LayoutSetBranch.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetBranchId",
				newLayoutSetBranch.getLayoutSetBranchId()));

		List<LayoutSetBranch> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutSetBranch existingLayoutSetBranch = result.get(0);

		Assert.assertEquals(existingLayoutSetBranch, newLayoutSetBranch);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetBranch.class,
				LayoutSetBranch.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetBranchId",
				RandomTestUtil.nextLong()));

		List<LayoutSetBranch> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		LayoutSetBranch newLayoutSetBranch = addLayoutSetBranch();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetBranch.class,
				LayoutSetBranch.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutSetBranchId"));

		Object newLayoutSetBranchId = newLayoutSetBranch.getLayoutSetBranchId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutSetBranchId",
				new Object[] { newLayoutSetBranchId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutSetBranchId = result.get(0);

		Assert.assertEquals(existingLayoutSetBranchId, newLayoutSetBranchId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetBranch.class,
				LayoutSetBranch.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutSetBranchId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutSetBranchId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		LayoutSetBranch newLayoutSetBranch = addLayoutSetBranch();

		_persistence.clearCache();

		LayoutSetBranchModelImpl existingLayoutSetBranchModelImpl = (LayoutSetBranchModelImpl)_persistence.findByPrimaryKey(newLayoutSetBranch.getPrimaryKey());

		Assert.assertEquals(existingLayoutSetBranchModelImpl.getGroupId(),
			existingLayoutSetBranchModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingLayoutSetBranchModelImpl.getPrivateLayout(),
			existingLayoutSetBranchModelImpl.getOriginalPrivateLayout());
		Assert.assertTrue(Validator.equals(
				existingLayoutSetBranchModelImpl.getName(),
				existingLayoutSetBranchModelImpl.getOriginalName()));
	}

	protected LayoutSetBranch addLayoutSetBranch() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSetBranch layoutSetBranch = _persistence.create(pk);

		layoutSetBranch.setMvccVersion(RandomTestUtil.nextLong());

		layoutSetBranch.setGroupId(RandomTestUtil.nextLong());

		layoutSetBranch.setCompanyId(RandomTestUtil.nextLong());

		layoutSetBranch.setUserId(RandomTestUtil.nextLong());

		layoutSetBranch.setUserName(RandomTestUtil.randomString());

		layoutSetBranch.setCreateDate(RandomTestUtil.nextDate());

		layoutSetBranch.setModifiedDate(RandomTestUtil.nextDate());

		layoutSetBranch.setPrivateLayout(RandomTestUtil.randomBoolean());

		layoutSetBranch.setName(RandomTestUtil.randomString());

		layoutSetBranch.setDescription(RandomTestUtil.randomString());

		layoutSetBranch.setMaster(RandomTestUtil.randomBoolean());

		layoutSetBranch.setLogoId(RandomTestUtil.nextLong());

		layoutSetBranch.setThemeId(RandomTestUtil.randomString());

		layoutSetBranch.setColorSchemeId(RandomTestUtil.randomString());

		layoutSetBranch.setWapThemeId(RandomTestUtil.randomString());

		layoutSetBranch.setWapColorSchemeId(RandomTestUtil.randomString());

		layoutSetBranch.setCss(RandomTestUtil.randomString());

		layoutSetBranch.setSettings(RandomTestUtil.randomString());

		layoutSetBranch.setLayoutSetPrototypeUuid(RandomTestUtil.randomString());

		layoutSetBranch.setLayoutSetPrototypeLinkEnabled(RandomTestUtil.randomBoolean());

		_persistence.update(layoutSetBranch);

		return layoutSetBranch;
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutSetBranchPersistenceTest.class);
	private ModelListener<LayoutSetBranch>[] _modelListeners;
	private LayoutSetBranchPersistence _persistence = (LayoutSetBranchPersistence)PortalBeanLocatorUtil.locate(LayoutSetBranchPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}