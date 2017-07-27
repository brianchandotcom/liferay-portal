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

package com.liferay.users.admin;

import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.users.admin.kernel.util.UserInitialsGenerator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = UserInitialsGenerator.class)
public class UserInitialsGeneratorImpl implements UserInitialsGenerator {

	@Override
	public String getInitials(
		Locale locale, String firstName, String middleName, String lastName) {

		String[] fields = _getNameIntialsFields(locale);

		Map<String, String> nameInitialsMap = _getNameIntialsMap(
			firstName, middleName, lastName);

		String initials = StringPool.BLANK;

		for (int i = 0; (i < fields.length) && (i < 2); i++) {
			String nameInitial = nameInitialsMap.get(fields[i]);

			if (Validator.isNotNull(nameInitial)) {
				initials = initials.concat(nameInitial);
			}
		}

		return StringUtil.toUpperCase(initials);
	}

	@Override
	public String getInitials(User user) {
		return getInitials(
			user.getLocale(), user.getFirstName(), user.getMiddleName(),
			user.getLastName());
	}

	private String[] _getNameIntialsFields(Locale locale) {
		return StringUtil.split(
			LanguageUtil.get(
				locale, LanguageConstants.KEY_USER_INITIALS_FIELD_NAMES,
				_DEFAULT_INITIALS_FIELDS));
	}

	private Map<String, String> _getNameIntialsMap(
		String firstName, String middleName, String lastName) {

		Map<String, String> nameInitialsMap = new HashMap<>();

		if (Validator.isNotNull(firstName)) {
			nameInitialsMap.put(
				LanguageConstants.VALUE_FIRST_NAME,
				StringUtil.shorten(firstName, 1));
		}

		if (Validator.isNotNull(middleName)) {
			nameInitialsMap.put(
				LanguageConstants.VALUE_MIDDLE_NAME,
				StringUtil.shorten(middleName, 1));
		}

		if (Validator.isNotNull(lastName)) {
			nameInitialsMap.put(
				LanguageConstants.VALUE_LAST_NAME,
				StringUtil.shorten(lastName, 1));
		}

		return nameInitialsMap;
	}

	private static final String _DEFAULT_INITIALS_FIELDS =
		"first-name,last-name";

}