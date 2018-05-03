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

package com.liferay.dynamic.data.mapping.form.builder.internal.converter.serializer;

import com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.action.JumpToPageDDMFormRuleAction;

/**
 * @author Leonardo Barros
 */
public class JumpToPageDDMFormRuleActionSerializer
	implements DDMFormRuleActionSerializer {

	public JumpToPageDDMFormRuleActionSerializer(
		JumpToPageDDMFormRuleAction jumpToPageDDMFormRuleAction) {

		_jumpToPageDDMFormRuleAction = jumpToPageDDMFormRuleAction;
	}

	@Override
	public String serialize(
		DDMFormRuleSerializerContext ddmFormRuleSerializerContext) {

		return String.format(
			_FUNCTION_CALL_BINARY_EXPRESSION_FORMAT, "jumpPage",
			_jumpToPageDDMFormRuleAction.getSource(),
			_jumpToPageDDMFormRuleAction.getTarget());
	}

	private static final String _FUNCTION_CALL_BINARY_EXPRESSION_FORMAT =
		"%s(%s, %s)";

	private final JumpToPageDDMFormRuleAction _jumpToPageDDMFormRuleAction;

}