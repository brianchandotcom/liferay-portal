/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;

import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class JavaAnnotationMemberValuePair
	extends BaseJavaTerm implements Comparable<JavaAnnotationMemberValuePair> {

	public JavaAnnotationMemberValuePair(
		String name, JavaExpression valueJavaExpression) {

		_name = new JavaSimpleValue(name);
		_valueJavaExpression = valueJavaExpression;
	}

	@Override
	public int compareTo(
		JavaAnnotationMemberValuePair javaAnnotationMemberValuePair) {

		NaturalOrderStringComparator comparator =
			new NaturalOrderStringComparator();

		return comparator.compare(
			getName(), javaAnnotationMemberValuePair.getName());
	}

	public String getName() {
		return _name.getName();
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		StringBundler sb = new StringBundler();

		if (_valueJavaExpression instanceof JavaArray) {
			sb.append(
				_name.toString(indent, prefix, " = ", NO_MAX_LINE_LENGTH));

			JavaArray javaArray = (JavaArray)_valueJavaExpression;

			if (Objects.equals(_name.toString(), "property")) {
				javaArray.setBreakJavaValueExpressions(false);
			}

			append(
				sb, javaArray, indent + "\t", "", suffix, maxLineLength, false);

			return sb.toString();
		}

		sb.append(_name.toString(indent, prefix, " = ", NO_MAX_LINE_LENGTH));
		sb.append(
			_valueJavaExpression.toString("", "", suffix, NO_MAX_LINE_LENGTH));

		return sb.toString();
	}

	private final JavaSimpleValue _name;
	private final JavaExpression _valueJavaExpression;

}