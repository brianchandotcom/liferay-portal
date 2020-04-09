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

package com.liferay.dynamic.data.mapping.spi.converter.model;

import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Marcellus Tavares
 */
public class SPIDDMFormRule {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SPIDDMFormRule)) {
			return false;
		}

		SPIDDMFormRule spiDDMFormRule = (SPIDDMFormRule)obj;

		if (Objects.equals(_logicalOperator, spiDDMFormRule._logicalOperator) &&
			Objects.equals(_name, spiDDMFormRule._name) &&
			Objects.equals(
				_spiDDMFormRuleActions,
				spiDDMFormRule._spiDDMFormRuleActions) &&
			Objects.equals(
				_spiDDMFormRuleConditions,
				spiDDMFormRule._spiDDMFormRuleConditions)) {

			return true;
		}

		return false;
	}

	@JSON(name = "logical-operator")
	public String getLogicalOperator() {
		return _logicalOperator;
	}

	@JSON(name = "name")
	public LocalizedValue getName() {
		return _name;
	}

	@JSON(name = "actions")
	public List<SPIDDMFormRuleAction> getSPIDDMFormRuleActions() {
		return _spiDDMFormRuleActions;
	}

	@JSON(name = "conditions")
	public List<SPIDDMFormRuleCondition> getSPIDDMFormRuleConditions() {
		return _spiDDMFormRuleConditions;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _logicalOperator);

		hash = HashUtil.hash(hash, _name);
		hash = HashUtil.hash(hash, _spiDDMFormRuleActions);

		return HashUtil.hash(hash, _spiDDMFormRuleConditions);
	}

	public void setLogicalOperator(String logicalOperator) {
		_logicalOperator = logicalOperator;
	}

	public void setName(LocalizedValue name) {
		_name = name;
	}

	public void setSPIDDMFormRuleActions(
		List<SPIDDMFormRuleAction> spiDDMFormRuleActions) {

		_spiDDMFormRuleActions = spiDDMFormRuleActions;
	}

	public void setSPIDDMFormRuleConditions(
		List<SPIDDMFormRuleCondition> spiDDMFormRuleConditions) {

		_spiDDMFormRuleConditions = spiDDMFormRuleConditions;
	}

	private String _logicalOperator = "AND";
	private LocalizedValue _name;
	private List<SPIDDMFormRuleAction> _spiDDMFormRuleActions =
		new ArrayList<>();
	private List<SPIDDMFormRuleCondition> _spiDDMFormRuleConditions =
		new ArrayList<>();

}