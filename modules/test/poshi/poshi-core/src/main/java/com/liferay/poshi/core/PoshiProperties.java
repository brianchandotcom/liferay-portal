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

package com.liferay.poshi.core;

import com.liferay.poshi.core.util.PropsValues;
import com.liferay.poshi.core.util.StringUtil;
import com.liferay.poshi.core.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class PoshiProperties {

	public static List<String> getPoshiPropertiesNames() {
		return PoshiContext.getPoshiPropertyNames();
	}

	public static List<String> getRequiredPoshiPropertiesNames() {
		List<String> requiredPoshiPropertyNames = new ArrayList<>();

		String testCaseRequiredPropertyNames =
			PropsValues.TEST_CASE_REQUIRED_PROPERTY_NAMES;

		if (Validator.isNotNull(testCaseRequiredPropertyNames)) {
			Collections.addAll(
				requiredPoshiPropertyNames,
				StringUtil.split(testCaseRequiredPropertyNames));
		}

		return requiredPoshiPropertyNames;
	}

}