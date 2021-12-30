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

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.petra.string.StringUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.ListUtils;

import org.osgi.framework.Version;

/**
 * @author Simon Jiang
 */
public class UpgradeUnusedAPICheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_CALL};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		String upgradeFromVersion = getAttributeValue(
			SourceFormatterUtil.UPGRADE_FROM_VERSION);
		String upgradeToVersion = getAttributeValue(
			SourceFormatterUtil.UPGRADE_TO_VERSION);

		Version upgradeFromOsgiVersion = Version.parseVersion(
			upgradeFromVersion);
		Version upgradeToOsgiVersion = Version.parseVersion(upgradeToVersion);

		if (upgradeFromOsgiVersion.compareTo(upgradeToOsgiVersion) >= 0) {
			return;
		}

		String targetVersion = getAttributeValue(_TARGET_VERSION);

		if (!Objects.equals(targetVersion, upgradeToVersion)) {
			return;
		}

		List<String> unusedMethodNameList = getAttributeValues(
			_UNUSED_CLASS_METHODS_KEY);

		List<UnusedClassMethod> unusedClassMethods = _getUnusedClassMethod(
			unusedMethodNameList);

		for (UnusedClassMethod unusedClassMethod : unusedClassMethods) {
			String qualifiedPackageClassName =
				unusedClassMethod.getQualifiedPackageClassName();
			String methodName = unusedClassMethod.getMethodName();
			List<String> parameterNames = unusedClassMethod.getParameterNames();

			String methodCallClassOrVariableName = getClassOrVariableName(
				detailAST);
			String methodCallName = getMethodName(detailAST);

			DetailAST variableClassDetailAST = getVariableDefinitionDetailAST(
				detailAST, methodCallClassOrVariableName, true);

			DetailAST parentDetailAST = _getParentDetailAST(detailAST);

			String fullyQualifiedTypeName = null;

			if (variableClassDetailAST == null) {
				fullyQualifiedTypeName = getFullyQualifiedTypeName(
					methodCallClassOrVariableName, detailAST, true);
			}
			else {
				if (variableClassDetailAST != null) {
					List<DetailAST> variableTypeTokens = getAllChildTokens(
						variableClassDetailAST, true, TokenTypes.TYPE);

					for (DetailAST variableTypeTokenDetailAST :
							variableTypeTokens) {

						fullyQualifiedTypeName = getVariableTypeName(
							variableTypeTokenDetailAST,
							methodCallClassOrVariableName, true, true, true);
					}
				}
			}

			List<String> methodCallParameters = new ArrayList<>();
			List<DetailAST> allChildTokens = getAllChildTokens(
				detailAST, true, TokenTypes.ELIST);

			for (DetailAST typeArgumentDetailAST : allChildTokens) {
				List<DetailAST> parametersAllChildTokens = getAllChildTokens(
					typeArgumentDetailAST, true, TokenTypes.IDENT);

				for (DetailAST parameterDetailAST : parametersAllChildTokens) {
					String parameterText = parameterDetailAST.getText();

					methodCallParameters.add(
						getVariableTypeName(
							parentDetailAST, parameterText, true, true, true));
				}
			}

			if (Objects.equals(
					qualifiedPackageClassName, fullyQualifiedTypeName) &&
				Objects.equals(methodName, methodCallName) &&
				ListUtils.isEqualList(parameterNames, methodCallParameters)) {

				log(
					detailAST, _MSG_UNUSED_METHOD, methodName,
					fullyQualifiedTypeName, targetVersion);
			}
		}
	}

	private DetailAST _getParentDetailAST(DetailAST detailAST) {
		if (detailAST != null) {
			DetailAST parentDetailAST = detailAST.getParent();

			if (parentDetailAST != null) {
				return _getParentDetailAST(detailAST.getParent());
			}
		}

		return detailAST;
	}

	private List<UnusedClassMethod> _getUnusedClassMethod(
		List<String> unusedMethodNameList) {

		if (Objects.nonNull(unusedMethodNameList)) {
			List<UnusedClassMethod> unusedClassMethodList = new ArrayList<>();

			for (String unusedMethodName : unusedMethodNameList) {
				String[] unusedMethod = unusedMethodName.split("\\|");

				if (unusedMethod.length < 2) {
					continue;
				}

				String qualifiedPackageClassName = unusedMethod[0];

				String methodName = unusedMethod[1];

				if (unusedMethod.length == 2) {
					unusedClassMethodList.add(
						new UnusedClassMethod(
							qualifiedPackageClassName, methodName));
				}
				else {
					List<String> parameters = StringUtil.split(
						unusedMethod[2], ' ');

					unusedClassMethodList.add(
						new UnusedClassMethod(
							qualifiedPackageClassName, methodName, parameters));
				}
			}

			return unusedClassMethodList;
		}

		return Collections.emptyList();
	}

	private static final String _MSG_UNUSED_METHOD = "method.unused";

	private static final String _TARGET_VERSION = "targetVersion";

	private static final String _UNUSED_CLASS_METHODS_KEY =
		"unusedClassMethods";

	private class UnusedClassMethod {

		public UnusedClassMethod(
			String qualifiedPackageClassName, String methodName) {

			_qualifiedPackageClassName = qualifiedPackageClassName;
			_methodName = methodName;
		}

		public UnusedClassMethod(
			String qualifiedPackageClassName, String methodName,
			List<String> parameterNames) {

			_qualifiedPackageClassName = qualifiedPackageClassName;
			_methodName = methodName;
			_parameterNames = parameterNames;
		}

		public String getMethodName() {
			return _methodName;
		}

		public List<String> getParameterNames() {
			return _parameterNames;
		}

		public String getQualifiedPackageClassName() {
			return _qualifiedPackageClassName;
		}

		private final String _methodName;
		private List<String> _parameterNames;
		private final String _qualifiedPackageClassName;

	}

}