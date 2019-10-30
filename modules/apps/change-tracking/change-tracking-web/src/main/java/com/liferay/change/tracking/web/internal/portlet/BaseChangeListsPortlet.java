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

package com.liferay.change.tracking.web.internal.portlet;

import com.liferay.change.tracking.web.internal.configuration.CTConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.UserBag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;

import org.osgi.service.component.annotations.Activate;

/**
 * @author Preston Crary
 */
public abstract class BaseChangeListsPortlet extends MVCPortlet {

	@Activate
	protected void activate(Map<String, Object> properties) {
		Set<String> administratorRoleNames = new HashSet<>();

		CTConfiguration ctConfiguration = ConfigurableUtil.createConfigurable(
			CTConfiguration.class, properties);

		Collections.addAll(
			administratorRoleNames, ctConfiguration.administratorRoleNames());

		_administratorRoleNames = administratorRoleNames;
	}

	@Override
	protected void checkPermissions(PortletRequest portletRequest)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker.isCompanyAdmin()) {
			return;
		}

		UserBag userBag = permissionChecker.getUserBag();

		for (Role role : userBag.getRoles()) {
			if (_administratorRoleNames.contains(role.getName())) {
				return;
			}
		}

		throw new PrincipalException(
			String.format(
				"User %s must have administrator role to access %s",
				permissionChecker.getUserId(), getClass().getSimpleName()));
	}

	protected void checkRender(RenderRequest renderRequest)
		throws PortletException {

		try {
			checkPermissions(renderRequest);
		}
		catch (Exception e) {
			throw new PortletException(
				"Unable to check permissions: " + e.getMessage(), e);
		}
	}

	private Set<String> _administratorRoleNames;

}