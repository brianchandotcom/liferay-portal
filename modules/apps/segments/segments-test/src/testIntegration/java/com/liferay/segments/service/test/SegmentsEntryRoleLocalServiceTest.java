/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.exception.NoSuchEntryException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryRole;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRoleLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.ArrayList;
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
		_serviceContext = ServiceContextTestUtil.getServiceContext();
	}

	@Test
	public void testAddSegmentsEntryRole() throws PortalException {
		SegmentsEntryRole segmentsEntryRole =
			_segmentsEntryRoleLocalService.addSegmentsEntryRole(
				_segmentsEntry.getSegmentsEntryId(), _role.getRoleId(),
				_serviceContext);

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

	@Test(expected = NoSuchRoleException.class)
	public void testAddSegmentsEntryRoleWithInvalidRoleId() throws Exception {
		_segmentsEntryRoleLocalService.addSegmentsEntryRole(
			_segmentsEntry.getSegmentsEntryId(), 0L, _serviceContext);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testAddSegmentsEntryRoleWithInvalidSegmentsEntryId()
		throws Exception {

		_segmentsEntryRoleLocalService.addSegmentsEntryRole(
			0L, _role.getRoleId(), _serviceContext);
	}

	@Test
	public void testDeleteSegmentsEntryRole() throws PortalException {
		_segmentsEntryRoleLocalService.addSegmentsEntryRole(
			_segmentsEntry.getSegmentsEntryId(), _role.getRoleId(),
			_serviceContext);

		_segmentsEntryRoleLocalService.deleteSegmentsEntryRole(
			_segmentsEntry.getSegmentsEntryId(), _role.getRoleId());

		Assert.assertEquals(
			0,
			_segmentsEntryRoleLocalService.getSegmentsEntryRolesCount(
				_segmentsEntry.getSegmentsEntryId()));
	}

	@Test
	public void testDeleteSegmentsEntryRolesByRoleId() throws PortalException {
		_segmentsEntryRoleLocalService.addSegmentsEntryRole(
			_segmentsEntry.getSegmentsEntryId(), _role.getRoleId(),
			_serviceContext);

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
			_segmentsEntry.getSegmentsEntryId(), _role.getRoleId(),
			_serviceContext);

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
				_segmentsEntry.getSegmentsEntryId(), _role.getRoleId(),
				_serviceContext);

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
				_segmentsEntry.getSegmentsEntryId(), _role.getRoleId(),
				_serviceContext);

		List<SegmentsEntryRole> segmentsEntryRoles =
			_segmentsEntryRoleLocalService.getSegmentsEntryRolesByRoleId(
				_role.getRoleId());

		Assert.assertEquals(
			segmentsEntryRoles.toString(), 1, segmentsEntryRoles.size());
		Assert.assertEquals(segmentsEntryRole, segmentsEntryRoles.get(0));
	}

	@Test
	public void testSetSegmentsEntrySiteRoles() throws Exception {
		List<Long> actualRoleIdsList = ListUtil.fromArray(
			_segmentsEntry.getRoleIds());

		Assert.assertEquals(
			actualRoleIdsList.toString(), 0, actualRoleIdsList.size());

		for (int i = 0; i < 5; i++) {
			_roles.add(RoleTestUtil.addRole(RoleConstants.TYPE_SITE));
		}

		long[] expectedRoleIds = ListUtil.toLongArray(
			_roles, Role.ROLE_ID_ACCESSOR);

		_segmentsEntryRoleLocalService.setSegmentsEntrySiteRoles(
			_segmentsEntry.getSegmentsEntryId(), expectedRoleIds,
			_serviceContext);

		List<Long> expectedRoleIdsList = ListUtil.fromArray(expectedRoleIds);

		actualRoleIdsList = ListUtil.fromArray(_segmentsEntry.getRoleIds());

		Assert.assertEquals(
			actualRoleIdsList.toString(), expectedRoleIdsList.size(),
			actualRoleIdsList.size());

		Assert.assertTrue(expectedRoleIdsList.containsAll(actualRoleIdsList));

		_segmentsEntryRoleLocalService.setSegmentsEntrySiteRoles(
			_segmentsEntry.getSegmentsEntryId(), new long[0], _serviceContext);

		actualRoleIdsList = ListUtil.fromArray(_segmentsEntry.getRoleIds());

		Assert.assertEquals(
			actualRoleIdsList.toString(), 0, actualRoleIdsList.size());
	}

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private final List<Role> _roles = new ArrayList<>();

	@DeleteAfterTestRun
	private SegmentsEntry _segmentsEntry;

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Inject
	private SegmentsEntryRoleLocalService _segmentsEntryRoleLocalService;

	private ServiceContext _serviceContext;

}