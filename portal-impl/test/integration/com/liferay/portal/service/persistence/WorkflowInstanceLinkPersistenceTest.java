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

import com.liferay.portal.NoSuchWorkflowInstanceLinkException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.WorkflowInstanceLink;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.PersistenceEnvConfigTestListener;

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
public class WorkflowInstanceLinkPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (WorkflowInstanceLinkPersistence)PortalBeanLocatorUtil.locate(WorkflowInstanceLinkPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		WorkflowInstanceLink workflowInstanceLink = _persistence.create(pk);

		Assert.assertNotNull(workflowInstanceLink);

		Assert.assertEquals(workflowInstanceLink.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WorkflowInstanceLink newWorkflowInstanceLink = addWorkflowInstanceLink();

		_persistence.remove(newWorkflowInstanceLink);

		WorkflowInstanceLink existingWorkflowInstanceLink = _persistence.fetchByPrimaryKey(newWorkflowInstanceLink.getPrimaryKey());

		Assert.assertNull(existingWorkflowInstanceLink);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWorkflowInstanceLink();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		WorkflowInstanceLink newWorkflowInstanceLink = _persistence.create(pk);

		newWorkflowInstanceLink.setGroupId(ServiceTestUtil.nextLong());

		newWorkflowInstanceLink.setCompanyId(ServiceTestUtil.nextLong());

		newWorkflowInstanceLink.setUserId(ServiceTestUtil.nextLong());

		newWorkflowInstanceLink.setUserName(ServiceTestUtil.randomString());

		newWorkflowInstanceLink.setCreateDate(ServiceTestUtil.nextDate());

		newWorkflowInstanceLink.setModifiedDate(ServiceTestUtil.nextDate());

		newWorkflowInstanceLink.setClassNameId(ServiceTestUtil.nextLong());

		newWorkflowInstanceLink.setClassPK(ServiceTestUtil.nextLong());

		newWorkflowInstanceLink.setWorkflowInstanceId(ServiceTestUtil.nextLong());

		_persistence.update(newWorkflowInstanceLink, false);

		WorkflowInstanceLink existingWorkflowInstanceLink = _persistence.findByPrimaryKey(newWorkflowInstanceLink.getPrimaryKey());

		Assert.assertEquals(existingWorkflowInstanceLink.getWorkflowInstanceLinkId(),
			newWorkflowInstanceLink.getWorkflowInstanceLinkId());
		Assert.assertEquals(existingWorkflowInstanceLink.getGroupId(),
			newWorkflowInstanceLink.getGroupId());
		Assert.assertEquals(existingWorkflowInstanceLink.getCompanyId(),
			newWorkflowInstanceLink.getCompanyId());
		Assert.assertEquals(existingWorkflowInstanceLink.getUserId(),
			newWorkflowInstanceLink.getUserId());
		Assert.assertEquals(existingWorkflowInstanceLink.getUserName(),
			newWorkflowInstanceLink.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWorkflowInstanceLink.getCreateDate()),
			Time.getShortTimestamp(newWorkflowInstanceLink.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingWorkflowInstanceLink.getModifiedDate()),
			Time.getShortTimestamp(newWorkflowInstanceLink.getModifiedDate()));
		Assert.assertEquals(existingWorkflowInstanceLink.getClassNameId(),
			newWorkflowInstanceLink.getClassNameId());
		Assert.assertEquals(existingWorkflowInstanceLink.getClassPK(),
			newWorkflowInstanceLink.getClassPK());
		Assert.assertEquals(existingWorkflowInstanceLink.getWorkflowInstanceId(),
			newWorkflowInstanceLink.getWorkflowInstanceId());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		WorkflowInstanceLink newWorkflowInstanceLink = addWorkflowInstanceLink();

		WorkflowInstanceLink existingWorkflowInstanceLink = _persistence.findByPrimaryKey(newWorkflowInstanceLink.getPrimaryKey());

		Assert.assertEquals(existingWorkflowInstanceLink,
			newWorkflowInstanceLink);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchWorkflowInstanceLinkException");
		}
		catch (NoSuchWorkflowInstanceLinkException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WorkflowInstanceLink newWorkflowInstanceLink = addWorkflowInstanceLink();

		WorkflowInstanceLink existingWorkflowInstanceLink = _persistence.fetchByPrimaryKey(newWorkflowInstanceLink.getPrimaryKey());

		Assert.assertEquals(existingWorkflowInstanceLink,
			newWorkflowInstanceLink);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		WorkflowInstanceLink missingWorkflowInstanceLink = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWorkflowInstanceLink);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WorkflowInstanceLink newWorkflowInstanceLink = addWorkflowInstanceLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowInstanceLink.class,
				WorkflowInstanceLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("workflowInstanceLinkId",
				newWorkflowInstanceLink.getWorkflowInstanceLinkId()));

		List<WorkflowInstanceLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		WorkflowInstanceLink existingWorkflowInstanceLink = result.get(0);

		Assert.assertEquals(existingWorkflowInstanceLink,
			newWorkflowInstanceLink);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowInstanceLink.class,
				WorkflowInstanceLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("workflowInstanceLinkId",
				ServiceTestUtil.nextLong()));

		List<WorkflowInstanceLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		WorkflowInstanceLink newWorkflowInstanceLink = addWorkflowInstanceLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowInstanceLink.class,
				WorkflowInstanceLink.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"workflowInstanceLinkId"));

		Object newWorkflowInstanceLinkId = newWorkflowInstanceLink.getWorkflowInstanceLinkId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("workflowInstanceLinkId",
				new Object[] { newWorkflowInstanceLinkId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingWorkflowInstanceLinkId = result.get(0);

		Assert.assertEquals(existingWorkflowInstanceLinkId,
			newWorkflowInstanceLinkId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WorkflowInstanceLink.class,
				WorkflowInstanceLink.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"workflowInstanceLinkId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("workflowInstanceLinkId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected WorkflowInstanceLink addWorkflowInstanceLink()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		WorkflowInstanceLink workflowInstanceLink = _persistence.create(pk);

		workflowInstanceLink.setGroupId(ServiceTestUtil.nextLong());

		workflowInstanceLink.setCompanyId(ServiceTestUtil.nextLong());

		workflowInstanceLink.setUserId(ServiceTestUtil.nextLong());

		workflowInstanceLink.setUserName(ServiceTestUtil.randomString());

		workflowInstanceLink.setCreateDate(ServiceTestUtil.nextDate());

		workflowInstanceLink.setModifiedDate(ServiceTestUtil.nextDate());

		workflowInstanceLink.setClassNameId(ServiceTestUtil.nextLong());

		workflowInstanceLink.setClassPK(ServiceTestUtil.nextLong());

		workflowInstanceLink.setWorkflowInstanceId(ServiceTestUtil.nextLong());

		_persistence.update(workflowInstanceLink, false);

		return workflowInstanceLink;
	}

	private WorkflowInstanceLinkPersistence _persistence;
}