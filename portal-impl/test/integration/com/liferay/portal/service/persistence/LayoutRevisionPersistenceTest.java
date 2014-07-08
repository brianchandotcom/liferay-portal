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

import com.liferay.portal.NoSuchLayoutRevisionException;
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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutRevisionModelImpl;
import com.liferay.portal.service.LayoutRevisionLocalServiceUtil;
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
public class LayoutRevisionPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<LayoutRevision> modelListener : _modelListeners) {
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

		for (ModelListener<LayoutRevision> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutRevision layoutRevision = _persistence.create(pk);

		Assert.assertNotNull(layoutRevision);

		Assert.assertEquals(layoutRevision.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		_persistence.remove(newLayoutRevision);

		LayoutRevision existingLayoutRevision = _persistence.fetchByPrimaryKey(newLayoutRevision.getPrimaryKey());

		Assert.assertNull(existingLayoutRevision);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutRevision();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutRevision newLayoutRevision = _persistence.create(pk);

		newLayoutRevision.setMvccVersion(RandomTestUtil.nextLong());

		newLayoutRevision.setGroupId(RandomTestUtil.nextLong());

		newLayoutRevision.setCompanyId(RandomTestUtil.nextLong());

		newLayoutRevision.setUserId(RandomTestUtil.nextLong());

		newLayoutRevision.setUserName(RandomTestUtil.randomString());

		newLayoutRevision.setCreateDate(RandomTestUtil.nextDate());

		newLayoutRevision.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutRevision.setLayoutSetBranchId(RandomTestUtil.nextLong());

		newLayoutRevision.setLayoutBranchId(RandomTestUtil.nextLong());

		newLayoutRevision.setParentLayoutRevisionId(RandomTestUtil.nextLong());

		newLayoutRevision.setHead(RandomTestUtil.randomBoolean());

		newLayoutRevision.setMajor(RandomTestUtil.randomBoolean());

		newLayoutRevision.setPlid(RandomTestUtil.nextLong());

		newLayoutRevision.setPrivateLayout(RandomTestUtil.randomBoolean());

		newLayoutRevision.setName(RandomTestUtil.randomString());

		newLayoutRevision.setTitle(RandomTestUtil.randomString());

		newLayoutRevision.setDescription(RandomTestUtil.randomString());

		newLayoutRevision.setKeywords(RandomTestUtil.randomString());

		newLayoutRevision.setRobots(RandomTestUtil.randomString());

		newLayoutRevision.setTypeSettings(RandomTestUtil.randomString());

		newLayoutRevision.setIconImageId(RandomTestUtil.nextLong());

		newLayoutRevision.setThemeId(RandomTestUtil.randomString());

		newLayoutRevision.setColorSchemeId(RandomTestUtil.randomString());

		newLayoutRevision.setWapThemeId(RandomTestUtil.randomString());

		newLayoutRevision.setWapColorSchemeId(RandomTestUtil.randomString());

		newLayoutRevision.setCss(RandomTestUtil.randomString());

		newLayoutRevision.setStatus(RandomTestUtil.nextInt());

		newLayoutRevision.setStatusByUserId(RandomTestUtil.nextLong());

		newLayoutRevision.setStatusByUserName(RandomTestUtil.randomString());

		newLayoutRevision.setStatusDate(RandomTestUtil.nextDate());

		_persistence.update(newLayoutRevision);

		LayoutRevision existingLayoutRevision = _persistence.findByPrimaryKey(newLayoutRevision.getPrimaryKey());

		Assert.assertEquals(existingLayoutRevision.getMvccVersion(),
			newLayoutRevision.getMvccVersion());
		Assert.assertEquals(existingLayoutRevision.getLayoutRevisionId(),
			newLayoutRevision.getLayoutRevisionId());
		Assert.assertEquals(existingLayoutRevision.getGroupId(),
			newLayoutRevision.getGroupId());
		Assert.assertEquals(existingLayoutRevision.getCompanyId(),
			newLayoutRevision.getCompanyId());
		Assert.assertEquals(existingLayoutRevision.getUserId(),
			newLayoutRevision.getUserId());
		Assert.assertEquals(existingLayoutRevision.getUserName(),
			newLayoutRevision.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutRevision.getCreateDate()),
			Time.getShortTimestamp(newLayoutRevision.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutRevision.getModifiedDate()),
			Time.getShortTimestamp(newLayoutRevision.getModifiedDate()));
		Assert.assertEquals(existingLayoutRevision.getLayoutSetBranchId(),
			newLayoutRevision.getLayoutSetBranchId());
		Assert.assertEquals(existingLayoutRevision.getLayoutBranchId(),
			newLayoutRevision.getLayoutBranchId());
		Assert.assertEquals(existingLayoutRevision.getParentLayoutRevisionId(),
			newLayoutRevision.getParentLayoutRevisionId());
		Assert.assertEquals(existingLayoutRevision.getHead(),
			newLayoutRevision.getHead());
		Assert.assertEquals(existingLayoutRevision.getMajor(),
			newLayoutRevision.getMajor());
		Assert.assertEquals(existingLayoutRevision.getPlid(),
			newLayoutRevision.getPlid());
		Assert.assertEquals(existingLayoutRevision.getPrivateLayout(),
			newLayoutRevision.getPrivateLayout());
		Assert.assertEquals(existingLayoutRevision.getName(),
			newLayoutRevision.getName());
		Assert.assertEquals(existingLayoutRevision.getTitle(),
			newLayoutRevision.getTitle());
		Assert.assertEquals(existingLayoutRevision.getDescription(),
			newLayoutRevision.getDescription());
		Assert.assertEquals(existingLayoutRevision.getKeywords(),
			newLayoutRevision.getKeywords());
		Assert.assertEquals(existingLayoutRevision.getRobots(),
			newLayoutRevision.getRobots());
		Assert.assertEquals(existingLayoutRevision.getTypeSettings(),
			newLayoutRevision.getTypeSettings());
		Assert.assertEquals(existingLayoutRevision.getIconImageId(),
			newLayoutRevision.getIconImageId());
		Assert.assertEquals(existingLayoutRevision.getThemeId(),
			newLayoutRevision.getThemeId());
		Assert.assertEquals(existingLayoutRevision.getColorSchemeId(),
			newLayoutRevision.getColorSchemeId());
		Assert.assertEquals(existingLayoutRevision.getWapThemeId(),
			newLayoutRevision.getWapThemeId());
		Assert.assertEquals(existingLayoutRevision.getWapColorSchemeId(),
			newLayoutRevision.getWapColorSchemeId());
		Assert.assertEquals(existingLayoutRevision.getCss(),
			newLayoutRevision.getCss());
		Assert.assertEquals(existingLayoutRevision.getStatus(),
			newLayoutRevision.getStatus());
		Assert.assertEquals(existingLayoutRevision.getStatusByUserId(),
			newLayoutRevision.getStatusByUserId());
		Assert.assertEquals(existingLayoutRevision.getStatusByUserName(),
			newLayoutRevision.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutRevision.getStatusDate()),
			Time.getShortTimestamp(newLayoutRevision.getStatusDate()));
	}

	@Test
	public void testCountByLayoutSetBranchId() {
		try {
			_persistence.countByLayoutSetBranchId(RandomTestUtil.nextLong());

			_persistence.countByLayoutSetBranchId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByPlid() {
		try {
			_persistence.countByPlid(RandomTestUtil.nextLong());

			_persistence.countByPlid(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByL_H() {
		try {
			_persistence.countByL_H(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean());

			_persistence.countByL_H(0L, RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByL_P() {
		try {
			_persistence.countByL_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByL_P(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByL_S() {
		try {
			_persistence.countByL_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByL_S(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByH_P() {
		try {
			_persistence.countByH_P(RandomTestUtil.randomBoolean(),
				RandomTestUtil.nextLong());

			_persistence.countByH_P(RandomTestUtil.randomBoolean(), 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByP_NotS() {
		try {
			_persistence.countByP_NotS(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByP_NotS(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByL_L_P() {
		try {
			_persistence.countByL_L_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

			_persistence.countByL_L_P(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByL_P_P() {
		try {
			_persistence.countByL_P_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

			_persistence.countByL_P_P(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByL_H_P() {
		try {
			_persistence.countByL_H_P(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), RandomTestUtil.nextLong());

			_persistence.countByL_H_P(0L, RandomTestUtil.randomBoolean(), 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByL_P_S() {
		try {
			_persistence.countByL_P_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

			_persistence.countByL_P_S(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		LayoutRevision existingLayoutRevision = _persistence.findByPrimaryKey(newLayoutRevision.getPrimaryKey());

		Assert.assertEquals(existingLayoutRevision, newLayoutRevision);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchLayoutRevisionException");
		}
		catch (NoSuchLayoutRevisionException nsee) {
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

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("LayoutRevision",
			"mvccVersion", true, "layoutRevisionId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "layoutSetBranchId", true,
			"layoutBranchId", true, "parentLayoutRevisionId", true, "head",
			true, "major", true, "plid", true, "privateLayout", true, "name",
			true, "title", true, "description", true, "keywords", true,
			"robots", true, "typeSettings", true, "iconImageId", true,
			"themeId", true, "colorSchemeId", true, "wapThemeId", true,
			"wapColorSchemeId", true, "css", true, "status", true,
			"statusByUserId", true, "statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		LayoutRevision existingLayoutRevision = _persistence.fetchByPrimaryKey(newLayoutRevision.getPrimaryKey());

		Assert.assertEquals(existingLayoutRevision, newLayoutRevision);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutRevision missingLayoutRevision = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutRevision);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		LayoutRevision newLayoutRevision1 = addLayoutRevision();
		LayoutRevision newLayoutRevision2 = addLayoutRevision();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutRevision1.getPrimaryKey());
		primaryKeys.add(newLayoutRevision2.getPrimaryKey());

		Map<Serializable, LayoutRevision> layoutRevisions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutRevisions.size());
		Assert.assertEquals(newLayoutRevision1,
			layoutRevisions.get(newLayoutRevision1.getPrimaryKey()));
		Assert.assertEquals(newLayoutRevision2,
			layoutRevisions.get(newLayoutRevision2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutRevision> layoutRevisions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutRevisions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutRevision.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutRevision> layoutRevisions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutRevisions.size());
		Assert.assertEquals(newLayoutRevision,
			layoutRevisions.get(newLayoutRevision.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutRevision> layoutRevisions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutRevisions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutRevision.getPrimaryKey());

		Map<Serializable, LayoutRevision> layoutRevisions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutRevisions.size());
		Assert.assertEquals(newLayoutRevision,
			layoutRevisions.get(newLayoutRevision.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = LayoutRevisionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					LayoutRevision layoutRevision = (LayoutRevision)object;

					Assert.assertNotNull(layoutRevision);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutRevision.class,
				LayoutRevision.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutRevisionId",
				newLayoutRevision.getLayoutRevisionId()));

		List<LayoutRevision> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutRevision existingLayoutRevision = result.get(0);

		Assert.assertEquals(existingLayoutRevision, newLayoutRevision);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutRevision.class,
				LayoutRevision.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutRevisionId",
				RandomTestUtil.nextLong()));

		List<LayoutRevision> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutRevision.class,
				LayoutRevision.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutRevisionId"));

		Object newLayoutRevisionId = newLayoutRevision.getLayoutRevisionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutRevisionId",
				new Object[] { newLayoutRevisionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutRevisionId = result.get(0);

		Assert.assertEquals(existingLayoutRevisionId, newLayoutRevisionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutRevision.class,
				LayoutRevision.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutRevisionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutRevisionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		LayoutRevision newLayoutRevision = addLayoutRevision();

		_persistence.clearCache();

		LayoutRevisionModelImpl existingLayoutRevisionModelImpl = (LayoutRevisionModelImpl)_persistence.findByPrimaryKey(newLayoutRevision.getPrimaryKey());

		Assert.assertEquals(existingLayoutRevisionModelImpl.getLayoutSetBranchId(),
			existingLayoutRevisionModelImpl.getOriginalLayoutSetBranchId());
		Assert.assertEquals(existingLayoutRevisionModelImpl.getHead(),
			existingLayoutRevisionModelImpl.getOriginalHead());
		Assert.assertEquals(existingLayoutRevisionModelImpl.getPlid(),
			existingLayoutRevisionModelImpl.getOriginalPlid());
	}

	protected LayoutRevision addLayoutRevision() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutRevision layoutRevision = _persistence.create(pk);

		layoutRevision.setMvccVersion(RandomTestUtil.nextLong());

		layoutRevision.setGroupId(RandomTestUtil.nextLong());

		layoutRevision.setCompanyId(RandomTestUtil.nextLong());

		layoutRevision.setUserId(RandomTestUtil.nextLong());

		layoutRevision.setUserName(RandomTestUtil.randomString());

		layoutRevision.setCreateDate(RandomTestUtil.nextDate());

		layoutRevision.setModifiedDate(RandomTestUtil.nextDate());

		layoutRevision.setLayoutSetBranchId(RandomTestUtil.nextLong());

		layoutRevision.setLayoutBranchId(RandomTestUtil.nextLong());

		layoutRevision.setParentLayoutRevisionId(RandomTestUtil.nextLong());

		layoutRevision.setHead(RandomTestUtil.randomBoolean());

		layoutRevision.setMajor(RandomTestUtil.randomBoolean());

		layoutRevision.setPlid(RandomTestUtil.nextLong());

		layoutRevision.setPrivateLayout(RandomTestUtil.randomBoolean());

		layoutRevision.setName(RandomTestUtil.randomString());

		layoutRevision.setTitle(RandomTestUtil.randomString());

		layoutRevision.setDescription(RandomTestUtil.randomString());

		layoutRevision.setKeywords(RandomTestUtil.randomString());

		layoutRevision.setRobots(RandomTestUtil.randomString());

		layoutRevision.setTypeSettings(RandomTestUtil.randomString());

		layoutRevision.setIconImageId(RandomTestUtil.nextLong());

		layoutRevision.setThemeId(RandomTestUtil.randomString());

		layoutRevision.setColorSchemeId(RandomTestUtil.randomString());

		layoutRevision.setWapThemeId(RandomTestUtil.randomString());

		layoutRevision.setWapColorSchemeId(RandomTestUtil.randomString());

		layoutRevision.setCss(RandomTestUtil.randomString());

		layoutRevision.setStatus(RandomTestUtil.nextInt());

		layoutRevision.setStatusByUserId(RandomTestUtil.nextLong());

		layoutRevision.setStatusByUserName(RandomTestUtil.randomString());

		layoutRevision.setStatusDate(RandomTestUtil.nextDate());

		_persistence.update(layoutRevision);

		return layoutRevision;
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutRevisionPersistenceTest.class);
	private ModelListener<LayoutRevision>[] _modelListeners;
	private LayoutRevisionPersistence _persistence = (LayoutRevisionPersistence)PortalBeanLocatorUtil.locate(LayoutRevisionPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}