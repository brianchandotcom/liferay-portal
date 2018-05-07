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

package com.liferay.staging.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class StagingLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = TestPropsValues.getUser();

		ServiceTestUtil.setUser(_user);
	}

	@Test
	public void testDisableStaging() throws Exception {
		Group group = GroupTestUtil.addGroup();

		try {
			StagingLocalServiceUtil.enableLocalStaging(
				_user.getUserId(), group, false, false, new ServiceContext());

			StagingLocalServiceUtil.disableStaging(group, new ServiceContext());

			group = GroupLocalServiceUtil.getGroup(group.getGroupId());

			Assert.assertFalse(group.hasStagingGroup());
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(group.getGroupId());
		}
	}

	@Test
	public void testDisableStagingWithParentGroup() throws Exception {
		Group parentGroup = GroupTestUtil.addGroup();

		Group childGroup = GroupTestUtil.addGroup(parentGroup.getGroupId());

		try {
			StagingLocalServiceUtil.enableLocalStaging(
				_user.getUserId(), childGroup, false, false,
				new ServiceContext());

			StagingLocalServiceUtil.disableStaging(
				childGroup, new ServiceContext());

			childGroup = GroupLocalServiceUtil.getGroup(
				childGroup.getGroupId());

			Assert.assertFalse(childGroup.hasStagingGroup());
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(childGroup.getGroupId());

			GroupLocalServiceUtil.deleteGroup(parentGroup.getGroupId());
		}
	}

	@Test
	public void testDisableStagingWithStagedChildGroup() throws Exception {
		Group parentGroup = GroupTestUtil.addGroup();

		Group childGroup = GroupTestUtil.addGroup(parentGroup.getGroupId());

		try {
			StagingLocalServiceUtil.enableLocalStaging(
				_user.getUserId(), parentGroup, false, false,
				new ServiceContext());

			StagingLocalServiceUtil.enableLocalStaging(
				_user.getUserId(), childGroup, false, false,
				new ServiceContext());

			StagingLocalServiceUtil.disableStaging(
				parentGroup, new ServiceContext());

			childGroup = GroupLocalServiceUtil.getGroup(
				childGroup.getGroupId());

			Group childGroupStagingGroup = childGroup.getStagingGroup();

			Assert.assertEquals(
				childGroupStagingGroup.getParentGroupId(),
				childGroup.getParentGroupId());
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(childGroup.getGroupId());

			GroupLocalServiceUtil.deleteGroup(parentGroup.getGroupId());
		}
	}

	@Test
	public void testEnableLocalStaging() throws Exception {
		Group group = GroupTestUtil.addGroup();

		try {
			StagingLocalServiceUtil.enableLocalStaging(
				_user.getUserId(), group, false, false, new ServiceContext());

			group = GroupLocalServiceUtil.getGroup(group.getGroupId());

			Assert.assertTrue(group.hasStagingGroup());
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(group.getGroupId());
		}
	}

	@Test
	public void testEnableLocalStagingWithParentGroup() throws Exception {
		Group parentGroup = GroupTestUtil.addGroup();

		Group group = GroupTestUtil.addGroup(parentGroup.getGroupId());

		try {
			StagingLocalServiceUtil.enableLocalStaging(
				_user.getUserId(), group, false, false, new ServiceContext());

			group = GroupLocalServiceUtil.getGroup(group.getGroupId());

			Group groupStagingGroup = group.getStagingGroup();

			Assert.assertEquals(
				parentGroup.getGroupId(), group.getParentGroupId());

			Assert.assertEquals(
				parentGroup.getGroupId(), groupStagingGroup.getParentGroupId());
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(group.getGroupId());

			GroupLocalServiceUtil.deleteGroup(parentGroup.getGroupId());
		}
	}

	@Test
	public void testEnableLocalStagingWithStagedChildGroup() throws Exception {
		Group parentGroup = GroupTestUtil.addGroup();

		Group childGroup = GroupTestUtil.addGroup(parentGroup.getGroupId());

		try {
			StagingLocalServiceUtil.enableLocalStaging(
				_user.getUserId(), childGroup, false, false,
				new ServiceContext());

			StagingLocalServiceUtil.enableLocalStaging(
				_user.getUserId(), parentGroup, false, false,
				new ServiceContext());

			childGroup = GroupLocalServiceUtil.getGroup(
				childGroup.getGroupId());

			Group childGroupStagingGroup = childGroup.getStagingGroup();

			Assert.assertEquals(
				parentGroup.getGroupId(), childGroup.getParentGroupId());

			Assert.assertEquals(
				parentGroup.getGroupId(),
				childGroupStagingGroup.getParentGroupId());
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(childGroup.getGroupId());

			GroupLocalServiceUtil.deleteGroup(parentGroup.getGroupId());
		}
	}

	@Test
	public void testEnableLocalStagingWithStagedParentGroup() throws Exception {
		Group parentGroup = GroupTestUtil.addGroup();

		Group childGroup = GroupTestUtil.addGroup(parentGroup.getGroupId());

		try {
			StagingLocalServiceUtil.enableLocalStaging(
				_user.getUserId(), parentGroup, false, false,
				new ServiceContext());

			StagingLocalServiceUtil.enableLocalStaging(
				_user.getUserId(), childGroup, false, false,
				new ServiceContext());

			childGroup = GroupLocalServiceUtil.getGroup(
				childGroup.getGroupId());

			Group childGroupStagingGroup = childGroup.getStagingGroup();

			Assert.assertEquals(
				parentGroup.getGroupId(), childGroup.getParentGroupId());

			Assert.assertEquals(
				parentGroup.getGroupId(),
				childGroupStagingGroup.getParentGroupId());
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(childGroup.getGroupId());

			GroupLocalServiceUtil.deleteGroup(parentGroup.getGroupId());
		}
	}

	private User _user;

}