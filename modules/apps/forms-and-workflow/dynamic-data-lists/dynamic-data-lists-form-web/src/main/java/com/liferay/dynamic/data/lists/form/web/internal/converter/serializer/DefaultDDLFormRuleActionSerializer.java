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

import com.liferay.dynamic.data.lists.form.web.internal.converter.model.action.DefaultDDLFormRuleAction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DefaultDDLFormRuleActionSerializer
	implements DDLFormRuleActionSerializer {

	public DefaultDDLFormRuleActionSerializer(
		DefaultDDLFormRuleAction defaultDDLFormRuleAction) {

		_defaultDefaultDDLFormRuleAction = defaultDDLFormRuleAction;
	}

	@Override
	public String serialize() {
		String functionName = _actionBooleanFunctionNameMap.get(
			_defaultDefaultDDLFormRuleAction.getAction());

		return String.format(
			_setBooleanPropertyFormat, functionName,
			_defaultDefaultDDLFormRuleAction.getTarget());
	}

	private DefaultDDLFormRuleAction _defaultDefaultDDLFormRuleAction;

	private static final String _setBooleanPropertyFormat = "%s('%s', true)";

	private static final Map<String, String> _actionBooleanFunctionNameMap =
		new HashMap<>();

	static {
		_actionBooleanFunctionNameMap.put("enable", "setEnabled");
		_actionBooleanFunctionNameMap.put("invalidate", "setInvalid");
		_actionBooleanFunctionNameMap.put("require", "setRequired");
		_actionBooleanFunctionNameMap.put("show", "setVisible");
	}

}