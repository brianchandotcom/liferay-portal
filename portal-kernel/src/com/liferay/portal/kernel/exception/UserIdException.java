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