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
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupModelImpl;

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
public class MDRRuleGroupPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<MDRRuleGroup> modelListener : _modelListeners) {
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

		for (ModelListener<MDRRuleGroup> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MDRRuleGroup mdrRuleGroup = _persistence.create(pk);

		Assert.assertNotNull(mdrRuleGroup);

		Assert.assertEquals(mdrRuleGroup.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		_persistence.remove(newMDRRuleGroup);

		MDRRuleGroup existingMDRRuleGroup = _persistence.fetchByPrimaryKey(newMDRRuleGroup.getPrimaryKey());

		Assert.assertNull(existingMDRRuleGroup);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMDRRuleGroup();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MDRRuleGroup newMDRRuleGroup = _persistence.create(pk);

		newMDRRuleGroup.setUuid(ServiceTestUtil.randomString());

		newMDRRuleGroup.setGroupId(ServiceTestUtil.nextLong());

		newMDRRuleGroup.setCompanyId(ServiceTestUtil.nextLong());

		newMDRRuleGroup.setUserId(ServiceTestUtil.nextLong());

		newMDRRuleGroup.setUserName(ServiceTestUtil.randomString());

		newMDRRuleGroup.setCreateDate(ServiceTestUtil.nextDate());

		newMDRRuleGroup.setModifiedDate(ServiceTestUtil.nextDate());

		newMDRRuleGroup.setName(ServiceTestUtil.randomString());

		newMDRRuleGroup.setDescription(ServiceTestUtil.randomString());

		_persistence.update(newMDRRuleGroup);

		MDRRuleGroup existingMDRRuleGroup = _persistence.findByPrimaryKey(newMDRRuleGroup.getPrimaryKey());

		Assert.assertEquals(existingMDRRuleGroup.getUuid(),
			newMDRRuleGroup.getUuid());
		Assert.assertEquals(existingMDRRuleGroup.getRuleGroupId(),
			newMDRRuleGroup.getRuleGroupId());
		Assert.assertEquals(existingMDRRuleGroup.getGroupId(),
			newMDRRuleGroup.getGroupId());
		Assert.assertEquals(existingMDRRuleGroup.getCompanyId(),
			newMDRRuleGroup.getCompanyId());
		Assert.assertEquals(existingMDRRuleGroup.getUserId(),
			newMDRRuleGroup.getUserId());
		Assert.assertEquals(existingMDRRuleGroup.getUserName(),
			newMDRRuleGroup.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMDRRuleGroup.getCreateDate()),
			Time.getShortTimestamp(newMDRRuleGroup.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMDRRuleGroup.getModifiedDate()),
			Time.getShortTimestamp(newMDRRuleGroup.getModifiedDate()));
		Assert.assertEquals(existingMDRRuleGroup.getName(),
			newMDRRuleGroup.getName());
		Assert.assertEquals(existingMDRRuleGroup.getDescription(),
			newMDRRuleGroup.getDescription());
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
				ServiceTestUtil.nextLong());

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
				ServiceTestUtil.nextLong());

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
			_persistence.countByGroupId(ServiceTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		MDRRuleGroup existingMDRRuleGroup = _persistence.findByPrimaryKey(newMDRRuleGroup.getPrimaryKey());

		Assert.assertEquals(existingMDRRuleGroup, newMDRRuleGroup);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchRuleGroupException");
		}
		catch (NoSuchRuleGroupException nsee) {
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
		return OrderByComparatorFactoryUtil.create("MDRRuleGroup", "uuid",
			true, "ruleGroupId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "description", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		MDRRuleGroup existingMDRRuleGroup = _persistence.fetchByPrimaryKey(newMDRRuleGroup.getPrimaryKey());

		Assert.assertEquals(existingMDRRuleGroup, newMDRRuleGroup);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MDRRuleGroup missingMDRRuleGroup = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMDRRuleGroup);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new MDRRuleGroupActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					MDRRuleGroup mdrRuleGroup = (MDRRuleGroup)object;

					Assert.assertNotNull(mdrRuleGroup);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroup.class,
				MDRRuleGroup.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleGroupId",
				newMDRRuleGroup.getRuleGroupId()));

		List<MDRRuleGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MDRRuleGroup existingMDRRuleGroup = result.get(0);

		Assert.assertEquals(existingMDRRuleGroup, newMDRRuleGroup);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroup.class,
				MDRRuleGroup.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ruleGroupId",
				ServiceTestUtil.nextLong()));

		List<MDRRuleGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroup.class,
				MDRRuleGroup.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("ruleGroupId"));

		Object newRuleGroupId = newMDRRuleGroup.getRuleGroupId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("ruleGroupId",
				new Object[] { newRuleGroupId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRuleGroupId = result.get(0);

		Assert.assertEquals(existingRuleGroupId, newRuleGroupId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MDRRuleGroup.class,
				MDRRuleGroup.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("ruleGroupId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("ruleGroupId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MDRRuleGroup newMDRRuleGroup = addMDRRuleGroup();

		_persistence.clearCache();

		MDRRuleGroupModelImpl existingMDRRuleGroupModelImpl = (MDRRuleGroupModelImpl)_persistence.findByPrimaryKey(newMDRRuleGroup.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingMDRRuleGroupModelImpl.getUuid(),
				existingMDRRuleGroupModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingMDRRuleGroupModelImpl.getGroupId(),
			existingMDRRuleGroupModelImpl.getOriginalGroupId());
	}

	protected MDRRuleGroup addMDRRuleGroup() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MDRRuleGroup mdrRuleGroup = _persistence.create(pk);

		mdrRuleGroup.setUuid(ServiceTestUtil.randomString());

		mdrRuleGroup.setGroupId(ServiceTestUtil.nextLong());

		mdrRuleGroup.setCompanyId(ServiceTestUtil.nextLong());

		mdrRuleGroup.setUserId(ServiceTestUtil.nextLong());

		mdrRuleGroup.setUserName(ServiceTestUtil.randomString());

		mdrRuleGroup.setCreateDate(ServiceTestUtil.nextDate());

		mdrRuleGroup.setModifiedDate(ServiceTestUtil.nextDate());

		mdrRuleGroup.setName(ServiceTestUtil.randomString());

		mdrRuleGroup.setDescription(ServiceTestUtil.randomString());

		_persistence.update(mdrRuleGroup);

		return mdrRuleGroup;
	}

	private static Log _log = LogFactoryUtil.getLog(MDRRuleGroupPersistenceTest.class);
	private ModelListener<MDRRuleGroup>[] _modelListeners;
	private MDRRuleGroupPersistence _persistence = (MDRRuleGroupPersistence)PortalBeanLocatorUtil.locate(MDRRuleGroupPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}