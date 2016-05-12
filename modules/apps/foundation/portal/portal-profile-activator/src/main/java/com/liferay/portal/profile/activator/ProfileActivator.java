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

package com.liferay.portal.profile.activator;

import java.util.Set;

import org.osgi.service.component.ComponentContext;

/**
 * @author Shuyang Zhou
 */
public interface ProfileActivator {

	public static final String CE_PROFILE = "CE";

	public static final String EE_PROFILE = "EE";

	public static final String PORTAL_PROFILES = "portal.profiles";

	public void activateByProfiles(
		ComponentContext componentContext, Set<String> profiles,
		String... componentNames);

}