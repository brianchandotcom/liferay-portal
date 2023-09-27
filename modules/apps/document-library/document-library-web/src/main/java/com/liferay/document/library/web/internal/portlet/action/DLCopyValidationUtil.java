/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

/**
 * @author Jaime León Rosado
 */
public class DLCopyValidationUtil {

	public static String getCopyToValidationMessage(
		long companyMaxSizeToCopy, long groupMaxSizeToCopy,
		long systemMaxSizeToCopy, long size) {

		if ((size > groupMaxSizeToCopy) && (groupMaxSizeToCopy != 0)) {
			String messagePrefix = LanguageUtil.get(
				LocaleUtil.getDefault(),
				"file-cannot-be-copied-because-it-exceeds-the-limit-defined-" +
					"in-site-settings");
			String messageSuffix = LanguageUtil.format(
				LocaleUtil.getDefault(),
				"the-total-number-of-files-must-not-exceed-x",
				LanguageUtil.formatStorageSize(
					groupMaxSizeToCopy, LocaleUtil.getDefault()));

			return messagePrefix + " " + messageSuffix;
		}

		if ((size > companyMaxSizeToCopy) && (companyMaxSizeToCopy != 0)) {
			String messagePrefix = LanguageUtil.get(
				LocaleUtil.getDefault(),
				"file-cannot-be-copied-because-it-exceeds-the-limit-defined-" +
					"in-instance-settings");
			String messageSuffix = LanguageUtil.format(
				LocaleUtil.getDefault(),
				"the-total-number-of-files-must-not-exceed-x",
				LanguageUtil.formatStorageSize(
					companyMaxSizeToCopy, LocaleUtil.getDefault()));

			return messagePrefix + " " + messageSuffix;
		}

		String messagePrefix = LanguageUtil.get(
			LocaleUtil.getDefault(),
			"file-cannot-be-copied-because-it-exceeds-the-limit-defined-in-" +
				"system-settings");
		String messageSuffix = LanguageUtil.format(
			LocaleUtil.getDefault(),
			"the-total-number-of-files-must-not-exceed-x",
			LanguageUtil.formatStorageSize(
				systemMaxSizeToCopy, LocaleUtil.getDefault()));

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