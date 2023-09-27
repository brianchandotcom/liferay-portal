/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.internal.converter;

import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.action.DefaultDDMFormRuleAction;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.visitor.ActionExpressionVisitor;
import com.liferay.dynamic.data.mapping.spi.converter.model.SPIDDMFormRuleAction;

import java.util.List;

/**
 * @author Leonardo Barros
 */
public class DefaultDDMFormRuleActionFactory {

	public static SPIDDMFormRuleAction create(
		String action, List<Expression> expressions,
		ActionExpressionVisitor actionExpressionVisitor) {

		String target = actionExpressionVisitor.doVisit(expressions.get(0));

		return new DefaultDDMFormRuleAction(action, target);
	}

}