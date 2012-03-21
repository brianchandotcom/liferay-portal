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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.PersistenceEnvConfigTestListener;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.documentlibrary.NoSuchFileRankException;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.impl.DLFileRankModelImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceEnvConfigTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLFileRankPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (DLFileRankPersistence)PortalBeanLocatorUtil.locate(DLFileRankPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFileRank dlFileRank = _persistence.create(pk);

		Assert.assertNotNull(dlFileRank);

		Assert.assertEquals(dlFileRank.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DLFileRank newDLFileRank = addDLFileRank();

		_persistence.remove(newDLFileRank);

		DLFileRank existingDLFileRank = _persistence.fetchByPrimaryKey(newDLFileRank.getPrimaryKey());

		Assert.assertNull(existingDLFileRank);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDLFileRank();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFileRank newDLFileRank = _persistence.create(pk);

		newDLFileRank.setGroupId(ServiceTestUtil.nextLong());

		newDLFileRank.setCompanyId(ServiceTestUtil.nextLong());

		newDLFileRank.setUserId(ServiceTestUtil.nextLong());

		newDLFileRank.setCreateDate(ServiceTestUtil.nextDate());

		newDLFileRank.setFileEntryId(ServiceTestUtil.nextLong());

		_persistence.update(newDLFileRank, false);

		DLFileRank existingDLFileRank = _persistence.findByPrimaryKey(newDLFileRank.getPrimaryKey());

		Assert.assertEquals(existingDLFileRank.getFileRankId(),
			newDLFileRank.getFileRankId());
		Assert.assertEquals(existingDLFileRank.getGroupId(),
			newDLFileRank.getGroupId());
		Assert.assertEquals(existingDLFileRank.getCompanyId(),
			newDLFileRank.getCompanyId());
		Assert.assertEquals(existingDLFileRank.getUserId(),
			newDLFileRank.getUserId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFileRank.getCreateDate()),
			Time.getShortTimestamp(newDLFileRank.getCreateDate()));
		Assert.assertEquals(existingDLFileRank.getFileEntryId(),
			newDLFileRank.getFileEntryId());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFileRank newDLFileRank = addDLFileRank();

		DLFileRank existingDLFileRank = _persistence.findByPrimaryKey(newDLFileRank.getPrimaryKey());

		Assert.assertEquals(existingDLFileRank, newDLFileRank);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchFileRankException");
		}
		catch (NoSuchFileRankException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFileRank newDLFileRank = addDLFileRank();

		DLFileRank existingDLFileRank = _persistence.fetchByPrimaryKey(newDLFileRank.getPrimaryKey());

		Assert.assertEquals(existingDLFileRank, newDLFileRank);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFileRank missingDLFileRank = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDLFileRank);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFileRank newDLFileRank = addDLFileRank();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileRank.class,
				DLFileRank.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileRankId",
				newDLFileRank.getFileRankId()));

		List<DLFileRank> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DLFileRank existingDLFileRank = result.get(0);

		Assert.assertEquals(existingDLFileRank, newDLFileRank);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileRank.class,
				DLFileRank.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileRankId",
				ServiceTestUtil.nextLong()));

		List<DLFileRank> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLFileRank newDLFileRank = addDLFileRank();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileRank.class,
				DLFileRank.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("fileRankId"));

		Object newFileRankId = newDLFileRank.getFileRankId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileRankId",
				new Object[] { newFileRankId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFileRankId = result.get(0);

		Assert.assertEquals(existingFileRankId, newFileRankId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileRank.class,
				DLFileRank.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("fileRankId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileRankId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLFileRank newDLFileRank = addDLFileRank();

		_persistence.clearCache();

		DLFileRankModelImpl existingDLFileRankModelImpl = (DLFileRankModelImpl)_persistence.findByPrimaryKey(newDLFileRank.getPrimaryKey());

		Assert.assertEquals(existingDLFileRankModelImpl.getCompanyId(),
			existingDLFileRankModelImpl.getOriginalCompanyId());
		Assert.assertEquals(existingDLFileRankModelImpl.getUserId(),
			existingDLFileRankModelImpl.getOriginalUserId());
		Assert.assertEquals(existingDLFileRankModelImpl.getFileEntryId(),
			existingDLFileRankModelImpl.getOriginalFileEntryId());
	}

	protected DLFileRank addDLFileRank() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFileRank dlFileRank = _persistence.create(pk);

		dlFileRank.setGroupId(ServiceTestUtil.nextLong());

		dlFileRank.setCompanyId(ServiceTestUtil.nextLong());

		dlFileRank.setUserId(ServiceTestUtil.nextLong());

		dlFileRank.setCreateDate(ServiceTestUtil.nextDate());

		dlFileRank.setFileEntryId(ServiceTestUtil.nextLong());

		_persistence.update(dlFileRank, false);

		return dlFileRank;
	}

	private DLFileRankPersistence _persistence;
}