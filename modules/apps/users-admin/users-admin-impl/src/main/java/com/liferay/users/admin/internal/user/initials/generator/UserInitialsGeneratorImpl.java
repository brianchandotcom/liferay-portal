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

package com.liferay.users.admin.internal.user.initials.generator;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.constants.LanguageConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.users.admin.kernel.util.UserInitialsGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = UserInitialsGenerator.class)
public class UserInitialsGeneratorImpl implements UserInitialsGenerator {

	@Override
	public String getInitials(
		Locale locale, String firstName, String middleName, String lastName) {

		String[] userNames = {firstName, middleName, lastName};

		List<String> userNameList = TransformUtil.transformToList(
			_getUserInitialsFieldNames(locale),
			userInitialsFieldName ->
				userNames[_userNameIndexes.get(userInitialsFieldName)]);

		if (userNameList.size() > 2) {
			userNameList = userNameList.subList(0, 2);
		}

		StringBundler sb = new StringBundler(userNameList.size());

		for (String userName : userNameList) {
			sb.append(StringUtil.toUpperCase(StringUtil.shorten(userName, 1)));
		}

		return sb.toString();
	}

	@Override
	public String getInitials(User user) {
		return getInitials(
			user.getLocale(), user.getFirstName(), user.getMiddleName(),
			user.getLastName());
	}

	private String[] _getUserInitialsFieldNames(Locale locale) {
		String[] userInitialsFieldNames = _userInitialsFieldNamesMap.get(
			locale);

		if (userInitialsFieldNames != null) {
			return userInitialsFieldNames;
		}

		userInitialsFieldNames = StringUtil.split(
			_language.get(
				locale, LanguageConstants.KEY_USER_INITIALS_FIELD_NAMES, null));

		if (ArrayUtil.isEmpty(userInitialsFieldNames)) {
			userInitialsFieldNames = _DEFAULT_USER_INITIALS_FIELD_NAMES;
		}

		_userInitialsFieldNamesMap.put(locale, userInitialsFieldNames);

		return userInitialsFieldNames;
	}

	private static final String[] _DEFAULT_USER_INITIALS_FIELD_NAMES = {
		LanguageConstants.VALUE_FIRST_NAME, LanguageConstants.VALUE_LAST_NAME
	};

	private static final Map<String, Integer> _userNameIndexes =
		HashMapBuilder.put(
			LanguageConstants.VALUE_FIRST_NAME, 0
		).put(
			LanguageConstants.VALUE_LAST_NAME, 2
		).put(
			LanguageConstants.VALUE_MIDDLE_NAME, 1
		).build();

	@Reference
	private Language _language;

	private final Map<Locale, String[]> _userInitialsFieldNamesMap =
		new HashMap<>();

}