/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaMethod;
import com.liferay.source.formatter.parser.JavaParameter;
import com.liferay.source.formatter.parser.JavaSignature;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.JavaVariable;
import com.liferay.source.formatter.parser.ParseException;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaTransactionBoundaryCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException, ParseException {

		if (!absolutePath.contains("-service/") ||
			!absolutePath.contains("/service/impl/") ||
			!fileName.endsWith("ServiceImpl.java")) {

			return javaTerm.getContent();
		}

		JavaClass javaClass = (JavaClass)javaTerm;

		if (javaClass.getParentJavaClass() != null) {
			return javaTerm.getContent();
		}

		JavaClass serviceJavaClass = null;

		List<JavaTerm> childJavaTerms = javaClass.getChildJavaTerms();

		for (JavaTerm childJavaTerm : childJavaTerms) {
			if (!childJavaTerm.isPublic() ||
				!(childJavaTerm instanceof JavaMethod)) {

				continue;
			}

			String methodContent = childJavaTerm.getContent();
			String methodName = childJavaTerm.getName();

			Matcher matcher = _methodCallPattern.matcher(methodContent);

			while (matcher.find()) {
				String calledMethodName = matcher.group(1);

				if (methodName.equals(calledMethodName)) {
					continue;
				}

				List<String> parameters = JavaSourceUtil.getParameterList(
					methodContent.substring(matcher.start(1)));

				if (_containsMethod(
						childJavaTerms, calledMethodName,
						JavaTerm.ACCESS_MODIFIER_PROTECTED,
						parameters.size()) ||
					!_containsMethod(
						childJavaTerms, calledMethodName,
						JavaTerm.ACCESS_MODIFIER_PUBLIC, parameters.size())) {

					continue;
				}

				if (serviceJavaClass == null) {
					serviceJavaClass = _getServiceJavaClass(absolutePath);

					if (serviceJavaClass == null) {
						return javaTerm.getContent();
					}
				}

				if (!_isReadOnlyMethod(serviceJavaClass, methodName) ||
					_isReadOnlyMethod(serviceJavaClass, calledMethodName)) {

					continue;
				}

				String serviceVariableName = _getServiceVariableName(
					absolutePath,
					StringUtil.replaceLast(
						javaClass.getName(), "Impl", StringPool.BLANK));

				if (serviceVariableName == null) {
					continue;
				}

				String newMethodContent = StringUtil.replaceFirst(
					methodContent, calledMethodName,
					serviceVariableName + "." + calledMethodName,
					matcher.start());

				return StringUtil.replaceFirst(
					javaTerm.getContent(), methodContent, newMethodContent);
			}
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private boolean _containsMethod(
		List<JavaTerm> javaTerms, String methodName, String accessModifier,
		int parameterCount) {

		for (JavaTerm javaTerm : javaTerms) {
			if (!(javaTerm instanceof JavaMethod) ||
				!methodName.equals(javaTerm.getName()) ||
				!accessModifier.equals(javaTerm.getAccessModifier())) {

				continue;
			}

			JavaSignature signature = javaTerm.getSignature();

			List<JavaParameter> javaParameters = signature.getParameters();

			if (javaParameters.size() == parameterCount) {
				return true;
			}
		}

		return false;
	}

	private JavaClass _getServiceJavaClass(String absolutePath)
		throws IOException, ParseException {

		String serviceClassPath = StringUtil.replace(
			absolutePath,
			new String[] {"-service/", "/service/impl/", "Impl.java"},
			new String[] {"-api/", "/service/", ".java"});

		File file = new File(serviceClassPath);

		if (!file.exists()) {
			return null;
		}

		return JavaClassParser.parseJavaClass(
			serviceClassPath, FileUtil.read(file));
	}

	private String _getServiceVariableName(
			String absolutePath, String expectedName)
		throws IOException, ParseException {

		String serviceBaseClassPath = StringUtil.replace(
			absolutePath, new String[] {"/service/impl/", "Impl.java"},
			new String[] {"/service/base/", "BaseImpl.java"});

		File file = new File(serviceBaseClassPath);

		if (!file.exists()) {
			return null;
		}

		JavaClass javaClass = JavaClassParser.parseJavaClass(
			serviceBaseClassPath, FileUtil.read(file));

		for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
			if ((javaTerm instanceof JavaVariable) && javaTerm.isProtected() &&
				StringUtil.equalsIgnoreCase(javaTerm.getName(), expectedName)) {

				return javaTerm.getName();
			}
		}

		return null;
	}

	private boolean _isReadOnlyMethod(JavaClass javaClass, String methodName) {
		for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
			if (!(javaTerm instanceof JavaMethod) ||
				!methodName.equals(javaTerm.getName())) {

				continue;
			}

			String content = javaTerm.getContent();

			if (content.contains("@Transactional(") &&
				content.contains("readOnly = true")) {

				return true;
			}

			return false;
		}

		return false;
	}

	private static final Pattern _methodCallPattern = Pattern.compile(
		"[^\\.\\s]\\s+([a-z]\\w+)\\(");

}