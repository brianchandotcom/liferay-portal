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

package com.liferay.portal.security.ldap;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.ldap.validator.LDAPFilterException;
import com.liferay.portal.security.ldap.validator.LDAPFilterValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @author Tomas Polesovsky
 */
public class SafeLdapFilterTemplate extends SafeLdapFilter {
	private final String[] _replaceKeys;
	private final LDAPFilterValidator _ldapFilterValidator;

	public SafeLdapFilterTemplate(
			String template, String[] replaceKeys,
			LDAPFilterValidator ldapFilterValidator)
		throws LDAPFilterException {

		super(new StringBundler(template), Collections.emptyList());

		if (replaceKeys == null) {
			replaceKeys = new String[0];
		}

		_replaceKeys = replaceKeys;
		_ldapFilterValidator = ldapFilterValidator;

		ldapFilterValidator.validate(template);
	}

	public String[] getReplaceKeys() {
		return Arrays.copyOf(_replaceKeys, _replaceKeys.length);
	}

	public SafeLdapFilterTemplate replace(String[] keys, String[] values)
		throws LDAPFilterException {

		if (keys == null) {
			throw new IllegalArgumentException("Parameter keys array is null");
		}

		if (values == null) {
			throw new IllegalArgumentException(
				"Parameter values array is null");
		}

		if (keys.length > values.length) {
			throw new IllegalArgumentException(
				"Parameters keys and values must have the same length");
		}

		for (String key : keys) {
			boolean found = false;

			for (String replaceKey : _replaceKeys) {
				if (replaceKey.equals(key)) {
					found = true;
				}
			}

			if (!found) {
				throw new IllegalArgumentException(
					"Parameter key " + key +
						" is not supported by the template");
			}
		}

		String[] placeholderValues = new String[values.length];

		Arrays.fill(placeholderValues, _ARGUMENT_PLACEHOLDER);

		StringBundler oldFilterSB = getFilterStringBundler();
		Object[] oldArguments = getArguments();

		int argumentsPos = 0;
		List<Object> newArguments = new ArrayList<>();
		StringBundler newFilterSB = new StringBundler();

		for (int i = 0; i < oldFilterSB.index(); i++) {
			String filterString = oldFilterSB.stringAt(i);

			if (filterString == null) {
				continue;
			}

			if (Objects.equals(filterString, _ARGUMENT_PLACEHOLDER)) {
				newFilterSB.append(_ARGUMENT_PLACEHOLDER);

				newArguments.add(oldArguments[argumentsPos]);

				argumentsPos++;

				continue;
			}

			TreeMap<Integer, String> valuesTreeMap = new TreeMap<>();

			for (int j = 0; j < keys.length; j++) {
				String key = keys[j];

				int pos = filterString.indexOf(key);

				while (pos > -1) {
					valuesTreeMap.put(pos, values[j]);

					pos = filterString.indexOf(key, pos + key.length());
				}
			}

			if (valuesTreeMap.isEmpty()) {
				newFilterSB.append(filterString);
			}
			else {
				newArguments.addAll(valuesTreeMap.values());

				String replacedKeys = StringUtil.replace(
					filterString, keys, placeholderValues);

				int lastPos = 0;
				int pos = replacedKeys.indexOf(_ARGUMENT_PLACEHOLDER);

				while (pos > -1) {
					newFilterSB.append(replacedKeys.substring(lastPos, pos));
					newFilterSB.append(_ARGUMENT_PLACEHOLDER);

					lastPos = pos + _ARGUMENT_PLACEHOLDER.length();

					pos = replacedKeys.indexOf(_ARGUMENT_PLACEHOLDER, lastPos);
				}

				if (lastPos < replacedKeys.length()) {
					newFilterSB.append(replacedKeys.substring(lastPos));
				}
			}
		}

		_ldapFilterValidator.validate(newFilterSB.toString());

		return new SafeLdapFilterTemplate(
			newFilterSB, newArguments, _replaceKeys, _ldapFilterValidator);
	}

	protected SafeLdapFilterTemplate(
		StringBundler filterSB, List<Object> arguments,
		String[] replaceKeys, LDAPFilterValidator ldapFilterValidator) {

		super(filterSB, arguments);

		_ldapFilterValidator = ldapFilterValidator;
		_replaceKeys = replaceKeys;
	}

}