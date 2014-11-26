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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.UserConstants;
import com.liferay.portal.service.ListTypeServiceUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class DefaultFullNameGenerator implements FullNameGenerator {

	@Override
	public String getFullName(
		String firstName, String middleName, String lastName) {

		String fullName = buildFullName(firstName, middleName, lastName, false);

		if (!exceedFullNameMaxLength(fullName)) {
			return fullName;
		}

		fullName = buildFullName(firstName, middleName, lastName, true);

		if (!exceedFullNameMaxLength(fullName)) {
			return fullName;
		}

		return fullName.substring(0, UserConstants.FULL_NAME_MAX_LENGTH);
	}

	public String getLocalizedFullName(
		String firstName, String middleName, String lastName, Locale locale,
		int prefixId, int suffixId) {

		String fullName = buildLocalizedFullName(
			firstName, middleName, lastName, locale, prefixId, suffixId, false);

		if (!exceedFullNameMaxLength(fullName)) {
			return fullName;
		}

		fullName = buildLocalizedFullName(
			firstName, middleName, lastName, locale, prefixId, suffixId, true);

		if (!exceedFullNameMaxLength(fullName)) {
			return fullName;
		}

		return fullName.substring(0, UserConstants.FULL_NAME_MAX_LENGTH);
	}

	@Override
	public String[] splitFullName(String fullName) {
		String firstName = StringPool.BLANK;
		String middleName = StringPool.BLANK;
		String lastName = StringPool.BLANK;

		if (Validator.isNull(fullName)) {
			return new String[] {firstName, middleName, lastName};
		}

		String[] name = StringUtil.split(fullName, CharPool.SPACE);

		firstName = name[0];
		middleName = StringPool.BLANK;
		lastName = name[name.length - 1];

		if (name.length > 2) {
			for (int i = 1; i < name.length - 1; i++) {
				if (Validator.isNull(name[i].trim())) {
					continue;
				}

				if (i != 1) {
					middleName += StringPool.SPACE;
				}

				middleName += name[i].trim();
			}
		}

		return new String[] {firstName, middleName, lastName};
	}

	protected String buildFullName(
		String firstName, String middleName, String lastName,
		boolean useInitials) {

		StringBundler sb = new StringBundler(5);

		if (useInitials) {
			firstName = firstName.substring(0, 1);
		}

		sb.append(firstName);

		if (Validator.isNotNull(middleName)) {
			if (useInitials) {
				middleName = middleName.substring(0, 1);
			}

			sb.append(StringPool.SPACE);
			sb.append(middleName);
		}

		if (Validator.isNotNull(lastName)) {
			sb.append(StringPool.SPACE);
			sb.append(lastName);
		}

		return sb.toString();
	}

	protected String buildLocalizedFullName(
		String firstName, String middleName, String lastName, Locale locale,
		int prefixId, int suffixId, boolean useInitials) {

		StringBundler sb = new StringBundler(5);

		Map<String, String> namesMap = new HashMap<String, String>();

		String fieldsString = LanguageUtil.get(locale, "user.name.fields");

		String[] userNameFields = StringUtil.split(fieldsString);

		if (Validator.isNotNull(firstName)) {
			if (useInitials) {
				firstName = firstName.substring(0, 1);
			}

			namesMap.put("first-name", firstName);
		}

		if (Validator.isNotNull(middleName)) {
			if (useInitials) {
				middleName = middleName.substring(0, 1);
			}

			namesMap.put("middle-name", middleName);
		}

		if (Validator.isNotNull(lastName)) {
			namesMap.put("last-name", lastName);
		}

		if (prefixId != 0) {
			try {
				String prefix = ListTypeServiceUtil.getListType(
					prefixId).getName();

				prefix = LanguageUtil.get(locale, prefix);

				namesMap.put("prefix", prefix);
			}
			catch (PortalException e) {}
		}

		if (suffixId != 0) {
			try {
				String suffix = ListTypeServiceUtil.getListType(
					suffixId).getName();

				suffix = LanguageUtil.get(locale, suffix);

				namesMap.put("suffix", suffix);
			}
			catch (PortalException e) {}
		}

		for (String userNameField : userNameFields) {
			if (namesMap.containsKey(userNameField)) {
				sb.append(StringPool.SPACE);
				sb.append(namesMap.get(userNameField));
			}
		}

		return sb.toString();
	}

	protected boolean exceedFullNameMaxLength(String fullName) {
		if (fullName.length() <= UserConstants.FULL_NAME_MAX_LENGTH) {
			return false;
		}

		if (_log.isInfoEnabled()) {
			StringBundler sb = new StringBundler(5);

			sb.append("Full name exceeds ");
			sb.append(UserConstants.FULL_NAME_MAX_LENGTH);
			sb.append(" characters for user ");
			sb.append(fullName);
			sb.append(". Full name has been shortened.");

			_log.info(sb.toString());
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultFullNameGenerator.class);

}