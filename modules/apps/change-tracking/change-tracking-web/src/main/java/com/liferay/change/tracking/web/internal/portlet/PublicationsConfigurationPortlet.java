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

import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.web.internal.constants.CTPortletKeys;
import com.liferay.change.tracking.web.internal.constants.CTWebKeys;
import com.liferay.change.tracking.web.internal.display.context.PublicationsConfigurationDisplayContext;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

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
		"com.liferay.portlet.css-class-wrapper=portlet-publications-configuration",
		"com.liferay.portlet.display-category=category.hidden",
		"javax.portlet.display-name=Settings",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/publications_configuration/view.jsp",
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS_CONFIGURATION,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class PublicationsConfigurationPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			checkPermissions(renderRequest);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		PublicationsConfigurationDisplayContext
			publicationsConfigurationDisplayContext =
				new PublicationsConfigurationDisplayContext(
					_ctPreferencesLocalService,
					_portal.getHttpServletRequest(renderRequest), _language,
					renderResponse);

		renderRequest.setAttribute(
			CTWebKeys.PUBLICATIONS_CONFIGURATION_DISPLAY_CONTEXT,
			publicationsConfigurationDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Override
	protected void checkPermissions(PortletRequest portletRequest)
		throws Exception {

		_portletPermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			CTPortletKeys.PUBLICATIONS_CONFIGURATION, ActionKeys.VIEW);
	}

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPermission _portletPermission;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.change.tracking.web)(&(release.schema.version>=1.0.1)(!(release.schema.version>=2.0.0))))"
	)
	private Release _release;

}