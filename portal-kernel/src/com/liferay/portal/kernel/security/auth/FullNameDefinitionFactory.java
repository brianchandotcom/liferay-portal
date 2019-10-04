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

package com.liferay.portal.kernel.security.auth;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class FullNameDefinitionFactory {

	public static FullNameDefinition getInstance(Locale locale) {
		return _fullNameDefinitionFactory._getInstance(locale);
	}

	private FullNameDefinitionFactory() {
	}

	private FullNameField _getFullNameField(
		Locale locale, String userNameField, boolean required) {

		FullNameField fullNameField = new FullNameField();

		fullNameField.setName(userNameField);
		fullNameField.setRequired(required);

		String[] values = null;

		if (userNameField.equals(LanguageConstants.VALUE_PREFIX)) {
			values = StringUtil.split(
				LanguageUtil.get(
					locale, LanguageConstants.KEY_USER_NAME_PREFIX_VALUES,
					StringPool.BLANK));
		}
		else if (userNameField.equals(LanguageConstants.VALUE_SUFFIX)) {
			values = StringUtil.split(
				LanguageUtil.get(
					locale, LanguageConstants.KEY_USER_NAME_SUFFIX_VALUES,
					StringPool.BLANK));
		}

		fullNameField.setValues(values);

		return fullNameField;
	}

	private FullNameDefinition _getInstance(Locale locale) {
		FullNameDefinition fullNameDefinition = _fullNameDefinitions.get(
			locale);

		if (fullNameDefinition != null) {
			return fullNameDefinition;
		}

		fullNameDefinition = new FullNameDefinition();

		String[] requiredFieldNames = _getRequiredFieldNames(locale);

		for (String requiredFieldName : requiredFieldNames) {
			fullNameDefinition.addRequiredField(requiredFieldName);
		}

		String[] fieldNames = StringUtil.split(
			LanguageUtil.get(
				locale, LanguageConstants.KEY_USER_NAME_FIELD_NAMES));

		fieldNames = _includeRequiredFieldNames(requiredFieldNames, fieldNames);

		for (String userNameField : fieldNames) {
			FullNameField fullNameField = _getFullNameField(
				locale, userNameField,
				fullNameDefinition.isFieldRequired(userNameField));

			fullNameDefinition.addFullNameField(fullNameField);
		}

		_fullNameDefinitions.put(locale, fullNameDefinition);

		return fullNameDefinition;
	}

	private String[] _getRequiredFieldNames(Locale locale) {
		String[] requiredFieldNames = StringUtil.split(
			LanguageUtil.get(
				locale, LanguageConstants.KEY_USER_NAME_REQUIRED_FIELD_NAMES));

		if (!ArrayUtil.contains(
				requiredFieldNames, LanguageConstants.VALUE_FIRST_NAME)) {

			requiredFieldNames = ArrayUtil.append(
				new String[] {LanguageConstants.VALUE_FIRST_NAME},
				requiredFieldNames);
		}

		return requiredFieldNames;
	}

	private String[] _includeRequiredFieldNames(
		String[] requiredFieldNames, String[] fieldNames) {

		fieldNames = ArrayUtil.append(requiredFieldNames, fieldNames);

		ArrayUtil.reverse(fieldNames);

		fieldNames = ArrayUtil.unique(fieldNames);

		ArrayUtil.reverse(fieldNames);

		return fieldNames;
	}

	private static final FullNameDefinitionFactory _fullNameDefinitionFactory =
		new FullNameDefinitionFactory();

	private final Map<Locale, FullNameDefinition> _fullNameDefinitions =
		new ConcurrentHashMap<>();

}