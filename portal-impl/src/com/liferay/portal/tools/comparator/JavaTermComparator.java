/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.comparator;

import com.liferay.portal.kernel.sourceformatter.JavaTerm;
import com.liferay.portal.tools.SourceFormatter;

import java.util.Comparator;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaTermComparator implements Comparator<JavaTerm> {

	@Override
	public int compare(JavaTerm javaTerm1, JavaTerm javaTerm2) {
		int type1 = javaTerm1.getType();
		int type2 = javaTerm2.getType();

		if (type1 != type2) {
			return type1 - type2;
		}

		String name1 = javaTerm1.getName();
		String name2 = javaTerm2.getName();

		if (type1 == SourceFormatter._TYPE_VARIABLE_PRIVATE_STATIC) {
			if (name2.equals("_log")) {
				return 1;
			}

			if (name1.equals("_instance") || name1.equals("_log")) {
				return -1;
			}

			if (name2.equals("_instance")) {
				return 1;
			}
		}

		if (name1.compareToIgnoreCase(name2) != 0) {
			return name1.compareToIgnoreCase(name2);
		}

		if (name1.compareTo(name2) != 0) {
			return -name1.compareTo(name2);
		}

		return _compareParameterTypes(javaTerm1, javaTerm2);
	}

	private static int _compareParameterTypes(
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

}