/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.impl.LayoutModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (LayoutPersistence)PortalBeanLocatorUtil.locate(LayoutPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Layout layout = _persistence.create(pk);

		assertNotNull(layout);

		assertEquals(layout.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Layout newLayout = addLayout();

		_persistence.remove(newLayout);

		Layout existingLayout = _persistence.fetchByPrimaryKey(newLayout.getPrimaryKey());

		assertNull(existingLayout);
	}

	public void testUpdateNew() throws Exception {
		addLayout();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Layout newLayout = _persistence.create(pk);

		newLayout.setUuid(randomString());

		newLayout.setGroupId(nextLong());

		newLayout.setCompanyId(nextLong());

		newLayout.setCreateDate(nextDate());

		newLayout.setModifiedDate(nextDate());

		newLayout.setPrivateLayout(randomBoolean());

		newLayout.setLayoutId(nextLong());

		newLayout.setParentLayoutId(nextLong());

		newLayout.setName(randomString());

		newLayout.setTitle(randomString());

		newLayout.setDescription(randomString());

		newLayout.setKeywords(randomString());

		newLayout.setRobots(randomString());

		newLayout.setType(randomString());

		newLayout.setTypeSettings(randomString());

		newLayout.setHidden(randomBoolean());

		newLayout.setFriendlyURL(randomString());

		newLayout.setIconImage(randomBoolean());

		newLayout.setIconImageId(nextLong());

		newLayout.setThemeId(randomString());

		newLayout.setColorSchemeId(randomString());

		newLayout.setWapThemeId(randomString());

		newLayout.setWapColorSchemeId(randomString());

		newLayout.setCss(randomString());

		newLayout.setPriority(nextInt());

		newLayout.setLayoutPrototypeUuid(randomString());

		newLayout.setLayoutPrototypeLinkEnabled(randomBoolean());

		newLayout.setSourcePrototypeLayoutUuid(randomString());

		_persistence.update(newLayout, false);

		Layout existingLayout = _persistence.findByPrimaryKey(newLayout.getPrimaryKey());

		assertEquals(existingLayout.getUuid(), newLayout.getUuid());
		assertEquals(existingLayout.getPlid(), newLayout.getPlid());
		assertEquals(existingLayout.getGroupId(), newLayout.getGroupId());
		assertEquals(existingLayout.getCompanyId(), newLayout.getCompanyId());
		assertEquals(Time.getShortTimestamp(existingLayout.getCreateDate()),
			Time.getShortTimestamp(newLayout.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingLayout.getModifiedDate()),
			Time.getShortTimestamp(newLayout.getModifiedDate()));
		assertEquals(existingLayout.getPrivateLayout(),
			newLayout.getPrivateLayout());
		assertEquals(existingLayout.getLayoutId(), newLayout.getLayoutId());
		assertEquals(existingLayout.getParentLayoutId(),
			newLayout.getParentLayoutId());
		assertEquals(existingLayout.getName(), newLayout.getName());
		assertEquals(existingLayout.getTitle(), newLayout.getTitle());
		assertEquals(existingLayout.getDescription(), newLayout.getDescription());
		assertEquals(existingLayout.getKeywords(), newLayout.getKeywords());
		assertEquals(existingLayout.getRobots(), newLayout.getRobots());
		assertEquals(existingLayout.getType(), newLayout.getType());
		assertEquals(existingLayout.getTypeSettings(),
			newLayout.getTypeSettings());
		assertEquals(existingLayout.getHidden(), newLayout.getHidden());
		assertEquals(existingLayout.getFriendlyURL(), newLayout.getFriendlyURL());
		assertEquals(existingLayout.getIconImage(), newLayout.getIconImage());
		assertEquals(existingLayout.getIconImageId(), newLayout.getIconImageId());
		assertEquals(existingLayout.getThemeId(), newLayout.getThemeId());
		assertEquals(existingLayout.getColorSchemeId(),
			newLayout.getColorSchemeId());
		assertEquals(existingLayout.getWapThemeId(), newLayout.getWapThemeId());
		assertEquals(existingLayout.getWapColorSchemeId(),
			newLayout.getWapColorSchemeId());
		assertEquals(existingLayout.getCss(), newLayout.getCss());
		assertEquals(existingLayout.getPriority(), newLayout.getPriority());
		assertEquals(existingLayout.getLayoutPrototypeUuid(),
			newLayout.getLayoutPrototypeUuid());
		assertEquals(existingLayout.getLayoutPrototypeLinkEnabled(),
			newLayout.getLayoutPrototypeLinkEnabled());
		assertEquals(existingLayout.getSourcePrototypeLayoutUuid(),
			newLayout.getSourcePrototypeLayoutUuid());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Layout newLayout = addLayout();

		Layout existingLayout = _persistence.findByPrimaryKey(newLayout.getPrimaryKey());

		assertEquals(existingLayout, newLayout);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchLayoutException");
		}
		catch (NoSuchLayoutException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Layout newLayout = addLayout();

		Layout existingLayout = _persistence.fetchByPrimaryKey(newLayout.getPrimaryKey());

		assertEquals(existingLayout, newLayout);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Layout missingLayout = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingLayout);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Layout newLayout = addLayout();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Layout.class,
				Layout.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("plid", newLayout.getPlid()));

		List<Layout> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Layout existingLayout = result.get(0);

		assertEquals(existingLayout, newLayout);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Layout.class,
				Layout.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("plid", nextLong()));

		List<Layout> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingPlid = result.get(0);

		assertEquals(existingPlid, newPlid);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Layout.class,
				Layout.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("plid"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("plid",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Layout newLayout = addLayout();

		_persistence.clearCache();

		LayoutModelImpl existingLayoutModelImpl = (LayoutModelImpl)_persistence.findByPrimaryKey(newLayout.getPrimaryKey());

		assertTrue(Validator.equals(existingLayoutModelImpl.getUuid(),
				existingLayoutModelImpl.getOriginalUuid()));
		assertEquals(existingLayoutModelImpl.getGroupId(),
			existingLayoutModelImpl.getOriginalGroupId());

		assertEquals(existingLayoutModelImpl.getIconImageId(),
			existingLayoutModelImpl.getOriginalIconImageId());

		assertEquals(existingLayoutModelImpl.getGroupId(),
			existingLayoutModelImpl.getOriginalGroupId());
		assertEquals(existingLayoutModelImpl.getPrivateLayout(),
			existingLayoutModelImpl.getOriginalPrivateLayout());
		assertEquals(existingLayoutModelImpl.getLayoutId(),
			existingLayoutModelImpl.getOriginalLayoutId());

		assertEquals(existingLayoutModelImpl.getGroupId(),
			existingLayoutModelImpl.getOriginalGroupId());
		assertEquals(existingLayoutModelImpl.getPrivateLayout(),
			existingLayoutModelImpl.getOriginalPrivateLayout());
		assertTrue(Validator.equals(existingLayoutModelImpl.getFriendlyURL(),
				existingLayoutModelImpl.getOriginalFriendlyURL()));

		assertEquals(existingLayoutModelImpl.getGroupId(),
			existingLayoutModelImpl.getOriginalGroupId());
		assertEquals(existingLayoutModelImpl.getPrivateLayout(),
			existingLayoutModelImpl.getOriginalPrivateLayout());
		assertTrue(Validator.equals(
				existingLayoutModelImpl.getSourcePrototypeLayoutUuid(),
				existingLayoutModelImpl.getOriginalSourcePrototypeLayoutUuid()));
	}

	protected Layout addLayout() throws Exception {
		long pk = nextLong();

		Layout layout = _persistence.create(pk);

		layout.setUuid(randomString());

		layout.setGroupId(nextLong());

		layout.setCompanyId(nextLong());

		layout.setCreateDate(nextDate());

		layout.setModifiedDate(nextDate());

		layout.setPrivateLayout(randomBoolean());

		layout.setLayoutId(nextLong());

		layout.setParentLayoutId(nextLong());

		layout.setName(randomString());

		layout.setTitle(randomString());

		layout.setDescription(randomString());

		layout.setKeywords(randomString());

		layout.setRobots(randomString());

		layout.setType(randomString());

		layout.setTypeSettings(randomString());

		layout.setHidden(randomBoolean());

		layout.setFriendlyURL(randomString());

		layout.setIconImage(randomBoolean());

		layout.setIconImageId(nextLong());

		layout.setThemeId(randomString());

		layout.setColorSchemeId(randomString());

		layout.setWapThemeId(randomString());

		layout.setWapColorSchemeId(randomString());

		layout.setCss(randomString());

		layout.setPriority(nextInt());

		layout.setLayoutPrototypeUuid(randomString());

		layout.setLayoutPrototypeLinkEnabled(randomBoolean());

		layout.setSourcePrototypeLayoutUuid(randomString());

		_persistence.update(layout, false);

		return layout;
	}

	private LayoutPersistence _persistence;
}