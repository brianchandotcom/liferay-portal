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

package com.liferay.document.library.opener.google.drive.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.opener.google.drive.DLOpenerGoogleDriveManager;
import com.liferay.document.library.opener.google.drive.web.internal.constants.DLOpenerGoogleDriveWebKeys;
import com.liferay.document.library.opener.google.drive.web.internal.util.OAuth2Helper;
import com.liferay.document.library.opener.google.drive.web.internal.util.State;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"mvc.command.name=/document_library/open_google_docs"
	},
	service = MVCRenderCommand.class
)
public class OpenGoogleDocsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			State state = State.get(
				_portal.getOriginalServletRequest(
					_portal.getHttpServletRequest(renderRequest)));

			if (state == null) {
				_performAuthorizationFlow(renderRequest, renderResponse);
			}
			else {
				renderRequest.setAttribute(
					DLOpenerGoogleDriveWebKeys.
						DL_OPENER_GOOGLE_DRIVE_FILE_REFERENCE,
					_googleDriveManager.requestEditAccess(
						_portal.getUserId(renderRequest),
						_dlAppService.getFileEntry(
							ParamUtil.getLong(renderRequest, "fileEntryId"))));

				RequestDispatcher requestDispatcher =
					_servletContext.getRequestDispatcher(
						"/open_google_docs.jsp");

				requestDispatcher.include(
					_portal.getHttpServletRequest(renderRequest),
					_portal.getHttpServletResponse(renderResponse));
			}

			return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
		}
		catch (IOException | PortalException | ServletException e) {
			throw new PortletException(e);
		}
	}

	private String _getFailureURL(PortletRequest portletRequest)
		throws PortalException {

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			portletRequest, _portal.getPortletId(portletRequest),
			_portal.getControlPanelPlid(portletRequest),
			PortletRequest.RENDER_PHASE);

		return liferayPortletURL.toString();
	}

	private String _getSuccessURL(PortletRequest portletRequest) {
		return _portal.getCurrentURL(
			_portal.getHttpServletRequest(portletRequest));
	}

	private void _performAuthorizationFlow(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String state = PwdGenerator.getPassword(5);

		State.save(
			_portal.getOriginalServletRequest(
				_portal.getHttpServletRequest(portletRequest)),
			themeDisplay.getUserId(), _getSuccessURL(portletRequest),
			_getFailureURL(portletRequest), state);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(portletResponse);

		httpServletResponse.sendRedirect(
			_dlOpenerGoogleDriveManager.getAuthorizationURL(
				themeDisplay.getCompanyId(), state,
				_oAuth2Helper.getRedirectURI(portletRequest)));
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLOpenerGoogleDriveManager _dlOpenerGoogleDriveManager;

	@Reference
	private DLOpenerGoogleDriveManager _googleDriveManager;

	@Reference
	private OAuth2Helper _oAuth2Helper;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.document.library.opener.google.drive.web)"
	)
	private ServletContext _servletContext;

}