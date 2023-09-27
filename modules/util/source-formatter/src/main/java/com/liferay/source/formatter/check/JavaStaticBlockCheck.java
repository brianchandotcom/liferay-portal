/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.JavaVariable;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaStaticBlockCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		JavaClass javaClass = (JavaClass)javaTerm;

		String classContent = javaClass.getContent();

		List<JavaTerm> childJavaTerms = javaClass.getChildJavaTerms();

		if (!isExcludedPath(JAVATERM_SORT_EXCLUDES, absolutePath)) {
			classContent = _sortStaticBlocks(classContent, childJavaTerms);
		}

		return _combineStaticBlocks(classContent, childJavaTerms);
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private String _combineStaticBlocks(
		String classContent, List<JavaTerm> childJavaTerms) {

		if (childJavaTerms.size() <= 1) {
			return classContent;
		}

		JavaTerm previousJavaTerm = null;

		for (JavaTerm javaTerm : childJavaTerms) {
			if (previousJavaTerm == null) {
				previousJavaTerm = javaTerm;

				continue;
			}

			if (previousJavaTerm.isJavaStaticBlock() &&
				javaTerm.isJavaStaticBlock()) {

				classContent = StringUtil.replaceFirst(
					classContent, javaTerm.getContent(), StringPool.BLANK);

				return StringUtil.replaceFirst(
					classContent, previousJavaTerm.getContent(),
					_getCombinedStaticBlocks(
						previousJavaTerm.getContent(), javaTerm.getContent()));
			}

			previousJavaTerm = javaTerm;
		}

		return classContent;
	}

	private String _getCombinedStaticBlocks(
		String staticBlockContent1, String staticBlockContent2) {

		int x = staticBlockContent1.lastIndexOf(StringPool.CLOSE_CURLY_BRACE);

		x = staticBlockContent1.lastIndexOf(StringPool.NEW_LINE, x - 1);

		int y = staticBlockContent2.indexOf(StringPool.OPEN_CURLY_BRACE) + 1;

		return staticBlockContent1.substring(0, x + 1) +
			staticBlockContent2.substring(y);
	}

	private JavaTerm _getLastJavaTerm(
		Class<?> clazz, String accessModifier, boolean isStatic,
		List<JavaTerm> childJavaTerms) {

		for (int i = childJavaTerms.size() - 1; i >= 0; i--) {
			JavaTerm javaTerm = childJavaTerms.get(i);

			String javaTermAccessModifier = javaTerm.getAccessModifier();

			if ((javaTerm.getClass() == clazz) &&
				javaTermAccessModifier.equals(accessModifier) &&
				(javaTerm.isStatic() == isStatic)) {

				return javaTerm;
			}
		}

		return null;
	}

	private JavaTerm _getLastReferencedJavaTerm(
		String staticBlockContent, List<JavaTerm> childJavaTerms) {

		boolean containsMethodCall = false;

		for (JavaTerm javaTerm : childJavaTerms) {
			if (javaTerm.isStatic() && javaTerm.isJavaMethod() &&
				staticBlockContent.matches(
					"[\\s\\S]*\\s" + javaTerm.getName() + "\\([\\s\\S]*")) {

				containsMethodCall = true;

				break;
			}
		}

		for (int i = childJavaTerms.size() - 1; i >= 0; i--) {
			JavaTerm javaTerm = childJavaTerms.get(i);

			if (!javaTerm.isStatic() ||
				!staticBlockContent.matches(
					"[\\s\\S]*\\W" + javaTerm.getName() + "\\W[\\s\\S]*")) {

				continue;
			}

			if (javaTerm.isJavaClass() ||
				(javaTerm.isJavaVariable() && !containsMethodCall)) {

				return javaTerm;
			}
		}

		return null;
	}

	private String _sortStaticBlocks(
		String classContent, List<JavaTerm> childJavaTerms) {

		for (JavaTerm childJavaTerm : childJavaTerms) {
			if (!childJavaTerm.isJavaStaticBlock()) {
				continue;
			}

			String staticBlockContent = childJavaTerm.getContent();

			JavaTerm lastReferencedJavaTerm = _getLastReferencedJavaTerm(
				staticBlockContent, childJavaTerms);

			JavaTerm lastJavaTerm = null;

			if (lastReferencedJavaTerm == null) {
				lastJavaTerm = _getLastJavaTerm(
					JavaVariable.class, JavaTerm.ACCESS_MODIFIER_PRIVATE, true,
					childJavaTerms);
			}
			else {
				lastJavaTerm = _getLastJavaTerm(
					lastReferencedJavaTerm.getClass(),
					lastReferencedJavaTerm.getAccessModifier(),
					lastReferencedJavaTerm.isStatic(), childJavaTerms);
			}

			if (lastJavaTerm == null) {
				return classContent;
			}

			String lastJavaTermContent = lastJavaTerm.getContent();

			if (classContent.contains(
					lastJavaTermContent + "\n" + staticBlockContent)) {

				continue;
			}

			classContent = StringUtil.removeSubstring(
				classContent, staticBlockContent + "\n");

			classContent = StringUtil.replace(
				classContent, lastJavaTermContent,
				lastJavaTermContent + "\n" + staticBlockContent);

			break;
		}

		return classContent;
	}

}