/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.helper.structure;

import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Set;

/**
 * @author Víctor Galán
 */
public interface LayoutStructureRulesHelper {

	public LayoutStructureRulesResult processLayoutStructureRules(
		long groupId, LayoutStructure layoutStructure,
		PermissionChecker permissionChecker, long[] segmentsEntryIds);

	public static class LayoutStructureRulesResult {

		public LayoutStructureRulesResult(
			Set<String> displayedItemIds, Set<String> hiddenItemIds) {

			_displayedItemIds = displayedItemIds;
			_hiddenItemIds = hiddenItemIds;
		}

		public Set<String> getDisplayedItemIds() {
			return _displayedItemIds;
		}

		public Set<String> getHiddenItemIds() {
			return _hiddenItemIds;
		}

		private final Set<String> _displayedItemIds;
		private final Set<String> _hiddenItemIds;

	}

}