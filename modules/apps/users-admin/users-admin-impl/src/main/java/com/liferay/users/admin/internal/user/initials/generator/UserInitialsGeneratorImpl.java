/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		List<String> userNamesList = TransformUtil.transformToList(
			_getUserInitialsFieldNames(locale),
			userInitialsFieldName ->
				userNames[_userNameIndexes.get(userInitialsFieldName)]);

		if (userNamesList.size() > 2) {
			userNamesList = userNamesList.subList(0, 2);
		}

		StringBundler sb = new StringBundler(userNamesList.size());

		for (String userName : userNamesList) {
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