/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.vldap.server.internal.directory.builder;

import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
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
		Set<String> lowerCaseValues = Collections.newSetFromMap(
			new ConcurrentHashMap<>());

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
		Collections.newSetFromMap(new ConcurrentHashMap<>());
	private final Map<String, Set<String>> _validAttributeValues =
		new ConcurrentHashMap<>();

}