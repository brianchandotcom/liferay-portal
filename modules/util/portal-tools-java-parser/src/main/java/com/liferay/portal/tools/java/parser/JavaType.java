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

import com.liferay.portal.kernel.util.StringBundler;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaType extends BaseJavaTerm {

	public JavaType(String name, int arrayDimension) {
		_name = new JavaSimpleValue(name);
		_arrayDimension = arrayDimension;
	}

	public void setGenericJavaTypes(List<JavaType> genericJavaTypes) {
		_genericJavaTypes = genericJavaTypes;
	}

	public void setLowerBoundJavaTypes(List<JavaType> lowerBoundJavaTypes) {
		_lowerBoundJavaTypes = lowerBoundJavaTypes;
	}

	public void setUpperBoundJavaTypes(List<JavaType> upperBoundJavaTypes) {
		_upperBoundJavaTypes = upperBoundJavaTypes;
	}

	public void setVarargs(boolean varargs) {
		_varargs = varargs;
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength) {

		return toString(indent, prefix, suffix, maxLineLength, false);
	}

	@Override
	public String toString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		StringBundler sb = new StringBundler();

		sb.append(indent);
		sb.append(prefix);

		int addedLinesCount = append(
			sb, _name, indent, maxLineLength, !forceLineBreak);

		if (_genericJavaTypes != null) {
			if (forceLineBreak && (addedLinesCount == 0)) {
				sb.append("\n");
				sb.append(indent);
				sb.append("\t");
			}

			append(sb, _genericJavaTypes, indent, "<", ">", maxLineLength);
		}

		if (_lowerBoundJavaTypes != null) {
			if (_lowerBoundJavaTypes.size() == 1) {
				append(
					sb, _lowerBoundJavaTypes.get(0), indent, " super ", "",
					maxLineLength, false);
			}
			else {
				append(
					sb, _lowerBoundJavaTypes, " & ", indent, " super ", "",
					maxLineLength);
			}
		}

		if (_upperBoundJavaTypes != null) {
			if (_upperBoundJavaTypes.size() == 1) {
				append(
					sb, _upperBoundJavaTypes.get(0), indent, " extends ", "",
					maxLineLength, false);
			}
			else {
				append(
					sb, _upperBoundJavaTypes, " & ", indent, " extends ", "",
					maxLineLength);
			}
		}

		for (int i = 0; i < _arrayDimension; i++) {
			sb.append("[]");
		}

		if (_varargs) {
			sb.append("...");
		}

		sb.append(suffix);

		String s = sb.toString();

		if (!forceLineBreak && (getLineLength(s) > maxLineLength) &&
			(maxLineLength != -1)) {

			return toString(indent, prefix, suffix, maxLineLength, true);
		}

		return s;
	}

	private final int _arrayDimension;
	private List<JavaType> _genericJavaTypes;
	private List<JavaType> _lowerBoundJavaTypes;
	private final JavaSimpleValue _name;
	private List<JavaType> _upperBoundJavaTypes;
	private boolean _varargs;

}