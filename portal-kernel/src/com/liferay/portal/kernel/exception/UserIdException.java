/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class UserIdException extends PortalException {

	public static class MustNotBeNull extends UserIdException {

		public MustNotBeNull() {
			super("User ID must not be null");
		}

	}

	public static class MustNotBeReserved extends UserIdException {

		public MustNotBeReserved(long userId, String[] reservedUserIds) {
			super(
				String.format(
					"User ID %s must not be a reserved one such as: %s", userId,
					StringUtil.merge(reservedUserIds)));

			this.userId = userId;
			this.reservedUserIds = reservedUserIds;
		}

		public final String[] reservedUserIds;
		public final long userId;

	}

	private UserIdException(String msg) {
		super(msg);
	}

}