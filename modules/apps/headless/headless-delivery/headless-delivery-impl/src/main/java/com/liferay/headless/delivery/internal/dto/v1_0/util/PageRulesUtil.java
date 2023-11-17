/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.PageRule;
import com.liferay.headless.delivery.dto.v1_0.PageRuleAction;
import com.liferay.headless.delivery.dto.v1_0.PageRuleCondition;
import com.liferay.layout.converter.ConditionTypeConverter;
import com.liferay.layout.util.structure.LayoutStructureRule;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Lourdes Fernández Besada
 */
public class PageRulesUtil {

	public static PageRuleAction toPageRuleAction(JSONObject jsonObject) {
		return new PageRuleAction() {
			{
				action = jsonObject.getString("action");
				id = jsonObject.getString("id");
				itemId = jsonObject.getString("itemId");
				type = jsonObject.getString("type");
			}
		};
	}

	public static PageRuleCondition toPageRuleCondition(JSONObject jsonObject) {
		return new PageRuleCondition() {
			{
				condition = jsonObject.getString("condition");
				id = jsonObject.getString("id");
				type = jsonObject.getString("type");
				value = jsonObject.getString("value");
			}
		};
	}

	public static PageRule[] toPageRules(
		List<LayoutStructureRule> layoutStructureRules) {

		if (ListUtil.isEmpty(layoutStructureRules)) {
			return null;
		}

		return TransformUtil.transformToArray(
			layoutStructureRules,
			layoutStructureRule -> new PageRule() {
				{
					conditionType = ConditionType.create(
						ConditionTypeConverter.convertToExternalValue(
							layoutStructureRule.getConditionType()));
					id = layoutStructureRule.getId();
					name = layoutStructureRule.getName();
					pageRuleActions = JSONUtil.toArray(
						layoutStructureRule.getActionsJSONArray(),
						jsonObject -> toPageRuleAction(jsonObject),
						exception -> {
							if (_log.isWarnEnabled()) {
								_log.warn(exception);
							}
						},
						PageRuleAction.class);
					pageRuleConditions = JSONUtil.toArray(
						layoutStructureRule.getConditionsJSONArray(),
						jsonObject -> toPageRuleCondition(jsonObject),
						exception -> {
							if (_log.isWarnEnabled()) {
								_log.warn(exception);
							}
						},
						PageRuleCondition.class);
				}
			},
			PageRule.class);
	}

	private static final Log _log = LogFactoryUtil.getLog(PageRulesUtil.class);

}