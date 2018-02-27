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

package com.liferay.portal.apio.architect.context.user;

import com.liferay.portal.kernel.model.User;

/**
 * Provides the current {@code User}.
 *
 * <p>
 * To use this class, add it as a parameter to the methods of the different
 * routes builders.
 * </p>
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@FunctionalInterface
public interface CurrentUser {

	/**
	 * Returns the current user.
	 *
	 * @return the current user
	 */
	public User getUser();

	/**
	 * Returns the current user's ID.
	 *
	 * @return the current user's ID.
	 */
	public default long getUserId() {
		User user = getUser();

		return user.getUserId();
	}

}