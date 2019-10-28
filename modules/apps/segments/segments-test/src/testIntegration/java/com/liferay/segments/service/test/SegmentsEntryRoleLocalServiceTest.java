/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.segments.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRole;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRoleLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class SegmentsEntryRoleLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			TestPropsValues.getGroupId());
	}

	@Test
	public void testAddSegmentsEntryRole() throws PortalException {
		SegmentsEntryRole segmentsEntryRole =
			_segmentsEntryRoleLocalService.addSegmentsEntryRole(
				_role.getRoleId(), _segmentsEntry.getSegmentsEntryId(),
				ServiceContextTestUtil.getServiceContext());

		Assert.assertNotNull(segmentsEntryRole);
		Assert.assertEquals(
			1,
			_segmentsEntryRoleLocalService.getSegmentsEntryRolesCount(
				_segmentsEntry.getSegmentsEntryId()));
		Assert.assertEquals(_role.getRoleId(), segmentsEntryRole.getRoleId());
		Assert.assertEquals(
			_segmentsEntry.getSegmentsEntryId(),
			segmentsEntryRole.getSegmentsEntryId());
	}

	@Test
	public void testDeleteSegmentsEntryRole() throws PortalException {
		_segmentsEntryRoleLocalService.addSegmentsEntryRole(
			_role.getRoleId(), _segmentsEntry.getSegmentsEntryId(),
			ServiceContextTestUtil.getServiceContext());

		_segmentsEntryRoleLocalService.deleteSegmentsEntryRole(
			_role.getRoleId(), _segmentsEntry.getSegmentsEntryId());

		Assert.assertEquals(
			0,
			_segmentsEntryRoleLocalService.getSegmentsEntryRolesCount(
				_segmentsEntry.getSegmentsEntryId()));
	}

	@Test
	public void testDeleteSegmentsEntryRolesByRoleId() throws PortalException {
		_segmentsEntryRoleLocalService.addSegmentsEntryRole(
			_role.getRoleId(), _segmentsEntry.getSegmentsEntryId(),
			ServiceContextTestUtil.getServiceContext());

		_segmentsEntryRoleLocalService.deleteSegmentsEntryRolesByRoleId(
			_role.getRoleId());

		Assert.assertEquals(
			0,
			_segmentsEntryRoleLocalService.getSegmentsEntryRolesCountByRoleId(
				_role.getRoleId()));
	}

	@Test
	public void testDeleteSegmentsEntryRolesBySegmentsEntryId()
		throws PortalException {

		_segmentsEntryRoleLocalService.addSegmentsEntryRole(
			_role.getRoleId(), _segmentsEntry.getSegmentsEntryId(),
			ServiceContextTestUtil.getServiceContext());

		_segmentsEntryRoleLocalService.deleteSegmentsEntryRoles(
			_segmentsEntry.getSegmentsEntryId());

		Assert.assertEquals(
			0,
			_segmentsEntryRoleLocalService.getSegmentsEntryRolesCount(
				_segmentsEntry.getSegmentsEntryId()));
	}

	@Test
	public void testGetSegmentsEntryRoles() throws PortalException {
		SegmentsEntryRole segmentsEntryRole =
			_segmentsEntryRoleLocalService.addSegmentsEntryRole(
				_role.getRoleId(), _segmentsEntry.getSegmentsEntryId(),
				ServiceContextTestUtil.getServiceContext());

		List<SegmentsEntryRole> segmentsEntryRoles =
			_segmentsEntryRoleLocalService.getSegmentsEntryRoles(
				_segmentsEntry.getSegmentsEntryId());

		Assert.assertEquals(
			segmentsEntryRoles.toString(), 1, segmentsEntryRoles.size());
		Assert.assertEquals(segmentsEntryRole, segmentsEntryRoles.get(0));
	}

	@Test
	public void testGetSegmentsEntryRolesByRoleId() throws PortalException {
		SegmentsEntryRole segmentsEntryRole =
			_segmentsEntryRoleLocalService.addSegmentsEntryRole(
				_role.getRoleId(), _segmentsEntry.getSegmentsEntryId(),
				ServiceContextTestUtil.getServiceContext());

		List<SegmentsEntryRole> segmentsEntryRoles =
			_segmentsEntryRoleLocalService.getSegmentsEntryRolesByRoleId(
				_role.getRoleId());

		Assert.assertEquals(
			segmentsEntryRoles.toString(), 1, segmentsEntryRoles.size());
		Assert.assertEquals(segmentsEntryRole, segmentsEntryRoles.get(0));
	}

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private SegmentsEntry _segmentsEntry;

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Inject
	private SegmentsEntryRoleLocalService _segmentsEntryRoleLocalService;

}