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

package com.liferay.dynamic.data.mapping.form.builder.internal.converter.model;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Marcellus Tavares
 */
public class DDMFormRule {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormRule)) {
			return false;
		}

		DDMFormRule ddmFormRule = (DDMFormRule)obj;

		if (Objects.equals(
				_ddmFormRuleActions, ddmFormRule._ddmFormRuleActions) &&
			Objects.equals(
				_ddmFormRuleConditions, ddmFormRule._ddmFormRuleConditions) &&
			Objects.equals(_logicalOperator, ddmFormRule._logicalOperator)) {

			return true;
		}

		return false;
	}

	@JSON(name = "actions")
	public List<DDMFormRuleAction> getDDMFormRuleActions() {
		return _ddmFormRuleActions;
	}

	@JSON(name = "conditions")
	public List<DDMFormRuleCondition> getDDMFormRuleConditions() {
		return _ddmFormRuleConditions;
	}

	@JSON(name = "logical-operator")
	public String getLogicalOperator() {
		return _logicalOperator;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _ddmFormRuleActions);

		hash = HashUtil.hash(hash, _ddmFormRuleConditions);

		return HashUtil.hash(hash, _logicalOperator);
	}

	public void setDDMFormRuleActions(
		List<DDMFormRuleAction> ddmFormRuleActions) {

		_ddmFormRuleActions = ddmFormRuleActions;
	}

	public void setDDMFormRuleConditions(
		List<DDMFormRuleCondition> ddmFormRuleConditions) {

		_ddmFormRuleConditions = ddmFormRuleConditions;
	}

	public void setLogicalOperator(String logicalOperator) {
		_logicalOperator = logicalOperator;
	}

	private List<DDMFormRuleAction> _ddmFormRuleActions = new ArrayList<>();
	private List<DDMFormRuleCondition> _ddmFormRuleConditions =
		new ArrayList<>();
	private String _logicalOperator = "AND";

}