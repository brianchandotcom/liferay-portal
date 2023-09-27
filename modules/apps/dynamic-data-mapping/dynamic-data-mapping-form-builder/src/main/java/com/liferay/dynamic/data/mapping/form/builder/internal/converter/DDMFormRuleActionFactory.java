/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.internal.converter;

import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.visitor.ActionExpressionVisitor;
import com.liferay.dynamic.data.mapping.spi.converter.model.SPIDDMFormRuleAction;

import java.util.List;

/**
 * @author Leonardo Barros
 */
public class DDMFormRuleActionFactory {

	public static SPIDDMFormRuleAction create(
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