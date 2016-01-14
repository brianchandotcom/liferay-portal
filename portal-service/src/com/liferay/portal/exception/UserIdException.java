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

package com.liferay.portal.exception;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class UserIdException extends PortalException {

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public UserIdException() {
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public UserIdException(String msg) {
		super(msg);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public UserIdException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by the inner classes
	 */
	@Deprecated
	public UserIdException(Throwable cause) {
		super(cause);
	}

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

}