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
		long groupId, LayoutStructure layoutStructure,
		PermissionChecker permissionChecker, long[] segmentsEntryIds);

	public JSONArray processLayoutStructureRules(
		long groupId, Map<String, Object> fieldValuesMap,
		List<LayoutStructureRule> layoutStructureRules,
		PermissionChecker permissionChecker, long[] segmentsEntryIds);

	public static class LayoutStructureRulesResult {

		public LayoutStructureRulesResult(
			Set<String> displayedItemIds, Set<String> hiddenItemIds,
			Map<String, List<String>> itemIdMap,
			Map<String, List<String>> ruleIdMap) {

			_displayedItemIds = displayedItemIds;
			_hiddenItemIds = hiddenItemIds;
			_itemIdMap = itemIdMap;
			_ruleIdMap = ruleIdMap;
		}

		public Set<String> getDisplayedItemIds() {
			return _displayedItemIds;
		}

		public Set<String> getHiddenItemIds() {
			return _hiddenItemIds;
		}

		public Map<String, List<String>> getItemIdMap() {
			return _itemIdMap;
		}

		public Map<String, List<String>> getRuleIdMap() {
			return _ruleIdMap;
		}

		private final Set<String> _displayedItemIds;
		private final Set<String> _hiddenItemIds;
		private final Map<String, List<String>> _itemIdMap;
		private final Map<String, List<String>> _ruleIdMap;

	}

}