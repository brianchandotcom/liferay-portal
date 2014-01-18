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
import com.liferay.portal.model.impl.LayoutRevisionModelImpl;
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
public class LayoutRevisionPersistenceTest {
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
		long pk = ServiceTestUtil.nextLong();

		LayoutRevision newLayoutRevision = _persistence.create(pk);

		newLayoutRevision.setGroupId(ServiceTestUtil.nextLong());

		newLayoutRevision.setCompanyId(ServiceTestUtil.nextLong());

		newLayoutRevision.setUserId(ServiceTestUtil.nextLong());

		newLayoutRevision.setUserName(ServiceTestUtil.randomString());

		newLayoutRevision.setCreateDate(ServiceTestUtil.nextDate());

		newLayoutRevision.setModifiedDate(ServiceTestUtil.nextDate());

		newLayoutRevision.setLayoutSetBranchId(ServiceTestUtil.nextLong());

		newLayoutRevision.setLayoutBranchId(ServiceTestUtil.nextLong());

		newLayoutRevision.setParentLayoutRevisionId(ServiceTestUtil.nextLong());

		newLayoutRevision.setHead(ServiceTestUtil.randomBoolean());

		newLayoutRevision.setMajor(ServiceTestUtil.randomBoolean());

		newLayoutRevision.setPlid(ServiceTestUtil.nextLong());

		newLayoutRevision.setPrivateLayout(ServiceTestUtil.randomBoolean());

		newLayoutRevision.setName(ServiceTestUtil.randomString());

		newLayoutRevision.setTitle(ServiceTestUtil.randomString());

		newLayoutRevision.setDescription(ServiceTestUtil.randomString());

		newLayoutRevision.setKeywords(ServiceTestUtil.randomString());

		newLayoutRevision.setRobots(ServiceTestUtil.randomString());

		newLayoutRevision.setTypeSettings(ServiceTestUtil.randomString());

		newLayoutRevision.setIconImageId(ServiceTestUtil.nextLong());

		newLayoutRevision.setThemeId(ServiceTestUtil.randomString());

		newLayoutRevision.setColorSchemeId(ServiceTestUtil.randomString());

		newLayoutRevision.setWapThemeId(ServiceTestUtil.randomString());

		newLayoutRevision.setWapColorSchemeId(ServiceTestUtil.randomString());

		newLayoutRevision.setCss(ServiceTestUtil.randomString());

		newLayoutRevision.setStatus(ServiceTestUtil.nextInt());

		newLayoutRevision.setStatusByUserId(ServiceTestUtil.nextLong());

		newLayoutRevision.setStatusByUserName(ServiceTestUtil.randomString());

		newLayoutRevision.setStatusDate(ServiceTestUtil.nextDate());

		newLayoutRevision.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newLayoutRevision);

		LayoutRevision existingLayoutRevision = _persistence.findByPrimaryKey(newLayoutRevision.getPrimaryKey());

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
		Assert.assertEquals(existingLayoutRevision.getMvccVersion(),
			newLayoutRevision.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		LayoutRevision existingLayoutRevision = _persistence.findByPrimaryKey(newLayoutRevision.getPrimaryKey());

		Assert.assertEquals(existingLayoutRevision, newLayoutRevision);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

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
			"layoutRevisionId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "layoutSetBranchId", true, "layoutBranchId",
			true, "parentLayoutRevisionId", true, "head", true, "major", true,
			"plid", true, "privateLayout", true, "name", true, "title", true,
			"description", true, "keywords", true, "robots", true,
			"typeSettings", true, "iconImageId", true, "themeId", true,
			"colorSchemeId", true, "wapThemeId", true, "wapColorSchemeId",
			true, "css", true, "status", true, "statusByUserId", true,
			"statusByUserName", true, "statusDate", true, "mvccVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		LayoutRevision existingLayoutRevision = _persistence.fetchByPrimaryKey(newLayoutRevision.getPrimaryKey());

		Assert.assertEquals(existingLayoutRevision, newLayoutRevision);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		LayoutRevision missingLayoutRevision = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutRevision);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new LayoutRevisionActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					LayoutRevision layoutRevision = (LayoutRevision)object;

					Assert.assertNotNull(layoutRevision);

					count.increment();
				}
			};

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
				ServiceTestUtil.nextLong()));

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
				new Object[] { ServiceTestUtil.nextLong() }));

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
		long pk = ServiceTestUtil.nextLong();

		LayoutRevision layoutRevision = _persistence.create(pk);

		layoutRevision.setGroupId(ServiceTestUtil.nextLong());

		layoutRevision.setCompanyId(ServiceTestUtil.nextLong());

		layoutRevision.setUserId(ServiceTestUtil.nextLong());

		layoutRevision.setUserName(ServiceTestUtil.randomString());

		layoutRevision.setCreateDate(ServiceTestUtil.nextDate());

		layoutRevision.setModifiedDate(ServiceTestUtil.nextDate());

		layoutRevision.setLayoutSetBranchId(ServiceTestUtil.nextLong());

		layoutRevision.setLayoutBranchId(ServiceTestUtil.nextLong());

		layoutRevision.setParentLayoutRevisionId(ServiceTestUtil.nextLong());

		layoutRevision.setHead(ServiceTestUtil.randomBoolean());

		layoutRevision.setMajor(ServiceTestUtil.randomBoolean());

		layoutRevision.setPlid(ServiceTestUtil.nextLong());

		layoutRevision.setPrivateLayout(ServiceTestUtil.randomBoolean());

		layoutRevision.setName(ServiceTestUtil.randomString());

		layoutRevision.setTitle(ServiceTestUtil.randomString());

		layoutRevision.setDescription(ServiceTestUtil.randomString());

		layoutRevision.setKeywords(ServiceTestUtil.randomString());

		layoutRevision.setRobots(ServiceTestUtil.randomString());

		layoutRevision.setTypeSettings(ServiceTestUtil.randomString());

		layoutRevision.setIconImageId(ServiceTestUtil.nextLong());

		layoutRevision.setThemeId(ServiceTestUtil.randomString());

		layoutRevision.setColorSchemeId(ServiceTestUtil.randomString());

		layoutRevision.setWapThemeId(ServiceTestUtil.randomString());

		layoutRevision.setWapColorSchemeId(ServiceTestUtil.randomString());

		layoutRevision.setCss(ServiceTestUtil.randomString());

		layoutRevision.setStatus(ServiceTestUtil.nextInt());

		layoutRevision.setStatusByUserId(ServiceTestUtil.nextLong());

		layoutRevision.setStatusByUserName(ServiceTestUtil.randomString());

		layoutRevision.setStatusDate(ServiceTestUtil.nextDate());

		layoutRevision.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(layoutRevision);

		return layoutRevision;
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutRevisionPersistenceTest.class);
	private LayoutRevisionPersistence _persistence = (LayoutRevisionPersistence)PortalBeanLocatorUtil.locate(LayoutRevisionPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}