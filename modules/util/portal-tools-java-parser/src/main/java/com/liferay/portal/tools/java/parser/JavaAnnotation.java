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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaAnnotation
	extends BaseJavaExpression implements Comparable<JavaAnnotation> {

	public JavaAnnotation(String name) {
		_name = new JavaSimpleValue(name);
	}

	@Override
	public int compareTo(JavaAnnotation javaAnnotation) {
		NaturalOrderStringComparator comparator =
			new NaturalOrderStringComparator();

		return comparator.compare(getName(), javaAnnotation.getName());
	}

	public String getName() {
		return _name.getName();
	}

	public void setJavaAnnotationMemberValuePairs(
		List<JavaAnnotationMemberValuePair> javaAnnotationMemberValuePairs) {

		_javaAnnotationMemberValuePairs = javaAnnotationMemberValuePairs;
	}

	public void setValueJavaExpression(JavaExpression valueJavaExpression) {
		_valueJavaExpression = valueJavaExpression;
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		sb.append(prefix);
		sb.append(StringPool.AT);
		sb.append(_name);

		if ((_javaAnnotationMemberValuePairs == null) &&
			(_valueJavaExpression == null)) {

			return sb.toString();
		}

		sb.append("(");

		if (_javaAnnotationMemberValuePairs != null) {
			if (appendSingleLine(
					sb, _javaAnnotationMemberValuePairs, "", ")" + suffix,
					maxLineLength)) {

				return sb.toString();
			}

			appendNewLine(
				sb, _javaAnnotationMemberValuePairs, indent, maxLineLength);
		}
		else {
			if (appendSingleLine(
					sb, _valueJavaExpression, "", ")" + suffix,
					maxLineLength)) {

				return sb.toString();
			}

			appendNewLine(sb, _valueJavaExpression, indent, maxLineLength);
		}

		sb.append("\n");
		sb.append(StringUtil.replaceFirst(indent, "\t", ""));
		sb.append(")");
		sb.append(suffix);

		return sb.toString();
	}

	private List<JavaAnnotationMemberValuePair> _javaAnnotationMemberValuePairs;
	private final JavaSimpleValue _name;
	private JavaExpression _valueJavaExpression;

}