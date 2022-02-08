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

package com.liferay.redirect.web.internal.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.redirect.configuration.RedirectConfiguration;
import com.liferay.redirect.service.RedirectEntryLocalService;
import com.liferay.redirect.service.RedirectEntryService;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;
import com.liferay.redirect.web.internal.constants.RedirectPortletKeys;
import com.liferay.redirect.web.internal.display.context.RedirectDisplayContext;
import com.liferay.redirect.web.internal.display.context.RedirectEntriesDisplayContext;
import com.liferay.redirect.web.internal.display.context.RedirectNotFoundEntriesDisplayContext;
import com.liferay.staging.StagingGroupHelper;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-redirect",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Redirect",
		"javax.portlet.init-param.always-send-redirect=true",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + RedirectPortletKeys.REDIRECT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class RedirectPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		RedirectDisplayContext redirectDisplayContext =
			new RedirectDisplayContext(
				_portal.getHttpServletRequest(renderRequest),
				_redirectConfiguration, renderResponse);

		renderRequest.setAttribute(
			RedirectDisplayContext.class.getName(), redirectDisplayContext);

		if (redirectDisplayContext.isShowRedirectNotFoundEntries()) {
			renderRequest.setAttribute(
				RedirectNotFoundEntriesDisplayContext.class.getName(),
				new RedirectNotFoundEntriesDisplayContext(
					_portal.getHttpServletRequest(renderRequest),
					_portal.getLiferayPortletRequest(renderRequest),
					_portal.getLiferayPortletResponse(renderResponse),
					_redirectNotFoundEntryLocalService));
		}
		else {
			renderRequest.setAttribute(
				RedirectEntriesDisplayContext.class.getName(),
				new RedirectEntriesDisplayContext(
					_portal.getHttpServletRequest(renderRequest),
					_portal.getLiferayPortletRequest(renderRequest),
					_portal.getLiferayPortletResponse(renderResponse),
					_redirectEntryLocalService, _redirectEntryService,
					_stagingGroupHelper));
		}

		super.render(renderRequest, renderResponse);
	}

	@Reference
	private Portal _portal;

	@Reference
	private RedirectConfiguration _redirectConfiguration;

	@Reference
	private RedirectEntryLocalService _redirectEntryLocalService;

	@Reference
	private RedirectEntryService _redirectEntryService;

	@Reference
	private RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}