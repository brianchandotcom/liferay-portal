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

package com.liferay.portlet.mobiledevicerules.service.persistence;

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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.mobiledevicerules.NoSuchRuleException;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleModelImpl;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Edward C. Han
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class MDRRulePersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<MDRRule> modelListener : _modelListeners) {
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

		for (ModelListener<MDRRule> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRRule mdrRule = _persistence.create(pk);

		Assert.assertNotNull(mdrRule);

		Assert.assertEquals(mdrRule.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MDRRule newMDRRule = addMDRRule();

		_persistence.remove(newMDRRule);

		MDRRule existingMDRRule = _persistence.fetchByPrimaryKey(newMDRRule.getPrimaryKey());

		Assert.assertNull(existingMDRRule);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMDRRule();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRRule newMDRRule = _persistence.create(pk);

		newMDRRule.setUuid(RandomTestUtil.randomString());

		newMDRRule.setGroupId(RandomTestUtil.nextLong());

		newMDRRule.setCompanyId(RandomTestUtil.nextLong());

		newMDRRule.setUserId(RandomTestUtil.nextLong());

		newMDRRule.setUserName(RandomTestUtil.randomString());

		newMDRRule.setCreateDate(RandomTestUtil.nextDate());

		newMDRRule.setModifiedDate(RandomTestUtil.nextDate());

		newMDRRule.setRuleGroupId(RandomTestUtil.nextLong());

		newMDRRule.setName(RandomTestUtil.randomString());

		newMDRRule.setDescription(RandomTestUtil.randomString());

		newMDRRule.setType(RandomTestUtil.randomString());

		newMDRRule.setTypeSettings(RandomTestUtil.randomString());

		_persistence.update(newMDRRule);

		MDRRule existingMDRRule = _persistence.findByPrimaryKey(newMDRRule.getPrimaryKey());

		Assert.assertEquals(existingMDRRule.getUuid(), newMDRRule.getUuid());
		Assert.assertEquals(existingMDRRule.getRuleId(), newMDRRule.getRuleId());
		Assert.assertEquals(existingMDRRule.getGroupId(),
			newMDRRule.getGroupId());
		Assert.assertEquals(existingMDRRule.getCompanyId(),
			newMDRRule.getCompanyId());
		Assert.assertEquals(existingMDRRule.getUserId(), newMDRRule.getUserId());
		Assert.assertEquals(existingMDRRule.getUserName(),
			newMDRRule.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMDRRule.getCreateDate()),
			Time.getShortTimestamp(newMDRRule.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMDRRule.getModifiedDate()),
			Time.getShortTimestamp(newMDRRule.getModifiedDate()));
		Assert.assertEquals(existingMDRRule.getRuleGroupId(),
			newMDRRule.getRuleGroupId());
		Assert.assertEquals(existingMDRRule.getName(), newMDRRule.getName());
		Assert.assertEquals(existingMDRRule.getDescription(),
			newMDRRule.getDescription());
		Assert.assertEquals(existingMDRRule.getType(), newMDRRule.getType());
		Assert.assertEquals(existingMDRRule.getTypeSettings(),
			newMDRRule.getTypeSettings());
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
	public void testCountByUUID_G() {
		try {
			_persistence.countByUUID_G(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUUID_G(StringPool.NULL, 0L);

			_persistence.countByUUID_G((String)null, 0L);
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
	public void testCountByRuleGroupId() {
		try {
			_persistence.countByRuleGroupId(RandomTestUtil.nextLong());

			_persistence.countByRuleGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MDRRule newMDRRule = addMDRRule();

		MDRRule existingMDRRule = _persistence.findByPrimaryKey(newMDRRule.getPrimaryKey());

		Assert.assertEquals(existingMDRRule, newMDRRule);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchRuleException");
		}
		catch (NoSuchRuleException nsee) {
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
		return OrderByComparatorFactoryUtil.create("MDRRule", "uuid", true,
			"ruleId", true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"ruleGroupId", true, "name", true, "description", true, "type",
			true, "typeSettings", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MDRRule newMDRRule = addMDRRule();

		MDRRule existingMDRRule = _persistence.fetchByPrimaryKey(newMDRRule.getPrimaryKey());

		Assert.assertEquals(existingMDRRule, newMDRRule);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRRule missingMDRRule = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMDRRule);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = MDRRuleLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					MDRRule mdrRule = (MDRRule)object;

					Assert.assertNotNull(mdrRule);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MDRRule newMDRRule = addMDRRule();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRule.class,
				MDRRule.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleId",
				newMDRRule.getRuleId()));

		List<MDRRule> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MDRRule existingMDRRule = result.get(0);

		Assert.assertEquals(existingMDRRule, newMDRRule);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRule.class,
				MDRRule.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleId",
				RandomTestUtil.nextLong()));

		List<MDRRule> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MDRRule newMDRRule = addMDRRule();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRule.class,
				MDRRule.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("ruleId"));

		Object newRuleId = newMDRRule.getRuleId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("ruleId",
				new Object[] { newRuleId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRuleId = result.get(0);

		Assert.assertEquals(existingRuleId, newRuleId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRule.class,
				MDRRule.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("ruleId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("ruleId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MDRRule newMDRRule = addMDRRule();

		_persistence.clearCache();

		MDRRuleModelImpl existingMDRRuleModelImpl = (MDRRuleModelImpl)_persistence.findByPrimaryKey(newMDRRule.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingMDRRuleModelImpl.getUuid(),
				existingMDRRuleModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingMDRRuleModelImpl.getGroupId(),
			existingMDRRuleModelImpl.getOriginalGroupId());
	}

	protected MDRRule addMDRRule() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MDRRule mdrRule = _persistence.create(pk);

		mdrRule.setUuid(RandomTestUtil.randomString());

		mdrRule.setGroupId(RandomTestUtil.nextLong());

		mdrRule.setCompanyId(RandomTestUtil.nextLong());

		mdrRule.setUserId(RandomTestUtil.nextLong());

		mdrRule.setUserName(RandomTestUtil.randomString());

		mdrRule.setCreateDate(RandomTestUtil.nextDate());

		mdrRule.setModifiedDate(RandomTestUtil.nextDate());

		mdrRule.setRuleGroupId(RandomTestUtil.nextLong());

		mdrRule.setName(RandomTestUtil.randomString());

		mdrRule.setDescription(RandomTestUtil.randomString());

		mdrRule.setType(RandomTestUtil.randomString());

		mdrRule.setTypeSettings(RandomTestUtil.randomString());

		_persistence.update(mdrRule);

		return mdrRule;
	}

	private static Log _log = LogFactoryUtil.getLog(MDRRulePersistenceTest.class);
	private ModelListener<MDRRule>[] _modelListeners;
	private MDRRulePersistence _persistence = (MDRRulePersistence)PortalBeanLocatorUtil.locate(MDRRulePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}