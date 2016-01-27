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

package com.liferay.staging.security.spring;

import com.liferay.portal.service.RoleLocalService;
import com.liferay.portlet.exportimport.staging.Staging;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true)
public class RoleLocalServiceStagingAdvice extends LiveGroupStagingAdvice {

	public RoleLocalServiceStagingAdvice() throws NoSuchMethodException {
		super(RoleLocalService.class);

		initGroupServiceBuilderMethods();

		initCustomMethod("getDefaultGroupRole", 0, long.class);
		initCustomMethod("getGroupRelatedRoles", 0, long.class);
		initCustomMethod("getTeamRoleMap", 0, long.class);
		initCustomMethod("getTeamRoles", 0, long.class);
		initCustomMethod("getTeamRoles", 0, long.class, long[].class);
		initCustomMethod("getUserGroupGroupRoles", 1, long.class, long.class);
		initCustomMethod(
			"getUserGroupGroupRoles", 1, long.class, long.class, int.class,
			int.class);
		initCustomMethod(
			"getUserGroupGroupRolesCount", 1, long.class, long.class);
		initCustomMethod("getUserGroupRoles", 1, long.class, long.class);
		initCustomMethod("getUserRelatedRoles", 1, long.class, long.class);
		initCustomMethod("getUserRelatedRoles", 1, long.class, long[].class);
		initCustomMethod("getUserRelatedRoles", 1, long.class, List.class);

		checkCoverage(_GROUP_METHODS_WHITELIST);
	}

	@Reference
	protected void setService(RoleLocalService service) {
		registerAdvice(service);
	}

	@Reference(unbind = "-")
	protected void setStaging(Staging staging) {
	}

	protected void unsetService(RoleLocalService service) {
		unregisterAdvice(service);
	}

	private static final List<String> _GROUP_METHODS_WHITELIST = Arrays.asList(
		new String[] {"getGroupPrimaryKeys"});

}