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

import com.liferay.portal.NoSuchWorkflowDefinitionLinkException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.model.impl.WorkflowDefinitionLinkModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class WorkflowDefinitionLinkPersistenceTest
	extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (WorkflowDefinitionLinkPersistence)PortalBeanLocatorUtil.locate(WorkflowDefinitionLinkPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		WorkflowDefinitionLink workflowDefinitionLink = _persistence.create(pk);

		assertNotNull(workflowDefinitionLink);

		assertEquals(workflowDefinitionLink.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		_persistence.remove(newWorkflowDefinitionLink);

		WorkflowDefinitionLink existingWorkflowDefinitionLink = _persistence.fetchByPrimaryKey(newWorkflowDefinitionLink.getPrimaryKey());

		assertNull(existingWorkflowDefinitionLink);
	}

	public void testUpdateNew() throws Exception {
		addWorkflowDefinitionLink();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		WorkflowDefinitionLink newWorkflowDefinitionLink = _persistence.create(pk);

		newWorkflowDefinitionLink.setGroupId(nextLong());

		newWorkflowDefinitionLink.setCompanyId(nextLong());

		newWorkflowDefinitionLink.setUserId(nextLong());

		newWorkflowDefinitionLink.setUserName(randomString());

		newWorkflowDefinitionLink.setCreateDate(nextDate());

		newWorkflowDefinitionLink.setModifiedDate(nextDate());

		newWorkflowDefinitionLink.setClassNameId(nextLong());

		newWorkflowDefinitionLink.setClassPK(nextLong());

		newWorkflowDefinitionLink.setTypePK(nextLong());

		newWorkflowDefinitionLink.setWorkflowDefinitionName(randomString());

		newWorkflowDefinitionLink.setWorkflowDefinitionVersion(nextInt());

		_persistence.update(newWorkflowDefinitionLink, false);

		WorkflowDefinitionLink existingWorkflowDefinitionLink = _persistence.findByPrimaryKey(newWorkflowDefinitionLink.getPrimaryKey());

		assertEquals(existingWorkflowDefinitionLink.getWorkflowDefinitionLinkId(),
			newWorkflowDefinitionLink.getWorkflowDefinitionLinkId());
		assertEquals(existingWorkflowDefinitionLink.getGroupId(),
			newWorkflowDefinitionLink.getGroupId());
		assertEquals(existingWorkflowDefinitionLink.getCompanyId(),
			newWorkflowDefinitionLink.getCompanyId());
		assertEquals(existingWorkflowDefinitionLink.getUserId(),
			newWorkflowDefinitionLink.getUserId());
		assertEquals(existingWorkflowDefinitionLink.getUserName(),
			newWorkflowDefinitionLink.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingWorkflowDefinitionLink.getCreateDate()),
			Time.getShortTimestamp(newWorkflowDefinitionLink.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingWorkflowDefinitionLink.getModifiedDate()),
			Time.getShortTimestamp(newWorkflowDefinitionLink.getModifiedDate()));
		assertEquals(existingWorkflowDefinitionLink.getClassNameId(),
			newWorkflowDefinitionLink.getClassNameId());
		assertEquals(existingWorkflowDefinitionLink.getClassPK(),
			newWorkflowDefinitionLink.getClassPK());
		assertEquals(existingWorkflowDefinitionLink.getTypePK(),
			newWorkflowDefinitionLink.getTypePK());
		assertEquals(existingWorkflowDefinitionLink.getWorkflowDefinitionName(),
			newWorkflowDefinitionLink.getWorkflowDefinitionName());
		assertEquals(existingWorkflowDefinitionLink.getWorkflowDefinitionVersion(),
			newWorkflowDefinitionLink.getWorkflowDefinitionVersion());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		WorkflowDefinitionLink existingWorkflowDefinitionLink = _persistence.findByPrimaryKey(newWorkflowDefinitionLink.getPrimaryKey());

		assertEquals(existingWorkflowDefinitionLink, newWorkflowDefinitionLink);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchWorkflowDefinitionLinkException");
		}
		catch (NoSuchWorkflowDefinitionLinkException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		WorkflowDefinitionLink existingWorkflowDefinitionLink = _persistence.fetchByPrimaryKey(newWorkflowDefinitionLink.getPrimaryKey());

		assertEquals(existingWorkflowDefinitionLink, newWorkflowDefinitionLink);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		WorkflowDefinitionLink missingWorkflowDefinitionLink = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingWorkflowDefinitionLink);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowDefinitionLink.class,
				WorkflowDefinitionLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"workflowDefinitionLinkId",
				newWorkflowDefinitionLink.getWorkflowDefinitionLinkId()));

		List<WorkflowDefinitionLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		WorkflowDefinitionLink existingWorkflowDefinitionLink = result.get(0);

		assertEquals(existingWorkflowDefinitionLink, newWorkflowDefinitionLink);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowDefinitionLink.class,
				WorkflowDefinitionLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"workflowDefinitionLinkId", nextLong()));

		List<WorkflowDefinitionLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowDefinitionLink.class,
				WorkflowDefinitionLink.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"workflowDefinitionLinkId"));

		Object newWorkflowDefinitionLinkId = newWorkflowDefinitionLink.getWorkflowDefinitionLinkId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"workflowDefinitionLinkId",
				new Object[] { newWorkflowDefinitionLinkId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingWorkflowDefinitionLinkId = result.get(0);

		assertEquals(existingWorkflowDefinitionLinkId,
			newWorkflowDefinitionLinkId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowDefinitionLink.class,
				WorkflowDefinitionLink.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"workflowDefinitionLinkId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"workflowDefinitionLinkId", new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		WorkflowDefinitionLink newWorkflowDefinitionLink = addWorkflowDefinitionLink();

		_persistence.clearCache();

		WorkflowDefinitionLinkModelImpl existingWorkflowDefinitionLinkModelImpl = (WorkflowDefinitionLinkModelImpl)_persistence.findByPrimaryKey(newWorkflowDefinitionLink.getPrimaryKey());

		assertEquals(existingWorkflowDefinitionLinkModelImpl.getGroupId(),
			existingWorkflowDefinitionLinkModelImpl.getOriginalGroupId());
		assertEquals(existingWorkflowDefinitionLinkModelImpl.getCompanyId(),
			existingWorkflowDefinitionLinkModelImpl.getOriginalCompanyId());
		assertEquals(existingWorkflowDefinitionLinkModelImpl.getClassNameId(),
			existingWorkflowDefinitionLinkModelImpl.getOriginalClassNameId());
		assertEquals(existingWorkflowDefinitionLinkModelImpl.getClassPK(),
			existingWorkflowDefinitionLinkModelImpl.getOriginalClassPK());
		assertEquals(existingWorkflowDefinitionLinkModelImpl.getTypePK(),
			existingWorkflowDefinitionLinkModelImpl.getOriginalTypePK());
	}

	protected WorkflowDefinitionLink addWorkflowDefinitionLink()
		throws Exception {
		long pk = nextLong();

		WorkflowDefinitionLink workflowDefinitionLink = _persistence.create(pk);

		workflowDefinitionLink.setGroupId(nextLong());

		workflowDefinitionLink.setCompanyId(nextLong());

		workflowDefinitionLink.setUserId(nextLong());

		workflowDefinitionLink.setUserName(randomString());

		workflowDefinitionLink.setCreateDate(nextDate());

		workflowDefinitionLink.setModifiedDate(nextDate());

		workflowDefinitionLink.setClassNameId(nextLong());

		workflowDefinitionLink.setClassPK(nextLong());

		workflowDefinitionLink.setTypePK(nextLong());

		workflowDefinitionLink.setWorkflowDefinitionName(randomString());

		workflowDefinitionLink.setWorkflowDefinitionVersion(nextInt());

		_persistence.update(workflowDefinitionLink, false);

		return workflowDefinitionLink;
	}

	private WorkflowDefinitionLinkPersistence _persistence;
}