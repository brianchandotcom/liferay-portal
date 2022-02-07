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

package com.liferay.source.formatter.checkstyle.check;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.util.SourceFormatterUpgradeUtil;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.osgi.framework.Version;

/**
 * @author Simon Jiang
 */
public class UpgradeUnusedAPICheck extends BaseAPICheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.METHOD_CALL};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST dotDetailAST = detailAST.findFirstToken(TokenTypes.DOT);

		if (dotDetailAST == null) {
			return;
		}

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

		for (UnusedClassMethod unusedClassMethod :
				_getUpgradeUnusedMethodNamesMap()) {

			String targetVersion = unusedClassMethod.getVersion();

			Version targetOsgiVersion = Version.parseVersion(targetVersion);

			if (upgradeToOsgiVersion.compareTo(targetOsgiVersion) < 0) {
				continue;
			}

			String qualifiedPackageClassName =
				unusedClassMethod.getQualifiedPackageClassName();
			String methodName = unusedClassMethod.getMethodName();
			List<String> parameterNames = unusedClassMethod.getParameterNames();

			String methodCallClassOrVariableName = getClassOrVariableName(
				detailAST);
			String methodCallName = getMethodName(detailAST);

			String fullyQualifiedTypeName = getVariableTypeName(
				detailAST, methodCallClassOrVariableName, true, true, true);

			List<String> methodCallParameters = getParameterTypeNames(
				detailAST);

			if (qualifiedPackageClassName.equals(fullyQualifiedTypeName) &&
				methodName.equals(methodCallName) &&
				(parameterNames.size() == methodCallParameters.size()) &&
				parameterNames.containsAll(methodCallParameters)) {

				log(
					detailAST, _MSG_UNUSED_METHOD, methodName,
					fullyQualifiedTypeName, targetVersion);
			}
		}
	}

	private synchronized List<UnusedClassMethod>
		_getUpgradeUnusedMethodNamesMap() {

		if (_upgradeUnusedMethodNames != null) {
			return _upgradeUnusedMethodNames;
		}

		Tuple upgradeUnusedMethodNamesTuple =
			_getUpgradeUnusedMethodNamesTuple();

		JSONObject jsonObject =
			(JSONObject)upgradeUnusedMethodNamesTuple.getObject(0);

		JSONArray jsonArray = (JSONArray)jsonObject.get(
			_UPGRADE_UNUSED_METHOD_CATEGORY);

		_upgradeUnusedMethodNames = new ArrayList<>();

		for (Object object : JSONUtil.toObjectList(jsonArray)) {
			jsonObject = (JSONObject)object;

			String parameterNamesString = jsonObject.getString(
				"parameterNames");

			if (Objects.isNull(parameterNamesString)) {
				_upgradeUnusedMethodNames.add(
					new UnusedClassMethod(
						jsonObject.getString("version"),
						jsonObject.getString("className"),
						jsonObject.getString("methodName")));
			}
			else {
				List<String> parameters = Arrays.asList(
					StringUtil.split(parameterNamesString, ' '));

				_upgradeUnusedMethodNames.add(
					new UnusedClassMethod(
						jsonObject.getString("version"),
						jsonObject.getString("className"),
						jsonObject.getString("methodName"), parameters));
			}
		}

		return _upgradeUnusedMethodNames;
	}

	private synchronized Tuple _getUpgradeUnusedMethodNamesTuple() {
		if (_upgradUnusedMethodNamesTuple != null) {
			return _upgradUnusedMethodNamesTuple;
		}

		_upgradUnusedMethodNamesTuple =
			SourceFormatterUpgradeUtil.getTypeNamesTuple(
				_UPGRADE_UNUSED_METHOD_NAME, _UPGRADE_UNUSED_METHOD_CATEGORY);

		return _upgradUnusedMethodNamesTuple;
	}

	private static final String _MSG_UNUSED_METHOD = "method.unused";

	private static final String _UPGRADE_UNUSED_METHOD_CATEGORY =
		"upgradeUnusedMethodNames";

	private static final String _UPGRADE_UNUSED_METHOD_NAME =
		"upgrade-unused-method-names.json";

	private List<UnusedClassMethod> _upgradeUnusedMethodNames;
	private Tuple _upgradUnusedMethodNamesTuple;

	private class UnusedClassMethod {

		public UnusedClassMethod(
			String version, String qualifiedPackageClassName,
			String methodName) {

			this(
				version, qualifiedPackageClassName, methodName,
				Collections.emptyList());
		}

		public UnusedClassMethod(
			String version, String qualifiedPackageClassName, String methodName,
			List<String> parameterNames) {

			_version = version;
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

		public String getVersion() {
			return _version;
		}

		private final String _methodName;
		private List<String> _parameterNames;
		private final String _qualifiedPackageClassName;
		private final String _version;

	}

}