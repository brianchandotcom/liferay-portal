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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Cavalcanti
 */
public class JavaUpgradeModelPermissionsCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws Exception {

		List<String> importNames = javaTerm.getImportNames();

		String javaTermContent = javaTerm.getContent();

		if (!importNames.contains(
				"com.liferay.portal.kernel.service.ServiceContext")) {

			return javaTermContent;
		}

		if (javaTerm.isJavaClass()) {
			return _formatClass(javaTermContent, importNames);
		}

		return _formatMethod(javaTermContent, fileContent);
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS, JAVA_METHOD};
	}

	private String _formatClass(String content, List<String> importNames) {
		Matcher setGroupPermissionsMatcher =
			_setGroupPermissionsPattern.matcher(content);

		Matcher setGuestPermissionsMatcher =
			_setGuestPermissionsPattern.matcher(content);

		if (!(setGroupPermissionsMatcher.find() ||
			  setGuestPermissionsMatcher.find())) {

			return content;
		}

		if (!importNames.contains(
				"com.liferay.portal.kernel.service.permission." +
					"ModelPermissions")) {

			content = StringBundler.concat(
				"import com.liferay.portal.kernel.service.permission.",
				"ModelPermissions;\n\n", content);
		}

		if (!importNames.contains(
				"com.liferay.portal.kernel.model.role.RoleConstants")) {

			content = StringBundler.concat(
				"import com.liferay.portal.kernel.model.role.",
				"RoleConstants;\n\n", content);
		}

		return content;
	}

	private String _formatMethod(String content, String fileContent) {
		Matcher setGroupPermissionsMatcher =
			_setGroupPermissionsPattern.matcher(content);

		Matcher setGuestPermissionsMatcher =
			_setGuestPermissionsPattern.matcher(content);

		boolean hasSetGroupPermissions = false;
		boolean hasSetGuestPermissions = false;

		if (setGroupPermissionsMatcher.find()) {
			hasSetGroupPermissions = _isServiceContextMethodCall(
				content, fileContent, setGroupPermissionsMatcher.group(1));
		}

		if (setGuestPermissionsMatcher.find()) {
			hasSetGuestPermissions = _isServiceContextMethodCall(
				content, fileContent, setGuestPermissionsMatcher.group(1));
		}

		if (hasSetGroupPermissions || hasSetGuestPermissions) {
			content = _formatSetGroupAndGuestPermissions(
				hasSetGroupPermissions, hasSetGuestPermissions,
				setGroupPermissionsMatcher, setGuestPermissionsMatcher,
				content);
		}

		return content;
	}

	private String _formatSetGroupAndGuestPermissions(
		boolean hasSetGroupPermissions, boolean hasSetGuestPermissions,
		Matcher setGroupPermissionsMatcher, Matcher setGuestPermissionsMatcher,
		String javaTermContent) {

		String oldSub;
		String serviceContext;
		String groupPermissions = "new String[0]";
		String guestPermissions = "new String[0]";

		if (hasSetGroupPermissions) {
			oldSub = setGroupPermissionsMatcher.group(0);
			serviceContext = setGroupPermissionsMatcher.group(1);
			groupPermissions = setGroupPermissionsMatcher.group(2);

			if (hasSetGuestPermissions) {
				guestPermissions = setGuestPermissionsMatcher.group(2);

				javaTermContent = StringUtil.removeSubstring(
					javaTermContent, setGuestPermissionsMatcher.group(0));
			}
		}
		else {
			oldSub = setGuestPermissionsMatcher.group(0);
			serviceContext = setGuestPermissionsMatcher.group(1);
			guestPermissions = setGuestPermissionsMatcher.group(2);
		}

		return StringUtil.replace(
			javaTermContent, oldSub,
			_getModelPermissionsImplementation(
				groupPermissions, guestPermissions,
				SourceUtil.getIndent(oldSub), serviceContext));
	}

	private String _getModelPermissionsImplementation(
		String groupPermissions, String guestPermissions, String indent,
		String serviceContext) {

		StringBundler sb = new StringBundler(35);

		sb.append(StringPool.NEW_LINE);
		sb.append(indent);
		sb.append("ModelPermissions modelPermissions = ");
		sb.append(serviceContext);
		sb.append(".getModelPermissions();\n\n");
		sb.append(indent);
		sb.append("if (modelPermissions == null) {\n");
		sb.append(indent);
		sb.append("\tmodelPermissions = ModelPermissionsFactory.create(");
		sb.append(groupPermissions);
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(guestPermissions);
		sb.append(");\n");
		sb.append(indent);
		sb.append("}\n");
		sb.append(indent);
		sb.append("else {");

		if (!groupPermissions.equals("new String[0]")) {
			sb.append(StringPool.NEW_LINE);
			sb.append(indent);
			sb.append("\tmodelPermissions.addRolePermissions(");
			sb.append("RoleConstants.PLACEHOLDER_DEFAULT_GROUP_ROLE, ");
			sb.append(groupPermissions);
			sb.append(");");
		}

		if (!guestPermissions.equals("new String[0]")) {
			sb.append(StringPool.NEW_LINE);
			sb.append(indent);
			sb.append("\tmodelPermissions.addRolePermissions(");
			sb.append("RoleConstants.GUEST, ");
			sb.append(guestPermissions);
			sb.append(");");
		}

		sb.append(StringPool.NEW_LINE);
		sb.append(indent);
		sb.append("}\n\n");
		sb.append(indent);
		sb.append(serviceContext);
		sb.append(".setModelPermissions(modelPermissions);");

		return sb.toString();
	}

	private boolean _isServiceContextMethodCall(
		String methodCall, String fileContent, String variableName) {

		return Objects.equals(
			getVariableTypeName(methodCall, fileContent, variableName),
			"ServiceContext");
	}

	private static final Pattern _setGroupPermissionsPattern = Pattern.compile(
		"\\t*(\\w+)\\.setGroupPermissions\\(\\s*(\\w+)\\s*\\);");
	private static final Pattern _setGuestPermissionsPattern = Pattern.compile(
		"\\t*(\\w+)\\.setGuestPermissions\\(\\s*(\\w+)\\s*\\);");

}