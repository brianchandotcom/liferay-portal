/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

import com.liferay.petra.string.StringBundler;

/**
 * @author Brian Wing Shun Chan
 */
public class OrganizationNameException extends PortalException {

	public OrganizationNameException() {
	}

	public OrganizationNameException(String msg) {
		super(msg);
	}

	public OrganizationNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public OrganizationNameException(Throwable throwable) {
		super(throwable);
	}

	public static class MustNotExceedMaximumLength
		extends OrganizationNameException {
		public MustNotExceedMaximumLength(String name, int nameMaxLength) {
			super(
				StringBundler.concat(
					"Organization ", name, " must have fewer than ",
					nameMaxLength, " characters"));
		}

	}

}