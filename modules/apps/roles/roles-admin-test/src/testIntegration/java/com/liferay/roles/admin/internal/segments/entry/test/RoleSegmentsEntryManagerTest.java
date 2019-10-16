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

package com.liferay.roles.admin.internal.segments.entry.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.roles.admin.segments.entry.RoleSegmentsEntryManager;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryRelLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class RoleSegmentsEntryManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			TestPropsValues.getGroupId());

		_roleClassNameId = _classNameLocalService.getClassNameId(Role.class);

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext());
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testAddRoleSegmentsEntries() throws Exception {
		_assertSegmentsEntries(0);

		_roleSegmentsEntryManager.addRoleSegmentsEntries(
			_role.getRoleId(),
			new long[] {_segmentsEntry.getSegmentsEntryId()});

		_assertSegmentsEntries(1);
	}

	@Test
	public void testRemoveRoleSegmentsEntries() throws Exception {
		_roleSegmentsEntryManager.addRoleSegmentsEntries(
			_role.getRoleId(),
			new long[] {_segmentsEntry.getSegmentsEntryId()});

		_assertSegmentsEntries(1);

		_roleSegmentsEntryManager.removeRoleSegmentsEntries(
			_role.getRoleId(),
			new long[] {_segmentsEntry.getSegmentsEntryId()});

		_assertSegmentsEntries(0);
	}

	private void _assertSegmentsEntries(int expected) {
		Assert.assertEquals(
			expected,
			_segmentsEntryRelLocalService.getSegmentsEntryRelsCount(
				_roleClassNameId, _role.getRoleId()));
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Role _role;

	private long _roleClassNameId;

	@Inject
	private RoleSegmentsEntryManager _roleSegmentsEntryManager;

	@DeleteAfterTestRun
	private SegmentsEntry _segmentsEntry;

	@Inject
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

}