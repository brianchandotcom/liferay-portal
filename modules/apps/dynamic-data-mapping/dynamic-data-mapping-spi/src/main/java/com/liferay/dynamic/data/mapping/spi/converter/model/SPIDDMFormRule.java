/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SPIDDMFormRule)) {
			return false;
		}

		SPIDDMFormRule spiDDMFormRule = (SPIDDMFormRule)object;

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