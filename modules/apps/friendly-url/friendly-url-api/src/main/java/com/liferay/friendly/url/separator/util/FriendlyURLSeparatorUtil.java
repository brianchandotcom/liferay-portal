/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.separator.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.FriendlyURLResolverRegistryUtil;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.impl.LayoutLocalServiceHelper;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Carolina Barbosa
 */
public class FriendlyURLSeparatorUtil {

	public static String validate(
		long companyId, String friendlyURLResolverKey,
		String friendlyURLSeparator, List<String> friendlyURLSeparators,
		LayoutLocalServiceHelper layoutLocalServiceHelper, Locale locale) {

		if (friendlyURLSeparators.contains(friendlyURLSeparator)) {
			return LanguageUtil.get(
				locale,
				"friendly-url-separator-error-other-asset-type-may-use-this-" +
					"prefix");
		}

		friendlyURLSeparators.add(friendlyURLSeparator);

		if (friendlyURLSeparator.length() < 3) {
			return LanguageUtil.format(
				locale,
				"friendly-url-separator-error-should-have-at-least-x-" +
					"characters",
				3);
		}

		if (friendlyURLSeparator.length() > 255) {
			return LanguageUtil.format(
				locale,
				"friendly-url-separator-error-should-have-at-most-x-characters",
				255);
		}

		if (friendlyURLSeparator.contains(Portal.FRIENDLY_URL_SEPARATOR)) {
			return LanguageUtil.get(
				locale, "friendly-url-separator-error-invalid-characters");
		}

		if (Validator.isNumber(
				friendlyURLSeparator.substring(
					1, friendlyURLSeparator.length() - 1))) {

			return LanguageUtil.get(
				locale, "friendly-url-separator-error-cannot-be-a-number");
		}

		String friendlyURL = friendlyURLSeparator.substring(
			0, friendlyURLSeparator.length() - 1);

		try {
			layoutLocalServiceHelper.validateFriendlyURLKeyword(friendlyURL);
		}
		catch (LayoutFriendlyURLException layoutFriendlyURLException) {
			String keywordConflict =
				layoutFriendlyURLException.getKeywordConflict();

			if (!keywordConflict.endsWith(StringPool.SLASH)) {
				keywordConflict = keywordConflict + StringPool.SLASH;
			}

			FriendlyURLResolver friendlyURLResolver1 =
				FriendlyURLResolverRegistryUtil.
					getFriendlyURLResolverByDefaultURLSeparator(
						keywordConflict);
			FriendlyURLResolver friendlyURLResolver2 =
				FriendlyURLResolverRegistryUtil.getFriendlyURLResolver(
					keywordConflict);

			if (((friendlyURLResolver1 == null) &&
				 (friendlyURLResolver2 == null)) ||
				((friendlyURLResolver1 != null) &&
				 Objects.equals(
					 friendlyURLResolver1.getDefaultURLSeparator(),
					 keywordConflict) &&
				 !Objects.equals(
					 friendlyURLResolver1.getKey(), friendlyURLResolverKey)) ||
				((friendlyURLResolver2 != null) &&
				 Objects.equals(
					 friendlyURLResolver2.getURLSeparator(), keywordConflict) &&
				 !Objects.equals(
					 friendlyURLResolver2.getKey(), friendlyURLResolverKey))) {

				return LanguageUtil.get(
					locale,
					"friendly-url-separator-error-other-asset-type-may-use-" +
						"this-prefix");
			}
		}

		int count1 =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLsCount(
				companyId, friendlyURL);
		int count2 =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLsCount(
				companyId, friendlyURLSeparator + CharPool.PERCENT);

		if ((count1 > 0) || (count2 > 0)) {
			return LanguageUtil.get(
				locale,
				"friendly-url-separator-error-other-asset-type-may-use-this-" +
					"prefix");
		}

		return null;
	}

}