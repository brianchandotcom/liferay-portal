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
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperimentService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
public class SegmentsExperimentServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_role = RoleLocalServiceUtil.addRole(
			TestPropsValues.getUserId(), null, 0, StringUtil.randomString(),
			null, null, RoleConstants.TYPE_SITE, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_user = UserTestUtil.addGroupUser(_group, _role.getName());
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddSegmentsExperimentWithoutManageSegmentsEntriesPermission()
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_addSegmentsExperiment(
				ServiceContextTestUtil.getServiceContext(
					_group, _user.getUserId()));
		}
	}

	@Test
	public void testAddSegmentsExperimentWithUpdateLayoutPermission()
		throws Exception {

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(), Layout.class.getName(),
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			_role.getRoleId(), ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_addSegmentsExperiment(
				ServiceContextTestUtil.getServiceContext(
					_group, _user.getUserId()));
		}
	}

	@Test
	public void testDeleteSegmentsExperimentWithDeletePermission()
		throws Exception {

		SegmentsExperiment segmentsExperiment = _addSegmentsExperiment();

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(),
			"com.liferay.segments.model.SegmentsExperiment",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			_role.getRoleId(), ActionKeys.DELETE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_segmentsExperimentService.deleteSegmentsExperiment(
				segmentsExperiment.getSegmentsExperimentKey());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteSegmentsExperimentWithoutDeletePermission()
		throws Exception {

		SegmentsExperiment segmentsExperiment = _addSegmentsExperiment();

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_segmentsExperimentService.deleteSegmentsExperiment(
				segmentsExperiment.getSegmentsExperimentKey());
		}
	}

	@Test
	public void testDeleteSegmentsExperimentWithoutDeletePermissionAndWithUpdateLayoutPermission()
		throws Exception {

		SegmentsExperiment segmentsExperiment = _addSegmentsExperiment();

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(), Layout.class.getName(),
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			_role.getRoleId(), ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_segmentsExperimentService.deleteSegmentsExperiment(
				segmentsExperiment.getSegmentsExperimentKey());
		}
	}

	@Test
	public void testGetSegmentsExperimentsWithoutViewPermission()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		SegmentsExperiment segmentsExperiment1 = _addSegmentsExperiment(layout);
		SegmentsExperiment segmentsExperiment2 = _addSegmentsExperiment(layout);
		SegmentsExperiment segmentsExperiment3 = _addSegmentsExperiment(layout);

		for (Role role : RoleLocalServiceUtil.getRoles(_group.getCompanyId())) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_group.getCompanyId(),
				"com.liferay.segments.model.SegmentsExperiment",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(segmentsExperiment2.getSegmentsExperimentId()),
				role.getRoleId(), ActionKeys.VIEW);
		}

		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			List<SegmentsExperiment> segmentsExperiments =
				_segmentsExperimentService.getSegmentsExperiments(
					layout.getGroupId(), classNameId, layout.getPlid());

			Assert.assertEquals(
				segmentsExperiments.toString(), 2, segmentsExperiments.size());

			Assert.assertTrue(
				segmentsExperiments.contains(segmentsExperiment1));

			Assert.assertTrue(
				segmentsExperiments.contains(segmentsExperiment3));
		}
	}

	@Test
	public void testGetSegmentsExperimentsWithViewPermission()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		SegmentsExperiment segmentsExperiment1 = _addSegmentsExperiment(layout);
		SegmentsExperiment segmentsExperiment2 = _addSegmentsExperiment(layout);
		SegmentsExperiment segmentsExperiment3 = _addSegmentsExperiment(layout);

		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			List<SegmentsExperiment> segmentsExperiments =
				_segmentsExperimentService.getSegmentsExperiments(
					layout.getGroupId(), classNameId, layout.getPlid());

			Assert.assertEquals(
				segmentsExperiments.toString(), 3, segmentsExperiments.size());
			Assert.assertTrue(
				segmentsExperiments.contains(segmentsExperiment1));
			Assert.assertTrue(
				segmentsExperiments.contains(segmentsExperiment2));
			Assert.assertTrue(
				segmentsExperiments.contains(segmentsExperiment3));
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateSegmentsExperimentWithoutUpdatePermission()
		throws Exception {

		SegmentsExperiment segmentsExperiment = _addSegmentsExperiment();

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_segmentsExperimentService.updateSegmentsExperimentStatus(
				segmentsExperiment.getSegmentsExperimentKey(),
				SegmentsExperimentConstants.STATUS_TERMINATED);
		}
	}

	@Test
	public void testUpdateSegmentsExperimentWithUpdatePermission()
		throws Exception {

		SegmentsExperiment segmentsExperiment = _addSegmentsExperiment();

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(),
			"com.liferay.segments.model.SegmentsExperiment",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			_role.getRoleId(), ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_segmentsExperimentService.updateSegmentsExperimentStatus(
				segmentsExperiment.getSegmentsExperimentKey(),
				SegmentsExperimentConstants.STATUS_RUNNING);
		}
	}

	private SegmentsExperiment _addSegmentsExperiment() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return _addSegmentsExperiment(serviceContext);
	}

	private SegmentsExperiment _addSegmentsExperiment(Layout layout)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return _addSegmentsExperiment(layout, serviceContext);
	}

	private SegmentsExperiment _addSegmentsExperiment(
			Layout layout, ServiceContext serviceContext)
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());

		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				_group.getGroupId(), classNameId, layout.getPlid());

		return _segmentsExperimentService.addSegmentsExperiment(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperience.getClassNameId(),
			segmentsExperience.getClassPK(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			SegmentsExperimentConstants.Goal.BOUNCE_RATE.getLabel(),
			StringPool.BLANK, serviceContext);
	}

	private SegmentsExperiment _addSegmentsExperiment(
			ServiceContext serviceContext)
		throws Exception {

		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		return _addSegmentsExperiment(layout, serviceContext);
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Role _role;

	@Inject
	private SegmentsExperimentService _segmentsExperimentService;

	@DeleteAfterTestRun
	private User _user;

}