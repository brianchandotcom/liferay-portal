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

		_valueJavaExpression = valueJavaExpression;

		_name = new JavaSimpleValue(name);
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
			sb.append(_name.toString(indent, prefix, " = ", -1));

			JavaArray javaArray = (JavaArray)_valueJavaExpression;

			if (Objects.equals(_name.toString(), "property")) {
				javaArray.setBreakJavaValueExpressions(false);
			}

			append(
				sb, javaArray, indent + "\t", "", suffix, maxLineLength, false);

			return sb.toString();
		}

		sb.append(_name.toString(indent, prefix, " = ", -1));
		sb.append(_valueJavaExpression.toString("", "", suffix, -1));

		return sb.toString();
	}

	private final JavaSimpleValue _name;
	private final JavaExpression _valueJavaExpression;

}