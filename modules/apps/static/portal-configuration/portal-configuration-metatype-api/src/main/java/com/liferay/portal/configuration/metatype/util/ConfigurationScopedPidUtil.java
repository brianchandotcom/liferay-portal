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

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition.Scope;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author Drew Brokke
 */
public class ConfigurationScopedPidUtil {

	public static String buildConfigurationScopedPid(
		String basePid, Scope scope, String scopePrimKey) {

		Objects.requireNonNull(
			basePid,
			"The base PID must not be null. A scoped PID must correspond to " +
				"a configuration PID.");

		if ((scope == null) || scope.equals(Scope.SYSTEM)) {
			return basePid;
		}

		Objects.requireNonNull(
			scopePrimKey,
			"The scope primary key must not be null. A scoped PID must " +
				"correspond to a primary key for its scope.");

		return StringBundler.concat(
			basePid, scope.getDelimiterString(), scopePrimKey);
	}

	public static String getBasePid(String scopedPid) {
		if (Validator.isNull(scopedPid)) {
			return null;
		}

		for (Scope scope : Scope.values()) {
			String separator = scope.getDelimiterString();

			if (scopedPid.contains(separator)) {
				return StringUtil.split(scopedPid, separator)[0];
			}
		}

		return scopedPid;
	}

	public static Scope getScope(String scopedPid) {
		if (Validator.isNull(scopedPid)) {
			return null;
		}

		for (Scope scope : Scope.values()) {
			if (scopedPid.contains(scope.getDelimiterString())) {
				return scope;
			}
		}

		return Scope.SYSTEM;
	}

	public static String getScopePrimKey(String scopedPid) {
		if (Validator.isNull(scopedPid)) {
			return null;
		}

		for (Scope scope : Scope.values()) {
			String separator = scope.getDelimiterString();

			if (scopedPid.contains(separator)) {
				return StringUtil.split(scopedPid, separator)[1];
			}
		}

		return null;
	}

}