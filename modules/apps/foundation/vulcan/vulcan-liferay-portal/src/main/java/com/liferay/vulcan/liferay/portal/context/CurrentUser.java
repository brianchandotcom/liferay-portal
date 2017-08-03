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

package com.liferay.vulcan.liferay.portal.context;

import com.liferay.portal.kernel.model.User;

/**
 * Provides the current {@link User}.
 *
 * <p>
 * To use this class, add it as a parameter in {@link
 * com.liferay.vulcan.resource.builder.RoutesBuilder} methods.
 * </p>
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
public interface CurrentUser {

	/**
	 * Returns the current {@link User}.
	 *
	 * @return current group.
	 */
	public User getUser();

}