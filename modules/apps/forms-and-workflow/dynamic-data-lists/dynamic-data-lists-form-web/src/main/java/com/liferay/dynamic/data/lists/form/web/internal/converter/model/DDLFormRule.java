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

package com.liferay.dynamic.data.lists.form.web.internal.converter.model;

import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.HashUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Marcellus Tavares
 */
public class DDLFormRule {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDLFormRule)) {
			return false;
		}

		DDLFormRule ddlFormRule = (DDLFormRule)obj;

		if (Objects.equals(
				_ddlFormRuleActions, ddlFormRule._ddlFormRuleActions) &&
			Objects.equals(
				_ddlFormRuleConditions, ddlFormRule._ddlFormRuleConditions) &&
			Objects.equals(
				_logicalOperator, ddlFormRule._logicalOperator)) {

			return true;
		}

		return false;
	}

	@JSON(name = "actions")
	public List<DDLFormRuleAction> getDDLFormRuleActions() {
		return _ddlFormRuleActions;
	}

	@JSON(name = "conditions")
	public List<DDLFormRuleCondition> getDDLFormRuleConditions() {
		return _ddlFormRuleConditions;
	}

	@JSON(name = "logical-operator")
	public String getLogicalOperator() {
		return _logicalOperator;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _ddlFormRuleActions);

		hash = HashUtil.hash(hash, _ddlFormRuleConditions);

		return HashUtil.hash(hash, _logicalOperator);
	}

	public void setDDLFormRuleActions(
		List<DDLFormRuleAction> ddlFormRuleActions) {

		_ddlFormRuleActions = ddlFormRuleActions;
	}

	public void setDDLFormRuleConditions(
		List<DDLFormRuleCondition> ddlFormRuleConditions) {

		_ddlFormRuleConditions = ddlFormRuleConditions;
	}

	public void setLogicalOperator(String logicalOperator) {
		_logicalOperator = logicalOperator;
	}

	private List<DDLFormRuleAction> _ddlFormRuleActions = new ArrayList<>();
	private List<DDLFormRuleCondition> _ddlFormRuleConditions =
		new ArrayList<>();
	private String _logicalOperator = "AND";

}