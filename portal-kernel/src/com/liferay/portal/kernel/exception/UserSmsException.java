/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class UserSmsException extends PortalException {

	public static class MustBeEmailAddress extends UserSmsException {

		public MustBeEmailAddress(String smsSn) {
			super(String.format("SMS %s must be an email address", smsSn));

			this.smsSn = smsSn;
		}

		public final String smsSn;

	}

	private UserSmsException(String msg) {
		super(msg);
	}

}