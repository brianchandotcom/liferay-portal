/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.helper.structure;

import com.liferay.layout.helper.structure.LayoutStructureRulesHelper;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureRule;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Víctor Galán
 */
@Component(service = LayoutStructureRulesHelper.class)
public class LayoutStructureRulesHelperImpl
	implements LayoutStructureRulesHelper {

	@Override
	public LayoutStructureRulesResult processLayoutStructureRules(
		long groupId, LayoutStructure layoutStructure,
		PermissionChecker permissionChecker, long[] segmentsEntryIds) {

		Set<String> displayedItemIds = new HashSet<>();
		Set<String> hiddenItemIds = new HashSet<>();

		List<LayoutStructureRule> layoutStructureRules =
			layoutStructure.getLayoutStructureRules();

		for (LayoutStructureRule layoutStructureRule : layoutStructureRules) {
			if (_isLayoutStructureRuleActive(
					groupId, layoutStructureRule, permissionChecker,
					segmentsEntryIds)) {

				_processActions(
					layoutStructureRule.getActionsJSONArray(), displayedItemIds,
					hiddenItemIds);
			}
		}

		return new LayoutStructureRulesResult(displayedItemIds, hiddenItemIds);
	}

	private boolean _isConditionActive(
		long groupId, JSONObject conditionJSONObject,
		PermissionChecker permissionChecker, long[] segmentsEntryIds) {

		long value = conditionJSONObject.getLong("value");

		if (Objects.equals(
				conditionJSONObject.getString("condition"), "role") &&
			ArrayUtil.contains(
				permissionChecker.getRoleIds(
					permissionChecker.getUserId(), groupId),
				value)) {

			return true;
		}

		if (Objects.equals(
				conditionJSONObject.getString("condition"), "segment") &&
			ArrayUtil.contains(segmentsEntryIds, value)) {

			return true;
		}

		if (Objects.equals(
				conditionJSONObject.getString("condition"), "user") &&
			Objects.equals(permissionChecker.getUserId(), value)) {

			return true;
		}

		return false;
	}

	private boolean _isLayoutStructureRuleActive(
		long groupId, LayoutStructureRule layoutStructureRule,
		PermissionChecker permissionChecker, long[] segmentsEntryIds) {

		JSONArray conditionsJSONArray =
			layoutStructureRule.getConditionsJSONArray();

		for (int i = 0; i < conditionsJSONArray.length(); i++) {
			JSONObject conditionJSONObject = conditionsJSONArray.getJSONObject(
				i);

			boolean conditionActive = _isConditionActive(
				groupId, conditionJSONObject, permissionChecker,
				segmentsEntryIds);

			if (conditionActive) {
				if (Objects.equals(
						layoutStructureRule.getConditionType(), "any")) {

					return true;
				}
			}
			else if (Objects.equals(
						layoutStructureRule.getConditionType(), "all")) {

				return false;
			}
		}

		return true;
	}

	private void _processActions(
		JSONArray actionsJSONArray, Set<String> displayedItemIds,
		Set<String> hiddenItemIds) {

		for (int i = 0; i < actionsJSONArray.length(); i++) {
			JSONObject actionsJSONObject = actionsJSONArray.getJSONObject(i);

			if (Objects.equals(actionsJSONObject.getString("type"), "show")) {
				displayedItemIds.add(actionsJSONObject.getString("itemId"));
			}
			else {
				hiddenItemIds.add(actionsJSONObject.getString("itemId"));
			}
		}
	}

}