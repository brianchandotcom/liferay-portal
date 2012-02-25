/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchPortletItemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.impl.PortletItemModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletItemPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (PortletItemPersistence)PortalBeanLocatorUtil.locate(PortletItemPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		PortletItem portletItem = _persistence.create(pk);

		assertNotNull(portletItem);

		assertEquals(portletItem.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		PortletItem newPortletItem = addPortletItem();

		_persistence.remove(newPortletItem);

		PortletItem existingPortletItem = _persistence.fetchByPrimaryKey(newPortletItem.getPrimaryKey());

		assertNull(existingPortletItem);
	}

	public void testUpdateNew() throws Exception {
		addPortletItem();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		PortletItem newPortletItem = _persistence.create(pk);

		newPortletItem.setGroupId(nextLong());

		newPortletItem.setCompanyId(nextLong());

		newPortletItem.setUserId(nextLong());

		newPortletItem.setUserName(randomString());

		newPortletItem.setCreateDate(nextDate());

		newPortletItem.setModifiedDate(nextDate());

		newPortletItem.setName(randomString());

		newPortletItem.setPortletId(randomString());

		newPortletItem.setClassNameId(nextLong());

		_persistence.update(newPortletItem, false);

		PortletItem existingPortletItem = _persistence.findByPrimaryKey(newPortletItem.getPrimaryKey());

		assertEquals(existingPortletItem.getPortletItemId(),
			newPortletItem.getPortletItemId());
		assertEquals(existingPortletItem.getGroupId(),
			newPortletItem.getGroupId());
		assertEquals(existingPortletItem.getCompanyId(),
			newPortletItem.getCompanyId());
		assertEquals(existingPortletItem.getUserId(), newPortletItem.getUserId());
		assertEquals(existingPortletItem.getUserName(),
			newPortletItem.getUserName());
		assertEquals(Time.getShortTimestamp(existingPortletItem.getCreateDate()),
			Time.getShortTimestamp(newPortletItem.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingPortletItem.getModifiedDate()),
			Time.getShortTimestamp(newPortletItem.getModifiedDate()));
		assertEquals(existingPortletItem.getName(), newPortletItem.getName());
		assertEquals(existingPortletItem.getPortletId(),
			newPortletItem.getPortletId());
		assertEquals(existingPortletItem.getClassNameId(),
			newPortletItem.getClassNameId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		PortletItem newPortletItem = addPortletItem();

		PortletItem existingPortletItem = _persistence.findByPrimaryKey(newPortletItem.getPrimaryKey());

		assertEquals(existingPortletItem, newPortletItem);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchPortletItemException");
		}
		catch (NoSuchPortletItemException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		PortletItem newPortletItem = addPortletItem();

		PortletItem existingPortletItem = _persistence.fetchByPrimaryKey(newPortletItem.getPrimaryKey());

		assertEquals(existingPortletItem, newPortletItem);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		PortletItem missingPortletItem = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingPortletItem);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PortletItem newPortletItem = addPortletItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				PortletItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portletItemId",
				newPortletItem.getPortletItemId()));

		List<PortletItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		PortletItem existingPortletItem = result.get(0);

		assertEquals(existingPortletItem, newPortletItem);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				PortletItem.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portletItemId", nextLong()));

		List<PortletItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PortletItem newPortletItem = addPortletItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				PortletItem.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portletItemId"));

		Object newPortletItemId = newPortletItem.getPortletItemId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("portletItemId",
				new Object[] { newPortletItemId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingPortletItemId = result.get(0);

		assertEquals(existingPortletItemId, newPortletItemId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				PortletItem.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portletItemId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("portletItemId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		PortletItem newPortletItem = addPortletItem();

		_persistence.clearCache();

		PortletItemModelImpl existingPortletItemModelImpl = (PortletItemModelImpl)_persistence.findByPrimaryKey(newPortletItem.getPrimaryKey());

		assertEquals(existingPortletItemModelImpl.getGroupId(),
			existingPortletItemModelImpl.getOriginalGroupId());
		assertTrue(Validator.equals(existingPortletItemModelImpl.getName(),
				existingPortletItemModelImpl.getOriginalName()));
		assertTrue(Validator.equals(
				existingPortletItemModelImpl.getPortletId(),
				existingPortletItemModelImpl.getOriginalPortletId()));
		assertEquals(existingPortletItemModelImpl.getClassNameId(),
			existingPortletItemModelImpl.getOriginalClassNameId());
	}

	protected PortletItem addPortletItem() throws Exception {
		long pk = nextLong();

		PortletItem portletItem = _persistence.create(pk);

		portletItem.setGroupId(nextLong());

		portletItem.setCompanyId(nextLong());

		portletItem.setUserId(nextLong());

		portletItem.setUserName(randomString());

		portletItem.setCreateDate(nextDate());

		portletItem.setModifiedDate(nextDate());

		portletItem.setName(randomString());

		portletItem.setPortletId(randomString());

		portletItem.setClassNameId(nextLong());

		_persistence.update(portletItem, false);

		return portletItem;
	}

	private PortletItemPersistence _persistence;
}