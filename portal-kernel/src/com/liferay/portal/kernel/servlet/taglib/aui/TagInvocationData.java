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

package com.liferay.portal.kernel.servlet.taglib.aui;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Iván Zaera Avellón
 */
class TagInvocationData implements Serializable {

	public TagInvocationData(String content, String require) {
		_contentSB.append(content);

		_initModulesAndVariableAliases(require);
	}

	public TagInvocationData(StringBundler contentSB, String require) {
		_contentSB.append(contentSB);

		_initModulesAndVariableAliases(require);
	}

	public String getContent(List<String> variables) {
		StringBundler sb = new StringBundler();

		sb.append("(function() {\n");

		for (int i = 0; i < variables.size(); i++) {
			String variable = variables.get(i);
			String variableAlias = _variableAliases.get(i);

			if (!variable.equals(variableAlias)) {
				sb.append("var ");
				sb.append(variableAlias);
				sb.append(" = ");
				sb.append(variable);
				sb.append(";\n");
			}
		}

		sb.append(_contentSB);

		sb.append("\n})();");

		return sb.toString();
	}

	public List<String> getModules() {
		return _modules;
	}

	private void _initModulesAndVariableAliases(String require) {
		String[] requireParts = require.split(StringPool.COMMA);

		for (String requirePart : requireParts) {
			String[] nameAndAlias = _splitNameAlias(requirePart);

			_modules.add(nameAndAlias[0]);

			if (Validator.isBlank(nameAndAlias[1])) {
				_variableAliases.add(
					VariableUtil.generateVariable(nameAndAlias[0]));
			}
			else {
				_variableAliases.add(nameAndAlias[1]);
			}
		}
	}

	private String[] _splitNameAlias(String requirePart) {
		requirePart = requirePart.trim();

		String[] parts = _whitespacePattern.split(requirePart, 4);

		if ((parts.length == 3) &&
			StringUtil.equalsIgnoreCase(parts[1], "as")) {

			return new String[] {parts[0], parts[2]};
		}

		return new String[] {requirePart, StringPool.BLANK};
	}

	private static final Pattern _whitespacePattern = Pattern.compile("\\s+");
	private static final long serialVersionUID = 1L;

	private final StringBundler _contentSB = new StringBundler();
	private final List<String> _modules = new ArrayList<>();
	private final List<String> _variableAliases = new ArrayList<>();

}