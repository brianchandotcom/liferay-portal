/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

import com.liferay.petra.string.StringBundler;

/**
 * @author Brian Wing Shun Chan
 */
public class CountryTitleException extends PortalException {

	public CountryTitleException() {
	}

	public CountryTitleException(String msg) {
		super(msg);
	}

	public CountryTitleException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CountryTitleException(Throwable throwable) {
		super(throwable);
	}

	public static class MustNotExceedMaximumLength
		extends CountryTitleException {

		public MustNotExceedMaximumLength(String title, int titleMaxLength) {
			super(
				StringBundler.concat(
					"Title ", title, " must have fewer than ", titleMaxLength,
					" characters"));
		}

	}

}