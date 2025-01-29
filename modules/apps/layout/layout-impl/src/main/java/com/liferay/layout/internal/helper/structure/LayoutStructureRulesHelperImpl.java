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
		LayoutStructureRulesContext layoutStructureRulesContext =
			new LayoutStructureRulesContext(
				groupId, permissionChecker, segmentsEntryIds);

		for (LayoutStructureRule layoutStructureRule :
				layoutStructure.getLayoutStructureRules()) {

			if (!_isActiveLayoutStructureRule(layoutStructureRule)) {
				continue;
			}

			_processActions(
				layoutStructureRule.getActionsJSONArray(), displayedItemIds,
				hiddenItemIds,
				!_evaluateLayoutStructureRule(
					layoutStructureRule, layoutStructureRulesContext));
		}

		return new LayoutStructureRulesResult(displayedItemIds, hiddenItemIds);
	}

	private boolean _evaluateLayoutStructureRule(
		LayoutStructureRule layoutStructureRule,
		LayoutStructureRulesContext layoutStructureRulesContext) {

		JSONArray conditionsJSONArray =
			layoutStructureRule.getConditionsJSONArray();

		for (int i = 0; i < conditionsJSONArray.length(); i++) {
			JSONObject conditionJSONObject = conditionsJSONArray.getJSONObject(
				i);

			if (_isConditionActive(
					conditionJSONObject, layoutStructureRulesContext)) {

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

	private boolean _isActiveLayoutStructureRule(
		LayoutStructureRule layoutStructureRule) {

		JSONArray conditionsJSONArray =
			layoutStructureRule.getConditionsJSONArray();

		for (int i = 0; i < conditionsJSONArray.length(); i++) {
			JSONObject conditionJSONObject = conditionsJSONArray.getJSONObject(
				i);

			if (!Objects.equals(
					conditionJSONObject.getString("type"), "user")) {

				return false;
			}
		}

		return true;
	}

	private boolean _isConditionActive(
		JSONObject conditionJSONObject,
		LayoutStructureRulesContext layoutStructureRulesContext) {

		boolean negated = false;
		long value = 0L;

		JSONObject optionsJSONObject = conditionJSONObject.getJSONObject(
			"options");

		if (optionsJSONObject != null) {
			if (Objects.equals(
					optionsJSONObject.getString("type"), "not-equal")) {

				negated = true;
			}

			value = optionsJSONObject.getLong("value");
		}

		if (Objects.equals(conditionJSONObject.getString("field"), "role")) {
			if (negated) {
				return !ArrayUtil.contains(
					layoutStructureRulesContext.getRoleIds(), value);
			}

			return ArrayUtil.contains(
				layoutStructureRulesContext.getRoleIds(), value);
		}

		if (Objects.equals(conditionJSONObject.getString("field"), "segment")) {
			if (negated) {
				return !ArrayUtil.contains(
					layoutStructureRulesContext.getSegmentsEntryIds(), value);
			}

			return ArrayUtil.contains(
				layoutStructureRulesContext.getSegmentsEntryIds(), value);
		}

		if (Objects.equals(conditionJSONObject.getString("field"), "user")) {
			if (negated) {
				return !Objects.equals(
					layoutStructureRulesContext.getUserId(), value);
			}

			return Objects.equals(
				layoutStructureRulesContext.getUserId(), value);
		}

		return false;
	}

	private void _processActions(
		JSONArray actionsJSONArray, Set<String> displayedItemIds,
		Set<String> hiddenItemIds, boolean negated) {

		for (int i = 0; i < actionsJSONArray.length(); i++) {
			JSONObject actionsJSONObject = actionsJSONArray.getJSONObject(i);

			if (Objects.equals(
					_getAction(negated, actionsJSONObject.getString("type")),
					Action.SHOW)) {

				displayedItemIds.add(actionsJSONObject.getString("itemId"));
			}
			else {
				hiddenItemIds.add(actionsJSONObject.getString("itemId"));
			}
		}
	}

	private enum Action {

		DISABLE, ENABLE, HIDE, SHOW

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