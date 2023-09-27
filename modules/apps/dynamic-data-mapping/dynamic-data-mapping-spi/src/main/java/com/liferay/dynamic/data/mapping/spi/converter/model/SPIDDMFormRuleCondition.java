/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.spi.converter.model;

import com.liferay.petra.lang.HashUtil;

import java.util.List;
import java.util.Objects;

/**
 * @author Marcellus Tavares
 */
public class SPIDDMFormRuleCondition {

	public SPIDDMFormRuleCondition() {
	}

	public SPIDDMFormRuleCondition(String operator, List<Operand> operands) {
		_operator = operator;
		_operands = operands;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SPIDDMFormRuleCondition)) {
			return false;
		}

		SPIDDMFormRuleCondition spiDDMFormRuleCondition =
			(SPIDDMFormRuleCondition)object;

		if (Objects.equals(_operands, spiDDMFormRuleCondition._operands) &&
			Objects.equals(_operator, spiDDMFormRuleCondition._operator)) {

			return true;
		}

		return false;
	}

	public List<Operand> getOperands() {
		return _operands;
	}

	public String getOperator() {
		return _operator;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _operands);

		return HashUtil.hash(hash, _operator);
	}

	public void setOperands(List<Operand> operands) {
		_operands = operands;
	}

	public void setOperator(String operator) {
		_operator = operator;
	}

	public static class Operand {

		public Operand() {
		}

		public Operand(String type, String value) {
			_type = type;
			_value = value;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof Operand)) {
				return false;
			}

			Operand operand = (Operand)object;

			if (Objects.equals(_type, operand._type) &&
				Objects.equals(_value, operand._value)) {

				return true;
			}

			return false;
		}

		public String getType() {
			return _type;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _type);

			return HashUtil.hash(hash, _value);
		}

		public void setType(String type) {
			_type = type;
		}

		public void setValue(String value) {
			_value = value;
		}

		private String _type;
		private String _value;

	}

	private List<Operand> _operands;
	private String _operator;

}