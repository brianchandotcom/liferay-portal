/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.parser;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaClassType {

	public JavaClassType(
		String type, String classPackageName, List<String> importNames) {

		_parseJavaClassType(type, classPackageName, importNames);
	}

	@Override
	public String toString() {
		return toString(false);
	}

	public String toString(boolean fullyQualifiedName) {
		StringBundler sb = new StringBundler();

		if (fullyQualifiedName && Validator.isNotNull(_packageName)) {
			sb.append(_packageName);
			sb.append(StringPool.PERIOD);
		}

		sb.append(_name);

		if (_extendedClassType != null) {
			sb.append(" extends ");
			sb.append(_extendedClassType.toString(fullyQualifiedName));

			return sb.toString();
		}

		if (_superClassType != null) {
			sb.append(" super ");
			sb.append(_superClassType.toString(fullyQualifiedName));

			return sb.toString();
		}

		if (!_genericClassTypes.isEmpty()) {
			sb.append("<");

			for (JavaClassType genericClassType : _genericClassTypes) {
				sb.append(genericClassType.toString(fullyQualifiedName));
				sb.append(", ");
			}

			sb.setIndex(sb.index() - 1);

			sb.append(">");
		}

		for (int i = 0; i < _arrayDimension; i++) {
			sb.append("[]");
		}

		if (_varargs) {
			sb.append("...");
		}

		return sb.toString();
	}

	private void _parseGenericClassTypes(
		String genericTypesString, String classPackageName,
		List<String> importNames) {

		int x = -1;

		while (true) {
			x = genericTypesString.indexOf(StringPool.COMMA, x + 1);

			if (x == -1) {
				_genericClassTypes.add(
					new JavaClassType(
						genericTypesString, classPackageName, importNames));

				return;
			}

			String genericType = genericTypesString.substring(0, x);

			if (ToolsUtil.getLevel(genericType, "<", ">") != 0) {
				continue;
			}

			_genericClassTypes.add(
				new JavaClassType(genericType, classPackageName, importNames));

			genericTypesString = genericTypesString.substring(x + 1);

			x = -1;
		}
	}

	private void _parseJavaClassType(
		String type, String classPackageName, List<String> importNames) {

		type = StringUtil.trim(type);

		type = type.replaceAll("\\.\n", ".");
		type = type.replaceAll("\n", " ");
		type = type.replaceAll("\t+ *", "");

		if (type.startsWith("? extends ")) {
			_name = "?";

			_extendedClassType = new JavaClassType(
				type.substring(10), classPackageName, importNames);

			return;
		}

		if (type.startsWith("? super ")) {
			_name = "?";

			_superClassType = new JavaClassType(
				type.substring(8), classPackageName, importNames);

			return;
		}

		Matcher matcher = _fullyQualifiedNamePattern.matcher(type);

		if (matcher.find()) {
			_packageName = type.substring(0, matcher.end(1) - 1);

			type = StringUtil.trim(matcher.group(2));
		}

		int x = type.indexOf("<");

		if (x != -1) {
			int y = type.lastIndexOf(">");

			if (y == -1) {
				return;
			}

			_parseGenericClassTypes(
				type.substring(x + 1, y), classPackageName, importNames);

			type = type.substring(0, x) + type.substring(y + 1);
		}

		if (type.endsWith("...")) {
			_varargs = true;

			type = StringUtil.trim(type.substring(0, type.length() - 3));
		}

		while (type.endsWith("[]")) {
			_arrayDimension++;

			type = StringUtil.trim(type.substring(0, type.length() - 2));
		}

		_name = type;

		if (_packageName == null) {
			_packageName = JavaSourceUtil.getPackageName(
				_name, classPackageName, importNames);
		}
	}

	private static final Pattern _fullyQualifiedNamePattern = Pattern.compile(
		"^([a-z]\\w*\\.){2,}([A-Z].*)");

	private int _arrayDimension;
	private JavaClassType _extendedClassType;
	private final List<JavaClassType> _genericClassTypes = new ArrayList<>();
	private String _name;
	private String _packageName;
	private JavaClassType _superClassType;
	private boolean _varargs;

}