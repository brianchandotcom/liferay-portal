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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
import com.liferay.portal.kernel.service.LayoutLocalService;
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
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsActionKeys;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.service.SegmentsExperienceService;
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
public class SegmentsExperienceServiceTest {

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

		Layout layout = LayoutTestUtil.addLayout(_group);

		_classNameId = _classNameLocalService.getClassNameId(Layout.class);

		_classPK = layout.getPlid();

		_segmentsExperienceLocalService.deleteSegmentsExperiences(
			_group.getGroupId(), _classNameId, layout.getPlid());
	}

	@Test
	public void testAddSegmentsExperience() throws Exception {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceService.addSegmentsExperience(
				segmentsEntry.getSegmentsEntryId(), _classNameId, _classPK,
				RandomTestUtil.randomLocaleStringMap(), true,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"property", "value"
				).build(),
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId()));

		UnicodeProperties actualTypeSettingsUnicodeProperties =
			segmentsExperience.getTypeSettingsUnicodeProperties();

		Assert.assertEquals(
			"value",
			actualTypeSettingsUnicodeProperties.getProperty("property"));
	}

	@Test
	public void testAddSegmentsExperienceWithManageSegmentsEntriesPermission()
		throws Exception {

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(), "com.liferay.segments",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			_role.getRoleId(), SegmentsActionKeys.MANAGE_SEGMENTS_ENTRIES);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_addSegmentsExperience(
				ServiceContextTestUtil.getServiceContext(
					_group, _user.getUserId()));
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testAddSegmentsExperienceWithoutManageSegmentsEntriesPermission()
		throws Exception {

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_addSegmentsExperience(
				ServiceContextTestUtil.getServiceContext(
					_group, _user.getUserId()));
		}
	}

	@Test
	public void testAddSegmentsExperienceWithoutManageSegmentsEntriesPermissionAndWithUpdateLayoutPermission()
		throws Exception {

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(), Layout.class.getName(),
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			_role.getRoleId(), ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_addSegmentsExperience(
				ServiceContextTestUtil.getServiceContext(
					_group, _user.getUserId()));
		}
	}

	@Test
	public void testAddSegmentsExperienceWithoutTypeSettings()
		throws Exception {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceService.addSegmentsExperience(
				segmentsEntry.getSegmentsEntryId(), _classNameId, _classPK,
				RandomTestUtil.randomLocaleStringMap(), true,
				new UnicodeProperties(true),
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId()));

		Assert.assertEquals(
			StringPool.BLANK, segmentsExperience.getTypeSettings());
	}

	@Test
	public void testAppendSegmentsExperience() throws Exception {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceService.appendSegmentsExperience(
				segmentsEntry.getSegmentsEntryId(), _classNameId, _classPK,
				RandomTestUtil.randomLocaleStringMap(), true,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"property", "value"
				).build(),
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId()));

		UnicodeProperties actualTypeSettingsUnicodeProperties =
			segmentsExperience.getTypeSettingsUnicodeProperties();

		Assert.assertEquals(
			"value",
			actualTypeSettingsUnicodeProperties.getProperty("property"));
	}

	@Test
	public void testAppendSegmentsExperienceWithoutTypeSettings()
		throws Exception {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceService.appendSegmentsExperience(
				segmentsEntry.getSegmentsEntryId(), _classNameId, _classPK,
				RandomTestUtil.randomLocaleStringMap(), true,
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId()));

		Assert.assertEquals(
			StringPool.BLANK, segmentsExperience.getTypeSettings());
	}

	@Test
	public void testDeleteSegmentsExperienceWithDeletePermission()
		throws Exception {

		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(),
			"com.liferay.segments.model.SegmentsExperience",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			_role.getRoleId(), ActionKeys.DELETE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_segmentsExperienceService.deleteSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId());
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testDeleteSegmentsExperienceWithoutDeletePermission()
		throws Exception {

		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_segmentsExperienceService.deleteSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId());
		}
	}

	@Test
	public void testDeleteSegmentsExperienceWithoutDeletePermissionAndWithUpdateLayoutPermission()
		throws Exception {

		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(), Layout.class.getName(),
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			_role.getRoleId(), ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_segmentsExperienceService.deleteSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId());
		}
	}

	@Test
	public void testGetSegmentsExperiencesCountWithoutViewPermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK, serviceContext);

		SegmentsTestUtil.addSegmentsExperience(
			_classNameId, _classPK, serviceContext);
		SegmentsTestUtil.addSegmentsExperience(
			_classNameId, _classPK, serviceContext);

		List<Role> roles = RoleLocalServiceUtil.getRoles(_group.getCompanyId());

		for (Role role : roles) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_group.getCompanyId(),
				"com.liferay.segments.model.SegmentsExperience",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(segmentsExperience.getSegmentsExperienceId()),
				role.getRoleId(), ActionKeys.VIEW);
		}

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			Assert.assertEquals(
				2,
				_segmentsExperienceService.getSegmentsExperiencesCount(
					_group.getGroupId(), _classNameId, _classPK, true));
		}
	}

	@Test
	public void testGetSegmentsExperiencesCountWithoutViewPermissionAndWithUpdateLayoutPermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK, serviceContext);

		SegmentsTestUtil.addSegmentsExperience(
			_classNameId, _classPK, serviceContext);
		SegmentsTestUtil.addSegmentsExperience(
			_classNameId, _classPK, serviceContext);

		List<Role> roles = RoleLocalServiceUtil.getRoles(_group.getCompanyId());

		for (Role role : roles) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_group.getCompanyId(),
				"com.liferay.segments.model.SegmentsExperience",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(segmentsExperience.getSegmentsExperienceId()),
				role.getRoleId(), ActionKeys.VIEW);

			ResourcePermissionLocalServiceUtil.setResourcePermissions(
				_group.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(segmentsExperience.getClassPK()),
				_role.getRoleId(), new String[] {ActionKeys.UPDATE});
		}

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			Assert.assertEquals(
				3,
				_segmentsExperienceService.getSegmentsExperiencesCount(
					_group.getGroupId(), _classNameId, _classPK, true));
		}
	}

	@Test
	public void testGetSegmentsExperiencesCountWithViewPermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SegmentsTestUtil.addSegmentsExperience(
			_classNameId, _classPK, serviceContext);
		SegmentsTestUtil.addSegmentsExperience(
			_classNameId, _classPK, serviceContext);
		SegmentsTestUtil.addSegmentsExperience(
			_classNameId, _classPK, serviceContext);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			Assert.assertEquals(
				3,
				_segmentsExperienceService.getSegmentsExperiencesCount(
					_group.getGroupId(), _classNameId, _classPK, true));
		}
	}

	@Test
	public void testGetSegmentsExperiencesWithoutViewPermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SegmentsExperience segmentsExperience1 =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK, serviceContext);
		SegmentsExperience segmentsExperience2 =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK, serviceContext);
		SegmentsExperience segmentsExperience3 =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK, serviceContext);

		for (Role role : RoleLocalServiceUtil.getRoles(_group.getCompanyId())) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_group.getCompanyId(),
				"com.liferay.segments.model.SegmentsExperience",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(segmentsExperience2.getSegmentsExperienceId()),
				role.getRoleId(), ActionKeys.VIEW);
		}

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			List<SegmentsExperience> segmentsEntries =
				_segmentsExperienceService.getSegmentsExperiences(
					_group.getGroupId(), _classNameId, _classPK, true,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			Assert.assertEquals(
				segmentsEntries.toString(), 2, segmentsEntries.size());

			Assert.assertTrue(segmentsEntries.contains(segmentsExperience1));
			Assert.assertTrue(segmentsEntries.contains(segmentsExperience3));
		}
	}

	@Test
	public void testGetSegmentsExperiencesWithoutViewPermissionAndWithUpdateLayoutPermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SegmentsExperience segmentsExperience1 =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK, serviceContext);
		SegmentsExperience segmentsExperience2 =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK, serviceContext);
		SegmentsExperience segmentsExperience3 =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK, serviceContext);

		for (Role role : RoleLocalServiceUtil.getRoles(_group.getCompanyId())) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_group.getCompanyId(),
				"com.liferay.segments.model.SegmentsExperience",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(segmentsExperience2.getSegmentsExperienceId()),
				role.getRoleId(), ActionKeys.VIEW);

			ResourcePermissionLocalServiceUtil.setResourcePermissions(
				_group.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(segmentsExperience2.getClassPK()),
				_role.getRoleId(), new String[] {ActionKeys.UPDATE});
		}

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			List<SegmentsExperience> segmentsEntries =
				_segmentsExperienceService.getSegmentsExperiences(
					_group.getGroupId(), _classNameId, _classPK, true,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			Assert.assertEquals(
				segmentsEntries.toString(), 3, segmentsEntries.size());

			Assert.assertTrue(segmentsEntries.contains(segmentsExperience1));
			Assert.assertTrue(segmentsEntries.contains(segmentsExperience2));
			Assert.assertTrue(segmentsEntries.contains(segmentsExperience3));
		}
	}

	@Test
	public void testGetSegmentsExperiencesWithViewPermission()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		SegmentsExperience segmentsExperience1 =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK, serviceContext);
		SegmentsExperience segmentsExperience2 =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK, serviceContext);
		SegmentsExperience segmentsExperience3 =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK, serviceContext);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			List<SegmentsExperience> segmentsExperiences =
				_segmentsExperienceService.getSegmentsExperiences(
					_group.getGroupId(), _classNameId, _classPK, true,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			Assert.assertEquals(
				segmentsExperiences.toString(), 3, segmentsExperiences.size());

			Assert.assertTrue(
				segmentsExperiences.contains(segmentsExperience1));
			Assert.assertTrue(
				segmentsExperiences.contains(segmentsExperience2));
			Assert.assertTrue(
				segmentsExperiences.contains(segmentsExperience3));
		}
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testGetSegmentsExperienceWithoutViewPermission()
		throws Exception {

		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		List<Role> roles = RoleLocalServiceUtil.getRoles(_group.getCompanyId());

		for (Role role : roles) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_group.getCompanyId(),
				"com.liferay.segments.model.SegmentsExperience",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(segmentsExperience.getSegmentsExperienceId()),
				role.getRoleId(), ActionKeys.VIEW);
		}

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_segmentsExperienceService.getSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId());
		}
	}

	@Test
	public void testGetSegmentsExperienceWithoutViewPermissionAndWithUpdateLayoutPermission()
		throws Exception {

		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		List<Role> roles = RoleLocalServiceUtil.getRoles(_group.getCompanyId());

		for (Role role : roles) {
			if (RoleConstants.OWNER.equals(role.getName())) {
				continue;
			}

			ResourcePermissionLocalServiceUtil.removeResourcePermission(
				_group.getCompanyId(),
				"com.liferay.segments.model.SegmentsExperience",
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(segmentsExperience.getSegmentsExperienceId()),
				role.getRoleId(), ActionKeys.VIEW);

			ResourcePermissionLocalServiceUtil.setResourcePermissions(
				_group.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(segmentsExperience.getClassPK()),
				_role.getRoleId(), new String[] {ActionKeys.UPDATE});
		}

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_segmentsExperienceService.getSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId());
		}
	}

	@Test
	public void testGetSegmentsExperienceWithViewPermission() throws Exception {
		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_segmentsExperienceService.getSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId());
		}
	}

	@Test
	public void testUpdateSegmentsExperience() throws Exception {
		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceService.addSegmentsExperience(
				segmentsEntry.getSegmentsEntryId(), _classNameId, _classPK,
				RandomTestUtil.randomLocaleStringMap(), true,
				new UnicodeProperties(true),
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId()));

		SegmentsExperience updatedSegmentsExperience =
			_segmentsExperienceService.updateSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId(),
				RandomTestUtil.randomLong(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomBoolean(),
				UnicodePropertiesBuilder.create(
					true
				).put(
					"property", "value"
				).build());

		UnicodeProperties actualTypeSettingsUnicodeProperties =
			updatedSegmentsExperience.getTypeSettingsUnicodeProperties();

		Assert.assertEquals(
			"value",
			actualTypeSettingsUnicodeProperties.getProperty("property"));
	}

	@Test
	public void testUpdateSegmentsExperienceWithoutTypeSettings()
		throws Exception {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceService.addSegmentsExperience(
				segmentsEntry.getSegmentsEntryId(), _classNameId, _classPK,
				RandomTestUtil.randomLocaleStringMap(), true,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"property", "value"
				).build(),
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId()));

		SegmentsExperience updatedSegmentsExperience =
			_segmentsExperienceService.updateSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId(),
				RandomTestUtil.randomLong(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomBoolean());

		UnicodeProperties actualTypeSettingsUnicodeProperties =
			updatedSegmentsExperience.getTypeSettingsUnicodeProperties();

		Assert.assertEquals(
			"value",
			actualTypeSettingsUnicodeProperties.getProperty("property"));
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUpdateSegmentsExperienceWithoutUpdatePermission()
		throws Exception {

		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_segmentsExperienceService.updateSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId(),
				RandomTestUtil.randomLong(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomBoolean());
		}
	}

	@Test
	public void testUpdateSegmentsExperienceWithoutUpdatePermissionAndWithUpdateLayoutPermission()
		throws Exception {

		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		ResourcePermissionLocalServiceUtil.setResourcePermissions(
			_group.getCompanyId(), Layout.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(segmentsExperience.getClassPK()), _role.getRoleId(),
			new String[] {ActionKeys.UPDATE});

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_segmentsExperienceService.updateSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId(),
				RandomTestUtil.randomLong(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomBoolean());
		}
	}

	@Test
	public void testUpdateSegmentsExperienceWithUpdatePermission()
		throws Exception {

		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				_classNameId, _classPK,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			_group.getCompanyId(),
			"com.liferay.segments.model.SegmentsExperience",
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			_role.getRoleId(), ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_segmentsExperienceService.updateSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId(),
				RandomTestUtil.randomLong(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomBoolean());
		}
	}

	private SegmentsExperience _addSegmentsExperience(
			ServiceContext serviceContext)
		throws Exception {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		return _segmentsExperienceService.addSegmentsExperience(
			segmentsEntry.getSegmentsEntryId(), _classNameId, _classPK,
			RandomTestUtil.randomLocaleStringMap(), true,
			new UnicodeProperties(true), serviceContext);
	}

	private long _classNameId;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private long _classPK;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@DeleteAfterTestRun
	private Role _role;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Inject
	private SegmentsExperienceService _segmentsExperienceService;

	@DeleteAfterTestRun
	private User _user;

}