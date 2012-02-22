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

import com.liferay.portal.NoSuchLayoutRevisionException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.impl.LayoutRevisionModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutRevisionPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (LayoutRevisionPersistence)PortalBeanLocatorUtil.locate(LayoutRevisionPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		LayoutRevision layoutRevision = _persistence.create(pk);

		assertNotNull(layoutRevision);

		assertEquals(layoutRevision.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		_persistence.remove(newLayoutRevision);

		LayoutRevision existingLayoutRevision = _persistence.fetchByPrimaryKey(newLayoutRevision.getPrimaryKey());

		assertNull(existingLayoutRevision);
	}

	public void testUpdateNew() throws Exception {
		addLayoutRevision();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		LayoutRevision newLayoutRevision = _persistence.create(pk);

		newLayoutRevision.setGroupId(nextLong());

		newLayoutRevision.setCompanyId(nextLong());

		newLayoutRevision.setUserId(nextLong());

		newLayoutRevision.setUserName(randomString());

		newLayoutRevision.setCreateDate(nextDate());

		newLayoutRevision.setModifiedDate(nextDate());

		newLayoutRevision.setLayoutSetBranchId(nextLong());

		newLayoutRevision.setLayoutBranchId(nextLong());

		newLayoutRevision.setParentLayoutRevisionId(nextLong());

		newLayoutRevision.setHead(randomBoolean());

		newLayoutRevision.setMajor(randomBoolean());

		newLayoutRevision.setPlid(nextLong());

		newLayoutRevision.setPrivateLayout(randomBoolean());

		newLayoutRevision.setName(randomString());

		newLayoutRevision.setTitle(randomString());

		newLayoutRevision.setDescription(randomString());

		newLayoutRevision.setKeywords(randomString());

		newLayoutRevision.setRobots(randomString());

		newLayoutRevision.setTypeSettings(randomString());

		newLayoutRevision.setIconImage(randomBoolean());

		newLayoutRevision.setIconImageId(nextLong());

		newLayoutRevision.setThemeId(randomString());

		newLayoutRevision.setColorSchemeId(randomString());

		newLayoutRevision.setWapThemeId(randomString());

		newLayoutRevision.setWapColorSchemeId(randomString());

		newLayoutRevision.setCss(randomString());

		newLayoutRevision.setStatus(nextInt());

		newLayoutRevision.setStatusByUserId(nextLong());

		newLayoutRevision.setStatusByUserName(randomString());

		newLayoutRevision.setStatusDate(nextDate());

		_persistence.update(newLayoutRevision, false);

		LayoutRevision existingLayoutRevision = _persistence.findByPrimaryKey(newLayoutRevision.getPrimaryKey());

		assertEquals(existingLayoutRevision.getLayoutRevisionId(),
			newLayoutRevision.getLayoutRevisionId());
		assertEquals(existingLayoutRevision.getGroupId(),
			newLayoutRevision.getGroupId());
		assertEquals(existingLayoutRevision.getCompanyId(),
			newLayoutRevision.getCompanyId());
		assertEquals(existingLayoutRevision.getUserId(),
			newLayoutRevision.getUserId());
		assertEquals(existingLayoutRevision.getUserName(),
			newLayoutRevision.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingLayoutRevision.getCreateDate()),
			Time.getShortTimestamp(newLayoutRevision.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingLayoutRevision.getModifiedDate()),
			Time.getShortTimestamp(newLayoutRevision.getModifiedDate()));
		assertEquals(existingLayoutRevision.getLayoutSetBranchId(),
			newLayoutRevision.getLayoutSetBranchId());
		assertEquals(existingLayoutRevision.getLayoutBranchId(),
			newLayoutRevision.getLayoutBranchId());
		assertEquals(existingLayoutRevision.getParentLayoutRevisionId(),
			newLayoutRevision.getParentLayoutRevisionId());
		assertEquals(existingLayoutRevision.getHead(),
			newLayoutRevision.getHead());
		assertEquals(existingLayoutRevision.getMajor(),
			newLayoutRevision.getMajor());
		assertEquals(existingLayoutRevision.getPlid(),
			newLayoutRevision.getPlid());
		assertEquals(existingLayoutRevision.getPrivateLayout(),
			newLayoutRevision.getPrivateLayout());
		assertEquals(existingLayoutRevision.getName(),
			newLayoutRevision.getName());
		assertEquals(existingLayoutRevision.getTitle(),
			newLayoutRevision.getTitle());
		assertEquals(existingLayoutRevision.getDescription(),
			newLayoutRevision.getDescription());
		assertEquals(existingLayoutRevision.getKeywords(),
			newLayoutRevision.getKeywords());
		assertEquals(existingLayoutRevision.getRobots(),
			newLayoutRevision.getRobots());
		assertEquals(existingLayoutRevision.getTypeSettings(),
			newLayoutRevision.getTypeSettings());
		assertEquals(existingLayoutRevision.getIconImage(),
			newLayoutRevision.getIconImage());
		assertEquals(existingLayoutRevision.getIconImageId(),
			newLayoutRevision.getIconImageId());
		assertEquals(existingLayoutRevision.getThemeId(),
			newLayoutRevision.getThemeId());
		assertEquals(existingLayoutRevision.getColorSchemeId(),
			newLayoutRevision.getColorSchemeId());
		assertEquals(existingLayoutRevision.getWapThemeId(),
			newLayoutRevision.getWapThemeId());
		assertEquals(existingLayoutRevision.getWapColorSchemeId(),
			newLayoutRevision.getWapColorSchemeId());
		assertEquals(existingLayoutRevision.getCss(), newLayoutRevision.getCss());
		assertEquals(existingLayoutRevision.getStatus(),
			newLayoutRevision.getStatus());
		assertEquals(existingLayoutRevision.getStatusByUserId(),
			newLayoutRevision.getStatusByUserId());
		assertEquals(existingLayoutRevision.getStatusByUserName(),
			newLayoutRevision.getStatusByUserName());
		assertEquals(Time.getShortTimestamp(
				existingLayoutRevision.getStatusDate()),
			Time.getShortTimestamp(newLayoutRevision.getStatusDate()));
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		LayoutRevision existingLayoutRevision = _persistence.findByPrimaryKey(newLayoutRevision.getPrimaryKey());

		assertEquals(existingLayoutRevision, newLayoutRevision);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchLayoutRevisionException");
		}
		catch (NoSuchLayoutRevisionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		LayoutRevision existingLayoutRevision = _persistence.fetchByPrimaryKey(newLayoutRevision.getPrimaryKey());

		assertEquals(existingLayoutRevision, newLayoutRevision);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		LayoutRevision missingLayoutRevision = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingLayoutRevision);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutRevision.class,
				LayoutRevision.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutRevisionId",
				newLayoutRevision.getLayoutRevisionId()));

		List<LayoutRevision> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		LayoutRevision existingLayoutRevision = result.get(0);

		assertEquals(existingLayoutRevision, newLayoutRevision);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutRevision.class,
				LayoutRevision.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutRevisionId",
				nextLong()));

		List<LayoutRevision> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingLayoutRevisionId = result.get(0);

		assertEquals(existingLayoutRevisionId, newLayoutRevisionId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutRevision.class,
				LayoutRevision.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutRevisionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutRevisionId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		LayoutRevision newLayoutRevision = addLayoutRevision();

		_persistence.clearCache();

		LayoutRevisionModelImpl existingLayoutRevisionModelImpl = (LayoutRevisionModelImpl)_persistence.findByPrimaryKey(newLayoutRevision.getPrimaryKey());

		assertEquals(existingLayoutRevisionModelImpl.getLayoutSetBranchId(),
			existingLayoutRevisionModelImpl.getOriginalLayoutSetBranchId());
		assertEquals(existingLayoutRevisionModelImpl.getHead(),
			existingLayoutRevisionModelImpl.getOriginalHead());
		assertEquals(existingLayoutRevisionModelImpl.getPlid(),
			existingLayoutRevisionModelImpl.getOriginalPlid());
	}

	protected LayoutRevision addLayoutRevision() throws Exception {
		long pk = nextLong();

		LayoutRevision layoutRevision = _persistence.create(pk);

		layoutRevision.setGroupId(nextLong());

		layoutRevision.setCompanyId(nextLong());

		layoutRevision.setUserId(nextLong());

		layoutRevision.setUserName(randomString());

		layoutRevision.setCreateDate(nextDate());

		layoutRevision.setModifiedDate(nextDate());

		layoutRevision.setLayoutSetBranchId(nextLong());

		layoutRevision.setLayoutBranchId(nextLong());

		layoutRevision.setParentLayoutRevisionId(nextLong());

		layoutRevision.setHead(randomBoolean());

		layoutRevision.setMajor(randomBoolean());

		layoutRevision.setPlid(nextLong());

		layoutRevision.setPrivateLayout(randomBoolean());

		layoutRevision.setName(randomString());

		layoutRevision.setTitle(randomString());

		layoutRevision.setDescription(randomString());

		layoutRevision.setKeywords(randomString());

		layoutRevision.setRobots(randomString());

		layoutRevision.setTypeSettings(randomString());

		layoutRevision.setIconImage(randomBoolean());

		layoutRevision.setIconImageId(nextLong());

		layoutRevision.setThemeId(randomString());

		layoutRevision.setColorSchemeId(randomString());

		layoutRevision.setWapThemeId(randomString());

		layoutRevision.setWapColorSchemeId(randomString());

		layoutRevision.setCss(randomString());

		layoutRevision.setStatus(nextInt());

		layoutRevision.setStatusByUserId(nextLong());

		layoutRevision.setStatusByUserName(randomString());

		layoutRevision.setStatusDate(nextDate());

		_persistence.update(layoutRevision, false);

		return layoutRevision;
	}

	private LayoutRevisionPersistence _persistence;
}