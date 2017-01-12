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

import com.liferay.dynamic.data.lists.form.web.internal.converter.model.DDLFormRuleAction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public abstract class DDLFormRuleActionSerializer<T extends DDLFormRuleAction> {

	public abstract String serialize(T ddlFormRuleAction);

	protected static final Map<String, String> actionBooleanFunctionNameMap =
		new HashMap<>();
	protected static final Map<String, String> actionFunctionNameMap =
		new HashMap<>();
	protected static final String functionCallBinaryExpressionFormat =
		"%s(%s, %s)";
	protected static final String functionCallTernaryExpressionFormat =
		"%s(%s, %s, %s)";
	protected static final String setBooleanPropertyFormat = "%s('%s', true)";

	static {
		actionBooleanFunctionNameMap.put("enable", "setEnabled");
		actionBooleanFunctionNameMap.put("invalidate", "setInvalid");
		actionBooleanFunctionNameMap.put("require", "setRequired");
		actionBooleanFunctionNameMap.put("show", "setVisible");

		actionFunctionNameMap.put("auto-fill", "call");
		actionFunctionNameMap.put("jump-to-page", "jumpPage");
	}
}