/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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