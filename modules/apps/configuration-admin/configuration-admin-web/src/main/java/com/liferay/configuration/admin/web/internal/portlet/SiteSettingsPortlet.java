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

package com.liferay.configuration.admin.web.internal.portlet;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-site-settings",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.display-name=Site Settings",
		"javax.portlet.info.keywords=osgi,site,settings",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SITE_SETTINGS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class SiteSettingsPortlet extends MVCPortlet {

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		checkSiteAdmin(actionRequest);

		super.processAction(actionRequest, actionResponse);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		checkSiteAdmin(renderRequest);

		super.render(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		checkSiteAdmin(resourceRequest);

		super.serveResource(resourceRequest, resourceResponse);
	}

	@SuppressWarnings("serial")
	public static class MustBeSiteAdmin extends PrincipalException {

		public MustBeSiteAdmin(long userId) {
			super(
				String.format(
					"User %s must be the site administrator to perform the " +
						"action",
					userId));

			this.userId = userId;
		}

		public MustBeSiteAdmin(PermissionChecker permissionChecker) {
			this(permissionChecker.getUserId());
		}

		public final long userId;

	}

	protected void checkSiteAdmin(PortletRequest portletRequest)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isGroupAdmin(themeDisplay.getScopeGroupId())) {
			PrincipalException principalException = new MustBeSiteAdmin(
				permissionChecker.getUserId());

			throw new PortletException(principalException);
		}
	}

}