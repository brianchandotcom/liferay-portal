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

import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.impl.LayoutSetModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutSetPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (LayoutSetPersistence)PortalBeanLocatorUtil.locate(LayoutSetPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		LayoutSet layoutSet = _persistence.create(pk);

		assertNotNull(layoutSet);

		assertEquals(layoutSet.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		LayoutSet newLayoutSet = addLayoutSet();

		_persistence.remove(newLayoutSet);

		LayoutSet existingLayoutSet = _persistence.fetchByPrimaryKey(newLayoutSet.getPrimaryKey());

		assertNull(existingLayoutSet);
	}

	public void testUpdateNew() throws Exception {
		addLayoutSet();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		LayoutSet newLayoutSet = _persistence.create(pk);

		newLayoutSet.setGroupId(nextLong());

		newLayoutSet.setCompanyId(nextLong());

		newLayoutSet.setCreateDate(nextDate());

		newLayoutSet.setModifiedDate(nextDate());

		newLayoutSet.setPrivateLayout(randomBoolean());

		newLayoutSet.setLogo(randomBoolean());

		newLayoutSet.setLogoId(nextLong());

		newLayoutSet.setThemeId(randomString());

		newLayoutSet.setColorSchemeId(randomString());

		newLayoutSet.setWapThemeId(randomString());

		newLayoutSet.setWapColorSchemeId(randomString());

		newLayoutSet.setCss(randomString());

		newLayoutSet.setPageCount(nextInt());

		newLayoutSet.setSettings(randomString());

		newLayoutSet.setLayoutSetPrototypeUuid(randomString());

		newLayoutSet.setLayoutSetPrototypeLinkEnabled(randomBoolean());

		_persistence.update(newLayoutSet, false);

		LayoutSet existingLayoutSet = _persistence.findByPrimaryKey(newLayoutSet.getPrimaryKey());

		assertEquals(existingLayoutSet.getLayoutSetId(),
			newLayoutSet.getLayoutSetId());
		assertEquals(existingLayoutSet.getGroupId(), newLayoutSet.getGroupId());
		assertEquals(existingLayoutSet.getCompanyId(),
			newLayoutSet.getCompanyId());
		assertEquals(Time.getShortTimestamp(existingLayoutSet.getCreateDate()),
			Time.getShortTimestamp(newLayoutSet.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingLayoutSet.getModifiedDate()),
			Time.getShortTimestamp(newLayoutSet.getModifiedDate()));
		assertEquals(existingLayoutSet.getPrivateLayout(),
			newLayoutSet.getPrivateLayout());
		assertEquals(existingLayoutSet.getLogo(), newLayoutSet.getLogo());
		assertEquals(existingLayoutSet.getLogoId(), newLayoutSet.getLogoId());
		assertEquals(existingLayoutSet.getThemeId(), newLayoutSet.getThemeId());
		assertEquals(existingLayoutSet.getColorSchemeId(),
			newLayoutSet.getColorSchemeId());
		assertEquals(existingLayoutSet.getWapThemeId(),
			newLayoutSet.getWapThemeId());
		assertEquals(existingLayoutSet.getWapColorSchemeId(),
			newLayoutSet.getWapColorSchemeId());
		assertEquals(existingLayoutSet.getCss(), newLayoutSet.getCss());
		assertEquals(existingLayoutSet.getPageCount(),
			newLayoutSet.getPageCount());
		assertEquals(existingLayoutSet.getSettings(), newLayoutSet.getSettings());
		assertEquals(existingLayoutSet.getLayoutSetPrototypeUuid(),
			newLayoutSet.getLayoutSetPrototypeUuid());
		assertEquals(existingLayoutSet.getLayoutSetPrototypeLinkEnabled(),
			newLayoutSet.getLayoutSetPrototypeLinkEnabled());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutSet newLayoutSet = addLayoutSet();

		LayoutSet existingLayoutSet = _persistence.findByPrimaryKey(newLayoutSet.getPrimaryKey());

		assertEquals(existingLayoutSet, newLayoutSet);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchLayoutSetException");
		}
		catch (NoSuchLayoutSetException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutSet newLayoutSet = addLayoutSet();

		LayoutSet existingLayoutSet = _persistence.fetchByPrimaryKey(newLayoutSet.getPrimaryKey());

		assertEquals(existingLayoutSet, newLayoutSet);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		LayoutSet missingLayoutSet = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingLayoutSet);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutSet newLayoutSet = addLayoutSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSet.class,
				LayoutSet.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetId",
				newLayoutSet.getLayoutSetId()));

		List<LayoutSet> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		LayoutSet existingLayoutSet = result.get(0);

		assertEquals(existingLayoutSet, newLayoutSet);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSet.class,
				LayoutSet.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetId", nextLong()));

		List<LayoutSet> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		LayoutSet newLayoutSet = addLayoutSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSet.class,
				LayoutSet.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("layoutSetId"));

		Object newLayoutSetId = newLayoutSet.getLayoutSetId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutSetId",
				new Object[] { newLayoutSetId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingLayoutSetId = result.get(0);

		assertEquals(existingLayoutSetId, newLayoutSetId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSet.class,
				LayoutSet.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("layoutSetId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutSetId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		LayoutSet newLayoutSet = addLayoutSet();

		_persistence.clearCache();

		LayoutSetModelImpl existingLayoutSetModelImpl = (LayoutSetModelImpl)_persistence.findByPrimaryKey(newLayoutSet.getPrimaryKey());

		assertEquals(existingLayoutSetModelImpl.getGroupId(),
			existingLayoutSetModelImpl.getOriginalGroupId());
		assertEquals(existingLayoutSetModelImpl.getPrivateLayout(),
			existingLayoutSetModelImpl.getOriginalPrivateLayout());
	}

	protected LayoutSet addLayoutSet() throws Exception {
		long pk = nextLong();

		LayoutSet layoutSet = _persistence.create(pk);

		layoutSet.setGroupId(nextLong());

		layoutSet.setCompanyId(nextLong());

		layoutSet.setCreateDate(nextDate());

		layoutSet.setModifiedDate(nextDate());

		layoutSet.setPrivateLayout(randomBoolean());

		layoutSet.setLogo(randomBoolean());

		layoutSet.setLogoId(nextLong());

		layoutSet.setThemeId(randomString());

		layoutSet.setColorSchemeId(randomString());

		layoutSet.setWapThemeId(randomString());

		layoutSet.setWapColorSchemeId(randomString());

		layoutSet.setCss(randomString());

		layoutSet.setPageCount(nextInt());

		layoutSet.setSettings(randomString());

		layoutSet.setLayoutSetPrototypeUuid(randomString());

		layoutSet.setLayoutSetPrototypeLinkEnabled(randomBoolean());

		_persistence.update(layoutSet, false);

		return layoutSet;
	}

	private LayoutSetPersistence _persistence;
}