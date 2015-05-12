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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class FullNameDefinitionFactory {

	public static FullNameDefinition getInstance(Locale locale) {
		return _instance._getInstance(locale);
	}

	private static String[] _prependMissingRequiredFieldNames(
		String[] fieldNames, String[] requiredFieldNames) {

		List<String> fieldNamesList = ListUtil.toList(fieldNames);

		int i;

		for (i = 0; i < ArrayUtil.getLength(requiredFieldNames); i++) {
			String requiredFieldName = requiredFieldNames[i];

			if (!fieldNamesList.contains(requiredFieldName)) {
				fieldNamesList.add(i, requiredFieldName);
			}
		}

		if (!fieldNamesList.contains("first-name")) {
			fieldNamesList.add(0, "first-name");
		}

		return fieldNamesList.toArray(new String[fieldNamesList.size()]);
	}

	private FullNameDefinitionFactory() {
	}

	private FullNameDefinition _getInstance(Locale locale) {
		FullNameDefinition fullNameDefinition = _fullNameDefinitions.get(
			locale);

		if (fullNameDefinition != null) {
			return fullNameDefinition;
		}

		fullNameDefinition = new FullNameDefinition();

		String[] fieldNames = StringUtil.split(
			LanguageUtil.get(locale, "lang.user.name.field.names"));

		String[] requiredFieldNames = StringUtil.split(
			LanguageUtil.get(locale, "lang.user.name.required.field.names"));

		fieldNames = _prependMissingRequiredFieldNames(
				fieldNames, requiredFieldNames);

		for (String requiredFieldName : requiredFieldNames) {
			fullNameDefinition.addRequiredField(requiredFieldName);
		}

		for (String userNameField : fieldNames) {
			FullNameField fullNameField = new FullNameField();

			fullNameField.setName(userNameField);

			String[] values = StringUtil.split(
					LanguageUtil.get(
							locale, "lang.user.name." + userNameField + ".values",
							StringPool.BLANK));

			fullNameField.setValues(values);

			fullNameField.setRequired(
				fullNameDefinition.isFieldRequired(userNameField));

			fullNameDefinition.addFullNameField(fullNameField);
		}

		_fullNameDefinitions.put(locale, fullNameDefinition);

		return fullNameDefinition;
	}

	private static final FullNameDefinitionFactory _instance =
		new FullNameDefinitionFactory();

	private final Map<Locale, FullNameDefinition> _fullNameDefinitions =
		new ConcurrentHashMap<>();

}