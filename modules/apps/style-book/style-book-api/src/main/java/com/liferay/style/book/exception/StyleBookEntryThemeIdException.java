/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Anderson Luiz
 */
public class StyleBookEntryThemeIdException extends PortalException {

	public static class MustNotBeNull extends StyleBookEntryThemeIdException {

		public MustNotBeNull() {
			super("Theme ID must not be null");
		}

	}

	private StyleBookEntryThemeIdException(String msg) {
		super(msg);
	}

}