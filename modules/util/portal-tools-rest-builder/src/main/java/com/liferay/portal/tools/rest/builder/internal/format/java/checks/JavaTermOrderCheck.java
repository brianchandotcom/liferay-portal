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

package com.liferay.portal.tools.rest.builder.internal.format.java.checks;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.comparator.JavaTermComparator;

import java.util.List;
import java.util.Objects;

/**
 * @author Hugo Huijser
 */
public class JavaTermOrderCheck {

	public static String format(String content, String fileName)
		throws Exception {

		String oldContent = content;

		while (true) {
			String newContent = _format(oldContent, fileName);

			if (oldContent.equals(newContent)) {
				return newContent;
			}

			oldContent = newContent;
		}
	}

	private static String _format(String content, String fileName)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		String javaClassContent = javaClass.getContent();

		String javaClassHeader = content.substring(
			0, content.indexOf(javaClassContent));

		if (javaClassContent.contains("@Meta.OCD")) {
			return javaClassHeader + javaClassContent;
		}

		return javaClassHeader + _sortJavaTerms(javaClass);
	}

	private static String _sortJavaTerms(JavaClass javaClass) {
		List<JavaTerm> childJavaTerms = javaClass.getChildJavaTerms();

		if (childJavaTerms.size() < 2) {
			return javaClass.getContent();
		}

		JavaTermComparator javaTermComparator = new JavaTermComparator(null);

		JavaTerm previousJavaTerm = null;

		for (JavaTerm javaTerm : childJavaTerms) {
			if (javaTerm.isJavaStaticBlock() ||
				Objects.equals(
					javaTerm.getAccessModifier(),
					JavaTerm.ACCESS_MODIFIER_DEFAULT)) {

				continue;
			}

			if (previousJavaTerm == null) {
				previousJavaTerm = javaTerm;

				continue;
			}

			int compare = javaTermComparator.compare(
				previousJavaTerm, javaTerm);

			if (compare > 0) {
				String classContent = javaClass.getContent();

				String newClassContent = StringUtil.replaceFirst(
					classContent, "\n" + previousJavaTerm.getContent(),
					"\n" + javaTerm.getContent());

				newClassContent = StringUtil.replaceLast(
					newClassContent, "\n" + javaTerm.getContent(),
					"\n" + previousJavaTerm.getContent());

				return newClassContent;
			}

			previousJavaTerm = javaTerm;
		}

		return javaClass.getContent();
	}

}