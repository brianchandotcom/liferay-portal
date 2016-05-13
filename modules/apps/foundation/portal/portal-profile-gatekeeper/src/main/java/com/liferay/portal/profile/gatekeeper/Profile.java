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

package com.liferay.portal.profile.gatekeeper;

import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public interface Profile {

	public static final String CE_PORTAL_PROFILE_NAME = "CE";

	public static final String EE_PORTAL_PROFILE_NAME = "EE";

	public static final String PORTAL_PROFILE_NAMES = "portal.profile.names";

	public void activate();

	public Set<String> getSupportedPortalProfileNames();

}