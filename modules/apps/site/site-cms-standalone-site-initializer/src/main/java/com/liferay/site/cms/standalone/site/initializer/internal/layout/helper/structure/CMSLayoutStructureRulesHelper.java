/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.standalone.site.initializer.internal.layout.helper.structure;

import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.layout.helper.structure.LayoutStructureRulesHelper;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureRule;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "service.ranking:Integer=100",
	service = LayoutStructureRulesHelper.class
)
public class CMSLayoutStructureRulesHelper
	implements LayoutStructureRulesHelper {

	@Override
	public LayoutStructureRulesResult processLayoutStructureRules(
		long groupId, InfoItemFieldValues infoItemFieldValues,
		LayoutStructure layoutStructure, Locale locale,
		PermissionChecker permissionChecker, long[] segmentsEntryIds) {

		LayoutStructureRulesResult layoutStructureRulesResult =
			_layoutStructureRulesHelper.processLayoutStructureRules(
				groupId, infoItemFieldValues, layoutStructure, locale,
				permissionChecker, segmentsEntryIds);

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				_VIEW_SPACE_SITES_SUMMARY_JSP_SECTION_FRAGMENT_RENDERER_ITEM_ID);

		if (layoutStructureItem == null) {
			return layoutStructureRulesResult;
		}

		Set<String> hiddenItemIds = new HashSet<>(
			layoutStructureRulesResult.getHiddenItemIds());

		hiddenItemIds.add(
			_VIEW_SPACE_SITES_SUMMARY_JSP_SECTION_FRAGMENT_RENDERER_ITEM_ID);

		return new LayoutStructureRulesResult(
			layoutStructureRulesResult.getDisabledItemIds(),
			layoutStructureRulesResult.getDisplayedItemIds(),
			layoutStructureRulesResult.getEnabledItemIds(), hiddenItemIds,
			layoutStructureRulesResult.getItemIdsMap(),
			layoutStructureRulesResult.getLayoutStructureRuleIdsMap());
	}

	@Override
	public JSONArray processLayoutStructureRules(
		long groupId, Map<String, Object> fieldValuesMap,
		List<LayoutStructureRule> layoutStructureRules,
		PermissionChecker permissionChecker, long[] segmentsEntryIds) {

		return _layoutStructureRulesHelper.processLayoutStructureRules(
			groupId, fieldValuesMap, layoutStructureRules, permissionChecker,
			segmentsEntryIds);
	}

	private static final String
		_VIEW_SPACE_SITES_SUMMARY_JSP_SECTION_FRAGMENT_RENDERER_ITEM_ID =
			"005a5946-a89c-8c17-5407-5e5a0a86f9b5";

	@Reference(
		target = "(component.name=com.liferay.layout.internal.helper.structure.LayoutStructureRulesHelperImpl)"
	)
	private LayoutStructureRulesHelper _layoutStructureRulesHelper;

}