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

package com.liferay.change.tracking.change.lists.configuration.web.internal.portlet;

import com.liferay.change.tracking.configuration.CTConfiguration;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;

import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Máté Thurzó
 */
@Component(
	configurationPid = "com.liferay.change.tracking.configuration.CTConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-change-lists-configuration",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"javax.portlet.display-name=Settings",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CTPortletKeys.CHANGE_LISTS_CONFIGURATION,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class ChangeListsConfigurationPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			checkPermissions(renderRequest);
		}
		catch (Exception e) {
			throw new PortletException(
				"Unable to check permissions: " + e.getMessage(), e);
		}

		boolean configurationSaved = ParamUtil.getBoolean(
			renderRequest, "configurationSaved");

		if (configurationSaved) {
			SessionMessages.add(renderRequest, "configurationSaved");
		}

		super.render(renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ctConfiguration = ConfigurableUtil.createConfigurable(
			CTConfiguration.class, properties);
	}

	@Override
	protected void checkPermissions(PortletRequest portletRequest)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker.isCompanyAdmin()) {
			return;
		}

		String[] administratorRoleNames =
			_ctConfiguration.administratorRoleNames();

		UserBag userBag = permissionChecker.getUserBag();

		for (Role role : userBag.getRoles()) {
			if (ArrayUtil.contains(administratorRoleNames, role.getName())) {
				return;
			}
		}

		throw new PrincipalException(
			String.format(
				"User %s must have administrator role",
				permissionChecker.getUserId()));
	}

	private CTConfiguration _ctConfiguration;

}