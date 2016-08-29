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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaTermComparator implements Comparator<JavaTerm> {

	public JavaTermComparator() {
		this(true);
	}

	public JavaTermComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(JavaTerm javaTerm1, JavaTerm javaTerm2) {
		int value = doCompare(javaTerm1, javaTerm2);

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	protected int compareFinderJavaTerms(
		JavaTerm javaTerm1, JavaTerm javaTerm2, String customSQLContent) {

		Matcher matcher1 = _finderPattern.matcher(javaTerm1.getName());

		if (!matcher1.find()) {
			return 0;
		}

		Matcher matcher2 = _finderPattern.matcher(javaTerm2.getName());

		if (!matcher2.find()) {
			return 0;
		}

		String namePart1 = matcher1.group(1);
		String namePart2 = matcher2.group(1);

		if (!namePart1.equals(namePart2)) {
			return 0;
		}

		String customSQLKey1 = getCustomSQLKey(javaTerm1);
		String customSQLKey2 = getCustomSQLKey(javaTerm2);

		if ((customSQLKey1 == null) || (customSQLKey2 == null)) {
			return 0;
		}

		int startsWithWeight = StringUtil.startsWithWeight(
			customSQLKey1, customSQLKey2);

		if (startsWithWeight != 0) {
			String startKey = customSQLKey1.substring(0, startsWithWeight);

			if (!startKey.contains("By")) {
				return customSQLKey1.compareTo(customSQLKey1);
			}
		}

		int pos1 = customSQLContent.indexOf(customSQLKey1);
		int pos2 = customSQLContent.indexOf(customSQLKey2);

		if ((pos1 != -1) && (pos2 != -1)) {
			return pos1 - pos2;
		}

		int columnCount1 = StringUtil.count(
			javaTerm1.getName(), CharPool.UNDERLINE);
		int columnCount2 = StringUtil.count(
			javaTerm2.getName(), CharPool.UNDERLINE);

		return columnCount1 - columnCount2;
	}

	protected int compareParameterTypes(
		JavaTerm javaTerm1, JavaTerm javaTerm2) {

		List<String> parameterTypes1 = javaTerm1.getParameterTypes();
		List<String> parameterTypes2 = javaTerm2.getParameterTypes();

		if (parameterTypes2.isEmpty()) {
			if (parameterTypes1.isEmpty()) {
				return 0;
			}

			return 1;
		}

		if (parameterTypes1.isEmpty()) {
			return -1;
		}

		for (int i = 0; i < parameterTypes1.size(); i++) {
			if (parameterTypes2.size() < (i + 1)) {
				return 1;
			}

			String parameterType1 = parameterTypes1.get(i);
			String parameterType2 = parameterTypes2.get(i);

			if ((parameterTypes1.size() != parameterTypes2.size()) &&
				(parameterType1.equals(parameterType2.concat("...")) ||
				 parameterType2.equals(parameterType1.concat("...")))) {

				continue;
			}

			if (parameterType1.compareToIgnoreCase(parameterType2) != 0) {
				return parameterType1.compareToIgnoreCase(parameterType2);
			}

			if (parameterType1.compareTo(parameterType2) != 0) {
				return -parameterType1.compareTo(parameterType2);
			}
		}

		if (parameterTypes1.size() == parameterTypes2.size()) {
			return 0;
		}

		return -1;
	}

	protected int doCompare(JavaTerm javaTerm1, JavaTerm javaTerm2) {
		int type1 = javaTerm1.getType();
		int type2 = javaTerm2.getType();

		if (type1 != type2) {
			return type1 - type2;
		}

		String name1 = javaTerm1.getName();
		String name2 = javaTerm2.getName();

		if (javaTerm1.isVariable()) {
			if (StringUtil.isUpperCase(name1) &&
				!StringUtil.isLowerCase(name1) &&
				!StringUtil.isUpperCase(name2)) {

				return -1;
			}

			if (!StringUtil.isUpperCase(name1) &&
				StringUtil.isUpperCase(name2) &&
				!StringUtil.isLowerCase(name2)) {

				return 1;
			}
		}

		if (type1 == JavaTerm.TYPE_VARIABLE_PRIVATE_STATIC) {
			if (name2.equals("_log") || name2.equals("_logger")) {
				return 1;
			}

			if (name1.equals("_instance") || name1.equals("_log") ||
				name1.equals("_logger")) {

				return -1;
			}

			if (name2.equals("_instance")) {
				return 1;
			}
		}

		String customSQLContent = javaTerm1.getCustomSQLContent();

		if (Validator.isNotNull(customSQLContent) &&
			(name1.compareToIgnoreCase(name2) != 0)) {

			int value = compareFinderJavaTerms(
				javaTerm1, javaTerm2, customSQLContent);

			if (value != 0) {
				return value;
			}
		}

		if (name1.compareToIgnoreCase(name2) != 0) {
			NaturalOrderStringComparator naturalOrderStringComparator =
				new NaturalOrderStringComparator(true, false);

			return naturalOrderStringComparator.compare(name1, name2);
		}

		if (name1.compareTo(name2) != 0) {
			NaturalOrderStringComparator naturalOrderStringComparator =
				new NaturalOrderStringComparator(true, true);

			return -naturalOrderStringComparator.compare(name1, name2);
		}

		return compareParameterTypes(javaTerm1, javaTerm2);
	}

	protected String getCustomSQLKey(JavaTerm javaTerm) {
		String fileName = javaTerm.getFileName();

		int pos = fileName.lastIndexOf(StringPool.SLASH);

		String objectName = fileName.substring(pos + 1, fileName.length() - 9);

		String javaTermName = javaTerm.getName();

		if (javaTermName.matches("(COUNT|FIND|JOIN)_.*")) {
			Matcher matcher = _sqlKeyPattern.matcher(javaTerm.getContent());

			if (matcher.find()) {
				return objectName + StringPool.PERIOD + matcher.group(1);
			}

			return null;
		}

		String sqlKey = javaTermName.replaceFirst(
			"^(do|filter)", StringPool.BLANK);

		sqlKey =
			StringUtil.toLowerCase(sqlKey.substring(0, 1)) +
				sqlKey.substring(1);

		return objectName + StringPool.PERIOD + sqlKey;
	}

	private final boolean _ascending;
	private final Pattern _finderPattern = Pattern.compile(
		"((COUNT|FIND|JOIN)_|(do|filter)?([Cc]ount|[Ff]ind)).*");
	private final Pattern _sqlKeyPattern = Pattern.compile(
		"\"\\.([^\"]+)\";\n");

}