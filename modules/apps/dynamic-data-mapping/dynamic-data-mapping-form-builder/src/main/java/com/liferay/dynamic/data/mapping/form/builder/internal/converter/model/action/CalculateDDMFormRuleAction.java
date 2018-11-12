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

package com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.action;

import com.liferay.dynamic.data.mapping.form.builder.internal.converter.serializer.CalculateDDMFormRuleActionSerializer;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.serializer.DDMFormRuleActionSerializer;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.serializer.DDMFormRuleSerializerContext;
import com.liferay.petra.lang.HashUtil;

import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class CalculateDDMFormRuleAction extends DefaultDDMFormRuleAction {

	public CalculateDDMFormRuleAction() {
	}

	public CalculateDDMFormRuleAction(String target, String expression) {
		super("calculate", target);

		_expression = expression;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CalculateDDMFormRuleAction)) {
			return false;
		}

		CalculateDDMFormRuleAction calculateDDMFormRuleAction =
			(CalculateDDMFormRuleAction)obj;

		if (super.equals(obj) &&
			Objects.equals(
				_expression, calculateDDMFormRuleAction._expression)) {

			return true;
		}

		return false;
	}

	public String getExpression() {
		return _expression;
	}

	@Override
	public int hashCode() {
		int hash = super.hashCode();

		return HashUtil.hash(hash, _expression);
	}

	@Override
	public String serialize(
		DDMFormRuleSerializerContext ddmFormRuleSerializerContext) {

		DDMFormRuleActionSerializer ddmFormRuleActionSerializer =
			new CalculateDDMFormRuleActionSerializer(this);

		return ddmFormRuleActionSerializer.serialize(
			ddmFormRuleSerializerContext);
	}

	public void setExpression(String expression) {
		_expression = expression;
	}

	private String _expression;

}