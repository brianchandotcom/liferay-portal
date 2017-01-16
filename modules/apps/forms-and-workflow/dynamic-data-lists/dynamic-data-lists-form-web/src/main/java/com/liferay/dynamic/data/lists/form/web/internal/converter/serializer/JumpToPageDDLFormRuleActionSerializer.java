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

package com.liferay.dynamic.data.lists.form.web.internal.converter.serializer;

import com.liferay.dynamic.data.lists.form.web.internal.converter.model.action.JumpToPageDDLFormRuleAction;

/**
 * @author Leonardo Barros
 */
public class JumpToPageDDLFormRuleActionSerializer
	implements DDLFormRuleActionSerializer {

	public JumpToPageDDLFormRuleActionSerializer(
		JumpToPageDDLFormRuleAction jumpToPageDDLFormRuleAction) {

		_jumpToPageDDLFormRuleAction = jumpToPageDDLFormRuleAction;
	}

	@Override
	public String serialize() {
		return String.format(
			_functionCallBinaryExpressionFormat, "jumpPage",
			_jumpToPageDDLFormRuleAction.getSource(),
			_jumpToPageDDLFormRuleAction.getTarget());
	}

	private static final String _functionCallBinaryExpressionFormat =
		"%s(%s, %s)";

	private final JumpToPageDDLFormRuleAction _jumpToPageDDLFormRuleAction;

}