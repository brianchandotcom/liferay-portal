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

import com.liferay.dynamic.data.lists.form.web.internal.converter.DDMFormRuleToDDLFormRuleConverter.ActionExpressionVisitor;
import com.liferay.dynamic.data.lists.form.web.internal.converter.model.DDLFormRuleAction;
import com.liferay.dynamic.data.lists.form.web.internal.converter.model.action.CalculateDDLFormRuleAction;
import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

/**
 * @author Leonardo Barros
 */
public class CalculateDDLFormRuleActionFactory {

	public static DDLFormRuleAction create(
		List<Expression> expressions,
		ActionExpressionVisitor actionExpressionVisitor) {

		String target = actionExpressionVisitor.doVisit(expressions.get(0));

		Expression expression = expressions.get(1);

		String expressionString = expression.toString();

		expressionString = expressionString.replaceAll(
			"(getValue\\(\\'([^\\(]+)\\'\\))", "[$2]");

		expressionString = StringUtil.removeChar(
			expressionString, CharPool.SPACE);

		return new CalculateDDLFormRuleAction(target, expressionString);
	}

}