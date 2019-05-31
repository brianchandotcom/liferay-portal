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

package com.liferay.change.tracking.change.lists.history.web.internal.portlet;

import com.liferay.change.tracking.configuration.CTConfiguration;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.constants.CTWebKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.servlet.SessionErrors;
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
import org.osgi.service.component.annotations.Reference;

/**
 * @author Laszlo Pap
 */
@Component(
	configurationPid = "com.liferay.change.tracking.configuration.CTConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=false",
		"com.liferay.portlet.css-class-wrapper=portlet-change-lists-history",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.show-portlet-access-denied=false",
		"com.liferay.portlet.show-portlet-inactive=false",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=History",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CTPortletKeys.CHANGE_LISTS_HISTORY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class ChangeListsHistoryPortlet extends MVCPortlet {

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

		long ctCollectionId = ParamUtil.getLong(
			renderRequest, "ctCollectionId");

		try {
			if (ctCollectionId > 0) {
				CTCollection ctCollection =
					_ctCollectionLocalService.getCTCollection(ctCollectionId);

				renderRequest.setAttribute(
					CTWebKeys.CT_COLLECTION, ctCollection);
			}
		}
		catch (PortalException pe) {
			SessionErrors.add(renderRequest, pe.getClass());
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
				"User %s must have administrator role to access %s",
				permissionChecker.getUserId(), getClass().getSimpleName()));
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	private CTConfiguration _ctConfiguration;

}