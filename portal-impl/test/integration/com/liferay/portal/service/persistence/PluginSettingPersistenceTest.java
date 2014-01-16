/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchPluginSettingException;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.model.impl.PluginSettingModelImpl;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class PluginSettingPersistenceTest {
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
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		PluginSetting pluginSetting = _persistence.create(pk);

		Assert.assertNotNull(pluginSetting);

		Assert.assertEquals(pluginSetting.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PluginSetting newPluginSetting = addPluginSetting();

		_persistence.remove(newPluginSetting);

		PluginSetting existingPluginSetting = _persistence.fetchByPrimaryKey(newPluginSetting.getPrimaryKey());

		Assert.assertNull(existingPluginSetting);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPluginSetting();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		PluginSetting newPluginSetting = _persistence.create(pk);

		newPluginSetting.setCompanyId(ServiceTestUtil.nextLong());

		newPluginSetting.setPluginId(ServiceTestUtil.randomString());

		newPluginSetting.setPluginType(ServiceTestUtil.randomString());

		newPluginSetting.setRoles(ServiceTestUtil.randomString());

		newPluginSetting.setActive(ServiceTestUtil.randomBoolean());

		newPluginSetting.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newPluginSetting);

		PluginSetting existingPluginSetting = _persistence.findByPrimaryKey(newPluginSetting.getPrimaryKey());

		Assert.assertEquals(existingPluginSetting.getPluginSettingId(),
			newPluginSetting.getPluginSettingId());
		Assert.assertEquals(existingPluginSetting.getCompanyId(),
			newPluginSetting.getCompanyId());
		Assert.assertEquals(existingPluginSetting.getPluginId(),
			newPluginSetting.getPluginId());
		Assert.assertEquals(existingPluginSetting.getPluginType(),
			newPluginSetting.getPluginType());
		Assert.assertEquals(existingPluginSetting.getRoles(),
			newPluginSetting.getRoles());
		Assert.assertEquals(existingPluginSetting.getActive(),
			newPluginSetting.getActive());
		Assert.assertEquals(existingPluginSetting.getMvccVersion(),
			newPluginSetting.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PluginSetting newPluginSetting = addPluginSetting();

		PluginSetting existingPluginSetting = _persistence.findByPrimaryKey(newPluginSetting.getPrimaryKey());

		Assert.assertEquals(existingPluginSetting, newPluginSetting);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchPluginSettingException");
		}
		catch (NoSuchPluginSettingException nsee) {
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
		return OrderByComparatorFactoryUtil.create("PluginSetting",
			"pluginSettingId", true, "companyId", true, "pluginId", true,
			"pluginType", true, "roles", true, "active", true, "mvccVersion",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PluginSetting newPluginSetting = addPluginSetting();

		PluginSetting existingPluginSetting = _persistence.fetchByPrimaryKey(newPluginSetting.getPrimaryKey());

		Assert.assertEquals(existingPluginSetting, newPluginSetting);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		PluginSetting missingPluginSetting = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPluginSetting);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new PluginSettingActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					PluginSetting pluginSetting = (PluginSetting)object;

					Assert.assertNotNull(pluginSetting);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PluginSetting newPluginSetting = addPluginSetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PluginSetting.class,
				PluginSetting.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("pluginSettingId",
				newPluginSetting.getPluginSettingId()));

		List<PluginSetting> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		PluginSetting existingPluginSetting = result.get(0);

		Assert.assertEquals(existingPluginSetting, newPluginSetting);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PluginSetting.class,
				PluginSetting.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("pluginSettingId",
				ServiceTestUtil.nextLong()));

		List<PluginSetting> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PluginSetting newPluginSetting = addPluginSetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PluginSetting.class,
				PluginSetting.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"pluginSettingId"));

		Object newPluginSettingId = newPluginSetting.getPluginSettingId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("pluginSettingId",
				new Object[] { newPluginSettingId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPluginSettingId = result.get(0);

		Assert.assertEquals(existingPluginSettingId, newPluginSettingId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PluginSetting.class,
				PluginSetting.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"pluginSettingId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("pluginSettingId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		PluginSetting newPluginSetting = addPluginSetting();

		_persistence.clearCache();

		PluginSettingModelImpl existingPluginSettingModelImpl = (PluginSettingModelImpl)_persistence.findByPrimaryKey(newPluginSetting.getPrimaryKey());

		Assert.assertEquals(existingPluginSettingModelImpl.getCompanyId(),
			existingPluginSettingModelImpl.getOriginalCompanyId());
		Assert.assertTrue(Validator.equals(
				existingPluginSettingModelImpl.getPluginId(),
				existingPluginSettingModelImpl.getOriginalPluginId()));
		Assert.assertTrue(Validator.equals(
				existingPluginSettingModelImpl.getPluginType(),
				existingPluginSettingModelImpl.getOriginalPluginType()));
	}

	protected PluginSetting addPluginSetting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		PluginSetting pluginSetting = _persistence.create(pk);

		pluginSetting.setCompanyId(ServiceTestUtil.nextLong());

		pluginSetting.setPluginId(ServiceTestUtil.randomString());

		pluginSetting.setPluginType(ServiceTestUtil.randomString());

		pluginSetting.setRoles(ServiceTestUtil.randomString());

		pluginSetting.setActive(ServiceTestUtil.randomBoolean());

		pluginSetting.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(pluginSetting);

		return pluginSetting;
	}

	private static Log _log = LogFactoryUtil.getLog(PluginSettingPersistenceTest.class);
	private PluginSettingPersistence _persistence = (PluginSettingPersistence)PortalBeanLocatorUtil.locate(PluginSettingPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}