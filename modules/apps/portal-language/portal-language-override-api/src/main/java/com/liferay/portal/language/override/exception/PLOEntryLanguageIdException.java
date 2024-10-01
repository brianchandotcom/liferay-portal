/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.override.exception;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Arrays;

/**
 * @author Drew Brokke
 */
public class PLOEntryLanguageIdException extends PortalException {

	public static class MustBeAvailable extends PLOEntryLanguageIdException {

		public MustBeAvailable(
			String[] availableLanguageIds, String languageId) {

			super(
				StringBundler.concat(
					"Language ID \"", languageId,
					"\" is not one of the available language IDs: ",
					Arrays.toString(availableLanguageIds)));
		}

	}

	private PLOEntryLanguageIdException(String msg) {
		super(msg);
	}

}