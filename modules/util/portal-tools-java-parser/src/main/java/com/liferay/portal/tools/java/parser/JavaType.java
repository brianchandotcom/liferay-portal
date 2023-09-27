/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaType extends BaseJavaTerm implements Comparable<JavaType> {

	public JavaType(String name, int arrayDimension) {
		this(name, null, arrayDimension);
	}

	public JavaType(
		String name, List<JavaAnnotation> javaAnnotations, int arrayDimension) {

		_name = new JavaSimpleValue(name);
		_javaAnnotations = javaAnnotations;
		_arrayDimension = arrayDimension;
	}

	@Override
	public int compareTo(JavaType javaType) {
		NaturalOrderStringComparator comparator =
			new NaturalOrderStringComparator();

		return comparator.compare(getName(), javaType.getName());
	}

	public String getName() {
		return _name.getName();
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

		for (int i = 0; i < _arrayDimension; i++) {
			sb.append("[]");
		}

		if (_varargs) {
			sb.append("...");
		}

		sb.append(suffix);

		suffix = sb.toString();

		sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		if (ListUtil.isNotEmpty(_javaAnnotations) &&
			!appendSingleLine(
				sb, _javaAnnotations, " ", "", " ", maxLineLength)) {

			append(sb, _javaAnnotations, " ", indent, "", "", maxLineLength);

			sb.append("\n");
			sb.append(indent.substring(1));
		}

		if ((_genericJavaTypes == null) && (_lowerBoundJavaTypes == null) &&
			(_upperBoundJavaTypes == null)) {

			append(
				sb, _name, indent, prefix, suffix, maxLineLength,
				!forceLineBreak);

			return sb.toString();
		}

		if (!appendSingleLine(sb, _name, prefix, "", maxLineLength)) {
			indent = append(
				sb, _name, indent, prefix, "", maxLineLength, !forceLineBreak);
		}
		else if (forceLineBreak) {
			indent = adjustIndent(sb, indent);

			sb.append("\n");
			sb.append(indent);
		}

		if (_genericJavaTypes != null) {
			append(
				sb, _genericJavaTypes, indent, "<", ">" + suffix,
				maxLineLength);
		}
		else if (_lowerBoundJavaTypes != null) {
			if (_lowerBoundJavaTypes.size() == 1) {
				append(
					sb, _lowerBoundJavaTypes.get(0), indent, " super ", suffix,
					maxLineLength, false);
			}
			else {
				indent = append(
					sb, _lowerBoundJavaTypes, " & ", indent, " super ", suffix,
					maxLineLength);
			}
		}
		else if (_upperBoundJavaTypes != null) {
			if (_upperBoundJavaTypes.size() == 1) {
				append(
					sb, _upperBoundJavaTypes.get(0), indent, " extends ",
					suffix, maxLineLength, false);
			}
			else {
				append(
					sb, _upperBoundJavaTypes, " & ", indent, " extends ",
					suffix, maxLineLength);
			}
		}

		return sb.toString();
	}

	private final int _arrayDimension;
	private List<JavaType> _genericJavaTypes;
	private final List<JavaAnnotation> _javaAnnotations;
	private List<JavaType> _lowerBoundJavaTypes;
	private final JavaSimpleValue _name;
	private List<JavaType> _upperBoundJavaTypes;
	private boolean _varargs;

}