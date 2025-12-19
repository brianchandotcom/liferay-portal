/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.helper.structure;

import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureRule;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Víctor Galán
 */
public interface LayoutStructureRulesHelper {

	public LayoutStructureRulesResult processLayoutStructureRules(
		long groupId, Map<String, Object> infoItemFieldValuesMap,
		LayoutStructure layoutStructure, PermissionChecker permissionChecker,
		long[] segmentsEntryIds);

	public JSONArray processLayoutStructureRules(
		long groupId, Map<String, Object> fieldValuesMap,
		List<LayoutStructureRule> layoutStructureRules,
		PermissionChecker permissionChecker, long[] segmentsEntryIds);

	public static class LayoutStructureRulesResult {

		public LayoutStructureRulesResult(
			Set<String> disabledItemIds, Set<String> displayedItemIds,
			Set<String> enabledItemIds, Set<String> hiddenItemIds,
			Map<String, List<String>> itemIdsMap,
			Map<String, List<String>> layoutStructureRuleIdsMap) {

			_disabledItemIds = disabledItemIds;
			_displayedItemIds = displayedItemIds;
			_enabledItemIds = enabledItemIds;
			_hiddenItemIds = hiddenItemIds;
			_itemIdsMap = itemIdsMap;
			_layoutStructureRuleIdsMap = layoutStructureRuleIdsMap;
		}

		public Set<String> getDisabledItemIds() {
			return _disabledItemIds;
		}

		public Set<String> getDisplayedItemIds() {
			return _displayedItemIds;
		}

		public Set<String> getEnabledItemIds() {
			return _enabledItemIds;
		}

		public Set<String> getHiddenItemIds() {
			return _hiddenItemIds;
		}

		public Map<String, List<String>> getItemIdsMap() {
			return _itemIdsMap;
		}

		public Map<String, List<String>> getLayoutStructureRuleIdsMap() {
			return _layoutStructureRuleIdsMap;
		}

		private final Set<String> _disabledItemIds;
		private final Set<String> _displayedItemIds;
		private final Set<String> _enabledItemIds;
		private final Set<String> _hiddenItemIds;
		private final Map<String, List<String>> _itemIdsMap;
		private final Map<String, List<String>> _layoutStructureRuleIdsMap;

	}

}