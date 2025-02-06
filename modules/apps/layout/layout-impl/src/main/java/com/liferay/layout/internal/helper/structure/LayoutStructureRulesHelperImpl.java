/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.helper.structure;

import com.liferay.layout.helper.structure.LayoutStructureRulesHelper;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureRule;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		Map<String, List<String>> itemIdsMap = new HashMap<>();
		Map<String, List<String>> layoutStructureRuleIdsMap = new HashMap<>();
		LayoutStructureRulesContext layoutStructureRulesContext =
			new LayoutStructureRulesContext(
				groupId, permissionChecker, segmentsEntryIds);

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (LayoutStructureRule layoutStructureRule :
				layoutStructure.getLayoutStructureRules()) {

			List<String> itemIds = _getItemIds(layoutStructureRule);

			if (itemIds.isEmpty()) {
				_processActions(
					layoutStructureRule.getActionsJSONArray(), jsonArray,
					!_evaluateLayoutStructureRule(
						Collections.emptyMap(), layoutStructureRule,
						layoutStructureRulesContext));

				continue;
			}

			itemIds = ListUtil.filter(
				ListUtil.unique(itemIds),
				itemId ->
					layoutStructure.getLayoutStructureItem(itemId) != null);

			if (itemIds.isEmpty()) {
				continue;
			}

			layoutStructureRuleIdsMap.put(layoutStructureRule.getId(), itemIds);

			for (String itemId : itemIds) {
				List<String> layoutStructureRuleIds =
					itemIdsMap.computeIfAbsent(
						itemId, key -> new ArrayList<>());

				layoutStructureRuleIds.add(layoutStructureRule.getId());
			}
		}

		Set<String> displayedItemIds = new HashSet<>();
		Set<String> hiddenItemIds = new HashSet<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (Objects.equals(
					jsonObject.getString("action"), Action.SHOW.getValue())) {

				displayedItemIds.add(jsonObject.getString("itemId"));
			}
			else {
				hiddenItemIds.add(jsonObject.getString("itemId"));
			}
		}

		return new LayoutStructureRulesResult(
			displayedItemIds, hiddenItemIds, itemIdsMap,
			layoutStructureRuleIdsMap);
	}

	@Override
	public JSONArray processLayoutStructureRules(
		long groupId, Map<String, Object> fieldValuesMap,
		List<LayoutStructureRule> layoutStructureRules,
		PermissionChecker permissionChecker, long[] segmentsEntryIds) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		LayoutStructureRulesContext layoutStructureRulesContext =
			new LayoutStructureRulesContext(
				groupId, permissionChecker, segmentsEntryIds);

		for (LayoutStructureRule layoutStructureRule : layoutStructureRules) {
			_processActions(
				layoutStructureRule.getActionsJSONArray(), jsonArray,
				!_evaluateLayoutStructureRule(
					fieldValuesMap, layoutStructureRule,
					layoutStructureRulesContext));
		}

		return jsonArray;
	}

	private boolean _evaluateLayoutStructureRule(
		Map<String, Object> fieldValuesMap,
		LayoutStructureRule layoutStructureRule,
		LayoutStructureRulesContext layoutStructureRulesContext) {

		JSONArray conditionsJSONArray =
			layoutStructureRule.getConditionsJSONArray();

		for (int i = 0; i < conditionsJSONArray.length(); i++) {
			JSONObject conditionJSONObject = conditionsJSONArray.getJSONObject(
				i);

			if (_isConditionActive(
					conditionJSONObject, fieldValuesMap,
					layoutStructureRulesContext)) {

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

		if (Objects.equals(layoutStructureRule.getConditionType(), "any")) {
			return false;
		}

		return true;
	}

	private boolean _evaluateUserTypeCondition(
		String field, LayoutStructureRulesContext layoutStructureRulesContext,
		boolean negated, long value) {

		if (Objects.equals(field, "role")) {
			if (negated) {
				return !ArrayUtil.contains(
					layoutStructureRulesContext.getRoleIds(), value);
			}

			return ArrayUtil.contains(
				layoutStructureRulesContext.getRoleIds(), value);
		}

		if (Objects.equals(field, "segment")) {
			if (negated) {
				return !ArrayUtil.contains(
					layoutStructureRulesContext.getSegmentsEntryIds(), value);
			}

			return ArrayUtil.contains(
				layoutStructureRulesContext.getSegmentsEntryIds(), value);
		}

		if (Objects.equals(field, "user")) {
			if (negated) {
				return !Objects.equals(
					layoutStructureRulesContext.getUserId(), value);
			}

			return Objects.equals(
				layoutStructureRulesContext.getUserId(), value);
		}

		return false;
	}

	private Action _getAction(boolean negated, String type) {
		if (Objects.equals(type, "disable")) {
			if (negated) {
				return Action.ENABLE;
			}

			return Action.DISABLE;
		}
		else if (Objects.equals(type, "enable")) {
			if (negated) {
				return Action.DISABLE;
			}

			return Action.ENABLE;
		}
		else if (Objects.equals(type, "show")) {
			if (negated) {
				return Action.HIDE;
			}

			return Action.SHOW;
		}
		else if (Objects.equals(type, "hide")) {
			if (negated) {
				return Action.SHOW;
			}

			return Action.HIDE;
		}

		throw new IllegalArgumentException("Unknown action type: " + type);
	}

	private List<String> _getItemIds(LayoutStructureRule layoutStructureRule) {
		List<String> itemIds = new ArrayList<>();

		JSONArray conditionsJSONArray =
			layoutStructureRule.getConditionsJSONArray();

		for (int i = 0; i < conditionsJSONArray.length(); i++) {
			JSONObject conditionJSONObject = conditionsJSONArray.getJSONObject(
				i);

			if (Objects.equals(conditionJSONObject.getString("type"), "user")) {
				continue;
			}

			itemIds.add(conditionJSONObject.getString("field"));
		}

		return itemIds;
	}

	private boolean _isConditionActive(
		JSONObject conditionJSONObject, Map<String, Object> fieldValuesMap,
		LayoutStructureRulesContext layoutStructureRulesContext) {

		boolean negated = false;
		Object value = 0L;

		JSONObject optionsJSONObject = conditionJSONObject.getJSONObject(
			"options");

		if (optionsJSONObject != null) {
			if (Objects.equals(
					optionsJSONObject.getString("type"), "not-equal")) {

				negated = true;
			}

			value = optionsJSONObject.get("value");
		}

		if (Objects.equals(conditionJSONObject.getString("type"), "form")) {
			if (negated) {
				return !Objects.equals(
					fieldValuesMap.get(conditionJSONObject.getString("field")),
					value);
			}

			return Objects.equals(
				fieldValuesMap.get(conditionJSONObject.getString("field")),
				value);
		}

		if (Objects.equals(conditionJSONObject.getString("type"), "user")) {
			return _evaluateUserTypeCondition(
				conditionJSONObject.getString("field"),
				layoutStructureRulesContext, negated,
				GetterUtil.getLong(value));
		}

		return false;
	}

	private void _processActions(
		JSONArray actionsJSONArray, JSONArray jsonArray, boolean negated) {

		for (int i = 0; i < actionsJSONArray.length(); i++) {
			JSONObject actionsJSONObject = actionsJSONArray.getJSONObject(i);

			jsonArray.put(
				JSONUtil.put(
					"action",
					() -> {
						Action action = _getAction(
							negated, actionsJSONObject.getString("type"));

						return action.getValue();
					}
				).put(
					"itemId", actionsJSONObject.getString("itemId")
				));
		}
	}

	@Reference
	private JSONFactory _jsonFactory;

	private enum Action {

		DISABLE("disable"), ENABLE("enable"), HIDE("hide"), SHOW("show");

		public String getValue() {
			return _value;
		}

		private Action(String value) {
			_value = value;
		}

		private final String _value;

	}

	private class LayoutStructureRulesContext {

		public long getGroupId() {
			return _groupId;
		}

		public long[] getRoleIds() {
			if (_roleIds != null) {
				return _roleIds;
			}

			_roleIds = _permissionChecker.getRoleIds(
				_permissionChecker.getUserId(), _groupId);

			return _roleIds;
		}

		public long[] getSegmentsEntryIds() {
			return _segmentsEntryIds;
		}

		public long getUserId() {
			return _permissionChecker.getUserId();
		}

		private LayoutStructureRulesContext(
			long groupId, PermissionChecker permissionChecker,
			long[] segmentsEntryIds) {

			_groupId = groupId;
			_permissionChecker = permissionChecker;
			_segmentsEntryIds = segmentsEntryIds;
		}

		private final long _groupId;
		private final PermissionChecker _permissionChecker;
		private long[] _roleIds;
		private final long[] _segmentsEntryIds;

	}

}