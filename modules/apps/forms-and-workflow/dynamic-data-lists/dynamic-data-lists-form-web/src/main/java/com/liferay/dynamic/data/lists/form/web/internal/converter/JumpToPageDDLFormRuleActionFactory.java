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
import com.liferay.dynamic.data.lists.form.web.internal.converter.model.action.JumpToPageDDLFormRuleAction;
import com.liferay.dynamic.data.mapping.expression.model.Expression;

import java.util.List;

/**
 * @author Leonardo Barros
 */
public class JumpToPageDDLFormRuleActionFactory {

	public static DDLFormRuleAction create(
		List<Expression> expressions, ActionExpressionVisitor visitor) {

		String source = visitor.doVisit(expressions.get(0));
		String target = visitor.doVisit(expressions.get(1));

		return new JumpToPageDDLFormRuleAction(source, target);
	}

}