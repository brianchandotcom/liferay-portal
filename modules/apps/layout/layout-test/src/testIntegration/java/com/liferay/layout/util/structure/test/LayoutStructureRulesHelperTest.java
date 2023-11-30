/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.structure.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureRulesHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsEntryConstants;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class LayoutStructureRulesHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser(_group.getGroupId());
	}

	@Test
	public void testWithAllConditionsCompleted() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		_userGroupRoleService.addUserGroupRoles(
			_user.getUserId(), _group.getGroupId(),
			new long[] {role.getRoleId()});

		LayoutStructure layoutStructure = LayoutStructure.of(
			StringUtil.replace(
				_read("layout_data_rules_all.json"), "${", "}",
				HashMapBuilder.put(
					"ROLE_ID", String.valueOf(role.getRoleId())
				).put(
					"SEGMENTS_ENTRY_ID",
					String.valueOf(SegmentsEntryConstants.ID_DEFAULT)
				).build()));

		LayoutStructureRulesHelper.LayoutStructureRulesResult
			layoutStructureRulesResult =
				_layoutStructureRulesHelper.processLayoutStructureRules(
					_group.getGroupId(), layoutStructure,
					PermissionCheckerFactoryUtil.create(_user),
					new long[] {SegmentsEntryConstants.ID_DEFAULT});

		Set<String> displayedItemIds =
			layoutStructureRulesResult.getDisplayedItemIds();

		Set<String> hiddenItemIds =
			layoutStructureRulesResult.getHiddenItemIds();

		Assert.assertEquals(
			displayedItemIds.toString(), 1, displayedItemIds.size());
		Assert.assertEquals(hiddenItemIds.toString(), 1, hiddenItemIds.size());

		Assert.assertTrue(displayedItemIds.contains("container2"));
		Assert.assertTrue(hiddenItemIds.contains("fragment1"));
	}

	@Test
	public void testWithAnyConditionsCompleted() throws Exception {
		LayoutStructure layoutStructure = LayoutStructure.of(
			StringUtil.replace(
				_read("layout_data_rules_any.json"), "${", "}",
				HashMapBuilder.put(
					"ROLE_ID", RandomTestUtil.randomString()
				).put(
					"SEGMENTS_ENTRY_ID",
					String.valueOf(SegmentsEntryConstants.ID_DEFAULT)
				).build()));

		LayoutStructureRulesHelper.LayoutStructureRulesResult
			layoutStructureRulesResult =
				_layoutStructureRulesHelper.processLayoutStructureRules(
					_group.getGroupId(), layoutStructure,
					PermissionCheckerFactoryUtil.create(_user),
					new long[] {SegmentsEntryConstants.ID_DEFAULT});

		Set<String> displayedItemIds =
			layoutStructureRulesResult.getDisplayedItemIds();

		Set<String> hiddenItemIds =
			layoutStructureRulesResult.getHiddenItemIds();

		Assert.assertEquals(
			displayedItemIds.toString(), 1, displayedItemIds.size());
		Assert.assertEquals(hiddenItemIds.toString(), 1, hiddenItemIds.size());

		Assert.assertTrue(displayedItemIds.contains("container2"));
		Assert.assertTrue(hiddenItemIds.contains("fragment1"));
	}

	@Test
	public void testWithoutConditionsCompleted() throws Exception {
		PermissionCheckerFactoryUtil.create(_user);

		LayoutStructure layoutStructure = LayoutStructure.of(
			_read("layout_data_rules_all.json"));

		LayoutStructureRulesHelper.LayoutStructureRulesResult
			layoutStructureRulesResult =
				_layoutStructureRulesHelper.processLayoutStructureRules(
					_group.getGroupId(), layoutStructure,
					PermissionCheckerFactoryUtil.create(_user), new long[0]);

		Set<String> displayedItemIds =
			layoutStructureRulesResult.getDisplayedItemIds();

		Set<String> hiddenItemIds =
			layoutStructureRulesResult.getHiddenItemIds();

		Assert.assertEquals(
			displayedItemIds.toString(), 0, displayedItemIds.size());
		Assert.assertEquals(hiddenItemIds.toString(), 0, hiddenItemIds.size());
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(),
			"com/liferay/layout/util/structure/test/dependencies/" + fileName);
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutStructureRulesHelper _layoutStructureRulesHelper;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserGroupRoleService _userGroupRoleService;

}