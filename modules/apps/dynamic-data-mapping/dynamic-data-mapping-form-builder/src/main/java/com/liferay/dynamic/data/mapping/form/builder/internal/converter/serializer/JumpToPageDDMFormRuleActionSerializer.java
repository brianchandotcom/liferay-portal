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
import com.liferay.dynamic.data.mapping.spi.converter.serializer.SPIDDMFormRuleActionSerializer;
import com.liferay.dynamic.data.mapping.spi.converter.serializer.SPIDDMFormRuleSerializerContext;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Leonardo Barros
 */
public class JumpToPageDDMFormRuleActionSerializer
	implements SPIDDMFormRuleActionSerializer {

	public JumpToPageDDMFormRuleActionSerializer(
		JumpToPageDDMFormRuleAction jumpToPageDDMFormRuleAction) {

		_jumpToPageDDMFormRuleAction = jumpToPageDDMFormRuleAction;
	}

	@Override
	public String serialize(
		SPIDDMFormRuleSerializerContext spiDDMFormRuleSerializerContext) {

		if (Validator.isNull(_jumpToPageDDMFormRuleAction.getTarget())) {
			return null;
		}

		return String.format(
			_FUNCTION_CALL_BINARY_EXPRESSION_FORMAT, "jumpPage",
			_jumpToPageDDMFormRuleAction.getSource(),
			_jumpToPageDDMFormRuleAction.getTarget());
	}

	private static final String _FUNCTION_CALL_BINARY_EXPRESSION_FORMAT =
		"%s(%s, %s)";

	private final JumpToPageDDMFormRuleAction _jumpToPageDDMFormRuleAction;

}