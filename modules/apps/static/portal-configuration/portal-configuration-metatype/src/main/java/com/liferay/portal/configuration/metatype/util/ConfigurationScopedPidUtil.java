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

package com.liferay.portal.configuration.metatype.util;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Drew Brokke
 */
public class ConfigurationScopedPidUtil {

	public static final String PID_SCOPE_SEPARATOR_COMPANY = "__COMPANY__";

	public static final String PID_SCOPE_SEPARATOR_GROUP = "__GROUP__";

	public static final String PID_SCOPE_SEPARATOR_PORTLET_INSTANCE =
		"__PORTLET_INSTANCE__";

	public static String buildConfigurationScopedPid(
		String basePid, ExtendedObjectClassDefinition.Scope scope,
		String scopePrimKey) {

		Objects.requireNonNull(
			basePid,
			"The basePid must not be null. A scoped PID must correspond to a " +
				"configuration PID");

		if ((scope == null) ||
			scope.equals(ExtendedObjectClassDefinition.Scope.SYSTEM)) {

			return basePid;
		}

		Objects.requireNonNull(
			scopePrimKey,
			"The scopePrimKey must not be null. A scoped PID must correspond " +
				"to a primary key for its scope.");

		StringBundler sb = new StringBundler(3);

		sb.append(basePid);
		sb.append(getScopeSeparatorString(scope));
		sb.append(scopePrimKey);

		return sb.toString();
	}

	public static String getBasePid(String scopedPid) {
		if (Validator.isNull(scopedPid)) {
			return null;
		}

		if (scopedPid.contains(PID_SCOPE_SEPARATOR_COMPANY)) {
			return StringUtil.split(scopedPid, PID_SCOPE_SEPARATOR_COMPANY)[0];
		}

		if (scopedPid.contains(PID_SCOPE_SEPARATOR_GROUP)) {
			return StringUtil.split(scopedPid, PID_SCOPE_SEPARATOR_GROUP)[0];
		}

		if (scopedPid.contains(PID_SCOPE_SEPARATOR_PORTLET_INSTANCE)) {
			return StringUtil.split(
				scopedPid, PID_SCOPE_SEPARATOR_PORTLET_INSTANCE)[0];
		}

		return scopedPid;
	}

	public static ExtendedObjectClassDefinition.Scope getScope(
		String scopedPid) {

		if (Validator.isNull(scopedPid)) {
			return null;
		}

		if (scopedPid.contains(PID_SCOPE_SEPARATOR_COMPANY)) {
			return ExtendedObjectClassDefinition.Scope.COMPANY;
		}

		if (scopedPid.contains(PID_SCOPE_SEPARATOR_GROUP)) {
			return ExtendedObjectClassDefinition.Scope.GROUP;
		}

		if (scopedPid.contains(PID_SCOPE_SEPARATOR_PORTLET_INSTANCE)) {
			return ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE;
		}

		return ExtendedObjectClassDefinition.Scope.SYSTEM;
	}

	public static String getScopePrimKey(String scopedPid) {
		if (Validator.isNull(scopedPid)) {
			return null;
		}

		if (scopedPid.contains(PID_SCOPE_SEPARATOR_COMPANY)) {
			return StringUtil.split(scopedPid, PID_SCOPE_SEPARATOR_COMPANY)[1];
		}

		if (scopedPid.contains(PID_SCOPE_SEPARATOR_GROUP)) {
			return StringUtil.split(scopedPid, PID_SCOPE_SEPARATOR_GROUP)[1];
		}

		if (scopedPid.contains(PID_SCOPE_SEPARATOR_PORTLET_INSTANCE)) {
			return StringUtil.split(
				scopedPid, PID_SCOPE_SEPARATOR_PORTLET_INSTANCE)[1];
		}

		return null;
	}

	public static String getScopeSeparatorString(
		ExtendedObjectClassDefinition.Scope scope) {

		return _scopeSeparatorMap.get(scope);
	}

	private static final Map<ExtendedObjectClassDefinition.Scope, String>
		_scopeSeparatorMap = new HashMap<>();

	static {
		_scopeSeparatorMap.put(
			ExtendedObjectClassDefinition.Scope.COMPANY,
			PID_SCOPE_SEPARATOR_COMPANY);
		_scopeSeparatorMap.put(
			ExtendedObjectClassDefinition.Scope.GROUP,
			PID_SCOPE_SEPARATOR_GROUP);
		_scopeSeparatorMap.put(
			ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE,
			PID_SCOPE_SEPARATOR_PORTLET_INSTANCE);
	}

}