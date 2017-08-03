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

package com.liferay.vldap.server.internal.directory.builder;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Andrew Betts
 */
public class AttributeValidator {

	public void addAlwaysValidAttribute(String name) {
		_alwaysValidAttributeNames.add(StringUtil.toLowerCase(name));
	}

	public void addValidAttributeValues(String name, String... values) {
		Set<String> lowerCaseValues = new ConcurrentHashSet<>(values.length);

		for (String value : values) {
			lowerCaseValues.add(StringUtil.toLowerCase(value));
		}

		_validAttributeValues.put(
			StringUtil.toLowerCase(name), lowerCaseValues);
	}

	public boolean isValidAttribute(String name, String value) {
		name = StringUtil.toLowerCase(name);

		if (_alwaysValidAttributeNames.contains(name)) {
			return true;
		}

		Set<String> validAttributeValues = _validAttributeValues.get(name);

		if (validAttributeValues == null) {
			return false;
		}

		value = StringUtil.toLowerCase(value);

		if (validAttributeValues.contains(value)) {
			return true;
		}

		return false;
	}

	private final Set<String> _alwaysValidAttributeNames =
		new ConcurrentHashSet<>();
	private final Map<String, Set<String>> _validAttributeValues =
		new ConcurrentHashMap<>();

}