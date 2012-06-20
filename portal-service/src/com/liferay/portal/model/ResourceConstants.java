/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

/**
 * Contains constants used for resource permissions and permission scoping.
 *
 * @author Brian Wing Shun Chan
 */
public interface ResourceConstants {

	public static final long PRIMKEY_DNE = -1;

	public static final int SCOPE_COMPANY = 1;

	public static final int SCOPE_GROUP = 2;

	public static final int SCOPE_GROUP_TEMPLATE = 3;

	public static final int SCOPE_INDIVIDUAL = 4;

	public static final int[] SCOPES = {
		ResourceConstants.SCOPE_COMPANY, ResourceConstants.SCOPE_GROUP,
		ResourceConstants.SCOPE_GROUP_TEMPLATE,
		ResourceConstants.SCOPE_INDIVIDUAL
	};

}