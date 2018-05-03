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

package com.liferay.dynamic.data.mapping.form.builder.internal.converter;

import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.DDMFormRuleAction;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.visitor.ActionExpressionVisitor;

import java.util.List;

/**
 * @author Leonardo Barros
 */
public class DDMFormRuleActionFactory {

	public static DDMFormRuleAction create(
		String action, List<Expression> expressions,
		ActionExpressionVisitor actionExpressionVisitor) {

		if (action.equals("auto-fill")) {
			return AutoFillDDMFormRuleActionFactory.create(
				expressions, actionExpressionVisitor);
		}
		else if (action.equals("calculate")) {
			return CalculateDDMFormRuleActionFactory.create(
				expressions, actionExpressionVisitor);
		}
		else if (action.equals("jump-to-page")) {
			return JumpToPageDDMFormRuleActionFactory.create(
				expressions, actionExpressionVisitor);
		}

		return DefaultDDMFormRuleActionFactory.create(
			action, expressions, actionExpressionVisitor);
	}

}