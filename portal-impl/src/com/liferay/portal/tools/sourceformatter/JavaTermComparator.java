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

package com.liferay.portal.tools.sourceformatter;

import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Comparator;
import java.util.List;

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

	protected int compareParameterTypes(
		JavaTerm javaTerm1, JavaTerm javaTerm2) {

		List<String> parameterTypes2 = javaTerm2.getParameterTypes();

		if (parameterTypes2.isEmpty()) {
			return 1;
		}

		List<String> parameterTypes1 = javaTerm1.getParameterTypes();

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

	private boolean _ascending;

}