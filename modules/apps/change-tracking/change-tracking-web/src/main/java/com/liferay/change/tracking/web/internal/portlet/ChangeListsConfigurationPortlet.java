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

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.web.internal.configuration.CTConfiguration;
import com.liferay.change.tracking.web.internal.constants.CTWebKeys;
import com.liferay.change.tracking.web.internal.display.context.ChangeListsConfigurationDisplayContext;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	configurationPid = "com.liferay.change.tracking.web.internal.configuration.CTConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-change-lists-configuration",
		"com.liferay.portlet.display-category=category.hidden",
		"javax.portlet.display-name=Settings",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/change_lists_configuration/view.jsp",
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

		checkRender(renderRequest);

		ChangeListsConfigurationDisplayContext
			changeListsConfigurationDisplayContext =
				new ChangeListsConfigurationDisplayContext(
					_ctPreferencesLocalService,
					_portal.getHttpServletRequest(renderRequest), _language,
					renderResponse);

		renderRequest.setAttribute(
			CTWebKeys.CHANGE_LISTS_CONFIGURATION_DISPLAY_CONTEXT,
			changeListsConfigurationDisplayContext);

		super.render(renderRequest, renderResponse);
	}

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
		catch (Exception exception) {
			throw new PortletException(
				"Unable to check permissions: " + exception.getMessage(),
				exception);
		}
	}

	private Set<String> _administratorRoleNames;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}