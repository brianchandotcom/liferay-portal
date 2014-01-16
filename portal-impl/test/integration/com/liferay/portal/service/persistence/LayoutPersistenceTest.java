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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.impl.LayoutModelImpl;
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
public class LayoutPersistenceTest {
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
		long pk = ServiceTestUtil.nextLong();

		Layout newLayout = _persistence.create(pk);

		newLayout.setUuid(ServiceTestUtil.randomString());

		newLayout.setGroupId(ServiceTestUtil.nextLong());

		newLayout.setCompanyId(ServiceTestUtil.nextLong());

		newLayout.setUserId(ServiceTestUtil.nextLong());

		newLayout.setUserName(ServiceTestUtil.randomString());

		newLayout.setCreateDate(ServiceTestUtil.nextDate());

		newLayout.setModifiedDate(ServiceTestUtil.nextDate());

		newLayout.setPrivateLayout(ServiceTestUtil.randomBoolean());

		newLayout.setLayoutId(ServiceTestUtil.nextLong());

		newLayout.setParentLayoutId(ServiceTestUtil.nextLong());

		newLayout.setName(ServiceTestUtil.randomString());

		newLayout.setTitle(ServiceTestUtil.randomString());

		newLayout.setDescription(ServiceTestUtil.randomString());

		newLayout.setKeywords(ServiceTestUtil.randomString());

		newLayout.setRobots(ServiceTestUtil.randomString());

		newLayout.setType(ServiceTestUtil.randomString());

		newLayout.setTypeSettings(ServiceTestUtil.randomString());

		newLayout.setHidden(ServiceTestUtil.randomBoolean());

		newLayout.setFriendlyURL(ServiceTestUtil.randomString());

		newLayout.setIconImageId(ServiceTestUtil.nextLong());

		newLayout.setThemeId(ServiceTestUtil.randomString());

		newLayout.setColorSchemeId(ServiceTestUtil.randomString());

		newLayout.setWapThemeId(ServiceTestUtil.randomString());

		newLayout.setWapColorSchemeId(ServiceTestUtil.randomString());

		newLayout.setCss(ServiceTestUtil.randomString());

		newLayout.setPriority(ServiceTestUtil.nextInt());

		newLayout.setLayoutPrototypeUuid(ServiceTestUtil.randomString());

		newLayout.setLayoutPrototypeLinkEnabled(ServiceTestUtil.randomBoolean());

		newLayout.setSourcePrototypeLayoutUuid(ServiceTestUtil.randomString());

		newLayout.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newLayout);

		Layout existingLayout = _persistence.findByPrimaryKey(newLayout.getPrimaryKey());

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
		Assert.assertEquals(existingLayout.getMvccVersion(),
			newLayout.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Layout newLayout = addLayout();

		Layout existingLayout = _persistence.findByPrimaryKey(newLayout.getPrimaryKey());

		Assert.assertEquals(existingLayout, newLayout);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

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
		return OrderByComparatorFactoryUtil.create("Layout", "uuid", true,
			"plid", true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"privateLayout", true, "layoutId", true, "parentLayoutId", true,
			"name", true, "title", true, "description", true, "keywords", true,
			"robots", true, "type", true, "typeSettings", true, "hidden", true,
			"friendlyURL", true, "iconImageId", true, "themeId", true,
			"colorSchemeId", true, "wapThemeId", true, "wapColorSchemeId",
			true, "css", true, "priority", true, "layoutPrototypeUuid", true,
			"layoutPrototypeLinkEnabled", true, "sourcePrototypeLayoutUuid",
			true, "mvccVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Layout newLayout = addLayout();

		Layout existingLayout = _persistence.fetchByPrimaryKey(newLayout.getPrimaryKey());

		Assert.assertEquals(existingLayout, newLayout);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Layout missingLayout = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayout);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new LayoutActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					Layout layout = (Layout)object;

					Assert.assertNotNull(layout);

					count.increment();
				}
			};

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
				ServiceTestUtil.nextLong()));

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
				new Object[] { ServiceTestUtil.nextLong() }));

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
		long pk = ServiceTestUtil.nextLong();

		Layout layout = _persistence.create(pk);

		layout.setUuid(ServiceTestUtil.randomString());

		layout.setGroupId(ServiceTestUtil.nextLong());

		layout.setCompanyId(ServiceTestUtil.nextLong());

		layout.setUserId(ServiceTestUtil.nextLong());

		layout.setUserName(ServiceTestUtil.randomString());

		layout.setCreateDate(ServiceTestUtil.nextDate());

		layout.setModifiedDate(ServiceTestUtil.nextDate());

		layout.setPrivateLayout(ServiceTestUtil.randomBoolean());

		layout.setLayoutId(ServiceTestUtil.nextLong());

		layout.setParentLayoutId(ServiceTestUtil.nextLong());

		layout.setName(ServiceTestUtil.randomString());

		layout.setTitle(ServiceTestUtil.randomString());

		layout.setDescription(ServiceTestUtil.randomString());

		layout.setKeywords(ServiceTestUtil.randomString());

		layout.setRobots(ServiceTestUtil.randomString());

		layout.setType(ServiceTestUtil.randomString());

		layout.setTypeSettings(ServiceTestUtil.randomString());

		layout.setHidden(ServiceTestUtil.randomBoolean());

		layout.setFriendlyURL(ServiceTestUtil.randomString());

		layout.setIconImageId(ServiceTestUtil.nextLong());

		layout.setThemeId(ServiceTestUtil.randomString());

		layout.setColorSchemeId(ServiceTestUtil.randomString());

		layout.setWapThemeId(ServiceTestUtil.randomString());

		layout.setWapColorSchemeId(ServiceTestUtil.randomString());

		layout.setCss(ServiceTestUtil.randomString());

		layout.setPriority(ServiceTestUtil.nextInt());

		layout.setLayoutPrototypeUuid(ServiceTestUtil.randomString());

		layout.setLayoutPrototypeLinkEnabled(ServiceTestUtil.randomBoolean());

		layout.setSourcePrototypeLayoutUuid(ServiceTestUtil.randomString());

		layout.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(layout);

		return layout;
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutPersistenceTest.class);
	private LayoutPersistence _persistence = (LayoutPersistence)PortalBeanLocatorUtil.locate(LayoutPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}