/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.importer.structure.util;

import com.liferay.headless.delivery.dto.v1_0.PageRule;
import com.liferay.headless.delivery.dto.v1_0.PageRuleAction;
import com.liferay.headless.delivery.dto.v1_0.PageRuleCondition;
import com.liferay.layout.converter.ConditionTypeConverter;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureRule;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutStructureRuleImporter {

	public static LayoutStructureRule addLayoutStructureRule(
		LayoutStructure layoutStructure, PageRule pageRule) {

		LayoutStructureRule layoutStructureRule =
			layoutStructure.addLayoutStructureRule(
				pageRule.getId(), pageRule.getName());

		PageRule.ConditionType conditionType = pageRule.getConditionType();

		if (conditionType == null) {
			conditionType = PageRule.ConditionType.ALL;
		}

		layoutStructureRule.setActionsJSONArray(
			_toJSONArray(pageRule.getPageRuleActions()));
		layoutStructureRule.setConditionsJSONArray(
			_toJSONArray(pageRule.getPageRuleConditions()));

		layoutStructureRule.setConditionType(
			ConditionTypeConverter.convertToInternalValue(
				conditionType.getValue()));

		return layoutStructureRule;
	}

	private static JSONArray _toJSONArray(PageRuleAction[] pageRuleActions) {
		return JSONUtil.toJSONArray(
			pageRuleActions,
			pageRuleAction -> JSONUtil.put(
				"action", pageRuleAction.getAction()
			).put(
				"id", pageRuleAction.getId()
			).put(
				"itemId", pageRuleAction.getItemId()
			).put(
				"type", pageRuleAction.getType()
			),
			exception -> {
				if (_log.isWarnEnabled()) {
					_log.warn(exception, exception);
				}
			});
	}

	private static JSONArray _toJSONArray(
		PageRuleCondition[] pageRuleConditions) {

		return JSONUtil.toJSONArray(
			pageRuleConditions,
			pageRuleCondition -> JSONUtil.put(
				"condition", pageRuleCondition.getCondition()
			).put(
				"id", pageRuleCondition.getId()
			).put(
				"type", pageRuleCondition.getType()
			).put(
				"value", pageRuleCondition.getValue()
			),
			exception -> {
				if (_log.isWarnEnabled()) {
					_log.warn(exception, exception);
				}
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutStructureRuleImporter.class);

}