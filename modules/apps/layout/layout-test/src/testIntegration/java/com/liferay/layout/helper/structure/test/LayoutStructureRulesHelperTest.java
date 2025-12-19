/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.helper.structure.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.helper.structure.LayoutStructureRulesHelper;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureRule;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsEntryConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
					"ROLE_ID_1", String.valueOf(role.getRoleId())
				).put(
					"ROLE_ID_2", String.valueOf(RandomTestUtil.randomLong())
				).put(
					"SEGMENTS_ENTRY_ID_1",
					String.valueOf(SegmentsEntryConstants.ID_DEFAULT)
				).put(
					"SEGMENTS_ENTRY_ID_2",
					String.valueOf(RandomTestUtil.randomLong())
				).put(
					"USER_ID_1", String.valueOf(_user.getUserId())
				).put(
					"USER_ID_2", String.valueOf(RandomTestUtil.randomLong())
				).build()));

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		LayoutStructureRulesHelper.LayoutStructureRulesResult
			layoutStructureRulesResult =
				_layoutStructureRulesHelper.processLayoutStructureRules(
					_group.getGroupId(), null, layoutStructure,
					LocaleUtil.getDefault(), permissionChecker,
					new long[] {SegmentsEntryConstants.ID_DEFAULT});

		Set<String> displayedItemIds =
			layoutStructureRulesResult.getDisplayedItemIds();

		Assert.assertEquals(
			displayedItemIds.toString(), 1, displayedItemIds.size());
		Assert.assertTrue(
			displayedItemIds.toString(),
			displayedItemIds.contains("container2"));

		Set<String> hiddenItemIds =
			layoutStructureRulesResult.getHiddenItemIds();

		Assert.assertEquals(hiddenItemIds.toString(), 1, hiddenItemIds.size());
		Assert.assertTrue(
			hiddenItemIds.toString(), hiddenItemIds.contains("fragment1"));

		Assert.assertTrue(
			MapUtil.toString(layoutStructureRulesResult.getItemIdsMap()),
			MapUtil.isEmpty(layoutStructureRulesResult.getItemIdsMap()));
		Assert.assertTrue(
			MapUtil.toString(
				layoutStructureRulesResult.getLayoutStructureRuleIdsMap()),
			MapUtil.isEmpty(
				layoutStructureRulesResult.getLayoutStructureRuleIdsMap()));

		_testProcessLayoutStructureRulesWithFormTypeCondition(
			layoutStructure, permissionChecker,
			HashMapBuilder.put(
				"hide", ListUtil.fromCollection(hiddenItemIds)
			).put(
				"show", ListUtil.fromCollection(displayedItemIds)
			).build());
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
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		LayoutStructureRulesHelper.LayoutStructureRulesResult
			layoutStructureRulesResult =
				_layoutStructureRulesHelper.processLayoutStructureRules(
					_group.getGroupId(), null, layoutStructure,
					LocaleUtil.getDefault(), permissionChecker,
					new long[] {SegmentsEntryConstants.ID_DEFAULT});

		Set<String> displayedItemIds =
			layoutStructureRulesResult.getDisplayedItemIds();

		Assert.assertEquals(
			displayedItemIds.toString(), 1, displayedItemIds.size());
		Assert.assertTrue(displayedItemIds.contains("container2"));

		Set<String> hiddenItemIds =
			layoutStructureRulesResult.getHiddenItemIds();

		Assert.assertEquals(hiddenItemIds.toString(), 1, hiddenItemIds.size());
		Assert.assertTrue(hiddenItemIds.contains("fragment1"));

		Assert.assertTrue(
			MapUtil.toString(layoutStructureRulesResult.getItemIdsMap()),
			MapUtil.isEmpty(layoutStructureRulesResult.getItemIdsMap()));
		Assert.assertTrue(
			MapUtil.toString(
				layoutStructureRulesResult.getLayoutStructureRuleIdsMap()),
			MapUtil.isEmpty(
				layoutStructureRulesResult.getLayoutStructureRuleIdsMap()));

		_testProcessLayoutStructureRulesWithFormTypeCondition(
			layoutStructure, permissionChecker,
			HashMapBuilder.put(
				"hide", ListUtil.fromCollection(hiddenItemIds)
			).put(
				"show", ListUtil.fromCollection(displayedItemIds)
			).build());
	}

	@Test
	public void testWithoutConditionsCompleted() throws Exception {
		LayoutStructure layoutStructure = LayoutStructure.of(
			_read("layout_data_rules_all.json"));
		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		LayoutStructureRulesHelper.LayoutStructureRulesResult
			layoutStructureRulesResult =
				_layoutStructureRulesHelper.processLayoutStructureRules(
					_group.getGroupId(), null, layoutStructure,
					LocaleUtil.getDefault(), permissionChecker, new long[0]);

		Set<String> displayedItemIds =
			layoutStructureRulesResult.getDisplayedItemIds();

		Assert.assertEquals(
			displayedItemIds.toString(), 1, displayedItemIds.size());
		Assert.assertTrue(
			displayedItemIds.toString(),
			displayedItemIds.contains("fragment1"));

		Set<String> hiddenItemIds =
			layoutStructureRulesResult.getHiddenItemIds();

		Assert.assertEquals(hiddenItemIds.toString(), 1, hiddenItemIds.size());
		Assert.assertTrue(
			hiddenItemIds.toString(), hiddenItemIds.contains("container2"));

		Assert.assertTrue(
			MapUtil.toString(layoutStructureRulesResult.getItemIdsMap()),
			MapUtil.isEmpty(layoutStructureRulesResult.getItemIdsMap()));
		Assert.assertTrue(
			MapUtil.toString(
				layoutStructureRulesResult.getLayoutStructureRuleIdsMap()),
			MapUtil.isEmpty(
				layoutStructureRulesResult.getLayoutStructureRuleIdsMap()));

		_testProcessLayoutStructureRulesWithFormTypeCondition(
			layoutStructure, permissionChecker,
			HashMapBuilder.put(
				"hide", ListUtil.fromCollection(hiddenItemIds)
			).put(
				"show", ListUtil.fromCollection(displayedItemIds)
			).build());
	}

	private void _addFormTypeCondition(
		String itemId, LayoutStructureRule layoutStructureRule, String value) {

		JSONArray conditionsJSONArray =
			layoutStructureRule.getConditionsJSONArray();

		conditionsJSONArray.put(
			JSONUtil.put(
				"field", itemId
			).put(
				"id", RandomTestUtil.randomString()
			).put(
				"options",
				JSONUtil.put(
					"type", "equal"
				).put(
					"value", value
				)
			).put(
				"type", "form"
			));
	}

	private void _assertMapEquals(
		Map<String, List<String>> actualMap,
		Map<String, List<String>> expectedMap) {

		Assert.assertEquals(
			MapUtil.toString(actualMap), expectedMap.size(), actualMap.size());

		for (Map.Entry<String, List<String>> entry : expectedMap.entrySet()) {
			Assert.assertTrue(
				MapUtil.toString(actualMap),
				actualMap.containsKey(entry.getKey()));

			List<String> actualList = actualMap.get(entry.getKey());
			List<String> expectedList = entry.getValue();

			Assert.assertEquals(
				actualList.toString(), expectedList.size(), actualList.size());

			for (int i = 0; i < expectedList.size(); i++) {
				Assert.assertEquals(expectedList.get(i), actualList.get(i));
			}
		}
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(),
			"com/liferay/layout/helper/structure/test/dependencies/" +
				fileName);
	}

	private void _testProcessLayoutStructureRulesWithFormTypeCondition(
		LayoutStructure layoutStructure, PermissionChecker permissionChecker,
		Map<String, List<String>> map) {

		String parentItemId = layoutStructure.getMainItemId();

		String itemId = RandomTestUtil.randomString();

		layoutStructure.addContainerStyledLayoutStructureItem(
			itemId, parentItemId, 0);

		String value = RandomTestUtil.randomString();

		Map<String, Object> fieldValuesMap = HashMapBuilder.<String, Object>put(
			itemId, value
		).build();

		List<String> layoutStructureRuleIds = new ArrayList<>();

		Map<String, List<String>> itemIdsMap =
			HashMapBuilder.<String, List<String>>put(
				itemId, layoutStructureRuleIds
			).build();

		Map<String, List<String>> layoutStructureRuleIdsMap = new HashMap<>();

		for (LayoutStructureRule layoutStructureRule :
				layoutStructure.getLayoutStructureRules()) {

			if (Objects.equals(layoutStructureRule.getConditionType(), "any")) {
				_addFormTypeCondition(
					itemId, layoutStructureRule, RandomTestUtil.randomString());
			}
			else {
				_addFormTypeCondition(itemId, layoutStructureRule, value);
			}

			layoutStructureRuleIds.add(layoutStructureRule.getId());

			String curItemId = RandomTestUtil.randomString();

			layoutStructure.addContainerStyledLayoutStructureItem(
				curItemId, parentItemId, 0);

			String curValue = RandomTestUtil.randomString();

			fieldValuesMap.put(curItemId, curValue);

			if (Objects.equals(layoutStructureRule.getConditionType(), "any")) {
				_addFormTypeCondition(
					curItemId, layoutStructureRule,
					RandomTestUtil.randomString());
				_addFormTypeCondition(
					curItemId, layoutStructureRule,
					RandomTestUtil.randomString());

				_addFormTypeCondition(
					RandomTestUtil.randomString(), layoutStructureRule,
					RandomTestUtil.randomString());

				fieldValuesMap.put(curItemId, RandomTestUtil.randomString());
			}
			else {
				_addFormTypeCondition(curItemId, layoutStructureRule, curValue);
				_addFormTypeCondition(curItemId, layoutStructureRule, curValue);
			}

			itemIdsMap.put(
				curItemId, ListUtil.fromArray(layoutStructureRule.getId()));
			layoutStructureRuleIdsMap.put(
				layoutStructureRule.getId(),
				ListUtil.fromArray(itemId, curItemId));
		}

		LayoutStructureRulesHelper.LayoutStructureRulesResult
			layoutStructureRulesResult =
				_layoutStructureRulesHelper.processLayoutStructureRules(
					_group.getGroupId(), null, layoutStructure,
					LocaleUtil.getDefault(), permissionChecker,
					new long[] {SegmentsEntryConstants.ID_DEFAULT});

		Set<String> displayedItemIds =
			layoutStructureRulesResult.getDisplayedItemIds();
		Set<String> hiddenItemIds =
			layoutStructureRulesResult.getHiddenItemIds();

		Assert.assertEquals(
			displayedItemIds.toString(), 0, displayedItemIds.size());
		Assert.assertEquals(hiddenItemIds.toString(), 0, hiddenItemIds.size());

		_assertMapEquals(
			layoutStructureRulesResult.getItemIdsMap(), itemIdsMap);
		_assertMapEquals(
			layoutStructureRulesResult.getLayoutStructureRuleIdsMap(),
			layoutStructureRuleIdsMap);

		JSONArray jsonArray =
			_layoutStructureRulesHelper.processLayoutStructureRules(
				_group.getGroupId(), fieldValuesMap,
				layoutStructure.getLayoutStructureRules(), permissionChecker,
				new long[] {SegmentsEntryConstants.ID_DEFAULT});

		Map<String, List<String>> actualMap = new HashMap<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			List<String> list = actualMap.computeIfAbsent(
				jsonObject.getString("action"), k -> new ArrayList<>());

			list.add(jsonObject.getString("itemId"));
		}

		_assertMapEquals(map, actualMap);
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