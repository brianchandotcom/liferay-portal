/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Jaime León Rosado
 */
public class DLCopyValidationUtil {

	public static String getCopyToValidationMessage(
		long companyMaxSizeToCopy, long groupMaxSizeToCopy,
		long systemMaxSizeToCopy, long size, Locale locale,
		boolean targetGroupValidation) {

		if ((size > groupMaxSizeToCopy) && (groupMaxSizeToCopy != 0)) {
			String messagePrefix = LanguageUtil.get(
				locale,
				"item-cannot-be-copied-because-it-exceeds-the-limit-defined-" +
					"in-site-settings");

			if (targetGroupValidation) {
				messagePrefix = LanguageUtil.get(
					locale,
					"item-cannot-be-copied-because-it-exceeds-the-limit-set-" +
						"in-destination-site-site-settings");
			}

			String messageSuffix = LanguageUtil.format(
				locale, "the-total-size-of-all-items-to-copy-must-not-exceed-x",
				LanguageUtil.formatStorageSize(groupMaxSizeToCopy, locale));

			return messagePrefix + " " + messageSuffix;
		}

		if ((size > companyMaxSizeToCopy) && (companyMaxSizeToCopy != 0)) {
			String messagePrefix = LanguageUtil.get(
				locale,
				"item-cannot-be-copied-because-it-exceeds-the-limit-defined-" +
					"in-instance-settings");
			String messageSuffix = LanguageUtil.format(
				locale, "the-total-size-of-all-items-to-copy-must-not-exceed-x",
				LanguageUtil.formatStorageSize(companyMaxSizeToCopy, locale));

			return messagePrefix + " " + messageSuffix;
		}

		String messagePrefix = LanguageUtil.get(
			locale,
			"item-cannot-be-copied-because-it-exceeds-the-limit-defined-in-" +
				"system-settings");
		String messageSuffix = LanguageUtil.format(
			locale, "the-total-size-of-all-items-to-copy-must-not-exceed-x",
			LanguageUtil.formatStorageSize(systemMaxSizeToCopy, locale));

		return messagePrefix + " " + messageSuffix;
	}

	public static boolean isCopyToAllowed(
		long companyMaxSizeToCopy, long groupMaxSizeToCopy,
		long systemMaxSizeToCopy, long size) {

		if (groupMaxSizeToCopy != 0) {
			if (size <= groupMaxSizeToCopy) {
				return true;
			}

			return false;
		}
		else if (companyMaxSizeToCopy != 0) {
			if (size <= companyMaxSizeToCopy) {
				return true;
			}

			return false;
		}
		else if (systemMaxSizeToCopy != 0) {
			if (size <= systemMaxSizeToCopy) {
				return true;
			}

			return false;
		}

		return true;
	}

}