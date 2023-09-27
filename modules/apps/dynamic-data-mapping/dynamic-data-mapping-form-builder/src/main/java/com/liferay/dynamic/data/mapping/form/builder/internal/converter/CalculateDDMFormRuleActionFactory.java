/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.internal.converter;

import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.action.CalculateDDMFormRuleAction;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.visitor.ActionExpressionVisitor;
import com.liferay.dynamic.data.mapping.spi.converter.model.SPIDDMFormRuleAction;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

/**
 * @author Leonardo Barros
 */
public class CalculateDDMFormRuleActionFactory {

	public static SPIDDMFormRuleAction create(
		List<Expression> expressions,
		ActionExpressionVisitor actionExpressionVisitor) {

		String target = actionExpressionVisitor.doVisit(expressions.get(0));

		String expressionString = String.valueOf(expressions.get(1));

		expressionString = expressionString.replaceAll(
			"(getValue\\(\\'([^\\(]+)\\'\\))", "[$2]");

		expressionString = StringUtil.removeChar(
			expressionString, CharPool.SPACE);

		return new CalculateDDMFormRuleAction(target, expressionString);
	}

}