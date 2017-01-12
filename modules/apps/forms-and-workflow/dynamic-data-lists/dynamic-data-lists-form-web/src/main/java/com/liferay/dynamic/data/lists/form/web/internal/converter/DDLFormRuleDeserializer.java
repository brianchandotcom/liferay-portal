/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dynamic.data.lists.form.web.internal.converter;

import com.liferay.dynamic.data.lists.form.web.internal.converter.model.DDLFormRule;
import com.liferay.dynamic.data.lists.form.web.internal.converter.model.DDLFormRuleAction;
import com.liferay.dynamic.data.lists.form.web.internal.converter.model.DDLFormRuleCondition;
import com.liferay.dynamic.data.lists.form.web.internal.converter.model.action.AutoFillDDLFormRuleAction;
import com.liferay.dynamic.data.lists.form.web.internal.converter.model.action.DefaultDDLFormRuleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = DDLFormRuleDeserializer.class)
public class DDLFormRuleDeserializer {

	public List<DDLFormRule> deserialize(String rules) throws PortalException {
		JSONArray rulesJSONArray = _jsonFactory.createJSONArray(rules);

		List<DDLFormRule> ddlFormRules = new ArrayList<>(
			rulesJSONArray.length());

		for (int i = 0; i < rulesJSONArray.length(); i++) {
			JSONObject ruleJSONObject = rulesJSONArray.getJSONObject(i);

			ddlFormRules.add(deserializeDDLFormRule(ruleJSONObject));
		}

		return ddlFormRules;
	}

	protected DDLFormRule deserializeDDLFormRule(JSONObject ruleJSONObject) {
		DDLFormRule ddlFormRule = new DDLFormRule();

		ddlFormRule.setDDLFormRuleActions(
			_ddlFormRuleActionDeserializerHelper.deserialize(
				ruleJSONObject.getJSONArray("actions")));

		ddlFormRule.setDDLFormRuleConditions(
			deserializeDDLFormRuleConditions(
				ruleJSONObject.getJSONArray("conditions")));

		ddlFormRule.setLogicalOperator(
			ruleJSONObject.getString("logical-operator"));

		return ddlFormRule;
	}

	protected List<DDLFormRuleCondition> deserializeDDLFormRuleConditions(
		JSONArray conditionsJSONArray) {

		JSONDeserializer<DDLFormRuleCondition[]> jsonDeserializer =
			_jsonFactory.createJSONDeserializer();

		DDLFormRuleCondition[] ruleConditions = jsonDeserializer.deserialize(
			conditionsJSONArray.toJSONString(), DDLFormRuleCondition[].class);

		return ListUtil.toList(ruleConditions);
	}

	private final DDLFormRuleActionDeserializerHelper
		_ddlFormRuleActionDeserializerHelper =
			new DDLFormRuleActionDeserializerHelper();

	@Reference
	private JSONFactory _jsonFactory;

	private class DDLFormRuleActionDeserializerHelper {

		protected List<DDLFormRuleAction> deserialize(
			JSONArray actionsJSONArray) {
			List<DDLFormRuleAction> ddlFormRuleActions = new ArrayList<>();

			for (int i = 0; i < actionsJSONArray.length(); i++) {
				JSONObject actionJSONObject = actionsJSONArray.getJSONObject(i);

				String action = actionJSONObject.getString("action");

				ddlFormRuleActions.add(
					deserialize(actionJSONObject, getRuleActionClass(action)));
			}

			return ddlFormRuleActions;
		}

		protected <T extends DDLFormRuleAction> DDLFormRuleAction deserialize(
			JSONObject actionJSONObject, Class<T> targetClass) {

			JSONDeserializer<T> jsonDeserializer =
				_jsonFactory.createJSONDeserializer();

			return jsonDeserializer.deserialize(
				actionJSONObject.toJSONString(), targetClass);
		}

		protected Class<? extends DDLFormRuleAction> getRuleActionClass(
			String action) {

			if (_actionNameActionClassMap.containsKey(action)) {
				return _actionNameActionClassMap.get(action);
			}

			return DefaultDDLFormRuleAction.class;
		}

		private final Map<String, Class<? extends DDLFormRuleAction>>
			_actionNameActionClassMap = new HashMap<>();

		{
			_actionNameActionClassMap.put(
				"auto-fill", AutoFillDDLFormRuleAction.class);
		}

	}

}